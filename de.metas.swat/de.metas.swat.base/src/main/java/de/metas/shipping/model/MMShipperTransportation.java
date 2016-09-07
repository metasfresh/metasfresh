/******************************************************************************
 * Copyright (C) 2009 Metas                                                   *
 * Copyright (C) 2009 Carlos Ruiz                                             *
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
package de.metas.shipping.model;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.MInOutLineConfirm;
import org.compiere.model.MPackage;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;

import de.metas.shipping.api.IShipperTransportationBL;

/**
 * Shipper Transportation model
 *
 * @author Carlos Ruiz
 */
public class MMShipperTransportation extends X_M_ShipperTransportation implements DocAction
{
	/**
	 *
	 */
	private static final long serialVersionUID = -8883936122691193136L;

	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_ShipperTransportation_ID id
	 * @param trxName transaction
	 */
	public MMShipperTransportation(Properties ctx, int M_ShipperTransportation_ID, String trxName)
	{
		super(ctx, M_ShipperTransportation_ID, trxName);
		if (M_ShipperTransportation_ID == 0)
		{
			setDateDoc(SystemTime.asDayTimestamp());
			setDocAction(DOCACTION_Fertigstellen);	// CO
			setDocStatus(DOCSTATUS_Entwurf);	// DR

			setAD_Org_ID(Env.getAD_Org_ID(ctx));

			Services.get(IShipperTransportationBL.class).setC_DocType(this);

			setIsApproved(false);
			super.setProcessed(false);
		}
	}	// MMShipperTransportation

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MMShipperTransportation(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MMShipperTransportation

	/**
	 * Approve Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		log.info(toString());
		setIsApproved(true);
		return true;
	}	// approveIt

	/**
	 * Close Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		setDocAction(DOCACTION_Nichts);

		return true;
	}	// closeIt

	/**
	 * Complete Document
	 *
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Implicit Approval
		if (!isApproved())
			approveIt();
		log.debug("Completed: {}", this);
		MMShippingPackage[] lines = getLines(false);

		// All lines
		for (int i = 0; i < lines.length; i++)
		{
			MMShippingPackage shippingPackage = lines[i];
			shippingPackage.set_TrxName(get_TrxName());
			shippingPackage.setProcessed(true);
			shippingPackage.saveEx();
			MPackage mPackage = (MPackage)shippingPackage.getM_Package();
			mPackage.setShipDate(getDateDoc());
			mPackage.saveEx();
		}	// for all lines

		// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		setProcessed(true);
		setDocAction(DOCACTION_Reaktivieren); // issue #347
		return DocAction.STATUS_Completed;
	}	// completeIt

	/**
	 * Create PDF
	 *
	 * @return File or null
	 */
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
	public File createPDF(File file)
	{
		// ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.SHIPPERTRANSPORTATION, getM_ShipperTransportation_ID());
		// if (re == null)
		return null;
		// return re.getPDF(file);
	}	// createPDF

	/**
	 * Get Document Approval Amount
	 *
	 * @return amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return Env.ZERO;
	}	// getApprovalAmt

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
	}	// getC_Currency_ID

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getUpdatedBy();
	}	// getDoc_User_ID

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		return Services.get(IMsgBL.class).translate(getCtx(), COLUMNNAME_M_ShipperTransportation_ID) + " " + getDocumentNo();
	}	// getDocumentInfo

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}	// getProcessMsg

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		// : (#1)
		sb.append(": ")
				.append(" (#").append(getLines(false).length).append(")");
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	// getSummary

	/**
	 * Invalidate Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Vorbereiten);
		return true;
	}	// invalidateIt

	/**
	 * Prepare Document
	 *
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		MMShippingPackage[] lines = getLines(true);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		//
		m_justPrepared = true;
		if (!DOCACTION_Fertigstellen.equals(getDocAction()))
			setDocAction(DOCACTION_Fertigstellen);
		return DocAction.STATUS_InProgress;
	}	// prepareIt

	/**************************************************************************
	 * Process document
	 *
	 * @param processAction document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	}	// processIt

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	/**
	 * Re-activate
	 *
	 * @return false
	 */
	@Override
	public boolean reActivateIt()
	{
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		setProcessed(false);
		setDocAction(DOCACTION_Fertigstellen);

		// metas: When shipping order is reactivated, shipping packages
		// need to be reactivated, too. MPackages are refreshed.
		MMShippingPackage[] lines = getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			// reset isProcessed for shipping packages
			final MMShippingPackage shippingPackage = lines[i];
			shippingPackage.set_TrxName(get_TrxName());
			shippingPackage.setProcessed(false);
			shippingPackage.saveEx();
			// reset shipping date for mPackages
			MPackage mPackage = (MPackage)shippingPackage.getM_Package();
			mPackage.setShipDate(null);
			mPackage.saveEx();
		}
		// metas:end

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return true;
	}	// reActivateIt

