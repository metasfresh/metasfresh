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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		//		 needed to register the spring context with the Adempiere main class
		StartupListener.class, ShutdownListener.class,

		// needed so that the spring context can discover those components. Note that there are other ways too, but this one is very fast
		PharmaBPartnerRepository.class,
		PharmaOrderLineInputValidator.class,
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

	@Test
	public void assertTypeBbPartnerCanReceiveNonRxProduct()
	{
		I_M_Product mProduct = createMProduct("NonPrescription(Non-RX)Product", false, false, "NonPrescriptionProduct");
		I_M_Warehouse mWarehouse = createMWarehouse();
		I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Customer", I_C_BPartner.ShipmentPermissionPharma_TypeB, true);
		I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		I_C_Order cOrder = createCOrder(true);
		I_C_Currency cCurrency = createCCurrency();
		I_C_UOM cUom = createCUom();
		I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatePrescriptionProduct(cOrderLine);
	}

	@Test
	public void assertTypeBbPartnerCanNotReceiveRxProduct()
	{
		I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		I_M_Warehouse mWarehouse = createMWarehouse();
		I_C_BPartner cbPartner = createCBpartner("NonPrescription Product Customer", I_C_BPartner.ShipmentPermissionPharma_TypeB, true);
		I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		I_C_Order cOrder = createCOrder(true);
		I_C_Currency cCurrency = createCCurrency();
		I_C_UOM cUom = createCUom();
		I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		thrown.expect(AdempiereException.class);
		// i' not sure if it's all right to check the expected message (below)
		// because the string is an ITranslatableString, so the message may be changed depending on language settings.
		thrown.expectMessage("NoPrescriptionPermission");
		orderLineInterceptor.validatePrescriptionProduct(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanReceiveNonRxProduct()
	{
		I_M_Product mProduct = createMProduct("NonPrescription(Non-RX)Product", false, false, "NonPrescriptionProduct");
		I_M_Warehouse mWarehouse = createMWarehouse();
		I_C_BPartner cbPartner = createCBpartner("Prescription Product Customer", I_C_BPartner.ShipmentPermissionPharma_TypeA, true);
		I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		I_C_Order cOrder = createCOrder(true);
		I_C_Currency cCurrency = createCCurrency();
		I_C_UOM cUom = createCUom();
		I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatePrescriptionProduct(cOrderLine);
	}

	@Test
	public void assertTypeAbPartnerCanReceiveRxProduct()
	{
		I_M_Product mProduct = createMProduct("Prescription(RX)Product", true, false, "PrescriptionProduct");
		I_M_Warehouse mWarehouse = createMWarehouse();
		I_C_BPartner cbPartner = createCBpartner("Prescription Product Customer", I_C_BPartner.ShipmentPermissionPharma_TypeA, true);
		I_C_PaymentTerm cPaymentTerm = createCPaymentTerm();
		I_C_Order cOrder = createCOrder(true);
		I_C_Currency cCurrency = createCCurrency();
		I_C_UOM cUom = createCUom();
		I_C_OrderLine cOrderLine = createCOrderLine(mProduct, mWarehouse, cbPartner, cPaymentTerm, cOrder, cCurrency, cUom, BigDecimal.ONE, BigDecimal.ONE);

		orderLineInterceptor.validatePrescriptionProduct(cOrderLine);
	}

	/**
	 * All the create* methods should belong to a god-object TestHelperFactory of some kind.
	 * It's really bad to keep creating these for all tests in all the different projects.
	 */

	private I_C_OrderLine createCOrderLine(I_M_Product mProduct, I_M_Warehouse mWarehouse, I_C_BPartner cbPartner, I_C_PaymentTerm cPaymentTerm, I_C_Order cOrder, I_C_Currency cCurrency, I_C_UOM cUom, BigDecimal priceActual, BigDecimal qtyEntered)
	{
		I_C_OrderLine cOrderLine = newInstance(I_C_OrderLine.class);
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
		I_C_UOM cUom = newInstance(I_C_UOM.class);
		save(cUom);
		return cUom;
	}

	private I_C_Currency createCCurrency()
	{
		I_C_Currency cCurrency = newInstance(I_C_Currency.class);
		cCurrency.setISO_Code("EUR");
		cCurrency.setStdPrecision(2);
		save(cCurrency);
		return cCurrency;
	}

	private I_C_Order createCOrder(boolean isSOTrx)
	{
		I_C_Order cOrder = newInstance(I_C_Order.class);
		cOrder.setIsSOTrx(isSOTrx);
		save(cOrder);
		return cOrder;
	}

	private I_C_PaymentTerm createCPaymentTerm()
	{
		I_C_PaymentTerm cPaymentTerm = newInstance(I_C_PaymentTerm.class);
		save(cPaymentTerm);
		return cPaymentTerm;
	}

	private I_C_BPartner createCBpartner(String name, String shipmentPermissionPharma, boolean isCustomer)
	{
		// since this is a sales order, this should be the seller, right?
		I_C_BPartner cbPartner = newInstance(I_C_BPartner.class);
		cbPartner.setName(name);
		cbPartner.setShipmentPermissionPharma(shipmentPermissionPharma);
		if (shipmentPermissionPharma.equals(I_C_BPartner.ShipmentPermissionPharma_TypeA) || shipmentPermissionPharma.equals(I_C_BPartner.ShipmentPermissionPharma_TypeC))
		{
			// need to have 1 pharmacie permission.
			// this should be done automatically by the interceptor
			// de.metas.vertical.pharma.model.interceptor.C_BPartner.onPharmaPermissionChanged_Customer
			// but i can't figure out how to add it right now to the test.
			cbPartner.setIsPharmaciePermission(true);
		}
		cbPartner.setIsCustomer(isCustomer);
		save(cbPartner);
		return cbPartner;
	}

	private I_M_Warehouse createMWarehouse()
	{
		I_M_Warehouse mWarehouse = newInstance(I_M_Warehouse.class);
		save(mWarehouse);
		return mWarehouse;
	}

	private I_M_Product createMProduct(String name, boolean isPrescription, boolean isNarcotic, String vitaminC)
	{
		I_M_Product mProduct = newInstance(I_M_Product.class);
		mProduct.setName(name);
		mProduct.setIsPrescription(isPrescription);
		mProduct.setIsNarcotic(isNarcotic);
		mProduct.setValue(vitaminC);
		save(mProduct);
		return mProduct;
	}
}
