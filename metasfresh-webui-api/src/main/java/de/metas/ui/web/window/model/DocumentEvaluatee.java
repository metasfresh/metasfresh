package de.metas.ui.web.window.model;

import java.util.Properties;
import java.util.Set;

import org.compiere.util.CtxName;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatee2;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;

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

public final class DocumentEvaluatee implements Evaluatee
{
	private static final Logger logger = LogManager.getLogger(DocumentEvaluatee.class);

	private final Document _document;

	/* package */ DocumentEvaluatee(final Document document)
	{
		super();
		_document = document;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(_document)
				.toString();
	}
	
	private Properties getCtx()
	{
		return _document.getCtx();
	}

	private DocumentEvaluatee getParent()
	{
		return _document.getParentDocument().asEvaluatee();
	}

	private boolean hasParent()
	{
		return _document.getParentDocument() != null;
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
	public String get_ValueAsString(final String variableName)
	{
		return getValueAsStringIfExists(variableName).orNull();
	}

	public Optional<String> getValueAsStringIfExists(final String variableName)
	{
		if (variableName == null)
		{
			return Optional.absent();
		}

		//
		// Check global context variable
		{
			final String valueStr = getGlobalContext(variableName);
			if (valueStr != null)
			{
				return Optional.of(valueStr);
			}

		}

		//
		// Document field
		final DocumentField documentField = getDocumentFieldOrNull(variableName);
		if (documentField != null)
		{
			final String documentFieldValue = convertToString(documentField.getFieldName(), documentField.getValue());
			if (documentFieldValue != null)
			{
				return Optional.of(documentFieldValue);
			}
		}

		//
		// Document's dynamic attribute
		if (_document.hasDynAttribute(variableName))
		{
			final String value = convertToString(variableName, _document.getDynAttribute(variableName));
			if (value != null)
			{
				return Optional.of(value);
			}
		}

		//
		// Check parent
		if (hasParent())
		{
			final DocumentEvaluatee parent = getParent();
			final Optional<String> value = parent.getValueAsStringIfExists(variableName);
			if (value.isPresent())
			{
				return value;
			}
		}
		
		//
		// Fallback: Check again the documentField and assume some defaults
		if (documentField != null)
		{
			final Class<?> valueClass = documentField.getDescriptor().getValueClass();
			if(StringLookupValue.class.equals(valueClass))
			{
				// corner case: e.g. Field: C_Order.IncotermLocation's DisplayLogic=@Incoterm@!''
				return Optional.of("");
			}
		}
		
		//
		// Value not found
		if (logger.isTraceEnabled())
		{
			logger.trace("Variable '{}' not found." //
					+ "\n Existing properties are: {}" //
					+ "\n Existing dyn attributes are: {}" //
					, variableName, getAvailableFieldNames(), _document.getAvailableDynAttributes());
		}
		return Optional.absent();
	}

	/**
	 * 
	 * @param variableName
	 * @return value or <code>null</code> if does not apply
	 */
	private String getGlobalContext(final String variableName)
	{
		final String value;
		if (CtxName.isExplicitGlobal(variableName))
		{
			value = Env.getContext(getCtx(), variableName);
		}
		else
		{
			return null;
		}

		//
		// Use some default value
		if (Check.isEmpty(value))
		{
			// FIXME hardcoded. when we will do a proper login, this won't be necessary
			if (variableName.startsWith("$Element_"))
			{
				return DisplayType.toBooleanString(false);
			}
		}

		return value;
	}

	/** Converts field value to {@link Evaluatee2} friendly string */
	private String convertToString(final String variableName, final Object value)
	{
		if (value == null)
		{
			if (hasParent())
			{
				return null; // advice the caller to ask the parent
			}

			// FIXME: hardcoded default to avoid a lot of warnings
			if (variableName.endsWith("_ID"))
			{
				return "-1";
			}

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
			return ((LookupValue)value).getIdAsString();
		}
		else if (value instanceof java.util.Date)
		{
			final java.util.Date valueDate = (java.util.Date)value;
			return Env.toString(valueDate);
		}
		else
		{
			return value.toString();
		}
	}
}