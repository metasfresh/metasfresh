package de.metas.gplr.repository;

import com.google.common.annotations.VisibleForTesting;
import de.metas.gplr.model.GPLRBPartnerName;
import de.metas.gplr.model.GPLRIncotermsInfo;
import de.metas.gplr.model.GPLRReportSalesOrder;
import de.metas.organization.OrgId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_GPLR_Report_SalesOrder;

import javax.annotation.Nullable;

@UtilityClass
@VisibleForTesting
class GPLRReportSalesOrder_Mapper
{
	static void updateRecord(
			@NonNull final I_GPLR_Report_SalesOrder record,
			@NonNull final GPLRReportSalesOrder from,
			@NonNull final OrgId orgId)
	{
		record.setAD_Org_ID(orgId.getRepoId());
		record.setDocumentNo(from.getDocumentNo());
		record.setBPartnerValue(from.getCustomer().getCode());
		record.setBPartnerName(from.getCustomer().getName());
		record.setBPartner_VatId(from.getCustomer().getVatId());
		record.setFrameContractNo(from.getFrameContractNo());
		record.setPOReference(from.getPoReference());

		updateRecord_Incoterms(record, from.getIncoterms());
	}

	private static void updateRecord_Incoterms(final @NonNull I_GPLR_Report_SalesOrder record, final GPLRIncotermsInfo from)
	{
		record.setIncoterm_Code(from != null ? from.getCode() : null);
		record.setIncotermLocation(from != null ? from.getLocation() : null);
	}

	@VisibleForTesting
	static GPLRReportSalesOrder fromRecord(
			@NonNull final I_GPLR_Report_SalesOrder record)
	{
		return GPLRReportSalesOrder.builder()
				.documentNo(record.getDocumentNo())
				.customer(GPLRBPartnerName.builder()
						.code(record.getBPartnerValue())
						.name(record.getBPartnerName())
						.vatId(record.getBPartner_VatId())
						.build())
				.frameContractNo(record.getFrameContractNo())
				.poReference(record.getPOReference())
				.incoterms(extractIncoterms(record))
				.build();
	}

	@Nullable
	private GPLRIncotermsInfo extractIncoterms(final I_GPLR_Report_SalesOrder record)
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

}
