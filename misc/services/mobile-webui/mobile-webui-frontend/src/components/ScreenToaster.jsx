import React from 'react';
import { ToastContainer, toast } from 'react-toastify';
import { useLocationChange } from '../hooks/useLocationChange';
import { showToastErrorsOnNextScreen } from '../utils/toast';

const ScreenToaster = () => {
  useLocationChange(() => {
    toast.dismiss();
    showToastErrorsOnNextScreen();
  });

  return (
    <ToastContainer
      position="bottom-center"
      autoClose={5000}
      hideProgressBar={true}
      newestOnTop={true}
      closeOnClick
      rtl={false}
      pauseOnFocusLoss
      draggable
      draggableDirection="x"
      draggablePercent={50}
      pauseOnHover
      theme="dark"
      style={{ marginBottom: '3rem' }}
    />
  );
};

export default ScreenToaster;
