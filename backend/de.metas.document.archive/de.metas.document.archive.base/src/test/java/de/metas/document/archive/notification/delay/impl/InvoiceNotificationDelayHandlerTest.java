package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.I_M_ShipperTransportation;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD tests for {@link InvoiceNotificationDelayHandler}.
 *
 * <p>Verifies that the handler bases its readiness check on the shipper gateway + shipping
 * packages + tracking URLs rather than Carrier_Advising_Status.</p>
 */
public class InvoiceNotificationDelayHandlerTest
{
	private InvoiceNotificationDelayHandler handler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handler = new InvoiceNotificationDelayHandler(new CarrierTrackingDelayRepository());
	}

	private void setSysConfig(final boolean enabled)
	{
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(InvoiceNotificationDelayHandler.SYSCONFIG_DelayUntilCarrierConfirmed);
		sysConfig.setValue(enabled ? "Y" : "N");
		sysConfig.setConfigurationLevel("S");
		save(sysConfig);
	}

	/**
	 * Creates a shipper optionally with a gateway string.
	 *
	 * @param gatewayValue non-null string sets a gateway; null leaves it unset (no gateway)
	 */
	private I_M_Shipper createShipper(final String gatewayValue)
	{
		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
		if (gatewayValue != null)
		{
			shipper.setShipperGateway(gatewayValue);
		}
		save(shipper);
		return shipper;
	}

	/**
	 * Build the core invoice graph: shipper → inout → inoutLine → invoice → invoiceLine → log.
	 */
	private GraphResult buildInvoiceGraph(final I_M_Shipper shipper)
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		if (shipper != null)
		{
			inOut.setM_Shipper_ID(shipper.getM_Shipper_ID());
		}
		save(inOut);

		final I_M_InOutLine inOutLine = newInstance(I_M_InOutLine.class);
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		save(inOutLine);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		save(invoice);

		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceLine.setM_InOutLine_ID(inOutLine.getM_InOutLine_ID());
		save(invoiceLine);

		final I_C_Doc_Outbound_Log log = newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Invoice.class));
		log.setRecord_ID(invoice.getC_Invoice_ID());
		save(log);

		return new GraphResult(inOut.getM_InOut_ID(), log);
	}

	/**
	 * Add a shipping package + shipment order + parcel to an inout.
	 *
	 * @param trackingUrl null/blank → parcel has no tracking URL; non-blank → parcel has a URL
	 */
	private void addPackageWithParcel(final int inOutId, final String trackingUrl)
	{
		final I_M_ShipperTransportation transportation = newInstance(I_M_ShipperTransportation.class);
		save(transportation);

		final I_M_ShippingPackage pkg = newInstance(I_M_ShippingPackage.class);
		pkg.setM_InOut_ID(inOutId);
		pkg.setM_ShipperTransportation_ID(transportation.getM_ShipperTransportation_ID());
		save(pkg);

		final I_Carrier_ShipmentOrder order = newInstance(I_Carrier_ShipmentOrder.class);
		order.setM_ShipperTransportation_ID(transportation.getM_ShipperTransportation_ID());
		save(order);

		final I_Carrier_ShipmentOrder_Parcel parcel = newInstance(I_Carrier_ShipmentOrder_Parcel.class);
		parcel.setCarrier_ShipmentOrder_ID(order.getCarrier_ShipmentOrder_ID());
		if (trackingUrl != null)
		{
			parcel.setTrackingURL(trackingUrl);
		}
		save(parcel);
	}

	// ------------------------------------------------------------------
	// Tests
	// ------------------------------------------------------------------

	@Test
	public void sysConfigOff_neverDelays()
	{
		setSysConfig(false);
		// gateway shipper + no packages would otherwise delay, but sysconfig is off → no delay
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInvoiceGraph(shipper);
		assertThat(handler.shouldDelaySending(g.log)).isFalse();
	}

	@Test
	public void gatewayShipper_noPackages_delays()
	{
		setSysConfig(true);
		// gateway configured but no M_ShippingPackage yet → carrier not invoked → delay
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInvoiceGraph(shipper);
		assertThat(handler.shouldDelaySending(g.log)).isTrue();
	}

	@Test
	public void gatewayShipper_packageWithoutTrackingUrl_delays()
	{
		setSysConfig(true);
		// package + order + parcel present but parcel has no tracking URL → delay
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInvoiceGraph(shipper);
		addPackageWithParcel(g.inOutId, null);
		assertThat(handler.shouldDelaySending(g.log)).isTrue();
	}

	@Test
	public void gatewayShipper_packageWithTrackingUrl_doesNotDelay()
	{
		setSysConfig(true);
		// package + order + parcel all present and parcel has a tracking URL → no delay
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInvoiceGraph(shipper);
		addPackageWithParcel(g.inOutId, "https://track/x");
		assertThat(handler.shouldDelaySending(g.log)).isFalse();
	}

	@Test
	public void noGatewayShipper_doesNotDelay()
	{
		setSysConfig(true);
		// shipper exists but has no gateway configured → not carrier-relevant → no delay
		final I_M_Shipper shipper = createShipper(null);
		final GraphResult g = buildInvoiceGraph(shipper);
		assertThat(handler.shouldDelaySending(g.log)).isFalse();
	}

	@Test
	public void noShipperOnInOut_doesNotDelay()
	{
		setSysConfig(true);
		// inout has no shipper (M_Shipper_ID = 0) → skip → no delay
		final GraphResult g = buildInvoiceGraph(null);
		assertThat(handler.shouldDelaySending(g.log)).isFalse();
	}

	@Test
	public void sysConfigOn_invoiceWithoutInOutLines_doesNotDelay()
	{
		setSysConfig(true);
		// Invoice whose C_InvoiceLine has M_InOutLine_ID = 0 (no shipment link) → returns false
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		save(invoice);

		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		// M_InOutLine_ID stays 0 (default) — no shipment link
		save(invoiceLine);

		final I_C_Doc_Outbound_Log log = newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Invoice.class));
		log.setRecord_ID(invoice.getC_Invoice_ID());
		save(log);

		assertThat(handler.shouldDelaySending(log)).isFalse();
	}

	@Test
	public void gatewayShipper_secondPackageMissingTrackingUrl_delays()
	{
		setSysConfig(true);
		// Two packages: first has a tracking URL (resolved), second has a blank tracking URL → delay.
		// This proves the loop continues past the first good package rather than short-circuiting on it.
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInvoiceGraph(shipper);
		addPackageWithParcel(g.inOutId, "https://track/good");   // first package: fully resolved
		addPackageWithParcel(g.inOutId, null);                    // second package: no tracking URL yet
		assertThat(handler.shouldDelaySending(g.log)).isTrue();
	}

	@Test
	public void gatewayAndNonGatewayShipments_delaysForTheGatewayOne()
	{
		setSysConfig(true);
		// One invoice with TWO invoice lines, each linked to a DIFFERENT inout:
		//   Shipment B (non-gateway): shipper has no ShipperGateway → must be skipped via continue.
		//   Shipment A (gateway):     shipper has gateway "nshift", package present but no tracking URL → pending.
		// Expected: shouldDelaySending returns true because shipment A forces a delay,
		// and the no-gateway shipment B is correctly skipped (not causing an early return or error).

		// Build the base invoice graph with the gateway shipper (shipment A).
		final I_M_Shipper gatewayShipper = createShipper("nshift");
		final GraphResult g = buildInvoiceGraph(gatewayShipper);

		// Add shipment B (no gateway) as a second inout linked to the same invoice.
		final I_M_Shipper nonGatewayShipper = createShipper(null);
		addShipmentLineToInvoice(g.log.getRecord_ID(), nonGatewayShipper);

		// Shipment A has a package but no tracking URL yet → still pending.
		addPackageWithParcel(g.inOutId, null);

		assertThat(handler.shouldDelaySending(g.log)).isTrue();
	}

	/**
	 * Attach a second shipment (inout + inoutLine + invoiceLine) to an existing invoice,
	 * allowing a single invoice to span multiple shipments with different shippers.
	 *
	 * @param invoiceId  the {@code C_Invoice_ID} of the already-saved invoice
	 * @param shipper    the shipper to assign to the new inout (null = no shipper)
	 * @return the new {@code M_InOut_ID}
	 */
	private int addShipmentLineToInvoice(final int invoiceId, final I_M_Shipper shipper)
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		if (shipper != null)
		{
			inOut.setM_Shipper_ID(shipper.getM_Shipper_ID());
		}
		save(inOut);

		final I_M_InOutLine inOutLine = newInstance(I_M_InOutLine.class);
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		save(inOutLine);

		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(invoiceId);
		invoiceLine.setM_InOutLine_ID(inOutLine.getM_InOutLine_ID());
		save(invoiceLine);

		return inOut.getM_InOut_ID();
	}

	// ------------------------------------------------------------------
	// Helper DTO
	// ------------------------------------------------------------------

	private static class GraphResult
	{
		final int inOutId;
		final I_C_Doc_Outbound_Log log;

		GraphResult(final int inOutId, final I_C_Doc_Outbound_Log log)
		{
			this.inOutId = inOutId;
			this.log = log;
		}
	}
}
