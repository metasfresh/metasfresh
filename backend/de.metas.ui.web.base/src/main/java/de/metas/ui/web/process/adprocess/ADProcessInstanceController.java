package de.metas.ui.web.process.adprocess;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.report.server.OutputType;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.IProcessInstanceParameter;
import de.metas.ui.web.process.ProcessExecutionContext;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.exceptions.ProcessExecutionException;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentSaveStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import static de.metas.report.server.ReportConstants.REPORT_PARAM_REPORT_FORMAT;

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
 * WEBUI AD_Process based process instance controller
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */final class ADProcessInstanceController implements IProcessInstanceController
{
	private static final Logger logger = LogManager.getLogger(ADProcessInstanceController.class);

	@Getter
	private final DocumentId instanceId;

	private final ITranslatableString caption;
	private final Document parameters;
	private final Object processClassInstance;

	@Getter
	private final boolean startProcessDirectly;

	@Getter
	private final ViewId viewId;

	private final DocumentPath contextSingleDocumentPath;

	private boolean executed = false;
	private ProcessInstanceResult executionResult;

	private final ReentrantReadWriteLock readwriteLock;

	/** New instance constructor */
	@Builder
	private ADProcessInstanceController(
			@NonNull final ITranslatableString caption,
			@NonNull final DocumentId instanceId,
			@Nullable final Document parameters,
			@Nullable final Object processClassInstance,
			@NonNull final Boolean startProcessDirectly,
			@Nullable final DocumentPath contextSingleDocumentPath,
			//
			@Nullable final ViewId viewId)
	{
		this.caption = caption;
		this.instanceId = instanceId;
		this.parameters = parameters;
		this.processClassInstance = processClassInstance;
		this.startProcessDirectly = startProcessDirectly;

		this.viewId = viewId;

		this.contextSingleDocumentPath = contextSingleDocumentPath;

		executed = false;
		executionResult = null;

		readwriteLock = new ReentrantReadWriteLock();
	}

	/** Copy constructor */
	private ADProcessInstanceController(final ADProcessInstanceController from, final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		instanceId = from.instanceId;

		caption = from.caption;
		parameters = from.parameters.copy(copyMode, changesCollector);
		processClassInstance = from.processClassInstance;
		startProcessDirectly = from.startProcessDirectly;

		viewId = from.viewId;

		contextSingleDocumentPath = from.contextSingleDocumentPath;

		executed = from.executed;
		executionResult = from.executionResult;

		readwriteLock = from.readwriteLock; // always share
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_PInstance_ID", instanceId)
				.add("executed", "executed")
				.add("executionResult", executionResult)
				.add("caption", caption)
				.toString();
	}

	private Document getParametersDocument()
	{
		return parameters;
	}

	@Override
	public Collection<IProcessInstanceParameter> getParameters()
	{
		return parameters.getFieldViews()
				.stream()
				.map(DocumentFieldAsProcessInstanceParameter::of)
				.collect(ImmutableList.toImmutableList());
	}

	public ADProcessInstanceController copyReadonly()
	{
		return new ADProcessInstanceController(this, CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance);
	}

	public ADProcessInstanceController copyReadWrite(final IDocumentChangesCollector changesCollector)
	{
		return new ADProcessInstanceController(this, CopyMode.CheckOutWritable, changesCollector);
	}

	public ADProcessInstanceController bindContextSingleDocumentIfPossible(@NonNull final DocumentCollection documentsCollection)
	{
		if (contextSingleDocumentPath == null)
		{
			return this;
		}
		if (!documentsCollection.isWindowIdSupported(contextSingleDocumentPath.getWindowIdOrNull()))
		{
			return this;
		}

		final Document contextSingleDocument = documentsCollection.getDocumentReadonly(contextSingleDocumentPath);
		getParametersDocument().setShadowParentDocumentEvaluatee(contextSingleDocument.asEvaluatee());

		return this;
	}

	public IAutoCloseable activate()
	{
		return JavaProcess.temporaryChangeCurrentInstance(processClassInstance);
	}

	@Override
	public LookupValuesList getParameterLookupValues(final String parameterName)
	{
		return parameters.getFieldLookupValues(parameterName);
	}

	@Override
	public LookupValuesPage getParameterLookupValuesForQuery(final String parameterName, final String query)
	{
		return parameters.getFieldLookupValuesForQuery(parameterName, query);
	}

	@Override
	public void processParameterValueChanges(final List<JSONDocumentChangedEvent> events, final ReasonSupplier reason)
	{
		assertNotExecuted();
		parameters.processValueChanges(events, reason);
		updateParametersDocumentFromJavaProcessAnnotatedFields();
	}

	private void updateParametersDocumentFromJavaProcessAnnotatedFields()
	{
		final JavaProcess currentProcessInstance = JavaProcess.currentInstance();

		// If the process has no callouts,
		// there is no point to update the parameters Document from process's annotated fields values,
		// because nobody will change those process's annotated fields values directly.
		if (!currentProcessInstance.hasParametersCallout())
		{
			return;
		}

		final IRangeAwareParams parameterFieldValues = currentProcessInstance.getParametersFromAnnotatedFields();
		for (final String parameterName : parameterFieldValues.getParameterNames())
		{
			if (!parameters.hasField(parameterName))
			{
				continue;
			}

			final Object value = parameterFieldValues.getParameterAsObject(parameterName);
			updateParametersDocumentFromJavaProcessAnnotatedFields(parameterName, value);
		}
	}

	private void updateParametersDocumentFromJavaProcessAnnotatedFields(
			@NonNull final String parameterName,
			@Nullable final Object value)
	{
		Object valueNorm;
		if (value == null)
		{
			valueNorm = null;
		}
		else if (InterfaceWrapperHelper.isModelInterface(value.getClass()))
		{
			int id = InterfaceWrapperHelper.getId(value);
			valueNorm = id;
		}
		else
		{
			valueNorm = value;
		}

		final boolean ignoreReadonlyFlag = true;
		parameters.processValueChange(
				parameterName,
				valueNorm,
				() -> "update from java process annotated fields",
				ignoreReadonlyFlag);
	}

	@Override
	public ProcessInstanceResult getExecutionResult()
	{
		final ProcessInstanceResult executionResult = this.executionResult;
		if (executionResult == null)
		{
			throw new AdempiereException("Process instance does not have an execution result yet: " + this);
		}
		return executionResult;
	}

	private boolean isExecuted()
	{
		return executed;
	}

	/* package */ void assertNotExecuted()
	{
		if (isExecuted())
		{
			throw new AdempiereException("Process already executed");
		}
	}

	@Override
	public ProcessInstanceResult startProcess(@NonNull final ProcessExecutionContext context)
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
		executionResult = executeADProcess(context);
		if (executionResult.isSuccess())
		{
			executed = false;
		}
		logger.debug("executionResult.success={} executionResult.summary={}", executionResult.isSuccess(), executionResult.getSummary());
		return executionResult;
	}

	private ProcessInstanceResult executeADProcess(@NonNull final ProcessExecutionContext context)
	{
		//
		// Create the process info and execute the process synchronously
		final ProcessExecutor processExecutor = ProcessInfo.builder()
				.setCtx(context.getCtx())
				.setCreateTemporaryCtx()
				.setPInstanceId(PInstanceId.ofRepoId(getInstanceId().toInt()))
				.setTitle(caption.translate(context.getAdLanguage()))
				.setPrintPreview(true)
				.setJRDesiredOutputType(getTargetOutputType())
				//
				// Execute the process/report
				.buildAndPrepareExecution()
				.onErrorThrowException() // throw exception directly... this will allow the original exception (including exception params) to be sent back to frontend
				.executeSync();

		final ADProcessPostProcessService postProcessService = ADProcessPostProcessService.builder()
				.viewsRepo(context.getViewsRepo())
				.documentsCollection(context.getDocumentsCollection())
				.build();
		return postProcessService.postProcess(ADProcessPostProcessRequest.builder()
				.viewId(getViewId())
				.processInfo(processExecutor.getProcessInfo())
				.processExecutionResult(processExecutor.getResult())
				.build());
	}

	/* package */boolean saveIfValidAndHasChanges(final boolean throwEx)
	{
		final Document parametersDocument = getParametersDocument();
		final DocumentSaveStatus parametersSaveStatus = parametersDocument.saveIfValidAndHasChanges();
		final boolean saved = parametersSaveStatus.isSaved();
		if (!saved && throwEx)
		{
			throw new ProcessExecutionException(parametersSaveStatus.getReason());
		}

		return saved;
	}

	/* package */ IAutoCloseable lockForReading()
	{
		final ReadLock readLock = readwriteLock.readLock();
		logger.debug("Acquiring read lock for {}: {}", this, readLock);
		readLock.lock();
		logger.debug("Acquired read lock for {}: {}", this, readLock);

		return () -> {
			readLock.unlock();
			logger.debug("Released read lock for {}: {}", this, readLock);
		};
	}

	/* package */ IAutoCloseable lockForWriting()
	{
		final WriteLock writeLock = readwriteLock.writeLock();
		logger.debug("Acquiring write lock for {}: {}", this, writeLock);
		writeLock.lock();
		logger.debug("Acquired write lock for {}: {}", this, writeLock);

		return () -> {
			writeLock.unlock();
			logger.debug("Released write lock for {}: {}", this, writeLock);
		};
	}

	/**
	 * Try to get the target output type from the specific process param {@link de.metas.report.server.ReportConstants#REPORT_PARAM_REPORT_FORMAT},
	 * if the parameter is missing, the default output type is returned.
	 *
	 * @see OutputType#getDefault()
	 */
	private OutputType getTargetOutputType()
	{
		final IDocumentFieldView formatField = parameters.getFieldViewOrNull(REPORT_PARAM_REPORT_FORMAT);

		if (formatField != null)
		{
			final LookupValue outputTypeParamValue = formatField.getValueAs(LookupValue.class);

			if (outputTypeParamValue != null)
			{
				final Optional<OutputType> targetOutputType = OutputType.getOutputTypeByFileExtension(String.valueOf(formatField.getValueAs(LookupValue.class).getId()));

				if (targetOutputType.isPresent())
				{
					return targetOutputType.get();
				}
			}
		}

		return OutputType.getDefault();
	}
}
