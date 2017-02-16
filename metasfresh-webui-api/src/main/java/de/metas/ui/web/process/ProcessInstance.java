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
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutionResult.RecordsToOpen;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.exceptions.ProcessExecutionException;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewsRepository;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest.JSONReferencing;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * WEBUI Process instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ProcessInstance
{
	private static final transient Logger logger = LogManager.getLogger(ProcessInstance.class);

	public static final String PARAM_ViewId = "$WEBUI_ViewId";

	@Autowired
	@Lazy
	private IDocumentViewsRepository documentViewsRepo;

	private final ProcessDescriptor processDescriptor;
	private final DocumentId adPInstanceId;
	private final Document parameters;

	private boolean executed = false;
	private ProcessInstanceResult executionResult;

	/** New instance constructor */
	/* package */ ProcessInstance(final ProcessDescriptor processDescriptor, final DocumentId adPInstanceId, final Document parameters)
	{
		super();
		Adempiere.autowire(this);

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

		// Adempiere.autowire(this);
		documentViewsRepo = from.documentViewsRepo;

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

	public DocumentId getAD_PInstance_ID()
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
			// shall not happen because the method throws the exception in case of failure
			throw new ProcessExecutionException("Instance could not be saved because it's not valid");
		}

		//
		// Create the process info and execute the process synchronously
		final Properties ctx = Env.getCtx(); // We assume the right context was already used when the process was loaded
		final ProcessDescriptor processDescriptor = getDescriptor();
		final String adLanguage = Env.getAD_Language(ctx);
		final String name = processDescriptor.getCaption(adLanguage);
		final ProcessExecutor processExecutor = ProcessInfo.builder()
				.setCtx(ctx)
				.setCreateTemporaryCtx()
				.setAD_PInstance_ID(getAD_PInstance_ID().toInt())
				.setTitle(name)
				.setPrintPreview(true)
				.setJRDesiredOutputType(OutputType.PDF)
				//
				// Execute the process/report
				.buildAndPrepareExecution()
				.executeSync();
		final ProcessExecutionResult processExecutionResult = processExecutor.getResult();

		//
		// Build and return the execution result
		{
			String summary = processExecutionResult.getSummary();
			if (Check.isEmpty(summary, true) || JavaProcess.MSG_OK.equals(summary))
			{
				// hide summary if empty or MSG_OK (which is the most used non-message)
				summary = null;
			}

			final ProcessInstanceResult.Builder resultBuilder = ProcessInstanceResult.builder()
					.setAD_PInstance_ID(processExecutionResult.getAD_PInstance_ID())
					.setSummary(summary)
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
			updateRecordsToOpen(resultBuilder, processExecutor.getProcessInfo(), processExecutionResult.getRecordsToOpen());

			//
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

	private void updateRecordsToOpen(final ProcessInstanceResult.Builder resultBuilder, final ProcessInfo processInfo, final RecordsToOpen recordsToOpen)
	{
		if (recordsToOpen == null)
		{
			return;
		}
		//
		// View
		else if (recordsToOpen.isGridView())
		{
			final IDocumentViewSelection view = createView(processInfo, recordsToOpen);
			resultBuilder.openView(view.getAD_Window_ID(), view.getViewId());
		}
		//
		// Single document
		else
		{
			final DocumentPath documentPath = extractSingleDocumentPath(recordsToOpen);
			resultBuilder.openSingleDocument(documentPath);
		}

	}

	private final IDocumentViewSelection createView(final ProcessInfo processInfo, final RecordsToOpen recordsToOpen)
	{
		final List<TableRecordReference> recordRefs = recordsToOpen.getRecords();
		if (recordRefs.isEmpty())
		{
			return null; // shall not happen
		}

		final int adWindowId_Override = recordsToOpen.getAD_Window_ID(); // optional

		//
		// Create view create request builders from current records
		final Map<Integer, JSONCreateDocumentViewRequest.Builder> viewRequestBuilders = new HashMap<>();
		for (final TableRecordReference recordRef : recordRefs)
		{
			final int recordWindowId = adWindowId_Override > 0 ? adWindowId_Override : RecordZoomWindowFinder.findAD_Window_ID(recordRef);
			final JSONCreateDocumentViewRequest.Builder viewRequestBuilder = viewRequestBuilders.computeIfAbsent(recordWindowId, key -> JSONCreateDocumentViewRequest.builder(recordWindowId, JSONViewDataType.grid));

			viewRequestBuilder.addFilterOnlyId(recordRef.getRecord_ID());
		}
		// If there is no view create request builder there stop here (shall not happen)
		if (viewRequestBuilders.isEmpty())
		{
			return null;
		}

		//
		// Create the view create request from first builder that we have.
		if (viewRequestBuilders.size() > 1)
		{
			logger.warn("More than one views to be created found for {}. Creating only the first view.", recordRefs);
		}
		final JSONCreateDocumentViewRequest viewRequest = viewRequestBuilders.values().iterator().next()
				.setReferencing(extractJSONReferencing(processInfo))
				.build();

		//
		// Create the view and set its ID to our process result.
		final IDocumentViewSelection view = documentViewsRepo.createView(viewRequest);
		return view;
	}

	private static final JSONReferencing extractJSONReferencing(final ProcessInfo processInfo)
	{
		final TableRecordReference sourceRecordRef = processInfo.getRecordRefOrNull();
		if (sourceRecordRef == null)
		{
			return null;
		}

		final int adWindowId = RecordZoomWindowFinder.findAD_Window_ID(sourceRecordRef);
		return JSONReferencing.of(adWindowId, sourceRecordRef.getRecord_ID());
	}

	private static final DocumentPath extractSingleDocumentPath(final RecordsToOpen recordsToOpen)
	{
		final TableRecordReference recordRef = recordsToOpen.getSingleRecord();
		final int documentId = recordRef.getRecord_ID();

		int adWindowId = recordsToOpen.getAD_Window_ID();
		if (adWindowId <= 0)
		{
			adWindowId = RecordZoomWindowFinder.findAD_Window_ID(recordRef);
		}

		return DocumentPath.rootDocumentPath(DocumentType.Window, adWindowId, documentId);
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
