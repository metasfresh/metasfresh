package de.metas.cucumber.stepdefs.allocation;

import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.C_AllocationLine_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;

import java.sql.Timestamp;

@RequiredArgsConstructor
public class C_AllocationHdr_StepDef
{
	@NonNull private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final C_AllocationHdr_StepDefData allocationTable;

	@And("create and complete manual payment allocations")
	public void createAndCompleteManualAllocations(DataTable table)
	{
		DataTableRows.of(table)
				.groupBy("C_AllocationHdr_ID")
				.forEach(this::createAndCompleteSingleManualAllocation);
	}

	private void createAndCompleteSingleManualAllocation(final String identifierStr, final DataTableRows rows)
	{
		trxManager.runInThreadInheritedTrx(() -> createAndCompleteSingleManualAllocation0(identifierStr, rows));
	}

	private void createAndCompleteSingleManualAllocation0(final String identifierStr, final DataTableRows rows)
	{
		final StepDefDataIdentifier identifier = StepDefDataIdentifier.ofString(identifierStr);

		final DataTableRow firstRow = rows.getFirstRow();
		final Timestamp dateTrx = firstRow.getAsOptionalLocalDateTimestamp("DateTrx").orElseGet(SystemTime::asDayTimestamp);
		CurrencyId currencyId = null;

		final OrgId orgId = OrgId.MAIN;
		final C_AllocationHdr_Builder builder = allocationBL.newBuilder()
				.orgId(orgId)
				// .currencyId(...)
				.dateTrx(dateTrx)
				.dateAcct(dateTrx)
				.manual(true); // flag it as manually created by user

		for (final DataTableRow row : rows.toList())
		{
			final Money amount = row.getAsOptionalMoney("Amount", moneyService::getCurrencyIdByCurrencyCode).orElse(null);
			final Money discountAmt = row.getAsOptionalMoney("DiscountAmt", moneyService::getCurrencyIdByCurrencyCode).orElse(null);
			final Money writeOffAmt = row.getAsOptionalMoney("WriteOffAmt", moneyService::getCurrencyIdByCurrencyCode).orElse(null);
			final Money overUnderAmt = row.getAsOptionalMoney("OverUnderAmt", moneyService::getCurrencyIdByCurrencyCode).orElse(null);
			final CurrencyId lineCurrencyId = Money.getCommonCurrencyIdOfAll(amount, discountAmt, writeOffAmt, overUnderAmt);

			if (currencyId == null)
			{
				currencyId = lineCurrencyId;
				builder.currencyId(currencyId);
			}
			else if (!CurrencyId.equals(currencyId, lineCurrencyId))
			{
				throw new AdempiereException("Mixing more than one currency is not supported.");
			}

			final C_AllocationLine_Builder lineBuilder = builder.addLine()
					.orgId(orgId);

			//
			// Amounts
			if (amount != null)
			{
				lineBuilder.amount(amount.toBigDecimal());
			}
			if (discountAmt != null)
			{
				lineBuilder.discountAmt(discountAmt.toBigDecimal());
			}
			if (writeOffAmt != null)
			{
				lineBuilder.writeOffAmt(writeOffAmt.toBigDecimal());
			}
			if (overUnderAmt != null)
			{
				lineBuilder.overUnderAmt(overUnderAmt.toBigDecimal());
			}

			row.getAsOptionalIdentifier("C_BPartner_ID")
					.map(bpartnerTable::getId)
					.ifPresent(lineBuilder::bpartnerId);
			row.getAsOptionalIdentifier("C_Invoice_ID")
					.map(invoiceTable::getId)
					.ifPresent(lineBuilder::invoiceId);
			row.getAsOptionalIdentifier("C_Payment_ID")
					.map(paymentTable::getId)
					.ifPresent(lineBuilder::paymentId);
		}

		final I_C_AllocationHdr allocationHdr = builder.createAndComplete();
		allocationTable.putOrReplaceIfSameId(identifier, allocationHdr);
	}

}
