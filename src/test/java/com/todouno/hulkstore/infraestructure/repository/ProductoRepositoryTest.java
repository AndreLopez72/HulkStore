package com.todouno.hulkstore.infraestructure.repository;

import org.junit.Test;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import com.todouno.hulkstore.domain.modelo.Material;
import com.todouno.hulkstore.domain.modelo.Producto;
import com.todouno.hulkstore.infraestructure.helpers.util.IsbnGenerator;
import com.todouno.hulkstore.infraestructure.helpers.util.NumberGenerator;
import com.todouno.hulkstore.infraestructure.helpers.util.TextUtil;

import javax.inject.Inject;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ProductoRepositoryTest {

	private static Long productoId;
	
	@Inject
    private ProductoRepository productoRepository;

    @Inject
    private IsbnGenerator isbnGenerator;

    @Inject
    private TextUtil textUtil;
    
    
    @Deployment
    public static Archive<?> createDeploymentPackage() {

        return ShrinkWrap.create(JavaArchive.class)
            .addClass(Producto.class)
            .addClass(Material.class)
            .addClass(ProductoRepository.class)
            .addClass(NumberGenerator.class)
            .addClass(IsbnGenerator.class)
            .addClass(TextUtil.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }

    // ======================================
    // =            Test methods            =
    // ======================================

    @Test
    @InSequence(1)
    public void BeDeployed() {
        assertNotNull(productoRepository);
        assertNotNull(isbnGenerator);
        assertNotNull(textUtil);
    }

    @Test
    @InSequence(2)
    public void GetNoProducto() {
        // Count all
        assertEquals(Long.valueOf(0), productoRepository.countAll());
        // Find all
        assertEquals(0, productoRepository.findAll().size());
    }

    @Test
    @InSequence(3)
    public void CreateAProductoproducto() {
        Producto producto = new Producto("isbn", "un nombre", 12F, 123, Material.ALGODON, new Date(), "imageURL", "descripcion");
        producto = productoRepository.create(producto);
        assertNotNull(producto);
        assertNotNull(producto.getId());
        productoId = producto.getId();
    }

    @Test
    @InSequence(4)
    public void FindTheCreatedProducto() {
        Producto productoFound = productoRepository.find(productoId);
        assertNotNull(productoFound.getId());
        assertTrue(productoFound.getCodigo().startsWith("13-84356-"));
        assertEquals("a nombre", productoFound.getNombre());
    }

    @Test
    @InSequence(5)
    public void GetOneProducto() {
        // Count all
        assertEquals(Long.valueOf(1), productoRepository.countAll());
        // Find all
        assertEquals(1, productoRepository.findAll().size());
    }

    @Test
    @InSequence(6)
    public void DeleteTheCreatedProducto() {
        productoRepository.delete(productoId);
        Producto productoDeleted = productoRepository.find(productoId);
        assertNull(productoDeleted);
    }

    @Test
    @InSequence(7)
    public void GetNoMoreProducto() {
        // Count all
        assertEquals(Long.valueOf(0), productoRepository.countAll());
        // Find all
        assertEquals(0, productoRepository.findAll().size());
    }

    @Test(expected = Exception.class)
    @InSequence(10)
    public void FailCreatingANullProducto() {
        productoRepository.create(null);
    }

    @Test(expected = Exception.class)
    @InSequence(11)
    public void FailCreatingAProductoWithNullnombre() {
        productoRepository.create(new Producto("isbn", null, 12F, 123, Material.ALGODON, new Date(), "imageURL", "descripcion"));
    }

    @Test(expected = Exception.class)
    @InSequence(12)
    public void FailCreatingAProductoWithLowUnitCostnombre() {
        productoRepository.create(new Producto("isbn", "nombre", 0F, 123, Material.ALGODON, new Date(), "imageURL", "descripcion"));
    }

    @Test
    @InSequence(13)
    public void NotFailCreatingAProductoWithNullISBN() {
        Producto productoFound = productoRepository.create(new Producto(null, "nombre", 12F, 123, Material.ALUMINIO, new Date(), "imageURL", "descripcion"));
        assertTrue(productoFound.getCodigo().startsWith("13-84356-"));
    }

    @Test(expected = Exception.class)
    @InSequence(14)
    public void FailInvokingFindByIdWithNull() {
        productoRepository.find(null);
    }

    @Test
    @InSequence(15)
    public void NotFindUnknownId() {
        assertNull(productoRepository.find(99999L));
    }

    @Test(expected = Exception.class)
    @InSequence(16)
    public void FailInvokingDeleteByIdWithNull() {
        productoRepository.delete(null);
    }
    @Test(expected = Exception.class)
    @InSequence(17)
    public void NotDeleteUnknownId() {
        productoRepository.delete(99999L);
    }
}