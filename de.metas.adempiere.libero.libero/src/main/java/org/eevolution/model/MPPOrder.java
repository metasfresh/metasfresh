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
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.model.X_C_DocType;
import org.compiere.print.ReportEngine;
import org.compiere.util.DB;
import org.eevolution.api.ActivityControlCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderNodeBL;

import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;

/**
 * PP Order Model.
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPOrder extends X_PP_Order implements IDocument
{
	private static final long serialVersionUID = 1L;

	public MPPOrder(final Properties ctx, final int PP_Order_ID, final String trxName)
	{
		super(ctx, PP_Order_ID, trxName);
		if (is_new())
		{
			Services.get(IPPOrderBL.class).setDefaults(this);
		}
	}

	public MPPOrder(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	private List<I_PP_Order_BOMLine> getLines()
	{
		return Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(this);
	}

	@Override
	public void setProcessed(final boolean processed)
	{
		super.setProcessed(processed);

		if (is_new())
		{
			return;
		}

		// FIXME: do we still need this?
		// Update DB:
		final String sql = "UPDATE PP_Order SET Processed=? WHERE PP_Order_ID=?";
		DB.executeUpdateEx(sql, new Object[] { processed, get_ID() }, get_TrxName());
	}

	@Override
	public boolean processIt(final String processAction)
	{
		return Services.get(IDocumentBL.class).processIt(this, processAction);
	}

	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

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

		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

		//
		// Validate BOM Lines
		final List<I_PP_Order_BOMLine> lines = getLines();
		if (lines.isEmpty())
		{
			throw new LiberoException("@NoLines@");
		}

		//
		// Cannot change Std to anything else if different warehouses
		// NOTE: shall not happen because we are updating BOM Line's warehouse/locator when order's Warehouse/Locator is changed
		if (getC_DocType_ID() > 0)
		{
			for (final I_PP_Order_BOMLine line : lines)
			{
				if (line.getM_Warehouse_ID() != getM_Warehouse_ID())
				{
					throw new LiberoException("@CannotChangeDocType@"
							+ "\n@PP_Order_BOMLine_ID@: " + line
							+ "\n@PP_Order_BOMLine_ID@ @M_Warehouse_ID@: " + line.getM_Warehouse()
							+ "\n@PP_Order_ID@ @M_Warehouse_ID@: " + getM_Warehouse());
				}
			}
		}

		//
		// New or in Progress/Invalid
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| getC_DocType_ID() <= 0)
		{
			setC_DocType_ID(getC_DocTypeTarget_ID());
		}

		final String docBaseType = MDocType.get(getCtx(), getC_DocType_ID()).getDocBaseType();
		if (X_C_DocType.DOCBASETYPE_QualityOrder.equals(docBaseType))
		{
			; // nothing
		}
		// ManufacturingOrder, MaintenanceOrder
		else
		{
			Services.get(IPPOrderBOMBL.class).reserveStock(lines);

			ppOrderBL.setForceQtyReservation(this, true);
			ppOrderBL.orderStock(this);
		}

		// From this point on, don't allow MRP to remove this document
		setMRP_AllowCleanup(false);

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);

		//
		// Update Document Status and return new status "InProgress"
		m_justPrepared = true;
		return IDocument.STATUS_InProgress;
	}

	@Override
	public boolean approveIt()
	{
		final I_C_DocType docType = Services.get(IDocTypeDAO.class).getById(getC_DocType_ID());
		if (X_C_DocType.DOCBASETYPE_QualityOrder.equals(docType.getDocBaseType()))
		{
			final String whereClause = COLUMNNAME_PP_Product_BOM_ID + "=? AND " + COLUMNNAME_AD_Workflow_ID + "=?";
			final MQMSpecification qms = new Query(getCtx(), I_QM_Specification.Table_Name, whereClause, get_TrxName())
					.setParameters(new Object[] { getPP_Product_BOM_ID(), getAD_Workflow_ID() })
					.firstOnly();
			return qms != null ? qms.isValid(getM_AttributeSetInstance_ID()) : true;
		}
		else
		{
			setIsApproved(true);
		}

		return true;
	}

	@Override
	public boolean rejectIt()
	{
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt()
	{
		// Just prepare
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return IDocument.STATUS_InProgress;
		}

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

		//
		// Mark BOM Lines as processed
		final List<I_PP_Order_BOMLine> orderBOMLines = getLines();
		for (final I_PP_Order_BOMLine orderBOMLine : orderBOMLines)
		{
			orderBOMLine.setProcessed(true);
			InterfaceWrapperHelper.save(orderBOMLine);
		}

		//
		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}

		//
		// Copy cost records from M_Cost to PP_Order_Cost
		Services.get(IPPOrderCostBL.class).createStandardCosts(this);

		//
		// Auto receipt and issue for kit
		final I_PP_Order_BOM ppOrderBOM = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOM(this);
		if (X_PP_Order_BOM.BOMTYPE_Make_To_Kit.equals(ppOrderBOM.getBOMType())
				&& X_PP_Order_BOM.BOMUSE_Manufacturing.equals(ppOrderBOM.getBOMUse()))
		{
			PPOrderMakeToKitHelper.complete(this);
			return DOCSTATUS_Closed;
		}

		//
		// Create the Activity Control
		autoReportActivities();

		//
		// Update Document Status
		// NOTE: we need to have it Processed=Yes before calling triggering AFTER_COMPLETE model validator event
		setProcessed(true);
		setDocAction(DOCACTION_Close);

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);

		//
		// Return new document status: Completed
		return IDocument.STATUS_Completed;
	} // completeIt

	@Override
	public boolean voidIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);

		//
		// Make sure there was nothing reported on this manufacturing order
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
		if (ppOrderBL.isDelivered(this))
		{
			throw new LiberoException("Cannot void this document because exist transactions"); // TODO: Create Message for Translation
		}

		//
		// Set QtyRequired=0 on all BOM Lines
		for (final I_PP_Order_BOMLine line : getLines())
		{
			final BigDecimal qtyRequiredOld = line.getQtyRequiered();
			if (qtyRequiredOld.signum() != 0)
			{
				Services.get(IPPOrderBOMBL.class).addDescription(line, Services.get(IMsgBL.class).parseTranslation(getCtx(), "@Voided@ @QtyRequiered@ : (" + qtyRequiredOld + ")"));
				line.setQtyRequiered(BigDecimal.ZERO);
				line.setProcessed(true);
				InterfaceWrapperHelper.save(line);
			}
		}

		//
		// Void all activitions
		getMPPOrderWorkflow().voidActivities();

		//
		// Set QtyOrdered/QtyEntered=0 to ZERO
		final BigDecimal qtyOrderedOld = getQtyOrdered();
		if (qtyOrderedOld.signum() != 0)
		{
			ppOrderBL.addDescription(this, Services.get(IMsgBL.class).parseTranslation(getCtx(), "@Voided@ @QtyOrdered@ : (" + qtyOrderedOld + ")"));
			ppOrderBL.setQtyOrdered(this, BigDecimal.ZERO);
			ppOrderBL.setQtyEntered(this, BigDecimal.ZERO);
			InterfaceWrapperHelper.save(this);
		}

		//
		// Clear Ordered Quantities
		ppOrderBL.orderStock(this);

		//
		// Clear BOM Lines Reservations
		Services.get(IPPOrderBOMBL.class).reserveStock(getLines());

		//
		// Call Model Validator: AFTER_VOID
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);

		//
		// Update document status and return true
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean closeIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);

		//
		// Check already closed
		String docStatus = getDocStatus();
		if (X_PP_Order.DOCSTATUS_Closed.equals(docStatus))
		{
			return true;
		}

		//
		// If DocStatus is not Completed => complete it now
		// TODO: don't know if this approach is ok, i think we shall throw an exception instead
		if (!X_PP_Order.DOCSTATUS_Completed.equals(docStatus))
		{
			docStatus = completeIt();
			setDocStatus(docStatus);
			setDocAction(ACTION_None);
		}

		// 06946: Commented out for now as a working increment.
		// if (!isDelivered())
		// {
		// throw new LiberoException("Cannot close this document because do not exist transactions"); // TODO: Create Message for Translation
		// }

		//
		// Create usage variances
		createVariances();

		//
		// Update BOM Lines and set QtyRequired=QtyDelivered
		final IPPOrderBOMBL ppOrderBOMLineBL = Services.get(IPPOrderBOMBL.class);
		for (final I_PP_Order_BOMLine line : getLines())
		{
			ppOrderBOMLineBL.close(line);
		}

		//
		// Close all the activity do not reported
		final MPPOrderWorkflow ppOrderWorkflow = getMPPOrderWorkflow();
		ppOrderWorkflow.closeActivities(
				ppOrderWorkflow.getLastNode(getAD_Client_ID()), // Current Activity to start from => last activity
				getUpdated(), // MovementDate
				false // stop on first milestone => no
		);

		//
		// Set QtyOrdered=QtyDelivered
		// Clear Ordered Quantities
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
		ppOrderBL.closeQtyOrdered(this);

		//
		// Clear BOM Lines Reservations
		// NOTE: at this point we assume QtyRequired==QtyDelivered => QtyReserved(new)=0
		Services.get(IPPOrderBOMBL.class).reserveStock(getLines());

		if (getDateDelivered() == null)
		{
			// making sure the column is set, even if there were no receipts
			setDateDelivered(SystemTime.asTimestamp());
		}

		//
		// Set Document status.
		// Do this before firing the AFTER_CLOSE events because the interceptors shall see the DocStatus=CLosed, in case some BLs are depending on that.
		setDocStatus(DOCSTATUS_Closed);
		setProcessed(true);
		setDocAction(DOCACTION_None);

		//
		// Call Model Validator: AFTER_CLOSE
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);

		return true;
	}

	@Override
	public boolean reverseCorrectIt()
	{
		return voidIt();
	}

	@Override
	public boolean reverseAccrualIt()
	{
		throw new LiberoException("@NotSupported@");
	}

	@Override
	public boolean reActivateIt()
	{
		if (Services.get(IPPOrderBL.class).isDelivered(this))
		{
			throw new LiberoException("Cannot re activate this document because exist transactions"); // TODO: Create Message for Translation
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);

		//
		// Iterate Order BOM Lines and un-process them
		for (final I_PP_Order_BOMLine orderBOMLine : Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(this))
		{
			orderBOMLine.setProcessed(false);
			InterfaceWrapperHelper.save(orderBOMLine);
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);

		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	} // reActivateIt

	@Override
	public int getDoc_User_ID()
	{
		return getPlanner_ID();
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public int getC_Currency_ID()
	{
		return 0;
	}

	@Override
	public String getProcessMsg()
	{
		return null;
	}

	@Override
	public String getSummary()
	{
		return "" + getDocumentNo() + "/" + getDatePromised();
	}

	@Override
	public File createPDF()
	{
		try
		{
			final File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Could not create PDF", e);
		}
	}

	private File createPDF(final File file)
	{
		final ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.MANUFACTURING_ORDER, getPP_Order_ID());
		if (re == null)
		{
			return null;
		}
		return re.getPDF(file);
	} // createPDF

	@Override
	public String getDocumentInfo()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	public MPPOrderWorkflow getMPPOrderWorkflow()
	{
		final I_PP_Order_Workflow ppOrderWorkflow = Services.get(IPPOrderBL.class).getPP_Order_Workflow(this);

		//
		// 07619: Preserve the transaction of this PPOrder on the workflow
		InterfaceWrapperHelper.setTrxName(ppOrderWorkflow, get_TrxName());

		return LegacyAdapters.convertToPO(ppOrderWorkflow);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MPPOrder[ID=").append(get_ID())
				.append("-DocumentNo=").append(getDocumentNo())
				.append(",IsSOTrx=").append(isSOTrx())
				.append(",C_DocType_ID=").append(getC_DocType_ID())
				.append("]");
		return sb.toString();
	}

	/**
	 * Auto report the first Activity and Sub contracting if are Milestone Activity
	 */
	private final void autoReportActivities()
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		final IPPOrderNodeBL ppOrderNodeBL = Services.get(IPPOrderNodeBL.class);

		final MPPOrderWorkflow ppOrderWorkflow = getMPPOrderWorkflow();

		for (final MPPOrderNode activity : ppOrderWorkflow.getNodes())
		{
			if (activity.isMilestone())
			{
				if (activity.isSubcontracting() || activity.getPP_Order_Node_ID() == ppOrderWorkflow.getPP_Order_Node_ID())
				{
					ppCostCollectorBL.createActivityControl(ActivityControlCreateRequest.builder()
							.node(activity)
							.qtyMoved(ppOrderNodeBL.getQtyToDeliver(activity))
							.durationSetup(0)
							.duration(BigDecimal.ZERO)
							.build());
				}
			}
		}
	}

	private void createVariances()
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		for (final I_PP_Order_BOMLine line : getLines())
		{
			ppCostCollectorBL.createUsageVariance(line);
		}

		//
		final MPPOrderWorkflow orderWorkflow = getMPPOrderWorkflow();
		if (orderWorkflow != null)
		{
			for (final MPPOrderNode node : orderWorkflow.getNodes(true))
			{
				ppCostCollectorBL.createUsageVariance(node);
			}
		}
		// orderWorkflow.m_nodes = null; // TODO: reset nodes cache
	}

} // MPPOrder
