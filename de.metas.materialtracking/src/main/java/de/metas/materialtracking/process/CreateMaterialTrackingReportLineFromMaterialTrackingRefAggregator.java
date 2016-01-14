package de.metas.materialtracking.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.collections.MapReduceAggregator;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.materialtracking.IMaterialTrackingReportBL;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.model.I_PP_Order;

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

public class CreateMaterialTrackingReportLineFromMaterialTrackingRefAggregator extends MapReduceAggregator<I_M_Material_Tracking_Report_Line, I_M_Material_Tracking_Ref>
{

	final IMaterialTrackingReportBL materialTrackingReportBL = Services.get(IMaterialTrackingReportBL.class);

	/**
	 * Report header containing the report line
	 */
	private I_M_Material_Tracking_Report report;

	private final Map<I_M_Material_Tracking_Report_Line, List<I_M_Material_Tracking_Ref>> reportLineToRefs = new IdentityHashMap<>();

	public CreateMaterialTrackingReportLineFromMaterialTrackingRefAggregator(final I_M_Material_Tracking_Report report)
	{
		super();
		this.report = report;
	}

	@Override
	protected I_M_Material_Tracking_Report_Line createGroup(final Object itemHashKey, final I_M_Material_Tracking_Ref ref)
	{
		// Create a report line based on a ref
		final I_M_Material_Tracking_Report_Line newLine;
		try
		{
			newLine = materialTrackingReportBL.createMaterialTrackingReportLine(report, ref);
		}
		catch (final Throwable t)
		{
			ILoggable.THREADLOCAL.getLoggable().addLog("@Error@: " + t);
			throw AdempiereException.wrapIfNeeded(t);
		}

		reportLineToRefs.put(newLine, new ArrayList<I_M_Material_Tracking_Ref>());

		return newLine;
	}

	@Override
	protected void closeGroup(final I_M_Material_Tracking_Report_Line reportLine)
	{
		// save the line
		InterfaceWrapperHelper.save(reportLine);

		final LineToQtys lineToQtys = new LineToQtys(reportLine, reportLine.getQtyReceived(), reportLine.getQtyIssued());

		for (final I_M_Material_Tracking_Ref ref : reportLineToRefs.get(reportLine))
		{
			// create allocation
			materialTrackingReportBL.createMaterialTrackingReportLineAllocation(reportLine, ref);

			// update qtys
			updateQtys(lineToQtys, ref);

		}

		final BigDecimal qtyReceived = lineToQtys.getQtyReceived().setScale(1, RoundingMode.HALF_UP);
		final BigDecimal qtyIssued = lineToQtys.getQtyIssued().setScale(1, RoundingMode.HALF_UP);
		final BigDecimal qtyDifference = qtyReceived.subtract(qtyIssued).setScale(1, RoundingMode.HALF_UP);

		// set qtys
		reportLine.setQtyReceived(qtyReceived);
		reportLine.setQtyIssued(qtyIssued);
		reportLine.setDifferenceQty(qtyDifference);

		InterfaceWrapperHelper.save(reportLine);

	}

	private void updateQtys(final LineToQtys lineTOQtys, final I_M_Material_Tracking_Ref ref)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ref);
		final String trxName = InterfaceWrapperHelper.getTrxName(ref);

		int table_ID = ref.getAD_Table_ID();

		if (InterfaceWrapperHelper.getTableId(I_PP_Order.class) == table_ID)
		{
			lineTOQtys.setQtyIssued(lineTOQtys.getQtyIssued().add(ref.getQtyIssued()));
		}

		else if (InterfaceWrapperHelper.getTableId(I_M_InOutLine.class) == table_ID)
		{
			final I_M_InOutLine iol = InterfaceWrapperHelper.create(ctx, ref.getRecord_ID(), I_M_InOutLine.class, trxName);

			lineTOQtys.setQtyReceived(lineTOQtys.getQtyReceived().add(iol.getMovementQty()));
		}
		else
		{
			throw new AdempiereException("Not suppoerted for table ID" + table_ID);
		}

	}

	@Override
	protected void addItemToGroup(final I_M_Material_Tracking_Report_Line reportLine, final I_M_Material_Tracking_Ref ref)
	{
		reportLineToRefs.get(reportLine).add(ref); // no NPE, because the list for this key was added in createGroup()
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * @author metas-dev <dev@metas-fresh.com>
	 *
	 * Class that helps keeping the qtys collected from the references up to date
	 *
	 */
	class LineToQtys
	{
		private I_M_Material_Tracking_Report_Line line;
		private BigDecimal qtyReceived;
		private BigDecimal qtyIssued;

		public LineToQtys(final I_M_Material_Tracking_Report_Line line, final BigDecimal qtyReceived, final BigDecimal qtyIssued)
		{
			this.line = line;
			this.qtyReceived = qtyReceived;
			this.qtyIssued = qtyIssued;
		}

		public I_M_Material_Tracking_Report_Line getLine()
		{
			return line;
		}

		public void setLine(final I_M_Material_Tracking_Report_Line line)
		{
			this.line = line;
		}

		public BigDecimal getQtyReceived()
		{
			return qtyReceived;
		}

		public void setQtyReceived(final BigDecimal qtyReceived)
		{
			this.qtyReceived = qtyReceived;
		}

		public BigDecimal getQtyIssued()
		{
			return qtyIssued;
		}

		public void setQtyIssued(final BigDecimal qtyIssued)
		{
			this.qtyIssued = qtyIssued;
		}
	}

}
