import toast from 'react-hot-toast';
import counterpart from 'counterpart';

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
    if (
      axiosError.response.data.endpointResponse &&
      axiosError.response.data.endpointResponse.errors &&
      axiosError.response.data.endpointResponse.errors[0] &&
      axiosError.response.data.endpointResponse.errors[0].message
    ) {
      return axiosError.response.data.endpointResponse.errors[0].message;
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
