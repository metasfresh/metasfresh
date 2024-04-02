export const getSettingFromStateAsBoolean = (
  state,
  name,
  defaultValue = false
) => {
  const value = getSettingFromState(state, name);
  return toBoolean(value, defaultValue);
};

export const getSettingFromMEAsBoolean = (me, name, defaultValue = false) => {
  const value = getSettingFromME(me, name);
  return toBoolean(value, defaultValue);
};

const toBoolean = (value, defaultValue) => {
  if (value == null) {
    return defaultValue;
  }

  return value === 'Y' || value === true;
};

export const getSettingFromStateAsPositiveInt = (
  state,
  name,
  defaultValue = 0
) => {
  const value = getSettingFromState(state, name);
  return value && value > 0 ? Number(value) : defaultValue;
};

export const getSettingFromState = (state, name) => {
  return getSettingFromME(state?.appHandler?.me, name);
};

const getSettingFromME = (me, name) => {
  return me?.settings?.[name];
};
