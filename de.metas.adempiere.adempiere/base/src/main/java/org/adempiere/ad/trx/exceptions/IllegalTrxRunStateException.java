package org.adempiere.ad.trx.exceptions;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.util.Check;

/**
 * Exception thrown when {@link ITrxManager#run(String, ITrxRunConfig, org.compiere.util.TrxRunnable)} methods are encounting an invalid state.
 * Possible reasons:
 * <ul>
 * <li>an invalid {@link ITrxRunConfig} was provided
 * <li>some invalid internal state was encounted
 * </ul>
 * 
 * @author tsa
 *
 */
public class IllegalTrxRunStateException extends TrxException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4659574948858028974L;

	private ITrxRunConfig trxRunConfig;
	private String trxName;
	private boolean trxNameSet;

	/**
	 * @param message reason why is not valid
	 */
	// NOTE: keep this method here no matter what because we want to use this exception in Check.assume methods.
	public IllegalTrxRunStateException(final String message)
	{
		super(message);
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder sb = new StringBuilder();

		final String message = super.buildMessage();
		if (Check.isEmpty(message, true))
		{
			sb.append("Illegal transaction run state");
		}
		else
		{
			sb.append(message);
		}

		if (trxRunConfig != null)
		{
			sb.append("\nTrxRunConfig: ").append(trxRunConfig);
		}

		if (trxNameSet)
		{
			sb.append("\nTrxName: ").append(trxName);
		}

		return sb.toString();

	}

	public IllegalTrxRunStateException setTrxRunConfig(final ITrxRunConfig trxRunConfig)
	{
		this.trxRunConfig = trxRunConfig;
		resetMessageBuilt();
		return this;
	}

	public IllegalTrxRunStateException setTrxName(final String trxName)
	{
		this.trxName = trxName;
		this.trxNameSet = true;
		resetMessageBuilt();
		return this;
	}
}
