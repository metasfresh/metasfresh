const translations = {
  appName: 'metasfresh mobile',
  general: {
    Product: 'Product',
    Locator: 'Locator',
    Barcode: 'Barcode',
    QtyToPick: 'Qty to pick',
    QtyPicked: 'Qty picked',
    QtyMoved: 'Qty moved',
    QtyToMove: 'Qty to move',
    QtyRejected: 'Qty Rejected',
    DropToLocator: 'Drop to locator',
    PleaseTryAgain: 'Please try again',
    cancelText: 'Cancel',
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
      invalidLocatorBarcode: 'Invalid locator barcode',
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
      issues: {
        target: 'To issue',
        picked: 'Issued',
        qtyToIssue: 'Qty to issue',
        qtyIssued: 'Qty Issued',
        qtyRejected: 'Qty Rejected',
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
