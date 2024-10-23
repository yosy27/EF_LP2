package pe.com.cibertec.ef_cantaro.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.ef_cantaro.entity.model.CategoriaEntity;
import pe.com.cibertec.ef_cantaro.repository.CategoriaRepository;
import pe.com.cibertec.ef_cantaro.service.CategoriaService;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService{

	private final CategoriaRepository categoriaRepository;
	@Override
	public List<CategoriaEntity> obtenerTodasCategorias() {
		// TODO Auto-generated method stub
		return categoriaRepository.findAll();
		}

}
