package de.metas.payment.esr.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementLineId;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.ESRImportId;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public interface IESRImportDAO extends ISingletonService
{
	void save(@NonNull I_ESR_Import esrImport);

	void saveOutOfTrx(@NonNull I_ESR_Import esrImport);

	void saveOutOfTrx(@NonNull I_ESR_ImportFile esrImportFile);

	void save(@NonNull I_ESR_ImportLine esrImportLine);

	void save(@NonNull I_ESR_ImportFile esrImportFile);

	List<I_ESR_ImportLine> retrieveLines(I_ESR_Import esrImport);

	List<I_ESR_ImportLine> retrieveLines(ESRImportId esrImportId);

	List<I_ESR_ImportLine> retrieveLines(Collection<PaymentId> paymentIds);

	/**
	 * Retrieve all ESR import lines that have the same <code>ESR_Import</code> and reference the given <code>invoice</code>.
	 *
	 * @return the lines that reference the given invoice and the given {@code esrImportLine}'s {@link I_ESR_Import}.<br>
	 * <b>IMPORTANT</b>: Note that the given <code>esrImportLine</code> itself will <b>always</b> be included in the list, even if it doesn't reference the given invoice when this method is
	 * called! If the list loaded from storage already contains a line with the same ID, then that line will be replaced with the given <code>esrImportLine</code>.
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

	Iterator<I_ESR_ImportFile> retrieveActiveESRImportFiles(@NonNull OrgId orgId);

	I_ESR_Import retrieveESRImportForPayment(final I_C_Payment payment);

	/**
	 * count lines
	 *
	 * @param esrImport
	 * @param processed 3 possible values: null = ignore processed status; true = only count processed lines; false = only count unprocessed lines
	 * @return lines count
	 */
	int countLines(I_ESR_Import esrImport, @Nullable Boolean processed);

	int countLines(@NonNull I_ESR_ImportFile esrImportFile, @Nullable Boolean processed);

	List<I_ESR_Import> getByIds(@NonNull Set<ESRImportId> esrImportIds);

	List<I_ESR_ImportLine> fetchESRLinesForESRLineText(String esrImportLineText,
			int excludeESRImportLineID);

	ImmutableSet<ESRImportId> retrieveNotReconciledESRImportIds(@NonNull Set<ESRImportId> esrImportIds);

	Optional<PaymentId> findExistentPaymentId(@NonNull I_ESR_ImportLine esrLine);

	I_ESR_ImportLine fetchLineForESRLineText(@NonNull I_ESR_ImportFile esrImportFile, @NonNull String esrImportLineText);

	I_ESR_ImportFile createESRImportFile(@NonNull I_ESR_Import header);

	ImmutableList<I_ESR_ImportFile> retrieveActiveESRImportFiles(I_ESR_Import esrImport);

	ImmutableList<I_ESR_ImportLine> retrieveActiveESRImportLinesFromFile(@NonNull I_ESR_ImportFile esrImportFile);

	I_ESR_ImportFile getImportFileById(int esr_importFile_id);

	void validateEsrImport(I_ESR_Import esrImport);
}
