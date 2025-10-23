import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { appLocation, routes } from './routes';
import { APPLICATION_ID } from './constants';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID,
  routes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
  startApplication: ({ history }) => {
    history.push(appLocation());
  },
};
