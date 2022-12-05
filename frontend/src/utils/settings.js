export const getSettingFromStateAsBoolean = (
  state,
  name,
  defaultValue = false
) => {
  const value = getSettingFromState(state, name);
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
  return state?.appHandler?.me?.settings?.[name];
};
