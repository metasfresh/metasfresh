/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 * Teo Sarca, www.arhipac.ro *
 *****************************************************************************/
package org.eevolution.model;

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

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.OrderBOMLineQtyChangeRequest;
import de.metas.material.planning.pporder.OrderQtyChangeRequest;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.workflow.WFDurationUnit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.MTransaction;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_Transaction;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPCostCollectorQuantities;
import org.eevolution.api.PPOrderActivityProcessReport;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityId;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 * PP Cost Collector Model
 *
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>Original contributor of Manufacturing Standard Cost
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @author Teo Sarca, www.arhipac.ro
 * @version $Id: MPPCostCollector.java,v 1.1 2004/06/19 02:10:34 vpj-cd Exp $
 */
public class MPPCostCollector extends X_PP_Cost_Collector implements IDocument
{
	private static final long serialVersionUID = 5529730708956719853L;

	// Services
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

	/**
	 * Just Prepared Flag
	 */
	private boolean m_justPrepared = false;

	@SuppressWarnings("unused")
	public MPPCostCollector(final Properties ctx, final int PP_Cost_Collector_ID, final String trxName)
	{
		super(ctx, PP_Cost_Collector_ID, trxName);
		if (is_new())
		{
			// setC_DocType_ID(0);
			setDocStatus(DOCSTATUS_Drafted);    // DR
			setDocAction(DOCACTION_Complete);    // CO
			setMovementDate(new Timestamp(System.currentTimeMillis()));    // @#Date@
			setIsActive(true);
			setPosted(false);
			setProcessing(false);
			setProcessed(false);
		}
	}

	@SuppressWarnings("unused")
	public MPPCostCollector(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    // MPPCostCollector

	@Override
	public void setProcessed(final boolean processed)
	{
		super.setProcessed(processed);
		if (is_new())
		{
			return;
		}
		final String sql = "UPDATE PP_Cost_Collector SET Processed=? WHERE PP_Cost_Collector_ID=?";
		DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { processed, get_ID() }, get_TrxName());
	}

	@Override
	public boolean processIt(final String processAction)
	{
		return Services.get(IDocumentBL.class).processIt(this, processAction);
	}

	@Override
	public boolean unlockIt()
	{
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt()
	{
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocTypeTarget_ID(), getAD_Org_ID());
		setC_DocType_ID(getC_DocTypeTarget_ID());

		// Don't check if ASI is mandatory because we have our attributes on HU level and not here.
		// final CostCollectorType costCollectorType = CostCollectorType.ofCode(getCostCollectorType());
		// final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(getPP_Order_BOMLine_ID());
		// if (costCollectorType.isMaterial(orderBOMLineId))
		// {
		// 	final ProductId productId = ProductId.ofRepoId(getM_Product_ID());
		// 	final boolean isSOTrx = costCollectorType.isMaterialReceipt();
		// 	if (getM_AttributeSetInstance_ID() <= 0 && Services.get(IProductBL.class).isASIMandatory(productId, isSOTrx))
		// 	{
		// 		throw new LiberoException("@M_AttributeSet_ID@ @IsMandatory@ @M_Product_ID@=" + Services.get(IProductBL.class).getProductValueAndName(productId));
		// 	}
		// }

		m_justPrepared = true;
		setDocAction(DOCACTION_Complete);

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);

