package pe.com.cibertec.ef_cantaro.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.ef_cantaro.entity.model.CategoriaEntity;
import pe.com.cibertec.ef_cantaro.entity.model.ProductoEntity;
import pe.com.cibertec.ef_cantaro.service.CategoriaService;
import pe.com.cibertec.ef_cantaro.service.ProductoService;

@Controller
@RequiredArgsConstructor
public class ProductoController {
	
	private final ProductoService productoService;
	
	private final CategoriaService categoriaService;
	
	@GetMapping("/lista_producto")
	public String listarProducto(Model model, HttpSession session) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
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

	
}
