package de.metas.inoutcandidate.spi.impl;

import java.math.BigDecimal;

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

import java.util.Properties;

import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.ShipmentSchedulesMDC;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.ToString;

/**
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php?title=Auftrag_versenden_mit_Abo-Lieferung_(2009_0027_G62)'>(2009 0027 G62)</a>"
 *
 */
@ToString
public class OnlyOneOpenInvoiceCandProcessor implements IShipmentSchedulesAfterFirstPassUpdater
{
	public static final String MSG_OPEN_INVOICE_1P = "ShipmentSchedule_OpenInvoice_1P";

	public static final String MSG_WITH_NEXT_SUBSCRIPTION = "ShipmentSchedule_WithNextSubscription";

	private static final Logger logger = LogManager.getLogger(OnlyOneOpenInvoiceCandProcessor.class);

	@Override
	public void doUpdateAfterFirstPass(
			final Properties ctx,
			final IShipmentSchedulesDuringUpdate candidates)
	{
		for (final DeliveryGroupCandidate groupCandidate : candidates.getCandidates())
		{
			for (final DeliveryLineCandidate lineCandidate : groupCandidate.getLines())
			{
				if (lineCandidate.isDiscarded())
				{
					// this line won't be delivered anyways. Nothing to do
					continue;
				}
				handleOnlyOneOpenInv(ctx, candidates, lineCandidate);
			}
		}
	}

	private void handleOnlyOneOpenInv(final Properties ctx,
			final IShipmentSchedulesDuringUpdate candidates,
			final DeliveryLineCandidate lineCandidate)
	{
		try (final MDCCloseable mdcClosable = ShipmentSchedulesMDC.putShipmentScheduleId(lineCandidate.getShipmentScheduleId()))
		{
			final BPartnerStats stats = Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(lineCandidate.getBillBPartnerId());

			final String creditStatus = I_C_BPartner.SO_CREDITSTATUS_ONE_OPEN_INVOICE;
			if (creditStatus.equals(stats.getSoCreditStatus()))
			{
				final BigDecimal soCreditUsed = stats.getSoCreditUsed();

				if (soCreditUsed.signum() > 0)
				{
					logger.debug("Discard lineCandidate because of an existing open invoice");
					candidates.addStatusInfo(lineCandidate, Services.get(IMsgBL.class).getMsg(ctx, MSG_OPEN_INVOICE_1P, new Object[] { soCreditUsed }));
					lineCandidate.setDiscarded();
				}
			}
		}
	}
}
