package de.metas.dlm.partitioner.config;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

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

public class PartitionerConfigBuilderTest
{
	/**
	 * Verifies tha {@link PartitionerConfig#builder()} works.
	 */
	@Test
	public void testConfigBuilder()
	{
		final PartitionerConfig config = createAndCheckBuilder0();
		assertThat(config.getLines().size(), is(2));
	}

	@Test
	public void testConfigBuilderExtendLine()
	{
		final PartitionerConfig config = PartitionerConfig.builder().setDLM_Partition_Config_ID(1)
				.line("ABC").setDLM_Partition_Config_Line(2)
				.ref().setReferencedTableName("123").setReferencingColumnName("ABC_columnName1").setDLM_Partition_Config_Reference(3).endRef()
				.line("ABC")
				.ref().setReferencedTableName("789").setReferencingColumnName("ABC_columnName2").setDLM_Partition_Config_Reference(4).endRef()
				.line("123").setDLM_Partition_Config_Line(4)
				.endLine()
				.build();

		assertThat(config.getLines().size(), is(2));

		assertThat(config.getLines().get(0).getTableName(), equalToIgnoringCase("ABC"));
		assertThat(config.getLineNotNull("ABC").getTableName(), equalToIgnoringCase("ABC"));

		assertNotNull(config.getLineNotNull("ABC").getReferences());
		assertThat(config.getLineNotNull("ABC").getReferences().size(), is(2));

		assertThat(config.getLineNotNull("ABC").getReferences().get(0).getReferencedTableName(), equalToIgnoringCase("123"));
		assertThat(config.getLineNotNull("ABC").getReferences().get(1).getReferencedTableName(), equalToIgnoringCase("789"));
	}

	/**
	 * Verifies that {@link PartitionerConfig#builder(PartitionerConfig)} works.
	 * If this test fails, I recommend to first check if {@link #testConfigBuilder()} also fails.
	 */
	@Test
	public void testExtendConfigBuilder()
	{
		final PartitionerConfig config = createAndCheckBuilder0();
		assertThat(config.getLines().size(), is(2));

		final PartitionerConfig extendedConfig = PartitionerConfig.builder(config)
				.line("XYZ")
				.ref().setReferencedTableName("123").setReferencingColumnName("XYZ_columnName").endRef()
				.endLine()
				.build();

		checkConfig(extendedConfig);
		assertThat(extendedConfig.getLines().size(), is(3));

		assertThat(extendedConfig.getLines().get(2).getTableName(), is("XYZ"));

		assertThat(extendedConfig.getLineNotNull("XYZ").getTableName(), is("XYZ"));
		assertNotNull(extendedConfig.getLineNotNull("XYZ").getReferences());
		assertThat(extendedConfig.getLineNotNull("XYZ").getReferences().get(0).getReferencedTableName(), is("123"));
		assertThat(extendedConfig.getLineNotNull("XYZ").getReferences().get(0).getReferencingColumnName(), is("XYZ_columnName"));
	}

	@Test
	public void testIgnoreCase()
	{
		final PartitionerConfig config = createAndCheckBuilder0();
		assertThat(config.getLines().size(), is(2));

		final PartitionerConfig extendedConfig = PartitionerConfig.builder(config)
				.line("XYZ")
				.ref().setReferencedTableName("123").setReferencingColumnName("XYZ_columnName").endRef()
				.line("xyz")
				.ref().setReferencedTableName("123").setReferencingColumnName("xyz_columnName").endRef()
				.endLine()
				.build();

		checkConfig(extendedConfig);
		assertThat(extendedConfig.getLines().size(), is(3));

		assertThat(extendedConfig.getLines().get(2).getTableName(), is("XYZ"));

		assertThat(extendedConfig.getLineNotNull("XYZ").getTableName(), is("XYZ"));
		assertNotNull(extendedConfig.getLineNotNull("XYZ").getReferences());
		assertThat(extendedConfig.getLineNotNull("XYZ").getReferences().size(), is(1));
		assertThat(extendedConfig.getLineNotNull("XYZ").getReferences().get(0).getReferencedTableName(), is("123"));
		assertThat(extendedConfig.getLineNotNull("XYZ").getReferences().get(0).getReferencingColumnName(), is("XYZ_columnName"));
	}

	private PartitionerConfig createAndCheckBuilder0()
	{
		final PartitionerConfig config = PartitionerConfig.builder().setDLM_Partition_Config_ID(1)
				.line("ABC").setDLM_Partition_Config_Line(2)
				.ref().setReferencedTableName("123").setReferencingColumnName("ABC_columnName").setDLM_Partition_Config_Reference(3).endRef()
				.line("123").setDLM_Partition_Config_Line(4)
				.endLine()
				.build();

		assertNotNull(config);
		assertNotNull(config.getLines());
		checkConfig(config);

		return config;
	}

	private void checkConfig(final PartitionerConfig config)
	{
		assertThat(config.getDLM_Partition_Config_ID(), is(1));
		assertThat(config.getLines().get(0).getTableName(), is("ABC"));
		assertThat(config.getLines().get(0).getDLM_Partition_Config_Line_ID(), is(2));

		assertThat(config.getLines().get(1).getTableName(), is("123"));
		assertThat(config.getLines().get(1).getDLM_Partition_Config_Line_ID(), is(4));

		assertThat(config.getLineNotNull("ABC").getTableName(), is("ABC"));
		assertNotNull(config.getLineNotNull("ABC").getReferences());

		assertThat(config.getLineNotNull("ABC").getReferences().get(0).getDLM_Partition_Config_Reference_ID(), is(3));
		assertThat(config.getLineNotNull("ABC").getReferences().get(0).getReferencedTableName(), is("123"));
		assertThat(config.getLineNotNull("ABC").getReferences().get(0).getReferencingColumnName(), is("ABC_columnName"));
	}
}
