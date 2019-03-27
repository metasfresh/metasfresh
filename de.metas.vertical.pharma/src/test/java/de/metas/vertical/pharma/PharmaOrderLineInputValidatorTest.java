package de.metas.vertical.pharma;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.order.OrderLineRepository;
import de.metas.vertical.pharma.model.I_C_BPartner;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.interceptor.C_OrderLine;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.Adempiere;
import org.compiere.model.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static de.metas.vertical.pharma.PharmaPurchaseOrderLineInputValidator.MSG_NoNarcoticPermission;
import static de.metas.vertical.pharma.PharmaSalesOrderLineInputValidator.MSG_NoPrescriptionPermission;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		//		 needed to register the spring context with the Adempiere main class
		StartupListener.class, ShutdownListener.class,

		// needed so that the spring context can discover those components. Note that there are other ways too, but this one is very fast
		PharmaBPartnerRepository.class,
		PharmaSalesOrderLineInputValidator.class,
		PharmaPurchaseOrderLineInputValidator.class,
		OrderLineRepository.class,
		PharmaProductRepository.class,
		C_OrderLine.class,
})
public class PharmaOrderLineInputValidatorTest
{
	@Rule public ExpectedException thrown = ExpectedException.none();

	private C_OrderLine orderLineInterceptor;

	@BeforeClass
	public static void initStatic()
	{
		AdempiereTestHelper.get().init();
	}

	@Before
	public void init()
	{
		orderLineInterceptor = Adempiere.getBean(C_OrderLine.class);
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
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeBbPartnerCanNotReceiveRxProduct()
	{
		final I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeB, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(MSG_NoPrescriptionPermission);
		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanReceiveNonRxProduct()
	{
		final I_M_Product mProduct = createMProduct("NonPrescription(Non-RX)Product", false, false, "NonPrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeA, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanReceiveRxProduct()
	{
		final I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeA, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanNotReceiveRxNarcoticProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeA, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(PharmaSalesOrderLineInputValidator.MSG_NoNarcoticPermission);
		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeBbPartnerCanNotReceiveRxNarcoticProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeB, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(PharmaSalesOrderLineInputValidator.MSG_NoNarcoticPermission);
		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeCbPartnerCanReceiveRxNarcoticProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Narcotic Product Customer", true, I_C_BPartner.ShipmentPermissionPharma_TypeC, false, null);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(true);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validateTheProducts(cOrderLine);
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
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeBbPartnerCanNotSellRxProduct()
	{
		final I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeB);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(PharmaPurchaseOrderLineInputValidator.MSG_NoPrescriptionPermission);
		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanSellNonRxProduct()
	{
		final I_M_Product mProduct = createMProduct("NonPrescription(Non-RX)Product", false, false, "NonPrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeA);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanSellRxProduct()
	{
		final I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeA);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanNotReceiveRxSellProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Prescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeA);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(MSG_NoNarcoticPermission);
		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeBbPartnerCanNotSellRxNarcoticProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeB);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		thrown.expectMessage(MSG_NoNarcoticPermission);
		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	@Test
	public void assertTypeCbPartnerCanReceiveRxSellProduct()
	{
		final I_M_Product mProduct = createMProduct("Narcotic Product", true, true, "Narcotic Product");
		final I_M_Warehouse mWarehouse = createMWarehouse();
		final I_C_BPartner cbPartner = createCBpartner("Narcotic Product Vendor", false, null, true, I_C_BPartner.ReceiptPermissionPharma_TypeC);
		final I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		final I_C_Order cOrder = createCOrder(false);
		final I_C_Currency cCurrency = createCCurrency();
		final I_C_UOM cUom = createCUom();
		final I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validateTheProducts(cOrderLine);
	}

	/**
	 * All the create* methods should belong to a god-object TestHelperFactory of some kind.
	 * It's really bad to keep creating these for all tests in all the different projects.
	 */

	private I_C_OrderLine createCOrderLine(final I_M_Product mProduct, final I_M_Warehouse mWarehouse, final I_C_BPartner cbPartner, final I_C_PaymentTerm cPaymentTerm, final I_C_Order cOrder, final I_C_Currency cCurrency, final I_C_UOM cUom, final BigDecimal priceActual, final BigDecimal qtyEntered)
	{
		final I_C_OrderLine cOrderLine = newInstance(I_C_OrderLine.class);
		cOrderLine.setM_Product(mProduct);
		cOrderLine.setM_Warehouse(mWarehouse);
		cOrderLine.setC_BPartner(cbPartner);
		cOrderLine.setC_PaymentTerm_Override(cPaymentTerm);
		cOrderLine.setDatePromised(Timestamp.valueOf(LocalDateTime.now().minusHours(1)));
		cOrderLine.setC_Order(cOrder);
		cOrderLine.setC_Currency(cCurrency);
		cOrderLine.setPriceActual(priceActual);
		cOrderLine.setQtyEntered(qtyEntered);
		cOrderLine.setC_UOM(cUom);
		save(cOrderLine);
		return cOrderLine;
	}

	private I_C_UOM createCUom()
	{
		final I_C_UOM cUom = newInstance(I_C_UOM.class);
		save(cUom);
		return cUom;
	}

	private I_C_Currency createCCurrency()
	{
		final I_C_Currency cCurrency = newInstance(I_C_Currency.class);
		cCurrency.setISO_Code("EUR");
		cCurrency.setStdPrecision(2);
		save(cCurrency);
		return cCurrency;
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
