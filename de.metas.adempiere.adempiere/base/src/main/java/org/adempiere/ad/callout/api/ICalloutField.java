package org.adempiere.ad.callout.api;

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

import java.util.Properties;

import org.adempiere.util.Check;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;

/**
 * Callout aware field
 *
 * @author tsa
 *
 */
public interface ICalloutField
{

	boolean isTriggerCalloutAllowed();

	ICalloutRecord getCalloutRecord();

	Properties getCtx();

	String getTableName();

	Object getValue();

	/**
	 * @return old/previous value (from when the underlying model was checked out for changing)
	 */
	Object getOldValue();

	String getColumnName();

	/**
	 * @see ICalloutRecord#getModel(Class)
	 */
	default <T> T getModel(final Class<T> modelClass)
	{
		final ICalloutRecord calloutRecord = getCalloutRecord();
		final T model = calloutRecord.getModel(modelClass);
		Check.assumeNotNull(model, "model not null");
		return model;
	}

	/**
	 * @see ICalloutRecord#getModelBeforeChanges(Class)
	 */
	default <T> T getModelBeforeChanges(final Class<T> modelClass)
	{
		final ICalloutRecord calloutRecord = getCalloutRecord();
		final T model = calloutRecord.getModelBeforeChanges(modelClass);
		Check.assumeNotNull(model, "model not null");
		return model;
	}

	int getWindowNo();

	int getTabNo();

	/**
	 * @return true if we are currently creating this record by copying (with or without details) from another record
	 */
	boolean isRecordCopyingMode();

	/**
	 * @return true if we are currently creating this record by copying (with details) from another record
	 */
	boolean isRecordCopyingModeIncludingDetails();

	ICalloutExecutor getCurrentCalloutExecutor();

	/**
	 * Create and fire Data Status Error Event
	 *
	 * @param AD_Message message
	 * @param info info
	 * @param isError if not true, it is a Warning
	 */
	void fireDataStatusEEvent(final String AD_Message, final String info, final boolean isError);

	/**
	 * Create and fire Data Status Error Event (from Error Log)
	 *
	 * @param errorLog log
	 */
	@Deprecated
	void fireDataStatusEEvent(final ValueNamePair errorLog);

	/**
	 * Put to window context.
	 */
	default void putContext(final String name, final String value)
	{
		Env.setContext(getCtx(), name, value);
	}

	/**
	 * Put to window context.
	 */
	default void putContext(final String name, final boolean value)
	{
		Env.setContext(getCtx(), getWindowNo(), name, value);
	}

	/**
	 * Put to window context.
	 */
	default void putContext(final String name, final java.util.Date value)
	{
		Env.setContext(getCtx(), getWindowNo(), name, value);
	}

	/**
	 * Put to window context.
	 */
	default void putContext(final String name, final int value)
	{
		Env.setContext(getCtx(), getWindowNo(), name, value);
	}

	default int getGlobalContextAsInt(final String name)
	{
		return Env.getContextAsInt(getCtx(), name);
	}

	default int getTabInfoContextAsInt(final String name)
	{
		return Env.getContextAsInt(getCtx(), getWindowNo(), Env.TAB_INFO, name);
	}

	default boolean getContextAsBoolean(final String name)
	{
		return DisplayType.toBoolean(Env.getContext(getCtx(), getWindowNo(), name));
	}
}
