package de.metas.material.planning.event;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Collections;

import org.compiere.model.I_M_Product;

import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.impl.SimpleMRPNoteBuilder;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public class SupplyRequiredHandlerUtils
{

	public IMaterialRequest mkRequest(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final IMaterialPlanningContext mrpContext)
	{
		final int descriptorBPartnerId = supplyRequiredDescriptor.getMaterialDescriptor().getBPartnerId();

		final int productId = supplyRequiredDescriptor.getMaterialDescriptor().getProductId();
		final I_M_Product product = load(productId, I_M_Product.class);

		final BigDecimal qtyToSupply = supplyRequiredDescriptor.getMaterialDescriptor().getQuantity();

		return MaterialRequest.builder()
				.qtyToSupply(Quantity.of(qtyToSupply, product.getC_UOM()))
				.mrpContext(mrpContext)
				.mrpDemandBPartnerId(descriptorBPartnerId > 0 ? descriptorBPartnerId : -1)
				.mrpDemandOrderLineSOId(supplyRequiredDescriptor.getOrderLineId())
				.demandDate(supplyRequiredDescriptor.getMaterialDescriptor().getDate())
				.build();
	}

	public IMRPNotesCollector mkMRPNotesCollector()
	{
		return new IMRPNotesCollector()
		{
			@Override
			public IMRPNoteBuilder newMRPNoteBuilder(final IMaterialPlanningContext mrpContext, final String mrpErrorCode)
			{
				final SimpleMRPNoteBuilder simpleMRPNoteBuilder = new SimpleMRPNoteBuilder(this, mrps -> Collections.emptySet());
				return simpleMRPNoteBuilder;
			}

			@Override
			public void collectNote(final IMRPNoteBuilder noteBuilder)
			{
				// as long as newMRPNoteBuilder() creates a SimpleMRPNoteBuilder with *this* as its node collector,
				// the following invocation would cause a StackOverFlowError. Plus, idk if and what to actually collect in this use case.
				// noteBuilder.collect();
			}
		};
	}

}
