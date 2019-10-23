package org.adempiere.ad.modelvalidator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

/**
 * Factory class which is able to create and bind(register) annotated model validators
 * 
 * @author tsa
 * 
 */
public class AnnotatedModelInterceptorFactory
{
	private static final AnnotatedModelInterceptorFactory instance = new AnnotatedModelInterceptorFactory();

	public static AnnotatedModelInterceptorFactory get()
	{
		return instance;
	}

	/**
	 * Creates a {@link ModelValidator} object for given annotated class.
	 * 
	 * This method is not checking if the annotatedObject was already registered.
	 * 
	 * @param annotatedObject
	 * @return {@link ModelValidator} or null if the given object is not a valid annotated model validator or it has no pointcuts
	 * @throws AdempiereException
	 *             if annotations were not correctly used
	 */
	public IModelInterceptor createModelInterceptor(Object annotatedObject)
	{
		return createAnnotatedModelInterceptor(annotatedObject);
	}

	private final AnnotatedModelInterceptor createAnnotatedModelInterceptor(Object annotatedObject)
	{
		final AnnotatedModelInterceptor validator = new AnnotatedModelInterceptor(annotatedObject);
		if (validator.isEmpty())
		{
			return null;
		}

		return validator;
	}
}
