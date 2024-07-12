package org.compiere.model;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.IMsgBL;
import de.metas.product.IProductBL;
import de.metas.product.IStorageBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

/**
 * Inventory Movement Model
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>FR [ 1948157  ]  Is necessary the reference for document reverse
 * <li> FR [ 2520591 ] Support multiples calendar for Org
 * @author Armen Rizal, Goodwill Consulting
 * <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2214883 ] Remove SQL code and Replace for Query
 * @version $Id: MMovement.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MMovement extends X_M_Movement implements IDocument
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3634169801280239573L;

	public MMovement(Properties ctx, int M_Movement_ID, String trxName)
	{
		super(ctx, M_Movement_ID, trxName);
		if (is_new())
		{
			//	setC_DocType_ID (0);
			setDocAction(DOCACTION_Complete);    // CO
			setDocStatus(DOCSTATUS_Drafted);    // DR
			setIsApproved(false);
			setIsInTransit(false);
			setMovementDate(Env.getDate(ctx));    // use Login date (08306)
			setPosted(false);
			super.setProcessed(false);
		}
	}

	@SuppressWarnings("unused")
	public MMovement(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Lines
	 */
	private MMovementLine[] m_lines = null;
	/**
	 * Confirmations
	 */
	private MMovementConfirm[] m_confirms = null;

	MMovementLine[] getLines(boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		final List<I_M_MovementLine> list = Services.get(IMovementDAO.class).retrieveLines(this);
		m_lines = LegacyAdapters.convertToPOArray(list, MMovementLine.class);
		return m_lines;
	}    //	getLines

	MMovementConfirm[] getConfirmations(boolean requery)
	{
		if (m_confirms != null && !requery)
		{
			return m_confirms;
		}

		List<MMovementConfirm> list = new Query(getCtx(), MMovementConfirm.Table_Name, "M_Movement_ID=?", get_TrxName())
				.setParameters(get_ID())
				.list(MMovementConfirm.class);
		m_confirms = list.toArray(new MMovementConfirm[list.size()]);
		return m_confirms;
	}    //	getConfirmations

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}    //	addDescription

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}    //	getDocumentInfo

	@Override
	public File createPDF()
	{
		return null;
	}    //	getPDF

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getC_DocType_ID() == 0)
		{
			MDocType[] types = MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_MaterialMovement);
			if (types.length > 0)
			{
				setC_DocType_ID(types[0].getC_DocType_ID());
			}
			else
			{
				throw new AdempiereException("@NotFound@ @C_DocType_ID@");
			}
		}
		return true;
	}    //	beforeSave

	/**
	 * Set Processed.
	 * Propergate to Lines/Taxes
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
		{
			return;
		}
		final String sql = "UPDATE M_MovementLine SET Processed=? WHERE M_Movement_ID=?";
		int noLine = DB.executeUpdateEx(sql, new Object[] { processed, get_ID() }, get_TrxName());
		m_lines = null;
		log.debug("Processed={} - Lines={}", processed, noLine);
	}    //	setProcessed

	/**************************************************************************
	 * 	Process document
	 *    @param processAction document action
	 *    @return true if performed
	 */
	@Override
	public boolean processIt(String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}    //	processIt

	/**
	 * Process Message
	 */
	private String m_processMsg = null;
	/**
	 * Just Prepared Flag
	 */
	private boolean m_justPrepared = false;

	/**
	 * Unlock Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		setProcessing(false);
		return true;
	}    //	unlockIt

	/**
	 * Invalidate Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean invalidateIt()
	{

		setDocAction(DOCACTION_Prepare);
		return true;
	}    //	invalidateIt

	/**
	 * Prepare Document
	 *
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getMovementDate(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return IDocument.STATUS_Invalid;
		}
		MMovementLine[] lines = getLines(true);
		if (lines.length == 0)
		{
			throw AdempiereException.noLines();
		}

		//	Confirmation
		if (dt.isInTransit())
		{
			createConfirmation();
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		return IDocument.STATUS_InProgress;
	}    //	prepareIt

	/**
	 * Create Movement Confirmation
	 */
	private void createConfirmation()
	{
		MMovementConfirm[] confirmations = getConfirmations(false);
		if (confirmations.length > 0)
		{
			return;
		}

		//	Create Confirmation
		MMovementConfirm.create(this, false);
	}    //	createConfirmation

	/**
	 * Approve Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		setIsApproved(true);
		return true;
	}    //	approveIt

	/**
	 * Reject Approval
	 *
	 * @return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		setIsApproved(false);
		return true;
	}    //	rejectIt

	/**
	 * Complete Document
	 *
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		//	Outstanding (not processed) Incoming Confirmations ?
		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
		MMovementConfirm[] confirmations = getConfirmations(true);
		for (MMovementConfirm confirmation : confirmations)
		{
			if (!confirmation.isProcessed())
			{
				m_processMsg = "Open: @M_MovementConfirm_ID@ - "
						+ confirmation.getDocumentNo();
				return IDocument.STATUS_InProgress;
			}
		}

		//	Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		log.debug("Completed: {}", this);

		//
		MMovementLine[] lines = getLines(true); // NOTE: we need to load the lines again in case some model validator created/changed some lines
		for (MMovementLine line : lines)
		{
			//Stock Movement - Counterpart MOrder.reserveStock
			final ProductId productId = line.getProductId();
			if (productId != null
					&& Services.get(IProductBL.class).isStocked(productId))
			{
				final IStorageBL storageBL = Services.get(IStorageBL.class);

					//Update Storage
					final WarehouseId warehouseId = warehousesRepo.getWarehouseIdByLocatorRepoId(line.getM_Locator_ID());
					storageBL.add(getCtx(),
							warehouseId.getRepoId(),
							line.getM_Locator_ID(),
							line.getM_Product_ID(),
							line.getM_AttributeSetInstance_ID(), 0,
							line.getMovementQty().negate(), BigDecimal.ZERO, BigDecimal.ZERO, get_TrxName());

					//Update Storage
					final WarehouseId warehouseToId = warehousesRepo.getWarehouseIdByLocatorRepoId(line.getM_LocatorTo_ID());
					storageBL.add(getCtx(),
							warehouseToId.getRepoId(),
							line.getM_LocatorTo_ID(),
							line.getM_Product_ID(),
							line.getM_AttributeSetInstanceTo_ID(), 0,
							line.getMovementQty(), BigDecimal.ZERO, BigDecimal.ZERO, get_TrxName());

				//
				final MTransaction trxFrom = new MTransaction (getCtx(),
						line.getAD_Org_ID(),
						MTransaction.MOVEMENTTYPE_MovementFrom,
						line.getM_Locator_ID(),
						line.getM_Product_ID(),
						line.getM_AttributeSetInstance_ID(),
						line.getMovementQty().negate(),
						getMovementDate(),
						get_TrxName());
				trxFrom.setM_MovementLine_ID(line.getM_MovementLine_ID());
				InterfaceWrapperHelper.save(trxFrom);
				//
				final MTransaction trxTo = new MTransaction (getCtx(),
						line.getAD_Org_ID(),
						MTransaction.MOVEMENTTYPE_MovementTo,
						line.getM_LocatorTo_ID(),
						line.getM_Product_ID(),
						line.getM_AttributeSetInstanceTo_ID(),
						line.getMovementQty(),
						getMovementDate(),
						get_TrxName());
				trxTo.setM_MovementLine_ID(line.getM_MovementLine_ID());
				InterfaceWrapperHelper.save(trxTo);
			} // product stock
		}    //	for all lines
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Reverse_Correct); // issue #347
		return IDocument.STATUS_Completed;
	}    //	completeIt

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete())
		{
			setMovementDate(new Timestamp(System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setEvaluationContext(this)
					.setFailOnError(false)
					.build();
			if (value != IDocumentNoBuilder.NO_DOCUMENTNO)
			{
				setDocumentNo(value);
			}
		}
	}

	/**
	 * Void Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

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
				|| DOCSTATUS_NotApproved.equals(getDocStatus()))
		{
			//	Set lines to 0
			MMovementLine[] lines = getLines(true);
			for (MMovementLine line : lines)
			{
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}    //	voidIt

	/**
	 * Close Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		//	Close Not delivered Qty
		setDocAction(DOCACTION_None);
		return true;
	}    //	closeIt

	/**
	 * Reverse Correction
	 *
	 * @return false
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

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
		reversal.setIsApproved(false);
		reversal.setIsInTransit(false);
		reversal.setPosted(false);
		reversal.setProcessed(false);
		reversal.addDescription("{->" + getDocumentNo() + ")");
		reversal.setReversal(this); //FR [ 1948157  ]
		InterfaceWrapperHelper.save(reversal);

		//	Reverse Line Qty
		final MMovementLine[] oLines = getLines(true);
		for (MMovementLine oLine : oLines)
		{
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
		if (!reversal.processIt(IDocument.ACTION_Complete))
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		//	Update Reversed (this)
		addDescription("(" + reversal.getDocumentNo() + "<-)");
		setReversal(reversal); //FR [ 1948157  ]
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);    //	may come from void
		setDocAction(DOCACTION_None);

		return true;
	}    //	reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 *
	 * @return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	}    //	reverseAccrualIt

	/**
	 * Re-activate
	 *
	 * @return false
	 */
	@Override
	public boolean reActivateIt()
	{
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	}    //	reActivateIt

	/*************************************************************************
	 * 	Get Summary
	 *    @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(": ")
				.append(Services.get(IMsgBL.class).translate(getCtx(), "ApprovalAmt")).append("=").append(getApprovalAmt())
				.append(" (#").append(getLines(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
		return sb.toString();
	}    //	getSummary

	@Override
	public LocalDate getDocumentDate()
	{
		return TimeUtil.asLocalDate(getMovementDate());
	}

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}    //	getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}    //	getDoc_User_ID

	/**
	 * Get Document Currency
	 *
	 * @return C_Currency_ID
	 */
	@Override
	public int getC_Currency_ID()
	{
		//	MPriceList pl = MPriceList.get(getCtx(), getM_PriceList_ID());
		//	return pl.getC_Currency_ID();
		return 0;
	}    //	getC_Currency_ID

	/**
	 * Is Reversal
	 *
	 * @return true if this movement is a reversal of an original movement
	 */
	private boolean isReversal()
	{
		return Services.get(IMovementBL.class).isReversal(this);
	}    //	isReversal

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	}    //	isComplete

}    //	MMovement

