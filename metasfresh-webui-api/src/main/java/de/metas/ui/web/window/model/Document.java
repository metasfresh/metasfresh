package de.metas.ui.web.window.model;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.util.Check;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee2;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap.DependencyType;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window_old.shared.datatype.LookupValue;

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

public class Document
{
	private static final Logger logger = LogManager.getLogger(Document.class);

	private final DocumentEntityDescriptor entityDescriptor;
	private final int windowNo;
	private final Map<String, DocumentField> fieldsByName;

	private DocumentEvaluatee _evaluatee; // lazy


	public Document(final DocumentEntityDescriptor entityDescriptor, final int windowNo)
	{
		this.entityDescriptor = entityDescriptor;
		this.windowNo = windowNo;

		final ImmutableMap.Builder<String, DocumentField> fieldsBuilder = ImmutableMap.builder();
		for (final DocumentFieldDescriptor fieldDescriptor : entityDescriptor.getFields())
		{
			final String name = fieldDescriptor.getName();
			final DocumentField field = new DocumentField(fieldDescriptor);
			fieldsBuilder.put(name, field);
		}
		fieldsByName = fieldsBuilder.build();

		for (final DocumentEntityDescriptor includedEntityDescriptor : entityDescriptor.getIncludedEntities())
		{
			// TODO: implement support for includedEntityDescriptor
		}
	}

