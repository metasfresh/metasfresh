package de.metas.procurement.base.order.impl;

import java.util.Date;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.dao.impl.NullQueryFilterModifier;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate_OrderLine;
import de.metas.procurement.base.order.IPMMPurchaseCandidateDAO;
import de.metas.procurement.base.order.PMMPurchaseCandidateSegment;

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

public class PMMPurchaseCandidateDAO implements IPMMPurchaseCandidateDAO
{
	@Override
	public void deletePurchaseCandidateOrderLines(final I_C_Order order)
	{
		retrivePurchaseCandidateOrderLines(order)
				.create()
				.delete();
	}

	@Override
	public IQueryBuilder<I_PMM_PurchaseCandidate_OrderLine> retrivePurchaseCandidateOrderLines(final I_C_Order order)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OrderLine.class, order)
				.addEqualsFilter(I_C_OrderLine.COLUMN_C_Order_ID, order.getC_Order_ID())
				.andCollectChildren(I_PMM_PurchaseCandidate_OrderLine.COLUMN_C_OrderLine_ID, I_PMM_PurchaseCandidate_OrderLine.class);
	}

	@Override
	public I_PMM_PurchaseCandidate retrieveFor(final PMMPurchaseCandidateSegment pmmSegment, final Date day)
	{
		return queryFor(pmmSegment, day, NullQueryFilterModifier.instance)
				.create()
				.firstOnly(I_PMM_PurchaseCandidate.class);
	}

	@Override
	public boolean hasRecordsForWeek(final PMMPurchaseCandidateSegment pmmSegment, final Date weekDate)
	{
		return queryFor(pmmSegment, weekDate, DateTruncQueryFilterModifier.WEEK)
				.create()
				.match();
	}
	
	private final IQueryBuilder<I_PMM_PurchaseCandidate> queryFor(final PMMPurchaseCandidateSegment pmmSegment, final Date day, final IQueryFilterModifier dayModifier)
	{
		Check.assumeNotNull(pmmSegment, "pmmSegment not null");
		Check.assumeNotNull(day, "day not null");

		final PlainContextAware context = PlainContextAware.newWithThreadInheritedTrx();
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PMM_PurchaseCandidate.class, context)
				.addOnlyActiveRecordsFilter()
				//
				// Segment
				.addEqualsFilter(I_PMM_PurchaseCandidate.COLUMN_C_BPartner_ID, pmmSegment.getC_BPartner_ID() > 0 ? pmmSegment.getC_BPartner_ID() : null)
				.addEqualsFilter(I_PMM_PurchaseCandidate.COLUMN_M_Product_ID, pmmSegment.getM_Product_ID() > 0 ? pmmSegment.getM_Product_ID() : null)
				.addEqualsFilter(I_PMM_PurchaseCandidate.COLUMN_M_AttributeSetInstance_ID, pmmSegment.getM_AttributeSetInstance_ID() > 0 ? pmmSegment.getM_AttributeSetInstance_ID() : null)
				.addEqualsFilter(I_PMM_PurchaseCandidate.COLUMN_M_HU_PI_Item_Product_ID, pmmSegment.getM_HU_PI_Item_Product_ID() > 0 ? pmmSegment.getM_HU_PI_Item_Product_ID() : null)
				.addEqualsFilter(I_PMM_PurchaseCandidate.COLUMN_C_Flatrate_DataEntry_ID, pmmSegment.getC_Flatrate_DataEntry_ID() > 0 ? pmmSegment.getC_Flatrate_DataEntry_ID() : null)
				//
				// Date
				.addEqualsFilter(I_PMM_PurchaseCandidate.COLUMN_DatePromised, day, dayModifier);
	}
}
