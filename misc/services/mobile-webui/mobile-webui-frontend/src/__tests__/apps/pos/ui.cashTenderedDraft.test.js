import { uiReducer } from '../../../apps/pos/reducers/ui';
import { getCashTenderedDraftFromState } from '../../../apps/pos/reducers/uiUtils';
import { setCashTenderedDraftAction } from '../../../apps/pos/actions/ui';

const AMOUNT_10 = { value: 10, valueString: '10.0', isInitialValue: false };

describe('POS ui state - cash tendered draft', () => {
  it('stores the draft and returns it for the same payment only', () => {
    const state = uiReducer({}, setCashTenderedDraftAction({ payment_uuid: 'p1', tenderedEditingAmount: AMOUNT_10 }));

    expect(getCashTenderedDraftFromState({ applicationState: state, payment_uuid: 'p1' })).toBe(AMOUNT_10);
    expect(getCashTenderedDraftFromState({ applicationState: state, payment_uuid: 'p2' })).toBeUndefined();
    expect(getCashTenderedDraftFromState({ applicationState: state, payment_uuid: undefined })).toBeUndefined();
    expect(getCashTenderedDraftFromState({ applicationState: {}, payment_uuid: 'p1' })).toBeUndefined();
  });

  it('keeps the same state when the same draft is set again', () => {
    const action = setCashTenderedDraftAction({ payment_uuid: 'p1', tenderedEditingAmount: AMOUNT_10 });
    const state = uiReducer({}, action);

    expect(uiReducer(state, action)).toBe(state);
  });

  it('replaces the draft of the previous payment', () => {
    let state = uiReducer({}, setCashTenderedDraftAction({ payment_uuid: 'p1', tenderedEditingAmount: AMOUNT_10 }));
    const amount5 = { value: 5, valueString: '5.0', isInitialValue: false };
    state = uiReducer(state, setCashTenderedDraftAction({ payment_uuid: 'p2', tenderedEditingAmount: amount5 }));

    expect(getCashTenderedDraftFromState({ applicationState: state, payment_uuid: 'p1' })).toBeUndefined();
    expect(getCashTenderedDraftFromState({ applicationState: state, payment_uuid: 'p2' })).toBe(amount5);
  });
});
