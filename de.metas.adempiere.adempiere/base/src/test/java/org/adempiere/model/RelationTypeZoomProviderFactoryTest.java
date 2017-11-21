package org.adempiere.model;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.service.impl.LookupDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.I_AD_Table;
import org.junit.Before;

import lombok.NonNull;
import mockit.Mocked;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class RelationTypeZoomProviderFactoryTest
{
	@Mocked
	private LookupDAO lookupDao;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

//	@Test
//	public void findZoomProvider()
//	{
//
//		final String refTargetName = "RefTargetName1";
//		final String validationType = X_AD_Reference.VALIDATIONTYPE_Tabellenvalidierung;
//		final I_AD_Reference referenceTarget = createReferenceTarget(refTargetName, validationType);
//
//		final String tableName = "TableName";
//		final I_AD_Table table = createTable(tableName);
//
//		final String keyColumnName = "TableName_ID";
//		final I_AD_Column keyColumn = createColumn(table, keyColumnName);
//
//		final String recordColumnname = "Record_ID";
//		final I_AD_Column recordID = createColumn(table, recordColumnname);
//		final I_AD_Ref_Table refTable = createRefTable(referenceTarget, recordID);
//
//		final boolean isReferenceTarget = true;
//		final I_AD_RelationType relationType = createRelationType(isReferenceTarget, referenceTarget);
//
//		TableRefInfo build = LookupDAO.TableRefInfo.builder()
//				.setName(refTargetName)
//				.setTableName(tableName)
//				.setKeyColumn(keyColumnName)
//				.setDisplayColumn(keyColumnName)
//				.setValueDisplayed(true)
//				.setDisplayColumnSQL("")
//				.setTranslated(true)
//				.setWhereClause("")
//				.setOrderByClause("")
//				.setZoomSO_Window_ID(-1)
//				.setZoomPO_Window_ID(-1)
//				.setZoomAD_Window_ID_Override(-1)
//				.setAutoComplete(false)
//				// #2340 ReferenceTarget
//				.setReferenceTarget(isReferenceTarget)
//				.setReferenceTargetColumnID(recordID.getAD_Column_ID())
//				.build();
//
//		
//
//		final RelationTypeZoomProvider zoomProvider = RelationTypeZoomProvidersFactory.findZoomProvider(relationType);
//		
//		// @formatter:off
//		new Expectations()
//		{{
//			lookupDao.retrieveTableRefInfo(referenceTarget.getAD_Reference_ID()); result = build ;
//		}};
//		// @formatter:on
//
//		System.out.println(zoomProvider);
//	}

	private I_AD_Column createColumn(I_AD_Table table, String columnname)
	{
		final I_AD_Column column = newInstance(I_AD_Column.class);
		column.setAD_Table(table);
		column.setColumnName(columnname);
		save(column);

		return column;
	}

	private I_AD_Table createTable(String tableName)
	{
		final I_AD_Table table = newInstance(I_AD_Table.class);
		table.setName(tableName);
		table.setTableName(tableName);
		save(table);
		return table;
	}

	private I_AD_Ref_Table createRefTable(final I_AD_Reference reference, @NonNull final I_AD_Table table)
	{
		final I_AD_Ref_Table refTable = newInstance(I_AD_Ref_Table.class);
		refTable.setAD_Reference(reference);
		refTable.setAD_Table(table);
	

		save(refTable);
		return refTable;
	}

	private I_AD_RelationType createRelationType(final boolean isReferenceTarget, final I_AD_Reference referenceTarget)
	{
		final I_AD_RelationType relationType = newInstance(I_AD_RelationType.class);
		relationType.setIsReferenceTarget(isReferenceTarget);
		relationType.setAD_Reference_Target(referenceTarget);

		save(relationType);
		return relationType;
	}

	private I_AD_Reference createReferenceTarget(final String name, final String validationType)
	{
		final I_AD_Reference referenceTarget = newInstance(I_AD_Reference.class);
		referenceTarget.setName(name);
		referenceTarget.setValidationType(validationType);

		save(referenceTarget);
		return referenceTarget;
	}
}
