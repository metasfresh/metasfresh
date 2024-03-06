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

package de.metas.contracts.commission.licensefee.repository;

import de.metas.bpartner.BPGroupId;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettings;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsId;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettings;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettingsLine;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LicenseFeeSettingsRepositoryTest
{
	private LicenseFeeSettingsRepository licenseFeeSettingsRepository;

	@BeforeAll
	static void init()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
		AdempiereTestHelper.get().init();
	}

	@BeforeEach
	public void beforeEach()
	{
		licenseFeeSettingsRepository = new LicenseFeeSettingsRepository();
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	public void givenValidId_whenGetById_thenReturnSettings()
	{
		// given
		final I_C_LicenseFeeSettings settings = createLicenseFeeSettingsRecord();
		final LicenseFeeSettingsId settingsId = LicenseFeeSettingsId.ofRepoId(settings.getC_LicenseFeeSettings_ID());

		// activeSettingsLine
		licenseFeeSettingsLineBuilder()
				.licenseFeeSettingsId(settingsId)
				.seqNo(10)
				.bpGroupMatchId(BPGroupId.STANDARD)
				.active(true)
				.build();

		// inactiveSettingsLine
		licenseFeeSettingsLineBuilder()
				.licenseFeeSettingsId(settingsId)
				.seqNo(20)
				.bpGroupMatchId(BPGroupId.ofRepoId(1))
				.active(false)
				.build();

		//when
		final LicenseFeeSettings licenseFeeSettings = licenseFeeSettingsRepository.getById(settingsId);

		//then
		assertThat(licenseFeeSettings.getLines().size()).isEqualTo(1);

		expect(licenseFeeSettings).toMatchSnapshot();
	}

	private I_C_LicenseFeeSettings createLicenseFeeSettingsRecord()
	{
		final I_C_LicenseFeeSettings record = InterfaceWrapperHelper.newInstance(I_C_LicenseFeeSettings.class);

		record.setAD_Org_ID(1);
		record.setName("name");
		record.setCommission_Product_ID(1001);
		record.setPointsPrecision(2);
		record.setIsActive(true);

		saveRecord(record);

		return record;
	}

	@Builder(builderMethodName = "licenseFeeSettingsLineBuilder")
	private I_C_LicenseFeeSettingsLine createLicenseFeeSettingsLineRecord(
			final LicenseFeeSettingsId licenseFeeSettingsId,
			final Integer seqNo,
			final BPGroupId bpGroupMatchId,
			final Boolean active)
	{
		final I_C_LicenseFeeSettingsLine record = InterfaceWrapperHelper.newInstance(I_C_LicenseFeeSettingsLine.class);

		record.setC_LicenseFeeSettings_ID(LicenseFeeSettingsId.toRepoId(licenseFeeSettingsId));
		record.setAD_Org_ID(1);
		record.setPercentOfBasePoints(BigDecimal.TEN);
		record.setSeqNo(seqNo);
		record.setC_BP_Group_Match_ID(BPGroupId.toRepoId(bpGroupMatchId));
		record.setIsActive(active);

		saveRecord(record);

		return record;
	}

}
