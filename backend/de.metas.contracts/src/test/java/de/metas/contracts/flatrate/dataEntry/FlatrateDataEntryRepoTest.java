/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.flatrate.dataEntry;

import de.metas.bpartner.department.BPartnerDepartmentRepo;
import de.metas.business.BusinessTestHelper;
import de.metas.calendar.PeriodRepo;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRepo;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_DataEntry_Detail;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.document.engine.DocStatus;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Department;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class FlatrateDataEntryRepoTest
{

	private FlatrateDataEntryRepo flatrateDataEntryRepo;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		flatrateDataEntryRepo = new FlatrateDataEntryRepo(new BPartnerDepartmentRepo(), new FlatrateTermRepo(), new PeriodRepo());
	}

	@Test
	public void getByIdTest()
	{
		// Given
		final I_C_UOM uomEach = BusinessTestHelper.createUomEach();
		final I_M_Product productRecord = BusinessTestHelper.createProduct("flatrateTermProduct", uomEach);
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("flatrateTerm");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);

		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setInvoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Immediate);
		saveRecord(conditionsRecord);

		final I_C_Flatrate_Term flatrateTerm = newInstance(I_C_Flatrate_Term.class);
		flatrateTerm.setBill_BPartner_ID(bPartner.getC_BPartner_ID());
		flatrateTerm.setBill_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
		flatrateTerm.setM_Product_ID(productRecord.getM_Product_ID());
		flatrateTerm.setC_Flatrate_Conditions_ID(conditionsRecord.getC_Flatrate_Conditions_ID());

		saveRecord(flatrateTerm);

		final FlatrateDataEntryId testId = FlatrateDataEntryId.ofRepoId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()), 100);

		final I_C_Year yearRecord = newInstance(I_C_Year.class);
		yearRecord.setC_Calendar_ID(10);
		saveRecord(yearRecord);
		
		final I_C_Period period = newInstance(I_C_Period.class);
		period.setC_Year_ID(yearRecord.getC_Year_ID());
		period.setStartDate(SystemTime.asTimestamp());
		period.setEndDate(SystemTime.asTimestamp());
		saveRecord(period);

		final I_C_Flatrate_DataEntry dataEntry1 = newInstance(I_C_Flatrate_DataEntry.class);
		dataEntry1.setC_Flatrate_DataEntry_ID(testId.getRepoId());
		dataEntry1.setC_Flatrate_Term_ID(flatrateTerm.getC_Flatrate_Term_ID());
		dataEntry1.setC_UOM_ID(uomEach.getC_UOM_ID()); // generally not mandatory, but for this flatrate-type always set
		dataEntry1.setC_Period_ID(period.getC_Period_ID());
		dataEntry1.setDocStatus(DocStatus.Completed.getCode());
		saveRecord(dataEntry1);

		final I_C_BPartner_Department department = newInstance(I_C_BPartner_Department.class);
		department.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		department.setValue("department");
		department.setName("department");
		saveRecord(department);

		final I_C_Flatrate_DataEntry_Detail dataEntryDetail1 = newInstance(I_C_Flatrate_DataEntry_Detail.class);
		dataEntryDetail1.setC_Flatrate_DataEntry_ID(dataEntry1.getC_Flatrate_DataEntry_ID());
		dataEntryDetail1.setC_Flatrate_DataEntry_Detail_ID(11);
		dataEntryDetail1.setSeqNo(20);
		dataEntryDetail1.setC_UOM_ID(uomEach.getC_UOM_ID());
		dataEntryDetail1.setC_BPartner_Department_ID(department.getC_BPartner_Department_ID());
		saveRecord(dataEntryDetail1);

		final I_C_Flatrate_DataEntry_Detail dataEntryDetail2 = newInstance(I_C_Flatrate_DataEntry_Detail.class);
		dataEntryDetail2.setC_Flatrate_DataEntry_ID(dataEntry1.getC_Flatrate_DataEntry_ID());
		dataEntryDetail2.setC_Flatrate_DataEntry_Detail_ID(12);
		dataEntryDetail2.setSeqNo(10);
		dataEntryDetail2.setC_UOM_ID(uomEach.getC_UOM_ID());
		dataEntryDetail2.setC_BPartner_Department_ID(department.getC_BPartner_Department_ID());
		saveRecord(dataEntryDetail2);

		// When
		final FlatrateDataEntry result = flatrateDataEntryRepo.getById(testId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(testId);
		assertThat(result.getDetails()).hasSize(2);

		assertThat(result.getDetails().get(0).getId()).isEqualTo(FlatrateDataEntryDetailId.ofRepoId(testId, 12)); // verify that it's ordered by seqno
		assertThat(result.getDetails().get(1).getId()).isEqualTo(FlatrateDataEntryDetailId.ofRepoId(testId, 11));
	}
}