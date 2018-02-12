import generateHotkeys from '../../../components/Shortcuts/generateHotkeys';

describe('generateHotkeys', () => {
  it('should return an object', () => {
    const hotkeys = generateHotkeys({});

    expect(typeof hotkeys).toBe('object');
  });

  it('should transform a key map to a map of hotkeys', () => {
    const keymap = {
      COMBO_A: 'CONTROL+A',
      COMBO_B: 'CONTROL+B'
    };

    const hotkeys = generateHotkeys({ keymap });

    expect(hotkeys).toEqual({
      'CONTROL+A': [],
      'CONTROL+B': []
    });
  });

  it('should warn about duplicate keys', () => {
    jest.spyOn(global.console, 'warn');

    const keymap = {
      COMBO_1: 'CONTROL+A',
      COMBO_2: 'CONTROL+A'
    };

    generateHotkeys({ keymap });
    // eslint-disable-next-line no-console
    expect(console.warn).toBeCalled();
  });

  it('should warn about blacklisted hotkeys', () => {
    jest.spyOn(global.console, 'warn');

    const blacklist = {
      'CONTROL+W': 'Close tab'
    };

    const keymap = {
      COMBO_1: 'CONTROL+W'
    };

    generateHotkeys({ keymap, blacklist });
    // eslint-disable-next-line no-console
    expect(console.warn).toBeCalled();
  });
});
