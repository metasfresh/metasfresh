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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.document.service.IDocActionBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_InOut;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingListener;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.spi.impl.listeners.CompositeMaterialTrackingListener;

public class MaterialTrackingBL implements IMaterialTrackingBL
{

	private final CompositeMaterialTrackingListener listeners = new CompositeMaterialTrackingListener();

	/**
	 * Is material tracking module enabled ?
	 *
	 * By default this is disabled and it will be enabled programmatically by material tracking module activator.
	 */
	private boolean _enabled = false;

	private static final transient CLogger logger = CLogger.getCLogger(MaterialTrackingBL.class);

	@Override
	public boolean isEnabled()
	{
		return _enabled;
	}

	@Override
	public void setEnabled(final boolean enabled)
	{
		_enabled = enabled;
	}

	@Override
	public void addModelTrackingListener(final String tableName, final IMaterialTrackingListener listener)
	{
		listeners.addMaterialTrackingListener(tableName, listener);
	}

	@Override
	public void linkModelToMaterialTracking(final MTLinkRequest request)
	{
		final I_M_Material_Tracking materialTracking = request.getMaterialTracking();
		final Object model = request.getModel();

		Check.assumeNotNull(model, "model not null in MTLinkRequest {0}", request);
		Check.assumeNotNull(materialTracking, "materialTracking not null in MTLinkRequest {0}", request);

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		//
		// Retrieve existing reference
		// and if exists and it's not linked to our material tracking, delete it
		final I_M_Material_Tracking_Ref refExisting = materialTrackingDAO.retrieveMaterialTrackingRefForModel(model);
		if (refExisting != null)
		{
			if (materialTracking.getM_Material_Tracking_ID() == refExisting.getM_Material_Tracking_ID())
			{
				// Case: material tracking was not changed => do nothing
				final String msg = ": M_Material_Tracking_ID=" + materialTracking.getM_Material_Tracking_ID() + " of existing M_Material_Tracking_Ref is still valid; nothing to do";
				logRequest(request, msg);
				return;
			}

			//
			// If material tracking don't match and we're going under the assumption that they're already assigned, do NOT drop the assignment to create a new one.
			// Instead, notify the user that he misconfigured something
			if (request.isAssumeNotAlreadyAssigned())
			{
				throw new AdempiereException("Cannot assign model to a different material tracking"
						+ "\n Model: " + request.getModel()
						+ "\n Material tracking (current): " + refExisting.getM_Material_Tracking()
						+ "\n Material tracking (new): " + materialTracking);
			}

			// Case: material tracking changed => delete old link
			unlinkModelFromMaterialTracking(request.getModel(), refExisting);
		}

		//
		// Create the new link
		createMaterialTrackingRef(request);
	}

	private final I_M_Material_Tracking_Ref createMaterialTrackingRef(final MTLinkRequest request)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking_Ref refNew = materialTrackingDAO.createMaterialTrackingRefNoSave(request.getMaterialTracking(), request.getModel());

		final String msg = ": Linking model with M_Material_Tracking_ID=" + refNew.getM_Material_Tracking_ID();
		logRequest(request, msg);

		listeners.beforeModelLinked(request, refNew);

		InterfaceWrapperHelper.save(refNew);

		listeners.afterModelLinked(request);

