package de.metas.frontend_testing.expectations;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.frontend_testing.expectations.request.JsonPOSExpectation;
import de.metas.frontend_testing.expectations.request.JsonPOSExpectation.JsonCashJournalLineExpectation;
import de.metas.frontend_testing.expectations.request.JsonPOSExpectation.JsonPOSCreditMemoExpectation;
import de.metas.frontend_testing.expectations.request.JsonPOSExpectation.JsonPOSCreditMemoLineExpectation;
import de.metas.frontend_testing.expectations.request.JsonPOSExpectation.JsonPOSInvoiceExpectation;
import de.metas.frontend_testing.expectations.request.JsonPOSExpectation.JsonPOSReturnLineExpectation;
import de.metas.frontend_testing.expectations.request.QtyAndUOMString;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSCashJournalLine;
import de.metas.pos.POSTerminalId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

/**
 * Asserts a POS product return: the return {@code M_InOut}, its credit memo, the credit memo's allocated
 * outbound payment, and the till's cash journal. Separately, via {@code invoices}, asserts a plain invoice's
 * paid / allocated-payment state — infrastructure with no POS-return artifact behind it, for a later flow that
 * does not exist on this branch yet.
 *
 * <p>Consumer-side JSON shape: see {@link JsonPOSExpectation}'s own Javadoc.
 */
@Builder
class AssertPOSExpectationsCommand
{
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPOSExpectation expectation;

	void execute()
	{
		if (expectation.getReturns() != null || expectation.getCreditMemos() != null)
		{
			final String externalId = expectation.getExternalId();
			if (externalId == null)
			{
				throw new AdempiereException("JsonPOSExpectation.externalId is required when returns/creditMemos are asserted");
			}

			final InOutId returnInOutId = services.getPOSReturnInOutIdByExternalId(externalId);

			if (expectation.getReturns() != null)
			{
				assertReturnLines(returnInOutId, expectation.getReturns());
			}
			if (expectation.getCreditMemos() != null)
			{
				assertCreditMemos(returnInOutId, expectation.getCreditMemos());
			}
		}

		if (expectation.getCashJournalLines() != null)
		{
			final String posTerminalStr = expectation.getPosTerminal();
			if (posTerminalStr == null)
			{
				throw new AdempiereException("JsonPOSExpectation.posTerminal is required when cashJournalLines is asserted");
			}

			final POSTerminalId posTerminalId = context.getId(Identifier.ofString(posTerminalStr), POSTerminalId.class);
			assertCashJournalLines(posTerminalId, expectation.getCashJournalLines());
		}

		if (expectation.getInvoices() != null)
		{
			expectation.getInvoices().forEach(this::assertInvoice);
		}
	}

	private void assertReturnLines(@NonNull final InOutId returnInOutId, @NonNull final List<JsonPOSReturnLineExpectation> lineExpectations)
	{
		final I_M_InOut returnRecord = services.getInOutById(returnInOutId);
		final List<I_M_InOutLine> actualLines = services.getInOutLines(returnRecord)
				.stream()
				.filter(I_M_InOutLine::isActive)
				.sorted(Comparator.comparingInt(I_M_InOutLine::getLine))
				.collect(Collectors.toList());

		softly(() -> {
			softlyPutContext("returnInOutId", returnInOutId);
			softlyPutContext("returnLineExpectations", lineExpectations);
			softlyPutContext("actualReturnLines", actualLines);

			assertThat(actualLines).as("lines of POS return M_InOut_ID=" + returnInOutId).hasSameSize(lineExpectations);

			final int count = Math.min(actualLines.size(), lineExpectations.size());
			for (int i = 0; i < count; i++)
			{
				assertReturnLine(lineExpectations.get(i), actualLines.get(i), returnRecord, i);
			}
		});
	}

