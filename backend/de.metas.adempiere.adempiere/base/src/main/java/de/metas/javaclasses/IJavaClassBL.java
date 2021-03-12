package de.metas.javaclasses;

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.util.ISingletonService;

public interface IJavaClassBL extends ISingletonService
{
	/**
	 * Creates a new instance of the given class definition
	 */
	<T> T newInstance(I_AD_JavaClass javaClassDef);

	<T> T newInstance(JavaClassId javaClassId);

	<T> Class<T> verifyClassName(I_AD_JavaClass javaClassDef);
}
