package de.metas.workflow.rest_api.model;

public enum WFActivityStatus
{
	NOT_STARTED,
	IN_PROGRESS,
	COMPLETED;

	public boolean isCompleted() {return this.equals(COMPLETED);}
}