		return refNew;
	}

	private void logRequest(final MTLinkRequest request, final String msgSuffix)
	{
		logger.log(Level.FINE, request + msgSuffix); // log the request
		ILoggable.THREADLOCAL
				.getLoggableOr(ILoggable.NULL)
				.addLog(request.getModel() + msgSuffix); // don't be too verbose in the user/admin output; keep it readable.
	}

	@Override
	public boolean unlinkModelFromMaterialTracking(final Object model)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking_Ref refExisting = materialTrackingDAO.retrieveMaterialTrackingRefForModel(model);
		if (refExisting == null)
		{
			return false;
		}

		unlinkModelFromMaterialTracking(model, refExisting);
		return true;
	}

	@Override
	public boolean unlinkModelFromMaterialTracking(final Object model, final I_M_Material_Tracking materialTracking)
	{
		Check.assumeNotNull(materialTracking, "materialTracking not null");

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking_Ref refExisting = materialTrackingDAO.retrieveMaterialTrackingRefForModel(model);
		if (refExisting == null)
		{
			return false;
		}

		if (refExisting.getM_Material_Tracking_ID() != materialTracking.getM_Material_Tracking_ID())
		{
			return false;
		}

		unlinkModelFromMaterialTracking(model, refExisting);
		return true;
	}

	private final void unlinkModelFromMaterialTracking(final Object model, final I_M_Material_Tracking_Ref ref)
	{
		final I_M_Material_Tracking materialTrackingOld = ref.getM_Material_Tracking();

		InterfaceWrapperHelper.delete(ref);
		listeners.afterModelUnlinked(model, materialTrackingOld);
	}

	@Override
	public void createOrUpdateMaterialTrackingReportLines(final I_M_Material_Tracking_Report report)
	{
		final Map<Integer, ProductToQtys> productToQtys = new HashMap<Integer, ProductToQtys>();

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_C_Period period = report.getC_Period();
		final Timestamp periodEndDate = period.getEndDate();

		// get all the material tracking entries that fit the given period
		final List<I_M_Material_Tracking> materialTrackings = materialTrackingDAO.retrieveMaterialTrackingsForPeriod(period);

		for (final I_M_Material_Tracking materialTracking : materialTrackings)
		{
			// for each material tracking, gather the information from the linked inoutlines and pp orders

			putForMaterialTracking_InOutLine(productToQtys, materialTracking, periodEndDate);

			putForMaterialTracking_PPOrder(productToQtys, materialTracking, periodEndDate);

		}

		// after gathering all the needed info, create a new set of lines based on the new results
		createOrUpdateMaterialTrackingReportLines(productToQtys, report);

		// Mark the report as processed. This is more of an info for the user, but he can uncheck the flag and process again if he wants
		report.setProcessed(true);

		InterfaceWrapperHelper.save(report);

	}

	/**
	 * Delete the existing lines and create new ones based on the given parameters
	 *
	 * @param productToQtys
	 * @param report
	 */
	private void createOrUpdateMaterialTrackingReportLines(final Map<Integer, ProductToQtys> productToQtys, final I_M_Material_Tracking_Report report)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		// delete existing lines
		materialTrackingDAO.deleteMaterialTrackingReportLines(report);

		for (final Entry<Integer, ProductToQtys> productToQtysEntry : productToQtys.entrySet())
		{
			final int productID = productToQtysEntry.getKey();
			final ProductToQtys prodToQtys = productToQtysEntry.getValue();

			// create the new line
			final I_M_Material_Tracking_Report_Line newLine = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking_Report_Line.class, report);

			// set header
			newLine.setM_Material_Tracking_Report(report);

			// set product
			newLine.setM_Product_ID(productID);

			final BigDecimal qtyReceived = prodToQtys.getReceivedQty().setScale(1, RoundingMode.HALF_UP);
			final BigDecimal qtyIssued = prodToQtys.getIssuedQty().setScale(1, RoundingMode.HALF_UP);
			final BigDecimal qtyDifference = qtyReceived.subtract(qtyIssued).setScale(1, RoundingMode.HALF_UP);

			// set qtys
			newLine.setQtyReceived(qtyReceived);
			newLine.setQtyIssued(qtyIssued);
			newLine.setDifferenceQty(qtyDifference);

			InterfaceWrapperHelper.save(newLine);
		}
	}

	private void putForMaterialTracking_InOutLine(final Map<Integer, ProductToQtys> productToQtysMap,
			final I_M_Material_Tracking materialTracking,
			final Timestamp endDate)
	{

		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		final int productID = materialTracking.getM_Product_ID();

		// shall not happen
		if (productID <= 0)
		{
			return;
		}

		final List<I_M_Material_Tracking_Ref> refsForInOutLines = materialTrackingDAO.retrieveMaterialTrackingRefForType(materialTracking, I_M_InOutLine.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(materialTracking);
		final String trxName = InterfaceWrapperHelper.getTrxName(materialTracking);

		for (final I_M_Material_Tracking_Ref ref : refsForInOutLines)
		{

			final I_M_InOutLine iol = InterfaceWrapperHelper.create(ctx, ref.getRecord_ID(), I_M_InOutLine.class, trxName);

			// shall never happen
			Check.assumeNotNull(iol, "M_InOutLine not null in M_Material_Tracking_Ref: ", ref);

			
			final I_M_InOut io = iol.getM_InOut();
			
			// only completed and closed inouts are eligible
			if(!docActionBL.isStatusCompletedOrClosed(io))
			{
				continue;
			}
			final Timestamp movementDate = io.getMovementDate();

			// Movement date must be <= endDate
			if (movementDate.after(endDate))
			{
				continue;
			}

			// skip inoutlines with other products
			if (productID != iol.getM_Product_ID())
			{
				continue;
			}

			// skip inout lines with other material tracking
			if (materialTracking.getM_Material_Tracking_ID() != iol.getM_Material_Tracking_ID())
			{
				continue; // should not happen because that would mean an inconsistent M_Material_Tracking_Ref
			}

			ProductToQtys currentProdToQtys = productToQtysMap.get(productID);

			// if there is no productToQtys class for the given product, create one
			if (currentProdToQtys == null)
			{
				currentProdToQtys = new ProductToQtys(productID);
				productToQtysMap.put(productID, currentProdToQtys);
			}

			// update the qtyReceived
			final BigDecimal receivedQty = currentProdToQtys.getReceivedQty();
			currentProdToQtys.setReceivedQty(receivedQty.add(iol.getMovementQty()));

		}
	}

	private void putForMaterialTracking_PPOrder(final Map<Integer, ProductToQtys> productToQtysMap,
			final I_M_Material_Tracking materialTracking,
			final Timestamp endDate)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

		final int productID = materialTracking.getM_Product_ID();

		// shall not happen
		if (productID <= 0)
		{
			return;
		}

		final List<I_M_Material_Tracking_Ref> refsForPPOrders = materialTrackingDAO.retrieveMaterialTrackingRefForType(materialTracking, I_PP_Order.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(materialTracking);
		final String trxName = InterfaceWrapperHelper.getTrxName(materialTracking);

		for (final I_M_Material_Tracking_Ref ref : refsForPPOrders)
		{
			final I_PP_Order ppOrder = InterfaceWrapperHelper.create(ctx, ref.getRecord_ID(), I_PP_Order.class, trxName);

			// shall never happen
			Check.assumeNotNull(ppOrder, "PP_Order not null in M_Material_Tracking_Ref: ", ref);

			// only closed pp_Orders are eligible
			if (!docActionBL.isStatusClosed(ppOrder))
			{
				continue; // this is a common and frequent case; don't pollute the loggable with it.
			}

			final Timestamp productionDate = materialTrackingPPOrderBL.getDateOfProduction(ppOrder);

			// Production date must be <= endDate
			if (productionDate.after(endDate))
			{
				continue; // this is a common and frequent case; don't pollute the loggable with it.
			}

			// skip pp_Orders with other material trackings
			if (materialTracking.getM_Material_Tracking_ID() != ppOrder.getM_Material_Tracking_ID())
			{
				// should not happen because that would mean an inconsistent M_Material_Tracking_Ref
				ILoggable.THREADLOCAL.getLoggable().addLog(
						"Skipping {0} because it is referenced via M_Material_Tracking_Ref, but itself does not reference {1}",
						ppOrder, materialTracking);
				continue; 
			}

			ProductToQtys currentProdToQtys = productToQtysMap.get(productID);

			// if there is no productToQtys class for the given product, create one
			if (currentProdToQtys == null)
			{
				currentProdToQtys = new ProductToQtys(productID);
				productToQtysMap.put(productID, currentProdToQtys);
			}

			final BigDecimal qtyIssued = currentProdToQtys.getIssuedQty();
			currentProdToQtys.setIssuedQty(qtyIssued.add(ref.getQtyIssued()));

		}
	}

	/**
	 * @author metas-dev <dev@metas-fresh.com>
	 *
	 *         Class used for keeping the qtys of products up to date during the process
	 */
	class ProductToQtys
	{
		private Integer productID;
		private BigDecimal receivedQty;
		private BigDecimal issuedQty;

		public ProductToQtys(final Integer productID)
		{
			this.setProductID(productID);
			this.receivedQty = Env.ZERO;
			this.issuedQty = Env.ZERO;
		}

		public Integer getProductID()
		{
			return productID;
		}

		public void setProductID(final Integer productID)
		{
			this.productID = productID;
		}

		public BigDecimal getReceivedQty()
		{
			return receivedQty;
		}

		public void setReceivedQty(final BigDecimal receivedQty)
		{
			this.receivedQty = receivedQty;
		}

		public BigDecimal getIssuedQty()
		{
			return issuedQty;
		}

		public void setIssuedQty(final BigDecimal issuedQty)
		{
			this.issuedQty = issuedQty;
		}
	}
}
