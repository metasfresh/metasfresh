import { expect } from 'chai';

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
    expect(ShortcutProvider).to.equal(_ShortcutProvider);
  });

  it('should export Shortcut', () => {
    expect(Shortcut).to.equal(_Shortcut);
  });

  it('should export generateHotkeys', () => {
    expect(generateHotkeys).to.equal(_generateHotkeys);
  });
});
