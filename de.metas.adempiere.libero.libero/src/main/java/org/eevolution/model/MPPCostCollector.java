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

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import org.compiere.model.MUOM;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_M_Product;
import org.compiere.model.X_M_Transaction;
import org.compiere.util.DB;
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
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>Original contributor of Manufacturing Standard Cost
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
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

	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	public MPPCostCollector(final Properties ctx, final int PP_Cost_Collector_ID, final String trxName)
	{
		super(ctx, PP_Cost_Collector_ID, trxName);
		if (is_new())
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
	public MPPCostCollector(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MPPCostCollector

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	private void addDescription(final String description)
	{
		final String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}	// addDescription

	@Override
	public void setProcessed(final boolean processed)
	{
		super.setProcessed(processed);
		if (is_new())
		{
			return;
		}
		final String sql = "UPDATE PP_Cost_Collector SET Processed=? WHERE PP_Cost_Collector_ID=?";
		DB.executeUpdateEx(sql, new Object[] { processed, get_ID() }, get_TrxName());
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
		// Convert/Check DocType
		setC_DocType_ID(getC_DocTypeTarget_ID());

		//
		// Operation Activity
		if (isActivityControl())
		{
			final MPPOrderNode activity = getPP_Order_Node();
			if (X_PP_Order_Node.DOCACTION_Complete.equals(activity.getDocStatus()))
			{
				throw new ActivityProcessedException(activity);
			}

			if (activity.isSubcontracting())
			{
				if (getReversal_ID() > 0)
				{
					throw new LiberoException("Reversing a subcontracting activity control is not supported");
				}

				if (X_PP_Order_Node.DOCSTATUS_InProgress.equals(activity.getDocStatus())
						&& X_PP_Cost_Collector.DOCSTATUS_InProgress.equals(getDocStatus()))
				{
					return X_PP_Order_Node.DOCSTATUS_InProgress;
				}
				else if (X_PP_Order_Node.DOCSTATUS_InProgress.equals(activity.getDocStatus())
						&& X_PP_Cost_Collector.DOCSTATUS_Drafted.equals(getDocStatus()))
				{
					throw new ActivityProcessedException(activity);
				}
				createPO(activity);
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
				final MPPOrderWorkflow order_workflow = activity.getMPPOrderWorkflow();
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
				final I_PP_Order_BOMLine orderBOMLine = getPP_Order_BOMLine();
				ppOrderBOMBL.addQtyDelivered(orderBOMLine,
						false, // isVariance
						getMovementQty());
				orderBOMLine.setQtyScrap(orderBOMLine.getQtyScrap().add(getScrappedQty()));
				orderBOMLine.setQtyReject(orderBOMLine.getQtyReject().add(getQtyReject()));
				orderBOMLine.setDateDelivered(getMovementDate());	// overwrite=last
				orderBOMLine.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
				InterfaceWrapperHelper.save(orderBOMLine);
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
			final MPPOrderNode activity = getPP_Order_Node();
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
				final String docStatus = X_PP_Order_Node.DOCSTATUS_Completed;
				// final StringBuffer msg = new StringBuffer("The quantity do not is complete for next Purchase Order : ");
				// for (MOrderLine oline : olines)
				// {
				// if(oline.getQtyDelivered().compareTo(oline.getQtyOrdered()) < 0)
				// {
				// DocStatus = MPPOrderNode.DOCSTATUS_InProgress;
				// }
				// msg.append(oline.getParent().getDocumentNo()).append(",");
				// }

				if (X_PP_Order_Node.DOCSTATUS_InProgress.equals(docStatus))
				{
					return docStatus;
				}
				setProcessed(true);
				setDocAction(X_PP_Order_Node.DOCACTION_Close);
				setDocStatus(X_PP_Order_Node.DOCSTATUS_Completed);
				activity.completeIt();
				activity.saveEx();
				// m_processMsg = Services.get(IMsgBL.class).translate(getCtx(), "PP_Order_ID")
				// + ": " + getPP_Order().getDocumentNo()
				// + " " + Services.get(IMsgBL.class).translate(getCtx(), "PP_Order_Node_ID")
				// + ": " + getPP_Order_Node().getValue();
				return docStatus;
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
			final I_PP_Order_BOMLine orderBOMLine = getPP_Order_BOMLine();
			ppOrderBOMBL.addQtyDelivered(orderBOMLine,
					true, // isVariance
					getMovementQty());
			orderBOMLine.setQtyScrap(orderBOMLine.getQtyScrap().add(getScrappedQty()));
			orderBOMLine.setQtyReject(orderBOMLine.getQtyReject().add(getQtyReject()));
			// obomline.setDateDelivered(getMovementDate()); // overwrite=last
			orderBOMLine.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
			InterfaceWrapperHelper.save(orderBOMLine);
			CostEngineFactory.getCostEngine(getAD_Client_ID()).createUsageVariances(this);
		}
		//
		// Usage Variance (resource)
		else if (isCostCollectorType(COSTCOLLECTORTYPE_UsegeVariance) && getPP_Order_Node_ID() > 0)
		{
			final MPPOrderNode activity = getPP_Order_Node();
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

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);

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
		setDocAction(DOCACTION_None);
		return true;
	}	// closeIt

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
		reversal.setMovementQty(getMovementQty().negate());
		reversal.setScrappedQty(getScrappedQty().negate());
		reversal.setQtyReject(getQtyReject().negate());
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
		setReversal(reversal);
		InterfaceWrapperHelper.save(this);

		//
		// Process reversal and update its status
		docActionBL.processEx(reversal, X_PP_Cost_Collector.DOCACTION_Complete, X_PP_Cost_Collector.DOCSTATUS_Completed);
		reversal.setProcessing(false);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		InterfaceWrapperHelper.save(reversal);

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

	@Override
	public String getSummary()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append(getDescription());
		return sb.toString();
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
		try
		{
			final File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (final Exception ex)
		{
			log.error("Could not create PDF", ex);
		}
		return null;
	}

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	private File createPDF(final File file)
	{
		throw new UnsupportedOperationException(); // N/A
		// final ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.ORDER, getPP_Order_ID());
		// if (re == null)
		// return null;
		// return re.getPDF(file);
	}

	@Override
	public String getDocumentInfo()
	{
		final I_C_DocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

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
	private long getDurationBaseSec()
	{
		return getPP_Order().getMPPOrderWorkflow().getDurationBaseSec();
	}

	/**
	 * @return Activity Control Report Start Date
	 */
	Timestamp getDateStart()
	{
		final double duration = getDurationReal().doubleValue();
		if (duration != 0)
		{
			final long durationMillis = (long)(getDurationReal().doubleValue() * getDurationBaseSec() * 1000.0);
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
	private Timestamp getDateFinish()
	{
		return getMovementDate();
	}

	/**
	 * Create Purchase Order (in case of Subcontracting)
	 *
	 * @param activity
	 */
	private void createPO(final MPPOrderNode activity)
	{
		// String msg = "";
		final Map<Integer, MOrder> orders = new HashMap<>();
		//
		final String whereClause = I_PP_Order_Node_Product.COLUMNNAME_PP_Order_Node_ID + "=?"
				+ " AND " + I_PP_Order_Node_Product.COLUMNNAME_IsSubcontracting + "=?";
		final Collection<I_PP_Order_Node_Product> subcontracts = new Query(getCtx(), I_PP_Order_Node_Product.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { activity.get_ID(), true })
				.setOnlyActiveRecords(true)
				.list(I_PP_Order_Node_Product.class);

		for (final I_PP_Order_Node_Product subcontract : subcontracts)
		{
			//
			// If Product is not Purchased or is not Service, then it is not a subcontracting candidate [SKIP]
			final I_M_Product product = MProduct.get(getCtx(), subcontract.getM_Product_ID());
			if (!product.isPurchased() || !X_M_Product.PRODUCTTYPE_Service.equals(product.getProductType()))
			{
				throw new LiberoException("The Product: " + product.getName() + " Do not is Purchase or Service Type");
			}

			//
			// Find Vendor and Product PO data
			int C_BPartner_ID = activity.getC_BPartner_ID();

			// FRESH-334: Make sure the BP_Product if of the product's org or org *
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
			final Timestamp today = new Timestamp(System.currentTimeMillis());
			final Timestamp datePromised = TimeUtil.addDays(today, partnerProduct.getDeliveryTime_Promised());
			//
			// Get/Create Purchase Order Header
			MOrder order = orders.get(C_BPartner_ID);
			if (order == null)
			{
				order = new MOrder(getCtx(), 0, get_TrxName());
				final MBPartner vendor = MBPartner.get(getCtx(), C_BPartner_ID);
				order.setAD_Org_ID(getAD_Org_ID());
				order.setBPartner(vendor);
				order.setIsSOTrx(false);
				Services.get(IOrderBL.class).setDocTypeTargetId(order);
				order.setDatePromised(datePromised);
				order.setDescription(Services.get(IMsgBL.class).translate(getCtx(), I_PP_Order.COLUMNNAME_PP_Order_ID) + ":" + getPP_Order().getDocumentNo());
				order.setDocStatus(X_C_Order.DOCSTATUS_Drafted);
				order.setDocAction(X_C_Order.DOCACTION_Complete);
				order.setAD_User_ID(getAD_User_ID());
				order.setM_Warehouse_ID(getM_Warehouse_ID());
				// order.setSalesRep_ID(getAD_User_ID());
				order.saveEx();
				addDescription(Services.get(IMsgBL.class).translate(getCtx(), "C_Order_ID") + ": " + order.getDocumentNo());
				orders.put(C_BPartner_ID, order);
				// msg = msg + Services.get(IMsgBL.class).translate(getCtx(), "C_Order_ID")
				// + " : " + order.getDocumentNo()
				// + " - "
				// + Services.get(IMsgBL.class).translate(getCtx(), "C_BPartner_ID")
				// + " : " + vendor.getName() + " , ";
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
			final MOrderLine orderLine = new MOrderLine(order);
			orderLine.setM_Product_ID(product.getM_Product_ID());
			orderLine.setDescription(activity.getDescription());
			orderLine.setM_Warehouse_ID(getM_Warehouse_ID());
			orderLine.setQty(QtyOrdered);
			// line.setPrice(m_product_po.getPricePO());
			// oline.setPriceList(m_product_po.getPriceList());
			// oline.setPP_Cost_Collector_ID(get_ID());
			orderLine.setDatePromised(datePromised);
			orderLine.saveEx();
			//
			// TODO: Mark this as processed?
			setProcessed(true);
		} // each subcontracting line

		// return msg;
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

	private boolean isIssue()
	{
		final boolean considerCoProductsAsIssue = true;  // backward compatibility: we consider by/co-products as issues (and not receipts)
		return ppCostCollectorBL.isMaterialIssue(this, considerCoProductsAsIssue);
	}

	private boolean isReceipt()
	{
		final boolean considerCoProductsAsReceipt = false; // backward compatibility: we consider only finished goods receipts (no by/co products)
		return ppCostCollectorBL.isMaterialReceipt(this, considerCoProductsAsReceipt);
	}

	private boolean isActivityControl()
	{
		return ppCostCollectorBL.isActivityControl(this);
	}

	private boolean isVariance()
	{
		return isCostCollectorType(COSTCOLLECTORTYPE_MethodChangeVariance, COSTCOLLECTORTYPE_UsegeVariance, COSTCOLLECTORTYPE_RateVariance, COSTCOLLECTORTYPE_MixVariance);
	}

	private String getMovementType()
	{
		if (isReceipt())
		{
			return X_M_Transaction.MOVEMENTTYPE_WorkOrderPlus;
		}
		else if (isIssue())
		{
			return X_M_Transaction.MOVEMENTTYPE_WorkOrder_;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Check if CostCollectorType is equal with any of provided types
	 *
	 * @param types
	 * @return
	 */
	public boolean isCostCollectorType(final String... types)
	{
		final String type = getCostCollectorType();
		for (final String t : types)
		{
			if (type.equals(t))
			{
				return true;
			}
		}
		return false;
	}
}
