package org.adempiere.util.lang;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.model.IContextAware;
import org.adempiere.util.collections.Converter;
import org.adempiere.util.lang.impl.TableRecordReference;

/**
 * Table record reference.
 *
 * Used to store references to models, without having to load them from the start (depends on implementation of this interface).
 *
 * In case the underlying model is needed, the developer can call {@link #getModel(IContextAware)} which will return the underlying model.
 *
 * @author tsa
 *
 */
public interface ITableRecordReference
{
	final String COLUMNNAME_Record_ID="Record_ID";

	final String COLUMNNAME_AD_Table_ID="AD_Table_ID";

	/**
	 * Converts a given model to {@link ITableRecordReference}. {@link ITableRecordReference#getModel(IContextAware)} with return the model that was converted.
	 */
	Converter<ITableRecordReference, Object> FromModelConverter = new Converter<ITableRecordReference, Object>()
	{
		@Override
		public ITableRecordReference convert(final Object model)
		{
			return TableRecordReference.ofOrNull(model);
		}
	};

	/**
	 * Converts a given model to {@link ITableRecordReference}. {@link ITableRecordReference#getModel(IContextAware)} with return the model that the converted actually references.
	 */
	Converter<ITableRecordReference, Object> FromReferencedModelConverter = new Converter<ITableRecordReference, Object>()
	{
		@Override
		public ITableRecordReference convert(final Object model)
		{
			return TableRecordReference.ofReferencedOrNull(model);
		}
	};

	/**
	 *
	 * @return referenced model's TableName
	 */
	String getTableName();

	/**
	 *
	 * @return referenced model's AD_Table_ID
	 */
	int getAD_Table_ID();

	/**
	 *
	 * @return referenced model's ID
	 */
	int getRecord_ID();

	/**
	 * Gets referenced/underlying model using given context.
	 *
	 * NOTE: implementations of this method can cache the model but they always need to check if the cached model is valid in given context (e.g. has the same transaction etc).
	 *
	 * @param context
	 * @return referenced model
	 */
	Object getModel(IContextAware context);

	/**
	 * Gets referenced/underlying model using given context and wraps it to provided <code>modelClass</code> interface.
	 *
	 * @param context
	 * @param modelClass
	 * @return referenced model wrapped to given model interface
	 * @see #getModel(IContextAware)
	 */
	<T> T getModel(IContextAware context, Class<T> modelClass);

	/**
	 * Use case: you updated records via directUpdate.
	 */
	void notifyModelStaled();
}
