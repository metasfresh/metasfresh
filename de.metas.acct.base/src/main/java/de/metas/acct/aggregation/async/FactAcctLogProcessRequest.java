package de.metas.acct.aggregation.async;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.acct
 * %%
 * Copyright (C) 2016 metas GmbH
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
public final class FactAcctLogProcessRequest
{
	public static final FactAcctLogProcessRequest of(final Properties ctx)
	{
		return new FactAcctLogProcessRequest(ctx, ITrx.TRXNAME_ThreadInherited);
	}

	public static FactAcctLogProcessRequest ofDocument(final Object document)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(document);
		final String trxName = InterfaceWrapperHelper.getTrxName(document);
		return new FactAcctLogProcessRequest(ctx, trxName);
	}

	private final Properties ctx;
	private final String trxName;

	private FactAcctLogProcessRequest(final Properties ctx, final String trxName)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;
		this.trxName = trxName;
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public String getTrxName()
	{
		return trxName;
	}

}
