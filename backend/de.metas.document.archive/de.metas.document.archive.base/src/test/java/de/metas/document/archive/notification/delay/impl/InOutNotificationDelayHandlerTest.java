package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InOutNotificationDelayHandler}.
 *
 * <p>The handler holds the shipment notification while the shipment's {@code M_InOut.TrackingURL}
 * virtual column is still blank — but only for customer shipments ({@code IsSOTrx='Y'}) whose
 * shipper has a configured gateway. Receipts, non-gateway shipments, and a present tracking URL all
 * release immediately. Here the virtual column is exercised directly via {@code setTrackingURL}
 * (POJO test layer; production computes it from the carrier parcels via the lazy {@code ColumnSQL}).</p>
 */
public class InOutNotificationDelayHandlerTest
{
	private InOutNotificationDelayHandler handler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handler = new InOutNotificationDelayHandler();
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
	 * @param gatewayValue non-null sets a configured gateway; null leaves the shipper without one
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
	 * Build a shipment ({@code M_InOut}) and the doc-outbound log carrying its notification.
	 *
	 * @param shipper     shipper to assign (null = no shipper)
	 * @param soTrx       {@code true} = customer shipment; {@code false} = vendor receipt
	 * @param trackingURL value of the {@code M_InOut.TrackingURL} virtual column (null = blank = carrier not yet confirmed)
	 */
	private I_C_Doc_Outbound_Log buildLog(final I_M_Shipper shipper, final boolean soTrx, final String trackingURL)
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setIsSOTrx(soTrx);
		if (shipper != null)
		{
			inOut.setM_Shipper_ID(shipper.getM_Shipper_ID());
		}
		if (trackingURL != null)
		{
			inOut.setTrackingURL(trackingURL);
		}
		save(inOut);

		final I_C_Doc_Outbound_Log log = newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_M_InOut.class));
		log.setRecord_ID(inOut.getM_InOut_ID());
		save(log);
		return log;
	}

	@Test
	public void sysConfigOff_neverDelays()
	{
		setSysConfig(false);
		// gateway shipper + blank tracking URL would otherwise delay, but the SysConfig is off
		assertThat(handler.shouldDelaySending(buildLog(createShipper("nshift"), true, null))).isFalse();
	}

	@Test
	public void gatewayShipper_noTrackingUrl_delays()
	{
		setSysConfig(true);
		// carrier-tracked customer shipment, tracking link not yet available → delay
		assertThat(handler.shouldDelaySending(buildLog(createShipper("nshift"), true, null))).isTrue();
	}

	@Test
	public void gatewayShipper_withTrackingUrl_doesNotDelay()
	{
		setSysConfig(true);
		// tracking link present (the value the email will render) → release
		assertThat(handler.shouldDelaySending(buildLog(createShipper("nshift"), true, "https://track/x"))).isFalse();
	}

	@Test
	public void noGatewayShipper_doesNotDelay()
	{
		setSysConfig(true);
		// shipper has no gateway → no tracking link ever expected → never held
		assertThat(handler.shouldDelaySending(buildLog(createShipper(null), true, null))).isFalse();
	}

	@Test
	public void noShipperOnInOut_doesNotDelay()
	{
		setSysConfig(true);
		// shipment has no shipper → not carrier-relevant → never held
		assertThat(handler.shouldDelaySending(buildLog(null, true, null))).isFalse();
	}

	@Test
	public void receiptInOut_doesNotDelay()
	{
		setSysConfig(true);
		// vendor receipt (IsSOTrx='N') with a gateway shipper + blank tracking URL would otherwise
		// delay, but receipts carry no customer tracking-link notification → never held
		assertThat(handler.shouldDelaySending(buildLog(createShipper("nshift"), false, null))).isFalse();
	}
}
