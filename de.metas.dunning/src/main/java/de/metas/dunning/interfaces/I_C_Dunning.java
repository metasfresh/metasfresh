package de.metas.dunning.interfaces;

/*
 * #%L
 * de.metas.dunning
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


import org.compiere.model.I_C_Currency;

public interface I_C_Dunning extends org.compiere.model.I_C_Dunning
{
	public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	public I_C_Currency getC_Currency();

	public int getC_Currency_ID();

	public void setC_Currency_ID(int C_Currency_ID);

	/** Column name GraceDays */
	public static final String COLUMNNAME_GraceDays = "GraceDays";

	/**
	 * Set Tage Frist. Tage nach Fälligkeitstermin bevor erste Mahnung versandt wird
	 */
	public void setGraceDays(int GraceDays);

	/**
	 * Get Tage Frist. Tage nach Fälligkeitstermin bevor erste Mahnung versandt wird
	 */
	public int getGraceDays();

	public static final String COLUMNNAME_IsManageDunnableDocGraceDate = "IsManageDunnableDocGraceDate";

	public void setIsManageDunnableDocGraceDate(boolean IsManageDunnableDocGraceDate);

	public boolean isManageDunnableDocGraceDate();
}
