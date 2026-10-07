import counterpart from 'counterpart';

import { getSessionNumberDelimiters } from './locale';

/**
 * The inputs whose last typed number was refused (see RawWidget.refuseInvalidNumber), with the refused text, until the
 * user types there again. A form or modal checks them before it submits or starts, so that it does not go on with the
 * stored value the refused field shows again, as if the user had confirmed it.
 */
const refusedInputs = new Map();

export const markRefusedNumberInput = (inputElement, refusedText) => {
  if (inputElement) {
    refusedInputs.set(inputElement, refusedText);
  }
};

export const unmarkRefusedNumberInput = (inputElement) => {
  refusedInputs.delete(inputElement);
};

/**
 * @param {Element} container e.g. a quick input form or a process modal
 * @returns {string|null} the refused text of an input in the container, or null when none of its inputs was refused
 */
export const getRefusedNumberText = (container) => {
  let refusedText = null;
  refusedInputs.forEach((text, inputElement) => {
    if (!inputElement.isConnected) {
      refusedInputs.delete(inputElement); // left the page without being typed into again
    } else if (refusedText === null && container?.contains(inputElement)) {
      refusedText = text ?? '';
    }
  });
  return refusedText;
};

/**
 * @summary The notification that tells the user why a typed or pasted text was not taken over as a number. A number may
 *          be typed with either "," or "." as the decimal separator; only genuinely unparseable input (e.g. a letter) is
 *          refused.
 * @param {string} refusedText
 * @returns {{title: string, message: string}}
 */
export const getRefusedNumberNotification = (refusedText) => {
  const { decimal } = getSessionNumberDelimiters();
  const params = {
    text: refusedText,
    decimal,
    example: `1234${decimal}56`,
  };
  return {
    title: counterpart.translate('window.error.invalidNumber.title', {
      fallback: 'Invalid number',
    }),
    message: counterpart.translate('window.error.invalidNumber.description', {
      ...params,
      fallback: `"${refusedText}" is not a valid number: enter digits with "," or "." as the decimal separator (e.g. ${params.example}).`,
    }),
  };
};
