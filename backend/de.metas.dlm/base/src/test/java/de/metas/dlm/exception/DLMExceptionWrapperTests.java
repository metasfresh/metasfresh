package de.metas.dlm.exception;

import de.metas.util.Services;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.DBException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
	@BeforeEach
	public void setup()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testInvalidString()
	{
		assertThatThrownBy(() -> DLMReferenceExceptionWrapper.extractInfos(""))
				.isInstanceOf(RuntimeException.class);
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

		assertThat(infos[0]).isEqualTo("c_payment");
		assertThat(infos[1]).isEqualTo("1243");
		assertThat(infos[2]).isEqualTo("c_bankstatementline_ref");
		assertThat(infos[3]).isEqualTo("c_payment_id");
	}

	/**
	 * Make sure that the lower-case infos coming from postgres result in a {@link TableRecordIdDescriptor} which contains the correct case for table and column names.
	 * This is required to avoid problems the same table or column (as far as the DB is concerned) being represented multiple times with table and column name strings that only differ in case.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testTableNameCases()
	{
		//
		// Set up the information that the DLMReferenceExceptionWrapper shall look up
		{
			// By just simply querying by TableNames and ColumnNames
			// we expect the Junit versions of those repositories to create those table/columns on the fly
			// and to create them with the correct upper/lower case.
			final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
			adTableDAO.retrieveAdTableId(I_AD_Window.Table_Name);
			adTableDAO.getMinimalColumnInfo(I_AD_Tab.Table_Name, I_AD_Tab.COLUMNNAME_AD_Window_ID);
		}

		// set up the infos the exception shall provide to the wrapper
		final PSQLException mockedPSQLException = Mockito.mock(PSQLException.class);
		Mockito.doReturn(DLMReferenceExceptionWrapper.PG_SQLSTATE_Referencing_Record_Has_Wrong_DLM_Level)
				.when(mockedPSQLException).getSQLState();

		final ServerErrorMessage mockedServerErrorMessage = Mockito.mock(ServerErrorMessage.class);
		Mockito.doReturn("DLM_Referenced_Table_Name = ad_window;  DLM_Referenced_Record_ID=1243 ; DLM_Referencing_Table_Name = ad_tab; DLM_Referencig_Column_Name = ad_window_id;") // automatically wrapped in a list of one item
				.when(mockedServerErrorMessage).getDetail();

		Mockito.doReturn(mockedServerErrorMessage)
				.when(mockedPSQLException).getServerErrorMessage();

		final DBException dbException = DLMReferenceExceptionWrapper.INSTANCE.wrapIfNeededOrReturnNull(mockedPSQLException);
		assertThat(dbException).isInstanceOf(DLMReferenceException.class);

		final DLMReferenceException dlmException = (DLMReferenceException)dbException;
		final TableRecordIdDescriptor tableReferenceDescriptor = dlmException.getTableReferenceDescriptor();

		// the descriptor needs to contain the "real" table and column names, not the lower-case versions that were returned by postgresl.
		assertThat(tableReferenceDescriptor.getTargetTableName()).isEqualTo(I_AD_Window.Table_Name);
		assertThat(tableReferenceDescriptor.getOriginTableName()).isEqualTo(I_AD_Tab.Table_Name);
		assertThat(tableReferenceDescriptor.getRecordIdColumnName()).isEqualTo(I_AD_Tab.COLUMNNAME_AD_Window_ID);
	}
}
