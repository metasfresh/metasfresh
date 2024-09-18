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

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.report.DocumentReportService;
import de.metas.report.ReportResultData;
import de.metas.report.StandardDocumentReportType;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.ActivityControlCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.model.validator.PPOrderChangedEventFactory;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

/**
 * PP Order Model.
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
@SuppressWarnings("unused")
public class MPPOrder extends X_PP_Order implements IDocument
{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	public MPPOrder(
			final Properties ctx,
			final int PP_Order_ID,
			final String trxName)
	{
		super(ctx, PP_Order_ID, trxName);
		if (is_new())
		{
			Services.get(IPPOrderBL.class).setDefaults(this);
		}
	}

	@SuppressWarnings("unused")
	public MPPOrder(
			final Properties ctx,
			final ResultSet rs,
			final String trxName)
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

	/**
	 * Just Prepared Flag
	 */
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
		setDocAction(IDocument.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);

		if (getQtyOrdered().signum() <= 0)
		{
			throw new AdempiereException("@QtyOrdered@ <= 0");
		}

		//
		// Validate BOM Lines
		final List<I_PP_Order_BOMLine> lines = getLines();
		if (lines.isEmpty())
		{
			throw new LiberoException(LiberoException.MSG_NoLines);
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
					throw new LiberoException(TranslatableStrings.parse("@CannotChangeDocType@"
							+ "\n@PP_Order_BOMLine_ID@: " + line
							+ "\n@PP_Order_BOMLine_ID@ @M_Warehouse_ID@: " + line.getM_Warehouse_ID()
							+ "\n@PP_Order_ID@ @M_Warehouse_ID@: " + getM_Warehouse_ID()));
				}
			}
		}

		//
		// New or in Progress/Invalid
		final String docStatus = getDocStatus();
		if (IDocument.STATUS_Drafted.equals(docStatus)
				|| IDocument.STATUS_InProgress.equals(docStatus)
				|| IDocument.STATUS_Invalid.equals(docStatus)
				|| getC_DocType_ID() <= 0)
		{
			setC_DocType_ID(getC_DocTypeTarget_ID());
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
		final PPOrderDocBaseType docBaseType = PPOrderDocBaseType.optionalOfNullable(getDocBaseType()).orElse(PPOrderDocBaseType.MANUFACTURING_ORDER);
		if (docBaseType.isQualityOrder())
		{
			final String whereClause = COLUMNNAME_PP_Product_BOM_ID + "=? AND " + COLUMNNAME_AD_Workflow_ID + "=?";
			final MQMSpecification qms = new Query(getCtx(), I_QM_Specification.Table_Name, whereClause, get_TrxName())
					.setParameters(getPP_Product_BOM_ID(), getAD_Workflow_ID())
					.firstOnly(MQMSpecification.class);
			return qms == null || qms.isValid(getM_AttributeSetInstance_ID());
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
		if (IDocument.ACTION_Prepare.equals(getDocAction()))
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
		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}

		//
		// Copy cost records from M_Cost to PP_Order_Cost
		// IMPORTANT: cost will be copied when document will be accounted
		// Services.get(IPPOrderCostBL.class).createOrderCosts(this);

		//
		// Create the Activity Control
		autoReportActivities();

		//
		// Update Document Status
		// NOTE: we need to have it Processed=Yes before calling triggering AFTER_COMPLETE model validator event
		setProcessed(true);
		setDocStatus(IDocument.STATUS_Completed);
		setDocAction(IDocument.ACTION_Close);

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
		if (ppOrderBL.isSomethingProcessed(this))
		{
			throw new LiberoException("Cannot void this document because exist transactions"); // TODO: Create Message for Translation
		}

		//
		// Set QtyRequired=0 on all BOM Lines
		final IPPOrderBOMBL orderBOMsService = Services.get(IPPOrderBOMBL.class);
		for (final I_PP_Order_BOMLine line : getLines())
		{
			orderBOMsService.voidBOMLine(line);
		}

		//
		// Void all activities
		final PPOrderRouting orderRouting = getOrderRouting();
		orderRouting.voidIt();
		Services.get(IPPOrderRoutingRepository.class).save(orderRouting);

		//
		// Set QtyOrdered/QtyEntered=0 to ZERO
		final PPOrderQuantities qtysOld = orderBOMsService.getQuantities(this);
		if (qtysOld.getQtyRequiredToProduce().signum() != 0)
		{
			ppOrderBL.addDescription(this, Services.get(IMsgBL.class).parseTranslation(getCtx(), "@Voided@ @QtyOrdered@ : (" + qtysOld.getQtyRequiredToProduce() + ")"));
			setQtyEntered(BigDecimal.ZERO);

			orderBOMsService.setQuantities(this, qtysOld.voidQtys());

			final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
			ppOrderDAO.save(this);
		}

		//
		// Call Model Validator: AFTER_VOID
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);

		//
		// Update document status and return true
		setProcessed(true);
		setDocAction(IDocument.ACTION_None);
		return true;
	}

	@Override
	public boolean closeIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);

		final PPOrderChangedEventFactory eventFactory = PPOrderChangedEventFactory.newWithPPOrderBeforeChange(
				SpringContextHolder.instance.getBean(PPOrderPojoConverter.class),
				this);

		//
		// Check already closed
		DocStatus docStatus = DocStatus.ofCode(getDocStatus());
		if (docStatus.isClosed())
		{
			return true;
		}

		//
		// If DocStatus is not Completed => complete it now
		// TODO: don't know if this approach is ok, i think we shall throw an exception instead
		if (!docStatus.isCompleted())
		{
			final String newDocStatus = completeIt();
			docStatus = DocStatus.ofCode(newDocStatus);
			setDocStatus(newDocStatus);
			setDocAction(ACTION_None);
		}

		//
		// Validate BOM Lines before closing them
		final IPPOrderBOMBL ppOrderBOMLineBL = Services.get(IPPOrderBOMBL.class);
		getLines().forEach(ppOrderBOMLineBL::validateBeforeClose);

		//
		// Create usage variances
		final PPOrderDocBaseType docBaseType = PPOrderDocBaseType.optionalOfNullable(getDocBaseType()).orElse(PPOrderDocBaseType.MANUFACTURING_ORDER);
		if (!docBaseType.isRepairOrder())
		{
			createVariances();
		}

		//
		// Close BOM Lines
		getLines().forEach(ppOrderBOMLineBL::close);

		//
		// Close all the activity do not reported
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
		final PPOrderId orderId = PPOrderId.ofRepoId(getPP_Order_ID());
		ppOrderBL.closeAllActivities(orderId);

		//
		// Set QtyOrdered=QtyDelivered
		// Clear Ordered Quantities
		ppOrderBL.closeQtyOrdered(this);

		if (getDateDelivered() == null)
		{
			// making sure the column is set, even if there were no receipts
			setDateDelivered(SystemTime.asTimestamp());
		}

		//
		// Set Document status.
		// Do this before firing the AFTER_CLOSE events because the interceptors shall see the DocStatus=CLosed, in case some BLs are depending on that.
		setDocStatus(IDocument.STATUS_Closed);
		setProcessed(true);
		setDocAction(IDocument.ACTION_None);

		//
		// Call Model Validator: AFTER_CLOSE
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);

		final PPOrderChangedEvent changeEvent = eventFactory.inspectPPOrderAfterChange();

		final PostMaterialEventService materialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);
		materialEventService.postEventAfterNextCommit(changeEvent);

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
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
		if (ppOrderBL.isSomethingProcessed(this))
		{
			throw new LiberoException("Cannot re-activate this document because exist transactions"); // TODO: Create Message for Translation
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);

		//
		// BOM
		final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
		final PPOrderId orderId = PPOrderId.ofRepoId(getPP_Order_ID());
		orderBOMsRepo.markBOMLinesAsNotProcessed(orderId);

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);

		setDocAction(IDocument.ACTION_Complete);
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
	public LocalDate getDocumentDate()
	{
		return TimeUtil.asLocalDate(getDateOrdered());
	}

	@Override
	public File createPDF()
	{
		final DocumentReportService documentReportService = SpringContextHolder.instance.getBean(DocumentReportService.class);
		final ReportResultData report = documentReportService.createStandardDocumentReportData(getCtx(), StandardDocumentReportType.MANUFACTURING_ORDER, getPP_Order_ID());
		return report.writeToTemporaryFile(get_TableName() + get_ID());
	}

	@Override
	public String getDocumentInfo()
	{
		final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

		final DocTypeId docTypeId = DocTypeId.ofRepoId(getC_DocType_ID());
		final ITranslatableString docTypeName = docTypeBL.getNameById(docTypeId);
		return docTypeName.translate(Env.getADLanguageOrBaseLanguage()) + " " + getDocumentNo();
	}

	private PPOrderRouting getOrderRouting()
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(getPP_Order_ID());
		return Services.get(IPPOrderRoutingRepository.class).getByOrderId(orderId);
	}

	@Override
	public String toString()
	{
		return "MPPOrder[ID=" + get_ID()
				+ "-DocumentNo=" + getDocumentNo()
				+ ",IsSOTrx=" + isSOTrx()
				+ ",C_DocType_ID=" + getC_DocType_ID()
				+ "]";
	}

	/**
	 * Auto report the first Activity and Sub contracting if are Milestone Activity
	 */
	private void autoReportActivities()
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		final PPOrderRouting orderRouting = getOrderRouting();
		for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
		{
			if (activity.isMilestone()
					&& (activity.isSubcontracting() || orderRouting.isFirstActivity(activity)))
			{
				ppCostCollectorBL.createActivityControl(ActivityControlCreateRequest.builder()
						.order(this)
						.orderActivity(activity)
						.qtyMoved(activity.getQtyToDeliver())
						.durationSetup(Duration.ZERO)
						.duration(Duration.ZERO)
						.build());
			}
		}
	}

	private void createVariances()
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		//
		for (final I_PP_Order_BOMLine bomLine : getLines())
		{
			ppCostCollectorBL.createMaterialUsageVariance(this, bomLine);
		}

		//
		final PPOrderRouting orderRouting = getOrderRouting();
		for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
		{
			ppCostCollectorBL.createResourceUsageVariance(this, activity);
		}
	}

} // MPPOrder
