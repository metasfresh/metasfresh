package de.metas.bpartner.api;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;

import de.metas.interfaces.I_C_BP_Relation;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IBPRelationDAO extends ISingletonService
{

	/**
	 * Retrieves the last created handover C_BP_Relation between the given partners.
	 * 
	 * @param partner
	 * @param relationPartner
	 * @return
	 */
	I_C_BP_Relation retrieveHandoverBPRelation(I_C_BPartner partner, I_C_BPartner relationPartner);

}
