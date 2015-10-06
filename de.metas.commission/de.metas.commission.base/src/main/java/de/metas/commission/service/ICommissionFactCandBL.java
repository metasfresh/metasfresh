package de.metas.commission.service;

/*
 * #%L
 * de.metas.commission.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;

import org.adempiere.util.ISingletonService;
import org.compiere.model.PO;

import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionFactCand;

public interface ICommissionFactCandBL extends ISingletonService
{
	PO retrievePO(I_C_AdvCommissionFactCand cand);

	Timestamp retrieveDateDocOfPO(Object po);

	Timestamp retrieveDateDocOfReferencedPO(I_C_AdvCommissionFactCand cand);

	I_C_AdvCommissionFactCand getCause(I_C_AdvCommissionFactCand cand);

	void setCause(I_C_AdvCommissionFactCand cand, MCAdvCommissionFactCand cause);
}
