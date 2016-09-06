package de.metas.ui.web.window.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.callout.api.impl.CalloutExecutor;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.api.NullStringExpression;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.ui.spi.ExceptionHandledTabCallout;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
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
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap.DependencyType;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.exceptions.DocumentFieldNotFoundException;
import de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException;
import de.metas.ui.web.window.exceptions.InvalidDocumentStateException;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;

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
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(Document.class);

	public static final Document NULL = null;

	private static final ReasonSupplier REASON_Value_DirectSetOnDocument = () -> "direct set on Document";
	private static final ReasonSupplier REASON_Value_NewDocument = () -> "new document";
	private static final ReasonSupplier REASON_Value_Refreshing = () -> "direct set on Document (refresh)";
	private static final ReasonSupplier REASON_Value_ParentLinkUpdateOnSave = () -> "parent link update on save";

	private static final AtomicInteger nextWindowNo = new AtomicInteger(1);

	//
	// Descriptors & paths
	private final DocumentRepository documentRepository;
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

	//
	// Callouts
	private ITabCallout documentCallout = ITabCallout.NULL;
	private final ICalloutExecutor fieldCalloutExecutor;
	private DocumentAsCalloutRecord _calloutRecord; // lazy

	//
	// Fields
	private final Map<String, DocumentField> fieldsByName;
	private final IDocumentFieldView idField;
	private final DocumentField parentLinkField;

	//
	// Parent & children
	private final Document _parentDocument;
	private final Map<String, IncludedDocumentsCollection> includedDocuments;

	//
	// Misc
	private DocumentEvaluatee _evaluatee; // lazy
	private Map<String, Object> _dynAttributes = null; // lazy

	public static enum FieldInitializationMode
	{
		NewDocument, Refresh, Load,
	}

	@FunctionalInterface
	public static interface FieldInitialValueSupplier
	{
		Object NO_VALUE = new Object();

		/**
		 * @param fieldDescriptor
		 * @return initial value or {@link #NO_VALUE} if it cannot provide a value
		 */
		Object getInitialValue(final DocumentFieldDescriptor fieldDescriptor);
	}

	private Document(final Builder builder)
	{
		super();
		documentRepository = Preconditions.checkNotNull(builder.documentRepository, "documentRepository");
		entityDescriptor = Preconditions.checkNotNull(builder.entityDescriptor, "entityDescriptor");
		_parentDocument = builder.parentDocument;
		documentPath = builder.getDocumentPath();
		if (_parentDocument == null)
		{
			windowNo = nextWindowNo.incrementAndGet();
			_writable = builder.isNewDocument();
		}
		else
		{
			windowNo = _parentDocument.getWindowNo();
			_writable = _parentDocument.isWritable();
		}

		_new = builder.isNewDocument();

		//
		// Create document fields
		{
			final ImmutableMap.Builder<String, DocumentField> fieldsBuilder = ImmutableMap.builder();
			IDocumentFieldView idField = null;
			DocumentField parentLinkField = null;
			for (final DocumentFieldDescriptor fieldDescriptor : entityDescriptor.getFields())
			{
				final String fieldName = fieldDescriptor.getFieldName();
				final DocumentField field = new DocumentField(fieldDescriptor, this);
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
			final ImmutableMap.Builder<String, IncludedDocumentsCollection> includedDocuments = ImmutableMap.builder();
			for (final DocumentEntityDescriptor includedEntityDescriptor : entityDescriptor.getIncludedEntities())
			{
				final String detailId = includedEntityDescriptor.getDetailId();
				final IncludedDocumentsCollection includedDocumentsForDetailId = new IncludedDocumentsCollection(this, includedEntityDescriptor);
				includedDocuments.put(detailId, includedDocumentsForDetailId);
			}
			this.includedDocuments = includedDocuments.build();
		}

		//
		// Initialize field callout executor
		fieldCalloutExecutor = new CalloutExecutor(getCtx(), windowNo);

		_evaluatee = null; // lazy

		//
		// Set default dynamic attributes
		{
			setDynAttributeNoCheck("DirectShip", false); // FIXME: workaround for https://github.com/metasfresh/metasfresh/issues/287 to avoid WARNINGs
			setDynAttributeNoCheck("HasCharges", false); // FIXME hardcoded because: Failed evaluating display logic @HasCharges@='Y' for C_Order.C_Charge_ID,ChargeAmt

			// Set document's header window default values
			// NOTE: these dynamic attributes will be considered by Document.asEvaluatee.
			if (_parentDocument == null)
			{
				setDynAttributeNoCheck("IsSOTrx", entityDescriptor.isSOTrx()); // cover the case for FieldName=IsSOTrx, DefaultValue=@IsSOTrx@
				setDynAttributeNoCheck("IsApproved", false); // cover the case for FieldName=IsApproved, DefaultValue=@IsApproved@
			}
		}

		//
		// Done
		logger.trace("Created new document instance: {}", this); // keep it last
	}

	/** copy constructor */
	private Document(final Document from, final Document parentDocumentCopy, final boolean writable)
	{
		super();
		documentRepository = from.documentRepository;
		entityDescriptor = from.entityDescriptor;
		windowNo = from.windowNo;
		_writable = writable;

		_new = from._new;
		_valid = from._valid;
		_saveStatus = from._saveStatus;

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
			final ImmutableMap.Builder<String, DocumentField> fieldsBuilder = ImmutableMap.builder();
			IDocumentFieldView idField = null;
			DocumentField parentLinkField = null;
			for (final DocumentField fieldOrig : from.fieldsByName.values())
			{
				final DocumentField fieldCopy = fieldOrig.copy(this);
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
			final ImmutableMap.Builder<String, IncludedDocumentsCollection> includedDocuments = ImmutableMap.builder();
			for (final Map.Entry<String, IncludedDocumentsCollection> e : from.includedDocuments.entrySet())
			{
				final String detailId = e.getKey();
				final IncludedDocumentsCollection includedDocumentsForDetailIdOrig = e.getValue();
				final IncludedDocumentsCollection includedDocumentsForDetailIdCopy = includedDocumentsForDetailIdOrig.copy(this);

				includedDocuments.put(detailId, includedDocumentsForDetailIdCopy);
			}
			this.includedDocuments = includedDocuments.build();
		}

		//
		// Initialize callout executor
		documentCallout = from.documentCallout;
		// NOTE: we need a new instance of it because the "calloutExecutor" has state (i.e. active callouts list etc)
		fieldCalloutExecutor = new CalloutExecutor(getCtx(), windowNo);

		_evaluatee = null; // lazy

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

	private final void initializeFields(final FieldInitializationMode mode, final FieldInitialValueSupplier initialValueSupplier)
	{
		logger.trace("Initializing fields: mode={}", mode);

		if (_initializing)
		{
			throw new InvalidDocumentStateException(this, "already initializing");
		}
		_initializing = true;
		try
		{
			for (final DocumentField documentField : getFields())
			{
				initializeField(documentField, mode, initialValueSupplier);
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

				final boolean collectEventsEventIfNoChange = true;
				updateAllFieldsFlags(Execution.getCurrentDocumentChangesCollector(), collectEventsEventIfNoChange);
			}
			else if (FieldInitializationMode.Load == mode)
			{
				final boolean collectEventsEventIfNoChange = false; // not relevant because we are not collecting events at all
				updateAllFieldsFlags(NullDocumentChangesCollector.instance, collectEventsEventIfNoChange);
			}
			else if (FieldInitializationMode.Refresh == mode)
			{
				// NOTE: we don't have to update all fields because we updated one by one when initialized
				// final boolean collectEventsEventIfNoChange = false;
				// updateAllFieldsFlags(Execution.getCurrentFieldChangedEventsCollector(), collectEventsEventIfNoChange);

				documentCallout.onRefresh(asCalloutRecord());
			}

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
	 * @param initialValueSupplier initial value supplier
	 */
	private void initializeField(final DocumentField documentField, final FieldInitializationMode mode, final FieldInitialValueSupplier initialValueSupplier)
	{
		boolean valueSet = false;
		Object valueOld = null;
		try
		{
			//
			// Get the initialization value
			final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();
			final Object initialValue = initialValueSupplier.getInitialValue(fieldDescriptor);

			//
			// Update field's initial value
			if (initialValue != FieldInitialValueSupplier.NO_VALUE)
			{
				valueOld = documentField.getValue();
				documentField.setInitialValue(initialValue);
			}

			valueSet = true;
		}
		catch (final Exception e)
		{
			valueSet = false;
			if (FieldInitializationMode.NewDocument == mode)
			{
				logger.warn("Failed initializing {}, mode={} using {}. Keeping current initial value.", documentField, mode, initialValueSupplier, e);
			}
			else
			{
				throw Throwables.propagate(e);
			}
		}
		finally
		{
			if (documentField.getValid().isInitialInvalid())
			{
				documentField.updateValid();
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
			if (valueSet)
			{
				// Collect change event and update dependencies only if the field's value changed
				final Object valueNew = documentField.getValue();
				if (!DataTypes.equals(valueOld, valueNew))
				{
					// collect changed value
					final IDocumentChangesCollector eventsCollector = Execution.getCurrentDocumentChangesCollector();
					eventsCollector.collectValueChanged(documentField, REASON_Value_Refreshing);

					// Update all fields which depends on this field
					updateFieldsWhichDependsOn(documentField.getFieldName(), eventsCollector);

					// NOTE: don't call callouts because this was not a user change.
					// calloutExecutor.execute(documentField.asCalloutField());
				}
			}
		}
	}

	private static final Object provideFieldInitialValueForNewDocument(final DocumentFieldDescriptor fieldDescriptor, final Document document, final IntSupplier documentIdSupplier)
	{
		//
		// Primary Key field
		if (fieldDescriptor.isKey())
		{
			final int id = documentIdSupplier.getAsInt();
			return id;
		}

		//
		// Parent link field
		if (fieldDescriptor.isParentLink())
		{
			final Document parentDocument = document.getParentDocument();
			if (parentDocument != null)
			{
				final int value = parentDocument.getDocumentId();
				return value;
			}
		}

		//
		// Default value expression
		final IStringExpression defaultValueExpression = fieldDescriptor.getDefaultValueExpression();
		if (!NullStringExpression.isNull(defaultValueExpression))
		{
			// TODO: optimize: here instead of IStringExpression we would need some generic expression which parses to a given type.
			String valueStr = defaultValueExpression.evaluate(document.asEvaluatee(), OnVariableNotFound.Fail);
			if (Check.isEmpty(valueStr, false))
			{
				valueStr = null;
			}

			return valueStr;
		}

		//
		// Preference (user) - P|
		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
		{
			final String valueStr = Env.getPreference(document.getCtx(), entityDescriptor.getAD_Window_ID(), fieldDescriptor.getFieldName(), false);
			if (!Check.isEmpty(valueStr, false))
			{
				return valueStr;
			}
		}

		//
		// Preference (System) - # $
		{
			final String valueStr = Env.getPreference(document.getCtx(), entityDescriptor.getAD_Window_ID(), fieldDescriptor.getFieldName(), true);
			if (!Check.isEmpty(valueStr, false))
			{
				return valueStr;
			}
		}

		//
		// Fallback
		return null;
	}

	public Document copyWritable()
	{
		final Document parentDocumentCopy = Document.NULL;
		final boolean writable = true;
		return new Document(this, parentDocumentCopy, writable);
	}

	public Document copyReadonly()
	{
		final Document parentDocumentCopy = Document.NULL;
		final boolean writable = false;
		return new Document(this, parentDocumentCopy, writable);
	}

	/* package */public Document copy(final Document parentDocumentCopy)
	{
		return new Document(this, parentDocumentCopy, parentDocumentCopy.isWritable());
	}

	/* package */final void assertWritable()
	{
		if (_writable)
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

	public void refresh(final FieldInitialValueSupplier fieldValuesSupplier)
	{
		initializeFields(FieldInitializationMode.Refresh, fieldValuesSupplier);
	}

	@Override
	public final String toString()
	{
		final Document parentDocument = getParentDocument();
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
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

	/* package */DocumentRepository getDocumentRepository()
	{
		return documentRepository;
	}

	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}

	public Document getParentDocument()
	{
		return _parentDocument;
	}

	public Document getRootDocument()
	{
		Document parent = _parentDocument;
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

	private Collection<DocumentField> getFields()
	{
		return fieldsByName.values();
	}

	public Collection<IDocumentFieldView> getFieldViews()
	{
		final Collection<DocumentField> documentFields = fieldsByName.values();
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
	private DocumentField getField(final String fieldName)
	{
		final DocumentField documentField = getFieldOrNull(fieldName);
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

	private DocumentField getFieldOrNull(final String fieldName)
	{
		final DocumentField documentField = fieldsByName.get(fieldName);
		return documentField;
	}

	public IDocumentFieldView getFieldViewOrNull(final String fieldName)
	{
		return getFieldOrNull(fieldName);
	}

	public IDocumentFieldView getIdFieldViewOrNull()
	{
		return idField;
	}

	public int getDocumentId()
	{
		if (idField == null)
		{
			// TODO handle NO ID field or composed PK

			return -1;
			// logger.warn("No ID field found for {}. Returning {}", this, DocumentId.NEW_ID);
			// return DocumentId.NEW_ID;
		}
		return idField.getValueAsInt(-1);
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
		Execution.getCurrentDocumentChangesCollector().collectDocumentValidStatusChanged(getDocumentPath(), valid);
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

	public DocumentEvaluatee asEvaluatee()
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
		final DocumentField documentField = getField(fieldName);

		if (documentField.isReadonly())
		{
			throw new DocumentFieldReadonlyException(fieldName, value);
		}

		setValue(documentField, value, reason);

		if (WindowConstants.FIELDNAME_DocAction.equals(fieldName))
		{
			processDocAction();
		}

	}

	private void processDocAction()
	{
		assertWritable();

		final DocumentField docActionField = getField(WindowConstants.FIELDNAME_DocAction);

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
		documentRepository.refresh(this);
	}

	public void setValue(final String fieldName, final Object value, final ReasonSupplier reason)
	{
		final DocumentField documentField = getField(fieldName);
		setValue(documentField, value, reason);
	}

	private final void setValue(final DocumentField documentField, final Object value, final ReasonSupplier reason)
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
		logger.trace("Executing all callouts for {}", this);

		for (final DocumentField documentField : getFields())
		{
			fieldCalloutExecutor.execute(documentField.asCalloutField());
		}
	}

	/**
	 * Updates field status flags for all fields (i.e.Mandatory, ReadOnly, Displayed etc)
	 *
	 * @param documentChangesCollector events collector (where to collect the change events)
	 * @param collectEventsEventIfNoChange true if we shall collect the change event even if there was no change
	 */
	private void updateAllFieldsFlags(final IDocumentChangesCollector documentChangesCollector, final boolean collectEventsEventIfNoChange)
	{
		logger.trace("Updating all dependencies for {}", this);

		final String triggeringFieldName = null; // N/A
		for (final DocumentField documentField : getFields())
		{
			for (final DependencyType triggeringDependencyType : DependencyType.values())
			{
				updateFieldFlag(documentField, triggeringFieldName, triggeringDependencyType, documentChangesCollector, collectEventsEventIfNoChange);
			}
		}
	}

	private final void updateFieldReadOnly(final DocumentField documentField)
	{
		final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();

		final ILogicExpression readonlyLogic = fieldDescriptor.getReadonlyLogic();
		try
		{
			final LogicExpressionResult readonly = readonlyLogic.evaluateToResult(asEvaluatee(), OnVariableNotFound.Fail);
			documentField.setReadonly(readonly);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating readonly logic {} for {}", readonlyLogic, documentField, e);
		}
	}

	private final void updateFieldMandatory(final DocumentField documentField)
	{
		final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();

		final ILogicExpression mandatoryLogic = fieldDescriptor.getMandatoryLogic();
		try
		{
			final LogicExpressionResult mandatory = mandatoryLogic.evaluateToResult(asEvaluatee(), OnVariableNotFound.Fail);
			documentField.setMandatory(mandatory);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating mandatory logic {} for {}", mandatoryLogic, documentField, e);
		}
	}

	private final void updateFieldDisplayed(final DocumentField documentField)
	{
		final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();

		LogicExpressionResult displayed = LogicExpressionResult.FALSE; // default false, i.e. not displayed
		final ILogicExpression displayLogic = fieldDescriptor.getDisplayLogic();
		try
		{
			displayed = displayLogic.evaluateToResult(asEvaluatee(), OnVariableNotFound.Fail);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating display logic {} for {}", displayLogic, documentField, e);
			displayed = LogicExpressionResult.FALSE;
		}

		documentField.setDisplayed(displayed);
	}

	private final void updateFieldsWhichDependsOn(final String triggeringFieldName, final IDocumentChangesCollector documentChangesCollector)
	{
		final DocumentFieldDependencyMap dependencies = getEntityDescriptor().getDependencies();
		dependencies.consumeForChangedFieldName(triggeringFieldName, (dependentFieldName, dependencyType) -> {
			final DocumentField dependentField = getFieldOrNull(dependentFieldName);
			if (dependentField == null)
			{
				// shall not happen
				logger.warn("Skip setting dependent propery {} because property value is missing", dependentFieldName);
				return;
			}

			final IDocumentChangesCollector fieldEventsCollector = DocumentChangesCollector.newInstance();
			final boolean collectEventsEventIfNoChange = false;
			updateFieldFlag(dependentField, triggeringFieldName, dependencyType, fieldEventsCollector, collectEventsEventIfNoChange);
			documentChangesCollector.collectFrom(fieldEventsCollector);

			for (final String dependentFieldNameLvl2 : fieldEventsCollector.getFieldNames(getDocumentPath()))
			{
				// TODO: i think we shall trigger only in case of Value changed event
				updateFieldsWhichDependsOn(dependentFieldNameLvl2, documentChangesCollector);
			}

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
			final DocumentField documentField //
			, final String triggeringFieldName //
			, final DependencyType triggeringDependencyType //
			, final IDocumentChangesCollector documentChangesCollector //
			, final boolean collectEventsEventIfNoChange)
	{
		if (DependencyType.ReadonlyLogic == triggeringDependencyType)
		{
			final boolean valueOld = documentField.isReadonly();
			updateFieldReadOnly(documentField);
			final LogicExpressionResult value = documentField.getReadonly();

			if (collectEventsEventIfNoChange || value.booleanValue() != valueOld)
			{
				final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName
						+ ", DependencyType=" + triggeringDependencyType
						+ ", EvaluationResult=" + value;
				documentChangesCollector.collectReadonlyChanged(documentField, reason);
			}
		}
		else if (DependencyType.MandatoryLogic == triggeringDependencyType)
		{
			final boolean valueOld = documentField.isMandatory();
			updateFieldMandatory(documentField);
			final LogicExpressionResult value = documentField.getMandatory();

			if (collectEventsEventIfNoChange || value.booleanValue() != valueOld)
			{
				final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName
						+ ", DependencyType=" + triggeringDependencyType
						+ ", EvaluationResult=" + value;
				documentChangesCollector.collectMandatoryChanged(documentField, reason);
			}
		}
		else if (DependencyType.DisplayLogic == triggeringDependencyType)
		{
			final boolean valueOld = documentField.isDisplayed();
			updateFieldDisplayed(documentField);
			final LogicExpressionResult value = documentField.getDisplayed();

			if (collectEventsEventIfNoChange || value.booleanValue() != valueOld)
			{
				final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName
						+ ", DependencyType=" + triggeringDependencyType
						+ ", EvaluationResult=" + value;
				documentChangesCollector.collectDisplayedChanged(documentField, reason);
			}
		}
		else if (DependencyType.LookupValues == triggeringDependencyType)
		{
			final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + triggeringDependencyType;
			final boolean lookupValuesStaled = documentField.setLookupValuesStaled(triggeringFieldName);
			if (lookupValuesStaled)
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

	public List<LookupValue> getFieldLookupValues(final String fieldName)
	{
		return getField(fieldName).getLookupValues(this);
	}

	public List<LookupValue> getFieldLookupValuesForQuery(final String fieldName, final String query)
	{
		return getField(fieldName).getLookupValuesForQuery(this, query);

	}

	public Document getIncludedDocument(final String detailId, final DocumentId rowId)
	{
		final IncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.getDocumentById(rowId);
	}

	public List<Document> getIncludedDocuments(final String detailId)
	{
		final IncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.getDocuments();
	}

	/* package */IncludedDocumentsCollection getIncludedDocumentsCollection(final String detailId)
	{
		final IncludedDocumentsCollection includedDocumentsForDetailId = includedDocuments.get(detailId);
		if (includedDocumentsForDetailId == null)
		{
			throw new IllegalArgumentException("detailId '" + detailId + "' not found for " + this);
		}
		return includedDocumentsForDetailId;
	}

	public Document createIncludedDocument(final String detailId)
	{
		final IncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		return includedDocuments.createNewDocument();
	}

	public void deleteIncludedDocuments(final String detailId, final Set<DocumentId> rowIds)
	{
		final IncludedDocumentsCollection includedDocuments = getIncludedDocumentsCollection(detailId);
		includedDocuments.deleteDocuments(rowIds);
	}

	/* package */ICalloutExecutor getFieldCalloutExecutor()
	{
		return fieldCalloutExecutor;
	}

	public boolean isProcessed()
	{
		final IDocumentFieldView processedField = getFieldOrNull("Processed");
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

	/* package */ DocumentValidStatus checkAndGetValidStatus()
	{
		//
		// Check document fields
		for (final DocumentField documentField : getFields())
		{
			final DocumentValidStatus validState = documentField.getValid();
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
		for (final DocumentField documentField : getFields())
		{
			if (documentField.hasChanges())
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
		final Document parentDocument = getParentDocument();
		if (parentLinkField != null && parentDocument != null)
		{
			setValue(parentLinkField, parentDocument.getDocumentId(), REASON_Value_ParentLinkUpdateOnSave);
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

		//
		// Try also saving the included documents
		for (final IncludedDocumentsCollection includedDocumentsForDetailId : includedDocuments.values())
		{
			includedDocumentsForDetailId.saveIfHasChanges();
		}

		return setSaveStatusAndReturn(DocumentSaveStatus.saved());
	}

	public ICalloutRecord asCalloutRecord()
	{
		if (_calloutRecord == null)
		{
			_calloutRecord = new DocumentAsCalloutRecord(this);
		}
		return _calloutRecord;
	}

	public static final class Builder
	{
		private DocumentRepository documentRepository;
		private DocumentEntityDescriptor entityDescriptor;
		private Document parentDocument;
		private FieldInitializationMode fieldInitializerMode;
		private FieldInitialValueSupplier fieldInitializer;
		private IntSupplier documentIdSupplier;

		private DocumentPath _documentPath;

		private Builder()
		{
			super();
		}

		public Document build()
		{
			final Document document = new Document(this);

			final ITabCallout documentCallout = Services.get(ITabCalloutFactory.class).createAndInitialize(document.asCalloutRecord());
			document.documentCallout = ExceptionHandledTabCallout.wrapIfNeeded(documentCallout);

			//
			// Initialize the fields
			if (fieldInitializerMode == FieldInitializationMode.NewDocument)
			{
				fieldInitializer = (fieldDescriptor) -> provideFieldInitialValueForNewDocument(fieldDescriptor, document, documentIdSupplier);
			}

			if (fieldInitializer != null)
			{
				document.initializeFields(fieldInitializerMode, fieldInitializer);
			}

			return document;
		}

		public Builder setDocumentRepository(final DocumentRepository documentRepository)
		{
			this.documentRepository = documentRepository;
			return this;
		}

		public Builder setEntityDescriptor(final DocumentEntityDescriptor entityDescriptor)
		{
			this.entityDescriptor = entityDescriptor;
			return this;
		}

		public Builder setParentDocument(final Document parentDocument)
		{
			this.parentDocument = parentDocument;
			return this;
		}

		public Builder initializeAsNewDocument()
		{
			fieldInitializerMode = FieldInitializationMode.NewDocument;
			// NOTE: fieldInitializer will be set by build() method because it's a method of document
			return this;
		}

		private boolean isNewDocument()
		{
			return fieldInitializerMode == FieldInitializationMode.NewDocument;
		}

		public Builder initializeAsExistingRecord(final FieldInitialValueSupplier fieldInitializer)
		{
			Preconditions.checkNotNull(fieldInitializer, "fieldInitializer");
			fieldInitializerMode = FieldInitializationMode.Load;
			this.fieldInitializer = fieldInitializer;
			return this;
		}

		public Builder setDocumentIdSupplier(final IntSupplier documentIdSupplier)
		{
			this.documentIdSupplier = documentIdSupplier;
			return this;
		}

		private DocumentPath getDocumentPath()
		{
			if (_documentPath == null)
			{
				final int documentId = documentIdSupplier.getAsInt();
				if (parentDocument == null)
				{
					_documentPath = DocumentPath.rootDocumentPath(entityDescriptor.getAD_Window_ID(), documentId);
				}
				else
				{
					_documentPath = parentDocument.getDocumentPath().createChildPath(entityDescriptor.getDetailId(), documentId);
				}
			}
			return _documentPath;
		}
	}
}
