import InventoryDisposalScreen from './containers/InventoryDisposalScreen';
import { push } from 'connected-react-router';

import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';

export const applicationDescriptor = {
  applicationId: 'inventoryDisposal',

  routes: [
    {
      path: '/inventoryDisposal',
      Component: InventoryDisposalScreen,
    },
  ],

  messages: {
    en: messages_en,
    de: messages_de,
  },

  startApplication: () => {
    return (dispatch) => {
      dispatch(push('/inventoryDisposal'));
    };
  },
};
