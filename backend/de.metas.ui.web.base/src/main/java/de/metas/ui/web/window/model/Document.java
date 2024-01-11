package de.metas.ui.web.window.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.exceptions.DocumentProcessingException;
import de.metas.i18n.BooleanWithReason;
import de.metas.lang.SOTrx;
import de.metas.letters.model.Letters;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap.DependencyType;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.decorator.ReadOnlyInfo;
import de.metas.ui.web.window.exceptions.DocumentFieldNotFoundException;
import de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.exceptions.InvalidDocumentStateException;
import de.metas.ui.web.window.model.DocumentsRepository.SaveResult;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentField.FieldInitializationMode;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.api.LogicExpressionResultWithReason;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

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

public final class Document
{
	public static Builder builder(final DocumentEntityDescriptor entityDescriptor)
	{
		return new Builder(entityDescriptor);
	}

	private static final Logger logger = LogManager.getLogger(Document.class);

	public static final Document NULL = null;

	private static final ReasonSupplier REASON_Value_DirectSetOnDocument = () -> "direct set on Document";
	private static final ReasonSupplier REASON_Value_NewDocument = () -> "new document";
	private static final ReasonSupplier REASON_Value_Refreshing = () -> "direct set on Document (refresh)";
	private static final ReasonSupplier REASON_Value_ParentLinkUpdateOnSave = () -> "parent link update on save";

	//
	// Descriptors & paths
	private final DocumentEntityDescriptor entityDescriptor;
	private final int windowNo;
	private final DocumentPath documentPath;

	//
	// Status
	private boolean _new;
	private boolean _deleted;
	private final boolean _writable;
	private FieldInitializationMode _initializingMode = null;
	private DocumentValidStatus _valid;
	private DocumentValidStatus _validOnCheckout;
	private DocumentSaveStatus _saveStatus;
	private DocumentSaveStatus _saveStatusOnCheckout;
	private final DocumentStaleState _staleStatus;
	private final ReentrantReadWriteLock _lock;
	// Status: readonly
	private DocumentReadonly parentReadonly = DocumentReadonly.NOT_READONLY;
	private DocumentReadonly readonly = DocumentReadonly.NOT_READONLY;

	//
	// Callouts
	private ITabCallout documentCallout = ITabCallout.NULL; // will be set from builder, after document is initialized
	private final ICalloutExecutor fieldCalloutExecutor;
	private DocumentAsCalloutRecord _calloutRecord; // lazy

	//
	// Fields
	private final ImmutableMap<String, IDocumentField> fieldsByName;
	private final ImmutableList<IDocumentFieldView> idFields;
	private final IDocumentField parentLinkField;

	//
	// Parent & children
	private final Document _parentDocument;
	private final ImmutableMap<DetailId, IIncludedDocumentsCollection> includedDocuments;

	//
	// Evaluatee
	private IDocumentEvaluatee _evaluatee; // lazy
	private transient IDocumentEvaluatee _shadowParentEvaluatee;

	// Changes tracking
	private final transient IDocumentChangesCollector changesCollector;

	//
	// Misc
	private Map<String, Object> _dynAttributes = null; // lazy

	public interface DocumentValuesSupplier
	{
		@SuppressWarnings("StringOperationCanBeSimplified")
		Object NO_VALUE = new String("NO_VALUE");

		DocumentId getDocumentId();

		String getVersion();

		/**
		 * @return initial value or {@link #NO_VALUE} if it cannot provide a value
		 */
		Object getValue(final DocumentFieldDescriptor fieldDescriptor);
	}

	private Document(@NonNull final Builder builder)
	{
		entityDescriptor = builder.getEntityDescriptor();
		_parentDocument = builder.getParentDocument();
		documentPath = builder.getDocumentPath();
		windowNo = builder.getWindowNo();
		_writable = builder.isWritable();
		_new = builder.isNewDocument();
		_deleted = false;
		_staleStatus = new DocumentStaleState();
		_lock = builder.createLock();
		parentReadonly = _parentDocument != null ? _parentDocument.getReadonly() : DocumentReadonly.NOT_READONLY;
		readonly = DocumentReadonly.ofParent(parentReadonly);

		_validOnCheckout = DocumentValidStatus.documentInitiallyInvalid();
		_valid = _validOnCheckout;

		_saveStatusOnCheckout = DocumentSaveStatus.unknown();
		_saveStatus = _saveStatusOnCheckout;

		changesCollector = builder.getChangesCollector();

		//
		// Create document fields
		{
			final ImmutableMap.Builder<String, IDocumentField> fieldsBuilder = ImmutableMap.builder();
			IDocumentField parentLinkField = null;
			for (final DocumentFieldDescriptor fieldDescriptor : entityDescriptor.getFields())
			{
				final String fieldName = fieldDescriptor.getFieldName();
				final IDocumentField field = builder.buildField(fieldDescriptor, this);
				fieldsBuilder.put(fieldName, field);

				if (fieldDescriptor.isParentLink())
				{
					Check.assumeNull(parentLinkField, "Only one parent link field shall exist but we found: {}, {}", parentLinkField, field); // shall no happen at this level
					parentLinkField = field;
				}
			}
			fieldsByName = fieldsBuilder.build();
			this.parentLinkField = parentLinkField;

			idFields = entityDescriptor.getIdFields()
					.stream()
					.map(idField -> fieldsByName.get(idField.getFieldName()))
					.collect(ImmutableList.toImmutableList());
		}

		//
		// Create included documents containers
		{
			this.includedDocuments = extractIncludedDocuments(entityDescriptor.getIncludedEntities());
		}

		//
		// Initialize field callout executor
		fieldCalloutExecutor = entityDescriptor.createFieldsCalloutExecutor();

		//
		// Evaluatee
		_evaluatee = null; // lazy
		_shadowParentEvaluatee = null;

		//
		// Set default dynamic attributes
		{
			setDynAttributeNoCheck("DirectShip", false); // FIXME: workaround for https://github.com/metasfresh/metasfresh/issues/287 to avoid WARNINGs
			setDynAttributeNoCheck("HasCharges", false); // FIXME hardcoded because: Failed evaluating display logic @HasCharges@='Y' for C_Order.C_Charge_ID,ChargeAmt

			// Set document's header window default values
			// NOTE: these dynamic attributes will be considered by Document.asEvaluatee.
			if (_parentDocument == null)
			{
				final Optional<SOTrx> soTrx = entityDescriptor.getSOTrx();
				if (soTrx.isPresent())
				{
					setDynAttributeNoCheck("IsSOTrx", soTrx.get().isSales()); // cover the case for FieldName=IsSOTrx, DefaultValue=@IsSOTrx@
				}
				setDynAttributeNoCheck("IsApproved", false); // cover the case for FieldName=IsApproved, DefaultValue=@IsApproved@
			}
		}

		//
		// Done
		logger.trace("Created new document instance: {}", this); // keep it last
	}

	private ImmutableMap<DetailId, IIncludedDocumentsCollection> extractIncludedDocuments(@NonNull final Collection<DocumentEntityDescriptor> includedEntities)
	{
		final ImmutableMap.Builder<DetailId, IIncludedDocumentsCollection> includedDocuments = ImmutableMap.builder();

		for (final DocumentEntityDescriptor includedEntityDescriptor : includedEntities)
		{
			// if (!includedEntityDescriptor.getFields().isEmpty())
			// {
			final DetailId detailId = includedEntityDescriptor.getDetailId();
			final IIncludedDocumentsCollection includedDocumentsForDetailId = includedEntityDescriptor.createIncludedDocumentsCollection(this);
			includedDocuments.put(detailId, includedDocumentsForDetailId);
			// }

			// recurse
			includedDocuments.putAll(extractIncludedDocuments(includedEntityDescriptor.getIncludedEntities()));
		}
		return includedDocuments.build();
	}

