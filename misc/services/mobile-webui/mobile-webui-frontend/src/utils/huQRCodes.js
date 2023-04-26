export const toQRCodeDisplayable = (qrCode) => {
  //
  // Case: null/empty qrCode
  if (!qrCode) {
    return null;
  }
  //
  // Case: possible { code, displayable } QR code object
  else if (typeof qrCode === 'object') {
    if (qrCode.displayable) {
      return qrCode.displayable;
    } else if (qrCode.code) {
      return parseQRCodeString(qrCode.code).displayable;
    } else {
      throw 'Invalid QR Code: ' + JSON.stringify(qrCode);
    }
  }
  //
  // Case: possible string
  else if (!Array.isArray(qrCode)) {
    return parseQRCodeString(`${qrCode}`).displayable;
  }

  //
  // Unknown QR code format
  throw 'Invalid QR Code: ' + JSON.stringify(qrCode);
};

export const toQRCodeString = (qrCode) => {
  //
  // Case: null/empty qrCode
  if (!qrCode) {
    return '';
  }
  //
  // Case: possible { code, displayable } QR code object
  else if (typeof qrCode === 'object') {
    if (qrCode.code) {
      return qrCode.code;
    } else {
      throw 'Invalid QR Code because the "code" field is missing: ' + JSON.stringify(qrCode);
    }
  }
  //
  // Case: possible string
  else if (!Array.isArray(qrCode)) {
    return `${qrCode}`;
  }

  //
  // Unknonw QR code format
  throw 'Invalid QR Code: ' + JSON.stringify(qrCode);
};

export const toQRCodeObject = (qrCode) => {
  //
  // Case: null/empty qrCode
  if (!qrCode) {
    return null;
  }
  //
  // Case: possible { code, displayable } QR code object
  else if (typeof qrCode === 'object') {
    if (qrCode.code) {
      return {
        code: qrCode.code,
        displayable: qrCode.displayable || toQRCodeDisplayable(qrCode.code),
      };
    } else {
      throw 'Invalid QR Code because the "code" field is missing: ' + JSON.stringify(qrCode);
    }
  }
  //
  // Case: possible string
  else if (!Array.isArray(qrCode)) {
    const code = `${qrCode}`;
    return {
      code,
      displayable: toQRCodeDisplayable(code),
    };
  }

  //
  // Unknonw QR code format
  throw 'Invalid QR Code: ' + JSON.stringify(qrCode);
};

// NOTE to dev: keep in sync with:
// de.metas.global_qrcodes.GlobalQRCode.ofString
// de.metas.handlingunits.qrcodes.model.HUQRCode
const SEPARATOR = '#';
export const parseQRCodeString = (string) => {
  let remainingString = string;

  //
  // Type
  //var type;
  {
    const idx = remainingString.indexOf(SEPARATOR);
    if (idx <= 0) {
      throw 'Invalid global QR code(1): ' + string;
    }
    //type = remainingString.substring(0, idx);
    remainingString = remainingString.substring(idx + 1);
  }

  //
  // Version
  //var version;
  {
    const idx = remainingString.indexOf(SEPARATOR);
    if (idx <= 0) {
      throw 'Invalid global QR code(2): ' + string;
    }
    //version = remainingString.substring(0, idx);
    remainingString = remainingString.substring(idx + 1);
  }

  //
  // Payload
  var displayable;
  {
    const payload = JSON.parse(remainingString);
    const id = payload.id ? String(payload.id) : null;

    // Displayable code
    displayable = id;
    {
      const idx = id?.lastIndexOf('-');
      if (idx > 0) {
        displayable = id.substring(idx + 1);
      } else if (payload.caption) {
        displayable = payload.caption;
      }
    }
  }

  //console.log('!!!!!!!! RETURNING', { code: string, displayable, remainingString });
  return { code: string, displayable };
};
