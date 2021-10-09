package de.metas.procurement.base;

import java.util.Date;
import java.util.List;

import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_M_Product;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IPMMProductDAO extends ISingletonService
{
	/**
	 * Create a query builder  retrieve to all active {@link I_PMM_Product}s that have a {@code M_HU_PI_Item_Product_ID} and {@code M_Warehouse_ID} set
	 * and whose {@code ValidFrom} and {@code ValidTo} dates are either {@code null} or lie within the given {@code date}.
	 */
	IQueryBuilder<I_PMM_Product> retrievePMMProductsValidOnDateQuery(Date date);

	List<I_PMM_Product> retrieveByHUPIItemProduct(I_M_HU_PI_Item_Product huPIItemProduct);

	List<I_PMM_Product> retrieveByProduct(I_M_Product product);

	List<I_PMM_Product> retrieveByBPartner(BPartnerId bpartnerId);

	/**
	 * Retrieve the PMM Products for the given product, date and M_HU_PI_Item_Product.
	 * The PMM products must be for the given partner or not have a partner set at all.
	 * The PMM Products will be sorted by SeqNo.
	 */
	List<I_PMM_Product> retrieveForDateAndProduct(Date date, @NonNull ProductId productId, @Nullable BPartnerId partnerId, int huPIPId);

}
