package de.metas.pos;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Message;

/**
 * The AD_Message rows the card payment refusals are raised with, so that raising them in a test does not depend on a missing row.
 */
final class POSTestMessages
{
	private POSTestMessages() {}

	static void createCardPaymentResultUnknown()
	{
		create(POSPaymentProcessingStatus.MSG_CardPaymentResultUnknown.toAD_Message(), "Card payment result unknown");
	}

	static void createLateApprovalNotBooked()
	{
		create("de.metas.pos.POSPayment.LateApprovalNotBooked", "Late approval not booked");
	}

	private static void create(final String value, final String msgText)
	{
		final I_AD_Message record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_Message.class);
		record.setValue(value);
		record.setMsgText(msgText);
		InterfaceWrapperHelper.saveRecord(record);
	}
}
