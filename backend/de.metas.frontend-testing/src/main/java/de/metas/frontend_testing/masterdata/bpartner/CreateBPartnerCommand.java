package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.RandomGLNGenerator;
import de.metas.common.util.CoalesceUtil;
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
import de.metas.user.UserId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
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

		final String customCode = request.getBpartnerCode();

		final Identifier suggestedIdentifier = CoalesceUtil.coalesceSuppliersNotNull(
				() -> Identifier.ofNullableString(identifier),
				() -> Identifier.ofNullableString(customCode),
				() -> Identifier.unique("BP"));
		this.bpIdentifier = suggestedIdentifier;

		// NEW: Use custom bpartnerCode if provided (max 40 chars - MOST RESTRICTIVE!)

		if (Check.isNotBlank(customCode))
		{
			// Validate length - C_BPartner.Value has max 40 characters
			if (customCode.length() > 40)
			{
				throw new AdempiereException("bpartnerCode must not exceed 40 characters (got: " + customCode.length() + ")");
			}
			// Use custom code as-is
			this.bpValue = customCode;
		}
		else
		{
			this.bpValue = suggestedIdentifier.toUniqueString();
		}
	}

	public JsonCreateBPartnerResponse execute()
	{
		final PricingSystemId pricingSystemId = createPricingSystem();

		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setValue(bpValue);

		// Use custom name if provided (max 100 chars), otherwise use value
		final String customName = request.getName();
		if (Check.isNotBlank(customName) && customName.length() > 100)
		{
			throw new AdempiereException("bpartner name must not exceed 100 characters");
		}
		final String bpName = CoalesceUtil.coalesceNotNull(customName, bpValue);
		bpartner.setName(bpName);
		bpartner.setIsCompany(true);
		bpartner.setCompanyName(bpName);
		bpartner.setC_BP_Group_ID(bpGroupId.getRepoId());
		bpartner.setIsVendor(request.isVendor());
		bpartner.setIsCustomer(request.isCustomer());
		bpartner.setAD_Org_ID(orgId.getRepoId());
		bpartner.setDeliveryRule(DeliveryRule.FORCE.getCode());

		// Set pricing system based on vendor/customer flags
		if (request.isCustomer())
		{
			bpartner.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId)); // Sales pricing system
		}
		if (request.isVendor())
		{
			bpartner.setPO_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId)); // Purchase pricing system
		}

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

			@NonNull final Identifier bpLocationIdentifier = Identifier.ofString(bpIdentifier.getAsString() + MasterdataContext.SINGLE_BP_LOCATION_SUFFIX);
			context.putIdentifier(bpLocationIdentifier, singleBPLocationId);
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

		// Create contacts (AD_User records) if provided
		final Map<String, JsonCreateBPartnerResponse.Contact> responseContacts;
		if (request.getContacts() != null && !request.getContacts().isEmpty())
		{
			responseContacts = new HashMap<>();
			for (final String contactIdentifierStr : request.getContacts().keySet())
			{
				final JsonCreateBPartnerRequest.Contact contactRequest = request.getContacts().get(contactIdentifierStr);
				final I_AD_User contactRecord = createContact(contactRequest, bpartnerId);

				@NonNull final Identifier contactIdentifier = Identifier.ofString(contactIdentifierStr);
				final UserId contactId = UserId.ofRepoId(contactRecord.getAD_User_ID());
				context.putIdentifier(contactIdentifier, contactId);

				responseContacts.put(contactIdentifierStr, JsonCreateBPartnerResponse.Contact.builder()
						.id(contactId.getRepoId())
						.firstName(contactRecord.getFirstname())
						.lastName(contactRecord.getLastname())
						.email(contactRecord.getEMail())
						.build());
			}
		}
		else
		{
			responseContacts = null;
		}

		return JsonCreateBPartnerResponse.builder()
				.id(bpartnerId)
				.bpartnerCode(bpartner.getValue())
				.bpartnerLocationId(singleBPLocationId != null ? singleBPLocationId.getRepoId() : null)
				.gln(singleGLN)
				.locations(responseLocations)
				.contacts(responseContacts)
				.build();
	}

	private I_C_BPartner_Location createBPLocation(
			@NonNull final JsonCreateBPartnerRequest.Location request,
			@NonNull final BPartnerId bpartnerId,
			final boolean isDefault)
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

	private I_AD_User createContact(
			@NonNull final JsonCreateBPartnerRequest.Contact request,
			@NonNull final BPartnerId bpartnerId)
	{
		final I_AD_User contactRecord = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		contactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		contactRecord.setAD_Org_ID(orgId.getRepoId());

		// Set name - use lastName or firstName, or generate a default
		final String firstName = StringUtils.trimBlankToNull(request.getFirstName());
		final String lastName = StringUtils.trimBlankToNull(request.getLastName());

		if (lastName != null)
		{
			contactRecord.setLastname(lastName);
			contactRecord.setName(lastName + (firstName != null ? ", " + firstName : ""));
		}
		else if (firstName != null)
		{
			contactRecord.setName(firstName);
		}
		else
		{
			contactRecord.setName("Contact-" + System.currentTimeMillis());
		}

		if (firstName != null)
		{
			contactRecord.setFirstname(firstName);
		}

		final String email = StringUtils.trimBlankToNull(request.getEmail());
		if (email != null)
		{
			contactRecord.setEMail(email);
		}

		final String phone = StringUtils.trimBlankToNull(request.getPhone());
		if (phone != null)
		{
			contactRecord.setPhone(phone);
		}

		final String description = StringUtils.trimBlankToNull(request.getDescription());
		if (description != null)
		{
			contactRecord.setDescription(description);
		}

		InterfaceWrapperHelper.saveRecord(contactRecord);
		return contactRecord;
	}

	@Nullable
	private GLN toGLN(@Nullable final String glnStr)
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

		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID());
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
		priceList.setIsSOPriceList(request.isSoPriceList());
		priceList.setC_Country_ID(countryId.getRepoId());
		InterfaceWrapperHelper.saveRecord(priceList);

		final I_M_PriceList_Version plv = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		plv.setM_PriceList_ID(priceList.getM_PriceList_ID());
		plv.setAD_Org_ID(priceList.getAD_Org_ID());
		plv.setM_PriceList_ID(priceList.getM_PriceList_ID());
		plv.setValidFrom(Timestamp.from(validFrom));
		saveRecord(plv);

		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID());
		context.putIdentifier(Identifier.unique("PLV"), priceListVersionId);

		return pricingSystemId;
	}
}
