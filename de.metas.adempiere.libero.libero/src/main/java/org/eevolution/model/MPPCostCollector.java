/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.exceptions.NoVendorForProductException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.engines.CostEngineFactory;
import org.adempiere.model.engines.IDocumentLine;
import org.adempiere.model.engines.StorageEngine;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MTransaction;
import org.compiere.model.MUOM;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.exceptions.ActivityProcessedException;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.order.IOrderBL;
import de.metas.product.IProductBL;
import de.metas.purchasing.api.IBPartnerProductDAO;

/**
 * PP Cost Collector Model
 *
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com <li>Original contributor of Manufacturing Standard Cost <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 * 
 * @author Teo Sarca, www.arhipac.ro
 * @version $Id: MPPCostCollector.java,v 1.1 2004/06/19 02:10:34 vpj-cd Exp $
 */
public class MPPCostCollector extends X_PP_Cost_Collector implements IDocument, IDocumentLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5529730708956719853L;

	// Services
	private final transient IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);

	/**
	 * Create & Complete Cost Collector
	 * 
	 * @param order
	 * @param productId
	 * @param locatorId
	 * @param attributeSetInstanceId
	 * @param resourceId
	 * @param ppOrderBOMLineId
	 * @param ppOrderNodeId
	 * @param docTypeId
	 * @param costCollectorType
	 * @param movementdate
	 * @param qty
	 * @param qtyScrap
	 * @param qtyReject
	 * @param durationSetup
	 * @param duration
	 * @param trxName
	 * @return completed cost collector
	 */
	public static I_PP_Cost_Collector createCollector(
			final I_PP_Order order,
			final int productId,
			final int locatorId,
			final int attributeSetInstanceId,
			final int resourceId,
			final int ppOrderBOMLineId,
			final int ppOrderNodeId,
			final int docTypeId,
			final String costCollectorType,
			final Timestamp movementdate,
			final BigDecimal qty,
			final BigDecimal qtyScrap,
			final BigDecimal qtyReject,
			final int durationSetup,
			final BigDecimal duration
			)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();
		
		final int docTypeId_ToUse;
		if (docTypeId > 0)
		{
			docTypeId_ToUse = docTypeId;
		}
		else
		{
			docTypeId_ToUse = MDocType.getDocType(MDocType.DOCBASETYPE_ManufacturingCostCollector);
		}

		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class);
		setPP_Order(cc, order);
		cc.setPP_Order_BOMLine_ID(ppOrderBOMLineId);
		cc.setPP_Order_Node_ID(ppOrderNodeId);
		cc.setC_DocType_ID(docTypeId_ToUse);
		cc.setC_DocTypeTarget_ID(docTypeId_ToUse);
		cc.setCostCollectorType(costCollectorType);
		cc.setDocAction(MPPCostCollector.DOCACTION_Complete);
		cc.setDocStatus(MPPCostCollector.DOCSTATUS_Drafted);
		cc.setIsActive(true);
		cc.setM_Locator_ID(locatorId);
		cc.setM_AttributeSetInstance_ID(attributeSetInstanceId);
		cc.setS_Resource_ID(resourceId);
		cc.setMovementDate(movementdate);
		cc.setDateAcct(movementdate);
		cc.setMovementQty(qty);
		cc.setScrappedQty(qtyScrap);
		cc.setQtyReject(qtyReject);
		cc.setSetupTimeReal(new BigDecimal(durationSetup));
		cc.setDurationReal(duration);
		cc.setPosted(false);
		cc.setProcessed(false);
		cc.setProcessing(false);
		cc.setUser1_ID(order.getUser1_ID());
		cc.setUser2_ID(order.getUser2_ID());
		cc.setM_Product_ID(productId);
		if (ppOrderNodeId > 0)
		{
			final I_PP_Order_Node ppOrderNode = InterfaceWrapperHelper.load(ppOrderNodeId, I_PP_Order_Node.class);
			cc.setIsSubcontracting(ppOrderNode.isSubcontracting());
		}
		// If this is an material issue, we should use BOM Line's UOM
		if (ppOrderBOMLineId > 0)
		{
			cc.setC_UOM(null); // we set the BOM Line UOM on beforeSave
		}

		InterfaceWrapperHelper.save(cc);

		//
		// Process the Cost Collector
		Services.get(IDocumentBL.class).processEx(cc,
				X_PP_Cost_Collector.DOCACTION_Complete,
				null // expectedDocStatus
				);

		return cc;
	}

	public static void setPP_Order(final I_PP_Cost_Collector cc, final I_PP_Order order)
	{
		final I_PP_Order_Workflow ppOrderWorkflow = Services.get(IPPOrderBL.class).getPP_Order_Workflow(order);

		cc.setPP_Order(order);
		cc.setPP_Order_Workflow(ppOrderWorkflow);
		cc.setAD_Org_ID(order.getAD_Org_ID());
		cc.setM_Warehouse_ID(order.getM_Warehouse_ID());
		cc.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		cc.setC_Activity_ID(order.getC_Activity_ID());
		cc.setC_Campaign_ID(order.getC_Campaign_ID());
		cc.setC_Project_ID(order.getC_Project_ID());
		cc.setDescription(order.getDescription());
		cc.setS_Resource_ID(order.getS_Resource_ID());
		cc.setM_Product_ID(order.getM_Product_ID());
		cc.setC_UOM_ID(order.getC_UOM_ID());
		cc.setM_AttributeSetInstance_ID(order.getM_AttributeSetInstance_ID());
		cc.setMovementQty(order.getQtyOrdered());
	}

	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param PP_Cost_Collector id
	 */
	public MPPCostCollector(Properties ctx, int PP_Cost_Collector_ID, String trxName)
	{
		super(ctx, PP_Cost_Collector_ID, trxName);
		if (PP_Cost_Collector_ID == 0)
		{
			// setC_DocType_ID(0);
			setDocStatus(DOCSTATUS_Drafted);	// DR
			setDocAction(DOCACTION_Complete);	// CO
			setMovementDate(new Timestamp(System.currentTimeMillis()));	// @#Date@
			setIsActive(true);
			setPosted(false);
			setProcessing(false);
			setProcessed(false);
		}
	}	// MPPCostCollector

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 */
	public MPPCostCollector(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MPPCostCollector

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	// addDescription

	public void setC_DocTypeTarget_ID(String docBaseType)
	{
		MDocType[] doc = MDocType.getOfDocBaseType(getCtx(), docBaseType);
		if (doc == null)
		{
			throw new DocTypeNotFoundException(docBaseType, "");
		}
		else
		{
			setC_DocTypeTarget_ID(doc[0].get_ID());
		}
	}

	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		final String sql = "UPDATE PP_Cost_Collector SET Processed=? WHERE PP_Cost_Collector_ID=?";
		int noLine = DB.executeUpdateEx(sql, new Object[] { processed, get_ID() }, get_TrxName());
		log.debug("setProcessed - " + processed + " - Lines=" + noLine);
	}	// setProcessed

	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction);
	}

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	@Override
	public boolean unlockIt()
	{
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}	// unlockIt

	@Override
	public boolean invalidateIt()
	{
		log.info("invalidateIt - " + toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	// invalidateIt

	@Override
	public String prepareIt()
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocTypeTarget_ID(), getAD_Org_ID());
		// Convert/Check DocType
		setC_DocType_ID(getC_DocTypeTarget_ID());

		//
		// Operation Activity
		if (isActivityControl())
		{
			MPPOrderNode activity = getPP_Order_Node();
			if (MPPOrderNode.DOCACTION_Complete.equals(activity.getDocStatus()))
			{
				throw new ActivityProcessedException(activity);
			}

			if (activity.isSubcontracting())
			{
				if (getReversal_ID() > 0)
				{
					throw new LiberoException("Reversing a subcontracting activity control is not supported");
				}

				if (MPPOrderNode.DOCSTATUS_InProgress.equals(activity.getDocStatus())
						&& MPPCostCollector.DOCSTATUS_InProgress.equals(getDocStatus()))
				{
					return MPPOrderNode.DOCSTATUS_InProgress;
				}
				else if (MPPOrderNode.DOCSTATUS_InProgress.equals(activity.getDocStatus())
						&& MPPCostCollector.DOCSTATUS_Drafted.equals(getDocStatus()))
				{
					throw new ActivityProcessedException(activity);
				}
				m_processMsg = createPO(activity);
				m_justPrepared = false;
				activity.setInProgress(this);
				activity.saveEx();
				return DOCSTATUS_InProgress;
			}

			activity.setInProgress(this);
			activity.setQtyDelivered(activity.getQtyDelivered().add(getMovementQty()));
			activity.setQtyScrap(activity.getQtyScrap().add(getScrappedQty()));
			activity.setQtyReject(activity.getQtyReject().add(getQtyReject()));
			activity.setDurationReal(activity.getDurationReal() + getDurationReal().intValueExact());
			activity.setSetupTimeReal(activity.getSetupTimeReal() + getSetupTimeReal().intValueExact());
			activity.saveEx();

			// report all activity previews to milestone activity
			if (activity.isMilestone())
			{
				MPPOrderWorkflow order_workflow = activity.getMPPOrderWorkflow();
				order_workflow.closeActivities(activity, getMovementDate(), true);
			}
		}
		// Issue
		else if (isIssue())
		{
			final I_M_Product product = getM_Product();
			if (getM_AttributeSetInstance_ID() <= 0 && Services.get(IProductBL.class).isASIMandatory(product, false))
			{
				throw new LiberoException("@M_AttributeSet_ID@ @IsMandatory@ @M_Product_ID@=" + product.getValue());
			}
		}
		// Receipt
		else if (isReceipt())
		{
			final I_M_Product product = getM_Product();
			if (getM_AttributeSetInstance_ID() <= 0 && Services.get(IProductBL.class).isASIMandatory(product, true))
			{
				throw new LiberoException("@M_AttributeSet_ID@ @IsMandatory@ @M_Product_ID@=" + product.getValue());
			}
		}

		m_justPrepared = true;
		setDocAction(DOCACTION_Complete);

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		return IDocument.STATUS_InProgress;
	}	// prepareIt

	@Override
	public boolean approveIt()
	{
		log.info("approveIt - " + toString());
		// setIsApproved(true);
		return true;
	}	// approveIt

	@Override
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		// setIsApproved(false);
		return true;
	}	// rejectIt

	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
				return status;
		}

		//
		// Fire: BEFORE_COMPLETE
		{
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
			if (m_processMsg != null)
				return IDocument.STATUS_Invalid;
		}

		final boolean isReversal = getReversal_ID() > 0;

		//
		// Material Issue (component issue, method change variance, mix variance)
		// Material Receipt
		if (isIssue() || isReceipt())
		{
			// Stock Movement
			final I_M_Product product = getM_Product();
			if (product != null && Services.get(IProductBL.class).isStocked(product) && !isVariance())
			{
				StorageEngine.createTrasaction(
						this,
						getMovementType(),
						getMovementDate(),
						getMovementQty(),
						isReversal,											// IsReversal=false
						getM_Warehouse_ID(),
						getPP_Order().getM_AttributeSetInstance_ID(),	// Reservation ASI
						getPP_Order().getM_Warehouse_ID(),				// Reservation Warehouse
						false											// IsSOTrx=false
						);
			}	// stock movement

			if (isIssue())
			{
				// Update PP Order Line
				final I_PP_Order_BOMLine obomline = getPP_Order_BOMLine();
				ppOrderBOMBL.addQtyDelivered(obomline,
						false, // isVariance
						getMovementQty());
				obomline.setQtyScrap(obomline.getQtyScrap().add(getScrappedQty()));
				obomline.setQtyReject(obomline.getQtyReject().add(getQtyReject()));
				obomline.setDateDelivered(getMovementDate());	// overwrite=last
				obomline.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
				log.debug("OrderLine - Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
				InterfaceWrapperHelper.save(obomline);
				log.debug("OrderLine -> Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
			}
			if (isReceipt())
			{
				// Update PP Order Qtys
				final I_PP_Order order = getPP_Order();
				order.setQtyDelivered(order.getQtyDelivered().add(getMovementQty()));
				order.setQtyScrap(order.getQtyScrap().add(getScrappedQty()));
				order.setQtyReject(order.getQtyReject().add(getQtyReject()));
				order.setQtyReserved(order.getQtyReserved().subtract(getMovementQty()));

				//
				// Update PP Order Dates
				order.setDateDelivered(getMovementDate()); // overwrite=last
				if (order.getDateStart() == null)
				{
					order.setDateStart(getDateStart());
				}

				final BigDecimal qtyOpen = Services.get(IPPOrderBL.class).getQtyOpen(order);
				if (qtyOpen.signum() <= 0)
				{
					order.setDateFinish(getDateFinish());
				}
				InterfaceWrapperHelper.save(order);
			}
		}
		//
		// Activity Control
		else if (isActivityControl())
		{
			if (isReversal)
			{
				// NOTE: not allowing to reverse an activity control, mainly because it's not tested.
				// Also, when implementing this, please take care of:
				// * subcontracting orders
				// * reversing to a milestone activity
				throw new LiberoException("Reversing an Activity Control is not supported");
			}
			MPPOrderNode activity = getPP_Order_Node();
			if (activity.isProcessed())
			{
				throw new ActivityProcessedException(activity);
			}

			if (isSubcontracting())
			{
				// String whereClause = MOrderLine.COLUMNNAME_PP_Cost_Collector_ID+"=?";
				// Collection<MOrderLine> olines = new Query(getCtx(), MOrderLine.Table_Name, whereClause, get_TrxName())
				// .setParameters(new Object[]{get_ID()})
				// .list();
				String DocStatus = MPPOrderNode.DOCSTATUS_Completed;
				StringBuffer msg = new StringBuffer("The quantity do not is complete for next Purchase Order : ");
				// for (MOrderLine oline : olines)
				// {
				// if(oline.getQtyDelivered().compareTo(oline.getQtyOrdered()) < 0)
				// {
				// DocStatus = MPPOrderNode.DOCSTATUS_InProgress;
				// }
				// msg.append(oline.getParent().getDocumentNo()).append(",");
				// }

				if (MPPOrderNode.DOCSTATUS_InProgress.equals(DocStatus))
				{
					m_processMsg = msg.toString();
					return DocStatus;
				}
				setProcessed(true);
				setDocAction(MPPOrderNode.DOCACTION_Close);
				setDocStatus(MPPOrderNode.DOCSTATUS_Completed);
				activity.completeIt();
				activity.saveEx();
				m_processMsg = Services.get(IMsgBL.class).translate(getCtx(), "PP_Order_ID")
						+ ": " + getPP_Order().getDocumentNo()
						+ " " + Services.get(IMsgBL.class).translate(getCtx(), "PP_Order_Node_ID")
						+ ": " + getPP_Order_Node().getValue();
				return DocStatus;
			}
			else
			{
				CostEngineFactory.getCostEngine(getAD_Client_ID()).createActivityControl(this);
				if (activity.getQtyDelivered().compareTo(activity.getQtyRequiered()) >= 0)
				{
					activity.closeIt();
					activity.saveEx();
				}
			}
		}
		//
		// Usage Variance (material)
		else if (isCostCollectorType(COSTCOLLECTORTYPE_UsegeVariance) && getPP_Order_BOMLine_ID() > 0)
		{
			final I_PP_Order_BOMLine obomline = getPP_Order_BOMLine();
			ppOrderBOMBL.addQtyDelivered(obomline,
					true, // isVariance
					getMovementQty());
			obomline.setQtyScrap(obomline.getQtyScrap().add(getScrappedQty()));
			obomline.setQtyReject(obomline.getQtyReject().add(getQtyReject()));
			// obomline.setDateDelivered(getMovementDate()); // overwrite=last
			obomline.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
			log.debug("OrderLine - Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
			InterfaceWrapperHelper.save(obomline);
			log.debug("OrderLine -> Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
			CostEngineFactory.getCostEngine(getAD_Client_ID()).createUsageVariances(this);
		}
		//
		// Usage Variance (resource)
		else if (isCostCollectorType(COSTCOLLECTORTYPE_UsegeVariance) && getPP_Order_Node_ID() > 0)
		{
			MPPOrderNode activity = getPP_Order_Node();
			activity.setDurationReal(activity.getDurationReal() + getDurationReal().intValueExact());
			activity.setSetupTimeReal(activity.getSetupTimeReal() + getSetupTimeReal().intValueExact());
			activity.saveEx();
			CostEngineFactory.getCostEngine(getAD_Client_ID()).createUsageVariances(this);
		}
		else if (isCostCollectorType(COSTCOLLECTORTYPE_RateVariance))
		{
			if (isReversal)
			{
				CostEngineFactory.getCostEngine(getAD_Client_ID()).createReversals(this);
			}
		}
		else if (isCostCollectorType(COSTCOLLECTORTYPE_MethodChangeVariance))
		{
			if (isReversal)
			{
				CostEngineFactory.getCostEngine(getAD_Client_ID()).createReversals(this);
			}
		}
		else
		{
			; // nothing
		}
		//

		//
		// Create Rate and Method Variances
		if (!isReversal)
		{
			CostEngineFactory.getCostEngine(getAD_Client_ID()).createRateVariances(this);
			CostEngineFactory.getCostEngine(getAD_Client_ID()).createMethodVariances(this);
		}
		// Reverse Rate and Method Variances
		else
		{
			// Get the initial cost collector (which is reversed by this one)
			final I_PP_Cost_Collector ccOriginal = getReversal();

			// Get it's child cost collectors and reverse them one by one
			final List<I_PP_Cost_Collector> childCostCollectors = Services.get(IPPCostCollectorDAO.class).retrieveForParent(ccOriginal);
			for (final I_PP_Cost_Collector childCostCollector : childCostCollectors)
			{
				docActionBL.processEx(childCostCollector, X_PP_Cost_Collector.DOCACTION_Reverse_Correct, X_PP_Cost_Collector.DOCSTATUS_Reversed);
			}
		}

		//
		// Fire: AFTER_COMPLETE
		{
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
			if (m_processMsg != null)
				return IDocument.STATUS_Invalid;
		}

		//
		// Set Cost Collector status and return
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		setDocStatus(DOCSTATUS_Completed);
		return IDocument.STATUS_Completed;
	}	// completeIt

	@Override
	public boolean voidIt()
	{
		throw new UnsupportedOperationException();
	}	// voidIt

	@Override
	public boolean closeIt()
	{
		log.info("closeIt - " + toString());
		setDocAction(DOCACTION_None);
		return true;
	}	// closeIt

	@Override
	public boolean reverseCorrectIt()
	{
		// Fire: BEFORE_REVERSECORRECT
		{
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
			if (m_processMsg != null)
				return false;
		}

		//
		// Get the reversal of this document's parent (if any)
		// NOTE: we assume that the Reversal_ID links were set right away (see below in this method)
		final I_PP_Cost_Collector parentCostCollectorReversal;
		if (this.getPP_Cost_Collector_Parent_ID() > 0)
		{
			final I_PP_Cost_Collector parentCostCollector = this.getPP_Cost_Collector_Parent();
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
		reversal.setMovementQty(this.getMovementQty().negate());
		reversal.setScrappedQty(this.getScrappedQty().negate());
		reversal.setQtyReject(this.getQtyReject().negate());
		reversal.setProcessed(false);
		reversal.setProcessing(false);
		reversal.setReversal(this);
		reversal.setPP_Cost_Collector_Parent(parentCostCollectorReversal);
		reversal.setDocStatus(X_PP_Cost_Collector.DOCSTATUS_Drafted);
		reversal.setDocAction(X_PP_Cost_Collector.DOCACTION_Complete);
		InterfaceWrapperHelper.save(reversal);

		//
		// Link the reversal to this cost collector
		// NOTE: we need to do this right away because the link needs to be accessible right away in case of child cost collector reversal
		this.setReversal(reversal);
		InterfaceWrapperHelper.save(this);

		//
		// Process reversal and update its status
		docActionBL.processEx(reversal, X_PP_Cost_Collector.DOCACTION_Complete, X_PP_Cost_Collector.DOCSTATUS_Completed);
		reversal.setProcessing(false);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		InterfaceWrapperHelper.save(reversal);

		// Fire: AFTER_REVERSECORRECT
		{
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
			if (m_processMsg != null)
				return false;
		}
		
		//
		// Update status of this document:
		// NOTE: we need to do this AFTER we are firing the AFTER events because in case some of the interceptors are failing
		// we want to preserve the original document status.
		// But nevertheless, i think the right fix shall be in org.compiere.wf.MWFActivity.performWork(Trx)
		this.setDocStatus(DOCSTATUS_Reversed);
		this.setDocAction(DOCACTION_None);

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

	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDescription());
		return sb.toString();
	}

	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
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
		return Env.ZERO; // N/A
	}

	@Override
	public File createPDF()
	{
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	// getPDF

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	private File createPDF(File file)
	{
		throw new UnsupportedOperationException(); // N/A
		// final ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.ORDER, getPP_Order_ID());
		// if (re == null)
		// return null;
		// return re.getPDF(file);
	}	// createPDF

	@Override
	public String getDocumentInfo()
	{
		final I_C_DocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	// getDocumentInfo

	@Override
	public MPPOrderNode getPP_Order_Node()
	{
		final I_PP_Order_Node node = super.getPP_Order_Node();
		return LegacyAdapters.convertToPO(node);
	}

	@Override
	public MPPOrder getPP_Order()
	{
		final I_PP_Order ppOrder = super.getPP_Order();
		return LegacyAdapters.convertToPO(ppOrder);
	}

	/**
	 * Get Duration Base in Seconds
	 * 
	 * @return duration unit in seconds
	 * @see MPPOrderWorkflow#getDurationBaseSec()
	 */
	public long getDurationBaseSec()
	{
		return getPP_Order().getMPPOrderWorkflow().getDurationBaseSec();
	}

	/**
	 * @return Activity Control Report Start Date
	 */
	public Timestamp getDateStart()
	{
		double duration = getDurationReal().doubleValue();
		if (duration != 0)
		{
			long durationMillis = (long)(getDurationReal().doubleValue() * getDurationBaseSec() * 1000.0);
			return new Timestamp(getMovementDate().getTime() - durationMillis);
		}
		else
		{
			return getMovementDate();
		}
	}

	/**
	 * @return Activity Control Report End Date
	 */
	public Timestamp getDateFinish()
	{
		return getMovementDate();
	}

	/**
	 * Create Purchase Order (in case of Subcontracting)
	 * 
	 * @param activity
	 */
	private String createPO(MPPOrderNode activity)
	{
		String msg = "";
		final Map<Integer, MOrder> orders = new HashMap<>();
		//
		String whereClause = I_PP_Order_Node_Product.COLUMNNAME_PP_Order_Node_ID + "=?"
				+ " AND " + I_PP_Order_Node_Product.COLUMNNAME_IsSubcontracting + "=?";
		Collection<I_PP_Order_Node_Product> subcontracts = new Query(getCtx(), I_PP_Order_Node_Product.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { activity.get_ID(), true })
				.setOnlyActiveRecords(true)
				.list(I_PP_Order_Node_Product.class);

		for (I_PP_Order_Node_Product subcontract : subcontracts)
		{
			//
			// If Product is not Purchased or is not Service, then it is not a subcontracting candidate [SKIP]
			MProduct product = MProduct.get(getCtx(), subcontract.getM_Product_ID());
			if (!product.isPurchased() || !MProduct.PRODUCTTYPE_Service.equals(product.getProductType()))
				throw new LiberoException("The Product: " + product.getName() + " Do not is Purchase or Service Type");

			//
			// Find Vendor and Product PO data
			int C_BPartner_ID = activity.getC_BPartner_ID();

			
			//FRESH-334: Make sure the BP_Product if of the product's org or org * 
			final int orgId = product.getAD_Org_ID();
			final int productId = product.getM_Product_ID();
			
			final List<I_C_BPartner_Product> partnerProducts = Services.get(IBPartnerProductDAO.class).retrieveBPartnerForProduct(getCtx(), 0, productId, orgId);

			I_C_BPartner_Product partnerProduct = null;
			for (final I_C_BPartner_Product bpp : partnerProducts)
			{
				if (C_BPartner_ID == bpp.getC_BPartner_ID())
				{
					C_BPartner_ID = bpp.getC_BPartner_ID();
					partnerProduct = bpp;
					break;
				}
				if (bpp.isCurrentVendor() && bpp.getC_BPartner_ID() != 0)
				{
					C_BPartner_ID = bpp.getC_BPartner_ID();
					partnerProduct = bpp;
					break;
				}
			}
			// task cg : 05952 : end
			if (C_BPartner_ID <= 0 || partnerProduct == null)
			{
				throw new NoVendorForProductException(product.getName());
			}
			//
			// Calculate Lead Time
			Timestamp today = new Timestamp(System.currentTimeMillis());
			Timestamp datePromised = TimeUtil.addDays(today, partnerProduct.getDeliveryTime_Promised());
			//
			// Get/Create Purchase Order Header
			MOrder order = orders.get(C_BPartner_ID);
			if (order == null)
			{
				order = new MOrder(getCtx(), 0, get_TrxName());
				MBPartner vendor = MBPartner.get(getCtx(), C_BPartner_ID);
				order.setAD_Org_ID(getAD_Org_ID());
				order.setBPartner(vendor);
				order.setIsSOTrx(false);
				Services.get(IOrderBL.class).setDocTypeTargetId(order);
				order.setDatePromised(datePromised);
				order.setDescription(Services.get(IMsgBL.class).translate(getCtx(), MPPOrder.COLUMNNAME_PP_Order_ID) + ":" + getPP_Order().getDocumentNo());
				order.setDocStatus(MOrder.DOCSTATUS_Drafted);
				order.setDocAction(MOrder.DOCACTION_Complete);
				order.setAD_User_ID(getAD_User_ID());
				order.setM_Warehouse_ID(getM_Warehouse_ID());
				// order.setSalesRep_ID(getAD_User_ID());
				order.saveEx();
				addDescription(Services.get(IMsgBL.class).translate(getCtx(), "C_Order_ID") + ": " + order.getDocumentNo());
				orders.put(C_BPartner_ID, order);
				msg = msg + Services.get(IMsgBL.class).translate(getCtx(), "C_Order_ID")
						+ " : " + order.getDocumentNo()
						+ " - "
						+ Services.get(IMsgBL.class).translate(getCtx(), "C_BPartner_ID")
						+ " : " + vendor.getName() + " , ";
			}
			//
			// Create Order Line:
			BigDecimal QtyOrdered = getMovementQty().multiply(subcontract.getQty());
			// Check Order Min
			if (partnerProduct.getOrder_Min().signum() > 0)
			{
				QtyOrdered = QtyOrdered.max(partnerProduct.getOrder_Min());
			}
			// Check Order Pack
			if (partnerProduct.getOrder_Pack().signum() > 0 && QtyOrdered.signum() > 0)
			{
				QtyOrdered = partnerProduct.getOrder_Pack().multiply(QtyOrdered.divide(partnerProduct.getOrder_Pack(), 0, BigDecimal.ROUND_UP));
			}
			MOrderLine oline = new MOrderLine(order);
			oline.setM_Product_ID(product.getM_Product_ID());
			oline.setDescription(activity.getDescription());
			oline.setM_Warehouse_ID(getM_Warehouse_ID());
			oline.setQty(QtyOrdered);
			// line.setPrice(m_product_po.getPricePO());
			// oline.setPriceList(m_product_po.getPriceList());
			// oline.setPP_Cost_Collector_ID(get_ID());
			oline.setDatePromised(datePromised);
			oline.saveEx();
			//
			// TODO: Mark this as processed?
			setProcessed(true);
		} // each subcontracting line
		return msg;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return MProduct.get(getCtx(), getM_Product_ID());
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return MUOM.get(getCtx(), getC_UOM_ID());
	}

	public boolean isIssue()
	{
		final boolean considerCoProductsAsIssue = true;  // backward compatibility: we consider by/co-products as issues (and not receipts)
		return ppCostCollectorBL.isMaterialIssue(this, considerCoProductsAsIssue);
	}

	public boolean isReceipt()
	{
		final boolean considerCoProductsAsReceipt = false; // backward compatibility: we consider only finished goods receipts (no by/co products)
		return ppCostCollectorBL.isMaterialReceipt(this, considerCoProductsAsReceipt);
	}

	public boolean isActivityControl()
	{
		return ppCostCollectorBL.isActivityControl(this);
	}

	public boolean isVariance()
	{
		return isCostCollectorType(COSTCOLLECTORTYPE_MethodChangeVariance
				, COSTCOLLECTORTYPE_UsegeVariance
				, COSTCOLLECTORTYPE_RateVariance
				, COSTCOLLECTORTYPE_MixVariance);
	}

	public String getMovementType()
	{
		if (isReceipt())
			return MTransaction.MOVEMENTTYPE_WorkOrderPlus;
		else if (isIssue())
			return MTransaction.MOVEMENTTYPE_WorkOrder_;
		else
			return null;
	}

	/**
	 * Check if CostCollectorType is equal with any of provided types
	 * 
	 * @param types
	 * @return
	 */
	public boolean isCostCollectorType(String... types)
	{
		String type = getCostCollectorType();
		for (String t : types)
		{
			if (type.equals(t))
				return true;
		}
		return false;
	}
}
