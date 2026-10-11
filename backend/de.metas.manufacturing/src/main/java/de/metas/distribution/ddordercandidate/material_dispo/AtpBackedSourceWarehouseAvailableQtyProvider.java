/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.distribution.ddordercandidate.material_dispo;

import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.ddordercandidate.SourceWarehouseAvailableQtyProvider;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

/**
 * Binds {@link SourceWarehouseAvailableQtyProvider} to the MD_Candidate-backed
 * {@link AvailableToPromiseRepository}. When this bean is present in the Spring context,
 * {@code DDOrderCandidateDataFactory} will clip each candidate's qty by the actual
 * available stock at the source warehouse, so partial DD fulfillment is possible and
 * the remainder spills to manufacturing / purchasing via {@code SupplyRequiredHandler}.
 *
 * <p>Introduced for <a href="https://github.com/metasfresh/me03/issues/28877">me03#28877</a>.
 */
@Component
@RequiredArgsConstructor
public class AtpBackedSourceWarehouseAvailableQtyProvider implements SourceWarehouseAvailableQtyProvider
{
	@NonNull private final AvailableToPromiseRepository availableToPromiseRepository;

	@NonNull
	@Override
	public Optional<Quantity> availableQtyAtSource(
			@NonNull final WarehouseId sourceWarehouseId,
			@NonNull final ProductId productId,
			@NonNull final AttributesKey storageAttributesKey,
			@NonNull final Instant demandDate)
	{
		final AvailableToPromiseQuery query = AvailableToPromiseQuery.builder()
				.warehouseId(sourceWarehouseId)
				.productId(productId.getRepoId())
				.storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(storageAttributesKey))
				.date(TimeUtil.asZonedDateTime(demandDate))
				.build();

		final BigDecimal qtyBD = availableToPromiseRepository.retrieveAvailableStockQtySum(query);
		if (qtyBD.signum() <= 0)
		{
			// Explicitly report zero — DDOrderCandidateDataFactory will skip this source line entirely.
			return Optional.of(Quantitys.of(BigDecimal.ZERO, productId));
		}
		return Optional.of(Quantitys.of(qtyBD, productId));
	}
}
