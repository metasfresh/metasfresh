package de.metas.ui.web.document.filter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Also see {@link de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Immutable
@EqualsAndHashCode
@ToString
public final class DocumentFilter
{
	public static DocumentFilter singleParameterFilter(
			@NonNull final String filterId,
			final String fieldName,
			final Operator operator,
			final Object value)
	{
		return builder()
				.filterId(filterId)
				.parameter(DocumentFilterParam.builder()
						.setFieldName(fieldName)
						.setOperator(operator)
						.setValue(value)
						.build())
				.build();
	}

	public static DocumentFilter inArrayFilter(
			@NonNull final String filterId,
			@NonNull final String fieldName,
			@NonNull final Collection<Integer> values)
	{
		Check.assumeNotEmpty(values, "values is not empty");

		return builder()
				.filterId(filterId)
				.parameter(DocumentFilterParam.builder()
						.setFieldName(fieldName)
						.setOperator(Operator.IN_ARRAY)
						.setValue(ImmutableList.copyOf(values))
						.build())
				.build();
	}

	public static DocumentFilter equalsFilter(
			@NonNull final String fieldName,
			@Nullable final Object value)
	{
		final String filterId = fieldName + "=" + value;
		return equalsFilter(filterId, fieldName, value);
	}

	public static DocumentFilter equalsFilter(
			@NonNull final String filterId,
			@NonNull final String fieldName,
			@Nullable final Object value)
	{
		return builder()
				.filterId(filterId)
				.parameter(DocumentFilterParam.builder()
						.setFieldName(fieldName)
						.setOperator(Operator.EQUAL)
						.setValue(value)
						.build())
				.build();
	}

	@Getter
	private final String filterId;
	private final ITranslatableString caption;

	private final ImmutableList<DocumentFilterParam> parameters;
	private final ImmutableMap<String, DocumentFilterParam> parametersByName;
	private final ImmutableSet<String> internalParameterNames;
	@Getter
	private final boolean facetFilter;

	@Builder(toBuilder = true)
	private DocumentFilter(
			@NonNull final String filterId,
			@Nullable final ITranslatableString caption,
			final boolean facetFilter,
			@NonNull @Singular final ImmutableList<DocumentFilterParam> parameters,
			@NonNull @Singular final ImmutableSet<String> internalParameterNames)
	{
		Check.assumeNotEmpty(filterId, "filterId is not empty");

		this.filterId = filterId;
		this.caption = caption != null ? caption : TranslatableStrings.empty();
		this.facetFilter = facetFilter;

		this.parameters = ImmutableList.copyOf(parameters);
		this.parametersByName = this.parameters.stream()
				.filter(parameter -> !parameter.isSqlFilter())
				.collect(GuavaCollectors.toImmutableMapByKey(DocumentFilterParam::getFieldName));

		this.internalParameterNames = ImmutableSet.copyOf(internalParameterNames);
	}

	private DocumentFilter(@NonNull final DocumentFilter from, @NonNull final String filterId)
	{
		Check.assumeNotEmpty(filterId, "filterId is not empty");

		this.filterId = filterId;
		this.caption = from.caption;
		this.facetFilter = from.facetFilter;
		this.parameters = from.parameters;
		this.parametersByName = from.parametersByName;
		this.internalParameterNames = from.internalParameterNames;
	}

	public DocumentFilter withId(@NonNull final String id)
	{
		return new DocumentFilter(this, id);
	}

	@Nullable
	public String getCaption(@Nullable final String adLanguage)
	{
		return caption != null
				? (adLanguage != null ? caption.translate(adLanguage) : caption.getDefaultValue())
				: null;
	}

	public boolean hasParameters()
	{
		return !parameters.isEmpty();
	}

	public ImmutableList<DocumentFilterParam> getParameters()
	{
		return parameters;
	}

	public boolean isInternalParameter(final String parameterName)
	{
		return internalParameterNames.contains(parameterName);
	}

	public DocumentFilterParam getParameter(@NonNull final String parameterName)
	{
		final DocumentFilterParam parameter = getParameterOrNull(parameterName);
		if (parameter == null)
		{
			throw new AdempiereException("Parameter " + parameterName + " not found in " + this);
		}
		return parameter;
	}

	@Nullable
	public DocumentFilterParam getParameterOrNull(@NonNull final String parameterName)
	{
		return parametersByName.get(parameterName);
	}

	@Nullable
	public String getParameterValueAsString(@NonNull final String parameterName)
	{
		final DocumentFilterParam param = getParameter(parameterName);
		return param.getValueAsString();
	}

	@Nullable
	public String getParameterValueAsString(@NonNull final String parameterName, @Nullable final String defaultValue)
	{
		final DocumentFilterParam param = getParameterOrNull(parameterName);
		if (param == null)
		{
			return defaultValue;
		}

		return param.getValueAsString();
	}

	public int getParameterValueAsInt(@NonNull final String parameterName, final int defaultValue)
	{
		final DocumentFilterParam param = getParameterOrNull(parameterName);
		if (param == null)
		{
			return defaultValue;
		}

		return param.getValueAsInt(defaultValue);
	}

	@Nullable
	public Boolean getParameterValueAsBoolean(@NonNull final String parameterName, @Nullable final Boolean defaultValue)
	{
		final DocumentFilterParam param = getParameterOrNull(parameterName);
		if (param == null)
		{
			return defaultValue;
		}

		return param.getValueAsBoolean(defaultValue);
	}

	public boolean getParameterValueAsBoolean(@NonNull final String parameterName, final boolean defaultValue)
	{
		final DocumentFilterParam param = getParameterOrNull(parameterName);
		if (param == null)
		{
			return defaultValue;
		}

		//noinspection ConstantConditions
		return param.getValueAsBoolean(defaultValue);
	}

	@Nullable
	public LocalDate getParameterValueAsLocalDateOrNull(@NonNull final String parameterName)
	{
		return getParameterValueAsLocalDateOr(parameterName, null);
	}

	@Nullable
	public LocalDate getParameterValueAsLocalDateOr(@NonNull final String parameterName, @Nullable final LocalDate defaultValue)
	{
		final DocumentFilterParam param = getParameterOrNull(parameterName);
		if (param == null)
		{
			return defaultValue;
		}

		return param.getValueAsLocalDateOr(defaultValue);
	}

	@Nullable
	public Instant getParameterValueAsInstantOrNull(@NonNull final String parameterName)
	{
		final DocumentFilterParam param = getParameterOrNull(parameterName);
		if (param == null)
		{
			return null;
		}

		return param.getValueAsInstant();
	}

	@Nullable
	public <T extends RepoIdAware> T getParameterValueAsRepoIdOrNull(@NonNull final String parameterName, @NonNull final IntFunction<T> repoIdMapper)
	{
		final DocumentFilterParam param = getParameterOrNull(parameterName);
		if (param == null)
		{
			return null;
		}

		return param.getValueAsRepoIdOrNull(repoIdMapper);
	}

	@Nullable
	public <T extends ReferenceListAwareEnum> T getParameterValueAsRefListOrNull(@NonNull final String parameterName, @NonNull final Function<String, T> mapper)
	{
		final DocumentFilterParam param = getParameterOrNull(parameterName);
		if (param == null)
		{
			return null;
		}

		return param.getParameterValueAsRefListOrNull(mapper);
	}

	@Nullable
	public <T> T getParameterValueAs(@NonNull final String parameterName)
	{
		final DocumentFilterParam param = getParameterOrNull(parameterName);
		if (param == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked") final T value = (T)param.getValue();
		return value;
	}

	//
	//
	//
	//
	//

	public static final class DocumentFilterBuilder
	{
		public DocumentFilterBuilder setFilterId(final String filterId)
		{
			return filterId(filterId);
		}

		public DocumentFilterBuilder setCaption(@NonNull final ITranslatableString caption)
		{
			return caption(caption);
		}

		public DocumentFilterBuilder setCaption(@NonNull final String caption)
		{
			return caption(TranslatableStrings.constant(caption));
		}

		public DocumentFilterBuilder setFacetFilter(final boolean facetFilter)
		{
			return facetFilter(facetFilter);
		}

		public boolean hasParameters()
		{
			return !Check.isEmpty(parameters)
					|| !Check.isEmpty(internalParameterNames);
		}

		public DocumentFilterBuilder setParameters(@NonNull final List<DocumentFilterParam> parameters)
		{
			return parameters(parameters);
		}

		public DocumentFilterBuilder addParameter(@NonNull final DocumentFilterParam parameter)
		{
			return parameter(parameter);
		}

		public DocumentFilterBuilder addInternalParameter(@NonNull final DocumentFilterParam parameter)
		{
			parameter(parameter);
			internalParameterName(parameter.getFieldName());
			return this;
		}
	}
}
