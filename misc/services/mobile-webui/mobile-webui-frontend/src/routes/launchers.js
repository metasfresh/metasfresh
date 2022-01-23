import WFLaunchersScreen from '../containers/wfLaunchersScreen/WFLaunchersScreen';
import WFLaunchersScanBarcodeScreen from '../containers/wfLaunchersScreen/WFLaunchersScanBarcodeScreen';
import { push } from 'connected-react-router';

export function gotoAppLaunchers(applicationId) {
  return (dispatch) => {
    dispatch(push(appLaunchersLocation({ applicationId })));
  };
}

export const appLaunchersLocation = ({ applicationId }) => `/${applicationId}/launchers`;
export const appLaunchersBarcodeScannerLocation = ({ applicationId }) =>
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
