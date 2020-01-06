package tacos.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import tacos.Taco;
import tacos.data.TacoRepository;

@RepositoryRestController
public class RecentTacoController {

	private TacoRepository tacoRepository;

	@Autowired
	public RecentTacoController(TacoRepository tacoRepository) {
		this.tacoRepository = tacoRepository;
	}

	@GetMapping(path = "/tacos/recent", produces = "application/hal+json")
	public ResponseEntity<Resources<TacoResource>> recentTacos() {

		List<Taco> tacos = tacoRepository.findAll(PageRequest.of(0, 12, Sort.by("createdAt").descending())).getContent();
		
		List<TacoResource> tacoResources = TacoResource.tacoAssembler.toResources(tacos);
		
		Resources<TacoResource> recentResources = new Resources<>(tacoResources);
		
		
		recentResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RecentTacoController.class).recentTacos())
												 .withRel("recents"));
		
		return new ResponseEntity<>(recentResources, HttpStatus.OK);
		

	}

}
