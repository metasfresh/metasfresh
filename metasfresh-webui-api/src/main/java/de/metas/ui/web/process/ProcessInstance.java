package de.metas.ui.web.process;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.google.common.base.MoreObjects;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.exceptions.ProcessExecutionException;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewsRepository;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentSaveStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
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

public final class ProcessInstance
{
	private static final transient Logger logger = LogManager.getLogger(ProcessInstance.class);
	
	@Autowired
	@Lazy
	private IDocumentViewsRepository documentViewsRepo;

	private final ProcessDescriptor processDescriptor;
	private final int adPInstanceId;
	private final Document parameters;

	private boolean executed = false;
	private ProcessInstanceResult executionResult;

	/** New instance constructor */
	/* package */ ProcessInstance(final ProcessDescriptor processDescriptor, final int adPInstanceId, final Document parameters)
	{
		super();
		this.processDescriptor = processDescriptor;
		this.adPInstanceId = adPInstanceId;
		this.parameters = parameters;

		executed = false;
		executionResult = null;
	}

	/** Copy constructor */
	private ProcessInstance(final ProcessInstance from, final CopyMode copyMode)
	{
		super();
		Adempiere.autowire(this);
		
		processDescriptor = from.processDescriptor;
		adPInstanceId = from.adPInstanceId;
		parameters = from.parameters.copy(copyMode);

		executed = from.executed;
		executionResult = from.executionResult;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_PInstance_ID", adPInstanceId)
				.add("executed", "executed")
				.add("executionResult", executionResult)
				.add("processDescriptor", processDescriptor)
				.toString();
	}

	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	public ProcessDescriptor getDescriptor()
	{
		return processDescriptor;
	}

	public int getAD_PInstance_ID()
	{
		return adPInstanceId;
	}

	public Document getParametersDocument()
	{
		return parameters;
	}

	public Collection<IDocumentFieldView> getParameters()
	{
		return parameters.getFieldViews();
	}

	public ProcessInstance copy(final CopyMode copyMode)
	{
		return new ProcessInstance(this, copyMode);
	}

	public LookupValuesList getParameterLookupValues(final String parameterName)
	{
		return parameters.getFieldLookupValues(parameterName);
	}

	public LookupValuesList getParameterLookupValuesForQuery(final String parameterName, final String query)
	{
		return parameters.getFieldLookupValuesForQuery(parameterName, query);
	}

	public void processParameterValueChanges(final List<JSONDocumentChangedEvent> events, final ReasonSupplier reason)
	{
		assertNotExecuted();
		parameters.processValueChanges(events, reason);
	}

	/**
	 * @return execution result or throws exception if the process was not already executed
	 */
	public ProcessInstanceResult getExecutionResult()
	{
		final ProcessInstanceResult executionResult = this.executionResult;
		if (executionResult == null)
		{
			throw new AdempiereException("Process instance does not have an execution result yet: " + this);
		}
		return executionResult;
	}

	public boolean isExecuted()
	{
		return executed;
	}

	private final void assertNotExecuted()
	{
		if (isExecuted())
		{
			throw new AdempiereException("Process already executed");
		}
	}

	public ProcessInstanceResult startProcess()
	{
		assertNotExecuted();

		//
		// Make sure it's saved in database
		if (!saveIfValidAndHasChanges(true))
		{
			// TODO: improve error message (be more explicit)
			throw new ProcessExecutionException("Instance could not be saved because it's not valid");
		}

		//
		// Process info
		final Properties ctx = Env.getCtx(); // We assume the right context was already used when the process was loaded
		final ProcessDescriptor processDescriptor = getDescriptor();
		final String adLanguage = Env.getAD_Language(ctx);
		final String name = processDescriptor.getCaption(adLanguage);
		final ProcessExecutionResult processExecutionResult = ProcessInfo.builder()
				.setCtx(ctx)
				.setCreateTemporaryCtx()
				.setAD_PInstance_ID(getAD_PInstance_ID())
				.setTitle(name)
				.setPrintPreview(true)
				.setJRDesiredOutputType(OutputType.PDF)
				//
				// Execute the process/report
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		//
		// Build and return the execution result
		{
			final ProcessInstanceResult.Builder resultBuilder = ProcessInstanceResult.builder()
					.setAD_PInstance_ID(processExecutionResult.getAD_PInstance_ID())
					.setSummary(processExecutionResult.getSummary())
					.setError(processExecutionResult.isError());
			//
			// Result: report
			final File reportTempFile = saveReportToDiskIfAny(processExecutionResult);
			if (reportTempFile != null)
			{
				resultBuilder.setReportData(processExecutionResult.getReportFilename(), processExecutionResult.getReportContentType(), reportTempFile);
			}

			//
			// Result: records to select
			createViewIfNeeded(resultBuilder, processExecutionResult.getRecordsToSelectAfterExecution());

			final ProcessInstanceResult result = resultBuilder.build();
			executionResult = result;
			if (result.isSuccess())
			{
				executed = false;
			}
			return result;
		}
	}

