package hl.sc.demo.organization.repository;

import hl.sc.demo.organization.model.Organization;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class OrganizationRepository {

	private List<Organization> organizations = new ArrayList<>();

    public Mono<Organization> add(Organization organization) {
        return Mono.just(organization)
                .doOnSuccess(o -> {
                    o.setId((long) (organizations.size() + 1));
                    organizations.add(o);
                });
	}

    public Mono<Organization> findById(Long id) {
        return Flux.fromIterable(organizations)
                .filter(a -> a.getId().equals(id)).next();
	}

    public Flux<Organization> findAll() {
        return Flux.fromIterable(organizations);
	}
	
}
