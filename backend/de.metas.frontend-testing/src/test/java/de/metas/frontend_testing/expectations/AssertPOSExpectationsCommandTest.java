package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.frontend_testing.expectations.request.JsonPOSExpectation.JsonCashJournalLineExpectation;
import de.metas.frontend_testing.expectations.request.JsonPOSExpectation.JsonPOSAllocatedPaymentExpectation;
import de.metas.frontend_testing.expectations.request.JsonPOSExpectation.JsonPOSCardPaymentExpectation;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.pos.POSCashJournalLine;
import de.metas.pos.POSCashJournalLineType;
import de.metas.pos.POSOrderAndPaymentId;
import de.metas.pos.POSOrderId;
import de.metas.pos.POSPaymentId;
import de.metas.pos.POSTerminalId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link AssertPOSExpectationsCommand#assertQtyMatches}: a POS-return / credit-memo line's expected qty must
 * match BOTH the numeric value AND the UOM — a scale-insensitive value match ({@code 0.3} vs {@code 0.300})
 * alone is not enough, since a qty stated in the wrong UOM (e.g. base UOM instead of the catch-weight UOM)
 * would silently pass a value-only check.
 */
class AssertPOSExpectationsCommandTest
{
	private static final UomId KGM = UomId.ofRepoId(1);
	private static final UomId EACH = UomId.ofRepoId(2);

	@Test
	void passes_whenQtyAndUomMatch_evenAtDifferentScale()
	{
		assertThatCode(() -> AssertPOSExpectationsCommand.assertQtyMatches(
				new BigDecimal("0.300"), KGM, new BigDecimal("0.3"), KGM, "return line[0]"))
				.doesNotThrowAnyException();
	}

