import { useDispatch, useSelector } from 'react-redux';
import { useWebsocket } from './useWebsocket';
import { userSessionUpdate } from '../actions/AppActions';
import { initNumeralLocales, setCurrentActiveLocale } from '../utils/locale';

export const useSessionWebsocket = ({ isLoggedIn }) => {
  const dispatch = useDispatch();
  const topic = useSelector((state) => state.appHandler.me.websocketEndpoint);

  useWebsocket({
    topic: isLoggedIn ? topic : null,
    onMessage: ({ event }) => onSessionChangedEvent({ event, dispatch }),
  });
};

const onSessionChangedEvent = ({ event, dispatch }) => {
  dispatch(userSessionUpdate(event));

  const languageKey = event.language?.key;
  if (languageKey) {
    setCurrentActiveLocale(languageKey);

    if (event.locale) {
      initNumeralLocales(languageKey, event.locale);
    }
  }
};
