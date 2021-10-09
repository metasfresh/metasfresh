package de.metas.ui.web.window.model;

import java.util.Properties;

import de.metas.util.lang.RepoIdAware;
import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.compiere.util.DisplayType;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.process.IProcessDefaultParameter;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.util.Services;

import lombok.NonNull;

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

public final class DocumentFieldAsCalloutField implements ICalloutField, IProcessDefaultParameter
{
	private static final Logger logger = LogManager.getLogger(DocumentFieldAsCalloutField.class);

	private final IDocumentField documentField;

	/* package */ DocumentFieldAsCalloutField(final IDocumentField documentField)
	{
		this.documentField = documentField;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(documentField)
				.toString();
	}

	private Document getDocument()
	{
		return documentField.getDocument();
	}

	private DocumentFieldDescriptor getDescriptor()
	{
		return documentField.getDescriptor();
	}

	@Override
	public boolean isTriggerCalloutAllowed()
	{
		if (!getDescriptor().isAlwaysUpdateable() && getDocument().isProcessed())
		{
			return false;
		}
		return true;
	}

	@Override
	public Properties getCtx()
	{
		return getDocument().getCtx();
	}

	@Override
	public String getTableName()
	{
		return getDocument().getEntityDescriptor().getTableName();
	}

	@Override
	public String getColumnName()
	{
		return documentField.getFieldName();
	}

	@Override
	public Object getValue()
	{
		return documentField.getValue();
	}

	@Override
	public Object getOldValue()
	{
		return documentField.getOldValue();
	}

	@Override
	public int getWindowNo()
	{
		return getDocument().getWindowNo();
	}

	@Override
	public boolean isRecordCopyingMode()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRecordCopyingModeIncludingDetails()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ICalloutExecutor getCurrentCalloutExecutor()
	{
		return getDocument().getFieldCalloutExecutor();
	}

	@Override
	public void fireDataStatusEEvent(@NonNull final String captionAD_Message, final String message, final boolean isError)
	{
		final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollectorOrNull();
		if (NullDocumentChangesCollector.isNull(changesCollector))
		{
			logger.warn("Got WARNING on field {} but there is no changes collector to dispatch"
					+ "\n captionAD_Message={}"
					+ "\n message={}"
					+ "\n isError={}",
					documentField, captionAD_Message, message, isError);
		}
		else
		{
			changesCollector.collectFieldWarning(documentField, DocumentFieldWarning.builder()
					.caption(Services.get(IMsgBL.class).translatable(captionAD_Message))
					.message(message)
					.error(isError)
					.build());
		}
	}

	@Override
	public void fireDataStatusEEvent(final ValueNamePair errorLog)
	{
		final boolean isError = true;
		fireDataStatusEEvent(errorLog.getValue(), errorLog.getName(), isError);
	}

	@Override
	public void putContext(final String name, final boolean value)
	{
		getDocument().setDynAttribute(name, value);
	}

	@Override
	public void putContext(final String name, final java.util.Date value)
	{
		getDocument().setDynAttribute(name, value);
	}

	@Override
	public void putContext(final String name, final int value)
	{
		getDocument().setDynAttribute(name, value);
	}

	@Override
	public boolean getContextAsBoolean(final String name)
	{
		final Object valueObj = getDocument().getDynAttribute(name);
		return DisplayType.toBoolean(valueObj);
	}

	@Override
	public int getContextAsInt(final String name)
	{
		final Object valueObj = getDocument().getDynAttribute(name);
		if (valueObj == null)
		{
			return -1;
		}
		else if (valueObj instanceof Number)
		{
			return ((Number)valueObj).intValue();
		}
		else
		{
			return Integer.parseInt(valueObj.toString());
		}
	}

	@Override
	public ICalloutRecord getCalloutRecord()
	{
		return getDocument().asCalloutRecord();
	}

	@Override
	public boolean isLookupValuesContainingId(@NonNull final RepoIdAware id)
	{
		return documentField.getLookupValueById(id) != null;
	}
}
