package org.adempiere.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.X_AD_SysConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class SysConfigBLTests
{
	private ISysConfigBL sysConfigBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		sysConfigBL = Services.get(ISysConfigBL.class);
	}

	@Nested
	class testSetGetString
	{
		@Test
		void sameOrg()
		{
			final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(ClientId.ofRepoId(1), OrgId.ofRepoId(1));

			sysConfigBL.setValue("name", "valueStr", clientAndOrgId.getClientId(), clientAndOrgId.getOrgId());

			assertThat(sysConfigBL.getValue("name", clientAndOrgId)).isEqualTo("valueStr");
		}

		@Test
		void setOn_System_getOn_Org1()
		{
			sysConfigBL.setValue("name", "valueStr", ClientId.SYSTEM, OrgId.ANY);

			final String value = sysConfigBL.getValue("name", ClientAndOrgId.ofClientAndOrg(ClientId.ofRepoId(1), OrgId.ofRepoId(1)));
			assertThat(value).isEqualTo("valueStr");
		}

		@Test
		void setOn_Org0_getOn_Org1()
		{
			sysConfigBL.setValue("name", "valueStr", ClientId.ofRepoId(1), OrgId.ANY);

			final String value = sysConfigBL.getValue("name", ClientAndOrgId.ofClientAndOrg(ClientId.ofRepoId(1), OrgId.ofRepoId(1)));
			assertThat(value).isEqualTo("valueStr");
		}
	}

	@Nested
	class setValueAtConfigLevel
	{
		private final IQueryBL queryBL = Services.get(IQueryBL.class);

		private void createSystemLevelRecord(@NonNull final String name, @Nullable final String configurationLevel)
		{
			final I_AD_SysConfig record = InterfaceWrapperHelper.newInstance(I_AD_SysConfig.class);
			InterfaceWrapperHelper.setValue(record, I_AD_SysConfig.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM.getRepoId());
			record.setAD_Org_ID(OrgId.ANY.getRepoId());
			record.setName(name);
			record.setValue("initial");
			record.setConfigurationLevel(configurationLevel);
			InterfaceWrapperHelper.saveRecord(record);
		}

		/** @return the (client, org) of every record named {@code name} that holds {@code value} */
		private List<ClientAndOrgId> findHolders(@NonNull final String name, @NonNull final String value)
		{
			return queryBL.createQueryBuilder(I_AD_SysConfig.class)
					.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, name)
					.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Value, value)
					.create()
					.stream()
					.map(record -> ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
					.collect(Collectors.toList());
		}

		@Test
		void noSystemRecord_writesAtMainOrg()
		{
			sysConfigBL.setValueAtConfigLevel("x", "new");

			assertThat(findHolders("x", "new")).containsExactly(ClientAndOrgId.MAIN_ORG);
		}

		@Test
		void orgLevel_writesAtMainOrg()
		{
			createSystemLevelRecord("x", X_AD_SysConfig.CONFIGURATIONLEVEL_Organization);

			sysConfigBL.setValueAtConfigLevel("x", "new");

			assertThat(findHolders("x", "new")).containsExactly(ClientAndOrgId.MAIN_ORG);
		}

		@Test
		void systemLevel_writesAtSystem()
		{
			createSystemLevelRecord("x", X_AD_SysConfig.CONFIGURATIONLEVEL_System);

			sysConfigBL.setValueAtConfigLevel("x", "new");

			assertThat(findHolders("x", "new")).containsExactly(ClientAndOrgId.SYSTEM);
		}

		@Test
		void clientLevel_writesAtMetasfreshClientAnyOrg()
		{
			createSystemLevelRecord("x", X_AD_SysConfig.CONFIGURATIONLEVEL_Client);

			sysConfigBL.setValueAtConfigLevel("x", "new");

			assertThat(findHolders("x", "new")).containsExactly(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ANY));
		}
	}
}
