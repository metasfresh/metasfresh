import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import history from '../../services/History';
import {
  cancelRedirect,
  confirmRedirect,
  resetRedirect,
  useAttemptedUrl,
  useTargetUrl,
} from '../../reducers/redirect';
import { useSaveStatusFlags } from '../../reducers/windowHandler';
import { useLocation } from 'react-router';
import { isObject } from 'lodash/lang';
import { trl } from '../../utils/locale';
import { markMasterDataAsChanged } from '../../actions/WindowActions';

const isCypress = () => navigator.userAgent.includes('Cypress');

const MSG_DiscardChanges = 'window.discardChanges';

const handleBeforeUnload = (event) => {
  const message = trl(MSG_DiscardChanges);
  event.preventDefault();
  event.returnValue = message;
  return message;
};

const confirmDiscardChanges = () => {
  console.trace('confirmDiscardChanges');
  return window.confirm(trl(MSG_DiscardChanges));
};

const KEY_PreviousPath = 'RedirectHandler_previousPath';

const RedirectHandler = () => {
  const dispatch = useDispatch();
  const currentPath = useLocation()?.pathname;
  const previousPath = sessionStorage.getItem(KEY_PreviousPath);

  const { isDocumentNotSaved } = useSaveStatusFlags();
  const attemptedUrl = useAttemptedUrl();
  const targetUrl = useTargetUrl();
  const isRedirecting = !!attemptedUrl || !!targetUrl;
  const isWarnOnPageUnload =
    isDocumentNotSaved && !isRedirecting && !isCypress();

  // console.log('RedirectHandler', {
  //   attemptedUrl,
  //   targetUrl,
  //   isDocumentNotSaved,
  //   isRedirecting,
  //   isWarnOnPageUnload,
  //   previousPath,
  // });

  //
  // Handle programmatic redirects
  useEffect(() => {
    if (targetUrl) {
      // dispatch(clearMasterData());
      dispatch(markMasterDataAsChanged());

      if (isObject(targetUrl)) {
        if (targetUrl.setWindowLocation) {
          window.location = targetUrl.url;
        } else {
          dispatch(() => history.push(targetUrl.url));
        }
      } else {
        dispatch(() => history.push(targetUrl));
      }

      dispatch(resetRedirect());
    }
  }, [targetUrl, dispatch, history]);

  //
  // Handle manual navigation (browser back/forward, manual URL entry)
  useEffect(() => {
    if (currentPath !== previousPath) {
      sessionStorage.setItem(KEY_PreviousPath, currentPath);
      if (isWarnOnPageUnload && !confirmDiscardChanges()) {
        history.push(previousPath); // Revert back to previous page
      }
    }
  }, [currentPath, isWarnOnPageUnload]);

  //
  // Handle programmatic navigation requests
  useEffect(() => {
    if (attemptedUrl) {
      if (isDocumentNotSaved) {
        confirmDiscardChanges()
          ? dispatch(confirmRedirect())
          : dispatch(cancelRedirect());
      } else {
        dispatch(confirmRedirect());
      }
    }
  }, [attemptedUrl, isDocumentNotSaved, dispatch]);

  //
  // Handle browser tab close, reload, or external navigation
  useEffect(() => {
    if (isWarnOnPageUnload) {
      window.addEventListener('beforeunload', handleBeforeUnload);
    } else {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    }

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, [isWarnOnPageUnload]);

  //
  //

  return null;
};

export default RedirectHandler;
