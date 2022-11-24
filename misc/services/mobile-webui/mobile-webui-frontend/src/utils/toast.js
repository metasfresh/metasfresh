import toast from 'react-hot-toast';
import counterpart from 'counterpart';
import { unboxAxiosResponse } from './index';

export const toastError = ({ axiosError, messageKey, fallbackMessageKey }) => {
  let message;
  if (axiosError) {
    message = extractUserFriendlyErrorMessageFromAxiosError({ axiosError, fallbackMessageKey });
  } else if (messageKey) {
    message = counterpart.translate(messageKey);
  } else {
    console.error('toastError called without any error');
    return;
  }

  console.log('toast error: ', message);
  toast(message, { type: 'error', style: { color: 'white' } });
};

export const extractUserFriendlyErrorMessageFromAxiosError = ({ axiosError, fallbackMessageKey }) => {
  if (axiosError && axiosError.response && axiosError.response.data) {
    const data = axiosError.response && unboxAxiosResponse(axiosError.response);
    if (data && data.errors && data.errors[0] && data.errors[0].message) {
      const error = data.errors[0];
      if (error.userFriendlyError) {
        return error.message;
      } else {
        // don't scare the user with weird errors. Better show him some generic error.
        return counterpart.translate('general.PleaseTryAgain');
      }
    } else if (axiosError.response.data.error) {
      // usually that the login error case when we get something like { error: "bla bla"}
      return axiosError.response.data.error;
    }
  }

  if (fallbackMessageKey) {
    return counterpart.translate(fallbackMessageKey);
  }

  return counterpart.translate('general.PleaseTryAgain');
};
