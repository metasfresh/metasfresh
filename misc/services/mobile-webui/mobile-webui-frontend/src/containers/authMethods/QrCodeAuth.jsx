import React, { useEffect } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import { toastError } from '../../utils/toast';

const QrCodeAuth = () => {
  const history = useHistory();
  const auth = useAuth();
  const location = useLocation();
  const { from } = location.state || { from: { pathname: '/' } };

  useEffect(() => {
    if (auth.isLoggedIn()) {
      console.log(`LoginScreen: ALREADY LOGGED IN. Forwarding to `, from);
      history.replace(from);
    }
  }, []);

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
