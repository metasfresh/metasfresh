/*
 * #%L
 * procurement-webui-backend
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

package de.metas.procurement.webui.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductService
{
	private final int productNameMaxSize;
	private final int productPackingInfoMaxSize;
	private final String truncationIndicator;

	public ProductService(
			@Value("${de.metas.procurement.webui.service.ProductNameCaptionBuilder.productNameMaxSize:58}") final int productNameMaxSize,
			@Value("${de.metas.procurement.webui.service.ProductNameCaptionBuilder.productPackingInfoMaxSize:30}") final int productPackingInfoMaxSize,
			@Value("${de.metas.procurement.webui.service.ProductNameCaptionBuilder.truncationIndicator:...}") final String truncationIndicator)
	{
		this.productNameMaxSize = productNameMaxSize;
		this.productPackingInfoMaxSize = productPackingInfoMaxSize;
		this.truncationIndicator = truncationIndicator;
	}

	public ProductNameCaptionBuilder newProductNameCaptionBuilder()
	{
		return new ProductNameCaptionBuilder()
				.setProductNameMaxSize(productNameMaxSize)
				.setProductPackingInfoMaxSize(productPackingInfoMaxSize)
				.setTruncationIndicator(truncationIndicator);
	}
}
