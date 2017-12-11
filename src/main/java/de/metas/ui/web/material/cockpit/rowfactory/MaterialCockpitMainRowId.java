package de.metas.ui.web.material.cockpit.rowfactory;

import java.sql.Timestamp;

import org.compiere.util.TimeUtil;

import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
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
public class MaterialCockpitMainRowId
{

	public static MaterialCockpitMainRowId createInstanceForDataRecord(@NonNull final I_X_MRP_ProductInfo_Detail_MV dataRecord)
	{
		return new MaterialCockpitMainRowId(
				dataRecord.getM_Product_ID(),
				TimeUtil.getDay(dataRecord.getDateGeneral()));
	}

	public static MaterialCockpitMainRowId createPlainInstance(final int productId, @NonNull final Timestamp date)
	{
		return new MaterialCockpitMainRowId(productId, date);
	}

	int productId;
	Timestamp date;

	private MaterialCockpitMainRowId(final int productId, @NonNull final Timestamp date)
	{
		this.productId = productId;
		this.date = date;
	}



}
