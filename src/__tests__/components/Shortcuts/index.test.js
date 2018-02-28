import { expect } from 'chai';

import _Shortcut from '../../../components/Shortcuts/Shortcut';
import _ShortcutProvider from '../../../components/Shortcuts/ShortcutProvider';
import _generateHotkeys from '../../../components/Shortcuts/generateHotkeys';
import {
  generateHotkeys,
  Shortcut,
  ShortcutProvider,
} from '../../../components/Shortcuts/index.js';

describe('Shortcuts/index.js', () => {
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
