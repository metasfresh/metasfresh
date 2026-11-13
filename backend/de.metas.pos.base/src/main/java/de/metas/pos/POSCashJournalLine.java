package de.metas.pos;

import de.metas.money.Money;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class POSCashJournalLine
{
	@NonNull POSCashJournalLineType type;
	@NonNull Money amount;
	@NonNull UserId cashierId;
	@Nullable String description;

	@Nullable POSOrderAndPaymentId posOrderAndPaymentId;
	@Nullable POSInvoiceCardSettlementId posInvoiceCardSettlementId;

	@Builder
	private POSCashJournalLine(
			@NonNull final POSCashJournalLineType type,
			@NonNull final Money amount,
			@NonNull final UserId cashierId,
			@Nullable final String description,
			@Nullable final POSOrderAndPaymentId posOrderAndPaymentId,
			@Nullable final POSInvoiceCardSettlementId posInvoiceCardSettlementId)
	{
		if (amount.isZero())
		{
			throw new AdempiereException("amount cannot be zero");
		}
		if (type.isPayment() && (posOrderAndPaymentId == null) == (posInvoiceCardSettlementId == null))
		{
			throw new IllegalArgumentException("Exactly one of posOrderAndPaymentId / posInvoiceCardSettlementId must be set when type is " + type);
		}

		this.type = type;
		this.amount = amount;
		this.cashierId = cashierId;
		this.description = description;
		this.posOrderAndPaymentId = posOrderAndPaymentId;
		this.posInvoiceCardSettlementId = posInvoiceCardSettlementId;
	}

	public boolean isCash() {return type.isCash();}
}
