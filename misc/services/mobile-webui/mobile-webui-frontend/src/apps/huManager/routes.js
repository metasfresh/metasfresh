import HUManagerScreen from './containers/HUManagerScreen';
import HUDisposalScreen from './containers/HUDisposalScreen';
import HUMoveScreen from './containers/HUMoveScreen';
import { AssociateExternalLotNoAction } from './components/AssociateExternalLotNoAction';

export const huManagerLocation = () => '/huManager';
export const huManagerDisposeLocation = () => '/huManager/dispose';
export const huManagerMoveLocation = () => '/huManager/move';
export const huManagerAssignExternalLotNo = () => '/huManager/assignExternalLotNo';

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
    path: huManagerAssignExternalLotNo(),
    Component: AssociateExternalLotNoAction,
  },
];
