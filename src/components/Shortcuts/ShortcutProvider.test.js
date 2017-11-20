/* eslint-env mocha */
import chai, { expect } from 'chai';
import { spy, stub } from 'sinon';
import sinonChai from 'sinon-chai';
import { Component } from 'react';
import ShortcutProvider from './ShortcutProvider';

chai.use(sinonChai);

describe('ShortcutProvider', () => {
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

    it('should clean up event listeners', () => {
        const listeners = [];

        const listenerAPI = {
            addEventListener: (event, handler) => {
                listeners.push([event, handler]);
            },
            removeEventListener: (event, handler) => {
                const indices = [];
                let i = 0;

                for (const listener of listeners) {
                    if (
                        listener[0] === event &&
                        listener[1] === handler
                    ) {
                        indices.push(i);
                    }

                    i++;
                }

                for (const index of indices.reverse()) {
                    listeners.splice(index, 1);
                }
            }
        };

        global.document = listenerAPI;
        global.window = listenerAPI;

        try {
            const shortcutProvider = new ShortcutProvider;

            shortcutProvider.componentWillMount();

            shortcutProvider.componentWillUnmount();

            expect(listeners).to.have.length(0);
        } catch (error) {
            throw error;
        } finally {
            delete global.document;
            delete global.window;
        }
    });

    it('should expose context', () => {
        const shortcutProvider = new ShortcutProvider;

        const context = shortcutProvider.getChildContext();

        expect(context).to.deep.equal({
            shortcuts: {
                subscribe: shortcutProvider.subscribe,
                unsubscribe: shortcutProvider.unsubscribe
            }
        });
    });

    describe('subscribe', () => {
        it('should be able to subscribe to hotkeys', () => {
            const shortcutProvider = new ShortcutProvider;

            const name = 'FOO';
            const shortcut = 'CONTROL+1';

            const hotkeys = {
                [shortcut]: []
            };

            const keymap = {
                [name]: shortcut
            };

            shortcutProvider.props = { hotkeys, keymap };

            const handler = () => {};

            shortcutProvider.subscribe(name, handler);

            expect(shortcutProvider.props.hotkeys[shortcut]).to.include(handler);
        });

        it('should warn when trying to subscribe to a not defined shortcut', () => {
            const warn = stub(console, 'warn');

            try {
                const shortcutProvider = new ShortcutProvider;

                const hotkeys = {};
                const keymap = {}

                shortcutProvider.props = { hotkeys, keymap };

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

            const name = 'FOO';
            const shortcut = 'CONTROL+1';
            const handler = () => {};

            shortcutProvider.props = {
                hotkeys: {
                    [shortcut]: [ handler ]
                },
                keymap: {
                    [name]: shortcut
                }
            };

            shortcutProvider.unsubscribe(name, handler);

            expect(shortcutProvider.props.hotkeys[shortcut]).to.not.include(handler);
        });

        it('should not modify other handlers when unsubscribing', () => {
            const shortcutProvider = new ShortcutProvider;

            const name = 'FOO';
            const shortcut = 'CONTROL+1';

            const handler1 = () => {};
            const handler2 = () => {};
            const handler3 = () => {};

            shortcutProvider.props = {
                hotkeys: {
                    [shortcut]: [ handler1, handler2, handler3 ]
                },
                keymap: {
                    [name]: shortcut
                }
            };

            shortcutProvider.unsubscribe(name, handler2);

            expect(shortcutProvider.props.hotkeys[shortcut]).to.deep.equal([
                handler1, handler3
            ]);
        });

        it('should warn when trying to unsubscribe from a not defined hotkey', () => {
            const warn = stub(console, 'warn');

            try {
                const shortcutProvider = new ShortcutProvider;

                shortcutProvider.props = { hotkeys: {}, keymap: {} };

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

                const name = 'FOO';
                const shortcut = 'CONTROL+1';

                shortcutProvider.props = {
                    hotkeys:  {
                        [shortcut]: []
                    },
                    keymap: {
                        [name]: shortcut
                    }
                };

                const handler = () => {};

                shortcutProvider.unsubscribe(name, handler);

                expect(warn).to.have.been.called;
            } catch (error) {
                throw error;
            } finally {
                warn.restore();
            }
        });
    });

    describe('handleKeyDown', () => {
        it('should not fire multiple times for a single key', () => {
            const shortcutProvider = new ShortcutProvider;
            shortcutProvider.props = { hotkeys: {} };

            const key = 'A';

            expect(shortcutProvider.fired[key]).to.equal(undefined);

            shortcutProvider.handleKeyDown({ key });

            expect(shortcutProvider.fired[key]).to.equal(true);
            expect(shortcutProvider.keySequence).to.deep.equal(['A']);

            shortcutProvider.handleKeyDown({ key });

            expect(shortcutProvider.fired[key]).to.equal(true);
            expect(shortcutProvider.keySequence).to.deep.equal(['A']);
        });

        it('should handle differently capitalized keys the same', () => {
            const shortcutProvider = new ShortcutProvider;
            shortcutProvider.props = { hotkeys: {} };

            const keyLowerCase = 'a';
            const keyUpperCase = 'A';

            expect(shortcutProvider.fired[keyLowerCase]).to.equal(undefined);
            expect(shortcutProvider.fired[keyUpperCase]).to.equal(undefined);

            shortcutProvider.handleKeyDown({ key: keyUpperCase });

            expect(shortcutProvider.fired[keyLowerCase]).to.equal(undefined);
            expect(shortcutProvider.fired[keyUpperCase]).to.equal(true);
            expect(shortcutProvider.keySequence).to.deep.equal(['A']);

            shortcutProvider.handleKeyDown({ key: keyLowerCase });

            expect(shortcutProvider.fired[keyLowerCase]).to.equal(undefined);
            expect(shortcutProvider.fired[keyUpperCase]).to.equal(true);
            expect(shortcutProvider.keySequence).to.deep.equal(['A']);
        });

        it('should call latest registered handler for that hotkey', () => {
            const shortcutProvider = new ShortcutProvider;

            const key = 'A';

            const handler1 = spy();
            const handler2 = spy();
            const handler3 = spy();

            shortcutProvider.props = {
                hotkeys: {
                    [key]: [ handler1, handler2, handler3 ]
                }
            };

            shortcutProvider.handleKeyDown({ key });

            expect(handler1).to.not.have.been.called;
            expect(handler1).to.not.have.been.called;
            expect(handler3).to.have.been.called;
        });

        it('should call handler with event as argument', () => {
            const shortcutProvider = new ShortcutProvider;

            const key = 'A';

            const handler = spy();

            shortcutProvider.props = {
                hotkeys: {
                    [key]: [ handler ]
                }
            };

            const event = { key };

            shortcutProvider.handleKeyDown(event);

            expect(handler).to.have.been.calledWith(event);
        });

        it('should warn when handler is not a function', () => {
            const warn = stub(console, 'warn');

            try {
                const shortcutProvider = new ShortcutProvider;

                const key = 'A';

                const handler = null;

                shortcutProvider.props = {
                    hotkeys: {
                        [key]: [ handler ]
                    }
                };

                shortcutProvider.handleKeyDown({ key });

                expect(warn).to.have.been.called;
            } catch (error) {
                throw error;
            } finally {
                warn.restore();
            }
        });
    });

    describe('handleKeyUp', () => {
        it('should take key out of sequence', () => {
            const shortcutProvider = new ShortcutProvider;

            const key1 = 'A';
            const key2 = 'B';
            const key3 = 'C';

            shortcutProvider.keySequence = [ key1, key2, key3 ];
            shortcutProvider.fired = {
                [key1]: true,
                [key2]: true,
                [key3]: true
            };

            shortcutProvider.handleKeyUp({ key: key2 });

            expect(shortcutProvider.keySequence).to.deep.equal([ key1, key3 ]);
            expect(shortcutProvider.fired).to.deep.equal({
                [key1]: true,
                [key3]: true
            });
        });
    });

    describe('handleBlur', () => {
        it('should reset key sequence', () => {
            const shortcutProvider = new ShortcutProvider;

            const key1 = 'a';
            const key2 = 'A';
            const key3 = 'b';
            const key4 = 'c';

            shortcutProvider.keySequence = [ key1, key2, key3, key4 ];

            shortcutProvider.handleBlur();

            expect(shortcutProvider.keySequence).to.deep.equal([]);
            expect(shortcutProvider.fired).to.deep.equal({});
        });
    });
});
