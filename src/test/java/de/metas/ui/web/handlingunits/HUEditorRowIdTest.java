package de.metas.ui.web.handlingunits;

import org.junit.Assert;
import org.junit.Test;

import de.metas.ui.web.window.datatypes.DocumentId;

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
		final HUEditorRowId rowId = HUEditorRowId.ofTopLevelHU(12345);
		testToFromJson(rowId);

		Assert.assertEquals("huId", 12345, rowId.getHuId());
		Assert.assertEquals("topLevelHUId", 12345, rowId.getTopLevelHUId());

		// IMPORTANT: top level rowIds shall be perfectly convertible to int.
		Assert.assertEquals("rowId.toJson() as int", 12345, Integer.parseInt(rowId.toJson()));
	}

	@Test
	public void test_includedHU()
	{
		final HUEditorRowId rowId = HUEditorRowId.ofHU(2, 1);
		testToFromJson(rowId);

		Assert.assertEquals("huId", 2, rowId.getHuId());
		Assert.assertEquals("topLevelHUId", 1, rowId.getTopLevelHUId());
	}

	@Test
	public void test_productStorage()
	{
		final HUEditorRowId rowId = HUEditorRowId.ofHUStorage(2, 1, 123);
		testToFromJson(rowId);

		Assert.assertEquals("huId", 2, rowId.getHuId());
		Assert.assertEquals("topLevelHUId", 1, rowId.getTopLevelHUId());
	}

	private static void testToFromJson(final HUEditorRowId rowId)
	{
		{
			final String json = rowId.toJson();
			final HUEditorRowId rowId2 = HUEditorRowId.fromJson(json);
			Assert.assertEquals(rowId, rowId2);
		}

		{
			final String json = rowId.toJson();
			final DocumentId documentId = DocumentId.of(json);
			final HUEditorRowId rowId2 = HUEditorRowId.ofDocumentId(documentId);
			Assert.assertEquals(rowId, rowId2);
		}
	}
}
