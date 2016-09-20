package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.compiere.model.MUser;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_C_POSKey;

public abstract class TerminalLoginDialog implements ITerminalLoginDialog
{
	protected final String ACTION_Exit = "Logout";

	protected ITerminalBasePanel parent;
	protected ITerminalKeyPanel usersPanel;
	protected ITerminalLabel passwordLabel;
	protected ITerminalTextField passwordField;
	protected IConfirmPanel confirmPanel;

	private KeyNamePair user;
	private boolean logged = false;
	private boolean isExit = false;

	private boolean disposed = false;

	private class PasswordListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (ITerminalTextField.PROPERTY_ActionPerformed.equals(evt.getPropertyName()))
			{
				if (evt.getSource() instanceof ITerminalTextField)
				{
					ITerminalTextField text = (ITerminalTextField)evt.getSource();
					if (!"Cancel".equals(text.getAction()))
						doLogin();
				}

			}
		}
	}

	private class UsersKeyListener extends TerminalKeyListenerAdapter
	{
		@Override
		public void keyReturned(ITerminalKey key)
		{
			KeyNamePair knp = key.getValue();
			setUser(knp);
		}
	}

	private class ConfirmPanelListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
			{
				return;
			}
			String action = String.valueOf(evt.getNewValue());
			if (IConfirmPanel.ACTION_OK.equals(action))
			{
				doLogin();
			}
			else if (IConfirmPanel.ACTION_Cancel.equals(action))
			{
				doCancel();
			}
			else if (ACTION_Exit.equals(action))
			{
				doExit();
			}
		}
	}

	public TerminalLoginDialog(ITerminalBasePanel parent)
	{
		this.parent = parent;
		init();
	}

	private void init()
	{
		initComponents();
		initLayout();

		//
		// If there is only one user, then select it
		final List<ITerminalKey> userKeys = usersPanel.getKeyLayout().getKeys();
		if (userKeys != null && userKeys.size() == 1)
		{
			final ITerminalKey userKey = userKeys.get(0);
			usersPanel.fireKeyPressed(userKey.getId());
		}
	}

	protected void initComponents()
	{
		int POSKeyId = 540003; // TODO: hardcoded -POS Users "AD_User - Internal"
		I_C_POSKey usersPOSKey = POSKey.getC_POSKey(POSKeyId);
		int AD_Reference_ID = usersPOSKey.getAD_Reference_ID();
		int AD_Val_Rule_ID = usersPOSKey.getAD_Val_Rule_ID();
		//
		TableRefKeyLayout keylayout = new TableRefKeyLayout(getTerminalContext(), AD_Reference_ID, AD_Val_Rule_ID);
		usersPanel = getTerminalFactory().createTerminalKeyPanel(keylayout, new UsersKeyListener());
		usersPanel.setAllowKeySelection(true);

		passwordLabel = getTerminalFactory().createLabel(ITerminalLabel.LABEL_PASSWORD);
		passwordField = getTerminalFactory().createTerminalTextField(ITerminalLabel.LABEL_PASSWORD, ITerminalTextField.TYPE_Password);
		passwordField.setAction(ITerminalTextField.ACTION_Nothing);
		passwordField.addListener(new PasswordListener());

		confirmPanel = getTerminalFactory().createConfirmPanel(false, "");
		confirmPanel.addButton(ACTION_Exit);
		confirmPanel.addListener(new ConfirmPanelListener());
	}

	protected abstract void initLayout();

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	public ITerminalFactory getTerminalFactory()
	{
		return parent.getTerminalFactory();
	}

	public ITerminalContext getTerminalContext()
	{
		return parent.getTerminalContext();
	}

	@Override
	public int getAD_User_ID()
	{
		return this.user == null ? -1 : this.user.getKey();
	}

	private void setUser(KeyNamePair user)
	{
		this.user = user;
		this.logged = false;
	}

	@Override
	public void setAD_User_ID(int userId)
	{
		if (userId <= 0)
		{
			setUser(null);
			usersPanel.onKeySelected(null);
		}
		else
		{
			setUser(new KeyNamePair(userId, MUser.getNameOfUser(userId)));
			ITerminalKey key = usersPanel.getKeyLayout().findKeyByValue(userId);
			usersPanel.onKeySelected(key);
		}
		//
	}

	@Override
	public boolean isLogged()
	{
		return this.logged;
	}

	@Override
	public boolean isExit()
	{
		return this.isExit;
	}

	@Override
	public void dispose()
	{
		disposeUI();

		DisposableHelper.disposeAll(
				usersPanel,
				passwordField, passwordLabel,
				confirmPanel
				);
		user = null;
		parent = null;

		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed  ;
	}

	protected abstract void disposeUI();

	private void validateCredentials()
	{
		int userId = getAD_User_ID();
		if (userId <= 0)
			throw new TerminalException("@FillMandatory@ @AD_User_ID@");

		String password = passwordField.getText();
		if (Check.isEmpty(password, false))
			throw new WrongValueException(passwordField, "@FillMandatory@");

		MUser user = MUser.get(getCtx(), userId);
		if (user == null)
			throw new TerminalException("@NotFound@ @AD_User_ID@");

		//
		// Because we are in terminal mode, we ignore case
		// else it would be a little bit harder and annoying for user to enter the password case sensitive
		if (!password.equalsIgnoreCase(user.getPassword()))
			throw new WrongValueException(passwordField, "@UserPwdError@");

		this.logged = true;
	}

	protected void doLogin()
	{
		try
		{
			validateCredentials();
			this.isExit = false;
			dispose();
		}
		catch (TerminalException e)
		{
			getTerminalFactory().showWarning(this.usersPanel, ITerminalFactory.TITLE_ERROR, e);
		}
	}

	protected void doCancel()
	{
		this.user = null;
		this.logged = false;
		this.isExit = false;
		dispose();
	}

	protected void doExit()
	{
		this.user = null;
		this.logged = false;
		this.isExit = true;
		dispose();
	}
}
