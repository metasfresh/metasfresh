import { Toaster } from 'react-hot-toast';
import React from 'react';

const ScreenToaster = () => {
  return (
    <Toaster
      position="bottom-center"
      toastOptions={{
        success: {
          style: {
            background: 'green',
          },
        },
        error: {
          style: {
            background: 'red',
          },
        },
      }}
    />
  );
};

export default ScreenToaster;
