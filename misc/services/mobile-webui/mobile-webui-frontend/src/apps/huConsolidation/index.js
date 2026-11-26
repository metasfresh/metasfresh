import React from 'react';
import { registerHandler } from '../../reducers/wfProcesses/activityStateHandlers';
import * as CompleteStatus from '../../constants/CompleteStatus';
import HUConsolidationCompleteActivity from './activities/HUConsolidationCompleteActivity';
import { huConsolidationRoutes } from './routes';
import { mergeJobIntoActivityDataStored } from './reducers';
import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';

export const APPLICATION_ID_HUConsolidation = 'huConsolidation';
export const COMPONENT_TYPE_huConsolidation_consolidate = 'huConsolidation/consolidate';
export const COMPONENT_TYPE_huConsolidation_complete = 'huConsolidation/complete';

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
    mergeJobIntoActivityDataStored({ draftActivityDataStored, job: fromActivity.componentProps.job });
  },
});

registerHandler({
  componentType: COMPONENT_TYPE_huConsolidation_complete,
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? true;
    draftActivityDataStored.confirmed = !!fromActivity.componentProps.confirmed;
    draftActivityDataStored.completeStatus = draftActivityDataStored.confirmed
      ? CompleteStatus.COMPLETED
      : CompleteStatus.NOT_STARTED;
  },
  uiComponentFactory: function uiComponentFactory({
    applicationId,
    wfProcessId,
    activityId,
    activityState,
    isLastActivity,
  }) {
    return (
      <HUConsolidationCompleteActivity
        key={activityId}
        applicationId={applicationId}
        wfProcessId={wfProcessId}
        activityId={activityId}
        activityState={activityState}
        isLastActivity={isLastActivity}
      />
    );
  },
});
