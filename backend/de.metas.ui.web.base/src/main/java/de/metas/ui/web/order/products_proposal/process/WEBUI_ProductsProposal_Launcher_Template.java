package de.metas.ui.web.order.products_proposal.process;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

abstract class WEBUI_ProductsProposal_Launcher_Template extends JavaProcess
{
	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);

	public WEBUI_ProductsProposal_Launcher_Template()
	{
		SpringContextHolder.instance.autowire(this);
	}

	@Override
	protected final String doIt()
	{
		final TableRecordReference recordRef = TableRecordReference.of(getTableName(), getRecord_ID());

		final IView view = viewsRepo.createView(createViewRequest(recordRef));
		final ViewId viewId = view.getViewId();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
											   .viewId(viewId.toJson())
											   .target(ViewOpenTarget.ModalOverlay)
											   .build());

		return MSG_OK;
	}

	protected abstract CreateViewRequest createViewRequest(TableRecordReference recordRef);

}
