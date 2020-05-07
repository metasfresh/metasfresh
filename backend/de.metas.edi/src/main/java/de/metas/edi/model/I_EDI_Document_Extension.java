package de.metas.edi.model;

/*
 * #%L
 * de.metas.edi
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


import org.compiere.model.I_C_BPartner;

import de.metas.esb.edi.model.I_EDI_Desadv;

/**
 * 
 * Interface extension to be used for data (like C_Invoice, M_InOut) that shall be exported as EDI, but is not "natively" made for that the way {@link I_EDI_Desadv} is. Declares properties that the
 * system uses to decided if a given record is suitable for the export or not.
 *
 */
public interface I_EDI_Document_Extension extends I_EDI_Document
{
	// @formatter:off
    /** Column name C_BPartner_ID */
	void setC_BPartner_ID (int C_BPartner_ID);
	int getC_BPartner_ID();
	I_C_BPartner getC_BPartner() throws RuntimeException;
	// @formatter:on

	// @formatter:off
    /** Column name IsSOTrx */
    String COLUMNNAME_IsSOTrx = "IsSOTrx";
	void setIsSOTrx (boolean IsSOTrx);
	boolean isSOTrx();
	// @formatter:on

	// @formatter:off
    String COLUMNNAME_DocStatus = "DocStatus";
	void setDocStatus (String DocStatus);
	String getDocStatus();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_IsEdiEnabled = "IsEdiEnabled";
	boolean isEdiEnabled();
	void setIsEdiEnabled(boolean IsEdiEnabled);
	// @formatter:on

	// @formatter:off
    String COLUMNNAME_Reversal_ID = "Reversal_ID";
	void setReversal_ID (int Reversal_ID);
	int getReversal_ID();
	// @formatter:on

	/** DocStatus AD_Reference_ID=131 */
	int DOCSTATUS_AD_Reference_ID = 131;
	/** Drafted = DR */
	String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	String DOCSTATUS_WaitingConfirmation = "WC";
}
