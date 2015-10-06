/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package de.metas.commission.model;

import java.math.BigDecimal;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_AdvComSponsorPoints_v1
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvComSponsorPoints_v1 
{

    /** TableName=C_AdvComSponsorPoints_v1 */
    public static final String Table_Name = "C_AdvComSponsorPoints_v1";

    /** AD_Table_ID=540212 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AbsoluteDownlineVolume */
    public static final String COLUMNNAME_AbsoluteDownlineVolume = "AbsoluteDownlineVolume";

	/** Set Ebenen Netto-UVP	  */
	public void setAbsoluteDownlineVolume (BigDecimal AbsoluteDownlineVolume);

	/** Get Ebenen Netto-UVP	  */
	public BigDecimal getAbsoluteDownlineVolume();

    /** Column name AbsolutePersonalVolume */
    public static final String COLUMNNAME_AbsolutePersonalVolume = "AbsolutePersonalVolume";

	/** Set Netto-UVP	  */
	public void setAbsolutePersonalVolume (BigDecimal AbsolutePersonalVolume);

	/** Get Netto-UVP	  */
	public BigDecimal getAbsolutePersonalVolume();

    /** Column name C_AdvComSystem_ID */
    public static final String COLUMNNAME_C_AdvComSystem_ID = "C_AdvComSystem_ID";

	/** Set Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID);

	/** Get Vergütungsplan	  */
	public int getC_AdvComSystem_ID();

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException;

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Periode.
	  * Periode des Kalenders
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Periode.
	  * Periode des Kalenders
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

    /** Column name C_Sponsor_ID */
    public static final String COLUMNNAME_C_Sponsor_ID = "C_Sponsor_ID";

	/** Set Sponsor	  */
	public void setC_Sponsor_ID (int C_Sponsor_ID);

	/** Get Sponsor	  */
	public int getC_Sponsor_ID();

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor() throws RuntimeException;

    /** Column name PersonalVolume */
    public static final String COLUMNNAME_PersonalVolume = "PersonalVolume";

	/** Set Pers. Netto-UVP	  */
	public void setPersonalVolume (BigDecimal PersonalVolume);

	/** Get Pers. Netto-UVP	  */
	public BigDecimal getPersonalVolume();
}
