import { registerHandler } from '../../reducers/wfProcesses/activityStateHandlers';
import { huConsolidationRoutes } from './routes';

export const APPLICATION_ID_HUConsolidation = 'huConsolidation';
export const COMPONENT_TYPE_huConsolidation_consolidate = 'huConsolidation/consolidate';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID_HUConsolidation,
  routes: huConsolidationRoutes,
};

registerHandler({
  componentType: COMPONENT_TYPE_huConsolidation_consolidate,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.job = fromActivity.componentProps.job;
  },
});
