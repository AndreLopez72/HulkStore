package com.todouno.hulkstore.infraestructure.repository;

import com.todouno.hulkstore.domain.modelo.Producto;
import com.todouno.hulkstore.infraestructure.helpers.util.NumberGenerator;
import com.todouno.hulkstore.infraestructure.helpers.util.TextUtil;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@Transactional(SUPPORTS)
public class ProductoRepository {
	
	
	
	@PersistenceContext(unitName = "producto")
    private EntityManager emEntityManager;
	
	
	@Inject
	private NumberGenerator generador;
	
	@Inject
	private TextUtil textUtil;
	
	
	 public Producto find(@NotNull Long id) {
	        return emEntityManager.find(Producto.class, id);
	    }

	    public List<Producto> findAll() {
	        TypedQuery<Producto> query = emEntityManager.createQuery("SELECT b FROM Producto b ORDER BY b.nombre DESC", Producto.class);
	        return query.getResultList();
	    }

	    public Long countAll() {
	        TypedQuery<Long> query = emEntityManager.createQuery("SELECT COUNT(b) FROM Producto b", Long.class);
	        return query.getSingleResult();
	    }

	    @Transactional(REQUIRED)
	    public Producto create(@NotNull Producto producto) {
	    	producto.setCodigo(generador.generarNumero());
	    	producto.setNombre(textUtil.limpiar(producto.getNombre()));
	        emEntityManager.persist(producto);
	        return producto;
	    }

	    @Transactional(REQUIRED)
	    public void delete(@NotNull Long id) {
	    	emEntityManager.remove(emEntityManager.getReference(Producto.class, id));
	    }

}
