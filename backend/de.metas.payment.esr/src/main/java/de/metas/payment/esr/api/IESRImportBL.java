package de.metas.payment.esr.api;

import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementLineId;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.ESRImportId;
import de.metas.payment.esr.actionhandler.IESRActionHandler;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IESRImportBL extends ISingletonService
{
	/**
	 * Loads the given V11 file by creating one {@link I_ESR_ImportLine} for each line of the file.
	 * Note that this method also matches the line's references against the system by calling {@link #matchESRImportLine(I_ESR_ImportLine)}.
	 */
	void loadESRImportFile(I_ESR_Import esrImport);

	/**
	 * Process ESR Import by creating payment documents for its lines.<br>
	 * Lines that are processed or that already have a payment are skipped.
	 *
	 * @return the number of lines that the method created payments for
	 */
	int process(I_ESR_Import esrImport);

	/**
	 * Method for calculating the check digit.
	 *
	 * @return checksum digit (between 0 and 9)
	 * @see <a href="http://w2.syronex.com/jmr/programming/mod11chk">ISBNs & The Modulo 11 Checksum Algorithm</a>
	 */
	int calculateESRCheckDigit(@NonNull String text);

	/**
	 * Fertigstellen => Complete the import. Shall only be possible, when all rows have an ESR_Payment_Action. After pressing Button, the Header is set to Processed. And will only be shown again after
	 * explicit search.
	 *
	 * @param message a status message or description to be passed to new documents (e.g. Payments or Allocations) that might be created by this method.
	 */
	void complete(I_ESR_Import esrImport, String message);

	/**
	 * Updates the given <code>importLine</code>'s <code>C_Invoice_ID</code> together with its related columns:
	 * <ul>
	 * <li>ESR_Invoice_Grandtotal</li>
	 * <li>ESR_Invoice_Openamt</li>
	 * <li>ESR_Document_Status: always updating to "partially matched"</li>
	 * </ul>
	 *
	 * Also important
	 * <ul>
	 * <li>Doesn't actually save the given <code>importLine</code></li>
	 * <li>Starts by updating all the invoice's  lines' open amounts, i.e. it might also update other import line of the same import.
	 * </ul>
	 */
	void setInvoice(final I_ESR_ImportLine importLine, final I_C_Invoice invoice);

	/**
	 * Registers a handler for a payment action.
	 */
	void registerActionHandler(String actionName, IESRActionHandler handler);

	/**
	 * @param payment Reverse all allocations of the payment; Unset invoice in the payment
	 */
	void reverseAllocationForPayment(I_C_Payment payment);

	/**
	 *
	 * Load the file with the given {@code filename} and create an MD5 checksum for it. Goal: enable us to prevent duplicates.
	 *
	 * @see <a href="http://stackoverflow.com/questions/304268/getting-a-files-md5-checksum-in-java">http://stackoverflow.com/questions/304268/getting-a-files-md5-checksum-in-java</a>
	 */
	String computeMD5Checksum(String filename);

	String computeMD5Checksum(byte[] fileContent);

	/**
	 * Link an unlinked payment to the importLine invoice (i.e. create allocation).
	 */
	void linkInvoiceToPayment(I_ESR_ImportLine importLine);

	/**
	 * Check is the import is processed by checking if all lines are processed
	 */
	boolean isProcessed(I_ESR_Import esrImport);

	void linkESRImportLineToBankStatement(@NonNull I_ESR_ImportLine esrImportLine, @NonNull BankStatementAndLineAndRefId bankStatementLineRefId);

	void unlinkESRImportLinesFromBankStatement(Collection<BankStatementLineId> bankStatementLineIds);

	void scheduleESRImportFor(RunESRImportRequest runESRImportRequest);

	Set<PaymentId> getPaymentIds(ESRImportId esrImportId);

	void linkBankStatementLinesByPaymentIds(Map<PaymentId, BankStatementAndLineAndRefId> bankStatementLineRefIdIndexByPaymentId);

	I_ESR_Import getById(ESRImportId esrImportId);
}
