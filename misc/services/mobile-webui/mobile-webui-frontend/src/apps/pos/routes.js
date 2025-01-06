import { APPLICATION_ID } from './constants';
import POSScreen from './containers/POSScreen';

export const posLocation = () => `/${APPLICATION_ID}`;

export const posRoutes = [
  {
    path: posLocation(),
    Component: POSScreen,
  },
];
