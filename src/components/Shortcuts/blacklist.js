const mods = [
    'ctrl', // Windows, Linux
    'cmd' // macOS
];

export default Object.assign(...mods.map(mod => ({
    [`${mod}+l`]: 'Focus address bar',
    [`${mod}+t`]: 'New tab',
    [`${mod}+w`]: 'Close tab'
})));
