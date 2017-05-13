package de.metas.ui.web.process.json;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import de.metas.ui.web.view.ViewId;
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

public class ViewIdTest
{
	private final Random random = new Random(System.currentTimeMillis());

	@Test
	public void test_ofViewIdString_CorrectWindowId()
	{
		final ViewId viewId = randomViewId();

		final WindowId expectedWindowId = viewId.getWindowId();
		final ViewId viewId2 = ViewId.ofViewIdString(viewId.getViewId(), expectedWindowId);

		Assert.assertEquals(viewId, viewId2);
	}

	@Test
	public void test_of_CorrectWindowId()
	{
		final ViewId viewId = randomViewId();

		final String expectedWindowIdStr = viewId.getWindowId().toJson();
		final ViewId viewId2 = ViewId.of(expectedWindowIdStr, viewId.toJson());

		Assert.assertEquals(viewId, viewId2);
	}

	@Test
	public void test_ofViewIdString_NullWindowId()
	{
		final ViewId viewId = randomViewId();

		final WindowId expectedWindowId = null;
		final ViewId viewId2 = ViewId.ofViewIdString(viewId.getViewId(), expectedWindowId);

		Assert.assertEquals(viewId, viewId2);
	}

	@Test
	public void test_of_NullWindowId()
	{
		final ViewId viewId = randomViewId();

		final String expectedWindowIdStr = null;
		final ViewId viewId2 = ViewId.of(expectedWindowIdStr, viewId.toJson());

		Assert.assertEquals(viewId, viewId2);
	}

	@Test
	public void test_ofViewIdString_WrongWindowId()
	{
		final ViewId viewId = randomViewId();

		final WindowId expectedWindowId = randomWindowIdButNot(viewId.getWindowId());

		try
		{
			final ViewId viewId2 = ViewId.ofViewIdString(viewId.getViewId(), expectedWindowId);
			Assert.fail("Exception was expected because windowId are not matching: viewId2=" + viewId2 + ", expectedWindowId=" + expectedWindowId);
		}
		catch (final IllegalArgumentException ex)
		{
			// OK
		}
	}

	@Test
	public void test_of_WrongWindowId()
	{
		final ViewId viewId = randomViewId();

		final String expectedWindowIdStr = randomWindowIdButNot(viewId.getWindowId()).toJson();

		try
		{
			final ViewId viewId2 = ViewId.of(expectedWindowIdStr, viewId.toJson());
			Assert.fail("Exception was expected because windowId are not matching: viewId2=" + viewId2 + ", expectedWindowId=" + expectedWindowIdStr);
		}
		catch (final IllegalArgumentException ex)
		{
			// OK
		}
	}

	private final ViewId randomViewId()
	{
		final WindowId windowId = randomWindowId();
		return ViewId.random(windowId);
	}

	private final WindowId randomWindowId()
	{
		final int windowIdInt = random.nextInt(9000000) + 1;
		return WindowId.of(windowIdInt);
	}

	private final WindowId randomWindowIdButNot(final WindowId windowIdToExclude)
	{
		WindowId windowId = randomWindowId();
		while (windowId.equals(windowIdToExclude))
		{
			windowId = randomWindowId();
		}
		return windowId;
	}

}
