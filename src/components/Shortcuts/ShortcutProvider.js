import { Component } from 'react';
import PropTypes from 'prop-types';

export default class ShortcutProvider extends Component {
    static propTypes = {
        children: PropTypes.node,
        hotkeys: PropTypes.object.isRequired,
        keymap: PropTypes.object.isRequired
    };

    static childContextTypes = {
        shortcuts: PropTypes.shape({
            subscribe: PropTypes.func.isRequired,
            unsubscribe: PropTypes.func.isRequired
        })
    };

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
        window.addEventListener('blur', this.handleBlur);
    }

    componentWillUnmount() {
        document.removeEventListener('keydown', this.handleKeyDown);
        document.removeEventListener('keyup', this.handleKeyUp);
        window.removeEventListener('blur', this.handleBlur);
    }

    handleKeyDown = event => {
        const { key } = event;
        const { keySequence, fired } = this;
        const { hotkeys } = this.props;

        if (fired[key]) {
            return;
        }

        fired[key] = true;

        this.keySequence = [...keySequence, key];

        const serializedSequence = (this.keySequence
            .join('+')
            .replace(/\s/, 'space')
            .toUpperCase()
        );

        if (!(serializedSequence in hotkeys)) {
            return;
        }

        const bucket = hotkeys[serializedSequence];
        const handler = bucket[bucket.length - 1];

        if (typeof handler === 'function') {
            return handler(event);
        }

        // eslint-disable-next-line max-len
        console.warn(`Handler defined for key sequence "${serializedSequence}" is not a function.`, handler);
    };

    handleKeyUp = () => {
        this.keySequence = [];
        this.fired = {};
    };

    handleBlur = () => {
        this.keySequence = [];
        this.fired = {};
    };

    subscribe = (name, handler) => {
        const { hotkeys, keymap } = this.props;

        if (!(name in this.props.keymap)) {
            console.warn(`There are no hotkeys defined for "${name}".`);

            return;
        }

        const key = keymap[name].toUpperCase();
        const bucket = hotkeys[key];

        hotkeys[key] = [...bucket, handler];
    };

    unsubscribe = (name, handler) => {
        const { hotkeys, keymap } = this.props;

        if (!(name in this.props.keymap)) {
            console.warn(`There are no hotkeys defined for "${name}".`);

            return;
        }

        const key = keymap[name].toUpperCase();
        const bucket = hotkeys[key];
        let found = false;

        hotkeys[key] = bucket.filter(_handler => {
            if (_handler === handler) {
                found = true;

                return false;
            }

            return true;
        });

        if (!found) {
            // eslint-disable-next-line max-len
            console.warn(`The handler you are trying to unsubscribe from "${name}" has not been subscribed yet.`, handler);
        }
    };

    render() {
        return this.props.children;
    }
}
