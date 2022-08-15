import toast from 'react-hot-toast';
import { unboxAxiosResponse } from './index';
import { trl } from './translations';

export const toastError = ({ axiosError, messageKey, fallbackMessageKey, plainMessage }) => {
  let message;
  if (axiosError) {
    message = extractUserFriendlyErrorMessageFromAxiosError({ axiosError, fallbackMessageKey });
  } else if (messageKey) {
    message = trl(messageKey);
  } else if (plainMessage) {
    message = plainMessage;
  } else {
    console.error('toastError called without any error');
    return;
  }

  console.trace('toast error: ', { message, axiosError });
  toast(message, { type: 'error', style: { color: 'white' } });
};

export const extractUserFriendlyErrorMessageFromAxiosError = ({ axiosError, fallbackMessageKey = null }) => {
  // console.log('extractUserFriendlyErrorMessageFromAxiosError', { axiosError });

  if (axiosError) {
    if (axiosError.request && !axiosError.response) {
      return trl('error.network.noResponse');
    } else if (axiosError.response && axiosError.response.data) {
      const data = axiosError.response && unboxAxiosResponse(axiosError.response);
      if (data && data.errors && data.errors[0] && data.errors[0].message) {
        const error = data.errors[0];
        return extractUserFriendlyErrorSingleErrorObject(error);
      } else if (axiosError.response.data.error) {
        return extractUserFriendlyErrorSingleErrorObject(axiosError.response.data.error);
      }
    }
  }

  if (fallbackMessageKey) {
    return trl(fallbackMessageKey);
  }

  return trl('error.PleaseTryAgain');
};

function extractUserFriendlyErrorSingleErrorObject(error) {
  if (!error) {
    // null/empty error message... shall not happen
    return trl('error.PleaseTryAgain');
  }
  if (typeof error === 'object') {
    if (error.userFriendlyError) {
      return error.message;
    } else {
      // don't scare the user with weird errors. Better show him some generic error.
      return trl('error.PleaseTryAgain');
    }
  } else {
    // assume it's a string
    // usually that the login error case when we get something like { error: "bla bla"}
    return `${error}`;
  }
}
