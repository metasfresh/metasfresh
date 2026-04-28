import { useDispatch } from 'react-redux';
import { useApplicationLaunchers } from '../../reducers/launchers';
import { populateLaunchersStart } from '../../actions/LauncherActions';
import { toQRCodeObject } from '../../utils/qrCode/hu';

export const useFilterByQRCode = ({ applicationId }) => {
  const dispatch = useDispatch();
  const { filterByQRCode } = useApplicationLaunchers({ applicationId });

  const setFilterByQRCode = (qrCode) => {
    dispatch(populateLaunchersStart({ applicationId, filterByQRCode: toQRCodeObject(qrCode) }));
  };

  return {
    filterByQRCode,
    setFilterByQRCode,
  };
};
