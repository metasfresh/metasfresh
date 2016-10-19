package de.metas.dlm.partitioner.config;

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

public class ConfigBuilderTest
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
				.newLine().setTableName("XYZ")
				.newRef().setReferencedTableName("123").setReferencingColumnName("XYZ_columnName").setReferencedConfigLine("123").endRef()
				.endLine()
				.build();

		checkConfig(extendedConfig);
		assertThat(extendedConfig.getLines().size(), is(3));

		assertThat(extendedConfig.getLines().get(2).getTableName(), is("XYZ"));

		assertThat(extendedConfig.getLine("XYZ").getTableName(), is("XYZ"));
		assertNotNull(extendedConfig.getLine("XYZ").getReferences());
		assertThat(extendedConfig.getLine("XYZ").getReferences().get(0).getReferencedTableName(), is("123"));
		assertThat(extendedConfig.getLine("XYZ").getReferences().get(0).getReferencingColumnName(), is("XYZ_columnName"));
		assertThat(extendedConfig.getLine("XYZ").getReferences().get(0).getReferencedConfigLine(), is(extendedConfig.getLine("123")));
	}

	private void checkConfig(final PartitionerConfig config)
	{
		assertThat(config.getLines().get(0).getTableName(), is("ABC"));
		assertThat(config.getLines().get(1).getTableName(), is("123"));

		assertThat(config.getLine("ABC").getTableName(), is("ABC"));
		assertNotNull(config.getLine("ABC").getReferences());
		assertThat(config.getLine("ABC").getReferences().get(0).getReferencedTableName(), is("123"));
		assertThat(config.getLine("ABC").getReferences().get(0).getReferencingColumnName(), is("ABC_columnName"));
		assertThat(config.getLine("ABC").getReferences().get(0).getReferencedConfigLine(), is(config.getLine("123")));
	}

	private PartitionerConfig createAndCheckBuilder0()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.newLine().setTableName("ABC")
				.newRef().setReferencedTableName("123").setReferencingColumnName("Record_ID").setReferencedConfigLine("123").endRef()
				.newLine().setTableName("123")
				.endLine()
				.build();

		assertNotNull(config);
		assertNotNull(config.getLines());
		checkConfig(config);

		return config;
	}
}
