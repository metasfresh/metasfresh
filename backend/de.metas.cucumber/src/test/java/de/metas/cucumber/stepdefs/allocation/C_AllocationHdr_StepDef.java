package de.metas.cucumber.stepdefs.allocation;

import de.metas.acct.api.FactAcctQuery;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.accounting.FactAcctMatchersFactory;
import de.metas.cucumber.stepdefs.accounting.FactAcctToTabularStringConverter;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;

import javax.annotation.Nullable;

import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.newFactAcctValidator;
import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.waitUtilPosted;

public class C_AllocationHdr_StepDef
{

	@NonNull private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	@NonNull private final C_AllocationHdr_StepDefData allocationHdrTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;

	@NonNull private final FactAcctMatchersFactory factAcctMatchersFactory;
	@NonNull private final FactAcctToTabularStringConverter factAcctTabularStringConverter;

	public C_AllocationHdr_StepDef(
			@NonNull final C_AllocationHdr_StepDefData allocationHdrTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable)
	{
		this.allocationHdrTable = allocationHdrTable;
		this.invoiceTable = invoiceTable;

		@NonNull final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		this.factAcctMatchersFactory = FactAcctMatchersFactory.builder()
				.uomDAO(uomDAO)
				.moneyService(moneyService)
				.build();
		this.factAcctTabularStringConverter = FactAcctToTabularStringConverter.builder()
				.uomDAO(uomDAO)
				.moneyService(moneyService)
				.bpartnerTable(bpartnerTable)
				.build();
	}

	@And("^Fact_Acct records are found for payment allocation (.*)$")
	public void validateFactAccts(
			@NonNull final String allocationHdrIdentifierStr,
			@Nullable final DataTable table) throws Throwable
	{
		final StepDefDataIdentifier allocationHdrIdentifier = StepDefDataIdentifier.ofString(allocationHdrIdentifierStr);
		final PaymentAllocationId allocationId = allocationHdrTable.getId(allocationHdrIdentifier);
		final TableRecordReference allocationRef = TableRecordReference.of(I_C_AllocationHdr.Table_Name, allocationId);

		waitUtilPosted(allocationRef);

		newFactAcctValidator()
				.factAcctMatchersFactory(factAcctMatchersFactory)
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.expectations(table)
				.query(FactAcctQuery.builder().recordRef(allocationRef).build())
				.validate();
	}

	@And("^no Fact_Acct records are found for payment allocation (.*)$")
	public void assertNoFactAccts(
			@NonNull final String allocationHdrIdentifierStr) throws Throwable
	{
		validateFactAccts(allocationHdrIdentifierStr, null);
	}

	@And("^allocate vendor invoice to customer credit memo$")
	public void allocateVendorInvoiceToCustomerCreditMemos(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::allocateVendorInvoiceToCustomerCreditMemo);
	}

	public void allocateVendorInvoiceToCustomerCreditMemo(@NonNull final DataTableRow row)
	{
		final InvoiceId vendorInvoiceId = row.getAsIdentifier("Vendor_Invoice_ID").lookupNotNullIdIn(invoiceTable);
		final InvoiceId customerCreditMemoId = row.getAsIdentifier("Customer_CreditMemo_ID").lookupNotNullIdIn(invoiceTable);
		final Money payAmt = row.getAsMoney("Amount", moneyService::getCurrencyIdByCurrencyCode);
		final Money discountAmt = row.getAsMoney("Discount", moneyService::getCurrencyIdByCurrencyCode);
		final Money writeOffAmt = row.getAsMoney("WriteOff", moneyService::getCurrencyIdByCurrencyCode);
		final Money overUnderAmt = row.getAsMoney("OverUnder", moneyService::getCurrencyIdByCurrencyCode);
		//final CurrencyId currencyId = Money.getCommonCurrencyIdOfAll(payAmt, discountAmt); TODO : keep in case I need it

		// Special case: Allocations of sales credit memos and purchase invoices must always be negative. See #20395
		final boolean negatePayableAllocationAmounts = true;

		// sync with de.metas.banking.payment.paymentallocation.service.AllocationLineCandidateSaver.saveCandidate_InvoiceToInvoice
		final I_C_AllocationHdr allocationHdr = allocationBL
				.newBuilder()
				//
				.addLine()
				.skipIfAllAmountsAreZero()

				.amount(payAmt.negateIf(negatePayableAllocationAmounts).toBigDecimal())
				.discountAmt(discountAmt.negateIf(negatePayableAllocationAmounts).toBigDecimal())
				.writeOffAmt(writeOffAmt.negateIf(negatePayableAllocationAmounts).toBigDecimal())
				.overUnderAmt(overUnderAmt.negateIf(negatePayableAllocationAmounts).toBigDecimal())
				.invoiceId(customerCreditMemoId)
				.getParent()
				//
				.addLine()
				.skipIfAllAmountsAreZero()
				//
				// Amounts
				.amount(payAmt.negate().toBigDecimal())
				.overUnderAmt(overUnderAmt.toBigDecimal())
				.invoiceId(vendorInvoiceId)
				.getParent()
				//
				.createAndComplete();

		row.getAsOptionalIdentifier("C_AllocationHdr_ID")
				.ifPresent(allocationIdentifier -> allocationHdrTable.putOrReplaceIfSameId(allocationIdentifier, allocationHdr));

	}
}
