const mods = [
  'Control', // Windows, Linux
  'Meta', // macOS
];

export default Object.entries({
  'Alt+Spacebar': 'Windows window menu',
  ...Object.assign(
    ...mods.map(mod => ({
      [`${mod}+C`]: 'Copy',
      [`${mod}+F`]: 'Search',
      [`${mod}+H`]: 'History',
      [`${mod}+L`]: 'Focus address bar',
      [`${mod}+N`]: 'New window',
      [`${mod}+T`]: 'New tab',
      [`${mod}+V`]: 'Paste',
      [`${mod}+W`]: 'Close tab',
    }))
  ),
}).reduce((blacklist, pair) => {
  const [key, value] = pair;

  blacklist[key.toUpperCase()] = value;

  return blacklist;
}, {});
