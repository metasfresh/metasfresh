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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/**
 * Lookup Display Column Value Object
 * 
 * @author tsa
 * 
 */
public interface ILookupDisplayColumn
{
	String getColumnName();

	boolean isVirtual();

	String getColumnSQL();

	int getDisplayType();

	int getAD_Reference_ID();

	/**
	 * 
	 * @return true if this column has translation
	 */
	boolean isTranslated();

	/**
	 * @return formatting pattern
	 * @see I_AD_Column#COLUMNNAME_FormatPattern
	 */
	String getFormatPattern();
}
