package de.metas.contracts.flatrate.process;

import de.metas.contracts.impl.ContractChangePriceQtyService;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;

public class C_Flatrate_Term_ChangePriceQty extends JavaProcess implements IProcessPrecondition
{

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_PriceActual, mandatory = false)
	private BigDecimal priceActual;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_PlannedQtyPerUnit, mandatory = false)
	private BigDecimal plannedQtyPerUnit;

	final private ContractChangePriceQtyService contractsRepository = SpringContextHolder.instance.getBean(ContractChangePriceQtyService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if(ProcessUtil.isFlatFeeContract(context))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not supported for FlatFee contracts");
		}
		return ProcessPreconditionsResolution.accept();
	}
	
	@Override
	protected String doIt()
	{
		final I_C_Flatrate_Term contract = getRecord(I_C_Flatrate_Term.class);
		contractsRepository.changePriceIfNeeded(contract, priceActual);
		contractsRepository.changeQtyIfNeeded(contract, plannedQtyPerUnit);
		return "@Success@";
	}
}
