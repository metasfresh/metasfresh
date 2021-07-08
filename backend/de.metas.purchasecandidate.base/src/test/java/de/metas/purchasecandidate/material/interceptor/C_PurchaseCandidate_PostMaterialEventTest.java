package de.metas.purchasecandidate.material.interceptor;

import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateUpdatedEvent;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

public class C_PurchaseCandidate_PostMaterialEventTest
{
	private ModelProductDescriptorExtractor productDescriptorFactory;
	private C_PurchaseCandidate_PostMaterialEvent mi;

	private UomId uomId;
	private WarehouseId warehouseId;
	private ProductId productId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);
		productDescriptorFactory = Mockito.mock(ModelProductDescriptorExtractor.class);

		mi = new C_PurchaseCandidate_PostMaterialEvent(postMaterialEventService, productDescriptorFactory, new ReplenishInfoRepository());

		final I_C_UOM uomRecord = BusinessTestHelper.createUomKg();
		uomId = UomId.ofRepoId(uomRecord.getC_UOM_ID());
		final I_M_Product productRecord = BusinessTestHelper.createProduct("Product", uomRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
		final I_M_Warehouse warehouseRecord = BusinessTestHelper.createWarehouse("Warehouse");
		warehouseId = WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());
	}

	@Test
	public void createUpdatedEvent()
	{
		final I_C_PurchaseCandidate purchaseCandidateRecord = newInstance(I_C_PurchaseCandidate.class);
		purchaseCandidateRecord.setM_Product_ID(productId.getRepoId());
		purchaseCandidateRecord.setC_UOM_ID(uomId.getRepoId());
		purchaseCandidateRecord.setM_WarehousePO_ID(warehouseId.getRepoId());
		purchaseCandidateRecord.setPurchaseDatePromised(SystemTime.asTimestamp());
		purchaseCandidateRecord.setVendor_ID(30);
		save(purchaseCandidateRecord);

		final ProductDescriptor p = ProductDescriptor.completeForProductIdAndEmptyAttribute(productId.getRepoId());

		Mockito.doReturn(p).when(productDescriptorFactory).createProductDescriptor(purchaseCandidateRecord);

		// invoke the method under test
		final PurchaseCandidateUpdatedEvent result = mi.createUpdatedEvent(purchaseCandidateRecord);

		assertThat(result).isNotNull();
		assertThat(result.getPurchaseMaterialDescriptor().getWarehouseId()).isEqualTo(warehouseId);
		assertThat(result.getVendorId()).isEqualTo(30);
	}

}
