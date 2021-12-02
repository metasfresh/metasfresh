/*
 * #%L
 * metasfresh-material-cockpit
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

package de.metas.material.cockpit.view;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;

import java.time.Instant;
import java.time.ZoneId;

@Value
public class MainDataRecordIdentifier
{
	/**
	 * @param timeZone needed because we have to know it. Otherwise we might end up at the previous day (23:00 o'clock) instead of the day we are expecting.
	 */
	public static MainDataRecordIdentifier createForMaterial(
			@NonNull final MaterialDescriptor material,
			@NonNull final ZoneId timeZone)
	{
		return MainDataRecordIdentifier.builder()
				.productDescriptor(material)
				.date(TimeUtil.getDay(material.getDate(), timeZone))
				.warehouseId(material.getWarehouseId())
				.build();
	}

	ProductDescriptor productDescriptor;

	Instant date;

	WarehouseId warehouseId;

	@Builder
	public MainDataRecordIdentifier(
			@NonNull final ProductDescriptor productDescriptor,
			@NonNull final Instant date,
			final WarehouseId warehouseId)
	{
		productDescriptor.getStorageAttributesKey().assertNotAllOrOther();
		this.productDescriptor = productDescriptor;
		this.date = date;
		this.warehouseId = warehouseId;
	}

	public IQueryBuilder<I_MD_Cockpit> createQueryBuilder()
	{
		final ProductDescriptor productDescriptor = getProductDescriptor();

		final AttributesKey attributesKey = productDescriptor.getStorageAttributesKey();
		attributesKey.assertNotAllOrOther();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Cockpit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_M_Product_ID, productDescriptor.getProductId())
				.addEqualsFilter(I_MD_Cockpit.COLUMN_AttributesKey, attributesKey.getAsString())
				.addEqualsFilter(I_MD_Cockpit.COLUMN_DateGeneral, TimeUtil.asTimestamp(getDate()))
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_M_Warehouse_ID, NumberUtils.asIntegerOrNull(warehouseId));
	}
}
