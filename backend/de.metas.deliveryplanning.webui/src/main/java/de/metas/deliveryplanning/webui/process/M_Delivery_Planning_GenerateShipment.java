package de.metas.deliveryplanning.webui.process;

import com.google.common.collect.ImmutableMap;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.DeliveryPlanningShipmentInfo;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractRef;
import de.metas.forex.ForexContractService;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.inout.ShipmentScheduleId;
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
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;

public class M_Delivery_Planning_GenerateShipment extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);
	private final LookupDataSource forexContractLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_ForeignExchangeContract.Table_Name);

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

	private ForexContracts _forexContracts = null; // lazy

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());
		final Optional<DeliveryPlanningShipmentInfo> optionalShipmentInfo = deliveryPlanningService.getShipmentInfoIfOutgoingType(deliveryPlanningId);
		if (!optionalShipmentInfo.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an outgoing delivery planning");
		}

		final DeliveryPlanningShipmentInfo shipmentInfo = optionalShipmentInfo.get();
		if (shipmentInfo.isShipped())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already shipped");
		}
		if (shipmentInfo.getSalesOrderId() == null)
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
		final DeliveryPlanningShipmentInfo shipmentInfo = deliveryPlanningService.getShipmentInfo(getDeliveryPlanningId());
		final OrderId salesOrderId = shipmentInfo.getSalesOrderId();
		if (salesOrderId == null)
		{
			throw new AdempiereException("Not an order based delivery planning");
		}

		return ForexContracts.builder()
				.orderCurrencyId(orderBL.getCurrencyId(salesOrderId))
				.forexContracts(forexContractService.getContractsByOrderId(salesOrderId))
				.build();
	}

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
	public LookupValuesList getAvailableForexContracts() {return forexContractLookup.findByIdsOrdered(getContracts().getForexContractIds());}

	@NonNull
	private DeliveryPlanningId getDeliveryPlanningId() {return DeliveryPlanningId.ofRepoId(getRecord_ID());}

	@Override
	protected String doIt()
	{
		if (p_DeliveryDate == null)
		{
			throw new FillMandatoryException(PARAM_DeliveryDate);
		}
		if (p_QtyBD == null || p_QtyBD.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_Qty);
		}

		final ForexContractRef forexContractRef = getForexContractParameters().getForexContractRef();

		final DeliveryPlanningId deliveryPlanningId = getDeliveryPlanningId();
		final DeliveryPlanningShipmentInfo shipmentInfo = deliveryPlanningService.getShipmentInfo(deliveryPlanningId);
		if (shipmentInfo.isShipped())
		{
			throw new AdempiereException("Already shipped");
		}

		final ShipmentScheduleId shipmentScheduleId = shipmentInfo.getShipmentScheduleId();

		final ShipmentScheduleEnqueuer.Result result = new ShipmentScheduleEnqueuer()
				.setContext(getCtx(), getTrxName())
				.createWorkpackages(
						ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.builder()
								.adPInstanceId(getPinstanceId())
								.queryFilters(queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
										.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId))
								.quantityType(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
								.completeShipments(true)
								.fixedShipmentDate(p_DeliveryDate)
								.qtysToDeliverOverride(ImmutableMap.<ShipmentScheduleId, BigDecimal>builder()
										.put(shipmentScheduleId, p_QtyBD)
										.build())
								.forexContractRef(forexContractRef)
								.deliveryPlanningId(deliveryPlanningId)
								.build());

		if (result.getWorkpackageEnqueuedCount() <= 0)
		{
			throw new AdempiereException("Could not ship")
					.setParameter("result", result);
		}

		return MSG_OK;
	}
}
