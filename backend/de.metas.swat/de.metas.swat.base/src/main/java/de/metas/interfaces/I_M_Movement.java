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


import org.compiere.model.I_M_InOut;

public interface I_M_Movement extends org.compiere.model.I_M_Movement
{
	//@formatter:off
	public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";
	public int getM_InOut_ID();
	public void setM_InOut_ID(int M_InOut_ID);
	public I_M_InOut getM_InOut();
	public void setM_InOut(I_M_InOut M_InOut);
	//@formatter:on
}
