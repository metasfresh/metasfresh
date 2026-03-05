package de.metas.order.model.interceptor;

import de.metas.bpartner.BPartnerSupplierApprovalService;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
import de.metas.shipping.PurchaseOrderToShipperTransportationService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import de.metas.adempiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class C_Order_PromotionCodeTest
{
	private C_Order interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		interceptor = new C_Order(
				mock(IBPartnerBL.class),
				mock(OrderLineDetailRepository.class),
				mock(IDocumentLocationBL.class),
				mock(BPartnerSupplierApprovalService.class),
				mock(PurchaseOrderToShipperTransportationService.class),
				mock(OrderPayScheduleService.class));
	}

	@Test
	void sameCode_throws()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_PromotionCode_ID(100);
		order.setC_PromotionCode2_ID(100);

		assertThatThrownBy(() -> interceptor.validateNoDuplicatePromotionCode(order))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void differentCodes_passes()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_PromotionCode_ID(100);
		order.setC_PromotionCode2_ID(200);

		assertThatCode(() -> interceptor.validateNoDuplicatePromotionCode(order))
				.doesNotThrowAnyException();
	}

	@Test
	void onlyOneCodeSet_passes()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_PromotionCode_ID(100);

		assertThatCode(() -> interceptor.validateNoDuplicatePromotionCode(order))
				.doesNotThrowAnyException();
	}

	@Test
	void bothCodesZero_passes()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);

		assertThatCode(() -> interceptor.validateNoDuplicatePromotionCode(order))
				.doesNotThrowAnyException();
	}
}
