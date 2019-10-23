package org.adempiere.ad.trx.exceptions;

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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;

/**
 * Exception thrown when {@link ITrx} was not found for a given transaction name.
 * 
 * @author tsa
 *
 */
public class TrxNotFoundException extends TrxException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 662721300736919676L;

	public TrxNotFoundException(final ITrxManager trxManager, final String trxName)
	{
		super(buildMsg(trxManager, trxName));
	}

	public TrxNotFoundException(final String message)
	{
		super(message);
	}

	private static final String buildMsg(final ITrxManager trxManager, final String trxName)
	{
		final StringBuilder sb = new StringBuilder();

		sb.append("No transaction was found for trxName='").append(trxName).append("'.");

		if (trxManager != null)
		{
			sb.append("\n Active transactions: ").append(trxManager.getActiveTransactionsList());
		}

		return sb.toString();
	}

}
