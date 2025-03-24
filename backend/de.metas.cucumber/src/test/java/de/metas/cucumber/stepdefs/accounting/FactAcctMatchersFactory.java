package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import de.metas.acct.AccountConceptualName;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.money.MoneyService;
import de.metas.uom.IUOMDAO;
import io.cucumber.datatable.DataTable;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;

@Builder
public class FactAcctMatchersFactory
{
	@NonNull private final IUOMDAO uomDAO;
	@NonNull private final MoneyService moneyService;

	public FactAcctMatchers ofDataTable(@Nullable final DataTable table)
	{
		if (table == null || table.isEmpty())
		{
			return new FactAcctMatchers(ImmutableList.of());
		}
		
		final ImmutableList<FactAcctMatcher> matchers = DataTableRows.of(table)
				.stream()
				.map(this::toFactAcctMatcher)
				.collect(ImmutableList.toImmutableList());

		return new FactAcctMatchers(matchers);
	}

	private FactAcctMatcher toFactAcctMatcher(final DataTableRow row)
	{
		return FactAcctMatcher.builder()
				.row(row)
				.accountConceptualName(AccountConceptualName.ofString(row.getAsString(I_Fact_Acct.COLUMNNAME_AccountConceptualName)))
				.amtAcctDr(row.getAsOptionalBigDecimal(I_Fact_Acct.COLUMNNAME_AmtAcctDr).orElse(null))
				.amtAcctCr(row.getAsOptionalBigDecimal(I_Fact_Acct.COLUMNNAME_AmtAcctCr).orElse(null))
				.amtSourceDr(row.getAsOptionalMoney(I_Fact_Acct.COLUMNNAME_AmtSourceDr, moneyService::getCurrencyIdByCurrencyCode).orElse(null))
				.amtSourceCr(row.getAsOptionalMoney(I_Fact_Acct.COLUMNNAME_AmtSourceCr, moneyService::getCurrencyIdByCurrencyCode).orElse(null))
				.qty(row.getAsOptionalQuantity(I_Fact_Acct.COLUMNNAME_Qty, uomDAO::getByX12DE355).orElse(null))
				.build();
	}
}
