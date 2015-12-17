package de.metas.materialtracking.impl;

/*
 * #%L
 * de.metas.materialtracking
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

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.util.Check;
import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;

public class MaterialTrackingPPOrderBL implements IMaterialTrackingPPOrderBL
{
	// public only for testing
	public static final String C_DocType_DOCSUBTYPE_QualityInspection = IMaterialTrackingBL.C_DocType_PPORDER_DOCSUBTYPE_QualityInspection;

	/**
	 * Quality Inspection Filter
	 *
	 * NOTE: keep in sync with {@link #isQualityInspection(I_PP_Order)}
	 */
	private final IQueryFilter<I_PP_Order> qualityInspectionFilter = new EqualsQueryFilter<I_PP_Order>(I_PP_Order.COLUMN_OrderType, C_DocType_DOCSUBTYPE_QualityInspection);

	@Override
	public boolean isQualityInspection(final I_PP_Order ppOrder)
	{
		// NOTE: keep in sync with #qualityInspectionFilter

		Check.assumeNotNull(ppOrder, "ppOrder not null");
		final String orderType = ppOrder.getOrderType();
		return C_DocType_DOCSUBTYPE_QualityInspection.equals(orderType);
	}

	@Override
	public void assertQualityInspectionOrder(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");
		Check.assume(isQualityInspection(ppOrder), "Order shall be Quality Inspection Order: {0}", ppOrder);
	}

	@Override
	public IQueryFilter<I_PP_Order> getQualityInspectionFilter()
	{
		return qualityInspectionFilter;
	}
	
	@Override
	public Timestamp getDateOfProduction(final I_PP_Order ppOrder)
	{
	
		final Timestamp dateOfProduction;
		if (ppOrder.getDateDelivered() != null)
		{
			dateOfProduction = ppOrder.getDateDelivered();
		}
		else
		{
			dateOfProduction = ppOrder.getDateFinishSchedule();
		}
		Check.assumeNotNull(dateOfProduction, "dateOfProduction not null for PP_Order {0}", ppOrder);
		return dateOfProduction;
	}

}
