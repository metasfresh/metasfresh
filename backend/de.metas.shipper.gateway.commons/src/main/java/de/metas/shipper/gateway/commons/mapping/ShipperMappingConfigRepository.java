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

package de.metas.shipper.gateway.commons.mapping;

import com.google.common.annotations.VisibleForTesting;
import de.metas.cache.CCache;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Shipper_Mapping_Config;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
public class ShipperMappingConfigRepository
{
	@VisibleForTesting
	public static ShipperMappingConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ShipperMappingConfigRepository();
	}

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ShipperMappingConfigList> cache = CCache.<Integer, ShipperMappingConfigList>builder()
			.tableName(I_M_Shipper_Mapping_Config.Table_Name)
			.build();

	public ShipperMappingConfigList getByShipperId(@NonNull final ShipperId shipperId)
	{
		return getList().subsetOf(shipperId);
	}


	private ShipperMappingConfigList getList()
	{
		//noinspection DataFlowIssue
		return cache.getOrLoad(0, this::retrieveList);
	}

	private ShipperMappingConfigList retrieveList()
	{
		return ShipperMappingConfigList.ofCollection(queryBL.createQueryBuilder(I_M_Shipper_Mapping_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(ShipperMappingConfigRepository::fromRecord)
				.collect(Collectors.toList()));
	}

	private static ShipperMappingConfig fromRecord(@NonNull final I_M_Shipper_Mapping_Config record)
	{
		return ShipperMappingConfig.builder()
				.id(ShipperMappingConfigId.ofRepoId(record.getM_Shipper_Mapping_Config_ID()))
				.shipperId(ShipperId.ofRepoId(record.getM_Shipper_ID()))
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.carrierProductId(CarrierProductId.ofRepoIdOrNull(record.getCarrier_Product_ID()))
				.attributeType(AttributeType.ofCode(record.getMappingAttributeType()))
				.attributeKey(record.getMappingAttributeKey())
				.attributeValue(AttributeValue.ofCode(record.getMappingAttributeValue()))
				.groupKey(record.getMappingGroupKey())
				.mappingRule(MappingRule.ofNullableCode(record.getMappingRule()))
				.mappingRuleValue(record.getMappingRuleValue())
				.build();
	}
}
