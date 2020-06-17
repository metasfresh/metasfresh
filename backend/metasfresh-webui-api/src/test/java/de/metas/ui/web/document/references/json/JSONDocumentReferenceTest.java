package de.metas.ui.web.document.references.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.ui.web.document.references.DocumentReferenceTargetWindow;
import de.metas.ui.web.window.datatypes.WindowId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class JSONDocumentReferenceTest
{
	@Nested
	public class buildId
	{
		@Test
		public void windowId_without_category()
		{
			final DocumentReferenceTargetWindow targetWindow = DocumentReferenceTargetWindow.ofWindowId(WindowId.fromJson("123W"));
			assertThat(JSONDocumentReference.buildId(targetWindow))
					.isEqualTo("123W");
		}

		@Test
		public void windowId_with_category()
		{
			final DocumentReferenceTargetWindow targetWindow = DocumentReferenceTargetWindow.ofWindowIdAndCategory(
					WindowId.fromJson("123W"),
					"CAT1");
			assertThat(JSONDocumentReference.buildId(targetWindow))
					.isEqualTo("123W_CAT1");
		}
	}
}
