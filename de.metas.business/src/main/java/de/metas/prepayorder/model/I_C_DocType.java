package de.metas.prepayorder.model;

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


import org.compiere.model.X_C_DocType;

public interface I_C_DocType extends org.compiere.model.I_C_DocType
{
	/**
	 * A metas prepay order differs from the {@link X_C_DocType#DOCSUBTYPE_PrepayOrder} in that no shipment and
	 * invoice is created immediately upon completion. Instead, these documents are supposed to be created by their
	 * respective common processes (allowing e.g. disposition and consolidation).
	 * 
	 * Created for Vorrauskasse (2009-0027-G5)
	 */
	String DOCSUBTYPE_PrepayOrder_metas = "PM";

}
