export default keymap => {
    const hotkeys = {};

    for (const hotkey of Object.values(keymap)) {
        if (hotkeys[hotkey]) {
            // eslint-disable-next-line max-len
            console.warn(`There exist multiple definition for key combination "${hotkey}"`);
        }

        const bucket = [];
        hotkeys[hotkey] = bucket;
    }

    return hotkeys;
};
