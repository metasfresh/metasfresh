/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.material.cockpit.availableforsales.process;

import de.metas.material.cockpit.availableforsales.AvailableForSalesService;
import de.metas.process.JavaProcess;
import de.metas.product.Product;
import de.metas.product.ProductQuery;
import de.metas.product.ProductRepository;
import org.compiere.SpringContextHolder;

import java.util.Iterator;

public class MD_Available_For_Sales_Populate_Table extends JavaProcess
{
	private final AvailableForSalesService availableForSalesService = SpringContextHolder.instance.getBean(AvailableForSalesService.class);
	private final ProductRepository productRepository = SpringContextHolder.instance.getBean(ProductRepository.class);

	@Override
	protected String doIt() throws Exception
	{
		final ProductQuery productQuery = ProductQuery.builder()
				.isStocked(true)
				.isSold(true)
				.build();

		final Iterator<Product> productsMeantToBeSold = productRepository.getProductsByQuery(productQuery);

		while (productsMeantToBeSold.hasNext())
		{
			availableForSalesService.syncAvailableForSalesForProduct(productsMeantToBeSold.next());
		}

		return MSG_OK;
	}
}
