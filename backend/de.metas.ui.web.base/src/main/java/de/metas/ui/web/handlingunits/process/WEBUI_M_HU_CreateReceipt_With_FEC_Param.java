package de.metas.ui.web.handlingunits.process;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.forex.ForexContractService;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.NestedParams;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.springframework.context.annotation.Profile;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_HU_CreateReceipt_With_FEC_Param
		extends WEBUI_M_HU_CreateReceipt_Base
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final LookupDataSource forexContractLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_ForeignExchangeContract.Table_Name);

	@NestedParams
	private final ForexContractParameters p_FECParams = ForexContractParameters.newInstance();

	private final Supplier<ForexContracts> forexContractsSupplier = Suppliers.memoize(this::retrieveContracts);

	@Nullable
	private ForexContracts getContracts()
	{
		return forexContractsSupplier.get();
	}

	@Nullable
	private ForexContracts retrieveContracts()
	{
		final ImmutableSet<OrderId> purchaseOrderIds = getM_ReceiptSchedules()
				.stream()
				.map(receiptSchedule -> OrderId.ofRepoIdOrNull(receiptSchedule.getC_Order_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (purchaseOrderIds.size() != 1)
		{
			return null;
		}

		final OrderId purchaseOrderId = purchaseOrderIds.iterator().next();

		return ForexContracts.builder()
				.orderCurrencyId(orderBL.getCurrencyId(purchaseOrderId))
				.forexContracts(forexContractService.getContractsByOrderId(purchaseOrderId))
				.build();
	}

	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return super.checkPreconditionsApplicable().and(this::checkHasFEC);
	}

	private ProcessPreconditionsResolution checkHasFEC()
	{
		final ForexContracts contracts = getContracts();
		return contracts != null && !contracts.isEmpty()
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("No FEC");
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return p_FECParams.getParameterDefaultValue(parameter.getColumnName(), getContracts());
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		p_FECParams.updateOnParameterChanged(parameterName, getContracts());
	}

	@ProcessParamLookupValuesProvider(parameterName = ForexContractParameters.PARAM_C_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getAvailableForexContracts()
	{
		final ForexContracts contracts = getContracts();
		return contracts != null
				? forexContractLookup.findByIdsOrdered(contracts.getForexContractIds())
				: LookupValuesList.EMPTY;
	}

	@Override
	protected void customizeParametersBuilder(final IHUReceiptScheduleBL.CreateReceiptsParameters.CreateReceiptsParametersBuilder parametersBuilder)
	{
		parametersBuilder.forexContractRef(p_FECParams.getForexContractRef());
	}
}
