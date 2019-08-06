import { QualityNote } from '../../support/utils/qualityNote';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create test: create quality note, https://github.com/metasfresh/metasfresh-e2e/issues/127', function() {
  const date = humanReadableNow();
  const qualityNoteName = `QualityNoteTest ${date}`;
  const qualityNoteValue = `QualityNoteValueTest ${date}`;
  it('Create quality note', function() {
    cy.fixture('material/quality_note.json').then(qualityNoteJson => {
      Object.assign(new QualityNote(), qualityNoteJson)
        .setValue(qualityNoteValue)
        .setName(qualityNoteName)
        .apply();
    });
  });
});
