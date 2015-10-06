/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.window;

import java.util.Properties;

import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trace;

import org.zkoss.zk.ui.Component;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Messagebox;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 */

public class FDialog
{
	/**	Logger			*/
    private static final CLogger logger = CLogger.getCLogger(FDialog.class);
    
    /**
     * Construct a message from the AD_Message and the additional message
     *
     * @param adMessage	AD_Message string
     * @param message	additional message
     * @return The translated AD_Message appended with the additional message
     */

    private static StringBuffer constructMessage(String adMessage, String message)
	{
		StringBuffer out = new StringBuffer();

		if (adMessage != null && !adMessage.equals(""))
		{
			out.append(Msg.getMsg(Env.getCtx(), adMessage));
		}

		if (message != null && message.length() > 0)
		{
			out.append("\n").append(message);
		}

		return out;
	}


	/**
	 *	Display warning with warning icon
	 *
	 *	@param	windowNo	Number of Window
	 *	@param	adMessage	Message to be translated
	 *	@param	title		Message box title
	 *
	 * @see #warn(int, String)
	 * @see #warn(int, Component, String, String, String)
	 * @see #warn(int, Component, String, String)
	 */
    
    public static void warn(int windowNo, String adMessage, String title)
    {
        warn(windowNo, null, adMessage, null, title);
    }

	/**
	 *	Display warning with warning icon
	 *	@param	windowNo	Number of Window
	 *	@param	adMessage	Message to be translated
	 *	@param	message		Additional message
	 *	@param	title		If none then one will be generated
	 *
	 * @see #warn(int, String)
	 * @see #warn(int, String, String)
	 * @see #warn(int, Component, String, String, String)
	 */
    
    public static void warn(int windowNo, Component comp, String adMessage, String message)
    {
    	warn(windowNo, comp, adMessage, message, null);
    }

	/**
	 *	Display warning with warning icon
	 *	@param	windowNo	Number of Window
	 *	@param	adMessage	Message to be translated
	 *	@param	message		Additional message
	 *	@param	title		If none then one will be generated
	 *
	 * @see #warn(int, String)
	 * @see #warn(int, String, String)
	 * @see #warn(int, Component, String, String)
	 */
    
    public static void warn(int windowNo, Component comp, String adMessage, String message, String title)
    {
    	Properties ctx = Env.getCtx();
    	StringBuffer out = null;

    	logger.info(adMessage + " - " + message);

    	out = constructMessage(adMessage, message);
    	
    	String newTitle;

    	if (title == null)
    	{
    		newTitle = AEnv.getDialogHeader(ctx, windowNo);
    	}
    	else
    	{
    		newTitle = title;
    	}
    	
		try
		{
			String s = out.toString().replace("\n", "<br>");
			Messagebox.showDialog(s, newTitle, Messagebox.OK, Messagebox.EXCLAMATION);
		}
		catch (InterruptedException exception)
		{
			// Restore the interrupted status
            Thread.currentThread().interrupt();
		}

		return;
    }

	/**
	 *	Display warning with warning icon
	 *	@param	windowNo	Number of Window
	 *	@param	adMessage	Message to be translated
	 *
	 *	@see #warn(int, String, String)
	 *	@see #warn(int, Component, String, String, String)
	 * @see #warn(int, Component, String, String)
	 */
    
    public static void warn(int windowNo, String adMessage)
    {
        warn(windowNo, null, adMessage, null, null);
    }

	/**
	 *	Display error with error icon
	 *	@param	windowNo	Number of Window
	 *  @param	comp		Component (unused)
	 *	@param	adMessage	Message to be translated
	 */
    
    public static void error(int windowNo, Component comp, String adMessage)
    {
        error(windowNo, comp, adMessage, null);
    }

	/**
	 *	Display error with error icon
	 *	@param	windowNo	Number of Window
	 *	@param	adMessage	Message to be translated
	 *
	 *  @see #error(int, String, String)
	 *  @see #error(int, Component, String)
	 *  @see #error(int, Component, String, String)
	 */
    
	public static void error (int windowNo, String adMessage)
	{
		error (windowNo, null, adMessage, null);
	}	//	error (int, String)

