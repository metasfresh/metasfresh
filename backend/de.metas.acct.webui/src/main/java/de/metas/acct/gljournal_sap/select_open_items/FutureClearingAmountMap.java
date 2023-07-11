package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.currency.Amount;
import de.metas.money.CurrencyCodeToCurrencyIdBiConverter;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;

class FutureClearingAmountMap
{
	private final ImmutableMap<FAOpenItemKey, FutureClearingAmount> byKey;

	private FutureClearingAmountMap(final List<FutureClearingAmount> list)
	{
		this.byKey = list.stream()
				.collect(ImmutableMap.toImmutableMap(
						FutureClearingAmount::getKey,
						item -> item,
						FutureClearingAmount::add
				));
	}

	public static FutureClearingAmountMap of(
			@NonNull final SAPGLJournal glJournal,
			@NonNull final CurrencyCodeToCurrencyIdBiConverter currencyCodeConverter)
	{
		return glJournal.getLines()
				.stream()
				.map(line -> extractFutureClearingAmount(line, currencyCodeConverter))
				.filter(Objects::nonNull)
				.collect(FutureClearingAmountMap.collect());
	}

	public static Collector<FutureClearingAmount, ?, FutureClearingAmountMap> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(FutureClearingAmountMap::new);
	}

	@Nullable
	private static FutureClearingAmount extractFutureClearingAmount(
			@NonNull final SAPGLJournalLine sapGLJournalLine,
			@NonNull final CurrencyCodeToCurrencyIdBiConverter currencyCodeConverter)
	{
		final FAOpenItemTrxInfo openItemTrxInfo = sapGLJournalLine.getOpenItemTrxInfo();
		if (openItemTrxInfo == null)
		{
			return null;
		}
		if (!openItemTrxInfo.isClearing())
		{
			return null;
		}

		final Amount amount = sapGLJournalLine.getAmount()
				.negateIf(sapGLJournalLine.getPostingSign().isCredit())
				.toAmount(currencyCodeConverter::getCurrencyCodeByCurrencyId);

		return FutureClearingAmount.builder()
				.key(openItemTrxInfo.getKey())
				.amount(amount)
				.build();
	}

	public Optional<Amount> getAmount(final FAOpenItemKey key) {return getByKey(key).map(FutureClearingAmount::getAmount);}

	@NonNull
	private Optional<FutureClearingAmount> getByKey(final FAOpenItemKey key) {return Optional.ofNullable(byKey.get(key));}
}
