package de.metas.ui.web.handlingunits;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;

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

public class HUEditorRowTest
{

	@Test
	public void testCreateHUEditorRow()
	{
		final int huId = 10;
		final int topLevelHUId = 20;
		final int windowId = 123;

		final HUEditorRow huEditorRow = HUEditorRow.builder(WindowId.of(windowId))
				.setRowId(HUEditorRowId.ofHU(huId, topLevelHUId))
				.setType(HUEditorRowType.TU)
				.setTopLevel(false)
				.build();
		assertThat(huEditorRow.getM_HU_ID()).isEqualTo(huId);

		final DocumentId documentId = huEditorRow.getHURowId().toDocumentId();
		assertThat(documentId.isInt()).isFalse();
		assertThat(documentId.toString()).isEqualTo(huId + "_T" + topLevelHUId); // expecting 10_T20

		final DocumentPath documentPath = huEditorRow.getDocumentPath();
		assertThat(documentPath.getWindowId().toInt()).isEqualTo(windowId);
		assertThat(documentPath.getDocumentId().toInt()).isEqualTo(huId);
	}

}
