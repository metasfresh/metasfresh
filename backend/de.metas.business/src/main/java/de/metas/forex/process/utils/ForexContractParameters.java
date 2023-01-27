package de.metas.forex.process.utils;

import de.metas.common.util.CoalesceUtil;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractRef;
import de.metas.money.CurrencyId;
import de.metas.process.IProcessDefaultParametersProvider;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Data
@Builder
public class ForexContractParameters
{
	public static final String PARAM_IsFEC = "IsFEC";
	public static final String PARAM_FEC_Order_Currency_ID = "FEC_Order_Currency_ID";
	public static final String PARAM_C_ForeignExchangeContract_ID = "C_ForeignExchangeContract_ID";
	public static final String PARAM_FEC_From_Currency_ID = "FEC_From_Currency_ID";
	public static final String PARAM_FEC_To_Currency_ID = "FEC_To_Currency_ID";
	public static final String PARAM_FEC_CurrencyRate = "FEC_CurrencyRate";

	private boolean isFEC;
	@Nullable private CurrencyId orderCurrencyId;
	@Nullable private ForexContractId forexContractId;
	@Nullable private CurrencyId fromCurrencyId;
	@Nullable private CurrencyId toCurrencyId;
	@Nullable private BigDecimal currencyRate;

	@Nullable
	public ForexContractRef getForexContractRef()
	{
		if (isFEC)
		{
			if (forexContractId == null && currencyRate == null)
			{
				throw new FillMandatoryException(PARAM_C_ForeignExchangeContract_ID, PARAM_FEC_CurrencyRate);
			}
			if (orderCurrencyId == null)
			{
				throw new FillMandatoryException(PARAM_FEC_Order_Currency_ID);
			}
			if (fromCurrencyId == null)
			{
				throw new FillMandatoryException(PARAM_FEC_From_Currency_ID);
			}
			if (toCurrencyId == null)
			{
				throw new FillMandatoryException(PARAM_FEC_To_Currency_ID);
			}

			return ForexContractRef.builder()
					.orderCurrencyId(orderCurrencyId)
					.forexContractId(forexContractId)
					.fromCurrencyId(fromCurrencyId)
					.toCurrencyId(toCurrencyId)
					.currencyRate(currencyRate)
					.build();
		}
		else
		{
			return null;
		}
	}

	@Nullable
	public Object getParameterDefaultValue(
			@NonNull final String parameterName,
			@Nullable final ForexContracts contracts)
	{
		switch (parameterName)
		{
			case ForexContractParameters.PARAM_IsFEC:
				return contracts != null && !contracts.isEmpty();
			case ForexContractParameters.PARAM_FEC_Order_Currency_ID:
				return contracts != null ? contracts.getOrderCurrencyId() : null;
			case ForexContractParameters.PARAM_C_ForeignExchangeContract_ID:
				return contracts != null ? contracts.getSingleForexContractIdOrNull() : null;
			case ForexContractParameters.PARAM_FEC_From_Currency_ID:
				return contracts != null ? contracts.suggestFromCurrencyId() : null;
			case ForexContractParameters.PARAM_FEC_To_Currency_ID:
				return contracts != null ? contracts.suggestToCurrencyId() : null;
			case ForexContractParameters.PARAM_FEC_CurrencyRate:
				return contracts != null ? contracts.suggestCurrencyRate() : null;
			default:
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	public void updateOnParameterChanged(
			@NonNull final String parameterName,
			@Nullable final ForexContracts contracts)
	{
		if (contracts == null)
		{
			return;
		}

		if (ForexContractParameters.PARAM_C_ForeignExchangeContract_ID.equals(parameterName))
		{
			this.fromCurrencyId = CoalesceUtil.coalesce(
					contracts.suggestFromCurrencyId(forexContractId),
					this.fromCurrencyId);
			this.toCurrencyId = CoalesceUtil.coalesce(
					contracts.suggestToCurrencyId(forexContractId),
					this.toCurrencyId);
			this.currencyRate = CoalesceUtil.coalesce(
					contracts.suggestCurrencyRate(forexContractId),
					this.currencyRate);
		}
		else if (ForexContractParameters.PARAM_FEC_Order_Currency_ID.equals(parameterName)
				|| ForexContractParameters.PARAM_FEC_From_Currency_ID.equals(parameterName)
				|| ForexContractParameters.PARAM_FEC_To_Currency_ID.equals(parameterName)
				|| ForexContractParameters.PARAM_FEC_CurrencyRate.equals(parameterName))
		{
			if (!contracts.isContractMatchingUserEnteredFields(forexContractId, fromCurrencyId, toCurrencyId, currencyRate))
			{
				this.forexContractId = null;
			}
		}
	}

}
