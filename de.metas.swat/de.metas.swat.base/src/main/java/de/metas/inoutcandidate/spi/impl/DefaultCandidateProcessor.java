package de.metas.inoutcandidate.spi.impl;

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

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate.CompleteStatus;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.ShipmentSchedulesMDC;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.util.Services;
import lombok.ToString;

@ToString
public class DefaultCandidateProcessor implements IShipmentSchedulesAfterFirstPassUpdater
{

	private static final String AD_SYSCONFIG_DE_METAS_INOUTCANDIDATE_ALLOW_SHIP_SINGLE_NON_ITEMS = "de.metas.inoutcandidate.AllowShipSingleNonItems";

	public final static String MSG_NO_ITEM_TO_SHIP = "ShipmentSchedule_NoItemToShip";

	private static final Logger logger = LogManager.getLogger(DefaultCandidateProcessor.class);

	@Override
	public final void doUpdateAfterFirstPass(
			final Properties ctx,
			final IShipmentSchedulesDuringUpdate candidates)
	{
		purgeLinesOK(ctx, candidates);
	}

	/**
	 * Removes all inOutLines (and afterwards also empty inOuts) that have {@link CompleteStatus#OK} <b>and</b>
	 * {@link PostageFreeStatus#OK}
	 */
	private void purgeLinesOK(final Properties ctx, final IShipmentSchedulesDuringUpdate candidates)
	{

		for (final DeliveryGroupCandidate groupCandidate : candidates.getCandidates())
		{
			for (final DeliveryLineCandidate lineCandidate : groupCandidate.getLines())
			{
				try (final MDCCloseable mdcClosable = ShipmentSchedulesMDC.withShipmentScheduleId(lineCandidate.getShipmentScheduleId()))
				{
					// check the complete and postage free status
					final CompleteStatus completeStatus = lineCandidate.getCompleteStatus();

					if (!CompleteStatus.OK.equals(completeStatus))
					{
						logger.debug("Discard lineCandidate because completeStatus={}", completeStatus);
						lineCandidate.setDiscarded();
					}

					//
					//
					final IProductBL productBL = Services.get(IProductBL.class);
					final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

					// task 08745: by default we don't allow this, to stay backwards compatible
					final boolean allowShipSingleNonItems = sysConfigBL.getBooleanValue(AD_SYSCONFIG_DE_METAS_INOUTCANDIDATE_ALLOW_SHIP_SINGLE_NON_ITEMS, false);
					final boolean isItemProduct = productBL.isItem(lineCandidate.getProductId());

					if (!allowShipSingleNonItems && !isItemProduct)
					{
						// allowShipSingleNonItems was true, we wouldn't have to care.

						// product is not an item -> check if there if there would
						// also be an item on the same inOut

						boolean inOutContainsItem = false;
						for (final DeliveryLineCandidate searchIol : groupCandidate.getLines())
						{
							if (productBL.isItem(searchIol.getProductId()))
							{
								inOutContainsItem = true;
								break;
							}
						}
						if (!inOutContainsItem)
						{
							// check if the delivery of this non-item has been
							// enforced using QtyToDeliver_Override>0

							final BigDecimal qtyToDeliverOverride = lineCandidate.getQtyToDeliverOverride();

							if (qtyToDeliverOverride == null || qtyToDeliverOverride.signum() <= 0)
							{
								logger.debug("Discard lineCandidate because its groupCandidate contains no 'item' products and qtyToDeliverOverride is not set");
								lineCandidate.setDiscarded();
								candidates.addStatusInfo(lineCandidate, Services.get(IMsgBL.class).getMsg(ctx, MSG_NO_ITEM_TO_SHIP));
							}
						}
					}
				}
			}
		}
	}
}
