package de.metas.ui.web.pickingV2.productsToPick;

import de.metas.ui.web.pickingV2.PickingConstantsV2;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

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

@ViewFactory(windowId = PickingConstantsV2.WINDOWID_ProductsToPickView_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class ProductsToPickViewFactory implements IViewFactory
{
	@Override
	public ViewLayout getViewLayout(WindowId windowId, JSONViewDataType viewDataType, ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(PickingConstantsV2.WINDOWID_ProductsToPickView)
				.setCaption("Pick products") // TODO: trl
				.addElementsFromViewRowClass(ProductsToPickRow.class, viewDataType)
				.build();
	}

	@Override
	public ProductsToPickView createView(final CreateViewRequest request)
	{
		throw new UnsupportedOperationException();
	}

	public ProductsToPickView createView(@NonNull final PackageableRow packageableRow)
	{
		final ViewId viewId = ViewId.random(PickingConstantsV2.WINDOWID_ProductsToPickView);

		final ProductsToPickRowsData rowsData = createProductsToPickRowsData(packageableRow);

		return ProductsToPickView.builder()
				.viewId(viewId)
				.rowsData(rowsData)
				.build();
	}

	private ProductsToPickRowsData createProductsToPickRowsData(PackageableRow packageableRow)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
