package de.metas.ui.web.window.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

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

	private final String id;
	private final ITranslatableString displayNameTrls;
	private final List<DocumentQueryFilterParamDescriptor> parameters;
	private final boolean frequentUsed;

	private DocumentQueryFilterDescriptor(final Builder builder)
	{
		super();

		id = builder.id;
		Check.assumeNotEmpty(id, "id is not empty");

		displayNameTrls = ImmutableTranslatableString.ofMap(builder.displayNameTrls);
		parameters = ImmutableList.copyOf(builder.parameters);
		frequentUsed = builder.frequentUsed;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("parameters", parameters)
				.toString();
	}

	public String getId()
	{
		return id;
	}

	public String getDisplayName(final String adLanguage)
	{
		return displayNameTrls.translate(adLanguage);
	}

	public List<DocumentQueryFilterParamDescriptor> getParameters()
	{
		return parameters;
	}

	public boolean isFrequentUsed()
	{
		return frequentUsed;
	}

	public static final class Builder
	{
		private String id;
		private Map<String, String> displayNameTrls;
		private final List<DocumentQueryFilterParamDescriptor> parameters = new ArrayList<>();
		private boolean frequentUsed;

		private Builder()
		{
			super();
		}

		public DocumentQueryFilterDescriptor build()
		{
			return new DocumentQueryFilterDescriptor(this);
		}

		public Builder setId(final String id)
		{
			this.id = id;
			return this;
		}

		public Builder setDisplayName(final Map<String, String> displayNameTrls)
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
	}
}
