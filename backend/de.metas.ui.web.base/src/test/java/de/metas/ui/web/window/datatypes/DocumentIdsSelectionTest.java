/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.window.datatypes;

import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class DocumentIdsSelectionTest
{
	@Test
	public void givenAllSelection_whenAddingDocumentIds_returnAllSelection()
	{
		final DocumentIdsSelection selection = DocumentIdsSelection.ofCommaSeparatedString("1");

		final DocumentIdsSelection result = DocumentIdsSelection.ALL.addAll(selection);

		assertThat(result.isAll()).isTrue();
	}

	@Test
	public void givenDocumentIds_whenAddingAllSelection_returnAllSelection()
	{
		final DocumentIdsSelection selection = DocumentIdsSelection.ofCommaSeparatedString("1");

		final DocumentIdsSelection result = selection.addAll(DocumentIdsSelection.ALL);

		assertThat(result.isAll()).isTrue();
	}

	@Test
	public void givenDocumentIds_whenAddingDocumentIds_returnCombinedSelection()
	{
		final DocumentIdsSelection reduceResult = Stream.of(DocumentIdsSelection.ofCommaSeparatedString("1"),
															DocumentIdsSelection.ofCommaSeparatedString("2"),
															DocumentIdsSelection.ofCommaSeparatedString("3"))
				.reduce(DocumentIdsSelection.EMPTY, DocumentIdsSelection::addAll);

		assertThat(reduceResult.toSet().size()).isEqualTo(3);
		assertThat(reduceResult.toSet().contains(DocumentId.of("1"))).isTrue();
		assertThat(reduceResult.toSet().contains(DocumentId.of("2"))).isTrue();
		assertThat(reduceResult.toSet().contains(DocumentId.of("3"))).isTrue();
	}
}
