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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
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

@Repository
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

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	@NonNull
	public ShipperConfig getByShipperId(@NonNull final ShipperId shipperId)
	{
		return queryBL.createQueryBuilder(I_Carrier_Config.class)
				.addEqualsFilter(I_Carrier_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.create()
				.firstOnlyOptional()
				.map(this::fromRecord)
				.orElseThrow(() -> new AdempiereException(MSG_NO_SHIPPER_CONFIG_FOUND, shipperId));
	}

	private ShipperConfig fromRecord(@NotNull final I_Carrier_Config carrierConfig)
	{
		final ShipperId shipperId = ShipperId.ofRepoId(carrierConfig.getM_Shipper_ID());
		final I_M_Shipper shipper = shipperDAO.getById(shipperId);
		return ShipperConfig.builder()
				.id(ShipperConfigId.ofRepoId(carrierConfig.getCarrier_Config_ID()))
				.shipperId(shipperId)
				.url(carrierConfig.getBase_url())
				.username(carrierConfig.getUserName())
				.password(carrierConfig.getPassword())
				.clientId(carrierConfig.getClient_Id())
				.clientSecret(carrierConfig.getClient_Secret())
				.trackingUrlTemplate(shipper.getTrackingURL())
				.additionalProperties(buildAdditionalPropertiesMap(carrierConfig))
				.build();
	}

	private static ImmutableMap<String, String> buildAdditionalPropertiesMap(final @NotNull I_Carrier_Config carrierConfig)
	{
		final POInfo poInfo = POInfo.getPOInfo(I_Carrier_Config.Table_Name);
		Check.assumeNotNull(poInfo, "POInfo for {} is not null", I_Carrier_Config.Table_Name);
		return
				poInfo.streamColumns(poInfoColumn -> !COLUMNS_TO_EXCLUDE_FROM_MAPPING.contains(poInfoColumn.getColumnName()))
						.map(POInfoColumn::getColumnName)
						.filter(columnName -> InterfaceWrapperHelper.getValueOrNull(carrierConfig, columnName) != null)
						.collect(ImmutableMap.toImmutableMap(Function.identity(), colName -> InterfaceWrapperHelper.getValueOrNull(carrierConfig, colName)));
	}

}
