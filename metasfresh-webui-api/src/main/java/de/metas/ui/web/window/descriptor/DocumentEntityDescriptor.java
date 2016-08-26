package de.metas.ui.web.window.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.DataTypes;

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

public class DocumentEntityDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	@JsonIgnore
	private final String id;

	@JsonProperty("detailId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String detailId;

	@JsonProperty("fields")
	private final List<DocumentFieldDescriptor> fields;
	@JsonIgnore
	private final DocumentFieldDescriptor idField;

	@JsonProperty("included-entities")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Map<String, DocumentEntityDescriptor> includedEntitiesByDetailId;

	@JsonProperty("data-binding")
	private final DocumentEntityDataBindingDescriptor dataBinding;

	@JsonIgnore
	private final DocumentFieldDependencyMap dependencies;

	// Legacy
	@JsonProperty("AD_Window_ID")
	private final int AD_Window_ID;
	@JsonProperty("AD_Tab_ID")
	private final int AD_Tab_ID;
	@JsonProperty("tabNo")
	private final int tabNo;
	@JsonProperty("IsSOTrx")
	private final boolean isSOTrx;

	private DocumentEntityDescriptor(final Builder builder)
	{
		super();
		detailId = builder.detailId;
		fields = ImmutableList.copyOf(builder.fields);
		idField = builder.idField;
		includedEntitiesByDetailId = ImmutableMap.copyOf(builder.includedEntitiesByDetailId);
		dataBinding = Preconditions.checkNotNull(builder.dataBinding, "dataBinding not null");
		dependencies = builder.buildDependencies();

		// legacy:
		AD_Window_ID = Preconditions.checkNotNull(builder.AD_Window_ID, "AD_Window_ID shall be set");
		AD_Tab_ID = Preconditions.checkNotNull(builder.AD_Tab_ID, "AD_Tab_ID shall be set");
		tabNo = builder.tabNo;
		isSOTrx = builder.isSOTrx;
		
		//
		id = String.valueOf(builder.AD_Tab_ID);

	}

	@JsonCreator
	private DocumentEntityDescriptor(
			@JsonProperty("detaildId") final String detailId //
			, @JsonProperty("fields") final List<DocumentFieldDescriptor> fields //
			, @JsonProperty("included-entities") final Map<String, DocumentEntityDescriptor> includedEntities //
			, @JsonProperty("data-binding") final DocumentEntityDataBindingDescriptor dataBinding //
			, @JsonProperty("AD_Window_ID") final int AD_Window_ID //
			, @JsonProperty("AD_Tab_ID") final int AD_Tab_ID //
			, @JsonProperty("tabNo") final int tabNo //
			, @JsonProperty("isSOTrx") final boolean isSOTrx //
	)
	{
		this(new Builder()
				.setDetailId(detailId)
				.addFields(fields)
				.addIncludedEntities(includedEntities == null ? ImmutableList.of() : includedEntities.values())
				.setDataBinding(dataBinding)
				.setAD_Window_ID(AD_Window_ID)
				.setAD_Tab_ID(AD_Tab_ID)
				.setTabNo(tabNo)
				.setIsSOTrx(isSOTrx));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fields", fields)
				.add("entityDataBinding", dataBinding)
				.add("includedEntitites", includedEntitiesByDetailId.isEmpty() ? null : includedEntitiesByDetailId)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (!(obj instanceof DocumentEntityDescriptor))
		{
			return false;
		}

		final DocumentEntityDescriptor other = (DocumentEntityDescriptor)obj;
		return DataTypes.equals(id, other.id);
	}

	public String getId()
	{
		return id;
	}

	public String getDetailId()
	{
		return detailId;
	}

	public DocumentFieldDescriptor getIdField()
	{
		return idField;
	}

	public List<DocumentFieldDescriptor> getFields()
	{
		return fields;
	}

	@JsonIgnore
	public Collection<DocumentEntityDescriptor> getIncludedEntities()
	{
		return includedEntitiesByDetailId.values();
	}

	public DocumentEntityDescriptor getIncludedEntityByDetailId(final String detailId)
	{
		final DocumentEntityDescriptor includedEntityDescriptor = includedEntitiesByDetailId.get(detailId);
		if (includedEntityDescriptor == null)
		{
			throw new IllegalArgumentException("No included entity found for detailId=" + detailId + " in " + this);
		}
		return includedEntityDescriptor;
	}

	public DocumentEntityDataBindingDescriptor getDataBinding()
	{
		return dataBinding;
	}

	public DocumentFieldDependencyMap getDependencies()
	{
		return dependencies;
	}

	// legacy
	@JsonIgnore
	public int getAD_Window_ID()
	{
		return AD_Window_ID;
	}

	// legacy
	@JsonIgnore
	public int getAD_Tab_ID()
	{
		return AD_Tab_ID;
	}

	// legacy
	public int getTabNo()
	{
		return tabNo;
	}

	// legacy
	@JsonIgnore
	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	public static final class Builder
	{
		private final List<DocumentFieldDescriptor> fields = new ArrayList<>();
		private DocumentFieldDescriptor idField;
		private final Map<String, DocumentEntityDescriptor> includedEntitiesByDetailId = new LinkedHashMap<>();
		private DocumentEntityDataBindingDescriptor dataBinding;
		private String detailId;

		// Legacy
		private Integer AD_Window_ID;
		private Integer AD_Tab_ID;
		private Integer tabNo;
		private Boolean isSOTrx;

		private Builder()
		{
			super();
		}

		public DocumentEntityDescriptor build()
		{
			return new DocumentEntityDescriptor(this);
		}

		public Builder setDetailId(final String detailId)
		{
			this.detailId = detailId;
			return this;
		}

		public Builder addField(final DocumentFieldDescriptor field)
		{
			if (field.isKey())
			{
				if (idField != null)
				{
					throw new IllegalArgumentException("More than one ID fields are not allowed: " + idField + ", " + field);
				}
				idField = field;
			}

			fields.add(field);
			return this;
		}

		public Builder addFields(final List<DocumentFieldDescriptor> fields)
		{
			if (fields == null)
			{
				return this;
			}
			fields.stream().forEach(this::addField);
			return this;
		}

		public Builder addIncludedEntity(final DocumentEntityDescriptor includedEntity)
		{
			final String detailId = includedEntity.getDetailId();
			Check.assumeNotEmpty(detailId, "detailId is not empty for {}", includedEntity);
			includedEntitiesByDetailId.put(detailId, includedEntity);
			return this;
		}

		private Builder addIncludedEntities(final Collection<DocumentEntityDescriptor> includedEntities)
		{
			if (includedEntities == null)
			{
				return this;
			}
			includedEntities.stream().forEach(this::addIncludedEntity);
			return this;
		}

		public Builder setDataBinding(final DocumentEntityDataBindingDescriptor dataBinding)
		{
			this.dataBinding = dataBinding;
			return this;
		}

		private DocumentFieldDependencyMap buildDependencies()
		{
			final DocumentFieldDependencyMap.Builder dependenciesBuilder = DocumentFieldDependencyMap.builder();
			fields.stream().forEach(field -> dependenciesBuilder.add(field.getDependencies()));
			return dependenciesBuilder.build();
		}

		public Builder setAD_Window_ID(final int AD_Window_ID)
		{
			this.AD_Window_ID = AD_Window_ID;
			return this;
		}

		public Builder setAD_Tab_ID(final int AD_Tab_ID)
		{
			this.AD_Tab_ID = AD_Tab_ID;
			return this;
		}

		public Builder setTabNo(final int tabNo)
		{
			this.tabNo = tabNo;
			return this;
		}

		public Builder setIsSOTrx(final boolean isSOTrx)
		{
			this.isSOTrx = isSOTrx;
			return this;
		}
	}
}
