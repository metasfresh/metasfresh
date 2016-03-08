package org.adempiere.mm.attributes.model;

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


import java.math.BigDecimal;

import de.metas.javaclasses.model.I_AD_JavaClass;

public interface I_M_Attribute extends org.compiere.model.I_M_Attribute
{
	String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

	int getAD_JavaClass_ID();

	// NOTE: don't use it. Please use: org.adempiere.appdict.IJavaClassDAO.retriveJavaClassOrNull(Properties, int)
	// I_AD_JavaClass getAD_JavaClass();

	void setAD_JavaClass_ID(int AD_JavaClass_ID);

	void setAD_JavaClass(I_AD_JavaClass javaClass);
	
    /** Column name ValueMax */
    public static final String COLUMNNAME_ValueMax = "ValueMax";

	/** Set Max. Value.
	  * Maximum Value for a field
	  */
	public void setValueMax(BigDecimal ValueMax);

	/** Get Max. Value.
	  * Maximum Value for a field
	  */
	public BigDecimal getValueMax();

    /** Column name ValueMin */
    public static final String COLUMNNAME_ValueMin = "ValueMin";

	/** Set Min. Value.
	  * Minimum Value for a field
	  */
	public void setValueMin(BigDecimal ValueMin);

	/** Get Min. Value.
	  * Minimum Value for a field
	  */
	public BigDecimal getValueMin();


    public static final String COLUMNNAME_IsAttrDocumentRelevant = "IsAttrDocumentRelevant";
	public void setIsAttrDocumentRelevant(boolean IsAttrDocumentRelevant);
	public boolean isAttrDocumentRelevant();
}
