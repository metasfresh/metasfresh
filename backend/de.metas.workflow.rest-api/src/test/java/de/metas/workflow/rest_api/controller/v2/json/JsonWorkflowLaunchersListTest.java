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

package de.metas.workflow.rest_api.controller.v2.json;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import lombok.NonNull;
import lombok.Value;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

class JsonWorkflowLaunchersListTest
{
	private static JsonOpts newJsonOpts()
	{
		return JsonOpts.builder()
				.adLanguage("en_US")
				.build();
	}

	WorkflowLauncher launcher(Captions.Part... captionParts)
	{
		return WorkflowLauncher.builder()
				.applicationId(MobileApplicationId.ofString("test"))
				.caption(Captions.of(captionParts))
				.build();
	}

	@Nullable
	private static ImmutableList<String> toJsonAndGetCaptions(final WorkflowLaunchersList launchers)
	{
		//noinspection DataFlowIssue
		return JsonWorkflowLaunchersList.of(launchers, false, newJsonOpts())
				.getLaunchers()
				.stream()
				.map(JsonWorkflowLauncher::getCaption)
				.collect(ImmutableList.toImmutableList());
	}

	@Test
	void caption_using_plainString()
	{
		final WorkflowLaunchersList launchers = WorkflowLaunchersList.builder()
				.launchers(ImmutableList.of(
						launcher(Captions.Part.of("caption", "C")),
						launcher(Captions.Part.of("caption", "B")),
						launcher(Captions.Part.of("caption", "A"))
				))
				.timestamp(SystemTime.asInstant())
				.build();

		Assertions.assertThat(toJsonAndGetCaptions(launchers))
				.containsExactly("A", "B", "C");
	}

	@Test
	void caption_orderBy_missing_field()
	{
		final WorkflowLaunchersList launchers = WorkflowLaunchersList.builder()
				.launchers(ImmutableList.of(
						launcher(Captions.Part.of("field", "C")),
						launcher(Captions.Part.of("field", "B")),
						launcher(Captions.Part.of("field", "A"))
				))
				.orderByField(WorkflowLauncherCaption.OrderBy.descending("missingField"))
				.timestamp(SystemTime.asInstant())
				.build();

		Assertions.assertThat(toJsonAndGetCaptions(launchers))
				.containsExactly("A", "B", "C");
	}

	@Test
	void caption_orderBy_field_withNullValues()
	{
		final WorkflowLaunchersList launchers = WorkflowLaunchersList.builder()
				.launchers(ImmutableList.of(
						launcher(Captions.Part.of("field", "A")),
						launcher(Captions.Part.of("field", "B")),
						launcher(Captions.Part.of("field", "C")),
						launcher(Captions.Part.of("otherField", "Z"))
				))
				.orderByField(WorkflowLauncherCaption.OrderBy.descending("field"))
				.timestamp(SystemTime.asInstant())
				.build();

		Assertions.assertThat(toJsonAndGetCaptions(launchers))
				.containsExactly("C", "B", "A", "Z");
	}

	@Test
	void caption_using_int_compableKey()
	{
		final WorkflowLaunchersList launchers = WorkflowLaunchersList.builder()
				.launchers(ImmutableList.of(
						launcher(Captions.Part.of("SetupPlace", null), Captions.Part.of("DocNo", "A001"), Captions.Part.of("Addr", "000")),
						launcher(Captions.Part.of("SetupPlace", "100", 100), Captions.Part.of("DocNo", "A222"), Captions.Part.of("Addr", "333")),
						launcher(Captions.Part.of("SetupPlace", "1234", 1234), Captions.Part.of("DocNo", "A111"), Captions.Part.of("Addr", "4444")),
						launcher(Captions.Part.of("SetupPlace", "100", 100), Captions.Part.of("DocNo", "A333"), Captions.Part.of("Addr", "890"))
				))
				.orderByField(WorkflowLauncherCaption.OrderBy.descending("SetupPlace"))
				.timestamp(SystemTime.asInstant())
				.build();

		Assertions.assertThat(toJsonAndGetCaptions(launchers))
				.containsExactly(
						"1234 | A111 | 4444",
						"100 | A222 | 333",
						"100 | A333 | 890",
						"A001 | 000"
				);
	}

	//
	//
	//
	//
	//

	private static class Captions
	{
		static WorkflowLauncherCaption of(Part... parts)
		{
			final ImmutableList.Builder<String> fieldsInOrderBuilder = ImmutableList.builder();
			final ImmutableMap.Builder<String, ITranslatableString> valuesBuilder = ImmutableMap.builder();
			final ImmutableMap.Builder<String, Comparable<?>> comparableKeysBuilder = ImmutableMap.builder();
			for (final Part part : parts)
			{
				final String field = part.getField();
				final ITranslatableString value = part.getValue();
				final Comparable<?> comparableKey = part.getComparableKey();

				fieldsInOrderBuilder.add(field);
				if (value != null)
				{
					valuesBuilder.put(field, value);
				}
				if (comparableKey != null)
				{
					comparableKeysBuilder.put(field, comparableKey);
				}
			}

			return WorkflowLauncherCaption.builder()
					.fieldsInOrder(fieldsInOrderBuilder.build())
					.fieldValues(valuesBuilder.build())
					.comparingKeys(comparableKeysBuilder.build())
					.build();
		}

		@Value
		private static class Part
		{
			@NonNull String field;
			@Nullable ITranslatableString value;
			@Nullable Comparable<?> comparableKey;

			static Part of(String field, String value, Comparable<?> comparableKey)
			{
				return new Part(field, value != null ? TranslatableStrings.anyLanguage(value) : null, comparableKey);
			}

			static Part of(String field, String value)
			{
				return of(field, value, null);
			}
		}
	}
}
