import React, { createContext, useContext, useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import defaultKeymap, { disabledWithFocus } from '../../shortcuts/keymap';
import blacklist from '../../shortcuts/blacklist';
import { useDocumentEventListener } from '../../hooks/useDocumentEventListener';
import { useWindowEventListener } from '../../hooks/useWindowEventListener';

const codeToKey = {
  8: 'Backspace',
  9: 'Tab',
  13: 'Enter',
  20: 'CapsLock',
  27: 'Escape',
  32: ' ',
  33: 'PageUp',
  34: 'PageDown',
  35: 'End',
  36: 'Home',
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
  107: '+',
  109: '-',
  187: '+',
  189: '-',
};

const ShortcutContext = createContext(null);

const useShortcutProvider = () => {
  const context = useContext(ShortcutContext);
  if (!context) {
    throw new Error('useShortcuts must be used within a ShortcutProvider');
  }
  return context;
};

export const useShortcut = ({
  name,
  shortcut,
  handler,
  enabled = true,
  dependencies = [],
}) => {
  const { subscribe } = useShortcutProvider();

  useEffect(() => {
    if (!enabled) return;

    // noinspection UnnecessaryLocalVariableJS
    const unsubscribe = subscribe({ name, shortcut, handler });
    return unsubscribe;
  }, [subscribe, name, shortcut, enabled, ...(dependencies ?? [])]);
};

export const ShortcutProvider = ({ children }) => {
  const keymapRef = useRef(null);
  const hotkeysRef = useRef({});
  const keySequenceRef = useRef([]);
  const firedRef = useRef({});

  // Initialize keymap
  if (keymapRef.current == null) {
    keymapRef.current = { ...defaultKeymap };
  }

  useWindowEventListener('blur', () => {
    keySequenceRef.current = [];
    firedRef.current = {};
  });

  useDocumentEventListener('keydown', (event) => {
    const key = codeToKey[event.keyCode]?.toUpperCase();
    if (!key) {
      // console.log(`Unknown key for code ${event.keyCode}`, { event });
      return;
    }

    const activeNode = document ? document.activeElement : null;

    const fired = firedRef.current;
    const hotkeys = hotkeysRef.current;
    let keySequence = keySequenceRef.current;

    if (event.altKey === true) {
      keySequence = ['Alt'];
    } else if (event.ctrlKey === true) {
      keySequence = ['Ctrl'];
    } else if (event.shiftKey === true) {
      keySequence = ['Shift'];
    }

    if (fired[key]) {
      return;
    }

    fired[key] = true;

    keySequenceRef.current = [...keySequence, key];

    const serializedSequence = keySequenceRef.current
      .join('+')
      .replace(/\s/, 'Spacebar')
      .toUpperCase();

    const handles = hotkeys[serializedSequence];
    if (!handles || handles.length === 0) {
      // console.debug(`[Shortcut ${key}] no handlers found`);
      return;
    }

    // some shortcuts should be disabled
    // when the input field is focused (for typing)
    if (
      activeNode &&
      activeNode.nodeName === 'INPUT' &&
      disabledWithFocus.indexOf(serializedSequence) > -1
    ) {
      // console.debug(`[Shortcut ${key}] skip firing because disabled for input field`);
      return;
    }

    fireHandlers(event, handles);
  });

  useDocumentEventListener('keyup', (event) => {
    const _key = codeToKey[event.keyCode];
    const key = _key && _key.toUpperCase();
    if (!key) {
      return;
    }

    const modifierKeys = ['Alt', 'Ctrl', 'Shift'];

    keySequenceRef.current = keySequenceRef.current.filter(
      (_key) => _key !== key && modifierKeys.indexOf(_key) === -1
    );

    delete firedRef.current[key];
  });

  const subscribe = ({ name, shortcut, handler }) => {
    if (!handler) {
      console.warn(`subscribe: no handler provided for "${name}".`);
      return;
    }
    if (typeof handler !== 'function') {
      console.warn(
        `subscribe: handler for "${name}" is not a function. Got ${typeof handler}.`
      );
      return;
    }

    const keymap = keymapRef.current;
    const previousKey = keymap[name];
    const key = (shortcut ? shortcut : keymap[name])?.toUpperCase();
    if (!key) {
      // console.warn(`There are no hotkeys defined for "${name}".`, { keymap });
      return;
    }

    if (key in blacklist) {
      const reason = blacklist[key];
      const reasonFormatted = reason ? ` (${reason})` : '';

      console.warn(
        `Key "${key}" used by "${name}" is blacklisted since it overrides browser behaviour${reasonFormatted}.`
      );
      return;
    }

    const hotkeys = hotkeysRef.current;
    keymap[name] = key;
    hotkeys[key] = [handler, ...(hotkeys[key] ?? [])];

    // console.log(`Added handler for "${name}" with shortcut "${key}".`, {
    //   shortcut,
    //   previousKey,
    //   hotkeys,
    //   keymap,
    // });

    return () => {
      // Restore previous key binding
      keymapRef.current[name] = previousKey;

      // Remove handler
      const hotkeys = hotkeysRef.current;
      hotkeys[key] = (hotkeys[key] || []).filter((h) => h !== handler);

      // console.log(`Removed handler for "${name}" with shortcut "${key}".`, {
      //   shortcut,
      //   previousKey,
      // });
    };
  };

  const contextValue = {
    subscribe,
  };

  return (
    <ShortcutContext.Provider value={contextValue}>
      {children}
    </ShortcutContext.Provider>
  );
};

ShortcutProvider.propTypes = {
  children: PropTypes.node,
};

//
//
//
//
//

// In the case of different handlers using the same shortcut, we can control which
// one will be fired by returning true/false from their execution. Handlers are
// added in reverse order, so this way we can only call the latest in the queue.
// Summarizing - if you want a handler to just pass through, return false before
// any other code in the function.
const fireHandlers = (event, handlers) => {
  for (let i = 0; i < handlers.length; i++) {
    const handler = handlers[i];
    const stopPropagation = handler(event);
    // console.log(`[Event ${event}] Invoked handler`, { handler, stopPropagation, event });

    if (stopPropagation) {
      break;
    }
  }
};
