import { unboxAxiosResponse } from './index';
import { trl } from './translations';
import { isError } from 'lodash';
import { Bounce, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as uiTrace from './ui_trace';
import React from 'react';
import PropTypes from 'prop-types';

export const toastErrorFromObj = (obj) => {
  console.log('toastErrorFromObj', { obj });
  if (!obj) {
    // shall not happen
    console.error('toastErrorFromObj called without any error');
  } else if (isError(obj)) {
    toastError({ axiosError: obj });
  } else if (typeof obj === 'object') {
    toastError(obj);
  } else {
    toastError({ plainMessage: `${obj}` });
  }
};

export const toastError = ({ axiosError, messageKey, fallbackMessageKey, plainMessage, context }) => {
  let code;
  let message;
  if (axiosError) {
    message = extractUserFriendlyErrorMessageFromAxiosError({ axiosError, fallbackMessageKey });
  } else if (messageKey) {
    message = trl(messageKey);
    code = messageKey;
  } else if (plainMessage) {
    message = plainMessage;
  } else {
    console.error('toastError called without any error');
    return;
  }

  console.trace('toast error: ', { message, code, axiosError, context });

  toast.error(<ToastMessage message={message} code={code} />, {
    autoClose: 1000 * 60 * 5,
    position: 'bottom-center',
    transition: Bounce,
    bodyStyle: { overflow: 'auto' },
  });

  uiTrace.trace({
    eventName: 'error',
    message,
    context,
  });
};

export const toastNotification = ({ messageKey, plainMessage }) => {
  let message;
  if (messageKey) {
    message = trl(messageKey);
  } else if (plainMessage) {
    message = plainMessage;
  } else {
    console.error('toastNotification called without any message');
    return;
  }

  toast.success(message, {
    position: 'bottom-center',
    transition: Bounce,
  });
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

export const extractErrorResponseFromAxiosError = (axiosError) => {
  if (!axiosError || !axiosError.response || !axiosError.response.data) {
    return undefined;
  }
  return unboxAxiosResponse(axiosError.response);
};

function extractUserFriendlyErrorSingleErrorObject(error) {
  if (!error) {
    // null/empty error message... shall not happen
    return trl('error.PleaseTryAgain');
  }
  if (typeof error === 'object') {
    if (error.userFriendlyError || window.showAllErrorMessages) {
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

const ToastMessage = ({ message, code }) => {
  if (!code) return message;

  return (
    <>
      {message}
      <span className="toast-code" style={{ display: 'none' }}>
        {code}
      </span>
    </>
  );
};
ToastMessage.propTypes = {
  message: PropTypes.string,
  code: PropTypes.string,
};
