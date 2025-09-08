package de.metas.calendar.impl;

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

import de.metas.calendar.ICalendarDAO;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Properties;

@ExtendWith(AdempiereTestWatcher.class)
public class CalendarTestBase
{	
	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	protected Properties ctx;
	protected POJOLookupMap db;
	protected PlainCalendarDAO dao;

	@BeforeEach
	public final void beforeTest()
	{
		AdempiereTestHelper.get().init();

		dao = (PlainCalendarDAO)Services.get(ICalendarDAO.class);

		db = dao.getDB();

		//
		// Setup context
		ctx = Env.getCtx();
		ctx.clear();
		Env.setContext(ctx, "#AD_Client_ID", 1);
		Env.setContext(ctx, "#AD_Org_ID", 1);
		Env.setContext(ctx, "#AD_Role_ID", 1);
		Env.setContext(ctx, "#AD_User_ID", 1);

		init();
	}

	protected void init()
	{
		// nothing
	}

	protected Properties getCtx()
	{
		return ctx;
	}

}
