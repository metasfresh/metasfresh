import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { ScannableCodeFormatPage } from '../utils/pages/ScannableCodeFormatPage';

/**
 * Scannable Code Format (C_ScannableCode_Format, window 541880), part tab: End No is optional.
 * An empty End No means "read to the end of the code (or up to the position before the next part)"; the field's
 * description (AD_Element via AD_Field.AD_Name_ID) tells the user so, in every language.
 * A copy edit of these texts in the AD has to be made here as well.
 */
const DESCRIPTION = {
  en_US: 'Leave empty: read to the end of the code, or up to the position before the next part.',
  de_DE: 'Leer lassen: bis zum Ende des Codes bzw. bis zur Position vor dem nächsten Teil lesen.',
};

// The language is a user setting (AD_User.AD_Language): put it back to English for whoever uses the stack next.
test.afterEach(async ({ page }) => { // eslint-disable-line no-unused-vars
  await ScannableCodeFormatPage.setUserLanguage('en_US');
});

[
  { language: 'en_US', label: 'English', endNoCaption: 'End No' },
  { language: 'de_DE', label: 'German', endNoCaption: 'End-Nr.' },
].forEach(({ language, label, endNoCaption }) => {
  test(`A scannable code format part can be saved without End No, and the field explains it (${label})`, async ({ page }) => { // eslint-disable-line no-unused-vars
    test.setTimeout(300000);
    allure.epic('Mobile UI');
    allure.story('Scannable code format: open End No');
    allure.severity('normal');
    allure.description(`End No of a scannable code format part is optional; the field description explains it in ${label}.`);

    await ScannableCodeFormatPage.loginAs(language);
    await ScannableCodeFormatPage.createFormat(`E2E open end ${language} ${Date.now()}`);

    await ScannableCodeFormatPage.openNewPartForm();
    await test.step('New part form: End No explains that empty reads to the end', async () => {
      const endNo = await ScannableCodeFormatPage.endNoInForm();
      expect(endNo.caption).toBe(endNoCaption);
      expect(endNo.hint).toBe(DESCRIPTION[language]);
      expect(endNo.value).toBe('');
    });

    await ScannableCodeFormatPage.fillStartNoAndDataType(11, 'LOT');
    await ScannableCodeFormatPage.done();

    await test.step('Grid: the part is saved with an empty End No; the End No column explains it', async () => {
      const cells = await ScannableCodeFormatPage.firstPartRowCells();
      expect(cells[0]).toBe('11');
      expect(cells[1]).toBe('');
      const header = await ScannableCodeFormatPage.endNoGridHeader();
      expect(header.caption).toBe(endNoCaption);
      expect(header.hint).toBe(DESCRIPTION[language]);
    });

    await ScannableCodeFormatPage.openFirstPartInAdvancedEdit();
    await test.step('Advanced edit: End No is empty and explains it', async () => {
      const endNo = await ScannableCodeFormatPage.endNoInForm();
      expect(endNo.hint).toBe(DESCRIPTION[language]);
      expect(endNo.value).toBe('');
    });
    await ScannableCodeFormatPage.closeForm();
  });
});
