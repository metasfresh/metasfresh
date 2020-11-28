package de.metas.edi.model;

/*
 * #%L
 * de.metas.edi
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


import de.metas.esb.edi.model.I_EDI_Desadv;

public interface I_C_Order extends de.metas.order.model.I_C_Order
{
	// @formatter:off
	String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";
	
	void setEDI_Desadv_ID (int EDI_Desadv_ID);
	int getEDI_Desadv_ID();
	
	void setEDI_Desadv (I_EDI_Desadv EDI_Desadv);
	I_EDI_Desadv getEDI_Desadv();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_IsEdiEnabled = "IsEdiEnabled";
	
	boolean isEdiEnabled();
	void setIsEdiEnabled(boolean IsEdiEnabled);
	// @formatter:on
}
