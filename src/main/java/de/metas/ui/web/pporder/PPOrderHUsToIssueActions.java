package de.metas.ui.web.pporder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.Adempiere;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.view.ViewAction;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;

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
 * Collection of view actions to be plugged when the HU editor is opened
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class PPOrderHUsToIssueActions
{
	/** Precondition: the are some HUs selected */
	public static class HasSelectedHUs implements ViewAction.Precondition
	{
		@Override
		public ProcessPreconditionsResolution matches(final IView view, final Set<DocumentId> selectedDocumentIds)
		{
			final Set<Integer> huIds = extractHUIds(view, selectedDocumentIds);
			if (huIds.isEmpty())
			{
				return ProcessPreconditionsResolution.reject("no HU(s) selected");
			}
			return ProcessPreconditionsResolution.accept();
		}
	}

	/**
	 * Action to issue selected HUs to currenty selected manufacturing order BOM line
	 * 
	 * @param husView
	 * @param selectedHURowIds
	 */
	@ViewAction(caption = "PPOrderIncludedHUEditorActions.issueSelectedHUs", precondition = HasSelectedHUs.class)
	public static void issueSelectedHUs(final HUEditorView husView, final Set<DocumentId> selectedHURowIds)
	{
		final PPOrderLinesView ppOrderView = getPPOrderView(husView).get();
		final int ppOrderId = ppOrderView.getPP_Order_ID();

		final List<I_M_HU> hus = extractHUs(husView, selectedHURowIds);
		if (hus.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		Services.get(IHUPPOrderBL.class)
				.createIssueProducer()
				.setTargetOrderBOMLinesByPPOrderId(ppOrderId)
				.createIssues(hus);

		husView.removesHUsAndInvalidate(hus);
		ppOrderView.invalidateAll();

	}

	private static final Set<Integer> extractHUIds(final IView view, final Set<DocumentId> selectedHURowIds)
	{
		if (selectedHURowIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final HUEditorView huView = HUEditorView.cast(view);
		return huView.getByIds(selectedHURowIds)
				.stream()
				.map(HUEditorRow::getM_HU_ID)
				.filter(huId -> huId > 0)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static final List<I_M_HU> extractHUs(final IView view, final Set<DocumentId> selectedHURowIds)
	{
		final Set<Integer> huIds = extractHUIds(view, selectedHURowIds);
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class)
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.list(I_M_HU.class);
	}

	private static Optional<PPOrderLinesView> getPPOrderView(final HUEditorView husView)
	{
		final ViewId parentViewId = husView.getParentViewId();
		if (parentViewId == null)
		{
			return Optional.empty();
		}

		final IViewsRepository viewsRepo = Adempiere.getSpringApplicationContext().getBean(IViewsRepository.class);
		final PPOrderLinesView ppOrderView = viewsRepo.getView(parentViewId, PPOrderLinesView.class);
		return Optional.of(ppOrderView);
	}

}
