package de.metas.location.impl;

import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationCreateRequest;
import de.metas.location.LocationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class LocationDAOTest
{
	private LocationDAO locationDAO;
	private CountryId countryId_DE;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		this.locationDAO = (LocationDAO)Services.get(ILocationDAO.class);

		this.countryId_DE = createCountry("DE");
	}

	@SuppressWarnings("SameParameterValue")
	private CountryId createCountry(@NonNull final String countryCode)
	{
		final I_C_Country record = newInstance(I_C_Country.class);
		record.setCountryCode(countryCode);
		POJOWrapper.setInstanceName(record, countryCode);
		saveRecord(record);
		return CountryId.ofRepoId(record.getC_Country_ID());
	}

	private LocationCreateRequest newLocationCreateRequestFullyPopulated()
	{
		return LocationCreateRequest.builder()
				.address1("address1")
				.address2("address2")
				.address3("address3")
				.address4("address4")
				//
				//.postalId()
				.postal("postal")
				.postalAdd("postalAdd")
				//
				.city("city")
				//
				//.regionId()
				.regionName("region")
				//
				.countryId(countryId_DE)
				//
				.poBox("poBox")
				//
				.build();
	}

	@Test
	void createOrReuseLocation()
	{
		final LocationCreateRequest request0 = newLocationCreateRequestFullyPopulated();

		final LocationId locationId = locationDAO.createOrReuseLocation(request0);
		final LocationId locationId2 = locationDAO.createOrReuseLocation(request0.toBuilder().existingLocationId(locationId).build());
		assertThat(locationId2).isEqualTo(locationId);
	}
}