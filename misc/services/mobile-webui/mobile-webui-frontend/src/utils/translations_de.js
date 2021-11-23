const translations = {
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
};

export default translations;
