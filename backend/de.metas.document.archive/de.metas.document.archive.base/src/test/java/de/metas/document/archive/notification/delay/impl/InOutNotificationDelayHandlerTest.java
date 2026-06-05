package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.I_M_ShipperTransportation;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD tests for {@link InOutNotificationDelayHandler}.
 *
 * <p>Verifies that the handler bases its readiness check on {@code M_InOut.IsSOTrx} plus the
 * shipper gateway + shipping packages + tracking URLs of the shipment ({@code M_InOut}) carried
 * by the doc-outbound log.</p>
 */
public class InOutNotificationDelayHandlerTest
{
	private InOutNotificationDelayHandler handler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handler = new InOutNotificationDelayHandler(new CarrierTrackingDelayRepository());
	}

	private void setSysConfig(final boolean enabled)
	{
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(InOutNotificationDelayHandler.SYSCONFIG_DelayUntilCarrierConfirmed);
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
	 * Build a customer shipment ({@code M_InOut}, {@code IsSOTrx='Y'}) with the given shipper plus
	 * the doc-outbound log that carries its notification — the log's {@code Record_ID} is the
	 * {@code M_InOut_ID}.
	 *
	 * @param shipper the shipper to assign (null = no shipper on the shipment)
	 */
	private GraphResult buildInOutGraph(final I_M_Shipper shipper)
	{
		return buildInOutGraph(shipper, true);
	}

	/**
	 * Build an {@code M_InOut} with the given shipper and sales/purchase flag plus its doc-outbound log.
	 *
	 * @param shipper the shipper to assign (null = no shipper on the shipment)
	 * @param soTrx   {@code true} → customer shipment ({@code IsSOTrx='Y'}); {@code false} → vendor receipt
	 */
	private GraphResult buildInOutGraph(final I_M_Shipper shipper, final boolean soTrx)
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setIsSOTrx(soTrx);
		if (shipper != null)
		{
			inOut.setM_Shipper_ID(shipper.getM_Shipper_ID());
		}
		save(inOut);

		final I_C_Doc_Outbound_Log log = newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_M_InOut.class));
		log.setRecord_ID(inOut.getM_InOut_ID());
		save(log);

		return new GraphResult(inOut.getM_InOut_ID(), log);
	}

	/**
	 * Add a shipping package + shipment order + parcel to a shipment.
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
		final GraphResult g = buildInOutGraph(shipper);
		assertThat(handler.shouldDelaySending(g.log)).isFalse();
	}

	@Test
	public void gatewayShipper_noPackages_delays()
	{
		setSysConfig(true);
		// gateway configured but no M_ShippingPackage yet → carrier not invoked → delay
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInOutGraph(shipper);
		assertThat(handler.shouldDelaySending(g.log)).isTrue();
	}

	@Test
	public void gatewayShipper_packageWithoutTrackingUrl_delays()
	{
		setSysConfig(true);
		// package + order + parcel present but parcel has no tracking URL → delay
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInOutGraph(shipper);
		addPackageWithParcel(g.inOutId, null);
		assertThat(handler.shouldDelaySending(g.log)).isTrue();
	}

	@Test
	public void gatewayShipper_packageWithTrackingUrl_doesNotDelay()
	{
		setSysConfig(true);
		// package + order + parcel all present and parcel has a tracking URL → no delay
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInOutGraph(shipper);
		addPackageWithParcel(g.inOutId, "https://track/x");
		assertThat(handler.shouldDelaySending(g.log)).isFalse();
	}

	@Test
	public void noGatewayShipper_doesNotDelay()
	{
		setSysConfig(true);
		// shipper exists but has no gateway configured → not carrier-relevant → no delay
		final I_M_Shipper shipper = createShipper(null);
		final GraphResult g = buildInOutGraph(shipper);
		assertThat(handler.shouldDelaySending(g.log)).isFalse();
	}

	@Test
	public void noShipperOnInOut_doesNotDelay()
	{
		setSysConfig(true);
		// shipment has no shipper (M_Shipper_ID = 0) → not carrier-relevant → no delay
		final GraphResult g = buildInOutGraph(null);
		assertThat(handler.shouldDelaySending(g.log)).isFalse();
	}

	@Test
	public void receiptInOut_doesNotDelay()
	{
		setSysConfig(true);
		// Vendor receipt (IsSOTrx='N') with a gateway shipper + no packages would otherwise delay,
		// but a receipt carries no customer tracking-link notification → no delay.
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInOutGraph(shipper, false);
		assertThat(handler.shouldDelaySending(g.log)).isFalse();
	}

	@Test
	public void gatewayShipper_secondPackageMissingTrackingUrl_delays()
	{
		setSysConfig(true);
		// Two packages on one shipment: first has a tracking URL (resolved), second has a blank
		// tracking URL → delay. Proves the loop continues past the first good package.
		final I_M_Shipper shipper = createShipper("nshift");
		final GraphResult g = buildInOutGraph(shipper);
		addPackageWithParcel(g.inOutId, "https://track/good");   // first package: fully resolved
		addPackageWithParcel(g.inOutId, null);                    // second package: no tracking URL yet
		assertThat(handler.shouldDelaySending(g.log)).isTrue();
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
