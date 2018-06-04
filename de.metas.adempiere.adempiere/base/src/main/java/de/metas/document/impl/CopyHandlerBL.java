package de.metas.document.impl;

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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.util.Util;

import de.metas.document.ICopyHandler;
import de.metas.document.ICopyHandlerBL;
import de.metas.document.IDocLineCopyHandler;

public class CopyHandlerBL implements ICopyHandlerBL
{
	/**
	 * Using LinkedHashMap to make sure the keys are iterated in the order of registration.
	 */
	final Map<ICopyHandler<?>, IQueryFilter<?>> handler2Filter = new LinkedHashMap<>();

	final Map<ICopyHandler<?>, Class<?>> handler2Class = new LinkedHashMap<>();

	@Override
	public <T> void registerCopyHandler(final Class<T> clazz, final IQueryFilter<ImmutablePair<T, T>> filter, final ICopyHandler<? extends T> handler)
	{
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(clazz);
		if (Check.isEmpty(tableName))
		{
			return;
		}
		if (handler2Filter.containsKey(handler))
		{
			return;
		}

		handler2Filter.put(handler, filter);
		handler2Class.put(handler, clazz);
	}

	@Override
	public <T> void copyPreliminaryValues(T from, T to)
	{
		for (final ICopyHandler<?> handler : handler2Class.keySet())
		{
			if (!isApplicable(handler, from, to))
			{
				continue; // nothing to do with this handler
			}

			@SuppressWarnings("unchecked")
			// note: isApplicable made sure that this won't fail
			final ICopyHandler<T> castedHandler = (ICopyHandler<T>)handler;

			@SuppressWarnings("unchecked")
			final Class<T> supportedItemsClass = (Class<T>)handler.getSupportedItemsClass();

			// note: we use the handler's SupportedItemsClass and InterfaceWrapperHelper so make sure that the handler will be called with the correct subtype of 'T'.
			castedHandler.copyPreliminaryValues(
					InterfaceWrapperHelper.create(from, supportedItemsClass),
					InterfaceWrapperHelper.create(to, supportedItemsClass));
		}
	}

	@Override
	public <T> void copyValues(T from, T to)
	{
		for (final ICopyHandler<?> handler : handler2Class.keySet())
		{
			if (!isApplicable(handler, from, to))
			{
				continue; // nothing to do with this handler
			}

			@SuppressWarnings("unchecked")
			// note: isApplicable made sure that this won't fail
			final ICopyHandler<T> castedHandler = (ICopyHandler<T>)handler;

			@SuppressWarnings("unchecked")
			final Class<T> supportedItemsClass = (Class<T>)handler.getSupportedItemsClass();

			// note: we use the handler's SupportedItemsClass and InterfaceWrapperHelper so make sure that the handler will be called with the correct subtype of 'T'.
			castedHandler.copyValues(
					InterfaceWrapperHelper.create(from, supportedItemsClass),
					InterfaceWrapperHelper.create(to, supportedItemsClass));
		}
	}

	/**
	 *
	 * @param handler
	 * @param from
	 * @param to
	 * @return <code>true</code> if
	 *         <ul>
	 *         <li>the given <code>from</code> and <code>to</code> have a table name (see {@link InterfaceWrapperHelper#getModelTableNameOrNull(Object)}), which is equal to the table name of the class
	 *         the given <code>handler</code> has registered with.
	 *         <li>the class the given <code>handler</code> was registered with is a super class/interface or the same as the class of the given <code>from</code> and <code>to</code>
	 *
	 *         </ul>
	 */
	private <T> boolean isApplicable(final ICopyHandler<?> handler, final T from, final T to)
	{
		final String tTypeTableName = InterfaceWrapperHelper.getModelTableNameOrNull(from);
		if (Check.isEmpty(tTypeTableName))
		{
			return false; // actually shouldn't happen
		}

		final Class<?> handlerClazz = handler2Class.get(handler);
		if (Util.same(NullDocLineCopyHandler.class, handlerClazz))
		{
			return false; // we are dealing with the null handler. Nothing to do.
		}

		final String handlerTableName = InterfaceWrapperHelper.getTableNameOrNull(handlerClazz);

		if (!Objects.equals(handlerTableName, tTypeTableName))
		{
			return false; // not applicable, because the handler was registered for a different table
		}

		if (!handlerClazz.isAssignableFrom(from.getClass()))
		{
			return false; // not applicable, because the handler was registered for a class that is (despite having the same table!) not compatible with the class of 'from' and 'to' example: handler
							 // has registered for the "commission" order line and 'from' and 'to' are "HU" order lines.
		}

		@SuppressWarnings("unchecked")
		// if reached this point we made sure that the cast won't fail.
		final IQueryFilter<IPair<T, T>> filter = (IQueryFilter<IPair<T, T>>)handler2Filter.get(handler);

		return filter.accept(ImmutablePair.of(from, to));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IDocLineCopyHandler<T> getNullDocLineCopyHandler()
	{
		return (IDocLineCopyHandler<T>)NullDocLineCopyHandler.instance;
	}
}
