const translations = {
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
    QtyPicked: 'Qty picked',
    QtyMoved: 'Qty moved',
    QtyToMove: 'Qty to move',
    QtyRejected: 'Qty Rejected',
    DropToLocator: 'Drop to locator',
    cancelText: 'Cancel',
    scanQRCode: 'Scan QR',
    Back: 'Back',
    Home: 'Home',
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
      PickHU: 'Pick HU',
      scanQRCode: 'Scan QR',
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
      scanLocator: 'Scan drop to Locator',
      invalidLocatorQRCode: 'Invalid locator QR code',
      invalidQtyToMove: 'Invalid qty to move',
    },
    confirmButton: {
      default: {
        caption: 'Confirm',
        promptQuestion: 'Are you sure?',
        yes: 'Yes',
        no: 'No',
      },
      abort: 'Abort',
      notFound: 'Not found',
    },
    mfg: {
      ProductName: 'Product Name',
      target: 'To issue',
      picked: 'Issued',
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
        existingLU: 'Existing HU',
        newHU: 'New HU',
        target: 'To receive',
        picked: 'Received',
      },
    },
  },
};

export default translations;
