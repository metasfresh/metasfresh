package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractService;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.springframework.context.annotation.Profile;

import javax.annotation.Nullable;
import java.util.Objects;

@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_HU_CreateReceipt_With_FEC_Param
		extends WEBUI_M_HU_CreateReceipt_Base
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);
	private final LookupDataSource forexContractLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_ForeignExchangeContract.Table_Name);

	private static final String PARAM_IsFEC = "IsFEC";
	@Param(parameterName = PARAM_IsFEC)
	private boolean isForexContract;

	private static final String PARAM_C_ForeignExchangeContract_ID = I_C_ForeignExchangeContract.COLUMNNAME_C_ForeignExchangeContract_ID;
	@Param(parameterName = PARAM_C_ForeignExchangeContract_ID)
	private ForexContractId forexContractId;

	private ImmutableSet<ForexContractId> _forexContractIds;

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_IsFEC.equals(parameter.getColumnName()))
		{
			return !getForexContractIds().isEmpty();
		}
		else if (PARAM_C_ForeignExchangeContract_ID.equals(parameter.getColumnName()))
		{
			final ImmutableSet<ForexContractId> forexContractIds = getForexContractIds();
			return forexContractIds.size() == 1
					? forexContractIds.iterator().next()
					: null;
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_C_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getAvailableForexContracts()
	{
		return forexContractLookup.findByIdsOrdered(getForexContractIds());
	}

	private ImmutableSet<ForexContractId> getForexContractIds()
	{
		ImmutableSet<ForexContractId> forexContractIds = this._forexContractIds;
		if (forexContractIds == null)
		{
			forexContractIds = this._forexContractIds = retrieveForexContractIds();
		}
		return forexContractIds;
	}

	private ImmutableSet<ForexContractId> retrieveForexContractIds()
	{
		final ImmutableSet<OrderId> orderIds = getM_ReceiptSchedules()
				.stream()
				.map(receiptSchedule -> OrderId.ofRepoIdOrNull(receiptSchedule.getC_Order_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (orderIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return forexContractService.getContractIdsByOrderIds(orderIds);
	}

	@Override
	protected void customizeParametersBuilder(final IHUReceiptScheduleBL.CreateReceiptsParameters.CreateReceiptsParametersBuilder parametersBuilder)
	{
		if (isForexContract)
		{
			if (forexContractId == null)
			{
				throw new FillMandatoryException(PARAM_C_ForeignExchangeContract_ID);
			}
			parametersBuilder.forexContractId(forexContractId);
		}
	}
}
