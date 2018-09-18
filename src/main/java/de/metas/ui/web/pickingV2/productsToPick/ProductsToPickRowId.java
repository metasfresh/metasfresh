package de.metas.ui.web.pickingV2.productsToPick;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@Value
class ProductsToPickRowId
{
	private HuId huId;
	private ProductId productId;

	private DocumentId documentId;

	@Builder
	private ProductsToPickRowId(
			@NonNull final HuId huId,
			@NonNull final ProductId productId)
	{
		this.huId = huId;
		this.productId = productId;
		this.documentId = createDocumentId(huId, productId);
	}

	public DocumentId toDocumentId()
	{
		return documentId;
	}

	private static DocumentId createDocumentId(final HuId huId, final ProductId productId)
	{
		return DocumentId.ofString(huId.getRepoId() + "_" + productId.getRepoId());
	}

}
