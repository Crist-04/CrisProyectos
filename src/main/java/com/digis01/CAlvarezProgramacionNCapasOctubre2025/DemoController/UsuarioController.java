package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DemoController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.CodigoPostalDAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.ColoniaDAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.DireccionDAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.EstadoDAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.MunicipioDAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.RolDAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.UsuarioDAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Colonia;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Direccion;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.ErrorCarga;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Rol;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Usuario;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    @Autowired
    private CodigoPostalDAOImplementation codigoPostalDAOImplementation;
    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;
    @Autowired
    private ValidationService validationService;

//    @GetMapping
//    public String UsuarioIndex(Model model) {
//        Result result = usuarioDAOImplementation.GetAll();
//        model.addAttribute("usuarios", result.objects);
//        return "UsuarioIndex";
//    }
    @GetMapping
    public String UsuarioIndex(Model model) {

        Result result = usuarioDAOImplementation.GetAll();

        Result rolesResult = rolDAOImplementation.GetAll();

        model.addAttribute("usuarios", result.objects);
        model.addAttribute("roles", rolesResult.objects);
        model.addAttribute("usuarioBusqueda", new Usuario());

        return "UsuarioIndex";
    }

    @GetMapping("formulario")
    public String UsuarioForm() {
        return "UsuarioForm";
    }

    @GetMapping("cargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }
    
    
@GetMapping("/cargaMasiva/procesar")
    public String CargaMasivaProcesar(HttpSession session) {
        String path = session.getAttribute("archivoCargaMasiva").toString();
        session.removeAttribute("archivoCargaMasiva");

        // Inserción con carga masiva

        return "CargaMasiva";
    }

    @PostMapping("/cargaMasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo, Model model, HttpSession session) throws IOException {
        String extension = archivo.getOriginalFilename().split("\\.")[1];

        String path = System.getProperty("user.dir");
        String pathArchivo = "src/main/resources/archivosCarga";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String pathDefinitivo = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();

        archivo.transferTo(new File(pathDefinitivo));

        List<Usuario> usuarios;
        if (extension.equals("txt")) {
            usuarios = LecturaArchivoTXT(new File(pathDefinitivo));
        } else if (extension.equals("xlsx")) {
            usuarios = LecturaArchivoXLSX(new File(pathDefinitivo));
        } else {
            model.addAttribute("errorMessage", "Tipo de archivo no válido. Solo se permiten TXT o XLSX.");
            model.addAttribute("mostrarBotonProcesar", false);
            return "CargaMasiva";
        }

        List<ErrorCarga> errores = ValidarDatosArchivo(usuarios);

        if (errores.isEmpty()) {
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("mostrarBotonProcesar", true);
            model.addAttribute("successMessage", "Archivo leído correctamente");
            session.setAttribute("archivoCargaMasiva", pathDefinitivo);
        } else {
            model.addAttribute("errores", errores);
            model.addAttribute("mostrarBotonProcesar", false);
            model.addAttribute("errorMessage", "Se encontraron " + errores.size() + " errores en el archivo.");
        }

        return "CargaMasiva";
    }

    public List<ErrorCarga> ValidarDatosArchivo(List<Usuario> usuarios) {
        List<ErrorCarga> erroresCarga = new ArrayList<>();
        int lineaError = 0;

        for (Usuario usuario : usuarios) {
            lineaError++;
            BindingResult bindingResult = validationService.validateObject(usuario);
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.campo = fieldError.getField();
                errorCarga.descripcion = fieldError.getDefaultMessage();
                errorCarga.linea = lineaError;
                erroresCarga.add(errorCarga);
            }
        }
        return erroresCarga;
    }

    public List<Usuario> LecturaArchivoTXT(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();

        try (InputStream fileInputStream = new FileInputStream(archivo); 
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));) {
            String linea = "";

            while ((linea = bufferedReader.readLine()) != null) {
                String[] campos = linea.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setNombre(campos[0].trim());
                usuario.setApellidoPaterno(campos[1].trim());
                usuario.setApellidoMaterno(campos[2].trim());
                usuario.setUserName(campos[3].trim());
                usuario.setEmail(campos[4].trim());
                usuario.setPassword(campos[5].trim());
                usuario.setTelefono(campos[6].trim());
                usuario.setCelular(campos[7].trim());
                usuario.setCURP(campos[8].trim());
                usuario.setSexo(campos[9].trim());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNacimiento = sdf.parse(campos[10].trim());
                usuario.setFechaNacimiento(fechaNacimiento);

                Rol rol = new Rol();
                int idRol = Integer.parseInt(campos[11].trim());
                rol.setIdRol(idRol);
                usuario.setRol(rol);

                usuarios.add(usuario);
            }

        } catch (Exception ex) {
            return null;
        }
        return usuarios;
    }

    public List<Usuario> LecturaArchivoXLSX(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();

        try (InputStream fileInputStream = new FileInputStream(archivo);
             XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
            XSSFSheet workSheet = workbook.getSheetAt(0);
            for (Row row : workSheet) {
                Usuario usuario = new Usuario();
                usuario.setNombre(row.getCell(0).toString());
                usuario.setApellidoPaterno(row.getCell(1).toString());
                usuario.setApellidoMaterno(row.getCell(2).toString());
                usuario.setUserName(row.getCell(3).toString());
                usuario.setEmail(row.getCell(4).toString());
                usuario.setPassword(row.getCell(5).toString());
                DecimalFormat df = new DecimalFormat("#");
                usuario.setTelefono(df.format(row.getCell(6).getNumericCellValue()));
                usuario.setCelular(df.format(row.getCell(7).getNumericCellValue()));
                usuario.setCURP(row.getCell(8).toString());
                usuario.setSexo(row.getCell(9).toString());
                usuario.setFechaNacimiento(row.getCell(10).getDateCellValue());

                Rol rol = new Rol();
                int idRol = (int) row.getCell(11).getNumericCellValue();
                rol.setIdRol(idRol);
                usuario.setRol(rol);

                usuarios.add(usuario);
            }

        } catch (Exception ex) {
            return null;
        }
        return usuarios;
    }

