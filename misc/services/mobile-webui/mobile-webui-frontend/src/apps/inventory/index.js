import { registerHandler } from '../../reducers/wfProcesses/activityStateHandlers';
import { inventoryRoutes } from './routes';
import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';

export const APPLICATION_ID_Inventory = 'inventory';
export const COMPONENT_TYPE_inventory = 'inventory/inventory';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID_Inventory,
  routes: inventoryRoutes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
};

registerHandler({
  componentType: COMPONENT_TYPE_inventory,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.job = fromActivity.componentProps.job;
  },
});
