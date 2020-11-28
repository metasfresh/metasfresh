package de.metas.interfaces;

/*
 * #%L
 * de.metas.swat.base
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


import org.compiere.model.I_M_InOutLine;

public interface I_M_MovementLine extends org.compiere.model.I_M_MovementLine
{
	public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	public int getM_InOutLine_ID();

	public void setM_InOutLine_ID(int M_InOutLine_ID);

	public I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(I_M_InOutLine M_InOutLine);

}
