package pe.com.cibertec.ef_cantaro.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productoId;
	
	@Column(name = "nombre")
	private String nombreProducto;
	
	@Column(name="precio", nullable = false)
	private Double precioProducto;
	
	@Column(name="cantidad", nullable = false)
	private Integer StockProducto;
	
	@ManyToOne
	@JoinColumn(name="fk_categoria", nullable = false)
	private CategoriaEntity categoriaEntity;
	
}
