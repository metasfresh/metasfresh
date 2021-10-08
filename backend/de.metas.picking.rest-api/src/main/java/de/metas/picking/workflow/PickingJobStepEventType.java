package de.metas.picking.workflow;

import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import org.adempiere.exceptions.AdempiereException;

public enum PickingJobStepEventType
{
	PICK, UNPICK;

	public static PickingJobStepEventType ofJson(JsonPickingStepEvent.EventType json)
	{
		switch (json)
		{
			case PICK:
				return PICK;
			case UNPICK:
				return UNPICK;
			default:
				throw new AdempiereException("Unknown event type: " + json);
		}
	}
}
