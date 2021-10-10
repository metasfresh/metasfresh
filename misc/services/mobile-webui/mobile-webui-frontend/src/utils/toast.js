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
  if (!axiosError) {
    return counterpart.translate('general.PleaseTryAgain');
  }
  if (
    axiosError.response &&
    axiosError.response.data &&
    axiosError.response.data.endpointResponse &&
    axiosError.response.data.endpointResponse.errors &&
    axiosError.response.data.endpointResponse.errors[0] &&
    axiosError.response.data.endpointResponse.errors[0].message
  ) {
    return axiosError.response.data.endpointResponse.errors[0].message;
  }

  return counterpart.translate(fallbackMessageKey);
};
