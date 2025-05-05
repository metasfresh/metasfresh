/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;

public class TableRecordReference_StepDefUtil
{

	private final C_OrderLine_StepDefData orderLineTable;
	private final M_InOutLine_StepDefData inoutLineTable;
	private final C_Flatrate_Term_StepDefData contractTable;

	public TableRecordReference_StepDefUtil(
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_InOutLine_StepDefData inoutLineTable,
			@NonNull final C_Flatrate_Term_StepDefData contractTable)
	{
		this.orderLineTable = orderLineTable;
		this.inoutLineTable = inoutLineTable;
		this.contractTable = contractTable;
	}

	@NonNull
	public TableRecordReference getTableRecordReferenceFromIdentifier(@NonNull final String identifier, @NonNull final String tableName)
	{
		switch (tableName)
		{
			case I_C_OrderLine.Table_Name:
				final I_C_OrderLine orderLine = orderLineTable.get(identifier);
				return TableRecordReference.of(tableName, orderLine.getC_OrderLine_ID());
			case I_M_InOutLine.Table_Name:
				final I_M_InOutLine inOutLine = inoutLineTable.get(identifier);
				return TableRecordReference.of(tableName, inOutLine.getM_InOutLine_ID());
			case I_C_Flatrate_Term.Table_Name:
				final I_C_Flatrate_Term flatrateTerm = contractTable.get(identifier);
				return TableRecordReference.of(tableName, flatrateTerm.getC_Flatrate_Term_ID());
			default:
				throw new AdempiereException("Table name: " + tableName + " not supported!");
		}
	}
}
