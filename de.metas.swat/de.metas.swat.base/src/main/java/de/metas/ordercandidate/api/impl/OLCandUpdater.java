package de.metas.ordercandidate.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;

import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.api.IParams;

import de.metas.ordercandidate.api.OLCandUpdateResult;
import de.metas.ordercandidate.model.I_C_OLCand;

public final class OLCandUpdater implements ITrxItemProcessor<I_C_OLCand, OLCandUpdateResult>
{
	private final OLCandUpdateResult result = new OLCandUpdateResult();
	private ITrxItemProcessorContext processorCtx;

	public OLCandUpdater()
	{
	}

	@Override
	public void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
	{
		this.processorCtx = processorCtx;
	}

	@Override
	public void process(final I_C_OLCand olCand) throws Exception
	{
		if (olCand.isProcessed())
		{
			result.incSkipped();
			return;
		}

		final IParams params = processorCtx.getParams();
		Check.errorIf(params == null, "Given processorCtx {} needs to contain params", processorCtx);

		// Partner
		final int bpartnerId = params.getParameterAsInt(I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID);
		olCand.setC_BPartner_Override_ID(bpartnerId);

		// Location
		final int bpartnerLocationId = params.getParameterAsInt(I_C_OLCand.COLUMNNAME_C_BP_Location_Override_ID);
		olCand.setC_BP_Location_Override_ID(bpartnerLocationId);

		// DatePrommissed
		final Timestamp datePromissed = params.getParameterAsTimestamp(I_C_OLCand.COLUMNNAME_DatePromised_Override);
		olCand.setDatePromised_Override(datePromissed);

		InterfaceWrapperHelper.save(olCand);
		result.incUpdated();
	}

	@Override
	public OLCandUpdateResult getResult()
	{
		return result;
	}
}
