package de.metas.ui.web.window.descriptor.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.window.model.filters.DocumentFilterParam;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class DocumentFilterDescriptor
{
	static final Builder builder()
	{
		return new Builder();
	}

	private final String filterId;
	private final ITranslatableString displayNameTrls;
	private final Map<String, DocumentFilterParamDescriptor> parametersByName;
	private final List<DocumentFilterParam> internalParameters;
	private final boolean frequentUsed;

	private final Map<String, Object> debugProperties;

	private DocumentFilterDescriptor(final Builder builder)
	{
		super();

		filterId = builder.filterId;
		Check.assumeNotEmpty(filterId, "filterId is not empty");

		displayNameTrls = builder.displayNameTrls;
		Check.assumeNotNull(displayNameTrls, "Parameter displayNameTrls is not null");

		parametersByName = builder.buildParameters();
		internalParameters = ImmutableList.copyOf(builder.internalParameters);
		frequentUsed = builder.frequentUsed;

		debugProperties = builder.debugProperties == null ? ImmutableMap.of() : ImmutableMap.copyOf(builder.debugProperties);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("filterId", filterId)
				.add("parameters", parametersByName.isEmpty() ? null : parametersByName.values())
				.add("internalParameters", internalParameters.isEmpty() ? null : internalParameters)
				.toString();
	}

	public String getFilterId()
	{
		return filterId;
	}

	public String getDisplayName(final String adLanguage)
	{
		return displayNameTrls.translate(adLanguage);
	}

	public Collection<DocumentFilterParamDescriptor> getParameters()
	{
		return parametersByName.values();
	}

	public DocumentFilterParamDescriptor getParameterByName(final String parameterName)
	{
		final DocumentFilterParamDescriptor parameter = parametersByName.get(parameterName);
		if (parameter == null)
		{
			throw new NoSuchElementException("Parameter '" + parameterName + "' not found in " + this);
		}
		return parameter;
	}

	public List<DocumentFilterParam> getInternalParameters()
	{
		return internalParameters;
	}

	public boolean isFrequentUsed()
	{
		return frequentUsed;
	}

	public Map<String, Object> getDebugProperties()
	{
		return debugProperties;
	}

	public static final class Builder
	{
		private String filterId;
		private ITranslatableString displayNameTrls;
		private final List<DocumentFilterParamDescriptor.Builder> parameters = new ArrayList<>();
		private final List<DocumentFilterParam> internalParameters = new ArrayList<>();
		private boolean frequentUsed;

		private Map<String, Object> debugProperties = null;

		private Builder()
		{
			super();
		}

		public DocumentFilterDescriptor build()
		{
			return new DocumentFilterDescriptor(this);
		}

		private Map<String, DocumentFilterParamDescriptor> buildParameters()
		{
			final Map<String, Integer> nextParamIndexByFieldName = new HashMap<>();
			return parameters
					.stream()
					.peek((paramBuilder) -> {
						final String fieldName = paramBuilder.getFieldName();
						final Integer nextParamIndex = nextParamIndexByFieldName.get(fieldName);
						if (nextParamIndex == null)
						{
							paramBuilder.setParameterName(fieldName);
							nextParamIndexByFieldName.put(fieldName, 2);
						}
						else
						{
							paramBuilder.setParameterName(fieldName + nextParamIndex);
							nextParamIndexByFieldName.put(fieldName, nextParamIndex + 1);
						}
					})
					.map(paramBuilder -> paramBuilder.build())
					.collect(GuavaCollectors.toImmutableMapByKey(param -> param.getParameterName()));
		}

		public Builder setFilterId(final String filterId)
		{
			this.filterId = filterId;
			return this;
		}

		public Builder setDisplayName(final Map<String, String> displayNameTrls)
		{
			this.displayNameTrls = ImmutableTranslatableString.ofMap(displayNameTrls);
			return this;
		}

		public Builder setDisplayName(final ITranslatableString displayNameTrls)
		{
			this.displayNameTrls = displayNameTrls;
			return this;
		}

		public Builder setDisplayName(final String displayName)
		{
			displayNameTrls = ImmutableTranslatableString.constant(displayName);
			return this;
		}

		public Builder setFrequentUsed(final boolean frequentUsed)
		{
			this.frequentUsed = frequentUsed;
			return this;
		}

		public Builder addParameter(final DocumentFilterParamDescriptor.Builder parameter)
		{
			parameters.add(parameter);
			return this;
		}

		private Builder addParameters(final Collection<DocumentFilterParamDescriptor.Builder> parameters)
		{
			this.parameters.addAll(parameters);
			return this;
		}

		public Collector<DocumentFilterParamDescriptor.Builder, ?, Builder> collectParameters()
		{
			final Supplier<List<DocumentFilterParamDescriptor.Builder>> supplier = ArrayList::new;
			final BiConsumer<List<DocumentFilterParamDescriptor.Builder>, DocumentFilterParamDescriptor.Builder> accumulator = (list, filter) -> list.add(filter);
			final BinaryOperator<List<DocumentFilterParamDescriptor.Builder>> combiner = (list1, list2) -> {
				list1.addAll(list2);
				return list1;
			};
			final Function<List<DocumentFilterParamDescriptor.Builder>, Builder> finisher = (params) -> addParameters(params);

			return Collector.of(supplier, accumulator, combiner, finisher);
		}

		public Builder addInternalParameter(final DocumentFilterParam parameter)
		{
			internalParameters.add(parameter);
			return this;
		}

		public Builder putDebugProperty(final String name, final Object value)
		{
			Check.assumeNotEmpty(name, "name is not empty");
			if (debugProperties == null)
			{
				debugProperties = new LinkedHashMap<>();
			}
			debugProperties.put("debug-" + name, value);
			return this;
		}
	}
}
