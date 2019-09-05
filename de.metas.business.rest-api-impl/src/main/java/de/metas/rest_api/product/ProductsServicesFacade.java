package de.metas.rest_api.product;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.rest_api.utils.JsonCreatedUpdatedInfo;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * Facade of all services on which this REST endpoints depends
 *
 */
@Service
public class ProductsServicesFacade
{
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IBPartnerProductDAO partnerProductsRepo = Services.get(IBPartnerProductDAO.class);

	public Stream<I_M_Product> streamAllProducts()
	{
		return productsRepo.streamAllProducts();
	}

	public String getUOMSymbol(@NonNull final UomId uomId)
	{
		final I_C_UOM uom = uomsRepo.getById(uomId);
		return uom.getUOMSymbol();
	}

	public List<I_C_BPartner_Product> getBPartnerProductRecords(Set<ProductId> productIds)
	{
		return partnerProductsRepo.retrieveForProductIds(productIds);
	}

	public JsonCreatedUpdatedInfo extractCreatedUpdatedInfo(final I_M_Product record)
	{
		return JsonCreatedUpdatedInfo.builder()
				.created(TimeUtil.asZonedDateTime(record.getCreated()))
				.createdBy(UserId.optionalOfRepoId(record.getCreatedBy()).orElse(UserId.SYSTEM))
				.updated(TimeUtil.asZonedDateTime(record.getUpdated()))
				.updatedBy(UserId.optionalOfRepoId(record.getUpdatedBy()).orElse(UserId.SYSTEM))
				.build();
	}

	public Stream<I_M_Product_Category> streamAllProductCategories()
	{
		return productsRepo.streamAllProductCategories();
	}

	public JsonCreatedUpdatedInfo extractCreatedUpdatedInfo(final I_M_Product_Category record)
	{
		return JsonCreatedUpdatedInfo.builder()
				.created(TimeUtil.asZonedDateTime(record.getCreated()))
				.createdBy(UserId.optionalOfRepoId(record.getCreatedBy()).orElse(UserId.SYSTEM))
				.updated(TimeUtil.asZonedDateTime(record.getUpdated()))
				.updatedBy(UserId.optionalOfRepoId(record.getUpdatedBy()).orElse(UserId.SYSTEM))
				.build();
	}

}
