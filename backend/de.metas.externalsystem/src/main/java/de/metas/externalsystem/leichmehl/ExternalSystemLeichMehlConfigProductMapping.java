/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.leichmehl;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemLeichMehlConfigProductMapping
{
	@NonNull
	ExternalSystemLeichMehlConfigProductMappingId id;

	@NonNull
	Integer seqNo;

	@NonNull
	String pluFile;

	@Nullable
	ProductCategoryId productCategoryId;

	@Nullable
	ProductId productId;

	@Nullable
	BPartnerId bPartnerId;

	@Builder
	public ExternalSystemLeichMehlConfigProductMapping(
			@NonNull final ExternalSystemLeichMehlConfigProductMappingId id,
			@NonNull final Integer seqNo,
			@NonNull final String pluFile,
			@Nullable final ProductCategoryId productCategoryId,
			@Nullable final ProductId productId,
			@Nullable final BPartnerId bPartnerId)
	{
		this.id = id;
		this.seqNo = seqNo;
		this.pluFile = pluFile;
		this.productCategoryId = productCategoryId;
		this.productId = productId;
		this.bPartnerId = bPartnerId;
	}

	public boolean isMatchingQuery(@NonNull final LeichMehlProductMappingQuery query)
	{
		return isMatchingBPartner(query.getBPartnerId())
				&& isMatchingProduct(query.getProductId())
				&& isMatchingProductCategory(query.getProductCategoryId());
	}

	private boolean isMatchingBPartner(@Nullable final BPartnerId candidateBPartnerId)
	{
		if (candidateBPartnerId == null && this.bPartnerId == null)
		{
			return true;
		}
		else if (this.bPartnerId == null)
		{
			return true;
		}
		else if (candidateBPartnerId != null)
		{
			return candidateBPartnerId.equals(this.bPartnerId);
		}
		else
		{
			return false;
		}
	}

	private boolean isMatchingProduct(@Nullable final ProductId candidateProductId)
	{
		if (candidateProductId == null && this.productId == null)
		{
			return true;
		}
		else if (this.productId == null)
		{
			return true;
		}
		else if (candidateProductId != null)
		{

			return candidateProductId.equals(this.productId);
		}
		else
		{
			return false;
		}
	}

	private boolean isMatchingProductCategory(@Nullable final ProductCategoryId candidateProductCategoryId)
	{
		if (candidateProductCategoryId == null && this.productCategoryId == null)
		{
			return true;
		}
		else if (this.productCategoryId == null)
		{
			return true;
		}
		else if (candidateProductCategoryId != null)
		{
			return candidateProductCategoryId.equals(this.productCategoryId);
		}
		else
		{
			return false;
		}
	}
}
