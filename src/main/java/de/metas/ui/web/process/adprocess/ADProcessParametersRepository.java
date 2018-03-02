package de.metas.ui.web.process.adprocess;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfoParameter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DateRangeValue;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.Password;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.DocumentValuesSupplier;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentEvaluatee;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.OrderedDocumentsList;
import de.metas.ui.web.window.model.lookup.DocumentZoomIntoInfo;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;

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

/* package */ class ADProcessParametersRepository implements DocumentsRepository
{
	public static final transient ADProcessParametersRepository instance = new ADProcessParametersRepository();

	private final transient IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

	private static final String VERSION_DEFAULT = "0";

	private ADProcessParametersRepository()
	{
		super();
	}

	@Override
	public OrderedDocumentsList retrieveDocuments(final DocumentQuery query, final IDocumentChangesCollector changesCollector)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Document retrieveDocument(final DocumentQuery query, final IDocumentChangesCollector changesCollector)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Document retrieveDocumentById(final DocumentEntityDescriptor parametersDescriptor, final DocumentId adPInstanceId, final IDocumentChangesCollector changesCollector)
	{
		//
		// Get parameters
		final Map<String, ProcessInfoParameter> processInfoParameters = retrieveProcessInfoParameters(adPInstanceId);

		//
		// Build the parameters (as document)
		return Document.builder(parametersDescriptor)
				.setChangesCollector(changesCollector)
				.initializeAsExistingRecord(new ProcessInfoParameterDocumentValuesSupplier(adPInstanceId, processInfoParameters));
	}

	@Override
	public DocumentId retrieveParentDocumentId(final DocumentEntityDescriptor parentEntityDescriptor, final DocumentQuery childDocumentQuery)
	{
		throw new EntityNotFoundException("Process documents does not have parents")
				.setParameter("parentEntityDescriptor", parentEntityDescriptor)
				.setParameter("childDocumentQuery", childDocumentQuery);
	}

	private static Object extractParameterValue(final Map<String, ProcessInfoParameter> processInfoParameters, final DocumentFieldDescriptor parameterDescriptor)
	{
		final String fieldName = parameterDescriptor.getFieldName();
		final ProcessInfoParameter processInfoParameter = processInfoParameters.get(fieldName);
		if (processInfoParameter == null)
		{
			return null;
		}

		final Object parameterValue = processInfoParameter.getParameter();
		final String parameterDisplay = processInfoParameter.getInfo();
		final Object parameterValueConv = parameterDescriptor.convertToValueClass(parameterValue, new LookupValueByIdSupplier()
		{

			@Override
			public DocumentZoomIntoInfo getDocumentZoomInto(final int id)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public LookupValue findById(final Object id)
			{
				return LookupValue.fromObject(id, parameterDisplay);
			}
		});
		return parameterValueConv;
	}

	@Override
	public Document createNewDocument(final DocumentEntityDescriptor parametersDescriptor, final Document parentProcessParameters, final IDocumentChangesCollector changesCollector)
	{
		throw new UnsupportedOperationException();
	}

	Document createNewParametersDocument(final DocumentEntityDescriptor parametersDescriptor, final DocumentId adPInstanceId, final IDocumentEvaluatee evalCtx)
	{
		final IDocumentEvaluatee evalCtxEffective;
		if (evalCtx != null)
		{
			evalCtxEffective = evalCtx.excludingFields(WindowConstants.FIELDNAME_Processed, WindowConstants.FIELDNAME_Processing, WindowConstants.FIELDNAME_IsActive);
		}
		else
		{
			evalCtxEffective = null;
		}

		return Document.builder(parametersDescriptor)
				.setShadowParentDocumentEvaluatee(evalCtxEffective)
				.initializeAsNewDocument(adPInstanceId, VERSION_DEFAULT);
	}

	@Override
	public void refresh(final Document processParameters)
	{
		final DocumentId adPInstanceId = processParameters.getDocumentId();
		final Map<String, ProcessInfoParameter> processInfoParameters = retrieveProcessInfoParameters(adPInstanceId);
		processParameters.refreshFromSupplier(new ProcessInfoParameterDocumentValuesSupplier(adPInstanceId, processInfoParameters));
	}

	private final Map<String, ProcessInfoParameter> retrieveProcessInfoParameters(final DocumentId adPInstanceId)
	{
		final Properties ctx = Env.getCtx();
		final Map<String, ProcessInfoParameter> processInfoParameters = adPInstanceDAO.retrieveProcessInfoParameters(ctx, adPInstanceId.toInt())
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(ProcessInfoParameter::getParameterName));
		return processInfoParameters;
	}

	@Override
	public void save(final Document processParameters)
	{
		final int adPInstanceId = processParameters.getDocumentIdAsInt();
		final List<ProcessInfoParameter> piParams = processParameters.getFieldViews()
				.stream()
				.map(field -> createProcessInfoParameter(field))
				.collect(GuavaCollectors.toImmutableList());
		adPInstanceDAO.saveParameterToDB(adPInstanceId, piParams);
	}

	private static ProcessInfoParameter createProcessInfoParameter(final IDocumentFieldView field)
	{
		final String parameterName = field.getFieldName();
		final Object fieldValue = field.getValue();

		final Object parameter;
		final String info;
		final Object parameterTo;
		final String infoTo;
		if (fieldValue instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)fieldValue;
			parameter = lookupValue.getId();
			info = lookupValue.getDisplayName();
			parameterTo = null;
			infoTo = null;
		}
		else if (fieldValue instanceof DateRangeValue)
		{
			final DateRangeValue dateRange = (DateRangeValue)fieldValue;
			parameter = dateRange.getFrom();
			info = parameter == null ? null : parameter.toString();
			parameterTo = dateRange.getTo();
			infoTo = parameterTo == null ? null : parameterTo.toString();
		}
		else if (fieldValue instanceof Password)
		{
			final Password password = Password.cast(fieldValue);
			parameter = password.getAsString();
			info = Password.OBFUSCATE_STRING;
			parameterTo = null;
			infoTo = null;
		}
		else
		{
			parameter = fieldValue;
			info = fieldValue == null ? null : fieldValue.toString();
			parameterTo = null;
			infoTo = null;
		}

		return new ProcessInfoParameter(parameterName, parameter, parameterTo, info, infoTo);
	}

	@Override
	public void delete(final Document processParameters)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String retrieveVersion(final DocumentEntityDescriptor entityDescriptor, final int documentIdAsInt)
	{
		return VERSION_DEFAULT;
	}

	@Override
	public int retrieveLastLineNo(final DocumentQuery query)
	{
		throw new UnsupportedOperationException();
	}

	private static final class ProcessInfoParameterDocumentValuesSupplier implements DocumentValuesSupplier
	{
		private final DocumentId adPInstanceId;
		private final Map<String, ProcessInfoParameter> processInfoParameters;

		public ProcessInfoParameterDocumentValuesSupplier(final DocumentId adPInstanceId, final Map<String, ProcessInfoParameter> processInfoParameters)
		{
			this.adPInstanceId = adPInstanceId;
			this.processInfoParameters = processInfoParameters;
		}

		@Override
		public DocumentId getDocumentId()
		{
			return adPInstanceId;
		}

		@Override
		public String getVersion()
		{
			return VERSION_DEFAULT;
		}

		@Override
		public Object getValue(final DocumentFieldDescriptor parameterDescriptor)
		{
			return extractParameterValue(processInfoParameters, parameterDescriptor);
		}
	}
}
