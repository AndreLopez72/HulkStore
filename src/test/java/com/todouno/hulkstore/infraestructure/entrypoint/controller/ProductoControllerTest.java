package com.todouno.hulkstore.infraestructure.entrypoint.controller;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.todouno.hulkstore.domain.modelo.Material;
import com.todouno.hulkstore.domain.modelo.Producto;
import com.todouno.hulkstore.infraestructure.helpers.util.IsbnGenerator;
import com.todouno.hulkstore.infraestructure.helpers.util.NumberGenerator;
import com.todouno.hulkstore.infraestructure.helpers.util.TextUtil;
import com.todouno.hulkstore.infraestructure.repository.ProductoRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNSUPPORTED_MEDIA_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(Arquillian.class)
@RunAsClient
public class ProductoControllerTest {
	
	
	   private static String productoId;
	    private Response response;

	
	
	
	
	 @Deployment(testable = false)
    public static Archive<?> createDeploymentPackage() {

        return ShrinkWrap.create(WebArchive.class)
            .addClass(Producto.class)
            .addClass(Material.class)
            .addClass(ProductoRepository.class)
            .addClass(NumberGenerator.class)
            .addClass(IsbnGenerator.class)
            .addClass(TextUtil.class)
            .addClass(ProductoController.class)
            .addClass(JAXRSConfiguration.class)
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }

	  @Test
	    @InSequence(2)
	    public void GetNoProducto(@ArquillianResteasyResource("api/productos") WebTarget webTarget) {
	        response = webTarget.path("count").request().get();
	        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
	        response = webTarget.request(APPLICATION_JSON).get();
	        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
	    }

	    @Test
	    @InSequence(3)
	    public void CreateAProducto(@ArquillianResteasyResource("api/productos") WebTarget webTarget) {
	        Producto producto = new Producto("isbn", "a   nombre", 12F, 123, Material.ACETATO, null, "imageURL", "description");
	        response = webTarget.request(APPLICATION_JSON).post(Entity.entity(producto, APPLICATION_JSON));
	        assertEquals(CREATED.getStatusCode(), response.getStatus());
	        String location = response.getHeaderString("location");
	        assertNotNull(location);
	        productoId = location.substring(location.lastIndexOf("/") + 1);
	    }

	    @Test
	    @InSequence(4)
	    public void dFindTheCreatedProducto(@ArquillianResteasyResource("api/productos") WebTarget webTarget) {
	        response = webTarget.path(productoId).request(APPLICATION_JSON).get();
	        assertEquals(OK.getStatusCode(), response.getStatus());
	        Producto productoFound = response.readEntity(Producto.class);
	        assertNotNull(productoFound.getId());
	        assertTrue(productoFound.getCodigo().startsWith("13-84356-"));
	        assertEquals("un nombre", productoFound.getNombre());
	    }

	    @Test
	    @InSequence(5)
	    public void GetOneProducto(@ArquillianResteasyResource("api/productos") WebTarget webTarget) {
	        response = webTarget.path("count").request().get();
	        assertEquals(OK.getStatusCode(), response.getStatus());
	        assertEquals(Long.valueOf(1), response.readEntity(Long.class));
	        response = webTarget.request(APPLICATION_JSON).get();
	        assertEquals(OK.getStatusCode(), response.getStatus());
	        assertEquals(1, response.readEntity(List.class).size());
	    }

	    @Test
	    @InSequence(6)
	    public void DeleteTheCreatedProducto(@ArquillianResteasyResource("api/productos") WebTarget webTarget) {
	        response = webTarget.path(productoId).request(APPLICATION_JSON).delete();
	        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
	        Response checkResponse = webTarget.path(productoId).request(APPLICATION_JSON).get();
	        assertEquals(NOT_FOUND.getStatusCode(), checkResponse.getStatus());
	    }
}