package pe.com.cibertec.ef_cantaro.service;

import java.util.List;

import pe.com.cibertec.ef_cantaro.entity.model.ProductoEntity;

public interface ProductoService {
	List<ProductoEntity>listarProducto();
	ProductoEntity buscarProductoPorId(Integer id);
	void crearProducto(ProductoEntity productoEntity);
	void actualizarProducto(Integer id, ProductoEntity productoEntity);
	void eliminarProducto(Integer id);
}
