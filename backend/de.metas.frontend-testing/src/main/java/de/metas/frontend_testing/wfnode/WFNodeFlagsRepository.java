package de.metas.frontend_testing.wfnode;

import lombok.NonNull;
import org.compiere.model.I_AD_WF_Node;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Loads and persists the {@code AD_WF_Node} through the model, so the workflow caches are reset (unlike a SQL update).
 */
class WFNodeFlagsRepository
{
	@Nullable
	I_AD_WF_Node getById(final int adWfNodeId)
	{
		return load(adWfNodeId, I_AD_WF_Node.class);
	}

	void save(@NonNull final I_AD_WF_Node node)
	{
		saveRecord(node);
	}
}