	/**
	 * copy constructor
	 */
	private Document(final Document from, @Nullable final Document parentDocumentCopy, final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		documentPath = from.documentPath;
		entityDescriptor = from.entityDescriptor;
		windowNo = from.windowNo;
		_writable = copyMode.isWritable();

		_new = from._new;
		_deleted = from._deleted;
		_staleStatus = new DocumentStaleState(from._staleStatus);
		_lock = from._lock; // always share the same lock

		_valid = from._valid;
		_saveStatus = from._saveStatus;
		this.changesCollector = changesCollector;
		switch (copyMode)
		{
			case CheckInReadonly:
				_validOnCheckout = from._valid;
				_saveStatusOnCheckout = from._saveStatus;
				break;
			case CheckOutWritable:
				_validOnCheckout = from._validOnCheckout;
				_saveStatusOnCheckout = from._saveStatusOnCheckout;
				break;
			default:
				throw new IllegalArgumentException("Unknown copy mode: " + copyMode);
				// break;
		}

		if (from._parentDocument != null)
		{
			Preconditions.checkNotNull(parentDocumentCopy, "parentDocumentCopy");
			_parentDocument = parentDocumentCopy;
			parentReadonly = parentDocumentCopy.getReadonly();
			readonly = from.readonly;
		}
		else
		{
			Preconditions.checkArgument(parentDocumentCopy == null, "parentDocumentCopy shall be null");
			_parentDocument = null;
			parentReadonly = DocumentReadonly.NOT_READONLY;
			readonly = from.readonly;
		}

		//
		// Copy document fields
		{
			final ImmutableMap.Builder<String, IDocumentField> fieldsBuilder = ImmutableMap.builder();
			IDocumentField parentLinkField = null;
			for (final IDocumentField fieldOrig : from.fieldsByName.values())
			{
				final IDocumentField fieldCopy = fieldOrig.copy(this, copyMode);
				final String fieldName = fieldCopy.getFieldName();
				fieldsBuilder.put(fieldName, fieldCopy);

				if (fieldOrig == from.parentLinkField)
				{
					parentLinkField = fieldCopy;
				}
			}
			fieldsByName = fieldsBuilder.build();
			this.parentLinkField = parentLinkField;
			this.idFields = from.idFields
					.stream()
					.map(idFieldFrom -> fieldsByName.get(idFieldFrom.getFieldName()))
					.collect(ImmutableList.toImmutableList());
		}

		//
		// Copy included documents containers
		{
			final ImmutableMap.Builder<DetailId, IIncludedDocumentsCollection> includedDocuments = ImmutableMap.builder();
			for (final Map.Entry<DetailId, IIncludedDocumentsCollection> e : from.includedDocuments.entrySet())
			{
				final DetailId detailId = e.getKey();
				final IIncludedDocumentsCollection includedDocumentsForDetailIdOrig = e.getValue();
				final IIncludedDocumentsCollection includedDocumentsForDetailIdCopy = includedDocumentsForDetailIdOrig.copy(this, copyMode);

				includedDocuments.put(detailId, includedDocumentsForDetailIdCopy);
			}
			this.includedDocuments = includedDocuments.build();
		}

		//
		// Initialize callout executor
		documentCallout = from.documentCallout;
		fieldCalloutExecutor = entityDescriptor.createFieldsCalloutExecutor();

		_evaluatee = null; // lazy
		_shadowParentEvaluatee = null; // never copy it!

		//
		// Copy dynamic attributes
		if (from._dynAttributes != null && !from._dynAttributes.isEmpty())
		{
			_dynAttributes = new HashMap<>(from._dynAttributes);
		}
		else
		{
			_dynAttributes = null;
		}

		//
		// Done
		logger.trace("Created COPY document instance: {} as a copy of {}", this, from); // keep it last
	}

	private boolean isInitializing()
	{
		return _initializingMode != null;
	}

	private boolean isInitializingNewDocument()
	{
		return isInitializing() && isNew();
	}

	/* package */boolean isWritable()
	{
		return _writable;
	}

	private void initializeFields(final FieldInitializationMode mode, final DocumentValuesSupplier documentValuesSupplier)
	{
		logger.trace("Initializing fields: mode={}", mode);

		if (_initializingMode != null)
		{
			throw new InvalidDocumentStateException(this, "already initializing");
		}
		_initializingMode = mode;
		try
		{
			//
			// Actually initialize document fields
			for (final IDocumentField documentField : getFields())
			{
				initializeField(documentField, mode, documentValuesSupplier);
			}

			//
			// Fire callouts
			if (FieldInitializationMode.NewDocument == mode)
			{
				// FIXME: i think it would be better to trigger the callouts when setting the initial value
				try
				{
					executeAllFieldCallouts();
				}
				catch (final Exception e)
				{
					logger.warn("Failed executing callouts while initializing {}. Ignored.", this, e);
				}

				documentCallout.onNew(asCalloutRecord());
			}
			else if (FieldInitializationMode.Load == mode)
			{
				// don't call the callouts on load
				// we shall have the document as it is
			}
			else if (FieldInitializationMode.Refresh == mode)
			{
				documentCallout.onRefresh(asCalloutRecord());
			}

			//
			// Update field's flags (readonly, mandatory etc)
			updateAllDependencies();

			//
			// Mark the document not staled because we just initialized it
			getStale().markNotStaled(documentValuesSupplier.getVersion());

			//
			// Update document's valid status
			checkAndGetValidStatus(OnValidStatusChanged.DO_NOTHING);
			updateIncludedDetailsStatus();

			//
			// Update document's save status
			if (mode == FieldInitializationMode.NewDocument)
			{
				setSaveStatusAndReturn(DocumentSaveStatus.notSavedJustCreated());
			}
			else if (mode == FieldInitializationMode.Load
					|| mode == FieldInitializationMode.Refresh)
			{
				setSaveStatusAndReturn(DocumentSaveStatus.savedJustLoaded());
			}
			else
			{
				throw new IllegalArgumentException("Unknown initialization mode: " + mode);
			}

		}
		finally
		{
			_initializingMode = null;
		}
	}

	/**
	 * Set field's initial value
	 *
	 * @param mode               initialization mode
	 * @param fieldValueSupplier initial value supplier
	 */
	private void initializeField(final IDocumentField documentField, final FieldInitializationMode mode, final DocumentValuesSupplier fieldValueSupplier)
	{
		boolean valueSet = false;
		Object valueOld = null;
		try
		{
			final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();

			//
			// Get the initialization value
			final Object initialValue = fieldValueSupplier.getValue(fieldDescriptor);

			//
			// Update field's initial value
			if (initialValue != DocumentValuesSupplier.NO_VALUE)
			{
				valueOld = documentField.getValue();
				documentField.setInitialValue(initialValue, changesCollector);
			}

			valueSet = true;
		}
		catch (final Exception e)
		{
			valueSet = false;
			if (FieldInitializationMode.NewDocument == mode)
			{
				logger.warn("Failed initializing {}, mode={} using {}. Keeping current initial value.", documentField, mode, fieldValueSupplier, e);
			}
			else
			{
				throw AdempiereException.wrapIfNeeded(e)
						.appendParametersToMessage()
						.setParameter("mode", mode)
						.setParameter("documentField", documentField)
						.setParameter("fieldValueSupplier", fieldValueSupplier);
			}
		}

		//
		// After field was initialized, based on "mode", trigger events, update other fields etc
		if (FieldInitializationMode.NewDocument == mode)
		{
			// Collect the change event, even if there was no change because we just set the initial value
			changesCollector.collectValueChanged(documentField, REASON_Value_NewDocument);

			// NOTE: don't update fields flags which depend on this field because we will do it all together after all fields are initialized
			// NOTE: don't call callouts because we will do it all together after all fields are initialized
		}
		else if (FieldInitializationMode.Load == mode)
		{
			// NOTE: don't collect field changes because we are just initializing a new Document instance from an existing database record.
			// NOTE: don't update fields flags which depend on this field because we will do it all together after all fields are initialized
			// NOTE: don't call callouts because this was not a user change.
		}
		else if (FieldInitializationMode.Refresh == mode)
		{
			// NOTE: don't update fields flags which depend on this field because we will do it all together after all fields are initialized
			// NOTE: don't call callouts because this was not a user change.

			if (valueSet)
			{
				changesCollector.collectValueIfChanged(documentField, valueOld, REASON_Value_Refreshing);
			}
		}
	}

	private static final class SimpleDocumentValuesSupplier implements DocumentValuesSupplier
	{
		private final Supplier<DocumentId> documentIdSupplier;
		private final String version;

		public SimpleDocumentValuesSupplier(final DocumentId documentId, final String version)
		{
			documentIdSupplier = () -> documentId;
			this.version = version;
		}

		public SimpleDocumentValuesSupplier(final Supplier<DocumentId> documentIdSupplier, final String version)
		{
			this.documentIdSupplier = documentIdSupplier;
			this.version = version;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("documentIdSupplier", documentIdSupplier)
					.add("version", version)
					.toString();
		}

		@Override
		public DocumentId getDocumentId()
		{
			return documentIdSupplier.get();
		}

		@Override
		public String getVersion()
		{
			return version;
		}

		@Override
		public Object getValue(final DocumentFieldDescriptor fieldDescriptor)
		{
			return NO_VALUE;
		}
	}

	private static final class InitialFieldValueSupplier implements DocumentValuesSupplier
	{
		// Parameters
		private final DocumentValuesSupplier parentSupplier;
		private final DocumentType documentType;
		private final DocumentId documentTypeId;
		private final IDocumentEvaluatee _evaluatee;
		private final Object parentLinkValue;

