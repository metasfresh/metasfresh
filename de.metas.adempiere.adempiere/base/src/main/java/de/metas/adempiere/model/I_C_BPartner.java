package de.metas.adempiere.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */



/**
 *
 * @author ts
 * @deprecated use {@link de.metas.interfaces.I_C_BPartner} instead.
 */
@Deprecated
public interface I_C_BPartner extends org.compiere.model.I_C_BPartner
{

	public static final String M_PRICELIST_ID = "M_PriceList_ID";

	@Override
	int getM_PriceList_ID();

	public static final String PO_PRICELIST_ID = "PO_PriceList_ID";

	@Override
	int getPO_PriceList_ID();

	public boolean isCompany();

	public void setIsCompany(boolean IsCompany);
}
