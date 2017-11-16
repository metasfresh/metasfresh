package de.metas.contracts.flatrate.process;

import java.math.BigDecimal;

import org.compiere.Adempiere;

import de.metas.contracts.impl.ContractChangePriceQtyRepository;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import lombok.NonNull;

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
		changePriceIfNeeded(contract);
		changeQtyIfNeeded(contract);
		return "@Success@";
	}

	private void changePriceIfNeeded(@NonNull final I_C_Flatrate_Term contract )
	{
		if (priceActual != null)
		{
			contractsRepository.changeFlatrateTermPrice(contract, priceActual);
		}
	}

	private void changeQtyIfNeeded(@NonNull final I_C_Flatrate_Term contract )
	{
		if (plannedQtyPerUnit != null)
		{
			contractsRepository.changeFlatrateTermQty(contract, plannedQtyPerUnit);
			contractsRepository.changeQtyInSubscriptionProgressOfFlatrateTerm(contract, plannedQtyPerUnit);
		}
	}
}