		private InitialFieldValueSupplier(@NonNull final Document document, @NonNull final DocumentValuesSupplier parentSupplier)
		{
			this.parentSupplier = parentSupplier;

			final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
			documentType = entityDescriptor.getDocumentType();
			documentTypeId = entityDescriptor.getDocumentTypeId();

			_evaluatee = document.asEvaluatee();

			final DocumentFieldDescriptor linkFieldDescriptor = entityDescriptor.getParentLinkFieldOrNull();
			final Document parentDocument = document.getParentDocument();
			if (linkFieldDescriptor != null && parentDocument != null)
			{
				final String parentLinkFieldName = linkFieldDescriptor.getParentLinkFieldName();
				parentLinkValue = parentDocument.getField(parentLinkFieldName).getValue();
			}
			else
			{
				parentLinkValue = null;
			}
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("type", documentType)
					.add("typeId", documentTypeId)
					.add("evaluatee", _evaluatee)
					.toString();
		}

		@Override
		public DocumentId getDocumentId()
		{
			return parentSupplier.getDocumentId();
		}

		@Override
		public String getVersion()
		{
			return parentSupplier.getVersion();
		}

		private IDocumentEvaluatee getEvaluatee(final DocumentFieldDescriptor fieldInScope)
		{
			if (fieldInScope == null)
			{
				return _evaluatee;
			}
			return _evaluatee.fieldInScope(fieldInScope.getFieldName());
		}

		@Override
		public Object getValue(final DocumentFieldDescriptor fieldDescriptor)
		{
			//
			// Ask parent first
			{
				final Object value = parentSupplier.getValue(fieldDescriptor);
				if (value != NO_VALUE)
				{
					return value;
				}
			}

			//
			// Primary Key field
			if (fieldDescriptor.isKey())
			{
				final DocumentId id = parentSupplier.getDocumentId();
				if (id == null)
				{
					return null;
				}
				else if (id.isInt())
				{
					return id.toInt();
				}
				else
				{
					return id.toJson();
				}
			}

			//
			// Parent link field
			if (fieldDescriptor.isParentLink())
			{
				return parentLinkValue;
			}

			//
			// Default value expression
			final IExpression<?> defaultValueExpression = fieldDescriptor.getDefaultValueExpression().orElse(null);
			if (defaultValueExpression != null)
			{
				final IDocumentEvaluatee evaluateeEffective = getEvaluatee(fieldDescriptor);
				final Object value = defaultValueExpression.evaluate(evaluateeEffective, OnVariableNotFound.Fail);

				if (value != null
						&& String.class.equals(defaultValueExpression.getValueClass())
						&& Check.isEmpty(value.toString(), false))
				{
					// FIXME: figure out how we can get rid of this hardcoded corner case! ... not sure if is needed
					logger.warn("Development hint: Converting default value empty string to null. Please check how can we avoid this case"
									+ "\n FieldDescriptor: {}" //
									+ "\n Document: {}" //
							, fieldDescriptor, this);
					return null;
				}

				return value;
			}

			//
			// Window User Preferences (only if it's not a virtual field)
			if (documentType == DocumentType.Window && !fieldDescriptor.isVirtualField())
			{
				final Properties ctx = Env.getCtx();
				final AdWindowId adWindowId = documentTypeId.toId(AdWindowId::ofRepoId);
				final String fieldName = fieldDescriptor.getFieldName();

				//
				// Preference (user) - P|
				{
					final boolean retrieveGlobalPreferences = false; // retrieve Window level preferences
					final String valueStr = Env.getPreference(ctx, adWindowId, fieldName, retrieveGlobalPreferences);
					if (!Check.isEmpty(valueStr, false))
					{
						return valueStr;
					}
				}

				//
				// Preference (System) - # $
				{
					final boolean retrieveGlobalPreferences = true;
					final String valueStr = Env.getPreference(ctx, adWindowId, fieldName, retrieveGlobalPreferences);
					if (!Check.isEmpty(valueStr, false))
					{
						return valueStr;
					}
				}
			}

			//
			// Fallback
			return DocumentValuesSupplier.NO_VALUE;
		}
	}

	public enum CopyMode
	{
		CheckOutWritable(true), CheckInReadonly(false);

		private final boolean writable;

		CopyMode(final boolean writable)
		{
			this.writable = writable;
		}

		public boolean isWritable()
		{
			return writable;
		}
	}

	public Document copy(final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		final Document parentDocumentCopy = Document.NULL;
		return new Document(this, parentDocumentCopy, copyMode, changesCollector);
	}

	/* package */
	public Document copy(final Document parentDocumentCopy, final CopyMode copyMode)
	{
		return new Document(this, parentDocumentCopy, copyMode, parentDocumentCopy.changesCollector);
	}

	/* package */void assertWritable()
	{
		if (isInitializing())
		{
			return;
		}

		if (!isWritable())
		{
			throw new InvalidDocumentStateException(this, "not a writable copy");
		}

		if (isDeleted())
		{
			throw new DocumentNotFoundException(getDocumentPath());
		}
	}

	/**
	 * NOTE: API method, don't call it directly
	 */
	public void refreshFromSupplier(final DocumentValuesSupplier documentValuesSupplier)
	{
		initializeFields(FieldInitializationMode.Refresh, documentValuesSupplier);
	}

	@Override
	public String toString()
	{
		// NOTE: keep it short

		final Document parentDocument = getParentDocument();
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", entityDescriptor.getTableNameOrNull())
				.add("parentId", parentDocument == null ? null : parentDocument.getDocumentId())
				.add("id", getDocumentIdOrNull()) // avoid NPE
				.add("NEW", _new ? Boolean.TRUE : null)
				.add("windowNo", windowNo)
				.add("writable", _writable)
				.add("valid", _valid)
				.add("validOnCheckout", _validOnCheckout)
				.add("saveStatus", _saveStatus)
				.add("saveStatusOnCheckout", _saveStatusOnCheckout)
				// .add("fields", fieldsByName.values()) // skip because it's too long
				.toString();
	}

	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	DetailId getDetailId()
	{
		return documentPath.getDetailId();
	}

	public Properties getCtx()
	{
		// make sure that InterfaceWrapperHelper.newInstance(Class<T>, Object, boolean useClientOrgFromProvider) works
		// (still, not sure if this is sufficient for *all* cases)
		final Properties result = Env.copyCtx(Env.getCtx());
		if (getClientId() != null)
		{
			Env.setClientId(result, getClientId());
		}
		if (getOrgId() != null)
		{
			Env.setOrgId(result, getOrgId());
		}
		return result;
	}

	public int getWindowNo()
	{
		return windowNo;
	}

