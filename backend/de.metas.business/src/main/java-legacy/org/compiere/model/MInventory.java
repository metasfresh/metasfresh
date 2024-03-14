/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.IMsgBL;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Physical Inventory Model
 *
 * @author Jorg Janke
 * @version $Id: MInventory.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 1948157 ] Is necessary the reference for document reverse
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 */
public class MInventory extends X_M_Inventory implements IDocument
{
	private static final long serialVersionUID = 910998472569265447L;

	private boolean m_justPrepared;

	public MInventory(final Properties ctx, final int M_Inventory_ID, final String trxName)
	{
		super(ctx, M_Inventory_ID, trxName);
		if (is_new())
		{
			// setName (null);
			// setM_Warehouse_ID (0); // FK
			setMovementDate(SystemTime.asTimestamp());
			setDocAction(DOCACTION_Complete);	// CO
			setDocStatus(DOCSTATUS_Drafted);	// DR
			setIsApproved(false);
			setPosted(false);
			setProcessed(false);
		}
	}

	@SuppressWarnings("unused")
	public MInventory(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MInventory(final I_M_Warehouse wh)
	{
		this(Env.getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
		setClientOrgFromModel(wh);
		setM_Warehouse_ID(wh.getM_Warehouse_ID());
	}

	public List<I_M_InventoryLine> getLines()
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(getM_Inventory_ID());
		return Services.get(IInventoryDAO.class).retrieveLinesForInventoryId(inventoryId);
	}

	private boolean hasLines()
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(getM_Inventory_ID());
		return Services.get(IInventoryDAO.class).hasLines(inventoryId);
	}

	/**
	 * Overwrite Client/Org - from Import.
	 *
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 */
	@Override
	public void setClientOrg(final int AD_Client_ID, final int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	// setClientOrg

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MInventory[");
		sb.append(get_ID())
				.append("-").append(getDocumentNo())
				.append(",M_Warehouse_ID=").append(getM_Warehouse_ID())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	// getDocumentInfo

	@Override
	public File createPDF()
	{
		return null;
	}

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getC_DocType_ID() <= 0)
		{
			final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
			final DocTypeQuery query = DocTypeQuery.builder()
					.docBaseType(DocBaseType.MaterialPhysicalInventory)
					.adClientId(getAD_Client_ID())
					.adOrgId(getAD_Org_ID())
					.build();
			final int docTypeId = docTypeDAO.getDocTypeId(query).getRepoId();
			setC_DocType_ID(docTypeId);
		}
		return true;
	}

