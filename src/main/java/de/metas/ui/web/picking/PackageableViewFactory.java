package de.metas.ui.web.picking;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;
import lombok.NonNull;

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
 * Factory class for {@link PackageableView} intances.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ViewFactory(windowId = PickingConstants.WINDOWID_PickingView_String, viewTypes =
	{ JSONViewDataType.grid, JSONViewDataType.includedView })
public class PackageableViewFactory implements IViewFactory
{
	private final PackageableViewRepository pickingViewRepo;

	private final PickingCandidateCommand pickingCandidateCommand;

	/**
	 * 
	 * @param pickingViewRepo
	 * @param pickingCandidateCommand when a new view is created, this stateless instance is given to that view
	 */
	public PackageableViewFactory(
			@NonNull final PackageableViewRepository pickingViewRepo,
			@NonNull final PickingCandidateCommand pickingCandidateCommand)
	{
		this.pickingViewRepo = pickingViewRepo;
		this.pickingCandidateCommand = pickingCandidateCommand;
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType)
	{
		// TODO: cache it

		return ViewLayout.builder()
				.setWindowId(PickingConstants.WINDOWID_PickingView)
				.setCaption("Picking")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				//
				.setHasAttributesSupport(false)
				.setHasTreeSupport(false)
				.setHasIncludedViewSupport(true)
				.setHasIncludedViewOnSelectSupport(true)
				//
				.addElementsFromViewRowClass(PackageableRow.class, viewDataType)
				//
				.build();
	}

	@Override
	public Collection<DocumentFilterDescriptor> getViewFilterDescriptors(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType)
	{
		return getViewLayout(windowId, viewDataType).getFilters();
	}

	/**
	 * @param request its {@code windowId} has to me {@link PickingConstants#WINDOWID_PickingView}
	 */
	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final WindowId windowId = request.getWindowId();
		if (!PickingConstants.WINDOWID_PickingView.equals(windowId))
		{
			throw new IllegalArgumentException("Invalid request's windowId: " + request);
		}

		final ViewId viewId = ViewId.random(PickingConstants.WINDOWID_PickingView);

		final Set<DocumentId> rowIds = request.getFilterOnlyIds().stream().map(DocumentId::of).collect(ImmutableSet.toImmutableSet());
		final Supplier<List<PackageableRow>> rowsSupplier = () -> pickingViewRepo.retrieveRowsByIds(viewId, rowIds);

		return PackageableView.builder()
				.viewId(viewId)
				.description(ITranslatableString.empty())
				.rowsSupplier(rowsSupplier)
				.pickingCandidateCommand(pickingCandidateCommand)
				.build();
	}

}
