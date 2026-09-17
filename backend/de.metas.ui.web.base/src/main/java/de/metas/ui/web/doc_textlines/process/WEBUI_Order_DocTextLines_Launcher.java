package de.metas.ui.web.doc_textlines.process;

import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.doc_textlines.DocTextLinesViewFactory;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Opens the {@link de.metas.ui.web.doc_textlines.DocTextLinesView} modal on top of the sales order line tab
 * (order-line tab {@code AD_Tab_ID=187}) -- the launcher process bound there via {@code AD_Table_Process}.
 * <p>
 * Shape follows {@code WEBUI_Order_ProductsProposal_Launcher}, which extends the abstract
 * {@code WEBUI_ProductsProposal_Launcher_Template} -- that template is package-private to
 * {@code de.metas.ui.web.order.products_proposal.process}, so a launcher living in a different package (this one,
 * in {@code doc_textlines.process}) cannot extend it. This class re-implements the same two-step {@code doIt()}
 * directly instead.
 */
public class WEBUI_Order_DocTextLines_Launcher extends JavaProcess implements IProcessPrecondition
{
	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final DocTextLinesViewFactory docTextLinesViewFactory = SpringContextHolder.instance.getBean(DocTextLinesViewFactory.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (context.isExistingDocument().isFalse())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not persisted");
		}

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Order order = orderDAO.getById(orderId);

		final DocStatus docStatus = DocStatus.ofCode(order.getDocStatus());
		if (docStatus == DocStatus.Closed || docStatus == DocStatus.Voided || docStatus == DocStatus.Reversed)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not allowed for a Closed, Voided or Reversed order");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final TableRecordReference recordRef = TableRecordReference.of(getTableName(), getRecord_ID());

		final IView view = viewsRepo.createView(docTextLinesViewFactory.createViewRequest(recordRef));
		final ViewId viewId = view.getViewId();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
												.viewId(viewId.toJson())
												.target(ViewOpenTarget.ModalOverlay)
												.build());

		return MSG_OK;
	}
}