	/**
	 * Set Processed. Propagate to Lines.
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(final boolean processed)
	{
		super.setProcessed(processed);
		if (!is_new())
		{
			final InventoryId inventoryId = InventoryId.ofRepoId(getM_Inventory_ID());
			Services.get(IInventoryDAO.class).setInventoryLinesProcessed(inventoryId, processed);
		}
	}

	@Override
	public boolean processIt(final String processAction)
	{
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
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

	/**
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);

		// Std Period open?
		MPeriod.testPeriodOpen(getCtx(), getMovementDate(), DocBaseType.MaterialPhysicalInventory, getAD_Org_ID());

		if (!hasLines())
		{
			throw AdempiereException.noLines();
		}

		// TODO: Add up Amounts
		// setApprovalAmt();

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);

		setDocAction(DOCACTION_Complete);
		m_justPrepared = true;

		return IDocument.STATUS_InProgress;
	}

	@Override
	public boolean approveIt()
	{
		setIsApproved(true);
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
		// Re-Check
		if (!m_justPrepared)
		{
			final String docStatus = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(docStatus))
			{
				return docStatus;
			}
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}

		// Process each line
		getLines().forEach(this::completeInventoryLine);

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return IDocument.STATUS_Completed;
	}

	private void completeInventoryLine(final I_M_InventoryLine line)
	{
		if (!line.isActive())
		{
			return;
		}

		final IProductBL productBL = Services.get(IProductBL.class);
		if (!productBL.isStocked(ProductId.ofRepoIdOrNull(line.getM_Product_ID())))
		{
			return;
		}

		// Get Quantity to Inventory Inernal Use
		final Quantity qtyDiff = Services.get(IInventoryBL.class).getMovementQtyInStockingUOM(line);
		final String movementType = qtyDiff.signum() > 0 ? X_M_Transaction.MOVEMENTTYPE_InventoryIn : X_M_Transaction.MOVEMENTTYPE_InventoryOut;

		// Transaction
		final MTransaction mtrx = new MTransaction(getCtx(),
				line.getAD_Org_ID(),
				movementType,
				line.getM_Locator_ID(),
				line.getM_Product_ID(),
				line.getM_AttributeSetInstance_ID(),
				qtyDiff.toBigDecimal(),
				getMovementDate(),
				get_TrxName());
		mtrx.setM_InventoryLine_ID(line.getM_InventoryLine_ID());
		InterfaceWrapperHelper.save(mtrx);
	}

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		final I_C_DocType docType = MDocType.get(getCtx(), getC_DocType_ID());
		if (docType.isOverwriteDateOnComplete())
		{
			setMovementDate(de.metas.common.util.time.SystemTime.asTimestamp());
		}
		if (docType.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setDocumentModel(this)
					.setFailOnError(false)
					.build();
			if (value != null && value != IDocumentNoBuilder.NO_DOCUMENTNO)
			{
				setDocumentNo(value);
			}
		}
	}

	@Override
	public boolean voidIt()
	{
		// Before Void
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);

		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			throw new AdempiereException("Document Closed: " + getDocStatus());
		}

		// Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus()))
		{
			getLines().forEach(this::voidInventoryLine);
		}
		else
		{
			return reverseCorrectIt();
		}

		// After Void
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}

	private void voidInventoryLine(final I_M_InventoryLine line)
	{
		final BigDecimal oldCount = line.getQtyCount();
		final BigDecimal oldInternal = line.getQtyInternalUse();
		if (oldCount.compareTo(line.getQtyBook()) != 0
				|| oldInternal.signum() != 0)
		{
			line.setQtyInternalUse(BigDecimal.ZERO);
			line.setQtyCount(line.getQtyBook());
			Services.get(IInventoryBL.class).addDescription(line, "Void (" + oldCount + "/" + oldInternal + ")");
			InterfaceWrapperHelper.save(line);
		}
	}

	@Override
	public boolean closeIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean reverseCorrectIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);

		final MDocType docType = MDocType.get(getCtx(), getC_DocType_ID());
		MPeriod.testPeriodOpen(getCtx(), getMovementDate(), DocBaseType.ofCode(docType.getDocBaseType()), getAD_Org_ID());

		final I_M_Inventory reversal = createAndProcessReversal();

		// Update Reversed (this)
		Services.get(IInventoryBL.class).addDescription(this, "(" + reversal.getDocumentNo() + "<-)");
		setProcessed(true);
		setReversal_ID(reversal.getM_Inventory_ID());
		setDocStatus(DOCSTATUS_Reversed);	// may come from void
		setDocAction(DOCACTION_None);

		// After reverseCorrect
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);

		return true;
	}

	private final I_M_Inventory createAndProcessReversal()
	{
		final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);

		final I_M_Inventory reversal = InterfaceWrapperHelper.newInstance(I_M_Inventory.class);
		InterfaceWrapperHelper.copyValues(this, reversal);
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DOCACTION_Complete);
		reversal.setIsApproved(false);
		reversal.setPosted(false);
		reversal.setProcessed(false);
		inventoryBL.addDescription(reversal, "{->" + getDocumentNo() + ")");
		reversal.setReversal_ID(getM_Inventory_ID());
		InterfaceWrapperHelper.save(reversal);

		// Reverse Line Qty
		for (final I_M_InventoryLine oLine : getLines())
		{
			final I_M_InventoryLine rLine = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);
			InterfaceWrapperHelper.copyValues(oLine, rLine);
			rLine.setM_Inventory(reversal);
			// AZ Goodwill
			// store original (voided/reversed) document line
			rLine.setReversalLine_ID(oLine.getM_InventoryLine_ID());
			//
			rLine.setQtyBook(oLine.getQtyCount());		// switch
			rLine.setQtyCount(oLine.getQtyBook());
			rLine.setQtyInternalUse(oLine.getQtyInternalUse().negate());

			InterfaceWrapperHelper.save(rLine);
		}

		//
		// Complete the reversal
		final IDocumentBL documentBL = Services.get(IDocumentBL.class);
		documentBL.processEx(reversal, IDocument.ACTION_Complete);

		//
		// Close the reversal
		InterfaceWrapperHelper.refresh(reversal);
		documentBL.processEx(reversal, IDocument.ACTION_Close);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		InterfaceWrapperHelper.save(reversal);

		//
		return reversal;
	}

	@Override
	public boolean reverseAccrualIt()
	{
		throw new UnsupportedOperationException();
		// ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		// ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		// return true;
	}

	@Override
	public boolean reActivateIt()
	{
		throw new UnsupportedOperationException();
		// ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		// ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		// return true;
	}

	@Override
	public String getSummary()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		sb.append(": ");
		sb.append(Services.get(IMsgBL.class).translate(getCtx(), "ApprovalAmt")).append("=").append(getApprovalAmt());

		// - Description
		final String description = getDescription();
		if (!Check.isEmpty(description, true))
		{
			sb.append(" - ").append(description.trim());
		}

		return sb.toString();
	}

	@Override
	public InstantAndOrgId getDocumentDate()
	{
		return InstantAndOrgId.ofTimestamp(getMovementDate(), OrgId.ofRepoId(getAD_Org_ID()));
	}

	/**
	 * @return <code>null</code>
	 */
	@Override
	@Deprecated
	public String getProcessMsg()
	{
		return null;
	}

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getUpdatedBy();
	}

	/**
	 * Get Document Currency
	 *
	 * @return C_Currency_ID
	 */
	@Override
	public int getC_Currency_ID()
	{
		// MPriceList pl = MPriceList.get(getCtx(), getM_PriceList_ID());
		// return pl.getC_Currency_ID();
		return 0;
	}

	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	}
}
