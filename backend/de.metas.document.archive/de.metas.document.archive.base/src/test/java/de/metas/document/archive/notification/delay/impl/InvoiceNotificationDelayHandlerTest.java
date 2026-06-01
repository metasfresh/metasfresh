package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD tests for {@link InvoiceNotificationDelayHandler}.
 *
 * <p>Verifies that the handler bases its readiness check on
 * {@code M_ShipmentSchedule.Carrier_Advising_Status} rather than carrier-parcel existence.</p>
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
	 *
	 * @param shipperId          the M_Shipper_ID to set on the schedule (0 = no shipper)
	 * @param advisingStatusCode the Carrier_Advising_Status code to set on the schedule (e.g. "NR", "R", "IP", "CO")
	 */
	private I_C_Doc_Outbound_Log scenario(final int shipperId, final String advisingStatusCode)
	{
		// M_InOut + M_InOutLine
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		save(inOut);

		final I_M_InOutLine inOutLine = newInstance(I_M_InOutLine.class);
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		save(inOutLine);

		// C_Invoice + C_InvoiceLine linking to the inout line
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		save(invoice);

		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceLine.setM_InOutLine_ID(inOutLine.getM_InOutLine_ID());
		save(invoiceLine);

		// M_ShipmentSchedule with configurable shipper + advising status
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Shipper_ID(shipperId);
		schedule.setCarrier_Advising_Status(advisingStatusCode);
		save(schedule);

		// M_ShipmentSchedule_QtyPicked linking the inout line to the schedule
		final I_M_ShipmentSchedule_QtyPicked qtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		qtyPicked.setM_InOutLine_ID(inOutLine.getM_InOutLine_ID());
		qtyPicked.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		save(qtyPicked);

		// C_Doc_Outbound_Log pointing at the invoice
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
		// even with a pending status, sysconfg off → no delay
		assertThat(handler.shouldDelaySending(scenario(10, "R"))).isFalse();
	}

	@Test
	public void sysConfigOn_requested_delays()
	{
		setSysConfig(true);
		// status R (Requested) with a shipper → delay
		assertThat(handler.shouldDelaySending(scenario(10, "R"))).isTrue();
	}

	@Test
	public void sysConfigOn_inProgress_delays()
	{
		setSysConfig(true);
		// status IP (InProgress) with a shipper → delay
		assertThat(handler.shouldDelaySending(scenario(10, "IP"))).isTrue();
	}

	@Test
	public void sysConfigOn_notRequestedWithShipper_delays()
	{
		setSysConfig(true);
		// status NR (NotRequested) but a shipper is assigned → delay (not yet sent to carrier)
		assertThat(handler.shouldDelaySending(scenario(10, "NR"))).isTrue();
	}

	@Test
	public void sysConfigOn_completed_doesNotDelay()
	{
		setSysConfig(true);
		// status CO (Completed) → carrier confirmed, no delay
		assertThat(handler.shouldDelaySending(scenario(10, "CO"))).isFalse();
	}

	@Test
	public void sysConfigOn_noShipper_doesNotDelay()
	{
		setSysConfig(true);
		// no shipper (M_Shipper_ID = 0) → not carrier-relevant, no delay
		assertThat(handler.shouldDelaySending(scenario(0, "NR"))).isFalse();
	}
}
