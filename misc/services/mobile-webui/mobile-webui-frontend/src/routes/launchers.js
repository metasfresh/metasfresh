import WFLaunchersScreen from '../containers/wfLaunchersScreen/WFLaunchersScreen';
import WFLaunchersScanBarcodeScreen from '../containers/wfLaunchersScreen/WFLaunchersScanBarcodeScreen';
import { push } from 'connected-react-router';

export function gotoAppLaunchers(applicationId) {
  return (dispatch) => {
    dispatch(push(appLaunchersLocation({ applicationId })));
  };
}

export function gotoAppLaunchersBarcodeScanner(applicationId) {
  return (dispatch) => {
    dispatch(push(appLaunchersBarcodeScannerLocation({ applicationId })));
  };
}

const appLaunchersLocation = ({ applicationId }) => `/${applicationId}/launchers`;
const appLaunchersBarcodeScannerLocation = ({ applicationId }) =>
  appLaunchersLocation({ applicationId }) + `/scanBarcode`;

export const launchersRoutes = [
  {
    path: appLaunchersLocation({ applicationId: ':applicationId' }),
    Component: WFLaunchersScreen,
  },

  {
    path: appLaunchersBarcodeScannerLocation({ applicationId: ':applicationId' }),
    Component: WFLaunchersScanBarcodeScreen,
  },
];
