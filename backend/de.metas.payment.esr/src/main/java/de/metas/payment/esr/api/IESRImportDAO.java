package de.metas.payment.esr.api;

import java.util.Collection;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementLineId;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.ESRImportId;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IESRImportDAO extends ISingletonService
{
	void save(@NonNull I_ESR_Import esrImport);

	void saveOutOfTrx(@NonNull I_ESR_Import esrImport);

	void save(@NonNull I_ESR_ImportLine esrImportLine);

	List<I_ESR_ImportLine> retrieveLines(I_ESR_Import esrImport);

	List<I_ESR_ImportLine> retrieveLines(ESRImportId esrImportId);

	List<I_ESR_ImportLine> retrieveLines(Collection<PaymentId> paymentIds);

	/**
	 * Retrieve all ESR import lines that have the same <code>ESR_Import</code> and reference the given <code>invoice</code>.
	 *
	 * @return the lines that reference the given invoice and the given {@code esrImportLine}'s {@link I_ESR_Import}.<br>
	 *         <b>IMPORTANT</b>: Note that the given <code>esrImportLine</code> itself will <b>always</b> be included in the list, even if it doesn't reference the given invoice when this method is
	 *         called! If the list loaded from storage already contains a line with the same ID, then that line will be replaced with the given <code>esrImportLine</code>.
	 */
	List<I_ESR_ImportLine> retrieveLinesForInvoice(I_ESR_ImportLine esrImportLine, I_C_Invoice invoice);

	/**
	 * Delete all the lines of the ESR Import given as parameter.
	 */
	void deleteLines(I_ESR_Import esrImport);

	/**
	 * Search the C_ReferenceNo table for the ref no given as parameter, with ref no type of the invoice.
	 */
	I_C_ReferenceNo_Doc retrieveESRInvoiceReferenceNumberDocument(OrgId orgId, String esrReferenceNumber);

	/**
	 * Retrieve the existing esr imports of the organization given as parameter (through ID)
	 */
	Iterator<I_ESR_Import> retrieveESRImports(Properties ctx, int orgID);

	List<I_ESR_ImportLine> retrieveAllLinesByBankStatementLineIds(Collection<BankStatementLineId> bankStatementLineIds);

	List<I_ESR_ImportLine> retrieveAllLinesByBankStatementLineRefId(BankStatementAndLineAndRefId bankStatementLineRefId);

	I_ESR_Import retrieveESRImportForPayment(final I_C_Payment payment);

	/**
	 * count lines
	 *
	 * @param esrImport
	 * @param processed 3 possible values: null = ignore processed status; true = only count processed lines; false = only count unprocessed lines
	 * @return lines count
	 */
	int countLines(I_ESR_Import esrImport, @Nullable Boolean processed);

	/***
	 * gets the esr line for the specified esr import header and esrlinetext
	 *
	 * @param import1
	 * @param esrImportLineText
	 */
	I_ESR_ImportLine fetchLineForESRLineText(I_ESR_Import import1, String esrImportLineText);

	List<I_ESR_Import> getByIds(@NonNull Set<ESRImportId> esrImportIds);
}
