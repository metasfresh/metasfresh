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
import com.google.common.collect.ImmutableList;
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
import de.metas.ui.web.window.model.IDocumentFieldChangedEventCollector.ReasonSupplier;

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

	private static final AtomicInteger nextWindowNo = new AtomicInteger(1);
	private static final AtomicInteger nextTemporaryId = new AtomicInteger(-1000);

	private final DocumentRepository documentRepository;
	private final DocumentEntityDescriptor entityDescriptor;
	private final int windowNo;
	private final ICalloutExecutor calloutExecutor;

	private final Map<String, DocumentField> fieldsByName;
	private final IDocumentFieldView idField;

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
			IDocumentFieldView idField = null;
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

			final boolean collectEventsEventIfNoChange = true;
			updateAllFieldsFlags(Execution.getCurrentFieldChangedEventsCollector(), collectEventsEventIfNoChange);
		}
		else if (FieldInitializationMode.Load == mode)
		{
			final boolean collectEventsEventIfNoChange = false; // not relevant because we are not collecting events at all
			updateAllFieldsFlags(NullDocumentFieldChangedEventCollector.instance, collectEventsEventIfNoChange);
		}
		else if (FieldInitializationMode.Refresh == mode)
		{
			// NOTE: we don't have to update all fields because we updated one by one when initialized
			// final boolean collectEventsEventIfNoChange = false;
			// updateAllFieldsFlags(Execution.getCurrentFieldChangedEventsCollector(), collectEventsEventIfNoChange);
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
		//
		// Get the initialization value
		final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();
		final Object initialValue = initialValueSupplier.getInitialValue(this, fieldDescriptor);

		//
		// Update field's initial value
		final Object valueOld = documentField.getValue();
		documentField.setInitialValue(initialValue);

		//
		// After field was initialized, based on "mode", trigger events, update other fields etc
		if (FieldInitializationMode.NewDocument == mode)
		{
			// Collect the change event, even if there was no change because we just set the initial value
			final IDocumentFieldChangedEventCollector eventsCollector = Execution.getCurrentFieldChangedEventsCollector();
			eventsCollector.collectValueChanged(documentField, REASON_Value_NewDocument);

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
			// Collect change event and update dependencies only if the field's value changed
			final Object valueNew = documentField.getValue();
			if (!Objects.equals(valueOld, valueNew))
			{
				// collect changed value
				final IDocumentFieldChangedEventCollector eventsCollector = Execution.getCurrentFieldChangedEventsCollector();
				eventsCollector.collectValueChanged(documentField, REASON_Value_Refreshing);

				// Update all fields which depends on this field
				updateFieldsWhichDependsOn(documentField.getFieldName(), eventsCollector);

				// NOTE: don't call callouts because this was not a user change.
				// calloutExecutor.execute(documentField.asCalloutField());
			}
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

	private Collection<DocumentField> getFields()
	{
		return fieldsByName.values();
	}
	
	public Collection<IDocumentFieldView> getFieldViews()
	{
		final Collection<DocumentField> documentFields = fieldsByName.values();
		return ImmutableList.<IDocumentFieldView>copyOf(documentFields);
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
	
	IDocumentFieldView getFieldViewOrNull(final String fieldName)
	{
		return getFieldOrNull(fieldName);
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

	public void setValue(final String fieldName, final Object value, final ReasonSupplier reason)
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
	
	public Object getValue(final String fieldName)
	{
		return getField(fieldName).getValue();
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
	 * Updates field status flags for all fields (i.e.Mandatory, ReadOnly, Displayed etc)
	 *
	 * @param eventsCollector events collector (where to collect the change events)
	 * @param collectEventsEventIfNoChange true if we shall collect the change event even if there was no change
	 */
	private void updateAllFieldsFlags(final IDocumentFieldChangedEventCollector eventsCollector, final boolean collectEventsEventIfNoChange)
	{
		logger.trace("Updating all dependencies for {}", this);

		final String triggeringFieldName = null; // N/A
		for (final DocumentField documentField : getFields())
		{
			for (final DependencyType triggeringDependencyType : DependencyType.values())
			{
				updateFieldFlag(documentField, triggeringFieldName, triggeringDependencyType, eventsCollector, collectEventsEventIfNoChange);
			}
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

	private final void updateFieldsWhichDependsOn(final String triggeringFieldName, final IDocumentFieldChangedEventCollector eventsCollector)
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

			final IDocumentFieldChangedEventCollector fieldEventsCollector = DocumentFieldChangedEventCollector.newInstance();
			final boolean collectEventsEventIfNoChange = false;
			updateFieldFlag(dependentField, triggeringFieldName, dependencyType, fieldEventsCollector, collectEventsEventIfNoChange);
			eventsCollector.collectFrom(fieldEventsCollector);

			for (final String dependentFieldNameLvl2 : fieldEventsCollector.getFieldNames())
			{
				// TODO: i think we shall trigger only in case of Value changed event
				updateFieldsWhichDependsOn(dependentFieldNameLvl2, eventsCollector);
			}

		});
	}

	/**
	 * Updates field's status flag (readonly, mandatory, displayed, lookupValuesStaled etc).
	 *
	 * @param documentField
	 * @param triggeringFieldName optional field name which triggered this update
	 * @param triggeringDependencyType
	 * @param eventsCollector events collector (where to collect the change events)
	 * @param collectEventsEventIfNoChange true if we shall collect the change event even if there was no change
	 */
	private void updateFieldFlag(
			final DocumentField documentField //
			, final String triggeringFieldName //
			, final DependencyType triggeringDependencyType //
			, final IDocumentFieldChangedEventCollector eventsCollector //
			, final boolean collectEventsEventIfNoChange)
	{
		if (DependencyType.ReadonlyLogic == triggeringDependencyType)
		{
			final boolean valueOld = documentField.isReadonly();
			updateFieldReadOnly(documentField);
			final boolean value = documentField.isReadonly();

			if (collectEventsEventIfNoChange || value != valueOld)
			{
				final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName
						+ ", DependencyType=" + triggeringDependencyType
						+ ", ReadOnlyLogic=" + documentField.getDescriptor().getReadonlyLogic();
				eventsCollector.collectReadonlyChanged(documentField, reason);
			}
		}
		else if (DependencyType.MandatoryLogic == triggeringDependencyType)
		{
			final boolean valueOld = documentField.isMandatory();
			updateFieldMandatory(documentField);
			final boolean value = documentField.isMandatory();

			if (collectEventsEventIfNoChange || value != valueOld)
			{
				final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName
						+ ", DependencyType=" + triggeringDependencyType
						+ ", MandatoryLogic=" + documentField.getDescriptor().getMandatoryLogic();
				eventsCollector.collectMandatoryChanged(documentField, reason);
			}
		}
		else if (DependencyType.DisplayLogic == triggeringDependencyType)
		{
			final boolean valueOld = documentField.isDisplayed();
			updateFieldDisplayed(documentField);
			final boolean value = documentField.isDisplayed();

			if (collectEventsEventIfNoChange || value != valueOld)
			{
				final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName
						+ ", DependencyType=" + triggeringDependencyType
						+ ", DisplayLogic=" + documentField.getDescriptor().getDisplayLogic();
				eventsCollector.collectDisplayedChanged(documentField, reason);
			}
		}
		else if (DependencyType.LookupValues == triggeringDependencyType)
		{
			final ReasonSupplier reason = () -> "TriggeringField=" + triggeringFieldName + ", DependencyType=" + triggeringDependencyType;
			final boolean lookupValuesStaled = documentField.setLookupValuesStaled(triggeringFieldName);
			if (lookupValuesStaled)
			{
				eventsCollector.collectLookupValuesStaled(documentField, reason);
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
