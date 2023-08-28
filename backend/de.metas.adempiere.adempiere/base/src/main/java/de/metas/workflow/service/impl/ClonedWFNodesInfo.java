package de.metas.workflow.service.impl;

import de.metas.workflow.WFNodeId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Workflow;

import javax.annotation.Nullable;
import java.util.HashMap;

class ClonedWFNodesInfo
{
	private static final String DYNATTR_ClonedWFStepsInfo = "ClonedWFStepsInfo";

	public static ClonedWFNodesInfo getOrCreate(I_AD_Workflow targetWorkflow)
	{
		return InterfaceWrapperHelper.computeDynAttributeIfAbsent(targetWorkflow, DYNATTR_ClonedWFStepsInfo, ClonedWFNodesInfo::new);
	}

	@Nullable
	static ClonedWFNodesInfo getOrNull(final I_AD_Workflow targetWorkflow)
	{
		return InterfaceWrapperHelper.getDynAttribute(targetWorkflow, DYNATTR_ClonedWFStepsInfo);
	}

	private final HashMap<WFNodeId, WFNodeId> original2targetWFStepIds = new HashMap<>();

	public void addOriginalToClonedWFStepMapping(@NonNull final WFNodeId originalWFStepId, @NonNull final WFNodeId targetWFStepId)
	{
		original2targetWFStepIds.put(originalWFStepId, targetWFStepId);
	}

	public WFNodeId getTargetWFStepId(@NonNull final WFNodeId originalWFStepId)
	{
		final WFNodeId targetWFStepId = original2targetWFStepIds.get(originalWFStepId);
		if (targetWFStepId == null)
		{
			throw new AdempiereException("No target workflow step found for " + originalWFStepId);
		}
		return targetWFStepId;
	}
}
