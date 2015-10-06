package de.metas.commission.interfaces;

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


import de.metas.commission.model.I_C_Sponsor;

public interface I_C_BPartner extends de.metas.interfaces.I_C_BPartner
{
	public String COLUMNNAME_C_Sponsor_Parent_ID = "C_Sponsor_Parent_ID";

	public void setC_Sponsor_Parent_ID(int bPartnerParentSponsorId);

	public I_C_Sponsor getC_Sponsor_Parent();

	public int getC_Sponsor_Parent_ID();

	public String COLUMNNAME_IsParentSponsorReadWrite = "IsParentSponsorReadWrite";

	public void setIsParentSponsorReadWrite(boolean IsParentSponsorReadWrite);

	public boolean isParentSponsorReadWrite();
}
