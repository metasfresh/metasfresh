package de.metas.ui.web.calendar.json;

public enum JsonSimulationOptimizerStatusType
{
	STARTED,
	STOPPED,
	;

	public static JsonSimulationOptimizerStatusType ofIsRunningFlag(final boolean isRunning) {return isRunning ? STARTED : STOPPED;}
}
