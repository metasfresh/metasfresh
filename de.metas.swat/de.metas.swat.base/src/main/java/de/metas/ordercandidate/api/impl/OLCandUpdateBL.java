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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;

import de.metas.ordercandidate.api.IOLCandUpdateBL;
import de.metas.ordercandidate.api.OLCandUpdateResult;
import de.metas.ordercandidate.model.I_C_OLCand;

public class OLCandUpdateBL implements IOLCandUpdateBL
{
	@Override
	public OLCandUpdateResult updateOLCands(final Properties ctx, final Iterator<I_C_OLCand> candsToUpdate, final IParams params)
	{
		final ITrxItemProcessorExecutorService processorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);

		final ITrxItemProcessorContext processorCtx = processorExecutorService.createProcessorContext(ctx, null, params); // trx = null

		final ITrxItemProcessorExecutor<I_C_OLCand, OLCandUpdateResult> executor =
				processorExecutorService.createExecutor(
						processorCtx,
						new OLCandUpdater());

		return executor.execute(candsToUpdate);
	}
}
