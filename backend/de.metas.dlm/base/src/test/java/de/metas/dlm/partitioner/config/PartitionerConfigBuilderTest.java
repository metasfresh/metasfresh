package de.metas.dlm.partitioner.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

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
	 * Verifies tha {@link PartitionConfig#builder()} works.
	 */
	@Test
	public void testConfigBuilder()
	{
		final PartitionConfig config = createAndCheckBuilder0();
		assertThat(config.getLines()).hasSize(2);
	}

	@Test
	public void testConfigBuilderExtendLine()
	{
		final PartitionConfig config = PartitionConfig.builder().setDLM_Partition_Config_ID(1)
				.line("ABC").setDLM_Partition_Config_Line(2)
				.ref().setReferencedTableName("123").setReferencingColumnName("ABC_columnName1").setDLM_Partition_Config_Reference_ID(3).endRef()
				.line("ABC")
				.ref().setReferencedTableName("789").setReferencingColumnName("ABC_columnName2").setDLM_Partition_Config_Reference_ID(4).endRef()
				.line("123").setDLM_Partition_Config_Line(4)
				.endLine()
				.build();

		assertThat(config.getLines()).hasSize(2);

		assertThat(config.getLines().get(0).getTableName()).isEqualToIgnoringCase("ABC");
		assertThat(config.getLineNotNull("ABC").getTableName()).isEqualToIgnoringCase("ABC");

		assertThat(config.getLineNotNull("ABC").getReferences()).isNotNull();
		assertThat(config.getLineNotNull("ABC").getReferences()).hasSize(2);

		assertThat(config.getLineNotNull("ABC").getReferences().get(0).getReferencedTableName()).isEqualToIgnoringCase("123");
		assertThat(config.getLineNotNull("ABC").getReferences().get(1).getReferencedTableName()).isEqualToIgnoringCase("789");
	}

	/**
	 * Verifies that {@link PartitionConfig#builder(PartitionConfig)} works.
	 * If this test fails, I recommend to first check if {@link #testConfigBuilder()} also fails.
	 */
	@Test
	public void testExtendConfigBuilder()
	{
		final PartitionConfig config = createAndCheckBuilder0();
		assertThat(config.getLines()).hasSize(2);

		final PartitionConfig extendedConfig = PartitionConfig.builder(config)
				.line("XYZ")
				.ref().setReferencedTableName("123").setReferencingColumnName("XYZ_columnName").endRef()
				.endLine()
				.build();

		checkConfig(extendedConfig);
		assertThat(extendedConfig.getLines()).hasSize(3);

		assertThat(extendedConfig.getLines().get(2).getTableName()).isEqualTo("XYZ");

		assertThat(extendedConfig.getLineNotNull("XYZ").getTableName()).isEqualTo("XYZ");
		assertThat(extendedConfig.getLineNotNull("XYZ").getReferences()).isNotNull();
		assertThat(extendedConfig.getLineNotNull("XYZ").getReferences().get(0).getReferencedTableName()).isEqualTo("123");
		assertThat(extendedConfig.getLineNotNull("XYZ").getReferences().get(0).getReferencingColumnName()).isEqualTo("XYZ_columnName");
	}

	private PartitionConfig createAndCheckBuilder0()
	{
		final PartitionConfig config = PartitionConfig.builder().setDLM_Partition_Config_ID(1)
				.line("ABC").setDLM_Partition_Config_Line(2)
				.ref().setReferencedTableName("123").setReferencingColumnName("ABC_columnName").setDLM_Partition_Config_Reference_ID(3).endRef()
				.line("123").setDLM_Partition_Config_Line(4)
				.endLine()
				.build();

		assertThat(config).isNotNull();
		assertThat(config.getLines()).isNotNull();
		checkConfig(config);

		return config;
	}

	private void checkConfig(final PartitionConfig config)
	{
		assertThat(config.getDLM_Partition_Config_ID()).isEqualTo(1);
		assertThat(config.getLines().get(0).getTableName()).isEqualTo("ABC");
		assertThat(config.getLines().get(0).getDLM_Partition_Config_Line_ID()).isEqualTo(2);

		assertThat(config.getLines().get(1).getTableName()).isEqualTo("123");
		assertThat(config.getLines().get(1).getDLM_Partition_Config_Line_ID()).isEqualTo(4);

		assertThat(config.getLineNotNull("ABC").getTableName()).isEqualTo("ABC");
		assertThat(config.getLineNotNull("ABC").getReferences()).isNotNull();

		assertThat(config.getLineNotNull("ABC").getReferences().get(0).getDLM_Partition_Config_Reference_ID()).isEqualTo(3);
		assertThat(config.getLineNotNull("ABC").getReferences().get(0).getReferencedTableName()).isEqualTo("123");
		assertThat(config.getLineNotNull("ABC").getReferences().get(0).getReferencingColumnName()).isEqualTo("ABC_columnName");
	}
}