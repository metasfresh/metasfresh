/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.other;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Other_ConfigParameter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
public class ExternalSystemOtherConfigRepositoryTest
{
	private ExternalSystemOtherConfigRepository externalSystemOtherConfigRepo;
	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		externalSystemOtherConfigRepo = new ExternalSystemOtherConfigRepository();
	}

	@Test
	public void getById()
	{
		//given
		final ExternalSystemParentConfigId targetParentConfigId = ExternalSystemParentConfigId.ofRepoId(1);
		final ExternalSystemParentConfigId otherParentConfigId = ExternalSystemParentConfigId.ofRepoId(2);

		createExternalConfigParameterRecord(targetParentConfigId, "name1", "value1");
		createExternalConfigParameterRecord(targetParentConfigId, "name2", "value2");

		createExternalConfigParameterRecord(otherParentConfigId, "nameShouldBeIgnored", "valueIgnored");

		//when
		final ExternalSystemOtherConfig otherConfig =
				externalSystemOtherConfigRepo.getById(ExternalSystemOtherConfigId.ofExternalSystemParentConfigId(targetParentConfigId));

		//then
		assertThat(otherConfig).isNotNull();
		expect.serializer("orderedJson").toMatchSnapshot(otherConfig);
	}

	public static void createExternalConfigParameterRecord(
			final ExternalSystemParentConfigId parentConfigId,
			final String name,
			final String value)
	{
		final I_ExternalSystem_Other_ConfigParameter configParameter = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Other_ConfigParameter.class);

		configParameter.setExternalSystem_Config_ID(parentConfigId.getRepoId());
		configParameter.setName(name);
		configParameter.setValue(value);
		configParameter.setIsActive(true);

		InterfaceWrapperHelper.saveRecord(configParameter);
	}
}
