/*
 * #%L
 * ic114
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import { QRCODE_SEPARATOR } from './common';

export const QRCODE_TYPE_WORKPLACE = 'WORKPLACE';

export const parseWorkplaceQRCodeString = (string) => {
  let remainingString = string;
  //
  // Type
  let type;
  {
    const idx = remainingString.indexOf(QRCODE_SEPARATOR);
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
    const idx = remainingString.indexOf(QRCODE_SEPARATOR);
    if (idx <= 0) {
      throw 'Invalid global QR code(2): ' + string;
    }
    version = remainingString.substring(0, idx);
    remainingString = remainingString.substring(idx + 1);
  }

  const payload = JSON.parse(remainingString);

  let payloadParsed;
  if (type === QRCODE_TYPE_WORKPLACE && version === '1') {
    payloadParsed = parseQRCodePayload_Workplace_v1(payload);
  } else {
    throw 'Invalid global QR code(3): ' + string;
  }

  return { ...payloadParsed, code: string };
};

const parseQRCodePayload_Workplace_v1 = (payload) => {
  return { workplaceId: payload.workplaceId, displayable: payload.caption };
};
