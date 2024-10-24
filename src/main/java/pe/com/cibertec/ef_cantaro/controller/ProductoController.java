package pe.com.cibertec.ef_cantaro.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.ef_cantaro.entity.model.CategoriaEntity;
import pe.com.cibertec.ef_cantaro.entity.model.ProductoEntity;
import pe.com.cibertec.ef_cantaro.entity.model.UsuarioEntity;
import pe.com.cibertec.ef_cantaro.service.CategoriaService;
import pe.com.cibertec.ef_cantaro.service.ProductoService;
import pe.com.cibertec.ef_cantaro.service.UsuarioService;
import pe.com.cibertec.ef_cantaro.service.impl.PdfServices;

@Controller
@RequiredArgsConstructor
public class ProductoController {
	
	private final ProductoService productoService;
	
	private final CategoriaService categoriaService;
	
	
	private final UsuarioService usuarioService;
	
	private final PdfServices pdfServices;
	
	@GetMapping("/lista_producto")
	public String listarProducto(Model model, HttpSession session) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
		String correoSesion = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioPorCorreo(
				correoSesion);
				
		model.addAttribute("foto",usuarioEncontrado.getUrlImagenUsuario());
		model.addAttribute("nombreUsuario", usuarioEncontrado.getNombres()); 
		List<ProductoEntity> listarProducto = productoService.listarProducto();
		model.addAttribute("listaprod", listarProducto);
		return "lista_producto";
	}
	
	@GetMapping("/registrar_producto")
	public String mostrarRegistrarProducto(Model model, HttpSession session) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
		
		List<CategoriaEntity>listaCategoria = categoriaService.obtenerTodasCategorias();
		model.addAttribute("categorias",listaCategoria);
		model.addAttribute("producto", new ProductoEntity());
		return "registrar_producto";
	}
	
	@PostMapping("registrar_producto")
	public String registrarProducto(@ModelAttribute("producto") ProductoEntity productoEntity,
			Model model) {
		productoService.crearProducto(productoEntity);
		return "redirect:/lista_producto";
	}
	
	@GetMapping("/detalle_producto/{id}")
	public String verDetalle(Model model, HttpSession session,  @PathVariable("id") Integer id) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
	    ProductoEntity producto = productoService.buscarProductoPorId(id);
	    model.addAttribute("producto",producto);
	    return "detalle_producto";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProducto(Model model, @PathVariable("id") Integer id) {
		productoService.eliminarProducto(id);
		return "redirect:/lista_producto";
	}
	
	@GetMapping("/editar_producto/{id}")
	public String mostrarActualizar(@PathVariable("id")Integer id, Model model,  HttpSession session) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
		ProductoEntity producto = productoService.buscarProductoPorId(id);
		List<CategoriaEntity>listaCategoria = categoriaService.obtenerTodasCategorias();
		model.addAttribute("categorias",listaCategoria);
		model.addAttribute("producto", producto);
		return "editar_producto";
	}
	
	@PostMapping("/editar_producto/{id}")
	public String actualizarProducto(@PathVariable("id")Integer id, 
			@ModelAttribute("producto") ProductoEntity producto, Model model) {
		productoService.actualizarProducto(id, producto);
		return "redirect:/lista_producto";
	}
	
	@GetMapping("/generar_pdf")
    public ResponseEntity<InputStreamResource>generarPDf(HttpSession sesion) throws IOException{
		if (sesion.getAttribute("usuario") == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	    }
		List<ProductoEntity> listarProducto = productoService.listarProducto();
		
		Map<String, Object> datos = new HashMap<>();
	    datos.put("productos", listarProducto);
		
	    ByteArrayInputStream pdfBytes = pdfServices.generarPdf("template_pdf", datos);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=productos.pdf");
	    
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(pdfBytes));
	}
}
