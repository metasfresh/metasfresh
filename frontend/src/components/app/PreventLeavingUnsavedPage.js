import { useEffect } from 'react';
import { computeSaveStatusFlags } from '../../reducers/windowHandler';
import { useSelector } from 'react-redux';

const PreventLeavingUnsavedPage = () => {
  usePreventLeavingUnsavedPage();
  return null;
};

export default PreventLeavingUnsavedPage;

//
//
//

const usePreventLeavingUnsavedPage = () => {
  const isDocumentNotSaved = useSelector(
    (state) => computeSaveStatusFlags({ state }).isDocumentNotSaved
  );

  const isCypress = navigator.userAgent.includes('Cypress');

  const isWarnOnLeaving = isDocumentNotSaved && !isCypress;

  useEffect(() => {
    const handleBeforeUnload = (event) => {
      const message = 'You have unsaved changes!';
      event.preventDefault();
      event.returnValue = message;
      return message;
    };

    if (isWarnOnLeaving) {
      // try workaround https://github.com/cypress-io/cypress/issues/1235#issuecomment-411839157 for our "hanging" problem
      window.addEventListener('beforeunload', handleBeforeUnload);
    }

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, [isWarnOnLeaving]);
};
