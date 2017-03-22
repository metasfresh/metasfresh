package de.metas.process.impl;

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


import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.impl.ADPInstanceDAO;

public class ADPInstanceDAOTest
{
	private PlainContextAware context;
	private ADPInstanceDAO dao;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new PlainContextAware(Env.getCtx());
		dao = (ADPInstanceDAO)Services.get(IADPInstanceDAO.class);
	}

	@Test
	public void test_createProcessInfoParameter_Timestamp()
	{
		final Timestamp date = SystemTime.asDayTimestamp();
		final I_AD_PInstance_Para adPInstancePara = InterfaceWrapperHelper.newInstance(I_AD_PInstance_Para.class, context);
		adPInstancePara.setP_Date(date);

		final ProcessInfoParameter para = dao.createProcessInfoParameter(adPInstancePara);
		Assert.assertEquals(date, para.getParameterAsTimestamp());
	}

	/**
	 * Test having a string parameter which is null.
	 */
	@Test
	public void test_createProcessInfoParameter_NullString()
	{
		final String stringParam = null;
		final I_AD_PInstance_Para adPInstancePara = InterfaceWrapperHelper.newInstance(I_AD_PInstance_Para.class, context);
		adPInstancePara.setP_String(null);

		final ProcessInfoParameter para = dao.createProcessInfoParameter(adPInstancePara);

		// NOTE: this is a common case in our processes
		final String paramStringActual = (String)para.getParameter();

		Assert.assertEquals(stringParam, paramStringActual);
	}
}
