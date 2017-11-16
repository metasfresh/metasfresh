package de.metas.contracts.flatrate.process;

import java.math.BigDecimal;

import org.compiere.Adempiere;

import de.metas.contracts.impl.ContractChangePriceQtyRepository;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

public class C_Flatrate_Term_ChangePriceQty extends JavaProcess
{

	public static final String PARAM_PRICEACTUAL = I_C_Flatrate_Term.COLUMNNAME_PriceActual;
	public static final String PARAM_PLANNED_QTY_PER_UNIT = I_C_Flatrate_Term.COLUMNNAME_PlannedQtyPerUnit;


	@Param(parameterName = PARAM_PRICEACTUAL, mandatory = false)
	private BigDecimal priceActual;

	@Param(parameterName = PARAM_PLANNED_QTY_PER_UNIT, mandatory = false)
	private BigDecimal plannedQtyPerUnit;

	final private ContractChangePriceQtyRepository contractsRepository = Adempiere.getBean(ContractChangePriceQtyRepository.class);

	@Override
	protected String doIt()
	{
		final I_C_Flatrate_Term contract = getRecord(I_C_Flatrate_Term.class);
		contractsRepository.changePriceIfNeeded(contract, priceActual);
		contractsRepository.changeQtyIfNeeded(contract, plannedQtyPerUnit);
		return "@Success@";
	}
}
