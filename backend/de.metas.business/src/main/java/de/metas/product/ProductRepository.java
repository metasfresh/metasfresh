package de.metas.product;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IModelTranslationMap;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.business
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
public class ProductRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Product getById(@NonNull final ProductId id)
	{
		final I_M_Product productRecord = loadOutOfTrx(id.getRepoId(), I_M_Product.class);
		return ofRecord(productRecord);
	}

	public ImmutableList<Product> getByIds(@NonNull final Set<ProductId> ids)
	{
		final List<I_M_Product> productRecords = queryBL
				.createQueryBuilder(I_M_Product.class)
				.addInArrayFilter(I_M_Product.COLUMNNAME_M_Product_ID, ids)
				.create()
				.list();

		final ImmutableList.Builder<Product> products = ImmutableList.builder();
		for (final I_M_Product productRecord : productRecords)
		{
			products.add(ofRecord(productRecord));
		}
		return products.build();
	}

	public Product ofRecord(@NonNull final I_M_Product productRecord)
	{
		final int manufacturerId = productRecord.getManufacturer_ID();

		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(productRecord);

		return Product.builder()
				.id(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.productNo(productRecord.getValue())
				.name(modelTranslationMap.getColumnTrl(I_M_Product.COLUMNNAME_Name, productRecord.getName()))
				.description(modelTranslationMap.getColumnTrl(I_M_Product.COLUMNNAME_Description, productRecord.getDescription()))
				.uomId(UomId.ofRepoId(productRecord.getC_UOM_ID()))
				.manufacturerId(manufacturerId > 0 ? BPartnerId.ofRepoId(manufacturerId) : null)
				.packageSize(productRecord.getPackageSize())
				.weight(productRecord.getWeight())
				.build();
	}
}
