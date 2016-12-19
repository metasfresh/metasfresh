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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.util.Env;

public final class PlainContextAware implements IContextAware
{
	/**
	 * @return a {@link IContextAware} for given <code>ctx</code> and {@link ITrx#TRXNAME_None}.
	 */
	public static final PlainContextAware createUsingOutOfTransaction(final Properties ctx)
	{
		return new PlainContextAware(ctx, ITrx.TRXNAME_None);
	}
	
	/**
	 * @return a {@link IContextAware} for {@link Env#getCtx()} and {@link ITrx#TRXNAME_None}.
	 */
	public static final PlainContextAware createUsingOutOfTransaction()
	{
		return new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);
	}

	/**
	 * @return a {@link IContextAware} for given <code>ctx</code> and {@link ITrx#TRXNAME_ThreadInherited}.
	 */
	public static final PlainContextAware createUsingThreadInheritedTransaction(final Properties ctx)
	{
		return new PlainContextAware(ctx, ITrx.TRXNAME_ThreadInherited);
	}

	public static final PlainContextAware createUsingThreadInheritedTransaction()
	{
		return new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * This method implementation is deprecated and shall not be merged back into master! 
	 * It's just here so that other recent stuff which we ported here does compile. 
	 * 
	 * @return a {@link IContextAware} for given <code>ctx</code> and <code>trxName</code>.<br>
	 *         Its {@link #isAllowThreadInherited()} will return <code>false</code>.
	 */
	public static final PlainContextAware newWithTrxName(final Properties ctx, final String trxName)
	{
		return new PlainContextAware(ctx, trxName);
	}
	
	private final Properties ctx;
	private final String trxName;

	/**
	 * Creates an instance with {@link ITrx#TRXNAME_None} as <code>trxName</code>.
	 *
	 * @param ctx
	 */
	public PlainContextAware(final Properties ctx)
	{
		this(ctx, ITrx.TRXNAME_None);
	}

	public PlainContextAware(final Properties ctx, final String trxName)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;

		this.trxName = trxName;
	}

	/**
	 * Copy constructor
	 *
	 * @param contextProvider
	 */
	public PlainContextAware(final IContextAware contextProvider)
	{
		this(contextProvider.getCtx(), contextProvider.getTrxName());
	}

	@Override
	public String toString()
	{
		return "PlainContextAware["
				// +", ctx="+ctx // don't print it... it's fucking too big
				+ ", trxName=" + trxName
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(ctx)
				.append(trxName)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final PlainContextAware other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.appendByRef(ctx, other.ctx)
				.append(trxName, other.trxName)
				.isEqual();
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public String getTrxName()
	{
		return trxName;
	}
}
