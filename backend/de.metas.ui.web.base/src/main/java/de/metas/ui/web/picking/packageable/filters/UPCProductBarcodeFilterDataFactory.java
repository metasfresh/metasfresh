/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.picking.packageable.filters;

import com.google.common.collect.ImmutableList;
import de.metas.product.ResolvedScannedProductCode;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.product.ProductId;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Matchers all rows for given barcode. M_Product.UPC and M_Product.Value are matched.
 */
class UPCProductBarcodeFilterDataFactory implements ProductBarcodeFilterDataFactory
{
	@Override
	public Optional<ProductBarcodeFilterData> createData(
			final @NonNull ProductBarcodeFilterServicesFacade services,
			final @NonNull String barcodeParam,
			final @NonNull ClientId clientId)
	{
		Check.assumeNotEmpty(barcodeParam, "barcode not empty");

		final String barcodeNorm = barcodeParam.trim();

		final ImmutableList<ResolvedScannedProductCode> ediProductLookupList = services.getEDIProductLookupByUPC(barcodeNorm);
		final ProductId productId = services.getProductIdByBarcode(barcodeNorm, clientId).orElse(null);
		final SqlAndParams sqlWhereClause = createSqlWhereClause(services, ediProductLookupList, productId).orElse(null);
		if (sqlWhereClause == null)
		{
			return Optional.empty();
		}

		return Optional.of(ProductBarcodeFilterData.builder()
								   .barcode(barcodeNorm)
								   .sqlWhereClause(sqlWhereClause)
								   .build());
	}

	private static Optional<SqlAndParams> createSqlWhereClause(
			@NonNull final ProductBarcodeFilterServicesFacade services,
			@NonNull final ImmutableList<ResolvedScannedProductCode> ediProductLookupList,
			@Nullable final ProductId productId)
	{
		if (productId == null && ediProductLookupList.isEmpty())
		{
			return Optional.empty();
		}

		final ICompositeQueryFilter<I_M_Packageable_V> resultFilter = services.createCompositeQueryFilter()
				.setJoinOr();

		if (!ediProductLookupList.isEmpty())
		{
			for (final ResolvedScannedProductCode ediProductLookup : ediProductLookupList)
			{
				final ICompositeQueryFilter<I_M_Packageable_V> filter = resultFilter.addCompositeQueryFilter()
						.setJoinAnd()
						.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_M_Product_ID, ediProductLookup.getProductId());

				if (ediProductLookup.getBpartnerId() != null)
				{
					filter.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID, ediProductLookup.getBpartnerId());
				}
			}
		}

		if (productId != null)
		{
			resultFilter.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_M_Product_ID, productId);
		}

		return Optional.of(services.toSqlAndParams(resultFilter));
	}
}
