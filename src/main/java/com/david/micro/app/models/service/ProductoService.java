package com.david.micro.app.models.service;

import org.springframework.http.codec.multipart.FilePart;

import com.david.micro.app.models.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//todos los metodos de nuestro servicio apirest
public interface ProductoService {

public Flux<Producto> findAll();
	
	public Mono<Producto> findByid(String id);
	
	public Mono<Producto> save(Producto producto);
	
	public Mono<Producto> update(Producto producto, String id);
	
	public Mono<Void> delete(String id);
	
	public Mono<Producto> upload(FilePart file, String id);
}
