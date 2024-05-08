
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

package de.metas.ui.web.pickingV2.packageable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.money.MoneyService;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.pickingV2.PickingConstantsV2;
import de.metas.ui.web.pickingV2.packageable.process.PackageablesView_OpenProductsToPick;
import de.metas.ui.web.pickingV2.packageable.process.PackageablesView_PrintPicklist;
import de.metas.ui.web.pickingV2.packageable.process.PackageablesView_UnlockAll;
import de.metas.ui.web.pickingV2.packageable.process.PackageablesView_UnlockFromLoggedUser;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;

@ViewFactory(windowId = PickingConstantsV2.WINDOWID_PackageableView_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class PackageableViewFactoryV2 implements IViewFactory
{
	private final PackageableRowsRepository rowsRepo;

	public PackageableViewFactoryV2(@NonNull final MoneyService moneyService)
	{
		rowsRepo = new PackageableRowsRepository(moneyService);
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(PickingConstantsV2.WINDOWID_PackageableView)
				.setCaption("Picking") // TODO: trl
				.setAllowOpeningRowDetails(false)
				.setFilters(getFilterDescriptorsProvider().getAll())
				.addElementsFromViewRowClass(PackageableRow.class, viewDataType)
				.build();
	}

	private DocumentFilterDescriptorsProvider getFilterDescriptorsProvider()
	{
		return PackageableViewFilters.getDescriptors();
	}

	@Override
	public PackageableView createView(final @NonNull CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(PickingConstantsV2.WINDOWID_PackageableView);

		final DocumentFilterDescriptorsProvider filterDescriptors = getFilterDescriptorsProvider();

		final PackageableRowsData rowsData = rowsRepo.newPackageableRowsData()
				.filters(request.getFiltersUnwrapped(filterDescriptors))
				.stickyFilters(request.getStickyFilters())
				.build();

		return PackageableView.builder()
				.viewId(viewId)
				.rowsData(rowsData)
				.relatedProcessDescriptors(getRelatedProcessDescriptors())
				.viewFilterDescriptors(filterDescriptors)
				.build();
	}

	private Iterable<? extends RelatedProcessDescriptor> getRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptor(PackageablesView_OpenProductsToPick.class),
				createProcessDescriptor(PackageablesView_UnlockFromLoggedUser.class),
				createProcessDescriptor(PackageablesView_UnlockAll.class),
				createProcessDescriptor(PackageablesView_PrintPicklist.class));
	}

	private final RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(processClass);
		if (processId == null)
		{
			throw new AdempiereException("No processId found for " + processClass);
		}

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.anyTable().anyWindow()
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

}
