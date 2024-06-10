/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.contract.flatrate.model;

import de.metas.bpartner.department.BPartnerDepartment;
import de.metas.bpartner.department.BPartnerDepartmentId;
import de.metas.calendar.Period;
import de.metas.calendar.PeriodId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntry;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetail;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetailId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryRepo;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryService;
import de.metas.organization.OrgId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.uom.UomId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;

public class DataEntryDetailsRowsLoaderTest
{

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void loadTest()
	{
		//Prepare Mock objects
		final LookupDataSource departmentLookup = Mockito.mock(LookupDataSource.class);
		final LookupValue departmentLookupValue = Mockito.mock(LookupValue.class);

		final LookupValue uomLookupValue = Mockito.mock(LookupValue.class);
		final LookupDataSource uomLookup = Mockito.mock(LookupDataSource.class);

		final FlatrateDataEntryService entryService = Mockito.mock(FlatrateDataEntryService.class);
		final FlatrateDataEntryRepo entryRepo = Mockito.mock(FlatrateDataEntryRepo.class);

		final FlatrateDataEntryId flatrateDataEntryId = FlatrateDataEntryId.ofRepoId(FlatrateTermId.ofRepoId(10), 10);

		final BPartnerDepartmentId bPartnerDepartmentId = BPartnerDepartmentId.ofRepoId(10, 10);
		final BPartnerDepartment bPartnerDepartment = BPartnerDepartment.builder()
				.id(bPartnerDepartmentId)
				.searchKey("value")
				.name("name")
				.build();

		final Period period = new Period(
				PeriodId.ofRepoId(YearId.ofRepoId(2), 3),
				OrgId.ofRepoId(10),
				ZonedDateTime.now(),
				ZonedDateTime.now());

		final FlatrateDataEntryDetail detail = FlatrateDataEntryDetail.builder()
				.id(FlatrateDataEntryDetailId.ofRepoId(flatrateDataEntryId, 20))
				.asiId(AttributeSetInstanceId.NONE)
				.bPartnerDepartment(bPartnerDepartment)
				.build();
		final FlatrateDataEntry entry = FlatrateDataEntry.builder()
				.processed(false)
				.id(flatrateDataEntryId)
				.uomId(UomId.ofRepoId(30))
				.detail(detail)
				.period(period)
				.build();

		//when specific methods are called on the mock objects, then return specific values
		Mockito.when(entryRepo.getById(flatrateDataEntryId)).thenReturn(entry);
		Mockito.when(entryService.addMissingDetails(entry)).thenReturn(entry);
		Mockito.when(departmentLookup.findById(Mockito.any())).thenReturn(departmentLookupValue);
		Mockito.when(uomLookup.findById(Mockito.any())).thenReturn(uomLookupValue);

		//Call the method to be tested
		final DataEntryDetailsRowsLoader dataEntryDetailsRowsLoader = DataEntryDetailsRowsLoader
				.builder()
				.flatrateDataEntryId(flatrateDataEntryId)
				.flatrateDataEntryService(entryService)
				.uomLookup(uomLookup)
				.departmentLookup(departmentLookup)
				.flatrateDataEntryRepo(entryRepo)
				.build();
		final DataEntryDetailsRowsData result = dataEntryDetailsRowsLoader.load();

		//Verify the method calls on mock objects
		Mockito.verify(entryRepo, Mockito.times(1)).getById(flatrateDataEntryId);
		Mockito.verify(entryService, Mockito.times(1)).addMissingDetails(Mockito.any());
		Mockito.verify(departmentLookup, Mockito.times(1)).findById(Mockito.any());
		Mockito.verify(uomLookup, Mockito.times(1)).findById(Mockito.any());

		//Assert the result
		assertThat(result).isNotNull();

	}
}