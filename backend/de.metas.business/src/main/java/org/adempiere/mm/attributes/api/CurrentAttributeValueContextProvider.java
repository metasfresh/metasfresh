package org.adempiere.mm.attributes.api;

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


import org.adempiere.mm.attributes.spi.IAttributeValueContext;

import de.metas.util.Check;

public final class CurrentAttributeValueContextProvider
{
	private static final ThreadLocal<IAttributeValueContext> currentAttributesContextRef = new ThreadLocal<IAttributeValueContext>();

	private CurrentAttributeValueContextProvider()
	{
		super();
	}

	/**
	 * @return {@link IAttributeValueContext} available or <code>null</code>
	 */
	public static IAttributeValueContext getCurrentAttributesContextOrNull()
	{
		return currentAttributesContextRef.get();
	}

	public static IAttributeValueContext setCurrentAttributesContext(final IAttributeValueContext attributesContext)
	{
		final IAttributeValueContext attributesContextOld = currentAttributesContextRef.get();

		currentAttributesContextRef.set(attributesContext);
		return attributesContextOld;
	}

	/**
	 * Makes sure current attribute context is not set (i.e. null)
	 */
	public static void assertNoCurrentContext()
	{
		final IAttributeValueContext currentAttributesContext = getCurrentAttributesContextOrNull();
		Check.assumeNull(currentAttributesContext, "currentAttributesContext shall be null but it was {}", currentAttributesContext);
	}

}
