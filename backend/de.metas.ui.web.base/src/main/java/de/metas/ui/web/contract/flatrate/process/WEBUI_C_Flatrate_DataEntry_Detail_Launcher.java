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
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

public class WEBUI_C_Flatrate_DataEntry_Detail_Launcher extends JavaProcess implements IProcessPrecondition
{
	@Autowired
	private DataEntryDedailsViewFactory dataEntryDedailsViewFactory;

	@Autowired
	private IViewsRepository viewsRepo;

	public WEBUI_C_Flatrate_DataEntry_Detail_Launcher()
	{
		SpringContextHolder.instance.autowire(this);
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
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
		final TableRecordReference recordRef = TableRecordReference.of(getTableName(), getRecord_ID());

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
