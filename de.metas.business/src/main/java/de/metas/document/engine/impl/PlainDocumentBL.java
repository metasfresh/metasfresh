package de.metas.document.engine.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.BeforeAfterType;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.util.Util.ArrayKey;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;

/**
 * Database decoupled implementation for {@link IDocumentBL}
 * 
 * @author tsa
 * 
 */
// @Ignore
public class PlainDocumentBL extends AbstractDocumentBL
{
	public static Boolean isDocumentTableResponse = null;

	@Override
	public boolean isDocumentTable(final String tableName)
	{
		if (isDocumentTableResponse == null)
		{
			throw new UnsupportedOperationException("Method not implemented");
		}

		return isDocumentTableResponse;
	}

	@Override
	protected IDocument getDocument(Object document, boolean throwEx)
	{
		if (document == null)
		{
			if (throwEx)
			{
				throw new IllegalArgumentException("document is null");
			}
			return null;
		}

		if (document instanceof IDocument)
		{
			return (IDocument)document;
		}

		//
		// WARNING: this is just a partial implementation
		final POJOWrapper wrapper = POJOWrapper.getWrapper(document);
		final Class<?> interfaceClass = wrapper.getInterfaceClass();
		if (hasMethod(interfaceClass, String.class, "getDocStatus")
				&& hasMethod(interfaceClass, String.class, "getDocAction")
				&& hasMethod(interfaceClass, String.class, "getDocumentNo"))
		{
			return POJOWrapper.create(document, IDocument.class);
		}

		if (throwEx)
		{
			throw new IllegalArgumentException("Document '" + document + "' cannot be converted to " + IDocument.class);
		}
		return null;
	}

	private static final boolean hasMethod(Class<?> clazz, Class<?> returnType, String methodName, Class<?>... parameterTypes)
	{
		try
		{
			final Method method = clazz.getMethod(methodName, parameterTypes);
			if (method == null)
			{
				return false;
			}

			if (!returnType.equals(method.getReturnType()))
			{
				return false;
			}

			return true;
		}
		catch (SecurityException e)
		{
			throw new AdempiereException(e);
		}
		catch (NoSuchMethodException e)
		{
			return false;
		}
	}

	@Override
	public int getC_DocType_ID(final Properties ctx, final int AD_Table_ID, final int Record_ID)
	{
		final Object model = POJOLookupMap.get().lookup(AD_Table_ID, Record_ID);
		if (model == null)
		{
			return -1;
		}

		final POJOWrapper wrapper = POJOWrapper.getWrapper(model);
		final Map<String, Object> valuesMap = wrapper.getValuesMap();

		final Object docTypeId = valuesMap.get("C_DocType_ID");
		if (docTypeId instanceof Number)
		{
			return ((Number)docTypeId).intValue();
		}

		final Object docTypeTargetId = valuesMap.get("C_DocTypeTarget_ID");
		if (docTypeTargetId instanceof Number)
		{
			return ((Number)docTypeTargetId).intValue();
		}

		return -1;
	}

	@Override
	protected String retrieveString(final int adTableId, final int recordId, final String columnName)
	{
		final Object model = POJOLookupMap.get().lookup(adTableId, recordId);
		if (model == null)
		{
			return null;
		}

		final POJOWrapper wrapper = POJOWrapper.getWrapper(model);
		final Map<String, Object> valuesMap = wrapper.getValuesMap();

		Object value = null;
		if (valuesMap.containsKey(columnName))
		{
			value = valuesMap.get(columnName);
		}

		return value == null ? null : value.toString();
	}

	@Override
	protected Object retrieveModelOrNull(Properties ctx, int adTableId, int recordId)
	{
		return POJOLookupMap.get().lookup(adTableId, recordId);
	}

