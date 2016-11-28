package de.metas.ui.web.window.model;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.LogicExpressionResult;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.Document.CopyMode;

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

/*package*/interface IDocumentField extends IDocumentFieldView
{
	public static enum FieldInitializationMode
	{
		NewDocument, Refresh, Load,
	}

	Document getDocument();

	@Override
	default DocumentPath getDocumentPath()
	{
		return getDocument().getDocumentPath();
	}

	/**
	 * @param initialValue initial value / last saved value
	 * @param mode initialization mode
	 */
	void setInitialValue(Object initialValue, FieldInitializationMode mode);

	/**
	 * Set field's current value.
	 *
	 * @param value
	 */
	void setValue(Object value);

	void setMandatory(LogicExpressionResult mandatory);

	void setReadonly(LogicExpressionResult readonly);

	void setDisplayed(LogicExpressionResult displayed);

	boolean setLookupValuesStaled(String triggeringFieldName);

	LookupValuesList getLookupValues();

	LookupValuesList getLookupValuesForQuery(String query);

	ICalloutField asCalloutField();

	void updateValid();

	void updateValidIfStaled();

	/**
	 * @return field's valid state; never return null
	 */
	DocumentValidStatus getValid();

	IDocumentField copy(Document document, CopyMode copyMode);
}