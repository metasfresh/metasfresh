import { useSelector } from 'react-redux';

const SETTING_PUT = 'settings/put';

export const putSettingsAction = (map) => {
  return {
    type: SETTING_PUT,
    payload: map,
  };
};

export const useBooleanSetting = (name, defaultIfNotFound = false) => {
  const value = useSetting(name);
  if (value === 'Y' || value === true) {
    return true;
  } else if (value === 'N' || value === false) {
    return false;
  } else {
    if (typeof defaultIfNotFound === 'function') {
      // noinspection JSValidateTypes
      return defaultIfNotFound();
    } else {
      return defaultIfNotFound;
    }
  }
};

export const usePositiveNumberSetting = (name, defaultValue) => {
  const value = useSetting(name);
  return value && value > 0 ? Number(value) : defaultValue;
};

export const useNumber = (name, defaultValue) => {
  const value = useSetting(name);
  return value != null ? Number(value) : defaultValue;
};

export const useSetting = (name) => {
  return useSelector((state) => getSettingFromState(state, name));
};

// True once the backend settings response has been stored (SETTING_PUT dispatched). Lets a
// component tell "settings not fetched yet" (all values undefined → hook defaults) apart from
// "settings loaded, this key is genuinely absent" — needed to adopt a settings-derived default
// exactly once, when settings first arrive.
export const useIsSettingsLoaded = () => {
  return useSelector((state) => state?.settings?.backend != null);
};

const getSettingFromState = (state, name) => {
  //console.log('getSettingFromState', { name, value, state });
  return state?.settings?.backend?.[name];
};

export const reducer = (state = {}, action) => {
  const { type, payload } = action;
  switch (type) {
    case SETTING_PUT: {
      return {
        ...state,
        backend: payload,
      };
    }
    default: {
      return state;
    }
  }
};
