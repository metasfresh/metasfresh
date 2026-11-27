import { leaveSelectTargetScreen } from '../../../../containers/activities/picking/SelectPickTargetScreen';
import { selectPickingTargetScreenLocation } from '../../../../routes/picking';

jest.mock('../../../../api/picking', () => ({}));
jest.mock('../../../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => ({}) }));

describe('SelectPickTargetScreen: where the picker lands after the choice', () => {
  const history = () => ({ goBack: jest.fn(), goTo: jest.fn() });

  it('opened from the scan screen ("Weiteres TU"): back to that scan screen', () => {
    const h = history();
    leaveSelectTargetScreen({ history: h, returnTo: '/picking/wf/w/pick/A/a/L/l/scanner' });
    expect(h.goTo).toHaveBeenCalledWith('/picking/wf/w/pick/A/a/L/l/scanner');
    expect(h.goBack).not.toHaveBeenCalled();
  });

  it('opened from the line screen ("HU verpacken", target buttons): the usual way back', () => {
    const h = history();
    leaveSelectTargetScreen({ history: h, returnTo: null });
    expect(h.goBack).toHaveBeenCalledTimes(1);
    expect(h.goTo).not.toHaveBeenCalled();
  });
});

describe('selectPickingTargetScreenLocation', () => {
  const base = { applicationId: 'picking', wfProcessId: 'w', activityId: 'a', lineId: 'l', type: 'TU' };

  it('without a return location it is the plain route (also used as the route pattern)', () => {
    expect(selectPickingTargetScreenLocation(base)).toBe('/picking/wf/w/selectPickTarget/a/l/TU');
  });

  it('carries the return location as a query param', () => {
    expect(selectPickingTargetScreenLocation({ ...base, returnTo: '/x/y?next=z' })).toBe(
      '/picking/wf/w/selectPickTarget/a/l/TU?returnTo=%2Fx%2Fy%3Fnext%3Dz'
    );
  });
});
