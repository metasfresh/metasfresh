/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.customerreturns.process;

import de.metas.inout.InOutId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.servicerepair.customerreturns.HUsToReturnViewFactory;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class CustomerReturns_OpenHUToReturn extends CustomerReturnsBasedProcess implements IProcessPrecondition
{
	private final transient IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return checkSingleDraftedServiceRepairReturns(context);
	}

	@Override
	protected String doIt()
	{
		final ProcessExecutionResult.WebuiViewToOpen viewToOpen = openServiceHUEditorView();
		getResult().setWebuiViewToOpen(viewToOpen);
		return MSG_OK;
	}

	private ProcessExecutionResult.WebuiViewToOpen openServiceHUEditorView()
	{
		final InOutId customerReturnsId = getCustomerReturnId();
		final IView view = viewsRepo.createView(HUsToReturnViewFactory.createViewRequest(customerReturnsId));

		return ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(view.getViewId().getViewId())
				.target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
				.build();
	}
}
