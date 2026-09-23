package de.metas.frontend_testing.masterdata;

import de.metas.common.util.time.SystemTime;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.CreatePriceListRequest;
import de.metas.pricing.service.CreatePriceListVersionRequest;
import de.metas.pricing.service.CreatePricingSystemRequest;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Shared record-creation for a fresh {@code M_PricingSystem} + {@code M_PriceList} + {@code M_PriceList_Version},
 * used by both {@link de.metas.frontend_testing.masterdata.bpartner.CreateBPartnerCommand} and
 * {@link de.metas.frontend_testing.masterdata.pos.CreatePOSTerminalCommand}. Neither caller's reuse/registration
 * policy lives here (e.g. whether a pricing system is shared via the {@link MasterdataContext}) — only the raw
 * three-record creation.
 */
public final class PricingSetupHelper
{
	private PricingSetupHelper()
	{
	}

	public static PricingSetupResult createPricingSystemAndPriceList(@NonNull final PricingSetupRequest request)
	{
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		final OrgId orgId = request.getOrgId();
		final String value = request.getValue();

		final PricingSystemId pricingSystemId = priceListDAO.createPricingSystem(CreatePricingSystemRequest.builder()
				.orgId(orgId)
				.value(value)
				.name(value)
				.build());

		final PriceListId priceListId = priceListDAO.createPriceList(CreatePriceListRequest.builder()
				.orgId(orgId)
				.pricingSystemId(pricingSystemId)
				.name(value)
				.currencyId(request.getCurrencyId())
				.countryId(request.getCountryId())
				.isTaxIncluded(request.isTaxIncluded())
				.isSOPriceList(request.isSoPriceList())
				.pricePrecision(2)
				.build());

		final PriceListVersionId priceListVersionId = priceListDAO.createPriceListVersion(CreatePriceListVersionRequest.builder()
				.orgId(orgId)
				.priceListId(priceListId)
				.validFrom(MasterdataContext.DEFAULT_ValidFrom.atStartOfDay(SystemTime.zoneId()).toInstant())
				.build());

		return PricingSetupResult.of(pricingSystemId, priceListId, priceListVersionId);
	}

	@Value
	@Builder
	public static class PricingSetupRequest
	{
		@NonNull OrgId orgId;
		@NonNull String value;
		@NonNull CurrencyId currencyId;
		@NonNull CountryId countryId;
		boolean isTaxIncluded;
		boolean isSoPriceList;
	}

	@Value(staticConstructor = "of")
	public static class PricingSetupResult
	{
		@NonNull PricingSystemId pricingSystemId;
		@NonNull PriceListId priceListId;
		@NonNull PriceListVersionId priceListVersionId;
	}
}
