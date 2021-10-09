package de.metas.material.dispo.commons.repository.atp;

import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.commons.attributes.clasifiers.WarehouseClassifier;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
@ToString(exclude = "storageAttributesKey" /* because it's just gibberish most of the time */)
public final class AvailableToPromiseResultGroup
{
	BPartnerClassifier bpartner;
	WarehouseClassifier warehouse;
	ProductId productId;
	AttributesKey storageAttributesKey;

	BigDecimal qty;

	@Builder
	private AvailableToPromiseResultGroup(
			@NonNull final BPartnerClassifier bpartner,
			@NonNull final WarehouseClassifier warehouse,
			@NonNull final ProductId productId,
			@NonNull final AttributesKey storageAttributesKey,
			@NonNull final BigDecimal qty)
	{

		this.bpartner = bpartner;
		this.warehouse = warehouse;
		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;
		this.qty = qty;
	}
}
