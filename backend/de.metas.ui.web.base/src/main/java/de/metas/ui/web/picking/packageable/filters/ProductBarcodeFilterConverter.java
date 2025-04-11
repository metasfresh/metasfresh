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

import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.FilterSqlRequest;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import lombok.NonNull;

import java.util.Objects;

public class ProductBarcodeFilterConverter implements SqlDocumentFilterConverter
{
	public static final ProductBarcodeFilterConverter instance = new ProductBarcodeFilterConverter();

	@Override
	public boolean canConvert(final String filterId)
	{
		return Objects.equals(filterId, PackageableFilterDescriptorProvider.ProductBarcode_FilterId);
	}

	@Override
	public FilterSql getSql(@NonNull final FilterSqlRequest request)
	{
		final ProductBarcodeFilterData data = PackageableFilterDescriptorProvider.extractProductBarcodeFilterData(request.getFilter()).orElse(null);

		if (data == null)
		{
			return FilterSql.ALLOW_NONE;
		}

		return FilterSql.ofWhereClause(data.getSqlWhereClause());
	}
}
