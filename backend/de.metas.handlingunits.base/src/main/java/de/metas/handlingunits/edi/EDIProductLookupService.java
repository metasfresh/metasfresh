/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.edi;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_EDI_M_Product_Lookup_UPC_v;
import de.metas.product.ResolvedScannedProductCode;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Service;

@Service
public class EDIProductLookupService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ImmutableList<ResolvedScannedProductCode> lookupByUPC(
			@NonNull final String upc,
			final boolean usedForCustomer)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_EDI_M_Product_Lookup_UPC_v.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_M_Product_Lookup_UPC_v.COLUMNNAME_UPC, upc)
				.addEqualsFilter(I_EDI_M_Product_Lookup_UPC_v.COLUMNNAME_UsedForCustomer, usedForCustomer)
				.create()
				.stream()
				.map(EDIProductLookupService::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private static ResolvedScannedProductCode fromRecord(@NonNull final I_EDI_M_Product_Lookup_UPC_v record)
	{
		return ResolvedScannedProductCode.builder()
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.bpartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.usedForCustomer(record.isUsedForCustomer())
				.build();
	}
}
