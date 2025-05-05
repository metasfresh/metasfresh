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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.document.engine.DocumentWrapper;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.organization.InstantAndOrgId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.BeforeAfterType;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.Util.ArrayKey;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Database decoupled implementation for {@link IDocumentBL}
 *
 * @author tsa
 *
 */
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

	@SuppressWarnings("SameParameterValue")
	private static boolean hasMethod(Class<?> clazz, Class<?> returnType, String methodName, Class<?>... parameterTypes)
	{
		try
		{
			final Method method = clazz.getMethod(methodName, parameterTypes);
			return returnType.equals(method.getReturnType());
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
	protected boolean processIt0(@NonNull final IDocument document, final String action) throws Exception
	{
		if (document instanceof DocumentWrapper)
		{
			return super.processIt0(document, action);
		}

		// for "normal" POJOWrappers what are not backed by a DocumentHandler, we need to use IProcessInterceptor and simulate the whole thing

		Check.assumeNotEmpty(action, "The given 'action' parameter needs to be not-empty");

		final DocTimingType timingTypeBefore = DocTimingType.forAction(action, BeforeAfterType.Before);
		final DocTimingType timingTypeAfter = DocTimingType.forAction(action, BeforeAfterType.After);

		final String tableName = InterfaceWrapperHelper.getModelTableName(document);

		//
		// To better emulate doc processing workflow, in case we are asked to Complete a document fire Prepare first
		if (IDocument.ACTION_Complete.equals(action))
		{
			final boolean prepared = processIt0(document, IDocument.ACTION_Prepare);
			if (!prepared)
			{
				return false;
			}
		}

		//
		// Fire Before Action
		POJOLookupMap.get().fireDocumentChange(document, timingTypeBefore);

		// Actual processing
		final boolean result;

		final ArrayKey key = new ArrayKey(tableName, action);
		IProcessInterceptor interceptor = processInterceptors.get(key);
		if (interceptor == null)
		{
			interceptor = defaultProcessInterceptor;
		}
		result = interceptor.processIt(document, action);

		//
		// Fire After Action
		POJOLookupMap.get().fireDocumentChange(document, timingTypeAfter);

		return result;
	}

	public void registerProcessInterceptor(final String tableName, final String action, final IProcessInterceptor interceptor)
	{
		final ArrayKey key = new ArrayKey(tableName, action);
		processInterceptors.put(key, interceptor);
	}

	private final Map<ArrayKey, IProcessInterceptor> processInterceptors = new HashMap<>();

	private IProcessInterceptor defaultProcessInterceptor = PROCESSINTERCEPTOR_CompleteDirectly;

	public void setDefaultProcessInterceptor(IProcessInterceptor defaultProcessInterceptor)
	{
		this.defaultProcessInterceptor = defaultProcessInterceptor;
	}

	private static void setDocStatus(final IDocument document, final String docStatus, final String docAction, final boolean processed)
	{
		document.setDocStatus(docStatus);
		final POJOWrapper wrapper = POJOWrapper.getWrapper(document);
		wrapper.setValue("DocAction", docAction);
		wrapper.setValue("Processed", processed);
		wrapper.setValue("Processing", false);
		InterfaceWrapperHelper.save(wrapper);

		// Check if DocStatus was really set
		// NOTE: in past we had a bug which was clearing/refreshing the object and it was discarding all it's changed values
		if (!docStatus.equals(document.getDocStatus()))
		{
			throw new AdempiereException("Cannot set DocStatus=" + docStatus + " to " + document);
		}
	}

	public interface IProcessInterceptor
	{
		boolean processIt(final IDocument doc, final String action) throws Exception;
	}

	public static final IProcessInterceptor PROCESSINTERCEPTOR_DirectCall = IDocument::processIt;

	/**
	 * This processor automatically sets the DocStatus, DocAction and Processed flags based on requested action.
	 */
	public static final IProcessInterceptor PROCESSINTERCEPTOR_CompleteDirectly = (doc, action) -> {
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
		else if (IDocument.ACTION_Close.equals(action))
		{
			setDocStatus(doc, IDocument.STATUS_Closed, IDocument.ACTION_None, true);
			return true;
		}
		else
		{
			return PROCESSINTERCEPTOR_DirectCall.processIt(doc, action);
		}
	};

	@Override
	public InstantAndOrgId getDocumentDate(Properties ctx, int adTableID, int recordId)
	{
		final Object model = POJOLookupMap.get().lookup(adTableID, recordId);
		return getDocumentDate(model);
	}

	@Override
	protected IDocument getLegacyDocumentOrNull(
			Object documentObj,
			boolean throwEx)
	{
		final Object documentObjToUse;
		final POJOWrapper wrapper;
		if (documentObj instanceof ITableRecordReference)
		{
			final Object referencedModel = ((ITableRecordReference)documentObj).getModel(Object.class);
			documentObjToUse = referencedModel;
			wrapper = POJOWrapper.getWrapper(referencedModel);
		}
		else
		{
			wrapper = POJOWrapper.getWrapper(documentObj);
			documentObjToUse = documentObj;
		}
		final Class<?> interfaceClass = wrapper.getInterfaceClass();
		if (hasMethod(interfaceClass, String.class, "getDocStatus")
				&& hasMethod(interfaceClass, String.class, "getDocAction")
		// allow for now to consider documents also the ones that don't have DocumentNo; see <code>I_C_Flatrate_Term</code>
		// && hasMethod(interfaceClass, String.class, "getDocumentNo")
		)
		{
			return POJOWrapper.create(documentObjToUse, IDocument.class);
		}
		if (throwEx)
		{
			throw new AdempiereException("Cannot extract " + IDocument.class + " from " + documentObj);
		}
		return null;
	}

}
