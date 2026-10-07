/**
 * The inputs whose last typed number was refused (see RawWidget.refuseInvalidNumber), until the user types there again.
 * A form or modal checks them before it submits or starts, so that it does not go on with the stored value the refused
 * field shows again, as if the user had confirmed it.
 */
const refusedInputs = new Set();

export const markRefusedNumberInput = (inputElement) => {
  if (inputElement) {
    refusedInputs.add(inputElement);
  }
};

export const unmarkRefusedNumberInput = (inputElement) => {
  refusedInputs.delete(inputElement);
};

/**
 * @param {Element} container e.g. a quick input form or a process modal
 * @returns {boolean} whether the container holds an input whose number was refused
 */
export const hasRefusedNumberInput = (container) => {
  let isFound = false;
  refusedInputs.forEach((inputElement) => {
    if (!inputElement.isConnected) {
      refusedInputs.delete(inputElement); // left the page without being typed into again
    } else if (container?.contains(inputElement)) {
      isFound = true;
    }
  });
  return isFound;
};
