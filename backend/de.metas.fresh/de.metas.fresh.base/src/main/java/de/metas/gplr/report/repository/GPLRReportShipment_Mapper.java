package de.metas.gplr.report.repository;

import com.google.common.annotations.VisibleForTesting;
import de.metas.gplr.report.model.GPLRBPartnerName;
import de.metas.gplr.report.model.GPLRIncotermsInfo;
import de.metas.gplr.report.model.GPLRReportShipment;
import de.metas.gplr.report.model.GPLRShipperRenderedString;
import de.metas.gplr.report.model.GPLRWarehouseName;
import de.metas.location.ICountryCodeFactory;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_GPLR_Report_Shipment;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.function.Function;

@UtilityClass
@VisibleForTesting
class GPLRReportShipment_Mapper
{
	static void updateRecord(
			@NonNull final I_GPLR_Report_Shipment record,
			@NonNull final GPLRReportShipment from,
			@NonNull final OrgId orgId,
			@NonNull final Function<OrgId, ZoneId> orgId2timeZoneMapper)
	{
		record.setAD_Org_ID(orgId.getRepoId());
		record.setDocumentNo(from.getDocumentNo());
		record.setShipTo_BPartnerValue(from.getShipTo().getCode());
		record.setShipTo_BPartnerName(from.getShipTo().getName());
		record.setShipTo_CountryCode(from.getShipToCountry() != null ? from.getShipToCountry().getAlpha2() : null);
		record.setIsDropShip(from.isDropShip());
		record.setWarehouseValue(from.getWarehouse().getCode());
		record.setWarehouseName(from.getWarehouse().getName());
		record.setWarehouseExternalId(from.getWarehouse().getExternalId());
		record.setIsB2B(from.isBackToBack());
		record.setMovementDate(from.getMovementDate().toTimestamp(orgId2timeZoneMapper));
		updateRecord_Incoterms(record, from.getIncoterms());
		record.setShippingInfo(from.getShipper() != null ? from.getShipper().toRenderedString() : null);
	}

	private static void updateRecord_Incoterms(final @NonNull I_GPLR_Report_Shipment record, final GPLRIncotermsInfo from)
	{
		record.setIncoterm_Code(from != null ? from.getCode() : null);
		record.setIncotermLocation(from != null ? from.getLocation() : null);
	}

	@VisibleForTesting
	static GPLRReportShipment fromRecord(
			@NonNull final I_GPLR_Report_Shipment record,
			@NonNull final Function<OrgId, ZoneId> orgId2timeZoneMapper,
			@NonNull final ICountryCodeFactory countryCodeFactory)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());

		return GPLRReportShipment.builder()
				.documentNo(record.getDocumentNo())
				.shipTo(GPLRBPartnerName.builder()
						.code(record.getShipTo_BPartnerValue())
						.name(record.getShipTo_BPartnerName())
						.vatId(null) // N/A
						.build())
				.isDropShip(record.isDropShip())
				.shipToCountry(StringUtils.trimBlankToOptional(record.getShipTo_CountryCode()).map(countryCodeFactory::getCountryCodeByAlpha2).orElse(null))
				.warehouse(GPLRWarehouseName.builder()
						.code(record.getWarehouseValue())
						.name(record.getWarehouseName())
						.externalId(record.getWarehouseExternalId())
						.build())
				.isBackToBack(record.isB2B())
				.movementDate(LocalDateAndOrgId.ofTimestamp(record.getMovementDate(), orgId, orgId2timeZoneMapper))
				.incoterms(extractIncoterms(record))
				.shipper(GPLRShipperRenderedString.ofNullableRenderedString(record.getShippingInfo()))
				.build();
	}

	@Nullable
	private static GPLRIncotermsInfo extractIncoterms(final I_GPLR_Report_Shipment record)
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
