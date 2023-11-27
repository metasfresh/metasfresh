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

package de.metas.workplace.qrcode;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class WorkplaceQRCode
{
	@NonNull WorkplaceId workplaceId;
	@NonNull String caption;

	public static boolean equals(@Nullable final WorkplaceQRCode o1, @Nullable final WorkplaceQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	public static WorkplaceQRCode ofWorkplace(final Workplace workplace)
	{
		return builder()
				.workplaceId(workplace.getId())
				.caption(workplace.getName())
				.build();
	}

	public static WorkplaceQRCode ofGlobalQRCodeJsonString(final String json) {return WorkplaceQRCodeJsonConverter.fromGlobalQRCodeJsonString(json);}

	public static WorkplaceQRCode ofGlobalQRCode(final GlobalQRCode globalQRCode) {return WorkplaceQRCodeJsonConverter.fromGlobalQRCode(globalQRCode);}

	public String toGlobalQRCodeJsonString() {return WorkplaceQRCodeJsonConverter.toGlobalQRCodeJsonString(this);}

	public PrintableQRCode toPrintableQRCode()
	{
		return PrintableQRCode.builder()
				.qrCode(toGlobalQRCodeJsonString())
				.bottomText(caption)
				.build();
	}

	public static boolean isTypeMatching(@NonNull final GlobalQRCode globalQRCode)
	{
		return WorkplaceQRCodeJsonConverter.isTypeMatching(globalQRCode);
	}
}
