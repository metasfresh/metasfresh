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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for A_Asset_Info_Oth
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_A_Asset_Info_Oth 
{

    /** TableName=A_Asset_Info_Oth */
    public static final String Table_Name = "A_Asset_Info_Oth";

    /** AD_Table_ID=53136 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/** Set Asset.
	  * Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID);

	/** Get Asset.
	  * Asset used internally or by customers
	  */
	public int getA_Asset_ID();

    /** Column name A_Asset_Info_Oth_ID */
    public static final String COLUMNNAME_A_Asset_Info_Oth_ID = "A_Asset_Info_Oth_ID";

	/** Set A_Asset_Info_Oth_ID	  */
	public void setA_Asset_Info_Oth_ID (int A_Asset_Info_Oth_ID);

	/** Get A_Asset_Info_Oth_ID	  */
	public int getA_Asset_Info_Oth_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name A_User1 */
    public static final String COLUMNNAME_A_User1 = "A_User1";

	/** Set A_User1	  */
	public void setA_User1 (String A_User1);

	/** Get A_User1	  */
	public String getA_User1();

    /** Column name A_User10 */
    public static final String COLUMNNAME_A_User10 = "A_User10";

	/** Set A_User10	  */
	public void setA_User10 (String A_User10);

	/** Get A_User10	  */
	public String getA_User10();

    /** Column name A_User11 */
    public static final String COLUMNNAME_A_User11 = "A_User11";

	/** Set A_User11	  */
	public void setA_User11 (String A_User11);

	/** Get A_User11	  */
	public String getA_User11();

    /** Column name A_User12 */
    public static final String COLUMNNAME_A_User12 = "A_User12";

	/** Set A_User12	  */
	public void setA_User12 (String A_User12);

	/** Get A_User12	  */
	public String getA_User12();

    /** Column name A_User13 */
    public static final String COLUMNNAME_A_User13 = "A_User13";

	/** Set A_User13	  */
	public void setA_User13 (String A_User13);

	/** Get A_User13	  */
	public String getA_User13();

    /** Column name A_User14 */
    public static final String COLUMNNAME_A_User14 = "A_User14";

	/** Set A_User14	  */
	public void setA_User14 (String A_User14);

	/** Get A_User14	  */
	public String getA_User14();

    /** Column name A_User15 */
    public static final String COLUMNNAME_A_User15 = "A_User15";

	/** Set A_User15	  */
	public void setA_User15 (String A_User15);

	/** Get A_User15	  */
	public String getA_User15();

    /** Column name A_User2 */
    public static final String COLUMNNAME_A_User2 = "A_User2";

	/** Set A_User2	  */
	public void setA_User2 (String A_User2);

	/** Get A_User2	  */
	public String getA_User2();

    /** Column name A_User3 */
    public static final String COLUMNNAME_A_User3 = "A_User3";

	/** Set A_User3	  */
	public void setA_User3 (String A_User3);

	/** Get A_User3	  */
	public String getA_User3();

    /** Column name A_User4 */
    public static final String COLUMNNAME_A_User4 = "A_User4";

	/** Set A_User4	  */
	public void setA_User4 (String A_User4);

	/** Get A_User4	  */
	public String getA_User4();

    /** Column name A_User5 */
    public static final String COLUMNNAME_A_User5 = "A_User5";

	/** Set A_User5	  */
	public void setA_User5 (String A_User5);

	/** Get A_User5	  */
	public String getA_User5();

    /** Column name A_User6 */
    public static final String COLUMNNAME_A_User6 = "A_User6";

	/** Set A_User6	  */
	public void setA_User6 (String A_User6);

	/** Get A_User6	  */
	public String getA_User6();

    /** Column name A_User7 */
    public static final String COLUMNNAME_A_User7 = "A_User7";

	/** Set A_User7	  */
	public void setA_User7 (String A_User7);

	/** Get A_User7	  */
	public String getA_User7();

    /** Column name A_User8 */
    public static final String COLUMNNAME_A_User8 = "A_User8";

	/** Set A_User8	  */
	public void setA_User8 (String A_User8);

	/** Get A_User8	  */
	public String getA_User8();

    /** Column name A_User9 */
    public static final String COLUMNNAME_A_User9 = "A_User9";

	/** Set A_User9	  */
	public void setA_User9 (String A_User9);

	/** Get A_User9	  */
	public String getA_User9();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Text */
    public static final String COLUMNNAME_Text = "Text";

	/** Set Text	  */
	public void setText (String Text);

	/** Get Text	  */
	public String getText();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
