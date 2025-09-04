package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.money.CurrencyId;
import de.metas.money.MixedMoney;
import de.metas.money.Money;
import de.metas.quantity.MixedQuantity;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Predicate;

@Builder(toBuilder = true)
public class FactAcctRecords
{
	@NonNull private final ImmutableList<I_Fact_Acct> list;
	@NonNull private final FactAcctToTabularStringConverter tabularStringConverter;

	@Override
	public String toString() {return tabularStringConverter.toTabular(list, 1).toTabularString();}

	public String toSingleRowTableString(final int index) {return tabularStringConverter.toTabular(list.get(index), index + 1).toTabularString();}

	public boolean isEmpty() {return list.isEmpty();}

	public int size() {return list.size();}

	public I_Fact_Acct get(final int index) {return list.get(index);}

	public HashMap<TableRecordReference, FactAcctRecords> indexedByDocumentRef()
	{
		final ImmutableListMultimap<TableRecordReference, I_Fact_Acct> listByDocumentRef = Multimaps.index(list, FactAcctRecords::extractDocumentRef);

		final HashMap<TableRecordReference, FactAcctRecords> result = new HashMap<>();
		listByDocumentRef.asMap().forEach((documentRef, subList) -> result.put(documentRef, subList(subList)));
		return result;
	}

	private static TableRecordReference extractDocumentRef(final I_Fact_Acct record)
	{
		return TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID());
	}

	private FactAcctRecords subList(Collection<I_Fact_Acct> subList) {return toBuilder().list(ImmutableList.copyOf(subList)).build();}

	public FactAcctRecords toEmpty()
	{
		return isEmpty() ? this : toBuilder().list(ImmutableList.of()).build();
	}

	public FactAcctRecords filter(@NonNull final Predicate<I_Fact_Acct> filter)
	{
		final ImmutableList<I_Fact_Acct> subList = list.stream().filter(filter).collect(ImmutableList.toImmutableList());
		if (subList.size() == list.size())
		{
			return this;
		}

		return subList(subList);
	}

	public BigDecimal getAmtAcctDr()
	{
		return list.stream()
				.map(I_Fact_Acct::getAmtAcctDr)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public BigDecimal getAmtAcctCr()
	{
		return list.stream()
				.map(I_Fact_Acct::getAmtAcctCr)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public BigDecimal getAcctBalance()
	{
		return list.stream()
				.map(record -> record.getAmtAcctDr().subtract(record.getAmtAcctCr()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public MixedMoney getAmtSourceDr()
	{
		return list.stream()
				.map(record -> Money.of(record.getAmtSourceDr(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.collect(MixedMoney.collectAndSum());
	}

	public MixedMoney getAmtSourceCr()
	{
		return list.stream()
				.map(record -> Money.of(record.getAmtSourceCr(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.collect(MixedMoney.collectAndSum());
	}

	public MixedMoney getSourceBalance()
	{
		return list.stream()
				.map(record -> Money.of(record.getAmtSourceDr().subtract(record.getAmtSourceCr()), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.collect(MixedMoney.collectAndSum());
	}

	public MixedQuantity getQty()
	{
		return list.stream()
				.map(FactAcctRecords::extractQtyOrNull)
				.filter(Objects::nonNull)
				.collect(MixedQuantity.collectAndSum());
	}

	@Nullable
	private static Quantity extractQtyOrNull(final I_Fact_Acct record)
	{
		final UomId uomId = UomId.ofRepoIdOrNull(record.getC_UOM_ID());
		return uomId != null ? Quantitys.of(record.getQty(), uomId) : null;
	}

}
