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

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.DB;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.IInventoryDAO;
import de.metas.product.IProductBL;

/**
 * Physical Inventory Model
 *
 * @author Jorg Janke
 * @version $Id: MInventory.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 1948157 ] Is necessary the reference for document reverse
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962
 */
public class MInventory extends X_M_Inventory implements IDocument
{
	private static final long serialVersionUID = -8161913729477113301L;
	
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

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

	public MInventory(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Warehouse Constructor
	 *
	 * @param wh
	 * @param trxName
	 */
	public MInventory(final MWarehouse wh, final String trxName)
	{
		this(wh.getCtx(), 0, trxName);
		setClientOrg(wh);
		setM_Warehouse_ID(wh.getM_Warehouse_ID());
	}

	public List<I_M_InventoryLine> getLines()
	{
		return Services.get(IInventoryDAO.class).retrieveLinesForInventoryId(getM_Inventory_ID());
	}

	private boolean hasLines()
	{
		return Services.get(IInventoryDAO.class).hasLines(getM_Inventory_ID());
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
			final int docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
					.adClientId(getAD_Client_ID())
					.adOrgId(getAD_Org_ID())
					.build());
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
			Services.get(IInventoryDAO.class).setInventoryLinesProcessed(getM_Inventory_ID(), processed);
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
		MPeriod.testPeriodOpen(getCtx(), getMovementDate(), X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory, getAD_Org_ID());

		if (!hasLines())
		{
			throw new AdempiereException("@NoLines@");
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
		if (!productBL.isStocked(line.getM_Product_ID()))
		{
			return;
		}

		// Get Quantity to Inventory Inernal Use
		final BigDecimal qtyDiff = Services.get(IInventoryBL.class).getMovementQty(line);
		final String movementType = qtyDiff.signum() > 0 ? X_M_Transaction.MOVEMENTTYPE_InventoryIn : X_M_Transaction.MOVEMENTTYPE_InventoryOut;

		// Transaction
		final MTransaction mtrx = new MTransaction(getCtx(),
				line.getAD_Org_ID(),
				movementType,
				line.getM_Locator_ID(),
				line.getM_Product_ID(),
				line.getM_AttributeSetInstance_ID(),
				qtyDiff,
				getMovementDate(),
				get_TrxName());
		mtrx.setM_InventoryLine_ID(line.getM_InventoryLine_ID());
		InterfaceWrapperHelper.save(mtrx);

		if (qtyDiff.signum() != 0)
		{
			final String err = createCostDetail(line, line.getM_AttributeSetInstance_ID(), qtyDiff);
			if (err != null && !err.isEmpty())
			{
				throw new AdempiereException(err);
			}
		}
	}

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		final I_C_DocType docType = MDocType.get(getCtx(), getC_DocType_ID());
		if (docType.isOverwriteDateOnComplete())
		{
			setMovementDate(SystemTime.asTimestamp());
		}
		if (docType.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setTrxName(get_TrxName())
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
		MPeriod.testPeriodOpen(getCtx(), getMovementDate(), docType.getDocBaseType(), getAD_Org_ID());

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

	private boolean isReversal()
	{
		final int reversalId = getReversal_ID();
		return reversalId > 0 && reversalId > getM_Inventory_ID();
	}

	/**
	 * Create Cost Detail
	 *
	 * @param line
	 * @param Qty
	 * @return an EMPTY String on success otherwise an ERROR message
	 */
	private String createCostDetail(final I_M_InventoryLine line, final int M_AttributeSetInstance_ID, final BigDecimal qty)
	{
		// Get Account Schemas to create MCostDetail
		final MAcctSchema[] acctschemas = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
		for (int asn = 0; asn < acctschemas.length; asn++)
		{
			final MAcctSchema as = acctschemas[asn];

			if (as.isSkipOrg(getAD_Org_ID()) || as.isSkipOrg(line.getAD_Org_ID()))
			{
				continue;
			}

			BigDecimal costs = BigDecimal.ZERO;
			if (isReversal())
			{
				String sql = "SELECT amt * -1 FROM M_CostDetail WHERE M_InventoryLine_ID=?"; // negate costs
				final MProduct product = new MProduct(getCtx(), line.getM_Product_ID(), ITrx.TRXNAME_ThreadInherited);
				final String CostingLevel = product.getCostingLevel(as);
				if (X_C_AcctSchema.COSTINGLEVEL_Organization.equals(CostingLevel))
				{
					sql = sql + " AND AD_Org_ID=" + getAD_Org_ID();
				}
				else if (X_C_AcctSchema.COSTINGLEVEL_BatchLot.equals(CostingLevel) && M_AttributeSetInstance_ID != 0)
				{
					sql = sql + " AND M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID;
				}
				costs = DB.getSQLValueBD(ITrx.TRXNAME_ThreadInherited, sql, line.getReversalLine_ID());
			}
			else
			{
				final ProductCost pc = new ProductCost(getCtx(),
						line.getM_Product_ID(), M_AttributeSetInstance_ID, ITrx.TRXNAME_ThreadInherited);
				pc.setQty(qty);
				costs = pc.getProductCosts(as, line.getAD_Org_ID(), as.getCostingMethod(), 0, false);
			}

			//
			// Validate the cost price
			if (ProductCost.isNoCosts(costs))
			{
				return "No Costs for " + line.getM_Product().getName();
			}

			// Set Total Amount and Total Quantity from Inventory
			MCostDetail.createInventory(as, line.getAD_Org_ID(),
					line.getM_Product_ID(), M_AttributeSetInstance_ID,
					line.getM_InventoryLine_ID(), 0,	// no cost element
					costs, qty,
					line.getDescription(), ITrx.TRXNAME_ThreadInherited);
		}

		return "";
	}
}