	private void assertReturnLine(
			@NonNull final JsonPOSReturnLineExpectation expected,
			@NonNull final I_M_InOutLine actual,
			@NonNull final I_M_InOut returnRecord,
			final int index)
	{
		if (expected.getProduct() != null)
		{
			final ProductId expectedProductId = context.getId(Identifier.ofString(expected.getProduct()), ProductId.class);
			assertThat(ProductId.ofRepoId(actual.getM_Product_ID()))
					.as("product of return line[" + index + "] M_InOutLine_ID=" + actual.getM_InOutLine_ID())
					.isEqualTo(expectedProductId);
		}

		if (expected.getQty() != null)
		{
			assertQty(expected.getQty(), actual.getMovementQty(), actual.getC_UOM_ID(), "return line[" + index + "] M_InOutLine_ID=" + actual.getM_InOutLine_ID());
		}

		if (expected.getWarehouse() != null)
		{
			final WarehouseId expectedWarehouseId = context.getId(Identifier.ofString(expected.getWarehouse()), WarehouseId.class);
			assertThat(WarehouseId.ofRepoId(returnRecord.getM_Warehouse_ID()))
					.as("warehouse of return line[" + index + "] (M_InOut_ID=" + returnRecord.getM_InOut_ID() + ")")
					.isEqualTo(expectedWarehouseId);
		}
	}

	private void assertCreditMemos(@NonNull final InOutId returnInOutId, @NonNull final List<JsonPOSCreditMemoExpectation> creditMemoExpectations)
	{
		// currently always exactly one credit memo per POS return (POSReturnService#ensureCreditMemo)
		final InvoiceId creditMemoId = services.getCreditMemoIdForPOSReturn(returnInOutId);

		softly(() -> {
			softlyPutContext("returnInOutId", returnInOutId);
			softlyPutContext("creditMemoId", creditMemoId);

			assertThat(creditMemoExpectations.size()).as("number of credit memos for POS return M_InOut_ID=" + returnInOutId).isEqualTo(1);
			if (!creditMemoExpectations.isEmpty())
			{
				assertCreditMemo(creditMemoExpectations.get(0), creditMemoId);
			}
		});
	}

	private void assertCreditMemo(@NonNull final JsonPOSCreditMemoExpectation expected, @NonNull final InvoiceId creditMemoId)
	{
		// a POS return's credit memo is always settled synchronously by an outgoing payment allocated to it
		assertThat(services.hasAllocatedPayment(creditMemoId))
				.as("credit memo C_Invoice_ID=" + creditMemoId + " has an allocated outbound payment")
				.isEqualTo(true);

		if (expected.getLines() == null)
		{
			return;
		}

		final List<I_C_InvoiceLine> actualLines = sortedLinesOf(creditMemoId);

		softlyPutContext("creditMemoLineExpectations", expected.getLines());
		softlyPutContext("actualCreditMemoLines", actualLines);

		assertThat(actualLines).as("lines of credit memo C_Invoice_ID=" + creditMemoId).hasSameSize(expected.getLines());

		final int count = Math.min(actualLines.size(), expected.getLines().size());
		for (int i = 0; i < count; i++)
		{
			assertCreditMemoLine(expected.getLines().get(i), actualLines.get(i), i);
		}
	}

	private List<I_C_InvoiceLine> sortedLinesOf(@NonNull final InvoiceId invoiceId)
	{
		return services.getInvoiceLines(invoiceId)
				.stream()
				.filter(I_C_InvoiceLine::isActive)
				.sorted(Comparator.comparingInt(I_C_InvoiceLine::getLine))
				.collect(Collectors.toList());
	}

	private void assertCreditMemoLine(@NonNull final JsonPOSCreditMemoLineExpectation expected, @NonNull final I_C_InvoiceLine actual, final int index)
	{
		if (expected.getProduct() != null)
		{
			final ProductId expectedProductId = context.getId(Identifier.ofString(expected.getProduct()), ProductId.class);
			assertThat(ProductId.ofRepoId(actual.getM_Product_ID()))
					.as("product of credit-memo line[" + index + "] C_InvoiceLine_ID=" + actual.getC_InvoiceLine_ID())
					.isEqualTo(expectedProductId);
		}

		if (expected.getQty() != null)
		{
			assertQty(expected.getQty(), actual.getQtyInvoiced(), actual.getC_UOM_ID(), "credit-memo line[" + index + "] C_InvoiceLine_ID=" + actual.getC_InvoiceLine_ID());
		}

		if (expected.getPrice() != null)
		{
			assertThat(actual.getPriceActual().stripTrailingZeros())
					.as("PriceActual of credit-memo line[" + index + "] C_InvoiceLine_ID=" + actual.getC_InvoiceLine_ID())
					.isEqualTo(expected.getPrice().stripTrailingZeros());
		}

		if (expected.getTaxRate() != null)
		{
			final BigDecimal actualTaxRate = services.getTaxRate(TaxId.ofRepoId(actual.getC_Tax_ID()));
			assertThat(actualTaxRate.stripTrailingZeros())
					.as("tax rate of credit-memo line[" + index + "] C_InvoiceLine_ID=" + actual.getC_InvoiceLine_ID())
					.isEqualTo(expected.getTaxRate().stripTrailingZeros());
		}
	}

