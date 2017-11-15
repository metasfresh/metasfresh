export default ({ keymap = {}, blacklist = {} } = {}) => {
    const hotkeys = {};

    for (const [name, hotkey] of Object.entries(keymap)) {
        if (hotkey in blacklist) {
            const reason = blacklist[hotkey];

            // eslint-disable-next-line max-len
            console.warn(`Key combination "${hotkey}" used by "${name}" is blacklisted since it overrides browser behaviour${reason && ` (${reason})`}.`);

            continue;
        }

        if (hotkeys[hotkey]) {
            // eslint-disable-next-line max-len
            console.warn(`There exist multiple definition for key combination "${hotkey}"`);
        }

        const bucket = [];
        hotkeys[hotkey] = bucket;
    }

    return hotkeys;
};
