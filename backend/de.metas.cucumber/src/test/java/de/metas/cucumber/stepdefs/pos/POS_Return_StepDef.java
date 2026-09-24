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
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.pos.POSProduct;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.returns.POSReturnLine;
import de.metas.pos.returns.POSReturnRequest;
import de.metas.pos.returns.POSReturnResult;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for a POS product return: the cashier takes goods back at the till, via
 * {@link POSService#createReturn}, and its credit invoice candidate is priced at the till's own price for the
 * product (not the walk-in customer's own sales pricing system).
 */
@RequiredArgsConstructor
public class POS_Return_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final POSService posService = SpringContextHolder.instance.getBean(POSService.class);

	private final C_POS_StepDefData posTable;
	private final M_Product_StepDefData productTable;
	private final M_InOut_StepDefData inoutTable;

	/**
	 * Drives a POS product return end to end: reads the till's current price for each returned product the same
	 * way {@code POSProductsService} does (from the terminal's own price list, not the walk-in customer's), then
	 * hands it to {@link POSService#createReturn}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) returned product<br>
	 *   <b>Qty</b> — (required) returned quantity<br>
	 *   <b>UOM</b> — (required) {@code X12DE355} code the quantity is expressed in (e.g. {@code KGM})<br>
	 *   <b>OPT.M_InOut_ID</b> — (optional, first row only, identifier) alias for the resulting customer-return
	 *   {@code M_InOut}<br>
	 * @cucumber.depends StepDefData: C_POS_StepDefData, M_Product_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a product return is made at POS terminal till by metasfresh:
	 *   | M_Product_ID | Qty | UOM | OPT.M_InOut_ID |
	 *   | product      | 0.3 | KGM | return_1       |
	 * </pre>
	 */
	@And("^a product return is made at POS terminal (\\S+) by (\\S+):$")
	public void posProductReturn(
			@NonNull final String terminalIdentifier,
			@NonNull final String userLogin,
			@NonNull final DataTable dataTable)
	{
		final POSTerminalId posTerminalId = posTable.getId(StepDefDataIdentifier.ofString(terminalIdentifier));
		final UserId cashierId = StepDefUtil.getUserIdByLogin(userLogin);
		final POSTerminal terminal = posService.getPOSTerminalById(posTerminalId);

		final List<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());

		final ImmutableList.Builder<POSReturnLine> lines = ImmutableList.builder();
		for (final DataTableRow row : rows)
		{
			final ProductId productId = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID).lookupNotNullIdIn(productTable);
			final BigDecimal qty = row.getAsBigDecimal("Qty");
			final UomId uomId = uomDAO.getUomIdByX12DE355(row.getAsUOMCode("UOM"));

			// reads the till price the same way POSProductsService does: from the terminal's own price list
			final POSProduct posProduct = posService.getProducts(posTerminalId, SystemTime.asInstant(), null)
					.stream()
					.filter(product -> product.getId().equals(productId))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("Product is not offered at the POS terminal")
							.setParameter("M_Product_ID", productId)
							.setParameter("posTerminalId", posTerminalId));

			lines.add(POSReturnLine.builder()
					.productId(productId)
					.qty(Quantity.of(qty, uomDAO.getById(uomId)))
					.price(Money.of(posProduct.getPrice().getAsBigDecimal(), terminal.getCurrencyId()))
					.priceUomId(uomId)
					.build());
		}

		final POSReturnResult result = posService.createReturn(POSReturnRequest.builder()
				.posTerminalId(posTerminalId)
				.cashierId(cashierId)
				.externalId(UUID.randomUUID())
				.lines(lines.build())
				.build());

		final I_M_InOut returnRecord = InterfaceWrapperHelper.load(result.getReturnInOutId(), I_M_InOut.class);
		rows.get(0).getAsOptionalIdentifier("M_InOut_ID")
				.ifPresent(returnIdentifier -> inoutTable.putOrReplace(returnIdentifier, returnRecord));
	}

	/**
	 * Verifies the return's credit: the return itself is order-less (no sales order, no origin shipment line —
	 * a POS return is a fresh receipt, never a match against a prior sale), and its line carries exactly one
	 * invoice candidate priced at the given override price/rule, error state and tax rate.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the returned product, to locate its return line<br>
	 *   <b>PriceEntered_Override</b> — (optional) expected override price<br>
	 *   <b>InvoiceRule_Override</b> — (optional) expected override invoice rule code (e.g. {@code I})<br>
	 *   <b>IsError</b> — (optional) expected {@code IsError} flag<br>
	 *   <b>C_Tax_Rate</b> — (optional) expected {@code C_Tax.Rate} of the candidate's tax<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the return identified by return_1 was credited at the till price:
	 *   | M_Product_ID | PriceEntered_Override | InvoiceRule_Override | IsError | C_Tax_Rate |
	 *   | product      | 15.50                 | I                     | false   | 7          |
	 * </pre>
	 */
	@Then("^the return identified by (\\S+) was credited at the till price:$")
	public void assertReturnCreditedAtTillPrice(@NonNull final String returnIdentifier, @NonNull final DataTable dataTable)
	{
		final I_M_InOut returnRecord = inoutTable.get(StepDefDataIdentifier.ofString(returnIdentifier));
		assertThat(returnRecord.getC_Order_ID()).as("the return's C_Order_ID").isZero();

		DataTableRows.of(dataTable).forEach(row -> {
			final ProductId productId = row.getAsIdentifier(I_M_InOutLine.COLUMNNAME_M_Product_ID).lookupNotNullIdIn(productTable);

			final I_M_InOutLine returnLine = queryBL.createQueryBuilder(I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, returnRecord.getM_InOut_ID())
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_Product_ID, productId)
					.create()
					.firstOnlyNotNull(I_M_InOutLine.class);

			final de.metas.inout.model.I_M_InOutLine returnLineExt = InterfaceWrapperHelper.create(returnLine, de.metas.inout.model.I_M_InOutLine.class);
			assertThat(returnLineExt.getReturn_Origin_InOutLine_ID()).as("Return_Origin_InOutLine_ID").isZero();

			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidatesForInOutLine(returnLine);
			assertThat(invoiceCandidates).as("invoice candidates for the return line of product %s", productId).hasSize(1);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandidates.get(0);

			final SoftAssertions softly = new SoftAssertions();

			row.getAsOptionalBigDecimal("PriceEntered_Override")
					.ifPresent(expected -> softly.assertThat(invoiceCandidate.getPriceEntered_Override()).as("PriceEntered_Override").isEqualByComparingTo(expected));

			row.getAsOptionalString("InvoiceRule_Override")
					.ifPresent(expected -> softly.assertThat(invoiceCandidate.getInvoiceRule_Override()).as("InvoiceRule_Override").isEqualTo(expected));

			row.getAsOptionalBoolean("IsError")
					.ifPresent(expected -> softly.assertThat(invoiceCandidate.isError()).as("IsError").isEqualTo(expected));

			row.getAsOptionalBigDecimal("C_Tax_Rate")
					.ifPresent(expected -> {
						final I_C_Tax tax = InterfaceWrapperHelper.load(invoiceCandidate.getC_Tax_ID(), I_C_Tax.class);
						softly.assertThat(tax.getRate()).as("C_Tax rate").isEqualByComparingTo(expected);
					});

			softly.assertAll();
		});
	}
}
