import { expect } from 'chai';

import _Shortcut from '../../../components/shortcuts/Shortcut';
import _ShortcutProvider from '../../../components/shortcuts/ShortcutProvider';
import _generateHotkeys from '../../../components/shortcuts/generateHotkeys';
import {
  generateHotkeys,
  Shortcut,
  ShortcutProvider,
} from '../../../components/shortcuts/index.js';

describe('shortcuts/index.js', () => {
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
