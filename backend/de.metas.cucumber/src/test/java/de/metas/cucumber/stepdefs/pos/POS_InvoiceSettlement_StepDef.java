package de.metas.cucumber.stepdefs.pos;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalId;
import de.metas.pos.invoice_settlement.POSInvoiceSettleRequest;
import de.metas.pos.invoice_settlement.POSInvoiceSettleResult;
import de.metas.pos.invoice_settlement.POSOpenInvoice;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for settling an existing open sales invoice in cash at the till: searching for it by document
 * number ({@link POSService#findOpenInvoices}) and settling it ({@link POSService#settleInvoiceInCash}).
 */
@RequiredArgsConstructor
public class POS_InvoiceSettlement_StepDef
{
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final POSService posService = SpringContextHolder.instance.getBean(POSService.class);

	@NonNull private final C_POS_StepDefData posTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;

	/**
	 * Searches the terminal org's open sales invoices by document number ({@link POSService#findOpenInvoices}) and
	 * asserts exactly the expected ones came back, with their {@link POSOpenInvoice} fields.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Invoice_ID</b> — (required, identifier-ref) the expected open invoice<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) expected invoice partner; asserted against the returned
	 *   {@code bpartnerName}<br>
	 *   <b>GrandTotal</b> — (optional) expected total<br>
	 *   <b>OpenAmt</b> — (optional) expected open amount<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, C_Invoice_StepDefData, C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then find open invoices at POS terminal till by document number 'INV-1001' returns:
	 *   | C_Invoice_ID | C_BPartner_ID | GrandTotal | OpenAmt |
	 *   | invoice1     | customer      | 119.00     | 119.00  |
	 * </pre>
	 */
	@And("^find open invoices at POS terminal (\\S+) by document number '([^']*)' returns:$")
	public void findOpenInvoicesReturns(
			@NonNull final String terminalIdentifier,
			@NonNull final String documentNo,
			@NonNull final DataTable dataTable)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final List<POSOpenInvoice> actual = posService.findOpenInvoices(posTerminalId, documentNo);

		final List<DataTableRow> expectedRows = DataTableRows.of(dataTable).toList();
		assertThat(actual)
				.as("open invoices found for document number '%s'", documentNo)
				.hasSize(expectedRows.size());

		for (final DataTableRow expectedRow : expectedRows)
		{
			assertOpenInvoiceMatches(actual, expectedRow, documentNo);
		}
	}

	/**
	 * Asserts that {@link POSService#findOpenInvoices} finds nothing for the given document number.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_POS_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then find open invoices at POS terminal till by document number 'INV-1002' returns no invoices
	 * </pre>
	 */
	@And("^find open invoices at POS terminal (\\S+) by document number '([^']*)' returns no invoices$")
	public void findOpenInvoicesReturnsNone(@NonNull final String terminalIdentifier, @NonNull final String documentNo)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final List<POSOpenInvoice> actual = posService.findOpenInvoices(posTerminalId, documentNo);
		assertThat(actual).as("open invoices found for document number '%s'", documentNo).isEmpty();
	}

	private void assertOpenInvoiceMatches(
			@NonNull final List<POSOpenInvoice> actual,
			@NonNull final DataTableRow expectedRow,
			@NonNull final String documentNo)
	{
		final InvoiceId expectedInvoiceId = expectedRow.getAsIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID).lookupNotNullIdIn(invoiceTable);

		final POSOpenInvoice openInvoice = actual.stream()
				.filter(candidate -> candidate.getInvoiceId().equals(expectedInvoiceId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Expected open invoice not found")
						.setParameter("C_Invoice_ID", expectedInvoiceId)
						.setParameter("documentNo", documentNo)
						.setParameter("actual", actual));

		assertThat(openInvoice.getDocumentNo()).as("documentNo").isEqualTo(documentNo);

		expectedRow.getAsOptionalIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
				.map(bpartnerTable::getId)
				.ifPresent(expectedBPartnerId -> assertThat(openInvoice.getBpartnerName())
						.as("bpartnerName")
						.isEqualTo(bpartnerDAO.getBPartnerNameById(expectedBPartnerId)));

		expectedRow.getAsOptionalBigDecimal("GrandTotal")
				.ifPresent(grandTotal -> assertThat(openInvoice.getGrandTotal().toBigDecimal())
						.as("grandTotal")
						.isEqualByComparingTo(grandTotal));

		expectedRow.getAsOptionalBigDecimal("OpenAmt")
				.ifPresent(openAmt -> assertThat(openInvoice.getOpenAmt().toBigDecimal())
						.as("openAmt")
						.isEqualByComparingTo(openAmt));
	}

	/**
	 * Settles each given open invoice in cash at the till ({@link POSService#settleInvoiceInCash}), mirroring the
	 * mobile POS client. The resulting {@code C_Payment} is registered under {@code C_Payment_ID}, when given.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Invoice_ID</b> — (required, identifier-ref) the open invoice to settle<br>
	 *   <b>C_Payment_ID</b> — (optional) identifier under which the resulting payment is registered<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, C_Invoice_StepDefData, C_Payment_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
	 *   | C_Invoice_ID | C_Payment_ID       |
	 *   | invoice1     | settlementPayment  |
	 * </pre>
	 */
	@And("^the following invoices are settled in cash at POS terminal (\\S+) by cashier (\\S+):$")
	public void settleInvoicesInCash(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final DataTable dataTable)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final UserId cashierId = StepDefUtil.getUserIdByLogin(userLogin);

		DataTableRows.of(dataTable).forEach(row -> settleInvoiceInCash(posTerminalId, cashierId, row));
	}

	private void settleInvoiceInCash(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final UserId cashierId,
			@NonNull final DataTableRow row)
	{
		final InvoiceId invoiceId = row.getAsIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID).lookupNotNullIdIn(invoiceTable);

		final POSInvoiceSettleResult result = posService.settleInvoiceInCash(POSInvoiceSettleRequest.builder()
				.posTerminalId(posTerminalId)
				.cashierId(cashierId)
				.invoiceId(invoiceId)
				.build());

		row.getAsOptionalIdentifier(I_C_Payment.COLUMNNAME_C_Payment_ID)
				.ifPresent(id -> paymentTable.putOrReplace(id, paymentBL.getById(result.getPaymentId())));
	}
}
