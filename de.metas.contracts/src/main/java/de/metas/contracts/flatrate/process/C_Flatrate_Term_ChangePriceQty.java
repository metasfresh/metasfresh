package de.metas.contracts.flatrate.process;

import java.math.BigDecimal;

import org.compiere.SpringContextHolder;

import de.metas.contracts.impl.ContractChangePriceQtyService;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

public class C_Flatrate_Term_ChangePriceQty extends JavaProcess
{

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_PriceActual, mandatory = false)
	private BigDecimal priceActual;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_PlannedQtyPerUnit, mandatory = false)
	private BigDecimal plannedQtyPerUnit;

	final private ContractChangePriceQtyService contractsRepository = SpringContextHolder.instance.getBean(ContractChangePriceQtyService.class);

	@Override
	protected String doIt()
	{
		final I_C_Flatrate_Term contract = getRecord(I_C_Flatrate_Term.class);
		contractsRepository.changePriceIfNeeded(contract, priceActual);
		contractsRepository.changeQtyIfNeeded(contract, plannedQtyPerUnit);
		return "@Success@";
	}
}
