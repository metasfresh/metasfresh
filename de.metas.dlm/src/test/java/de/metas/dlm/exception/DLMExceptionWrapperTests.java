package de.metas.dlm.exception;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MTable;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import de.metas.dlm.partitioner.config.TableReferenceDescriptor;
import mockit.Expectations;
import mockit.Mocked;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class DLMExceptionWrapperTests
{
	@Before
	public void setup()
	{
		AdempiereTestHelper.get().init();
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidString()
	{
		DLMReferenceExceptionWrapper.extractInfos("");
	}

	/**
	 * Verify that parsing the error message's HINT works and is also a bit lenient.
	 */
	@Test
	public void testParsePostgresHint()
	{
		assertOK("DLM_Referenced_Table_Name = c_payment;  DLM_Referenced_Record_ID=1243 ; DLM_Referencing_Table_Name = c_bankstatementline_ref; DLM_Referencig_Column_Name = c_payment_id;");
		assertOK("DLM_Referenced_Table_Name = c_payment_tbl DLM_Referenced_Record_ID = 1243 DLM_Referencing_Table_Name=c_bankstatementline_ref; DLM_Referencig_Column_Name=c_payment_id;");
		assertOK("dlm_Referenced_Table_Name= c_payment   DLM_Referenced_Record_ID=1243;dlm_Referencing_Table_Name=c_bankstatementline_ref;dLm_Referencig_Column_Name=c_payment_id;");
		assertOK("DLM_Referenced_Table_Name = c_payment;DLM_Referenced_Record_ID=1243 DLM_Referencing_Table_Name=c_bankstatementline_ref  DLM_Referencig_Column_Name=c_payment_id;");
		assertOK("   DLM_Referenced_Table_Name = c_payment;DLM_rEFERENCED_Record_ID=1243, DLM_Referencing_Table_Name=c_bankstatementline_ref_tbl,DLM_Referencig_Column_Name=c_payment_id");
		assertOK("; DLM_Referenced_Table_Name = c_payment;  DLM_Referenced_Record_ID=1243 DLM_Referencing_Table_Name=c_bankstatementline_ref,DLM_Referencig_Column_Name=c_payment_id   ");
	}

	private void assertOK(final String string)
	{
		final String[] infos = DLMReferenceExceptionWrapper.extractInfos(string);

		assertThat(infos[0], is("c_payment"));
		assertThat(infos[1], is("1243"));
		assertThat(infos[2], is("c_bankstatementline_ref"));
		assertThat(infos[3], is("c_payment_id"));
	}

	/**
	 * Used in {@link #testTableNameCases()}.
	 */
	@Mocked
	PSQLException mockedPSQLException;

	/**
	 * Make sure that the lower-case infos coming from postgres result in a {@link TableReferenceDescriptor} which contains the correct case for table and column names.
	 * This is required to avoid problems the same table or column (as far as the DB is concerned) being represented multiple times with table and column name strings that only differ in case.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testTableNameCases()
	{
		//
		// set up the information that the DLMReferenceExceptionWrapper shall look up
		final int windowTableId = MTable.getTable_ID(I_AD_Window.Table_Name);
		final I_AD_Table windowTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		windowTable.setTableName(I_AD_Window.Table_Name);
		windowTable.setAD_Table_ID(windowTableId);
		InterfaceWrapperHelper.save(windowTable);

		final int tabTableId = MTable.getTable_ID(I_AD_Tab.Table_Name);
		final I_AD_Table tabTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		tabTable.setTableName(I_AD_Tab.Table_Name);
		tabTable.setAD_Table_ID(tabTableId);
		InterfaceWrapperHelper.save(tabTable);

		final I_AD_Column column = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
		column.setColumnName(I_AD_Tab.COLUMNNAME_AD_Window_ID);
		column.setAD_Table_ID(tabTable.getAD_Table_ID());
		column.setIsActive(true);
		InterfaceWrapperHelper.save(column);

		// set up the infos the exception shall provide to the wrapper
		new Expectations()
		{
			{
				mockedPSQLException.getServerErrorMessage().getDetail();
				result = "DLM_Referenced_Table_Name = ad_window;  DLM_Referenced_Record_ID=1243 ; DLM_Referencing_Table_Name = ad_tab; DLM_Referencig_Column_Name = ad_window_id;"; // automatically wrapped in a list of one item

				mockedPSQLException.getSQLState();
				result = DLMReferenceExceptionWrapper.PG_SQLSTATE_Referencing_Record_Has_Wrong_DLM_Level;
			}
		};

		final DBException dbException = DLMReferenceExceptionWrapper.INSTANCE.wrapIfNeededOrReturnNull(mockedPSQLException);
		assertThat(dbException, instanceOf(DLMReferenceException.class));

		DLMReferenceException dlmException = (DLMReferenceException)dbException;
		final TableReferenceDescriptor tableReferenceDescriptor = dlmException.getTableReferenceDescriptor();

		// the descriptor needs to contain the "real" table and column names, not the lower-case versions that were returned by postgresl.
		assertThat(tableReferenceDescriptor.getReferencedTableName(), is(I_AD_Window.Table_Name));
		assertThat(tableReferenceDescriptor.getReferencingTableName(), is(I_AD_Tab.Table_Name));
		assertThat(tableReferenceDescriptor.getReferencingColumnName(), is(I_AD_Tab.COLUMNNAME_AD_Window_ID));

	}
}
