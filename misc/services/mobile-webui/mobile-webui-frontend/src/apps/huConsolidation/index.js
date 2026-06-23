import { registerHandler } from '../../reducers/wfProcesses/activityStateHandlers';
import { huConsolidationRoutes } from './routes';
import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { isGraiReady, getHUConsolidationJob } from './reducers';
import * as CompleteStatus from '../../constants/CompleteStatus';

export const APPLICATION_ID_HUConsolidation = 'huConsolidation';
export const COMPONENT_TYPE_huConsolidation_consolidate = 'huConsolidation/consolidate';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID_HUConsolidation,
  routes: huConsolidationRoutes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
};

registerHandler({
  componentType: COMPONENT_TYPE_huConsolidation_consolidate,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.job = fromActivity.componentProps.job;
  },
  computeActivityStatus: ({ draftActivity }) => {
    const job = getHUConsolidationJob({ activity: draftActivity });
    return isGraiReady(job) ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
  },
});
