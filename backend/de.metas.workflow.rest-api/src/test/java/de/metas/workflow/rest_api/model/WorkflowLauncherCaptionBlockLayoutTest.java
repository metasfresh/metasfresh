/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.workflow.rest_api.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Covers the block-layout rendering of {@link WorkflowLauncherCaption#computeTranslatableString()}: a caption item
 * flagged as block-layout is rendered as its own block of lines, with the {@code " | "} separator suppressed on
 * either side of it, instead of the normal inline join.
 * <p>
 * Covers the block-layout caption acceptance criteria (AC3, AC5, AC9).
 */
class WorkflowLauncherCaptionBlockLayoutTest
{
	private static final String AD_LANGUAGE = "en_US";

	private static String translate(final WorkflowLauncherCaption caption)
	{
		return caption.translate(AD_LANGUAGE);
	}

	private static ITranslatableString value(final String value)
	{
		return TranslatableStrings.anyLanguage(value);
	}

	@Test
	void blockLayoutItem_inTheMiddle_hasNoPipeAdjacentToItsLineBreaks()
	{
		final WorkflowLauncherCaption caption = WorkflowLauncherCaption.builder()
				.fieldsInOrder(ImmutableList.of("a", "block", "c"))
				.fieldValues(ImmutableMap.of(
						"a", value("a"),
						"block", value("b1\nb2"),
						"c", value("c")))
				.blockLayoutFields(ImmutableSet.of("block"))
				.build();

		Assertions.assertThat(translate(caption)).isEqualTo("a\nb1\nb2\nc");
	}

	@Test
	void blockLayoutItem_first_hasNoLeadingEmptyLine()
	{
		final WorkflowLauncherCaption caption = WorkflowLauncherCaption.builder()
				.fieldsInOrder(ImmutableList.of("block", "b", "c"))
				.fieldValues(ImmutableMap.of(
						"block", value("a1\na2"),
						"b", value("b"),
						"c", value("c")))
				.blockLayoutFields(ImmutableSet.of("block"))
				.build();

		Assertions.assertThat(translate(caption)).isEqualTo("a1\na2\nb | c");
	}

	@Test
	void blockLayoutItem_last_hasNoTrailingEmptyLine()
	{
		final WorkflowLauncherCaption caption = WorkflowLauncherCaption.builder()
				.fieldsInOrder(ImmutableList.of("a", "b", "block"))
				.fieldValues(ImmutableMap.of(
						"a", value("a"),
						"b", value("b"),
						"block", value("c1\nc2")))
				.blockLayoutFields(ImmutableSet.of("block"))
				.build();

		Assertions.assertThat(translate(caption)).isEqualTo("a | b\nc1\nc2");
	}

	@Test
	void blockLayoutItem_nextToBlankValuedItem_suppressionComputedAgainstNearestNonBlankItem()
	{
		// "blankFlagged" is itself flagged as block-layout AND blank (no fieldValues entry) - it must be
		// dropped entirely (no stray separator or empty line), so the effective neighbours of "block" are
		// "a" and "c", not "blankFlagged".
		final WorkflowLauncherCaption caption = WorkflowLauncherCaption.builder()
				.fieldsInOrder(ImmutableList.of("a", "blankFlagged", "block", "c"))
				.fieldValues(ImmutableMap.of(
						"a", value("a"),
						"block", value("b1\nb2"),
						"c", value("c")))
				.blockLayoutFields(ImmutableSet.of("blankFlagged", "block"))
				.build();

		Assertions.assertThat(translate(caption)).isEqualTo("a\nb1\nb2\nc");
	}

	@Test
	void noFlagSetAtAll_joinedWithPipe_byteIdenticalToTodaysOutput()
	{
		final WorkflowLauncherCaption caption = WorkflowLauncherCaption.builder()
				.fieldsInOrder(ImmutableList.of("a", "b", "c"))
				.fieldValues(ImmutableMap.of(
						"a", value("a"),
						"b", value("b"),
						"c", value("c")))
				.build();

		Assertions.assertThat(translate(caption)).isEqualTo("a | b | c");
	}
}
