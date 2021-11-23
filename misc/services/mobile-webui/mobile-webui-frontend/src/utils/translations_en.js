const translations = {
<<<<<<< HEAD
  appName: 'metasfresh mobile',
  error: {
    PleaseTryAgain: 'Please try again',
    network: {
      noResponse: 'Connection error',
    },
  },
  general: {
    Product: 'Product',
    Locator: 'Locator',
    QRCode: 'QR Code',
    QtyToPick: 'Qty to pick',
    QtyToPick_Total: 'Qty to pick (total)',
    QtyPicked: 'Qty picked',
    QtyMoved: 'Qty moved',
    QtyToMove: 'Qty to move',
    QtyRejected: 'Qty Rejected',
    DropToLocator: 'Drop to locator',
    cancelText: 'Cancel',
    scanQRCode: 'Scan QR',
    Back: 'Back',
    Home: 'Home',
=======
  appName: 'Mobile UI',
  general: {
    Product: 'Product',
    Locator: 'Locator',
    Barcode: 'Barcode',
    QtyToPick: 'Qty to pick',
    QtyPicked: 'Qty picked',
    QtyMoved: 'Qty moved',
    QtyToMove: 'Qty to move',
    DropToLocator: 'Drop to locator',
    PleaseTryAgain: 'Please try again',
    cancelText: 'Cancel',
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
  },
  login: {
    submitButton: 'Login',
  },
<<<<<<< HEAD
  logout: 'Logout',
=======
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
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
      invalidScannedBarcode: 'Scanned code is invalid',
    },
<<<<<<< HEAD
    picking: {
      PickingLine: 'Picking Line',
      PickHU: 'Pick HU',
      scanQRCode: 'Scan QR',
      notEligibleHUBarcode: 'HU barcode not matching',
      qtyAboveMax: '%(qtyDiff)s above max',
      notPositiveQtyNotAllowed: 'Zero or negative qty not allowed',
=======
    abortText: 'Abort',
    picking: {
      PickingLine: 'Picking Line',
      scanHUBarcode: 'Scan HU',
      notEligibleHUBarcode: 'HU barcode not matching',
      invalidQtyPicked: 'Invalid qty picked',
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
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
<<<<<<< HEAD
      scanLocator: 'Scan drop to Locator',
      invalidLocatorQRCode: 'Invalid locator QR code',
      invalidQtyToMove: 'Invalid qty to move',
=======
      scanLocator: 'Scan drop from Locator',
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
    },
    confirmButton: {
      default: {
        caption: 'Confirm',
        promptQuestion: 'Are you sure?',
        yes: 'Yes',
        no: 'No',
      },
<<<<<<< HEAD
      abort: 'Abort',
      notFound: 'Not found',
=======
      abort: {
        caption: 'Abort',
      },
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
    },
    mfg: {
      ProductName: 'Product Name',
      target: 'To issue',
      picked: 'Issued',
<<<<<<< HEAD
      generateHUQRCodes: {
        packing: 'Packing',
        qtyTUs: 'TUs',
        print: 'Print',
      },
      issues: {
        target: 'To issue',
        picked: 'Issued',
        qtyToIssueTarget: 'Qty to Issue Target',
        qtyToIssueRemaining: 'Qty to Issue',
        qtyIssued: 'Qty Issued',
        qtyRejected: 'Qty Rejected',
        step: {
          name: 'Issue HU',
        },
      },
      receipts: {
        qtyToReceiveTarget: 'Qty to Receive Target',
        qtyReceived: 'Qty Received',
        qtyToReceive: 'Qty to Receive',
        btnReceiveTarget: 'Receive target',
        btnReceiveProducts: 'Receive products',
=======
      issues: {
        target: 'To issue',
        picked: 'Issued',
        qtyToIssue: 'Qty to issue',
        qtyIssued: 'Qty Issued',
        qtyRejected: 'Qty Rejected',
      },
      receipts: {
        pickPromptTitle: 'Quantity to receive',
        receiveTarget: 'Receive target',
        receiveQty: 'Receive quantity',
        qtyBtnCaption: 'Received quantity',
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
        existingLU: 'Existing HU',
        newHU: 'New HU',
        target: 'To receive',
        picked: 'Received',
      },
    },
  },
};

export default translations;
