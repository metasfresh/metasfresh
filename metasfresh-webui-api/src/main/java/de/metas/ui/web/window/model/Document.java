package de.metas.ui.web.window.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.ui.spi.ExceptionHandledTabCallout;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.document.engine.IDocActionBL;
import de.metas.document.exceptions.DocumentProcessingException;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap.DependencyType;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.exceptions.DocumentFieldNotFoundException;
import de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException;
import de.metas.ui.web.window.exceptions.InvalidDocumentStateException;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentField.FieldInitializationMode;

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

public final class Document
{
	public static final Builder builder(final DocumentEntityDescriptor entityDescriptor)
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
	private final boolean _writable;
	private boolean _initializing = false;
	private DocumentValidStatus _valid = DocumentValidStatus.inititalInvalid();
	private DocumentSaveStatus _saveStatus = DocumentSaveStatus.unknown();
	private final DocumentStaleState _staleStatus;

	//
	// Callouts
	private ITabCallout documentCallout = ITabCallout.NULL; // will be set from builder, after document it's initialized
	private final ICalloutExecutor fieldCalloutExecutor;
	private DocumentAsCalloutRecord _calloutRecord; // lazy

	//
	// Fields
	private final Map<String, IDocumentField> fieldsByName;
	private final IDocumentFieldView idField;
	private final IDocumentField parentLinkField;

	//
	// Parent & children
	private final Document _parentDocument;
	private final Map<DetailId, IncludedDocumentsCollection> includedDocuments;

	//
	// Evaluatee
	private IDocumentEvaluatee _evaluatee; // lazy
	private transient IDocumentEvaluatee _shadowParentEvaluatee;

	//
	// Misc
	private Map<String, Object> _dynAttributes = null; // lazy

	public static interface DocumentValuesSupplier
	{
		Object NO_VALUE = new Object();

		DocumentId getDocumentId();

		String getVersion();

		/**
		 * @param fieldDescriptor
		 * @return initial value or {@link #NO_VALUE} if it cannot provide a value
		 */
		Object getValue(final DocumentFieldDescriptor fieldDescriptor);
	}

	private Document(final Builder builder)
	{
		super();
		entityDescriptor = builder.getEntityDescriptor();
		_parentDocument = builder.getParentDocument();
		documentPath = builder.getDocumentPath();
		windowNo = builder.getWindowNo();
		_writable = builder.isWritable();
		_new = builder.isNewDocument();
		_staleStatus = new DocumentStaleState();

		//
		// Create document fields
		{
			final ImmutableMap.Builder<String, IDocumentField> fieldsBuilder = ImmutableMap.builder();
			IDocumentFieldView idField = null;
			IDocumentField parentLinkField = null;
			for (final DocumentFieldDescriptor fieldDescriptor : entityDescriptor.getFields())
			{
				final String fieldName = fieldDescriptor.getFieldName();
				final IDocumentField field = builder.buildField(fieldDescriptor, this);
				fieldsBuilder.put(fieldName, field);

				if (fieldDescriptor.isKey())
				{
					Check.assumeNull(idField, "Only one ID field shall exist but we found: {}, {}", idField, field); // shall no happen at this level
					idField = field;
				}

				if (fieldDescriptor.isParentLink())
				{
					Check.assumeNull(parentLinkField, "Only one parent link field shall exist but we found: {}, {}", parentLinkField, field); // shall no happen at this level
					parentLinkField = field;
				}
			}
			fieldsByName = fieldsBuilder.build();
			this.idField = idField;
			this.parentLinkField = parentLinkField;
		}

		//
		// Create included documents containers
		{
			final ImmutableMap.Builder<DetailId, IncludedDocumentsCollection> includedDocuments = ImmutableMap.builder();
			for (final DocumentEntityDescriptor includedEntityDescriptor : entityDescriptor.getIncludedEntities())
			{
				final DetailId detailId = includedEntityDescriptor.getDetailId();
				final IncludedDocumentsCollection includedDocumentsForDetailId = new IncludedDocumentsCollection(this, includedEntityDescriptor);
				includedDocuments.put(detailId, includedDocumentsForDetailId);
			}
			this.includedDocuments = includedDocuments.build();
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
				final Optional<Boolean> isSOTrx = entityDescriptor.getIsSOTrx();
				if (isSOTrx.isPresent())
				{
					setDynAttributeNoCheck("IsSOTrx", isSOTrx.get()); // cover the case for FieldName=IsSOTrx, DefaultValue=@IsSOTrx@
				}
				setDynAttributeNoCheck("IsApproved", false); // cover the case for FieldName=IsApproved, DefaultValue=@IsApproved@
			}
		}

