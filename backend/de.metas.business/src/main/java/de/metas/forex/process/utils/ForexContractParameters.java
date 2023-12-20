package de.metas.forex.process.utils;

import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractRef;
import de.metas.money.CurrencyId;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.api.IParams;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ForexContractParameters
{
	public static ForexContractParameters newInstance() {return builder().build();}

	public static final String PARAM_IsFEC = "IsFEC";
	public static final String PARAM_FEC_Order_Currency_ID = "FEC_Order_Currency_ID";
	public static final String PARAM_C_ForeignExchangeContract_ID = "C_ForeignExchangeContract_ID";
	public static final String PARAM_FEC_From_Currency_ID = "FEC_From_Currency_ID";
	public static final String PARAM_FEC_To_Currency_ID = "FEC_To_Currency_ID";
	public static final String PARAM_FEC_CurrencyRate = "FEC_CurrencyRate";

	@Param(parameterName = ForexContractParameters.PARAM_IsFEC)
	private final boolean isFEC;
	@Param(parameterName = ForexContractParameters.PARAM_FEC_Order_Currency_ID)
	@Nullable private final CurrencyId orderCurrencyId;
	@Param(parameterName = ForexContractParameters.PARAM_C_ForeignExchangeContract_ID)
	@Nullable private ForexContractId forexContractId;
	@Param(parameterName = ForexContractParameters.PARAM_FEC_From_Currency_ID)
	@Nullable private CurrencyId fromCurrencyId;
	@Param(parameterName = ForexContractParameters.PARAM_FEC_To_Currency_ID)
	@Nullable private CurrencyId toCurrencyId;
	@Param(parameterName = ForexContractParameters.PARAM_FEC_CurrencyRate)
	@Nullable private BigDecimal currencyRate;

	public static ForexContractParameters ofParams(@NonNull final IParams params)
	{
		return builder()
				.isFEC(params.getParameterAsBool(PARAM_IsFEC))
				.orderCurrencyId(params.getParameterAsId(PARAM_FEC_Order_Currency_ID, CurrencyId.class))
				.forexContractId(params.getParameterAsId(PARAM_C_ForeignExchangeContract_ID, ForexContractId.class))
				.fromCurrencyId(params.getParameterAsId(PARAM_FEC_From_Currency_ID, CurrencyId.class))
				.toCurrencyId(params.getParameterAsId(PARAM_FEC_To_Currency_ID, CurrencyId.class))
				.currencyRate(params.getParameterAsBigDecimal(PARAM_FEC_CurrencyRate))
				.build();
	}

	public Map<String, Object> toMap()
	{
		final HashMap<String, Object> params = new HashMap<>();
		params.put(PARAM_IsFEC, isFEC);
		params.put(PARAM_FEC_Order_Currency_ID, orderCurrencyId);
		params.put(PARAM_C_ForeignExchangeContract_ID, forexContractId);
		params.put(PARAM_FEC_From_Currency_ID, fromCurrencyId);
		params.put(PARAM_FEC_To_Currency_ID, toCurrencyId);
		params.put(PARAM_FEC_CurrencyRate, currencyRate);

		return params;
	}

	@Nullable
	public ForexContractRef getForexContractRef()
	{
		if (isFEC)
		{
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
			if (currencyRate == null || currencyRate.signum() <= 0)
			{
				throw new FillMandatoryException(PARAM_FEC_CurrencyRate);
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
			if(forexContractId != null)
			{
				this.fromCurrencyId = contracts.suggestFromCurrencyId(forexContractId);
				this.toCurrencyId = contracts.suggestToCurrencyId(forexContractId);
				this.currencyRate = contracts.suggestCurrencyRate(forexContractId);
			}
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
