/* eslint-env mocha */
import { expect } from 'chai';
import { ShortcutProvider, Shortcut, generateHotkeys } from './index.js';
import _ShortcutProvider from './ShortcutProvider';
import _Shortcut from './Shortcut';
import _generateHotkeys from './generateHotkeys';

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
