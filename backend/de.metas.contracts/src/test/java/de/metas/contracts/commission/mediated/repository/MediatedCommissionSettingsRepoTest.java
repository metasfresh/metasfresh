/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.mediated.repository;

import de.metas.contracts.commission.mediated.model.MediatedCommissionSettings;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsId;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettings;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettingsLine;
import lombok.Builder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class MediatedCommissionSettingsRepoTest
{
	private MediatedCommissionSettingsRepo mediatedCommissionSettingsRepo;

	@BeforeAll
	static void init()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
		AdempiereTestHelper.get().init();
	}

	@BeforeEach
	public void beforeEach()
	{
		mediatedCommissionSettingsRepo = new MediatedCommissionSettingsRepo();
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	public void givenValidId_whenGetById_thenReturnSettings()
	{
		//given
		final I_C_MediatedCommissionSettings settings = createMediatedSettingsRecord();
		final I_C_MediatedCommissionSettingsLine activeLine = mediatedSettingsLineBuilder()
				.isActive(true)
				.mediatedCommissionSettingsId(settings.getC_MediatedCommissionSettings_ID())
				.productCategoryId(100)
				.build();

		final I_C_MediatedCommissionSettingsLine activeNoProductCategory = mediatedSettingsLineBuilder()
				.isActive(true)
				.mediatedCommissionSettingsId(settings.getC_MediatedCommissionSettings_ID())
				.build();

		final I_C_MediatedCommissionSettingsLine inactiveLine = mediatedSettingsLineBuilder()
				.isActive(false)
				.mediatedCommissionSettingsId(settings.getC_MediatedCommissionSettings_ID())
				.build();

		//when
		final MediatedCommissionSettings mediatedCommissionSettings = mediatedCommissionSettingsRepo.getById(MediatedCommissionSettingsId.ofRepoId(settings.getC_MediatedCommissionSettings_ID()));

		//then
		expect(mediatedCommissionSettings).toMatchSnapshot();
	}

	private I_C_MediatedCommissionSettings createMediatedSettingsRecord()
	{
		final I_C_MediatedCommissionSettings record = InterfaceWrapperHelper.newInstance(I_C_MediatedCommissionSettings.class);

		record.setAD_Org_ID(1);
		record.setName("name");
		record.setDescription("description");
		record.setCommission_Product_ID(1001);
		record.setIsActive(true);
		record.setPointsPrecision(2);

		saveRecord(record);

		return record;
	}

	@Builder(builderMethodName = "mediatedSettingsLineBuilder")
	private I_C_MediatedCommissionSettingsLine createMediatedSettingsLineRecord(
			final Integer mediatedCommissionSettingsId,
			final boolean isActive,
			final Integer productCategoryId)
	{
		final I_C_MediatedCommissionSettingsLine record = InterfaceWrapperHelper.newInstance(I_C_MediatedCommissionSettingsLine.class);

		record.setC_MediatedCommissionSettings_ID(mediatedCommissionSettingsId);
		record.setAD_Org_ID(1);
		record.setM_Product_Category_ID(productCategoryId != null ? productCategoryId : -1);
		record.setPercentOfBasePoints(BigDecimal.TEN);
		record.setSeqNo(3);
		record.setIsActive(isActive);

		saveRecord(record);

		return record;
	}
}
