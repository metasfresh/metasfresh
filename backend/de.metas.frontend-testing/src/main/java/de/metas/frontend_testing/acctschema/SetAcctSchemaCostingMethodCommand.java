package de.metas.frontend_testing.acctschema;

import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_CostElement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Switches {@code C_AcctSchema.CostingMethod} and, unless the new method already has an active material cost element, activates one, so that products
 * created afterwards get {@code M_Cost} rows for it. Returns the previous method code and the ids of the cost elements it
 * activated; passing them back (method + {@code deactivateCostElementIds}) restores everything the switch changed, so no
 * global state leaks into other specs.
 */
@Builder
public class SetAcctSchemaCostingMethodCommand
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final AcctSchemaCostingRepository costingRepository = new AcctSchemaCostingRepository();

	@NonNull private final AcctSchemaId acctSchemaId;
	@NonNull private final String costingMethod;
	/**
	 * When not {@code null} this call is a restore: it only sets the method back and deactivates exactly these cost elements,
	 * i.e. the {@link Result#getActivatedCostElementIds()} of the call being undone; nothing is activated.
	 */
	@Nullable private final List<Integer> deactivateCostElementIds;

	@Value
	public static class Result
	{
		String previousCostingMethod;
		List<Integer> activatedCostElementIds;
	}

	public Result execute()
	{
		final CostingMethod newMethod = CostingMethod.ofCode(costingMethod);
		final boolean isRestore = deactivateCostElementIds != null;

		// fail before touching the schema, otherwise the schema would be left on a method without any cost element
		if (!isRestore)
		{
			retrieveMaterialCostElements(newMethod);
		}

		final String previousCostingMethod = costingRepository.updateCostingMethod(acctSchemaId, newMethod.getCode());

		// Activate one element only, and none when the method already has an active one: two active material cost
		// elements of the same method double every cost detail, M_Cost row and posting.
		// Look the elements up AFTER the switch: saving the schema creates an active material cost element for the new
		// method when it has none active (C_AcctSchema.checkCosting), which a lookup before the switch does not see.
		// A restore only puts the recorded state back: it must neither require nor activate cost elements,
		// or an element that was inactive before the switch would be silently activated.
		final List<Integer> activatedIds = new ArrayList<>();
		if (!isRestore)
		{
			final List<I_M_CostElement> costElements = retrieveMaterialCostElements(newMethod);
			final boolean hasActiveElement = costElements.stream().anyMatch(I_M_CostElement::isActive);
			if (!hasActiveElement)
			{
				final I_M_CostElement costElement = costElements.get(0);
				costingRepository.activateCostElement(costElement);
				activatedIds.add(costElement.getM_CostElement_ID());
			}
		}

		if (deactivateCostElementIds != null)
		{
			for (final int costElementId : deactivateCostElementIds)
			{
				costingRepository.deactivateCostElement(costElementId);
			}
		}

		return new Result(previousCostingMethod, activatedIds);
	}

	private List<I_M_CostElement> retrieveMaterialCostElements(@NonNull final CostingMethod newMethod)
	{
		final List<I_M_CostElement> costElements = queryBL.createQueryBuilder(I_M_CostElement.class)
				.addEqualsFilter(I_M_CostElement.COLUMNNAME_CostingMethod, newMethod.getCode())
				.addEqualsFilter(I_M_CostElement.COLUMNNAME_CostElementType, CostElementType.Material.getCode())
				.orderBy(I_M_CostElement.COLUMNNAME_M_CostElement_ID)
				.create()
				.list();
		if (costElements.isEmpty())
		{
			throw new AdempiereException("No material cost element found for costing method " + newMethod);
		}
		return costElements;
	}
}
