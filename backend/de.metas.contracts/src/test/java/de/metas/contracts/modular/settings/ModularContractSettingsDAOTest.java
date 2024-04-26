/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.settings;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.I_ModCntr_Type;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static de.metas.contracts.modular.ComputingMethodType.INTERIM_CONTRACT;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

class ModularContractSettingsDAOTest
{

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void getByFlatrateTermId()
	{
		final I_C_Calendar calendarRecord = newInstance(I_C_Calendar.class);
		saveRecord(calendarRecord);

		final I_C_Year yearRecord = newInstance(I_C_Year.class);
		yearRecord.setC_Calendar_ID(calendarRecord.getC_Calendar_ID());
		saveRecord(yearRecord);

		final I_ModCntr_Settings settingsRecord = newInstance(I_ModCntr_Settings.class);
		settingsRecord.setC_Calendar(calendarRecord);
		settingsRecord.setC_Year_ID(yearRecord.getC_Year_ID());
		settingsRecord.setName("ModCntr_Settings");
		settingsRecord.setM_Raw_Product_ID(30);
		settingsRecord.setM_PricingSystem_ID(40);
		settingsRecord.setStorageCostStartDate(Timestamp.valueOf("2024-04-24 07:15:00"));
		saveRecord(settingsRecord);

		final I_ModCntr_Type typeRecord = newInstance(I_ModCntr_Type.class);
		typeRecord.setName("ModCntr_Settings");
		typeRecord.setModularContractHandlerType(INTERIM_CONTRACT.getCode());
		saveRecord(typeRecord);

		final I_ModCntr_Module moduleRecord = newInstance(I_ModCntr_Module.class);
		moduleRecord.setName("ModCntr_Module");
		moduleRecord.setModCntr_Settings_ID(settingsRecord.getModCntr_Settings_ID());
		moduleRecord.setModCntr_Type_ID(typeRecord.getModCntr_Type_ID());
		moduleRecord.setM_Product_ID(130);
		moduleRecord.setSeqNo(10);
		moduleRecord.setInvoicingGroup("Costs");
		saveRecord(moduleRecord);

		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setModCntr_Settings_ID(settingsRecord.getModCntr_Settings_ID());
		saveRecord(conditionsRecord);

		final I_C_Flatrate_Term contractRecord = newInstance(I_C_Flatrate_Term.class);
		contractRecord.setC_Flatrate_Conditions_ID(conditionsRecord.getC_Flatrate_Conditions_ID());
		saveRecord(contractRecord);

		final ModularContractSettings settings = new ModularContractSettingsDAO().getByFlatrateTermIdOrNull(FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID()));

		assertThat(settings).isNotNull();
		assertThat(settings.getYearAndCalendarId()).isEqualTo(YearAndCalendarId.ofRepoId(yearRecord.getC_Year_ID(), calendarRecord.getC_Calendar_ID()));
		assertThat(settings.getId().getRepoId()).isEqualTo(settingsRecord.getModCntr_Settings_ID());
		assertThat(settings.getModuleConfigs()).hasSize(1);

		final ModuleConfig moduleConfig = settings.getModuleConfigs().get(0);
		assertThat(moduleConfig.getSeqNo().toInt()).isEqualTo(10);
		assertThat(moduleConfig.getProductId().getRepoId()).isEqualTo(130);

		assertThat(moduleConfig.getComputingMethodType()).isEqualTo(INTERIM_CONTRACT);
	}
}