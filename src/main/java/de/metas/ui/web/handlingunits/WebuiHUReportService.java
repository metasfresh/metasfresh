package de.metas.ui.web.handlingunits;

import java.util.List;

import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.process.api.HUProcessDescriptor;
import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import lombok.NonNull;
import lombok.ToString;

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

/**
 * Provides HU reports,
 * {@link RelatedProcessDescriptor} which are provided by {@link IMHUProcessDAO}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/883
 */
@Service
class WebuiHUReportService
{
	public List<RelatedProcessDescriptor> getHUReportDescriptors()
	{
		final IMHUProcessDAO huProcessDAO = Services.get(IMHUProcessDAO.class);
		return huProcessDAO.getAllHUProcessDescriptors()
				.stream()
				.map(this::createRelatedProcessDescriptor)
				.collect(ImmutableList.toImmutableList());
	}

	private RelatedProcessDescriptor createRelatedProcessDescriptor(final HUProcessDescriptor huProcessDescriptor)
	{
		return RelatedProcessDescriptor.builder()
				.anyWindow().anyTable()
				.processId(huProcessDescriptor.getProcessId())
				.processPreconditionsChecker(createProcessPreconditionChecker(huProcessDescriptor))
				.webuiQuickAction(true)
				.build();
	}

	private IProcessPrecondition createProcessPreconditionChecker(@NonNull final HUProcessDescriptor huProcessDescriptor)
	{
		return new HUProcessDescriptorPreconditionsChecker(huProcessDescriptor);
	}

	@ToString
	private static final class HUProcessDescriptorPreconditionsChecker implements IProcessPrecondition
	{
		private final HUProcessDescriptor huProcessDescriptor;

		private HUProcessDescriptorPreconditionsChecker(@NonNull final HUProcessDescriptor huProcessDescriptor)
		{
			this.huProcessDescriptor = huProcessDescriptor;
		}

		@Override
		public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
		{
			final ViewAsPreconditionsContext viewCtx = ViewAsPreconditionsContext.cast(context);
			final HUEditorView view = HUEditorView.cast(viewCtx.getView());
			final boolean anyMatch = view.streamByIds(viewCtx.getSelectedRowIds())
					.anyMatch(row -> checkApplies(row, huProcessDescriptor));
			if (!anyMatch)
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("No rows are matching");
			}

			return ProcessPreconditionsResolution.accept();
		}

		private static boolean checkApplies(final HUEditorRow row, final HUProcessDescriptor huProcessDescriptor)
		{
			final String huUnitType = row.getType().toHUUnitTypeOrNull();
			return huUnitType != null && huProcessDescriptor.appliesToHUUnitType(huUnitType);
		}

	}
}
