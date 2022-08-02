import WFLaunchersScreen from '../containers/wfLaunchersScreen/WFLaunchersScreen';
import WFLaunchersScanBarcodeScreen from '../containers/wfLaunchersScreen/WFLaunchersScanBarcodeScreen';

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
