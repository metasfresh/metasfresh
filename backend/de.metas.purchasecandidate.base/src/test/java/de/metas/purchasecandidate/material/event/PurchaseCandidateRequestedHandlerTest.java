package de.metas.purchasecandidate.material.event;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidateId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public class PurchaseCandidateRequestedHandlerTest
{
	private ProductId productId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		
		setupMasterdata();
	}

	private void setupMasterdata()
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(60);
		product.setValue("Value");
		product.setName("Name");
		save(product);
		this.productId = ProductId.ofRepoId(product.getM_Product_ID());
	}

	MaterialDescriptor createMaterialDescriptor()
	{
		return MaterialDescriptor.builder()
				.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(productId.getRepoId()))
				.warehouseId(WarehouseId.ofRepoId(40))
				.quantity(BigDecimal.ONE)
				.date(SystemTime.asInstant())
				.build();
	}

	@Test
	public void createCandidateCreatedEvent()
	{
		final PurchaseCandidateRequestedEvent requestedEvent = PurchaseCandidateRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(5, 6))
				.supplyCandidateRepoId(10)
				.purchaseMaterialDescriptor(createMaterialDescriptor())
				.build();
		final BPartnerId vendorId = BPartnerId.ofRepoId(30);
		final PurchaseCandidateId purchaseCandidateId = PurchaseCandidateId.ofRepoId(20);

		final PurchaseCandidateCreatedEvent result = PurchaseCandidateRequestedHandler.createCandidateCreatedEvent(requestedEvent,
				vendorId,
				purchaseCandidateId);

		assertThat(result.getVendorId()).isEqualTo(vendorId.getRepoId());
		assertThat(result.getSupplyCandidateRepoId()).isEqualTo(10);
	}

}
