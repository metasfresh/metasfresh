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

package de.metas.cucumber.stepdefs.order;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceLine_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Split-Payment iter-3 Cucumber scenarios (TC1, TC2, TC7, TC8, TC9).
 *
 * <p>Provides:
 * <ul>
 *   <li>Receipt creation (M_InOut + M_InOutLine) directly linked to a PO and order line,
 *       then completed — triggering {@code recomputeDeliverySteps}.</li>
 *   <li>M_MatchInv creation linking receipt line to invoice line (required before
 *       invoice completion so {@code DeliveryPrepaymentAllocationService} can
 *       traverse the M_MatchInv chain).</li>
 *   <li>Prepayment payment AvailableAmt assertion.</li>
 *   <li>Order pay-schedule delivery sub-row assertions (BaseAmt, M_InOut_ID, C_Invoice_ID, Status, count).</li>
 * </ul>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
// TODO delete me
@Deprecated
@RequiredArgsConstructor
public class SplitPaymentIter3_StepDef
{
	// Services (field-level, matches metasfresh convention)
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	// StepDefData tables (injected by PicoContainer)
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_InOut_StepDefData inOutTable;
	@NonNull private final M_InOutLine_StepDefData inOutLineTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_InvoiceLine_StepDefData invoiceLineTable;
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;

	// -----------------------------------------------------------------------
	// Receipt creation
	// -----------------------------------------------------------------------

	/**
	 * Creates a purchase receipt (M_InOut + M_InOutLine) directly linked to the given order
	 * and order line, then completes it.
	 *
	 * <p>This is the iter-3 substitute for the full receipt-schedule + HU pipeline.
	 * It is intentionally low-ceremony and used only in Split-Payment iter-3 scenarios.
	 * Completing the receipt triggers the {@code M_InOut_DeliveryStep} model interceptor
	 * which calls {@code OrderPayScheduleDeliveryService.recomputeDeliverySteps(orderId)}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns <b>C_Order_ID</b> — (required, identifier-ref) the purchase order<br>
	 * <b>C_OrderLine_ID</b> — (required, identifier-ref) the order line to receive<br>
	 * <b>MovementQty</b> — (required) quantity received (units, no UOM conversion)<br>
	 * @cucumber.example <pre>
	 * When iter3 purchase receipt 'r1' is created and completed:
	 *   | C_Order_ID | C_OrderLine_ID | MovementQty |
	 *   | lcOrder    | lcOrderL1      | 400         |
	 * </pre>
	 */
	@When("^iter3 purchase receipt '(.*)' is created and completed:$")
	public void createAndCompleteReceipt(
			@NonNull final String receiptIdentifier,
			@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();

		final I_C_Order order = row.getAsIdentifier(I_M_InOut.COLUMNNAME_C_Order_ID).lookupNotNullIn(orderTable);
		final I_C_OrderLine orderLine = row.getAsIdentifier(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID).lookupNotNullIn(orderLineTable);
		final BigDecimal movementQty = row.getAsBigDecimal(I_M_InOutLine.COLUMNNAME_MovementQty);

		// Determine warehouse: use the column from the row if provided, otherwise fall back to the order's warehouse.
		final WarehouseId warehouseId = row.getAsOptionalIdentifier(I_M_InOut.COLUMNNAME_M_Warehouse_ID)
				.map(id -> WarehouseId.ofRepoId(warehouseTable.get(id).getM_Warehouse_ID()))
				.orElseGet(() -> WarehouseId.ofRepoId(order.getM_Warehouse_ID()));

		final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseId);

