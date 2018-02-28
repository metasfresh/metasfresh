export default ({ keymap = {}, blacklist = {} } = {}) => {
  const hotkeys = {};

  for (const [name, _hotkey] of Object.entries(keymap)) {
    const hotkey = _hotkey.toUpperCase();

    if (hotkey in blacklist) {
      const reason = blacklist[hotkey];
      const reasonFormatted = reason ? ` (${reason})` : '';

      // eslint-disable-next-line no-console
      console.warn(
        `Key combination "${hotkey}" used by "${name}" is blackliste since it overrides browser behaviour${reasonFormatted}.`
      );

      continue;
    }

    if (hotkeys[hotkey]) {
      // eslint-disable-next-line no-console
      console.warn(
        `"${name}" uses already existing key combination "${hotkey}"`
      );
    }

    const bucket = [];
    hotkeys[hotkey] = bucket;
  }

  return hotkeys;
};
