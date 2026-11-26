import translations_de from '../../utils/translations_de';

// The German texts of the next-TU flow say "TU", not "Kiste"
describe('German texts of the next-TU flow', () => {
  const picking = translations_de.picking;

  it('button', () => expect(picking.nextTU).toBe('Weiteres TU'));
  it('busy message', () =>
    expect(picking.nextTUBusy).toBe('Es wird gerade ein TU geöffnet. Bitte gleich nochmal scannen.'));
  it('header line of the open TU', () =>
    expect(translations_de.activities.picking.tuPickingTarget.Current).toBe('Offenes TU'));
  it('no "Kiste" left in the picking texts', () => {
    expect(JSON.stringify(translations_de)).not.toContain('Kiste');
  });
});
