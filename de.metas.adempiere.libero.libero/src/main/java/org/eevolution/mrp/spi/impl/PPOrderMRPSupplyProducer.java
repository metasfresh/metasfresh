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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.PlainStringLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IMutable;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alternative;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Order;
import org.eevolution.mrp.api.ILiberoMRPContextFactory;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPExecutorService;
import org.eevolution.mrp.api.IMRPSourceEvent;
import org.eevolution.mrp.spi.impl.pporder.PPOrderProducer;

import de.metas.material.event.pporder.PPOrder;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderDemandMatcher;
import de.metas.material.planning.pporder.PPOrderPojoSupplier;
import de.metas.material.planning.pporder.PPOrderUtil;

public class PPOrderMRPSupplyProducer extends AbstractMRPSupplyProducer
{
	//
	// Services
	// private final IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);

	public PPOrderMRPSupplyProducer()
	{
		addSourceColumnNames(I_PP_Order.Table_Name, new String[] {
				I_PP_Order.COLUMNNAME_AD_Org_ID,
				I_PP_Order.COLUMNNAME_M_Product_ID,
				I_PP_Order.COLUMNNAME_C_UOM_ID,
				I_PP_Order.COLUMNNAME_DatePromised,
				I_PP_Order.COLUMNNAME_QtyOrdered,
				I_PP_Order.COLUMNNAME_QtyDelivered,
				I_PP_Order.COLUMNNAME_PP_Product_BOM_ID,
				I_PP_Order.COLUMNNAME_AD_Workflow_ID,
				I_PP_Order.COLUMNNAME_DocStatus,
				I_PP_Order.COLUMNNAME_PreparationDate,
				I_PP_Order.COLUMNNAME_C_OrderLine_ID,
				I_PP_Order.COLUMNNAME_C_BPartner_ID,
				I_PP_Order.COLUMNNAME_M_Warehouse_ID,
				I_PP_Order.COLUMNNAME_M_Locator_ID,
		});
		addSourceColumnNames(I_PP_Order_BOMLine.Table_Name, new String[] {
				I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID,
				I_PP_Order_BOMLine.COLUMNNAME_C_UOM_ID,
				I_PP_Order_BOMLine.COLUMNNAME_M_Warehouse_ID,
				I_PP_Order_BOMLine.COLUMNNAME_QtyEntered,
				I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered,
				I_PP_Order_BOMLine.COLUMNNAME_QtyDelivered,
				I_PP_Order_BOMLine.COLUMNNAME_M_Warehouse_ID,
				I_PP_Order_BOMLine.COLUMNNAME_M_Locator_ID,
				I_PP_Order_BOMLine.COLUMNNAME_ComponentType,
				I_PP_Order_BOMLine.COLUMNNAME_VariantGroup,
		});

	}

	@Override
	public Class<?> getDocumentClass()
	{
		return I_PP_Order.class;
	}

	@Override
	public boolean applies(final IMaterialPlanningContext mrpContext, IMutable<String> notAppliesReason)
	{
		final PlainStringLoggable loggable = new PlainStringLoggable();
		final boolean matches;
		try (final IAutoCloseable closable = Loggables.temporarySetLoggable(loggable))
		{
			matches = new PPOrderDemandMatcher().matches(mrpContext);
		}
		if (!matches)
		{
			notAppliesReason.setValue(loggable.getConcatenatedMessages());
		}
		return matches;
	}

