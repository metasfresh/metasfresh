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


import de.metas.esb.edi.model.I_EDI_DesadvLine;

public interface I_C_OrderLine extends de.metas.handlingunits.model.I_C_OrderLine
{
	// @formatter:off
	String COLUMNNAME_EDI_DesadvLine_ID = "EDI_DesadvLine_ID";
	
	void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID);
	int getEDI_DesadvLine_ID();
	
	void setEDI_DesadvLine (I_EDI_DesadvLine EDI_DesadvLine);
	I_EDI_DesadvLine getEDI_DesadvLine();
	// @formatter:on
	
}
