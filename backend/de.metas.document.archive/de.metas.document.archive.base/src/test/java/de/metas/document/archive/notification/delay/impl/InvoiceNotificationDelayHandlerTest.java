package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.I_M_ShipperTransportation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD tests for {@link InvoiceNotificationDelayHandler}.
 */
public class InvoiceNotificationDelayHandlerTest
{
	private InvoiceNotificationDelayHandler handler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handler = new InvoiceNotificationDelayHandler();
	}

	/**
	 * Build the object graph and return a doc outbound log for the created invoice.
	 * @param parcelTrackingUrl the TrackingURL to set on the carrier parcel; {@code null} means blank/no tracking
	 */
	private I_C_Doc_Outbound_Log scenario(final String parcelTrackingUrl)
	{
		// inout + inout line
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		save(inOut);

		final I_M_InOutLine inOutLine = newInstance(I_M_InOutLine.class);
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		save(inOutLine);

		// invoice + invoice line linking to the inout line
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		save(invoice);

		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceLine.setM_InOutLine_ID(inOutLine.getM_InOutLine_ID());
		save(invoiceLine);

		// shipper transportation
		final I_M_ShipperTransportation shipperTransportation = newInstance(I_M_ShipperTransportation.class);
		save(shipperTransportation);

		// shipping package linking inout to transportation
		final I_M_ShippingPackage shippingPackage = newInstance(I_M_ShippingPackage.class);
		shippingPackage.setM_InOut_ID(inOut.getM_InOut_ID());
		shippingPackage.setM_ShipperTransportation_ID(shipperTransportation.getM_ShipperTransportation_ID());
		save(shippingPackage);

		// carrier shipment order for that transportation
		final I_Carrier_ShipmentOrder shipmentOrder = newInstance(I_Carrier_ShipmentOrder.class);
		shipmentOrder.setM_ShipperTransportation_ID(shipperTransportation.getM_ShipperTransportation_ID());
		save(shipmentOrder);

		// carrier parcel
		final I_Carrier_ShipmentOrder_Parcel parcel = newInstance(I_Carrier_ShipmentOrder_Parcel.class);
		parcel.setCarrier_ShipmentOrder_ID(shipmentOrder.getCarrier_ShipmentOrder_ID());
		if (parcelTrackingUrl != null)
		{
			parcel.setTrackingURL(parcelTrackingUrl);
		}
		save(parcel);

		// outbound log pointing at the invoice
		final I_C_Doc_Outbound_Log log = newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Invoice.class));
		log.setRecord_ID(invoice.getC_Invoice_ID());
		save(log);

		return log;
	}

	private void setSysConfig(final boolean enabled)
	{
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(InvoiceNotificationDelayHandler.SYSCONFIG_DelayUntilCarrierConfirmed);
		sysConfig.setValue(enabled ? "Y" : "N");
		sysConfig.setConfigurationLevel("S");
		save(sysConfig);
	}

	@Test
	public void sysConfigOff_neverDelays()
	{
		setSysConfig(false);
		assertThat(handler.shouldDelaySending(scenario(null))).isFalse();
	}

	@Test
	public void sysConfigOn_parcelMissingTrackingUrl_delays()
	{
		setSysConfig(true);
		assertThat(handler.shouldDelaySending(scenario(null))).isTrue();
	}

	@Test
	public void sysConfigOn_allTrackingUrlsPresent_doesNotDelay()
	{
		setSysConfig(true);
		assertThat(handler.shouldDelaySending(scenario("https://track/abc"))).isFalse();
	}
}
