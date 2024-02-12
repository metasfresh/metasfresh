/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.api.impl;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.BOMVersionsCreateRequest;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ProductBOMVersionsDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ProductBOMVersionsId createBOMVersions(@NonNull final BOMVersionsCreateRequest request)
	{
		final OrgId orgId = request.getOrgId();

		final I_PP_Product_BOMVersions bomVersionsRecord = newInstance(I_PP_Product_BOMVersions.class);
		bomVersionsRecord.setAD_Org_ID(orgId.getRepoId());
		bomVersionsRecord.setM_Product_ID(request.getProductId().getRepoId());
		bomVersionsRecord.setName(request.getName());
		bomVersionsRecord.setDescription(request.getDescription());

		saveRecord(bomVersionsRecord);

		return ProductBOMVersionsId.ofRepoId(bomVersionsRecord.getPP_Product_BOMVersions_ID());
	}

	@NonNull
	public Optional<ProductBOMVersionsId> retrieveBOMVersionsId(final ProductId productId)
	{
		return getBOMVersionsByProductId(productId)
				.map(I_PP_Product_BOMVersions::getPP_Product_BOMVersions_ID)
				.map(ProductBOMVersionsId::ofRepoId);
	}

	@NonNull
	public I_PP_Product_BOMVersions getBOMVersions(final ProductBOMVersionsId versionsId)
	{
		return InterfaceWrapperHelper.load(versionsId, I_PP_Product_BOMVersions.class);
	}

	@NonNull
	private Optional<I_PP_Product_BOMVersions> getBOMVersionsByProductId(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_PP_Product_BOMVersions.class)
				.addEqualsFilter(I_PP_Product_BOMVersions.COLUMNNAME_M_Product_ID, productId.getRepoId())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_PP_Product_BOMVersions.class);
	}
}
