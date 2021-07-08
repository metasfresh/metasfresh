package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_C_BP_Relation;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import lombok.Builder;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Builder
public class BPRelation
{

	private boolean billTo;

	private final BPartnerId bpartnerId;

	private final BPartnerLocationId bpLocationId;

	private final BPartnerId relBPartnerId;

	private final BPartnerLocationId relBPLocationId;

	private final String name;

	public I_C_BP_Relation createRecord()
	{
		final I_C_BP_Relation bpRelation = newInstance(I_C_BP_Relation.class);
		bpRelation.setC_BPartner_ID(bpartnerId.getRepoId());
		bpRelation.setC_BPartner_Location_ID(bpLocationId.getRepoId());
		bpRelation.setC_BPartnerRelation_ID(relBPartnerId.getRepoId());
		bpRelation.setC_BPartnerRelation_Location_ID(relBPLocationId.getRepoId());
		bpRelation.setIsBillTo(billTo);
		bpRelation.setName(name);
		saveRecord(bpRelation);

		return bpRelation;
	}
}
