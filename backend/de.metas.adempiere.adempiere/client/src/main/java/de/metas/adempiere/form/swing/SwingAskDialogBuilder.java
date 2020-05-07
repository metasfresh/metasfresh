package de.metas.adempiere.form.swing;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.Component;
import java.awt.Window;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.ADialogDialog;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.adempiere.form.IAskDialogBuilder;
import de.metas.i18n.IMsgBL;

public class SwingAskDialogBuilder implements IAskDialogBuilder
{
	// Services
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	// private final transient Logger log = CLogMgt.getLogger(getClass());

	private Object _parentCompObj;
	private int _parentWindowNo;
	private String _adMessage;
	private Object[] _adMessageParams;
	private String _additionalMessage;
	private boolean _defaultAnswer = true; // OK/Yes by default

	@Override
	public boolean getAnswer()
	{
		final Window parent = getParentWindowOrNull();
		final String title = buildTitle();
		final String message = buildMessage();
		final boolean defaultAnswer = getDefaultAnswer();
		final int messageType = JOptionPane.QUESTION_MESSAGE;

		boolean answer = false;
		if (parent instanceof JFrame)
		{
			final ADialogDialog dialog = new ADialogDialog((JFrame)parent, title);
			dialog.setMessage(message, messageType);
			dialog.setInitialAnswer(defaultAnswer ? ADialogDialog.A_OK : ADialogDialog.A_CANCEL);
			dialog.showCenterScreen();
			answer = dialog.getReturnCode() == ADialogDialog.A_OK;
		}
		else if (parent instanceof JDialog)
		{
			final ADialogDialog dialog = new ADialogDialog((JDialog)parent, title);
			dialog.setMessage(message, messageType);
			dialog.setInitialAnswer(defaultAnswer ? ADialogDialog.A_OK : ADialogDialog.A_CANCEL);
			dialog.showCenterScreen();
			answer = dialog.getReturnCode() == ADialogDialog.A_OK;
		}
		else
		{
			ADialogDialog.assertUIOutOfTransaction();
			
			final Properties ctx = getCtx();
			final Object[] options = {
					Util.cleanAmp(msgBL.getMsg(ctx, "OK")),
					Util.cleanAmp(msgBL.getMsg(ctx, "Cancel"))
			};
			final Object initialOption = defaultAnswer ? options[0] : options[1];
			int i = JOptionPane.showOptionDialog(parent,
					message + "\n",			// message
					title,	// title
					JOptionPane.DEFAULT_OPTION,
					messageType,
					null, // icon
					options,
					initialOption);
			answer = i == JOptionPane.YES_OPTION;
		}
		return answer;
	}

	private String buildTitle()
	{
		return Env.getHeader(getCtx(), _parentWindowNo);
	}

	private String buildMessage()
	{
		final StringBuilder out = new StringBuilder();

		final String adMessageTrl = getAD_Message_Translated();
		if (!Check.isEmpty(adMessageTrl))
		{
			out.append(adMessageTrl);
		}

		final String additionalMessage = getAdditionalMessage();
		if (!Check.isEmpty(additionalMessage))
		{
			out.append("\n").append(additionalMessage);
		}

		return out.toString();
	}

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public IAskDialogBuilder setParentComponent(final Object parentCompObj)
	{
		this._parentCompObj = parentCompObj;
		return this;
	}

	@Override
	public IAskDialogBuilder setParentWindowNo(final int windowNo)
	{
		this._parentWindowNo = windowNo;
		return this;
	}

	private Window getParentWindowOrNull()
	{
		Window parent = null;
		if (_parentCompObj instanceof Component)
		{
			parent = Env.getParent((Component)_parentCompObj);
		}

		if (parent == null && Env.isRegularOrMainWindowNo(_parentWindowNo))
		{
			parent = Env.getWindow(_parentWindowNo);
		}

		return parent;
	}

	@Override
	public IAskDialogBuilder setAD_Message(final String adMessage, Object ... params)
	{
		this._adMessage = adMessage;
		this._adMessageParams = params;
		return this;
	}


	private String getAD_Message_Translated()
	{
		if (Check.isEmpty(_adMessage, true))
		{
			return "";
		}
		
		final Properties ctx = getCtx();
		if (Check.isEmpty(_adMessageParams))
		{
			return msgBL.getMsg(ctx, _adMessage);
		}
		else
		{
			return msgBL.getMsg(ctx, _adMessage, _adMessageParams);
		}
	}

	@Override
	public IAskDialogBuilder setAdditionalMessage(final String additionalMessage)
	{
		this._additionalMessage = additionalMessage;
		return this;
	}

	private String getAdditionalMessage()
	{
		return this._additionalMessage;
	}

	@Override
	public IAskDialogBuilder setDefaultAnswer(final boolean defaultAnswer)
	{
		this._defaultAnswer = defaultAnswer;
		return this;
	}

	private boolean getDefaultAnswer()
	{
		return this._defaultAnswer;
	}
}
