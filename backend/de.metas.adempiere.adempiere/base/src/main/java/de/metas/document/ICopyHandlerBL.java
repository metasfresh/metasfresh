package de.metas.document;

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


import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.ImmutablePair;

/**
 * Generic service that allows us do add handlers (could also be called listeners for all I know) which add module specific aspects to the copying of records.
 * 
 * @author ts
 * 
 */
public interface ICopyHandlerBL extends ISingletonService
{
	/**
	 * Registers a copy handler and a filter for a given type. Note that handlers will be evaluated in the order of their registration. If a handler was already registered before, the method will do
	 * nothing.
	 * 
	 * @param <T> the type (like <code>I_C_Order</code> or <code>M_Product</code> we register the handler for).
	 * @param filter the given implementation shall decide if the given handler handler is to be applied for a given record or not.
	 * @param handler the given implementation will do some kind of copying.
	 * @see InterfaceWrapperHelper#getTableNameOrNull(Class)
	 */
	<T> void registerCopyHandler(Class<T> clazz, IQueryFilter<ImmutablePair<T, T>> filter, ICopyHandler<? extends T> handler);

	/**
	 * Invokes {@link ICopyHandler#copyPreliminaryValues(Object, Object)} for all applicable handlers, in the order of their registration.
	 * 
	 * @param from
	 * @param to
	 */
	<T> void copyPreliminaryValues(T from, T to);

	/**
	 * Invokes {@link ICopyHandler#copyValues(Object, Object)} for all applicable handlers, in the order of their registration.
	 * 
	 * @param from
	 * @param to
	 */
	<T> void copyValues(T from, T to);
	
	<T> IDocLineCopyHandler<T> getNullDocLineCopyHandler();

}