	/**
	 * Reject Approval
	 *
	 * @return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		log.info(toString());
		setIsApproved(false);
		return true;
	}	// rejectIt

	/**
	 * Reverse Accrual - none
	 *
	 * @return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}	// reverseAccrualIt

	/**
	 * Reverse Correction
	 *
	 * @return false
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return false;
	}	// reverseCorrectionIt

	/**
	 * Unlock Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		log.info(toString());
		setProcessing(false);
		return true;
	}	// unlockIt

	/**
	 * Void Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (DOCSTATUS_Geschlossen.equals(getDocStatus())
				|| DOCSTATUS_Rueckgaengig.equals(getDocStatus())
				|| DOCSTATUS_Storniert.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		// Not Processed
		if (DOCSTATUS_Entwurf.equals(getDocStatus())
				|| DOCSTATUS_Ungueltig.equals(getDocStatus())
				|| DOCSTATUS_InVerarbeitung.equals(getDocStatus())
				|| DOCSTATUS_Genehmigt.equals(getDocStatus())
				|| DOCSTATUS_NichtGenehmigt.equals(getDocStatus()))
		{
			// Set lines to 0
			MMShippingPackage[] lines = getLines(false);
			for (int i = 0; i < lines.length; i++)
			{
				final MMShippingPackage line = lines[i];
				line.setProcessed(true);
				line.setIsActive(false);
				line.setPackageWeight(Env.ZERO);
				line.setPackageNetTotal(Env.ZERO);
				line.save(get_TrxName());
			}
		}

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setPackageWeight(Env.ZERO);
		setPackageNetTotal(Env.ZERO);
		setProcessed(true);
		setDocAction(DOCACTION_Nichts);
		return true;
	}	// voidIt

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MMShipperTransportation[");
		sb.append(get_ID()).append("-").append(getSummary())
				.append("]");
		return sb.toString();
	}	// toString

	/** Package Lines */
	private MMShippingPackage[] m_lines = null;

	/**
	 * Get Lines
	 *
	 * @param requery requery
	 * @return array of lines
	 */
	public MMShippingPackage[] getLines(boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		final String whereClause = MMShippingPackage.COLUMNNAME_M_ShipperTransportation_ID + "=?";
		List<MInOutLineConfirm> list = new Query(getCtx(), MMShippingPackage.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { getM_ShipperTransportation_ID() })
				.list();
		m_lines = new MMShippingPackage[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}	// getLines

	/**
	 * Before Delete
	 *
	 * @return true of it can be deleted
	 */
	@Override
	protected boolean beforeDelete()
	{
		if (isProcessed())
			return false;

		MMShippingPackage[] lines = getLines(false);
		// All lines
		for (int i = 0; i < lines.length; i++)
		{
			MMShippingPackage shippingPackage = lines[i];
			shippingPackage.delete(true);
		}

		return true;
	}	// beforeDelete
}	// MMShipperTransportation
