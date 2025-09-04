/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.contract.flatrate.process;

import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.contract.flatrate.view.DataEntryDedailsViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

public class WEBUI_C_Flatrate_DataEntry_Detail_Launcher extends JavaProcess implements IProcessPrecondition
{
	private final DataEntryDedailsViewFactory dataEntryDedailsViewFactory;
	private final IViewsRepository viewsRepo;

	public WEBUI_C_Flatrate_DataEntry_Detail_Launcher()
	{
		dataEntryDedailsViewFactory = SpringContextHolder.instance.getBean(DataEntryDedailsViewFactory.class);
		viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final String doIt()
	{
		final I_C_Flatrate_DataEntry entryRecord = ProcessUtil.extractEntryOrNull(getProcessInfo());
		if (entryRecord == null)
		{
			addLog("Currently there is no C_Flatrate_DataEntry selected; nothing to do");
			return MSG_OK;
		}
		if (entryRecord.isProcessed())
		{
			// it would be nice to open the modal real-only and somehow indicate to the user why it is red-only
			throw new AdempiereException(AdMessageKey.of("de.metas.ui.web.contract.flatrate.process.WEBUI_C_Flatrate_DataEntry_Detail_Launcher.EntryAlreadyProcessed")).markAsUserValidationError();
		}

		final TableRecordReference recordRef = TableRecordReference.of(entryRecord);

		final IView view = viewsRepo.createView(createViewRequest(recordRef));
		final ViewId viewId = view.getViewId();

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
											   .viewId(viewId.toJson())
											   .target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
											   .build());

		return MSG_OK;
	}

	private CreateViewRequest createViewRequest(@NonNull final TableRecordReference recordRef)
	{
		return dataEntryDedailsViewFactory.createViewRequest(recordRef);
	}
}
