package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;

public interface IHUContextFactory extends ISingletonService
{

	/**
	 * Create HU Context.
	 *
	 * Compared with {@link #createMutableHUContext(IContextAware)} this method optimizes the HU Context for HU generation/processing (i.e. enable caching etc etc).
	 *
	 * @param contextProvider
	 * @return mutable HU context for <code>contextProvider</code>
	 */
	IMutableHUContext createMutableHUContextForProcessing(IContextAware contextProvider);

	/**
	 *
	 * @param ctx
	 * @return mutable HU context (out of transaction)
	 * @see #createMutableHUContextForProcessing(IContextAware)
	 */
	IMutableHUContext createMutableHUContextForProcessing(Properties ctx);

	default IMutableHUContext createMutableHUContextForProcessing()
	{
		return createMutableHUContextForProcessing(PlainContextAware.newWithThreadInheritedTrx());
	}

	/**
	 * Creates a mutable context with the given <code>ctx</code> (may not be <code>null</code>) and <code>trxName</code> = {@link ITrx#TRXNAME_None}.
	 *
	 * @param ctx
	 * @return mutable HU context
	 */
	IMutableHUContext createMutableHUContext(Properties ctx);

	/**
	 * Creates a mutable context with the given <code>ctx</code> and <code>trxName</code>.
	 *
	 * @param ctx
	 * @param trxName
	 * @return mutable HU context
	 */
	IMutableHUContext createMutableHUContext(Properties ctx, String trxName);

	default IMutableHUContext createMutableHUContext()
	{
		return createMutableHUContext(PlainContextAware.newWithThreadInheritedTrx());
	}

	/**
	 * @param contextProvider
	 * @return mutable HU context for <code>contextProvider</code>
	 */
	IMutableHUContext createMutableHUContext(IContextAware contextProvider);

	/**
	 * Create an identical context but with our given "trxName".
	 *
	 * @param context
	 * @param trxName
	 * @return newly created context
	 */
	IHUContext deriveWithTrxName(IHUContext context, String trxName);

}
