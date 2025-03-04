import HUManagerScreen from './containers/HUManagerScreen';
import HUDisposalScreen from './containers/HUDisposalScreen';
import HUMoveScreen from './containers/HUMoveScreen';
import HUPrintingOptionsScreen from './containers/HUPrintingOptionsScreen';
import { APPLICATION_ID } from './constants';

export const huManagerLocation = () => `/${APPLICATION_ID}`;
export const huManagerDisposeLocation = () => `/${APPLICATION_ID}/dispose`;
export const huManagerMoveLocation = () => `/${APPLICATION_ID}/move`;
export const huManagerHuLabelsLocation = () => `/${APPLICATION_ID}/huLabels`;

export const huManagerRoutes = [
  {
    path: huManagerLocation(),
    Component: HUManagerScreen,
  },
  {
    path: huManagerDisposeLocation(),
    Component: HUDisposalScreen,
  },
  {
    path: huManagerMoveLocation(),
    Component: HUMoveScreen,
  },
  {
    path: huManagerHuLabelsLocation(),
    Component: HUPrintingOptionsScreen,
  },
];
