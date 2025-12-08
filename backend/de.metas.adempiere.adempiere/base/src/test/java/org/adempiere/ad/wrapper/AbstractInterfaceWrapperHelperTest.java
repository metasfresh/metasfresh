/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package org.adempiere.ad.wrapper;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.adempiere.ad.wrapper.AbstractInterfaceWrapperHelperTest.ITable1.COLUMNNNAME_Name;
import static org.adempiere.ad.wrapper.AbstractInterfaceWrapperHelperTest.ITable1.COLUMNNNAME_Name2;
import static org.adempiere.ad.wrapper.AbstractInterfaceWrapperHelperTest.ITable1.COLUMNNNAME_Table1_ID;
import static org.adempiere.ad.wrapper.AbstractInterfaceWrapperHelperTest.ITable1.COLUMNNNAME_Table1_Value_ID;
import static org.assertj.core.api.Assertions.assertThat;

class AbstractInterfaceWrapperHelperTest
{
	private PlainContextAware contextProvider;

	public interface ITable1
	{
		// @formatter:off
		String Table_Name = "Table1";
		String COLUMNNNAME_Table1_ID = "Table1_ID";
		void setTable1_ID(int value);
		int getTable1_ID();

		String COLUMNNNAME_Table1_Override_ID = "Table1_Override_ID";
		void setTable1_Override_ID(int value);
		int getTable1_Override_ID();
		
		String COLUMNNNAME_Name = "Name";
		String getName();
		void setName(String name);

		String COLUMNNNAME_Name_Override = "Name_Override";
		String getName_Override();
		void setName_Override(String name);

		String COLUMNNNAME_Table1_Value_ID = "Table1_Value_ID";
		int getTable1_Value_ID();
		void setTable1_Value_ID(int value);

		String COLUMNNNAME_Table1_Override_Value_ID = "Table1_Override_Value_ID";
		int getTable1_Override_Value_ID();
		void setTable1_Override_Value_ID(int value);

		String COLUMNNNAME_Name2 = "Name2";
		String getName2();
		void setName2(String name);
		
		// @formatter:on
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.get().setCopyOnSave(true);

		// NOTE: By Default we work with strict values
		// Tests will un-set this setting if necessary
		POJOWrapper.setDefaultStrictValues(true);

		contextProvider = PlainContextAware.newOutOfTrx();
	}

	@Test
	void getValueOverrideOrNull()
	{
		final Properties ctx = contextProvider.getCtx();

		final ITable1 record = POJOWrapper.create(ctx, ITable1.class);

		record.setName("name");
		record.setName_Override("name_override");
		
		record.setTable1_ID(1234);
		record.setTable1_Override_ID(2234);

		record.setTable1_Value_ID(1235);
		record.setTable1_Override_Value_ID(2235);

		record.setName2("name2");
		
		POJOWrapper.save(record);

		// then
		final String value1 = InterfaceWrapperHelper.getValueOverrideOrValue(record, COLUMNNNAME_Name);
		assertThat(value1).as("simply append _Override").isEqualTo("name_override");
		
		final Integer value2 = InterfaceWrapperHelper.getValueOverrideOrValue(record, COLUMNNNAME_Table1_ID);
		assertThat(value2).as("change ...ID for ..._Override_ID").isEqualTo(2234);

		final Integer value3 = InterfaceWrapperHelper.getValueOverrideOrValue(record, COLUMNNNAME_Table1_Value_ID);
		assertThat(value3).as("change ..._Value_ID for ..._Override_Value_ID").isEqualTo(2235);

		final String value4 = InterfaceWrapperHelper.getValueOverrideOrValue(record, COLUMNNNAME_Name2);
		assertThat(value4).isEqualTo("name2");
	}
}