package de.metas.adempiere.util.cache;

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


import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;

/**
 * Exception thrown when a value could not be retrieved from cache because of inconsistencies or because some parameters were not valid.
 * 
 * @author tsa
 *
 */
public class CacheGetException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2145287441754772885L;
	private Object targetObject;
	private Object[] methodArgs;
	private int parameterIndex;
	private Object parameter;
	private boolean parameterSet = false;
	private Class<? extends Annotation> annotationType = null;

	public CacheGetException(final String message)
	{
		super(message);
		setParseTranslation(false); // don't try to translate the build message because it's not translatable
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder message = new StringBuilder();
		message.append(super.buildMessage());

		if (targetObject != null)
		{
			message.append("\n Target object: ").append(targetObject);
		}

		if (methodArgs != null)
		{
			message.append("\n Method Arguments: ").append(Arrays.asList(methodArgs));
		}

		if (parameterSet)
		{
			message.append("\n Invalid parameter at index ").append(parameterIndex).append(": ").append(parameter);
			if (parameter != null)
			{
				message.append(" (").append(parameter.getClass()).append(")");
			}
		}

		if (annotationType != null)
		{
			message.append("\n Annotation: ").append(annotationType);
		}

		return message.toString();
	}

	public CacheGetException setTargetObject(final Object targetObject)
	{
		this.targetObject = targetObject;
		resetMessageBuilt();
		return this;
	}

	public CacheGetException setMethodArguments(final Object[] methodArgs)
	{
		this.methodArgs = methodArgs;
		resetMessageBuilt();
		return this;
	}

	public CacheGetException setInvalidParameter(final int parameterIndex, final Object parameter)
	{
		this.parameterIndex = parameterIndex;
		this.parameter = parameter;
		this.parameterSet = true;
		resetMessageBuilt();
		return this;
	}

	public CacheGetException setAnnotation(final Class<? extends Annotation> annotation)
	{
		this.annotationType = annotation;
		return this;
	}
	
	public CacheGetException addSuppressIfNotNull(final Throwable exception)
	{
		if(exception != null)
		{
			addSuppressed(exception);
		}
		return this;
	}
}
