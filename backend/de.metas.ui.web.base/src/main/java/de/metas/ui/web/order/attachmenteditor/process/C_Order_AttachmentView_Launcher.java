/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.order.attachmenteditor.process;

import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.attachmenteditor.OrderAttachmentViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

/**
 * Note that I'm not extending ViewBasedProcessTemplate, because its {@code checkPreconditionsApplicable()} method is not invoked in the detail view.
 */
public class C_Order_AttachmentView_Launcher extends JavaProcess implements IProcessPrecondition
{
	public static final String PARAM_PURCHASE_ORDER_ID = "PurchaseOrderId";

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
	protected String doIt() throws Exception
	{
		final OrderId purchaseOrderId = OrderId.ofRepoId(getRecord_ID());
		final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);
		final ViewId viewId = viewsFactory.createView(CreateViewRequest.builder(OrderAttachmentViewFactory.WINDOWID)
															  .setParameter(PARAM_PURCHASE_ORDER_ID, purchaseOrderId)
															  .build()).getViewId();

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
											   .viewId(viewId.getViewId())
											   .target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
											   .build());

		return MSG_OK;
	}
}
