package com.buschmais.jqassistant.plugin.java.impl.store.descriptor;

import com.buschmais.cdo.neo4j.api.annotation.Property;

/**
 * Created by dimahler on 12/6/13.
 */
public interface AbstractDescriptor {

    @Property("ABSTRACT")
    public Boolean isAbstract();

    void setAbstract(Boolean isAbstract);

}
