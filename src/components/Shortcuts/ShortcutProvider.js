import { Component } from 'react';
import PropTypes from 'prop-types';

export default class ShortcutProvider extends Component {
    static propTypes = {
        children: PropTypes.node
    };

    static childContextTypes = {
        subscribe: PropTypes.func.isRequired,
        unsubscribe: PropTypes.func.isRequired
    };

    getChildContext() {
        return {
            subscribe: this.subscribe,
            unsubscribe: this.unsubscribe
        };
    }

    hotkeys = {};

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
