package de.metas.gplr.report.repository;

import com.google.common.annotations.VisibleForTesting;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.gplr.report.model.GPLRReportLineItem;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_GPLR_Report_Line;

@UtilityClass
@VisibleForTesting
class GPLRReportLineItem_Mapper
{
	static void updateRecord(
			@NonNull final I_GPLR_Report_Line record,
			@NonNull final GPLRReportLineItem from,
			@NonNull final OrgId orgId,
			final int seqNo)
	{
		record.setAD_Org_ID(orgId.getRepoId());
		record.setSeqNo(seqNo);
		record.setDocumentNo(from.getDocumentNo());
		record.setLine(from.getLineCode());
		record.setDescription(from.getDescription());
		record.setC_UOM_ID(from.getQty() != null ? from.getQty().getUomId().getRepoId() : -1);
		record.setUOMSymbol(from.getQty() != null ? from.getQty().getUOMSymbol() : null);
		record.setQty(from.getQty() != null ? from.getQty().toBigDecimal() : null);

		final CurrencyCode localCurrency = from.getLocalCurrency();
		record.setLocalCurrencyCode(localCurrency != null ? localCurrency.toThreeLetterCode() : null);

		final CurrencyCode foreignCurrency = from.getForeignCurrency();
		record.setForeignCurrencyCode(foreignCurrency != null ? foreignCurrency.toThreeLetterCode() : null);

		record.setPrice_FC(from.getPriceFC() != null ? from.getPriceFC().toBigDecimal() : null);
		record.setAmount_FC(from.getAmountFC() != null ? from.getAmountFC().toBigDecimal() : null);
		record.setAmount_LC(from.getAmountLC() != null ? from.getAmountLC().toBigDecimal() : null);

		record.setBatch(from.getBatchNo());
	}

	@VisibleForTesting
	static GPLRReportLineItem fromRecord(
			@NonNull final I_GPLR_Report_Line record)
	{
		final CurrencyCode foreignCurrencyCode = StringUtils.trimBlankToOptional(record.getForeignCurrencyCode())
				.map(CurrencyCode::ofThreeLetterCode)
				.orElse(null);
		final CurrencyCode localCurrencyCode = StringUtils.trimBlankToOptional(record.getLocalCurrencyCode())
				.map(CurrencyCode::ofThreeLetterCode)
				.orElse(null);

		final UomId uomId = UomId.ofRepoIdOrNull(record.getC_UOM_ID());

		return GPLRReportLineItem.builder()
				.documentNo(record.getDocumentNo())
				.lineCode(record.getLine())
				.description(record.getDescription())
				.qty(uomId != null ? Quantitys.of(record.getQty(), uomId) : null)
				.priceFC(foreignCurrencyCode != null ? Amount.of(record.getPrice_FC(), foreignCurrencyCode) : null)
				.amountFC(foreignCurrencyCode != null ? Amount.of(record.getAmount_FC(), foreignCurrencyCode) : null)
				.amountLC(localCurrencyCode != null ? Amount.of(record.getAmount_LC(), localCurrencyCode) : null)
				.batchNo(StringUtils.trimBlankToNull(record.getBatch()))
				.build();
	}
}
