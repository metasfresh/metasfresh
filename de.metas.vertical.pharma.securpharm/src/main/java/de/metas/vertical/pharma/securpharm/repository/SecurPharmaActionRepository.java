package de.metas.vertical.pharma.securpharm.repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.springframework.stereotype.Repository;

import de.metas.vertical.pharma.securpharm.model.SecurPharmAction;
import de.metas.vertical.pharma.securpharm.model.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResultId;
import de.metas.vertical.pharma.securpharm.model.UndoDecommissionResponse;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class SecurPharmaActionRepository
{
	public SecurPharmActionResultId save(@NonNull final DecommissionResponse response)
	{
		final I_M_Securpharm_Action_Result record = newInstance(I_M_Securpharm_Action_Result.class);

		record.setIsError(response.isError());
		record.setAction(SecurPharmAction.DECOMMISSION.getCode());
		record.setM_Inventory_ID(response.getInventoryId().getRepoId());
		record.setM_Securpharm_Productdata_Result_ID(response.getProductDataResultId().getRepoId());
		record.setTransactionIDServer(response.getServerTransactionId());

		saveRecord(record);

		return SecurPharmActionResultId.ofRepoId(record.getM_Securpharm_Action_Result_ID());
	}

	public SecurPharmActionResultId save(@NonNull final UndoDecommissionResponse response)
	{
		final I_M_Securpharm_Action_Result record = newInstance(I_M_Securpharm_Action_Result.class);

		record.setIsError(response.isError());
		record.setAction(SecurPharmAction.UNDO_DECOMMISSION.getCode());
		record.setM_Inventory_ID(response.getInventoryId().getRepoId());
		record.setM_Securpharm_Productdata_Result_ID(response.getProductDataResultId().getRepoId());
		record.setTransactionIDServer(response.getServerTransactionId());

		saveRecord(record);

		return SecurPharmActionResultId.ofRepoId(record.getM_Securpharm_Action_Result_ID());
	}

}
