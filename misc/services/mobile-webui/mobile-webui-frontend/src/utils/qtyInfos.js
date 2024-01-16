export const qtyInfos = {
  of: ({ qty, notValidMessage }) => ({
    qtyStr: qty != null ? `${qty}` : null,
    qty: qty != null ? parseFloat(qty) : null,
    isQtyValid: !notValidMessage,
    notValidMessage,
  }),

  invalidOf: ({ qtyInputString, prevQtyInfo }) => ({
    qtyStr: qtyInputString,
    qty: prevQtyInfo?.qty ?? 0,
    isQtyValid: false,
    notValidMessage: prevQtyInfo?.notValidMessage ?? null, // preserve last notValidMessage
  }),

  invalidOfNumber: (qty) => ({
    qtyStr: qty != null ? `${qty}` : null,
    qty: qty != null ? parseFloat(qty) : null,
    isQtyValid: false,
    notValidMessage: null,
  }),

  isValid: (qtyInfo) => qtyInfo && qtyInfo.isQtyValid,

  toNumberOrString: (qtyObj) => {
    if (!qtyObj) {
      return null;
    }
    // QtyInfo data structure
    else if (typeof qtyObj === 'object') {
      const qtyInfo = qtyObj;
      return qtyInfo.qty ?? qtyInfo.qtyStr;
    }
    // Case: possible string
    else if (!Array.isArray(qtyObj)) {
      return `${qtyObj}`;
    } else {
      throw 'Invalid qtyObj: ' + JSON.stringify(qtyObj);
    }
  },
};
