/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.amazon;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class ExternalSystemAmazonConfig implements IExternalSystemChildConfig
{
	@NonNull
	ExternalSystemAmazonConfigId id;
	@NonNull
	ExternalSystemParentConfigId parentId;
	@NonNull
	String value;
	@NonNull
	String name;
	@NonNull
	String basePath;
	@NonNull
	String accessKeyId;
	@NonNull
	String clientSecret;
	@NonNull
	String lwaEndpoint;
	@NonNull
	String secretKey;
	@NonNull
	String refreshToken;
	@NonNull
	String regionName;
	@NonNull
	String clientId;
	@NonNull
	String roleArn;
	boolean active;
	boolean debugProtocol;

	@Builder(toBuilder = true)
	public ExternalSystemAmazonConfig(final @NonNull ExternalSystemAmazonConfigId id,
			final @NonNull ExternalSystemParentConfigId parentId,
			final @NonNull String value,
			final @NonNull String name,
			final @NonNull String basePath,
			final @NonNull String accessKeyId,
			final @NonNull String clientId,
			final @NonNull String clientSecret,
			final @NonNull String lwaEndpoint,
			final @NonNull String secretKey,
			final @NonNull String refreshToken,
			final @NonNull String regionName,
			final @NonNull String roleArn,
			final boolean active,
			final boolean debugProtocol)
	{
		this.id = id;
		this.parentId = parentId;
		this.value=value;
		this.name = name;
		this.basePath = basePath;
		this.accessKeyId = accessKeyId;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.lwaEndpoint = lwaEndpoint;
		this.secretKey = secretKey;
		this.refreshToken = refreshToken;
		this.regionName = regionName;
		this.roleArn = roleArn;
		this.active = active;
		this.debugProtocol = debugProtocol;
	}

	public static ExternalSystemAmazonConfig cast(@NonNull final IExternalSystemChildConfig childConfig)
	{
		return (ExternalSystemAmazonConfig)childConfig;
	}

}
