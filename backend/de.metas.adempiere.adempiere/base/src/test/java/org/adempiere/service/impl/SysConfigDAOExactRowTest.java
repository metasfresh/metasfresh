package org.adempiere.service.impl;

import de.metas.organization.ClientAndOrgId;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SysConfigDAOExactRowTest
{
	private static final String NAME = "SysConfigDAOExactRowTest.sysconfig";

	private ISysConfigDAO dao;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		dao = Services.get(ISysConfigDAO.class);
	}

	@Test
	void getValueOfExactRow_shouldNotFallBackToALessSpecificRow()
	{
		dao.setValue(NAME, "system", ClientAndOrgId.SYSTEM);

		assertThat(dao.getValueOfExactRow(NAME, ClientAndOrgId.MAIN)).isEmpty();
		assertThat(dao.getValueOfExactRow(NAME, ClientAndOrgId.SYSTEM)).contains("system");
	}

	@Test
	void getValueOfExactRow_shouldReturnTheNoValueMarkerAsStored()
	{
		dao.setValue(NAME, null, ClientAndOrgId.MAIN);

		assertThat(dao.getValueOfExactRow(NAME, ClientAndOrgId.MAIN)).contains("-");
	}

	@Test
	void deleteExactRow_shouldMakeReadsFallBackToTheLessSpecificRow()
	{
		dao.setValue(NAME, "system", ClientAndOrgId.SYSTEM);
		dao.setValue(NAME, "main", ClientAndOrgId.MAIN);
		assertThat(dao.getValue(NAME, ClientAndOrgId.MAIN)).contains("main");

		dao.deleteExactRow(NAME, ClientAndOrgId.MAIN);

		assertThat(dao.getValueOfExactRow(NAME, ClientAndOrgId.MAIN)).isEmpty();
		assertThat(dao.getValue(NAME, ClientAndOrgId.MAIN)).contains("system");
	}

	@Test
	void deleteExactRow_whenThereIsNoSuchRow_shouldDoNothing()
	{
		dao.setValue(NAME, "system", ClientAndOrgId.SYSTEM);

		dao.deleteExactRow(NAME, ClientAndOrgId.MAIN);

		assertThat(dao.getValueOfExactRow(NAME, ClientAndOrgId.SYSTEM)).contains("system");
	}

	@Test
	void setActiveExactRow_inactiveRow_isIgnoredByReads_untilReactivated()
	{
		dao.setValue(NAME, "system", ClientAndOrgId.SYSTEM);
		dao.setValue(NAME, "main", ClientAndOrgId.MAIN);

		dao.setActiveExactRow(NAME, ClientAndOrgId.MAIN, false);
		assertThat(dao.isActiveExactRow(NAME, ClientAndOrgId.MAIN)).isEqualTo(OptionalBoolean.FALSE);
		assertThat(dao.getValue(NAME, ClientAndOrgId.MAIN)).contains("system");

		dao.setValue(NAME, "main2", ClientAndOrgId.MAIN);
		assertThat(dao.getValue(NAME, ClientAndOrgId.MAIN)).as("a value written to an inactive row does not take effect").contains("system");

		dao.setActiveExactRow(NAME, ClientAndOrgId.MAIN, true);
		assertThat(dao.isActiveExactRow(NAME, ClientAndOrgId.MAIN)).isEqualTo(OptionalBoolean.TRUE);
		assertThat(dao.getValue(NAME, ClientAndOrgId.MAIN)).contains("main2");
	}

	@Test
	void isActiveExactRow_whenThereIsNoSuchRow_shouldBeUnknown_andSetActiveShouldDoNothing()
	{
		dao.setValue(NAME, "system", ClientAndOrgId.SYSTEM);

		dao.setActiveExactRow(NAME, ClientAndOrgId.MAIN, true);

		assertThat(dao.isActiveExactRow(NAME, ClientAndOrgId.MAIN)).isEqualTo(OptionalBoolean.UNKNOWN);
		assertThat(dao.getValueOfExactRow(NAME, ClientAndOrgId.MAIN)).isEmpty();
	}
}
