package de.metas.frontend_testing.wfnode;

import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_WF_Node;

/**
 * Sets {@code AD_WF_Node.IsActive} / {@code PP_AlwaysAvailableToUser} through the model, so the workflow caches are reset
 * (unlike a SQL update), and returns the previous values so a spec can restore them.
 */
@Builder
public class SetWFNodeFlagsCommand
{
	@NonNull private final JsonSetWFNodeFlagsRequest request;

	public JsonSetWFNodeFlagsResponse execute()
	{
		final I_AD_WF_Node node = InterfaceWrapperHelper.load(request.getAdWfNodeId(), I_AD_WF_Node.class);
		if (node == null)
		{
			throw new AdempiereException("AD_WF_Node not found: " + request.getAdWfNodeId());
		}

		final JsonSetWFNodeFlagsResponse previous = JsonSetWFNodeFlagsResponse.builder()
				.adWfNodeId(node.getAD_WF_Node_ID())
				.isActive(node.isActive())
				.alwaysAvailableToUser(node.getPP_AlwaysAvailableToUser())
				.build();

		if (request.getIsActive() != null)
		{
			node.setIsActive(request.getIsActive());
		}
		if (request.getAlwaysAvailableToUser() != null)
		{
			node.setPP_AlwaysAvailableToUser(StringUtils.trimBlankToNull(request.getAlwaysAvailableToUser()));
		}
		InterfaceWrapperHelper.saveRecord(node);

		return previous;
	}
}
