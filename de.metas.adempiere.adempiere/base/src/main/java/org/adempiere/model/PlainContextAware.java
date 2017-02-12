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
	 * @return a {@link IContextAware} for given <code>ctx</code> and {@link ITrx#TRXNAME_None}.<br>
	 *         Its {@link #isAllowThreadInherited()} will return <code>false</code>.
	 */
	public static final PlainContextAware newOutOfTrx(final Properties ctx)
	{
		final boolean allowThreadInherited = false;
		return new PlainContextAware(ctx, ITrx.TRXNAME_None, allowThreadInherited);
	}

	/**
	 * Like {@link #newOutOfTrx(Properties)}, but the returned instance's {@link #isAllowThreadInherited()} will return <code>true</code>.
	 *
	 * @param ctx
	 * @return
	 */
	public static final PlainContextAware newOutOfTrxAllowThreadInherited(final Properties ctx)
	{
		final boolean allowThreadInherited = true;
		return new PlainContextAware(ctx, ITrx.TRXNAME_None, allowThreadInherited);
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
	public static final PlainContextAware newWithThreadInheritedTrx(final Properties ctx)
	{
		return new PlainContextAware(ctx, ITrx.TRXNAME_ThreadInherited);
	}

	public static final PlainContextAware newWithThreadInheritedTrx()
	{
		return new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * @return a {@link IContextAware} for given <code>ctx</code> and <code>trxName</code>.<br>
	 *         Its {@link #isAllowThreadInherited()} will return <code>false</code>.
	 */
	public static final PlainContextAware newWithTrxName(final Properties ctx, final String trxName)
	{
		final boolean allowThreadInherited = false;
		return new PlainContextAware(ctx, trxName, allowThreadInherited);
	}

	public static final PlainContextAware newCopy(final IContextAware contextProvider)
	{
		return new PlainContextAware(contextProvider.getCtx(), contextProvider.getTrxName(), contextProvider.isAllowThreadInherited());
	}

	private final Properties ctx;
	private final String trxName;
	private final boolean allowThreadInherited;

	/**
	 * Creates an instance with {@link ITrx#TRXNAME_None} as <code>trxName</code>.
	 * <p>
	 * <b>WARNING:</b>
	 * <li>{@link InterfaceWrapperHelper#newInstance(Class, Object, boolean)
	 * and maybe other code will still use a thread-inherited trx over the "null" trx if there is any.
	 * <li>Use {@link #newOutOfTrx(Properties)}
	 *
	 * @param ctx
	 * @deprecated please use {@link #newOutOfTrx(Properties)} to get a context aware that will use a local trx.
	 */
	@Deprecated
	public PlainContextAware(final Properties ctx)
	{
		this(ctx, ITrx.TRXNAME_None);
	}

	/**
	 *
	 * @param ctx
	 * @param trxName
	 * @deprecated please use {@link #newWithTrxName(Properties, String)} instead.
	 */
	@Deprecated
	public PlainContextAware(final Properties ctx, final String trxName)
	{
		this(ctx, trxName, true); // allowThreadInherited == true for backward compatibility; also see javadoc of isAllowThreadInherited.
	}

	private PlainContextAware(final Properties ctx, final String trxName, final boolean allowThreadInherited)
	{
		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;

		this.trxName = trxName;
		this.allowThreadInherited = allowThreadInherited;
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

	@Override
	public boolean isAllowThreadInherited()
	{
		return allowThreadInherited;
	}
}
