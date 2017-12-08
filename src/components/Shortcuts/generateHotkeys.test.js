/* eslint-env mocha */
import chai, { expect } from 'chai';
import { stub } from 'sinon';
import sinonChai from 'sinon-chai';

import generateHotkeys from './generateHotkeys';

chai.use(sinonChai);

describe('generateHotkeys', () => {
    it('should return an object', () => {
        const hotkeys = generateHotkeys({});

        expect(hotkeys).to.be.an('object');
    });

    it('should transform a key map to a map of hotkeys', () => {
        const keymap = {
            COMBO_A: 'CONTROL+A',
            COMBO_B: 'CONTROL+B'
        };

        const hotkeys = generateHotkeys({ keymap });

        expect(hotkeys).to.deep.equal({
            'CONTROL+A': [],
            'CONTROL+B': []
        });
    });

    it('should warn about duplicate keys', () => {
        const warn = stub(console, 'warn');

        try {
            const keymap = {
                COMBO_1: 'CONTROL+A',
                COMBO_2: 'CONTROL+A'
            };

            generateHotkeys({ keymap });

            expect(warn).to.have.been.called;
        } catch (error) {
            throw error;
        } finally {
            warn.restore();
        }
    });

    it('should warn about blacklisted hotkeys', () => {
        const warn = stub(console, 'warn');

        try {
            const blacklist = {
                'CONTROL+W': 'Close tab'
            };

            const keymap = {
                COMBO_1: 'CONTROL+W'
            };

            generateHotkeys({ keymap, blacklist });

            expect(warn).to.have.been.called;
        } catch (error) {
            throw error;
        } finally {
            warn.restore();
        }
    });
});