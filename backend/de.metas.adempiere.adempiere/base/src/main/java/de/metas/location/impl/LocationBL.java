package de.metas.location.impl;

import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationBL;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.Properties;

public class LocationBL implements ILocationBL
{
	private static final Logger logger = LogManager.getLogger(LocationBL.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_Location getRecordById(@NonNull final LocationId locationId)
	{
		return locationDAO.getById(locationId);
	}

	@Override
	public void validatePostal(final I_C_Location location)
	{
		addToCPostalIfValid(location);
		location.setIsPostalValidated(true);
	}

	private void addToCPostalIfValid(final I_C_Location location)
	{
		final String postal = location.getPostal();
		if (Check.isEmpty(postal, true))
		{
			logger.info("Empty postal code found [IGNORED]");
			return;
		}

		final CountryId countryId = CountryId.ofRepoId(location.getC_Country_ID());
		if (checkPostalExists(countryId, postal))
		{
			return; // OK
		}

		addToCPostal(location);
	}

	private boolean checkPostalExists(@NonNull final CountryId countryId, final String postal)
	{
		return queryBL
				.createQueryBuilder(I_C_Postal.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Postal.COLUMN_C_Country_ID, countryId)
				.filter(PostalQueryFilter.of(postal))
				.create()
				.anyMatch();
	}

	private void addToCPostal(@NonNull final I_C_Location location)
	{
		final I_C_Postal postal = InterfaceWrapperHelper.newInstance(I_C_Postal.class);
		postal.setPostal(location.getPostal());
		// postal.setPostal_Add(location.getPostal_Add());

		final int countryId = location.getC_Country_ID();
		if (countryId <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_Country_ID@: " + location);
		}
		postal.setC_Country_ID(countryId);

		//
		final int regionId = location.getC_Region_ID();
		if (regionId > 0)
		{
			postal.setC_Region_ID(regionId);
		}
		postal.setRegionName(location.getRegionName());

		//
		final int cityId = location.getC_City_ID();
		if (cityId > 0)
		{
			postal.setC_City_ID(cityId);
		}
		postal.setCity(location.getCity());
		InterfaceWrapperHelper.save(postal);
		logger.debug("Created a new C_Postal record for {}: {}", location, postal);
	}

	@Override
	public String toString(I_C_Location location)
	{
		return location.toString();
	}

	@Override
	public String mkAddress(final I_C_Location location)
	{
		final String bPartnerBlock = null;
		final String userBlock = null;
		final I_C_BPartner bPartner = null;
		return mkAddress(location, bPartner, bPartnerBlock, userBlock);
	}

	@Override
	public String mkAddress(final I_C_Location location, final I_C_BPartner bPartner, String bPartnerBlock, String userBlock)
	{
		final I_C_Country countryLocal = countryDAO.getDefault(Env.getCtx());
		final boolean isLocalAddress = location.getC_Country_ID() == countryLocal.getC_Country_ID();
		return mkAddress(location, isLocalAddress, bPartner, bPartnerBlock, userBlock);
	}

	public String mkAddress(
			I_C_Location location,
			boolean isLocalAddress,
			final I_C_BPartner bPartner,
			String bPartnerBlock,
			String userBlock)
	{
		final String adLanguage;
		final OrgId orgId;
		if (bPartner == null)
		{
			adLanguage = countryDAO.getDefault(Env.getCtx()).getAD_Language();
			orgId = Env.getOrgId();
		}
		else
		{
			adLanguage = bPartner.getAD_Language();
			orgId = OrgId.ofRepoId(bPartner.getAD_Org_ID());
		}

		return AddressBuilder.builder()
				.orgId(orgId)
				.adLanguage(adLanguage)
				.build()
				.buildAddressString(location, isLocalAddress, bPartnerBlock, userBlock);
	}

	@Override
	public I_C_Location duplicate(@NonNull final I_C_Location location)
	{
		final I_C_Location locationNew = InterfaceWrapperHelper.newInstance(I_C_Location.class, location);
		InterfaceWrapperHelper.copyValues(location, locationNew);
		InterfaceWrapperHelper.save(locationNew);
		return locationNew;
	}

}