	@Override
	protected boolean processIt0(final IDocument doc, final String action) throws Exception
	{
		Check.assumeNotEmpty(action, "action not empty");

		final DocTimingType timingTypeBefore = DocTimingType.forAction(action, BeforeAfterType.Before);
		final DocTimingType timingTypeAfter = DocTimingType.forAction(action, BeforeAfterType.After);

		final String tableName = InterfaceWrapperHelper.getModelTableName(doc);
		
		//
		// To better emulate doc processing workflow, in case we are asked to Complete a document fire Prepare first
		if (IDocument.ACTION_Complete.equals(action))
		{
			final boolean prepared = processIt0(doc, IDocument.ACTION_Prepare);
			if (!prepared)
			{
				return prepared;
			}
		}

		//
		// Fire Before Action
		POJOLookupMap.get().fireDocumentChange(doc, timingTypeBefore);

		//
		// Actual processing
		final ArrayKey key = new ArrayKey(tableName, action);
		IProcessInterceptor interceptor = processInterceptors.get(key);
		if (interceptor == null)
		{
			interceptor = defaultProcessInterceptor;
		}
		final boolean result = interceptor.processIt(doc, action);

		//
		// Fire After Action
		POJOLookupMap.get().fireDocumentChange(doc, timingTypeAfter);

		return result;
	}

	public void registerProcessInterceptor(final String tableName, final String action, final IProcessInterceptor interceptor)
	{
		final ArrayKey key = new ArrayKey(tableName, action);
		processInterceptors.put(key, interceptor);
	}

	private final Map<ArrayKey, IProcessInterceptor> processInterceptors = new HashMap<>();

	private IProcessInterceptor defaultProcessInterceptor = PROCESSINTERCEPTOR_CompleteDirectly;

	public IProcessInterceptor getDefaultProcessInterceptor()
	{
		return defaultProcessInterceptor;
	}

	public void setDefaultProcessInterceptor(IProcessInterceptor defaultProcessInterceptor)
	{
		this.defaultProcessInterceptor = defaultProcessInterceptor;
	}

	private static void setDocStatus(final IDocument doc, final String docStatus, final String docAction, final boolean processed)
	{
		doc.setDocStatus(docStatus);
		final POJOWrapper wrapper = POJOWrapper.getWrapper(doc);
		wrapper.setValue("DocAction", docAction);
		wrapper.setValue("Processed", processed);
		wrapper.setValue("Processing", false);
		InterfaceWrapperHelper.save(wrapper);

		// Check if DocStatus was really set
		// NOTE: in past we had a bug which was clearing/refreshing the object and it was discarding all it's changed values
		if (!docStatus.equals(doc.getDocStatus()))
		{
			throw new AdempiereException("Cannot set DocStatus=" + docStatus + " to " + doc);
		}
	}

	public static interface IProcessInterceptor
	{
		boolean processIt(final IDocument doc, final String action) throws Exception;
	}

	public static final IProcessInterceptor PROCESSINTERCEPTOR_DirectCall = new IProcessInterceptor()
	{
		@Override
		public boolean processIt(IDocument doc, String action) throws Exception
		{
			return doc.processIt(action);
		}

	};

	/**
	 * This processor automatically sets the DocStatus, DocAction and Processed flags based on requested action.
	 */
	public static final IProcessInterceptor PROCESSINTERCEPTOR_CompleteDirectly = new IProcessInterceptor()
	{

		@Override
		public boolean processIt(IDocument doc, String action) throws Exception
		{
			if (IDocument.ACTION_Complete.equals(action))
			{
				setDocStatus(doc, IDocument.STATUS_Completed, IDocument.ACTION_Close, true);
				return true;
			}
			if (IDocument.ACTION_Prepare.equals(action))
			{
				setDocStatus(doc, IDocument.STATUS_InProgress, IDocument.ACTION_Complete, false);
				return true;
			}
			else if (IDocument.ACTION_Void.equals(action))
			{
				setDocStatus(doc, IDocument.STATUS_Voided, IDocument.ACTION_None, true);
				return true;
			}
			else if (IDocument.ACTION_ReActivate.equals(action))
			{
				setDocStatus(doc, IDocument.STATUS_InProgress, IDocument.ACTION_Complete, false);
				return true;
			}
			else if (IDocument.ACTION_Reverse_Correct.equals(action))
			{
				setDocStatus(doc, IDocument.STATUS_Reversed, IDocument.ACTION_None, true);
				return true;
			}
			else
			{
				return PROCESSINTERCEPTOR_DirectCall.processIt(doc, action);
			}
		}

	};

	@Override
	public Timestamp getDocumentDate(Properties ctx, int adTableID, int recordId)
	{
		final Object model = POJOLookupMap.get().lookup(adTableID, recordId);
		return getDocumentDate(model);
	}
}
