/* eslint-env mocha */
import chai, { expect } from 'chai';
import sinon from 'sinon';
import sinonChai from 'sinon-chai';

chai.use(sinonChai);

import generateHotkeys from './generateHotkeys';

describe('generateHotkeys', () => {
    it('should return an object', () => {
        const hotkeys = generateHotkeys({});

        expect(hotkeys).to.be.an('object');
    });

    it('should transform a key map to a map of hotkeys', () => {
        const keymap = {
            COMBO_A: 'ctrl+a',
            COMBO_B: 'ctrl+b'
        };

        const hotkeys = generateHotkeys(keymap);

        expect(hotkeys).to.deep.equal({
            'ctrl+a': [],
            'ctrl+b': []
        });
    });

    it('should warn about duplicate keys', () => {
        const warn = sinon.spy(console, 'warn');

        try {
            const keymap = {
                COMBO_1: 'ctrl+a',
                COMBO_2: 'ctrl+a'
            };

            generateHotkeys(keymap);

            expect(warn).to.have.been.called;
        } catch (error) {
            throw error;
        } finally {
            warn.restore();
        }
    });
});
