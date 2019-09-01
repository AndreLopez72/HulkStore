package com.todouno.hulkstore.infraestructure.entrypoint.controller;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.validation.constraints.Min;

import com.todouno.hulkstore.domain.modelo.Producto;
import com.todouno.hulkstore.infraestructure.repository.ProductoRepository; 
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.net.URI;
import java.util.List;


@SwaggerDefinition(
	    info = @Info(
	        title = "HulkStore APIs",
	        description = "Hulk store APIs expone de un Java EE back-end a un Angular front-end",
	        version = "V1.0.0",
	        contact = @Contact(
	            name = "Andres lopez",
	            email = "andres.lopez@sofka.com.co",
	            url = ""
	        )
	    ),
	    host = "localhost:8080",
	    basePath = "/hulkstore-back/api",
	    schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
	    tags = {
	        @Tag(name = "Producto", description = "etiqueta para operaciones")
	    }
	)

@Path("/producto")
@Api("Producto")
public class ProductoController {
	
	@Inject
    private ProductoRepository productoRepository;
	
	
	@POST
	@Consumes(APPLICATION_JSON)
	@ApiOperation("Creates un producto JSon")
	@ApiResponses({
		@ApiResponse(code = 201, message = "El Producto fue crado" ),
		@ApiResponse(code = 415, message = "El formato no es JSon" )
	})
	public Response crearProducto(@ApiParam(value = "Producto to be created", required = true) Producto producto, @Context UriInfo uriInfo) {
		producto = productoRepository.create(producto);
		URI createdURI = uriInfo.getAbsolutePathBuilder().path(producto.getId().toString()).build();
		return Response.created(createdURI).build();
	}
	
	@GET
	@Path("/{id : \\d+}")
	@Consumes(APPLICATION_JSON)
	@ApiOperation(value = "Retorna un Prducto dado un id", response = Producto.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Producto Encontrado" ),
		@ApiResponse(code = 400, message = "Entrada invalida. el id debe ser mayor que 1  " ),
		@ApiResponse(code = 404, message = "Libro no encontrado " )
	})
	public Response getProductoById(@PathParam("id") @Min(1) Long id) {
        Producto producto = productoRepository.find(id);

        if (producto == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(producto).build();
	}
	
	@DELETE
	@Path("/{id : \\d+}")
	@ApiOperation("Borra un producto dado un id")
	@ApiResponses({
		 @ApiResponse(code = 204, message = "El Producto fue borrado"),
	     @ApiResponse(code = 400, message = "Entrada invalida. el id debe ser mayor que 1"),
	     @ApiResponse(code = 500, message = "Producto no encontrado")
	})
	public Response deleteProducto(@PathParam("id") @Min(1) long id) {
		productoRepository.delete(id);
		return Response.noContent().build();
	}
	
	@GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Retorna todos los Productos", response = Producto.class, responseContainer = "List")
    @ApiResponses( {
        @ApiResponse(code = 200, message = "Productos Encontrados"),
        @ApiResponse(code = 204, message = "Productos no encontrados"),
    })
    public Response getAllProductos() {
        List<Producto> producto = productoRepository.findAll();

        if (producto.size() == 0)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(producto).build();
    }
	
	@GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Retorna el numero de productos", response = Long.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Numero de productos encontrados"),
        @ApiResponse(code = 204, message = "Productos no encontrados"),
    })
    public Response countProductos() {
        Long nbOfProducto = productoRepository.countAll();

        if (nbOfProducto == 0)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(nbOfProducto).build();
    }
	

}