	@Test
	void fails_whenUomDiffers_evenIfNumericValueMatches()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertQtyMatches(
				new BigDecimal("0.300"), KGM, new BigDecimal("0.300"), EACH, "return line[0]"))
				.hasMessageContaining("UOM");
	}

	@Test
	void fails_whenQtyValueDiffers()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertQtyMatches(
				new BigDecimal("0.300"), KGM, new BigDecimal("0.482"), KGM, "return line[0]"))
				.hasMessageContaining("qty");
	}

	//
	// invoices[].allocatedPayments[].tenderType
	//

	private static final InvoiceId INVOICE_ID = InvoiceId.ofRepoId(101);

	@Test
	void allocatedTenderType_passes_whenSomeActualPaymentIsCreditCard()
	{
		assertThatCode(() -> AssertPOSExpectationsCommand.assertAllocatedTenderTypes(
				INVOICE_ID,
				ImmutableList.of("C"),
				ImmutableList.of(JsonPOSAllocatedPaymentExpectation.builder().tenderType("C").build())))
				.doesNotThrowAnyException();
	}

	@Test
	void allocatedTenderType_fails_whenActualPaymentIsCash()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertAllocatedTenderTypes(
				INVOICE_ID,
				ImmutableList.of("X"),
				ImmutableList.of(JsonPOSAllocatedPaymentExpectation.builder().tenderType("C").build())))
				.hasMessageContaining("tenderType=C");
	}

	//
	// cashJournalLines[].type = CARD_PAY with cardSettlement
	//

	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);

	private static POSCashJournalLine cardPayLineOfSettlement()
	{
		return POSCashJournalLine.builder()
				.type(POSCashJournalLineType.CARD_PAYMENT)
				.amount(Money.of("16.07", EUR))
				.cashierId(UserId.ofRepoId(100))
				.posInvoiceCardSettlementId(POSInvoiceCardSettlementId.ofRepoId(1))
				.build();
	}

	private static POSCashJournalLine cardPayLineOfPOSOrder()
	{
		return POSCashJournalLine.builder()
				.type(POSCashJournalLineType.CARD_PAYMENT)
				.amount(Money.of("2.50", EUR))
				.cashierId(UserId.ofRepoId(100))
				.posOrderAndPaymentId(POSOrderAndPaymentId.of(POSOrderId.ofRepoId(1), POSPaymentId.ofRepoId(1)))
				.build();
	}

	@Test
	void cashJournalLine_passes_forCardPayOfASettlement_withCardSettlementTrue()
	{
		assertThatCode(() -> AssertPOSExpectationsCommand.assertCashJournalLine(
				JsonCashJournalLineExpectation.builder()
						.type(POSCashJournalLineType.CARD_PAYMENT)
						.amount(new BigDecimal("16.07"))
						.cardSettlement(true)
						.build(),
				cardPayLineOfSettlement(),
				0))
				.doesNotThrowAnyException();
	}

	@Test
	void cashJournalLine_fails_whenCardSettlementExpectedButLineBelongsToAPOSOrder()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertCashJournalLine(
				JsonCashJournalLineExpectation.builder().cardSettlement(true).build(),
				cardPayLineOfPOSOrder(),
				0))
				.hasMessageContaining("cardSettlement");
	}

	@Test
	void cashJournalLine_fails_whenNoCardSettlementExpectedButLineBelongsToOne()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertCashJournalLine(
				JsonCashJournalLineExpectation.builder().cardSettlement(false).build(),
				cardPayLineOfSettlement(),
				0))
				.hasMessageContaining("cardSettlement");
	}

	@Test
	void cashJournalLine_doesNotCheckCardSettlement_whenNotStated()
	{
		assertThatCode(() -> AssertPOSExpectationsCommand.assertCashJournalLine(
				JsonCashJournalLineExpectation.builder().type(POSCashJournalLineType.CARD_PAYMENT).build(),
				cardPayLineOfSettlement(),
				0))
				.doesNotThrowAnyException();
	}

	//
	// cardSettlements[]{invoice, status}
	//

	private static POSInvoiceCardSettlement settlement(final int id, final POSInvoiceCardSettlementStatus status)
	{
		return POSInvoiceCardSettlement.builder()
				.id(POSInvoiceCardSettlementId.ofRepoId(id))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1000000, 1000000))
				.posTerminalId(POSTerminalId.ofRepoId(1))
				.invoiceId(INVOICE_ID)
				.bpartnerId(BPartnerId.ofRepoId(2))
				.cashierId(UserId.ofRepoId(100))
				.amount(Money.of("16.07", EUR))
				.status(status)
				.created(Instant.EPOCH)
				.build();
	}

	@Test
	void cardSettlements_pass_whenStatusesMatchInOrder()
	{
		assertThatCode(() -> AssertPOSExpectationsCommand.assertCardSettlementStatuses(
				INVOICE_ID,
				ImmutableList.of("FA", "SU"),
				ImmutableList.of(settlement(1, POSInvoiceCardSettlementStatus.FAILED), settlement(2, POSInvoiceCardSettlementStatus.SUCCESSFUL))))
				.doesNotThrowAnyException();
	}

	@Test
	void cardSettlements_fail_whenStatusDiffers()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertCardSettlementStatuses(
				INVOICE_ID,
				ImmutableList.of("SU"),
				ImmutableList.of(settlement(1, POSInvoiceCardSettlementStatus.PENDING))))
				.hasMessageContaining("status");
	}

	@Test
	void cardSettlements_fail_whenCountDiffers()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertCardSettlementStatuses(
				INVOICE_ID,
				ImmutableList.of("SU"),
				ImmutableList.of()))
				.hasMessageContaining("card settlements");
	}

	@Test
	void cardSettlements_noExpectedStatus_passes_whenInvoiceHasNoSettlement()
	{
		assertThatCode(() -> AssertPOSExpectationsCommand.assertCardSettlementStatuses(INVOICE_ID, ImmutableList.of(), ImmutableList.of()))
				.doesNotThrowAnyException();
	}

	@Test
	void cardSettlements_noExpectedStatus_fails_whenInvoiceHasASettlement()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertCardSettlementStatuses(
				INVOICE_ID,
				ImmutableList.of(),
				ImmutableList.of(settlement(1, POSInvoiceCardSettlementStatus.PENDING))))
				.hasMessageContaining("card settlements");
	}

	//
	// cardPayments[]{status, issueMessages[]}
	//

	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);

	private static AssertPOSExpectationsCommand.ActualCardPayment actualCardPayment(final String status, final String... issueErrorCodes)
	{
		return new AssertPOSExpectationsCommand.ActualCardPayment(status, ImmutableList.copyOf(issueErrorCodes));
	}

	private static JsonPOSCardPaymentExpectation expectedCardPayment(final String status, final String... issueMessages)
	{
		return JsonPOSCardPaymentExpectation.builder().status(status).issueMessages(ImmutableList.copyOf(issueMessages)).build();
	}

	@Test
	void cardPayments_pass_whenStatusesAndIssuesMatchInOrder()
	{
		assertThatCode(() -> AssertPOSExpectationsCommand.assertCardPayments(
				TERMINAL_ID,
				ImmutableList.of(expectedCardPayment("UNKNOWN", "de.metas.pos.POSPayment.LateApprovalNotBooked"), expectedCardPayment("SUCCESSFUL")),
				ImmutableList.of(actualCardPayment("UNKNOWN", "de.metas.pos.POSPayment.LateApprovalNotBooked"), actualCardPayment("SUCCESSFUL"))))
				.doesNotThrowAnyException();
	}

	@Test
	void cardPayments_fail_whenCountDiffers()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertCardPayments(
				TERMINAL_ID,
				ImmutableList.of(expectedCardPayment("UNKNOWN")),
				ImmutableList.of()))
				.hasMessageContaining("card payments");
	}

	@Test
	void cardPayments_fail_whenStatusDiffers()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertCardPayments(
				TERMINAL_ID,
				ImmutableList.of(expectedCardPayment("SUCCESSFUL")),
				ImmutableList.of(actualCardPayment("UNKNOWN"))))
				.hasMessageContaining("status");
	}

	@Test
	void cardPayments_fail_whenIssueMissing()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertCardPayments(
				TERMINAL_ID,
				ImmutableList.of(expectedCardPayment("UNKNOWN", "de.metas.pos.POSPayment.LateApprovalNotBooked")),
				ImmutableList.of(actualCardPayment("UNKNOWN"))))
				.hasMessageContaining("issues");
	}

	@Test
	void cardPayments_fail_whenUnexpectedIssuePresent_andEmptyIssueListStated()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertCardPayments(
				TERMINAL_ID,
				ImmutableList.of(expectedCardPayment("UNKNOWN")),
				ImmutableList.of(actualCardPayment("UNKNOWN", "de.metas.pos.POSPayment.LateApprovalNotBooked"))))
				.hasMessageContaining("issues");
	}

	@Test
	void cardPayments_doNotCheckIssues_whenIssueMessagesNotStated()
	{
		assertThatCode(() -> AssertPOSExpectationsCommand.assertCardPayments(
				TERMINAL_ID,
				ImmutableList.of(JsonPOSCardPaymentExpectation.builder().status("UNKNOWN").build()),
				ImmutableList.of(actualCardPayment("UNKNOWN", "de.metas.pos.POSPayment.LateApprovalNotBooked"))))
				.doesNotThrowAnyException();
	}
}
