package de.metas.ui.web.pporder.process;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUDocumentViewSelection;
import de.metas.ui.web.handlingunits.process.HUViewProcessTemplate;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.view.IDocumentViewsRepository;
import de.metas.ui.web.view.ViewId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Issue selected HU to manufacturing order (BOM line).
 * 
 * Action linked to {@link HUDocumentViewSelection} and it's available only when the HU's view was created from {@link PPOrderLinesView}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_M_HU_IssueSelectedHU
		extends HUViewProcessTemplate
		implements IProcessPrecondition
{
	// services
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	@Autowired
	private IDocumentViewsRepository viewsRepo;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedDocumentIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!ppOrderView().isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not called from manufacturing issue/receipt view");
		}

		final Set<Integer> huIds = getSelectedHUIds();
		if (huIds.isEmpty())
		{
			ProcessPreconditionsResolution.reject("no HUs");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final PPOrderLinesView ppOrderView = ppOrderView().get();
		final int ppOrderId = ppOrderView.getPP_Order_ID();

		final List<I_M_HU> hus = getSelectedHUs();
		if(hus.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		huPPOrderBL
				.createIssueProducer()
				.setTargetOrderBOMLinesByPPOrderId(ppOrderId)
				.createIssues(hus);

		getView().removesHUAndInvalidate(hus);
		ppOrderView.invalidateAll();

		return MSG_OK;
	}

	private Optional<PPOrderLinesView> ppOrderView()
	{
		final HUDocumentViewSelection husView = getView();
		final ViewId parentViewId = husView.getParentViewId();
		if (parentViewId == null)
		{
			return Optional.empty();
		}

		final PPOrderLinesView ppOrderView = viewsRepo.getView(parentViewId, PPOrderLinesView.class);
		return Optional.of(ppOrderView);
	}
}
