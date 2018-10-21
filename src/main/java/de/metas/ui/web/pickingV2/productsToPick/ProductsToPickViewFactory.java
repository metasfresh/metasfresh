package de.metas.ui.web.pickingV2.productsToPick;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.pickingV2.PickingConstantsV2;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.productsToPick.process.ProductsToPick_4EyesReview_ProcessAll;
import de.metas.ui.web.pickingV2.productsToPick.process.ProductsToPick_MarkWillNotPickSelected;
import de.metas.ui.web.pickingV2.productsToPick.process.ProductsToPick_PickSelected;
import de.metas.ui.web.pickingV2.productsToPick.process.ProductsToPick_Request4EyesReview;
import de.metas.ui.web.pickingV2.productsToPick.process.ProductsToPick_SetPackingInstructions;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.ClassViewColumnOverrides;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
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
	@Autowired
	private ProductsToPickRowsRepository rowsRepository;
	private IViewsRepository viewsRepository;

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository)
	{
		this.viewsRepository = viewsRepository;
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		if (PickingConstantsV2.PROFILE_ID_ProductsToPickView_Review.equals(profileId))
		{
			return ViewLayout.builder()
					.setWindowId(PickingConstantsV2.WINDOWID_ProductsToPickView)
					.setCaption("Review") // TODO: trl
					.addElementsFromViewRowClassAndFieldNames(
							ProductsToPickRow.class,
							viewDataType,
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_Product),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_LotNumber),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_ExpiringDate),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_RepackNumber),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_Damaged),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_Locator),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_QtyReview),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_ApprovalStatus))
					.build();
		}
		else
		{
			return ViewLayout.builder()
					.setWindowId(PickingConstantsV2.WINDOWID_ProductsToPickView)
					.setCaption("Pick products") // TODO: trl
					.addElementsFromViewRowClassAndFieldNames(
							ProductsToPickRow.class,
							viewDataType,
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_Product),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_Locator),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_LotNumber),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_ExpiringDate),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_RepackNumber),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_Damaged),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_Qty),
							ClassViewColumnOverrides.ofFieldName(ProductsToPickRow.FIELD_PickStatus))
					.build();

		}
	}

	@Override
	public ProductsToPickView createView(final CreateViewRequest request)
	{
		throw new UnsupportedOperationException();
	}

	public ProductsToPickView createView(@NonNull final PackageableRow packageableRow)
	{
		final ViewId viewId = ViewId.random(PickingConstantsV2.WINDOWID_ProductsToPickView);

		final ProductsToPickRowsData rowsData = rowsRepository.createProductsToPickRowsData(packageableRow);

		final ProductsToPickView view = ProductsToPickView.builder()
				.viewId(viewId)
				.rowsData(rowsData)
				//
				// Picker processes:
				.relatedProcessDescriptor(createProcessDescriptor(ProductsToPick_PickSelected.class))
				.relatedProcessDescriptor(createProcessDescriptor(ProductsToPick_MarkWillNotPickSelected.class))
				.relatedProcessDescriptor(createProcessDescriptor(ProductsToPick_SetPackingInstructions.class))
				.relatedProcessDescriptor(createProcessDescriptor(ProductsToPick_Request4EyesReview.class))
				//
				// Reviewer processes:
				.relatedProcessDescriptor(createProcessDescriptor(ProductsToPick_4EyesReview_ProcessAll.class))
				//
				.build();

		viewsRepository.getViewsStorageFor(viewId).put(view);

		return view;
	}

	private final RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final int processId = adProcessDAO.retrieveProcessIdByClass(processClass);
		if (processId <= 0)
		{
			throw new AdempiereException("No processId found for " + processClass);
		}

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.anyTable()
				.anyWindow()
				.webuiQuickAction(true)
				.build();
	}
}
