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
import de.metas.inout.IInOutBL;
import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.IHandlingUnitsInfoWritableQty;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class HUHandlingUnitsInfoFactory implements IHandlingUnitsInfoFactory
{
	private static final Logger logger = LogManager.getLogger(HUHandlingUnitsInfoFactory.class);

	@Override
	public IHandlingUnitsInfo createFromModel(@NonNull final Object model)
	{
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
		final I_M_HU_PI tuPI = resolveTU_HU_PI(inoutLine);

		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		final int qtyTU = inOutBL.negateIfReturnMovmenType(inoutLine, inoutLine.getQtyEnteredTU()).intValueExact();

		return new HUHandlingUnitsInfo(tuPI, qtyTU);
	}

	/**
	 * Resolve the TU Packing Instructions for an InOutLine.
	 * <p>
	 * Primary: use M_HU_PI_Item_Product from the InOutLine or its linked OrderLine
	 * (the originally-planned TU PI — preserves prior behavior for all non-rogue lines).
	 * Fallback: look at the actual HU assignments (M_TU_HU_ID / top-level M_HU),
	 * used only when the planned TU PI is missing ("rogue" lines that would previously crash).
	 *
	 * @throws AdempiereException if no TU PI could be determined from any source
	 */
	@NonNull
	private I_M_HU_PI resolveTU_HU_PI(final I_M_InOutLine inoutLine)
	{
		//
		// Primary: resolve from M_HU_PI_Item_Product on the InOutLine or its linked OrderLine.
		// This preserves the prior behavior for all lines that used to work.
		final I_M_HU_PI plannedTuPI = Services.get(IHUInOutBL.class).getTU_HU_PI(inoutLine);
		if (plannedTuPI != null)
		{
			return plannedTuPI;
		}

		//
		// Fallback: resolve from actual HU assignments. Only reached when the planned TU PI
		// is missing (previously caused "Assumption failure: tuPI not null").
		final I_M_HU_PI actualTuPI = resolveTU_HU_PI_FromHUAssignments(inoutLine);
		if (actualTuPI != null)
		{
			return actualTuPI;
		}

		//
		// Neither approach found a TU PI
		final org.compiere.model.I_M_InOut inout = inoutLine.getM_InOut();
		throw new AdempiereException("Receipt " + inout.getDocumentNo() + ", Line " + inoutLine.getLine()
				+ ": TU Packing Instructions could not be determined."
				+ " Neither the receipt line nor its linked order line has a TU Packing Instruction (Packvorschrift-TU),"
				+ " and no usable HU assignment was found either.")
				.setParameter("M_InOut_ID", inout.getM_InOut_ID())
				.setParameter("DocumentNo", inout.getDocumentNo())
				.setParameter("M_InOutLine_ID", inoutLine.getM_InOutLine_ID())
				.setParameter("Line", inoutLine.getLine())
				.setParameter("M_Product_ID", inoutLine.getM_Product_ID())
				.setParameter("M_HU_PI_Item_Product_ID", inoutLine.getM_HU_PI_Item_Product_ID())
				.setParameter("C_OrderLine_ID", inoutLine.getC_OrderLine_ID());
	}

	/**
	 * Two-pass resolution from actual HU assignments:
	 * <ul>
	 *   <li>Pass 1: derived assignments with M_TU_HU_ID explicitly set (most reliable).</li>
	 *   <li>Pass 2: top-level M_HU, used when the top-level HU itself is a TU (no LU involved).</li>
	 * </ul>
	 * Returns {@code null} if no non-virtual HU could be found.
	 */
	@Nullable
	private I_M_HU_PI resolveTU_HU_PI_FromHUAssignments(final I_M_InOutLine inoutLine)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		I_M_HU_PI firstTuPI = null;
		boolean hasMultipleDistinctPIs = false;

		// Pass 1: derived assignments with M_TU_HU_ID set.
		final List<I_M_HU_Assignment> tuAssignments = huAssignmentDAO.retrieveTUHUAssignmentsForModelQuery(inoutLine)
				.create()
				.list();
		for (final I_M_HU_Assignment huAssignment : tuAssignments)
		{
			final I_M_HU tuHU = huAssignment.getM_TU_HU();
			if (tuHU == null || handlingUnitsBL.isVirtual(tuHU))
			{
				continue;
			}

			final I_M_HU_PI huPI = handlingUnitsBL.getPI(tuHU);
			if (firstTuPI == null)
			{
				firstTuPI = huPI;
			}
			else if (firstTuPI.getM_HU_PI_ID() != huPI.getM_HU_PI_ID())
			{
				hasMultipleDistinctPIs = true;
			}
		}

		// Pass 2: fall back to top-level M_HU when no derived assignment had a usable TU.
		if (firstTuPI == null)
		{
			final List<I_M_HU_Assignment> topLevelAssignments = huAssignmentDAO.retrieveTopLevelHUAssignmentsForModel(inoutLine);
			for (final I_M_HU_Assignment huAssignment : topLevelAssignments)
			{
				final I_M_HU hu = huAssignment.getM_HU();
				if (hu == null || handlingUnitsBL.isVirtual(hu))
				{
					continue;
				}

				final I_M_HU_PI huPI = handlingUnitsBL.getPI(hu);
				if (firstTuPI == null)
				{
					firstTuPI = huPI;
				}
				else if (firstTuPI.getM_HU_PI_ID() != huPI.getM_HU_PI_ID())
				{
					hasMultipleDistinctPIs = true;
				}
			}
		}

		if (firstTuPI != null && hasMultipleDistinctPIs)
		{
			logger.warn("InOutLine {} (M_InOutLine_ID={}) has HU assignments with different TU Packing Instructions. Using the first one: {}",
					inoutLine.getLine(), inoutLine.getM_InOutLine_ID(), firstTuPI.getName());
		}

		return firstTuPI;
	}

	private IHandlingUnitsInfo createFromPPOrder(final I_PP_Order ppOrder)
	{
		return createFromOrderOrBomLine(ppOrder, null);
	}

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
			final List<I_M_HU_Assignment> huAssignments = Services.get(IHUAssignmentDAO.class).retrieveTopLevelHUAssignmentsForModel(costCollector);
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
	public void updateInvoiceDetail(final org.compiere.model.I_C_Invoice_Detail invoiceDetail, final IHandlingUnitsInfo handlingUnitsInfo)
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
