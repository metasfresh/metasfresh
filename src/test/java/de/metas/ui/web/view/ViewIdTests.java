package de.metas.ui.web.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

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
	private final Random random = new Random(System.currentTimeMillis());

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

	@Test
	public void test_getOtherParts_NoElementExpected()
	{
		final ViewId viewId = ViewId.ofViewIdString("packingHUs-351a5c70a1ff4d4fb2852468d6b5b01b");
		assertThat(viewId.getOtherParts()).isEqualTo(ImmutableList.of());
	}

	@Test
	public void test_getOtherParts_OneElementExpected()
	{
		final ViewId viewId = ViewId.ofViewIdString("packingHUs-351a5c70a1ff4d4fb2852468d6b5b01b-1000103");
		assertThat(viewId.getOtherParts()).isEqualTo(ImmutableList.of("1000103"));
	}

	@Test
	public void test_getOtherParts_ThreeElementExpected()
	{
		final ViewId viewId = ViewId.ofViewIdString("packingHUs-351a5c70a1ff4d4fb2852468d6b5b01b-1-2-3");
		assertThat(viewId.getOtherParts()).isEqualTo(ImmutableList.of("1", "2", "3"));
	}

	@Test
	public void test_encodeUsingDigits()
	{
		final char[] BASE10 = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		for (int i = 0; i < 1000; i++)
		{
			final String expected = String.valueOf(i);
			final String actual = ViewId.encodeUsingDigits(i, BASE10);
			assertThat(actual).isEqualTo(expected);
		}
	}
}
