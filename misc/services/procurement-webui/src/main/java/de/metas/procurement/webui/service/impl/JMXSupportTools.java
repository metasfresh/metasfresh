package de.metas.procurement.webui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.repository.BPartnerRepository;
import de.metas.procurement.webui.repository.ProductRepository;

/*
 * #%L
 * de.metas.procurement.webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
@ManagedResource(description = "JMX support tools")
public class JMXSupportTools
{
	@Autowired
	@Lazy
	private ProductRepository productRepository;

	@Autowired
	@Lazy
	private BPartnerRepository bpartnersRepository;

	@ManagedOperation(description = "Finds all products which have a name like given parameter")
	public List<String> getProductsByNameLike(final String productNameLike)
	{
		final List<Product> products = productRepository.findByNameLike(productNameLike);

		final List<String> result = new ArrayList<>(products.size());
		for (final Product product : products)
		{
			result.add(product.toString());
		}
		return result;
	}

	@ManagedOperation(description = "Finds all business partners which have a name like given parameter")
	public List<String> getBPartnersByNameLike(final String productNameLike)
	{
		final List<BPartner> bpartners = bpartnersRepository.findByNameLike(productNameLike);

		final List<String> result = new ArrayList<>(bpartners.size());
		for (final BPartner bpartner : bpartners)
		{
			result.add(bpartner.toString());
		}
		return result;
	}

}
