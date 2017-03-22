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
package org.compiere.apps;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.Adempiere;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;
import org.compiere.util.SupportInfo;
import org.compiere.util.Trace;
import org.slf4j.Logger;

import de.metas.adempiere.form.swing.SwingAskDialogBuilder;
import de.metas.logging.LogManager;

/**
 *  Info Dialog Management
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: ADialog.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */

public final class ADialog
{
	/** Show ADialogADialog - if false use JOptionPane  */
	private static final boolean showDialog = true;
	/**	Logger			*/
	private static final transient Logger log = LogManager.getLogger(ADialog.class);
	
	/**
	 *	Show plain message
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	clearHeading	Translated Title of window
	 *	@param	clearMessage	Translated message
	 *	@param	clearText		Additional message
	 */
	public static void info (final int WindowNo, final Container c, final String clearHeading, final String clearMessage, final String clearText)
	{
		log.info("{}: {} {}", clearHeading, clearMessage, clearText);
		
		String out = clearMessage;
		if(!Check.isEmpty(clearText, true))
		{
			out += "\n" + clearText;
		}
		
		//
		Window parent = Env.getParent(c);
		if (parent == null)
			parent = Env.getWindow(WindowNo);
		//
		if (showDialog  && parent != null)
		{
			if (parent instanceof JFrame)
			{
				new ADialogDialog ((JFrame)parent,
					clearHeading,
					out,
					JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				new ADialogDialog ((JDialog)parent,
					clearHeading,
					out,
					JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(parent,
				out + "\n",						//	message
				clearHeading,					//	title
				JOptionPane.INFORMATION_MESSAGE);
		}
	}	//	info

	/**
	 *	Show message with info icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@param	msg			Additional message
	 */
	public static void info (final int WindowNo, final Container c, final String AD_Message, final String msg)
	{
		log.info("{} - {}", AD_Message, msg);
		
		final Properties ctx = Env.getCtx();
		final StringBuilder out = new StringBuilder();
		if(!Check.isEmpty(AD_Message, true))
			out.append(Services.get(IMsgBL.class).getMsg(ctx, AD_Message));
		if(!Check.isEmpty(msg, true))
			out.append("\n").append(msg);
		//
		Window parent = Env.getParent(c);
		if (parent == null)
			parent = Env.getWindow(WindowNo);
		//
		if (showDialog && parent != null)
		{
			if (parent instanceof JFrame)
			{
				new ADialogDialog ((JFrame)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				new ADialogDialog ((JDialog)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(parent,
				out.toString() + "\n",			//	message
				Env.getHeader(ctx, WindowNo),	//	title
				JOptionPane.INFORMATION_MESSAGE);
		}
	}	//	info

	/**
	 *	Show message with info icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 */
	public static void info (int WindowNo, Container c, String AD_Message)
	{
		final String msg = null;
		info (WindowNo, c, AD_Message, msg);
	}	//	info


	/**************************************************************************
	 *	Display warning with warning icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@param	msg			Additional message
	 */
	public static void warn (final int WindowNo, final Container c, final String AD_Message, final String msg)
	{
		log.info("{} - {}", AD_Message, msg);
		
		Properties ctx = Env.getCtx();
		StringBuilder out = new StringBuilder();
		if (AD_Message != null && !AD_Message.equals(""))
			out.append(Services.get(IMsgBL.class).getMsg(ctx, AD_Message));
		if (msg != null && msg.length() > 0)
			out.append("\n").append(msg);
		//
		Window parent = Env.getParent(c);
		if (parent == null)
			parent = Env.getWindow(WindowNo);
		//
		if (showDialog  && parent != null)
		{
			if (parent instanceof JFrame)
			{
				new ADialogDialog ((JFrame)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				new ADialogDialog ((JDialog)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.WARNING_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(parent,
				out.toString() + "\n",  		//	message
				Env.getHeader(ctx, WindowNo),	//	title
				JOptionPane.WARNING_MESSAGE);
		}
	}	//	warn (int, String)

	/**
	 *	Display warning with warning icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 */
	public static void warn (final int WindowNo, final Container c, final String AD_Message)
	{
		final String msg = null;
		warn (WindowNo, c, AD_Message, msg);
	}

	
	/**************************************************************************
	 *	Display error with error icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@param	msg			Additional message
	 */
	public static void error (final int WindowNo, final Container c, final String AD_Message, final String msg)
	{
		log.info("{} - {}", AD_Message, msg);
		if (LogManager.isLevelFinest())
			Trace.printStack();
		
		final Properties ctx = Env.getCtx();
		final StringBuilder out = new StringBuilder();
		if(!Check.isEmpty(AD_Message, true))
			out.append(Services.get(IMsgBL.class).getMsg(ctx, AD_Message));
		if(!Check.isEmpty(msg, true))
			out.append("\n").append(msg);
		//
		Window parent = Env.getParent(c);
		if (parent == null)
			parent = Env.getWindow(WindowNo);
		//
		if (showDialog && parent != null)
		{
			if (parent instanceof JFrame)
			{
				new ADialogDialog ((JFrame)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.ERROR_MESSAGE);
			}
			else if (parent instanceof JDialog)
			{
				new ADialogDialog ((JDialog)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(Env.getWindow(WindowNo),
				out.toString() + "\n",			//	message
				Env.getHeader(ctx, WindowNo),	//	title
				JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 *	Display error with error icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 */
	public static void error (final int WindowNo, final Container c, final String AD_Message)
	{
		final String msg = null;
		error(WindowNo, c, AD_Message, msg);
	}

	
	/**************************************************************************
	 *	Ask Question with question icon and (OK) (Cancel) buttons
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@param	msg			Additional clear text message
	 *	@return true, if OK
	 */
	public static boolean ask (final int WindowNo, final Container c, final String AD_Message, final String msg)
	{
		return new SwingAskDialogBuilder()
				.setParentWindowNo(WindowNo)
				.setParentComponent(c)
				.setAD_Message(AD_Message)
				.setAdditionalMessage(msg)
				.getAnswer();
	}	//	ask

	/**
	 *	Ask Question with question icon and (OK) (Cancel) buttons
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@return true, if OK
	 */
	public static boolean ask (int WindowNo, Container c, String AD_Message)
	{
		return ask (WindowNo, c, AD_Message, null);
	}	//	ask

	/*************************************************************************

	/**
	 * Create Support EMail
	 * @param owner owner
	 * @param subject subject
	 * @param message message
	 */
	public static void createSupportEMail(final Dialog owner, final String subject, final String message)
	{
		log.info("ADialog.createSupportEMail");
		String to = Adempiere.getSupportEMail();
		MUser from = MUser.get(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()));
		//
		StringBuilder myMessage = new StringBuilder(message);
		myMessage.append("\n");
		SupportInfo.getInfo(myMessage);
		SupportInfo.getInfoDetail(myMessage, Env.getCtx());
		ModelValidationEngine.get().getInfoDetail(myMessage, Env.getCtx()); // teo_sarca - FR [ 1724662 ]

		new EMailDialog(owner,
			Services.get(IMsgBL.class).getMsg(Env.getCtx(), "EMailSupport"),
			from, to, "Support: " + subject, myMessage.toString(), null);
	}	//	createEmail

	/**
	 *	Create Support EMail
	 *	@param owner owner
	 *  @param subject subject
	 *  @param message message
	 */
	public static void createSupportEMail(final Frame owner, final String subject, final String message)
	{
		log.info("ADialog.createSupportEMail");
		String to = Adempiere.getSupportEMail();
		MUser from = MUser.get(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()));
		//
		StringBuilder myMessage = new StringBuilder(message);
		myMessage.append("\n");
		SupportInfo.getInfo(myMessage);
		SupportInfo.getInfoDetail(myMessage, Env.getCtx());
		ModelValidationEngine.get().getInfoDetail(myMessage, Env.getCtx()); // teo_sarca - FR [ 1724662 ]

		new EMailDialog(owner,
			Services.get(IMsgBL.class).getMsg(Env.getCtx(), "EMailSupport"),
			from, to, "Support: " + subject, myMessage.toString(), null);
	}	//	createEmail

	
	/**************************************************************************
	 *	Beep
	 */
	public static void beep()
	{
		Toolkit.getDefaultToolkit().beep();
	}	//	beep

// metas: begin
	public static void error (int WindowNo, Container c, Throwable ex)
	{
		String adMessage = "Error";
		String msg = ex.getLocalizedMessage();
		log.warn(msg, ex);
		error(WindowNo, c, adMessage, msg);
	}
// metas: end
}	//	Dialog
