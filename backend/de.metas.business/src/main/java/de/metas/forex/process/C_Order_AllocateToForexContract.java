package de.metas.forex.process;

import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractService;
import de.metas.i18n.BooleanWithReason;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract;

public class C_Order_AllocateToForexContract extends JavaProcess implements IProcessPrecondition
{
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);

	@Param(parameterName = I_C_ForeignExchangeContract.COLUMNNAME_C_ForeignExchangeContract_ID, mandatory = true)
	private ForexContractId p_forexContractId;

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

	@Override
	protected String doIt()
	{
		final OrderId orderId = OrderId.ofRepoId(getRecord_ID());
		forexContractService.allocateOrder(p_forexContractId, orderId);
		return MSG_OK;
	}
}
