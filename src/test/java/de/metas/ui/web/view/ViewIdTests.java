package de.metas.ui.web.view;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

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

public class ViewIdTests
{
	@Test
	public void test_deriveWithWindowId_StandardCase()
	{
		final ViewId viewId = ViewId.ofViewIdString("123-abcde");
		assertViewIdMatching(viewId, "123", "abcde");

		final ViewId viewId2 = viewId.deriveWithWindowId(WindowId.of(456));
		assertViewIdMatching(viewId2, "456", "abcde");
	}

	@Test
	public void test_deriveWithWindowId_SameWindowId()
	{
		final ViewId viewId = ViewId.ofViewIdString("123-abcde");
		final ViewId viewId2 = viewId.deriveWithWindowId(WindowId.of(123));
		assertThat(viewId).isSameAs(viewId2);
	}

	private static final void assertViewIdMatching(final ViewId viewId, final String windowIdStr, final String viewIdPart)
	{
		final String viewIdStr = windowIdStr + "-" + viewIdPart;

		assertThat(viewId.getWindowId()).isEqualTo(WindowId.fromJson(windowIdStr));
		assertThat(viewId.getViewId()).isEqualTo(viewIdStr);
		assertThat(viewId.toJson()).isEqualTo(viewIdStr);
		assertThat(viewId.getViewIdPart()).isEqualTo(viewIdPart);
		assertThat(viewId.getPart(0)).isEqualTo(windowIdStr);
		assertThat(viewId.getPart(1)).isEqualTo(viewIdPart);
	}
}
