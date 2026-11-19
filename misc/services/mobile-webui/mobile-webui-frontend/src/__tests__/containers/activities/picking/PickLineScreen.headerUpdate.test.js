import React from 'react';
import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

import { useHeaderUpdate } from '../../../../containers/activities/picking/PickLineScreen';
import { HEADER_UPDATE_ENTRY } from '../../../../constants/HeaderActionTypes';

jest.mock('../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));

const Harness = (props) => {
  useHeaderUpdate({ url: '/line/1', caption: 'Line 1', uom: 'PCE', qtyToPick: 10, qtyPicked: 0, ...props });
  return null;
};

const renderWithStore = (props) => {
  const actions = [];
  const store = createStore((state = {}, action) => {
    actions.push(action);
    return state;
  });
  const ui = (p) => (
    <Provider store={store}>
      <Harness {...p} />
    </Provider>
  );
  const view = render(ui(props));
  return { actions, rerenderWith: (p) => view.rerender(ui(p)) };
};

const lastHeaderValues = (actions) => {
  const headerActions = actions.filter((a) => a.type === HEADER_UPDATE_ENTRY);
  return headerActions[headerActions.length - 1].payload.values;
};

describe('useHeaderUpdate: additional header entries', () => {
  it('shows a box that is opened later, on the same screen, and the next box after it', () => {
    const { actions, rerenderWith } = renderWithStore({ additionalHeaderProperties: { entries: [] } });

    const box1 = { entries: [{ caption: 'Box', value: 'BOX-1' }] };
    rerenderWith({ additionalHeaderProperties: box1 });
    expect(lastHeaderValues(actions)).toEqual(expect.arrayContaining([expect.objectContaining({ value: 'BOX-1' })]));

    const box2 = { entries: [{ caption: 'Box', value: 'BOX-2' }] };
    rerenderWith({ additionalHeaderProperties: box2 });
    const values = lastHeaderValues(actions);
    expect(values).toEqual(expect.arrayContaining([expect.objectContaining({ value: 'BOX-2' })]));
    expect(values).not.toEqual(expect.arrayContaining([expect.objectContaining({ value: 'BOX-1' })]));
  });

  it('refreshes the picked and to-pick quantities while staying on the line', () => {
    const { actions, rerenderWith } = renderWithStore({ additionalHeaderProperties: { entries: [] } });
    const shown = () => JSON.stringify(lastHeaderValues(actions));
    expect(shown()).toContain('"10');

    rerenderWith({ additionalHeaderProperties: { entries: [] }, qtyPicked: 4, qtyToPick: 10 });
    expect(shown()).toContain('4');
    const afterPick = actions.length;

    rerenderWith({ additionalHeaderProperties: { entries: [] }, qtyPicked: 7, qtyToPick: 12 });
    expect(actions.length).toBeGreaterThan(afterPick);
    expect(shown()).toContain('12');
  });
});
