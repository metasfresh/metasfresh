package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.organization.OrgId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import java.util.LinkedHashMap;
import java.util.Map;

public class CreateBPartnerCommand
{
	private static final String DEFAULT_COUNTRY_CODE = "DE";

	@NonNull private final IBPartnerDAO bpartnerDAO;
	@NonNull private final IBPGroupDAO bpGroupDAO;
	@NonNull private final ICountryDAO countryDAO;
	@NonNull private final ILocationDAO locationDAO;

	@NonNull private final MasterdataContext context;
	@NonNull private final Identifier identifier;
	@NonNull private final JsonCreateBPartnerRequest request;

	@Builder
	private CreateBPartnerCommand(
			@NonNull final IBPartnerDAO bpartnerDAO,
			@NonNull final IBPGroupDAO bpGroupDAO,
			@NonNull final ICountryDAO countryDAO,
			@NonNull final ILocationDAO locationDAO,
			@NonNull final MasterdataContext context,
			@NonNull final Identifier identifier,
			@NonNull final JsonCreateBPartnerRequest request)
	{
		this.bpartnerDAO = bpartnerDAO;
		this.bpGroupDAO = bpGroupDAO;
		this.countryDAO = countryDAO;
		this.locationDAO = locationDAO;
		this.context = context;
		this.identifier = identifier;
		this.request = request;
	}

	public JsonCreateBPartnerResponse execute()
	{
		final String code = CoalesceUtil.coalesceNotNull(
				StringUtils.trimBlankToNull(request.getBpartnerCode()),
				identifier.toUniqueString());
		final String name = CoalesceUtil.coalesceNotNull(StringUtils.trimBlankToNull(request.getName()), code);

		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setAD_Org_ID(OrgId.MAIN.getRepoId());
		bpartner.setValue(code);
		bpartner.setName(name);
		bpartner.setIsCompany(true);
		bpartner.setCompanyName(name);
		bpartner.setC_BP_Group_ID(getBPGroupId().getRepoId());
		bpartner.setIsCustomer(request.isCustomer());
		bpartner.setIsVendor(request.isVendor());
		bpartnerDAO.save(bpartner);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		context.putIdentifier(identifier, bpartnerId);

		final JsonCreateBPartnerResponse.JsonCreateBPartnerResponseBuilder response = JsonCreateBPartnerResponse.builder()
				.id(bpartnerId)
				.bpartnerCode(code);

		if (request.getLocations() == null || request.getLocations().isEmpty())
		{
			final BPartnerLocationId bpLocationId = createBPLocation(JsonCreateBPartnerRequest.Location.builder().build(), bpartnerId, true);
			context.putIdentifier(Identifier.ofString(identifier.getAsString() + MasterdataContext.SINGLE_BP_LOCATION_SUFFIX), bpLocationId);
			response.bpartnerLocationId(bpLocationId.getRepoId());
		}
		else
		{
			final Map<String, JsonCreateBPartnerResponse.Location> responseLocations = new LinkedHashMap<>();
			boolean isFirstLocation = true;
			for (final Map.Entry<String, JsonCreateBPartnerRequest.Location> entry : request.getLocations().entrySet())
			{
				final BPartnerLocationId bpLocationId = createBPLocation(entry.getValue(), bpartnerId, isFirstLocation);
				isFirstLocation = false;

				context.putIdentifier(Identifier.ofString(entry.getKey()), bpLocationId);
				responseLocations.put(entry.getKey(), JsonCreateBPartnerResponse.Location.builder().id(bpLocationId.getRepoId()).build());
			}
			response.locations(responseLocations);
		}

		return response.build();
	}

	private BPGroupId getBPGroupId()
	{
		if (request.getBpGroup() != null)
		{
			return context.getId(request.getBpGroup(), BPGroupId.class);
		}

		final I_C_BP_Group defaultGroup = bpGroupDAO.getDefaultByClientId(ClientId.METASFRESH);
		if (defaultGroup == null)
		{
			throw new AdempiereException("No default BP group found; specify `bpGroup`");
		}
		return BPGroupId.ofRepoId(defaultGroup.getC_BP_Group_ID());
	}

	private BPartnerLocationId createBPLocation(
			@NonNull final JsonCreateBPartnerRequest.Location locationRequest,
			@NonNull final BPartnerId bpartnerId,
			final boolean isDefault)
	{
		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		bpLocation.setAD_Org_ID(OrgId.MAIN.getRepoId());
		bpLocation.setC_BPartner_ID(bpartnerId.getRepoId());
		bpLocation.setC_Location_ID(createLocation(locationRequest).getRepoId());
		bpLocation.setIsBillToDefault(isDefault);
		bpLocation.setIsShipToDefault(isDefault);
		bpLocation.setIsShipTo(true);
		bpartnerDAO.save(bpLocation);

		return BPartnerLocationId.ofRepoId(bpartnerId, bpLocation.getC_BPartner_Location_ID());
	}

	private LocationId createLocation(@NonNull final JsonCreateBPartnerRequest.Location locationRequest)
	{
		final CountryId countryId = countryDAO.getCountryIdByCountryCode(CoalesceUtil.coalesceNotNull(locationRequest.getCountryCode(), DEFAULT_COUNTRY_CODE));

		final I_C_Location location = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId.getRepoId());
		location.setAddress1(locationRequest.getAddress1());
		location.setPostal(locationRequest.getPostal());
		location.setCity(locationRequest.getCity());
		locationDAO.save(location);
		return LocationId.ofRepoId(location.getC_Location_ID());
	}
}
