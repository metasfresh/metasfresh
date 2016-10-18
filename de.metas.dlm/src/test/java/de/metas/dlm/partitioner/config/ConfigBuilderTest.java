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
	@Test
	public void test()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.newLine().setTableName("ABC")
				.newRef().setReferencedTableName("123").setReferencingColumnName("ABC_columnName").setReferencedConfigLine("123").endRef()
				.newLine().setTableName("123")
				.endLine()
				.build();

		assertNotNull(config);
		assertNotNull(config.getLines());
		assertThat(config.getLines().size(), is(2));

		assertThat(config.getLines().get(0).getTableName(), is("ABC"));
		assertThat(config.getLines().get(1).getTableName(), is("123"));

		assertThat(config.getLine("ABC").getTableName(), is("ABC"));
		assertNotNull(config.getLine("ABC").getReferences());
		assertThat(config.getLine("ABC").getReferences().get(0).getReferencedTableName(), is("123"));
		assertThat(config.getLine("ABC").getReferences().get(0).getReferencingColumnName(), is("ABC_columnName"));
		assertThat(config.getLine("ABC").getReferences().get(0).getReferencedConfigLine(), is(config.getLine("123")));
	}
}
