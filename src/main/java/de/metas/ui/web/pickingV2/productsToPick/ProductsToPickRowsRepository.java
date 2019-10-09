package de.metas.ui.web.pickingV2.productsToPick;

import org.compiere.model.I_M_Locator;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.PickHURequest;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
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

@Repository
public class ProductsToPickRowsRepository
{
	private final IBPartnerBL bpartnersService;
	private final HUReservationService huReservationService;
	private final PickingCandidateService pickingCandidateService;

	public ProductsToPickRowsRepository(
			@NonNull final IBPartnerBL bpartnersService,
			@NonNull final HUReservationService huReservationService,
			@NonNull final PickingCandidateService pickingCandidateService)
	{
		this.bpartnersService = bpartnersService;
		this.huReservationService = huReservationService;
		this.pickingCandidateService = pickingCandidateService;
	}

	public ProductsToPickRowsData createProductsToPickRowsData(final PackageableRow packageableRow)
	{
		return newProductsToPickRowsFactory()
				.create(packageableRow);
	}

	private ProductsToPickRowsDataFactory newProductsToPickRowsFactory()
	{
		return ProductsToPickRowsDataFactory.builder()
				.bpartnersService(bpartnersService)
				.huReservationService(huReservationService)
				.pickingCandidateService(pickingCandidateService)
				.locatorLookup(LookupDataSourceFactory.instance.searchInTableLookup(I_M_Locator.Table_Name))
				.build();
	}

	public PickHURequest createPickHURequest(@NonNull final ProductsToPickRow row, final boolean isPickingReviewRequired)
	{
		return PickHURequest.builder()
				.shipmentScheduleId(row.getShipmentScheduleId())
				.qtyToPick(row.getQtyEffective())
				.pickFromHuId(row.getHuId())
				.autoReview(!isPickingReviewRequired)
				.build();
	}

	public void pick(@NonNull final PackageableRow packageableRow)
	{
		final ProductsToPickRowsData productsToPickRowsData = createProductsToPickRowsData(packageableRow);
		productsToPickRowsData.getAllRows().forEach(productsToPickRow -> createPickHURequest(productsToPickRow, false));
	}
}
