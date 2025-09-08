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

package de.metas.externalsystem.config.qrcode.v1;

import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.config.qrcode.ExternalSystemConfigQRCode;
import de.metas.externalsystem.config.qrcode.ExternalSystemConfigQRCodeJsonConverter;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfigId;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigId;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfigId;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

public class JsonConverterV1
{
	public static final GlobalQRCodeVersion GLOBAL_QRCODE_VERSION = GlobalQRCodeVersion.ofInt(1);

	public static GlobalQRCode toGlobalQRCode(@NonNull final ExternalSystemConfigQRCode qrCode)
	{
		return GlobalQRCode.of(ExternalSystemConfigQRCodeJsonConverter.GLOBAL_QRCODE_TYPE, GLOBAL_QRCODE_VERSION, toJson(qrCode));
	}

	private static JsonPayload toJson(@NonNull final ExternalSystemConfigQRCode qrCode)
	{
		final IExternalSystemChildConfigId childConfigId = qrCode.getChildConfigId();

		return JsonPayload.builder()
				.externalSystemType(childConfigId.getType().getCode())
				.childConfigId(childConfigId.getRepoId())
				.build();
	}

	public static ExternalSystemConfigQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		Check.assumeEquals(globalQRCode.getVersion(), GLOBAL_QRCODE_VERSION, "QR Code version");
		final JsonPayload payload = globalQRCode.getPayloadAs(JsonPayload.class);
		return fromJson(payload);
	}

	private static ExternalSystemConfigQRCode fromJson(@NonNull final JsonPayload json)
	{
		final ExternalSystemType externalSystemType = ExternalSystemType.ofCode(json.getExternalSystemType());
		final int repoId = json.getChildConfigId();
		return ExternalSystemConfigQRCode.builder()
				.childConfigId(toExternalSystemChildConfigId(externalSystemType, repoId))
				.build();
	}

	private static IExternalSystemChildConfigId toExternalSystemChildConfigId(final ExternalSystemType externalSystemType, final int repoId)
	{
		switch (externalSystemType)
		{
			case Alberta:
				return ExternalSystemAlbertaConfigId.ofRepoId(repoId);
			case Shopware6:
				return ExternalSystemShopware6ConfigId.ofRepoId(repoId);
			// case Other:
			// 	return ExternalSystemOtherConfigId.ofRepoId(repoId);
			case RabbitMQ:
				return ExternalSystemRabbitMQConfigId.ofRepoId(repoId);
			case WOO:
				return ExternalSystemWooCommerceConfigId.ofRepoId(repoId);
			case GRSSignum:
				return ExternalSystemGRSSignumConfigId.ofRepoId(repoId);
			case LeichUndMehl:
				return ExternalSystemLeichMehlConfigId.ofRepoId(repoId);
			default:
				throw new AdempiereException("Unsupported externalSystemType: " + externalSystemType);
		}
	}

	//
	//
	//
	@Value
	@Builder
	@Jacksonized
	public static class JsonPayload
	{
		@NonNull String externalSystemType;
		int childConfigId;
	}
}
