package de.metas.materialtracking.qualityBasedInvoicing.impl;

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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.model.I_PP_Order_Report;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineByTypeComparator;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;

/**
 * Gets {@link IQualityInspectionLine}s and saves them as {@link I_PP_Order_Report}s.
 *
 * @author tsa
 *
 */
public class PPOrderReportWriter
{
	// Services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	// Parameters
	private final IContextAware context;
	private final I_PP_Order ppOrder;
	private QualityInspectionLineByTypeComparator reportLinesSorter;

	// State
	private int _seqNoNext = 10;
	private final List<I_PP_Order_Report> _createdReportLines = new ArrayList<I_PP_Order_Report>();

	public PPOrderReportWriter(final I_PP_Order ppOrder)
	{
		super();

		Check.assumeNotNull(ppOrder, "ppOrder not null");
		this.ppOrder = ppOrder;
		context = InterfaceWrapperHelper.getContextAware(ppOrder);
	}

	/**
	 * Sets which line types will be accepted and saved.
	 *
	 * Also, the report lines will be sorted exactly by the order of given types.
	 *
	 * @param reportLineTypes
	 */
	public void setLineTypes(final List<QualityInspectionLineType> reportLineTypes)
	{
		if (reportLineTypes == null)
		{
			reportLinesSorter = null;
		}
		else
		{
			reportLinesSorter = new QualityInspectionLineByTypeComparator(reportLineTypes);
		}
	}

	public List<I_PP_Order_Report> getCreatedReportLines()
	{
		return new ArrayList<>(_createdReportLines);
	}

	private IContextAware getContext()
	{
		return context;
	}

	private I_PP_Order getPP_Order()
	{
		// not null
		return ppOrder;
	}

	/**
	 * Delete existing {@link I_PP_Order_Report}s lines linked to current manufacturing order.
	 */
	public void deleteReportLines()
	{
		final I_PP_Order ppOrder = getPP_Order();

		queryBL.createQueryBuilder(I_PP_Order_Report.class, getContext())
		.addEqualsFilter(org.eevolution.model.I_PP_Order_Report.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
		.create() // create query
		.delete(); // delete matching records

		_createdReportLines.clear();
		_seqNoNext = 10; // reset seqNo
	}

	/**
	 * Save given {@link IQualityInspectionLine} (i.e. creates {@link I_PP_Order_Report} lines).
	 *
	 * @param qiLines
	 */
	public void save(final Collection<IQualityInspectionLine> qiLines)
	{
		if (qiLines == null || qiLines.isEmpty())
		{
			return;
		}

		final List<IQualityInspectionLine> qiLinesToSave = new ArrayList<>(qiLines);

		//
		// Discard not accepted lines and then sort them
		if (reportLinesSorter != null)
		{
			reportLinesSorter.filterAndSort(qiLinesToSave);
		}

		//
		// Iterate lines and save one by one
		for (final IQualityInspectionLine qiLine : qiLinesToSave)
		{
			save(qiLine);
		}
	}

	/**
	 * Save given {@link IQualityInspectionLine} (i.e. creates {@link I_PP_Order_Report} line).
	 *
	 * @param qiLine
	 */
	private void save(final IQualityInspectionLine qiLine)
	{
		Check.assumeNotNull(qiLine, "qiLine not null");

		final I_PP_Order ppOrder = getPP_Order();
		final int seqNo = _seqNoNext;

		BigDecimal qty = qiLine.getQty();
		if (qty != null && qiLine.isNegateQtyInReport())
		{
			qty = qty.negate();
		}

		//
		// Create report line
		final I_PP_Order_Report reportLine = InterfaceWrapperHelper.newInstance(I_PP_Order_Report.class, getContext());
		reportLine.setPP_Order(ppOrder);
		reportLine.setAD_Org_ID(ppOrder.getAD_Org_ID());
		reportLine.setSeqNo(seqNo);
		reportLine.setIsActive(true);
		// reportLine.setQualityInspectionLineType(qiLine.getQualityInspectionLineType());
		reportLine.setProductionMaterialType(qiLine.getProductionMaterialType());
		reportLine.setM_Product(qiLine.getM_Product());
		reportLine.setName(qiLine.getName());
		reportLine.setQty(qty);
		reportLine.setC_UOM(qiLine.getC_UOM());
		reportLine.setPercentage(qiLine.getPercentage());
		reportLine.setQtyProjected(qiLine.getQtyProjected());
		reportLine.setComponentType(qiLine.getComponentType());
		reportLine.setVariantGroup(qiLine.getVariantGroup());

		//
		// Save report line
		InterfaceWrapperHelper.save(reportLine);
		_createdReportLines.add(reportLine);
		_seqNoNext += 10;
	}

}
