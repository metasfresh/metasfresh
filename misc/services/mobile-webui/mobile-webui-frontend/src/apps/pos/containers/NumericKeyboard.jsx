import React from 'react';
import './NumericKeyboard.scss';
import PropTypes from 'prop-types';
import useKeyDown from '../../../hooks/useKeyDown';

export const NumericKeyboard = ({ onKey }) => {
  useKeyDown((event) => {
    // console.log(`key down event: ${event.key}`, { event });
    if (event.key === '1' || event.code === 'Numpad1') {
      event.preventDefault();
      onKey('1');
    } else if (event.key === '2' || event.code === 'Numpad2') {
      event.preventDefault();
      onKey('2');
    } else if (event.key === '3' || event.code === 'Numpad3') {
      event.preventDefault();
      onKey('3');
    } else if (event.key === '4' || event.code === 'Numpad4') {
      event.preventDefault();
      onKey('4');
    } else if (event.key === '5' || event.code === 'Numpad5') {
      event.preventDefault();
      onKey('5');
    } else if (event.key === '6' || event.code === 'Numpad6') {
      event.preventDefault();
      onKey('6');
    } else if (event.key === '7' || event.code === 'Numpad7') {
      event.preventDefault();
      onKey('7');
    } else if (event.key === '8' || event.code === 'Numpad8') {
      event.preventDefault();
      onKey('8');
    } else if (event.key === '9' || event.code === 'Numpad9') {
      event.preventDefault();
      onKey('9');
    } else if (event.key === '0' || event.code === 'Numpad0') {
      event.preventDefault();
      onKey('0');
    } else if (event.key === '.' || event.code === 'NumpadDecimal') {
      event.preventDefault();
      onKey('.');
    } else if (event.key === '+') {
      event.preventDefault();
      onKey('+');
    } else if (event.key === '-') {
      event.preventDefault();
      onKey('-');
    } else if (event.key === 'Backspace') {
      event.preventDefault();
      onKey('Backspace');
    } else {
      //console.log('no event forwarded', { event });
    }
  });

  return (
    <div className="numpad">
      <Key code="1" onKey={onKey} />
      <Key code="2" onKey={onKey} />
      <Key code="3" onKey={onKey} />
      <Key code="+10" onKey={onKey} />
      <Key code="4" onKey={onKey} />
      <Key code="5" onKey={onKey} />
      <Key code="6" onKey={onKey} />
      <Key code="+20" onKey={onKey} />
      <Key code="7" onKey={onKey} />
      <Key code="8" onKey={onKey} />
      <Key code="9" onKey={onKey} />
      <Key code="+50" onKey={onKey} />
      <Key code="+/-" caption="±" onKey={onKey} />
      <Key code="0" onKey={onKey} />
      <Key code="." onKey={onKey} />
      <Key code="Backspace" icon="fa-solid fa-delete-left" onKey={onKey} />
    </div>
  );
};
NumericKeyboard.propTypes = {
  onKey: PropTypes.func.isRequired,
};

//
//
//

const Key = ({ code, icon, caption, onKey }) => {
  const isEnabled = !!code && onKey;

  let captionElement;
  if (icon) {
    captionElement = <i className={icon}></i>;
  } else if (caption) {
    captionElement = caption;
  } else if (code) {
    captionElement = code;
  }

  return (
    <button
      className="numpad-key"
      disabled={!isEnabled}
      onClick={() => {
        if (isEnabled) {
          onKey(code);
        }
      }}
    >
      <div>{captionElement}</div>
    </button>
  );
};
Key.propTypes = {
  code: PropTypes.string,
  icon: PropTypes.string,
  caption: PropTypes.string,
  onKey: PropTypes.func.isRequired,
};

//
//
//
//
//
//
//
//
//

export const toEditingAmount = ({ value, precision }) => {
  const valueString = value ? value + '' : '0';
  const parts = valueString.split('.');
  const intPart = parts[0];
  const fractionPart = parts.length >= 2 ? parts[1] : '0';

  return {
    value,
    precision,
    valueString,
    intPart,
    fractionPart,
    isDecimalEditing: false,
    isInitialValue: true,
  };
};

export const recomputeAmount = (editingAmount, key) => {
  const { precision, isInitialValue } = editingAmount;

  let intPart, fractionPart, isDecimalEditing;
  if (isInitialValue) {
    intPart = '0';
    fractionPart = '0';
    isDecimalEditing = false;
  } else {
    intPart = editingAmount.intPart;
    fractionPart = editingAmount.fractionPart;
    isDecimalEditing = editingAmount.isDecimalEditing;
  }

  if (
    key === '0' ||
    key === '1' ||
    key === '2' ||
    key === '3' ||
    key === '4' ||
    key === '5' ||
    key === '6' ||
    key === '7' ||
    key === '8' ||
    key === '9'
  ) {
    const digit = key + '';
    if (isDecimalEditing) {
      if (fractionPart === '0') {
        fractionPart = digit;
      } else {
        fractionPart += digit;
      }
      if (fractionPart.length > precision) {
        fractionPart = fractionPart.substring(0, precision);
      }
    } else {
      intPart += digit;
    }
  } else if (key === '+10') {
    intPart = Number(intPart) + 10 + '';
  } else if (key === '+20') {
    intPart = Number(intPart) + 20 + '';
  } else if (key === '+50') {
    intPart = Number(intPart) + 50 + '';
  } else if (key === '+') {
    if (intPart.startsWith('-')) {
      intPart = intPart.substring(1);
    }
  } else if (key === '-') {
    if (!intPart.startsWith('-')) {
      intPart = '-' + intPart;
    }
  } else if (key === '+/-') {
    if (intPart.startsWith('-')) {
      intPart = intPart.substring(1);
    } else {
      intPart = '-' + intPart;
    }
  } else if (key === '.') {
    isDecimalEditing = true;
  } else if (key === 'Backspace') {
    if (isDecimalEditing) {
      if (fractionPart.length <= 1) {
        fractionPart = '0';
        isDecimalEditing = false;
      } else {
        fractionPart = fractionPart.substring(0, fractionPart.length - 1);
      }
    } else {
      if (intPart.length <= 1) {
        intPart = '0';
      } else {
        intPart = intPart.substring(0, intPart.length - 1);
      }
    }
  }

  //console.log('', { key, intPart, fractionPart, isDecimalEditing, editingAmount });

  // No change
  if (
    intPart === editingAmount.intPart &&
    fractionPart === editingAmount.fractionPart &&
    isDecimalEditing === editingAmount.isDecimalEditing
  ) {
    // console.log('recomputeAmount: no change', { editingAmount, key });
    return editingAmount;
  }

  //
  // Build up & return the final result
  const valueString = intPart + '.' + fractionPart;
  return {
    ...editingAmount,
    value: Number(valueString),
    valueString,
    intPart,
    fractionPart,
    isDecimalEditing,
    isInitialValue: false,
  };
};
