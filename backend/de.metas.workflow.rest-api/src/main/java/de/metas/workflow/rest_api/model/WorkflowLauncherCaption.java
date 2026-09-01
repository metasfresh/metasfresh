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
import de.metas.util.StringUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class WorkflowLauncherCaption
{
	@NonNull private final ImmutableList<String> fieldsInOrder;
	@NonNull private final ImmutableMap<String, ITranslatableString> fieldValues;
	@NonNull private final ImmutableMap<String, Comparable<?>> comparingKeys;
	/** Fields (from {@link #fieldsInOrder}) whose value shall be rendered as its own block of lines, with no {@code " | "} adjacent to it. */
	@NonNull private final ImmutableSet<String> blockLayoutFields;

	@Nullable private ITranslatableString _asTranslatableString = null; // lazy
	@NonNull private final HashMap<String, String> trlsCache = new HashMap<>();

	@Builder
	private WorkflowLauncherCaption(
			@NonNull final List<String> fieldsInOrder,
			@NonNull final Map<String, ITranslatableString> fieldValues,
			@Nullable final Map<String, Comparable<?>> comparingKeys,
			@Nullable final Set<String> blockLayoutFields)
	{
		this.fieldsInOrder = ImmutableList.copyOf(fieldsInOrder);
		this.fieldValues = ImmutableMap.copyOf(fieldValues);
		this.comparingKeys = comparingKeys != null ? ImmutableMap.copyOf(comparingKeys) : ImmutableMap.of();
		this.blockLayoutFields = blockLayoutFields != null ? ImmutableSet.copyOf(blockLayoutFields) : ImmutableSet.of();
	}

	public static WorkflowLauncherCaption of(@NonNull final ITranslatableString caption)
	{
		return builder()
				.fieldsInOrder(ImmutableList.of("caption"))
				.fieldValues(ImmutableMap.of("caption", caption))
				.build();
	}

	public ITranslatableString toTranslatableString()
	{
		ITranslatableString trl = _asTranslatableString;
		if (trl == null)
		{
			trl = this._asTranslatableString = computeTranslatableString();
		}
		return trl;
	}

	private ITranslatableString computeTranslatableString()
	{
		// Blank-valued fields are dropped first, so block-layout suppression below is always computed
		// against the nearest NON-BLANK neighbour, never against a blank one.
		final ImmutableList<String> nonBlankFields = fieldsInOrder
				.stream()
				.filter(field -> !TranslatableStrings.isBlank(fieldValues.get(field)))
				.collect(ImmutableList.toImmutableList());

		if (nonBlankFields.isEmpty())
		{
			return TranslatableStrings.join(" | ", ImmutableList.of());
		}

		ITranslatableString result = fieldValues.get(nonBlankFields.get(0));
		for (int i = 1; i < nonBlankFields.size(); i++)
		{
			final String previousField = nonBlankFields.get(i - 1);
			final String field = nonBlankFields.get(i);

			// A block-layout item is rendered as its own block of lines: the " | " that would otherwise
			// land next to either of its line breaks is suppressed on both sides, in favor of a line break.
			final String separator = blockLayoutFields.contains(previousField) || blockLayoutFields.contains(field)
					? "\n"
					: " | ";

			result = TranslatableStrings.join(separator, result, fieldValues.get(field));
		}
		return result;
	}

	public String translate(@NonNull final String adLanguage)
	{
		return trlsCache.computeIfAbsent(adLanguage, this::buildTranslation);
	}

	private String buildTranslation(final String adLanguage)
	{
		return toTranslatableString().translate(adLanguage);
	}

	private String getFieldValue(@NonNull final String field, @NonNull final String adLanguage)
	{
		final ITranslatableString value = fieldValues.get(field);
		return value != null
				? StringUtils.trimBlankToNull(value.translate(adLanguage))
				: null;
	}

	private Comparable<?> getFieldComparingKey(@NonNull final String field, @NonNull final String adLanguage)
	{
		final Comparable<?> cmp = comparingKeys.get(field);
		if (cmp != null)
		{
			return cmp;
		}

		return getFieldValue(field, adLanguage);
	}

	public static Comparator<WorkflowLauncherCaption> orderBy(@NonNull final String adLanguage, @NonNull final List<OrderBy> orderBys)
	{
		//
		// Order by each given field
		Comparator<WorkflowLauncherCaption> result = null;
		for (final OrderBy orderBy : orderBys)
		{
			final Comparator<WorkflowLauncherCaption> cmp = toComparator(adLanguage, orderBy);
			result = result != null
					? result.thenComparing(cmp)
					: cmp;
		}

		// Last, order by complete caption
		final Comparator<WorkflowLauncherCaption> completeCaptionComparator = toCompleteCaptionComparator(adLanguage);
		result = result != null
				? result.thenComparing(completeCaptionComparator)
				: completeCaptionComparator;

		return result;
	}

	private static Comparator<WorkflowLauncherCaption> toComparator(@NonNull final String adLanguage, @NonNull final OrderBy orderBy)
	{
		final String field = orderBy.getField();
		final Function<WorkflowLauncherCaption, Comparable<?>> keyExtractor = caption -> caption.getFieldComparingKey(field, adLanguage);

		//noinspection unchecked,rawtypes
		Comparator<Comparable> keyComparator = Comparator.naturalOrder();
		if (!orderBy.isAscending())
		{
			keyComparator = keyComparator.reversed();
		}
		keyComparator = Comparator.nullsLast(keyComparator);

		return Comparator.comparing(keyExtractor, keyComparator);
	}

	private static Comparator<WorkflowLauncherCaption> toCompleteCaptionComparator(@NonNull final String adLanguage)
	{
		final Function<WorkflowLauncherCaption, String> keyExtractor = caption -> caption.translate(adLanguage);
		Comparator<String> keyComparator = Comparator.nullsLast(Comparator.naturalOrder());
		return Comparator.comparing(keyExtractor, keyComparator);
	}

	//
	//
	//

	@Value
	@Builder
	public static class OrderBy
	{
		@NonNull String field;
		@Builder.Default boolean ascending = true;

		public static OrderBy descending(@NonNull final ReferenceListAwareEnum field)
		{
			return descending(field.getCode());
		}

		public static OrderBy descending(@NonNull final String field)
		{
			return builder().field(field).ascending(false).build();
		}
	}

}
