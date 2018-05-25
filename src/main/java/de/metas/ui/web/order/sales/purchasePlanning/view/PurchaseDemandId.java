package de.metas.ui.web.order.sales.purchasePlanning.view;

import org.adempiere.util.Check;

import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class PurchaseDemandId
{
	public static PurchaseDemandId ofTableAndRecordId(final String tableName, final int recordId)
	{
		return new PurchaseDemandId(tableName, recordId);
	}

	String tableName;
	int recordId;

	public PurchaseDemandId(final String tableName, final int recordId)
	{
		Check.assumeNotEmpty(tableName, "tableName is not empty");
		Check.assumeGreaterThanZero(recordId, "recordId");

		this.tableName = tableName;
		this.recordId = recordId;
	}

}
