package org.adempiere.ad.migration.logger;

import org.adempiere.service.ClientId;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_Window_Access;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableNamesSkipListTest
{
	@Test
	void addTablesToIgnoreList()
	{
		final TableNamesSkipList skipList = new TableNamesSkipList();

		final int initialSystemListSize = skipList.getTablesToIgnoreUC(ClientId.SYSTEM).size();
		final int initialClientListSize = skipList.getTablesToIgnoreUC(ClientId.METASFRESH).size();

		// Add a new table => expect the list to grow by 1
		skipList.addTablesToIgnoreList("my_table");
		assertThat(skipList.getTablesToIgnoreUC(ClientId.SYSTEM)).hasSize(initialSystemListSize + 1);
		assertThat(skipList.getTablesToIgnoreUC(ClientId.METASFRESH)).hasSize(initialClientListSize + 1);

		// Add it again => expect the list to not grow
		skipList.addTablesToIgnoreList("my_table");
		assertThat(skipList.getTablesToIgnoreUC(ClientId.SYSTEM)).hasSize(initialSystemListSize + 1);
		assertThat(skipList.getTablesToIgnoreUC(ClientId.METASFRESH)).hasSize(initialClientListSize + 1);
	}

	@Test
	void addTablesToIgnoreList_isLogTableName()
	{
		final TableNamesSkipList skipList = new TableNamesSkipList();
		skipList.addTablesToIgnoreList("my_table");

		assertThat(skipList.isLogTableName("my_table", ClientId.SYSTEM)).isFalse();
		assertThat(skipList.isLogTableName("my_table", ClientId.METASFRESH)).isFalse();

		assertThat(skipList.isLogTableName("MY_Table", ClientId.SYSTEM)).isFalse();
		assertThat(skipList.isLogTableName("MY_Table", ClientId.METASFRESH)).isFalse();

		assertThat(skipList.getTablesToIgnoreUC(ClientId.SYSTEM)).contains("MY_TABLE");
		assertThat(skipList.getTablesToIgnoreUC(ClientId.METASFRESH)).contains("MY_TABLE");
	}

	@Test
	void test_role_access()
	{
		final TableNamesSkipList skipList = new TableNamesSkipList();

		Assertions.assertThat(skipList.isLogTableName(I_AD_Window_Access.Table_Name, ClientId.SYSTEM)).isFalse();
		Assertions.assertThat(skipList.isLogTableName(I_AD_Window_Access.Table_Name, ClientId.METASFRESH)).isTrue();
	}
}