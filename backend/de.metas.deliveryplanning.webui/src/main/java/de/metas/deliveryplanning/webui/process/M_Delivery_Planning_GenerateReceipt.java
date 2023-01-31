package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractRef;
import de.metas.forex.ForexContractService;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;

public class M_Delivery_Planning_GenerateReceipt extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final LookupDataSource forexContractLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_ForeignExchangeContract.Table_Name);

	@Param(parameterName = "ReceiptDate", mandatory = true)
	private Instant p_ReceiptDate;

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

	private ForexContracts _forexContracts = null; // lazy

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());
		final Optional<DeliveryPlanningReceiptInfo> optionalDeliveryPlanningReceipt = deliveryPlanningService.getReceiptInfoIfIncomingType(deliveryPlanningId);
		if (!optionalDeliveryPlanningReceipt.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an incoming delivery planning");
		}

		final DeliveryPlanningReceiptInfo receiptInfo = optionalDeliveryPlanningReceipt.get();
		if (receiptInfo.isReceived())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already received");
		}
		if (receiptInfo.getPurchaseOrderId() == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an order based delivery planning");
		}

		return ProcessPreconditionsResolution.accept();
	}

	private ForexContracts getContracts()
	{
		ForexContracts forexContracts = this._forexContracts;
		if (forexContracts == null)
		{
			forexContracts = this._forexContracts = retrieveContracts();
		}
		return forexContracts;
	}

	private ForexContracts retrieveContracts()
	{
		final DeliveryPlanningReceiptInfo receiptInfo = deliveryPlanningService.getReceiptInfo(getDeliveryPlanningId());
		final OrderId purchaseOrderId = receiptInfo.getPurchaseOrderId();
		if (purchaseOrderId == null)
		{
			throw new AdempiereException("Not an order based delivery planning");
		}

		return ForexContracts.builder()
				.orderCurrencyId(orderBL.getCurrencyId(purchaseOrderId))
				.forexContracts(forexContractService.getContractsByOrderId(purchaseOrderId))
				.build();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return getForexContractParameters().getParameterDefaultValue(parameter.getColumnName(), getContracts());
	}

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
	public LookupValuesList getAvailableForexContracts() {return forexContractLookup.findByIdsOrdered(getContracts().getForexContractIds());}

	@NonNull
	private DeliveryPlanningId getDeliveryPlanningId() {return DeliveryPlanningId.ofRepoId(getRecord_ID());}

	@Override
	protected String doIt()
	{
		final DeliveryPlanningId deliveryPlanningId = getDeliveryPlanningId();
		final DeliveryPlanningReceiptInfo receiptInfo = deliveryPlanningService.getReceiptInfo(deliveryPlanningId);
		if (receiptInfo.isReceived())
		{
			throw new AdempiereException("Already received");
		}

		createReceipt(receiptInfo.getReceiptScheduleId(), deliveryPlanningId);

		return MSG_OK;
	}

	private void createReceipt(
			@NonNull final ReceiptScheduleId receiptScheduleId,
			@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(receiptScheduleId);
		final Quantity qty = Quantitys.create(p_QtyBD, extractUomId(receiptSchedule));
		if (!qty.isPositive())
		{
			throw new FillMandatoryException(PARAM_Qty);
		}

		final ForexContractRef forexContractRef = getForexContractParameters().getForexContractRef();

		final I_M_HU vhu = huReceiptScheduleBL.createPlanningVHU(receiptSchedule, qty);
		if (vhu == null)
		{
			throw new AdempiereException("Failed receiving"); // shall not happen
		}

		final InOutGenerateResult result = huReceiptScheduleBL.processReceiptSchedules(
				CreateReceiptsParameters.builder()
						.commitEachReceiptIndividually(false)
						.movementDateRule(ReceiptMovementDateRule.fixedDate(p_ReceiptDate))
						.ctx(getCtx())
						.destinationLocatorIdOrNull(null) // use receipt schedules' destination-warehouse settings
						.printReceiptLabels(true)
						.receiptSchedule(receiptSchedule)
						.selectedHuId(HuId.ofRepoId(vhu.getM_HU_ID()))
						.forexContractRef(forexContractRef)
						.deliveryPlanningId(deliveryPlanningId)
						.build());

		result.getSingleInOutId();
	}

	private static UomId extractUomId(@NonNull I_M_ReceiptSchedule rs)
	{
		return UomId.ofRepoId(rs.getC_UOM_ID());
	}
}
