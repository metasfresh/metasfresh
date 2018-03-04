package de.metas.ui.web.process.adprocess;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.util.CCache;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IModelTranslationMap;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessParams;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.WebuiPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.ProcessDescriptor.ProcessDescriptorType;
import de.metas.ui.web.process.descriptor.ProcessLayout;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.window.datatypes.DateRangeValue;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.factory.standard.DefaultValueExpressionsFactory;
import de.metas.ui.web.window.descriptor.factory.standard.DescriptorsFactoryHelper;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.DocumentsRepository;
import lombok.Builder;
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

/**
 * Creates {@link ProcessDescriptor}s from {@link I_AD_Process} based processes
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ class ADProcessDescriptorsFactory
{
	// services
	// private static final transient Logger logger = LogManager.getLogger(ProcessDescriptorsFactory.class);
	private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
	private final transient DefaultValueExpressionsFactory defaultValueExpressions = DefaultValueExpressionsFactory.newInstance();
	private final transient IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final transient IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final CCache<ProcessId, ProcessDescriptor> processDescriptorsByProcessId = CCache.newLRUCache(I_AD_Process.Table_Name + "#Descriptors#by#AD_Process_ID", 200, 0);

	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final WebuiPreconditionsContext preconditionsContext, final IUserRolePermissions userRolePermissions)
	{
		final String tableName = preconditionsContext.getTableName();
		final int adTableId = !Check.isEmpty(tableName) ? adTableDAO.retrieveTableId(tableName) : -1;

		final int adWindowId = preconditionsContext.getAD_Window_ID();

		final Stream<RelatedProcessDescriptor> relatedProcessDescriptors;
		{
			final Stream<RelatedProcessDescriptor> tableRelatedProcessDescriptors = adProcessDAO.retrieveRelatedProcessesForTableIndexedByProcessId(Env.getCtx(), adTableId, adWindowId)
					.values()
					.stream();
			final Stream<RelatedProcessDescriptor> additionalRelatedProcessDescriptors = preconditionsContext.getAdditionalRelatedProcessDescriptors()
					.stream();

			relatedProcessDescriptors = Stream.concat(additionalRelatedProcessDescriptors, tableRelatedProcessDescriptors)
					.collect(GuavaCollectors.distinctBy(RelatedProcessDescriptor::getProcessId));
		}

		return relatedProcessDescriptors
				.filter(relatedProcess -> relatedProcess.isExecutionGranted(userRolePermissions)) // only those which can be executed by current user permissions
				.map(relatedProcess -> toWebuiRelatedProcessDescriptor(relatedProcess, preconditionsContext));
	}

	private WebuiRelatedProcessDescriptor toWebuiRelatedProcessDescriptor(@NonNull final RelatedProcessDescriptor relatedProcessDescriptor, @NonNull final IProcessPreconditionsContext preconditionsContext)
	{
		final ProcessId processId = ProcessId.ofAD_Process_ID(relatedProcessDescriptor.getProcessId());
		final ProcessDescriptor processDescriptor = getProcessDescriptor(processId);
		final ProcessPreconditionsResolutionSupplier preconditionsResolutionSupplier = ProcessPreconditionsResolutionSupplier.builder()
				.preconditionsContext(preconditionsContext)
				.processPreconditionsCheckers(relatedProcessDescriptor.getProcessPreconditionsCheckers())
				.processDescriptor(processDescriptor)
				.build();

		return WebuiRelatedProcessDescriptor.builder()
				.processId(processDescriptor.getProcessId())
				.processCaption(processDescriptor.getCaption())
				.processDescription(processDescriptor.getDescription())
				.debugProcessClassname(processDescriptor.getProcessClassname())
				//
				.quickAction(relatedProcessDescriptor.isWebuiQuickAction())
				.defaultQuickAction(relatedProcessDescriptor.isWebuiDefaultQuickAction())
				//
				.preconditionsResolutionSupplier(preconditionsResolutionSupplier)
				//
				.build();
	}

	public ProcessDescriptor getProcessDescriptor(final ProcessId processId)
	{
		return processDescriptorsByProcessId.getOrLoad(processId, () -> retrieveProcessDescriptor(processId));
	}

	private ProcessDescriptor retrieveProcessDescriptor(final ProcessId processId)
	{
		final I_AD_Process adProcess = InterfaceWrapperHelper.create(Env.getCtx(), processId.getProcessIdAsInt(), I_AD_Process.class, ITrx.TRXNAME_None);
		if (adProcess == null)
		{
			throw new EntityNotFoundException("@NotFound@ @AD_Process_ID@ (" + processId + ")");
		}

		final WebuiProcessClassInfo webuiProcesClassInfo = WebuiProcessClassInfo.of(adProcess.getClassname());

		final IModelTranslationMap adProcessTrlsMap = InterfaceWrapperHelper.getModelTranslationMap(adProcess);

		//
		// Parameters document descriptor
		final DocumentEntityDescriptor parametersDescriptor;
		{
			final DocumentEntityDescriptor.Builder parametersDescriptorBuilder = DocumentEntityDescriptor.builder()
					.setDocumentType(DocumentType.Process, processId.toDocumentId())
					.setCaption(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Name, adProcess.getName()))
					.setDescription(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Description, adProcess.getDescription()))
					.setDataBinding(ProcessParametersDataBindingDescriptorBuilder.instance)
					.disableDefaultTableCallouts();

			// Get AD_Process_Para(s) and populate the entity descriptor
			adProcessDAO.retrieveProcessParameters(adProcess)
					.stream()
					.map(adProcessParam -> createProcessParaDescriptor(webuiProcesClassInfo, adProcessParam))
					.forEach(processParaDescriptor -> parametersDescriptorBuilder.addField(processParaDescriptor));

			parametersDescriptor = parametersDescriptorBuilder.build();
		}

		//
		// Parameters layout
		final ProcessLayout.Builder layout = ProcessLayout.builder()
				.setProcessId(processId)
				.setLayoutType(webuiProcesClassInfo.getLayoutType())
				.setCaption(parametersDescriptor.getCaption())
				.setDescription(parametersDescriptor.getDescription())
				.addElements(parametersDescriptor);

		//
		// Process descriptor
		return ProcessDescriptor.builder()
				.setProcessId(processId)
				.setType(extractType(adProcess))
				.setProcessClassname(extractClassnameOrNull(adProcess))
				.setParametersDescriptor(parametersDescriptor)
				.setLayout(layout.build())
				.build();
	}

	private DocumentFieldDescriptor.Builder createProcessParaDescriptor(final WebuiProcessClassInfo webuiProcesClassInfo, final I_AD_Process_Para adProcessParam)
	{
		final IModelTranslationMap adProcessParaTrlsMap = InterfaceWrapperHelper.getModelTranslationMap(adProcessParam);
		final String parameterName = adProcessParam.getColumnName();

		//
		// Ask the provider if it has some custom lookup descriptor
		LookupDescriptorProvider lookupDescriptorProvider = webuiProcesClassInfo.getLookupDescriptorProviderOrNull(parameterName);
		// Fallback: create an SQL lookup descriptor based on adProcessParam
		if (lookupDescriptorProvider == null)
		{
			lookupDescriptorProvider = SqlLookupDescriptor.builder()
					.setCtxTableName(null)
					.setCtxColumnName(parameterName)
					.setDisplayType(adProcessParam.getAD_Reference_ID())
					.setAD_Reference_Value_ID(adProcessParam.getAD_Reference_Value_ID())
					.setAD_Val_Rule_ID(adProcessParam.getAD_Val_Rule_ID())
					.setReadOnlyAccess()
					.buildProvider();
		}
		//
		final LookupDescriptor lookupDescriptor = lookupDescriptorProvider.provideForScope(LookupDescriptorProvider.LookupScope.DocumentField);

		final DocumentFieldWidgetType widgetType = extractWidgetType(parameterName, adProcessParam.getAD_Reference_ID(), lookupDescriptor, adProcessParam.isRange());
		final Class<?> valueClass = DescriptorsFactoryHelper.getValueClass(widgetType, lookupDescriptor);
		final boolean allowShowPassword = widgetType == DocumentFieldWidgetType.Password ? true : false; // process parameters shall always allow displaying the password

		final ILogicExpression readonlyLogic = expressionFactory.compileOrDefault(adProcessParam.getReadOnlyLogic(), ConstantLogicExpression.FALSE, ILogicExpression.class);
		final ILogicExpression displayLogic = expressionFactory.compileOrDefault(adProcessParam.getDisplayLogic(), ConstantLogicExpression.TRUE, ILogicExpression.class);
		final ILogicExpression mandatoryLogic = ConstantLogicExpression.of(adProcessParam.isMandatory());

		final Optional<IExpression<?>> defaultValueExpr = defaultValueExpressions.extractDefaultValueExpression(
				adProcessParam.getDefaultValue(),
				parameterName,
				widgetType,
				valueClass,
				mandatoryLogic.isConstantTrue(),
				false // don't allow using auto sequence
		);

		final DocumentFieldDescriptor.Builder paramDescriptor = DocumentFieldDescriptor.builder(parameterName)
				.setCaption(adProcessParaTrlsMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Name, adProcessParam.getName()))
				.setDescription(adProcessParaTrlsMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Description, adProcessParam.getDescription()))
				// .setHelp(adProcessParaTrlsMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Help, adProcessParam.getHelp()))
				//
				.setValueClass(valueClass)
				.setWidgetType(widgetType)
				.setAllowShowPassword(allowShowPassword)
				.setLookupDescriptorProvider(lookupDescriptorProvider)
				//
				.setDefaultValueExpression(defaultValueExpr)
				.setReadonlyLogic(readonlyLogic)
				.setDisplayLogic(displayLogic)
				.setMandatoryLogic(mandatoryLogic)
				//
				.addCharacteristic(Characteristic.PublicField)
		//
		;

		// Add a callout to forward process parameter value (UI) to current process instance
		if (webuiProcesClassInfo.isForwardValueToJavaProcessInstance(parameterName))
		{
			paramDescriptor.addCallout(ProcessParametersCallout::forwardValueToCurrentProcessInstance);
		}

		return paramDescriptor;
	}

	private static DocumentFieldWidgetType extractWidgetType(final String parameterName, final int adReferenceId, final LookupDescriptor lookupDescriptor, final boolean isRange)
	{
		final DocumentFieldWidgetType widgetType = DescriptorsFactoryHelper.extractWidgetType(parameterName, adReferenceId, lookupDescriptor);

		// Date range:
		if (isRange && widgetType == DocumentFieldWidgetType.Date)
		{
			return DocumentFieldWidgetType.DateRange;
		}
		// Others
		else
		{
			return widgetType;
		}
	}

	private static final ProcessDescriptorType extractType(final I_AD_Process adProcess)
	{
		if (adProcess.getAD_Form_ID() > 0)
		{
			return ProcessDescriptorType.Form;
		}
		else if (adProcess.getAD_Workflow_ID() > 0)
		{
			return ProcessDescriptorType.Workflow;
		}
		else if (adProcess.isReport())
		{
			return ProcessDescriptorType.Report;
		}
		else
		{
			return ProcessDescriptorType.Process;
		}
	}

	private static final String extractClassnameOrNull(final I_AD_Process adProcess)
	{
		//
		// First try: Check process classname
		if (!Check.isEmpty(adProcess.getClassname(), true))
		{
			return adProcess.getClassname();
		}

		//
		// Second try: form classname (05089)
		final I_AD_Form form = adProcess.getAD_Form();
		if (form != null && !Check.isEmpty(form.getClassname(), true))
		{
			return form.getClassname();
		}

		return null;
	}

	private static final class ProcessPreconditionsResolutionSupplier implements Supplier<ProcessPreconditionsResolution>
	{
		private final IProcessPreconditionsContext preconditionsContext;
		private final ImmutableList<IProcessPrecondition> processPreconditionsCheckers;
		private final ProcessDescriptor processDescriptor;

		@Builder
		private ProcessPreconditionsResolutionSupplier(
				@NonNull final IProcessPreconditionsContext preconditionsContext,
				final List<IProcessPrecondition> processPreconditionsCheckers,
				@NonNull final ProcessDescriptor processDescriptor)
		{
			this.preconditionsContext = preconditionsContext;
			this.processPreconditionsCheckers = !processPreconditionsCheckers.isEmpty() ? ImmutableList.copyOf(processPreconditionsCheckers) : ImmutableList.of();
			this.processDescriptor = processDescriptor;
		}

		@Override
		public ProcessPreconditionsResolution get()
		{
			//
			// Check registered preconditions
			final ProcessPreconditionsResolution rejectResolution = processPreconditionsCheckers.stream()
					.map(processPreconditionsChecker -> processPreconditionsChecker.checkPreconditionsApplicable(preconditionsContext))
					.filter(resolution -> !resolution.isAccepted())
					.findFirst()
					.orElse(null);
			if (rejectResolution != null)
			{
				return rejectResolution;
			}

			//
			// Ask the process descriptor
			return processDescriptor.checkPreconditionsApplicable(preconditionsContext);
		}

	}

	private static final class ProcessParametersCallout
	{
		private static final void forwardValueToCurrentProcessInstance(final ICalloutField calloutField)
		{
			final JavaProcess processInstance = JavaProcess.currentInstance();

			final String parameterName = calloutField.getColumnName();

			//
			// Build up our value source
			final IRangeAwareParams source;
			final Object fieldValue = calloutField.getValue();
			if (fieldValue instanceof LookupValue)
			{
				final Object idObj = ((LookupValue)fieldValue).getId();
				source = ProcessParams.ofValueObject(parameterName, idObj);
			}
			else if (fieldValue instanceof DateRangeValue)
			{
				final DateRangeValue dateRange = (DateRangeValue)fieldValue;
				source = ProcessParams.of(parameterName, dateRange.getFrom(), dateRange.getTo());
			}
			else
			{
				source = ProcessParams.ofValueObject(parameterName, fieldValue);
			}

			// Ask the instance to load the parameter
			processInstance.loadParameterValueNoFail(parameterName, source);
		}
	}

	private static final class ProcessParametersDataBindingDescriptorBuilder implements DocumentEntityDataBindingDescriptorBuilder
	{
		public static final transient ProcessParametersDataBindingDescriptorBuilder instance = new ProcessParametersDataBindingDescriptorBuilder();

		private static final DocumentEntityDataBindingDescriptor dataBinding = new DocumentEntityDataBindingDescriptor()
		{
			@Override
			public DocumentsRepository getDocumentsRepository()
			{
				return ADProcessParametersRepository.instance;
			}

		};

		@Override
		public DocumentEntityDataBindingDescriptor getOrBuild()
		{
			return dataBinding;
		}
	}
}
