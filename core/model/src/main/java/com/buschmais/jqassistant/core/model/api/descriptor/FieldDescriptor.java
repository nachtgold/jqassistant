package com.buschmais.jqassistant.core.model.api.descriptor;

import java.util.HashSet;
import java.util.Set;

/**
 * Describes a field (i.e. static or instance variable) of a Java class.
 */
public class FieldDescriptor extends ParentDescriptor implements DependentDescriptor, AnnotatedDescriptor,
		AccessModifierDescriptor {

    /**
     * The classes the field depends on.
     */
    private Set<ClassDescriptor> dependencies = new HashSet<ClassDescriptor>();

    /**
     * The classes this class is annotated by.
     */
    private Set<ClassDescriptor> annotations = new HashSet<ClassDescriptor>();

	/**
	 * Visibility of this field.
	 */
	private VisibilityModifier visbility;

	/**
	 * <code>true</code> if this field is static, otherwise <code>false</code>.
	 */
	private Boolean staticField;

	/**
	 * <code>true</code> if this field is final, otherwise <code>false</code>.
	 */
	private Boolean finalField;
	/**
	 * <code>true</code> if this field is transient, otherwise
	 * <code>false</code>.
	 */
	private Boolean transientField;
	/**
	 * <code>true</code> if this field is volatile, otherwise <code>false</code>
	 * .
	 */
	private Boolean volatileField;


    @Override
    public Set<ClassDescriptor> getDependencies() {
        return dependencies;
    }

    @Override
    public void setDependencies(Set<ClassDescriptor> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public Set<ClassDescriptor> getAnnotatedBy() {
        return annotations;
    }

    @Override
    public void setAnnotatedBy(Set<ClassDescriptor> annotations) {
        this.annotations = annotations;
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VisibilityModifier getVisibility() {
		return visbility;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVisibility(VisibilityModifier visibilityModifier) {
		visbility = visibilityModifier;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isStatic() {
		return staticField;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStatic(Boolean s) {
		staticField = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isFinal() {
		return finalField;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFinal(Boolean f) {
		finalField = f;
	}

	/**
	 * @return the transientField
	 */
	public Boolean isTransient() {
		return transientField;
	}

	/**
	 * @param transientField the transientField to set
	 */
	public void setTransient(Boolean transientField) {
		this.transientField = transientField;
	}

	/**
	 * @return the volatileField
	 */
	public Boolean isVolatile() {
		return volatileField;
	}

	/**
	 * @param volatileField the volatileField to set
	 */
	public void setVolatile(Boolean volatileField) {
		this.volatileField = volatileField;
	}

}