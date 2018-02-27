package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.text.annotation.ToStringBuilder;

import com.google.common.base.MoreObjects;

import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;

/* package */class DefaultInOutGenerateResult implements InOutGenerateResult
{
	@ToStringBuilder(skip = true)
	private final boolean storeInOuts;

	private int inoutCount = 0;
	private final List<I_M_InOut> inouts = new ArrayList<>();
	@ToStringBuilder(skip = true)
	private final List<I_M_InOut> inoutsRO = Collections.unmodifiableList(inouts);

	public DefaultInOutGenerateResult()
	{
		this(false);
	}

	public DefaultInOutGenerateResult(final boolean storeInOuts)
	{
		this.storeInOuts = storeInOuts;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("inoutCount", inoutCount)
				.add("storeInOuts", storeInOuts)
				.add("inouts", inoutsRO)
				.toString();
	}

	@Override
	public void addInOut(final I_M_InOut inOut)
	{
		if (storeInOuts)
		{
			// Avoid an internal transaction name to slip out to external world
			InterfaceWrapperHelper.setTrxName(inOut, ITrx.TRXNAME_ThreadInherited);

			inouts.add(inOut);
		}
		inoutCount++;
	}

	@Override
	public List<I_M_InOut> getInOuts()
	{
		if (!storeInOuts)
		{
			throw new AdempiereException("Cannot provide the generated shipments because the result was not configured to retain them");
		}
		return inoutsRO;
	}

	@Override
	public int getInOutCount()
	{
		if (storeInOuts)
		{
			return inouts.size();
		}
		return inoutCount;
	}
}
