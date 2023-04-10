package com.david.micro.app.models.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.david.micro.app.models.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
//Queda registrado en el contenedor de spring como un servicio cliente
//implementacion del servicio
@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private WebClient.Builder client;

	@Override
	public Flux<Producto> findAll() {
		// TODO Auto-generated method stub
		// lo modificamos un poco con respecto al curso porque los metodos del curso nos salia deprecados
		return client.build().get().accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(Producto.class);
//				.exchangeToFlux(response -> response.bodyToFlux(Producto.class));
	}
	@Override
	public Mono<Producto> findByid(String id) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", id);
		
		return (Mono<Producto>) client.build().get().uri("/{id}", params)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Producto.class);
//				.exchangeToMono(response -> response.bodyToMono(Producto.class));
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		// TODO Auto-generated method stub
		return client.build().post()
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(producto))
				.retrieve()
				.bodyToMono(Producto.class);
	}

	@Override
	public Mono<Producto> update(Producto producto, String id) {
		// TODO Auto-generated method stub
		return client.build().put()
				
				.uri("/{id}" , Collections.singletonMap("id", id))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(producto))
				.retrieve()
				.bodyToMono(Producto.class);
	}

	@Override
	public Mono<Void> delete(String id) {
		// TODO Auto-generated method stub
		return client.build().delete()
				.uri("/{id}" , Collections.singletonMap("id", id))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Producto.class)
				.then();
	}
	@Override
	public Mono<Producto> upload(FilePart file, String id) {
		// TODO Auto-generated method stub
		MultipartBodyBuilder parts = new MultipartBodyBuilder();
		parts.asyncPart("file", file.content(), DataBuffer.class).headers(h -> {
			h.setContentDispositionFormData("file",file.filename());	
		});
		
		return client.build().post()
				.uri("/upload/{id}", Collections.singletonMap("id", id))
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.body(BodyInserters.fromValue(parts.build()))
				.retrieve()
				.bodyToMono(Producto.class);
	}

}
