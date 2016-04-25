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

import mockit.NonStrictExpectations;

import org.adempiere.util.MiscUtils;
import org.compiere.model.PO;

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

		new NonStrictExpectations() 
		{
			{
				onInstance(poMock).getCtx(); returns(POTest.CTX);

				onInstance(poMock).get_TrxName(); returns(POTest.TRX_NAME);

				onInstance(poMock).get_ID(); returns(id);

				onInstance(poMock).getAD_Client_ID(); returns(AD_CLIENT_ID);
				
				onInstance(poMock).getAD_Org_ID(); returns(AD_Org_ID);
			}
		};
	}

	public static void recordSaveOk(final PO poMock) {

		new NonStrictExpectations() {
			{
				poMock.save();	returns(true);
				
				poMock.save(POTest.TRX_NAME);	returns(true);
			}
		};

	}

}
