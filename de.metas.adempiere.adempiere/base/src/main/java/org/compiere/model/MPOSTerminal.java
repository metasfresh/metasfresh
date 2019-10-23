/******************************************************************************
 *  Product: Posterita Web-Based POS and Adempiere Plugin                     *
 *  Copyright (C) 2008  Posterita Ltd                                         *
 *  This file is part of POSterita                                            *
 *                                                                            *
 *  POSterita is free software; you can redistribute it and/or modify         *
 *  it under the terms of the GNU General Public License as published by      *
 *  the Free Software Foundation; either version 2 of the License, or         *
 *  (at your option) any later version.                                       *
 *                                                                            *
 *  This program is distributed in the hope that it will be useful,           *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 *  GNU General Public License for more details.                              *
 *                                                                            *
 *  You should have received a copy of the GNU General Public License along   *
 *  with this program; if not, write to the Free Software Foundation, Inc.,   *
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.               *
 *****************************************************************************/
package org.compiere.model;

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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import de.metas.cache.CCache;

/**
 * @author Ashley G Ramdass
 *
 */
public class MPOSTerminal extends X_U_POSTerminal
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6972567212871993024L;
	/** Cache                   */
    static private CCache<Integer,MPOSTerminal> s_cache = new CCache<Integer,MPOSTerminal>(X_U_POSTerminal.Table_Name, 10, 60);
    
    /**
     * @param ctx Context
     * @param rs Result Set
     * @param trxName Transaction
     */
    public MPOSTerminal(Properties ctx, ResultSet rs, String trxName)
    {
        super(ctx, rs, trxName);
    }

    /**
     * @param ctx
     * @param U_POSTerminal_ID
     * @param trxName
     */
    public MPOSTerminal(Properties ctx, int U_POSTerminal_ID, String trxName)
    {
        super(ctx, U_POSTerminal_ID, trxName);
    }
    
    /**
     * @param ctx Context
     * @param U_POSTerminal_ID Terminal ID
     * @return Terminal
     */
    public static MPOSTerminal get(Properties ctx, int U_POSTerminal_ID)
    {
        Integer key = new Integer(U_POSTerminal_ID);
        MPOSTerminal retValue = (MPOSTerminal)s_cache.get(key);
        if (retValue == null)
        {
            retValue = new MPOSTerminal (ctx, U_POSTerminal_ID, null);
            if (retValue.get_ID() <= 0)
            {
                return null;
            }
            s_cache.put(key, retValue);
        }
        
        checkLock(retValue);
        return retValue;
    }
    
    @Override
    protected void onLoadComplete(boolean success)
    {
        if (success)
        {
            checkLock(this);
        }
    }
    
    public static void checkLock(MPOSTerminal terminal)
    {
        if (terminal.isLocked())
        {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            if (terminal.getUnlockingTime() != null && currentTime.after(terminal.getUnlockingTime()))
            {
                terminal.setLocked(false);
            }
            
            if (terminal.getLastLockTime() != null && terminal.getLockTime() > 0)
            {
                long timeToUnlock = terminal.getLastLockTime().getTime() + (terminal.getLockTime() * 60 * 1000);
                if (currentTime.after(new Timestamp(timeToUnlock)))
                {
                    terminal.setLocked(false);
                }
            }
            terminal.save();
        }
    }

    /**
     * @see org.compiere.model.PO#beforeSave(boolean)
     */
    protected boolean beforeSave(boolean newRecord)
    {
        if (is_ValueChanged(COLUMNNAME_Locked) && isLocked())
        {
            setLastLockTime(new Timestamp(System.currentTimeMillis()));
        }
        
        return true;
    }
    
    /**
     * @see org.compiere.model.PO#afterSave(boolean, boolean)
     */
    protected boolean afterSave(boolean newRecord, boolean success)
    {
        success =  super.afterSave(newRecord, success);
        
        if (success)
        {
            s_cache.remove(new Integer(get_ID()));
        }
        
        return success;
    }
}