	private static final File saveReportToDiskIfAny(final ProcessExecutionResult processExecutionResult)
	{
		//
		// If we are not dealing with a report, stop here
		final byte[] reportData = processExecutionResult.getReportData();
		if (reportData == null || reportData.length <= 0)
		{
			return null;
		}

		//
		// Create report temporary file
		File reportFile = null;
		try
		{
			final String reportFilePrefix = "report_" + processExecutionResult.getAD_PInstance_ID() + "_";

			final String reportContentType = processExecutionResult.getReportContentType();
			final String reportFileExtension = MimeType.getExtensionByType(reportContentType);
			final String reportFileSuffix = Check.isEmpty(reportFileExtension, true) ? "" : "." + reportFileExtension.trim();
			reportFile = File.createTempFile(reportFilePrefix, reportFileSuffix);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed creating report temporary file " + reportFile);
		}

		//
		// Write report data to our temporary report file
		Util.writeBytes(reportFile, reportData);

		return reportFile;
	}
	
	private void createViewIfNeeded(final ProcessInstanceResult.Builder resultBuilder, final List<TableRecordReference> recordRefs)
	{
		if(recordRefs.isEmpty())
		{
			return;
		}
		
		final Map<String, JSONCreateDocumentViewRequest.Builder> tableName2viewBuilder = new HashMap<>();
		for (final TableRecordReference recordRef : recordRefs)
		{
			final String tableName = recordRef.getTableName();
			final JSONCreateDocumentViewRequest.Builder viewRequestBuilder = tableName2viewBuilder.computeIfAbsent(tableName, key -> {
				final int adWindowId = RecordZoomWindowFinder.findAD_Window_ID(recordRef);
				return JSONCreateDocumentViewRequest.builder(adWindowId, JSONViewDataType.grid);
			});
			
			viewRequestBuilder.addFilterOnlyId(recordRef.getRecord_ID());
		}
		
		if (tableName2viewBuilder.isEmpty())
		{
			return;
		}
		
		if (tableName2viewBuilder.size() > 1)
		{
			logger.warn("More than one views to be created found for {}. Creating only the first view.", recordRefs);
		}
		
		final JSONCreateDocumentViewRequest viewRequest = tableName2viewBuilder.values().iterator().next().build();
		
		final IDocumentViewSelection view = documentViewsRepo.createView(viewRequest);
		resultBuilder.setView(view.getAD_Window_ID(), view.getViewId());
	}


	/**
	 * Save
	 *
	 * @param throwEx <code>true</code> if we shall throw an exception if document it's not valid or it's not saved
	 * @return true if valid and saved
	 */
	public boolean saveIfValidAndHasChanges(final boolean throwEx)
	{
		final Document parametersDocument = getParametersDocument();
		final DocumentSaveStatus parametersSaveStatus = parametersDocument.saveIfValidAndHasChanges();
		final boolean saved;
		if (parametersSaveStatus.hasChangesToBeSaved() || parametersSaveStatus.isError())
		{
			saved = false;
			if (throwEx)
			{
				throw new ProcessExecutionException(parametersSaveStatus.getReason());
			}
		}
		else
		{
			saved = true;
		}

		return saved;
	}
}
