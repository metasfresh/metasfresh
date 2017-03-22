package org.adempiere.ad.trx.api.impl;

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


import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.util.Check;

/**
 * Default immutable implementation for {@link ITrxRunConfig}
 *
 * @author ts
 *
 */
@Immutable
final class TrxRunConfig implements ITrxRunConfig
{
	private final TrxPropagation trxMode;
	private final OnRunnableSuccess onRunnableSuccess;
	private final OnRunnableFail onRunnableFail;
	private final boolean autocommit;

	public TrxRunConfig(final TrxPropagation trxMode,
			final OnRunnableSuccess onRunnableSuccess,
			final OnRunnableFail onRunnableFail,
			final boolean autoCommit)
	{
		Check.assumeNotNull(trxMode, "Param 'trxMode' is not null");
		Check.assumeNotNull(onRunnableSuccess, "Param 'onRunnableSuccess' is not null");
		Check.assumeNotNull(onRunnableFail, "Param 'onRunnableFail' is not null");

		this.trxMode = trxMode;
		this.onRunnableSuccess = onRunnableSuccess;
		this.onRunnableFail = onRunnableFail;
		this.autocommit = autoCommit;
	}

	@Override
	public String toString()
	{
		return "TrxRunConfig [trxMode=" + trxMode + ", onRunnableSuccess=" + onRunnableSuccess + ", onRunnableFail=" + onRunnableFail + "]";
	}



	@Override
	public TrxPropagation getTrxPropagation()
	{
		return trxMode;
	}

	@Override
	public OnRunnableSuccess getOnRunnableSuccess()
	{
		return onRunnableSuccess;
	}

	@Override
	public OnRunnableFail getOnRunnableFail()
	{
		return onRunnableFail;
	}

	@Override
	public boolean isAutoCommit()
	{
		return autocommit;
	}
}
