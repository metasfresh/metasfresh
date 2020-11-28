package de.metas.ui.web.order.products_proposal.process;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewId;

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

public abstract class ProductsProposalViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private static final Logger logger = LogManager.getLogger(ProductsProposalViewBasedProcess.class);

	@Autowired
	private IViewsRepository viewsRepo;

	@Override
	protected final ProductsProposalView getView()
	{
		return ProductsProposalView.cast(super.getView());
	}

	protected final List<ProductsProposalRow> getSelectedRows()
	{
		return getView()
				.streamByIds(getSelectedRowIds())
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	protected final ProductsProposalRow getSingleSelectedRow()
	{
		return ProductsProposalRow.cast(super.getSingleSelectedRow());
	}

	protected final ViewId getInitialViewId()
	{
		return getView().getInitialViewId();
	}

	protected final ProductsProposalView getInitialView()
	{
		return ProductsProposalView.cast(viewsRepo.getView(getInitialViewId()));
	}

	protected final void closeAllViewsAndShowInitialView()
	{
		closeAllViewsExcludingInitialView();

		afterCloseOpenView(getInitialViewId());
	}

	private final void closeAllViewsExcludingInitialView()
	{
		IView currentView = getView();
		while (currentView != null && currentView.getParentViewId() != null)
		{
			try
			{
				viewsRepo.closeView(currentView.getViewId(), ViewCloseAction.CANCEL);
			}
			catch (Exception ex)
			{
				logger.warn("Failed closing view {}. Ignored", currentView, ex);
			}

			final ViewId viewId = currentView.getParentViewId();
			currentView = viewsRepo.getViewIfExists(viewId);
		}
	}

	protected final void afterCloseOpenView(final ViewId viewId)
	{
		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
				.viewId(viewId.toJson())
				.target(ViewOpenTarget.ModalOverlay)
				.build());
	}

}