	private void assertCashJournalLines(@NonNull final POSTerminalId posTerminalId, @NonNull final List<JsonCashJournalLineExpectation> lineExpectations)
	{
		final POSCashJournal journal = services.getCurrentCashJournal(posTerminalId);
		final List<POSCashJournalLine> actualLines = journal.getLines();

		softly(() -> {
			softlyPutContext("posTerminalId", posTerminalId);
			softlyPutContext("cashJournalLineExpectations", lineExpectations);
			softlyPutContext("actualCashJournalLines", actualLines);

			assertThat(actualLines).as("lines of the cash journal of POS terminal " + posTerminalId).hasSameSize(lineExpectations);

			final int count = Math.min(actualLines.size(), lineExpectations.size());
			for (int i = 0; i < count; i++)
			{
				assertCashJournalLine(lineExpectations.get(i), actualLines.get(i), i);
			}
		});
	}

	private void assertCashJournalLine(@NonNull final JsonCashJournalLineExpectation expected, @NonNull final POSCashJournalLine actual, final int index)
	{
		if (expected.getType() != null)
		{
			assertThat(actual.getType()).as("type of cash-journal line[" + index + "]").isEqualTo(expected.getType());
		}
		if (expected.getAmount() != null)
		{
			assertThat(actual.getAmount().toBigDecimal().stripTrailingZeros())
					.as("amount of cash-journal line[" + index + "]")
					.isEqualTo(expected.getAmount().stripTrailingZeros());
		}
	}

	private void assertInvoice(@NonNull final JsonPOSInvoiceExpectation expected)
	{
		final Identifier identifier = Identifier.ofString(expected.getInvoice());
		final InvoiceId invoiceId = context.getOptionalId(identifier, InvoiceId.class).orElseGet(() -> identifier.toId(InvoiceId.class));
		final I_C_Invoice invoice = services.getInvoiceById(invoiceId);

		softly(() -> {
			softlyPutContext("invoiceId", invoiceId);
			softlyPutContext("invoiceExpectation", expected);

			if (expected.getIsPaid() != null)
			{
				assertThat(invoice.isPaid()).as("IsPaid of invoice C_Invoice_ID=" + invoiceId).isEqualTo(expected.getIsPaid());
			}
			if (expected.getHasAllocatedPayment() != null)
			{
				final List<I_C_Payment> allocatedPayments = services.getAllocatedPayments(invoice);
				assertThat(!allocatedPayments.isEmpty())
						.as("invoice C_Invoice_ID=" + invoiceId + " has an allocated payment")
						.isEqualTo(expected.getHasAllocatedPayment());
			}
		});
	}

	private void assertQty(
			@NonNull final QtyAndUOMString expectedQty,
			@NonNull final BigDecimal actualQty,
			final int actualUomRepoId,
			@NonNull final String what)
	{
		final UomId expectedUomId = services.getUomIdByX12DE355(expectedQty.getUom());
		assertQtyMatches(expectedQty.getQty(), expectedUomId, actualQty, UomId.ofRepoId(actualUomRepoId), what);
	}

	/**
	 * Package-private and pure (no DB access — both UOMs are passed in already resolved) so this comparison is
	 * unit-testable without a real price list: a qty match requires BOTH the numeric value AND the UOM to agree
	 * (e.g. {@code 0.300 KGM} must not accidentally match a line stated in a different UOM at the same numeric
	 * value).
	 */
	static void assertQtyMatches(
			@NonNull final BigDecimal expectedQty,
			@NonNull final UomId expectedUomId,
			@NonNull final BigDecimal actualQty,
			@NonNull final UomId actualUomId,
			@NonNull final String what)
	{
		assertThat(actualUomId).as("UOM of " + what).isEqualTo(expectedUomId);
		assertThat(actualQty.stripTrailingZeros()).as("qty of " + what).isEqualTo(expectedQty.stripTrailingZeros());
	}
}
