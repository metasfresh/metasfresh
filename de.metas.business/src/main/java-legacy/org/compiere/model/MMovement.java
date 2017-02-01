/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.engine.IDocActionBL;
import de.metas.product.IProductBL;

/**
 *	Inventory Movement Model
 *
 *  @author Jorg Janke
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li>FR [ 1948157  ]  Is necessary the reference for document reverse
 *  		@see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org
 *			@see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *  @author Armen Rizal, Goodwill Consulting
 * 			<li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>FR [ 2214883 ] Remove SQL code and Replace for Query
 *  @version $Id: MMovement.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MMovement extends X_M_Movement implements DocAction
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3634169801280239573L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_Movement_ID id
	 *	@param trxName transaction
	 */
	public MMovement (Properties ctx, int M_Movement_ID, String trxName)
	{
		super (ctx, M_Movement_ID, trxName);
		if (M_Movement_ID == 0)
		{
		//	setC_DocType_ID (0);
			setDocAction (DOCACTION_Complete);	// CO
			setDocStatus (DOCSTATUS_Drafted);	// DR
			setIsApproved (false);
			setIsInTransit (false);
			setMovementDate (Env.getDate(ctx));	// use Login date (08306)
			setPosted (false);
			super.setProcessed (false);
		}
	}	//	MMovement

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MMovement (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MMovement

	/**	Lines						*/
	private MMovementLine[]		m_lines = null;
	/** Confirmations				*/
	private MMovementConfirm[]	m_confirms = null;

	/**
	 * 	Get Lines
	 *	@param requery requery
	 *	@return array of lines
	 */
	public MMovementLine[] getLines (boolean requery)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		final List<I_M_MovementLine> list = Services.get(IMovementDAO.class).retrieveLines(this);
		m_lines = LegacyAdapters.convertToPOArray(list, MMovementLine.class);
		return m_lines;
	}	//	getLines

	/**
	 * 	Get Confirmations
	 * 	@param requery requery
	 *	@return array of Confirmations
	 */
	public MMovementConfirm[] getConfirmations(boolean requery)
	{
		if (m_confirms != null && !requery)
			return m_confirms;

		List<MMovementConfirm> list = new Query(getCtx(), MMovementConfirm.Table_Name, "M_Movement_ID=?", get_TrxName())
										.setParameters(new Object[]{get_ID()})
										.list();
		m_confirms = list.toArray(new MMovementConfirm[list.size()]);
		return m_confirms;
	}	//	getConfirmations

	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription

	/**
	 * 	Get Document Info
	 *	@return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	//	getDocumentInfo

	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	@Override
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF


	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (getC_DocType_ID() == 0)
		{
			MDocType types[] = MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_MaterialMovement);
			if (types.length > 0)	//	get first
				setC_DocType_ID(types[0].getC_DocType_ID());
			else
			{
				throw new AdempiereException("@NotFound@ @C_DocType_ID@");
			}
		}
		return true;
	}	//	beforeSave

	/**
	 * 	Set Processed.
	 * 	Propergate to Lines/Taxes
	 *	@param processed processed
	 */
	@Override
	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		final String sql = "UPDATE M_MovementLine SET Processed=? WHERE M_Movement_ID=?";
		int noLine = DB.executeUpdateEx(sql, new Object[]{processed, get_ID()}, get_TrxName());
		m_lines = null;
		log.debug("Processed=" + processed + " - Lines=" + noLine);
	}	//	setProcessed


	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	@Override
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocActionBL.class).processIt(this, processAction); // task 09824
	}	//	processIt

	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		log.info(toString());
		setProcessing(false);
		return true;
	}	//	unlockIt

	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt

	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getMovementDate(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		MMovementLine[] lines = getLines(true);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}

		//	Confirmation
		if (dt.isInTransit())
			createConfirmation();

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}	//	prepareIt

	/**
	 * 	Create Movement Confirmation
	 */
	private void createConfirmation()
	{
		MMovementConfirm[] confirmations = getConfirmations(false);
		if (confirmations.length > 0)
			return;

		//	Create Confirmation
		MMovementConfirm.create (this, false);
	}	//	createConfirmation

	/**
	 * 	Approve Document
	 * 	@return true if success
	 */
	@Override
	public boolean  approveIt()
	{
		log.info(toString());
		setIsApproved(true);
		return true;
	}	//	approveIt

	/**
	 * 	Reject Approval
	 * 	@return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		log.info(toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//	Outstanding (not processed) Incoming Confirmations ?
		MMovementConfirm[] confirmations = getConfirmations(true);
		for (int i = 0; i < confirmations.length; i++)
		{
			MMovementConfirm confirm = confirmations[i];
			if (!confirm.isProcessed())
			{
				m_processMsg = "Open: @M_MovementConfirm_ID@ - "
					+ confirm.getDocumentNo();
				return DocAction.STATUS_InProgress;
			}
		}

		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.debug("Completed: {}", this);

		//
		MMovementLine[] lines = getLines(true); // NOTE: we need to load the lines again in case some model validator created/changed some lines
		for (int i = 0; i < lines.length; i++)
		{
			MMovementLine line = lines[i];
			MTransaction trxFrom = null;

			//Stock Movement - Counterpart MOrder.reserveStock
			MProduct product = line.getProduct();
			if (product != null
					&& Services.get(IProductBL.class).isStocked(product) )
			{
				//Ignore the Material Policy when is Reverse Correction
				if(!isReversal())
				{
					checkMaterialPolicy(line);
				}

				if (line.getM_AttributeSetInstance_ID() == 0)
				{
					MMovementLineMA mas[] = MMovementLineMA.get(getCtx(), line.getM_MovementLine_ID(), get_TrxName());
					for (int j = 0; j < mas.length; j++)
					{
						MMovementLineMA ma = mas[j];
						//
						MLocator locator = new MLocator (getCtx(), line.getM_Locator_ID(), get_TrxName());
						//Update Storage
						if (!MStorage.add(getCtx(),locator.getM_Warehouse_ID(),
								line.getM_Locator_ID(),
								line.getM_Product_ID(),
								ma.getM_AttributeSetInstance_ID(), 0,
								ma.getMovementQty().negate(), BigDecimal.ZERO ,  BigDecimal.ZERO , get_TrxName()))
						{
							m_processMsg = "Cannot correct Inventory (MA)";
							return DocAction.STATUS_Invalid;
						}

						int M_AttributeSetInstanceTo_ID = line.getM_AttributeSetInstanceTo_ID();
						//only can be same asi if locator is different
						if (M_AttributeSetInstanceTo_ID <= 0 && line.getM_Locator_ID() != line.getM_LocatorTo_ID())
						{
							M_AttributeSetInstanceTo_ID = ma.getM_AttributeSetInstance_ID();
						}
						//Update Storage
						if (!MStorage.add(getCtx(),locator.getM_Warehouse_ID(),
								line.getM_LocatorTo_ID(),
								line.getM_Product_ID(),
								M_AttributeSetInstanceTo_ID, 0,
								ma.getMovementQty(), BigDecimal.ZERO ,  BigDecimal.ZERO , get_TrxName()))
						{
							m_processMsg = "Cannot correct Inventory (MA)";
							return DocAction.STATUS_Invalid;
						}

						//
						trxFrom = new MTransaction (getCtx(),
								line.getAD_Org_ID(),
								MTransaction.MOVEMENTTYPE_MovementFrom,
								line.getM_Locator_ID(),
								line.getM_Product_ID(),

								// #gh489: M_Storage is a legacy and currently doesn't really work.
								// In this case, its use of M_AttributeSetInstance_ID (which is forwarded from storage to 'ma') introduces a coupling between random documents.
								// this coupling is a big problem, so we don't forward the ASI-ID to the M_Transaction
								0, // ma.getM_AttributeSetInstance_ID(),
								ma.getMovementQty().negate(),
								getMovementDate(),
								get_TrxName());
						trxFrom.setM_MovementLine_ID(line.getM_MovementLine_ID());
						if (!trxFrom.save())
						{
							m_processMsg = "Transaction From not inserted (MA)";
							return DocAction.STATUS_Invalid;
						}
						//
						final MTransaction trxTo = new MTransaction (getCtx(),
								line.getAD_Org_ID(),
								MTransaction.MOVEMENTTYPE_MovementTo,
								line.getM_LocatorTo_ID(),
								line.getM_Product_ID(),

								// #gh489: see the comment about 'trxFrom'
								0, // M_AttributeSetInstanceTo_ID,
								ma.getMovementQty(),
								getMovementDate(),
								get_TrxName());

						trxTo.setM_MovementLine_ID(line.getM_MovementLine_ID());
						if (!trxTo.save())
						{
							m_processMsg = "Transaction To not inserted (MA)";
							return DocAction.STATUS_Invalid;
						}
					}
				}
				//	Fallback - We have ASI
				if (trxFrom == null)
				{
					MLocator locator = new MLocator (getCtx(), line.getM_Locator_ID(), get_TrxName());
					//Update Storage
					if (!MStorage.add(getCtx(),locator.getM_Warehouse_ID(),
							line.getM_Locator_ID(),
							line.getM_Product_ID(),
							line.getM_AttributeSetInstance_ID(), 0,
							line.getMovementQty().negate(), BigDecimal.ZERO ,  BigDecimal.ZERO , get_TrxName()))
					{
						m_processMsg = "Cannot correct Inventory (MA)";
						return DocAction.STATUS_Invalid;
					}

					//Update Storage
					if (!MStorage.add(getCtx(),locator.getM_Warehouse_ID(),
							line.getM_LocatorTo_ID(),
							line.getM_Product_ID(),
							line.getM_AttributeSetInstanceTo_ID(), 0,
							line.getMovementQty(), BigDecimal.ZERO ,  BigDecimal.ZERO , get_TrxName()))
					{
						m_processMsg = "Cannot correct Inventory (MA)";
						return DocAction.STATUS_Invalid;
					}

					//
					trxFrom = new MTransaction (getCtx(),
							line.getAD_Org_ID(),
							MTransaction.MOVEMENTTYPE_MovementFrom,
							line.getM_Locator_ID(),
							line.getM_Product_ID(),
							line.getM_AttributeSetInstance_ID(),
							line.getMovementQty().negate(),
							getMovementDate(),
							get_TrxName());
					trxFrom.setM_MovementLine_ID(line.getM_MovementLine_ID());
					if (!trxFrom.save())
					{
						m_processMsg = "Transaction From not inserted";
						return DocAction.STATUS_Invalid;
					}
					//
					MTransaction trxTo = new MTransaction (getCtx(),
							line.getAD_Org_ID(),
							MTransaction.MOVEMENTTYPE_MovementTo,
							line.getM_LocatorTo_ID(),
							line.getM_Product_ID(),
							line.getM_AttributeSetInstanceTo_ID(),
							line.getMovementQty(),
							getMovementDate(),
							get_TrxName());
					trxTo.setM_MovementLine_ID(line.getM_MovementLine_ID());
					if (!trxTo.save())
					{
						m_processMsg = "Transaction To not inserted";
						return DocAction.STATUS_Invalid;
					}
				}	//	Fallback
			} // product stock
		}	//	for all lines
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Reverse_Correct); // issue #347
		return DocAction.STATUS_Completed;
	}	//	completeIt

	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setMovementDate(new Timestamp (System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setTrxName(get_TrxName())
					.setDocumentModel(this)
					.setFailOnError(false)
					.build();
			if (value != null && value != IDocumentNoBuilder.NO_DOCUMENTNO)
				setDocumentNo(value);
		}
	}

	/**
	 * 	Check Material Policy
	 * 	Sets line ASI
	 */
	private void checkMaterialPolicy(MMovementLine line)
	{
		int no = MMovementLineMA.deleteMovementMA(getM_Movement_ID(), get_TrxName());
		if (no > 0)
			log.info("Deleted old #" + no);

		boolean needSave = false;

		//	Attribute Set Instance
		if (line.getM_AttributeSetInstance_ID() == 0)
		{
			MProduct product = MProduct.get(getCtx(), line.getM_Product_ID());
			String MMPolicy = Services.get(IProductBL.class).getMMPolicy(product);
			MStorage[] storages = MStorage.getWarehouse(getCtx(), 0, line.getM_Product_ID(), 0,
					null, MClient.MMPOLICY_FiFo.equals(MMPolicy), true, line.getM_Locator_ID(), get_TrxName());

			BigDecimal qtyToDeliver = line.getMovementQty();

			for (MStorage storage: storages)
			{
				if (storage.getQtyOnHand().compareTo(qtyToDeliver) >= 0)
				{
					MMovementLineMA ma = new MMovementLineMA (line,
							0, // storage.getM_AttributeSetInstance_ID(),
							qtyToDeliver);
					ma.saveEx();
					qtyToDeliver = BigDecimal.ZERO;
					log.debug( ma + ", QtyToDeliver=" + qtyToDeliver);
				}
				else
				{
					MMovementLineMA ma = new MMovementLineMA (line,
								0, // storage.getM_AttributeSetInstance_ID(),
								storage.getQtyOnHand());
					ma.saveEx();
					qtyToDeliver = qtyToDeliver.subtract(storage.getQtyOnHand());
					log.debug( ma + ", QtyToDeliver=" + qtyToDeliver);
				}
				if (qtyToDeliver.signum() == 0)
					break;
			}

			//	No AttributeSetInstance found for remainder
			if (qtyToDeliver.signum() != 0)
			{
				//deliver using new asi
				MAttributeSetInstance asi = MAttributeSetInstance.create(getCtx(), product, get_TrxName());
				int M_AttributeSetInstance_ID = asi.getM_AttributeSetInstance_ID();
				MMovementLineMA ma = new MMovementLineMA (line, M_AttributeSetInstance_ID , qtyToDeliver);
				ma.saveEx();
				log.debug("##: " + ma);
			}
		}	//	attributeSetInstance

		if (needSave)
		{
			line.saveEx();
		}
	}	//	checkMaterialPolicy

	/**
	 * 	Void Document.
	 * 	@return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		//	Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
			|| DOCSTATUS_Invalid.equals(getDocStatus())
			|| DOCSTATUS_InProgress.equals(getDocStatus())
			|| DOCSTATUS_Approved.equals(getDocStatus())
			|| DOCSTATUS_NotApproved.equals(getDocStatus()) )
		{
			//	Set lines to 0
			MMovementLine[] lines = getLines(true);
			for (int i = 0; i < lines.length; i++)
			{
				MMovementLine line = lines[i];
				BigDecimal old = line.getMovementQty();
				if (old.compareTo(BigDecimal.ZERO) != 0)
				{
					line.setMovementQty(BigDecimal.ZERO);
					line.addDescription("Void (" + old + ")");
					line.save(get_TrxName());
				}
			}
		}
		else
		{
			return reverseCorrectIt();
		}
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	//	voidIt

	/**
	 * 	Close Document.
	 * 	@return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		//	Close Not delivered Qty
		setDocAction(DOCACTION_None);
		return true;
	}	//	closeIt

	/**
	 * 	Reverse Correction
	 * 	@return false
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (!MPeriod.isOpen(getCtx(), getMovementDate(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return false;
		}

		//	Deep Copy
		final MMovement reversal = new MMovement(getCtx(), 0, get_TrxName());
		copyValues(this, reversal, getAD_Client_ID(), getAD_Org_ID());
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DOCACTION_Complete);
		reversal.setIsApproved (false);
		reversal.setIsInTransit (false);
		reversal.setPosted(false);
		reversal.setProcessed(false);
		reversal.addDescription("{->" + getDocumentNo() + ")");
		reversal.setReversal(this); //FR [ 1948157  ]
		InterfaceWrapperHelper.save(reversal);

		//	Reverse Line Qty
		final MMovementLine[] oLines = getLines(true);
		for (int i = 0; i < oLines.length; i++)
		{
			MMovementLine oLine = oLines[i];
			MMovementLine rLine = new MMovementLine(getCtx(), 0, get_TrxName());
			copyValues(oLine, rLine, oLine.getAD_Client_ID(), oLine.getAD_Org_ID());
			rLine.setM_Movement_ID(reversal.getM_Movement_ID());
			//AZ Goodwill
			// store original (voided/reversed) document line
			rLine.setReversalLine_ID(oLine.getM_MovementLine_ID());
			//
			rLine.setMovementQty(rLine.getMovementQty().negate());
			rLine.setTargetQty(BigDecimal.ZERO);
			rLine.setScrappedQty(BigDecimal.ZERO);
			rLine.setConfirmedQty(BigDecimal.ZERO);
			rLine.setProcessed(false);
			if (!rLine.save())
			{
				m_processMsg = "Could not create Movement Reversal Line";
				return false;
			}
		}
		//
		if (!reversal.processIt(DocAction.ACTION_Complete))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return false;
		}
		reversal.closeIt();
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		InterfaceWrapperHelper.save(reversal);
		m_processMsg = reversal.getDocumentNo();

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		//	Update Reversed (this)
		addDescription("(" + reversal.getDocumentNo() + "<-)");
		setReversal(reversal); //FR [ 1948157  ]
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);	//	may come from void
		setDocAction(DOCACTION_None);

		return true;
	}	//	reverseCorrectionIt

	/**
	 * 	Reverse Accrual - none
	 * 	@return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseAccrualIt

	/**
	 * 	Re-activate
	 * 	@return false
	 */
	@Override
	public boolean reActivateIt()
	{
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reActivateIt


	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(": ")
			.append(Services.get(IMsgBL.class).translate(getCtx(),"ApprovalAmt")).append("=").append(getApprovalAmt())
			.append(" (#").append(getLines(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg

	/**
	 * 	Get Document Owner (Responsible)
	 *	@return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}	//	getDoc_User_ID

	/**
	 * 	Get Document Currency
	 *	@return C_Currency_ID
	 */
	@Override
	public int getC_Currency_ID()
	{
	//	MPriceList pl = MPriceList.get(getCtx(), getM_PriceList_ID());
	//	return pl.getC_Currency_ID();
		return 0;
	}	//	getC_Currency_ID

	/**
	 * 	Is Reversal
	 *	@return true if this movement is a reversal of an original movement
	 */
	private boolean isReversal()
	{
		return Services.get(IMovementBL.class).isReversal(this);
	}	//	isReversal

	/**
	 * 	Document Status is Complete or Closed
	 *	@return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
			|| DOCSTATUS_Closed.equals(ds)
			|| DOCSTATUS_Reversed.equals(ds);
	}	//	isComplete

}	//	MMovement

