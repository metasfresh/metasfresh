/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.config.qrcode;

import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class ExternalSystemConfigQRCode
{
	@NonNull IExternalSystemChildConfigId childConfigId;

	public static boolean equals(@Nullable final ExternalSystemConfigQRCode o1, @Nullable final ExternalSystemConfigQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	public String toGlobalQRCodeJsonString() {return ExternalSystemConfigQRCodeJsonConverter.toGlobalQRCodeJsonString(this);}

	public static ExternalSystemConfigQRCode ofGlobalQRCode(@NonNull final GlobalQRCode globalQRCode) {return ExternalSystemConfigQRCodeJsonConverter.fromGlobalQRCode(globalQRCode);}

	public PrintableQRCode toPrintableQRCode()
	{
		return PrintableQRCode.builder()
				.qrCode(toGlobalQRCodeJsonString())
				.bottomText(getCaption())
				.build();
	}

	@NonNull
	public String getCaption()
	{
		return childConfigId.getType() + "-" + childConfigId.getRepoId();
	}
}
