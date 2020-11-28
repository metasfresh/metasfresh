package de.metas.material.dispo.commons.repository.atp;

import java.math.BigDecimal;
import java.time.Instant;

import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Util.ArrayKey;

import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
public final class AddToResultGroupRequest
{
	WarehouseId warehouseId;
	int productId;
	BPartnerClassifier bpartner;
	AttributesKey storageAttributesKey;

	BigDecimal qty;

	Instant date;
	int seqNo; // needed to disambiguated requests with the same date

	@Builder
	public AddToResultGroupRequest(
			@NonNull final WarehouseId warehouseId,
			final int productId,
			@NonNull final AttributesKey storageAttributesKey,
			@NonNull final BPartnerClassifier bpartner,
			@NonNull final BigDecimal qty,
			@NonNull final Instant date,
			final int seqNo)
	{
		this.warehouseId = warehouseId;
		this.productId = Check.assumeGreaterThanZero(productId, "productId");
		this.bpartner = bpartner;
		this.storageAttributesKey = storageAttributesKey;

		this.qty = qty;

		this.date = date;
		this.seqNo = Check.assumeGreaterThanZero(seqNo, "seqNo");
	}

	public ArrayKey computeKey()
	{
		return ArrayKey.of(warehouseId, productId, storageAttributesKey);
	}

	public DateAndSeqNo getDateAndSeqNo()
	{
		return DateAndSeqNo.builder()
				.date(date)
				.seqNo(seqNo)
				.build();
	}
}
