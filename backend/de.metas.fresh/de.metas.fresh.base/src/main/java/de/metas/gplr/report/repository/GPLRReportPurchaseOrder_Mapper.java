package de.metas.gplr.report.repository;

import com.google.common.annotations.VisibleForTesting;
import de.metas.currency.CurrencyCode;
import de.metas.gplr.report.model.GPLRBPartnerName;
import de.metas.gplr.report.model.GPLRCurrencyInfo;
import de.metas.gplr.report.model.GPLRIncotermsInfo;
import de.metas.gplr.report.model.GPLRPaymentTermRenderedString;
import de.metas.gplr.report.model.GPLRReportPurchaseOrder;
import de.metas.organization.OrgId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_GPLR_Report_PurchaseOrder;

import javax.annotation.Nullable;

@UtilityClass
@VisibleForTesting
class GPLRReportPurchaseOrder_Mapper
{
	static void updateRecord(
			@NonNull final I_GPLR_Report_PurchaseOrder record,
			@NonNull final GPLRReportPurchaseOrder from,
			@NonNull final OrgId orgId)
	{
		record.setAD_Org_ID(orgId.getRepoId());
		record.setDocumentNo(from.getDocumentNo());
		record.setBPartnerValue(from.getPurchasedFrom().getCode());
		record.setBPartnerName(from.getPurchasedFrom().getName());
		record.setBPartner_VatId(from.getPurchasedFrom().getVatId());
		record.setVendorReferenceNo(from.getVendorReference());
		record.setPaymentTermInfo(from.getPaymentTerm().toRenderedString());
		updateRecord_Incoterms(record, from.getIncoterms());
		updateRecord_CurrencyInfo(record, from.getCurrencyInfo());
	}

	private static void updateRecord_Incoterms(final @NonNull I_GPLR_Report_PurchaseOrder record, final GPLRIncotermsInfo from)
	{
		record.setIncoterm_Code(from != null ? from.getCode() : null);
		record.setIncotermLocation(from != null ? from.getLocation() : null);
	}

	private static void updateRecord_CurrencyInfo(
			final @NonNull I_GPLR_Report_PurchaseOrder record,
			final @NonNull GPLRCurrencyInfo currencyInfo)
	{
		record.setCurrencyRate(currencyInfo.getCurrencyRate());
		record.setForeignCurrencyCode(currencyInfo.getForeignCurrency().toThreeLetterCode());
		record.setFEC_DocumentNo(currencyInfo.getFecDocumentNo());
	}

	@VisibleForTesting
	static GPLRReportPurchaseOrder fromRecord(
			@NonNull final I_GPLR_Report_PurchaseOrder record)
	{
		return GPLRReportPurchaseOrder.builder()
				.documentNo(record.getDocumentNo())
				.purchasedFrom(GPLRBPartnerName.builder()
						.code(record.getBPartnerValue())
						.name(record.getBPartnerName())
						.vatId(record.getBPartner_VatId())
						.build())
				.vendorReference(record.getVendorReferenceNo())
				.paymentTerm(GPLRPaymentTermRenderedString.ofRenderedString(record.getPaymentTermInfo()))
				.incoterms(extractIncoterms(record))
				.currencyInfo(extractCurrencyInfo(record))
				.build();
	}

	@Nullable
	private static GPLRIncotermsInfo extractIncoterms(final I_GPLR_Report_PurchaseOrder record)
	{
		final String code = StringUtils.trimBlankToNull(record.getIncoterm_Code());
		if (code == null)
		{
			return null;
		}

		return GPLRIncotermsInfo.builder()
				.code(code)
				.location(record.getIncotermLocation())
				.build();
	}

	private static GPLRCurrencyInfo extractCurrencyInfo(final @NonNull I_GPLR_Report_PurchaseOrder record)
	{
		return GPLRCurrencyInfo.builder()
				.currencyRate(record.getCurrencyRate())
				.foreignCurrency(CurrencyCode.ofThreeLetterCode(record.getForeignCurrencyCode()))
				.fecDocumentNo(record.getFEC_DocumentNo())
				.build();
	}

}
