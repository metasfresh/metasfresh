package de.metas.bpartner.service;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.util.ISingletonService;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BP_Group;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public interface IBPGroupDAO extends ISingletonService
{
	I_C_BP_Group getById(BPGroupId bpGroupId);

	/**
	 * Loading within currently inherited trx.
	 * Use case: this method ca be called when the BPGroup in question was only just created, and that trx was not yet committed.
	 */
	I_C_BP_Group getByIdInInheritedTrx(BPGroupId bpGroupId);

	I_C_BP_Group getByBPartnerId(BPartnerId bpartnerId);

	BPGroupId getBPGroupByBPartnerId(BPartnerId bpartnerId);

	I_C_BP_Group getDefaultByClientId(ClientId clientId);
}
