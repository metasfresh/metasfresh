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
 * @param {Element} container e.g. a quick input form or a process modal
 * @returns {boolean} whether the container holds an input whose number was refused
 */
export const hasRefusedNumberInput = (container) =>
  getRefusedNumberText(container) !== null;

/**
 * @summary The notification that tells the user why a typed or pasted number was not taken over, the session way
 * @param {string} refusedText
 * @returns {{title: string, message: string}}
 */
export const getRefusedNumberNotification = (refusedText) => {
  const { decimal, thousands } = getSessionNumberDelimiters();
  const params = {
    text: refusedText,
    decimal,
    grouping: thousands,
    example: `1${thousands}234${decimal}56`,
  };
  return {
    title: counterpart.translate('window.error.invalidNumber.title', {
      fallback: 'Invalid number',
    }),
    message: counterpart.translate('window.error.invalidNumber.description', {
      ...params,
      fallback: `"${refusedText}" was not taken over: the decimal separator is "${decimal}", "${thousands}" is allowed only to group thousands in groups of three (e.g. ${params.example}).`,
    }),
  };
};
