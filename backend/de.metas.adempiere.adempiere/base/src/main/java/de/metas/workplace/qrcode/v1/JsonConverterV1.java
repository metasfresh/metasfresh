/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workplace.qrcode.v1;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.util.Check;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.qrcode.WorkplaceQRCode;
import de.metas.workplace.qrcode.WorkplaceQRCodeJsonConverter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonConverterV1
{
	public static final GlobalQRCodeVersion GLOBAL_QRCODE_VERSION = GlobalQRCodeVersion.ofInt(1);

	public static GlobalQRCode toGlobalQRCode(@NonNull final WorkplaceQRCode qrCode)
	{
		return GlobalQRCode.of(WorkplaceQRCodeJsonConverter.GLOBAL_QRCODE_TYPE, GLOBAL_QRCODE_VERSION, toJson(qrCode));
	}

	private static JsonWorkspaceQRCodePayloadV1 toJson(@NonNull final WorkplaceQRCode qrCode)
	{
		return JsonWorkspaceQRCodePayloadV1.builder()
				.workplaceId(qrCode.getWorkplaceId().getRepoId())
				.caption(qrCode.getCaption())
				.build();
	}

	public static WorkplaceQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		Check.assumeEquals(globalQRCode.getVersion(), GLOBAL_QRCODE_VERSION, "QR Code version");
		final JsonWorkspaceQRCodePayloadV1 payload = globalQRCode.getPayloadAs(JsonWorkspaceQRCodePayloadV1.class);
		return fromJson(payload);
	}

	private static WorkplaceQRCode fromJson(@NonNull final JsonWorkspaceQRCodePayloadV1 json)
	{
		return WorkplaceQRCode.builder()
				.workplaceId(WorkplaceId.ofRepoId(json.getWorkplaceId()))
				.caption(json.getCaption())
				.build();
	}
}
