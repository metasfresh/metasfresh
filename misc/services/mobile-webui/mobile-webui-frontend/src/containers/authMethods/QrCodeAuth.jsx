import React from 'react';
import { useAuth } from '../../hooks/useAuth';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import { toastError } from '../../utils/toast';
import { useMobileNavigation } from '../../hooks/useMobileNavigation';

const QrCodeAuth = () => {
  const history = useMobileNavigation();
  const auth = useAuth();

  const performLogin = ({ scannedBarcode }) => {
    auth
      .qrLogin(scannedBarcode)
      .then(() => history.goToFromLocation())
      .catch((axiosError) => {
        toastError({ axiosError });
      });
  };

  return (
    <div id="qr-code-auth" className="section is-size-5 qr-code-auth">
      <div className="container px-6">
        <BarcodeScannerComponent onResolvedResult={performLogin} />
      </div>
    </div>
  );
};

export default QrCodeAuth;
