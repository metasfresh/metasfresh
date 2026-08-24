package org.eevolution.model.validator;

import de.metas.ad_reference.ADReferenceService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.X_PP_Cost_Collector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

/**
 * Tests the product life-cycle (BBS-status) manufacturing guard on the finished-good RECEIPT
 * ({@link PP_Cost_Collector#assertManufacturingAllowedOnReceipt(I_PP_Cost_Collector)}). A product whose
 * {@code ProductLifeCycleStatus} blocks MANUFACTURE (G/Gesperrt) must not be received from a production
 * order — closing the gap where the {@code PP_Order} guard fires only at order creation, not at receipt
 * (so a product blocked AFTER its order was created could still be received, in both webUI and mobile).
 * <p>
 * Direct-method approach, as in {@link PP_OrderTest}: the guard reads the product's status through the
 * real {@code IProductBL} from the test {@code POJOLookupMap}, so the product's status drives the outcome.
 */
public class PP_Cost_CollectorTest
{
	private PP_Cost_Collector interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());
		interceptor = new PP_Cost_Collector();
	}

	private int createProduct(final String lifeCycleStatus)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("P1");
		product.setName("P1");
		product.setProductLifeCycleStatus(lifeCycleStatus);
		save(product);
		return product.getM_Product_ID();
	}

	private I_PP_Cost_Collector receiptCostCollector(final int productId)
	{
		final I_PP_Cost_Collector cc = newInstance(I_PP_Cost_Collector.class);
		cc.setCostCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt);
		cc.setM_Product_ID(productId);
		return cc;
	}

	@Test
	public void blockedProduct_receipt_isRejected()
	{
		// PRODUCTLIFECYCLESTATUS_Blocked = "G" = BLOCKED (blocks every action, incl. MANUFACTURE)
		final I_PP_Cost_Collector cc = receiptCostCollector(createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked));

		assertThatThrownBy(() -> interceptor.assertManufacturingAllowedOnReceipt(cc))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("M_Product_BBSStatus_ActionBlocked");
	}

	@Test
	public void okProduct_receipt_isAllowed()
	{
		// PRODUCTLIFECYCLESTATUS_OK = "O" = fully permissive => guard must NOT fire.
		final I_PP_Cost_Collector cc = receiptCostCollector(createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK));

		assertThatCode(() -> interceptor.assertManufacturingAllowedOnReceipt(cc))
				.doesNotThrowAnyException();
	}

	@Test
	public void blockedProduct_componentIssue_isAllowed()
	{
		// The guard fires only on the finished-good MaterialReceipt, never on a component ISSUE
		// (which consumes a different product carrying its own status).
		final I_PP_Cost_Collector cc = receiptCostCollector(createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked));
		cc.setCostCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue);

		assertThatCode(() -> interceptor.assertManufacturingAllowedOnReceipt(cc))
				.doesNotThrowAnyException();
	}

	@Test
	public void blockedProduct_coProductReceipt_isRejected()
	{
		// A co/by-product receipt (MixVariance) materialises a manufactured product too, so a Blocked
		// co-product must also be rejected.
		final I_PP_Cost_Collector cc = receiptCostCollector(createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked));
		cc.setCostCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance);

		assertThatThrownBy(() -> interceptor.assertManufacturingAllowedOnReceipt(cc))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("M_Product_BBSStatus_ActionBlocked");
	}

	@Test
	public void blockedProduct_reversalReceipt_isAllowed()
	{
		// A reversal/void of an already-completed receipt (Reversal_ID set) must NEVER be retroactively
		// blocked, even if the product is now Blocked — otherwise a legitimately-completed receipt could
		// not be undone. Mirrors the M_InOut reversal exemption.
		final I_PP_Cost_Collector cc = receiptCostCollector(createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked));
		cc.setReversal_ID(1_000_000); // any existing receipt — POJO does not enforce the FK

		assertThatCode(() -> interceptor.assertManufacturingAllowedOnReceipt(cc))
				.doesNotThrowAnyException();
	}
}
