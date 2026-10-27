import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { fireEvent, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';

import QtyInputField from '../../components/QtyInputField';
import { qtyInfos } from '../../utils/qtyInfos';

const TEST_ID = 'qty-input';

const lastPayload = (onQtyChange) => onQtyChange.mock.calls[onQtyChange.mock.calls.length - 1][0];
const getInput = () => screen.getByTestId(TEST_ID);
const type = (text) => fireEvent.change(getInput(), { target: { value: text } });

const renderField = (props = {}) => {
  const onQtyChange = jest.fn();
  render(<QtyInputField testId={TEST_ID} onQtyChange={onQtyChange} {...props} />);
  return onQtyChange;
};

describe('QtyInputField', () => {
  it('is a text input with a decimal keypad, not a locale-bound number input', () => {
    renderField();
    expect(getInput()).toHaveAttribute('type', 'text');
    expect(getInput()).toHaveAttribute('inputmode', 'decimal');
  });

  it('forwards the dot-decimal for a comma-typed value', () => {
    const onQtyChange = renderField();
    type('1,222');
    const payload = lastPayload(onQtyChange);
    expect(qtyInfos.toNumberOrString(payload)).toBe('1.222');
    expect(payload.qty).toBe(1.222);
    expect(payload.isQtyValid).toBe(true);
  });

  it('forwards the dot-decimal for a dot-typed value', () => {
    const onQtyChange = renderField();
    type('1.222');
    expect(qtyInfos.toNumberOrString(lastPayload(onQtyChange))).toBe('1.222');
  });

  it('keeps the raw typed text on screen', () => {
    renderField();
    type('1,');
    expect(getInput()).toHaveValue('1,');
  });

  it('clearing the field is invalid and is never forwarded as 0', () => {
    const onQtyChange = renderField({ qty: '5' });
    type('');
    const payload = lastPayload(onQtyChange);
    expect(payload.isQtyValid).toBe(false);
    expect(['0', 0]).not.toContain(qtyInfos.toNumberOrString(payload));
    expect(getInput()).toHaveValue('');
  });

  it('integerValuesOnly floors the forwarded qty', () => {
    const onQtyChange = renderField({ integerValuesOnly: true });
    type('3.7');
    expect(lastPayload(onQtyChange).qty).toBe(3);
  });

  it('flags unparseable text as invalid', () => {
    const onQtyChange = renderField();
    type('abc');
    expect(lastPayload(onQtyChange).isQtyValid).toBe(false);
  });

  it('seeds the display from the initial dot-decimal qty', () => {
    renderField({ qty: '2.5' });
    expect(getInput()).toHaveValue('2.5');
  });

  it('a readonly field does not accept input', () => {
    renderField({ readonly: true, qty: '1' });
    expect(getInput()).toBeDisabled();
  });

  it('takes over a genuinely new external value', () => {
    const onQtyChange = jest.fn();
    const { rerender } = render(<QtyInputField testId={TEST_ID} qty="1" onQtyChange={onQtyChange} />);
    rerender(<QtyInputField testId={TEST_ID} qty="7.5" onQtyChange={onQtyChange} />);
    expect(getInput()).toHaveValue('7.5');
  });

  describe('with a parent echoing the forwarded value back (like GetQuantityDialog)', () => {
    const EchoParent = ({ onPayload }) => {
      const [qtyInfo, setQtyInfo] = useState(null);
      return (
        <QtyInputField
          testId={TEST_ID}
          qty={qtyInfos.toNumberOrString(qtyInfo)}
          onQtyChange={(p) => {
            onPayload(p);
            setQtyInfo(p);
          }}
        />
      );
    };

    EchoParent.propTypes = { onPayload: PropTypes.func.isRequired };

    it('typing 1,222 char by char keeps the typed text and forwards 1.222', () => {
      const onPayload = jest.fn();
      render(<EchoParent onPayload={onPayload} />);

      ['1', '1,', '1,2', '1,22', '1,222'].forEach((text) => {
        type(text);
        expect(getInput()).toHaveValue(text);
      });
      expect(qtyInfos.toNumberOrString(lastPayload(onPayload))).toBe('1.222');
    });
  });
});
