import HUManagerScreen from './containers/HUManagerScreen';
import HUDisposalScreen from './containers/HUDisposalScreen';
import HUMoveScreen from './containers/HUMoveScreen';
import HUBulkActionsScreen from './containers/HUBulkActionsScreen';

export const huManagerLocation = () => '/huManager';
export const huManagerDisposeLocation = () => '/huManager/dispose';
export const huManagerMoveLocation = () => '/huManager/move';
export const huManagerBulkActionsLocation = () => '/huManager/bulkActions';

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
    path: huManagerBulkActionsLocation(),
    Component: HUBulkActionsScreen,
  },
];
