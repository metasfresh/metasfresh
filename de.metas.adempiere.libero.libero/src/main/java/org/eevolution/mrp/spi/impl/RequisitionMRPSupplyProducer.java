package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_Requisition;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPSourceEvent;

import de.metas.adempiere.service.IRequisitionBL;
import de.metas.document.engine.IDocument;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.ProductPlanningBL;
import de.metas.quantity.Quantity;

public class RequisitionMRPSupplyProducer extends AbstractMRPSupplyProducer
{
	public RequisitionMRPSupplyProducer()
	{
		super();
		addSourceColumnNames(I_M_Requisition.Table_Name, new String[] {
				I_M_Requisition.COLUMNNAME_DateRequired,
				I_M_Requisition.COLUMNNAME_M_Warehouse_ID,
				// I_M_Requisition.COLUMNNAME_DocStatus, // not needed
		});
		addSourceColumnNames(I_M_RequisitionLine.Table_Name, new String[] {
				"AD_Org_ID",
				I_M_RequisitionLine.COLUMNNAME_M_Product_ID,
				I_M_RequisitionLine.COLUMNNAME_Qty,
				I_M_RequisitionLine.COLUMNNAME_C_OrderLine_ID, // QtyOrdered depends on that
		});
	}

	@Override
	public Class<?> getDocumentClass()
	{
		return I_M_Requisition.class;
	}

	@Override
	public boolean applies(final IMaterialPlanningContext mrpContext, IMutable<String> notAppliesReason)
	{
		final I_PP_Product_Planning productPlanning = mrpContext.getProductPlanning();

		// Requisition
		final boolean isPurchased = X_PP_Product_Planning.ISPURCHASED_Yes.equals(productPlanning.getIsPurchased());
		if (!isPurchased)
		{
			final I_M_Product product = mrpContext.getM_Product();
			notAppliesReason.setValue("The product is not a requisition (is not purchased): " + product.getValue());

			return false;
		}

		return true;
	}

	@Override
	public void onDocumentChange(final Object model, final DocTimingType timing)
	{
		// TODO: delete the MRP records when document is reversed/voided
	}

