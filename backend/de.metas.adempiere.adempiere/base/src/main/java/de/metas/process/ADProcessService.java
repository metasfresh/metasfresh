/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.process;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.references.zoom_into.CustomizedWindowInfo;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.util.Services;
import de.metas.workflow.service.IADWorkflowBL;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.X_AD_Process;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ADProcessService
{
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IADWorkflowBL workflowBL = Services.get(IADWorkflowBL.class);
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;

	public ADProcessService(
			@NonNull final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository)
	{
		this.customizedWindowInfoMapRepository = customizedWindowInfoMapRepository;
	}

	public I_AD_Process getById(final AdProcessId adProcessId)
	{
		return adProcessDAO.getById(adProcessId);
	}

	public Collection<I_AD_Process_Para> retrieveProcessParameters(final AdProcessId adProcessId)
	{
		return adProcessDAO.retrieveProcessParameters(adProcessId);
	}

	public List<RelatedProcessDescriptor> getRelatedProcessDescriptors(
			@Nullable final AdTableId adTableId,
			@Nullable final AdWindowId adWindowId,
			@Nullable final AdTabId adTabId)
	{
		final ImmutableList<AdWindowId> windowIdsFromBaseToCustomization;
		if (adWindowId != null)
		{
			windowIdsFromBaseToCustomization = customizedWindowInfoMapRepository
					.get()
					.getCustomizedWindowInfo(adWindowId)
					.map(CustomizedWindowInfo::getWindowIdsFromBaseToCustomization)
					.orElse(null);
		}
		else
		{
			windowIdsFromBaseToCustomization = null;
		}

		if (windowIdsFromBaseToCustomization != null)
		{
			final LinkedHashMap<AdProcessId, RelatedProcessDescriptor> result = new LinkedHashMap<>();
			for (final AdWindowId currentWindowId : windowIdsFromBaseToCustomization)
			{
				final boolean isTargetWindow = AdWindowId.equals(currentWindowId, adWindowId);
				final AdTabId currentTabId = isTargetWindow ? adTabId : null;

				for (final RelatedProcessDescriptor descriptor : adProcessDAO.retrieveRelatedProcessDescriptors(adTableId, currentWindowId, currentTabId))
				{
					result.put(descriptor.getProcessId(), descriptor);
				}

				if (isTargetWindow)
				{
					break;
				}
			}

			return result.values()
					.stream()
					.map(descriptor -> replacingWindowIdIfNeeded(descriptor, adWindowId))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			return adProcessDAO.retrieveRelatedProcessDescriptors(adTableId, adWindowId, adTabId);
		}
	}

	private static RelatedProcessDescriptor replacingWindowIdIfNeeded(
			@NonNull final RelatedProcessDescriptor descriptor,
			@NonNull final AdWindowId windowId)
	{
		if (descriptor.getWindowId() == null)
		{
			return descriptor;
		}
		else if (AdWindowId.equals(descriptor.getWindowId(), windowId))
		{
			return descriptor;
		}
		else
		{
			return descriptor.toBuilder().windowId(windowId).tabId(null).build();
		}
	}

	/**
	 * Create a document specific process with a specific workflow for the given document. Link the process to the document
	 */
	public I_AD_Process createAndLinkDocumentSpecificProcess(@NonNull final I_AD_Table document)
	{
		// Workflow
		final I_AD_Workflow adWorkflow = workflowBL.createWorkflowForDocument(document);

		// Process
		final I_AD_Process adProcess = InterfaceWrapperHelper.newInstance(I_AD_Process.class);
		adProcess.setAD_Workflow_ID(adWorkflow.getAD_Workflow_ID());
		adProcess.setValue(adWorkflow.getValue());
		adProcess.setName(adWorkflow.getName());
		adProcess.setEntityType(adWorkflow.getEntityType());
		adProcess.setAccessLevel(adWorkflow.getAccessLevel());
		adProcess.setType(X_AD_Process.TYPE_Java);
		adProcess.setIsReport(false);
		adProcess.setIsUseBPartnerLanguage(true);
		InterfaceWrapperHelper.save(adProcess);

		//
		linkProcessWithDocument(document, adProcess);

		return adProcess;
	}

	private void linkProcessWithDocument(@NonNull final I_AD_Table document, @NonNull final I_AD_Process documentSpecificProcess)
	{
		final I_AD_Column processingColumn = getColumnForDocument(document, DocumentTableFields.COLUMNNAME_Processing);
		linkProcessingColumnWithProcess(documentSpecificProcess, processingColumn);

		final I_AD_Column docActionColumn = getColumnForDocument(document, DocumentTableFields.COLUMNNAME_DocAction);
		linkProcessingColumnWithProcess(documentSpecificProcess, docActionColumn);

	}

	private void linkProcessingColumnWithProcess(final I_AD_Process documentSpecificProcess, final I_AD_Column processingColumn)
	{
		if (processingColumn == null)
		{
			// nothing to do if the column does not exist for the document table
			return;
		}
		processingColumn.setAD_Process(documentSpecificProcess);

		InterfaceWrapperHelper.save(processingColumn);

	}

	private I_AD_Column getColumnForDocument(@NonNull final I_AD_Table document, @NonNull final String columnName)
	{
		final String tableName = adTableDAO.retrieveTableName(document.getAD_Table_ID());
		return adTableDAO.retrieveColumn(tableName, columnName);
	}

}
