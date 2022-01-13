const translations = {
  appName: 'metasfresh mobile',
  general: {
    Product: 'Produkt',
    Locator: 'Lagerort',
    Barcode: 'Barcode',
    QtyToPick: 'Pick Menge',
    QtyPicked: 'Menge gepickt',
    QtyMoved: 'Menge bewegt',
    QtyToMove: 'Bewegungsmenge',
    DropToLocator: 'Ziel Lagerort',
    PleaseTryAgain: 'Oops, das sollte nicht passieren',
    cancelText: 'Abbrechen',
  },
  login: {
    submitButton: 'Login',
  },
  mobileui: {
    manufacturing: {
      appName: 'Produktion',
    },
    picking: {
      appName: 'Kommissionierung',
    },
    distribution: {
      appName: 'Bereitstellung',
    },
  },
  activities: {
    scanBarcode: {
      defaultCaption: 'Scan',
      invalidScannedBarcode: 'Code ist ungültig',
    },
    abortText: 'Verlassen',
    picking: {
      PickingLine: 'Pick Zeile',
      scanHUBarcode: 'Scan HU',
      notEligibleHUBarcode: 'HU Code passt nicht',
      invalidQtyPicked: 'Falsche Menge gepickt',
      confirmDone: 'OK',
      rejectedPrompt: 'Es gibt %(qtyRejected)s %(uom)s ungepickte Mengen. Warum?',
      unPickBtn: 'Rückgängig',
      target: 'Soll',
      picked: 'Ist',
    },
    distribution: {
      DistributionLine: 'Bereitstellung Zeile',
      target: 'Soll',
      picked: 'Ist',
      scanHU: 'Scan HU',
      scanLocator: 'Scan Ziel Lagerort',
      invalidLocatorBarcode: 'Invalid locator barcode',
      invalidQtyToMove: 'Invalid qty to move',
    },
    confirmButton: {
      default: {
        caption: 'Confirm',
        promptQuestion: 'Bist du sicher?',
        yes: 'Ja',
        no: 'Nein',
      },
      abort: 'Rückgängig',
      notFound: 'Nicht gefunden',
    },
    mfg: {
      ProductName: 'Produkt',
      target: 'Soll',
      picked: 'Ist',
      issues: {
        target: 'Zuf. Soll',
        picked: 'Ist',
        qtyToIssue: 'Menge Soll',
        qtyIssued: 'Menge Ist',
        qtyRejected: 'Menge verworfen',
      },
      receipts: {
        pickPromptTitle: 'Quantity to receive',
        receiveTarget: 'Receive target',
        receiveQty: 'Receive quantity',
        qtyBtnCaption: 'Received quantity',
        existingLU: 'Existing HU',
        newHU: 'New HU',
        target: 'Empf. Soll',
        picked: 'Ist',
      },
    },
  },
};

export default translations;
