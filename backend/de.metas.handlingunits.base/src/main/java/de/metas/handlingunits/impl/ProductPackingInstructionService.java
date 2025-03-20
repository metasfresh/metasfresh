/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.product.IProductPackingInstructionService;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.model.I_I_Product;
import org.springframework.stereotype.Service;

@Service
public class ProductPackingInstructionService implements IProductPackingInstructionService
{

	public void handlePackingInstructions(@NonNull final I_I_Product importRecord, @NonNull final ProductId productId)
	{
		if (Check.isEmpty(importRecord.getM_HU_PI_Value()))
		{
			return;
		}

		final ProductPackingInstructionsRequest request = ProductPackingInstructionsRequest.builder()
				.productId(productId)
				.qtyCU(importRecord.getQtyCU())
				.bPartnerId(BPartnerId.ofRepoIdOrNull(importRecord.getC_BPartner_ID()))
				.M_HU_PI_Value(importRecord.getM_HU_PI_Value())
				.isDefault(importRecord.isDefaultPacking())
				.isInfiniteCapacity(importRecord.getQtyCU() != null)
				.build();

		CreatePackingInstructionsCommand.builder()
				.request(request)
				.build()
				.execute();
	}
}
