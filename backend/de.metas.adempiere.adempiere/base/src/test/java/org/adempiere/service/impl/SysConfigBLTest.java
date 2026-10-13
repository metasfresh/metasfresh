package org.adempiere.service.impl;

import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.X_AD_SysConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class SysConfigBLTest
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private ISysConfigBL sysConfigBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		sysConfigBL = Services.get(ISysConfigBL.class);
	}

	private static void createSystemLevelRecord(@NonNull final String name, @Nullable final String configurationLevel)
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
	void setValueAtConfigLevel_noSystemRecord_writesAtMainOrg()
	{
		sysConfigBL.setValueAtConfigLevel("x", "new");

		assertThat(findHolders("x", "new")).containsExactly(ClientAndOrgId.MAIN_ORG);
	}

	@Test
	void setValueAtConfigLevel_orgLevel_writesAtMainOrg()
	{
		createSystemLevelRecord("x", X_AD_SysConfig.CONFIGURATIONLEVEL_Organization);

		sysConfigBL.setValueAtConfigLevel("x", "new");

		assertThat(findHolders("x", "new")).containsExactly(ClientAndOrgId.MAIN_ORG);
	}

	@Test
	void setValueAtConfigLevel_systemLevel_writesAtSystem()
	{
		createSystemLevelRecord("x", X_AD_SysConfig.CONFIGURATIONLEVEL_System);

		sysConfigBL.setValueAtConfigLevel("x", "new");

		assertThat(findHolders("x", "new")).containsExactly(ClientAndOrgId.SYSTEM);
	}

	@Test
	void setValueAtConfigLevel_clientLevel_writesAtMetasfreshClientAnyOrg()
	{
		createSystemLevelRecord("x", X_AD_SysConfig.CONFIGURATIONLEVEL_Client);

		sysConfigBL.setValueAtConfigLevel("x", "new");

		assertThat(findHolders("x", "new")).containsExactly(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ANY));
	}
}
