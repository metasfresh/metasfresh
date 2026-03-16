package de.metas.acct.account_info.bpartner_balance;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.MoneyService;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.service.ClientId;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;

class BPartnerBalanceViewDataService
{
	private final IFactAcctBL factAcctBL;
	private final IAcctSchemaBL acctSchemaBL;
	private final MoneyService moneyService;
	private final ElementValueService elementValueService;

	@Builder
	private BPartnerBalanceViewDataService(
			@NonNull final IFactAcctBL factAcctBL,
			@NonNull final IAcctSchemaBL acctSchemaBL,
			@NonNull final MoneyService moneyService,
			@NonNull final ElementValueService elementValueService)
	{
		this.factAcctBL = factAcctBL;
		this.acctSchemaBL = acctSchemaBL;
		this.moneyService = moneyService;
		this.elementValueService = elementValueService;
	}

	BPartnerBalanceViewData getData(@Nullable final DocumentFilter filter)
	{
		return BPartnerBalanceViewData.builder()
				.viewDataService(this)
				.filter(filter)
				.build();
	}

	ImmutableList<BPartnerBalanceRow> loadRows(@Nullable final DocumentFilter filter)
	{
		if (filter == null)
		{
			return ImmutableList.of();
		}

		final BPartnerId bpartnerId = filter.getParameterValueAsRepoIdOrNull(BPartnerBalanceFilterHelper.PARAM_C_BPartner_ID, BPartnerId::ofRepoIdOrNull);
		if (bpartnerId == null)
		{
			return ImmutableList.of();
		}

		final AcctSchema acctSchema = acctSchemaBL.getPrimaryAcctSchema(ClientId.METASFRESH);
		final CurrencyCode acctCurrencyCode = moneyService.getCurrencyCodeByCurrencyId(acctSchema.getCurrencyId());

		final FactAcctQuery.FactAcctQueryBuilder queryBuilder = FactAcctQuery.builder()
				.acctSchemaId(acctSchema.getId())
				.bpartnerIds(InSetPredicate.only(bpartnerId));

		final Instant dateFrom = filter.getParameterValueAsInstantOrNull(BPartnerBalanceFilterHelper.PARAM_DateAcct);
		final Instant dateTo = filter.getParameterValueToAsInstantOrNull(BPartnerBalanceFilterHelper.PARAM_DateAcct);
		if (dateFrom != null)
		{
			queryBuilder.dateAcctGreaterOrEqualsTo(dateFrom);
		}
		if (dateTo != null)
		{
			queryBuilder.dateAcctLessOrEqualsTo(dateTo);
		}

		final FactAcctQuery query = queryBuilder.build();

		return computeRunningBalances(
				factAcctBL.stream(query),
				acctCurrencyCode);
	}

	@VisibleForTesting
	ImmutableList<BPartnerBalanceRow> computeRunningBalances(
			@NonNull final java.util.stream.Stream<I_Fact_Acct> factAcctStream,
			@NonNull final CurrencyCode acctCurrencyCode)
	{
		final ImmutableList<I_Fact_Acct> sortedRecords = factAcctStream
				.sorted(Comparator.comparing(I_Fact_Acct::getDateAcct)
						.thenComparingInt(I_Fact_Acct::getFact_Acct_ID))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList.Builder<BPartnerBalanceRow> rows = ImmutableList.builder();
		BigDecimal runningBalance = BigDecimal.ZERO;

		for (final I_Fact_Acct record : sortedRecords)
		{
			final BigDecimal amtDr = record.getAmtAcctDr();
			final BigDecimal amtCr = record.getAmtAcctCr();
			runningBalance = runningBalance.add(amtDr).subtract(amtCr);

			rows.add(BPartnerBalanceRow.builder()
					.factAcctId(FactAcctId.ofRepoId(record.getFact_Acct_ID()))
					.dateAcct(toLocalDate(record.getDateAcct()))
					.accountCaption(getAccountCaption(ElementValueId.ofRepoId(record.getAccount_ID())))
					.documentNo(record.getDocumentNo())
					.description(record.getDescription())
					.amtAcctDr(amtDr)
					.amtAcctCr(amtCr)
					.runningBalance(runningBalance)
					.currencyCode(acctCurrencyCode)
					.adTableId(record.getAD_Table_ID())
					.recordId(record.getRecord_ID())
					.build());
		}

		return rows.build();
	}

	private ITranslatableString getAccountCaption(@NonNull final ElementValueId elementValueId)
	{
		final ElementValue ev = elementValueService.getById(elementValueId);
		return TranslatableStrings.anyLanguage(ev.getValue() + " " + ev.getName());
	}

	private static LocalDate toLocalDate(@NonNull final java.sql.Timestamp timestamp)
	{
		return timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
