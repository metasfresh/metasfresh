package de.metas.frontend_testing.masterdata.pos;

import de.metas.bpartner.BPartnerId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.pos.POSTerminalId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatePOSInvoiceCardSettlementCommandTest
{
	private static final UserId CASHIER_ID = UserId.ofRepoId(100);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(200);

	private POSInvoiceCardSettlementRepository repository;
	private MasterdataContext context;
	private InvoiceId invoiceId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		repository = new POSInvoiceCardSettlementRepository();
		context = new MasterdataContext();
		context.putIdentifier(Identifier.ofString("user"), CASHIER_ID);
		context.putIdentifier(Identifier.ofString("T1"), TERMINAL_ID);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(300);
		invoice.setC_Currency_ID(318);
		invoice.setGrandTotal(new BigDecimal("16.07"));
		InterfaceWrapperHelper.saveRecord(invoice);
		invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		context.putIdentifier(Identifier.ofString("INV1"), invoiceId);
	}

	private POSInvoiceCardSettlement create(final JsonPOSInvoiceCardSettlementRequest request)
	{
		final JsonPOSInvoiceCardSettlementResponse response = CreatePOSInvoiceCardSettlementCommand.builder()
				.repository(repository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("S1"))
				.build()
				.execute();
		return repository.getById(POSInvoiceCardSettlementId.ofRepoId(response.getId()));
	}

	@Test
	public void takesInvoicePartnerAmountAndCashierFromTheReferencedRecords()
	{
		final POSInvoiceCardSettlement settlement = create(JsonPOSInvoiceCardSettlementRequest.builder()
				.posTerminal(Identifier.ofString("T1"))
				.invoice(Identifier.ofString("INV1"))
				.cashier(Identifier.ofString("user"))
				.build());

		assertThat(settlement.getInvoiceId()).isEqualTo(invoiceId);
		assertThat(settlement.getBpartnerId()).isEqualTo(BPartnerId.ofRepoId(300));
		assertThat(settlement.getAmount().toBigDecimal()).isEqualByComparingTo("16.07");
		assertThat(settlement.getAmount().getCurrencyId()).isEqualTo(CurrencyId.ofRepoId(318));
		assertThat(settlement.getPosTerminalId()).isEqualTo(TERMINAL_ID);
		assertThat(settlement.getCashierId()).isEqualTo(CASHIER_ID);
		assertThat(settlement.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.PENDING);
		assertThat(settlement.isAbandoned()).isFalse();
	}

	@Test
	public void appliesRequestedStatusNoteErrorAndAbandonedFlag()
	{
		final POSInvoiceCardSettlement settlement = create(JsonPOSInvoiceCardSettlementRequest.builder()
				.posTerminal(Identifier.ofString("T1"))
				.invoice(Identifier.ofString("INV1"))
				.cashier(Identifier.ofString("user"))
				.status(POSInvoiceCardSettlementStatus.BOOKING_ERROR)
				.note("checked at the terminal")
				.errorMsg("booking failed")
				.bookingAttempts(3)
				.isAbandoned(true)
				.build());

		assertThat(settlement.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.BOOKING_ERROR);
		assertThat(settlement.getNote()).isEqualTo("checked at the terminal");
		assertThat(settlement.getErrorMsg()).isEqualTo("booking failed");
		assertThat(settlement.getBookingAttempts()).isEqualTo(3);
		assertThat(settlement.isAbandoned()).isTrue();
	}
}
