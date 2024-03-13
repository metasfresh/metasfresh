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
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocBaseType;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.Msg;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.requisition.RequisitionRepository;
import de.metas.requisition.RequisitionService;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.util.TimeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

/**
 * Requisition Model
 *
 * @author Jorg Janke
 *
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see [ http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962 ]
 * @version $Id: MRequisition.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 * @author red1
 *         <li>FR [ 2214883 ] Remove SQL code and Replace for Query
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 2744682 ] Requisition: improve error reporting
 */
public class MRequisition extends X_M_Requisition implements IDocument
{
	/**
	 *
	 */
	private static final long serialVersionUID = 898606565778668659L;

	public MRequisition(final Properties ctx, final int M_Requisition_ID, final String trxName)
	{
		super(ctx, M_Requisition_ID, trxName);
		if (is_new())
		{
			// setDocumentNo (null);
			// setAD_User_ID (0);
			// setM_PriceList_ID (0);
			// setM_Warehouse_ID(0);
			setDateDoc(new Timestamp(System.currentTimeMillis()));
			setDateRequired(new Timestamp(System.currentTimeMillis()));
			setDocAction(IDocument.ACTION_Complete);	// CO
			setDocStatus(IDocument.STATUS_Drafted);		// DR
			setPriorityRule(PRIORITYRULE_Medium);	// 5
			setTotalLines(BigDecimal.ZERO);
			setIsApproved(false);
			setPosted(false);
			setProcessed(false);
		}
	}

	public MRequisition(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public List<I_M_RequisitionLine> getLines()
	{
		return getRequisitionRepository()
				.getLinesByRequisitionId(getM_Requisition_ID());
	}

	private RequisitionService getRequisitionService()
	{
		return Adempiere.getBean(RequisitionService.class);
	}

	private RequisitionRepository getRequisitionRepository()
	{
		return Adempiere.getBean(RequisitionRepository.class);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MRequisition[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
				.append(",Status=").append(getDocStatus()).append(",Action=").append(getDocAction())
				.append("]");
		return sb.toString();
	}

	@Override
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), "M_Requisition_ID") + " " + getDocumentNo();
	}	// getDocumentInfo

	@Override
	public File createPDF()
	{
		try
		{
			final File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (final Exception e)
		{
			log.error("Could not create PDF", e);
			return null;
		}
	}

	public File createPDF(final File file)
	{
		return null;
	}

	@Override
	protected boolean beforeDelete()
	{
		getRequisitionRepository().deleteLinesByRequisitionId(getM_Requisition_ID());
		return true;
	}

	/**************************************************************************
	 * Process document
	 *
	 * @param processAction document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}	// process

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	@Override
	public boolean unlockIt()
	{
		setProcessing(false);
		return true;
	}	// unlockIt

	@Override
	public boolean invalidateIt()
	{
		return true;
	}

	/**
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

		// Invalid
		if (getAD_User_ID() <= 0
				|| getM_PriceList_ID() <= 0
				|| getM_Warehouse_ID() <= 0)
		{
			return IDocument.STATUS_Invalid;
		}

		final List<I_M_RequisitionLine> lines = getLines();
		if (lines.isEmpty())
		{
			throw AdempiereException.noLines();
		}

		// Std Period open?
		MPeriod.testPeriodOpen(getCtx(), getDateDoc(), DocBaseType.PurchaseRequisition, getAD_Org_ID());

		// Add up Amounts
		final CurrencyPrecision netPrecision = Services.get(IPriceListBL.class).getAmountPrecision(PriceListId.ofRepoId(getM_PriceList_ID()));
		BigDecimal totalLines = BigDecimal.ZERO;
		for (final I_M_RequisitionLine line : lines)
		{
			BigDecimal lineNet = line.getQty().multiply(line.getPriceActual());
			lineNet = netPrecision.round(lineNet);
			if (lineNet.compareTo(line.getLineNetAmt()) != 0)
			{
				line.setLineNetAmt(lineNet);
				InterfaceWrapperHelper.saveRecord(line);
			}
			totalLines = totalLines.add(line.getLineNetAmt());
		}
		if (totalLines.compareTo(getTotalLines()) != 0)
		{
			setTotalLines(totalLines);
			saveEx();
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		m_justPrepared = true;
		return IDocument.STATUS_InProgress;
	}	// prepareIt

	@Override
	public boolean approveIt()
	{
		setIsApproved(true);
		return true;
	}	// approveIt

	@Override
	public boolean rejectIt()
	{
		setIsApproved(false);
		return true;
	}	// rejectIt

	/**
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
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

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		log.debug("Completed: {}", this);

		// User Validation
		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(ACTION_Close);
		return IDocument.STATUS_Completed;
	}	// completeIt

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete())
		{
			setDateDoc(SystemTime.asTimestamp());
		}
		if (dt.isOverwriteSeqOnComplete())
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		if (!closeIt())
		{
			return false;
		}

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	}	// voidIt

	@Override
	public boolean closeIt()
	{
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		// Close Not delivered Qty
		BigDecimal totalLines = BigDecimal.ZERO;
		for (final I_M_RequisitionLine line : getLines())
		{
			BigDecimal finalQty = line.getQty();
			if (line.getC_OrderLine_ID() == 0)
			{
				finalQty = BigDecimal.ZERO;
			}
			else
			{
				final MOrderLine ol = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName());
				finalQty = ol.getQtyOrdered();
			}
			// final qty is not line qty
			if (finalQty.compareTo(line.getQty()) != 0)
			{
				String description = line.getDescription();
				if (description == null)
				{
					description = "";
				}
				description += " [" + line.getQty() + "]";
				line.setDescription(description);
				line.setQty(finalQty);
				getRequisitionService().updateLineNetAmt(line);
				InterfaceWrapperHelper.saveRecord(line);
			}
			totalLines = totalLines.add(line.getLineNetAmt());
		}
		if (totalLines.compareTo(getTotalLines()) != 0)
		{
			setTotalLines(totalLines);
			saveEx();
		}
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean reverseCorrectIt()
	{
		throw new AdempiereException("@NotSupported@");
	}

	@Override
	public boolean reverseAccrualIt()
	{
		throw new AdempiereException("@NotSupported@");
	}

	@Override
	public boolean reActivateIt()
	{
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		// setProcessed(false);
		if (!reverseCorrectIt())
		{
			return false;
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	}

	@Override
	public String getSummary()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		// - User
		sb.append(" - ").append(getUserName());
		// : Total Lines = 123.00
		sb.append(": ").append(Msg.translate(getCtx(), "TotalLines")).append("=").append(getTotalLines());
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
		return sb.toString();
	}	// getSummary

	@Override
	public InstantAndOrgId getDocumentDate()
	{
		return InstantAndOrgId.ofTimestamp(getDateDoc(), OrgId.ofRepoId(getAD_Org_ID()));
	}

	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID()
	{
		return getAD_User_ID();
	}

	@Override
	public int getC_Currency_ID()
	{
		final I_M_PriceList pl = Services.get(IPriceListDAO.class).getById(getM_PriceList_ID());
		return pl.getC_Currency_ID();
	}

	/**
	 * Get Document Approval Amount
	 *
	 * @return amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getTotalLines();
	}

	private String getUserName()
	{
		return Services.get(IUserDAO.class).retrieveUserOrNull(getCtx(), getAD_User_ID()).getName();
	}

	/**
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		final String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	}

}
