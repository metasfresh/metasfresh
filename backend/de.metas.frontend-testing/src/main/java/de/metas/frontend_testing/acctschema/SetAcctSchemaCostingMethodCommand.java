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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostElement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Switches {@code C_AcctSchema.CostingMethod} and activates the material cost elements of the new method, so that products
 * created afterwards get {@code M_Cost} rows for it. Returns the previous method code and the ids of the cost elements it
 * activated; passing them back (method + {@code deactivateCostElementIds}) restores everything the switch changed, so no
 * global state leaks into other specs.
 */
@Builder
public class SetAcctSchemaCostingMethodCommand
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final AcctSchemaId acctSchemaId;
	@NonNull private final String costingMethod;
	/** cost elements to deactivate after the switch, i.e. {@link Result#getActivatedCostElementIds()} of the call being undone */
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

		// fail before touching the schema, otherwise the schema would be left on a method without any cost element
		final List<I_M_CostElement> costElements = queryBL.createQueryBuilder(I_M_CostElement.class)
				.addEqualsFilter(I_M_CostElement.COLUMNNAME_CostingMethod, newMethod.getCode())
				.addEqualsFilter(I_M_CostElement.COLUMNNAME_CostElementType, CostElementType.Material.getCode())
				.create()
				.list();
		if (costElements.isEmpty())
		{
			throw new AdempiereException("No material cost element found for costing method " + newMethod);
		}

		final I_C_AcctSchema acctSchema = InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class);
		final String previousCostingMethod = acctSchema.getCostingMethod();
		acctSchema.setCostingMethod(newMethod.getCode());
		InterfaceWrapperHelper.saveRecord(acctSchema);

		final List<Integer> activatedIds = new ArrayList<>();
		for (final I_M_CostElement costElement : costElements)
		{
			if (!costElement.isActive())
			{
				costElement.setIsActive(true);
				InterfaceWrapperHelper.saveRecord(costElement);
				activatedIds.add(costElement.getM_CostElement_ID());
			}
		}

		if (deactivateCostElementIds != null)
		{
			for (final int costElementId : deactivateCostElementIds)
			{
				final I_M_CostElement costElement = InterfaceWrapperHelper.load(costElementId, I_M_CostElement.class);
				costElement.setIsActive(false);
				InterfaceWrapperHelper.saveRecord(costElement);
			}
		}

		return new Result(previousCostingMethod, activatedIds);
	}
}
