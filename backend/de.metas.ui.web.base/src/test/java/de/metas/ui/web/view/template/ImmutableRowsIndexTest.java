/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.view.template;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ImmutableRowsIndexTest
{
	private static MockedRow row(final int id)
	{
		return new MockedRow(id, null);
	}

	private static MockedRow row(final int id, final String data)
	{
		return new MockedRow(id, data);
	}

	private static TestingFilter excludingIds(int ... ids)
	{
		final ImmutableSet<DocumentId> excludeIds = IntStream.of(ids).mapToObj(DocumentId::of).collect(ImmutableSet.toImmutableSet());
		return new TestingFilter(excludeIds);
	}

	@Test
	public void constructAndTest()
	{
		final ImmutableList<MockedRow> rows = ImmutableList.of(row(1), row(2), row(3), row(4));
		final ImmutableRowsIndex<MockedRow> index = ImmutableRowsIndex.of(rows);
		assertThat(index.getDocumentId2TopLevelRows().values()).isEqualTo(rows);
		rows.forEach(row -> assertThat(index.isRelevantForRefreshing(row.getId())).isTrue());
	}

	@Test
	public void addingRow()
	{
		final ImmutableRowsIndex<MockedRow> index = ImmutableRowsIndex.of(ImmutableList.of(row(1), row(2)))
				.addingRow(row(3));

		assertThat(index.getDocumentId2TopLevelRows().values()).containsExactly(row(1), row(2), row(3));
	}

	@Test
	public void removingRowId()
	{
		final ImmutableRowsIndex<MockedRow> index = ImmutableRowsIndex.of(ImmutableList.of(row(1), row(2), row(3)))
				.removingRowId(DocumentId.of(3));

		assertThat(index.getDocumentId2TopLevelRows().values()).containsExactly(row(1), row(2));
	}

	@Test
	public void replacingRows()
	{
		final ImmutableRowsIndex<MockedRow> index = ImmutableRowsIndex.of(ImmutableList.of(row(1), row(2), row(3)))
				.replacingRows(
						DocumentIdsSelection.ofCommaSeparatedString("1,3"),
						ImmutableList.of(row(1, "updated"), row(3, "updated"), row(4, "new"))
				);

		assertThat(index.getDocumentId2TopLevelRows().values()).containsExactly(
				row(1, "updated"),
				row(2),
				row(3, "updated"),
				row(4, "new")
		);
	}

	@Test
	public void withFilter()
	{
		final ImmutableRowsIndex<MockedRow> index = ImmutableRowsIndex.of(ImmutableList.of(row(1), row(2), row(3)));
		final ImmutableRowsIndex<MockedRow> indexFiltered = index.withFilter(excludingIds(2));
		assertThat(indexFiltered.getDocumentId2TopLevelRows().values()).containsExactly(row(1), row(3));

		final ImmutableRowsIndex<MockedRow> indexFilteredThenUnfiltered = indexFiltered.withFilter(excludingIds());
		assertThat(indexFilteredThenUnfiltered.getDocumentId2TopLevelRows().values()).containsExactly(row(1), row(2), row(3));
	}

	//
	//
	//
	//
	//

	@Value
	private static class MockedRow implements IViewRow
	{
		DocumentId id;
		String data;

		public MockedRow(final int id, final String data)
		{
			this.id = DocumentId.of(id);
			this.data = data;
		}

		@Override
		public boolean isProcessed()
		{
			throw new IllegalStateException("should not be called");
		}

		@Nullable
		@Override
		public DocumentPath getDocumentPath()
		{
			throw new IllegalStateException("should not be called");
		}

		@Override
		public Set<String> getFieldNames()
		{
			throw new IllegalStateException("should not be called");
		}

		@Override
		public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
		{
			throw new IllegalStateException("should not be called");
		}
	}

	@EqualsAndHashCode
	@ToString
	private static class TestingFilter implements Predicate<MockedRow>
	{
		private final ImmutableSet<DocumentId> excludeIds;

		private TestingFilter(final Set<DocumentId> excludeIds)
		{
			this.excludeIds = ImmutableSet.copyOf(excludeIds);
		}

		@Override
		public boolean test(final MockedRow mockedRow)
		{
			return !excludeIds.contains(mockedRow.id);
		}
	}
}