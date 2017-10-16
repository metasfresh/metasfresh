package de.metas.materialtransaction;

import java.math.BigDecimal;

import org.compiere.model.I_M_Transaction;

import lombok.NonNull;

public class MTransactionUtil
{

	public static boolean isInboundTransaction(@NonNull final I_M_Transaction mtrx)
	{
		final String movementType = mtrx.getMovementType();
		if (movementType == null || movementType.length() != 2)
		{
			throw new IllegalArgumentException("Invalid movement type '" + movementType + "' for " + mtrx);
		}

		final boolean isInbound;
		final char sign = movementType.charAt(1);
		switch (sign)
		{
			case '+':
				isInbound = true;
				break;
			case '-':
				isInbound = false;
				break;
			default:
				throw new IllegalArgumentException("Invalid movement type '" + movementType + "' for " + mtrx);
		}

		return isInbound;
	}

	public static boolean isReversal(@NonNull final I_M_Transaction mtrx)
	{
		final BigDecimal movementQty = mtrx.getMovementQty();

		if (isInboundTransaction(mtrx))
		{
			return movementQty.signum() < 0;
		}
		return movementQty.signum() >= 0;
	}

	public static BigDecimal getEffectiveMovementQty(@NonNull final I_M_Transaction transaction)
	{
		if (isInboundTransaction(transaction))
		{
			return transaction.getMovementQty();
		}
		return transaction.getMovementQty().negate();
	}

}
