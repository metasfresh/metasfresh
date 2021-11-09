import counterpart from 'counterpart';

export const setupCounterpart = () => {
  console.log('Setting up counterpart defaults...');

  counterpart.setMissingEntryGenerator(function (key) {
    // eslint-disable-next-line no-console
    console.error(`Missing translation: ${key}`);
    return `${key}`;
  });

  counterpart.registerTranslations('en', {
    appName: 'Mobile UI',
    general: {
      Product: 'Product',
      Locator: 'Locator',
      Barcode: 'Barcode',
      QtyToPick: 'Qty to pick',
      QtyPicked: 'Qty picked',
      QtyMoved: 'Qty moved',
      DropToLocator: 'Drop to locator',
      PleaseTryAgain: 'Please try again',
    },
    login: {
      submitButton: 'Login',
    },
    mobileui: {
      manufacturing: {
        appName: 'Production',
      },
      picking: {
        appName: 'Picking',
      },
      distribution: {
        appName: 'Distribution',
      },
    },
    activities: {
      scanBarcode: {
        defaultCaption: 'Scan',
        invalidScannedBarcode: 'Scanned code is invalid',
      },
      abortText: 'Abort',
      picking: {
        PickingLine: 'Picking Line',
        scanHUBarcode: 'Scan HU',
        notEligibleHUBarcode: 'HU barcode not matching',
        invalidQtyPicked: 'Invalid qty picked',
        confirmDone: 'Done',
        rejectedPrompt: 'There are %(qtyRejected)s %(uom)s not picked. Why ?',
        unPickBtn: 'Unpick',
        target: 'To Pick',
        picked: 'Picked',
      },
      distribution: {
        DistributionLine: 'Distribution Line',
        target: 'To Move',
        picked: 'Picked',
        scanHU: 'Scan pick from HU',
        scanLocator: 'Scan drop from Locator',
      },
      confirmButton: {
        default: {
          caption: 'Confirm',
          promptQuestion: 'Are you sure?',
          yes: 'Yes',
          no: 'No',
        },
        abort: {
          caption: 'Abort',
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
    mobileui: {
      manufacturing: {
        appName: 'Production',
      },
      picking: {
        appName: 'Picking',
      },
      distribution: {
        appName: 'Distribution',
      },
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
        target: 'To Pick',
        picked: 'Picked',
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