	@Override
	public final String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("windowNo", windowNo)
				.add("fields", fieldsByName.values())
				.toString();
	}
	
	public int getWindowNo()
	{
		return windowNo;
	}


	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
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
		Check.assumeNotNull(documentField, "Parameter documentField is not null for name={} in {}", fieldName, this);
		return documentField;
	}

	DocumentField getFieldOrNull(final String fieldName)
	{
		final DocumentField documentField = fieldsByName.get(fieldName);
		return documentField;
	}

	public Evaluatee2 asEvaluatee()
	{
		if (_evaluatee == null)
		{
			_evaluatee = new DocumentEvaluatee(this);
		}
		return _evaluatee;
	}

	private static final class DocumentEvaluatee implements Evaluatee2
	{
		private final Document _document;

		public DocumentEvaluatee(final Document values)
		{
			super();
			_document = values;
		}

		private Properties getCtx()
		{
			return Env.getCtx();
		}

		private final DocumentField getDocumentFieldOrNull(final String name)
		{
			return _document.getFieldOrNull(name);
		}

		private final Set<String> getAvailableFieldNames()
		{
			return _document.getFieldNames();
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			if (variableName == null)
			{
				return false;
			}
			else if (variableName.startsWith("#"))              // Env, global var
			{
				return true;
			}
			else if (variableName.startsWith("$"))              // Env, global accounting var
			{
				return true;
			}
			final DocumentField documentField = getDocumentFieldOrNull(variableName);
			if (documentField != null)
			{
				return true;
			}

			if (logger.isTraceEnabled())
			{
				logger.trace("No document field {} found. Existing properties are: {}", variableName, getAvailableFieldNames());
			}

			return false;
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			if (variableName.startsWith("#"))              // Env, global var
			{
				return Env.getContext(getCtx(), variableName);
			}
			else if (variableName.startsWith("$"))              // Env, global accounting var
			{
				return Env.getContext(getCtx(), variableName);
			}

			final DocumentField documentField = getDocumentFieldOrNull(variableName);
			return convertToString(documentField);
		}

		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			// TODO Auto-generated method stub
			return null;
		}

		/** Converts field value to {@link Evaluatee2} friendly string */
		private String convertToString(final DocumentField documentField)
		{
			final Object value = documentField.getValue();
			if (value == null)
			{
				// TODO: find some defaults?
				return null;
			}
			else if (value instanceof Boolean)
			{
				return DisplayType.toBooleanString((Boolean)value);
			}
			else if (value instanceof String)
			{
				return value.toString();
			}
			else if (value instanceof LookupValue)
			{
				final Object idObj = ((LookupValue)value).getId();
				return idObj == null ? null : idObj.toString().trim();
			}
			else
			{
				return value.toString();
			}
		}
	}

	public void setValueFromJsonObject(final String fieldName, final Object value, final FieldChangedEventCollector eventsCollector)
	{
		final DocumentField field = getField(fieldName);
		final Object valueOld = field.getValue();
		field.setValue(value);

		// Check if changed. If not, stop here.
		final Object valueNew = field.getValue();
		if (Objects.equals(valueOld, valueNew))
		{
			return;
		}

		// collect changed value
		eventsCollector.collectValueChanged(fieldName, field.getValueAsJsonObject());

		// Update all dependencies
		updateFieldsWhichDependsOn(fieldName, eventsCollector);
	}

	/**
	 * Updates all dependencies for all fields (i.e.Mandatory, ReadOnly, Displayed properties etc)
	 */
	public void updateAllDependencies()
	{
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
			final Boolean readonlyValue = readonlyLogic.evaluate(asEvaluatee(), OnVariableNotFound.Fail);
			documentField.setReadonly(readonlyValue);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating readonly logic {} for {}", readonlyLogic, documentField);
		}
	}

	private final void updateFieldMandatory(final DocumentField documentField)
	{
		final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();

		final ILogicExpression mandatoryLogic = fieldDescriptor.getMandatoryLogic();
		try
		{
			final Boolean mandatoryValue = mandatoryLogic.evaluate(asEvaluatee(), OnVariableNotFound.Fail);
			documentField.setMandatory(mandatoryValue);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating mandatory logic {} for {}", mandatoryLogic, documentField);
		}
	}

	private final void updateFieldDisplayed(final DocumentField documentField)
	{
		final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();

		final ILogicExpression displayLogic = fieldDescriptor.getDisplayLogic();
		try
		{
			final Boolean displayValue = displayLogic.evaluate(asEvaluatee(), OnVariableNotFound.Fail);
			documentField.setDisplayed(displayValue);
		}
		catch (final Exception e)
		{
			logger.warn("Failed evaluating display logic {} for {}", displayLogic, documentField);
		}
	}

	private final void updateFieldsWhichDependsOn(final String fieldName, final FieldChangedEventCollector eventsCollector)
	{
		final DocumentFieldDependencyMap dependencies = getEntityDescriptor().getDependencies();
		dependencies.consumeForChangedFieldName(fieldName, (dependentFieldName, dependencyType) -> {
			final DocumentField dependentField = getFieldOrNull(dependentFieldName);
			if (dependentField == null)
			{
				logger.warn("Skip setting dependent propery {} because property value is missing", dependentFieldName);
				return;
			}

			final FieldChangedEventCollector fieldEventsCollector = FieldChangedEventCollector.newInstance();
			updateDependentField(dependentField, fieldName, dependencyType, fieldEventsCollector);
			eventsCollector.collectFrom(fieldEventsCollector);

			for (final String dependentFieldNameLvl2 : fieldEventsCollector.getFieldNames())
			{
				updateFieldsWhichDependsOn(dependentFieldNameLvl2, eventsCollector);
			}

		});

	}

	private void updateDependentField(
			final DocumentField dependentField //
			, final String triggeringFieldName //
			, final DependencyType dependencyType //
			, final FieldChangedEventCollector eventsCollector //
	)
	{
		if (DependencyType.ReadonlyLogic == dependencyType)
		{
			final boolean valueOld = dependentField.isReadonly();
			updateFieldReadOnly(dependentField);
			final boolean value = dependentField.isReadonly();

			if (value != valueOld)
			{
				eventsCollector.collectReadonlyChanged(dependentField.getName(), value);
			}
		}
		if (DependencyType.MandatoryLogic == dependencyType)
		{
			final boolean valueOld = dependentField.isMandatory();
			updateFieldMandatory(dependentField);
			final boolean value = dependentField.isMandatory();

			if (value != valueOld)
			{
				eventsCollector.collectReadonlyChanged(dependentField.getName(), value);
			}
		}
		if (DependencyType.DisplayLogic == dependencyType)
		{
			final boolean valueOld = dependentField.isDisplayed();
			updateFieldDisplayed(dependentField);
			final boolean value = dependentField.isDisplayed();

			if (value != valueOld)
			{
				eventsCollector.collectReadonlyChanged(dependentField.getName(), value);
			}
		}
		if (DependencyType.LookupValues == dependencyType)
		{
			eventsCollector.collectLookupValuesStaled(dependentField.getName());
		}
	}
}
