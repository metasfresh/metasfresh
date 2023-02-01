package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningShipmentInfo;
import de.metas.forex.ForexContractId;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.forex.process.utils.ShipmentForexContractParameters;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.money.CurrencyId;
import de.metas.order.OrderAndLineId;
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
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;

public class M_Delivery_Planning_GenerateReceipt extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private final DeliveryPlanningGenerateProcessesHelper helper = DeliveryPlanningGenerateProcessesHelper.newInstance();

	private static final String PARAM_ReceiptDate = "ReceiptDate";
	@Param(parameterName = PARAM_ReceiptDate, mandatory = true)
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

	@Param(parameterName = ShipmentForexContractParameters.PARAM_IsB2B)
	private boolean p_IsB2B;

	@Param(parameterName = ShipmentForexContractParameters.PARAM_IsGenerateB2BShipment)
	private boolean p_IsGenerateB2BShipment;

	//
	// Shipment FEC Parameters
	@Param(parameterName = ShipmentForexContractParameters.PARAM_IsShipmentFEC)
	private boolean p_IsShipmentFEC;

	@Param(parameterName = ShipmentForexContractParameters.PARAM_FEC_SalesOrder_Currency_ID)
	private CurrencyId p_FEC_SalesOrder_Currency_ID;

	@Param(parameterName = ShipmentForexContractParameters.PARAM_FEC_Shipment_ForeignExchangeContract_ID)
	private ForexContractId p_FEC_Shipment_ForeignExchangeContract_ID;

	@Param(parameterName = ShipmentForexContractParameters.PARAM_FEC_Shipment_From_Currency_ID)
	private CurrencyId p_FEC_Shipment_From_Currency_ID;
	@Param(parameterName = ShipmentForexContractParameters.PARAM_FEC_Shipment_To_Currency_ID)
	private CurrencyId p_FEC_Shipment_To_Currency_ID;

	@Param(parameterName = ShipmentForexContractParameters.PARAM_FEC_Shipment_CurrencyRate)
	private BigDecimal p_FEC_Shipment_CurrencyRate;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());
		final Optional<DeliveryPlanningReceiptInfo> optionalDeliveryPlanningReceipt = helper.getReceiptInfoIfIncomingType(deliveryPlanningId);
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

	private ForexContracts getReceiptContracts() {return helper.getReceiptContracts(getDeliveryPlanningId());}

	private Optional<ForexContracts> getB2BShipmentContracts() {return helper.getB2BShipmentContracts(getReceiptInfo());}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();

		return IProcessDefaultParametersProvider.firstAvailableValue(
				() -> getReceiptForexContractParameters().getParameterDefaultValue(parameterName, getReceiptContracts()),
				() -> getShipmentForexContractParameters().getParameterDefaultValue(parameterName, getB2BShipmentContracts().orElse(null))
		);
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		updateReceiptForexContractParameters(params -> params.updateOnParameterChanged(parameterName, getReceiptContracts()));
		updateShipmentForexContractParameters(params -> params.updateOnParameterChanged(parameterName, getB2BShipmentContracts().orElse(null)));
	}

	private ForexContractParameters getReceiptForexContractParameters()
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

	private ShipmentForexContractParameters getShipmentForexContractParameters()
	{
		return ShipmentForexContractParameters.builder()
				.isB2B(p_IsB2B)
				.isGenerateB2BShipment(p_IsGenerateB2BShipment)
				.isFEC(p_IsShipmentFEC)
				.orderCurrencyId(p_FEC_SalesOrder_Currency_ID)
				.forexContractId(p_FEC_Shipment_ForeignExchangeContract_ID)
				.fromCurrencyId(p_FEC_Shipment_From_Currency_ID)
				.toCurrencyId(p_FEC_Shipment_To_Currency_ID)
				.currencyRate(p_FEC_Shipment_CurrencyRate)
				.build();
	}

	private void updateReceiptForexContractParameters(@NonNull final Consumer<ForexContractParameters> updater)
	{
		final ForexContractParameters params = getReceiptForexContractParameters();
		updater.accept(params);
		this.p_IsForexContract = params.isFEC();
		this.p_FEC_Order_Currency_ID = params.getOrderCurrencyId();
		this.p_forexContractId = params.getForexContractId();
		this.p_FEC_From_Currency_ID = params.getFromCurrencyId();
		this.p_FEC_To_Currency_ID = params.getToCurrencyId();
		this.p_FEC_CurrencyRate = params.getCurrencyRate();
	}

	private void updateShipmentForexContractParameters(@NonNull final Consumer<ShipmentForexContractParameters> updater)
	{
		final ShipmentForexContractParameters params = getShipmentForexContractParameters();
		updater.accept(params);
		this.p_IsB2B = params.isB2B();
		this.p_IsGenerateB2BShipment = params.isGenerateB2BShipment();
		this.p_IsShipmentFEC = params.isFEC();
		this.p_FEC_SalesOrder_Currency_ID = params.getOrderCurrencyId();
		this.p_FEC_Shipment_ForeignExchangeContract_ID = params.getForexContractId();
		this.p_FEC_Shipment_From_Currency_ID = params.getFromCurrencyId();
		this.p_FEC_Shipment_To_Currency_ID = params.getToCurrencyId();
		this.p_FEC_Shipment_CurrencyRate = params.getCurrencyRate();
	}

	@ProcessParamLookupValuesProvider(parameterName = ForexContractParameters.PARAM_C_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getAvailableReceiptForexContracts() {return helper.toLookupValuesList(getReceiptContracts());}

	@ProcessParamLookupValuesProvider(parameterName = ShipmentForexContractParameters.PARAM_FEC_Shipment_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getAvailableB2BShipmentForexContracts()
	{
		final ForexContracts b2bShipmentContracts = helper.getB2BShipmentContracts(getReceiptInfo()).orElse(null);
		return helper.toLookupValuesList(b2bShipmentContracts);
	}

	@NonNull
	private DeliveryPlanningId getDeliveryPlanningId() {return DeliveryPlanningId.ofRepoId(getRecord_ID());}

	private DeliveryPlanningReceiptInfo getReceiptInfo() {return helper.getReceiptInfo(getDeliveryPlanningId());}

	@Override
	protected String doIt()
	{
		final Instant receiptDate = FillMandatoryException.assertNotNull(p_ReceiptDate, PARAM_ReceiptDate);
		final BigDecimal qty = FillMandatoryException.assertPositive(p_QtyBD, PARAM_Qty);

		final DeliveryPlanningGenerateReceiptResult receiptResult = helper.generateReceipt(
				DeliveryPlanningGenerateReceiptRequest.builder()
						.deliveryPlanningId(getDeliveryPlanningId())
						.receiptDate(receiptDate)
						.qtyToReceiveBD(qty)
						.forexContractRef(getReceiptForexContractParameters().getForexContractRef())
						.build());

		if (p_IsGenerateB2BShipment)
		{
			final DeliveryPlanningShipmentInfo b2bShipmentInfo = helper.getB2BShipmentInfo(getReceiptInfo())
					.orElseThrow(() -> new AdempiereException("No B2B shipment found")); // shall not happen

			final OrderAndLineId salesOrderAndLineId = Check.assumeNotNull(b2bShipmentInfo.getSalesOrderAndLineId(), "B2B Sales Order Line is set");

			final HUReservationService huReservationService = SpringContextHolder.instance.getBean(HUReservationService.class);
			huReservationService.makeReservation(ReserveHUsRequest.builder()
					.qtyToReserve(receiptResult.getQty())
					.documentRef(HUReservationDocRef.ofSalesOrderLineId(salesOrderAndLineId.getOrderLineId()))
					.productId(receiptResult.getProductId())
					.customerId(b2bShipmentInfo.getCustomerId())
					.huId(receiptResult.getReceivedVHUId())
					.build());

			helper.generateShipment(DeliveryPlanningGenerateShipmentRequest.builder()
					.adPInstanceId(getPinstanceId())
					.deliveryPlanningId(b2bShipmentInfo.getDeliveryPlanningId())
					.deliveryDate(TimeUtil.asLocalDate(receiptDate))
					.qtyToShipBD(qty)
					.forexContractRef(getShipmentForexContractParameters().getForexContractRef())
					.build());
		}

		return MSG_OK;
	}
}
