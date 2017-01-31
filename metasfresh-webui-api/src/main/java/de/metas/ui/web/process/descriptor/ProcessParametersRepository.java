package de.metas.ui.web.process.descriptor;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfoParameter;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.DocumentValuesSupplier;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentFieldView;

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

public class ProcessParametersRepository implements DocumentsRepository
{
	public static final transient ProcessParametersRepository instance = new ProcessParametersRepository();

	private final transient IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

	private static final String VERSION_DEFAULT = "0";

	private ProcessParametersRepository()
	{
		super();
	}

	@Override
	public List<Document> retrieveDocuments(final DocumentQuery query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Document retrieveDocument(final DocumentQuery query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Document retrieveDocumentById(final DocumentEntityDescriptor parametersDescriptor, final int adPInstanceId)
	{
		//
		// Get parameters
		final Map<String, ProcessInfoParameter> processInfoParameters = retrieveProcessInfoParameters(adPInstanceId);

		//
		// Build the parameters (as document)
		return Document.builder()
				.setEntityDescriptor(parametersDescriptor)
				.initializeAsExistingRecord(new ProcessInfoParameterDocumentValuesSupplier(adPInstanceId, processInfoParameters))
				.build();
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
		final Object parameterValueConv = parameterDescriptor.convertToValueClass(parameterValue, id -> LookupValue.fromObject(id, parameterDisplay));
		return parameterValueConv;
	}

	@Override
	public Document createNewDocument(final DocumentEntityDescriptor parametersDescriptor, final Document parentProcessParameters)
	{
		throw new UnsupportedOperationException();
	}

	public Document createNewParametersDocument(final DocumentEntityDescriptor parametersDescriptor, final int adPInstanceId)
	{
		//
		// Build the parameters (as document)
		return Document.builder()
				.setEntityDescriptor(parametersDescriptor)
				.initializeAsNewDocument(adPInstanceId, VERSION_DEFAULT)
				.build();
	}

	@Override
	public void refresh(final Document processParameters)
	{
		final int adPInstanceId = processParameters.getDocumentIdAsInt();
		final Map<String, ProcessInfoParameter> processInfoParameters = retrieveProcessInfoParameters(adPInstanceId);
		processParameters.refreshFromSupplier(new ProcessInfoParameterDocumentValuesSupplier(adPInstanceId, processInfoParameters));
	}

	private final Map<String, ProcessInfoParameter> retrieveProcessInfoParameters(final int adPInstanceId)
	{
		final Properties ctx = Env.getCtx();
		final Map<String, ProcessInfoParameter> processInfoParameters = adPInstanceDAO.retrieveProcessInfoParameters(ctx, adPInstanceId)
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
		if (fieldValue instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)fieldValue;
			parameter = lookupValue.getId();
			info = lookupValue.getDisplayName();
		}
		else
		{
			parameter = fieldValue;
			info = fieldValue == null ? null : fieldValue.toString();
		}

		final Object parameter_To = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameter, parameter_To, info, info_To);
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

	private static final class ProcessInfoParameterDocumentValuesSupplier implements DocumentValuesSupplier
	{
		private final int adPInstanceId;
		private final Map<String, ProcessInfoParameter> processInfoParameters;

		public ProcessInfoParameterDocumentValuesSupplier(final int adPInstanceId, final Map<String, ProcessInfoParameter> processInfoParameters)
		{
			this.adPInstanceId = adPInstanceId;
			this.processInfoParameters = processInfoParameters;
		}

		@Override
		public int getId()
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
