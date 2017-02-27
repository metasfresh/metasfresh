package de.metas.ui.web.process.descriptor;

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
import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.i18n.IModelTranslationMap;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessParams;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.descriptor.ProcessDescriptor.ProcessDescriptorType;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.factory.standard.DefaultValueExpressionsFactory;
import de.metas.ui.web.window.descriptor.factory.standard.DescriptorsFactoryHelper;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.DocumentsRepository;

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

@Service
public class ProcessDescriptorsFactory
{
	// services
	// private static final transient Logger logger = LogManager.getLogger(ProcessDescriptorsFactory.class);
	private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
	private final transient DefaultValueExpressionsFactory defaultValueExpressions = new DefaultValueExpressionsFactory(false);
	private final transient IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final transient IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final CCache<Integer, ProcessDescriptor> processDescriptorsByProcessId = CCache.newLRUCache(I_AD_Process.Table_Name + "#Descriptors#by#AD_Process_ID", 200, 0);

	@Autowired
	private UserSession userSession;

	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final IProcessPreconditionsContext preconditionsContext)
	{
		final String tableName = preconditionsContext.getTableName();
		final int adTableId = adTableDAO.retrieveTableId(tableName);
		final IUserRolePermissions userRolePermissions = userSession.getUserRolePermissions();
		return adProcessDAO.retrieveRelatedProcessesForTableIndexedByProcessId(Env.getCtx(), adTableId)
				.values()
				.stream()
				.filter(relatedProcess -> relatedProcess.isExecutionGranted(userRolePermissions)) // only those which can be executed by current user permissions
				.map(relatedProcess -> toWebuiRelatedProcessDescriptor(relatedProcess, preconditionsContext));
	}

	private WebuiRelatedProcessDescriptor toWebuiRelatedProcessDescriptor(final RelatedProcessDescriptor relatedProcess, final IProcessPreconditionsContext preconditionsContext)
	{
		final int adProcessId = relatedProcess.getAD_Process_ID();
		final ProcessDescriptor processDescriptor = getProcessDescriptor(adProcessId);
		final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier = () -> processDescriptor.checkPreconditionsApplicable(preconditionsContext);
		return WebuiRelatedProcessDescriptor.of(relatedProcess, processDescriptor, preconditionsResolutionSupplier);
	}

	public ProcessDescriptor getProcessDescriptor(final int adProcessId)
	{
		return processDescriptorsByProcessId.getOrLoad(adProcessId, () -> retrieveProcessDescriptor(adProcessId));
	}

	private ProcessDescriptor retrieveProcessDescriptor(final int adProcessId)
	{
		final I_AD_Process adProcess = InterfaceWrapperHelper.create(Env.getCtx(), adProcessId, I_AD_Process.class, ITrx.TRXNAME_None);
		if (adProcess == null)
		{
			throw new EntityNotFoundException("@NotFound@ @AD_Process_ID@ (" + adProcessId + ")");
		}

		final WebuiProcessClassInfo webuiProcesClassInfo = WebuiProcessClassInfo.of(adProcess.getClassname());

		final IModelTranslationMap adProcessTrlsMap = InterfaceWrapperHelper.getModelTranslationMap(adProcess);

		final ProcessLayout.Builder layout = ProcessLayout.builder()
				.setAD_Process_ID(adProcessId)
				.setCaption(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Name, adProcess.getName()))
				.setDescription(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Description, adProcess.getDescription()));

		final DocumentEntityDescriptor.Builder parametersDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.Process, adProcessId)
				.setCaption(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Name, adProcess.getName()))
				.setDescription(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Description, adProcess.getDescription()))
				.setDataBinding(ProcessParametersDataBindingDescriptorBuilder.instance)
				.disableDefaultTableCallouts();

		// Get AD_Process_Para(s) and populate the entity descriptor and the layout
		adProcessDAO.retrieveProcessParameters(adProcess)
				.forEach(adProcessParam -> {
					final DocumentFieldDescriptor.Builder processParaDescriptor = createProcessParaDescriptor(webuiProcesClassInfo, adProcessParam);
					parametersDescriptor.addField(processParaDescriptor);
					layout.addElement(createLayoutElement(processParaDescriptor));
				});

		return ProcessDescriptor.builder()
				.setAD_Process_ID(adProcessId)
				.setType(extractType(adProcess))
				.setProcessClassname(extractClassnameOrNull(adProcess))
				.setParametersDescriptor(parametersDescriptor.build())
				.setLayout(layout.build())
				.build();
	}

	private DocumentFieldDescriptor.Builder createProcessParaDescriptor(final WebuiProcessClassInfo webuiProcesClassInfo, final I_AD_Process_Para adProcessParam)
	{
		final IModelTranslationMap adProcessParaTrlsMap = InterfaceWrapperHelper.getModelTranslationMap(adProcessParam);
		final String parameterName = adProcessParam.getColumnName();
		final boolean isParameterTo = false; // TODO: implement range parameters support
		
		//
		// Ask the provider if it has some custom lookup descriptor
		LookupDescriptorProvider lookupDescriptorProvider = webuiProcesClassInfo.getLookupDescriptorProviderOrNull(parameterName);
		// Fallback: create an SQL lookup descriptor based on adProcessParam
		if (lookupDescriptorProvider == null)
		{
			lookupDescriptorProvider = SqlLookupDescriptor.builder()
					.setColumnName(parameterName)
					.setDisplayType(adProcessParam.getAD_Reference_ID())
					.setAD_Reference_Value_ID(adProcessParam.getAD_Reference_Value_ID())
					.setAD_Val_Rule_ID(adProcessParam.getAD_Val_Rule_ID())
					.buildProvider();
		}
		//
		final LookupDescriptor lookupDescriptor = lookupDescriptorProvider.provideForScope(LookupDescriptorProvider.LookupScope.DocumentField);

		final DocumentFieldWidgetType widgetType = DescriptorsFactoryHelper.extractWidgetType(parameterName, adProcessParam.getAD_Reference_ID(), lookupDescriptor);
		final Class<?> valueClass = DescriptorsFactoryHelper.getValueClass(widgetType, lookupDescriptor);

		final ILogicExpression readonlyLogic = expressionFactory.compileOrDefault(adProcessParam.getReadOnlyLogic(), ConstantLogicExpression.FALSE, ILogicExpression.class);
		final ILogicExpression displayLogic = expressionFactory.compileOrDefault(adProcessParam.getDisplayLogic(), ConstantLogicExpression.TRUE, ILogicExpression.class);
		final ILogicExpression mandatoryLogic = ConstantLogicExpression.of(adProcessParam.isMandatory());

		final Optional<IExpression<?>> defaultValueExpr = defaultValueExpressions.extractDefaultValueExpression(
				adProcessParam.getDefaultValue() //
				, parameterName //
				, widgetType //
				, valueClass //
				, mandatoryLogic.isConstantTrue() //
		);

		final DocumentFieldDescriptor.Builder paramDescriptor = DocumentFieldDescriptor.builder(parameterName)
				.setCaption(adProcessParaTrlsMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Name, adProcessParam.getName()))
				.setDescription(adProcessParaTrlsMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Description, adProcessParam.getDescription()))
				// .setHelp(adProcessParaTrlsMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Help, adProcessParam.getHelp()))
				//
				.setValueClass(valueClass)
				.setWidgetType(widgetType)
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
		if(webuiProcesClassInfo.isForwardValueToJavaProcessInstance(parameterName, isParameterTo))
		{
			paramDescriptor.addCallout(calloutField -> forwardValueToCurrentProcessInstance(calloutField, isParameterTo));
		}
		
		return paramDescriptor;
	}

	private static final void forwardValueToCurrentProcessInstance(final ICalloutField calloutField, final boolean isParameterTo)
	{
		final JavaProcess processInstance = JavaProcess.currentInstance();

		final String parameterName = calloutField.getColumnName();

		//
		// Build up our value source
		Object parameterValue = calloutField.getValue();
		if(parameterValue instanceof LookupValue)
		{
			parameterValue = ((LookupValue)parameterValue).getId();
		}
		final IRangeAwareParams source = ProcessParams.ofValue(parameterName, parameterValue);

		// Ask the instance to load the parameter
		processInstance.loadParameterValueNoFail(parameterName, isParameterTo, source);
	}

	private static DocumentLayoutElementDescriptor.Builder createLayoutElement(final DocumentFieldDescriptor.Builder processParaDescriptor)
	{
		return DocumentLayoutElementDescriptor.builder()
				.setCaption(processParaDescriptor.getCaption())
				.setDescription(processParaDescriptor.getDescription())
				.setWidgetType(processParaDescriptor.getWidgetType())
				.addField(DocumentLayoutElementFieldDescriptor.builder(processParaDescriptor.getFieldName())
						.setLookupSource(processParaDescriptor.getLookupSourceType())
						.setPublicField(true));
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

	private static final class ProcessParametersDataBindingDescriptorBuilder implements DocumentEntityDataBindingDescriptorBuilder
	{
		public static final transient ProcessParametersDataBindingDescriptorBuilder instance = new ProcessParametersDataBindingDescriptorBuilder();

		private static final DocumentEntityDataBindingDescriptor dataBinding = new DocumentEntityDataBindingDescriptor()
		{
			@Override
			public DocumentsRepository getDocumentsRepository()
			{
				return ProcessParametersRepository.instance;
			}

		};

		@Override
		public DocumentEntityDataBindingDescriptor getOrBuild()
		{
			return dataBinding;
		}
	}
}
