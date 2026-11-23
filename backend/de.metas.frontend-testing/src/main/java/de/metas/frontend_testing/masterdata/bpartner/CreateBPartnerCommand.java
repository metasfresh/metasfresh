package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.RandomGLNGenerator;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.order.DeliveryRule;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Check;
import de.metas.util.Services;
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

/**
 * Creates a business partner, or - when the request's identifier already stands for a business partner of the masterdata
 * context (e.g. created by an earlier call) - updates its header with the values given in the request.
 * Locations and contacts are created only together with a new partner.
 * Masterdata is created and updated through the one createMasterdata endpoint; do not add per-entity update endpoints.
 */
public class CreateBPartnerCommand
{
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
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
		final BPartnerId existingBPartnerId = context.getOptionalId(bpIdentifier, BPartnerId.class).orElse(null);
		final boolean isNew = existingBPartnerId == null;
		if (!isNew)
		{
			assertUpdatable();
		}

		final I_C_BPartner bpartner = isNew
				? bpartnerDAO.newBPartner()
				: bpartnerDAO.getById(existingBPartnerId);
		setHeader(bpartner, isNew);
		bpartnerDAO.save(bpartner);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		if (!isNew)
		{
			return JsonCreateBPartnerResponse.builder()
					.id(bpartnerId)
					.bpartnerCode(bpartner.getValue())
					.build();
		}

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

	/**
	 * Locations and contacts are created only together with a new partner; the partner header can be created and updated.
	 */
	private void assertUpdatable()
	{
		if (request.getGln() != null || request.getLocations() != null || request.getContacts() != null)
		{
			throw new AdempiereException("Business partner " + bpIdentifier + " already exists: its header can be updated, but not gln, locations, contacts");
		}
	}

	/**
	 * Sets the partner header from the request: a new partner gets the defaults, an existing one only the fields given in the request;
	 * in both cases a customer or vendor without a pricing system gets one (its price list is a sales price list unless {@code isSoPriceList} says otherwise).
	 */
	private void setHeader(@NonNull final I_C_BPartner bpartner, final boolean isNew)
	{
		final String customName = request.getName();
		if (Check.isNotBlank(customName) && customName.length() > 100)
		{
			throw new AdempiereException("bpartner name must not exceed 100 characters");
		}

		if (isNew)
		{
			bpartner.setValue(bpValue);
			// Use custom name if provided (max 100 chars), otherwise use value
			final String bpName = CoalesceUtil.coalesceNotNull(customName, bpValue);
			bpartner.setName(bpName);
			bpartner.setIsCompany(true);
			bpartner.setCompanyName(bpName);
			bpartner.setC_BP_Group_ID(bpGroupId.getRepoId());
			bpartner.setAD_Org_ID(orgId.getRepoId());
			bpartner.setDeliveryRule(DeliveryRule.FORCE.getCode());
		}
		else
		{
			if (request.getBpartnerCode() != null)
			{
				bpartner.setValue(bpValue);
			}
			if (Check.isNotBlank(customName))
			{
				bpartner.setName(customName);
				bpartner.setCompanyName(customName);
			}
		}

		final GRAIRequired graiRequired = request.getGraiRequired();
		if (graiRequired != null)
		{
			bpartner.setGRAIRequired(graiRequired.getCode());
		}

		final Boolean isVendor = isNew ? CoalesceUtil.coalesceNotNull(request.getIsVendor(), false) : request.getIsVendor();
		final Boolean isCustomer = isNew ? CoalesceUtil.coalesceNotNull(request.getIsCustomer(), true) : request.getIsCustomer();
		if (isVendor != null)
		{
			bpartner.setIsVendor(isVendor);
		}
		if (isCustomer != null)
		{
			bpartner.setIsCustomer(isCustomer);
		}

		// A customer needs a sales pricing system and a vendor a purchase one; a side that has none yet gets it (on create and on update)
		final boolean needsSalesPricingSystem = bpartner.isCustomer() && bpartner.getM_PricingSystem_ID() <= 0;
		final boolean needsPurchasePricingSystem = bpartner.isVendor() && bpartner.getPO_PricingSystem_ID() <= 0;
		if (needsSalesPricingSystem || needsPurchasePricingSystem)
		{
			final PricingSystemId pricingSystemId = createPricingSystem();
			if (needsSalesPricingSystem)
			{
				bpartner.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId));
			}
			if (needsPurchasePricingSystem)
			{
				bpartner.setPO_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId));
			}
		}
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
		priceList.setIsSOPriceList(CoalesceUtil.coalesceNotNull(request.getIsSoPriceList(), true));
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
