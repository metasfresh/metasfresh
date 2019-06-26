import { QualityNote } from '../../support/utils/qualityNote';

describe('Create test: create quality note, https://github.com/metasfresh/metasfresh-e2e/issues/127', function() {
  const timestamp = new Date().getTime();
  const qualityNoteName = `QualityNoteTest ${timestamp}`;
  const qualityNoteValue = `QualityNoteValueTest ${timestamp}`;
  it('Create quality note', function() {
    cy.fixture('material/quality_note.json').then(qualityNoteJson => {
      Object.assign(new QualityNote(), qualityNoteJson)
        .setValue(qualityNoteValue)
        .setName(qualityNoteName)
        .apply();
    });
  });
});
