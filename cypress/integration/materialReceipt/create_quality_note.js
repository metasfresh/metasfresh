import { QualityNote } from '../../support/utils/qualityNote';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create test: create quality note', function() {
  let qualityNoteName;

  it('Read the fixture', function() {
    cy.fixture('materialReceipt/create_quality_note.json').then(f => {
      qualityNoteName = appendHumanReadableNow(f['qualityNoteName']);
    });
  });

  it('Create quality note', function() {
    cy.fixture('material/quality_note.json').then(qualityNoteJson => {
      Object.assign(new QualityNote(), qualityNoteJson)
        .setValue(qualityNoteName)
        .setName(qualityNoteName)
        .apply();
    });
  });
});
