package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import de.metas.pos.repository.model.I_C_POS_Payment;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;

import java.util.List;
import java.util.Properties;

/**
 * The card payments for which metasfresh stores no SumUp transaction with the payment's SumUp client transaction id. In SQL a
 * correlated {@code NOT EXISTS}, which PostgreSQL runs as an anti join probing the unique index on
 * {@code SUMUP_Transaction.SUMUP_ClientTransactionId} ({@code NOT IN (subquery)} would read the whole table in every sweep).
 * In Java (the in-memory database of unit tests) the same condition, by a query.
 */
@EqualsAndHashCode
@ToString
final class NoStoredSumUpTransactionFilter implements IQueryFilter<I_C_POS_Payment>, ISqlQueryFilter
{
	@EqualsAndHashCode.Exclude @ToString.Exclude @NonNull private final IQueryBL queryBL;

	NoStoredSumUpTransactionFilter(@NonNull final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
	}

	@Override
	public boolean accept(final I_C_POS_Payment payment)
	{
		final String clientTransactionId = payment.getSUMUP_ClientTransactionID();
		return clientTransactionId == null // as in SQL: NULL matches no row
				|| !queryBL.createQueryBuilder(I_SUMUP_Transaction.class)
				.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_SUMUP_ClientTransactionId, clientTransactionId)
				.create()
				.anyMatch();
	}

	@Override
	public String getSql()
	{
		return "NOT EXISTS (SELECT 1 FROM " + I_SUMUP_Transaction.Table_Name + " t"
				+ " WHERE t." + I_SUMUP_Transaction.COLUMNNAME_SUMUP_ClientTransactionId
				+ " = " + I_C_POS_Payment.Table_Name + "." + I_C_POS_Payment.COLUMNNAME_SUMUP_ClientTransactionID + ")";
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return ImmutableList.of();
	}
}
