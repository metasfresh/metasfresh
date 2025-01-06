import React from 'react';
import { ToastContainer } from 'react-toastify';

const ScreenToaster = () => {
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
      pauseOnHover
      theme="dark"
      style={{ marginBottom: '3rem' }}
    />
  );
};

export default ScreenToaster;
