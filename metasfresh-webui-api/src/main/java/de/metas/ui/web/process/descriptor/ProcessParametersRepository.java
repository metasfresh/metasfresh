package de.metas.ui.web.process.descriptor;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.Env;

import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.Document;
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
				.setDocumentIdSupplier(() -> adPInstanceId)
				.initializeAsExistingRecord(parameterDescriptor -> extractParameterValue(processInfoParameters, parameterDescriptor))
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
		if (parentProcessParameters != null)
		{
			throw new IllegalArgumentException("parentDocument is not supported");
		}

		final Properties ctx = Env.getCtx();
		final I_AD_PInstance adPInstance = InterfaceWrapperHelper.create(ctx, I_AD_PInstance.class, ITrx.TRXNAME_None);
		adPInstance.setAD_Process_ID(parametersDescriptor.getDocumentTypeId(DocumentType.Process));
		// TODO set, user, role, table/record
		adPInstance.setRecord_ID(-1);
		InterfaceWrapperHelper.save(adPInstance);
		final int adPInstanceId = adPInstance.getAD_PInstance_ID();

		//
		// Build the parameters (as document)
		return Document.builder()
				.setEntityDescriptor(parametersDescriptor)
				.setDocumentIdSupplier(() -> adPInstanceId)
				.initializeAsNewDocument()
				.build();
	}

	@Override
	public void refresh(final Document processParameters)
	{
		final Map<String, ProcessInfoParameter> processInfoParameters = retrieveProcessInfoParameters(processParameters.getDocumentIdAsInt());
		processParameters.refresh(parameterDescriptor -> extractParameterValue(processInfoParameters, parameterDescriptor));
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

}