//    @GetMapping("/add")
//    public String Add(Model model) {
////        Usuario usuario = new Usuario();
////        usuario.setRol(new Rol());
////        usuario.setDireccion(new Direccion());
////        usuario.getDireccion().Colonia.getIdColonia();
////        usuario.getDireccion().Colonia.getNombre();
//////    usuario.getDireccion().setColonia(new Colonia());
//        model.addAttribute("Usuario", new Usuario());
//        model.addAttribute("roles", rolDAOImplementation.GetAll());
//        Result resultRoles = rolDAOImplementation.GetAll();
//
//    
//    // Pasar la lista de roles al modelo
//    model.addAttribute("roles", resultRoles.objects);
//        return "UsuarioForm";
//    }
    @GetMapping("/add")
    public String Add(Model model) {
        Usuario usuario = new Usuario();

        usuario.setDirecciones(new ArrayList<>());

        Direccion direccion = new Direccion();
        direccion.setColonia(new Colonia());
        usuario.getDirecciones().add(direccion);

        model.addAttribute("Usuario", usuario);

        Result resultRoles = rolDAOImplementation.GetAll();
        model.addAttribute("roles", resultRoles.objects);

        return "UsuarioForm";
    }

    @PostMapping
    public String UsuarioIndex(@ModelAttribute("usuarioBusqueda") Usuario usuarioBusqueda, Model model) {

        String nombre = usuarioBusqueda.getNombre() != null ? usuarioBusqueda.getNombre() : "";
        String aPaterno = usuarioBusqueda.getApellidoPaterno() != null ? usuarioBusqueda.getApellidoPaterno() : "";
        String aMaterno = usuarioBusqueda.getApellidoMaterno() != null ? usuarioBusqueda.getApellidoMaterno() : "";
        int idRol = usuarioBusqueda.getRol() != null ? usuarioBusqueda.getRol().getIdRol() : 0;

        Result result = usuarioDAOImplementation.GetAllDinamico(nombre, aPaterno, aMaterno, idRol);

        Result rolesResult = rolDAOImplementation.GetAll();

        model.addAttribute("usuarios", result.objects);
        model.addAttribute("roles", rolesResult.objects);
        model.addAttribute("usuarioBusqueda", usuarioBusqueda);

        return "UsuarioIndex";
    }

    @PostMapping("add")
    public String Form(@Valid @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("foto") MultipartFile foto) {

        if (bindingResult.hasErrors()) {

            if (usuario.getDirecciones() == null) {
                usuario.setDirecciones(new ArrayList<>());
            }

            if (usuario.getDirecciones().isEmpty()) {
                Direccion direccion = new Direccion();
                direccion.setColonia(new Colonia());
                usuario.getDirecciones().add(direccion);
            } else {
                if (usuario.getDirecciones().get(0) != null) {
                    if (usuario.getDirecciones().get(0).getColonia() == null) {
                        usuario.getDirecciones().get(0).setColonia(new Colonia());
                    }
                }
            }

            //Recargar los roles
            Result resultRoles = rolDAOImplementation.GetAll();
            model.addAttribute("roles", resultRoles.objects);

            model.addAttribute("Usuario", usuario);

            return "UsuarioForm";
        }

        if (foto != null) {
            try {

                String extension = foto.getOriginalFilename().split("\\.")[1];
                if (extension.equals("jpg") || extension.equals("png")) {
                    byte[] byteImagen = foto.getBytes();
                    String imagenBase64 = Base64.getEncoder().encodeToString(byteImagen);
                    usuario.setImagen(imagenBase64);

                }

            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

        usuarioDAOImplementation.Add(usuario);

        redirectAttributes.addFlashAttribute("successMessage", "El usuario: " + usuario.getUserName() + " se creo con exito");

        return "redirect:/usuario";

    }

//     if (result.correct) {
//            redirectAttributes.addFlashAttribute("successMessage", "El usuario " + usuario.getUserName() + " se creó con éxito");
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", "Error al crear el usuario: " + result.errorMessage);
//        }
    @GetMapping("detalle/{idUsuario}")
    public String UsuarioDetalle(@PathVariable("idUsuario") int idUsuario, Model model,
            RedirectAttributes redirectAtrributes) {
        Result result = usuarioDAOImplementation.GetById(idUsuario);

        if (result.correct && result.object != null) {
            model.addAttribute("usuario", result.object);
            return "UsuarioDetail";
        } else {

            return "redirect:/usuario";
        }
    }

    @GetMapping("estado/{idPais}")
    @ResponseBody
    public Result GetEstadoByIdPaid(@PathVariable("idPais") int idPais) {
        return estadoDAOImplementation.GetByIdPais(idPais);
    }

    @GetMapping("municipio/{idEstado}")
    @ResponseBody
    public Result GetByIdEstado(@PathVariable("idEstado") int idEstado) {
        return municipioDAOImplementation.GetByIdEstado(idEstado);
    }

    @GetMapping("colonia/{idMunicipio}")
    @ResponseBody
    public Result GetByIdMunicipio(@PathVariable("idMunicipio") int idMunicipio) {
        return coloniaDAOImplementation.GetByIdMunicipio(idMunicipio);
    }

    @GetMapping("codigopostal/{idColonia}")
    @ResponseBody
    public Result GetByIdColonia(@PathVariable("idColonia") int idColonia) {
        return codigoPostalDAOImplementation.GetByIdColonia(idColonia);
    }

    @PostMapping("/detalle")
    public String UpdateUsuario(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes) {
        Result result = usuarioDAOImplementation.Update(usuario);

        if (result.correct) {
            redirectAttributes.addFlashAttribute("successMessage", "El usuario " + usuario.getUserName() + " se actualizó con éxito");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar el usuario: " + result.errorMessage);
        }

        return "redirect:/usuario/detalle/" + usuario.getIdUsuario();
    }

    @PostMapping("/direccion/update")
    public String UpdateDireccion(
            @RequestParam("idDireccion") int idDireccion,
            @RequestParam("idUsuario") int idUsuario,
            @RequestParam("calle") String calle,
            @RequestParam("numeroExterior") String numeroExterior,
            @RequestParam(value = "numeroInterior", required = false) String numeroInterior,
            @RequestParam("colonia.idColonia") int idColonia,
            RedirectAttributes redirectAttributes) {

        try {

            Direccion direccion = new Direccion();
            direccion.setIdDireccion(idDireccion);
            direccion.setCalle(calle);
            direccion.setNumeroExterior(numeroExterior);
            direccion.setNumeroInterior(numeroInterior != null && !numeroInterior.trim().isEmpty() ? numeroInterior : null);

            Colonia colonia = new Colonia();
            colonia.setIdColonia(idColonia);
            direccion.setColonia(colonia);

            Result result = direccionDAOImplementation.UpdateDireccion(direccion, idUsuario);

            if (result.correct) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "La dirección se actualizó con éxito");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Error al actualizar la dirección: " + result.errorMessage);
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error inesperado al actualizar la dirección: " + ex.getMessage());
        }

        return "redirect:/usuario/detalle/" + idUsuario;
    }

    @PostMapping("/direccion/add")
    public String AddDireccion(
            @RequestParam("idUsuario") int idUsuario,
            @RequestParam("calle") String calle,
            @RequestParam("numeroExterior") String numeroExterior,
            @RequestParam(value = "numeroInterior", required = false) String numeroInterior,
            @RequestParam("colonia.idColonia") int idColonia,
            RedirectAttributes redirectAttributes) {

        try {
            Direccion direccion = new Direccion();
            direccion.setCalle(calle);
            direccion.setNumeroExterior(numeroExterior);
            direccion.setNumeroInterior(numeroInterior);

            Colonia colonia = new Colonia();
            colonia.setIdColonia(idColonia);
            direccion.setColonia(colonia);

            Result result = direccionDAOImplementation.AddDireccion(direccion, idUsuario);

            if (result.correct) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "La dirección se agregó con éxito");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Error: " + result.errorMessage);
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error: " + ex.getMessage());
        }

        return "redirect:/usuario/detalle/" + idUsuario;
    }

    @PostMapping("/direccion/delete")
    @ResponseBody
    public Result DeleteDireccion(
            @RequestParam("idDireccion") int idDireccion) {

        try {
            Result result = direccionDAOImplementation.DeleteDireccion(idDireccion);

            if (result.correct) {
                result.errorMessage = "La dirección se eliminó correctamente";
            } else {
                result.errorMessage = "No se pudo eliminar la dirección";
            }

            return result;

        } catch (Exception ex) {
            Result result = new Result();
            result.correct = false;
            result.errorMessage = "Error al eliminar dirección: " + ex.getMessage();
            result.ex = ex;
            return result;
        }
    }

}

//    @PostMapping("/add")
//    public String Add(@ModelAttribute Usuario usuario) {
//
//        usuario.setDirecciones(new ArrayList<>());
//
//        Result result = usuarioDAOImplementation.Add(usuario);
//        return "UsuarioIndex";
//    }
//    @GetMapping
//    public String UsuarioIndex() {
//        return "UsuarioIndex";
//    }
//    
//    
//    @GetMapping("formulario")
//    public String UsuarioForm() {
//        return "UsuarioForm";
//    