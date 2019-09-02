package com.todouno.hulkstore.domain.modelo;


import javax.persistence.Entity;

import java.util.Date;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past; 



@Entity
public class Producto {
	

	@Id
    @GeneratedValue
    @ApiModelProperty("identificador")
    private Long id;

    @Column(length = 200)
    @NotNull
    @Size(min = 1, max = 200)
    @ApiModelProperty("Nombre del producto")
    private String nombre;

    @Column(length = 10000)
    @Size(min = 1, max = 10000)
    @ApiModelProperty("Descripcion del producto")
    private String descripcion;

    @Column(name = "uniCosto")
    @Min(1)
    @ApiModelProperty("Costo unitario")
    private Float uniCosto;

    @Column(length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty("Codigo")
    private String codigo;

    @Column(name = "datoProduccion")
    @Temporal(TemporalType.DATE)
    @Past
    @ApiModelProperty("Donde Fue elaborado el procucto")
    private Date datoProduccion;

    @Column(name = "catidad")
    @ApiModelProperty("Numero de productos")
    private Integer catidad;

    @Column(name = "imageURL")
    @ApiModelProperty("URL de la imagen")
    @Size(min = 1, max = 100)
    private String imageURL;

    @Enumerated
    @ApiModelProperty( value = "Material del producto")
    private Material material;
    
    public Producto(String codigo, String nombre, Float uniCosto, Integer catidad, Material material, Date datoProduccion, String imageURL, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.uniCosto = uniCosto;
        this.catidad = catidad;
        this.material = material;
        this.datoProduccion = datoProduccion;
        this.imageURL = imageURL;
        this.descripcion = descripcion;
    }
    
    public Producto () {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Float getUniCosto() {
		return uniCosto;
	}

	public void setUniCosto(Float uniCosto) {
		this.uniCosto = uniCosto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getDatoProduccion() {
		return datoProduccion;
	}

	public void setDatoProduccion(Date datoProduccion) {
		this.datoProduccion = datoProduccion;
	}

	public Integer getCatidad() {
		return catidad;
	}

	public void setCatidad(Integer catidad) {
		this.catidad = catidad;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Override
    public String toString() {
        return "Producto{" +
            "id=" + id +
            ", title='" + nombre + '\'' +
            ", description='" + descripcion + '\'' +
            ", unitCost=" + uniCosto +
            ", isbn='" + codigo + '\'' +
            ", publicationDate=" + datoProduccion +
            ", Material=" + material +
            '}';
    }
	

}