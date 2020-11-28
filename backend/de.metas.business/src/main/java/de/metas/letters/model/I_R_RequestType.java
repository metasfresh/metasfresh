/**
 * 
 */
package de.metas.letters.model;

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
 * @author teo_sarca
 *
 */
public interface I_R_RequestType extends org.compiere.model.I_R_RequestType
{
	public static final String COLUMNNAME_IsDefaultForEMail = "IsDefaultForEMail";
	public void isDefaultForEMail();
	public boolean setIsDefaultForEMail(boolean IsDefaultEMail);
	
	public static final String COLUMNNAME_IsDefaultForLetter = "IsDefaultForLetter";
	public void isDefaultLetter();
	public boolean setIsDefaultForLetter(boolean IsDefaultLetter);
}
