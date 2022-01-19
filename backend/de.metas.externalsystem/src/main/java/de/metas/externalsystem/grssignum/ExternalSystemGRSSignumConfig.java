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

package de.metas.externalsystem.grssignum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemGRSSignumConfig implements IExternalSystemChildConfig
{
	@NonNull
	ExternalSystemGRSSignumConfigId id;

	@NonNull
	ExternalSystemParentConfigId parentId;

	@NonNull
	String value;

	@NonNull
	String baseUrl;

	@NonNull
	String tenantId;

	@Nullable
	String camelHttpResourceAuthKey;

	@Nullable
	String authToken;

	boolean syncBPartnersToRestEndpoint;

	boolean autoSendVendors;

	boolean autoSendCustomers;

	boolean syncHUsOnMaterialReceipt;

	boolean syncHUsOnProductionReceipt;

	@Builder
	ExternalSystemGRSSignumConfig(
			@NonNull final ExternalSystemGRSSignumConfigId id,
			@NonNull final ExternalSystemParentConfigId parentId,
			@NonNull final String value,
			@NonNull final String baseUrl,
			@NonNull final String tenantId,
			@Nullable final String camelHttpResourceAuthKey,
			@Nullable final String authToken,
			final boolean syncBPartnersToRestEndpoint,
			final boolean autoSendVendors,
			final boolean autoSendCustomers,
			final boolean syncHUsOnMaterialReceipt,
			final boolean syncHUsOnProductionReceipt)
	{
		this.id = id;
		this.parentId = parentId;
		this.value = value;
		this.baseUrl = baseUrl;
		this.tenantId = tenantId;
		this.camelHttpResourceAuthKey = camelHttpResourceAuthKey;
		this.authToken = authToken;
		this.syncBPartnersToRestEndpoint = syncBPartnersToRestEndpoint;
		this.autoSendVendors = autoSendVendors;
		this.autoSendCustomers = autoSendCustomers;
		this.syncHUsOnMaterialReceipt = syncHUsOnMaterialReceipt;
		this.syncHUsOnProductionReceipt = syncHUsOnProductionReceipt;
	}

	public static ExternalSystemGRSSignumConfig cast(@NonNull final IExternalSystemChildConfig childConfig)
	{
		return (ExternalSystemGRSSignumConfig)childConfig;
	}

	@JsonIgnore
	public boolean isHUsSyncEnabled()
	{
		return syncHUsOnMaterialReceipt || syncHUsOnProductionReceipt;
	}
}
