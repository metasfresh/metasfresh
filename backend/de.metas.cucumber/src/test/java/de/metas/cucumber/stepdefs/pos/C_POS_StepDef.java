/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.charge.C_Charge_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_PriceList_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_TaxCategory_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSCashJournalLine;
import de.metas.pos.POSCashJournalLineType;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import de.metas.pos.POSPayment;
import de.metas.pos.POSPaymentCheckoutRequest;
import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalCreateRequest;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalOpenJournalRequest;
import de.metas.pos.POSTerminalRepository;
import de.metas.pos.remote.RemotePOSOrder;
import de.metas.pos.remote.RemotePOSOrderLine;
import de.metas.pos.remote.RemotePOSPayment;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_POS;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static de.metas.cucumber.stepdefs.StepDefConstants.CLIENT_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.METASFRESH_VALUE;
import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the POS terminal ({@code C_POS}) master data, opening its cash journal, driving a POS
 * cash sale through {@link POSService} (mirroring the mobile POS client's remote-order/checkout calls), and
 * asserting the resulting cash journal state.
 */
@RequiredArgsConstructor
public class C_POS_StepDef
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final POSService posService = SpringContextHolder.instance.getBean(POSService.class);
	@NonNull private final POSTerminalRepository posTerminalRepository = SpringContextHolder.instance.getBean(POSTerminalRepository.class);

	/** Seconds to wait for the async {@code C_POSOrder_CreateInvoiceAndShipment} work package (sales order + invoice + shipment) to land. */
	private static final int ASYNC_INVOICE_TIMEOUT_SEC = 60;

	private final C_POS_StepDefData posTable;
	private final C_BP_BankAccount_StepDefData bpBankAccountTable;
	private final M_PricingSystem_StepDefData pricingSystemTable;
	private final M_PriceList_StepDefData priceListTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Product_StepDefData productTable;
	private final C_TaxCategory_StepDefData taxCategoryTable;
	private final C_Payment_StepDefData paymentTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_Charge_StepDefData chargeTable;

	/**
	 * Creates one {@code C_POS} (POS terminal) record per data-table row, via {@link POSTerminalRepository} —
	 * the same primitive {@code CreatePOSTerminalCommand} (frontend-testing masterdata) uses. The sales-order
	 * document type is resolved automatically (the standard org's {@link DocBaseType#SalesOrder} doc type), as
	 * production does.
	 * <p>
	 * {@code M_PricingSystem_ID} and {@code C_BPartner_Location_ID} are not stored directly on {@code C_POS} —
	 * the pricing system is carried by the price list itself, and the walk-in customer's ship-to location is
	 * resolved by {@code POSTerminalService} from the bpartner, not stored as an FK. Both columns are used here
	 * as fail-fast setup checks instead: this step rejects a price list that does not belong to the given
	 * pricing system, or a bpartner whose {@code SHIP_TO} location does not resolve to the given location —
	 * so a Background wiring mistake fails at setup time with a clear message, not later inside checkout.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>C_BP_BankAccount_ID</b> — (required, identifier-ref) the terminal's cashbook (org bank account)<br>
	 *   <b>M_PricingSystem_ID</b> — (required, identifier-ref) must be the pricing system of {@code M_PriceList_ID}<br>
	 *   <b>M_PriceList_ID</b> — (required, identifier-ref) the terminal's price list<br>
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) the walk-in customer<br>
	 *   <b>C_BPartner_Location_ID</b> — (required, identifier-ref) must be the walk-in customer's {@code SHIP_TO} location<br>
	 *   <b>M_Warehouse_ID</b> — (required, identifier-ref) the ship-from warehouse<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, C_BP_BankAccount_StepDefData, M_PricingSystem_StepDefData,
	 * M_PriceList_StepDefData, C_BPartner_StepDefData, C_BPartner_Location_StepDefData, M_Warehouse_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_POS:
	 *   | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Warehouse_ID |
	 *   | till       | cashbook             | pricingSystem       | priceList      | customer      | customerShipTo         | warehouse      |
	 * </pre>
	 */
	@And("metasfresh contains C_POS:")
	public void createPOSTerminals(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPOSTerminal);
	}

	private void createPOSTerminal(@NonNull final DataTableRow row)
	{
		final BankAccountId cashbookId = bpBankAccountTable.getOrgBankAccountId(row.getAsIdentifier(I_C_POS.COLUMNNAME_C_BP_BankAccount_ID));

		final I_M_PriceList priceList = row.getAsIdentifier(I_C_POS.COLUMNNAME_M_PriceList_ID).lookupNotNullIn(priceListTable);
		final PriceListId priceListId = PriceListId.ofRepoId(priceList.getM_PriceList_ID());

		final PricingSystemId expectedPricingSystemId = row.getAsIdentifier(I_M_PriceList.COLUMNNAME_M_PricingSystem_ID).lookupNotNullIdIn(pricingSystemTable);
		final PricingSystemId actualPricingSystemId = PricingSystemId.ofRepoId(priceList.getM_PricingSystem_ID());
		if (!actualPricingSystemId.equals(expectedPricingSystemId))
		{
			throw new AdempiereException("M_PriceList_ID given for the POS terminal does not belong to the given M_PricingSystem_ID")
					.setParameter("M_PriceList_ID", priceListId)
					.setParameter("expected M_PricingSystem_ID", expectedPricingSystemId)
					.setParameter("actual M_PricingSystem_ID", actualPricingSystemId);
		}

		final BPartnerId walkInCustomerId = row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID).lookupNotNullIdIn(bpartnerTable);

		final I_C_BPartner_Location expectedShipTo = row.getAsIdentifier(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID).lookupNotNullIn(bpartnerLocationTable);
		final I_C_BPartner_Location resolvedShipTo = bpartnerDAO.retrieveBPartnerLocation(IBPartnerDAO.BPartnerLocationQuery.builder()
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
				.bpartnerId(walkInCustomerId)
				.build());
		if (resolvedShipTo.getC_BPartner_Location_ID() != expectedShipTo.getC_BPartner_Location_ID())
		{
			throw new AdempiereException("C_BPartner_Location_ID given for the POS terminal's walk-in customer is not the SHIP_TO location metasfresh resolves for it")
					.setParameter("C_BPartner_ID", walkInCustomerId)
					.setParameter("expected C_BPartner_Location_ID", expectedShipTo.getC_BPartner_Location_ID())
					.setParameter("resolved C_BPartner_Location_ID", resolvedShipTo.getC_BPartner_Location_ID());
		}

		final WarehouseId shipFromWarehouseId = row.getAsIdentifier(I_C_POS.COLUMNNAME_M_Warehouse_ID).lookupNotNullIdIn(warehouseTable);

		final DocTypeId salesOrderDocTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.SalesOrder)
				.clientAndOrgId(CLIENT_ID, ORG_ID)
				.build());

		final POSTerminalId posTerminalId = posTerminalRepository.createPOSTerminal(POSTerminalCreateRequest.builder()
				.orgId(ORG_ID)
				.name(row.suggestValueAndName().getName())
				.walkInCustomerId(walkInCustomerId)
				.cashbookId(cashbookId)
				.salesOrderDocTypeId(salesOrderDocTypeId)
				.priceListId(priceListId)
				.shipFromWarehouseId(shipFromWarehouseId)
				.build());

		final I_C_POS posRecord = InterfaceWrapperHelper.load(posTerminalId.getRepoId(), I_C_POS.class);
		row.getAsIdentifier().putOrReplace(posTable, posRecord);
	}

	/**
	 * Opens the POS terminal's cash journal with a beginning cash balance, via {@link POSService#openCashJournal}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the cash journal of POS terminal till is opened with 100 by metasfresh
	 * </pre>
	 */
	@And("^the cash journal of POS terminal (\\S+) is opened with (\\S+) by (\\S+)$")
	public void openCashJournal(
			@NonNull final String terminalIdentifier,
			@NonNull final String beginningBalance,
			@NonNull final String userLogin)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final UserId cashierId = StepDefUtil.getUserIdByLogin(userLogin);

		posService.openCashJournal(POSTerminalOpenJournalRequest.builder()
				.posTerminalId(posTerminalId)
				.cashierId(cashierId)
				.dateTrx(SystemTime.asInstant())
				.cashBeginningBalance(new BigDecimal(beginningBalance))
				.build());
	}

	/**
	 * Drives a POS cash sale end to end, mirroring the mobile POS client: builds a remote order with the given
	 * product lines and a single {@code CASH} payment for their total, via {@link POSService#updateOrderFromRemote},
	 * transitions it to {@code WaitingPayment} ({@link POSService#changeStatusTo}), then checks the cash payment
	 * out ({@link POSService#checkoutPayment}) — which also completes the order once fully paid. Waits for the
	 * async {@code C_POSOrder_CreateInvoiceAndShipment} work package to generate the sales order + invoice, then
	 * asserts the invoice is paid (the receipt payment auto-allocates against it in the same transaction that
	 * sets the sales order). The resulting {@code C_Invoice} is registered under the sale's identifier, and its
	 * {@code C_Payment} receipt under {@code <sale identifier>Receipt} (distinct identifiers — {@code
	 * IdentifiersResolver}, used e.g. by {@code Fact_Acct records are matching}'s {@code Record_ID}, fails if
	 * more than one {@code StepDefData} table matches the same identifier) for later assertions.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) product sold on this line<br>
	 *   <b>Price</b> — (required) unit price, in the terminal's currency (tax included, per the price list)<br>
	 *   <b>Qty</b> — (optional) quantity; defaults to 1<br>
	 *   <b>C_TaxCategory_ID</b> — (required, identifier-ref) the product's line-level tax category (POS requires
	 *   all-line-level tax; a document-level tax category makes checkout fail)<br>
	 *   <b>Identifier</b> — (optional, first row only) alias for the resulting {@code C_Invoice} (its {@code
	 *   C_Payment} receipt is registered under {@code <Identifier>Receipt}); defaults to the terminal's own
	 *   identifier (only safe for a single sale per terminal per scenario — a scenario making several sales at
	 *   the same terminal must set this)<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, C_TaxCategory_StepDefData,
	 * C_Payment_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And a POS cash sale is made at till:
	 *   | M_Product_ID | Price | C_TaxCategory_ID |
	 *   | product      | 2.50  | taxCategory      |
	 * </pre>
	 */
	@And("^a POS cash sale is made at (\\S+):$")
	public void posCashSale(@NonNull final String terminalIdentifier, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final UserId cashierId = StepDefUtil.getUserIdByLogin(METASFRESH_VALUE);

		final List<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());
		final StepDefDataIdentifier saleIdentifier = rows.get(0).getAsOptionalIdentifier().orElseGet(() -> StepDefDataIdentifier.ofString(terminalIdentifier));

		BigDecimal totalAmount = BigDecimal.ZERO;
		final ImmutableList.Builder<RemotePOSOrderLine> lines = ImmutableList.builder();
		for (final DataTableRow row : rows)
		{
			final I_M_Product product = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
			final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
			final TaxCategoryId taxCategoryId = row.getAsIdentifier("C_TaxCategory_ID").lookupNotNullIdIn(taxCategoryTable);
			final BigDecimal price = row.getAsBigDecimal("Price");
			final BigDecimal qty = row.getAsOptionalBigDecimal("Qty").orElse(BigDecimal.ONE);
			final UomId uomId = productBL.getStockUOMId(productId);

			lines.add(RemotePOSOrderLine.builder()
					.uuid(UUID.randomUUID().toString())
					.productId(productId)
					.productName(product.getName())
					.taxCategoryId(taxCategoryId)
					.price(price)
					.qty(qty)
					.uomId(uomId)
					.build());

			totalAmount = totalAmount.add(price.multiply(qty));
		}

		final POSOrderExternalId orderExternalId = POSOrderExternalId.ofString(UUID.randomUUID().toString());
		final POSPaymentExternalId paymentExternalId = POSPaymentExternalId.ofString(UUID.randomUUID().toString());

		posService.updateOrderFromRemote(
				RemotePOSOrder.builder()
						.uuid(orderExternalId)
						.posTerminalId(posTerminalId)
						.lines(lines.build())
						.payments(ImmutableList.of(RemotePOSPayment.builder()
								.uuid(paymentExternalId)
								.paymentMethod(POSPaymentMethod.CASH)
								.amount(totalAmount)
								.build()))
						.build(),
				cashierId);

		posService.changeStatusTo(posTerminalId, orderExternalId, POSOrderStatus.WaitingPayment, cashierId);

		final POSOrder afterCheckout = posService.checkoutPayment(POSPaymentCheckoutRequest.builder()
				.posTerminalId(posTerminalId)
				.posOrderExternalId(orderExternalId)
				.posPaymentExternalId(paymentExternalId)
				.userId(cashierId)
				.cashTenderedAmount(totalAmount)
				.build());
		afterCheckout.assertCompleted();

		final POSOrder completedOrder = StepDefUtil.tryAndWaitForItem(
				ASYNC_INVOICE_TIMEOUT_SEC,
				500L,
				() -> posService.getOpenOrders(posTerminalId, cashierId, ImmutableSet.of(orderExternalId))
						.stream()
						.findFirst()
						.filter(order -> order.getSalesOrderId() != null));

		final OrderId salesOrderId = completedOrder.getSalesOrderId();
		final List<I_C_Invoice> invoices = invoiceDAO.getInvoicesForOrderIds(ImmutableList.of(salesOrderId));
		if (invoices.size() != 1)
		{
			throw new AdempiereException("Expected exactly one invoice for the POS sales order")
					.setParameter("C_Order_ID", salesOrderId)
					.setParameter("invoices found", invoices.size());
		}

		final I_C_Invoice invoice = invoices.get(0);
		assertThat(invoice.isPaid()).as("POS sale invoice IsPaid").isTrue();
		invoiceTable.putOrReplace(saleIdentifier, invoice);

		final POSPayment posPayment = completedOrder.getPaymentsNotDeleted()
				.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No payment found on the completed POS order"));
		final PaymentId paymentReceiptId = posPayment.getPaymentReceiptId();
		if (paymentReceiptId == null)
		{
			throw new AdempiereException("POS payment has no payment receipt yet").setParameter("posPayment", posPayment);
		}
		final I_C_Payment paymentReceipt = paymentBL.getById(paymentReceiptId);
		// distinct identifier from the invoice's: IdentifiersResolver (used e.g. by "Fact_Acct records are
		// matching") probes every StepDefData table for a given identifier and fails if more than one matches.
		paymentTable.putOrReplace(StepDefDataIdentifier.ofString(saleIdentifier.getAsString() + "Receipt"), paymentReceipt);
	}

	/**
	 * Asserts the POS terminal's currently open cash journal ending balance, via {@link POSService#getCurrentCashJournal}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the cash journal of POS terminal till has ending balance 102.50
	 * </pre>
	 */
	@And("^the cash journal of POS terminal (\\S+) has ending balance (\\S+)$")
	public void assertCashJournalEndingBalance(@NonNull final String terminalIdentifier, @NonNull final String expectedEndingBalance)
	{
		final POSCashJournal journal = getCurrentCashJournalOrThrow(terminalIdentifier);

		assertThat(journal.getCashEndingBalance().toBigDecimal())
				.as("POS terminal %s cash journal ending balance", terminalIdentifier)
				.isEqualByComparingTo(new BigDecimal(expectedEndingBalance));
	}

	/**
	 * Asserts the POS terminal's currently open cash journal lines, in the order they were added, via
	 * {@link POSService#getCurrentCashJournal}. {@code Type} is the {@link POSCashJournalLineType} code
	 * (e.g. {@code CASH_PAY} for a cash-sale payment, {@code CASH_INOUT} for a manual cash withdrawal/deposit).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Type</b> — (required) {@link POSCashJournalLineType} code<br>
	 *   <b>Amount</b> — (required) signed line amount (negative for cash out)<br>
	 *   <b>Description</b> — (optional) line description<br>
	 *   <b>C_Charge_ID</b> — (optional, identifier-ref) the line's description must be this charge's name (a cash
	 *   withdrawal's line carries its category's name)<br>
	 * @cucumber.example
	 * <pre>
	 * And the cash journal of POS terminal till contains lines:
	 *   | Type     | Amount |
	 *   | CASH_PAY | 2.50   |
	 * </pre>
	 */
	@And("^the cash journal of POS terminal (\\S+) contains lines:$")
	public void assertCashJournalLines(@NonNull final String terminalIdentifier, @NonNull final DataTable dataTable)
	{
		final POSCashJournal journal = getCurrentCashJournalOrThrow(terminalIdentifier);
		final ImmutableList<POSCashJournalLine> lines = journal.getLines();

		final List<DataTableRow> expectedRows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());
		assertThat(lines).as("POS terminal %s cash journal lines count", terminalIdentifier).hasSize(expectedRows.size());

		for (int lineIdx = 0; lineIdx < expectedRows.size(); lineIdx++)
		{
			final DataTableRow expectedRow = expectedRows.get(lineIdx);
			final POSCashJournalLine line = lines.get(lineIdx);

			final POSCashJournalLineType expectedType = expectedRow.getAsEnum("Type", POSCashJournalLineType.class);
			assertThat(line.getType()).as("Type of line %s", lineIdx).isEqualTo(expectedType);

			assertThat(line.getAmount().toBigDecimal()).as("Amount of line %s", lineIdx).isEqualByComparingTo(expectedRow.getAsBigDecimal("Amount"));

			final Optional<String> expectedDescription = expectedRow.getAsOptionalString("Description").map(StringUtils::trimBlankToNull);
			if (expectedDescription.isPresent())
			{
				assertThat(line.getDescription()).as("Description of line %s", lineIdx).isEqualTo(expectedDescription.get());
			}

			final Optional<I_C_Charge> expectedCharge = expectedRow.getAsOptionalIdentifier(I_C_Charge.COLUMNNAME_C_Charge_ID).map(identifier -> identifier.lookupNotNullIn(chargeTable));
			if (expectedCharge.isPresent())
			{
				assertThat(line.getDescription()).as("Description of line %s (charge name)", lineIdx).isEqualTo(expectedCharge.get().getName());
			}
		}
	}

	private POSCashJournal getCurrentCashJournalOrThrow(@NonNull final String terminalIdentifier)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		return posService.getCurrentCashJournal(posTerminalId)
				.orElseThrow(() -> new AdempiereException("No open cash journal for POS terminal " + terminalIdentifier));
	}
}
