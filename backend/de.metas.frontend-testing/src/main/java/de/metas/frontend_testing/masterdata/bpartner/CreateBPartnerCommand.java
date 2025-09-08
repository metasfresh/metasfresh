package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.RandomGLNGenerator;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.order.DeliveryRule;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class CreateBPartnerCommand
{
	@NonNull private final RandomGLNGenerator randomGLNGenerator = new RandomGLNGenerator();
	@NonNull private final CurrencyRepository currencyRepository;

	@NonNull private final MasterdataContext context;
	@NonNull final JsonCreateBPartnerRequest request;

	@NonNull private final BPGroupId bpGroupId = MasterdataContext.BP_GROUP_ID;
	@NonNull private final OrgId orgId = MasterdataContext.ORG_ID;
	@NonNull private final CountryId countryId = MasterdataContext.COUNTRY_ID;
	@NonNull private final Identifier bpIdentifier;
	@NonNull private final String bpValue;

	@Builder
	private CreateBPartnerCommand(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final MasterdataContext context,
			@NonNull final JsonCreateBPartnerRequest request,
			@Nullable final String identifier)
	{
		this.currencyRepository = currencyRepository;
		this.context = context;
		this.request = request;

		final Identifier suggestedIdentifier = Identifier.ofNullableString(identifier);
		if (suggestedIdentifier == null)
		{
			this.bpIdentifier = Identifier.unique("BP");
			this.bpValue = bpIdentifier.getAsString();
		}
		else
		{
			this.bpIdentifier = suggestedIdentifier;
			this.bpValue = suggestedIdentifier.toUniqueString();
		}
	}

	public JsonCreateBPartnerResponse execute()
	{
		final PricingSystemId pricingSystemId = createPricingSystem();

		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setValue(bpValue);
		bpartner.setName(bpValue);
		bpartner.setIsCompany(true);
		bpartner.setCompanyName(bpValue);
		bpartner.setC_BP_Group_ID(bpGroupId.getRepoId());
		bpartner.setIsVendor(false);
		bpartner.setIsCustomer(true);
		bpartner.setAD_Org_ID(orgId.getRepoId());
		bpartner.setDeliveryRule(DeliveryRule.FORCE.getCode());
		bpartner.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId));
		InterfaceWrapperHelper.saveRecord(bpartner);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		context.putIdentifier(bpIdentifier, bpartnerId);

		final BPartnerLocationId singleBPLocationId;
		final GLN singleGLN;
		final Map<String, JsonCreateBPartnerResponse.Location> responseLocations;
		if (request.getLocations() == null || request.getLocations().isEmpty())
		{
			final I_C_BPartner_Location bpLocationRecord = createBPLocation(
					JsonCreateBPartnerRequest.Location.builder()
							.gln(request.getGln())
							.build(),
					bpartnerId,
					true
			);
			singleBPLocationId = BPartnerLocationId.ofRepoId(bpLocationRecord.getC_BPartner_ID(), bpLocationRecord.getC_BPartner_Location_ID());
			singleGLN = GLN.ofNullableString(bpLocationRecord.getGLN());
			responseLocations = null;

			context.putIdentifier(bpIdentifier, singleBPLocationId);
		}
		else
		{
			singleBPLocationId = null;
			singleGLN = null;
			responseLocations = new HashMap<>();

			boolean isFirstLocation = true;
			for (final String bpLocationIdentifierStr : request.getLocations().keySet())
			{
				final I_C_BPartner_Location bpLocationRecord = createBPLocation(
						request.getLocations().get(bpLocationIdentifierStr),
						bpartnerId,
						isFirstLocation
				);
				isFirstLocation = false;

				@NonNull final Identifier bpLocationIdentifier = Identifier.ofString(bpLocationIdentifierStr);
				final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoId(bpLocationRecord.getC_BPartner_ID(), bpLocationRecord.getC_BPartner_Location_ID());
				context.putIdentifier(bpLocationIdentifier, bpLocationId);

				responseLocations.put(bpLocationIdentifierStr, JsonCreateBPartnerResponse.Location.builder()
						.id(bpLocationId.getRepoId())
						.gln(GLN.ofNullableString(bpLocationRecord.getGLN()))
						.build());
			}
		}

		return JsonCreateBPartnerResponse.builder()
				.id(bpartnerId)
				.bpartnerCode(bpartner.getValue())
				.bpartnerLocationId(singleBPLocationId != null ? singleBPLocationId.getRepoId() : null)
				.gln(singleGLN)
				.locations(responseLocations)
				.build();
	}

	private I_C_BPartner_Location createBPLocation(
			@NonNull JsonCreateBPartnerRequest.Location request,
			@NonNull final BPartnerId bpartnerId,
			boolean isDefault)
	{
		final LocationId locationId = createLocation();

		final GLN gln = toGLN(request.getGln());

		final I_C_BPartner_Location bPartnerLocationRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		bPartnerLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bPartnerLocationRecord.setC_Location_ID(locationId.getRepoId());
		bPartnerLocationRecord.setIsBillToDefault(isDefault);
		bPartnerLocationRecord.setIsShipToDefault(isDefault);
		bPartnerLocationRecord.setIsShipTo(true);
		bPartnerLocationRecord.setGLN(gln != null ? gln.getCode() : null);
		InterfaceWrapperHelper.saveRecord(bPartnerLocationRecord);

		return bPartnerLocationRecord;
	}

	private GLN toGLN(final String glnStr)
	{
		final String glnStrNorm = StringUtils.trimBlankToNull(glnStr);
		if (glnStrNorm == null)
		{
			return null;
		}
		else if (glnStrNorm.equalsIgnoreCase("random"))
		{
			return randomGLNGenerator.next();
		}
		else
		{
			return GLN.ofString(glnStrNorm);
		}
	}

	private LocationId createLocation()
	{
		final I_C_Location locationRecord = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		locationRecord.setC_Country_ID(countryId.getRepoId());
		InterfaceWrapperHelper.saveRecord(locationRecord);
		return LocationId.ofRepoId(locationRecord.getC_Location_ID());
	}

	private PricingSystemId createPricingSystem()
	{
		final PricingSystemId existingPricingSystemId = context.getIdOfTypeIfUnique(PricingSystemId.class).orElse(null);
		if (existingPricingSystemId != null)
		{
			return existingPricingSystemId;
		}

		final Identifier pricingSystemIdentifier = Identifier.unique("PS");
		final String value = pricingSystemIdentifier.getAsString();

		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.EUR);
		final Instant validFrom = MasterdataContext.DEFAULT_ValidFrom.atStartOfDay(SystemTime.zoneId()).toInstant();

		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);
		pricingSystem.setValue(value);
		pricingSystem.setName(value);
		pricingSystem.setAD_Org_ID(orgId.getRepoId());
		InterfaceWrapperHelper.saveRecord(pricingSystem);
		PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID());
		context.putIdentifier(pricingSystemIdentifier, pricingSystemId);

		final I_M_PriceList priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		priceList.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		priceList.setAD_Org_ID(pricingSystem.getAD_Org_ID());
		priceList.setM_PricingSystem_ID(pricingSystemId.getRepoId());
		priceList.setC_Currency_ID(currencyId.getRepoId());
		priceList.setName(value);
		priceList.setIsTaxIncluded(false);
		priceList.setPricePrecision(2);
		priceList.setIsActive(true);
		priceList.setIsSOPriceList(true);
		priceList.setC_Country_ID(countryId.getRepoId());
		InterfaceWrapperHelper.saveRecord(priceList);

		final I_M_PriceList_Version plv = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		plv.setM_PriceList_ID(priceList.getM_PriceList_ID());
		plv.setAD_Org_ID(priceList.getAD_Org_ID());
		plv.setM_PriceList_ID(priceList.getM_PriceList_ID());
		plv.setValidFrom(Timestamp.from(validFrom));
		saveRecord(plv);
		PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID());
		context.putIdentifier(Identifier.unique("PLV"), priceListVersionId);

		return pricingSystemId;
	}
}
