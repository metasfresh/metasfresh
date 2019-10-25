package de.metas.contracts.commission.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
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

@Value
@Builder
public class ContractTestRecord
{
	@NonNull
	BPartnerId C_BPartner_SalesRep_ID;

	public FlatrateTermId createContractData(@NonNull Integer C_Flatrate_Conditions_ID)
	{
		final I_C_Flatrate_Term termRecord = newInstance(I_C_Flatrate_Term.class);
		termRecord.setBill_BPartner_ID(C_BPartner_SalesRep_ID.getRepoId());
		termRecord.setC_Flatrate_Conditions_ID(C_Flatrate_Conditions_ID);
		saveRecord(termRecord);

		return FlatrateTermId.ofRepoId(termRecord.getC_Flatrate_Term_ID());
	}

}
