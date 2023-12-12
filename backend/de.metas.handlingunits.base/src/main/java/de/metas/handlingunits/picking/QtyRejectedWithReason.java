package de.metas.handlingunits.picking;

import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class QtyRejectedWithReason
{
	@Getter(AccessLevel.NONE)
	@NonNull Quantity qty;

	@NonNull QtyRejectedReasonCode reasonCode;

	private QtyRejectedWithReason(@NonNull Quantity qty, @NonNull QtyRejectedReasonCode reasonCode)
	{
		if (qty.signum() <= 0)
		{
			throw new AdempiereException("Rejected Qty shall be greater than zero but it was " + qty);
		}

		this.qty = qty;
		this.reasonCode = reasonCode;
	}

	public Quantity toQuantity() {return qty;}

	public BigDecimal toBigDecimal() {return qty.toBigDecimal();}

	public @NonNull QtyRejectedReasonCode getReasonCode()
	{return this.reasonCode;}
}
