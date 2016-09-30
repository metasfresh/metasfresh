package de.metas.ui.web.window.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.window.model.DocumentQueryFilterParam;

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

public final class DocumentQueryFilterDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String filterId;
	private final ITranslatableString displayNameTrls;
	private final List<DocumentQueryFilterParamDescriptor> parameters;
	private final List<DocumentQueryFilterParam> internalParameters;
	private final boolean frequentUsed;

	private DocumentQueryFilterDescriptor(final Builder builder)
	{
		super();

		filterId = builder.filterId;
		Check.assumeNotEmpty(filterId, "filterId is not empty");

		displayNameTrls = builder.displayNameTrls;
		Check.assumeNotNull(displayNameTrls, "Parameter displayNameTrls is not null");

		parameters = ImmutableList.copyOf(builder.parameters);
		internalParameters = ImmutableList.copyOf(builder.internalParameters);
		frequentUsed = builder.frequentUsed;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("filterId", filterId)
				.add("parameters", parameters.isEmpty() ? null : parameters)
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

	public List<DocumentQueryFilterParamDescriptor> getParameters()
	{
		return parameters;
	}

	public List<DocumentQueryFilterParam> getInternalParameters()
	{
		return internalParameters;
	}

	public boolean isFrequentUsed()
	{
		return frequentUsed;
	}

	public static final class Builder
	{
		private String filterId;
		private ITranslatableString displayNameTrls;
		private final List<DocumentQueryFilterParamDescriptor> parameters = new ArrayList<>();
		private final List<DocumentQueryFilterParam> internalParameters = new ArrayList<>();
		private boolean frequentUsed;

		private Builder()
		{
			super();
		}

		public DocumentQueryFilterDescriptor build()
		{
			return new DocumentQueryFilterDescriptor(this);
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

		public Builder setFrequentUsed(final boolean frequentUsed)
		{
			this.frequentUsed = frequentUsed;
			return this;
		}

		public Builder addParameter(final DocumentQueryFilterParamDescriptor parameter)
		{
			parameters.add(parameter);
			return this;
		}

		public Builder addInternalParameter(final DocumentQueryFilterParam parameter)
		{
			internalParameters.add(parameter);
			return this;
		}
	}
}
