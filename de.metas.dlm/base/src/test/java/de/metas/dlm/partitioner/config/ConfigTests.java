package de.metas.dlm.partitioner.config;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

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

/**
 * If tests fail in here, i recommend to first make sure all tests in {@link PartitionerConfigBuilderTest} succeed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ConfigTests
{

	/**
	 * Verifies that {@link PartitionConfig#getReferences(String)} works.
	 */
	@Test
	public void testGetReferences()
	{
		final PartitionConfig config = PartitionConfig.builder()
				.line("ABC")
				.ref().setReferencedTableName("MNO").setReferencingColumnName("ABC_columnName").endRef()
				.line("MNO") // this one is referenced by both "ABC" and "XYZ"
				.line("XYZ")
				.ref().setReferencedTableName("MNO").setReferencingColumnName("XYZ_columnName").endRef()
				.endLine()
				.build();

		final List<PartitionerConfigReference> referencingLines = config.getReferences("MNO");

		assertThat(referencingLines.size(), is(2));

		final Optional<PartitionerConfigReference> refLine1 = referencingLines.stream().filter(r -> r.getReferencingColumnName().equals("ABC_columnName")).findFirst();
		assertThat(refLine1.isPresent(), is(true));
		assertThat(refLine1.get().getParent().getTableName(), is("ABC"));
		assertThat(refLine1.get().getReferencedTableName(), is("MNO"));

		final Optional<PartitionerConfigReference> refLine2 = referencingLines.stream().filter(r -> r.getReferencingColumnName().equals("XYZ_columnName")).findFirst();
		assertThat(refLine2.isPresent(), is(true));
		assertThat(refLine2.get().getParent().getTableName(), is("XYZ"));
		assertThat(refLine2.get().getReferencedTableName(), is("MNO"));
	}
}