	@Override
	public void createSupply(final IMRPCreateSupplyRequest request)
	{
		final IMaterialPlanningContext mrpContext = request.getMrpContext();
		final IMRPExecutor executor = request.getMRPExecutor();

		final String trxName = mrpContext.getTrxName();
		final I_PP_Product_Planning m_product_planning = mrpContext.getProductPlanning();
		final int AD_Org_ID = mrpContext.getAD_Org().getAD_Org_ID();
		final Timestamp DemandDateStartSchedule = TimeUtil.asTimestamp(request.getDemandDate());
		final Quantity qtyPlanned = request.getQtyToSupply();
		final int bpartnerId = m_product_planning.getC_BPartner_ID();

		log.info("Create Requisition");

		final int duration = calculateDurationDays(mrpContext);
		// Get PriceList from BPartner/Group - teo_sarca, FR [ 2829476 ]
		int M_PriceList_ID = -1;
		if (bpartnerId > 0)
		{
			final String sql = "SELECT COALESCE(bp." + I_C_BPartner.COLUMNNAME_PO_PriceList_ID
					+ ",bpg." + I_C_BP_Group.COLUMNNAME_PO_PriceList_ID + ")"
					+ " FROM C_BPartner bp"
					+ " INNER JOIN C_BP_Group bpg ON (bpg.C_BP_Group_ID=bp.C_BP_Group_ID)"
					+ " WHERE bp.C_BPartner_ID=?";
			M_PriceList_ID = DB.getSQLValueEx(trxName, sql, bpartnerId);
		}

		final I_M_Requisition req = InterfaceWrapperHelper.newInstance(I_M_Requisition.class, mrpContext);
		req.setAD_Org_ID(AD_Org_ID);

		//
		// Set AD_User_ID: Planner (if any) or logged in user
		int adUserId = m_product_planning.getPlanner_ID();
		if (adUserId <= 0)
		{
			adUserId = Env.getAD_User_ID(mrpContext.getCtx());
		}
		req.setAD_User_ID(adUserId);

		req.setDateRequired(TimeUtil.addDays(DemandDateStartSchedule, 0 - duration));
		req.setDescription("Generate from MRP"); // TODO: add translation
		req.setM_Warehouse_ID(m_product_planning.getM_Warehouse_ID());

		final int docTypeReq_ID = getC_DocType_ID(mrpContext, X_C_DocType.DOCBASETYPE_PurchaseRequisition);
		req.setC_DocType_ID(docTypeReq_ID);
		if (M_PriceList_ID > 0)
		{
			req.setM_PriceList_ID(M_PriceList_ID);
		}
		InterfaceWrapperHelper.save(req);

		final I_M_RequisitionLine reqline = InterfaceWrapperHelper.newInstance(I_M_RequisitionLine.class, req);
		reqline.setM_Requisition_ID(req.getM_Requisition_ID());
		reqline.setLine(10);
		reqline.setAD_Org_ID(AD_Org_ID);
		reqline.setC_BPartner_ID(bpartnerId);
		reqline.setM_Product_ID(m_product_planning.getM_Product_ID());
		// reqline.setPrice(); // called automatically on beforeSave
		reqline.setPriceActual(BigDecimal.ZERO);
		reqline.setQty(qtyPlanned.getQty());
		reqline.setC_UOM_ID(qtyPlanned.getUOM().getC_UOM_ID());
		InterfaceWrapperHelper.save(reqline);

		// Set Correct Dates for Plan
		final List<I_PP_MRP> mrpList = mrpDAO.retrieveMRPRecords(req);
		for (final I_PP_MRP mrp : mrpList)
		{
			mrp.setDateOrdered(mrpContext.getDateAsTimestamp());
			mrp.setS_Resource_ID(m_product_planning.getS_Resource_ID());
			mrp.setDatePromised(req.getDateRequired());
			mrp.setDateStartSchedule(req.getDateRequired());
			mrp.setDateFinishSchedule(DemandDateStartSchedule);
			InterfaceWrapperHelper.save(mrp);

		}

		executor.addGeneratedSupplyDocument(req);
	}

	private int calculateDurationDays(final IMaterialPlanningContext mrpContext)
	{
		final ProductPlanningBL productPlanningBL = Adempiere.getSpringApplicationContext().getBean(ProductPlanningBL.class);

		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();
		final int leadtimeDays = productPlanningData.getDeliveryTime_Promised().intValueExact();
		final int durationTotalDays = productPlanningBL.calculateDurationDays(leadtimeDays, productPlanningData);

		return durationTotalDays;
	}

	@Override
	public void cleanup(final IMaterialPlanningContext mrpContext, final IMRPExecutor executor)
	{
		//
		// Delete generated requisitions
		// (i.e. Requisitions with Draft Status)
		final ICompositeQueryFilter<I_M_Requisition> filters = Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_Requisition.class);
		filters.addEqualsFilter(I_M_Requisition.COLUMNNAME_DocStatus, X_M_Requisition.DOCSTATUS_Drafted);
		filters.addEqualsFilter(I_M_Requisition.COLUMNNAME_AD_Client_ID, mrpContext.getAD_Client_ID());
		filters.addEqualsFilter(I_M_Requisition.COLUMNNAME_AD_Org_ID, mrpContext.getAD_Org().getAD_Org_ID());
		filters.addEqualsFilter(I_M_Requisition.COLUMNNAME_M_Warehouse_ID, mrpContext.getM_Warehouse().getM_Warehouse_ID());

		//
		// If we are running in an constrained MRP Context, filter only those documents
		if (mrpContext.getEnforced_PP_MRP_Demand_ID() > 0)
		{
			final IQuery<I_PP_MRP> mrpQuery = createMRPQueryBuilderForCleanup(mrpContext, executor)
					.createQueryBuilder()
					.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Supply)
					.create();

