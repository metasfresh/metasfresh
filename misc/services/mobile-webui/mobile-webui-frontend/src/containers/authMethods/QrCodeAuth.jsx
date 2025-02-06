import React from 'react';
import { useLocation } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import { toastError } from '../../utils/toast';
import { useMobileNavigation } from '../../hooks/useMobileNavigation';

const QrCodeAuth = () => {
  const history = useMobileNavigation();
  const auth = useAuth();
  const location = useLocation();
  const { from } = location.state || { from: { pathname: '/' } };

  const performLogin = ({ scannedBarcode }) => {
    auth
      .qrLogin(scannedBarcode)
      .then(() => history.replace(from))
      .catch((axiosError) => {
        toastError({ axiosError });
      });
  };

  return (
    <div className="section is-size-5">
      <div className="container px-6">
        <BarcodeScannerComponent onResolvedResult={performLogin} />
      </div>
    </div>
  );
};

export default QrCodeAuth;
