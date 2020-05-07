package org.compiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Interface which exposes AD_Client_ID and AD_Org_ID properties.
 * 
 * @author tsa
 * 
 */
public interface IClientOrgAware
{
	// @formatter:off
	int getAD_Client_ID();
	I_AD_Client getAD_Client();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID"; 
	int getAD_Org_ID();
	void setAD_Org_ID(int AD_Org_ID);
	I_AD_Org getAD_Org();
	// @formatter:on
}
