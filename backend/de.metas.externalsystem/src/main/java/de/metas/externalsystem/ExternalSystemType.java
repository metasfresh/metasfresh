package de.metas.externalsystem;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.model.X_ExternalSystem_Config;
import de.metas.externalsystem.process.InvokeAlbertaAction;
import de.metas.externalsystem.process.InvokeAmazonAction;
import de.metas.externalsystem.process.InvokeEbayAction;
import de.metas.externalsystem.process.InvokeGRSSignumAction;
import de.metas.externalsystem.process.InvokeMetasfreshAction;
import de.metas.externalsystem.process.InvokeOtherAction;
import de.metas.externalsystem.process.InvokePCMAction;
import de.metas.externalsystem.process.InvokeSAPAction;
import de.metas.externalsystem.process.InvokeShopware6Action;
import de.metas.externalsystem.process.InvokeWooCommerceAction;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public enum ExternalSystemType implements ReferenceListAwareEnum
{
	Alberta(X_ExternalSystem_Config.TYPE_Alberta, "Alberta", InvokeAlbertaAction.class.getName()),
	Shopware6(X_ExternalSystem_Config.TYPE_Shopware6, "Shopware6", InvokeShopware6Action.class.getName()),
	Ebay(X_ExternalSystem_Config.TYPE_Ebay, "Ebay", InvokeEbayAction.class.getName()),
	RabbitMQ(X_ExternalSystem_Config.TYPE_RabbitMQRESTAPI, "RabbitMQRESTAPI", null),
	Other(X_ExternalSystem_Config.TYPE_Other, "Other", InvokeOtherAction.class.getName()),
	WOO(X_ExternalSystem_Config.TYPE_WooCommerce, "WOO", InvokeWooCommerceAction.class.getName()),
	GRSSignum(X_ExternalSystem_Config.TYPE_GRSSignum, "GRSSignum", InvokeGRSSignumAction.class.getName()),
	LeichUndMehl(X_ExternalSystem_Config.TYPE_LeichMehl, "LeichUndMehl", null),
	SAP(X_ExternalSystem_Config.TYPE_SAP, "SAP", InvokeSAPAction.class.getName()),
	Metasfresh(X_ExternalSystem_Config.TYPE_Metasfresh, "metasfresh", InvokeMetasfreshAction.class.getName()),
	Amazon(X_ExternalSystem_Config.TYPE_Amazon, "Amazon", InvokeAmazonAction.class.getName()),
	PrintClient(X_ExternalSystem_Config.TYPE_PrintingClient, "PrintingClient", null),
	ProCareManagement(X_ExternalSystem_Config.TYPE_ProCareManagement, "ProCareManagement", InvokePCMAction.class.getName())
	;

	@Getter
	private final String code;

	@Getter
	private final String name;

	@Getter
	private final String externalSystemProcessClassName;

	ExternalSystemType(@NonNull final String code, final String name, @Nullable final String externalSystemProcessClassName)
	{
		this.code = code;
		this.name = name;
		this.externalSystemProcessClassName = externalSystemProcessClassName;
	}

	@Nullable
	public static ExternalSystemType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static ExternalSystemType ofCode(@NonNull final String code)
	{
		final ExternalSystemType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ExternalSystemType.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static ExternalSystemType ofCodeOrNameOrNull(@NonNull final String codeOrName)
	{
		return CoalesceUtil.coalesceSuppliers(() -> typesByCode.get(codeOrName), () -> typesByName.get(codeOrName));
	}

	private static final ImmutableMap<String, ExternalSystemType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ExternalSystemType::getCode);
	private static final ImmutableMap<String, ExternalSystemType> typesByName = Maps.uniqueIndex(Arrays.asList(values()), ExternalSystemType::getName);
}
