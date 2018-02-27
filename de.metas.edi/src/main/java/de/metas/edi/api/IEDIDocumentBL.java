package de.metas.edi.api;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.process.export.IExport;
import de.metas.esb.edi.model.I_EDI_Desadv;

public interface IEDIDocumentBL extends ISingletonService
{
	String MSG_Partner_ValidateIsEDIRecipient_Error = "de.metas.edi.ValidateIsEDIRecipientError";
	String MSG_Invalid_Invoice_Aggregation_Error = "de.metas.edi.InvalidInvoiceAggregationError";

	/**
	 * Update EDI status, but do not save
	 *
	 * @param document
	 * @return true if document.IsEdiEnabled=Y, false otherwise
	 */
	boolean updateEdiEnabled(I_EDI_Document_Extension document);

	List<Exception> isValidInvoice(I_C_Invoice invoice);

	List<Exception> isValidInOut(I_M_InOut inOut);

	List<Exception> isValidDesAdv(I_EDI_Desadv desadv);
	
	List<Exception> isValidPartner(org.compiere.model.I_C_BPartner partner);

	/**
	 * @param document
	 * @param EDI_ExportStatus
	 * @param feedback
	 * @param saveLocally if true, also save document here
	 * @return {@link ValidationState#INVALID} if document is invalid due to feedback,<br>
	 *         {@link ValidationState#ALREADY_VALID} if document is already valid, or<br>
	 *         {@link ValidationState#UPDATED_VALID} if document was invalid, but is now valid
	 */
	ValidationState updateInvalid(I_EDI_Document document, String EDI_ExportStatus, List<Exception> feedback, boolean saveLocally);

//	ValidationState updateInvalid(I_EDI_Desadv document, String EDI_ExportStatus, List<Exception> feedback, boolean saveLocally);
	
	/**
	 * @param ctx
	 * @param clientId
	 * @param tableId
	 * @param recordId
	 * @param trxName
	 * @return EDI export processor
	 */
	IExport<? extends I_EDI_Document> createExport(Properties ctx, int clientId, int tableId, int recordId, String trxName);

	/**
	 * @param feedback
	 * @return feedback error message
	 */
	String buildFeedback(List<Exception> feedback);
}
