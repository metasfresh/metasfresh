import {
  getRefusedNumberText,
  hasRefusedNumberInput,
  markRefusedNumberInput,
  unmarkRefusedNumberInput,
} from '../../utils/refusedNumberInputs';

describe('refused number inputs', () => {
  it('tells whether a container holds an input whose number was refused', () => {
    const form = document.createElement('form');
    const input = document.createElement('input');
    const otherForm = document.createElement('form');
    form.appendChild(input);
    document.body.append(form, otherForm);

    markRefusedNumberInput(input);
    expect(hasRefusedNumberInput(form)).toBe(true);
    expect(hasRefusedNumberInput(otherForm)).toBe(false);

    unmarkRefusedNumberInput(input);
    expect(hasRefusedNumberInput(form)).toBe(false);
    form.remove();
    otherForm.remove();
  });

  it('forgets an input that is no longer in the page', () => {
    const form = document.createElement('form');
    const input = document.createElement('input');
    form.appendChild(input);
    document.body.append(form);
    markRefusedNumberInput(input);

    form.remove();

    expect(hasRefusedNumberInput(form)).toBe(false);
  });

  it('tells which text was refused in a container', () => {
    const form = document.createElement('form');
    const input = document.createElement('input');
    form.appendChild(input);
    document.body.append(form);

    expect(getRefusedNumberText(form)).toBeNull();
    markRefusedNumberInput(input, '3.57');
    expect(getRefusedNumberText(form)).toEqual('3.57');

    unmarkRefusedNumberInput(input);
    form.remove();
  });
});
