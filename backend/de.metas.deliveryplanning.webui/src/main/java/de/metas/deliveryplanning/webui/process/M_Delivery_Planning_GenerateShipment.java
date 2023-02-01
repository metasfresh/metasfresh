package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.forex.ForexContractId;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.money.CurrencyId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Consumer;

public class M_Delivery_Planning_GenerateShipment extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private final DeliveryPlanningGenerateProcessesHelper helper = DeliveryPlanningGenerateProcessesHelper.newInstance();

	private static final String PARAM_DeliveryDate = "DeliveryDate";
	@Param(parameterName = PARAM_DeliveryDate, mandatory = true)
	private LocalDate p_DeliveryDate;

	private static final String PARAM_Qty = "Qty";
	@Param(parameterName = PARAM_Qty, mandatory = true)
	private BigDecimal p_QtyBD;

	//
	// FEC Parameters
	@Param(parameterName = ForexContractParameters.PARAM_IsFEC)
	private boolean p_IsForexContract;

	@Param(parameterName = ForexContractParameters.PARAM_FEC_Order_Currency_ID)
	private CurrencyId p_FEC_Order_Currency_ID;

	@Param(parameterName = ForexContractParameters.PARAM_C_ForeignExchangeContract_ID)
	private ForexContractId p_forexContractId;

	@Param(parameterName = ForexContractParameters.PARAM_FEC_From_Currency_ID)
	private CurrencyId p_FEC_From_Currency_ID;
	@Param(parameterName = ForexContractParameters.PARAM_FEC_To_Currency_ID)
	private CurrencyId p_FEC_To_Currency_ID;

	@Param(parameterName = ForexContractParameters.PARAM_FEC_CurrencyRate)
	private BigDecimal p_FEC_CurrencyRate;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());
		return helper.checkEligibleToCreateShipment(deliveryPlanningId);
	}

	private ForexContracts getContracts() {return helper.getShipmentContracts(getDeliveryPlanningId());}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter) {return getForexContractParameters().getParameterDefaultValue(parameter.getColumnName(), getContracts());}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		updateForexContractParameters(params -> params.updateOnParameterChanged(parameterName, getContracts()));
	}

	private ForexContractParameters getForexContractParameters()
	{
		return ForexContractParameters.builder()
				.isFEC(p_IsForexContract)
				.orderCurrencyId(p_FEC_Order_Currency_ID)
				.forexContractId(p_forexContractId)
				.fromCurrencyId(p_FEC_From_Currency_ID)
				.toCurrencyId(p_FEC_To_Currency_ID)
				.currencyRate(p_FEC_CurrencyRate)
				.build();
	}

	private void updateForexContractParameters(@NonNull final Consumer<ForexContractParameters> updater)
	{
		final ForexContractParameters params = getForexContractParameters();
		updater.accept(params);
		this.p_IsForexContract = params.isFEC();
		this.p_FEC_Order_Currency_ID = params.getOrderCurrencyId();
		this.p_forexContractId = params.getForexContractId();
		this.p_FEC_From_Currency_ID = params.getFromCurrencyId();
		this.p_FEC_To_Currency_ID = params.getToCurrencyId();
		this.p_FEC_CurrencyRate = params.getCurrencyRate();
	}

	@ProcessParamLookupValuesProvider(parameterName = ForexContractParameters.PARAM_C_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getAvailableForexContracts() {return helper.toLookupValuesList(getContracts());}

	@NonNull
	private DeliveryPlanningId getDeliveryPlanningId() {return DeliveryPlanningId.ofRepoId(getRecord_ID());}

	@Override
	protected String doIt()
	{
		helper.generateShipment(
				DeliveryPlanningGenerateShipmentRequest.builder()
						.adPInstanceId(getPinstanceId())
						.deliveryPlanningId(getDeliveryPlanningId())
						.deliveryDate(FillMandatoryException.assertNotNull(p_DeliveryDate, PARAM_DeliveryDate))
						.qtyToShipBD(FillMandatoryException.assertPositive(p_QtyBD, PARAM_Qty))
						.forexContractRef(getForexContractParameters().getForexContractRef())
						.build());

		return MSG_OK;
	}
}