	@Override
	protected void onRecordChange(final IMRPSourceEvent event)
	{
		if (!event.isChange())
		{
			return;
		}

		final Object model = event.getModel();

		//
		// Case: manufacturing order header changed
		if (InterfaceWrapperHelper.isInstanceOf(model, I_PP_Order.class))
		{
			final I_PP_Order order = InterfaceWrapperHelper.create(model, I_PP_Order.class);
			onPP_Order_Changed(order);
		}
		//
		// Case: BOM Line changed
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_PP_Order_BOMLine.class))
		{
			final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(model, I_PP_Order_BOMLine.class);
			onPP_Order_BOMLine_Changed(ppOrderBOMLine);
		}
	}

	@Override
	protected void onRecordDeleted(final IMRPSourceEvent event)
	{
		super.onRecordDeleted(event);

		//
		// Delete MRP alternatives, if any
		final Object model = event.getModel();
		if (InterfaceWrapperHelper.isInstanceOf(model, I_PP_Order_BOMLine.class))
		{
			final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(model, I_PP_Order_BOMLine.class);
			final I_PP_MRP_Alternative mrpAlternative = retrieveMRPAlternative(ppOrderBOMLine);
			if (mrpAlternative != null)
			{
				InterfaceWrapperHelper.delete(mrpAlternative);
			}
		}
	}

	@Override
	public void onDocumentChange(final Object model, final DocTimingType timing)
	{
		// nothing
	}

	private I_PP_MRP retrieveMRPRecordOrNull(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final String typeMRP = null; // we don't care
		// NOTE: in case we are dealing with a Co/By-Product line, we will get an MRP Supply record

		return mrpDAO.retrieveQueryBuilder(ppOrderBOMLine, typeMRP, X_PP_MRP.ORDERTYPE_ManufacturingOrder)
				.setSkipIfMRPExcluded(false)
				.firstOnly();
	}

	public List<I_PP_MRP> retrieveMRPRecords(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final String typeMRP = null; // accept Demands and Supplies too

		return mrpDAO.retrieveQueryBuilder(ppOrderBOMLine, typeMRP, X_PP_MRP.ORDERTYPE_ManufacturingOrder)
				.setSkipIfMRPExcluded(false)
				.list();
	}

	/**
	 *
	 * @param ppOrder
	 * @return all MRP demands for a given manufacturing order
	 */
	private List<I_PP_MRP> retrieveMRPDemands(final I_PP_Order ppOrder)
	{
		final List<I_PP_MRP> mrpDemands = mrpDAO.retrieveQueryBuilder(ppOrder, X_PP_MRP.TYPEMRP_Demand, X_PP_MRP.ORDERTYPE_ManufacturingOrder)
				.setSkipIfMRPExcluded(false)
				.list();
		return mrpDemands;
	}

	/**
	 *
	 * @param ppOrder
	 * @return MRP supply record for manufacturing order header (i.e. the actual supply and not the co/by products supply)
	 */
	private I_PP_MRP retrieveMRPSupply(final I_PP_Order ppOrder)
	{
		return mrpDAO.retrieveQueryBuilder(ppOrder, X_PP_MRP.TYPEMRP_Supply, X_PP_MRP.ORDERTYPE_ManufacturingOrder)
				.setSkipIfMRPExcluded(false)
				.firstOnly();
	}

	/**
	 * Retrieves MRP Alternative record for given BOM Line.
	 *
	 * NOTE: this method does not validate if the BOM Line is actually an alternative. Please keep it as it is because we re-use this method also when a BOM Line is deleted.
	 *
	 * @param orderBOMLine
	 * @return MRP Alternative or <code>null</code>.
	 */
	private I_PP_MRP_Alternative retrieveMRPAlternative(final I_PP_Order_BOMLine orderBOMLine)
	{
		Check.assumeNotNull(orderBOMLine, "orderBOMLine not null");
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_PP_MRP_Alternative> queryBuilder = queryBL.createQueryBuilder(I_PP_MRP_Alternative.class, orderBOMLine)
				.addEqualsFilter(I_PP_MRP_Alternative.COLUMN_PP_Order_BOMLine_ID, orderBOMLine.getPP_Order_BOMLine_ID());

		return queryBuilder.create().firstOnly(I_PP_MRP_Alternative.class);
	}

	/**
	 * Called when {@link I_PP_Order} changed.
	 *
	 * It will create MRP supply record for manufacturing order header.
	 *
	 * @param I_PP_Order Manufacturing Order
	 */
	private final void onPP_Order_Changed(final I_PP_Order ppOrder)
	{
		//
		// Supply (from Order Header)
		I_PP_MRP mrpSupply = retrieveMRPSupply(ppOrder);
		if (mrpSupply == null)
		{
			mrpSupply = mrpBL.createMRP(ppOrder);
			mrpSupply.setAD_Org_ID(ppOrder.getAD_Org_ID());
			mrpSupply.setTypeMRP(X_PP_MRP.TYPEMRP_Supply);
		}
		setPP_Order(mrpSupply, ppOrder);
		mrpSupply.setM_Product_ID(ppOrder.getM_Product_ID());
		mrpSupply.setM_AttributeSetInstance_ID(ppOrder.getM_AttributeSetInstance_ID());
		mrpSupply.setM_Warehouse_ID(ppOrder.getM_Warehouse_ID());

		final BigDecimal qtyTarget = ppOrder.getQtyOrdered();
		BigDecimal qtyToDeliver = qtyTarget.subtract(ppOrder.getQtyDelivered());
		if (qtyToDeliver.signum() < 0)
		{
			qtyToDeliver = BigDecimal.ZERO;
		}
		mrpBL.setQty(mrpSupply, qtyTarget, qtyToDeliver, ppOrder.getC_UOM());
		InterfaceWrapperHelper.save(mrpSupply);

		//
		// Update Demand (from Order BOM Lines)
		final List<I_PP_MRP> mrpDemands = retrieveMRPDemands(ppOrder);
		for (final I_PP_MRP mrpDemand : mrpDemands)
		{
			setPP_Order(mrpDemand, ppOrder);
			InterfaceWrapperHelper.save(mrpDemand);
		}
	}

	/**
	 * Called when {@link I_PP_Order_BOMLine} changed.
	 *
	 * It will create MRP demand (or supply in case of by/co-product) for manufacturing order's BOM Line.
	 *
	 * @param ppOrderBOMLine
	 */
	private final void onPP_Order_BOMLine_Changed(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		if (isAlternative(ppOrderBOMLine))
		{
			createUpdateMRPDemandAlternativeIfPossible(ppOrderBOMLine);
		}
		else
		{
			final I_PP_MRP mrpRecordOld = retrieveMRPRecordOrNull(ppOrderBOMLine);
			final I_PP_MRP mrpRecordNew = createUpdateMRPRecord(ppOrderBOMLine);
			updateDependingChildSupplies(ppOrderBOMLine, mrpRecordOld, mrpRecordNew);
		}

	}

	/**
	 * Create/Update MRP record based in Manufacturing Order BOM Line
	 *
	 * @param I_PP_OrderBOMLine Order BOM Line
	 * @return newly created or update {@link I_PP_MRP} demand record.
	 */
	private I_PP_MRP createUpdateMRPRecord(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final IPPOrderBOMDAO orderBOMDAO = Services.get(IPPOrderBOMDAO.class);

		if (isAlternative(ppOrderBOMLine))
		{
			// task 06005: if component type is now to "Variant", delete existing mrp records (if any)
			for (final I_PP_MRP mrpToDelete : retrieveMRPRecords(ppOrderBOMLine))
			{
				InterfaceWrapperHelper.delete(mrpToDelete);
			}
			return null;
		}

		final String typeMRP = getTypeMRP(ppOrderBOMLine);
		final BigDecimal qtyTarget = getQtyTarget(ppOrderBOMLine);
		final BigDecimal qty = getQty(ppOrderBOMLine);
		final I_C_UOM uom = ppOrderBOMLine.getC_UOM();

		//
		// Create/Update BOM Line's MRP record
		I_PP_MRP mrpRecord = retrieveMRPRecordOrNull(ppOrderBOMLine);
		final boolean isNewMRPRecord = mrpRecord == null;
		if (isNewMRPRecord)
		{
			mrpRecord = mrpBL.createMRP(ppOrderBOMLine);
			mrpRecord.setPP_Order_BOMLine_ID(ppOrderBOMLine.getPP_Order_BOMLine_ID());
		}
		mrpRecord.setAD_Org_ID(ppOrderBOMLine.getAD_Org_ID());
		mrpRecord.setTypeMRP(typeMRP);
		setPP_Order(mrpRecord, ppOrderBOMLine.getPP_Order());
		mrpRecord.setM_Warehouse_ID(ppOrderBOMLine.getM_Warehouse_ID());
		mrpRecord.setM_Product_ID(ppOrderBOMLine.getM_Product_ID());
		mrpRecord.setM_AttributeSetInstance_ID(ppOrderBOMLine.getM_AttributeSetInstance_ID());
		mrpBL.setQty(mrpRecord, qtyTarget, qty, uom);
		InterfaceWrapperHelper.save(mrpRecord);

		//
		// Create MRP Alternative records
		if (isNewMRPRecord)
		{
			final List<I_PP_Order_BOMLine> orderBOMAlternatives = orderBOMDAO.retrieveOrderBOMLineAlternatives(ppOrderBOMLine, I_PP_Order_BOMLine.class);
			for (final I_PP_Order_BOMLine orderBOMAlternative : orderBOMAlternatives)
			{
				createUpdateMRPDemandAlternative(orderBOMAlternative, mrpRecord);
			}
		}

		return mrpRecord;
	}

	private String getTypeMRP(final I_PP_Order_BOMLine orderBOMLine)
	{
		final String typeMRP;

		if (PPOrderUtil.isReceipt(orderBOMLine.getComponentType()))
		{
			typeMRP = X_PP_MRP.TYPEMRP_Supply;
		}
		else
		{
			typeMRP = X_PP_MRP.TYPEMRP_Demand;
		}

		return typeMRP;
	}

	public BigDecimal getQtyTarget(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);

		final BigDecimal qtyTarget;
		if (PPOrderUtil.isReceipt(ppOrderBOMLine.getComponentType()))
		{
			qtyTarget = orderBOMBL.getQtyRequiredToReceive(ppOrderBOMLine);
		}
		else
		{
			qtyTarget = orderBOMBL.getQtyRequiredToIssue(ppOrderBOMLine);
		}

		return qtyTarget;
	}

	/**
	 * Gets quantity to Receive or to Issue. This quantity will be set to {@link I_PP_MRP}.
	 *
	 * NOTE: in case of an over receipt/over issue it will return ZERO instead of negative quantity, because that's the quantity on which we shall plan.
	 *
	 * @param ppOrderBOMLine
	 * @return Quantity to Receive or to Issue
	 */
	private BigDecimal getQty(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);

		final BigDecimal qty;
		if (PPOrderUtil.isReceipt(ppOrderBOMLine.getComponentType()))
		{
			qty = orderBOMBL.getQtyToReceive(ppOrderBOMLine);
		}
		else
		{
			qty = orderBOMBL.getQtyToIssue(ppOrderBOMLine);
		}

		//
		// In case the quantity is negative then the actual Qty to issue/receive which shall be considered for MRP planning is ZERO.
		if (qty.signum() < 0)
		{
			return BigDecimal.ZERO;
		}

		return qty;
	}

	/**
	 * Create MRP alternative record for given Order BOM Line alternative, only if the MRP record for main component already exists.
	 *
	 * @param ppOrderBOMLineAlternative
	 */
	private void createUpdateMRPDemandAlternativeIfPossible(final I_PP_Order_BOMLine ppOrderBOMLineAlternative)
	{
		final IPPOrderBOMDAO orderBOMDAO = Services.get(IPPOrderBOMDAO.class);

		assertAlternative(ppOrderBOMLineAlternative);

		final I_PP_Order_BOMLine ppOrderBOMLineComponent = orderBOMDAO.retrieveComponentBOMLineForAlternative(ppOrderBOMLineAlternative);
		if (ppOrderBOMLineComponent == null)
		{
			// Case: we are about creating the PP Order's BOM line,
			// BOM Line for alternative was already created but the BOM Line for main component was not yet created
			// => skip it because we will create the MRP records for alternatives when the BOM line for main component will be created
			return;
		}

		//
		// Get the MRP record for alternative's main component.
		// If there is no MRP record already created, do nothing,
		// we will create the MRP records for alternatives when MRP record for main component will be created.
		final I_PP_MRP mrpComponent = retrieveMRPRecordOrNull(ppOrderBOMLineComponent);
		if (mrpComponent == null)
		{
			return;
		}

		createUpdateMRPDemandAlternative(ppOrderBOMLineAlternative, mrpComponent);
	}

	/**
	 * Create/Update MRP alternative record for given Order BOM Line alternative
	 *
	 * @param ppOrderBOMLineAlternative
	 * @param mrpComponent MRP record of the main component
	 */
	private void createUpdateMRPDemandAlternative(final I_PP_Order_BOMLine ppOrderBOMLineAlternative, final I_PP_MRP mrpComponent)
	{
		Check.assumeNotNull(mrpComponent, "mrpComponent not null");

		I_PP_MRP_Alternative mrpAlternative = retrieveMRPAlternative(ppOrderBOMLineAlternative);

		// If we got an MRP alternative but is not linked to our main component MRP record (but to other record)
		// we need to delete it first and recreate it
		if (mrpAlternative != null && mrpAlternative.getPP_MRP_ID() != mrpComponent.getPP_MRP_ID())
		{
			InterfaceWrapperHelper.delete(mrpAlternative);
			mrpAlternative = null;
		}

		//
		// If no MRP alternative found (or it was deleted)
		// we need to create a new one
		final boolean isNewMRPAlternative = mrpAlternative == null;
		if (isNewMRPAlternative)
		{
			mrpAlternative = mrpBL.createMRPAlternative(mrpComponent);
			mrpAlternative.setPP_Order_ID(ppOrderBOMLineAlternative.getPP_Order_ID());
			mrpAlternative.setPP_Order_BOMLine(ppOrderBOMLineAlternative);
		}

		//
		// Update MRP alternative record
		final BigDecimal qty = getQty(ppOrderBOMLineAlternative);
		mrpAlternative.setAD_Org_ID(ppOrderBOMLineAlternative.getAD_Org_ID());
		mrpAlternative.setM_Product_ID(ppOrderBOMLineAlternative.getM_Product_ID());
		mrpAlternative.setQty(qty);
		mrpAlternative.setIsActive(true);
		InterfaceWrapperHelper.save(mrpAlternative);

		//
		// If the MRP record for the component was just created,
		// then create also the MRP alternatives for bom component alternative.
		// NOTE: in case this is just an update we don't have to update the alternatives because those will be updated directly in case they change.
		if (!isNewMRPAlternative)
		{
			// NOTE: we have to notify about the Component change and not about alternative because alternative is not considered activelly in MRP planning.
			// It is considered only passivelly, i.e. won't affect the planning but the records will be created/updated and carried on.
			final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
			final I_PP_Order_BOMLine ppOrderBOMLine_Component = ppOrderBOMDAO.retrieveComponentBOMLineForAlternative(ppOrderBOMLineAlternative);
			Check.assumeNotNull(ppOrderBOMLine_Component, LiberoException.class, "ppOrderBOMLine_Component not null"); // at this point shall not be null because we already have the MRP record for it
			updateDependingChildSupplies(ppOrderBOMLine_Component,
					mrpComponent, // mrpDemandOld,
					mrpComponent // mrpDemandNew
			);
		}
	}

	@Override
	public void createSupply(final IMRPCreateSupplyRequest request)
	{
		final IMaterialPlanningContext mrpContext = request.getMrpContext();
		final IMRPExecutor executor = request.getMRPExecutor();

		final PPOrderPojoSupplier ppOrderPojoSupplier = Adempiere.getBean(PPOrderPojoSupplier.class);

		final PPOrder ppOrderPojo = ppOrderPojoSupplier
				.supplyPPOrderPojo(request,
						executor.getMRPNotesCollector());


		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();

		final PPOrderProducer ppOrderProducer = new PPOrderProducer();

		// note that the PP_OrderBOM and PP_OrderBOMLines are currently created via model interceptor
		final I_PP_Order ppOrder = ppOrderProducer.createPPOrder(ppOrderPojo, mrpContext.getDate());
		executor.addGeneratedSupplyDocument(ppOrder);
		//
		// If we are asked to complete it, enqueue the MO for completion after MRP runs
		if (productPlanningData.isDocComplete())
		{
			final DocumentsToCompleteAfterMRPExecution scheduler = DocumentsToCompleteAfterMRPExecution.getCreate(executor);
			scheduler.enqueuePPOrder(ppOrder);
		}
		//
		// Let the executor know that we generated a new document
		executor.addGeneratedSupplyDocument(ppOrder);
	}

	@Override
	public void cleanup(final IMaterialPlanningContext mrpContext, final IMRPExecutor executor)
	{
		//
		// Delete generated manufacturing orders
		// (i.e. Manufacturing Order with Draft Status)
		final ICompositeQueryFilter<I_PP_Order> filters = Services.get(IQueryBL.class).createCompositeQueryFilter(I_PP_Order.class);
		filters.addEqualsFilter(I_PP_Order.COLUMNNAME_DocStatus, X_PP_Order.DOCSTATUS_Drafted);

		//
		// Only those which were generated by MRP
		filters.addEqualsFilter(I_PP_Order.COLUMN_MRP_Generated, true);
		// Only those which are allowed to be deleted by MRP cleanup
		filters.addEqualsFilter(I_PP_Order.COLUMN_MRP_AllowCleanup, true);

		// For our MRP segment
		filters.addEqualsFilter(I_PP_Order.COLUMNNAME_AD_Client_ID, mrpContext.getAD_Client_ID());
		filters.addEqualsFilter(I_PP_Order.COLUMNNAME_AD_Org_ID, mrpContext.getAD_Org().getAD_Org_ID());
		filters.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Warehouse_ID, mrpContext.getM_Warehouse().getM_Warehouse_ID());
		filters.addEqualsFilter(I_PP_Order.COLUMNNAME_S_Resource_ID, mrpContext.getPlant().getS_Resource_ID());

		//
		// If we are running in an constrained MRP Context, filter only those documents
		if (mrpContext.getEnforced_PP_MRP_Demand_ID() > 0)
		{
			final IQuery<I_PP_MRP> mrpQuery = createMRPQueryBuilderForCleanup(mrpContext, executor)
					.createQueryBuilder()
					.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Supply)
					.create();

			filters.addInSubQueryFilter(I_PP_Order.COLUMN_PP_Order_ID, I_PP_MRP.COLUMN_PP_Order_ID, mrpQuery);
		}

		deletePO(mrpContext, executor, I_PP_Order.class, filters);
	}

	/**
	 * Updates MRP record (demand or supply) from given manufacturing order.
	 *
	 * @param mrp
	 * @param ppOrder
	 */
	private final void setPP_Order(final I_PP_MRP mrp, final I_PP_Order ppOrder)
	{
		mrpBL.updateMRPFromContext(mrp);

		mrp.setPP_Order(ppOrder);
		mrp.setOrderType(X_PP_MRP.ORDERTYPE_ManufacturingOrder);
		// TypeMRP : don't set because it depends if is for finished good or raw material

		mrp.setName(ppOrder.getDocumentNo());
		mrp.setDescription(ppOrder.getDescription());
		mrp.setDatePromised(ppOrder.getDatePromised());
		mrp.setDateOrdered(ppOrder.getDateOrdered());
		mrp.setDateStartSchedule(ppOrder.getDateStartSchedule());
		mrp.setDateFinishSchedule(ppOrder.getDateFinishSchedule());
		mrp.setS_Resource_ID(ppOrder.getS_Resource_ID()); // Plant
		mrp.setC_OrderLineSO_ID(ppOrder.getC_OrderLine_ID());
		mrp.setC_BPartner_ID(ppOrder.getC_BPartner_ID());
		mrp.setDocStatus(ppOrder.getDocStatus());
		mrp.setPreparationDate(ppOrder.getPreparationDate());
	}

	/**
	 * Update depending MRP child supplies.
	 *
	 * @param orderBOMLine
	 * @param mrpDemandOld old MRP demand (or null)
	 * @param mrpDemandNew new MRP demand (or null)
	 */
	private void updateDependingChildSupplies(I_PP_Order_BOMLine orderBOMLine, final I_PP_MRP mrpDemandOld, final I_PP_MRP mrpDemandNew)
	{
		final IMRPExecutorService mrpExecutorService = Services.get(IMRPExecutorService.class);

		// If we already have an executor running, don't overlap with it
		if (mrpExecutorService.isRunning())
		{
			return;
		}

		//
		// If PP_Order was already processed, do nothing
		if (orderBOMLine.getPP_Order().isProcessed())
		{
			return;
		}

		final ILiberoMRPContextFactory mrpContextFactory = Services.get(ILiberoMRPContextFactory.class);

		final MRPContextUniquePlanningSegmentsCollector mrpContextsCollector = new MRPContextUniquePlanningSegmentsCollector();
		mrpContextsCollector.setKeepLastAdded(true);

		//
		// Create MRP Context from old MRP Demand
		// NOTE: To avoid the case when we are dealing with co/by products which are actually supplies, we are making sure this is a demand MRP record
		if (mrpDemandOld != null && mrpBL.isDemand(mrpDemandOld))
		{
			final IMutableMRPContext mrpContextOld = mrpContextFactory.createMRPContextFromDemand(mrpDemandOld);
			mrpContextOld.setSubsequentMRPExecutorCall(true);
			// FIXME: mrpContextOld.setQtyToSupply(BigDecimal.ZERO); // we want to drop the plannings for this old MRP Demand
			mrpContextsCollector.addMRPContext(mrpContextOld);
		}

		//
		// Create MRP Context from new MRP Demand
		// NOTE: To avoid the case when we are dealing with co/by products which are actually supplies, we are making sure this is a demand MRP record
		if (mrpDemandNew != null && mrpBL.isDemand(mrpDemandNew))
		{
			final IMutableMRPContext mrpContextNew = mrpContextFactory.createMRPContextFromDemand(mrpDemandNew);
			mrpContextNew.setSubsequentMRPExecutorCall(true);
			mrpContextsCollector.addMRPContext(mrpContextNew);
		}

		//
		// Run MRP on collected contexts (if any)
		if (!mrpContextsCollector.isEmpty())
		{
			// NOTE: in future we will definitelly need to call MRP executor asynchronously.
			mrpExecutorService.run(mrpContextsCollector.getMRPContexts());
		}
	}

	private final boolean isAlternative(final I_PP_Order_BOMLine orderBOMLine)
	{
		final boolean alternative = PPOrderUtil.isVariant(orderBOMLine);
		return alternative;
	}

	private final void assertAlternative(final I_PP_Order_BOMLine orderBOMLine)
	{
		Check.assume(isAlternative(orderBOMLine), LiberoException.class, "BOM Order Line shall be an alternative BOM Line: {}", orderBOMLine);
	}
}
