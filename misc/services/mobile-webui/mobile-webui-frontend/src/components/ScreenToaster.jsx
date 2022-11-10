import React, { useEffect } from 'react';
import toast, { Toaster, useToasterStore } from 'react-hot-toast';

const TOASTS_LIMIT = 1;

const ScreenToaster = () => {
  //
  // Limit the number of toasts displayed on screen
  // (thx to https://github.com/timolins/react-hot-toast/issues/31#issuecomment-803359550)
  if (TOASTS_LIMIT > 0) {
    const { toasts } = useToasterStore();
    useEffect(() => {
      toasts
        .filter((t) => t.visible)
        .filter((_, i) => i >= TOASTS_LIMIT)
        .forEach((t) => toast.dismiss(t.id));
    }, [toasts]);
  }

  return (
    <Toaster
      position="bottom-center"
      containerClassName="app-toaster"
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
