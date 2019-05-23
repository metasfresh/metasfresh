/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.model;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.inventory.InventoryId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Data
@FieldDefaults(makeFinal = true)
@Builder
public class SecurPharmActionResult
{
	@NonNull
	private DecommissionAction action;

	@Nullable
	ProductData productData;

	@NonNull
	private SecurPharmRequestLogData requestLogData;

	@Nullable
	@NonFinal
	private InventoryId inventoryId;

	@Nullable
	@NonFinal
	SecurPharmProductDataResultId productDataResultId;

	@Nullable
	@NonFinal
	private SecurPharmActionResultId id;

	public TableRecordReference getRecordRef()
	{
		return TableRecordReference.of(I_M_Securpharm_Action_Result.Table_Name, getId());
	}

	public boolean isError()
	{
		return getRequestLogData().isError();
	}

	public String getServerTransactionId()
	{
		return getRequestLogData().getServerTransactionId();
	}
}
