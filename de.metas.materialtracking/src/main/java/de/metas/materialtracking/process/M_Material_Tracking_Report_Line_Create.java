package de.metas.materialtracking.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_InOut;

import de.metas.document.engine.IDocActionBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.IMaterialTrackingReportDAO;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.model.IMaterialTrackingAware;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.JavaProcess;

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

public class M_Material_Tracking_Report_Line_Create
		extends JavaProcess
		implements IProcessPrecondition
{
	@Override
	protected String doIt() throws Exception
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final IMaterialTrackingReportDAO materialTrackingReportDAO = Services.get(IMaterialTrackingReportDAO.class);
		final IPPOrderMInOutLineRetrievalService ppOrderMInOutLineRetrievalService = Services.get(IPPOrderMInOutLineRetrievalService.class);

		final I_M_Material_Tracking_Report report = getRecord(I_M_Material_Tracking_Report.class);
		final I_C_Period period = report.getC_Period();

		final CreateMaterialTrackingReportLineFromMaterialTrackingRefAggregator workpackageAggregator = new CreateMaterialTrackingReportLineFromMaterialTrackingRefAggregator(report);

		workpackageAggregator.setItemAggregationKeyBuilder(new CreateMaterialTrackingReportLineFromMaterialTrackingRefKeyBuilder());

		// get all the material tracking entries that fit the given period
		final List<I_M_Material_Tracking> materialTrackings = materialTrackingDAO.retrieveMaterialTrackingsForPeriodAndOrg(period, report.getAD_Org());

		for (final I_M_Material_Tracking materialTracking : materialTrackings)
		{
			final int productID = materialTracking.getM_Product_ID();

			final Iterator<I_M_Material_Tracking_Ref> it = materialTrackingReportDAO.retrieveMaterialTrackingRefsForMaterialTracking(materialTracking);

			for (final I_M_Material_Tracking_Ref ref : IteratorUtils.asIterable(it))
			{
				if (!isValid(ref, report))
				{
					continue;
				}

				if (InterfaceWrapperHelper.getTableId(I_M_InOutLine.class) == ref.getAD_Table_ID())
				{
					// add the material receipt lines with their *MovementQtys*
					final I_M_InOutLine iol = InterfaceWrapperHelper.create(getCtx(), ref.getRecord_ID(), I_M_InOutLine.class, getTrxName());

					final MaterialTrackingReportAgregationItem item = new MaterialTrackingReportAgregationItem(
							null, // PP_Order
							iol,
							materialTracking,
							iol.getMovementQty());
					workpackageAggregator.add(item);
				}
				else if (InterfaceWrapperHelper.getTableId(I_PP_Order.class) == ref.getAD_Table_ID())
				{
					// add the PP_Orders with their *issued* qties
					final I_PP_Order ppOrder = InterfaceWrapperHelper.create(getCtx(), ref.getRecord_ID(), I_PP_Order.class, getTrxName());

					final Map<Integer, BigDecimal> iolAndQty = ppOrderMInOutLineRetrievalService.retrieveIolAndQty(ppOrder);

					for (final Entry<Integer, BigDecimal> entry : iolAndQty.entrySet())
					{
						final I_M_InOutLine iol = InterfaceWrapperHelper.create(getCtx(), entry.getKey(), I_M_InOutLine.class, getTrxName());

						if (productID != iol.getM_Product_ID())
						{
							continue;
						}

						final MaterialTrackingReportAgregationItem item = new MaterialTrackingReportAgregationItem(
								ppOrder,
								iol,
								materialTracking,
								entry.getValue());
						workpackageAggregator.add(item);
					}
				}
				else
				{
					throw new AdempiereException("Not suppoerted for table ID " + ref.getAD_Table_ID());
				}
			}
		}

		workpackageAggregator.closeAllGroups();

		report.setProcessed(true);
		InterfaceWrapperHelper.save(report);

		return "Success";
	}

	private boolean isValid(
			final I_M_Material_Tracking_Ref ref,
			final I_M_Material_Tracking_Report report)
	{
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);

		final I_M_Material_Tracking materialTracking = ref.getM_Material_Tracking();
		final int productID = materialTracking.getM_Product_ID();

		final I_C_Period period = report.getC_Period();
		final Timestamp periodEndDate = period.getEndDate();
		final IContextAware reportCtx = InterfaceWrapperHelper.getContextAware(report);

		final ITableRecordReference tableRecordReference = new TableRecordReference(ref.getAD_Table_ID(), ref.getRecord_ID());

		// skip references records with other material trackings
		final IMaterialTrackingAware materialTrackingAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(tableRecordReference.getModel(reportCtx), IMaterialTrackingAware.class);
		if(materialTrackingAware == null
				|| materialTracking.getM_Material_Tracking_ID() != materialTrackingAware.getM_Material_Tracking_ID())
		{
			// should not happen because that would mean an inconsistent M_Material_Tracking_Ref
			Loggables.get().addLog(
					"Skipping {} because it is referenced via M_Material_Tracking_Ref, but itself does not reference {}",
					materialTrackingAware, materialTracking);
			return false;
		}

		if (I_PP_Order.Table_Name.equals(tableRecordReference.getTableName()))
		{
			final I_PP_Order ppOrder = tableRecordReference.getModel(reportCtx, I_PP_Order.class);

			// shall never happen
			Check.assumeNotNull(ppOrder, "PP_Order not null in M_Material_Tracking_Ref: {}", ref);

			// only closed pp_Orders are eligible
			if (!docActionBL.isStatusClosed(ppOrder))
			{
				return false; // this is a common and frequent case; don't pollute the loggable with it.
			}

			final Timestamp productionDate = materialTrackingPPOrderBL.getDateOfProduction(ppOrder);

			// Production date must be <= endDate
			if (productionDate.after(periodEndDate))
			{
				return false; // this is a common and frequent case; don't pollute the loggable with it.
			}
		}

		else if (I_M_InOutLine.Table_Name.equals(tableRecordReference.getTableName()))
		{
			final I_M_InOutLine iol = tableRecordReference.getModel(reportCtx, I_M_InOutLine.class);

			// shall never happen
			Check.assumeNotNull(iol, "M_InOutLine not null in M_Material_Tracking_Ref: {}", ref);

			final I_M_InOut io = iol.getM_InOut();

			// only completed and closed inouts are eligible
			if (!docActionBL.isStatusCompletedOrClosed(io))
			{
				return false;
			}
			final Timestamp movementDate = io.getMovementDate();

			// Movement date must be <= endDate
			if (movementDate.after(periodEndDate))
			{
				return false;
			}

			// skip inoutlines with other products
			if (productID != iol.getM_Product_ID())
			{
				return false;
			}
		}

		else
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		// This process is just for unprocessed reports
		final I_M_Material_Tracking_Report report = context.getModel(I_M_Material_Tracking_Report.class);

		return !report.isProcessed();
	}

}
