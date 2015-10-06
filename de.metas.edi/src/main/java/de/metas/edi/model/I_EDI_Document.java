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



/**
 * 
 * General interface for documents that can be exported as EDI document.
 *
 */
public interface I_EDI_Document
{
	// @formatter:off
	/** Column name DocumentNo */
	String COLUMNNAME_DocumentNo = "DocumentNo";
	void setDocumentNo(String DocumentNo);
	String getDocumentNo();
	// @formatter:on

	/** Column name EDI_ExportStatus */
	// @formatter:off
	String COLUMNNAME_EDI_ExportStatus = "EDI_ExportStatus";
	void setEDI_ExportStatus(String EDI_ExportStatus);
	String getEDI_ExportStatus();
	// @formatter:on

	/** EDI_ExportStatus AD_Reference_ID=540381 */
	int EDI_EXPORTSTATUS_AD_Reference_ID = 540381;
	/** EDI Senden = P. EDI Pending */
	String EDI_EXPORTSTATUS_Pending = "P";
	/** EDI Gesendet = S. EDI Sent */
	String EDI_EXPORTSTATUS_Sent = "S";
	/** EDI Übertragung läuft = D. EDI Sending started */
	String EDI_EXPORTSTATUS_SendingStarted = "D";
	/** EDI Übertragung läuft = U. EDI Sending started (enqueued via Async) */
	String EDI_EXPORTSTATUS_Enqueued = "U";
	/** EDI Übertragungsfehler = E. EDI Sending Error. */
	String EDI_EXPORTSTATUS_Error = "E";
	/** EDI-Daten Validieren = I. EDI Invalid status. */
	String EDI_EXPORTSTATUS_Invalid = "I";

	/**
	 * @task http://dewiki908/mediawiki/index.php/08926_EDI-Ausschalten_f%C3%BCr_bestimmte_Belege_%28109751792947%29
	 */
	String EDI_EXPORTSTATUS_DontSend = "N";

	/** Column name EDIErrorMsg */
	// @formatter:off
	String COLUMNNAME_EDIErrorMsg = "EDIErrorMsg";
	void setEDIErrorMsg(String EDIErrorMsg);
	String getEDIErrorMsg();
	// @formatter:on
}
