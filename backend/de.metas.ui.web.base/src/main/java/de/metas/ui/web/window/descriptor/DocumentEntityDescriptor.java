package de.metas.ui.web.window.descriptor;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.AdProcessId;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvidersService;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap.DependencyType;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.HighVolumeReadWriteIncludedDocumentsCollection;
import de.metas.ui.web.window.model.HighVolumeReadonlyIncludedDocumentsCollection;
import de.metas.ui.web.window.model.IIncludedDocumentsCollection;
import de.metas.ui.web.window.model.IIncludedDocumentsCollectionFactory;
import de.metas.ui.web.window.model.SingleRowDetailIncludedDocumentsCollection;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.callout.api.impl.CalloutExecutor;
import org.adempiere.ad.callout.api.impl.NullCalloutExecutor;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.ad.callout.spi.ImmutablePlainCalloutProvider;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DocumentEntityDescriptor
{
	public static Builder builder()
	{
		return new Builder();
	}

	@Getter
	private final DocumentType documentType;
	@Getter
	private final DocumentId documentTypeId;
	private final String id;
	@Getter
	private final String internalName;

	@Getter
	private final ITranslatableString caption;
	@Getter
	private final ITranslatableString description;

	@Nullable
	@Getter
	private final DetailId detailId;

	@Getter
	private final ILogicExpression allowCreateNewLogic;
	@Getter
	private final ILogicExpression allowDeleteLogic;
	@Getter
	private final ILogicExpression readonlyLogic;
	@Getter
	private final ILogicExpression displayLogic;
	@Getter
	private final boolean allowQuickInput;
	@Getter
	private final boolean autodetectDefaultDateFilter;

	private final ImmutableMap<String, DocumentFieldDescriptor> fields;
	@Getter
	private final ImmutableList<DocumentFieldDescriptor> idFields;
	private final DocumentFieldDescriptor parentLinkField;

	private final ImmutableMap<DetailId, DocumentEntityDescriptor> includedEntitiesByDetailId;
	private final transient IIncludedDocumentsCollectionFactory includedDocumentsCollectionFactory;

	@Getter
	private final DocumentEntityDataBindingDescriptor dataBinding;

	@Getter
	private final DocumentFieldDependencyMap dependencies;

	private final ConcurrentHashMap<Characteristic, Set<String>> _fieldNamesByCharacteristic = new ConcurrentHashMap<>();

	//
	// Callouts
	private final boolean calloutsEnabled;
	private final boolean defaultTableCalloutsEnabled;
	private final transient ICalloutExecutor calloutExecutorFactory;

	private final AdProcessId printProcessId;

	//
	// View related
	@Getter
	private final DocumentFilterDescriptorsProvider filterDescriptors;
	@Getter
	private final boolean refreshViewOnChangeEvents;

	// Legacy
	private final Optional<AdTabId> adTabId;
	private final Optional<String> tableName;
	private final Optional<SOTrx> soTrx;

	@Getter
	private final boolean cloneEnabled;

	private DocumentEntityDescriptor(@NonNull final Builder builder)
	{
		documentType = builder.getDocumentType();
		documentTypeId = builder.getDocumentTypeId();
		caption = builder.getCaption();
		description = builder.getDescription();
		detailId = builder.getDetailId();

		allowCreateNewLogic = builder.getAllowCreateNewLogic();
		allowDeleteLogic = builder.getAllowDeleteLogic();
		readonlyLogic = builder.getReadonlyLogic();
		displayLogic = builder.getDisplayLogic();
		allowQuickInput = builder.getQuickInputSupport() != null;
		autodetectDefaultDateFilter = builder.isAutodetectDefaultDateFilter();

		fields = ImmutableMap.copyOf(builder.getFields());
		idFields = builder.getIdFields();
		parentLinkField = builder.getParentLinkFieldOrNull();

		includedEntitiesByDetailId = builder.buildIncludedEntitiesByDetailId();
		includedDocumentsCollectionFactory = builder.getIncludedDocumentsCollectionFactory();
		dataBinding = builder.getOrBuildDataBinding();
		dependencies = builder.buildDependencies();

		//
		id = builder.getId();
		internalName = builder.getInternalName();

		//
		// Callouts
		calloutsEnabled = builder.isCalloutsEnabled();
		defaultTableCalloutsEnabled = builder.isDefaultTableCalloutsEnabled();
		calloutExecutorFactory = builder.buildCalloutExecutorFactory(fields.values());

		printProcessId = builder.getPrintProcessId();

		//
		// View
		filterDescriptors = builder.createFilterDescriptors();
		refreshViewOnChangeEvents = builder.isRefreshViewOnChangeEvents();
		// legacy:
		adTabId = builder.getAdTabId();
		tableName = builder.getTableName();
		soTrx = builder.getSOTrx();

		cloneEnabled = builder.isCloneEnabled();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", tableName.orElse(null))
				.add("fields.count", fields.size()) // only fields count because else it's too long
				// .add("entityDataBinding", dataBinding) // skip it because it's too long
				.add("includedEntitites.count", includedEntitiesByDetailId.isEmpty() ? null : includedEntitiesByDetailId.size())
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

	/**
	 * @return AD_Window_ID
	 */
	public WindowId getWindowId()
	{
		Check.assume(documentType == DocumentType.Window, "expected document type to be {} but it was {}", DocumentType.Window, documentType);
		return WindowId.of(documentTypeId);
	}

	public DocumentFieldDescriptor getSingleIdFieldOrNull()
	{
		return idFields.size() == 1 ? idFields.get(0) : null;
	}

	public DocumentFieldDescriptor getSingleIdField()
	{
		final DocumentFieldDescriptor idField = getSingleIdFieldOrNull();
		if (idField == null)
		{
			throw new AdempiereException("Entity " + this + " does not have a single ID");
		}
		return idField;
	}

	public Collection<DocumentFieldDescriptor> getFields()
	{
		return fields.values();
	}

	public boolean hasField(final String fieldName)
	{
		return fields.containsKey(fieldName);
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

	public Set<String> getFieldNamesWithCharacteristic(final Characteristic characteristic)
	{
		return _fieldNamesByCharacteristic.computeIfAbsent(characteristic, this::buildFieldsWithCharacteristic);
	}

	private Set<String> buildFieldsWithCharacteristic(final Characteristic characteristic)
	{
		return getFields()
				.stream()
				.filter(field -> field.hasCharacteristic(characteristic))
				.map(DocumentFieldDescriptor::getFieldName)
				.collect(GuavaCollectors.toImmutableSet());
	}

	public DocumentFieldDescriptor getParentLinkFieldOrNull()
	{
		return parentLinkField;
	}

	public IIncludedDocumentsCollection createIncludedDocumentsCollection(final Document parentDocument)
	{
		return includedDocumentsCollectionFactory.createIncludedDocumentsCollection(parentDocument, this);
	}

	public Collection<DocumentEntityDescriptor> getIncludedEntities()
	{
		return includedEntitiesByDetailId.values();
	}

	/**
	 * @return included {@link DocumentEntityDescriptor}; never returns null
	 */
	public DocumentEntityDescriptor getIncludedEntityByDetailId(final DetailId detailId) throws NoSuchElementException
	{
		final DocumentEntityDescriptor includedEntityDescriptor = includedEntitiesByDetailId.get(detailId);
		if (includedEntityDescriptor == null)
		{
			throw new NoSuchElementException("No included entity found for detailId=" + detailId + " in " + this);
		}
		return includedEntityDescriptor;
	}

	public List<DocumentEntityDescriptor> getIncludedEntitiesByTableName(@NonNull final String tableName)
	{
		return streamIncludedEntitiesByTableName(tableName)
				.collect(ImmutableList.toImmutableList());
	}

	public Stream<DocumentEntityDescriptor> streamIncludedEntitiesByTableName(@NonNull final String tableName)
	{
		return includedEntitiesByDetailId.values()
				.stream()
				.filter(includedEntity -> tableName.equals(includedEntity.getTableNameOrNull()));
	}

	public <T extends DocumentEntityDataBindingDescriptor> T getDataBinding(final Class<T> ignoredBindingType)
	{
		@SuppressWarnings("unchecked") final T dataBindingCasted = (T)getDataBinding();
		return dataBindingCasted;
	}

	// legacy

	/**
	 * @throws IllegalArgumentException if AD_Tab_ID is not defined
	 */
	public AdTabId getAdTabId()
	{
		return adTabId.orElseThrow(() -> new IllegalStateException("No TableName defined for " + this));
	}

	// legacy

	/**
	 * @return tableName
	 * @throws IllegalArgumentException if TableName is not defined
	 * @see #getTableNameOrNull()
	 */
	public String getTableName()
	{
		return tableName.orElseThrow(() -> new IllegalStateException("No TableName defined for " + this));
	}

	public String getTableNameOrNull()
	{
		return tableName.orElse(null);
	}

	// legacy
	public Optional<SOTrx> getSOTrx()
	{
		return soTrx;
	}

	public ITabCallout createAndInitializeDocumentCallout(final ICalloutRecord documentAsCalloutRecord)
	{
		if (!defaultTableCalloutsEnabled)
		{
			return ITabCallout.NULL;
		}
		return Services.get(ITabCalloutFactory.class).createAndInitialize(documentAsCalloutRecord);
	}

	public ICalloutExecutor createFieldsCalloutExecutor()
	{
		if (!calloutsEnabled)
		{
			return NullCalloutExecutor.instance;
		}
		return calloutExecutorFactory.newInstanceSharingMasterData();
	}

	public AdProcessId getPrintProcessId()
	{
		if (printProcessId == null)
		{
			new AdempiereException("No print process configured for " + this);
		}
		return printProcessId;
	}

	public boolean isPrintable()
	{
		return printProcessId != null;
	}

	//
	//
	// -----------------------------------------------------------------------------------------------------------------------
	//
	//

	@SuppressWarnings({ "OptionalAssignedToNull", "UnusedReturnValue" })
	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentEntityDescriptor.Builder.class);
		private DocumentFilterDescriptorsProvidersService filterDescriptorsProvidersService;

		private boolean _built = false;

		private DocumentType _documentType;
		private DocumentId _documentTypeId;

		private String _internalName;

		private ITranslatableString _caption = TranslatableStrings.empty();
		private ITranslatableString _description = TranslatableStrings.empty();

		private final Map<String, DocumentFieldDescriptor.Builder> _fieldBuilders = new LinkedHashMap<>();
		private Map<String, DocumentFieldDescriptor> _fields = null; // will be built
		private final Map<DetailId, DocumentEntityDescriptor> _includedEntitiesByDetailId = new LinkedHashMap<>();
		private DocumentEntityDataBindingDescriptorBuilder _dataBinding = DocumentEntityDataBindingDescriptorBuilder.NULL;
		private boolean _highVolume;

		private DetailId _detailId;

		private ILogicExpression _allowCreateNewLogic = ConstantLogicExpression.TRUE;
		private ILogicExpression _allowDeleteLogic = ConstantLogicExpression.TRUE;
		private ILogicExpression _displayLogic = ConstantLogicExpression.TRUE;
		private ILogicExpression _readonlyLogic = ConstantLogicExpression.FALSE;

		@Getter
		private QuickInputSupportDescriptor quickInputSupport = null;

		private IncludedTabNewRecordInputMode includedTabNewRecordInputMode = IncludedTabNewRecordInputMode.ALL_AVAILABLE_METHODS;

		private boolean _refreshViewOnChangeEvents = false;

		//
		// Callouts
		private boolean _calloutsEnabled = true; // enabled by default
		private boolean _defaultTableCalloutsEnabled = true; // enabled by default

		private AdProcessId _printProcessId = null;

		@Getter
		private boolean singleRowDetail = false;

		@Getter
		private boolean autodetectDefaultDateFilter = true;

		// Legacy
		private Optional<AdTabId> _adTabId = Optional.empty();
		private Optional<String> _tableName = Optional.empty();
		private Optional<SOTrx> _soTrx = Optional.empty();
		private int viewPageLength;

		private Builder()
		{
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("documentType", _documentType)
					.add("documentTypeId", _documentTypeId)
					.add("detailId", _detailId)
					.add("caption", _caption)
					.add("tableName", _tableName)
					.toString();
		}

		public DocumentEntityDescriptor build()
		{
			assertNotBuilt();
			_built = true;

			return new DocumentEntityDescriptor(this);
		}

		private void assertNotBuilt()
		{
			if (_built)
			{
				throw new IllegalStateException("Already built: " + this);
			}
		}

		/**
		 * @return {@link DocumentEntityDescriptor}'s ID
		 */
		private String getId()
		{
			final StringBuilder id = new StringBuilder();
			id.append(getDocumentType());
			id.append("-").append(getDocumentTypeId());

			final DetailId detailId = getDetailId();
			if (detailId != null)
			{
				id.append("-").append(detailId);
			}

			return id.toString();
		}

		public Builder setInternalName(final String internalName)
		{
			this._internalName = internalName;
			return this;
		}

		public String getInternalName()
		{
			if (_internalName != null)
			{
				return _internalName;
			}

			return getId();
		}

		public WindowId getWindowId()
		{
			Check.assume(_documentType == DocumentType.Window, "expected document type to be {} but it was {}", DocumentType.Window, _documentType);
			return WindowId.of(_documentTypeId);
		}

		public Builder setDetailId(@Nullable final DetailId detailId)
		{
			_detailId = detailId;

			updateFieldBuilders(fieldBuilder -> fieldBuilder.setDetailId(detailId));

			return this;
		}

		public DetailId getDetailId()
		{
			return _detailId;
		}

		private void assertFieldsNotBuilt()
		{
			assertNotBuilt();
			Check.assumeNull(_fields, "Fields not already built");
		}

		public Builder addFieldIf(final boolean condition, @NonNull final Supplier<DocumentFieldDescriptor.Builder> fieldBuilderSupplier)
		{
			if (!condition)
			{
				return this;
			}

			assertFieldsNotBuilt();
			addField(fieldBuilderSupplier.get());
			return this;
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

		public Collection<DocumentFieldDescriptor.Builder> getFieldBuilders()
		{
			return _fieldBuilders.values();
		}

		public boolean hasField(final String fieldName)
		{
			return getFieldBuilder(fieldName) != null;
		}

		public int getFieldsCount()
		{
			return _fieldBuilders.size();
		}

		private void updateFieldBuilders(final Consumer<DocumentFieldDescriptor.Builder> fieldUpdater)
		{
			assertFieldsNotBuilt();

			_fieldBuilders.values()
					.forEach(fieldUpdater);
		}

		private ImmutableList<DocumentFieldDescriptor> getIdFields()
		{
			return getFields()
					.values()
					.stream()
					.filter(DocumentFieldDescriptor::isKey)
					.collect(ImmutableList.toImmutableList());
		}

		public DocumentFieldDescriptor.Builder getSingleIdFieldBuilderOrNull()
		{
			final List<DocumentFieldDescriptor.Builder> idFieldBuilders = getIdFieldBuilders();
			return idFieldBuilders.size() == 1 ? idFieldBuilders.get(0) : null;
		}

		@Nullable
		public String getSingleIdFieldNameOrNull()
		{
			final List<DocumentFieldDescriptor.Builder> idFieldBuilders = getIdFieldBuilders();
			return idFieldBuilders.size() == 1 ? idFieldBuilders.get(0).getFieldName() : null;
		}

		private List<DocumentFieldDescriptor.Builder> getIdFieldBuilders()
		{
			return _fieldBuilders
					.values()
					.stream()
					.filter(DocumentFieldDescriptor.Builder::isKey)
					.collect(ImmutableList.toImmutableList());
		}

		private Map<String, DocumentFieldDescriptor> getFields()
		{
			if (_fields == null)
			{
				_fields = _fieldBuilders
						.values()
						.stream()
						.map(DocumentFieldDescriptor.Builder::getOrBuild)
						.collect(GuavaCollectors.toImmutableMapByKey(DocumentFieldDescriptor::getFieldName));
			}
			return _fields;
		}

		private DocumentFieldDescriptor getParentLinkFieldOrNull()
		{
			final List<DocumentFieldDescriptor> parentLinkFields = getFields()
					.values()
					.stream()
					.filter(DocumentFieldDescriptor::isParentLink)
					.collect(ImmutableList.toImmutableList());
			if (parentLinkFields.isEmpty())
			{
				return null;
			}
			else if (parentLinkFields.size() == 1)
			{
				return parentLinkFields.get(0);
			}
			else
			{
				throw new AdempiereException("More than one parent link fields found for " + this)
						.appendParametersToMessage()
						.setParameter("parentLinkFields", parentLinkFields);
			}
		}

		public Builder addAllIncludedEntities(@NonNull final Collection<DocumentEntityDescriptor> includedEntities)
		{
			for (final DocumentEntityDescriptor includedEntity : includedEntities)
			{
				addIncludedEntity(includedEntity);
			}
			return this;
		}

		public Builder addIncludedEntity(@NonNull final DocumentEntityDescriptor includedEntity)
		{
			final DetailId detailId = includedEntity.getDetailId();
			Check.assumeNotNull(detailId, "detailId is not null for {}", includedEntity);
			_includedEntitiesByDetailId.put(detailId, includedEntity);
			return this;
		}

		private ImmutableMap<DetailId, DocumentEntityDescriptor> buildIncludedEntitiesByDetailId()
		{
			return ImmutableMap.copyOf(_includedEntitiesByDetailId);
		}

		public IIncludedDocumentsCollectionFactory getIncludedDocumentsCollectionFactory()
		{
			if (isSingleRowDetail())
			{
				return SingleRowDetailIncludedDocumentsCollection::new;
			}

			if (isHighVolume())
			{
				if (getReadonlyLogic().isConstantTrue())
				{
					return HighVolumeReadonlyIncludedDocumentsCollection::new;
				}
				else
				{
					return HighVolumeReadWriteIncludedDocumentsCollection::newInstance;
				}
			}

			// Fallback
			return HighVolumeReadWriteIncludedDocumentsCollection::newInstance;
		}

		public Builder setDataBinding(final DocumentEntityDataBindingDescriptorBuilder dataBindingBuilder)
		{
			_dataBinding = dataBindingBuilder;
			return this;
		}

		public <T extends DocumentEntityDataBindingDescriptorBuilder> T getDataBindingBuilder(final Class<T> ignoredBuilderType)
		{
			@SuppressWarnings("unchecked") final T dataBindingBuilder = (T)_dataBinding;
			return dataBindingBuilder;
		}

		private DocumentEntityDataBindingDescriptor getOrBuildDataBinding()
		{
			Preconditions.checkNotNull(_dataBinding, "dataBinding");
			return _dataBinding.getOrBuild();
		}

		public Builder setHighVolume(final boolean highVolume)
		{
			_highVolume = highVolume;
			return this;
		}

		public boolean isHighVolume()
		{
			return _highVolume;
		}

		public Builder setSingleRowDetail(final boolean singleRowDetail)
		{
			this.singleRowDetail = singleRowDetail;
			return this;
		}

		public boolean isQueryIncludedTabOnActivate()
		{
			return true;
		}

		private DocumentFieldDependencyMap buildDependencies()
		{
			final DocumentFieldDependencyMap.Builder dependenciesBuilder = DocumentFieldDependencyMap.builder();

			dependenciesBuilder.add(DocumentFieldDependencyMap.DOCUMENT_Readonly,
					getReadonlyLogic().getParameterNames(),
					DependencyType.DocumentReadonlyLogic);

			getFields().values().forEach(field -> dependenciesBuilder.add(field.getDependencies()));
			return dependenciesBuilder.build();
		}

		public Builder setDocumentType(final DocumentType documentType, final DocumentId documentTypeId)
		{
			_documentType = documentType;
			_documentTypeId = documentTypeId;
			return this;
		}

		public Builder setDocumentType(@NonNull final AdWindowId adWindowId)
		{
			return setDocumentType(DocumentType.Window, DocumentId.of(adWindowId.getRepoId()));
		}

		public DocumentType getDocumentType()
		{
			Check.assumeNotNull(_documentType, "documentType is set for {}", this);
			return _documentType;
		}

		public DocumentId getDocumentTypeId()
		{
			Check.assumeNotNull(_documentTypeId, "documentTypeId is set for {}", this);
			return _documentTypeId;
		}

		public Builder setAD_Tab_ID(final int AD_Tab_ID)
		{
			_adTabId = Optional.ofNullable(AdTabId.ofRepoIdOrNull(AD_Tab_ID));
			return this;
		}

		public Optional<AdTabId> getAdTabId()
		{
			return _adTabId;
		}

		public Builder setTableName(final String tableName)
		{
			_tableName = Optional.ofNullable(tableName);
			return this;
		}

		public Optional<String> getTableName()
		{
			return _tableName;
		}

		public String getTableNameOrNull()
		{
			return _tableName.orElse(null);
		}

		public Builder setCaption(final Map<String, String> captionTrls, final String defaultCaption)
		{
			_caption = TranslatableStrings.ofMap(captionTrls, defaultCaption);
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			Check.assumeNotNull(caption, "Parameter caption is not null");
			_caption = caption;
			return this;
		}

		public Builder setCaption(final String caption)
		{
			_caption = TranslatableStrings.constant(caption);
			return this;
		}

		public ITranslatableString getCaption()
		{
			return _caption;
		}

		public Builder setDescription(final Map<String, String> descriptionTrls, final String defaultDescription)
		{
			_description = TranslatableStrings.ofMap(descriptionTrls, defaultDescription);
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			Check.assumeNotNull(description, "Parameter description is not null");
			_description = description;
			return this;
		}

		public Builder setDescription(final String description)
		{
			_description = TranslatableStrings.constant(description);
			return this;
		}

		public ITranslatableString getDescription()
		{
			return _description;
		}

		public Builder setIsSOTrx(final boolean isSOTrx)
		{
			_soTrx = Optional.of(SOTrx.ofBoolean(isSOTrx));
			return this;
		}

		public Builder setIsSOTrx(final Optional<SOTrx> soTrx)
		{
			_soTrx = soTrx != null ? soTrx : Optional.empty();
			return this;
		}

		public Optional<SOTrx> getSOTrx()
		{
			return _soTrx;
		}

		public Builder setViewPageLength(final int viewPageLength)
		{
			this.viewPageLength = Math.max(viewPageLength, 0);
			return this;
		}

		public int getViewPageLength()
		{
			return viewPageLength;
		}

		public Builder setAllowCreateNewLogic(final ILogicExpression allowCreateNewLogic)
		{
			Check.assumeNotNull(allowCreateNewLogic, "Parameter allowCreateNewLogic is not null");
			_allowCreateNewLogic = allowCreateNewLogic;
			return this;
		}

		public ILogicExpression getAllowCreateNewLogic()
		{
			return _allowCreateNewLogic;
		}

		public Builder setAllowDeleteLogic(final ILogicExpression allowDeleteLogic)
		{
			Check.assumeNotNull(allowDeleteLogic, "Parameter allowDeleteLogic is not null");
			_allowDeleteLogic = allowDeleteLogic;
			return this;
		}

		private ILogicExpression getAllowDeleteLogic()
		{
			return _allowDeleteLogic;
		}

		public Builder setDisplayLogic(final ILogicExpression displayLogic)
		{
			_displayLogic = displayLogic;
			return this;
		}

		public ILogicExpression getDisplayLogic()
		{
			return _displayLogic;
		}

		public Builder setReadonlyLogic(@NonNull final ILogicExpression readonlyLogic)
		{
			_readonlyLogic = readonlyLogic;
			updateFieldBuilders(fieldBuilder -> fieldBuilder.setEntityReadonlyLogic(readonlyLogic));
			return this;
		}

		private ILogicExpression getReadonlyLogic()
		{
			return _readonlyLogic;
		}

		public Builder setQuickInputSupport(@Nullable final QuickInputSupportDescriptor quickInputSupport)
		{
			this.quickInputSupport = quickInputSupport;
			return this;
		}

		public Builder setIncludedTabNewRecordInputMode(@NonNull final IncludedTabNewRecordInputMode includedTabNewRecordInputMode)
		{
			this.includedTabNewRecordInputMode = includedTabNewRecordInputMode;
			return this;
		}

		public IncludedTabNewRecordInputMode getIncludedTabNewRecordInputMode()
		{
			return includedTabNewRecordInputMode;
		}

		public Builder setAutodetectDefaultDateFilter(final boolean autodetectDefaultDateFilter)
		{
			this.autodetectDefaultDateFilter = autodetectDefaultDateFilter;
            return this;
		}

		/**
		 * Advises the descriptor that Document instances which will be created based on this descriptor will not have ANY callouts.
		 */
		public Builder disableCallouts()
		{
			_calloutsEnabled = false;
			return this;
		}

		private boolean isCalloutsEnabled()
		{
			return _calloutsEnabled;
		}

		public Builder disableDefaultTableCallouts()
		{
			_defaultTableCalloutsEnabled = false;
			return this;
		}

		private boolean isDefaultTableCalloutsEnabled()
		{
			return _calloutsEnabled && _defaultTableCalloutsEnabled;
		}

		private ICalloutExecutor buildCalloutExecutorFactory(final Collection<DocumentFieldDescriptor> fields)
		{
			if (!isCalloutsEnabled())
			{
				return NullCalloutExecutor.instance;
			}

			//
			// CalloutExecutor builder
			final String tableName = getTableName().orElse(ICalloutProvider.ANY_TABLE);
			final CalloutExecutor.Builder calloutExecutorBuilder = CalloutExecutor.builder()
					.setTableName(tableName);

			//
			// Create a provider from callouts which were programmatically registered on each field
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
			//
			calloutExecutorBuilder.addCalloutProvider(entityCalloutProviderBuilder.build());

			//
			// Standard callouts provider (which will fetch the callouts from application dictionary)
			if (isDefaultTableCalloutsEnabled())
			{
				calloutExecutorBuilder.addDefaultCalloutProvider();
			}

			//
			return calloutExecutorBuilder.build();
		}

		private DocumentFilterDescriptorsProvider createFilterDescriptors()
		{
			final DocumentFilterDescriptorsProvidersService filterDescriptorsProvidersService = this.filterDescriptorsProvidersService != null
					? this.filterDescriptorsProvidersService
					: SpringContextHolder.instance.getBean(DocumentFilterDescriptorsProvidersService.class);

			final String tableName = getTableName().orElse(null);
			final AdTabId adTabId = getAdTabId().orElse(null);
			final Collection<DocumentFieldDescriptor> fields = getFields().values();

			final CreateFiltersProviderContext context = CreateFiltersProviderContext.builder()
					.adTabId(adTabId)
					.tableName(tableName)
					.isAutodetectDefaultDateFilter(isAutodetectDefaultDateFilter())
					.build();

			return filterDescriptorsProvidersService.createFiltersProvider(context, fields);
		}

		public Builder setFilterDescriptorsProvidersService(final DocumentFilterDescriptorsProvidersService filterDescriptorsProvidersService)
		{
			this.filterDescriptorsProvidersService = filterDescriptorsProvidersService;
			return this;
		}

		public Builder setRefreshViewOnChangeEvents(final boolean refreshViewOnChangeEvents)
		{
			this._refreshViewOnChangeEvents = refreshViewOnChangeEvents;
			return this;
		}

		private boolean isRefreshViewOnChangeEvents()
		{
			return _refreshViewOnChangeEvents;
		}

		public Builder setPrintProcessId(final AdProcessId printProcessId)
		{
			_printProcessId = printProcessId;
			return this;
		}

		private AdProcessId getPrintProcessId()
		{
			return _printProcessId;
		}

		private boolean isCloneEnabled()
		{
			return isCloneEnabled(_tableName);
		}

		private static boolean isCloneEnabled(@Nullable final Optional<String> tableName)
		{
			if (tableName == null || !tableName.isPresent())
			{
				return false;
			}

			return CopyRecordFactory.isEnabledForTableName(tableName.get());
		}

		public DocumentQueryOrderByList getDefaultOrderBys()
		{
			return getFieldBuilders()
					.stream()
					.filter(DocumentFieldDescriptor.Builder::isDefaultOrderBy)
					.sorted(Ordering.natural().onResultOf(DocumentFieldDescriptor.Builder::getDefaultOrderByPriority))
					.map(field -> DocumentQueryOrderBy.byFieldName(field.getFieldName(), field.isDefaultOrderByAscending()))
					.collect(DocumentQueryOrderByList.toDocumentQueryOrderByList());
		}
	}
}