		//
		// Done
		logger.trace("Created new document instance: {}", this); // keep it last
	}

	/** copy constructor */
	private Document(final Document from, final Document parentDocumentCopy, final CopyMode copyMode)
	{
		super();
		entityDescriptor = from.entityDescriptor;
		windowNo = from.windowNo;
		_writable = copyMode.isWritable();

		_new = from._new;
		_valid = from._valid;
		_saveStatus = from._saveStatus;
		_staleStatus = new DocumentStaleState(from._staleStatus);

		if (from._parentDocument != null)
		{
			Preconditions.checkNotNull(parentDocumentCopy, "parentDocumentCopy");
			_parentDocument = parentDocumentCopy;
		}
		else
		{
			Preconditions.checkArgument(parentDocumentCopy == null, "parentDocumentCopy shall be null");
			_parentDocument = null;
		}

		documentPath = from.documentPath;

		//
		// Copy document fields
		{
			final ImmutableMap.Builder<String, IDocumentField> fieldsBuilder = ImmutableMap.builder();
			IDocumentFieldView idField = null;
			IDocumentField parentLinkField = null;
			for (final IDocumentField fieldOrig : from.fieldsByName.values())
			{
				final IDocumentField fieldCopy = fieldOrig.copy(this, copyMode);
				final String fieldName = fieldCopy.getFieldName();
				fieldsBuilder.put(fieldName, fieldCopy);

				if (fieldOrig == from.idField)
				{
					idField = fieldCopy;
				}
				if (fieldOrig == from.parentLinkField)
				{
					parentLinkField = fieldCopy;
				}
			}
			fieldsByName = fieldsBuilder.build();
			this.idField = idField;
			this.parentLinkField = parentLinkField;
		}

		//
		// Copy included documents containers
		{
			final ImmutableMap.Builder<DetailId, IncludedDocumentsCollection> includedDocuments = ImmutableMap.builder();
			for (final Map.Entry<DetailId, IncludedDocumentsCollection> e : from.includedDocuments.entrySet())
			{
				final DetailId detailId = e.getKey();
				final IncludedDocumentsCollection includedDocumentsForDetailIdOrig = e.getValue();
				final IncludedDocumentsCollection includedDocumentsForDetailIdCopy = includedDocumentsForDetailIdOrig.copy(this, copyMode);

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

	/* package */boolean isWritable()
	{
		return _writable;
	}

	private final void initializeFields(final FieldInitializationMode mode, final DocumentValuesSupplier documentValuesSupplier)
	{
		logger.trace("Initializing fields: mode={}", mode);

		if (_initializing)
		{
			throw new InvalidDocumentStateException(this, "already initializing");
		}
		_initializing = true;
		try
		{
			for (final IDocumentField documentField : getFields())
			{
				initializeField(documentField, mode, documentValuesSupplier);
			}

			//
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

				updateAllFieldsFlags(Execution.getCurrentDocumentChangesCollector());
			}
			else if (FieldInitializationMode.Load == mode)
			{
				updateAllFieldsFlags(NullDocumentChangesCollector.instance);
			}
			else if (FieldInitializationMode.Refresh == mode)
			{
				documentCallout.onRefresh(asCalloutRecord());

				updateAllFieldsFlags(Execution.getCurrentDocumentChangesCollectorOrNull());
			}
			else
			{
				throw new IllegalArgumentException("Unknown mode: " + mode);
			}

			//
			updateValidIfStaled();
			getStale().markNotStaled(documentValuesSupplier.getVersion());

		}
		finally
		{
			_initializing = false;
		}
	}

	/**
	 * Set field's initial value
	 *
	 * @param documentField
	 * @param mode initialization mode
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
				documentField.setInitialValue(initialValue, mode);
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
				throw Throwables.propagate(e);
			}
		}

		//
		// After field was initialized, based on "mode", trigger events, update other fields etc
		if (FieldInitializationMode.NewDocument == mode)
		{
			// Collect the change event, even if there was no change because we just set the initial value
			final IDocumentChangesCollector documentChangesCollector = Execution.getCurrentDocumentChangesCollector();
			documentChangesCollector.collectValueChanged(documentField, REASON_Value_NewDocument);

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
				final IDocumentChangesCollector eventsCollector = Execution.getCurrentDocumentChangesCollectorOrNull();
				eventsCollector.collectValueIfChanged(documentField, valueOld, REASON_Value_Refreshing);
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
		private final Properties ctx;
		private final DocumentType documentType;
		private final int documentTypeId;
		private final Evaluatee evaluatee;
		private final DocumentId parentDocumentId;

		private InitialFieldValueSupplier(final Document document, final DocumentValuesSupplier parentSupplier)
		{
			super();
			this.parentSupplier = parentSupplier;

			ctx = document.getCtx();

			final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
			documentType = entityDescriptor.getDocumentType();
			documentTypeId = entityDescriptor.getDocumentTypeId();

			evaluatee = document.asEvaluatee();

			final Document parentDocument = document.getParentDocument();
			parentDocumentId = parentDocument == null ? null : parentDocument.getDocumentId();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("type", documentType)
					.add("typeId", documentTypeId)
					.add("evaluatee", evaluatee)
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
				return id == null ? null : id.toInt();
			}

			//
			// Parent link field
			if (fieldDescriptor.isParentLink())
			{
				if (parentDocumentId != null)
				{
					return parentDocumentId.toInt();
				}
			}

			//
			// Default value expression
			final IExpression<?> defaultValueExpression = fieldDescriptor.getDefaultValueExpression().orElse(null);
			if (defaultValueExpression != null)
			{
				final Object value = defaultValueExpression.evaluate(evaluatee, OnVariableNotFound.Fail);

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
			// Window User Preferences
			final String fieldName = fieldDescriptor.getFieldName();
			if (documentType == DocumentType.Window)
			{
				final int adWindowId = documentTypeId;

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

	public static enum CopyMode
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

	public Document copy(final CopyMode copyMode)
	{
		final Document parentDocumentCopy = Document.NULL;
		return new Document(this, parentDocumentCopy, copyMode);
	}

	/* package */public Document copy(final Document parentDocumentCopy, final CopyMode copyMode)
	{
		return new Document(this, parentDocumentCopy, copyMode);
	}

	/* package */final void assertWritable()
	{
		if (isWritable())
		{
			return;
		}
		if (_initializing)
		{
			return;
		}

		throw new InvalidDocumentStateException(this, "not a writable copy");
	}

	/* package */final void destroy()
	{
		// TODO: mark it as destroyed, fail read/write operations etc
	}

	/**
	 * NOTE: API method, don't call it directly
	 *
	 * @param documentValuesSupplier
	 */
	public void refreshFromSupplier(final DocumentValuesSupplier documentValuesSupplier)
	{
		initializeFields(FieldInitializationMode.Refresh, documentValuesSupplier);
	}

	@Override
	public final String toString()
	{
		// NOTE: keep it short

		final Document parentDocument = getParentDocument();
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", entityDescriptor.getTableNameOrNull())
				.add("parentId", parentDocument == null ? null : parentDocument.getDocumentId())
				.add("id", getDocumentId())
				.add("NEW", _new ? Boolean.TRUE : null)
				.add("windowNo", windowNo)
				.add("writable", _writable)
				.add("valid", _valid)
				.add("saveStatus", _saveStatus)
				// .add("fields", fieldsByName.values()) // skip because it's too long
				.toString();
	}

	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	public Properties getCtx()
	{
		return Env.getCtx(); // FIXME use document level context
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

	/* package */ Document getParentDocument()
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
	 *
	 * NOTE: this shadow evaluatee is not persisted and is discarded on {@link #copy(CopyMode)}.
	 *
	 * @param shadowParentDocumentEvaluatee
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

	private Collection<IDocumentField> getFields()
	{
		return fieldsByName.values();
	}

	public Collection<IDocumentFieldView> getFieldViews()
	{
		final Collection<IDocumentField> documentFields = fieldsByName.values();
		return ImmutableList.<IDocumentFieldView> copyOf(documentFields);
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

	public int getDocumentIdAsInt()
	{
		// TODO handle NO ID field or composed PK
		if (idField == null)
		{
			// Get it from document path.
			// This will cover the case of missing ID which was somehow generated internally
			if (getParentDocument() == null)
			{
				return getDocumentPath().getDocumentId().toInt();
			}
			else
			{
				return getDocumentPath().getSingleRowId().toInt();
			}
		}
		final int idInt = idField.getValueAsInt(-1);
		return idInt;

	}

	public DocumentId getDocumentId()
	{
		// TODO handle NO ID field or composed PK
		if (idField == null)
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
		final int idInt = idField.getValueAsInt(-1);
		return DocumentId.of(idInt);
	}

	public Object getDocumentIdAsJson()
	{
		return getDocumentIdAsInt();
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

	private final DocumentValidStatus setValidStatusAndReturn(final DocumentValidStatus valid)
	{
		Preconditions.checkNotNull(valid, "valid"); // shall not happen
		final DocumentValidStatus validOld = _valid;
		if (Objects.equals(validOld, valid))
		{
			return validOld; // no change
		}

		_valid = valid;
		Execution.getCurrentDocumentChangesCollectorOrNull().collectDocumentValidStatusChanged(getDocumentPath(), valid);
		return valid;
	}

	private final DocumentSaveStatus setSaveStatusAndReturn(final DocumentSaveStatus saveStatus)
	{
		Preconditions.checkNotNull(saveStatus, "saveStatus");
		final DocumentSaveStatus saveStatusOld = _saveStatus;
		if (Objects.equals(saveStatusOld, saveStatus))
		{
			return saveStatusOld; // no change
		}

		_saveStatus = saveStatus;
		Execution.getCurrentDocumentChangesCollector().collectDocumentSaveStatusChanged(getDocumentPath(), saveStatus);
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
	 * Similar with {@link #setValue(String, Object, ReasonSupplier)} but this method is also checking if we are allowed to change that field
	 *
	 * @param fieldName
	 * @param value
	 * @param reason
	 */
	public void processValueChange(final String fieldName, final Object value, final ReasonSupplier reason) throws DocumentFieldReadonlyException
	{
		final IDocumentField documentField = getField(fieldName);

		if (documentField.isReadonly())
		{
			throw new DocumentFieldReadonlyException(fieldName, value);
		}

		setValue(documentField, value, reason);

		// FIXME: hardcoded DocAction processing
		if (WindowConstants.FIELDNAME_DocAction.equals(fieldName))
		{
			processDocAction();
		}
	}

	public void processValueChanges(final List<JSONDocumentChangedEvent> events, final ReasonSupplier reason) throws DocumentFieldReadonlyException
	{
		for (final JSONDocumentChangedEvent event : events)
		{
			if (JSONDocumentChangedEvent.JSONOperation.replace == event.getOperation())
			{
				processValueChange(event.getPath(), event.getValue(), reason);
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

		// TODO: Check Existence of Workflow Activities
		// final String wfStatus = MWFActivity.getActiveInfo(Env.getCtx(), m_AD_Table_ID, Record_ID);
		// if (wfStatus != null)
		// {
		// ADialog.error(m_WindowNo, this, "WFActiveForRecord", wfStatus);
		// return;
		// }

		// TODO: make sure the DocStatus column is up2date
		// if (!checkStatus(m_mTab.getTableName(), Record_ID, DocStatus))
		// {
		// ADialog.error(m_WindowNo, this, "DocumentStatusChanged");
		// return;
		// }

		//
		// Actually process the document
		// TODO: trigger the document workflow instead!
		final String docAction = docActionField.getValueAs(StringLookupValue.class).getIdAsString();
		final String expectedDocStatus = null; // N/A
		Services.get(IDocActionBL.class).processEx(this, docAction, expectedDocStatus);

		//
		// Refresh it
		// and also mark all included documents as stale because it might be that processing add/removed/changed some data in included documents too
		refreshFromRepository();
		for (final IncludedDocumentsCollection includedDocumentsPerDetail : includedDocuments.values())
		{
			includedDocumentsPerDetail.markStaleAll();
		}
	}

	/* package */void setValue(final String fieldName, final Object value, final ReasonSupplier reason)
	{
		final IDocumentField documentField = getField(fieldName);
		setValue(documentField, value, reason);
	}

	private final void setValue(final IDocumentField documentField, final Object value, final ReasonSupplier reason)
	{
		assertWritable();

		final Object valueOld = documentField.getValue();
		documentField.setValue(value);

		// Check if changed. If not, stop here.
		final Object valueNew = documentField.getValue();
		if (DataTypes.equals(valueOld, valueNew))
		{
			return;
		}

		// collect changed value
		final IDocumentChangesCollector documentChangesCollector = Execution.getCurrentDocumentChangesCollector();
		documentChangesCollector.collectValueChanged(documentField, reason != null ? reason : REASON_Value_DirectSetOnDocument);

		// Update all dependencies
		updateFieldsWhichDependsOn(documentField.getFieldName(), documentChangesCollector);

		// Callouts
		fieldCalloutExecutor.execute(documentField.asCalloutField());
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
			if (widgetType == DocumentFieldWidgetType.Button || widgetType == DocumentFieldWidgetType.ActionButton)
			{
				return null;
			}

			return documentField.asCalloutField();
		});
	}

	/**
	 * Updates field status flags for all fields (i.e.Mandatory, ReadOnly, Displayed etc)
	 *
	 * @param documentChangesCollector events collector (where to collect the change events)
	 */
	private void updateAllFieldsFlags(final IDocumentChangesCollector documentChangesCollector)
	{
		logger.trace("Updating all dependencies for {}", this);

		final String triggeringFieldName = null; // N/A
		for (final IDocumentField documentField : getFields())
		{
			for (final DependencyType triggeringDependencyType : DependencyType.values())
			{
				updateFieldFlag(documentField, triggeringFieldName, triggeringDependencyType, documentChangesCollector);
			}
		}
	}

	private final void updateFieldReadOnly(final IDocumentField documentField)
	{
		final ILogicExpression readonlyLogic = documentField.getDescriptor().getReadonlyLogic();
		try
		{
			final LogicExpressionResult readonly = readonlyLogic.evaluateToResult(asEvaluatee(), OnVariableNotFound.Fail);
			documentField.setReadonly(readonly);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating readonly logic {} for {}. Preserving {}", readonlyLogic, documentField, documentField.getReadonly(), e);
		}
	}

	private final void updateFieldMandatory(final IDocumentField documentField)
	{
		final ILogicExpression mandatoryLogic = documentField.getDescriptor().getMandatoryLogic();
		try
		{
			final LogicExpressionResult mandatory = mandatoryLogic.evaluateToResult(asEvaluatee(), OnVariableNotFound.Fail);
			documentField.setMandatory(mandatory);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating mandatory logic {} for {}. Preserving {}", mandatoryLogic, documentField, documentField.getMandatory(), e);
		}
	}

	private final void updateFieldDisplayed(final IDocumentField documentField)
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

	private final void updateFieldsWhichDependsOn(final String triggeringFieldName, final IDocumentChangesCollector documentChangesCollector)
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

			updateFieldFlag(dependentField, triggeringFieldName, dependencyType, documentChangesCollector);
		});
	}

	/**
	 * Updates field's status flag (readonly, mandatory, displayed, lookupValuesStaled etc).
	 *
	 * @param documentField
	 * @param triggeringFieldName optional field name which triggered this update
	 * @param triggeringDependencyType
	 * @param documentChangesCollector events collector (where to collect the change events)
	 * @param collectEventsEventIfNoChange true if we shall collect the change event even if there was no change
	 */
	private void updateFieldFlag(
			final IDocumentField documentField //
			, final String triggeringFieldName //
			, final DependencyType triggeringDependencyType //
			, final IDocumentChangesCollector documentChangesCollector //
	)
	{
		if (DependencyType.ReadonlyLogic == triggeringDependencyType)
		{
			final LogicExpressionResult valueOld = documentField.getReadonly();
			updateFieldReadOnly(documentField);

			final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + triggeringDependencyType;
			documentChangesCollector.collectReadonlyIfChanged(documentField, valueOld, reason);
		}
		else if (DependencyType.MandatoryLogic == triggeringDependencyType)
		{
			final LogicExpressionResult valueOld = documentField.getMandatory();
			updateFieldMandatory(documentField);

			final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + triggeringDependencyType;
			documentChangesCollector.collectMandatoryIfChanged(documentField, valueOld, reason);
		}
		else if (DependencyType.DisplayLogic == triggeringDependencyType)
		{
			final LogicExpressionResult valueOld = documentField.getDisplayed();
			updateFieldDisplayed(documentField);

			final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + triggeringDependencyType;
			documentChangesCollector.collectDisplayedIfChanged(documentField, valueOld, reason);
		}
		else if (DependencyType.LookupValues == triggeringDependencyType)
		{
			final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + triggeringDependencyType;
			final boolean lookupValuesStaledOld = documentField.isLookupValuesStale();
			final boolean lookupValuesStaled = documentField.setLookupValuesStaled(triggeringFieldName);
			if (lookupValuesStaled && !lookupValuesStaledOld)
			{
				documentChangesCollector.collectLookupValuesStaled(documentField, reason);
			}
		}
		else
		{
			new AdempiereException("Unknown dependency type: " + triggeringDependencyType)
					.throwIfDeveloperModeOrLogWarningElse(logger);
		}
	}

	private void updateValidIfStaled()
	{
		for (final IDocumentField field : getFields())
		{
			field.updateValid();
		}

		checkAndGetValidStatus();
	}

	public LookupValuesList getFieldLookupValues(final String fieldName)
	{
		return getField(fieldName).getLookupValues();
	}

	public LookupValuesList getFieldLookupValuesForQuery(final String fieldName, final String query)
	{
		return getField(fieldName).getLookupValuesForQuery(query);

	}

	public Document getIncludedDocument(final DetailId detailId, final DocumentId rowId)
	{
		final IncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.getDocumentById(rowId);
	}

	public List<Document> getIncludedDocuments(final DetailId detailId)
	{
		final IncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.getDocuments();
	}

	public void assertNewDocumentAllowed(final DetailId detailId)
	{
		getIncludedDocumentsCollection(detailId).assertNewDocumentAllowed();
	}

	/* package */IncludedDocumentsCollection getIncludedDocumentsCollection(final DetailId detailId)
	{
		Check.assumeNotNull(detailId, "Parameter detailId is not null");
		final IncludedDocumentsCollection includedDocumentsForDetailId = includedDocuments.get(detailId);
		if (includedDocumentsForDetailId == null)
		{
			throw new IllegalArgumentException("detailId '" + detailId + "' not found for " + this);
		}
		return includedDocumentsForDetailId;
	}

	/* package */ Document createIncludedDocument(final DetailId detailId)
	{
		final IncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.createNewDocument();
	}

	/* package */ void deleteIncludedDocuments(final DetailId detailId, final Set<DocumentId> rowIds)
	{
		final IncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		includedDocuments.deleteDocuments(rowIds);
	}

	/* package */ICalloutExecutor getFieldCalloutExecutor()
	{
		return fieldCalloutExecutor;
	}

	/* package */ boolean isProcessed()
	{
		final IDocumentFieldView processedField = getFieldOrNull(WindowConstants.FIELDNAME_Processed);
		if (processedField != null)
		{
			return processedField.getValueAsBoolean();
		}

		final Document parentDocument = getParentDocument();
		if (parentDocument != null)
		{
			return parentDocument.isProcessed();
		}

		return false;
	}

	/**
	 * Set Dynamic Attribute.
	 * A dynamic attribute is an attribute that is not stored in database and is kept as long as this this instance is not destroyed.
	 *
	 * @param name
	 * @param value
	 */
	public final Object setDynAttribute(final String name, final Object value)
	{
		assertWritable();
		return setDynAttributeNoCheck(name, value);
	}

	private final Object setDynAttributeNoCheck(final String name, final Object value)
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
	 * @param name
	 * @return attribute value or null if not found
	 */
	public final <T> T getDynAttribute(final String name)
	{
		final T defaultValue = null;
		return getDynAttribute(name, defaultValue);
	}

	/**
	 * Get Dynamic Attribute
	 *
	 * @param name
	 * @param defaultValue
	 * @return attribute value or <code>defaultValue</code> if not found
	 */
	public final <T> T getDynAttribute(final String name, final T defaultValue)
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

		@SuppressWarnings("unchecked")
		final T value = (T)valueObj;
		return value;
	}

	public final boolean hasDynAttribute(final String name)
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

	public DocumentValidStatus checkAndGetValidStatus()
	{
		//
		// Check document fields
		for (final IDocumentField documentField : getFields())
		{
			final DocumentValidStatus validState = documentField.getValidStatus();
			if (!validState.isValid())
			{
				logger.trace("Considering document invalid because {} is not valid: {}", documentField, validState);
				return setValidStatusAndReturn(validState);
			}
		}

		//
		// Check included documents
		for (final IncludedDocumentsCollection includedDocumentsPerDetailId : includedDocuments.values())
		{
			final DocumentValidStatus validState = includedDocumentsPerDetailId.checkAndGetValidStatus();
			if (!validState.isValid())
			{
				logger.trace("Considering document invalid because {} is not valid: {}", includedDocumentsPerDetailId, validState);
				return setValidStatusAndReturn(validState);
			}
		}

		return setValidStatusAndReturn(DocumentValidStatus.valid()); // valid
	}

	public DocumentValidStatus getValidStatus()
	{
		return _valid;
	}

	public DocumentSaveStatus getSaveStatus()
	{
		return _saveStatus;
	}

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

	/* package */boolean hasChangesRecursivelly()
	{
		//
		// Check this document
		if (hasChanges())
		{
			return true;
		}

		//
		// Check included documents
		for (final IncludedDocumentsCollection includedDocumentsPerDetailId : includedDocuments.values())
		{
			if (includedDocumentsPerDetailId.hasChangesRecursivelly())
			{
				return true;
			}
		}

		return false; // no changes

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
				setValue(parentLinkField, parentDocument.getDocumentIdAsInt(), REASON_Value_ParentLinkUpdateOnSave);
			}
		}

		//
		// Check if valid for saving
		final DocumentValidStatus validState = checkAndGetValidStatus();
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
			// NOTE: usually if we do the right checkings we shall not get to this
			logger.warn("Failed saving document, but IGNORED: {}", this, saveEx);
			return setSaveStatusAndReturn(DocumentSaveStatus.notSaved(saveEx));
		}
	}

	/* package */DocumentSaveStatus saveIfHasChanges() throws RuntimeException
	{
		boolean wasNew = isNew();

		//
		// Save this document
		if (hasChanges())
		{
			getDocumentRepository().save(this);
			documentCallout.onSave(asCalloutRecord());
			logger.debug("Document saved: {}", this);
		}
		else
		{
			logger.debug("Skip saving because document has NO change: {}", this);
		}

		// Update "wasNew" flag: true only if the document was new before and we just save it now.
		wasNew = wasNew && !isNew();

		//
		// Try also saving the included documents
		for (final IncludedDocumentsCollection includedDocumentsForDetailId : includedDocuments.values())
		{
			includedDocumentsForDetailId.saveIfHasChanges();

			// If document was new we need to invalidate all included documents.
			// NOTE: Usually this has no real effect besides some corner cases like BPartner window where Vendor and Customer tabs are referencing exactly the same record as the header.
			if (wasNew)
			{
				includedDocumentsForDetailId.markStaleAll();
			}
		}

		return setSaveStatusAndReturn(DocumentSaveStatus.saved());
	}

	/* package */void deleteFromRepository()
	{
		getDocumentRepository().delete(this);
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
		private final DocumentEntityDescriptor entityDescriptor;
		private Document parentDocument;
		private FieldInitializationMode fieldInitializerMode;
		private DocumentValuesSupplier documentValuesSupplier;

		private DocumentPath _documentPath;

		private Integer _windowNo;
		private static final AtomicInteger nextWindowNo = new AtomicInteger(1);

		private Builder(final DocumentEntityDescriptor entityDescriptor)
		{
			super();
			Preconditions.checkNotNull(entityDescriptor, "entityDescriptor");
			this.entityDescriptor = entityDescriptor;
		}

		public Document build()
		{
			final Document document = new Document(this);

			final ITabCallout documentCallout = entityDescriptor.createAndInitializeDocumentCallout(document.asCalloutRecord());
			document.documentCallout = ExceptionHandledTabCallout.wrapIfNeeded(documentCallout);

			DocumentValuesSupplier documentValuesSupplierEffective = documentValuesSupplier;
			//
			// Initialize the fields
			if (fieldInitializerMode == FieldInitializationMode.NewDocument)
			{
				documentValuesSupplierEffective = new InitialFieldValueSupplier(document, documentValuesSupplier);
			}

			if (documentValuesSupplierEffective != null)
			{
				document.initializeFields(fieldInitializerMode, documentValuesSupplierEffective);
			}

			//
			// Update document's valid status
			document.checkAndGetValidStatus();

			return document;
		}

		private DocumentField buildField(final DocumentFieldDescriptor descriptor, final Document document)
		{
			return new DocumentField(descriptor, document);
		}

		private DocumentEntityDescriptor getEntityDescriptor()
		{
			return entityDescriptor;
		}

		public Builder setParentDocument(final Document parentDocument)
		{
			this.parentDocument = parentDocument;
			return this;
		}

		public Document initializeAsNewDocument(final DocumentValuesSupplier documentValuesSupplier)
		{
			Preconditions.checkNotNull(documentValuesSupplier, "documentValuesSupplier");
			fieldInitializerMode = FieldInitializationMode.NewDocument;
			this.documentValuesSupplier = documentValuesSupplier;
			return build();
		}

		public Document initializeAsNewDocument(final DocumentId newDocumentId, final String version)
		{
			initializeAsNewDocument(new SimpleDocumentValuesSupplier(newDocumentId, version));
			return build();
		}

		public Builder initializeAsNewDocument(final Supplier<DocumentId> newDocumentIdSupplier, final String version)
		{
			initializeAsNewDocument(new SimpleDocumentValuesSupplier(newDocumentIdSupplier, version));
			return this;
		}
		
		public Builder initializeAsNewDocument(final IntSupplier newDocumentIdSupplier, final String version)
		{
			initializeAsNewDocument(new SimpleDocumentValuesSupplier(DocumentId.supplier(newDocumentIdSupplier), version));
			return this;
		}


		private boolean isNewDocument()
		{
			return fieldInitializerMode == FieldInitializationMode.NewDocument;
		}

		public Document initializeAsExistingRecord(final DocumentValuesSupplier documentValuesSupplier)
		{
			Preconditions.checkNotNull(documentValuesSupplier, "documentValuesSupplier");
			fieldInitializerMode = FieldInitializationMode.Load;
			this.documentValuesSupplier = documentValuesSupplier;
			
			return build();
		}

		private Document getParentDocument()
		{
			return parentDocument;
		}

		private DocumentPath getDocumentPath()
		{
			if (_documentPath == null)
			{
				final DocumentId documentId = documentValuesSupplier.getDocumentId();
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
				if (parentDocument == null)
				{
					_windowNo = nextWindowNo.incrementAndGet();
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
			if (parentDocument == null)
			{
				return isNewDocument();
			}
			else
			{
				return parentDocument.isWritable();
			}
		}
	}
}
