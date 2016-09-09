package de.metas.ui.web.window.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.impl.CalloutExecutor;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;

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

	@JsonProperty("allow-new")
	private final ILogicExpression allowCreateNewLogic;
	@JsonProperty("allow-delete")
	private final ILogicExpression allowDeleteLogic;
	@JsonProperty("displayLogic")
	private final ILogicExpression displayLogic;

	@JsonProperty("fields")
	private final Map<String, DocumentFieldDescriptor> fields;
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
	@JsonProperty("AD_Table_ID")
	private final int AD_Table_ID;
	@JsonProperty("tabNo")
	private final int tabNo;
	@JsonProperty("IsSOTrx")
	private final boolean isSOTrx;

	@JsonIgnore
	private final Map<Characteristic, List<DocumentFieldDescriptor>> fieldsByCharacteristic = new HashMap<>();

	@JsonIgnore
	private final CalloutExecutor calloutExecutorFactory;

	private DocumentEntityDescriptor(final Builder builder)
	{
		super();

		if (!builder.detailIdSet)
		{
			throw new IllegalArgumentException("detailId was not set to " + builder);
		}
		detailId = builder.detailId;

		allowCreateNewLogic = Preconditions.checkNotNull(builder.allowCreateNewLogic);
		allowDeleteLogic = Preconditions.checkNotNull(builder.allowDeleteLogic);
		displayLogic = Preconditions.checkNotNull(builder.displayLogic, "displayLogic not null");

		fields = ImmutableMap.copyOf(builder.getFields());
		idField = builder.getIdField();
		includedEntitiesByDetailId = ImmutableMap.copyOf(builder.includedEntitiesByDetailId);
		dataBinding = builder.getOrBuildDataBinding();
		dependencies = builder.buildDependencies();

		// legacy:
		AD_Window_ID = Preconditions.checkNotNull(builder.AD_Window_ID, "AD_Window_ID shall be set");
		AD_Tab_ID = Preconditions.checkNotNull(builder.AD_Tab_ID, "AD_Tab_ID shall be set");
		AD_Table_ID = Preconditions.checkNotNull(builder.AD_Table_ID, "AD_Table_ID shall be set");
		tabNo = builder.tabNo;
		isSOTrx = builder.isSOTrx;

		//
		id = String.valueOf(builder.AD_Tab_ID);
		
		calloutExecutorFactory = CalloutExecutor.builder()
				.setAD_Table_ID(AD_Table_ID)
				.build();
	}

	@JsonCreator
	private DocumentEntityDescriptor(
			@JsonProperty("detaildId") final String detailId //
			, @JsonProperty("allow-new") final ILogicExpression allowCreateNewLogic //
			, @JsonProperty("allow-delete") final ILogicExpression allowDeleteLogic //
			, @JsonProperty("readonlyLogic") final ILogicExpression readonlyLogic //
			, @JsonProperty("displayLogic") final ILogicExpression displayLogic //
			, @JsonProperty("fields") final List<DocumentFieldDescriptor> fields //
			, @JsonProperty("included-entities") final Map<String, DocumentEntityDescriptor> includedEntities //
			, @JsonProperty("data-binding") final DocumentEntityDataBindingDescriptor dataBinding //
			// legacy:
			, @JsonProperty("AD_Window_ID") final int AD_Window_ID //
			, @JsonProperty("AD_Tab_ID") final int AD_Tab_ID //
			, @JsonProperty("AD_Table_ID") final int AD_Table_ID //
			, @JsonProperty("tabNo") final int tabNo //
			, @JsonProperty("isSOTrx") final boolean isSOTrx //
	)
	{
		this(new Builder()
				.setDetailId(detailId)
				//
				.setAllowCreateNewLogic(allowCreateNewLogic)
				.setAllowDeleteLogic(allowDeleteLogic)
				.setDisplayLogic(displayLogic)
				//
				.addFields(fields)
				.addIncludedEntities(includedEntities == null ? ImmutableList.of() : includedEntities.values())
				.setDataBinding(dataBinding)
				// legacy:
				.setAD_Window_ID(AD_Window_ID)
				.setAD_Tab_ID(AD_Tab_ID)
				.setTabNo(tabNo)
				.setAD_Table_ID(AD_Table_ID)
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

	public ILogicExpression getAllowCreateNewLogic()
	{
		return allowCreateNewLogic;
	}

	public ILogicExpression getAllowDeleteLogic()
	{
		return allowDeleteLogic;
	}

	public ILogicExpression getDisplayLogic()
	{
		return displayLogic;
	}

	public DocumentFieldDescriptor getIdField()
	{
		return idField;
	}

	public Collection<DocumentFieldDescriptor> getFields()
	{
		return fields.values();
	}

	public DocumentFieldDescriptor getFieldOrNull(final String fieldName)
	{
		return fields.get(fieldName);
	}

	public DocumentFieldDescriptor getField(final String fieldName)
	{
		final DocumentFieldDescriptor field = fields.get(fieldName);
		if (field == null)
		{
			throw new IllegalArgumentException("Field " + fieldName + " not found in " + this);
		}

		return field;
	}

	public List<DocumentFieldDescriptor> getFieldsWithCharacteristic(final Characteristic characteristic)
	{
		return fieldsByCharacteristic.computeIfAbsent(characteristic, (c) -> buildFieldsWithCharacteristic(c));
	}

	private List<DocumentFieldDescriptor> buildFieldsWithCharacteristic(final Characteristic characteristic)
	{
		return getFields()
				.stream()
				.filter(field -> field.hasCharacteristic(characteristic))
				.collect(GuavaCollectors.toImmutableList());
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
	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	// legacy
	@JsonIgnore
	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	public ICalloutExecutor createCalloutExecutor()
	{
		return calloutExecutorFactory.newInstanceSharingMasterData();
	}

	public static final class Builder
	{
		private boolean _built = false;

		private final List<Object> _fieldsOrBuilders = new ArrayList<>();
		private Map<String, DocumentFieldDescriptor> _fields = null; // will be built
		private Optional<DocumentFieldDescriptor> _idField = null; // will be built
		private final Map<String, DocumentEntityDescriptor> includedEntitiesByDetailId = new LinkedHashMap<>();
		private Object _dataBindingOrBuilder;

		private String detailId;
		private boolean detailIdSet;

		private ILogicExpression allowCreateNewLogic = ILogicExpression.TRUE;
		private ILogicExpression allowDeleteLogic = ILogicExpression.TRUE;
		private ILogicExpression displayLogic = ILogicExpression.TRUE;

		// Legacy
		private Integer AD_Window_ID;
		private Integer AD_Tab_ID;
		private Integer tabNo;
		private Integer AD_Table_ID;
		private Boolean isSOTrx;


		private Builder()
		{
			super();
		}

		public DocumentEntityDescriptor build()
		{
			if (_built)
			{
				throw new IllegalStateException("Already built: " + this);
			}
			_built = true;

			return new DocumentEntityDescriptor(this);
		}

		private final void assertNotBuilt()
		{
			if (_built)
			{
				throw new IllegalStateException("Already built: " + this);
			}
		}

		public Builder setDetailId(final String detailId)
		{
			this.detailId = detailId;
			detailIdSet = true;
			return this;
		}

		public Builder addField(final DocumentFieldDescriptor.Builder fieldBuilder)
		{
			assertNotBuilt();

			Preconditions.checkNotNull(fieldBuilder, "fieldBuilder not null");
			_fieldsOrBuilders.add(fieldBuilder);
			return this;
		}

		public Builder addField(final DocumentFieldDescriptor field)
		{
			assertNotBuilt();

			Preconditions.checkNotNull(field, "field not null");

			_fieldsOrBuilders.add(field);
			return this;
		}

		public Builder addFields(final List<DocumentFieldDescriptor> fields)
		{
			if (fields == null || fields.isEmpty())
			{
				return this;
			}
			fields.stream().forEach(this::addField);
			return this;
		}

		public DocumentFieldDescriptor getIdField()
		{
			if (_idField == null)
			{
				DocumentFieldDescriptor idField = null;
				for (final DocumentFieldDescriptor field : getFields().values())
				{
					if (field.isKey())
					{
						if (idField != null)
						{
							throw new IllegalArgumentException("More than one ID fields are not allowed: " + idField + ", " + field);
						}
						idField = field;
					}
				}

				_idField = Optional.fromNullable(idField);
			}

			return _idField.orNull();
		}

		private Map<String, DocumentFieldDescriptor> getFields()
		{
			if (_fields == null)
			{
				_fields = _fieldsOrBuilders.stream()
						.map(Builder::getOrBuildField)
						.collect(GuavaCollectors.toImmutableMapByKey(field -> field.getFieldName()));
			}
			return _fields;
		}

		private static DocumentFieldDescriptor getOrBuildField(final Object fieldOrBuilder)
		{
			if (fieldOrBuilder instanceof DocumentFieldDescriptor.Builder)
			{
				return ((DocumentFieldDescriptor.Builder)fieldOrBuilder).getOrBuild();
			}
			else if (fieldOrBuilder instanceof DocumentFieldDescriptor)
			{
				return (DocumentFieldDescriptor)fieldOrBuilder;
			}
			else
			{
				throw new IllegalArgumentException("Unknown field or builder: " + fieldOrBuilder);
			}
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
			_dataBindingOrBuilder = dataBinding;
			return this;
		}

		public Builder setDataBinding(final DocumentEntityDataBindingDescriptorBuilder dataBindingBuilder)
		{
			_dataBindingOrBuilder = dataBindingBuilder;
			return this;
		}

		private DocumentEntityDataBindingDescriptor getOrBuildDataBinding()
		{
			Preconditions.checkNotNull(_dataBindingOrBuilder, "dataBindingOrBuilder");
			if (_dataBindingOrBuilder instanceof DocumentEntityDataBindingDescriptor)
			{
				return (DocumentEntityDataBindingDescriptor)_dataBindingOrBuilder;
			}
			else if (_dataBindingOrBuilder instanceof DocumentEntityDataBindingDescriptorBuilder)
			{
				return ((DocumentEntityDataBindingDescriptorBuilder)_dataBindingOrBuilder).getOrBuild();
			}
			else
			{
				throw new IllegalStateException("Unknown dataBinding type: " + _dataBindingOrBuilder);
			}
		}

		private DocumentFieldDependencyMap buildDependencies()
		{
			final DocumentFieldDependencyMap.Builder dependenciesBuilder = DocumentFieldDependencyMap.builder();
			getFields().values().stream().forEach(field -> dependenciesBuilder.add(field.getDependencies()));
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

		public Builder setAD_Table_ID(final int AD_Table_ID)
		{
			this.AD_Table_ID = AD_Table_ID;
			return this;
		}

		public Builder setIsSOTrx(final boolean isSOTrx)
		{
			this.isSOTrx = isSOTrx;
			return this;
		}

		public Builder setAllowCreateNewLogic(final ILogicExpression allowCreateNewLogic)
		{
			this.allowCreateNewLogic = allowCreateNewLogic;
			return this;
		}

		public Builder setAllowDeleteLogic(final ILogicExpression allowDeleteLogic)
		{
			this.allowDeleteLogic = allowDeleteLogic;
			return this;
		}

		public Builder setDisplayLogic(final ILogicExpression displayLogic)
		{
			this.displayLogic = displayLogic;
			return this;
		}
	}
}
