import { unboxAxiosResponse } from './index';
import { trl } from './translations';
import { isError } from 'lodash';
import { Bounce, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as uiTrace from './ui_trace';
import React from 'react';
import PropTypes from 'prop-types';
import { ContextualError } from './ContextualError';

export const toastErrorFromObj = (obj) => {
  console.log('toastErrorFromObj', { obj });
  if (!obj) {
    // shall not happen
    console.error('toastErrorFromObj called without any error');
  } else if (isError(obj) || obj instanceof ContextualError) {
    toastError({ axiosError: obj, context: obj?.context });
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
    code = extractErrorCodeFromAxiosError(axiosError);
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
    errorCode: code,
    exception: axiosError ? errorToString(axiosError) : null,
    callstack: new Error().stack,
    context,
  });
};

export const extractErrorCodeFromAxiosError = (axiosError) => {
  const responseData = axiosError?.response && unboxAxiosResponse(axiosError.response);
  if (responseData?.errors?.[0]?.errorCode) {
    return responseData.errors[0].errorCode;
  } else if (axiosError?.response?.data?.error?.errorCode) {
    return axiosError.response.data.error.errorCode;
  }
  return null;
};

export const extractUserFriendlyErrorMessageFromAxiosError = ({ axiosError, fallbackMessageKey = null }) => {
  // console.log('extractUserFriendlyErrorMessageFromAxiosError', { axiosError });

  // Read the UI trace ID from the request config (not from uiTrace.getCurrentTraceId()) because the trace
  // context is cleaned up before the .catch() handler fires, so the live axios default header is already gone.
  // This UUID matches UI_Trace.ExternalId in the metasfresh WebUI trace window; from there support can
  // navigate to the linked API Request Audit record for the full request/response details.
  const traceId = axiosError?.config?.headers?.['X-Ui-Trace-Id'];

  if (axiosError) {
    if (axiosError.request && !axiosError.response) {
      return trl('error.network.noResponse');
    } else if (axiosError.response && axiosError.response.data) {
      const data = axiosError.response && unboxAxiosResponse(axiosError.response);
      if (data && data.errors && data.errors[0] && data.errors[0].message) {
        const error = data.errors[0];
        return extractUserFriendlyErrorSingleErrorObject({ error, fallbackMessageKey, traceId });
      } else if (axiosError.response.data.error) {
        return extractUserFriendlyErrorSingleErrorObject({
          error: axiosError.response.data.error,
          fallbackMessageKey,
          traceId,
        });
      }
    } else if (axiosError.message) {
      return extractUserFriendlyErrorSingleErrorObject({ error: axiosError, fallbackMessageKey, traceId });
    }
  }

  if (fallbackMessageKey) {
    return trl(fallbackMessageKey);
  }

  return trl('error.PleaseTryAgain');
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

export const extractErrorResponseFromAxiosError = (axiosError) => {
  if (!axiosError || !axiosError.response || !axiosError.response.data) {
    return undefined;
  }
  return unboxAxiosResponse(axiosError.response);
};

function extractUserFriendlyErrorSingleErrorObject({ error, fallbackMessageKey, traceId }) {
  if (!error) {
    // null/empty error message... shall not happen
    return trl(fallbackMessageKey ?? 'error.PleaseTryAgain');
  }
  if (typeof error === 'object') {
    if (error.userFriendlyError || window.showAllErrorMessages) {
      return error.message;
    } else {
      // Non-user-friendly error: show generic message with UI trace ID so users can report it to support.
      // The trace ID matches UI_Trace.ExternalId in the metasfresh WebUI trace window.
      return trl(fallbackMessageKey ?? 'error.InternalError', { traceId: traceId ?? '-' });
    }
  } else {
    // assume it's a string
    // usually that the login error case when we get something like { error: "bla bla"}
    return `${error}`;
  }
}

export const errorToString = (error) => {
  if (!error) {
    return null;
  } else if (error instanceof Error) {
    return `${error.name}: ${error.message}\n${error.stack}`;
  } else {
    // Handle scenarios where the input might not be a proper Error object
    return String(error);
  }
};

export const is404 = (axiosError) => {
  return axiosError?.response?.status === 404;
};

//
//
//
//
//
//
//

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
