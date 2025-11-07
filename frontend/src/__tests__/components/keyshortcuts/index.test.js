import _Shortcut from '../../../components/keyshortcuts/Shortcut';
import _ShortcutProvider from '../../../components/keyshortcuts/ShortcutProvider';
import {
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
});
