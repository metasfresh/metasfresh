package de.metas.cucumber.stepdefs.costing;

import de.metas.costing.impl.CostElementRepository;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;

@RequiredArgsConstructor
public class M_CostElement_StepDef
{
	private final CostElementRepository costElementRepository = SpringContextHolder.instance.getBean(CostElementRepository.class);
	private final M_CostElement_StepDefData costElementTable;

	@Given("^cost elements for material costing methods (.*) are active")
	public void createOrUpdateCostElementsForCostingMethods(@NonNull final String costingMethodsStr) throws Throwable
	{
		costElementTable.getIdsOfCommaSeparatedString(costingMethodsStr);
	}
}
