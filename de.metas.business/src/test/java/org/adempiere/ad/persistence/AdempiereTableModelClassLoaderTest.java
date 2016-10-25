package org.adempiere.ad.persistence;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.junit.Before;
import org.junit.Test;

public class AdempiereTableModelClassLoaderTest
{
	private TableModelClassLoaderTester tester = new TableModelClassLoaderTester()
			.setEntityTypeModelPackage(PO.ENTITYTYPE_Dictionary, null);

	@Before
	public void setup()
	{
		AdempiereTestHelper.get().init();

		// create two tables to test with. Note that i avoided business object tables because on the longer perspective, those don't belong into this base probject.
		final I_AD_Table tableTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		tableTable.setTableName(I_AD_Table.Table_Name);
		InterfaceWrapperHelper.save(tableTable);

		final I_AD_Table columnTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		columnTable.setTableName(I_AD_Column.Table_Name);
		InterfaceWrapperHelper.save(columnTable);

	}

	@Test
	public void test_LoadStandardClasses()
	{
		tester.setTableNameEntityType(I_AD_Column.Table_Name, PO.ENTITYTYPE_Dictionary)
				.assertClass(I_AD_Column.Table_Name, MColumn.class)
				.cacheReset()
				.assertClass(I_AD_Column.Table_Name, MColumn.class);
	}

	@Test
	public void test_EntityTypesAreReloadedAfterCacheReset()
	{
		tester
				// Make sure our EntityType is not registered yet
				.assertEntityTypeNotExists("MyEntityType")
				// Add our EntityType to the map of entity types which will be loaded after cache reset
				.setEntityTypeModelPackage("MyEntityType", null)
				// Atm there was no cache reset so we assume our EntityType is not yet there
				.assertEntityTypeNotExists("MyEntityType")
				// Do a cache reset, we expect the entity types to be loaded again
				// and now they shall contain our entity type
				.cacheReset()
				.assertEntityTypeExists("MyEntityType");
	}

	/**
	 * Verifies that the correct class is also loaded if the table name is given in lower case.
	 */
	@Test
	public void testTableNameIgnoreCase()
	{
		tester.setTableNameEntityType(I_AD_Table.Table_Name, PO.ENTITYTYPE_Dictionary)
				.assertClass(I_AD_Table.Table_Name, MTable.class) // guard
				.assertClass(I_AD_Table.Table_Name.toLowerCase(), MTable.class);
	}
}