			filters.addInSubQueryFilter(I_M_Requisition.COLUMN_M_Requisition_ID, I_PP_MRP.COLUMN_M_Requisition_ID, mrpQuery);
		}

		deletePO(mrpContext, executor, I_M_Requisition.class, filters);
	}

	@Override
	protected void onRecordChange(final IMRPSourceEvent event)
	{
		final Object model = event.getModel();

		if (model instanceof I_M_Requisition && event.isChange())
		{
			final I_M_Requisition r = (I_M_Requisition)model;
			M_Requisition(r);
		}
		//
		else if (model instanceof I_M_RequisitionLine && event.isChange())
		{
			final I_M_RequisitionLine rl = (I_M_RequisitionLine)model;
			M_RequisitionLine(rl);
		}
	}

	/**
	 * Create MRP record based in Requisition Line
	 *
	 * @param MRequisitionLine Requisition Line
	 */
	private void M_Requisition(final I_M_Requisition r)
	{
		final String typeMRP = null;
		final String orderType = null;
		final List<I_PP_MRP> mrpList = Services.get(IMRPDAO.class)
				.retrieveQueryBuilder(r, typeMRP, orderType)
				.setSkipIfMRPExcluded(false)
				.list();
		for (final I_PP_MRP mrp : mrpList)
		{
			setM_Requisition(mrp, r);
			InterfaceWrapperHelper.save(mrp);
		}
	}

	/**
	 * Create MRP record based in Requisition Line
	 *
	 * @param MRequisitionLine Requisition Line
	 */
	private void M_RequisitionLine(final I_M_RequisitionLine rl)
	{
		I_PP_MRP mrp = Services.get(IMRPDAO.class)
				.retrieveQueryBuilder(rl, null, null)
				.setSkipIfMRPExcluded(false)
				.firstOnly();
		final I_M_Requisition r = rl.getM_Requisition();
		if (mrp == null)
		{
			mrp = InterfaceWrapperHelper.newInstance(I_PP_MRP.class, rl);
			mrp.setM_Requisition_ID(rl.getM_Requisition_ID());
			mrp.setM_RequisitionLine(rl);
		}

		// We create a MRP record only for Not Ordered Qty. The Order will generate a MRP record for Ordered Qty.
		final BigDecimal qtyTarget = rl.getQty();
		final BigDecimal qtyAlreadyOrdered = Services.get(IRequisitionBL.class).getQtyOrdered(rl);
		final BigDecimal qty = qtyTarget.subtract(qtyAlreadyOrdered);
		final I_C_UOM uom = null; // i.e. N/A

		setM_Requisition(mrp, r);
		//
		mrp.setAD_Org_ID(rl.getAD_Org_ID());
		mrp.setName("MRP");
		mrp.setDescription(rl.getDescription());
		mrp.setM_Product_ID(rl.getM_Product_ID());
		mrp.setM_AttributeSetInstance_ID(rl.getM_AttributeSetInstance_ID());
		mrpBL.setQty(mrp, qtyTarget, qty, uom);
		// MRP record for a requisition will be ALWAYS Drafted because
		// a requisition generates just Planned Orders (which is a wish list)
		// and not Scheduled Receipts
		mrp.setDocStatus(IDocument.STATUS_Drafted);
		InterfaceWrapperHelper.save(mrp);
	}

	private final void setM_Requisition(final I_PP_MRP mrp, final I_M_Requisition r)
	{
		mrpBL.updateMRPFromContext(mrp);

		mrp.setM_Requisition_ID(r.getM_Requisition_ID());
		mrp.setOrderType(X_PP_MRP.ORDERTYPE_MaterialRequisition);
		mrp.setTypeMRP(X_PP_MRP.TYPEMRP_Supply);
		//
		// setAD_Org_ID(r.getAD_Org_ID());
		mrp.setDateOrdered(r.getDateRequired());
		mrp.setDatePromised(r.getDateRequired());
		mrp.setDateStartSchedule(r.getDateRequired());
		mrp.setDateFinishSchedule(r.getDateRequired());
		mrp.setM_Warehouse_ID(r.getM_Warehouse_ID());
	}

}