	private DocumentsRepository getDocumentRepository()
	{
		return entityDescriptor.getDataBinding().getDocumentsRepository();
	}

	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}

	public IDocumentChangesCollector getChangesCollector()
	{
		return changesCollector;
	}

	/**
	 * @return parent document or null
	 */
	public Document getParentDocument()
	{
		return _parentDocument;
	}

	/* package */ IDocumentEvaluatee getParentDocumentEvaluateeOrNull()
	{
		if (_shadowParentEvaluatee != null)
		{
			return _shadowParentEvaluatee;
		}

		final Document parentDocument = getParentDocument();
		if (parentDocument != null)
		{
			return parentDocument.asEvaluatee();
		}

		return null;
	}

	/**
	 * Sets a {@link DocumentEvaluatee} which will be used as a parent evaluatee for {@link #asEvaluatee()}.
	 * <p>
	 * NOTE: this shadow evaluatee is not persisted and is discarded on {@link #copy(Document, CopyMode)}.
	 */
	public void setShadowParentDocumentEvaluatee(final IDocumentEvaluatee shadowParentDocumentEvaluatee)
	{
		if (getParentDocument() != null)
		{
			throw new IllegalStateException("Cannot set a shadow parent DocumentEvaluatee when document has a parent: " + this);
		}
		_shadowParentEvaluatee = shadowParentDocumentEvaluatee;
	}

	public Document getRootDocument()
	{
		Document parent = getParentDocument();
		if (parent == null)
		{
			return this;
		}

		while (parent.getParentDocument() != null)
		{
			parent = parent.getParentDocument();
		}

		return parent;
	}

	public boolean isRootDocument()
	{
		return getParentDocument() == null;
	}

	private Collection<IDocumentField> getFields()
	{
		return fieldsByName.values();
	}

	public Collection<IDocumentFieldView> getFieldViews()
	{
		final Collection<IDocumentField> documentFields = fieldsByName.values();
		return ImmutableList.copyOf(documentFields);
	}

	public Set<String> getFieldNames()
	{
		return fieldsByName.keySet();
	}

	public boolean hasField(final String fieldName)
	{
		return fieldsByName.containsKey(fieldName);
	}

	/**
	 * @return field; never returns null
	 */
	private IDocumentField getField(final String fieldName)
	{
		final IDocumentField documentField = getFieldOrNull(fieldName);
		if (documentField == null)
		{
			throw new DocumentFieldNotFoundException(this, fieldName);
		}
		return documentField;
	}

	public IDocumentFieldView getFieldView(final String fieldName)
	{
		return getField(fieldName);
	}

	private IDocumentField getFieldOrNull(final String fieldName)
	{
		final IDocumentField documentField = fieldsByName.get(fieldName);
		return documentField;
	}

	public IDocumentFieldView getFieldViewOrNull(final String fieldName)
	{
		return getFieldOrNull(fieldName);
	}

	private IDocumentFieldView getFieldUpToRootOrNull(final String fieldName)
	{
		Document document = this;
		while (document != null)
		{
			final IDocumentFieldView field = document.getFieldOrNull(fieldName);
			if (field != null)
			{
				return field;
			}

			document = document.getParentDocument();
		}

		return null;
	}

	public int getDocumentIdAsInt()
	{
		return getDocumentId().toInt();
	}

	public DocumentId getDocumentId()
	{
		final DocumentId documentIdOrNull = getDocumentIdOrNull();
		return Check.assumeNotNull(documentIdOrNull, "This instance needs to have a DocumentId; this={}", this);
	}

	private DocumentId getDocumentIdOrNull()
	{
		// TODO handle NO ID field or composed PK
		if (idFields.size() != 1)
		{
			// Get it from document path.
			// This will cover the case of missing ID which was somehow generated internally
			if (getParentDocument() == null)
			{
				return getDocumentPath().getDocumentId();
			}
			else
			{
				return getDocumentPath().getSingleRowId();
			}
		}
		final Object idObj = idFields.get(0).getValue();
		return DocumentId.ofObjectOrNull(idObj);
	}

	public Object getDocumentIdAsJson()
	{
		final DocumentId documentId = getDocumentId();
		if (documentId.isInt())
		{
			return documentId.toInt();
		}
		return documentId.toString();
	}

	public boolean isNew()
	{
		return _new;
	}

	// TODO: make this method private/package
	public void markAsNotNew()
	{
		_new = false;
	}

	/* package */ void markAsDeleted()
	{
		_deleted = true;
		changesCollector.collectDeleted(getDocumentPath());
	}

	/* package */ boolean isDeleted()
	{
		return _deleted;
	}

	public DocumentValidStatus getValidStatus()
	{
		return _valid;
	}

	private DocumentValidStatus setValidStatusAndReturn(@NonNull final DocumentValidStatus valid, final OnValidStatusChanged onValidStatusChanged)
	{
		// Don't check if changed because we want ALWAYS to collect the valid status
		// final DocumentValidStatus validOld = _valid;
		// if (Objects.equals(validOld, valid))
		// {
		// return validOld; // no change
		// }

		_valid = valid;
		if (isInitializing())
		{
			_validOnCheckout = valid;
		}

		if (!isInitializingNewDocument() && !Objects.equals(valid, _validOnCheckout))
		{
			changesCollector.collectDocumentValidStatusChanged(getDocumentPath(), valid);
		}

		if (!valid.isValid())
		{
			onValidStatusChanged.onInvalidStatus(this, valid);
		}

		return valid;
	}

	public DocumentSaveStatus getSaveStatus()
	{
		return _saveStatus;
	}

	private DocumentSaveStatus setSaveStatusAndReturn(@NonNull final DocumentSaveStatus saveStatus)
	{
		_saveStatus = saveStatus;
		final DocumentSaveStatus saveStatusOnCheckoutOld = _saveStatusOnCheckout;
		if (isInitializing())
		{
			_saveStatusOnCheckout = saveStatus;
		}

		if (!isInitializingNewDocument() && !NullDocumentChangesCollector.isNull(changesCollector) && !Objects.equals(saveStatus, saveStatusOnCheckoutOld))
		{
			changesCollector.collectDocumentSaveStatusChanged(getDocumentPath(), saveStatus);
		}

		return _saveStatus;
	}

	public IDocumentEvaluatee asEvaluatee()
	{
		if (_evaluatee == null)
		{
			_evaluatee = new DocumentEvaluatee(this);
		}
		return _evaluatee;
	}

	/**
	 * Similar with {@link #setValue(String, Object, ReasonSupplier)}
	 * but this method is also
	 * <li>checking if we are allowed to change that field (if <code>ignoreReadonlyFlag</code> is false)
	 * <li>trigger document processing if <code>fieldName</code> is {@link WindowConstants#FIELDNAME_DocAction}.
	 */
	public void processValueChange(
			@NonNull final String fieldName,
			@Nullable final Object value,
			@Nullable final ReasonSupplier reason,
			@NonNull final DocumentFieldLogicExpressionResultRevaluator readonlyRevaluator)
			throws DocumentFieldReadonlyException
	{
		final IDocumentField documentField = getField(fieldName);

		if (readonlyRevaluator.isReadonly(documentField))
		{
			throw new DocumentFieldReadonlyException(fieldName, value);
		}

		setValue(documentField, value, reason);

		// FIXME: hardcoded DocAction processing
		if (WindowConstants.FIELDNAME_DocAction.equals(fieldName)
				&& DocumentType.Window.equals(getDocumentPath().getDocumentType()))
		{
			processDocAction();
		}
	}

	public void processValueChanges(
			@NonNull final List<JSONDocumentChangedEvent> events,
			@Nullable final ReasonSupplier reason) throws DocumentFieldReadonlyException
	{
		processValueChanges(events, reason, DocumentFieldLogicExpressionResultRevaluator.DEFAULT);
	}

	public void processValueChanges(
			@NonNull final List<JSONDocumentChangedEvent> events,
			@Nullable final ReasonSupplier reason,
			@NonNull final DocumentFieldLogicExpressionResultRevaluator readonlyRevaluator) throws DocumentFieldReadonlyException
	{
		for (final JSONDocumentChangedEvent event : events)
		{
			if (JSONDocumentChangedEvent.JSONOperation.replace == event.getOperation())
			{
				processValueChange(event.getPath(), event.getValue(), reason, readonlyRevaluator);
			}
			else
			{
				throw new IllegalArgumentException("Unknown operation: " + event);
			}
		}
	}

	private void processDocAction()
	{
		assertWritable();

		final IDocumentField docActionField = getField(WindowConstants.FIELDNAME_DocAction);

		//
		// Make sure it's saved
		saveIfValidAndHasChanges();
		if (hasChangesRecursivelly())
		{
			final String docAction = null; // not relevant
			throw new DocumentProcessingException("Not all changes could be saved", this, docAction);
		}

		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		//
		// Actually process the document
		// TODO: trigger the document workflow instead!
		final String docAction = docActionField.getValueAs(StringLookupValue.class).getIdAsString();
		final ButtonFieldActionDescriptor buttonActionDescriptor = docActionField.getDescriptor().getButtonActionDescriptor();
		if (buttonActionDescriptor != null)
		{
			final IDocument workflowDocument = documentBL.getDocument(this);
			final ProcessId workflowStarterProcessId = buttonActionDescriptor.getProcessId();
			ProcessInfo.builder()
					.setAD_Process_ID(workflowStarterProcessId.toAdProcessId())
					.setRecord(workflowDocument.toTableRecordReference())
					.buildAndPrepareExecution()
					.onErrorThrowException()
					.executeSync();
		}
		else
		{
			if (documentBL.getDocumentOrNull(this) != null)
			{
				documentBL.processEx(this, docAction, null);
			}
		}

		//
		// Refresh it
		// and also mark all included documents as stale because it might be that processing add/removed/changed some data in included documents too
		refreshFromRepository();
		for (final IIncludedDocumentsCollection includedDocumentsPerDetail : includedDocuments.values())
		{
			includedDocumentsPerDetail.markStaleAll();
		}
	}

	/* package */void setValue(final String fieldName, final Object value, final ReasonSupplier reason)
	{
		final IDocumentField documentField = getField(fieldName);
		setValue(documentField, value, reason);
	}

	private void setValue(final IDocumentField documentField, final Object value, final ReasonSupplier reason)
	{
		assertWritable();

		final Object valueOld = documentField.getValue();
		documentField.setValue(value, changesCollector);

		// Check if changed. If not, stop here.
		final Object valueNew = documentField.getValue();
		if (DataTypes.equals(valueOld, valueNew))
		{
			return;
		}

		// collect changed value
		changesCollector.collectValueChanged(documentField, reason != null ? reason : REASON_Value_DirectSetOnDocument);

		// Update all dependencies
		updateFieldsWhichDependsOn(documentField.getFieldName());

		// Callouts
		fieldCalloutExecutor.execute(documentField.asCalloutField());

		// Notify parent that one of it's children was changed
		if (!isRootDocument() && hasChanges())
		{
			getParentDocument().getIncludedDocumentsCollection(getDetailId()).onChildChanged(this);
		}
	}

	private void executeAllFieldCallouts()
	{
		fieldCalloutExecutor.executeAll((fieldName) -> {
			final IDocumentField documentField = getFieldOrNull(fieldName);
			if (documentField == null)
			{
				return null;
			}

			// Skip button callouts because it's expected to execute those callouts ONLY when the button is pressed
			final DocumentFieldWidgetType widgetType = documentField.getWidgetType();
			if (widgetType.isButton())
			{
				return null;
			}

			return documentField.asCalloutField();
		});
	}

	/**
	 * Updates field status flags for all document level and field level dependencies (i.e.Mandatory, ReadOnly, Displayed etc)
	 */
	private void updateAllDependencies()
	{
		logger.trace("Updating all dependencies for {}", this);

		final String triggeringFieldName = null; // N/A

		// Document level properties (e.g. docuemnt readonly)
		for (final String documentFieldName : DocumentFieldDependencyMap.DOCUMENT_ALL_FIELDS)
		{
			for (final DependencyType triggeringDependencyType : DocumentFieldDependencyMap.DEPENDENCYTYPES_DocumentLevel)
			{
				updateOnDependencyChanged(documentFieldName, null, triggeringFieldName, triggeringDependencyType);
			}
		}

		// Fields
		for (final IDocumentField documentField : getFields())
		{
			for (final DependencyType triggeringDependencyType : DocumentFieldDependencyMap.DEPENDENCYTYPES_FieldLevel)
			{
				updateOnDependencyChanged(documentField.getFieldName(), documentField, triggeringFieldName, triggeringDependencyType);
			}
		}
	}

	DocumentReadonly getReadonly()
	{
		return readonly;
	}

	private void updateReadonlyAndPropagate(final ReasonSupplier reason)
	{
		final DocumentReadonly readonlyOld = this.readonly;
		final DocumentReadonly readonlyNew = computeReadonly();
		if (Objects.equals(readonlyOld, readonlyNew))
		{
			return;
		}

		this.readonly = readonlyNew;

		getFields().forEach(documentField -> updateFieldReadOnlyAndCollect(documentField, reason));
	}

	@NonNull
	private DocumentReadonly computeReadonly()
	{
		return DocumentReadonly.builder()
				.parentActive(parentReadonly.isActive()).active(isActive())
				.processed(parentReadonly.isProcessed() || isProcessed())
				.processing(parentReadonly.isProcessing() || isProcessing())
				.parentEnforcingReadOnly(parentReadonly.computeForceReadOnlyChildDocuments())
				.fieldsReadonly(ExtendedMemorizingSupplier.of(this::computeFieldsReadOnly))
				.build();
	}

	@NonNull
	private ReadOnlyInfo computeFieldsReadOnly()
	{
		final boolean isReadOnlyLogicTrue = computeDefaultFieldsReadOnly().booleanValue();
		if (isReadOnlyLogicTrue)
		{
			return ReadOnlyInfo.TRUE;
		}

		if(isFieldsReadOnlyInUI())
		{
			return ReadOnlyInfo.TRUE;
		}

		final TableRecordReference recordReference = this.getTableRecordReference().orElse(null);
		if (recordReference == null)
		{
			return ReadOnlyInfo.of(BooleanWithReason.FALSE);
		}

		return getEntityDescriptor()
				.getDocumentDecorators()
				.stream()
				.map(documentDecorator -> documentDecorator.isReadOnly(recordReference))
				.filter(ReadOnlyInfo::isReadOnly)
				.findFirst()
				.orElse(ReadOnlyInfo.FALSE);
	}

	@NonNull
	private LogicExpressionResult computeDefaultFieldsReadOnly()
	{
		final ILogicExpression allFieldsReadonlyLogic = getEntityDescriptor().getReadonlyLogic();

		final LogicExpressionResult allFieldsReadonly;
		try
		{
			return allFieldsReadonlyLogic.evaluateToResult(asEvaluatee(), OnVariableNotFound.Fail);
		}
		catch (final Exception e)
		{
			allFieldsReadonly = LogicExpressionResult.FALSE;
			logger.warn("Failed evaluating entity readonly logic {} for {}. Considering {}", allFieldsReadonlyLogic, this, allFieldsReadonly, e);
		}

		return allFieldsReadonly;
	}

	private void updateFieldReadOnlyAndCollect(final IDocumentField documentField, final ReasonSupplier reason)
	{
		final LogicExpressionResult readonlyOld = documentField.getReadonly();
		final LogicExpressionResult readonlyNew = computeFieldReadOnly(documentField);
		if (readonlyNew != null)
		{
			documentField.setReadonly(readonlyNew);
			changesCollector.collectReadonlyIfChanged(documentField, readonlyOld, reason);
		}
	}

	@Nullable
	private LogicExpressionResult computeFieldReadOnly(final IDocumentField documentField)
	{
		// Check document's readonly logic
		final DocumentReadonly documentReadonlyLogic = getReadonly();
		final BooleanWithReason isReadOnly = documentReadonlyLogic.computeFieldReadonly(documentField.getFieldName(), documentField.isAlwaysUpdateable());

		if (isReadOnly.isTrue())
		{
			if (WindowConstants.FIELDNAME_DocumentSummary.equals(documentField.getFieldName()))
			{
				return new LogicExpressionResultWithReason(LogicExpressionResult.TRUE, isReadOnly.getReason());
			}

			return LogicExpressionResult.TRUE;
		}

		// Check field's readonly logic
		final ILogicExpression fieldReadonlyLogic = documentField.getDescriptor().getReadonlyLogic();
		try
		{
			final LogicExpressionResult readonly = fieldReadonlyLogic.evaluateToResult(asEvaluatee(), OnVariableNotFound.Fail);
			return readonly;
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating readonly logic {} for {}. Preserving {}", fieldReadonlyLogic, documentField, documentField.getReadonly(), e);
			return null;
		}

	}

	private void updateFieldDisplayed(final IDocumentField documentField)
	{
		LogicExpressionResult displayed = LogicExpressionResult.FALSE; // default false, i.e. not displayed
		final ILogicExpression displayLogic = documentField.getDescriptor().getDisplayLogic();
		try
		{
			displayed = displayLogic.evaluateToResult(asEvaluatee(), OnVariableNotFound.Fail);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating display logic {} for {}. Preserving {}", displayLogic, documentField, documentField.getDisplayed(), e);
			displayed = LogicExpressionResult.FALSE;
		}

		documentField.setDisplayed(displayed);
	}

	private void updateFieldsWhichDependsOn(final String triggeringFieldName)
	{
		final DocumentFieldDependencyMap dependencies = getEntityDescriptor().getDependencies();
		dependencies.consumeForChangedFieldName(triggeringFieldName, (dependentFieldName, dependencyType) -> {
			final IDocumentField dependentField = getFieldOrNull(dependentFieldName);
			if (dependentField == null)
			{
				// shall not happen
				logger.warn("Skip setting dependent propery {} because field is missing", dependentFieldName);
				return;
			}

			updateOnDependencyChanged(dependentFieldName, dependentField, triggeringFieldName, dependencyType);
		});
	}

	/**
	 * Updates document or fields characteristics (e.g. readonly, mandatory, displayed, lookupValuesStaled etc).
	 *
	 * @param triggeringFieldName optional field name which triggered this update
	 */
	private void updateOnDependencyChanged(
			final String propertyName,
			final IDocumentField documentField,
			final String triggeringFieldName,
			final DependencyType triggeringDependencyType)
	{
		final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + triggeringDependencyType;

		if (DependencyType.DocumentReadonlyLogic == triggeringDependencyType)
		{
			if (DocumentFieldDependencyMap.DOCUMENT_Readonly.equals(propertyName))
			{
				updateReadonlyAndPropagate(reason);
			}
		}
		else if (DependencyType.ReadonlyLogic == triggeringDependencyType)
		{
			updateFieldReadOnlyAndCollect(documentField, reason);
		}
		else if (DependencyType.MandatoryLogic == triggeringDependencyType)
		{
			final LogicExpressionResult valueOld = documentField.getMandatory();
			final ILogicExpression mandatoryLogic = documentField.getDescriptor().getMandatoryLogic();
			try
			{
				final LogicExpressionResult mandatory = mandatoryLogic.evaluateToResult(asEvaluatee(), OnVariableNotFound.Fail);
				documentField.setMandatory(mandatory, changesCollector);
			}
			catch (final Exception e)
			{
				logger.warn("Failed evaluating mandatory logic {} for {}. Preserving {}", mandatoryLogic, documentField, documentField.getMandatory(), e);
			}

			changesCollector.collectMandatoryIfChanged(documentField, valueOld, reason);
		}
		else if (DependencyType.DisplayLogic == triggeringDependencyType)
		{
			final LogicExpressionResult valueOld = documentField.getDisplayed();
			updateFieldDisplayed(documentField);

			changesCollector.collectDisplayedIfChanged(documentField, valueOld, reason);
		}
		else if (DependencyType.LookupValues == triggeringDependencyType)
		{
			final boolean lookupValuesStaledOld = documentField.isLookupValuesStale();
			final boolean lookupValuesStaled = documentField.setLookupValuesStaled(triggeringFieldName);
			if (lookupValuesStaled && !lookupValuesStaledOld)
			{
				// https://github.com/metasfresh/metasfresh-webui-api/issues/551 check if we can leave the old value as it is
				final Object valueOld = documentField.getValue();
				if (valueOld != null)
				{
					final boolean currentValueStillValid = documentField.getLookupValues() // because we did setLookupValuesStaled(), this causes a reload
							.stream()
							.anyMatch(value -> Objects.equals(value, valueOld)); // check if the current value is still value after we reloaded the list
					if (!currentValueStillValid)
					{
						documentField.setValue(null, changesCollector);
						changesCollector.collectValueIfChanged(documentField, valueOld, reason);
					}
				}

				// https://github.com/metasfresh/metasfresh-webui-frontend/issues/1165 - the value was not stale, but now it is => notify the frontend so it shall invalidate its cache
				changesCollector.collectLookupValuesStaled(documentField, reason);
			}
		}
		else if (DependencyType.FieldValue == triggeringDependencyType)
		{
			final IDocumentFieldValueProvider valueProvider = documentField.getDescriptor().getVirtualFieldValueProvider().orElse(null);
			if (valueProvider != null)
			{
				try
				{
					final Object valueOld = documentField.getValue();
					final Object valueNew = valueProvider.calculateValue(this);

					documentField.setInitialValue(valueNew, changesCollector);
					documentField.setValue(valueNew, changesCollector);

					changesCollector.collectValueIfChanged(documentField, valueOld, reason);
				}
				catch (final Exception ex)
				{
					logger.warn("Failed updating virtual field {} for {}", documentField, this, ex);
				}
			}
		}
		else
		{
			new AdempiereException("Unknown dependency type: " + triggeringDependencyType)
					.throwIfDeveloperModeOrLogWarningElse(logger);
		}
	}

	public LookupValuesList getFieldLookupValues(final String fieldName)
	{
		return getField(fieldName).getLookupValues();
	}

	public LookupValuesPage getFieldLookupValuesForQuery(final String fieldName, final String query)
	{
		return getField(fieldName).getLookupValuesForQuery(query);
	}

	public Optional<LookupValue> getLookupValueById(@NonNull final String fieldName, @NonNull final RepoIdAware id)
	{
		return getField(fieldName).getLookupValueById(id);
	}

	public Optional<Document> getIncludedDocument(final DetailId detailId, final DocumentId rowId)
	{
		final IIncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.getDocumentById(rowId);
	}

	@NonNull
	public OrderedDocumentsList getIncludedDocuments(@NonNull final DetailId detailId, @Nullable final DocumentQueryOrderByList orderBys)
	{
		final IIncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.getDocuments(orderBys);
	}

	public OrderedDocumentsList getIncludedDocuments(final DetailId detailId, final DocumentIdsSelection documentIds)
	{
		if (documentIds.isEmpty())
		{
			return OrderedDocumentsList.newEmpty();
		}

		final IIncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.getDocumentsByIds(documentIds);
	}

	public void assertNewDocumentAllowed(final DetailId detailId)
	{
		getIncludedDocumentsCollection(detailId).assertNewDocumentAllowed();
	}

	/* package */IIncludedDocumentsCollection getIncludedDocumentsCollection(@NonNull final DetailId detailId)
	{
		final IIncludedDocumentsCollection includedDocumentsForDetailId = includedDocuments.get(detailId);
		if (includedDocumentsForDetailId == null)
		{
			throw new AdempiereException("detailId '" + detailId + "' not found for " + this);
		}
		return includedDocumentsForDetailId;
	}

	public Collection<IIncludedDocumentsCollection> getIncludedDocumentsCollections()
	{
		return includedDocuments.values();
	}

	/* package */ Document createIncludedDocument(final DetailId detailId)
	{
		final IIncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.createNewDocument();
	}

	/* package */ void deleteIncludedDocuments(final DetailId detailId, final DocumentIdsSelection rowIds)
	{
		final BooleanWithReason isDeleteSubDocumentsForbidden = isDeleteSubDocumentsForbidden();
		if (isDeleteSubDocumentsForbidden.isTrue())
		{
			throw new AdempiereException(isDeleteSubDocumentsForbidden.getReason()).markAsUserValidationError();
		}

		final IIncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		includedDocuments.deleteDocuments(rowIds);
		checkAndGetValidStatus();
	}

	/* package */ICalloutExecutor getFieldCalloutExecutor()
	{
		return fieldCalloutExecutor;
	}

	/* package */ boolean isProcessed()
	{
		final IDocumentFieldView isActiveField = getFieldUpToRootOrNull(WindowConstants.FIELDNAME_Processed);
		return isActiveField != null && isActiveField.getValueAsBoolean(); // not processed if field missing
	}

	/* package */ boolean isProcessing()
	{
		final IDocumentFieldView isActiveField = getFieldUpToRootOrNull(WindowConstants.FIELDNAME_Processing);
		return isActiveField != null && isActiveField.getValueAsBoolean(); // not processed if field missing
	}

	/* package */ boolean isActive()
	{
		final IDocumentFieldView isActiveField = getFieldUpToRootOrNull(WindowConstants.FIELDNAME_IsActive);
		return isActiveField == null || isActiveField.getValueAsBoolean(); // active if field not found (shall not happen)
	}

	/* package */ boolean isFieldsReadOnlyInUI()
	{
		final IDocumentFieldView isFieldsReadOnlyInUI = getFieldUpToRootOrNull(WindowConstants.FIELDNAME_IsFieldsReadOnlyInUI);
		return isFieldsReadOnlyInUI != null && isFieldsReadOnlyInUI.getValueAsBoolean();
	}

	/* package */ void setParentReadonly(@NonNull final DocumentReadonly parentReadonly)
	{
		final DocumentReadonly parentReadonlyOld = this.parentReadonly;
		this.parentReadonly = parentReadonly;

		if (!Objects.equals(parentReadonlyOld, parentReadonly))
		{
			updateReadonlyAndPropagate(() -> "parent readonly state changed");
			updateIncludedDetailsStatus();
		}
	}

	/**
	 * Set Dynamic Attribute.
	 * A dynamic attribute is an attribute that is not stored in database and is kept as long as this this instance is not destroyed.
	 *
	 */
	public Object setDynAttribute(final String name, final Object value)
	{
		assertWritable();
		return setDynAttributeNoCheck(name, value);
	}

	private Object setDynAttributeNoCheck(final String name, final Object value)
	{
		Check.assumeNotEmpty(name, "name not empty");

		if (_dynAttributes == null)
		{
			_dynAttributes = new HashMap<>();
		}
		final Object valueOld = _dynAttributes.put(name, value);

		logger.trace("Changed document dyn attribute {}'s value: {} -> {}", name, valueOld, value);
		return valueOld;
	}

	/**
	 * Get Dynamic Attribute
	 *
	 * @return attribute value or null if not found
	 */
	public <T> T getDynAttribute(final String name)
	{
		final T defaultValue = null;
		return getDynAttribute(name, defaultValue);
	}

	/**
	 * Get Dynamic Attribute
	 *
	 * @return attribute value or <code>defaultValue</code> if not found
	 */
	public <T> T getDynAttribute(final String name, final T defaultValue)
	{
		if (_dynAttributes == null)
		{
			return defaultValue;
		}

		final Object valueObj = _dynAttributes.get(name);
		if (valueObj == null)
		{
			return defaultValue;
		}

		@SuppressWarnings("unchecked") final T value = (T)valueObj;
		return value;
	}

	public boolean hasDynAttribute(final String name)
	{
		final Map<String, Object> dynAttributes = _dynAttributes;
		return dynAttributes != null && dynAttributes.get(name) != null;
	}

	Set<String> getAvailableDynAttributes()
	{
		final Map<String, Object> dynAttributes = _dynAttributes;
		if (dynAttributes == null)
		{
			return ImmutableSet.of();
		}
		return ImmutableSet.copyOf(dynAttributes.keySet());
	}

	/* package */ interface OnValidStatusChanged
	{
		void onInvalidStatus(Document document, DocumentValidStatus invalidStatus);

		OnValidStatusChanged DO_NOTHING = (document, invalidStatus) -> {
		};

		OnValidStatusChanged MARK_NOT_SAVED = (document, invalidStatus) -> {
			document.setSaveStatusAndReturn(DocumentSaveStatus.notSaved(invalidStatus));
		};

	}

	/**
	 * Checks document's valid status, sets it and returns it.
	 */
	public DocumentValidStatus checkAndGetValidStatus()
	{
		return checkAndGetValidStatus(OnValidStatusChanged.DO_NOTHING);
	}

	/**
	 * Checks document's valid status, sets it and returns it.
	 *
	 * @param onValidStatusChanged callback to be called when the valid state of this document or of any of it's included documents was changed
	 */
	/* package */ DocumentValidStatus checkAndGetValidStatus(final OnValidStatusChanged onValidStatusChanged)
	{
		//
		// Check document fields
		for (final IDocumentField documentField : getFields())
		{
			// skip virtual fields, those does not matter
			if (documentField.isReadonlyVirtualField())
			{
				continue;
			}

			final DocumentValidStatus validState = documentField.updateStatusIfInitialInvalidAndGet(changesCollector);
			if (!validState.isValid())
			{
				logger.trace("Considering document invalid because {} is not valid: {}", documentField, validState);
				return setValidStatusAndReturn(validState, onValidStatusChanged);
			}
		}

		//
		// Check included documents
		for (final IIncludedDocumentsCollection includedDocumentsPerDetailId : includedDocuments.values())
		{
			final DocumentValidStatus validState = includedDocumentsPerDetailId.checkAndGetValidStatus(onValidStatusChanged);
			if (!validState.isValid())
			{
				logger.trace("Considering document invalid because {} is not valid: {}", includedDocumentsPerDetailId, validState);
				return setValidStatusAndReturn(validState, onValidStatusChanged);
			}
		}

		return setValidStatusAndReturn(DocumentValidStatus.documentValid(), onValidStatusChanged); // valid
	}

	/**
	 * Checks if this document has changes.
	 * NOTE: it's not checking the included documents.
	 *
	 * @return true if it has changes.
	 */
	private boolean hasChanges()
	{
		// If this is a new document then always consider it as changed
		if (isNew())
		{
			return true;
		}

		boolean changes = false;

		//
		// Check document fields
		for (final IDocumentField documentField : getFields())
		{
			if (documentField.hasChangesToSave())
			{
				logger.trace("Considering document has changes because {} is changed", documentField);
				changes = true;
				break;
			}
		}

		return changes;
	}

	/**
	 * Checks if this document or any of it's included documents has changes.
	 *
	 * @return true if it this document or any of it's included documents has changes.
	 */
	public boolean hasChangesRecursivelly()
	{
		//
		// Check this document
		if (hasChanges())
		{
			return true;
		}

		//
		// Check included documents
		for (final IIncludedDocumentsCollection includedDocumentsPerDetailId : includedDocuments.values())
		{
			if (includedDocumentsPerDetailId.hasChangesRecursivelly())
			{
				return true;
			}
		}

		return false; // no changes

	}

	/* package */void updateIncludedDetailsStatus()
	{
		includedDocuments.values().forEach(IIncludedDocumentsCollection::updateStatusFromParent);
	}

	public DocumentSaveStatus saveIfValidAndHasChanges()
	{
		//
		// Update parent link field
		// TODO: i think this is no longer needed since we preallocate the IDs
		if (parentLinkField != null)
		{
			final Document parentDocument = getParentDocument();
			if (parentDocument != null)
			{
				final int parentLinkValueOld = parentLinkField.getValueAsInt(-1);
				final int parentLinkValueNew = parentDocument.getDocumentIdAsInt();
				if (parentLinkValueOld != parentLinkValueNew)
				{
					logger.warn("Updating parent link value: {} -> {}", parentLinkValueOld, parentLinkValueNew);
					setValue(parentLinkField, parentLinkValueNew, REASON_Value_ParentLinkUpdateOnSave);
				}
			}
		}

		//
		// Check if valid for saving
		final DocumentValidStatus validState = checkAndGetValidStatus(OnValidStatusChanged.MARK_NOT_SAVED);
		// FIXME: i think this is no longer needed because we use OnValidStatusChanged.MARK_NOT_SAVED
		if (!validState.isValid())
		{
			logger.debug("Skip saving because document {} is not valid: {}", this, validState);
			return setSaveStatusAndReturn(DocumentSaveStatus.notSaved(validState));
		}

		//
		// Try saving it
		try
		{
			return saveIfHasChanges();
		}
		catch (final Exception saveEx)
		{
			// NOTE: usually if we do the right checks we shall not get to this
			// logger.warn("Failed saving document, but IGNORED: {}", this, saveEx);
			setValidStatusAndReturn(DocumentValidStatus.invalid(saveEx), OnValidStatusChanged.DO_NOTHING);
			return setSaveStatusAndReturn(DocumentSaveStatus.error(saveEx));
		}
	}

	/* package */DocumentSaveStatus saveIfHasChanges() throws RuntimeException
	{
		boolean wasNew = isNew();

		//
		// Save this document
		boolean deleted = false;
		if (hasChanges())
		{
			try
			{
				final SaveResult saveResult = getDocumentRepository().save(this);
				if (saveResult == SaveResult.DELETED)
				{
					deleted = true;
				}

				documentCallout.onSave(asCalloutRecord());
				logger.debug("Document saved: {}", this);
			}
			catch (Exception ex)
			{
				return setSaveStatusAndReturn(DocumentSaveStatus.error(ex)).throwIfError();
			}
		}
		else
		{
			logger.debug("Skip saving because document has NO change: {}", this);
		}

		// Update "wasNew" flag: true only if the document was new before and we just save it now.
		wasNew = wasNew && !isNew();

		//
		// Try also saving the included documents
		for (final IIncludedDocumentsCollection includedDocumentsForDetailId : includedDocuments.values())
		{
			includedDocumentsForDetailId.saveIfHasChanges();

			// If document was new we need to invalidate all included documents.
			// NOTE: Usually this has no real effect besides some corner cases like BPartner window where Vendor and Customer tabs are referencing exactly the same record as the header.
			if (wasNew)
			{
				includedDocumentsForDetailId.markStaleAll();
			}
		}

		return setSaveStatusAndReturn(deleted ? DocumentSaveStatus.deleted() : DocumentSaveStatus.saved());
	}

	/* package */void deleteFromRepository()
	{
		getDocumentRepository().delete(this);
		markAsDeleted();
	}

	/* package */void refreshFromRepository()
	{
		getDocumentRepository().refresh(this);
	}

	/* package */ Document refreshFromRepositoryIfStaled()
	{
		if (getEntityDescriptor().getDataBinding().isVersioningSupported())
		{
			if (getStale().checkStaled())
			{
				refreshFromRepository();
			}
		}

		return this;
	}

	public ICalloutRecord asCalloutRecord()
	{
		if (_calloutRecord == null)
		{
			_calloutRecord = new DocumentAsCalloutRecord(this);
		}
		return _calloutRecord;
	}

	private DocumentStaleState getStale()
	{
		return _staleStatus;
	}

	/* package */boolean isStaled()
	{
		return _staleStatus.isStaled();
	}

	public IAutoCloseable lockForReading()
	{
		// assume _lock is not null
		final ReadLock readLock = _lock.readLock();
		logger.debug("Acquiring read lock for {}: {}", this, readLock);
		readLock.lock();
		logger.debug("Acquired read lock for {}: {}", this, readLock);

		return () -> {
			readLock.unlock();
			logger.debug("Released read lock for {}: {}", this, readLock);
		};
	}

	public IAutoCloseable lockForWriting()
	{
		// assume _lock is not null
		final WriteLock writeLock = _lock.writeLock();
		logger.debug("Acquiring write lock for {}: {}", this, writeLock);
		writeLock.lock();
		logger.debug("Acquired write lock for {}: {}", this, writeLock);

		return () -> {
			writeLock.unlock();
			logger.debug("Released write lock for {}: {}", this, writeLock);
		};
	}

	public ClientId getClientId()
	{
		final IDocumentField field = getFieldOrNull(WindowConstants.FIELDNAME_AD_Client_ID);
		return ClientId.ofRepoIdOrNull(field != null ? field.getValueAsInt(-1) : -1);
	}

	public OrgId getOrgId()
	{
		final IDocumentField field = getFieldOrNull(WindowConstants.FIELDNAME_AD_Org_ID);
		return OrgId.ofRepoIdOrNull(field != null ? field.getValueAsInt(-1) : -1);
	}

	public void onChildSaved(final Document document)
	{
		getIncludedDocumentsCollection(document.getDetailId()).onChildSaved(document);
	}

	public Set<DocumentStandardAction> getStandardActions()
	{
		final EnumSet<DocumentStandardAction> standardActions = EnumSet.allOf(DocumentStandardAction.class);

		// Remove Clone action if not supported
		if (!getEntityDescriptor().isCloneEnabled())
		{
			standardActions.remove(DocumentStandardAction.Clone);
		}

		// Remove Print action if document is not printable (https://github.com/metasfresh/metasfresh-webui-api/issues/570)
		if (!getEntityDescriptor().isPrintable())
		{
			standardActions.remove(DocumentStandardAction.Print);
		}

		// Remove letter action if functionality is not enabled (https://github.com/metasfresh/metasfresh-webui-api/issues/178)
		if (!Letters.isEnabled())
		{
			standardActions.remove(DocumentStandardAction.Letter);
		}

		return standardActions;
	}

	@NonNull
	public Optional<TableRecordReference> getTableRecordReference()
	{
		final String tableName = entityDescriptor.getTableNameOrNull();
		final Integer recordId = getDocumentId().isInt() ? getDocumentIdAsInt() : null;

		if (tableName == null || recordId == null)
		{
			return Optional.empty();
		}

		return Optional.of(TableRecordReference.of(tableName, recordId));
	}

	@NonNull
	public BooleanWithReason isDeleteForbidden()
	{
		final TableRecordReference recordReference = getTableRecordReference().orElse(null);

		if (recordReference == null)
		{
			return BooleanWithReason.FALSE;
		}

		return entityDescriptor.getDocumentDecorators()
				.stream()
				.map(decorator -> decorator.isDeleteForbidden(recordReference))
				.filter(BooleanWithReason::isTrue)
				.findFirst()
				.orElse(BooleanWithReason.FALSE);
	}

	@NonNull
	private BooleanWithReason isDeleteSubDocumentsForbidden()
	{
		final TableRecordReference recordReference = getTableRecordReference().orElse(null);

		if (recordReference == null)
		{
			return BooleanWithReason.FALSE;
		}

		return entityDescriptor.getDocumentDecorators()
				.stream()
				.map(decorator -> decorator.isDeleteSubDocumentsForbidden(recordReference))
				.filter(BooleanWithReason::isTrue)
				.findFirst()
				.orElse(BooleanWithReason.FALSE);
	}

	//
	//
	//
	private final class DocumentStaleState
	{
		private boolean staled;
		private String version;

		private DocumentStaleState()
		{
			staled = false; // initially not staled
			version = null; // unknown
		}

		private DocumentStaleState(final DocumentStaleState from)
		{
			staled = from.staled;
			version = from.version;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("staled", staled)
					.add("version", version)
					.add("document", Document.this)
					.toString();
		}

		public boolean isStaled()
		{
			return staled;
		}

		private boolean checkStaled()
		{
			if (staled)
			{
				return true;
			}

			if (isNew())
			{
				return false;
			}

			final String versionNow = getDocumentRepository().retrieveVersion(getEntityDescriptor(), getDocumentIdAsInt());
			if (Objects.equals(version, versionNow))
			{
				return false;
			}

			staled = true;
			return true;
		}

		private void markNotStaled(final String version)
		{
			staled = false;
			this.version = version;
		}
	}

	//
	//
	// Builder
	//
	//

	public static final class Builder
	{
		private final DocumentEntityDescriptor _entityDescriptor;
		private Document _parentDocument;
		private FieldInitializationMode _fieldInitializerMode;
		private DocumentValuesSupplier _documentValuesSupplier;

		private DocumentPath _documentPath;

		private Integer _windowNo;
		private static final AtomicInteger _nextWindowNo = new AtomicInteger(1);
		private IDocumentEvaluatee shadowParentDocumentEvaluatee;

		private IDocumentChangesCollector changesCollector = NullDocumentChangesCollector.instance;

		private Builder(@NonNull final DocumentEntityDescriptor entityDescriptor)
		{
			_entityDescriptor = entityDescriptor;
		}

		public Document build()
		{
			final Document document = new Document(this);

			if (shadowParentDocumentEvaluatee != null)
			{
				document.setShadowParentDocumentEvaluatee(shadowParentDocumentEvaluatee);
			}

			final DocumentEntityDescriptor entityDescriptor = getEntityDescriptor();
			final ITabCallout documentCallout = entityDescriptor.createAndInitializeDocumentCallout(document.asCalloutRecord());
			document.documentCallout = documentCallout;

			//
			// Initialize document fields
			final FieldInitializationMode fieldInitializerMode = getFieldInitializerMode();
			final DocumentValuesSupplier documentValuesSupplierEffective = getDocumentValuesSupplierEffective(document);
			document.initializeFields(fieldInitializerMode, documentValuesSupplierEffective);
			return document;
		}

		private IDocumentChangesCollector getChangesCollector()
		{
			return changesCollector;
		}

		public Builder setChangesCollector(@NonNull final IDocumentChangesCollector changesCollector)
		{
			this.changesCollector = changesCollector;
			return this;
		}

		private DocumentValuesSupplier getDocumentValuesSupplierEffective(final Document document)
		{
			DocumentValuesSupplier documentValuesSupplierEffective = _documentValuesSupplier;
			//
			// Initialize the fields
			if (isNewDocument())
			{
				documentValuesSupplierEffective = new InitialFieldValueSupplier(document, _documentValuesSupplier);
			}

			return documentValuesSupplierEffective;
		}

		private DocumentField buildField(final DocumentFieldDescriptor descriptor, final Document document)
		{
			return new DocumentField(descriptor, document);
		}

		private DocumentEntityDescriptor getEntityDescriptor()
		{
			return _entityDescriptor;
		}

		public Builder setParentDocument(final Document parentDocument)
		{
			_parentDocument = parentDocument;
			return this;
		}

		public Builder setShadowParentDocumentEvaluatee(@Nullable final IDocumentEvaluatee shadowParentDocumentEvaluatee)
		{
			this.shadowParentDocumentEvaluatee = shadowParentDocumentEvaluatee;
			return this;
		}

		public Document initializeAsNewDocument(@NonNull final DocumentValuesSupplier documentValuesSupplier)
		{
			_fieldInitializerMode = FieldInitializationMode.NewDocument;
			_documentValuesSupplier = documentValuesSupplier;
			return build();
		}

		public Document initializeAsNewDocument(final DocumentId newDocumentId, final String version)
		{
			return initializeAsNewDocument(new SimpleDocumentValuesSupplier(newDocumentId, version));
		}

		public Document initializeAsNewDocument(final Supplier<DocumentId> newDocumentIdSupplier, final String version)
		{
			return initializeAsNewDocument(new SimpleDocumentValuesSupplier(newDocumentIdSupplier, version));
		}

		public Document initializeAsNewDocument(final IntSupplier newDocumentIdSupplier, final String version)
		{
			return initializeAsNewDocument(new SimpleDocumentValuesSupplier(DocumentId.supplier(newDocumentIdSupplier), version));
		}

		public Document initializeAsExistingRecord(@NonNull final DocumentValuesSupplier documentValuesSupplier)
		{
			_fieldInitializerMode = FieldInitializationMode.Load;
			_documentValuesSupplier = documentValuesSupplier;

			return build();
		}

		private DocumentId getDocumentId()
		{
			return _documentValuesSupplier.getDocumentId();
		}

		private FieldInitializationMode getFieldInitializerMode()
		{
			return _fieldInitializerMode;
		}

		private boolean isNewDocument()
		{
			return _fieldInitializerMode == FieldInitializationMode.NewDocument;
		}

		private Document getParentDocument()
		{
			return _parentDocument;
		}

		private DocumentPath getDocumentPath()
		{
			if (_documentPath == null)
			{
				final DocumentEntityDescriptor entityDescriptor = getEntityDescriptor();
				final DocumentId documentId = getDocumentId();
				final Document parentDocument = getParentDocument();
				if (parentDocument == null)
				{
					_documentPath = DocumentPath.rootDocumentPath(entityDescriptor.getDocumentType(), entityDescriptor.getDocumentTypeId(), documentId);
				}
				else
				{
					_documentPath = parentDocument.getDocumentPath().createChildPath(entityDescriptor.getDetailId(), documentId);
				}
			}
			return _documentPath;
		}

		private int getWindowNo()
		{
			if (_windowNo == null)
			{
				final Document parentDocument = getParentDocument();
				if (parentDocument == null)
				{
					_windowNo = _nextWindowNo.incrementAndGet();
				}
				else
				{
					_windowNo = parentDocument.getWindowNo();
				}
			}
			return _windowNo;
		}

		private boolean isWritable()
		{
			final Document parentDocument = getParentDocument();
			if (parentDocument == null)
			{
				return isNewDocument();
			}
			else
			{
				return parentDocument.isWritable();
			}
		}

		private ReentrantReadWriteLock createLock()
		{
			// don't create locks for any other entity which is not window
			final DocumentEntityDescriptor entityDescriptor = getEntityDescriptor();
			if (entityDescriptor.getDocumentType() != DocumentType.Window)
			{
				return null;
			}

			//
			final Document parentDocument = getParentDocument();
			if (parentDocument == null)
			{
				return new ReentrantReadWriteLock();
			}
			else
			{
				// don't create lock for included documents
				return null;
			}
		}

	}
}
