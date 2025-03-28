package de.metas.cucumber.stepdefs.allocation;

import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.accounting.FactAcctMatchersFactory;
import de.metas.cucumber.stepdefs.accounting.FactAcctToTabularStringConverter;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TrxRunnable;

import java.math.BigDecimal;
import java.time.LocalDate;

public class C_AllocationHdr_StepDef
{

	final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	@NonNull
	private final C_AllocationHdr_StepDefData allocationHdrTable;
	@NonNull
	private final C_Invoice_StepDefData invoiceTable;

	// @NonNull private final FactAcctMatchersFactory factAcctMatchersFactory;
	// @NonNull private final FactAcctToTabularStringConverter factAcctTabularStringConverter;

	public C_AllocationHdr_StepDef(
			@NonNull final C_AllocationHdr_StepDefData allocationHdrTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable)
	{
		this.allocationHdrTable = allocationHdrTable;
		this.invoiceTable = invoiceTable;

		@NonNull
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		// this.factAcctMatchersFactory = FactAcctMatchersFactory.builder()
		// 		.uomDAO(uomDAO)
		// 		.moneyService(moneyService)
		// 		.build();
		// this.factAcctTabularStringConverter = FactAcctToTabularStringConverter.builder()
		// 		.uomDAO(uomDAO)
		// 		.moneyService(moneyService)
		// 		.bpartnerTable(bpartnerTable)
		// 		.build();

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
		final Money amountARC = row.getAsMoney("AmountARC", moneyService::getCurrencyIdByCurrencyCode);
		final Money amountAPI = row.getAsMoney("AmountAPI", moneyService::getCurrencyIdByCurrencyCode);

		final Money discountAmt = row.getAsOptionalMoney("Discount", moneyService::getCurrencyIdByCurrencyCode).orElse(null);
		//		final CurrencyId currencyId = Money.getCommonCurrencyIdOfAll(amount, discountAmt);

		final I_C_Invoice customerCreditMemo = row.getAsIdentifier("Customer_CreditMemo_ID").lookupNotNullIn(invoiceTable);

		final I_C_Invoice vendorInvoice = row.getAsIdentifier("Vendor_Invoice_ID").lookupNotNullIn(invoiceTable);
		trxManager.runInNewTrx(new TrxRunnable()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{

				final C_AllocationHdr_Builder allocationBuilder = allocationBL.newBuilder()

						.orgId(customerCreditMemo.getAD_Org_ID())
						.currencyId(amountARC.getCurrencyId())
						.dateTrx(LocalDate.now())
						.dateAcct(LocalDate.now())
						.manual(true); // flag it as manually created by user

				// Sales credit memo
				final BigDecimal overUnderAmt = amountAPI.add(amountARC).negate().toBigDecimal();
				allocationBuilder.addLine()
						.skipIfAllAmountsAreZero()
						//
						.orgId(customerCreditMemo.getAD_Org_ID())
						.bpartnerId(BPartnerId.ofRepoId(customerCreditMemo.getC_BPartner_ID()))
						//
						// Amounts
						.amount(amountAPI.toBigDecimal().negate())
						.discountAmt(discountAmt.toBigDecimal())
						.overUnderAmt(overUnderAmt)

						//
						.invoiceId(customerCreditMemoId);

				// Purchase customerCreditMemo
				allocationBuilder.addLine()
						.skipIfAllAmountsAreZero()
						//
						.orgId(vendorInvoice.getAD_Org_ID())
						.bpartnerId(BPartnerId.ofRepoId(vendorInvoice.getC_BPartner_ID()))
						//
						// Amounts
						.amount(amountAPI.toBigDecimal().negate())
						.overUnderAmt(BigDecimal.ZERO)
						//
						.invoiceId(vendorInvoiceId);

				final I_C_AllocationHdr allocationHdr = allocationBuilder.createAndComplete();

				row.getAsOptionalIdentifier("C_AllocationHdr_ID")
						.ifPresent(allocationIdentifier -> allocationHdrTable.putOrReplaceIfSameId(allocationIdentifier, allocationHdr));
			}
		});

	}

}
