/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/ 

package org.adempiere.model;

import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;

/**
 *
 * @author  Ashley G Ramdass
 */
public class CalloutRMA extends CalloutEngine
{

    /**
    *  docType - set document properties based on document type.
    *  @param ctx
    *  @param WindowNo
    *  @param mTab
    *  @param mField
    *  @param value
    *  @return error message or ""
    */
   public String docType (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
   {
       Integer C_DocType_ID = (Integer)value;
       if (C_DocType_ID == null || C_DocType_ID.intValue() == 0)
           return "";
       
       String sql = "SELECT d.IsSoTrx "
           + "FROM C_DocType d WHERE C_DocType_ID=?";
       
       String docSOTrx = DB.getSQLValueString(null, sql, C_DocType_ID);
       
       boolean isSOTrx = "Y".equals(docSOTrx);
       
       mTab.setValue("IsSOTrx", isSOTrx);
       
       return "";
   }
}
