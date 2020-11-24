/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.process.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import de.metas.i18n.IModelTranslationMap;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessBasicInfo;
import de.metas.process.ProcessParamBasicInfo;
import de.metas.process.ProcessType;
import de.metas.reflist.ReferenceId;
import de.metas.security.PermissionService;
import de.metas.security.PermissionServiceFactories;
import de.metas.security.PermissionServiceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.IADElementDAO;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProcessService
{
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IADElementDAO elementDAO = Services.get(IADElementDAO.class);
	private final IADReferenceDAO referenceDAO = Services.get(IADReferenceDAO.class);
	private final PermissionServiceFactory permissionServiceFactory = PermissionServiceFactories.currentContext();

	@NonNull
	public ImmutableList<ProcessBasicInfo> getProcessesByType(@NonNull final Set<ProcessType> processTypes)
	{
		final PermissionService permissionService = permissionServiceFactory.createPermissionService();

		final List<I_AD_Process> processes = adProcessDAO.getProcessesByType(processTypes);

		if (processes.isEmpty())
		{
			return ImmutableList.of();
		}

		final Set<Integer> processIDs = processes.stream().map(I_AD_Process::getAD_Process_ID).collect(Collectors.toSet());

		final List<I_AD_Process_Para> processParams = adProcessDAO.getProcessParamsByProcessIds(processIDs);

		final Map<Integer, I_AD_Process> processByIdMap = Maps.uniqueIndex(processes, I_AD_Process::getAD_Process_ID);

		final ImmutableListMultimap<Integer, I_AD_Process_Para> processParamByProcessIdMap =
				Multimaps.index(processParams, I_AD_Process_Para::getAD_Process_ID);

		return buildProcessBasicInfoList(processByIdMap, processParamByProcessIdMap)
				.stream()
				.filter(processBasicInfo -> permissionService.canRunProcess(processBasicInfo.getProcessId()))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableList<ProcessBasicInfo> buildProcessBasicInfoList(
			@NonNull final Map<Integer, I_AD_Process> processByIdMap,
			@NonNull final ImmutableListMultimap<Integer, I_AD_Process_Para> processParamByProcessIdMap)
	{
		return processByIdMap.keySet()
				.stream()
				.map(processId -> {
					final List<ProcessParamBasicInfo> params = processParamByProcessIdMap.get(processId)
							.stream()
							.map(this::buildProcessParamBasicInfo)
							.collect(Collectors.toList());

					return buildProcessBasicInfo(processByIdMap.get(processId), params);
				})
				.collect(ImmutableList.toImmutableList());
	}

	private ProcessParamBasicInfo buildProcessParamBasicInfo(@NonNull final I_AD_Process_Para processPara)
	{
		final IModelTranslationMap processParamTrlMap;
		if (processPara.getAD_Element_ID() <= 0)
		{
			processParamTrlMap = InterfaceWrapperHelper.getModelTranslationMap(processPara);
		}
		else
		{
			final I_AD_Element element = elementDAO.getById(processPara.getAD_Element_ID());

			processParamTrlMap = InterfaceWrapperHelper.getModelTranslationMap(element);
		}

		final I_AD_Reference typeReference = referenceDAO.getReferenceByID(ReferenceId.ofRepoId(processPara.getAD_Reference_ID()));

		return ProcessParamBasicInfo.builder()
				.columnName(processPara.getColumnName())
				.type(typeReference.getName())
				.name(processParamTrlMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Name, processPara.getName()))
				.description(processParamTrlMap.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Description, processPara.getDescription()))
				.build();
	}

	private ProcessBasicInfo buildProcessBasicInfo(@NonNull final I_AD_Process adProcess, @Nullable final List<ProcessParamBasicInfo> paramBasicInfos)
	{
		final IModelTranslationMap processTrlMap = InterfaceWrapperHelper.getModelTranslationMap(adProcess);

		return ProcessBasicInfo.builder()
				.processId(AdProcessId.ofRepoId(adProcess.getAD_Process_ID()))
				.value(adProcess.getValue())
				.name(processTrlMap.getColumnTrl(I_AD_Process.COLUMNNAME_Name, adProcess.getName()))
				.description(processTrlMap.getColumnTrl(I_AD_Process.COLUMNNAME_Description, adProcess.getName()))
				.type(ProcessType.ofCode(adProcess.getType()))
				.parameters(paramBasicInfos)
				.build();
	}
}
