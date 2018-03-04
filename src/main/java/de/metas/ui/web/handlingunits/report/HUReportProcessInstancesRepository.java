package de.metas.ui.web.handlingunits.report;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.util.CCache;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.handlingunits.model.I_M_HU_Process;
import de.metas.handlingunits.process.api.HUProcessDescriptor;
import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.process.IADProcessDAO;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.CreateProcessInstanceRequest;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.IProcessInstancesRepository;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.WebuiPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.ProcessDescriptor.ProcessDescriptorType;
import de.metas.ui.web.process.descriptor.ProcessLayout;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class HUReportProcessInstancesRepository implements IProcessInstancesRepository
{
	private static final String PROCESS_HANDLER_TYPE = "HUReport";

	private final CCache<Integer, IndexedWebuiHUProcessDescriptors> processDescriptors = CCache.newCache(I_M_HU_Process.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	private final Cache<DocumentId, HUReportProcessInstance> instances = CacheBuilder.newBuilder()
			.expireAfterAccess(10, TimeUnit.MINUTES)
			.build();

	@Override
	public String getProcessHandlerType()
	{
		return PROCESS_HANDLER_TYPE;
	}

	@Override
	public ProcessDescriptor getProcessDescriptor(@NonNull final ProcessId processId)
	{
		return getWebuiHUProcessDescriptor(processId).getProcessDescriptor();
	}

	private WebuiHUProcessDescriptor getWebuiHUProcessDescriptor(@NonNull final ProcessId processId)
	{
		return getIndexedWebuiHUProcessDescriptors().getByProcessId(processId);
	}

	private IndexedWebuiHUProcessDescriptors getIndexedWebuiHUProcessDescriptors()
	{
		return processDescriptors.getOrLoad(0, this::retrieveIndexedWebuiHUProcessDescriptors);
	}

	private IndexedWebuiHUProcessDescriptors retrieveIndexedWebuiHUProcessDescriptors()
	{
		final IMHUProcessDAO huProcessDescriptorsRepo = Services.get(IMHUProcessDAO.class);
		return new IndexedWebuiHUProcessDescriptors(huProcessDescriptorsRepo
				.getAllHUProcessDescriptors()
				.stream()
				.map(this::toWebuiHUProcessDescriptor)
				.collect(ImmutableList.toImmutableList()));
	}

	private WebuiHUProcessDescriptor toWebuiHUProcessDescriptor(final HUProcessDescriptor huProcessDescriptor)
	{
		final int reportADProcessId = huProcessDescriptor.getProcessId();
		final ProcessId processId = ProcessId.of(PROCESS_HANDLER_TYPE, reportADProcessId);

		final I_AD_Process adProcess = Services.get(IADProcessDAO.class).retrieveProcessById(reportADProcessId);
		final IModelTranslationMap adProcessTrl = InterfaceWrapperHelper.getModelTranslationMap(adProcess);
		final ITranslatableString caption = adProcessTrl.getColumnTrl(I_AD_Process.COLUMNNAME_Name, adProcess.getName());
		final ITranslatableString description = adProcessTrl.getColumnTrl(I_AD_Process.COLUMNNAME_Description, adProcess.getDescription());

		return WebuiHUProcessDescriptor.builder()
				.huProcessDescriptor(huProcessDescriptor)
				.processDescriptor(ProcessDescriptor.builder()
						.setProcessId(processId)
						.setType(ProcessDescriptorType.Report)
						.setLayout(ProcessLayout.builder()
								.setProcessId(processId)
								.setCaption(caption)
								.setDescription(description)
								.build())
						.build())
				.build();
	}

	@Override
	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final WebuiPreconditionsContext preconditionsContext)
	{
		final ViewAsPreconditionsContext viewContext = ViewAsPreconditionsContext.castOrNull(preconditionsContext);
		if (viewContext == null)
		{
			return Stream.empty();
		}

		if (viewContext.isNoSelection())
		{
			return Stream.empty();
		}

		if (!(viewContext.getView() instanceof HUEditorView))
		{
			return Stream.empty();
		}

		return getIndexedWebuiHUProcessDescriptors()
				.getAll()
				.stream()
				.filter(descriptor -> checkApplies(descriptor, viewContext))
				.map(descriptor -> descriptor.toWebuiRelatedProcessDescriptor());
	}

	private boolean checkApplies(WebuiHUProcessDescriptor descriptor, ViewAsPreconditionsContext viewContext)
	{
		final DocumentIdsSelection rowIds = viewContext.getSelectedRowIds();
		if (rowIds.isEmpty())
		{
			return false;
		}

		final HUEditorView huView = viewContext.getView(HUEditorView.class);
		final boolean foundNotMatchingRows = huView.streamByIds(rowIds)
				.anyMatch(row -> !checkApplies(row, descriptor));

		return !foundNotMatchingRows;
	}

	private static boolean checkApplies(final HUEditorRow row, final WebuiHUProcessDescriptor descriptor)
	{
		final String huUnitType = row.getType().toHUUnitTypeOrNull();
		return huUnitType != null && descriptor.appliesToHUUnitType(huUnitType);
	}

	@Override
	public HUReportProcessInstance createNewProcessInstance(final CreateProcessInstanceRequest request)
	{
		final WebuiHUProcessDescriptor descriptor = getWebuiHUProcessDescriptor(request.getProcessId());

		final HUReportProcessInstance instance = HUReportProcessInstance.builder()
				.instanceId(nextPInstanceId())
				.viewRowIdsSelection(request.getViewRowIdsSelection())
				.reportADProcessId(descriptor.getReportADProcessId())
				.build();

		instances.put(instance.getInstanceId(), instance);
		instances.cleanUp();

		return instance;
	}

	private HUReportProcessInstance getInstance(final DocumentId instanceId)
	{
		final HUReportProcessInstance instance = instances.getIfPresent(instanceId);
		if (instance == null)
		{
			throw new EntityNotFoundException("No HU Report instance found for " + instanceId);
		}
		return instance;
	}

	@Override
	public <R> R forProcessInstanceReadonly(final DocumentId pinstanceId, final Function<IProcessInstanceController, R> processor)
	{
		return processor.apply(getInstance(pinstanceId));
	}

	@Override
	public <R> R forProcessInstanceWritable(final DocumentId pinstanceId, final IDocumentChangesCollector changesCollector, final Function<IProcessInstanceController, R> processor)
	{
		return processor.apply(getInstance(pinstanceId));
	}

	@Override
	public void cacheReset()
	{
		processDescriptors.clear();
		instances.cleanUp();
	}

	private DocumentId nextPInstanceId()
	{
		return DocumentId.ofString(UUID.randomUUID().toString());
	}

	private static final class IndexedWebuiHUProcessDescriptors
	{
		private final ImmutableMap<ProcessId, WebuiHUProcessDescriptor> descriptorsByProcessId;

		private IndexedWebuiHUProcessDescriptors(final List<WebuiHUProcessDescriptor> descriptors)
		{
			descriptorsByProcessId = Maps.uniqueIndex(descriptors, WebuiHUProcessDescriptor::getProcessId);
		}

		public WebuiHUProcessDescriptor getByProcessId(final ProcessId processId)
		{
			final WebuiHUProcessDescriptor descriptor = descriptorsByProcessId.get(processId);
			if (descriptor == null)
			{
				throw new EntityNotFoundException("No HU process descriptor found for " + processId);
			}
			return descriptor;
		}

		public Collection<WebuiHUProcessDescriptor> getAll()
		{
			return descriptorsByProcessId.values();
		}
	}
}
