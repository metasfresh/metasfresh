/**
 *
 */
package de.metas.impexp.product;

import de.metas.pricing.PriceListVersionId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPriceCreateRequest;
import de.metas.pricing.service.ProductPrices;
import de.metas.util.Services;
import lombok.NonNull;

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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class ProductPriceImporter
{

	private final ProductPriceCreateRequest request;

	public ProductPriceImporter(@NonNull final ProductPriceCreateRequest request)
	{
		this.request = request;
	}

	public void createProductPrice_And_PriceListVersionIfNeeded()
	{
		if (!isValidPriceRecord())
		{
			throw new AdempiereException("ProductPriceImporter.InvalidProductPriceList");
		}

		if (request.getPrice().signum() >= 0)
		{
			if ((request.getPrice().signum() == 0 && request.isUseNewestPriceListversion())
					|| request.getPrice().signum() > 0)
			{
				final I_M_PriceList_Version plv = Services.get(IPriceListDAO.class).getCreatePriceListVersion(request);
				ProductPrices.createProductPriceOrUpdateExistentOne(request, PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID()));
			}
			
		}
	}

	private boolean isValidPriceRecord()
	{
		return request.getProductId() > 0
				&& request.getPriceListId() > 0
				&& request.getValidDate() != null;
	}
	}
