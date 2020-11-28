import _Shortcut from '../../../components/keyshortcuts/Shortcut';
import _ShortcutProvider from '../../../components/keyshortcuts/ShortcutProvider';
import _generateHotkeys from '../../../components/keyshortcuts/generateHotkeys';
import {
  generateHotkeys,
  Shortcut,
  ShortcutProvider,
} from '../../../components/keyshortcuts/index.js';

describe('keyshortcuts/index.js', () => {
  it('should export ShortcutProvider', () => {
    expect(ShortcutProvider).toEqual(_ShortcutProvider);
  });

  it('should export Shortcut', () => {
    expect(Shortcut).toEqual(_Shortcut);
  });

  it('should export generateHotkeys', () => {
    expect(generateHotkeys).toEqual(_generateHotkeys);
  });
});
