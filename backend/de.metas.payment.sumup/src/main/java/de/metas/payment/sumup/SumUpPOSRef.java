package de.metas.payment.sumup;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class SumUpPOSRef
{
	/**
	 * Table of the POS invoice card settlement; a string because this module cannot see the POS model.
	 */
	private static final String TABLE_NAME_POSInvoiceCardSettlement = "C_POS_InvoiceCardSettlement";

	@Builder.Default int posOrderId = -1;
	@Builder.Default int posPaymentId = -1;
	@Builder.Default int posInvoiceCardSettlementId = -1;

	/**
	 * @return the settlement this reference points to (used to tag the AD_Issue of a failed call), or null for a card sale
	 */
	@Nullable
	public TableRecordReference toPOSInvoiceCardSettlementRecordRefOrNull()
	{
		return posInvoiceCardSettlementId > 0
				? TableRecordReference.of(TABLE_NAME_POSInvoiceCardSettlement, posInvoiceCardSettlementId)
				: null;
	}
}
