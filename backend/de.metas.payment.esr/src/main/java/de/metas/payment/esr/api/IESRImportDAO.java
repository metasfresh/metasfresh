package de.metas.payment.esr.api;

/*
 * #%L
 * de.metas.payment.esr
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

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;

public interface IESRImportDAO extends ISingletonService
{

	/**
	 * Retrieve all the ESR Import Lines of the given <code>esrImport</code>.
	 *
	 * @param esrImport
	 * @return
	 */
	List<I_ESR_ImportLine> retrieveLines(I_ESR_Import esrImport);

	/**
	 * Retrieve all the ESR Import Lines of the given <code>esrImport</code>, optionally filtered by type.
	 *
	 * @param esrImport
	 * @param esrTrxTypes may be <code>null</code>. If not null and not empty, then only those lines are returned whose <code>ESRTrxType</code> is one one the given types.
	 * @return
	 */
	List<I_ESR_ImportLine> retrieveLinesForTrxTypes(I_ESR_Import esrImport, List<String> esrTrxTypes);

	/**
	 * Retrieve all ESR import lines that have the same <code>ESR_Import</code> and reference the given <code>invoice</code>.
	 *
	 *
	 * @param invoice
	 * @return the lines that reference the given invoice and the given {@code esrImportLine}'s {@link I_ESR_Import}.<br>
	 *         <b>IMPORTANT</b>: Note that the given <code>esrImportLine</code> itself will <b>always</b> be included in the list, even if it doesn't reference the given invoice when this method is
	 *         called! If the list loaded from storage already contains a line with the same ID, then that line will be replaced with the given <code>esrImportLine</code>.
	 */
	List<I_ESR_ImportLine> retrieveLinesForInvoice(I_ESR_ImportLine esrImportLine, I_C_Invoice invoice);

	/**
	 * Delete all the lines of the ESR Import given as parameter.
	 *
	 * @param esrImport
	 */
	void deleteLines(I_ESR_Import esrImport);

	/**
	 * Search the C_ReferenceNo table for the ref no given as parameter, with ref no type of the invoice.
	 *
	 * @param esrReferenceNumber
	 * @return
	 */
	I_C_ReferenceNo_Doc retrieveESRInvoiceReferenceNumberDocument(Properties ctx, String esrReferenceNumber);

	/**
	 * Retrieve the existing esr imports of the organization given as parameter (through ID)
	 *
	 * @param ctx
	 * @param orgID
	 * @return
	 */
	Iterator<I_ESR_Import> retrieveESRImports(Properties ctx, int orgID);

	/**
	 * Retrieve {@link I_ESR_ImportLine}s which are linked to given bank statement line.
	 *
	 * @param line
	 */
	List<I_ESR_ImportLine> retrieveLinesForBankStatementLine(I_C_BankStatementLine line);

	/**
	 * Retrieve {@link I_ESR_ImportLine}s which are linked to given bank statement line reference. Also inactive ones.
	 *
	 * @param lineRef
	 */
	List<I_ESR_ImportLine> retrieveAllLinesForBankStatementLineRef(I_C_BankStatementLine_Ref lineRef);

	I_ESR_Import retrieveESRImportForPayment(final I_C_Payment payment);
	

	/**
	 * count lines
	 * 
	 * @param esrImport
	 * @param processed
	 * @return
	 */
	int countLines(I_ESR_Import esrImport, Boolean processed);
	
	/***
	 * gets the esr line for the specified esr import header and esrlinetext
	 * 
	 * @param import1
	 * @param esrImportLineText
	 * @return
	 */
	I_ESR_ImportLine fetchLineForESRLineText(I_ESR_Import import1, String esrImportLineText);
}
