const translations = {
<<<<<<< HEAD
  appName: 'metasfresh mobile',
  error: {
    PleaseTryAgain: 'Oops, das sollte nicht passieren',
    network: {
      noResponse: 'Verbindung Fehler',
    },
  },
  general: {
    Product: 'Produkt',
    Locator: 'Lagerort',
    QRCode: 'QR-Code',
    QtyToPick: 'Pick Menge',
    QtyToPick_Total: 'Pick Menge (total)',
    QtyPicked: 'Menge gepickt',
    QtyMoved: 'Menge bewegt',
    QtyToMove: 'Bewegungsmenge',
    QtyRejected: 'Menge verworfen',
    DropToLocator: 'Ziel Lagerort',
    cancelText: 'Abbrechen',
    scanQRCode: 'QR scannen',
    Back: 'Zurück',
    Home: 'Home',
=======
  appName: 'Kommissionierung',
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
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
  },
  login: {
    submitButton: 'Login',
  },
<<<<<<< HEAD
  logout: 'Abmelden',
=======
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
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
<<<<<<< HEAD
  components: {
    BarcodeScannerComponent: {
      scanTextPlaceholder: 'scan...',
    },
  },
=======
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
  activities: {
    scanBarcode: {
      defaultCaption: 'Scan',
      invalidScannedBarcode: 'Code ist ungültig',
    },
<<<<<<< HEAD
    picking: {
      PickingLine: 'Pick Zeile',
      PickHU: 'HU kommissionieren',
      scanQRCode: 'QR scannen',
      notEligibleHUBarcode: 'HU Code passt nicht',
      qtyAboveMax: '%(qtyDiff)s über max', // TODO verify trl
      notPositiveQtyNotAllowed: 'Null oder negative Menge nicht erlaubt', // TODO verify trl
=======
    abortText: 'Verlassen',
    picking: {
      PickingLine: 'Pick Zeile',
      scanHUBarcode: 'Scan HU',
      notEligibleHUBarcode: 'HU Code passt nicht',
      invalidQtyPicked: 'Falsche Menge gepickt',
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
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
<<<<<<< HEAD
      invalidLocatorQRCode: 'Lagerort QR ungültig',
      invalidQtyToMove: 'Bewegungsmenge ungültig',
    },
    confirmButton: {
      default: {
        caption: 'Bestätigen',
=======
    },
    confirmButton: {
      default: {
        caption: 'Confirm',
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
        promptQuestion: 'Bist du sicher?',
        yes: 'Ja',
        no: 'Nein',
      },
<<<<<<< HEAD
      abort: 'Rückgängig',
      notFound: 'Nicht gefunden',
=======
      abort: {
        caption: 'Rückgängig',
      },
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
    },
    mfg: {
      ProductName: 'Produkt',
      target: 'Soll',
      picked: 'Ist',
<<<<<<< HEAD
      generateHUQRCodes: {
        packing: 'Verpackung',
        qtyTUs: 'Anzahl TUs',
        print: 'Drucken',
      },
      issues: {
        target: 'Zuf. Soll',
        picked: 'Ist',
        qtyToIssueTarget: 'Menge Soll',
        qtyToIssueRemaining: 'noch offen',
        qtyIssued: 'Menge Ist',
        qtyRejected: 'Menge verworfen',
        step: {
          name: 'HU einfüllen',
        },
      },
      receipts: {
        qtyToReceiveTarget: 'Sollmenge',
        qtyReceived: 'Produziert',
        qtyToReceive: 'noch offen',
        btnReceiveTarget: 'Gebinde',
        btnReceiveProducts: 'Produzieren',
        existingLU: 'Scan',
        newHU: 'Neues Gebinde',
=======
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
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
        target: 'Empf. Soll',
        picked: 'Ist',
      },
    },
  },
};

export default translations;
