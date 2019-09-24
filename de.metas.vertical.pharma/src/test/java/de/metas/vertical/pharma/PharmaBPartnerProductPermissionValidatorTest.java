package de.metas.vertical.pharma;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineRepository;
import de.metas.vertical.pharma.model.I_C_BPartner;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.interceptor.C_OrderLine;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		// needed to register the spring context with the Adempiere main class
		StartupListener.class, ShutdownListener.class,

		// needed so that the spring context can discover those components. Note that there are other ways too, but this one is very fast
		PharmaBPartnerRepository.class,
		PharmaBPartnerProductPermissionValidator.class,
		OrderLineRepository.class,
		PharmaProductRepository.class,
		C_OrderLine.class,
})
public class PharmaBPartnerProductPermissionValidatorTest
{
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private C_OrderLine orderLineInterceptor;

	@BeforeClass
	public static void initStatic()
	{
		AdempiereTestHelper.get().init();
	}

	@Before
	public void init()
	{
		orderLineInterceptor = SpringContextHolder.instance.getBean(C_OrderLine.class);
	}

	// here starts the CUSTOMER part
	@Test
	public void assertTypeBbPartnerCanReceiveNonRxProduct()
	{
		final I_M_Product mProduct = createMProduct("NonPrescription(Non-RX)Product", false, false, "NonPrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeB, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final CurrencyId currency = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeBbPartnerCanNotReceiveRxProduct()
	{
		final I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeB, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(PharmaBPartnerProductPermissionValidator.MSG_NoPrescriptionPermission_Sales);
		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanReceiveNonRxProduct()
	{
		final I_M_Product mProduct = createMProduct("NonPrescription(Non-RX)Product", false, false, "NonPrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeA, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanReceiveRxProduct()
	{
		final I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeA, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanNotReceiveRxNarcoticProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeA, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(PharmaBPartnerProductPermissionValidator.MSG_NoNarcoticPermission_Sales);
		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeBbPartnerCanNotReceiveRxNarcoticProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeB, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(PharmaBPartnerProductPermissionValidator.MSG_NoNarcoticPermission_Sales);
		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeCbPartnerCanReceiveRxNarcoticProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Narcotic Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeC, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	// here starts the VENDOR part

	@Test
	public void assertTypeBbPartnerCanSellNonRxProduct()
	{
		final I_M_Product mProduct = createMProduct("NonPrescription(Non-RX)Product", false, false, "NonPrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeB);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeBbPartnerCanNotSellRxProduct()
	{
		final I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeB);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(PharmaBPartnerProductPermissionValidator.MSG_NoPrescriptionPermission_Purchase);
		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanSellNonRxProduct()
	{
		final I_M_Product mProduct = createMProduct("NonPrescription(Non-RX)Product", false, false, "NonPrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeA);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanSellRxProduct()
	{
		final I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeA);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanNotReceiveRxSellProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeA);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(PharmaBPartnerProductPermissionValidator.MSG_NoNarcoticPermission_Purchase);
		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeBbPartnerCanNotSellRxNarcoticProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeB);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(PharmaBPartnerProductPermissionValidator.MSG_NoNarcoticPermission_Purchase);
		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	@Test
	public void assertTypeCbPartnerCanReceiveRxSellProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Narcotic Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeC);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final CurrencyId currencyId = createCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, currencyId, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatebPartnerProductPermissions(cOrderLine);
	}

	/**
	 * All the create* methods should belong to a god-object TestHelperFactory of some kind.
	 * It's really bad to keep creating these for all tests in all the different projects.
	 */

	private I_C_OrderLine createCOrderLine(
			final I_M_Product mProduct, 
			final I_M_Warehouse mWarehouse, 
			final I_C_BPartner cbPartner, 
			final I_C_PaymentTerm cPaymentTerm, 
			final I_C_Order cOrder, 
			final CurrencyId currencyId, 
			final I_C_UOM cUom, 
			final BigDecimal priceActual, 
			final BigDecimal qtyEntered)
	{
		final I_C_OrderLine cOrderLine = newInstance(I_C_OrderLine.class);
		cOrderLine.setM_Product_ID(mProduct.getM_Product_ID());
		cOrderLine.setM_Warehouse_ID(mWarehouse.getM_Warehouse_ID());
		cOrderLine.setC_BPartner_ID(cbPartner.getC_BPartner_ID());
		cOrderLine.setC_PaymentTerm_Override_ID(cPaymentTerm.getC_PaymentTerm_ID());
		cOrderLine.setDatePromised(Timestamp.valueOf(LocalDateTime.now().minusHours(1)));
		cOrderLine.setC_Order_ID(cOrder.getC_Order_ID());
		cOrderLine.setC_Currency_ID(currencyId.getRepoId());
		cOrderLine.setPriceActual(priceActual);
		cOrderLine.setQtyEntered(qtyEntered);
		cOrderLine.setC_UOM_ID(cUom.getC_UOM_ID());
		save(cOrderLine);
		return cOrderLine;
	}

	private I_C_UOM createCUom()
	{
		final I_C_UOM cUom = newInstance(I_C_UOM.class);
		save(cUom);
		return cUom;
	}

	private CurrencyId createCurrency()
	{
		return PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
	}

	private I_C_Order createCOrder(final boolean isSOTrx)
	{
		final I_C_Order cOrder = newInstance(I_C_Order.class);
		cOrder.setIsSOTrx(isSOTrx);
		save(cOrder);
		return cOrder;
	}

	private I_C_PaymentTerm createCPaymentTerm()
	{
		final I_C_PaymentTerm cPaymentTerm = newInstance(I_C_PaymentTerm.class);
		save(cPaymentTerm);
		return cPaymentTerm;
	}

	private I_C_BPartner createCBpartner(final String name, final boolean isCustomer, @Nullable final String shipmentPermissionPharma, final boolean isVendor, @Nullable final String receiptPermissionPharma)
	{
		// since this is a sales order, this should be the seller, right?
		final I_C_BPartner cbPartner = newInstance(I_C_BPartner.class);
		cbPartner.setName(name);
		// customer
		cbPartner.setShipmentPermissionPharma(shipmentPermissionPharma);
		if (I_C_BPartner.ShipmentPermissionPharma_TypeA.equals(shipmentPermissionPharma) || I_C_BPartner.ShipmentPermissionPharma_TypeC.equals(shipmentPermissionPharma))
		{
			// need to have 1 PharmaCustomerPermission.
			// this should be done automatically by the interceptor
			// de.metas.vertical.pharma.model.interceptor.C_BPartner.onPharmaPermissionChanged_Customer
			// but i can't figure out how to add it right now to the test.
			cbPartner.setIsPharmaciePermission(true);
		}
		cbPartner.setIsCustomer(isCustomer);
		// vendor
		cbPartner.setReceiptPermissionPharma(receiptPermissionPharma);
		if (I_C_BPartner.ReceiptPermissionPharma_TypeA.equals(receiptPermissionPharma) || I_C_BPartner.ReceiptPermissionPharma_TypeC.equals(receiptPermissionPharma))
		{
			cbPartner.setIsPharmaVendorAgentPermission(true);
		}
		cbPartner.setIsVendor(isVendor);
		save(cbPartner);
		return cbPartner;
	}

	private I_M_Warehouse createMWarehouse()
	{
		final I_M_Warehouse mWarehouse = newInstance(I_M_Warehouse.class);
		save(mWarehouse);
		return mWarehouse;
	}

	private I_M_Product createMProduct(final String name, final boolean isPrescription, final boolean isNarcotic, final String vitaminC)
	{
		final I_M_Product mProduct = newInstance(I_M_Product.class);
		mProduct.setName(name);
		mProduct.setIsPrescription(isPrescription);
		mProduct.setIsNarcotic(isNarcotic);
		mProduct.setValue(vitaminC);
		save(mProduct);
		return mProduct;
	}
}
