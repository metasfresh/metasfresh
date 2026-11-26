import React from 'react';
import '@testing-library/jest-dom';
import { fireEvent, render, screen } from '@testing-library/react';

import DialogButton from '../../../components/dialogs/DialogButton';

jest.mock('../../../utils/ui_trace', () => ({ traceFunction: (func) => func }));
jest.mock('../../../utils/translations', () => ({ trl: (key) => `T(${key})` }));

describe('DialogButton', () => {
  it('without an icon the markup is the plain caption inside the button, as before the icon prop existed', () => {
    render(<DialogButton captionKey="general.cancelText" className="is-danger" type="button" onClick={jest.fn()} />);
    expect(screen.getByTestId('cancelText-button').outerHTML).toBe(
      '<button id="cancelText-button" data-testid="cancelText-button" class="button is-danger" type="button">T(general.cancelText)</button>'
    );
  });

  it('with an icon the button shows the icon next to the caption and still fires onClick', () => {
    const onClick = jest.fn();
    render(<DialogButton testId="b" caption="Weiteres TU" icon="fa-barcode" onClick={onClick} />);
    const button = screen.getByTestId('b');
    expect(button.querySelector('i.fas.fa-barcode')).not.toBeNull();
    expect(button).toHaveTextContent('Weiteres TU');
    fireEvent.click(button);
    expect(onClick).toHaveBeenCalledTimes(1);
  });
});
