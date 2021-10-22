import counterpart from 'counterpart';

export const setupCounterpart = () => {
  console.log('Setting up counterpart defaults...');

  counterpart.setMissingEntryGenerator(function (key) {
    // eslint-disable-next-line no-console
    console.error(`Missing translation: ${key}`);
    return `${key}`;
  });

  counterpart.registerTranslations('en', {
    appName: 'Picking',
    general: {
      Product: 'Product',
      Locator: 'Locator',
      Barcode: 'Barcode',
      QtyToPick: 'Qty to pick',
      QtyPicked: 'Qty picked',
      PleaseTryAgain: 'Please try again',
    },
    login: {
      submitButton: 'Login',
    },
    activities: {
      scanBarcode: {
        defaultCaption: 'Scan',
        invalidScannedBarcode: 'Scanned code is invalid',
      },
      picking: {
        PickingLine: 'Picking Line',
        scanHUBarcode: 'Scan HU',
        notEligibleHUBarcode: 'HU barcode not matching',
        invalidQtyPicked: 'Invalid qty picked',
        confirmDone: 'Done',
        rejectedPrompt: 'There are %(qtyRejected)s %(uom)s not picked. Why ?',
        unPickBtn: 'Unpick',
        pickingBtn: {
          toPick: 'To pick',
          picked: 'Picked',
        },
      },
      confirmButton: {
        default: {
          caption: 'Confirm',
          promptQuestion: 'Are you sure?',
          yes: 'Yes',
          no: 'No',
        },
      },
    },
  });

  counterpart.registerTranslations('de', {
    appName: 'Kommissionierung',
    general: {
      Product: 'Produkt',
      Locator: 'Lagerort',
      Barcode: 'Barcode',
      QtyToPick: 'Qty to pick',
      QtyPicked: 'Menge (Lagereinheit)',
      PleaseTryAgain: 'Please try again',
    },
    login: {
      submitButton: 'Login',
    },
    activities: {
      scanBarcode: {
        defaultCaption: 'Scan',
        invalidScannedBarcode: 'Scanned code is invalid',
      },
      picking: {
        PickingLine: 'Picking Line',
        scanHUBarcode: 'Scan HU',
        notEligibleHUBarcode: 'HU barcode not matching',
        invalidQtyPicked: 'Invalid qty picked',
        confirmDone: 'Done',
        rejectedPrompt: 'There are %(qtyRejected)s %(uom)s not picked. Why ?',
        unPickBtn: 'Unpick',
      },
      confirmButton: {
        default: {
          caption: 'Confirm',
          promptQuestion: 'Are you sure?',
          yes: 'Yes',
          no: 'No',
        },
      },
    },
  });

  // setLanguage('de_DE');
};

export const setLanguage = (language) => {
  const idx = language.indexOf('_');
  if (idx > 0) {
    language = language.substr(0, idx);
  }

  console.log('Setting language to ', language);
  counterpart.setLocale(language);
};
