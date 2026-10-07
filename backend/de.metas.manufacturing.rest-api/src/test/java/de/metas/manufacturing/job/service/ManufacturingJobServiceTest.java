package de.metas.manufacturing.job.service;

import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ManufacturingJobServiceTest
{
	private ManufacturingJobService manufacturingJobService;
	private ISysConfigDAO sysConfigDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		this.manufacturingJobService = ManufacturingJobService.newInstanceForUnitTesting();
		this.sysConfigDAO = Services.get(ISysConfigDAO.class);
	}

	@Nested
	class getDefaultFilters
	{
		@Test
		void empty()
		{
			Assertions.assertThat(manufacturingJobService.getDefaultFilters().toSet()).isEmpty();
		}

		@Test
		void userPlant()
		{
			// IMPORTANT: set the value as plain string to also enforce the name of the enums are not changed on refactoring
			sysConfigDAO.setValue(ManufacturingJobService.SYSCONFIG_defaultFilters, "UserPlant", ClientAndOrgId.SYSTEM);

			Assertions.assertThat(manufacturingJobService.getDefaultFilters().toSet())
					.contains(ManufacturingJobDefaultFilter.UserPlant);
		}

		@Test
		void allEnumValues()
		{
			// IMPORTANT: set the value as plain string to also enforce the name of the enums are not changed on refactoring
			sysConfigDAO.setValue(ManufacturingJobService.SYSCONFIG_defaultFilters, "UserPlant, TodayDateStartSchedule", ClientAndOrgId.SYSTEM);

			Assertions.assertThat(manufacturingJobService.getDefaultFilters().toSet())
					.contains(ManufacturingJobDefaultFilter.UserPlant, ManufacturingJobDefaultFilter.TodayDateStartSchedule);
		}
	}
}