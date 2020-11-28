package de.metas.ui.web.handlingunits;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUEditorRowIdTest
{
	@Test
	public void test_topLevelHU()
	{
		final HUEditorRowId rowId = HUEditorRowId.ofTopLevelHU(HuId.ofRepoId(12345));
		testToFromJson(rowId);

		assertThat(rowId.getHuId().getRepoId()).as("huId").isEqualTo(12345);
		assertThat(rowId.getTopLevelHUId().getRepoId()).as("topLevelHUId").isEqualTo(12345);

		// IMPORTANT: top level rowIds shall be perfectly convertible to int.
		assertThat(Integer.parseInt(rowId.toJson())).as("rowId.toJson() as int").isEqualTo(12345);

		assertThat(rowId.toDocumentId().toString()).isEqualTo("12345");
	}

	@Test
	public void test_includedHU()
	{
		final HUEditorRowId rowId = HUEditorRowId.ofHU(HuId.ofRepoId(2), HuId.ofRepoId(1));
		testToFromJson(rowId);

		assertThat(rowId.getHuId().getRepoId()).as("huId").isEqualTo(2);
		assertThat(rowId.getTopLevelHUId().getRepoId()).as("topLevelHUId").isEqualTo(1);

		assertThat(rowId.toDocumentId().toString()).isEqualTo("2_T1");
	}

	@Test
	public void test_productStorage()
	{
		final HUEditorRowId rowId = HUEditorRowId.ofHUStorage(HuId.ofRepoId(2), HuId.ofRepoId(1), ProductId.ofRepoId(123));
		testToFromJson(rowId);

		assertThat(rowId.getHuId().getRepoId()).as("huId").isEqualTo(2);
		assertThat(rowId.getTopLevelHUId().getRepoId()).as("topLevelHUId").isEqualTo(1);
		assertThat(rowId.getStorageProductId().getRepoId()).as("storageProductId").isEqualTo(123);

		assertThat(rowId.toDocumentId().toString()).isEqualTo("2-123_T1");
	}

	private static void testToFromJson(final HUEditorRowId rowId)
	{
		{
			final String json = rowId.toJson();
			final HUEditorRowId rowId2 = HUEditorRowId.fromJson(json);
			assertThat(rowId2).isEqualTo(rowId);
		}

		{
			final String json = rowId.toJson();
			final DocumentId documentId = DocumentId.of(json);
			final HUEditorRowId rowId2 = HUEditorRowId.ofDocumentId(documentId);
			assertThat(rowId2).isEqualTo(rowId);
		}

		{
			final HUEditorRowId rowId2 = HUEditorRowId.ofDocumentId(rowId.toDocumentId());
			assertThat(rowId2).isEqualTo(rowId);
		}
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/1206
	 */
	@Test
	public void test_extractHUIdsOnly_shall_extract_the_HU_from_CUs_too()
	{
		final DocumentIdsSelection rowIds = DocumentIdsSelection.of(ImmutableList.of(
				HUEditorRowId.ofHUStorage(HuId.ofRepoId(10), HuId.ofRepoId(1), ProductId.ofRepoId(100)).toDocumentId() //
		));

		assertThat(HUEditorRowId.extractHUIdsOnly(rowIds)).containsExactly(HuId.ofRepoId(10));
	}

}