		// Create the receipt header
		final I_M_InOut inOut = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		inOut.setC_Order_ID(order.getC_Order_ID());
		inOut.setC_BPartner_ID(order.getC_BPartner_ID());
		inOut.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
		inOut.setM_Warehouse_ID(warehouseId.getRepoId());
		inOut.setIsSOTrx(false);
		inOut.setMovementType("V+"); // Vendor receipt / goods in
		final DocTypeId receiptDocTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.MaterialReceipt)
				.isSOTrx(false)
				.adClientId(order.getAD_Client_ID())
				.adOrgId(order.getAD_Org_ID())
				.build());
		inOut.setC_DocType_ID(receiptDocTypeId.getRepoId());
		inOut.setMovementDate(de.metas.common.util.time.SystemTime.asTimestamp());
		inOut.setDateAcct(de.metas.common.util.time.SystemTime.asTimestamp());
		inOut.setDeliveryRule("A");       // Availability
		inOut.setDeliveryViaRule("S");    // Shipper
		inOut.setFreightCostRule("I");    // Ignore
		inOut.setPriorityRule("5");       // Medium
		InterfaceWrapperHelper.save(inOut);

		// Create the receipt line
		final I_M_InOutLine inOutLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		inOutLine.setC_Order_ID(order.getC_Order_ID());
		inOutLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		inOutLine.setM_Product_ID(orderLine.getM_Product_ID());
		inOutLine.setM_Locator_ID(locatorId.getRepoId());
		inOutLine.setMovementQty(movementQty);
		inOutLine.setQtyEntered(movementQty);
		inOutLine.setC_UOM_ID(orderLine.getC_UOM_ID());
		inOutLine.setLine(10);
		InterfaceWrapperHelper.save(inOutLine);

		// Complete (triggers recomputeDeliverySteps via M_InOut_DeliveryStep interceptor)
		documentBL.processEx(inOut, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		InterfaceWrapperHelper.refresh(inOut);
		InterfaceWrapperHelper.refresh(inOutLine);

		// Register in StepDefData
		final StepDefDataIdentifier receiptId = StepDefDataIdentifier.ofString(receiptIdentifier);
		inOutTable.putOrReplace(receiptId, inOut);

		// Register the receipt line under a predictable alias: "<receiptIdentifier>_line1"
		inOutLineTable.putOrReplace(StepDefDataIdentifier.ofString(receiptIdentifier + "_line1"), inOutLine);
	}

	// -----------------------------------------------------------------------
	// M_MatchInv creation
	// -----------------------------------------------------------------------

	/**
	 * Creates a {@code M_MatchInv} record linking the given invoice line to the first line
	 * of the given receipt.
	 *
	 * <p>Must be called <em>before</em> completing the invoice so that
	 * {@code DeliveryPrepaymentAllocationService.computeMatchedReceiptValueWithTax}
	 * can traverse the M_MatchInv chain on AFTER_COMPLETE.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns <b>C_InvoiceLine_ID</b> — (required, identifier-ref) the invoice line<br>
	 * <b>M_InOut_ID</b> — (required, identifier-ref) the receipt whose first line is matched<br>
	 * <b>Qty</b> — (required) matched quantity<br>
	 * @cucumber.example <pre>
	 * And iter3 M_MatchInv is created:
	 *   | C_InvoiceLine_ID | M_InOut_ID | Qty |
	 *   | inv1L1           | r1         | 400 |
	 * </pre>
	 */
	@And("iter3 M_MatchInv is created:")
	public void createMatchInv(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createSingleMatchInv);
	}

	private void createSingleMatchInv(@NonNull final DataTableRow row)
	{
		final I_C_InvoiceLine invoiceLine = row.getAsIdentifier(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID).lookupNotNullIn(invoiceLineTable);
		final I_M_InOut receipt = row.getAsIdentifier(I_M_MatchInv.COLUMNNAME_M_InOut_ID).lookupNotNullIn(inOutTable);
		final BigDecimal qty = row.getAsBigDecimal(I_M_MatchInv.COLUMNNAME_Qty);

		final I_M_InOutLine inOutLine = loadFirstInOutLine(InOutId.ofRepoId(receipt.getM_InOut_ID()));

		final I_M_MatchInv matchInv = InterfaceWrapperHelper.newInstance(I_M_MatchInv.class);
		matchInv.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
		matchInv.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
		matchInv.setM_InOut_ID(receipt.getM_InOut_ID());
		matchInv.setM_InOutLine_ID(inOutLine.getM_InOutLine_ID());
		matchInv.setM_Product_ID(inOutLine.getM_Product_ID());
		matchInv.setQty(qty);
		matchInv.setDateTrx(receipt.getMovementDate());
		matchInv.setIsSOTrx(false);
		InterfaceWrapperHelper.save(matchInv);
	}

	private I_M_InOutLine loadFirstInOutLine(@NonNull final InOutId inOutId)
	{
		return queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOutId.getRepoId())
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_InOutLine.COLUMN_Line)
				.create()
				.firstNotNull(I_M_InOutLine.class);
	}

	// -----------------------------------------------------------------------
	// Payment AvailableAmt assertion
	// -----------------------------------------------------------------------

	/**
	 * Asserts the remaining available amount on the given prepayment payment.
	 *
	 * <p>AvailableAmt = PayAmt − Σ allocated (via active C_AllocationLine rows).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example <pre>
	 * Then the payment 'lcPayment' has AvailableAmt 11053.92
	 * </pre>
	 */
	@Then("^the payment '(.*)' has AvailableAmt (.*)$")
	public void assertPaymentAvailableAmt(
			@NonNull final String paymentIdentifier,
			@NonNull final String expectedAvailableAmtStr)
	{
		final PaymentId paymentId = paymentTable.getId(paymentIdentifier);
		final BigDecimal expected = new BigDecimal(expectedAvailableAmtStr);
		final BigDecimal rawActual = paymentDAO.getAvailableAmount(paymentId);
		// paymentAvailable() returns negative for AP (C_Payment_v negates PayAmt for AP).
		// Negate for AP to compare as a positive "available capacity" value.
		final I_C_Payment payment = paymentDAO.getById(paymentId);
		final BigDecimal actual = payment.isReceipt() ? rawActual : rawActual.negate();
		assertThat(actual)
				.as("AvailableAmt for payment '" + paymentIdentifier + "'")
				.isEqualByComparingTo(expected);
	}

	// -----------------------------------------------------------------------
	// C_OrderPaySchedule delivery sub-row assertions (iter-3 columns)
	// -----------------------------------------------------------------------

	/**
	 * Asserts iter-3-specific columns (BaseAmt, DueAmt, M_InOut_ID, C_Invoice_ID, Status) on the
	 * Delivery sub-rows of the pay schedule for the given order.
	 *
	 * <p>DataTable rows are matched by {@code M_InOut_ID} (identifier-ref); use literal {@code null}
	 * for the remainder row (M_InOut_ID = 0).
	 * This step does NOT enforce row count — use
	 * {@link #assertDeliveryRowCount(String, int)} for that.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns <b>M_InOut_ID</b> — (required) receipt identifier-ref or literal {@code null} for the remainder row<br>
	 * <b>BaseAmt</b> — (optional) expected base amount<br>
	 * <b>DueAmt</b> — (optional) expected due amount<br>
	 * <b>Status</b> — (optional) expected status code: PR=Pending, WP=Awaiting_Pay, P=Paid<br>
	 * <b>C_Invoice_ID</b> — (optional) expected matched invoice (identifier-ref) or literal {@code null}<br>
	 * @cucumber.example <pre>
	 * Then the order identified by lcOrder has following delivery sub-rows:
	 *   | M_InOut_ID | BaseAmt  | DueAmt   | Status | C_Invoice_ID |
	 *   | r1         | 31808.00 | 22265.60 | WP     | inv1         |
	 *   | null       | 36846.40 | 25792.48 | PR     | null         |
	 * </pre>
	 */
	@And("^the order identified by (.*) has following delivery sub-rows:$")
	public void assertDeliverySubRows(
			@NonNull final String orderIdentifier,
			@NonNull final DataTable dataTable)
	{
		final OrderId orderId = orderTable.getId(orderIdentifier);
		final List<I_C_OrderPaySchedule> deliveryRows = loadDeliveryRows(orderId);

		DataTableRows.of(dataTable).forEach(row -> assertSingleDeliverySubRow(row, deliveryRows));
	}

	private void assertSingleDeliverySubRow(
			@NonNull final DataTableRow row,
			@NonNull final List<I_C_OrderPaySchedule> deliveryRows)
	{
		final I_C_OrderPaySchedule matchedRow = findDeliveryRow(row, deliveryRows);
		final SoftAssertions softly = new SoftAssertions();
		final String context = "M_InOut_ID=" + matchedRow.getM_InOut_ID();

		row.getAsOptionalBigDecimal(I_C_OrderPaySchedule.COLUMNNAME_BaseAmt)
				.ifPresent(expected -> softly.assertThat(matchedRow.getBaseAmt())
						.as("BaseAmt for " + context)
						.isEqualByComparingTo(expected));

		row.getAsOptionalBigDecimal(I_C_OrderPaySchedule.COLUMNNAME_DueAmt)
				.ifPresent(expected -> softly.assertThat(matchedRow.getDueAmt())
						.as("DueAmt for " + context)
						.isEqualByComparingTo(expected));

		row.getAsOptionalString(I_C_OrderPaySchedule.COLUMNNAME_Status)
				.ifPresent(expected -> softly.assertThat(matchedRow.getStatus())
						.as("Status for " + context)
						.isEqualTo(expected));

		row.getAsOptionalIdentifier(I_C_OrderPaySchedule.COLUMNNAME_C_Invoice_ID)
				.ifPresent(identifier -> {
					final InvoiceId actualInvoiceId = InvoiceId.ofRepoIdOrNull(matchedRow.getC_Invoice_ID());

					if (identifier.isNullPlaceholder())
					{
						softly.assertThat(actualInvoiceId)
								.as("C_Invoice_ID should be not set for " + context)
								.isNull();
					}
					else
					{
						final InvoiceId expectedInvoiceId = invoiceTable.getId(identifier);
						softly.assertThat(actualInvoiceId)
								.as("C_Invoice_ID for " + context)
								.isEqualTo(expectedInvoiceId);
					}
				});

		row.getAsOptionalIdentifier(I_C_OrderPaySchedule.COLUMNNAME_M_InOut_ID)
				.ifPresent(identifier -> {
					final InOutId actualInOutId = InOutId.ofRepoIdOrNull(matchedRow.getM_InOut_ID());
					if (identifier.isNullPlaceholder())
					{
						softly.assertThat(actualInOutId)
								.as("M_InOut_ID should be not set for " + context)
								.isNull();
					}
					else
					{
						final InOutId expectedInOutId = inOutTable.getId(identifier);
						softly.assertThat(actualInOutId)
								.as("M_InOut_ID for " + context)
								.isEqualTo(expectedInOutId);
					}
				});

		softly.assertAll();
	}

	@NonNull
	private I_C_OrderPaySchedule findDeliveryRow(
			@NonNull final DataTableRow row,
			@NonNull final List<I_C_OrderPaySchedule> deliveryRows)
	{
		final String rawInOutId = row.getAsOptionalString(I_C_OrderPaySchedule.COLUMNNAME_M_InOut_ID).orElse(null);

		if (rawInOutId == null || DataTableUtil.isNullPlaceholder(rawInOutId))
		{
			// Remainder row: M_InOut_ID = 0
			return deliveryRows.stream()
					.filter(r -> r.getM_InOut_ID() == 0)
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No remainder delivery row (M_InOut_ID=0) found in pay schedule"));
		}
		else
		{
			final int expectedInOutId = inOutTable.getId(StepDefDataIdentifier.ofString(rawInOutId)).getRepoId();
			return deliveryRows.stream()
					.filter(r -> r.getM_InOut_ID() == expectedInOutId)
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No delivery sub-row found for M_InOut_ID=" + expectedInOutId));
		}
	}

	/**
	 * Asserts the exact count of delivery sub-rows (including remainder row) for the given order.
	 */
	@Then("^the order identified by (.*) has exactly (\\d+) delivery sub-rows$")
	public void assertDeliveryRowCount(
			@NonNull final String orderIdentifier,
			final int expectedCount)
	{
		final OrderId orderId = orderTable.getId(orderIdentifier);
		final List<I_C_OrderPaySchedule> rows = loadDeliveryRows(orderId);
		assertThat(rows)
				.as("delivery sub-row count for order '" + orderIdentifier + "'")
				.hasSize(expectedCount);
	}

	/**
	 * Asserts that no remainder row (M_InOut_ID=0) exists for the given order's delivery schedule
	 * (over-delivery scenario where Σ receipt.with_tax ≥ order.GrandTotal).
	 *
	 */
	@Then("^the order identified by (.*) has no remainder delivery sub-row$")
	public void assertNoRemainderRow(@NonNull final String orderIdentifier)
	{
		final OrderId orderId = orderTable.getId(orderIdentifier);
		final List<I_C_OrderPaySchedule> rows = loadDeliveryRows(orderId);
		final boolean hasRemainder = rows.stream().anyMatch(r -> r.getM_InOut_ID() == 0);
		assertThat(hasRemainder)
				.as("Expected no remainder row for order '" + orderIdentifier + "'")
				.isFalse();
	}

	// -----------------------------------------------------------------------
	// Private helpers
	// -----------------------------------------------------------------------

	/**
	 * Loads all non-LC (Delivery) {@code C_OrderPaySchedule} rows for the given order,
	 * refreshing each record from the DB to ensure current state.
	 *
	 * <p>The LC row is identified by {@code ReferenceDateType = 'LC'} and is excluded.
	 */
	@NonNull
	private List<I_C_OrderPaySchedule> loadDeliveryRows(@NonNull final OrderId orderId)
	{
		final List<I_C_OrderPaySchedule> rows = queryBL.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId.getRepoId())
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(
						I_C_OrderPaySchedule.COLUMNNAME_ReferenceDateType,
						ReferenceDateType.LetterOfCreditDate.getCode())
				.create()
				.list();
		rows.forEach(InterfaceWrapperHelper::refresh);
		return rows;
	}
}
