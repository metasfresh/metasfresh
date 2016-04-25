package de.metas.procurement.base.balance;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import de.metas.procurement.base.event.impl.PMMQtyReportEventTrxItemProcessor_TestHelper;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate_OrderLine;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;
import de.metas.procurement.base.order.interceptor.PMM_PurchaseCandidate_OrderLine;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class IntegrationBalanceTest extends AbstractBalanceScenariosTest
{

	@Override
	protected void addQtyPromised(final PMMBalanceSegment segment, final Date date, final BigDecimal qtyPromised, final BigDecimal qtyPromisedTU)
	{
		final I_PMM_QtyReport_Event qtyReportEvent = InterfaceWrapperHelper.newInstance(I_PMM_QtyReport_Event.class);
		qtyReportEvent.setC_BPartner_ID(segment.getC_BPartner_ID());
		qtyReportEvent.setM_Product_ID(segment.getM_Product_ID());
		qtyReportEvent.setM_AttributeSetInstance_ID(segment.getM_AttributeSetInstance_ID());
		//qtyReportEvent.setM_HU_PI_Item_Product_ID(segment.getM_HU_PI_Item_Product_ID());
		qtyReportEvent.setC_Flatrate_DataEntry_ID(segment.getC_Flatrate_DataEntry_ID());
		qtyReportEvent.setDatePromised(TimeUtil.asTimestamp(date));
		qtyReportEvent.setQtyPromised(qtyPromised);
		qtyReportEvent.setQtyPromised_TU(qtyPromisedTU);

		final PMMBalanceChangeEvent event = PMMQtyReportEventTrxItemProcessor_TestHelper.createPMMBalanceChangeEvent(qtyReportEvent);
		processEvent(event);
	}

	@Override
	protected void addQtyOrdered(final PMMBalanceSegment segment, final Date date, final BigDecimal qtyOrdered, final BigDecimal qtyOrderedTU)
	{
		final I_PMM_PurchaseCandidate candidate = InterfaceWrapperHelper.newInstance(I_PMM_PurchaseCandidate.class);
		candidate.setC_BPartner_ID(segment.getC_BPartner_ID());
		candidate.setM_Product_ID(segment.getM_Product_ID());
		candidate.setM_AttributeSetInstance_ID(segment.getM_AttributeSetInstance_ID());
		//candidate.setM_HU_PI_Item_Product_ID(segment.getM_HU_PI_Item_Product_ID());
		candidate.setC_Flatrate_DataEntry_ID(segment.getC_Flatrate_DataEntry_ID());
		candidate.setDatePromised(TimeUtil.asTimestamp(date));
		InterfaceWrapperHelper.save(candidate);

		final I_PMM_PurchaseCandidate_OrderLine alloc = InterfaceWrapperHelper.newInstance(I_PMM_PurchaseCandidate_OrderLine.class);
		alloc.setPMM_PurchaseCandidate(candidate);
		alloc.setQtyOrdered(qtyOrdered);
		alloc.setQtyOrdered_TU(qtyOrderedTU);
		InterfaceWrapperHelper.save(alloc);

		final boolean isReversal = false;
		final PMMBalanceChangeEvent event = PMM_PurchaseCandidate_OrderLine.createPMMBalanceChangeEvent(alloc, isReversal);
		processEvent(event);
	}
}
