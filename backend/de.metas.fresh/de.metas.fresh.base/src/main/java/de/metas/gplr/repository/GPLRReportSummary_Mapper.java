package de.metas.gplr.repository;

import com.google.common.annotations.VisibleForTesting;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.gplr.model.GPLRReportSummary;
import de.metas.organization.OrgId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.compiere.model.I_GPLR_Report_Summary;

// @UtilityClass
@VisibleForTesting
class GPLRReportSummary_Mapper
{
	static void updateRecord(
			@NonNull final I_GPLR_Report_Summary record,
			@NonNull final GPLRReportSummary from,
			@NonNull final OrgId orgId)
	{
		record.setAD_Org_ID(orgId.getRepoId());
		record.setLocalCurrencyCode(from.getLocalCurrency().toThreeLetterCode());
		record.setForeignCurrencyCode(from.getForeignCurrency().toThreeLetterCode());
		record.setSales_LC(from.getSalesLC().toBigDecimal());
		record.setSales_FC(from.getSalesFC().toBigDecimal());
		record.setTaxes_LC(from.getTaxesLC().toBigDecimal());
		record.setEstimated_LC(from.getEstimatedLC().toBigDecimal());
		record.setEstimated_FC(from.getEstimatedFC().toBigDecimal());
		record.setCOGS_LC(from.getCogsLC().toBigDecimal());
		record.setCharges_LC(from.getChargesLC().toBigDecimal());
		record.setCharges_FC(from.getChargesFC().toBigDecimal());
		record.setProfitOrLoss_LC(from.getProfitOrLossLC().toBigDecimal());
		record.setProfitRate(from.getProfitRate().toBigDecimal());
	}

	@VisibleForTesting
	static GPLRReportSummary fromRecord(
			@NonNull final I_GPLR_Report_Summary record)
	{
		final CurrencyCode localCurrency = CurrencyCode.ofThreeLetterCode(record.getLocalCurrencyCode());
		final CurrencyCode foreignCurrency = CurrencyCode.ofThreeLetterCode(record.getForeignCurrencyCode());

		return GPLRReportSummary.builder()
				.localCurrency(localCurrency)
				.foreignCurrency(foreignCurrency)
				.salesLC(Amount.of(record.getSales_LC(), localCurrency))
				.salesFC(Amount.of(record.getSales_FC(), foreignCurrency))
				.taxesLC(Amount.of(record.getTaxes_LC(), localCurrency))
				.estimatedLC(Amount.of(record.getEstimated_LC(), localCurrency))
				.estimatedFC(Amount.of(record.getEstimated_FC(), foreignCurrency))
				.cogsLC(Amount.of(record.getCOGS_LC(), localCurrency))
				.chargesLC(Amount.of(record.getCharges_LC(), localCurrency))
				.chargesFC(Amount.of(record.getCharges_FC(), foreignCurrency))
				.profitOrLossLC(Amount.of(record.getProfitOrLoss_LC(), localCurrency))
				.profitRate(Percent.of(record.getProfitRate()))
				.build();
	}

}
