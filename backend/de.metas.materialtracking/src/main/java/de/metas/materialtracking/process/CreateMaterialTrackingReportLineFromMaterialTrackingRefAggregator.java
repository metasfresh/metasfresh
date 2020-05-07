package de.metas.materialtracking.process;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.collections.MapReduceAggregator;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.materialtracking.IMaterialTrackingReportBL;
import de.metas.materialtracking.IMaterialTrackingReportDAO;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line;

/*
 * #%L
 * de.metas.materialtracking
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

public class CreateMaterialTrackingReportLineFromMaterialTrackingRefAggregator extends MapReduceAggregator<I_M_Material_Tracking_Report_Line, MaterialTrackingReportAgregationItem>
{

	final IMaterialTrackingReportBL materialTrackingReportBL = Services.get(IMaterialTrackingReportBL.class);

	/**
	 * Report header containing the report line
	 */
	private I_M_Material_Tracking_Report report;

	public CreateMaterialTrackingReportLineFromMaterialTrackingRefAggregator(final I_M_Material_Tracking_Report report)
	{
		super();
		this.report = report;
	}

	@Override
	protected I_M_Material_Tracking_Report_Line createGroup(
			final Object itemHashKey,
			final MaterialTrackingReportAgregationItem items)
	{
		final IMaterialTrackingReportDAO materialTrackingReportDAO = Services.get(IMaterialTrackingReportDAO.class);

		final I_M_Material_Tracking_Report_Line existingLine = materialTrackingReportDAO.retrieveMaterialTrackingReportLineOrNull(report, itemHashKey.toString());
		// Create a report line based on a ref

		if (existingLine != null)
		{
			return existingLine;
		}

		final I_M_Material_Tracking_Report_Line newLine;
		try
		{
			newLine = materialTrackingReportBL.createMaterialTrackingReportLine(report,
					items.getInOutLine(),
					itemHashKey.toString());

			InterfaceWrapperHelper.save(newLine);
		}
		catch (final Throwable t)
		{
			Loggables.get().addLog("@Error@: " + t);
			throw AdempiereException.wrapIfNeeded(t);
		}

		return newLine;
	}

	@Override
	protected void closeGroup(final I_M_Material_Tracking_Report_Line reportLine)
	{
		// save the line

		final BigDecimal qtyReceived = reportLine.getQtyReceived().setScale(1, RoundingMode.HALF_UP);
		final BigDecimal qtyIssued = reportLine.getQtyIssued().setScale(1, RoundingMode.HALF_UP);
		final BigDecimal qtyDifference = qtyReceived.subtract(qtyIssued).setScale(1, RoundingMode.HALF_UP);

		reportLine.setDifferenceQty(qtyDifference);
		InterfaceWrapperHelper.save(reportLine);

	}

	@Override
	protected void addItemToGroup(final I_M_Material_Tracking_Report_Line reportLine,
			final MaterialTrackingReportAgregationItem items)
	{
		materialTrackingReportBL.createMaterialTrackingReportLineAllocation(reportLine, items);

		if (items.getPPOrder() != null)
		{
			// issue Side
			reportLine.setQtyIssued(reportLine.getQtyIssued().add(items.getQty()));
		}
		else
		{
			// receipt side
			reportLine.setQtyReceived(reportLine.getQtyReceived().add(items.getQty()));
		}

		InterfaceWrapperHelper.save(reportLine);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

}
