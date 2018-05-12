package com.nidis.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;

public abstract class ResourceWithEmbeddeds extends ResourceSupport {

    @JsonUnwrapped
    private Resources<EmbeddedWrapper> embeddeds;

    public Resources<EmbeddedWrapper> getEmbeddeds() {
        return embeddeds;
    }

    public void setEmbeddeds(Resources<EmbeddedWrapper> embeddeds) {
        this.embeddeds = embeddeds;
    }
}
