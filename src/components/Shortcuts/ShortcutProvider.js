import { Component } from 'react';
import PropTypes from 'prop-types';

export default class ShortcutProvider extends Component {
    static propTypes = {
        children: PropTypes.node
    };

    static childContextTypes = {
        shortcuts: PropTypes.shape({
            subscribe: PropTypes.func.isRequired,
            unsubscribe: PropTypes.func.isRequired
        })
    };

    hotkeys = {};
    keySequence = [];
    fired = {};

    getChildContext() {
        return {
            shortcuts: {
                subscribe: this.subscribe,
                unsubscribe: this.unsubscribe
            }
        };
    }

    componentWillMount() {
        document.addEventListener('keydown', this.handleKeyDown);
        document.addEventListener('keyup', this.handleKeyUp);
    }

    componentWillUnmount() {
        document.removeEventListener('keydown', this.handleKeyDown);
        document.removeEventListener('keyup', this.handleKeyUp);
    }

    handleKeyDown = event => {
        const { key } = event;
        const { keySequence, fired, hotkeys } = this;

        if (fired[key]) {
            return;
        }

        fired[key] = true;

        this.keySequence = [...keySequence, key];

        const serializedSequence = this.keySequence.join('+').toUpperCase();

        if (!(serializedSequence in hotkeys)) {
            return;
        }

        const bucket = hotkeys[serializedSequence];
        const handler = bucket[bucket.length - 1];

        if (typeof handler === 'function') {
            return handler(event);
        }

        // eslint-disable-next-line max-len
        console.warn(`Handler defined for key sequence "${keySequence}" is not a function.`);
    };

    handleKeyUp = () => {
        this.keySequence = [];
        this.fired = {};
    };

    register = hotkeys => {
        this.hotkeys = hotkeys;
    };

    subscribe = (name, handler) => {
        const { hotkeys } = this;

        if (!(name in hotkeys)) {
            console.warn(`There are no hotkeys defined for "${name}".`);

            return;
        }

        const bucket = hotkeys[name];

        hotkeys[name] = [...bucket, handler];
    };

    unsubscribe = (name, handler) => {
        const { hotkeys } = this;

        if (!(name in hotkeys)) {
            console.warn(`There are no hotkeys defined for "${name}".`);

            return;
        }

        const bucket = hotkeys[name];
        let found = false;

        hotkeys[name] = bucket.filter(_handler => {
            if (_handler === handler) {
                found = true;

                return false;
            }

            return true;
        });

        if (!found) {
            // eslint-disable-next-line max-len
            console.warn(`The handler you are trying to unsubscribe from "${name}" has not been subscribed yet.`);
        }
    };

    render() {
        return this.props.children;
    }
}
