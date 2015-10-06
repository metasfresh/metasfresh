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

package org.adempiere.webui.component;

import java.io.IOException;
import java.net.URI;

import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.apps.AEnv;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *  Application Action.
 *      Creates Action with Button
 *      The ActionCommand is translated for display
 *      If translated text contains &, the next character is the Mnemonic
 *
 *  @author     Andrew Kimball
 */
public class WAppsAction
{
   /**
    *  Application Action
    *
    *  @param   action base action command - used as AD_Message for Text and Icon name
    *  @param   accelerator optional keystroke for accelerator
    *  @param   toggle is toggle action (maintains state)
    */
   public WAppsAction (String action, String accelerator) throws IOException
   {
       this (action, accelerator, null);
   }

   /**
    *  Application Action.
    *
    *  @param   action base action command - used as AD_Message for Text and Icon name
    *  @param   accelerator optional keystroke for accelerator
    *  @param   toolTipText text, if null defered from action
    *  @param   toggle is toggle action (maintains state)
    */
   public WAppsAction (String action, String accelerator, String toolTipText) throws IOException
   {
       super();
       String newToolTipText = toolTipText;
       m_action = action;
       if (m_accelerator == null)
       {
           m_accelerator = "";
       }
       else
       {
           m_accelerator = accelerator;
       }


       //  Data
       if (newToolTipText == null)
       {
           newToolTipText = Msg.getMsg(Env.getCtx(), action);
       }
       int pos = newToolTipText.indexOf('&');
       if (pos != -1  && newToolTipText.length() > pos)   //  We have a nemonic - creates ALT-_
       {
           Character ch = new Character(newToolTipText.toLowerCase().charAt(pos + 1));
           if (ch != ' ')
           {
               // remove ampersand
               newToolTipText = newToolTipText.substring(0, pos)
                       + newToolTipText.substring(pos + 1);
               // append ALT-mnemonic to accelerator
               m_accelerator += "@" + ch;
           }
       }
       //
       URI large = getImage(action, false);

       //  Create Button
       m_button = new Button();
       m_button.setTooltiptext(newToolTipText);                 //  Display
       m_button.setName("btn" + action);
       m_button.setId(action);
       
       //Image only if image is available
       if (large != null)
       {
           m_button.setImage(large.getPath());
           m_button.setLabel(null);
       }
       else
       {
    	   m_button.setLabel(newToolTipText);
       }
       LayoutUtils.addSclass("action-button", m_button);
   }   //  Action
   
   private Button  m_button;

   private String m_action = null;
   private String m_accelerator = null;

   /**
    *  Get Icon with name action
    *  @param name name
    *  @param small small
    *  @return Icon
    */
   private URI getImage(String name, boolean small) throws IOException
   {
       String fullName = name + (small ? "16" : "24");
       URI uri = AEnv.getImage(fullName + ".png");
       return uri;
   }   //  getIcon

   /**
    *  Get Name/ActionCommand
    *  @return ActionName
    */
   public String getName()
   {
       return m_action;
   }   //  getName

   /**
    *  Return Button
    *  @return Button
    */
   public Button getButton()
   {
       return m_button;
   }   //  getButton


    public String getCtrlKeys()
    {
        return this.m_accelerator;
    }

}
