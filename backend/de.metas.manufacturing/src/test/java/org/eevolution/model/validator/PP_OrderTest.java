package org.eevolution.model.validator;

import de.metas.ad_reference.ADReferenceService;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

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
 * Tests the product life-cycle (BBS-status) manufacturing guard added at the top of
 * {@link PP_Order#validateBOMAndProduct(I_PP_Order)}: a product whose
 * {@code ProductLifeCycleStatus} blocks MANUFACTURE must be rejected regardless of BOM state.
 * <p>
 * Uses the deterministic direct-method approach: the guard reads the product's status through the
 * real {@code IProductBL} (which resolves it from the {@code I_M_Product} POJO in the test
 * {@code POJOLookupMap}), so setting the status on the product record drives the outcome.
 * The BOM is always set up to MATCH the product, so the ONLY thing that can differ between the two
 * cases is the life-cycle guard.
 */
public class PP_OrderTest
{
	private PP_Order interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());

		// validateBOMAndProduct uses only the Services.get(...) fields (productBL, productBOMDAO);
		// the constructor collaborators are irrelevant to it, so mocks suffice.
		interceptor = new PP_Order(
				mock(PPOrderPojoConverter.class),
				mock(PostMaterialEventService.class),
				mock(IDocumentNoBuilderFactory.class),
				mock(IPPOrderBOMBL.class),
				mock(ProductBOMVersionsDAO.class));
	}

	private I_M_Product createProduct(final String lifeCycleStatus)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("P1");
		product.setName("P1");
		product.setProductLifeCycleStatus(lifeCycleStatus);
		save(product);
		return product;
	}

	private I_PP_Product_BOM createMatchingBOM(final I_M_Product product)
	{
		final I_PP_Product_BOM bom = newInstance(I_PP_Product_BOM.class);
		bom.setM_Product_ID(product.getM_Product_ID());
		save(bom);
		return bom;
	}

	private I_PP_Order createPPOrder(final I_M_Product product, final I_PP_Product_BOM bom)
	{
		final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(product.getM_Product_ID());
		ppOrder.setPP_Product_BOM_ID(bom.getPP_Product_BOM_ID());
		return ppOrder;
	}

	@Test
	public void blockedProduct_manufacture_isRejected()
	{
		// PRODUCTLIFECYCLESTATUS_Blocked = "G" = BLOCKED (blocks every action, incl. MANUFACTURE)
		final I_M_Product product = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked);
		final I_PP_Product_BOM bom = createMatchingBOM(product); // BOM matches, so only the guard can reject
		final I_PP_Order ppOrder = createPPOrder(product, bom);

		assertThatThrownBy(() -> interceptor.validateBOMAndProduct(ppOrder))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("M_Product_BBSStatus_ActionBlocked");
	}

	@Test
	public void okProduct_manufacture_isAllowed()
	{
		// PRODUCTLIFECYCLESTATUS_OK = "O" = fully permissive => guard must NOT fire.
		// BOM matches the product, so validateBOMAndProduct passes cleanly.
		final I_M_Product product = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK);
		final I_PP_Product_BOM bom = createMatchingBOM(product);
		final I_PP_Order ppOrder = createPPOrder(product, bom);

		assertThatCode(() -> interceptor.validateBOMAndProduct(ppOrder))
				.doesNotThrowAnyException();
	}

	@Test
	public void completion_productFlippedToAuslauf_isRejected()
	{
		// The order was legitimately created while the product was still 'O'; the status was flipped to
		// 'A' (Auslauf, blocks MANUFACTURE) afterwards, so only the completion re-check can catch it.
		final I_M_Product product = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_PhaseOut);
		final I_PP_Order ppOrder = createPPOrder(product, createMatchingBOM(product));

		assertThatThrownBy(() -> interceptor.assertProductAllowedOnComplete(ppOrder))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("M_Product_BBSStatus_ActionBlocked");
	}

	@Test
	public void completion_okProduct_isAllowed()
	{
		final I_M_Product product = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK);
		final I_PP_Order ppOrder = createPPOrder(product, createMatchingBOM(product));

		assertThatCode(() -> interceptor.assertProductAllowedOnComplete(ppOrder))
				.doesNotThrowAnyException();
	}
}
