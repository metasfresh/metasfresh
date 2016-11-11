package de.metas.ui.web.process.descriptor;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.IADProcessDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.i18n.IModelTranslationMap;
import de.metas.ui.web.process.descriptor.ProcessDescriptor.ProcessDescriptorType;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptor.LookupScope;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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

	private final CCache<Integer, ProcessDescriptor> cacheByProcessId = CCache.newLRUCache(I_AD_Process.Table_Name + "#Descriptors#by#AD_Process_ID", 200, 0);

	@Autowired
	private UserSession userSession;

	public List<ProcessDescriptor> getDocumentRelatedProcesses(final String tableName)
	{
		final int adTableId = adTableDAO.retrieveTableId(tableName);
		final IUserRolePermissions userRolePermissions = userSession.getUserRolePermissions();
		return adProcessDAO.retrieveProcessesIdsForTable(Env.getCtx(), adTableId)
				.stream()
				.map(adProcessId -> getProcessDescriptor(adProcessId))
				.filter(processDescriptor -> processDescriptor.isExecutionGranted(userRolePermissions))
				.collect(GuavaCollectors.toImmutableList());
	}

	public ProcessDescriptor getProcessDescriptor(final int adProcessId)
	{
		return cacheByProcessId.getOrLoad(adProcessId, () -> {
			final I_AD_Process adProcess = InterfaceWrapperHelper.create(Env.getCtx(), adProcessId, I_AD_Process.class, ITrx.TRXNAME_None);
			return retrieveProcessDescriptor(adProcess);
		});
	}

	public ProcessDescriptor retrieveProcessDescriptor(final I_AD_Process adProcess)
	{
		final IModelTranslationMap adProcessTrlsMap = InterfaceWrapperHelper.getModelTranslationMap(adProcess);

		final int adProcessId = adProcess.getAD_Process_ID();

		final ProcessLayout.Builder layout = ProcessLayout.builder()
				.setAD_Process_ID(adProcessId)
				.setCaption(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Name, adProcess.getName()))
				.setDescription(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Description, adProcess.getDescription()));

		final DocumentEntityDescriptor.Builder parametersDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.Process, adProcessId)
				.setCaption(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Name, adProcess.getName()))
				.setDescription(adProcessTrlsMap.getColumnTrl(I_AD_Process.COLUMNNAME_Description, adProcess.getDescription()))
				.setDataBinding(ProcessParametersDataBindingDescriptorBuilder.instance)
				// Defaults:
				.setDetailId(null)
				.setAD_Tab_ID(0)
				.setTableName(I_AD_Process_Para.Table_Name)
				.setIsSOTrx(true)
				//
				;

		for (final I_AD_Process_Para adProcessParam : adProcessDAO.retrieveProcessParameters(adProcess))
		{
			final DocumentFieldDescriptor.Builder processParaDescriptor = createProcessParaDescriptor(adProcessParam);
			parametersDescriptor.addField(processParaDescriptor);
			layout.addElement(createLayoutElement(processParaDescriptor));
		}

		return ProcessDescriptor.builder()
				.setAD_Process_ID(adProcessId)
				.setType(extractType(adProcess))
				.setProcessClassname(extractClassnameOrNull(adProcess))
				.setParametersDescriptor(parametersDescriptor.build())
				.setLayout(layout.build())
				.build();
	}

	private DocumentFieldDescriptor.Builder createProcessParaDescriptor(final I_AD_Process_Para adProcessParam)
	{
		final IModelTranslationMap adProcessParaTrlsMap = InterfaceWrapperHelper.getModelTranslationMap(adProcessParam);

		final Function<LookupScope, LookupDescriptor> lookupDescriptorProvider = SqlLookupDescriptor.builder()
				.setColumnName(adProcessParam.getColumnName())
				.setDisplayType(adProcessParam.getAD_Reference_ID())
				.setAD_Reference_Value_ID(adProcessParam.getAD_Reference_Value_ID())
				.setAD_Val_Rule_ID(adProcessParam.getAD_Val_Rule_ID())
				.buildProvider();
		final LookupDescriptor lookupDescriptor = lookupDescriptorProvider.apply(LookupScope.DocumentField);

		final Class<?> valueClass = DescriptorsFactoryHelper.getValueClass(adProcessParam.getAD_Reference_ID(), lookupDescriptor);
		final DocumentFieldWidgetType widgetType = DescriptorsFactoryHelper.extractWidgetType(adProcessParam.getColumnName(), adProcessParam.getAD_Reference_ID());

		final ILogicExpression readonlyLogic = expressionFactory.compileOrDefault(adProcessParam.getReadOnlyLogic(), ConstantLogicExpression.FALSE, ILogicExpression.class);
		final ILogicExpression displayLogic = expressionFactory.compileOrDefault(adProcessParam.getDisplayLogic(), ConstantLogicExpression.TRUE, ILogicExpression.class);
		final ILogicExpression mandatoryLogic = ConstantLogicExpression.of(adProcessParam.isMandatory());

		final Optional<IExpression<?>> defaultValueExpr = defaultValueExpressions.extractDefaultValueExpression(
				adProcessParam.getDefaultValue() //
				, adProcessParam.getColumnName() //
				, widgetType //
				, valueClass //
				, mandatoryLogic.isConstantTrue() //
		);

		return DocumentFieldDescriptor.builder(adProcessParam.getColumnName())
				.setCaption(adProcessParaTrlsMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Name, adProcessParam.getName()))
				.setDescription(adProcessParaTrlsMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Description, adProcessParam.getDescription()))
				// .setHelp(adProcessParaTrlsMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Help, adProcessParam.getHelp()))
				//
				.setValueClass(valueClass)
				.setWidgetType(widgetType)
				.setLookupDescriptorProvider(lookupDescriptorProvider)
				// .setRange(adProcessParam.isRange()) // TODO
				//
				.setDefaultValueExpression(defaultValueExpr)
				.setReadonlyLogic(readonlyLogic)
				.setDisplayLogic(displayLogic)
				.setMandatoryLogic(mandatoryLogic)
				//
				.addCharacteristic(Characteristic.PublicField)
				//
				;
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
