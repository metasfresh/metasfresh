package de.metas.ui.web.document.filter;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.BarcodeScannerType;
import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import de.metas.ui.web.document.filter.json.JSONDocumentFilterParam;
import de.metas.ui.web.window.datatypes.DebugProperties;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

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

@EqualsAndHashCode // required for (ETag) caching
public final class DocumentFilterDescriptor
{
	public static Builder builder()
	{
		return new Builder();
	}

	@Getter
	private final String filterId;
	@Getter
	private final int sortNo;
	private final ITranslatableString displayNameTrls;

	/**
	 * To be displayed outside the regular filters dropdown list for quicker access.
	 */
	@Getter
	private final boolean frequentUsed;

	/**
	 * How to render it when the filter is inline (i.e. {@link #frequentUsed} is true)
	 */
	@Getter
	private final DocumentFilterInlineRenderMode inlineRenderMode;

	@Getter
	private final PanelLayoutType parametersLayoutType;
	private final ImmutableMap<String, DocumentFilterParamDescriptor> parametersByName;
	@Getter
	private final ImmutableList<DocumentFilterParam> internalParameters;
	@Getter
	private final boolean autoFilter;

	@Getter
	private final BarcodeScannerType barcodeScannerType;

	@Getter
	private final boolean facetFilter;

	@Getter
	private final DebugProperties debugProperties;