	/**
	 *	Display error with error icon
	 *	@param	windowNo	Number of Window
	 *	@param	adMessage	Message to be translated
	 *	@param	adMessage	Additional message
	 *
	 *  @see #error(int, String)
	 *  @see #error(int, Component, String)
	 *  @see #error(int, Component, String, String)
	 */
	
    public static void error(int windowNo, String adMessage, String msg)
    {
        error(windowNo, null, adMessage, msg);
    }

	/**
	 *	Display error with error icon.
	 *
	 *	@param	windowNo	Number of Window
	 *  @param	comp		Component (unused)
	 *	@param	adMessage	Message to be translated
	 *	@param	message		Additional message
	 *
	 *  @see #error(int, String)
	 *  @see #error(int, Component, String)
	 *  @see #error(int, String, String)
	 */
    
    public static void error(int windowNo, Component comp, String adMessage, String message)
    {
    	Properties ctx = Env.getCtx();
		StringBuffer out = new StringBuffer();

		logger.info(adMessage + " - " + message);

		if (CLogMgt.isLevelFinest())
		{
			Trace.printStack();
		}

		out = constructMessage(adMessage, message);
		
		try
		{
			String s = out.toString().replace("\n", "<br>");
			Messagebox.showDialog(s, AEnv.getDialogHeader(ctx, windowNo), Messagebox.OK, Messagebox.ERROR);
		}
		catch (InterruptedException exception)
		{
			// Restore the interrupted status
            Thread.currentThread().interrupt();
		}
		
		return;
    }

    /**************************************************************************
	 *	Ask Question with question icon and (OK) (Cancel) buttons
	 *
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@param	msg			Additional clear text message
	 *
	 *	@return true, if OK
	 */    
    public static boolean ask(int windowNo, Component comp, String adMessage, String msg)
    {
    	StringBuffer out = new StringBuffer();
		if (adMessage != null && !adMessage.equals(""))
			out.append(Msg.getMsg(Env.getCtx(), adMessage));
		if (msg != null && msg.length() > 0)
			out.append("\n").append(msg);
		String s = out.toString().replace("\n", "<br>");
		return ask(windowNo, comp, s);
    }
    
	/**************************************************************************
	 *	Ask Question with question icon and (OK) (Cancel) buttons
	 *
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *
	 *	@return true, if OK
	 */
    
    public static boolean ask(int windowNo, Component comp, String adMessage)
    {
        try
        {
        	String s = Msg.getMsg(Env.getCtx(), adMessage).replace("\n", "<br>");
            int response = Messagebox.showDialog(s, AEnv.getDialogHeader(Env.getCtx(), windowNo), Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);

            return (response == Messagebox.OK);
        }
        catch (InterruptedException ex)
        {
			// Restore the interrupted status
            Thread.currentThread().interrupt();
        }
    	
        return true;
    }
    
    /**
     *  Display information with information icon.
     *
     *  @param  windowNo    Number of Window
     *  @param  comp        Component (unused)
     *  @param  adMessage   Message to be translated
     *
     *  @see #info(int, Component, String, String)
     */
    
    public static void info(int windowNo, Component comp, String adMessage)
    {
        info(windowNo, comp, adMessage, null);

        return;
    }


    /**
     *  Display information with information icon.
     *
     *  @param  windowNo    Number of Window
     *  @param  comp        Component (unused)
     *  @param  adMessage   Message to be translated
     *  @param  message     Additional message
     *
     *  @see #info(int, Component, String)
     */
    
    public static void info(int windowNo, Component comp, String adMessage, String message)
    {
        Properties ctx = Env.getCtx();
        
        StringBuffer out = new StringBuffer();

        logger.info(adMessage + " - " + message);

        if (CLogMgt.isLevelFinest())
        {
            Trace.printStack();
        }

        out = constructMessage(adMessage, message);

        try
        {
        	String s = out.toString().replace("\n", "<br>");
        	Messagebox.showDialog(s, AEnv.getDialogHeader(ctx, windowNo), Messagebox.OK, Messagebox.INFORMATION);
        }
        catch (InterruptedException exception)
        {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        }
        
        return;
    }
}
