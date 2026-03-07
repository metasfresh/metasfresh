package org.compiere.db;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CConnectionAttributesTest
{
	@Test
	public void test_parse_and_toString()
	{
		final String attributesStr = "CConnection[DBhost=roddb001,DBport=5432,DBname=ad_rt_tsa,UID=metasfresh,PWD=metas]";
		final CConnectionAttributes attrs = CConnectionAttributes.of(attributesStr);

		Assertions.assertEquals("roddb001", attrs.getDbHost(), "DbHost");
		Assertions.assertEquals(5432, attrs.getDbPort(), "DbPort");
		Assertions.assertEquals("ad_rt_tsa", attrs.getDbName(), "DbName");
		Assertions.assertEquals("metasfresh", attrs.getDbUid(), "DbUid");
		Assertions.assertEquals("metas", attrs.getDbPwd(), "DbPwd");

		// Convert back to string and test
		Assertions.assertEquals(attributesStr, attrs.toString());
	}

	@Test
	public void test_parse_and_toString_old()
	{
		final String attributesStrOld = "CConnection[name=MyAppsServer{roddb001-ad_rt_tsa-adempiere},AppsHost=MyAppsServer,AppsPort=1099,type=PostgreSQL,DBhost=roddb001,DBport=5432,DBname=ad_rt_tsa,BQ=false,FW=false,FWhost=,FWport=0,UID=metasfresh,PWD=metas]";
		final String attributesStr = "CConnection[DBhost=roddb001,DBport=5432,DBname=ad_rt_tsa,UID=metasfresh,PWD=metas]";
		final CConnectionAttributes attrs = CConnectionAttributes.of(attributesStrOld);

		Assertions.assertEquals("roddb001", attrs.getDbHost(), "DbHost");
		Assertions.assertEquals(5432, attrs.getDbPort(), "DbPort");
		Assertions.assertEquals("ad_rt_tsa", attrs.getDbName(), "DbName");
		Assertions.assertEquals("metasfresh", attrs.getDbUid(), "DbUid");
		Assertions.assertEquals("metas", attrs.getDbPwd(), "DbPwd");

		// Convert back to string and test
		Assertions.assertEquals(attributesStr, attrs.toString());
	}
}
