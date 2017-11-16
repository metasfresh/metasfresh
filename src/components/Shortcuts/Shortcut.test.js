/* eslint-env mocha */
import chai, { expect } from 'chai';
import { spy } from 'sinon';
import sinonChai from 'sinon-chai';
import { Component } from 'react';
import Shortcut from './Shortcut';

chai.use(sinonChai);

describe('Shortcut', () => {
    it('should be a React component', () => {
        expect(Shortcut).to.be.an.instanceOf(Component.constructor);
    });

    it('should return null from render()', () => {
        const shortcut = new Shortcut();

        expect(shortcut.render()).to.equal(null);
    });

    it('should subscribe when mounting', () => {
        const shortcut = new Shortcut();

        const subscribe = spy();

        const name = 'Foo';
        const handler = () => {};

        shortcut.props = { name, handler };
        shortcut.context = { shortcuts: { subscribe } };

        shortcut.componentWillMount();

        expect(subscribe).to.have.been.calledWith(name, handler);
    });

    it('should unsubscribe when unmounting', () => {
        const shortcut = new Shortcut();

        const unsubscribe = spy();

        shortcut.context = { shortcuts: { unsubscribe } };

        const name = 'Foo';
        const handler = () => {};

        shortcut.name = name;
        shortcut.handler = handler;

        shortcut.componentWillUnmount();

        expect(unsubscribe).to.have.been.calledWith(name, handler);
    });

    it('should unsubscribe the same attributes which were subscribed', () => {
        const shortcut = new Shortcut();

        const subscribe = spy();
        const unsubscribe = spy();

        shortcut.context = { shortcuts: { subscribe, unsubscribe } };

        const name1 = 'Foo';
        const handler1 = () => {};

        shortcut.props = { name: name1, handler: handler1 };

        shortcut.componentWillMount();

        expect(subscribe).to.have.been.calledWith(name1, handler1);

        const name2 = 'Bar';
        const handler2 = () => {};

        shortcut.props = { name: name2, handler: handler2 };

        shortcut.componentWillUnmount();

        expect(unsubscribe).to.have.been.calledWith(name1, handler1);
    });
});
