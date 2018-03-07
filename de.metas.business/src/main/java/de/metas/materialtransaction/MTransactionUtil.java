package de.metas.materialtransaction;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Transaction;

import lombok.NonNull;

public class MTransactionUtil
{

	public static boolean isInboundTransaction(@NonNull final I_M_Transaction mtrx)
	{
		try
		{
			return isInboundMovementType(mtrx.getMovementType());
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage().setParameter("transaction", mtrx);
		}
	}

	public static boolean isInboundMovementType(@NonNull final String transactionMovementType)
	{

		if (transactionMovementType == null || transactionMovementType.length() != 2)
		{
			throw new AdempiereException("Invalid movement type '" + transactionMovementType + "'");
		}

		final boolean isInbound;
		final char sign = transactionMovementType.charAt(1);
		switch (sign)
		{
			case '+':
				isInbound = true;
				break;
			case '-':
				isInbound = false;
				break;
			default:
				throw new AdempiereException("Invalid movement type '" + transactionMovementType + "'");
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
}
