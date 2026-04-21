package de.metas.cucumber.stepdefs.tax_declaration;

import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import lombok.NonNull;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

class TaxReportRowRepository
{
	public ImmutableList<TaxReportRow> list(
			@NonNull final OrgId orgId,
			@NonNull final TaxId taxId,
			@NonNull final LocalDate dateFrom,
			@NonNull final LocalDate dateTo,
			@Nullable final String level)
	{
		final String sql = "SELECT level, vatcode, accountname, taxname,"
				+ "        netamt, taxamt, netamt_sum, taxamt_sum,"
				+ "        currency, source_currency, documentno, bpartnername"
				+ " FROM de_metas_acct.report_taxaccounts("
				+ "      p_ad_org_id      => ?,"
				+ "      p_account_id     => NULL,"
				+ "      p_c_vat_code_id  => NULL,"
				+ "      p_datefrom       => ?::date,"
				+ "      p_dateto         => ?::date,"
				+ "      p_isshowdetails  => 'Y',"
				+ "      p_level          => ?,"
				+ "      p_ad_language    => 'de_DE',"
				+ "      p_c_tax_id       => ?"
				+ " )";

		return DB.retrieveRowsOutOfTrx(
				sql,
				Arrays.asList(orgId, dateFrom, dateTo, level, taxId),
				TaxReportRowRepository::toTaxReportRow);
	}

	private static TaxReportRow toTaxReportRow(@NonNull final ResultSet rs) throws SQLException
	{
		final CurrencyCode acctCurrency = CurrencyCode.ofThreeLetterCode(rs.getString("currency"));
		final CurrencyCode sourceCurrency = CurrencyCode.ofThreeLetterCode(rs.getString("source_currency"));
		return TaxReportRow.builder()
				.level(rs.getString("level"))
				.vatCode(rs.getString("vatcode"))
				.accountName(rs.getString("accountname"))
				.taxName(rs.getString("taxname"))
				.taxAmt(amountOrNull(rs.getBigDecimal("taxamt"), acctCurrency))
				.netAmt(amountOrNull(rs.getBigDecimal("netamt"), sourceCurrency))
				.taxAmtSum(amountOrNull(rs.getBigDecimal("taxamt_sum"), acctCurrency))
				.netAmtSum(amountOrNull(rs.getBigDecimal("netamt_sum"), sourceCurrency))
				.documentNo(rs.getString("documentno"))
				.bpartnerName(rs.getString("bpartnername"))
				.build();
	}

	@Nullable
	private static Amount amountOrNull(@Nullable final BigDecimal value, @NonNull final CurrencyCode currencyCode)
	{
		return value != null ? Amount.of(value, currencyCode) : null;
	}
}
