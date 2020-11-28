/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          *
 * http://www.adempiere.org                                           *
 *                                                                    *
 * Copyright (C) Trifon Trifonov.                                     *
 * Copyright (C) Contributors                                         *
 *                                                                    *
 * This program is free software, you can redistribute it and/or      *
 * modify it under the terms of the GNU General Public License        *
 * as published by the Free Software Foundation, either version 2     *
 * of the License, or (at your option) any later version.             *
 *                                                                    *
 * This program is distributed in the hope that it will be useful,    *
 * but WITHOUT ANY WARRANTY, without even the implied warranty of     *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       *
 * GNU General Public License for more details.                       *
 *                                                                    *
 * You should have received a copy of the GNU General Public License  *
 * along with this program, if not, write to the Free Software        *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         *
 * MA 02110-1301, USA.                                                *
 *                                                                    *
 * Contributors:                                                      *
 * - Trifon Trifonov (trifonnt@users.sourceforge.net)                 *
 *                                                                    *
 * Sponsors:                                                          *
 * - Company (http://www.site.com)                                    *
 **********************************************************************/
package org.compiere.model;

/*
 * #%L
 * de.metas.banking.base
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

import org.compiere.util.KeyNamePair;

/** Generated Interface for C_DirectDebit
 *  @author Adempiere (generated) 
 *  @version Release 3.5.2a
 */
public interface I_C_DirectDebit 
{

    /** TableName=C_DirectDebit */
    public static final String Table_Name = "C_DirectDebit";

    /** AD_Table_ID=540087 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (String Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public String getAmount();

    /** Column name C_BankStatementLine_ID */
    public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/** Set Bank statement line.
	  * Line on a statement from this Bank
	  */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/** Get Bank statement line.
	  * Line on a statement from this Bank
	  */
	public int getC_BankStatementLine_ID();

    /** Column name C_DirectDebit_ID */
    public static final String COLUMNNAME_C_DirectDebit_ID = "C_DirectDebit_ID";

	/** Set C_DirectDebit_ID	  */
	public void setC_DirectDebit_ID (int C_DirectDebit_ID);

	/** Get C_DirectDebit_ID	  */
	public int getC_DirectDebit_ID();

    /** Column name Dtafile */
    public static final String COLUMNNAME_Dtafile = "Dtafile";

	/** Set Dtafile.
	  * Copy of the *.dta stored as plain text
	  */
	public void setDtafile (String Dtafile);

	/** Get Dtafile.
	  * Copy of the *.dta stored as plain text
	  */
	public String getDtafile();

    /** Column name IsRemittance */
    public static final String COLUMNNAME_IsRemittance = "IsRemittance";

	/** Set IsRemittance	  */
	public void setIsRemittance (boolean IsRemittance);

	/** Get IsRemittance	  */
	public boolean isRemittance();
}
