package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

public class CreateBPartnerCommand
{
	@NonNull private final IQueryBL queryBL;
	@NonNull private final IBPGroupDAO bpGroupDAO;
	@NonNull private final ICountryDAO countryDAO;
	@NonNull private final ILocationDAO locationDAO;

	@NonNull private final MasterdataContext context;
	@NonNull private final Identifier identifier;
	@NonNull private final JsonCreateBPartnerRequest request;

	@Builder
	private CreateBPartnerCommand(
			@NonNull final IQueryBL queryBL,
			@NonNull final IBPGroupDAO bpGroupDAO,
			@NonNull final ICountryDAO countryDAO,
			@NonNull final ILocationDAO locationDAO,
			@NonNull final MasterdataContext context,
			@NonNull final Identifier identifier,
			@NonNull final JsonCreateBPartnerRequest request)
	{
		this.queryBL = queryBL;
		this.bpGroupDAO = bpGroupDAO;
		this.countryDAO = countryDAO;
		this.locationDAO = locationDAO;
		this.context = context;
		this.identifier = identifier;
		this.request = request;
	}

	public JsonCreateBPartnerResponse execute()
	{
		final String code = CoalesceUtil.coalesceNotNull(request.getCode(), identifier.getAsString());
		final String name = CoalesceUtil.coalesceNotNull(request.getName(), code);

		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setAD_Org_ID(OrgId.MAIN.getRepoId());
		bpartner.setValue(code);
		bpartner.setName(name);
		bpartner.setIsCompany(true);
		bpartner.setCompanyName(name);
		bpartner.setC_BP_Group_ID(getBPGroupId().getRepoId());
		bpartner.setIsCustomer(request.isCustomer());
		bpartner.setIsVendor(request.isVendor());
		InterfaceWrapperHelper.saveRecord(bpartner);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		context.putIdentifier(identifier, bpartnerId);

		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		bpLocation.setAD_Org_ID(OrgId.MAIN.getRepoId());
		bpLocation.setC_BPartner_ID(bpartnerId.getRepoId());
		bpLocation.setC_Location_ID(createLocation().getC_Location_ID());
		bpLocation.setIsBillToDefault(true);
		bpLocation.setIsShipToDefault(true);
		bpLocation.setIsShipTo(true);
		InterfaceWrapperHelper.saveRecord(bpLocation);

		return JsonCreateBPartnerResponse.builder()
				.id(bpartnerId.getRepoId())
				.code(code)
				.locationId(bpLocation.getC_BPartner_Location_ID())
				.build();
	}

	private BPGroupId getBPGroupId()
	{
		if (request.getGroup() == null)
		{
			final I_C_BP_Group defaultGroup = bpGroupDAO.getDefaultByClientId(ClientId.METASFRESH);
			if (defaultGroup == null)
			{
				throw new AdempiereException("No default BP group found; specify `group`");
			}
			return BPGroupId.ofRepoId(defaultGroup.getC_BP_Group_ID());
		}

		final BPGroupId bpGroupId = queryBL.createQueryBuilder(I_C_BP_Group.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Group.COLUMNNAME_Name, request.getGroup())
				.create()
				.firstIdOnly(BPGroupId::ofRepoIdOrNull);
		if (bpGroupId == null)
		{
			throw new AdempiereException("No BP group found with name `" + request.getGroup() + "`");
		}
		return bpGroupId;
	}

	private I_C_Location createLocation()
	{
		final JsonCreateBPartnerRequest.Location locationRequest = request.getLocation();

		final CountryId countryId;
		if (locationRequest != null && locationRequest.getCountryCode() != null)
		{
			countryId = countryDAO.getCountryIdByCountryCode(locationRequest.getCountryCode());
		}
		else
		{
			countryId = countryDAO.getDefaultCountryId();
		}

		final I_C_Location location = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId.getRepoId());
		if (locationRequest != null)
		{
			location.setAddress1(locationRequest.getAddress1());
			location.setPostal(locationRequest.getPostal());
			location.setCity(locationRequest.getCity());
		}
		locationDAO.save(location);
		return location;
	}
}
