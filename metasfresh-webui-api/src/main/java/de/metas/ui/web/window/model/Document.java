package de.metas.ui.web.window.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.callout.api.impl.CalloutExecutor;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.NullStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap.DependencyType;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.exceptions.DocumentFieldNotFoundException;

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

	private static final Supplier<String> REASON_Value_DirectSetOnDocument = () -> "direct set on Document";
	private static final Supplier<String> REASON_Value_NewDocument = () -> "new document";
	private static final Supplier<String> REASON_Value_Refreshing = () -> "direct set on Document (refresh)";

	private static final AtomicInteger nextWindowNo = new AtomicInteger(1);
	private static final AtomicInteger nextTemporaryId = new AtomicInteger(-1000);

	private final DocumentRepository documentRepository;
	private final DocumentEntityDescriptor entityDescriptor;
	private final int windowNo;
	private final ICalloutExecutor calloutExecutor;

	private final Map<String, DocumentField> fieldsByName;
	private final DocumentField idField;

	private final Document _parentDocument;
	private final Map<String, IncludedDocumentsCollection> includedDocuments;

	private DocumentEvaluatee _evaluatee; // lazy
	private Map<String, Object> _dynAttributes = null; // lazy
	private DocumentAsCalloutRecord _calloutRecord; // lazy

	public static enum FieldInitializationMode
	{
		NewDocument, Refresh, Load,
	}

	public static interface FieldInitialValueSupplier
	{
		Object getInitialValue(final Document document, final DocumentFieldDescriptor fieldDescriptor);
	}

	private Document(final Builder builder)
	{
		super();
		documentRepository = Preconditions.checkNotNull(builder.documentRepository, "documentRepository");
		entityDescriptor = Preconditions.checkNotNull(builder.entityDescriptor, "entityDescriptor");
		windowNo = nextWindowNo.incrementAndGet();
		_parentDocument = builder.parentDocument;

		//
		// Create document fields
		{
			final ImmutableMap.Builder<String, DocumentField> fieldsBuilder = ImmutableMap.builder();
			DocumentField idField = null;
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
			}
			fieldsByName = fieldsBuilder.build();
			this.idField = idField;
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
		// Initialize callout executor
		calloutExecutor = new CalloutExecutor(getCtx(), windowNo);

		//
		// Set default dynamic attributes
		{
			setDynAttribute("DirectShip", false); // FIXME: workaround for https://github.com/metasfresh/metasfresh/issues/287 to avoid WARNINGs
			setDynAttribute("HasCharges", false); // FIXME hardcoded because: Failed evaluating display logic @HasCharges@='Y' for C_Order.C_Charge_ID,ChargeAmt

			// Set document's header window default values
			// NOTE: these dynamic attributes will be considered by Document.asEvaluatee.
			if (_parentDocument == null)
			{
				setDynAttribute("IsSOTrx", entityDescriptor.isSOTrx()); // cover the case for FieldName=IsSOTrx, DefaultValue=@IsSOTrx@
				setDynAttribute("IsApproved", false); // cover the case for FieldName=IsApproved, DefaultValue=@IsApproved@
			}
		}

		//
		// Done
		logger.trace("Created new document instance: {}", this); // keep it last
	}

	private final void initializeFields(final FieldInitializationMode mode, final FieldInitialValueSupplier initialValueSupplier)
	{
		logger.trace("Initializing fields: mode={}", mode);

		for (final DocumentField documentField : getFields())
		{
			initializeField(documentField, mode, initialValueSupplier);
		}

		//
		if (FieldInitializationMode.NewDocument == mode)
		{
			executeAllCallouts(); // FIXME: i think it would be better to trigger the callouts when setting the initial value
			updateAllDependencies();
		}
		else if (FieldInitializationMode.Load == mode)
		{
			updateAllDependencies();
		}
		else if (FieldInitializationMode.Refresh == mode)
		{
			updateAllDependencies();
		}
	}

	private void initializeField(final DocumentField documentField, final FieldInitializationMode mode, final FieldInitialValueSupplier initialValueSupplier)
	{
		final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();
		final Object initialValue = initialValueSupplier.getInitialValue(this, fieldDescriptor);

		final Object valueOld = documentField.getValue();
		documentField.setInitialValue(initialValue);

		if (FieldInitializationMode.NewDocument == mode)
		{
			final IDocumentFieldChangedEventCollector eventsCollector = Execution.getCurrentFieldChangedEventsCollector();
			eventsCollector.collectValueChanged(documentField, REASON_Value_NewDocument);
		}
		else if (FieldInitializationMode.Refresh == mode)
		{
			// Check if changed. If not, stop here.
			// NEEDED for Refresh
			final Object valueNew = documentField.getValue();
			if (Objects.equals(valueOld, valueNew))
			{
				return;
			}

			// collect changed value
			final IDocumentFieldChangedEventCollector eventsCollector = Execution.getCurrentFieldChangedEventsCollector();
			eventsCollector.collectValueChanged(documentField, REASON_Value_Refreshing);

			// Update all dependencies
			updateFieldsWhichDependsOn(documentField.getFieldName(), eventsCollector);

			// Callouts - don't execute them!
			// calloutExecutor.execute(documentField.asCalloutField());
		}

	}

	private static final Object createDefaultValue(final Document document, final DocumentFieldDescriptor fieldDescriptor)
	{
		//
		// Primary Key field
		if (fieldDescriptor.isKey())
		{
			final int value = generateNextTemporaryId();
			return value;
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

	public void refresh(final FieldInitialValueSupplier fieldValuesSupplier)
	{
		initializeFields(FieldInitializationMode.Refresh, fieldValuesSupplier);
	}

	private static int generateNextTemporaryId()
	{
		return nextTemporaryId.decrementAndGet();
	}

	@Override
	public final String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", getDocumentId())
				.add("windowNo", windowNo)
				// .add("fields", fieldsByName.values()) // skip because it's too long
				.toString();
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

	public Collection<DocumentField> getFields()
	{
		return fieldsByName.values();
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
	public DocumentField getField(final String fieldName)
	{
		final DocumentField documentField = getFieldOrNull(fieldName);
		if (documentField == null)
		{
			throw new DocumentFieldNotFoundException(this, fieldName);
		}
		return documentField;
	}

	DocumentField getFieldOrNull(final String fieldName)
	{
		final DocumentField documentField = fieldsByName.get(fieldName);
		return documentField;
	}

	public int getDocumentId()
	{
		if (idField == null)
		{
			// TODO handle NO ID field or composed PK
			logger.warn("No ID field found for {}. Returning {}", this, DocumentId.NEW_ID);
			return DocumentId.NEW_ID;
		}
		return idField.getValueAsInt(DocumentId.NEW_ID);
	}

	public boolean isNew()
	{
		// TODO: handle this state in a more reliable way
		return DocumentId.isNew(getDocumentId());
	}

	public DocumentEvaluatee asEvaluatee()
	{
		if (_evaluatee == null)
		{
			_evaluatee = new DocumentEvaluatee(this);
		}
		return _evaluatee;
	}

	public void setValue(final String fieldName, final Object value, final Supplier<String> reason)
	{
		final DocumentField documentField = getField(fieldName);
		final Object valueOld = documentField.getValue();
		documentField.setValue(value);

		// Check if changed. If not, stop here.
		final Object valueNew = documentField.getValue();
		if (Objects.equals(valueOld, valueNew))
		{
			return;
		}

		// collect changed value
		final IDocumentFieldChangedEventCollector eventsCollector = Execution.getCurrentFieldChangedEventsCollector();
		eventsCollector.collectValueChanged(documentField, reason != null ? reason : REASON_Value_DirectSetOnDocument);

		// Update all dependencies
		updateFieldsWhichDependsOn(fieldName, eventsCollector);

		// Callouts
		calloutExecutor.execute(documentField.asCalloutField());
	}

	public void executeAllCallouts()
	{
		logger.trace("Executing all callouts for {}", this);

		for (final DocumentField documentField : getFields())
		{
			calloutExecutor.execute(documentField.asCalloutField());
		}
	}

	/**
	 * Updates all dependencies for all fields (i.e.Mandatory, ReadOnly, Displayed properties etc)
	 */
	public void updateAllDependencies()
	{
		logger.trace("Updating all dependencies for {}", this);

		for (final DocumentField documentField : getFields())
		{
			updateFieldReadOnly(documentField);
			updateFieldMandatory(documentField);
			updateFieldDisplayed(documentField);
		}
	}

	private final void updateFieldReadOnly(final DocumentField documentField)
	{
		final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();

		final ILogicExpression readonlyLogic = fieldDescriptor.getReadonlyLogic();
		try
		{
			final boolean readonly = readonlyLogic.evaluate(asEvaluatee(), OnVariableNotFound.Fail);
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
			final boolean mandatory = mandatoryLogic.evaluate(asEvaluatee(), OnVariableNotFound.Fail);
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

		boolean displayed = false; // default false, i.e. not displayed
		final ILogicExpression displayLogic = fieldDescriptor.getDisplayLogic();
		try
		{
			displayed = displayLogic.evaluate(asEvaluatee(), OnVariableNotFound.Fail);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating display logic {} for {}", displayLogic, documentField, e);
			displayed = false;
		}

		documentField.setDisplayed(displayed);
	}

	private final void updateFieldsWhichDependsOn(final String fieldName, final IDocumentFieldChangedEventCollector eventsCollector)
	{
		final DocumentFieldDependencyMap dependencies = getEntityDescriptor().getDependencies();
		dependencies.consumeForChangedFieldName(fieldName, (dependentFieldName, dependencyType) -> {
			final DocumentField dependentField = getFieldOrNull(dependentFieldName);
			if (dependentField == null)
			{
				logger.warn("Skip setting dependent propery {} because property value is missing", dependentFieldName);
				return;
			}

			final IDocumentFieldChangedEventCollector fieldEventsCollector = DocumentFieldChangedEventCollector.newInstance();
			updateDependentField(dependentField, fieldName, dependencyType, fieldEventsCollector);
			eventsCollector.collectFrom(fieldEventsCollector);

			for (final String dependentFieldNameLvl2 : fieldEventsCollector.getFieldNames())
			{
				// TODO: i think we shall trigger only in case of Value changed event
				updateFieldsWhichDependsOn(dependentFieldNameLvl2, eventsCollector);
			}

		});
	}

	private void updateDependentField(
			final DocumentField dependentField //
			, final String triggeringFieldName //
			, final DependencyType dependencyType //
			, final IDocumentFieldChangedEventCollector eventsCollector //
	)
	{
		if (DependencyType.ReadonlyLogic == dependencyType)
		{
			final boolean valueOld = dependentField.isReadonly();
			updateFieldReadOnly(dependentField);
			final boolean value = dependentField.isReadonly();

			if (value != valueOld)
			{
				final Supplier<String> reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + dependencyType + ", ReadOnlyLogic="
						+ dependentField.getDescriptor().getReadonlyLogic();
				eventsCollector.collectReadonlyChanged(dependentField, reason);
			}
		}
		else if (DependencyType.MandatoryLogic == dependencyType)
		{
			final boolean valueOld = dependentField.isMandatory();
			updateFieldMandatory(dependentField);
			final boolean value = dependentField.isMandatory();

			if (value != valueOld)
			{
				final Supplier<String> reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + dependencyType
						+ ", ReadOnlyLogic=" + dependentField.getDescriptor().getMandatoryLogic();
				eventsCollector.collectMandatoryChanged(dependentField, reason);
			}
		}
		else if (DependencyType.DisplayLogic == dependencyType)
		{
			final boolean valueOld = dependentField.isDisplayed();
			updateFieldDisplayed(dependentField);
			final boolean value = dependentField.isDisplayed();

			if (value != valueOld)
			{
				final Supplier<String> reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + dependencyType
						+ ", ReadOnlyLogic=" + dependentField.getDescriptor().getDisplayLogic();
				eventsCollector.collectDisplayedChanged(dependentField, reason);
			}
		}
		else if (DependencyType.LookupValues == dependencyType)
		{
			final Supplier<String> reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + dependencyType;
			dependentField.setLookupValuesStaled();
			eventsCollector.collectLookupValuesStaled(dependentField, reason);
		}
		else
		{
			new AdempiereException("Unknown dependency type: " + dependencyType)
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

	private IncludedDocumentsCollection getIncludedDocumentsCollection(final String detailId)
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

	/* package */ICalloutExecutor getCalloutExecutor()
	{
		return calloutExecutor;
	}

	public boolean isProcessed()
	{
		final DocumentField processedField = getFieldOrNull("Processed");
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
		if (_dynAttributes == null)
		{
			return null;
		}
		final Object valueObj = _dynAttributes.get(name);
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

	private boolean isValid()
	{
		boolean valid = true;

		//
		// Check document fields
		for (final DocumentField documentField : getFields())
		{
			if (!documentField.isValid())
			{
				logger.trace("Considering document invalid because {} is not valid", documentField);
				valid = false;
				break;
			}
		}

		return valid;
	}

	private boolean hasChanges()
	{
		boolean changes = false;

		//
		// Check document fields
		for (final DocumentField documentField : getFields())
		{
			if (!documentField.hasChanges())
			{
				logger.trace("Considering document has changes because {} is changed", documentField);
				changes = true;
				break;
			}
		}

		return changes;

	}

	public void saveIfPossible()
	{
		if (!isValid())
		{
			logger.debug("Skip saving because document is not valid: {}", this);
			return;
		}

		if (!isNew() && !hasChanges())
		{
			logger.debug("Skip saving because document has NO change: {}", this);
			return;
		}

		try
		{
			getDocumentRepository().save(this);
			logger.debug("Document saved: {}", this);
		}
		catch (final Exception e)
		{
			// NOTE: usually if we do the right checkings we shall not get to this
			logger.warn("Failed saving document, but IGNORED: {}", this, e);
		}
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

		private Builder()
		{
			super();
		}

		public Document build()
		{
			final Document document = new Document(this);

			//
			// Initialize the fields
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
			fieldInitializer = Document::createDefaultValue;
			return this;
		}

		public Builder initializeAsExistingRecord(final FieldInitialValueSupplier fieldInitializer)
		{
			Preconditions.checkNotNull(fieldInitializer, "fieldInitializer");
			fieldInitializerMode = FieldInitializationMode.Load;
			this.fieldInitializer = fieldInitializer;
			return this;
		}
	}
}
