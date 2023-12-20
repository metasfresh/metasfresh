package de.metas.forex.process.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.forex.ForexContract;
import de.metas.forex.ForexContractId;
import de.metas.money.CurrencyId;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class ForexContracts
{
	@Getter @NonNull private final CurrencyId orderCurrencyId;
	@NonNull private final ImmutableMap<ForexContractId, ForexContract> forexContractsById;
	@Nullable private final ForexContract singleForexContract;

	@Builder
	private ForexContracts(
			final @NonNull CurrencyId orderCurrencyId,
			final @NonNull ImmutableList<ForexContract> forexContracts)
	{
		this.orderCurrencyId = orderCurrencyId;
		this.forexContractsById = Maps.uniqueIndex(forexContracts, ForexContract::getId);
		this.singleForexContract = forexContracts.size() == 1 ? forexContracts.get(0) : null;
	}

	public boolean isEmpty()
	{
		return forexContractsById.isEmpty();
	}

	public ImmutableSet<ForexContractId> getForexContractIds()
	{
		return forexContractsById.keySet();
	}

	@Nullable
	public ForexContractId getSingleForexContractIdOrNull()
	{
		return singleForexContract != null ? singleForexContract.getId() : null;
	}

	@Nullable
	public CurrencyId suggestFromCurrencyId()
	{
		return singleForexContract != null ? singleForexContract.getCurrencyId() : null;
	}

	@Nullable
	public CurrencyId suggestFromCurrencyId(@Nullable final ForexContractId selectedContractId)
	{
		final ForexContract selectedContract = selectedContractId != null
				? forexContractsById.get(selectedContractId)
				: null;

		return selectedContract != null ? selectedContract.getCurrencyId() : suggestFromCurrencyId();
	}

	@Nullable
	public CurrencyId suggestToCurrencyId()
	{
		return singleForexContract != null ? singleForexContract.getToCurrencyId() : null;
	}

	@Nullable
	public CurrencyId suggestToCurrencyId(@Nullable final ForexContractId selectedContractId)
	{
		final ForexContract selectedContract = selectedContractId != null
				? forexContractsById.get(selectedContractId)
				: null;

		return selectedContract != null ? selectedContract.getToCurrencyId() : suggestToCurrencyId();
	}

	@Nullable
	public BigDecimal suggestCurrencyRate()
	{
		return singleForexContract != null ? singleForexContract.getCurrencyRate() : null;
	}

	@Nullable
	public BigDecimal suggestCurrencyRate(@Nullable final ForexContractId selectedContractId)
	{
		final ForexContract selectedContract = selectedContractId != null
				? forexContractsById.get(selectedContractId)
				: null;

		return selectedContract != null
				? selectedContract.getCurrencyRate()
				: suggestCurrencyRate();
	}

	public boolean isContractMatchingUserEnteredFields(
			@Nullable final ForexContractId selectedContractId,
			@Nullable final CurrencyId typedFromCurrencyId,
			@Nullable final CurrencyId typedToCurrencyId,
			@Nullable final BigDecimal typedCurrencyRate)
	{
		return CurrencyId.equals(typedFromCurrencyId, suggestFromCurrencyId(selectedContractId))
				&& CurrencyId.equals(typedToCurrencyId, suggestToCurrencyId(selectedContractId))
				&& NumberUtils.equalsByCompareTo(typedCurrencyRate, suggestCurrencyRate(selectedContractId));
	}
}
