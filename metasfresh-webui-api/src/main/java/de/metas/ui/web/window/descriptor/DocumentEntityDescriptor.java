package de.metas.ui.web.window.descriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.impl.CalloutExecutor;
import org.adempiere.ad.callout.spi.CompositeCalloutProvider;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.ad.callout.spi.ImmutablePlainCalloutProvider;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProviderFactory;

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

	private final DocumentType documentType;
	private final int documentTypeId;
	private final String _id;
	
	private final ITranslatableString caption;

	private final DetailId detailId;

	private final ILogicExpression allowCreateNewLogic;
	private final ILogicExpression allowDeleteLogic;
	private final ILogicExpression displayLogic;

	private final Map<String, DocumentFieldDescriptor> fields;
	private final DocumentFieldDescriptor idField;

	private final Map<DetailId, DocumentEntityDescriptor> includedEntitiesByDetailId;

	private final DocumentEntityDataBindingDescriptor dataBinding;

	private final DocumentFieldDependencyMap dependencies;

	// Legacy
	private final int AD_Tab_ID;
	private final String tableName;
	private final boolean isSOTrx;

	private final Map<Characteristic, List<DocumentFieldDescriptor>> fieldsByCharacteristic = new HashMap<>();

	private final CalloutExecutor calloutExecutorFactory;

	private final DocumentFilterDescriptorsProvider filtersProvider;
	
	private final int printProcessId;

	private DocumentEntityDescriptor(final Builder builder)
	{
		super();

		documentType = Preconditions.checkNotNull(builder.documentType, "documentType shall be set");
		documentTypeId = Preconditions.checkNotNull(builder.documentTypeId, "documentTypeId shall be set");
		caption = builder.getCaption();

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
		AD_Tab_ID = Preconditions.checkNotNull(builder.AD_Tab_ID, "AD_Tab_ID shall be set");
		tableName = builder.getTableName();
		isSOTrx = builder.isSOTrx;

		//
		_id = String.valueOf(builder.AD_Tab_ID);

		calloutExecutorFactory = builder.buildCalloutExecutorFactory(fields.values());

		filtersProvider = builder.createFiltersProvider();
		
		printProcessId = builder.getPrintAD_Process_ID();
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
		return Objects.hash(_id);
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
		return DataTypes.equals(_id, other._id);
	}

	public DocumentType getDocumentType()
	{
		return documentType;
	}

	public int getDocumentTypeId()
	{
		return documentTypeId;
	}

	public int getDocumentTypeId(final DocumentType expectedDocumentType)
	{
		Check.assume(documentType == expectedDocumentType, "expected document type to be {} but it was {}", expectedDocumentType, documentType);
		return documentTypeId;
	}

	public int getAD_Window_ID()
	{
		return getDocumentTypeId(DocumentType.Window);
	}
	
	public ITranslatableString getCaption()
	{
		return caption;
	}

	public DetailId getDetailId()
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

	public String getIdFieldName()
	{
		return idField == null ? null : idField.getFieldName();
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

	public Collection<DocumentEntityDescriptor> getIncludedEntities()
	{
		return includedEntitiesByDetailId.values();
	}

	/**
	 *
	 * @param detailId
	 * @return included {@link DocumentEntityDescriptor}; never returns null
	 */
	public DocumentEntityDescriptor getIncludedEntityByDetailId(final String detailId) throws NoSuchElementException
	{
		final DocumentEntityDescriptor includedEntityDescriptor = includedEntitiesByDetailId.get(detailId);
		if (includedEntityDescriptor == null)
		{
			throw new NoSuchElementException("No included entity found for detailId=" + detailId + " in " + this);
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
	public int getAD_Tab_ID()
	{
		return AD_Tab_ID;
	}

	// legacy
	public String getTableName()
	{
		return tableName;
	}

	// legacy
	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	public ICalloutExecutor createCalloutExecutor()
	{
		return calloutExecutorFactory.newInstanceSharingMasterData();
	}

	public DocumentFilterDescriptorsProvider getFiltersProvider()
	{
		return filtersProvider;
	}
	
	public int getPrintProcessId()
	{
		return printProcessId;
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentEntityDescriptor.Builder.class);

		private boolean _built = false;

		private DocumentType documentType;
		private Integer documentTypeId;

		private ITranslatableString caption = ImmutableTranslatableString.empty();
		private ITranslatableString description = ImmutableTranslatableString.empty();

		private final Map<String, DocumentFieldDescriptor.Builder> _fieldBuilders = new LinkedHashMap<>();
		private Map<String, DocumentFieldDescriptor> _fields = null; // will be built
		private Optional<DocumentFieldDescriptor> _idField = null; // will be built
		private final Map<DetailId, DocumentEntityDescriptor> includedEntitiesByDetailId = new LinkedHashMap<>();
		private DocumentEntityDataBindingDescriptorBuilder _dataBinding;

		private DetailId detailId;
		private boolean detailIdSet;

		private ILogicExpression allowCreateNewLogic = ILogicExpression.TRUE;
		private ILogicExpression allowDeleteLogic = ILogicExpression.TRUE;
		private ILogicExpression displayLogic = ILogicExpression.TRUE;
		private ILogicExpression readonlyLogic = ILogicExpression.FALSE;

		// Legacy
		private Integer AD_Tab_ID;
		private String _tableName;
		private Boolean isSOTrx;

		private int printProcessId = -1;

		private Builder()
		{
			super();
		}

		public DocumentEntityDescriptor build()
		{
			assertNotBuilt();
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

		public Builder setDetailId(final DetailId detailId)
		{
			this.detailId = detailId;
			detailIdSet = true;

			updateFieldBuilders(fieldBuilder -> fieldBuilder.setDetailId(detailId));

			return this;
		}

		public DetailId getDetailId()
		{
			Check.assume(detailIdSet, "detailId set");
			return detailId;
		}

		private final void assertFieldsNotBuilt()
		{
			assertNotBuilt();
			Check.assumeNull(_fields, "Fields not already built");
		}

		public Builder addField(final DocumentFieldDescriptor.Builder fieldBuilder)
		{
			assertFieldsNotBuilt();

			//
			// Update field from entity
			fieldBuilder
					.setDetailId(getDetailId())
					.setEntityReadonlyLogic(getReadonlyLogic());

			// Add field
			_fieldBuilders.put(fieldBuilder.getFieldName(), fieldBuilder);

			return this;
		}

		public DocumentFieldDescriptor.Builder getFieldBuilder(final String fieldName)
		{
			return _fieldBuilders.get(fieldName);
		}

		private final void updateFieldBuilders(final Consumer<DocumentFieldDescriptor.Builder> fieldUpdater)
		{
			assertFieldsNotBuilt();

			_fieldBuilders.values()
					.stream()
					.forEach(fieldUpdater);
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
				_fields = _fieldBuilders
						.values()
						.stream()
						.map(fieldBuilder -> fieldBuilder.getOrBuild())
						.collect(GuavaCollectors.toImmutableMapByKey(field -> field.getFieldName()));
			}
			return _fields;
		}

		public Builder addIncludedEntity(final DocumentEntityDescriptor includedEntity)
		{
			final DetailId detailId = includedEntity.getDetailId();
			Check.assumeNotNull(detailId, "detailId is not null for {}", includedEntity);
			includedEntitiesByDetailId.put(detailId, includedEntity);
			return this;
		}

		public Builder setDataBinding(final DocumentEntityDataBindingDescriptorBuilder dataBindingBuilder)
		{
			_dataBinding = dataBindingBuilder;
			return this;
		}

		public <T extends DocumentEntityDataBindingDescriptorBuilder> T getDataBindingBuilder(final Class<T> builderType)
		{
			@SuppressWarnings("unchecked")
			final T dataBindingBuilder = (T)_dataBinding;
			return dataBindingBuilder;
		}

		private DocumentEntityDataBindingDescriptor getOrBuildDataBinding()
		{
			Preconditions.checkNotNull(_dataBinding, "dataBinding");
			return _dataBinding.getOrBuild();
		}

		private DocumentFieldDependencyMap buildDependencies()
		{
			final DocumentFieldDependencyMap.Builder dependenciesBuilder = DocumentFieldDependencyMap.builder();
			getFields().values().stream().forEach(field -> dependenciesBuilder.add(field.getDependencies()));
			return dependenciesBuilder.build();
		}

		public Builder setDocumentType(final DocumentType documentType, final int documentTypeId)
		{
			this.documentType = documentType;
			this.documentTypeId = documentTypeId;
			return this;
		}

		public DocumentType getDocumentType()
		{
			return documentType;
		}

		public int getDocumentTypeId()
		{
			return documentTypeId;
		}

		public Builder setAD_Tab_ID(final int AD_Tab_ID)
		{
			this.AD_Tab_ID = AD_Tab_ID;
			return this;
		}

		public Builder setTableName(final String tableName)
		{
			_tableName = tableName;
			return this;
		}

		public String getTableName()
		{
			Check.assumeNotEmpty(_tableName, "tableName shall be set");
			return _tableName;
		}

		public Builder setCaption(final Map<String, String> captionTrls, final String defaultCaption)
		{
			caption = ImmutableTranslatableString.ofMap(captionTrls, defaultCaption);
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			Check.assumeNotNull(caption, "Parameter caption is not null");
			this.caption = caption;
			return this;
		}

		public ITranslatableString getCaption()
		{
			return caption;
		}

		public Builder setDescription(final Map<String, String> descriptionTrls, final String defaultDescription)
		{
			description = ImmutableTranslatableString.ofMap(descriptionTrls, defaultDescription);
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			Check.assumeNotNull(description, "Parameter description is not null");
			this.description = description;
			return this;
		}

		public ITranslatableString getDescription()
		{
			return description;
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

		public ILogicExpression getDisplayLogic()
		{
			return displayLogic;
		}

		public Builder setReadonlyLogic(final ILogicExpression readonlyLogic)
		{
			Check.assumeNotNull(readonlyLogic, "Parameter readonlyLogic is not null");
			this.readonlyLogic = readonlyLogic;
			updateFieldBuilders(fieldBuilder -> fieldBuilder.setEntityReadonlyLogic(readonlyLogic));
			return this;
		}

		public ILogicExpression getReadonlyLogic()
		{
			return readonlyLogic;
		}

		private CalloutExecutor buildCalloutExecutorFactory(final Collection<DocumentFieldDescriptor> fields)
		{
			final String tableName = getTableName();

			final ImmutablePlainCalloutProvider.Builder entityCalloutProviderBuilder = ImmutablePlainCalloutProvider.builder();
			for (final DocumentFieldDescriptor field : fields)
			{
				final List<IDocumentFieldCallout> fieldCallouts = field.getCallouts();
				if (fieldCallouts.isEmpty())
				{
					continue;
				}

				for (final IDocumentFieldCallout fieldCallout : fieldCallouts)
				{
					final Set<String> dependsOnFieldNames = fieldCallout.getDependsOnFieldNames();
					if (dependsOnFieldNames.isEmpty())
					{
						logger.warn("Callout {} has no dependencies. Skipped from adding to entity descriptor.\n Target: {} \n Source: {}", fieldCallout, this, field);
						continue;
					}

					for (final String dependsOnFieldName : dependsOnFieldNames)
					{
						entityCalloutProviderBuilder.addCallout(tableName, dependsOnFieldName, fieldCallout);
					}
				}
			}

			final CalloutExecutor.Builder calloutExecutorBuilder = CalloutExecutor.builder()
					.setTableName(tableName);

			final ICalloutProvider entityCalloutProvider = entityCalloutProviderBuilder.build();
			final ICalloutProvider defaultCalloutProvider = calloutExecutorBuilder.getDefaultCalloutProvider();
			final ICalloutProvider calloutProvider = CompositeCalloutProvider.compose(defaultCalloutProvider, entityCalloutProvider);
			calloutExecutorBuilder.setCalloutProvider(calloutProvider);

			return calloutExecutorBuilder.build();
		}

		private final DocumentFilterDescriptorsProvider createFiltersProvider()
		{
			final int adTabId = AD_Tab_ID;
			final String tableName = getTableName();
			final Collection<DocumentFieldDescriptor> fields = getFields().values();
			return DocumentFilterDescriptorsProviderFactory.instance.createFiltersProvider(adTabId, tableName, fields);
		}
		
		public Builder setPrintAD_Process_ID(final int printProcessId)
		{
			this.printProcessId = printProcessId > 0 ? printProcessId : -1;
			return this;
		}
		
		private int getPrintAD_Process_ID()
		{
			return printProcessId;
		}
	}
}
