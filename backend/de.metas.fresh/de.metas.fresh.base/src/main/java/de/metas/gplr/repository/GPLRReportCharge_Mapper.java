package de.metas.gplr.repository;

import com.google.common.annotations.VisibleForTesting;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.gplr.model.GPLRBPartnerName;
import de.metas.gplr.model.GPLRReportCharge;
import de.metas.organization.OrgId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.compiere.model.I_GPLR_Report_Charge;

public class GPLRReportCharge_Mapper
{
	static void updateRecord(
			@NonNull final I_GPLR_Report_Charge record,
			@NonNull final GPLRReportCharge from,
			@NonNull final OrgId orgId)
	{
		record.setAD_Org_ID(orgId.getRepoId());
		record.setOrderDocumentNo(from.getPurchaseOrderDocumentNo());
		record.setLineNo(from.getOrderLineNo());
		record.setOrderCostTypeName(from.getCostTypeName());
		record.setPayee_BPartnerValue(from.getVendor() != null ? from.getVendor().getCode() : null);
		record.setPayee_BPartnerName(from.getVendor() != null ? from.getVendor().getName() : null);

		record.setLocalCurrencyCode(from.getAmountLC() != null ? from.getAmountLC().getCurrencyCode().toThreeLetterCode() : null);
		record.setAmount_LC(from.getAmountLC() != null ? from.getAmountLC().toBigDecimal() : null);

		record.setForeignCurrencyCode(from.getAmountFC().getCurrencyCode().toThreeLetterCode());
		record.setAmount_FC(from.getAmountFC().toBigDecimal());
	}

	@VisibleForTesting
	static GPLRReportCharge fromRecord(
			@NonNull final I_GPLR_Report_Charge record)
	{
		final CurrencyCode foreignCurrencyCode = StringUtils.trimBlankToOptional(record.getForeignCurrencyCode())
				.map(CurrencyCode::ofThreeLetterCode)
				.orElse(null);
		final CurrencyCode localCurrencyCode = StringUtils.trimBlankToOptional(record.getLocalCurrencyCode())
				.map(CurrencyCode::ofThreeLetterCode)
				.orElse(null);

		return GPLRReportCharge.builder()
				.purchaseOrderDocumentNo(record.getOrderDocumentNo())
				.orderLineNo(record.getLineNo())
				.costTypeName(record.getOrderCostTypeName())
				.vendor(extractVendor(record))
				.amountFC(foreignCurrencyCode != null ? Amount.of(record.getAmount_FC(), foreignCurrencyCode) : null)
				.amountLC(localCurrencyCode != null ? Amount.of(record.getAmount_LC(), localCurrencyCode) : null)
				.build();
	}

	private static GPLRBPartnerName extractVendor(final @NonNull I_GPLR_Report_Charge record)
	{
		final String code = record.getPayee_BPartnerValue();
		final String name = record.getPayee_BPartnerName();
		if (code == null || name == null)
		{
			return null;
		}

		return GPLRBPartnerName.builder()
				.code(code)
				.name(name)
				.build();
	}
}
