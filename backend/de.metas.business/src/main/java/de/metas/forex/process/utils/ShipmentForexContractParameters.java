package de.metas.forex.process.utils;

import com.google.common.collect.ImmutableBiMap;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractRef;
import de.metas.money.CurrencyId;
import de.metas.process.IProcessDefaultParametersProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class ShipmentForexContractParameters
{
	public static final String PARAM_IsB2B = "IsB2B";
	public static final String PARAM_IsGenerateB2BShipment = "IsGenerateB2BShipment";
	public static final String PARAM_IsShipmentFEC = "IsShipmentFEC";
	public static final String PARAM_FEC_SalesOrder_Currency_ID = "FEC_SalesOrder_Currency_ID";
	public static final String PARAM_FEC_Shipment_ForeignExchangeContract_ID = "FEC_Shipment_ForeignExchangeContract_ID";
	public static final String PARAM_FEC_Shipment_From_Currency_ID = "FEC_Shipment_From_Currency_ID";
	public static final String PARAM_FEC_Shipment_To_Currency_ID = "FEC_Shipment_To_Currency_ID";
	public static final String PARAM_FEC_Shipment_CurrencyRate = "FEC_Shipment_CurrencyRate";

	private static final ImmutableBiMap<String, String> MAP_NormalizedParameterNameByShipmentParameterName = ImmutableBiMap.<String, String>builder()
			.put(PARAM_IsShipmentFEC, ForexContractParameters.PARAM_IsFEC)
			.put(PARAM_FEC_SalesOrder_Currency_ID, ForexContractParameters.PARAM_FEC_Order_Currency_ID)
			.put(PARAM_FEC_Shipment_ForeignExchangeContract_ID, ForexContractParameters.PARAM_C_ForeignExchangeContract_ID)
			.put(PARAM_FEC_Shipment_From_Currency_ID, ForexContractParameters.PARAM_FEC_From_Currency_ID)
			.put(PARAM_FEC_Shipment_To_Currency_ID, ForexContractParameters.PARAM_FEC_To_Currency_ID)
			.put(PARAM_FEC_Shipment_CurrencyRate, ForexContractParameters.PARAM_FEC_CurrencyRate)
			.build();

	@Getter private final boolean isB2B;
	@Getter private final boolean isGenerateB2BShipment;
	private final ForexContractParameters parameters;

	@Builder
	private ShipmentForexContractParameters(
			final boolean isB2B,
			final boolean isGenerateB2BShipment,
			final boolean isFEC,
			@Nullable final CurrencyId orderCurrencyId,
			@Nullable final ForexContractId forexContractId,
			@Nullable final CurrencyId fromCurrencyId,
			@Nullable final CurrencyId toCurrencyId,
			@Nullable final BigDecimal currencyRate)
	{
		this.isB2B = isB2B;
		this.isGenerateB2BShipment = isGenerateB2BShipment;

		this.parameters = ForexContractParameters.builder()
				.isFEC(isFEC)
				.orderCurrencyId(orderCurrencyId)
				.forexContractId(forexContractId)
				.fromCurrencyId(fromCurrencyId)
				.toCurrencyId(toCurrencyId)
				.currencyRate(currencyRate)
				.build();
	}

	public boolean isFEC() {return parameters.isFEC();}

	public CurrencyId getOrderCurrencyId() {return parameters.getOrderCurrencyId();}

	public ForexContractId getForexContractId() {return parameters.getForexContractId();}

	public CurrencyId getFromCurrencyId() {return parameters.getFromCurrencyId();}

	public CurrencyId getToCurrencyId() {return parameters.getToCurrencyId();}

	public BigDecimal getCurrencyRate() {return parameters.getCurrencyRate();}

	@Nullable
	public Object getParameterDefaultValue(
			@NonNull final String shipmentParameterName,
			@Nullable final ForexContracts contracts)
	{
		if (PARAM_IsB2B.equals(shipmentParameterName))
		{
			return contracts != null;
		}
		if (PARAM_IsGenerateB2BShipment.equals(shipmentParameterName))
		{
			return contracts != null;
		}

		final String normalizedParameterName = MAP_NormalizedParameterNameByShipmentParameterName.get(shipmentParameterName);
		if (normalizedParameterName != null)
		{
			return parameters.getParameterDefaultValue(normalizedParameterName, contracts);
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	public void updateOnParameterChanged(final String shipmentParameterName, @Nullable final ForexContracts contracts)
	{
		final String normalizedParameterName = MAP_NormalizedParameterNameByShipmentParameterName.get(shipmentParameterName);
		if (normalizedParameterName != null)
		{
			parameters.updateOnParameterChanged(normalizedParameterName, contracts);
		}
	}

	public ForexContractRef getForexContractRef()
	{
		if (!isGenerateB2BShipment)
		{
			return null;
		}

		return parameters.getForexContractRef();
	}
}
