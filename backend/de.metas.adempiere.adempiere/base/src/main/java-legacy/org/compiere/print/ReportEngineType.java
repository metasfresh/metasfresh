/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.compiere.print;

import de.metas.process.AdProcessId;
import lombok.Getter;
import org.compiere.model.I_C_DunningRunEntry;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_InOut;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;

/*************************************************************************/

public enum ReportEngineType
{
	ORDER(I_C_Order.Table_Name, I_C_Order.COLUMNNAME_C_Order_ID),
	SHIPMENT(I_M_InOut.Table_Name, I_M_InOut.COLUMNNAME_M_InOut_ID),
	INVOICE(I_C_Invoice.Table_Name, I_C_Invoice.COLUMNNAME_C_Invoice_ID),
	PROJECT(I_C_Project.Table_Name, I_C_Project.COLUMNNAME_C_Project_ID),
	REMITTANCE(null, null),
	CHECK(null, null),
	DUNNING(I_C_DunningRunEntry.Table_Name, I_C_DunningRunEntry.COLUMNNAME_C_DunningRunEntry_ID),
	MANUFACTURING_ORDER(I_PP_Order.Table_Name, I_PP_Order.COLUMNNAME_PP_Order_ID),
	DISTRIBUTION_ORDER(I_DD_Order.Table_Name, I_DD_Order.COLUMNNAME_DD_Order_ID);

	@Getter
	@Nullable
	private final String baseTableName;
	@Getter
	@Nullable
	private final String keyColumnName;

	ReportEngineType(
			@Nullable final String baseTableName,
			@Nullable final String keyColumnName)
	{
		this.baseTableName = baseTableName;
		this.keyColumnName = keyColumnName;
	}

	@Nullable
	public static final ReportEngineType ofProcessIdOrNull(final AdProcessId adProcessId)
	{
		if (adProcessId.getRepoId() == 110)
		{
			return ReportEngineType.ORDER;
		}
		else if (adProcessId.getRepoId() == 116)
		{
			return ReportEngineType.INVOICE;
		}
		else if (adProcessId.getRepoId() == 117)
		{
			return ReportEngineType.SHIPMENT;
		}
		else if (adProcessId.getRepoId() == 217)
		{
			return ReportEngineType.PROJECT;
		}
		else if (adProcessId.getRepoId() == 159)
		{
			return ReportEngineType.DUNNING;
		}
		// else if(adProcessId == 313) // Payment // => will be handled on upper level
		// return;
		if (adProcessId.getRepoId() == 53028) // Rpt PP_Order
		{
			return ReportEngineType.MANUFACTURING_ORDER;
		}
		if (adProcessId.getRepoId() == 53044) // Rpt DD_Order
		{
			return ReportEngineType.DISTRIBUTION_ORDER;
		}
		else
		{
			return null;
		}
	}

}
