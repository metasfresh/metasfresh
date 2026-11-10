package de.metas.pos.invoice_settlement.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum POSInvoiceCardSettlementStatus implements ReferenceListAwareEnum
{
	PENDING("PE"),
	UNKNOWN("UN"),
	APPROVED("AP"),
	BOOKING_ERROR("BE"),
	SUCCESSFUL("SU"),
	CREDIT_REFUND_PENDING("CR"),
	REFUNDED("RF"),
	FAILED("FA"),
	CANCELLED("CA"),
	ABANDONED("AB"),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<POSInvoiceCardSettlementStatus> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	/**
	 * The JSON form is the 2-letter status code, the same as in the DB.
	 */
	@Override
	@JsonValue
	@NonNull
	public String getCode() {return code;}

	@JsonCreator
	@NonNull
	public static POSInvoiceCardSettlementStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	/**
	 * Statuses that block a new settlement of the same invoice, unless the settlement is flagged abandoned.
	 * Keep in sync with the blocking index predicate ({@code Status IN ('PE','UN','AP','BE') AND IsAbandoned='N'}).
	 */
	public boolean isBlockingCandidate()
	{
		return this == PENDING || this == UNKNOWN || this == APPROVED || this == BOOKING_ERROR;
	}
}
