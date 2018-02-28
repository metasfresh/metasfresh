import PropTypes from 'prop-types';
import { Component } from 'react';

const codeToKey = {
  8: 'Backspace',
  9: 'Tab',
  13: 'Enter',
  16: 'Shift',
  17: 'Control',
  18: 'Alt',
  20: 'CapsLock',
  27: 'Escape',
  32: ' ',
  33: 'PageUp',
  34: 'PageDown',
  35: 'End',
  37: 'ArrowLeft',
  38: 'ArrowUp',
  39: 'ArrowRight',
  40: 'ArrowDown',
  48: '0',
  49: '1',
  50: '2',
  51: '3',
  52: '4',
  53: '5',
  54: '6',
  55: '7',
  56: '8',
  57: '9',
  65: 'a',
  66: 'b',
  67: 'c',
  68: 'd',
  69: 'e',
  70: 'f',
  71: 'g',
  72: 'h',
  73: 'i',
  74: 'j',
  75: 'k',
  76: 'l',
  77: 'm',
  78: 'n',
  79: 'o',
  80: 'p',
  81: 'q',
  82: 'r',
  83: 's',
  84: 't',
  85: 'u',
  86: 'v',
  87: 'w',
  88: 'x',
  89: 'y',
  90: 'z',
  91: 'Meta',
  187: '+',
  189: '-',
};

export default class ShortcutProvider extends Component {
  static propTypes = {
    children: PropTypes.node,
    hotkeys: PropTypes.object.isRequired,
    keymap: PropTypes.object.isRequired,
  };

  static childContextTypes = {
    shortcuts: PropTypes.shape({
      subscribe: PropTypes.func.isRequired,
      unsubscribe: PropTypes.func.isRequired,
    }),
  };

  keySequence = [];
  fired = {};

  getChildContext() {
    return {
      shortcuts: {
        subscribe: this.subscribe,
        unsubscribe: this.unsubscribe,
      },
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
    const _key = codeToKey[event.keyCode];
    const key = _key && _key.toUpperCase();

    if (!key) {
      return;
    }

    const { keySequence, fired } = this;
    const { hotkeys } = this.props;

    if (fired[key]) {
      return;
    }

    fired[key] = true;

    this.keySequence = [...keySequence, key];

    const serializedSequence = this.keySequence
      .join('+')
      .replace(/\s/, 'Spacebar')
      .toUpperCase();

    if (!(serializedSequence in hotkeys)) {
      return;
    }

    const bucket = hotkeys[serializedSequence];
    const handler = bucket[bucket.length - 1];

    if (typeof handler === 'function') {
      return handler(event);
    }

    // eslint-disable-next-line no-console
    console.warn(
      `Handler defined for key sequence "${serializedSequence}" is not a function.`,
      handler
    );
  };

  handleKeyUp = event => {
    const _key = codeToKey[event.keyCode];
    const key = _key && _key.toUpperCase();

    if (!key) {
      return;
    }

    this.keySequence = this.keySequence.filter(_key => _key !== key);

    delete this.fired[key];
  };

  handleBlur = () => {
    this.keySequence = [];
    this.fired = {};
  };

  subscribe = (name, handler) => {
    const { hotkeys, keymap } = this.props;

    if (!(name in this.props.keymap)) {
      // eslint-disable-next-line no-console
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
      // eslint-disable-next-line no-console
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
      // eslint-disable-next-line no-console
      console.warn(
        `The handler you are trying to unsubscribe from "${name}" has not been subscribed yet.`,
        handler
      );
    }
  };

  render() {
    return this.props.children;
  }
}
