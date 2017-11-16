/* eslint-env mocha */
import chai, { expect } from 'chai';
import { stub } from 'sinon';
import sinonChai from 'sinon-chai';
import { Component } from 'react';
import ShortcutProvider from './ShortcutProvider';

chai.use(sinonChai);

describe('Shortcuts', () => {
    it('should be a React component', () => {
        expect(ShortcutProvider).to.be.an.instanceOf(Component.constructor);
    });

    it('should return children', () => {
        const shortcutProvider = new ShortcutProvider;

        const children = {};

        shortcutProvider.props = {
            children
        };

        expect(shortcutProvider.render()).to.equal(children);
    });

    it('should expose context', () => {
        const shortcutProvider = new ShortcutProvider;

        const context = shortcutProvider.getChildContext();

        expect(context).to.deep.equal({
            subscribe: shortcutProvider.subscribe,
            unsubscribe: shortcutProvider.unsubscribe
        });
    });

    describe('register', () => {
        it('should be able to register hotkeys', () => {
            const shortcutProvider = new ShortcutProvider;

            const hotkeys = {};

            shortcutProvider.register(hotkeys);

            expect(shortcutProvider.hotkeys).to.equal(hotkeys);
        });
    });

    describe('subscribe', () => {
        it('should be able to subscribe to hotkeys', () => {
            const shortcutProvider = new ShortcutProvider;

            const shortcut = 'ctrl+1';

            const hotkeys = {
                [shortcut]: []
            };

            shortcutProvider.register(hotkeys);

            const handler = () => {};

            shortcutProvider.subscribe(shortcut, handler);

            expect(shortcutProvider.hotkeys[shortcut]).to.include(handler);
        });

        it('should warn when trying to subscribe to a not defined shortcut', () => {
            const warn = stub(console, 'warn');

            try {
                const shortcutProvider = new ShortcutProvider;

                const hotkeys = {};

                shortcutProvider.register(hotkeys);

                const handler = () => {};

                shortcutProvider.subscribe('NOT_DEFINED', handler);

                expect(warn).to.have.been.called;
            } catch (error) {
                throw error;
            } finally {
                warn.restore();
            }
        });
    });

    describe('unsubscribe', () => {
        it('should be able to unsubscribe from hotkeys', () => {
            const shortcutProvider = new ShortcutProvider;

            const shortcut = 'ctrl+1';
            const handler = () => {};

            shortcutProvider.hotkeys = {
                [shortcut]: [ handler ]
            };

            shortcutProvider.unsubscribe(shortcut, handler);

            expect(shortcutProvider.hotkeys[shortcut]).to.not.include(handler);
        });

        it('should not modify other handlers when unsubscribing', () => {
            const shortcutProvider = new ShortcutProvider;

            const shortcut = 'ctrl+1';

            const handler1 = () => {};
            const handler2 = () => {};
            const handler3 = () => {};

            shortcutProvider.hotkeys = {
                [shortcut]: [ handler1, handler2, handler3 ]
            };

            shortcutProvider.unsubscribe(shortcut, handler2);

            expect(shortcutProvider.hotkeys[shortcut]).to.deep.equal([
                handler1, handler3
            ]);
        });

        it('should warn when trying to unsubscribe from a not defined hotkey', () => {
            const warn = stub(console, 'warn');

            try {
                const shortcutProvider = new ShortcutProvider;

                shortcutProvider.hotkeys = {};

                const handler = () => {};

                shortcutProvider.unsubscribe('NOT_DEFINED', handler);

                expect(warn).to.have.been.called;
            } catch (error) {
                throw error;
            } finally {
                warn.restore();
            }
        });

        it('should warn when trying to unsubscribe a not subscribed handler', () => {
            const warn = stub(console, 'warn');

            try {
                const shortcutProvider = new ShortcutProvider;

                const shortcut = 'ctrl+1';

                shortcutProvider.hotkeys = {
                    [shortcut]: []
                };

                const handler = () => {};

                shortcutProvider.unsubscribe(shortcut, handler);

                expect(warn).to.have.been.called;
            } catch (error) {
                throw error;
            } finally {
                warn.restore();
            }
        });
    });
});
