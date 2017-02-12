package de.metas.adempiere.test;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Properties;

import org.adempiere.util.MiscUtils;
import org.compiere.model.PO;

import mockit.Expectations;

/**
 * 
 * @author ts
 * 
 */
public class POTest {

	public static final String TRX_NAME = "POTest_TrxName";

	public static final int AD_CLIENT_ID = 28;
	public static final int AD_Org_ID = 29;

	public static final Properties CTX = new Properties();

	public static final Timestamp DATE = MiscUtils.toTimeStamp("2009-11-11");
		
	public static void recordGenericExpectations(final PO poMock, final int id) {

		new Expectations() 
		{{
			poMock.getCtx(); minTimes = 0; result = POTest.CTX;
			poMock.get_TrxName(); minTimes = 0; result = POTest.TRX_NAME;
			poMock.get_ID(); minTimes = 0; result = id;
			poMock.getAD_Client_ID(); minTimes = 0; result = AD_CLIENT_ID;
			poMock.getAD_Org_ID(); minTimes = 0; result = AD_Org_ID;
		}};
	}

	public static void recordSaveOk(final PO poMock) {

		new Expectations() 
		{{
				poMock.save();	result = true;
				poMock.save(POTest.TRX_NAME);	result = true;
		}};

	}

}
