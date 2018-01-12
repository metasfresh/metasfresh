package de.metas.material.cockpit.view;

import java.util.Date;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
@Builder
public class MainDataRecordIdentifier
{
	public static MainDataRecordIdentifier createForMaterial(
			@NonNull final MaterialDescriptor material)
	{
		final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.builder()
				.productDescriptor(material)
				.date(TimeUtil.getDay(material.getDate()))
				.plantId(0)
				.build();
		return identifier;
	}

	ProductDescriptor productDescriptor;

	Date date;

	/**
	 * Optional, a value <= 0 means "none"
	 */
	int plantId;

	public MainDataRecordIdentifier(
			@NonNull final ProductDescriptor productDescriptor,
			@NonNull final Date date,
			int plantId)
	{
		productDescriptor.getStorageAttributesKey().assertNotAllOrOther();
		this.productDescriptor = productDescriptor;
		this.date = date;
		this.plantId = plantId;
	}

	public IQueryBuilder<I_MD_Cockpit> createQueryBuilder()
	{
		final ProductDescriptor productDescriptor = getProductDescriptor();

		final AttributesKey attributesKey = productDescriptor.getStorageAttributesKey();
		attributesKey.assertNotAllOrOther();

		final IQueryBuilder<I_MD_Cockpit> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Cockpit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Cockpit.COLUMN_M_Product_ID, productDescriptor.getProductId())
				.addEqualsFilter(I_MD_Cockpit.COLUMN_AttributesKey, attributesKey.getAsString())
				.addEqualsFilter(I_MD_Cockpit.COLUMN_DateGeneral, getDate());

		if (getPlantId() > 0)
		{
			queryBuilder.addEqualsFilter(I_MD_Cockpit.COLUMN_PP_Plant_ID, getPlantId());
		}
		else
		{
			queryBuilder.addEqualsFilter(I_MD_Cockpit.COLUMN_PP_Plant_ID, null);
		}

		return queryBuilder;
	}
}
