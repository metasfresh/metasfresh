package de.metas.ui.web.pickingV2.packageable;

import de.metas.ui.web.pickingV2.PickingConstantsV2;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ViewFactory(windowId = PickingConstantsV2.WINDOWID_PackageableView_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class PackageableViewFactoryV2 implements IViewFactory
{
	private PackageableRowsRepository rowsRepo = new PackageableRowsRepository();

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(PickingConstantsV2.WINDOWID_PackageableView)
				.setCaption("Picking")
				.addElementsFromViewRowClass(PackageableRow.class, viewDataType)
				.build();
	}

	@Override
	public IView createView(final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(PickingConstantsV2.WINDOWID_PackageableView);

		final PackageableRowsData rowsData = rowsRepo.getPackageableRowsData();

		return PackageableView.builder()
				.viewId(viewId)
				.rowsData(rowsData)
				.build();
	}
}
