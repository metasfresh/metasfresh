package de.metas.dlm.partitioner.process;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.dlm.partitioner.config.PartitionerConfig;
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

public class DLM_Partition_Config_Add_TableRecord_LinesTests
{
	@Before
	public void before()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.DEBUG);
	}

	@Test
	public void test()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.line(I_AD_Field.Table_Name)
				.line(I_AD_Tab.Table_Name)
				.endLine().build();

		final ImmutableList<TableReferenceDescriptor> descriptors = ImmutableList.of(
				TableReferenceDescriptor.of(I_AD_Field.Table_Name, I_AD_ChangeLog.Table_Name, I_AD_ChangeLog.COLUMNNAME_Record_ID),
				TableReferenceDescriptor.of(I_AD_Tab.Table_Name, I_AD_ChangeLog.Table_Name, I_AD_ChangeLog.COLUMNNAME_Record_ID),
				TableReferenceDescriptor.of(I_AD_Table.Table_Name, I_AD_Column.Table_Name, I_AD_Column.COLUMNNAME_AD_Table_ID),    // some irrelevant descriptor in between
				TableReferenceDescriptor.of(I_AD_Tab.Table_Name, I_AD_PInstance.Table_Name, I_AD_ChangeLog.COLUMNNAME_Record_ID),
				TableReferenceDescriptor.of(I_AD_Field.Table_Name, I_AD_PInstance.Table_Name, I_AD_ChangeLog.COLUMNNAME_Record_ID),
				TableReferenceDescriptor.of(I_AD_Window.Table_Name, I_AD_PInstance.Table_Name, I_AD_ChangeLog.COLUMNNAME_Record_ID)); // some irrelevant descriptor in between

		final List<TableReferenceDescriptor> relevantDescriptors = new DLM_Partition_Config_Add_TableRecord_Lines().retainRelevantDescriptors(config, descriptors);

		assertThat(relevantDescriptors.size(), is(4));

	}
}
