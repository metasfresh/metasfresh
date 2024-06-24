import WFLaunchersScreen from '../containers/wfLaunchersScreen/WFLaunchersScreen';
import WFLaunchersFiltersScreen from '../containers/wfLaunchersScreen/WFLaunchersFiltersScreen';
import WFLaunchersScanBarcodeScreen from '../containers/wfLaunchersScreen/WFLaunchersScanBarcodeScreen';

export const appLaunchersLocation = ({ applicationId }) => `/${applicationId}/launchers`;
export const appLaunchersFilterLocation = ({ applicationId }) => `/${applicationId}/launchers/filters`;
export const appLaunchersBarcodeScannerLocation = ({ applicationId }) =>
  appLaunchersLocation({ applicationId }) + `/scanBarcode`;

export const launchersRoutes = [
  {
    path: appLaunchersLocation({ applicationId: ':applicationId' }),
    Component: WFLaunchersScreen,
  },

  {
    path: appLaunchersFilterLocation({ applicationId: ':applicationId' }),
    Component: WFLaunchersFiltersScreen,
  },

  {
    path: appLaunchersBarcodeScannerLocation({ applicationId: ':applicationId' }),
    Component: WFLaunchersScanBarcodeScreen,
  },
];