	private DocumentFilterDescriptor(final Builder builder)
	{
		filterId = builder.filterId;
		Check.assumeNotEmpty(filterId, "filterId is not empty");

		sortNo = builder.sortNo;

		displayNameTrls = builder.getDisplayNameTrls();
		Check.assumeNotNull(displayNameTrls, "Parameter displayNameTrls is not null");

		frequentUsed = builder.frequentUsed;
		inlineRenderMode = builder.getInlineRenderMode();

		parametersLayoutType = builder.getParametersLayoutType();
		parametersByName = builder.buildParameters();
		internalParameters = ImmutableList.copyOf(builder.internalParameters);
		autoFilter = parametersByName.values().stream().anyMatch(DocumentFilterParamDescriptor::isAutoFilter);
		facetFilter = builder.facetFilter;

		debugProperties = DebugProperties.ofNullableMap(builder.debugProperties);

		final ImmutableSet<BarcodeScannerType> barcodeScannerTypes = parametersByName.values().stream()
				.map(DocumentFilterParamDescriptor::getBarcodeScannerType)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		barcodeScannerType = barcodeScannerTypes.size() == 1
				? barcodeScannerTypes.iterator().next()
				: null;
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

	public boolean hasParameter(final String parameterName)
	{
		return parametersByName.get(parameterName) != null;
	}

	public DocumentFilter unwrap(@NonNull final JSONDocumentFilter jsonFilter)
	{
		final DocumentFilter.DocumentFilterBuilder filter = DocumentFilter.builder()
				.setFilterId(jsonFilter.getFilterId())
				.setFacetFilter(isFacetFilter());

		final Map<String, JSONDocumentFilterParam> jsonParams = Maps.uniqueIndex(jsonFilter.getParameters(), JSONDocumentFilterParam::getParameterName);

		for (final DocumentFilterParamDescriptor paramDescriptor : getParameters())
		{
			final String parameterName = paramDescriptor.getParameterName();
			final JSONDocumentFilterParam jsonParam = jsonParams.get(parameterName);

			// If parameter is missing: skip it if no required, else throw exception
			if (jsonParam == null)
			{
				if (paramDescriptor.isMandatory())
				{
					throw new IllegalArgumentException("Parameter '" + parameterName + "' was not provided");
				}
				continue;
			}

			final Object value = paramDescriptor.convertValueFromJson(jsonParam.getValue());
			final Object valueTo = paramDescriptor.convertValueFromJson(jsonParam.getValueTo());

			// If there was no value/valueTo provided: skip it if no required, else throw exception
			if (value == null && valueTo == null)
			{
				if (paramDescriptor.isMandatory())
				{
					throw new IllegalArgumentException("Parameter '" + parameterName + "' has no value");
				}
				continue;
			}

			filter.addParameter(DocumentFilterParam.builder()
					.setFieldName(paramDescriptor.getFieldName())
					.setOperator(paramDescriptor.getOperatorOrEqualsIfNull())
					.setValue(value)
					.setValueTo(valueTo)
					.build());
		}

		for (final DocumentFilterParam internalParam : getInternalParameters())
		{
			filter.addInternalParameter(internalParam);
		}

		return filter.build();
	}

	//
	//
	//
	//
	//

	public static final class Builder
	{
		private String filterId;
		private int sortNo;
		private ITranslatableString displayNameTrls;
		private boolean frequentUsed;

		private DocumentFilterInlineRenderMode inlineRenderMode;

		private PanelLayoutType parametersLayoutType;
		private final List<DocumentFilterParamDescriptor.Builder> parameters = new ArrayList<>();
		private final List<DocumentFilterParam> internalParameters = new ArrayList<>();

		private boolean facetFilter;

		private Map<String, Object> debugProperties = null;

		private Builder()
		{
		}

		public DocumentFilterDescriptor build()
		{
			return new DocumentFilterDescriptor(this);
		}

		private ImmutableMap<String, DocumentFilterParamDescriptor> buildParameters()
		{
			//
			// Update and collect parameter names:
			final HashSet<String> availableParameterNames = new HashSet<>();
			final Map<String, Integer> nextParamIndexByFieldName = new HashMap<>();
			for (final DocumentFilterParamDescriptor.Builder paramBuilder : parameters)
			{
				final String fieldName = paramBuilder.getFieldName();
				final Integer nextParamIndex = nextParamIndexByFieldName.get(fieldName);
				if (nextParamIndex == null)
				{
					paramBuilder.parameterName(fieldName);
					nextParamIndexByFieldName.put(fieldName, 2);
				}
				else
				{
					paramBuilder.parameterName(fieldName + nextParamIndex);
					nextParamIndexByFieldName.put(fieldName, nextParamIndex + 1);
				}

				availableParameterNames.add(paramBuilder.getParameterName());
			}

			final ImmutableMap.Builder<String, DocumentFilterParamDescriptor> parametersByName = ImmutableMap.builder();
			for (final DocumentFilterParamDescriptor.Builder paramBuilder : parameters)
			{
				paramBuilder.lookupDescriptor(lookupDescriptor -> {
					if (lookupDescriptor instanceof SqlLookupDescriptor)
					{
						return SqlLookupDescriptor.cast(lookupDescriptor)
								.withScope(LookupDescriptorProvider.LookupScope.DocumentFilter)
								.withOnlyForAvailableParameterNames(availableParameterNames);
					}
					else
					{
						return lookupDescriptor;
					}
				});

				final DocumentFilterParamDescriptor param = paramBuilder.build();
				parametersByName.put(param.getParameterName(), param);
			}

			return parametersByName.build();
		}

		public Builder setFilterId(final String filterId)
		{
			this.filterId = filterId;
			return this;
		}

		public Builder setSortNo(final int sortNo)
		{
			this.sortNo = sortNo;
			return this;
		}

		public Builder setDisplayName(final Map<String, String> displayNameTrls)
		{
			this.displayNameTrls = TranslatableStrings.ofMap(displayNameTrls);
			return this;
		}

		public Builder setDisplayName(final ITranslatableString displayNameTrls)
		{
			this.displayNameTrls = displayNameTrls;
			return this;
		}

		public Builder setDisplayName(final String displayName)
		{
			displayNameTrls = TranslatableStrings.constant(displayName);
			return this;
		}

		public Builder setDisplayName(final AdMessageKey displayName)
		{
			return setDisplayName(TranslatableStrings.adMessage(displayName));
		}

		@Nullable
		public ITranslatableString getDisplayNameTrls()
		{
			if (displayNameTrls != null)
			{
				return displayNameTrls;
			}

			if (parameters.size() == 1)
			{
				return parameters.get(0).getDisplayName();
			}

			return null;
		}

		public Builder setFrequentUsed(final boolean frequentUsed)
		{
			this.frequentUsed = frequentUsed;
			return this;
		}

		public Builder setInlineRenderMode(final DocumentFilterInlineRenderMode inlineRenderMode)
		{
			this.inlineRenderMode = inlineRenderMode;
			return this;
		}

		private DocumentFilterInlineRenderMode getInlineRenderMode()
		{
			return inlineRenderMode != null ? inlineRenderMode : DocumentFilterInlineRenderMode.BUTTON;
		}

		public Builder setParametersLayoutType(final PanelLayoutType parametersLayoutType)
		{
			this.parametersLayoutType = parametersLayoutType;
			return this;
		}

		private PanelLayoutType getParametersLayoutType()
		{
			return parametersLayoutType != null ? parametersLayoutType : PanelLayoutType.Panel;
		}

		public Builder setFacetFilter(final boolean facetFilter)
		{
			this.facetFilter = facetFilter;
			return this;
		}

		public boolean hasParameters()
		{
			return !parameters.isEmpty();
		}

		public Builder addParameter(final DocumentFilterParamDescriptor.Builder parameter)
		{
			parameters.add(parameter);
			return this;
		}

		public Builder addInternalParameter(final String parameterName, final Object constantValue)
		{
			return addInternalParameter(DocumentFilterParam.ofNameEqualsValue(parameterName, constantValue));
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
