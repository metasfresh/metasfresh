package de.metas.materialtracking.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import org.eevolution.api.PPOrderId;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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
	public boolean isQualityInspection(final int ppOrderId)
	{
		final I_PP_Order ppOrderRecord = loadOutOfTrx(ppOrderId, I_PP_Order.class);
		return isQualityInspection(ppOrderRecord);
	}

	@Override
	public boolean isQualityInspection(@NonNull final I_PP_Order ppOrder)
	{
		// NOTE: keep in sync with #qualityInspectionFilter
		final String orderType = ppOrder.getOrderType();
		return C_DocType_DOCSUBTYPE_QualityInspection.equals(orderType);
	}

	@Override
	public void assertQualityInspectionOrder(@NonNull final I_PP_Order ppOrder)
	{
		Check.assume(isQualityInspection(ppOrder), "Order shall be Quality Inspection Order: {}", ppOrder);
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
		Check.assumeNotNull(dateOfProduction, "dateOfProduction not null for PP_Order {}", ppOrder);
		return dateOfProduction;
	}

	@Override
	public List<I_M_InOutLine> retrieveIssuedInOutLines(final I_PP_Order ppOrder)
	{
		// services
		final IPPOrderMInOutLineRetrievalService ppOrderMInOutLineRetrievalService = Services.get(IPPOrderMInOutLineRetrievalService.class);
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);

		final List<I_M_InOutLine> allIssuedInOutLines = new ArrayList<>();

		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		for (final I_PP_Cost_Collector cc : ppCostCollectorDAO.getByOrderId(ppOrderId))
		{
			if (!ppCostCollectorBL.isAnyComponentIssueOrCoProduct(cc))
			{
				continue;
			}

			final List<I_M_InOutLine> issuedInOutLinesForCC = ppOrderMInOutLineRetrievalService.provideIssuedInOutLines(cc);
			allIssuedInOutLines.addAll(issuedInOutLinesForCC);
		}
		return allIssuedInOutLines;
	}
}
