package de.metas.inoutcandidate.api.impl;

import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Services;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InOutProducerTest extends ReceiptScheduleTestBase
{
	protected InOutProducer inOutProducer;
	protected InOutGenerateResult receiptGenerateResult;

	@Override
	protected void setup()
	{
		receiptGenerateResult = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true

		final boolean complete = true;
		inOutProducer = new InOutProducer(receiptGenerateResult, complete);
	}

	@Nested
	class isNewReceiptRequired
	{
		@Test
		public void identicalReceiptSchedules()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isFalse();
		}

		@Test
		public void differentWarehouses()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void differentWarehouseOverrides()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			previousReceiptSchedule.setM_Warehouse_Override_ID(12);
			receiptSchedule.setM_Warehouse_Override_ID(14);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void differentBPartners()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void differentBPartnerOverrides()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			previousReceiptSchedule.setC_BPartner_Override_ID(111);
			receiptSchedule.setC_BPartner_Override_ID(123);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void differentDateOrdered()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date2, product1_wh1, 10);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void differentProducts()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product2_wh1, 10);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isFalse();
		}

		@Test
		public void differentQtys()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 20);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isFalse();
		}

		@Test
		public void differentDocTypes()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			previousReceiptSchedule.setC_DocType_ID(123);
			receiptSchedule.setC_DocType_ID(100);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void differentBPartnerLocations()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			previousReceiptSchedule.setC_BPartner_Location_ID(12);
			receiptSchedule.setC_BPartner_Location_ID(13);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void differentContacts()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			previousReceiptSchedule.setAD_User_ID(12);
			receiptSchedule.setAD_User_ID(14);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void differentContactOverrides()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			previousReceiptSchedule.setAD_User_ID(12);
			receiptSchedule.setAD_User_ID(14);

			previousReceiptSchedule.setAD_User_Override_ID(11);
			receiptSchedule.setAD_User_Override_ID(10);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);

			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void differentOrgs()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			previousReceiptSchedule.setAD_Org_ID(12);
			receiptSchedule.setAD_Org_ID(13);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}

		@Test
		public void sameOrg()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			previousReceiptSchedule.setAD_Org_ID(10);
			receiptSchedule.setAD_Org_ID(10);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isFalse();
		}

		@Test
		public void differentOrders()
		{
			final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
			previousReceiptSchedule.setC_Order_ID(10);
			receiptSchedule.setC_Order_ID(20);
			final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
			assertThat(isNewRequired).isTrue();
		}
	}
}
