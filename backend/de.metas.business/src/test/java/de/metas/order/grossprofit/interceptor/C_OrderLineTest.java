package de.metas.order.grossprofit.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import de.metas.money.grossprofit.ProfitPriceActualFactory;
import de.metas.order.OrderLineRepository;
import de.metas.order.grossprofit.model.I_C_OrderLine;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class C_OrderLineTest
{
	private static final int PI_ITEM_PRODUCT_ID = 77;

	private ProfitPriceActualFactory profitPriceActualFactory;
	private C_OrderLine interceptor;
	private CurrencyId currencyId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		profitPriceActualFactory = Mockito.mock(ProfitPriceActualFactory.class);
		Mockito.when(profitPriceActualFactory.calculateProfitPriceActual(Mockito.any())).thenReturn(Money.of(BigDecimal.TEN, currencyId));

		interceptor = new C_OrderLine(profitPriceActualFactory, new OrderLineRepository());
	}

	/**
	 * The profit price is calculated while the order line is saved. So it has to use the packing instruction that the line has right now, not the one in the database:
	 * a new line has no database record yet.
	 */
	@Test
	public void updateProfitPriceActual_usesThePackingInstructionOfTheUnsavedLine()
	{
		final I_C_OrderLine unsavedOrderLine = createUnsavedOrderLine();
		InterfaceWrapperHelper.create(unsavedOrderLine, de.metas.interfaces.I_C_OrderLine.class).setM_HU_PI_Item_Product_ID(PI_ITEM_PRODUCT_ID);

		// invoke the method under test
		interceptor.updateProfitPriceActual(unsavedOrderLine);

		final ArgumentCaptor<CalculateProfitPriceActualRequest> requestCaptor = ArgumentCaptor.forClass(CalculateProfitPriceActualRequest.class);
		Mockito.verify(profitPriceActualFactory).calculateProfitPriceActual(requestCaptor.capture());
		assertThat(requestCaptor.getValue().getHuPIItemProductId()).isEqualTo(HUPIItemProductId.ofRepoId(PI_ITEM_PRODUCT_ID));
	}

	@Test
	public void updateProfitPriceActual_lineWithoutPackingInstruction()
	{
		interceptor.updateProfitPriceActual(createUnsavedOrderLine());

		final ArgumentCaptor<CalculateProfitPriceActualRequest> requestCaptor = ArgumentCaptor.forClass(CalculateProfitPriceActualRequest.class);
		Mockito.verify(profitPriceActualFactory).calculateProfitPriceActual(requestCaptor.capture());
		assertThat(requestCaptor.getValue().getHuPIItemProductId()).isNull();
	}

	/**
	 * Refund terms match the sales invoiced to their partner. So when a store orders and its head office is invoiced, the gross profit price has to use the head office's terms.
	 */
	@Test
	public void updateProfitPriceActual_usesTheInvoicePartnerOfTheOrder()
	{
		final I_C_OrderLine unsavedOrderLine = createUnsavedOrderLine();
		final I_C_BPartner headOffice = newInstance(I_C_BPartner.class);
		saveRecord(headOffice);
		final I_C_Order order = unsavedOrderLine.getC_Order();
		order.setBill_BPartner_ID(headOffice.getC_BPartner_ID());
		saveRecord(order);

		interceptor.updateProfitPriceActual(unsavedOrderLine);

		final ArgumentCaptor<CalculateProfitPriceActualRequest> requestCaptor = ArgumentCaptor.forClass(CalculateProfitPriceActualRequest.class);
		Mockito.verify(profitPriceActualFactory).calculateProfitPriceActual(requestCaptor.capture());
		assertThat(requestCaptor.getValue().getBPartnerId()).isEqualTo(BPartnerId.ofRepoId(headOffice.getC_BPartner_ID()));
	}

	@Test
	public void updateProfitPriceActual_orderWithoutInvoicePartner_usesTheOrderPartner()
	{
		final I_C_OrderLine unsavedOrderLine = createUnsavedOrderLine();

		interceptor.updateProfitPriceActual(unsavedOrderLine);

		final ArgumentCaptor<CalculateProfitPriceActualRequest> requestCaptor = ArgumentCaptor.forClass(CalculateProfitPriceActualRequest.class);
		Mockito.verify(profitPriceActualFactory).calculateProfitPriceActual(requestCaptor.capture());
		assertThat(requestCaptor.getValue().getBPartnerId()).isEqualTo(BPartnerId.ofRepoId(unsavedOrderLine.getC_Order().getC_BPartner_ID()));
	}

	private I_C_OrderLine createUnsavedOrderLine()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		saveRecord(bpartner);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		order.setIsSOTrx(true);
		order.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2026, 7, 1)));
		order.setM_Warehouse_ID(5);
		final I_C_PaymentTerm paymentTerm = newInstance(I_C_PaymentTerm.class);
		saveRecord(paymentTerm);
		order.setC_PaymentTerm_ID(paymentTerm.getC_PaymentTerm_ID());
		saveRecord(order);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		orderLine.setM_Product_ID(product.getM_Product_ID());
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		orderLine.setC_Currency_ID(currencyId.getRepoId());
		orderLine.setPriceActual(BigDecimal.TEN);
		orderLine.setQtyEntered(BigDecimal.ONE);
		orderLine.setPrice_UOM_ID(uom.getC_UOM_ID());
		return orderLine; // not saved
	}
}
