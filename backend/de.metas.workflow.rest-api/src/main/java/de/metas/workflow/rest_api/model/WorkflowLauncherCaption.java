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
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class WorkflowLauncherCaption
{
	@NonNull private final ImmutableList<String> fieldsInOrder;
	@NonNull private final ImmutableMap<String, ITranslatableString> fieldValues;
	@NonNull private final ImmutableMap<String, Comparable<?>> comparingKeys;

	@Nullable private ITranslatableString _asTranslatableString = null; // lazy
	@NonNull private final HashMap<String, String> trlsCache = new HashMap<>();

	@Builder
	private WorkflowLauncherCaption(
			@NonNull final ImmutableList<String> fieldsInOrder,
			@NonNull final ImmutableMap<String, ITranslatableString> fieldValues,
			@Nullable final ImmutableMap<String, Comparable<?>> comparingKeys)
	{
		this.fieldsInOrder = fieldsInOrder;
		this.fieldValues = fieldValues;
		this.comparingKeys = comparingKeys != null ? comparingKeys : ImmutableMap.of();
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
		final ImmutableList<ITranslatableString> parts = fieldsInOrder
				.stream()
				.map(fieldValues::get)
				.filter(caption -> !TranslatableStrings.isBlank(caption))
				.collect(ImmutableList.toImmutableList());

		return TranslatableStrings.join(" | ", parts);
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

		//noinspection unchecked
		Comparator<Comparable<?>> keyComparator = (Comparator<Comparable<?>>)Comparator.naturalOrder();
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
