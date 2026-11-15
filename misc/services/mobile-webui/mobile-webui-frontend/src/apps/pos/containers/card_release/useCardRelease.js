import { useEffect, useState } from 'react';
import { toastError } from '../../../../utils/toast';

/**
 * The cashier's check / release of a card payment whose result is unknown, in two requests: the first one checks and
 * says whether the cashier must confirm that the card was not charged; confirming releases the payment only if it is
 * still in the status the confirmation was asked for. The confirmation is dropped as soon as the payment leaves that
 * status (e.g. it was approved meanwhile); the caller then shows the new state.
 *
 * @param status the payment's current status
 * @param isReleasable whether the payment may be checked / released (its result is unknown)
 * @param isSubmitting the caller's "a request is running" flag; no release is sent while it is set
 * @param setSubmitting sets that flag
 * @param release ({ confirmNotCharged, expectedStatus }) => Promise of {@code { status, confirmationRequired, refusedMessage }};
 * the caller applies the new payment state itself and returns the payment's status as it is after the request
 */
export const useCardRelease = ({ status, isReleasable, isSubmitting, setSubmitting, release }) => {
  // the status the cashier was asked to confirm "not charged" for; null while no confirmation is asked
  const [confirmationStatus, setConfirmationStatus] = useState(null);
  const [releaseMessage, setReleaseMessage] = useState(null);

  const isConfirmationShown = confirmationStatus != null && isReleasable && status === confirmationStatus;

  useEffect(() => {
    if (confirmationStatus != null && !isConfirmationShown) {
      setConfirmationStatus(null);
    }
  }, [confirmationStatus, isConfirmationShown]);

  const callRelease = (params) => {
    setSubmitting(true);
    release(params)
      .then((response) => {
        // remember the status the confirmation is asked for: the confirm request must refer to exactly that one
        setConfirmationStatus(response.confirmationRequired ? response.status : null);
        setReleaseMessage(response.refusedMessage ?? null);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSubmitting(false));
  };

  return {
    isConfirmationShown,
    releaseMessage,
    onRelease: () => {
      if (isSubmitting || !isReleasable) return;
      callRelease({});
    },
    onConfirmRelease: () => {
      if (isSubmitting || !isConfirmationShown) return;
      // released only if the payment is still in the status the cashier confirmed
      callRelease({ confirmNotCharged: true, expectedStatus: confirmationStatus });
    },
    onCancelConfirmation: () => setConfirmationStatus(null),
    /** Forgets the confirmation and the release message, e.g. when another payment is shown */
    reset: () => {
      setConfirmationStatus(null);
      setReleaseMessage(null);
    },
  };
};
