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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/* package */interface IDocumentField extends IDocumentFieldView
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
	 * Sets initial value / last saved value.
	 * 
	 * This method is also:
	 * <ul>
	 * <li>updating the field value (see {@link #setValue(Object)}).
	 * <li>setting the validStatus to {@link DocumentValidStatus#fieldInitiallyStaled()}
	 * </ul>
	 */
	void setInitialValue(Object initialValue, IDocumentChangesCollector changesCollector);

	/**
	 * Set field's current value.
	 *
	 * @param value
	 */
	void setValue(Object value, IDocumentChangesCollector changesCollector);

	void setMandatory(LogicExpressionResult mandatory, final IDocumentChangesCollector changesCollector);

	void setReadonly(LogicExpressionResult readonly);

	void setDisplayed(LogicExpressionResult displayed);

	/**
	 * Notify this instance that it's lookup values are staled. So next time they are needed, they need to be reloaded.
	 * 
	 * @param triggeringFieldName
	 * @return
	 */
	boolean setLookupValuesStaled(String triggeringFieldName);

	LookupValuesList getLookupValues();

	LookupValuesList getLookupValuesForQuery(String query);

	ICalloutField asCalloutField();

	/** @return field's valid state; never return null */
	@Override
	DocumentValidStatus getValidStatus();

	/**
	 * Note: it's not clear why it is enough to only evaluate the while the <i>initial</i> status is invalid..<br>
	 * Yet I keep it that way for now, because it works as far as we see. And changing it might result in a performance degradation.
	 */
	DocumentValidStatus updateStatusIfInitialInvalidAndGet(IDocumentChangesCollector changesCollector);

	IDocumentField copy(Document document, CopyMode copyMode);
}
