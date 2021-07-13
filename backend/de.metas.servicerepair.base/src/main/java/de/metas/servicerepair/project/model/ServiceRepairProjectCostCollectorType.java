/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.model;

import de.metas.servicerepair.repository.model.X_C_Project_Repair_CostCollector;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@AllArgsConstructor
public enum ServiceRepairProjectCostCollectorType implements ReferenceListAwareEnum
{
	SparePartsToBeInvoiced(X_C_Project_Repair_CostCollector.TYPE_SparePartsToBeInvoiced),
	SparePartsOwnedByCustomer(X_C_Project_Repair_CostCollector.TYPE_SparePartsOwnedByCustomer),
	RepairedProductToReturn(X_C_Project_Repair_CostCollector.TYPE_RepairProductToReturn),
	RepairingConsumption(X_C_Project_Repair_CostCollector.TYPE_RepairingConsumption),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ServiceRepairProjectCostCollectorType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	public static ServiceRepairProjectCostCollectorType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static ServiceRepairProjectCostCollectorType ofSparePartOwnership(@NonNull final PartOwnership partOwnership)
	{
		if(PartOwnership.OWNED_BY_COMPANY.equals(partOwnership))
		{
			return SparePartsToBeInvoiced;
		}
		else if(PartOwnership.OWNED_BY_CUSTOMER.equals(partOwnership))
		{
			return SparePartsOwnedByCustomer;
		}
		else
		{
			throw new AdempiereException("Unknown PartOwnership: "+partOwnership);
		}
	}
}