		return IDocument.STATUS_InProgress;
	}

	@Override
	public boolean approveIt()
	{
		// setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt()
	{
		// setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			final String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);

		final CostCollectorType costCollectorType = CostCollectorType.ofCode(getCostCollectorType());
		final boolean isReversal = isReversal();
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(getPP_Order_BOMLine_ID());
		final PPOrderId orderId = PPOrderId.ofRepoId(getPP_Order_ID());
		final PPOrderRoutingActivityId activityId = PPOrderRoutingActivityId.ofRepoIdOrNull(orderId, getPP_Order_Node_ID());

		if (costCollectorType.isMaterial(orderBOMLineId))
		{
			completeIt_MaterialIssueOrReceipt();
		}
		else if (costCollectorType.isActivityControl())
		{
			completeIt_ActivityControl();
		}
		else if (costCollectorType.isMaterialUsageVariance(orderBOMLineId))
		{
			completeIt_MaterialUsageVariance();
		}
		else if (costCollectorType.isResourceUsageVariance(activityId))
		{
			completeIt_ResourceUsageVariance();
		}
		else if (costCollectorType.isRateVariance())
		{
			completeIt_RateVariance();
		}
		else if (costCollectorType.isMethodChangeVariance())
		{
			completeIt_MethodChangedVariance();
		}

		//
		// Create Rate and Method Variances
		// if (!isReversal)
		// {
		// costEngine.createRateVariances(this);
		// costEngine.createMethodVariances(this);
		// }

		// Reverse Rate and Method Variances
		if (isReversal)
		{
			// Get the initial cost collector (which is reversed by this one)
			final I_PP_Cost_Collector ccOriginal = getReversal();

			// Get it's child cost collectors and reverse them one by one
			final List<I_PP_Cost_Collector> childCostCollectors = Services.get(IPPCostCollectorDAO.class).getByParent(ccOriginal);
			for (final I_PP_Cost_Collector childCostCollector : childCostCollectors)
			{
				docActionBL.processEx(childCostCollector, X_PP_Cost_Collector.DOCACTION_Reverse_Correct, X_PP_Cost_Collector.DOCSTATUS_Reversed);
			}
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);

		//
		// Set Cost Collector status and return
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		setDocStatus(DOCSTATUS_Completed);
		return IDocument.STATUS_Completed;
	}    // completeIt

	private void completeIt_MaterialIssueOrReceipt()
	{
		final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);

		final CostCollectorType costCollectorType = CostCollectorType.ofCode(getCostCollectorType());
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(getPP_Order_BOMLine_ID());
		final String mtrxMovementType;

		if (costCollectorType.isAnyComponentIssueOrCoProduct(orderBOMLineId))
		{
			mtrxMovementType = X_M_Transaction.MOVEMENTTYPE_WorkOrderMinus;

			final PPCostCollectorQuantities qtys = costCollectorBL.getQuantities(this);
			ppOrderBOMBL.addQty(OrderBOMLineQtyChangeRequest.builder()
					.orderBOMLineId(orderBOMLineId)
					.usageVariance(false)
					.qtyIssuedOrReceivedToAdd(qtys.getMovementQty())
					.qtyScrappedToAdd(qtys.getScrappedQty())
					.qtyRejectedToAdd(qtys.getRejectedQty())
					.date(TimeUtil.asZonedDateTime(getMovementDate()))
					.asiId(AttributeSetInstanceId.ofRepoIdOrNone(getM_AttributeSetInstance_ID()))
					.roundToScaleQuantity(ppOrderBL.getRoundingToScale(PPOrderId.ofRepoId(getPP_Order_ID())).orElse(null))
					.build());
		}
		else if (costCollectorType.isMaterialReceipt())
		{
			mtrxMovementType = X_M_Transaction.MOVEMENTTYPE_WorkOrderPlus;

			final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

			final PPCostCollectorQuantities qtys = costCollectorBL.getQuantities(this);

			ppOrderBL.addQty(OrderQtyChangeRequest.builder()
					.ppOrderId(PPOrderId.ofRepoId(getPP_Order_ID()))
					.qtyReceivedToAdd(qtys.getMovementQty())
					.qtyScrappedToAdd(qtys.getScrappedQty())
					.qtyRejectedToAdd(qtys.getRejectedQty())
					.date(TimeUtil.asZonedDateTime(this.getMovementDate()))
					.build());
		}
		else
		{
			throw new AdempiereException("Unknown issue/receipt cost collector type: " + costCollectorType);
		}

		//
		final Quantity movementQtyInStockingUOM = costCollectorBL.getMovementQtyInStockingUOM(this)
				.negateIf(X_M_Transaction.MOVEMENTTYPE_WorkOrderMinus.equals(mtrxMovementType));
		final MTransaction mtrx = new MTransaction(getCtx(),
				getAD_Org_ID(),
				mtrxMovementType,
				getM_Locator_ID(),
				getM_Product_ID(),
				getM_AttributeSetInstance_ID(),
				movementQtyInStockingUOM.toBigDecimal(),
				getMovementDate(),
				get_TrxName());
		mtrx.setPP_Cost_Collector_ID(getPP_Cost_Collector_ID());
		InterfaceWrapperHelper.save(mtrx);
	}

	private void completeIt_ActivityControl()
	{
		final IPPOrderRoutingRepository orderRoutingsRepo = Services.get(IPPOrderRoutingRepository.class);
		final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);

		if (isReversal())
		{
			// NOTE: not allowing to reverse an activity control, mainly because it's not tested.
			// Also, when implementing this, please take care of:
			// * subcontracting orders
			// * reversing to a milestone activity
			throw new LiberoException("Reversing an Activity Control is not supported");
		}

		final PPOrderRouting orderRouting = getOrderRouting();
		final PPOrderRoutingActivity activity = orderRouting.getActivityById(getActivityId());
		if (activity.isSubcontracting())
		{
			throw new AdempiereException("Sub-contracting not supported");
		}

		final PPCostCollectorQuantities qtys = costCollectorBL.getQuantities(this);
		final WFDurationUnit durationUnit = activity.getDurationUnit();

		orderRouting.reportProgress(PPOrderActivityProcessReport.builder()
				.activityId(activity.getId())
				.finishDate(getMovementDate().toInstant())
				.qtyProcessed(qtys.getMovementQty())
				.qtyScrapped(qtys.getScrappedQty())
				.qtyRejected(qtys.getRejectedQty())
				.duration(durationUnit.toDuration(getDurationReal()))
				.setupTime(durationUnit.toDuration(getSetupTimeReal()))
				.build());

		orderRoutingsRepo.save(orderRouting);

		// costEngine.createActivityControl(this);

		orderRoutingsRepo.save(orderRouting);
	}

	private void completeIt_MaterialUsageVariance()
	{
		final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);

		final PPCostCollectorQuantities qtys = costCollectorBL.getQuantities(this);

		ppOrderBOMBL.addQty(OrderBOMLineQtyChangeRequest.builder()
				.orderBOMLineId(PPOrderBOMLineId.ofRepoId(getPP_Order_BOMLine_ID()))
				.usageVariance(true)
				.qtyIssuedOrReceivedToAdd(qtys.getMovementQty())
				.qtyScrappedToAdd(qtys.getScrappedQty())
				.qtyRejectedToAdd(qtys.getRejectedQty())
				.date(TimeUtil.asZonedDateTime(getMovementDate()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(getM_AttributeSetInstance_ID()))
				.build());
	}

	private void completeIt_ResourceUsageVariance()
	{
		final IPPOrderRoutingRepository orderRoutingsRepo = Services.get(IPPOrderRoutingRepository.class);

		final PPOrderRouting orderRouting = getOrderRouting();
		final PPOrderRoutingActivityId activityId = getActivityId();
		final WFDurationUnit durationUnit = orderRouting.getActivityById(activityId).getDurationUnit();

		orderRouting.reportProgress(PPOrderActivityProcessReport.builder()
				.activityId(activityId)
				.setupTime(durationUnit.toDuration(getSetupTimeReal()))
				.duration(durationUnit.toDuration(getDurationReal()))
				.build());

		orderRoutingsRepo.save(orderRouting);

		// costEngine.createUsageVariances(this);
	}

	private void completeIt_RateVariance()
	{
		// if (isReversal)
		// {
		// costEngine.createReversals(this);
		// }
	}

	private void completeIt_MethodChangedVariance()
	{
		// if (isReversal)
		// {
		// costEngine.createReversals(this);
		// }
	}

	@Override
	public boolean voidIt()
	{
		throw new UnsupportedOperationException();
	}    // voidIt

	@Override
	public boolean closeIt()
	{
		setDocAction(DOCACTION_None);
		return true;
	}    // closeIt

	@Override
	public boolean reverseCorrectIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);

		//
		// Get the reversal of this document's parent (if any)
		// NOTE: we assume that the Reversal_ID links were set right away (see below in this method)
		final I_PP_Cost_Collector parentCostCollectorReversal;
		if (getPP_Cost_Collector_Parent_ID() > 0)
		{
			final I_PP_Cost_Collector parentCostCollector = getPP_Cost_Collector_Parent();
			parentCostCollectorReversal = parentCostCollector.getReversal();
		}
		else
		{
			parentCostCollectorReversal = null;
		}

		//
		// Create reversal cost collector
		final I_PP_Cost_Collector reversal = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class, this);
		InterfaceWrapperHelper.copyValues(this, reversal, true); // honorIsCalculated=true

		final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);
		costCollectorBL.setQuantities(reversal, costCollectorBL.getQuantities(this).negate());

		reversal.setProcessed(false);
		reversal.setProcessing(false);
		reversal.setReversal(this);
		reversal.setPP_Cost_Collector_Parent(parentCostCollectorReversal);
		reversal.setDocStatus(X_PP_Cost_Collector.DOCSTATUS_Drafted);
		reversal.setDocAction(X_PP_Cost_Collector.DOCACTION_Complete);
		Services.get(IPPCostCollectorDAO.class).save(reversal);

		//
		// Link the reversal to this cost collector
		// NOTE: we need to do this right away because the link needs to be accessible right away in case of child cost collector reversal
		setReversal(reversal);
		Services.get(IPPCostCollectorDAO.class).save(this);

		//
		// Process reversal and update its status
		docActionBL.processEx(reversal, X_PP_Cost_Collector.DOCACTION_Complete, X_PP_Cost_Collector.DOCSTATUS_Completed);
		reversal.setProcessing(false);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		Services.get(IPPCostCollectorDAO.class).save(reversal);

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);

		//
		// Update status of this document:
		// NOTE: we need to do this AFTER we are firing the AFTER events because in case some of the interceptors are failing
		// we want to preserve the original document status.
		// But nevertheless, i think the right fix shall be in org.compiere.wf.MWFActivity.performWork(Trx)
		setDocStatus(DOCSTATUS_Reversed);
		setDocAction(DOCACTION_None);

		return true;
	}

	@Override
	public boolean reverseAccrualIt()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean reActivateIt()
	{
		throw new UnsupportedOperationException();
	}

	private boolean isReversal()
	{
		final int reversalId = getReversal_ID();
		return reversalId > 0 && reversalId > getPP_Cost_Collector_ID();
	}

	@Override
	public String getSummary()
	{
		return getDescription();
	}

	@Override
	public InstantAndOrgId getDocumentDate()
	{
		return InstantAndOrgId.ofTimestamp(getMovementDate(), OrgId.ofRepoId(getAD_Org_ID()));
	}

	@Override
	public String getProcessMsg()
	{
		return null;
	}

	@Override
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}

	@Override
	public int getC_Currency_ID()
	{
		return 0; // N/A
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		return BigDecimal.ZERO; // N/A
	}

	@Override
	public File createPDF()
	{
		throw new UnsupportedOperationException(); // N/A
	}

	@Override
	public String getDocumentInfo()
	{
		final I_C_DocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	private PPOrderRouting getOrderRouting()
	{
		final IPPOrderRoutingRepository orderRoutingsRepo = Services.get(IPPOrderRoutingRepository.class);

		final PPOrderId orderId = PPOrderId.ofRepoId(getPP_Order_ID());
		return orderRoutingsRepo.getByOrderId(orderId);
	}

	private PPOrderRoutingActivityId getActivityId()
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(getPP_Order_ID());
		return PPOrderRoutingActivityId.ofRepoId(orderId, getPP_Order_Node_ID());
	}
}
