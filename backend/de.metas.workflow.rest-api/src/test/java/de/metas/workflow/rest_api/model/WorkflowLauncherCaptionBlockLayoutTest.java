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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

	@Test
	void tieOnEveryOrderBy_prefixValue_tieBrokenByCompleteCaptionIncludingBlockSeparator()
	{
		// Both captions tie on the only configured order-by ("code" == "C1" in both), so the comparator falls
		// through to WorkflowLauncherCaption's last resort: comparing the complete glued caption strings
		// (see WorkflowLauncherCaption#orderBy / #toCompleteCaptionComparator).
		//
		// "customer" is the differing field, immediately followed by the block-layout field "block", and its
		// value in one caption ("Acme") is a strict prefix of its value in the other ("Acme GmbH"). With block
		// layout on, the glued strings are:
		//   captionPrefix: "C1 | Acme"     + "\n" + "X"  ->  "C1 | Acme\nX"
		//   captionLonger: "C1 | Acme GmbH" + "\n" + "X" ->  "C1 | Acme GmbH\nX"
		// They share the common prefix "C1 | Acme"; right after it, captionPrefix has '\n' (0x0A, the start of
		// its block separator) while captionLonger still has ' ' (0x20, continuing " GmbH"). Since 0x0A < 0x20,
		// captionPrefix sorts BEFORE captionLonger.
		//
		// This is the case AC10 pins: had "block" not been block-layout-flagged, the separator at that same
		// position would be " | " instead of "\n" - its first char ' ' (0x20) TIES against captionLonger's ' ',
		// so the comparison would continue into '|' (0x7C) vs 'G' (0x47) and produce the OPPOSITE order. Block
		// layout's '\n' separator therefore changes which caption sorts first for this tie.
		final WorkflowLauncherCaption captionPrefix = WorkflowLauncherCaption.builder()
				.fieldsInOrder(ImmutableList.of("code", "customer", "block"))
				.fieldValues(ImmutableMap.of(
						"code", value("C1"),
						"customer", value("Acme"),
						"block", value("X")))
				.blockLayoutFields(ImmutableSet.of("block"))
				.build();

		final WorkflowLauncherCaption captionLonger = WorkflowLauncherCaption.builder()
				.fieldsInOrder(ImmutableList.of("code", "customer", "block"))
				.fieldValues(ImmutableMap.of(
						"code", value("C1"),
						"customer", value("Acme GmbH"),
						"block", value("X")))
				.blockLayoutFields(ImmutableSet.of("block"))
				.build();

		Assertions.assertThat(translate(captionPrefix)).isEqualTo("C1 | Acme\nX");
		Assertions.assertThat(translate(captionLonger)).isEqualTo("C1 | Acme GmbH\nX");

		final Comparator<WorkflowLauncherCaption> comparator = WorkflowLauncherCaption.orderBy(
				AD_LANGUAGE,
				ImmutableList.of(WorkflowLauncherCaption.OrderBy.builder().field("code").build()));

		final List<WorkflowLauncherCaption> sorted = new ArrayList<>(ImmutableList.of(captionLonger, captionPrefix));
		sorted.sort(comparator);

		Assertions.assertThat(sorted).containsExactly(captionPrefix, captionLonger);
	}
}
