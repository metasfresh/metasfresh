/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.common.util.StringUtils;
import de.metas.i18n.AdMessageKey;
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Carrier_Config;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

/**
 * Repository Tables: Carrier_Config
 * Repository Cluster: ShipperConfigRepository, ShipperRepository
 */
@Repository
@RequiredArgsConstructor
public class ShipperConfigRepository
{
	private final static AdMessageKey MSG_NO_SHIPPER_CONFIG_FOUND = AdMessageKey.of("de.metas.shipper.gateway.commons.config.NoShipperConfigFound");
	private final static ImmutableSet<String> COLUMNS_TO_EXCLUDE_FROM_MAPPING = ImmutableSet.of(
			//already mapped or irrelevant columns
			I_Carrier_Config.COLUMNNAME_M_Shipper_ID,
			I_Carrier_Config.COLUMNNAME_Carrier_Config_ID,
			I_Carrier_Config.COLUMNNAME_UserName,
			I_Carrier_Config.COLUMNNAME_Client_Id,
			I_Carrier_Config.COLUMNNAME_Client_Secret,
			I_Carrier_Config.COLUMNNAME_Password,
			I_Carrier_Config.COLUMNNAME_Base_url,

			//metasfresh specific columns
			I_Carrier_Config.COLUMNNAME_Created,
			I_Carrier_Config.COLUMNNAME_CreatedBy,
			I_Carrier_Config.COLUMNNAME_IsActive,
			I_Carrier_Config.COLUMNNAME_Updated,
			I_Carrier_Config.COLUMNNAME_UpdatedBy,
			I_Carrier_Config.COLUMNNAME_AD_Org_ID,
			I_Carrier_Config.COLUMNNAME_AD_Client_ID);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ShipperRepository shipperRepository;

	@NonNull private final CCache<Integer, ImmutableMap<ShipperId, ShipperConfig>> cache = CCache.<Integer, ImmutableMap<ShipperId, ShipperConfig>>builder()
			.tableName(I_Carrier_Config.Table_Name)
			.additionalTableNameToResetFor(I_M_Shipper.Table_Name)
			.build();

	@NonNull
	public ShipperConfig getByShipperId(@NonNull final ShipperId shipperId)
	{
		final ShipperConfig config = getMap().get(shipperId);
		if (config == null)
		{
			throw new AdempiereException(MSG_NO_SHIPPER_CONFIG_FOUND, shipperId);
		}
		return config;
	}

	/**
	 * {@code Carrier_Config.IsSelectionRules} for the given shipper — does nShift resolve the carrier via its
	 * selection rules (the explicit carrier product is not authoritative)? Defaults to the column default {@code 'Y'}
	 * (rules ON) when the shipper has no {@code Carrier_Config} row.
	 */
	public boolean isSelectionRules(@NonNull final ShipperId shipperId)
	{
		final ShipperConfig config = getMap().get(shipperId);
		// no config row → column default 'Y' (rules ON)
		return config == null || config.isSelectionRules();
	}

	@NonNull
	private ImmutableMap<ShipperId, ShipperConfig> getMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveMap);
	}

	@NonNull
	private ImmutableMap<ShipperId, ShipperConfig> retrieveMap()
	{
		final ImmutableList<ShipperConfig> configs = queryBL.createQueryBuilder(I_Carrier_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList());
		return Maps.uniqueIndex(configs, ShipperConfig::getShipperId);
	}

	private ShipperConfig fromRecord(@NotNull final I_Carrier_Config carrierConfig)
	{
		final ShipperId shipperId = ShipperId.ofRepoId(carrierConfig.getM_Shipper_ID());
		final Shipper shipper = shipperRepository.getById(shipperId);
		return ShipperConfig.builder()
				.id(ShipperConfigId.ofRepoId(carrierConfig.getCarrier_Config_ID()))
				.shipperId(shipperId)
				.url(carrierConfig.getBase_url())
				.username(carrierConfig.getUserName())
				.password(carrierConfig.getPassword())
				.clientId(carrierConfig.getClient_Id())
				.clientSecret(carrierConfig.getClient_Secret())
				.trackingUrlTemplate(shipper.getTrackingUrl())
				.additionalProperties(buildAdditionalPropertiesMap(carrierConfig))
				.build();
	}

	private static ImmutableMap<String, String> buildAdditionalPropertiesMap(final @NotNull I_Carrier_Config carrierConfig)
	{
		final POInfo poInfo = POInfo.getPOInfo(I_Carrier_Config.Table_Name);
		Check.assumeNotNull(poInfo, "POInfo for {} is not null", I_Carrier_Config.Table_Name);
		return poInfo.streamColumns(poInfoColumn -> !COLUMNS_TO_EXCLUDE_FROM_MAPPING.contains(poInfoColumn.getColumnName()))
				.map(POInfoColumn::getColumnName)
				.filter(columnName -> InterfaceWrapperHelper.getValueOrNull(carrierConfig, columnName) != null)
				.collect(ImmutableMap.toImmutableMap(Function.identity(), colName -> toPropertyString(InterfaceWrapperHelper.getValueOrNull(carrierConfig, colName))));
	}

	private static String toPropertyString(@NotNull final Object value)
	{
		// PO layer stores YesNo columns as Boolean; convert to "Y"/"N" for consistent string-based lookup
		if (value instanceof Boolean)
		{
			return StringUtils.ofBooleanNonNull((Boolean) value);
		}
		return String.valueOf(value);
	}

}
