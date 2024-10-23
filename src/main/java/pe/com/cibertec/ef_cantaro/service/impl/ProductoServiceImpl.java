package pe.com.cibertec.ef_cantaro.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.ef_cantaro.entity.model.ProductoEntity;
import pe.com.cibertec.ef_cantaro.repository.ProductoRepository;
import pe.com.cibertec.ef_cantaro.service.ProductoService;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{
	
	private final ProductoRepository productoRepository;
	
	@Override
	public List<ProductoEntity> listarProducto() {
		// TODO Auto-generated method stub
		return productoRepository.findAll();
	}

	@Override
	public void crearProducto(ProductoEntity productoEntity) {
		productoRepository.save(productoEntity);
	}
	@Override
	public ProductoEntity buscarProductoPorId(Integer id) {
		// TODO Auto-generated method stub
		return productoRepository.findById(id)
				.get();
	}

	@Override
	public void eliminarProducto(Integer id) {
		ProductoEntity productoEncontrado = buscarProductoPorId(id);
		if(productoEncontrado == null) {
			throw new RuntimeException("Producto no encontrado");
		}
		productoRepository.delete(productoEncontrado);
		
	}

	@Override
	public void actualizarProducto(Integer id, ProductoEntity productoEntity) {
		ProductoEntity productoEncontrado = buscarProductoPorId(id);
		if(productoEncontrado == null) {
			throw new RuntimeException("Producto no encontrado");
		}
		try {
			productoEncontrado.setNombreProducto(productoEntity.getNombreProducto());
			productoEncontrado.setPrecioProducto(productoEntity.getPrecioProducto());
			productoEncontrado.setStockProducto(productoEntity.getStockProducto());
			productoEncontrado.setCategoriaEntity(productoEntity.getCategoriaEntity());
			productoRepository.save(productoEncontrado);
		}catch(Exception e) {
			throw new RuntimeException("Error al actualizar");
		}
		
	}



}
