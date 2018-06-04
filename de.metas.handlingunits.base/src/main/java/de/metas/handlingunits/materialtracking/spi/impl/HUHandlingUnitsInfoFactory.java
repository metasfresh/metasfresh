package de.metas.handlingunits.materialtracking.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_C_Invoice_Detail;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.IHandlingUnitsInfoWritableQty;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;

public class HUHandlingUnitsInfoFactory implements IHandlingUnitsInfoFactory
{
	@Override
	public IHandlingUnitsInfo createFromModel(final Object model)
	{
		Check.assumeNotNull(model, "model not null"); // shall not happen

		if (InterfaceWrapperHelper.isInstanceOf(model, I_M_InOutLine.class))
		{
			final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
			return createFromInOutLine(inoutLine);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_PP_Order.class))
		{
			final I_PP_Order ppOrder = InterfaceWrapperHelper.create(model, I_PP_Order.class);
			return createFromPPOrder(ppOrder);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_PP_Order_BOMLine.class))
		{
			final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(model, I_PP_Order_BOMLine.class);
			return createFromPPOrderBOMLine(ppOrderBOMLine);
		}
		else
		{
			// Case not supported/not handled
			return null;
		}
	}

	private IHandlingUnitsInfo createFromInOutLine(final I_M_InOutLine inoutLine)
	{
		final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);

		final I_M_HU_PI tuPI = huInOutBL.getTU_HU_PI(inoutLine);
		if (tuPI == null)
		{
			// TODO: shall we just return null or throw exception?
		}

		final int qtyTU = inoutLine.getQtyEnteredTU().intValueExact();

		return new HUHandlingUnitsInfo(tuPI, qtyTU);
	}

	private IHandlingUnitsInfo createFromPPOrder(final I_PP_Order ppOrder)
	{
		return createFromOrderOrBomLine(ppOrder, null);
	}

	/**
	 *
	 * @param ppOrderBOMLine
	 * @return the {@link UnavailableHandlingUnitsInfo}
	 */
	private IHandlingUnitsInfo createFromPPOrderBOMLine(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		return createFromOrderOrBomLine(null, ppOrderBOMLine);
	}

	private IHandlingUnitsInfo createFromOrderOrBomLine(final I_PP_Order ppOrder,
			final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final IQueryBuilder<I_PP_Cost_Collector> ccFilter = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Cost_Collector.class, ppOrderBOMLine)
				.addOnlyActiveRecordsFilter();

		if (ppOrder != null)
		{
			ccFilter.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID());
		}
		if (ppOrderBOMLine != null)
		{
			ccFilter.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_BOMLine_ID, ppOrderBOMLine.getPP_Order_BOMLine_ID());
		}

		final List<I_PP_Cost_Collector> costCollectors = ccFilter
				.create()
				.list(I_PP_Cost_Collector.class);

		final Map<Integer, I_M_HU_PI> topLevelHUIds = new HashMap<>();
		for (final I_PP_Cost_Collector costCollector : costCollectors)
		{
			final List<I_M_HU_Assignment> huAssignments = Services.get(IHUAssignmentDAO.class).retrieveHUAssignmentsForModel(costCollector);
			for (final I_M_HU_Assignment huAssignment : huAssignments)
			{
				final I_M_HU hu = huAssignment.getM_HU();
				final I_M_HU_PI huPI = Services.get(IHandlingUnitsBL.class).getPI(hu);
				topLevelHUIds.put(hu.getM_HU_ID(), huPI);
			}
		}

		IHandlingUnitsInfo result = null;
		for (final int huId : topLevelHUIds.keySet())
		{
			final HUHandlingUnitsInfo current = new HUHandlingUnitsInfo(topLevelHUIds.get(huId), 1);

			if (result == null)
			{
				result = current;
			}
			else
			{
				result = result.add(current);
			}
		}
		return result;
	}

	/**
	 * NOTE: we assume handlingUnitsInfo was created by this factory
	 */
	@Override
	public void updateInvoiceDetail(final org.adempiere.model.I_C_Invoice_Detail invoiceDetail, final IHandlingUnitsInfo handlingUnitsInfo)
	{
		// nothing to update
		if (handlingUnitsInfo == null)
		{
			return;
		}

		final I_C_Invoice_Detail huInvoiceDetail = InterfaceWrapperHelper.create(invoiceDetail, I_C_Invoice_Detail.class);
		// NOTE: we assume handlingUnitsInfo were created by this factory
		final HUHandlingUnitsInfo huInfo = HUHandlingUnitsInfo.cast(handlingUnitsInfo);

		huInvoiceDetail.setM_TU_HU_PI(huInfo.getTU_HU_PI());

		final BigDecimal qtyTU = BigDecimal.valueOf(huInfo.getQtyTU());
		huInvoiceDetail.setQtyTU(qtyTU);
	}

	/**
	 * NOTE: we assume handlingUnitsInfo was created by this factory
	 */
	@Override
	public IHandlingUnitsInfoWritableQty createHUInfoWritableQty(IHandlingUnitsInfo template)
	{
		final HUHandlingUnitsInfo cast = HUHandlingUnitsInfo.cast(template);
		return new HUHandlingUnitsInfoRW(cast.getTU_HU_PI(), template.getQtyTU());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
