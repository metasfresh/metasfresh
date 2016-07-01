package de.metas.procurement.base.rfq.model;

import de.metas.flatrate.model.I_C_Flatrate_Term;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface I_C_RfQ_Topic extends de.metas.rfq.model.I_C_RfQ_Topic
{
	//@formatter:off
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";
	org.adempiere.model.ModelColumn<I_C_Flatrate_Term, de.metas.flatrate.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Term, de.metas.flatrate.model.I_C_Flatrate_Conditions>(I_C_Flatrate_Term.class, "C_Flatrate_Conditions_ID", de.metas.flatrate.model.I_C_Flatrate_Conditions.class);
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);
	int getC_Flatrate_Conditions_ID();
	de.metas.flatrate.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions();
	void setC_Flatrate_Conditions(de.metas.flatrate.model.I_C_Flatrate_Conditions C_Flatrate_Conditions);
	//@formatter:on
}
