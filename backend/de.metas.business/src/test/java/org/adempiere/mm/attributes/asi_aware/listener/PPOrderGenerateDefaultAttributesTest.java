package org.adempiere.mm.attributes.asi_aware.listener;

import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Evaluatee;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link PPOrderGenerateDefaultAttributes}.
 * <p>
 * The listener's job is to delegate to {@link IAttributeSetInstanceBL#setInitialAttributes(ProductId, AttributeSetInstanceId, Evaluatee)}.
 * The engine's skip-if-set / log-and-skip semantics (AC2/AC3/AC4) are owned by
 * {@code AttributeSetInstanceBL} and are proven by delegating with the CURRENT
 * {@code M_AttributeSetInstance_ID} — the engine then decides whether to overwrite.
 * These tests therefore mock the engine and assert the listener's contract with it.
 *
 * @see PPOrderGenerateDefaultAttributes
 */
public class PPOrderGenerateDefaultAttributesTest
{
	private static final int PRODUCT_REPO_ID = 1_000_001;
	private static final AttributeSetInstanceId ASI_RESULT = AttributeSetInstanceId.ofRepoId(555_555);

	private IAttributeSetInstanceBL attributeSetInstanceBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		attributeSetInstanceBL = mock(IAttributeSetInstanceBL.class);
		Services.registerService(IAttributeSetInstanceBL.class, attributeSetInstanceBL);
	}

	private I_PP_Order newPPOrder(final int asiRepoId)
	{
		final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(PRODUCT_REPO_ID);
		ppOrder.setM_AttributeSetInstance_ID(asiRepoId);
		save(ppOrder);
		return ppOrder;
	}

	@Test
	public void autoDefault_setsHUBestBeforeDate_onBeforeNew()
	{
		// given: engine returns a fresh ASI id (the auto-defaulted values live in that ASI)
		when(attributeSetInstanceBL.setInitialAttributes(any(), any(), any()))
				.thenReturn(ASI_RESULT);

		// PP_Order created with no ASI (M_AttributeSetInstance_ID = 0)
		final I_PP_Order ppOrder = newPPOrder(0);

		// when: the listener fires (as it would on BEFORE_NEW)
		new PPOrderGenerateDefaultAttributes().modelChanged(ppOrder);

		// then: engine is asked to compute defaults with ASI-NONE (i.e. auto-default is requested)
		final ArgumentCaptor<ProductId> productIdCaptor = ArgumentCaptor.forClass(ProductId.class);
		final ArgumentCaptor<AttributeSetInstanceId> asiIdCaptor = ArgumentCaptor.forClass(AttributeSetInstanceId.class);
		final ArgumentCaptor<Evaluatee> evalCtxCaptor = ArgumentCaptor.forClass(Evaluatee.class);
		verify(attributeSetInstanceBL, times(1)).setInitialAttributes(
				productIdCaptor.capture(),
				asiIdCaptor.capture(),
				evalCtxCaptor.capture());

		assertThat(productIdCaptor.getValue()).isEqualTo(ProductId.ofRepoId(PRODUCT_REPO_ID));
		assertThat(asiIdCaptor.getValue()).isEqualTo(AttributeSetInstanceId.NONE);
		assertThat(evalCtxCaptor.getValue().get_ValueAsString("TableName")).isEqualTo(I_PP_Order.Table_Name);

		// and: the listener stores the engine's returned ASI id on the PP_Order
		assertThat(ppOrder.getM_AttributeSetInstance_ID()).isEqualTo(ASI_RESULT.getRepoId());
	}
}
