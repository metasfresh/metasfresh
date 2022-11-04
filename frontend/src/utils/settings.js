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

const getSettingFromState = (state, name) => {
  return state?.appHandler?.me?.settings?.[name];
};
