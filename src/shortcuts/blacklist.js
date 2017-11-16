const mods = [
    'Control', // Windows, Linux
    'Meta' // macOS
];

export default Object.entries(Object.assign(...mods.map(mod => ({
    [`${mod}+l`]: 'Focus address bar',
    [`${mod}+t`]: 'New tab',
    [`${mod}+w`]: 'Close tab'
})))).reduce((blacklist, pair) => {
    const [key, value] = pair;

    blacklist[key.toUpperCase()] = value;

    return blacklist;
}, {});
