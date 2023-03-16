package de.metas.forex.webui.process;

import com.google.common.collect.ImmutableSet;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractService;
import de.metas.i18n.BooleanWithReason;
import de.metas.order.OrderId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
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
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class C_Order_AllocateToForexContract extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);
	private final LookupDataSource forexContractLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_ForeignExchangeContract.Table_Name);

	private static final String PARAM_C_ForeignExchangeContract_ID = I_C_ForeignExchangeContract.COLUMNNAME_C_ForeignExchangeContract_ID;
	@Param(parameterName = PARAM_C_ForeignExchangeContract_ID, mandatory = true)
	private ForexContractId p_forexContractId;

	private static final String PARAM_Amount = "Amount";
	@Param(parameterName = PARAM_Amount, mandatory = true)
	private BigDecimal p_AmountBD;

	private ImmutableSet<ForexContractId> _eligibleForexContractIds;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final BooleanWithReason orderEligibleToAllocate = forexContractService.checkOrderEligibleToAllocate(orderId);
		if (orderEligibleToAllocate.isFalse())
		{
			return ProcessPreconditionsResolution.reject(orderEligibleToAllocate.getReason()).toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_Amount.equals(parameter.getColumnName()))
		{
			final OrderId orderId = getSelectedOrderId();
			return forexContractService.computeOrderAmountToAllocate(orderId).toBigDecimal();
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_C_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getEligibleForexContracts()
	{
		return forexContractLookup.findByIdsOrdered(getEligibleForexContractIds());
	}

	private ImmutableSet<ForexContractId> getEligibleForexContractIds()
	{
		ImmutableSet<ForexContractId> eligibleForexContractIds = this._eligibleForexContractIds;
		if (eligibleForexContractIds == null)
		{
			eligibleForexContractIds = this._eligibleForexContractIds = forexContractService.getContractIdsEligibleToAllocateOrder(getSelectedOrderId());
		}
		return eligibleForexContractIds;
	}

	@NonNull
	private OrderId getSelectedOrderId()
	{
		return OrderId.ofRepoId(getRecord_ID());
	}

	@Override
	protected String doIt()
	{
		if (p_AmountBD == null || p_AmountBD.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_Amount);
		}

		final OrderId orderId = getSelectedOrderId();
		forexContractService.allocateOrder(p_forexContractId, orderId, p_AmountBD);
		return MSG_OK;
	}
}
