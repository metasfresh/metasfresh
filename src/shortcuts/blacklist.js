const mods = [
    'Control', // Windows, Linux
    'Meta' // macOS
];

export default Object.entries(Object.assign(...mods.map(mod => ({
    [`${mod}+c`]: 'Copy',
    [`${mod}+f`]: 'Search',
    [`${mod}+h`]: 'History',
    [`${mod}+l`]: 'Focus address bar',
    [`${mod}+n`]: 'New window',
    [`${mod}+t`]: 'New tab',
    [`${mod}+v`]: 'Paste',
    [`${mod}+w`]: 'Close tab'
})))).reduce((blacklist, pair) => {
    const [key, value] = pair;

    blacklist[key.toUpperCase()] = value;

    return blacklist;
}, {});
