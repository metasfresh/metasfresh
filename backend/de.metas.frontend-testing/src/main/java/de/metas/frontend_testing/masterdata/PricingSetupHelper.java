package de.metas.frontend_testing.masterdata;

import de.metas.common.util.time.SystemTime;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

import java.sql.Timestamp;

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
		final OrgId orgId = request.getOrgId();
		final String value = request.getValue();

		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);
		pricingSystem.setValue(value);
		pricingSystem.setName(value);
		pricingSystem.setAD_Org_ID(orgId.getRepoId());
		InterfaceWrapperHelper.saveRecord(pricingSystem);
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		priceList.setM_PricingSystem_ID(pricingSystemId.getRepoId());
		priceList.setAD_Org_ID(orgId.getRepoId());
		priceList.setC_Currency_ID(request.getCurrencyId().getRepoId());
		priceList.setName(value);
		priceList.setIsTaxIncluded(request.isTaxIncluded());
		priceList.setPricePrecision(2);
		priceList.setIsActive(true);
		priceList.setIsSOPriceList(request.isSoPriceList());
		priceList.setC_Country_ID(request.getCountryId().getRepoId());
		InterfaceWrapperHelper.saveRecord(priceList);
		final PriceListId priceListId = PriceListId.ofRepoId(priceList.getM_PriceList_ID());

		final I_M_PriceList_Version plv = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		plv.setM_PriceList_ID(priceListId.getRepoId());
		plv.setAD_Org_ID(orgId.getRepoId());
		plv.setValidFrom(Timestamp.from(MasterdataContext.DEFAULT_ValidFrom.atStartOfDay(SystemTime.zoneId()).toInstant()));
		InterfaceWrapperHelper.saveRecord(plv);
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID());

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
