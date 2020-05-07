package org.adempiere.model;

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

import java.util.Properties;

/**
 * Simulates context objects.
 */
public interface IContextAware
{
	/**
	 * @return ctx
	 */
	Properties getCtx();

	/**
	 * @return trxName
	 */
	String getTrxName();

	/**
	 * Advise the caller if it should check for and prefer an existing inherited trx name.
	 *
	 * If {@link #getTrxName()} returned a local/null transaction name (see {@link org.adempiere.ad.trx.api.ITrxManager#isNull(String)})
	 * and if there is a thread-inherited transaction (see {@link org.adempiere.ad.trx.api.ITrxManager#getThreadInheritedTrx(org.adempiere.ad.trx.api.OnTrxMissingPolicy)}),
	 * then this method advises the caller whether is OK to use the thread-inherited transaction instead.
	 * <p>
	 * Background: the default implementation return <code>true</code> for backward compatibility reasons.
	 * In many cases the business logic developer didn't care if the <i>really</i> wanted a null-transaction or not, and in the past,
	 * {@link InterfaceWrapperHelper#newInstance(Class, Object, boolean)} always assumed <code>true</code>, so a lot of business logic might rely on this behavior.
	 *
	 * @return
	 * @see PlainContextAware#withOutOfTrx(Properties).
	 */
	default boolean isAllowThreadInherited()
	{
		return true;
	}
}
