package de.metas.dlm.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.dlm.partitioner.config.TableReferenceDescriptor;
import de.metas.logging.LogManager;

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

public class DLMServiceTests
{
	@Before
	public void before()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.DEBUG);
	}

	/**
	 * tests {@link DLMService#retrieveTableRecordReferences()}
	 * Creates two <code>AD_ChangeLog</code> records, one referencing an <code>AD_Element</code>, the other one referencing an <code>AD_Field</code>.
	 * Then verifies that the map returned by the method under test contains two entries. One for <code>AD_Element</code>, one for <code>AD_Field</code>.
	 */
	@Test
	public void testRetrieveTableRecordReferences()
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final I_AD_Element referencedElement = InterfaceWrapperHelper.newInstance(I_AD_Element.class);
		InterfaceWrapperHelper.save(referencedElement);
		final int adElementTableID = adTableDAO.retrieveTableId(I_AD_Element.Table_Name);

		final I_AD_Field referencedField = InterfaceWrapperHelper.newInstance(I_AD_Field.class);
		InterfaceWrapperHelper.save(referencedField);
		final int adfieldTableID = adTableDAO.retrieveTableId(I_AD_Field.Table_Name);

		final I_AD_ChangeLog referencingChangeLog = InterfaceWrapperHelper.newInstance(I_AD_ChangeLog.class);
		referencingChangeLog.setAD_Table_ID(adElementTableID);
		referencingChangeLog.setRecord_ID(referencedElement.getAD_Element_ID());
		InterfaceWrapperHelper.save(referencingChangeLog);

		final I_AD_ChangeLog referencingChangeLog2 = InterfaceWrapperHelper.newInstance(I_AD_ChangeLog.class);
		referencingChangeLog2.setAD_Table_ID(adfieldTableID);
		referencingChangeLog2.setRecord_ID(referencedField.getAD_Field_ID());
		InterfaceWrapperHelper.save(referencingChangeLog2);

		final int adChangeLogTableID = adTableDAO.retrieveTableId(I_AD_ChangeLog.Table_Name);

		// for the unit test need to explicitly create AD_Column records for the two AD_Changelog properties
		{
			final I_AD_Column recordIDColumn = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
			recordIDColumn.setColumnName(I_AD_ChangeLog.COLUMNNAME_Record_ID);
			recordIDColumn.setAD_Table_ID(adChangeLogTableID);
			InterfaceWrapperHelper.save(recordIDColumn);

			final I_AD_Column tableIDIDColumn = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
			tableIDIDColumn.setColumnName(I_AD_ChangeLog.COLUMNNAME_AD_Table_ID);
			tableIDIDColumn.setAD_Table_ID(adChangeLogTableID);
			InterfaceWrapperHelper.save(tableIDIDColumn);
		}

		final List<TableReferenceDescriptor> tableRecordReferences = new DLMService().retrieveTableRecordReferences();

		// assert that there are two records, one about AD_Fleid, one about AD_Element
		assertThat(tableRecordReferences.size(), is(2));
		assertThat(tableRecordReferences.stream().anyMatch(d -> I_AD_Element.Table_Name.equals(d.getReferencedTableName())), is(true));
		assertThat(tableRecordReferences.stream().anyMatch(d -> I_AD_Field.Table_Name.equals(d.getReferencedTableName())), is(true));

		final TableReferenceDescriptor adElementReferenceDescriptor = tableRecordReferences.stream().filter(d -> I_AD_Element.Table_Name.equals(d.getReferencedTableName())).findFirst().get();
		assertThat(adElementReferenceDescriptor.getReferencingTableName(), is(I_AD_ChangeLog.Table_Name));
		assertThat(adElementReferenceDescriptor.getReferencingColumnName(), is(I_AD_ChangeLog.COLUMNNAME_Record_ID));

		final TableReferenceDescriptor adFieldReferenceDescriptor = tableRecordReferences.stream().filter(d -> I_AD_Field.Table_Name.equals(d.getReferencedTableName())).findFirst().get();
		assertThat(adFieldReferenceDescriptor.getReferencingTableName(), is(I_AD_ChangeLog.Table_Name));
		assertThat(adFieldReferenceDescriptor.getReferencingColumnName(), is(I_AD_ChangeLog.COLUMNNAME_Record_ID));

	}
}
