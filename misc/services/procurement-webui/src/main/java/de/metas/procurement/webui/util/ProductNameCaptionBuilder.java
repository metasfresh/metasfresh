package de.metas.procurement.webui.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vaadin.spring.annotation.PrototypeScope;

import com.google.gwt.thirdparty.guava.common.base.Ascii;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;

import de.metas.procurement.webui.Application;

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

/**
 * Helper class used to build captions of product name, including packing info and other details.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@Component
@PrototypeScope
public class ProductNameCaptionBuilder
{
	public static final ProductNameCaptionBuilder newBuilder()
	{
		// ask the spring context to create the builder because we also need to wire some variables
		return Application.getContext().getBean(ProductNameCaptionBuilder.class);
		//return new ProductNameCaptionBuilder();
	}

	private String productName;
	private String productPackingInfo;

	private String productNameDetailShortInfo;

	@Value("${de.metas.procurement.webui.util.ProductNameCaptionBuilder.productNameMaxSize:58}")
	private int productNameMaxSize;
	@Value("${de.metas.procurement.webui.util.ProductNameCaptionBuilder.productPackingInfoMaxSize:30}")
	private int productPackingInfoMaxSize;
	@Value("${de.metas.procurement.webui.util.ProductNameCaptionBuilder.truncationIndicator:...}")
	private String truncationIndicator = "...";

	private ProductNameCaptionBuilder()
	{
		super();
	}

	public String build()
	{
		final String productNameTruncated;
		if (productName == null || productName.isEmpty())
		{
			productNameTruncated = truncationIndicator == null ? "" : truncationIndicator;
		}
		else if (productNameMaxSize > 0)
		{
			productNameTruncated = Ascii.truncate(productName.trim(), productNameMaxSize, truncationIndicator);
		}
		else
		{
			productNameTruncated = productName.trim();
		}

		final StringBuilder caption = new StringBuilder();
		caption.append(productNameTruncated);

		if (productNameDetailShortInfo != null && !productNameDetailShortInfo.isEmpty())
		{
			caption.append(" (").append(productNameDetailShortInfo).append(")");
		}

		if (productPackingInfo != null && !productPackingInfo.isEmpty())
		{
			final String packingInfoTruncated = Ascii.truncate(productPackingInfo.trim(), productPackingInfoMaxSize, truncationIndicator);
			caption.append("\n").append(packingInfoTruncated);
		}

		return caption.toString();
	}

	public ProductNameCaptionBuilder setProductName(final String productName)
	{
		this.productName = productName;
		return this;
	}

	public ProductNameCaptionBuilder setProductPackingInfo(final String productPackingInfo)
	{
		this.productPackingInfo = productPackingInfo;
		return this;
	}

	public ProductNameCaptionBuilder setProductNameDetailShortInfo(final String productNameDetailShortInfo)
	{
		this.productNameDetailShortInfo = productNameDetailShortInfo;
		return this;
	}

	public ProductNameCaptionBuilder setProductNameMaxSize(final int productNameMaxSize)
	{
		this.productNameMaxSize = productNameMaxSize;
		return this;
	}

	public ProductNameCaptionBuilder setProductPackingInfoMaxSize(final int productPackingInfoMaxSize)
	{
		this.productPackingInfoMaxSize = productPackingInfoMaxSize;
		return this;
	}

	public ProductNameCaptionBuilder setTruncationIndicator(final String truncationIndicator)
	{
		Preconditions.checkNotNull(truncationIndicator);
		this.truncationIndicator = truncationIndicator;
		return this;
	}
}
