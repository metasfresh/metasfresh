import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { CostRevaluationPage } from '../utils/pages/CostRevaluationPage';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation, window 541568): translation check.
 *
 * The functional specs (cost-revaluation-quickinput / -refusal) compare the UI with the WebAPI layout in the login
 * language. This spec pins the exact English and German texts the feature adds (AD_Field_Trl, AD_Process_Trl,
 * AD_Ref_List_Trl, AD_Message), as shown in the window, so a missing or wrong translation is caught.
 * A copy edit of one of these texts in the AD has to be made here as well.
 */

/** Masterdata: PSTK, a stocked Item with 10 on hand in a warehouse. */
async function createMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'CostReval', lastname: 'E2E' } },
      warehouses: { WH: {} },
      products: { PSTK: { name: 'CR_TRANSLATION_CHECK_PRODUCT_WITH_A_LONG_NAME', type: 'Item' } },
      handlingUnits: { HU1: { product: 'PSTK', warehouse: 'WH', qty: 10 } },
    },
  });
}

const TEXTS = {
  en_US: {
    // AD_Field_Trl description of the line fields CurrentCostPrice / CurrentQty / DeltaAmt (line tab)
    lineValuesHint: 'Values as of line creation or the last "Run Revaluation"; recalculated at Complete; booked values after posting.',
    // AD_Field_Trl name of the line field DeltaAmt
    deltaAmtCaption: 'Delta Amount',
    // AD_Process_Trl names of M_CostRevaluation_Run / M_CostRevaluation_CreateLines
    runProcessName: 'Run Revaluation',
    createLinesProcessName: 'Create revaluation lines',
    // AD_Ref_List_Trl name of the Revaluation Source value Manual
    manualSourceName: 'Manual',
    // AD_Message M_CostRevaluationLine_ZeroStockCostProvisional: the quick-input New cost price hint
    provisionalPriceHint:
      'For a product with no stock the entered cost price is provisional: if goods are received before this cost revaluation is posted, it books stock × (new − current); after that every goods receipt re-derives the moving-average price.',
    // AD_Message M_CostRevaluation.LineAlreadyExistsForProduct
    lineAlreadyExistsMessage: (product) => `This cost revaluation already has a line for product ${product}.`,
  },
  de_DE: {
    lineValuesHint:
      'Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
    deltaAmtCaption: 'Differenzbetrag',
    runProcessName: 'Neubewertung ausführen',
    createLinesProcessName: 'Neubewertungspositionen erstellen',
    manualSourceName: 'Manuell',
    provisionalPriceHint:
      'Bei einem Produkt ohne Lagerbestand ist der eingegebene Einstandspreis vorläufig: Wird vor dem Buchen dieser Kosten Neubewertung Ware eingebucht, bucht sie Bestand × (neu − aktuell); danach berechnet jeder Wareneingang den gleitenden Durchschnittspreis neu.',
    lineAlreadyExistsMessage: (product) => `Für das Produkt ${product} gibt es in dieser Kosten Neubewertung bereits eine Zeile.`,
  },
};

const testCases = [
  { language: 'en_US', label: 'English', otherLanguage: 'de_DE' },
  { language: 'de_DE', label: 'German', otherLanguage: 'en_US' },
];

testCases.forEach(({ language, label, otherLanguage }) => {
  // eslint-disable-next-line no-unused-vars
  test(`The cost revaluation window shows its texts translated (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allure.epic('E0226: Costing');
    allure.tag('F1500: Costing');
    allure.tag('F1500');
    allure.story('AD translation verification');
    allure.severity('normal');
    allure.description(`
## F1500: Costing — Kosten Neubewertung texts in ${label}

A new header, a quick-input line and a refused second line for the same product show the ${label} texts:
the Revaluation Source, the process names, the provisional-price hint, the line values' hint, the value
difference caption and the refusal message.
    `);

    const expected = TEXTS[language];
    // Each expected text is a translation: present, and different from the other language's text.
    for (const key of Object.keys(expected)) {
      const text = typeof expected[key] === 'function' ? expected[key]('P') : expected[key];
      const otherText = typeof TEXTS[otherLanguage][key] === 'function' ? TEXTS[otherLanguage][key]('P') : TEXTS[otherLanguage][key];
      expect(text, key).toBeTruthy();
      expect(text, key).not.toBe(otherText);
    }

    const md = await createMasterdata(language);
    const productCode = md.products.PSTK.productCode;
    await LoginPage.goto();
    await LoginPage.login(md.login.user);
    await DashboardPage.expectVisible();

    const recordId = await CostRevaluationPage.createHeader();

    await test.step('Header: Revaluation Source and line creation process', async () => {
      expect(await CostRevaluationPage.revaluationSourceText()).toBe(expected.manualSourceName);
      const captions = await CostRevaluationPage.actionCaptions(['M_CostRevaluation_CreateLines']);
      expect(captions.M_CostRevaluation_CreateLines).toBe(expected.createLinesProcessName);
    });

    await CostRevaluationPage.expectQuickInputOpened();
    await test.step('Quick-input: provisional-price hint on New cost price', async () => {
      await CostRevaluationPage.pickProduct(productCode);
      expect(await CostRevaluationPage.hoverNewCostPriceHint()).toBe(expected.provisionalPriceHint);
      await CostRevaluationPage.submitLine('15');
    });

    const rowId = String((await CostRevaluationPage.getLines(recordId))[0].rowId);

    await test.step('Line grid: value difference caption and the line values\' hint', async () => {
      await expect(CostRevaluationPage.lineCell(rowId, 'DeltaAmt')).toBeVisible();
      const deltaAmtHeader = await CostRevaluationPage.lineColumnHeader('DeltaAmt');
      expect(deltaAmtHeader.caption).toBe(expected.deltaAmtCaption);
      expect(deltaAmtHeader.hint).toBe(expected.lineValuesHint);
    });

    await test.step('Line single-row view: the line values\' hint', async () => {
      await CostRevaluationPage.openLine(rowId);
      for (const columnName of ['CurrentCostPrice', 'CurrentQty', 'DeltaAmt']) {
        expect((await CostRevaluationPage.openLineFieldLabel(columnName)).hint, columnName).toBe(expected.lineValuesHint);
      }
      await CostRevaluationPage.closeLine();
    });

    await test.step('With a line: the revaluation process', async () => {
      const captions = await CostRevaluationPage.actionCaptions(['M_CostRevaluation_Run']);
      expect(captions.M_CostRevaluation_Run).toBe(expected.runProcessName);
    });

    await test.step('A second line for the same product: the refusal message', async () => {
      const product = `${productCode}_${md.products.PSTK.productName}`;
      await CostRevaluationPage.openQuickInput();
      await CostRevaluationPage.addLineExpectingRefusal(productCode, '20', expected.lineAlreadyExistsMessage(product));
    });
  });
});
