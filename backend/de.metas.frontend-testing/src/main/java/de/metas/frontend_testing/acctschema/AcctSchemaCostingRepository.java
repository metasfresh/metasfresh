package de.metas.frontend_testing.acctschema;

import de.metas.acct.api.AcctSchemaId;
import lombok.NonNull;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostElement;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Persists the costing settings that {@link SetAcctSchemaCostingMethodCommand} switches.
 */
class AcctSchemaCostingRepository
{
	/**
	 * @return the costing method code the schema had before
	 */
	@NonNull
	String updateCostingMethod(@NonNull final AcctSchemaId acctSchemaId, @NonNull final String costingMethodCode)
	{
		final I_C_AcctSchema acctSchema = load(acctSchemaId, I_C_AcctSchema.class);
		final String previousCostingMethod = acctSchema.getCostingMethod();
		acctSchema.setCostingMethod(costingMethodCode);
		saveRecord(acctSchema);
		return previousCostingMethod;
	}

	void activateCostElement(@NonNull final I_M_CostElement costElement)
	{
		costElement.setIsActive(true);
		saveRecord(costElement);
	}

	void deactivateCostElement(final int costElementId)
	{
		final I_M_CostElement costElement = load(costElementId, I_M_CostElement.class);
		costElement.setIsActive(false);
		saveRecord(costElement);
	}
}
