package de.metas.cache.interceptor;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.util.concurrent.UncheckedExecutionException;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;

/**
 * Exception thrown when an annotated cached method could not be introspected or it's not valid.
 * 
 * @author tsa
 *
 */
public class CacheIntrospectionException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6014120086160495511L;

	public static final CacheIntrospectionException wrapIfNeeded(final Throwable e)
	{
		if (e == null)
		{
			return new CacheIntrospectionException("Unknown cache exception");
		}
		else if (e instanceof CacheIntrospectionException)
		{
			return (CacheIntrospectionException)e;
		}
		else if ((e instanceof InvocationTargetException) && (e.getCause() != null))
		{
			return wrapIfNeeded(e.getCause());
		}
		else if ((e instanceof UncheckedExecutionException) && (e.getCause() != null))
		{
			return wrapIfNeeded(e.getCause());
		}
		else
		{
			return new CacheIntrospectionException(e);
		}
	}

	private Method method = null;
	private int parameterIndex;
	private Class<?> parameterType;
	private boolean parameterSet;
	private Set<String> hintsToFix = null;

	public CacheIntrospectionException(final String message)
	{
		super(message);
	}

	public CacheIntrospectionException(final Throwable cause)
	{
		super(cause);
	}

	public CacheIntrospectionException setMethod(final Method method)
	{
		this.method = method;
		resetMessageBuilt();
		return this;
	}

	public CacheIntrospectionException setParameter(final int parameterIndex, final Class<?> parameterType)
	{
		this.parameterIndex = parameterIndex;
		this.parameterType = parameterType;
		this.parameterSet = true;
		resetMessageBuilt();
		return this;
	}

	public CacheIntrospectionException addHintToFix(final String hintToFix)
	{
		if (Check.isEmpty(hintToFix, true))
		{
			return this;
		}

		if (this.hintsToFix == null)
		{
			this.hintsToFix = new HashSet<>();
		}
		this.hintsToFix.add(hintToFix);
		resetMessageBuilt();
		return this;
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder();

		message.append(super.buildMessage());

		if (method != null)
		{
			message.append("\n Method: ").appendObj(method);
		}

		if (parameterSet)
		{
			message.append("\n Invalid parameter at index ").append(parameterIndex).append(": ").appendObj(parameterType);
		}

		if (!Check.isEmpty(hintsToFix))
		{
			for (final String hint : hintsToFix)
			{
				message.append("\n Hint to fix: ").append(hint);
			}
		}

		return message.build();
	}
}
