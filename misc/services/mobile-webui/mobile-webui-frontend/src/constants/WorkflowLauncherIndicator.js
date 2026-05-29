// note to dev: keep in sync with de.metas.workflow.rest_api.model.WorkflowLauncherIndicator
export const WorkflowLauncherIndicator = Object.freeze({
  STOCK_NOT_AVAILABLE: 'STOCK_NOT_AVAILABLE',
  STOCK_PARTIALLY_AVAILABLE: 'STOCK_PARTIALLY_AVAILABLE',
  STOCK_FULLY_AVAILABLE: 'STOCK_FULLY_AVAILABLE',

  // additional, not present in WorkflowLauncherIndicator.java:
  JOB_ALREADY_STARTED: 'JOB_ALREADY_STARTED',
});
