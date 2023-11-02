const SEPARATOR = '#';
export const parseWorkplaceQRCodeString = (string) => {
  let remainingString = string;
  //
  // Type
  let type;
  {
    const idx = remainingString.indexOf(SEPARATOR);
    if (idx <= 0) {
      throw 'Invalid global QR code(1): ' + string;
    }
    type = remainingString.substring(0, idx);
    remainingString = remainingString.substring(idx + 1);
  }
  //
  // Version
  let version;
  {
    const idx = remainingString.indexOf(SEPARATOR);
    if (idx <= 0) {
      throw 'Invalid global QR code(2): ' + string;
    }
    version = remainingString.substring(0, idx);
    remainingString = remainingString.substring(idx + 1);
  }

  const payload = JSON.parse(remainingString);

  let payloadParsed;
  if (type === 'WORKPLACE' && version === '1') {
    payloadParsed = parseQRCodePayload_Workplace_v1(payload);
  } else {
    throw 'Invalid global QR code(3): ' + string;
  }

  return { ...payloadParsed, code: string };
};

const parseQRCodePayload_Workplace_v1 = (payload) => {
  return { workplaceId: payload.workplaceId, displayable: payload.caption };
};
