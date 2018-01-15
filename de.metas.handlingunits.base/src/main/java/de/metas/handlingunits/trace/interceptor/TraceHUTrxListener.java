package de.metas.handlingunits.trace.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.setTrxName;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.trace.HUTraceEventsService;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 *
 * Contains glue code such that:
 * <ul>
 * <li>{@link HUTraceEventsService#createAndAddForHuParentChanged(I_M_HU, I_M_HU_Item)} is invoked when an HU parent relation is changed.</li>
 * <li>{@link HUTraceEventsService#createAndAddFor(I_M_HU_Trx_Hdr, List)} is invoked <b>after commit</b> when a hu-trx is processed.<br>
 * It's important to be run after the commit, because otherwise, the respective HU-storages (products and qtys) are not there yet.
 * </li>
 * </ul>
 * <p>
 */
public class TraceHUTrxListener implements IHUTrxListener
{
	private static final Logger logger = LogManager.getLogger(TraceHUTrxListener.class);

	public static TraceHUTrxListener INSTANCE = new TraceHUTrxListener();

	private TraceHUTrxListener()
	{
	}

	@Override
	public void huParentChanged(
			@NonNull final I_M_HU hu,
			@Nullable final I_M_HU_Item parentHUItemOld)
	{
		final HUTraceEventsService huTraceEventService = HUTraceModuleInterceptor.INSTANCE.getHUTraceEventsService();
		huTraceEventService.createAndAddForHuParentChanged(hu, parentHUItemOld);
	}

	@Override
	public void afterTrxProcessed(
			@NonNull final IReference<I_M_HU_Trx_Hdr> trxHdrRef,
			@NonNull final List<I_M_HU_Trx_Line> trxLines)
	{
		final I_M_HU_Trx_Hdr trxHdr = trxHdrRef.getValue(); // we'll need the record, not the reference that might be stale after the commit.

		if (Adempiere.isUnitTestMode())
		{
			afterTrxProcessed0(trxLines, trxHdr); // in unit test mode, there won't be a commit
			return;
		}

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrxListenerManager trxListenerManager = trxManager.getTrxListenerManager(ITrx.TRXNAME_ThreadInherited);
		trxListenerManager
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(innerTrx -> {

					// do a brand new transaction in which we execute our things,
					// because basically 'innerTrx' is already done and might even already be closed
					final ITrxManager innerTrxManager = Services.get(ITrxManager.class);
					innerTrxManager.run(localTrxName -> {

						// we need to update our subjects' trxNames, because the one this listener method was called with was committed and therefore is not valid anymore
						setTrxName(trxHdr, localTrxName);
						trxLines.forEach(l -> setTrxName(l, localTrxName));

						afterTrxProcessed0(trxLines, trxHdr);
					});
				});
		logger.info("Enqueued M_HU_Trx_Hdr and _M_HU_Trx_Lines for HU-tracing after the next commit; trxHdr={}; trxLines={}", trxHdr, trxLines);
	}

	private void afterTrxProcessed0(
			@NonNull final List<I_M_HU_Trx_Line> trxLines,
			@NonNull final I_M_HU_Trx_Hdr trxHdr)
	{
		logger.info("Invoke HUTraceEventsService; trxHdr={}; trxLines={}", trxHdr, trxLines);

		final HUTraceEventsService huTraceEventService = HUTraceModuleInterceptor.INSTANCE.getHUTraceEventsService();
		huTraceEventService.createAndAddFor(trxHdr, trxLines);
	}
}
