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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.inout.util.CachedObjects;
import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.inout.util.IShipmentCandidates.OverallStatus;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Msg;

import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.spi.ICandidateProcessor;
import de.metas.interfaces.I_C_BPartner;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php?title=Auftrag_versenden_mit_Abo-Lieferung_(2009_0027_G62)'>(2009 0027 G62)</a>"
 *
 */
public class OnlyOneOpenInvoiceCandProcessor implements ICandidateProcessor
{
	public static final String MSG_OPEN_INVOICE_1P = "ShipmentSchedule_OpenInvoice_1P";

	public static final String MSG_WITH_NEXT_SUBSCRIPTION = "ShipmentSchedule_WithNextSubscription";

	@Override
	public int processCandidates(final Properties ctx,
			final IShipmentCandidates candidates, final CachedObjects co, final String trxName)
	{
		int removeCount = 0;

		for (final I_M_InOut inOut : candidates.getCandidates())
		{
			for (final I_M_InOutLine inOutLine : candidates.getLines(inOut))
			{
				if (candidates.isLineDiscarded(inOutLine))
				{
					// this line won't be delivered anyways. Nothing to do
					continue;
				}

				removeCount += handleOnlyOneOpenInv(ctx, candidates, inOutLine, trxName, removeCount);
			}
		}
		return removeCount;
	}

	private int handleOnlyOneOpenInv(final Properties ctx,
			final IShipmentCandidates candidates,
			final I_M_InOutLine inOutLine, final String trxName, int removeCount)
	{
		final I_C_BPartner billPartner = InterfaceWrapperHelper.create(inOutLine.getC_OrderLine().getC_Order().getBill_BPartner(), I_C_BPartner.class);
		
		final IBPartnerStats stats = Services.get(IBPartnerStatsDAO.class).retrieveBPartnerStats(billPartner);

		final String creditStatus = I_C_BPartner.SO_CREDITSTATUS_ONE_OPEN_INVOICE;

		if (creditStatus.equals(stats.getSOCreditStatus()))
		{
			final BigDecimal soCreditUsed = stats.getSOCreditUsed();
			
			if (soCreditUsed.signum() > 0)
			{
				candidates.addStatusInfo(inOutLine, Msg.getMsg(ctx, MSG_OPEN_INVOICE_1P, new Object[] { soCreditUsed }));

				candidates.setOverallStatus(inOutLine, OverallStatus.DISCARD);
				removeCount = 1;
			}
		}
		return removeCount;
	}
}
