import { getAssignedGrais, getExtraGrais, mergeGraisAfterSend } from '../../utils/grai';

describe('mergeGraisAfterSend', () => {
  it('nothing changed since the baseline: backend answer plus the baseline extras', () => {
    expect(
      mergeGraisAfterSend({
        baselineGrais: ['A', 'B', 'X'],
        currentGrais: ['A', 'B', 'X'],
        backendGrais: ['A', 'B'],
        tuCount: 2,
      })
    ).toEqual(['A', 'B', 'X']);
  });

  it('the backend lowers the TU count: GRAIs beyond it stay in the list (shown as extras)', () => {
    const merged = mergeGraisAfterSend({
      baselineGrais: ['A', 'B'],
      currentGrais: ['A', 'B'],
      backendGrais: ['A'],
      tuCount: 1,
    });
    expect(merged).toEqual(['A', 'B']);
    expect(getAssignedGrais(merged, 1)).toEqual(['A']);
    expect(getExtraGrais(merged, 1)).toEqual(['B']);
  });

  it('a code in both the backend answer and the extras appears only once', () => {
    expect(
      mergeGraisAfterSend({
        baselineGrais: ['A', 'B', 'C'],
        currentGrais: ['A', 'B', 'C'],
        backendGrais: ['A', 'C'],
        tuCount: 2,
      })
    ).toEqual(['A', 'C']);
  });

  it('a code added since the baseline that the backend also reports appears only once', () => {
    expect(
      mergeGraisAfterSend({ baselineGrais: ['A'], currentGrais: ['A', 'B'], backendGrais: ['A', 'B'], tuCount: 0 })
    ).toEqual(['A', 'B']);
  });

  it('a code removed and scanned again since the baseline is kept, once', () => {
    expect(
      mergeGraisAfterSend({ baselineGrais: ['A', 'B'], currentGrais: ['B', 'A'], backendGrais: ['A', 'B'], tuCount: 0 })
    ).toEqual(['A', 'B']);
  });

  it('a code removed since the baseline stays removed; a code added is appended', () => {
    expect(
      mergeGraisAfterSend({ baselineGrais: ['A', 'B'], currentGrais: ['B', 'C'], backendGrais: ['A', 'B'], tuCount: 0 })
    ).toEqual(['B', 'C']);
  });
});
