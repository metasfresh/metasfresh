/**
 *
 */
package org.adempiere.webui.panel;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.theme.ITheme;
import org.adempiere.webui.window.LoginWindow;
import org.compiere.util.Msg;
import org.zkoss.zhtml.Div;
import org.zkoss.zhtml.Table;
import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.au.out.AuFocus;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Label;
import org.zkoss.zul.mesg.MZul;

import de.metas.adempiere.model.I_AD_User;

/**
 * @author ab
 *
 */
public class ResetPasswordPanel extends Window implements EventListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8818276034234180658L;

	private static final String MSG_RetypedPasswordNoMatch = "RetypedPasswordNoMatch";
	private static final String MSG_InvalidAccountPasswordResetCodeError = "InvalidAccountPasswordResetCodeError";

	private final Properties ctx;
	@SuppressWarnings("unused")
	private final LoginWindow wndLogin;

	private String passwordResetCode;
	private I_AD_User user;

	private Label lblUserName;
	private Label lblLogin;
	private Label lblEmail;
	private Label lblActivationCode;
	private Label lblNewPassword;
	private Label lblRetypePassword;
	private Textbox txtUserName;
	private Textbox txtLogin;
	private Textbox txtEmail;
	private Textbox txtActivationCode;
	private Textbox txtNewPassword;
	private Textbox txtRetypePassword;

	public ResetPasswordPanel(Properties ctx, LoginWindow loginWindow)
	{
		this.ctx = ctx;
		this.wndLogin = loginWindow;
		try
		{
			initComponents();
			init();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		this.setId("resetPasswordPanel");
	}

	private void initComponents() throws Exception
	{
		lblUserName = new Label();
		lblUserName.setValue(Msg.translate(ctx, "Name"));
		txtUserName = new Textbox();
		txtUserName.setWidth("220px");
		txtUserName.setReadonly(true);

		lblLogin = new Label();
		lblLogin.setValue(Msg.translate(ctx, "Login"));
		txtLogin = new Textbox();
		txtLogin.setWidth("220px");
		txtLogin.setReadonly(true);

		lblEmail = new Label();
		lblEmail.setValue(Msg.translate(ctx, I_AD_User.COLUMNNAME_EMail));
		txtEmail = new Textbox();
		txtEmail.setWidth("220px");
		txtEmail.setReadonly(true);

		lblActivationCode = new Label();
		lblActivationCode.setValue(Msg.translate(ctx, I_AD_User.COLUMNNAME_PasswordResetCode));
		txtActivationCode = new Textbox();
		txtActivationCode.setWidth("220px");
		txtActivationCode.setReadonly(true);

		lblNewPassword = new Label();
		lblNewPassword.setValue(Msg.translate(ctx, "NewPassword"));
		txtNewPassword = new Textbox();
		txtNewPassword.setType("password");
		txtNewPassword.setWidth("220px");
		txtNewPassword.setConstraint(new Constraint()
		{
			@Override
			public void validate(Component comp, Object value) throws WrongValueException
			{
				validatePassword(comp, value);
			}
		});

		lblRetypePassword = new Label();
		lblRetypePassword.setValue(Msg.translate(ctx, "RetypeNewPassword"));
		txtRetypePassword = new Textbox();
		txtRetypePassword.setType("password");
		txtRetypePassword.setWidth("220px");
		txtRetypePassword.setConstraint(new Constraint()
		{
			@Override
			public void validate(Component comp, Object value) throws WrongValueException
			{
				validatePassword(comp, value);
			}
		});
	}

	private void init()
	{
		Div div = new Div();
		div.setSclass(ITheme.LOGIN_BOX_HEADER_CLASS);
		Label label = new Label(Msg.getMsg(ctx, "ResetPasswordPanelTitle"));
		label.setSclass(ITheme.LOGIN_BOX_HEADER_TXT_CLASS);
		div.appendChild(label);
		this.appendChild(div);

		Table table = new Table();
		table.setId("grdLogin");
		table.setDynamicProperty("cellpadding", "0");
		table.setDynamicProperty("cellspacing", "5");
		table.setSclass(ITheme.LOGIN_BOX_BODY_CLASS);

		this.appendChild(table);

		/*
		 * Tr tr = new Tr(); table.appendChild(tr); Td td = new Td(); td.setSclass(ITheme.LOGIN_BOX_HEADER_LOGO_CLASS); tr.appendChild(td); td.setDynamicProperty("colspan", "2"); Image image = new
		 * Image(); image.setSrc(ThemeManager.getLargeLogo()); td.appendChild(image);
		 */

		Tr tr = new Tr();
		tr.setId("rowUser");
		table.appendChild(tr);
		Td td = new Td();
		tr.appendChild(td);
		td.setSclass(ITheme.LOGIN_LABEL_CLASS);
		td.appendChild(lblUserName);
		td = new Td();
		td.setSclass(ITheme.LOGIN_FIELD_CLASS);
		tr.appendChild(td);
		td.appendChild(txtUserName);

		tr = new Tr();
		tr.setId("rowLogin");
		table.appendChild(tr);
		td = new Td();
		tr.appendChild(td);
		td.setSclass(ITheme.LOGIN_LABEL_CLASS);
		td.appendChild(lblLogin);
		td = new Td();
		td.setSclass(ITheme.LOGIN_FIELD_CLASS);
		tr.appendChild(td);
		td.appendChild(txtLogin);

		tr = new Tr();
		tr.setId("rowEmail");
		table.appendChild(tr);
		td = new Td();
		tr.appendChild(td);
		td.setSclass(ITheme.LOGIN_LABEL_CLASS);
		td.appendChild(lblEmail);
		td = new Td();
		td.setSclass(ITheme.LOGIN_FIELD_CLASS);
		tr.appendChild(td);
		td.appendChild(txtEmail);

		tr = new Tr();
		tr.setId("rowActivationCode");
		table.appendChild(tr);
		td = new Td();
		tr.appendChild(td);
		td.setSclass(ITheme.LOGIN_LABEL_CLASS);
		td.appendChild(lblActivationCode);
		td = new Td();
		td.setSclass(ITheme.LOGIN_FIELD_CLASS);
		tr.appendChild(td);
		td.appendChild(txtActivationCode);

		tr = new Tr();
		tr.setId("rowNewPassword");
		table.appendChild(tr);
		td = new Td();
		tr.appendChild(td);
		td.setSclass(ITheme.LOGIN_LABEL_CLASS);
		td.appendChild(lblNewPassword);
		td = new Td();
		td.setSclass(ITheme.LOGIN_FIELD_CLASS);
		tr.appendChild(td);
		td.appendChild(txtNewPassword);

		tr = new Tr();
		tr.setId("rowRetypePassword");
		table.appendChild(tr);
		td = new Td();
		tr.appendChild(td);
		td.setSclass(ITheme.LOGIN_LABEL_CLASS);
		td.appendChild(lblRetypePassword);
		td = new Td();
		td.setSclass(ITheme.LOGIN_FIELD_CLASS);
		tr.appendChild(td);
		td.appendChild(txtRetypePassword);

		div = new Div();
		div.setSclass(ITheme.LOGIN_BOX_FOOTER_CLASS);
		ConfirmPanel pnlButtons = new ConfirmPanel(false);
		pnlButtons.addActionListener(this);
		LayoutUtils.addSclass(ITheme.LOGIN_BOX_FOOTER_PANEL_CLASS, pnlButtons);
		pnlButtons.setWidth(null);
		pnlButtons.getButton(ConfirmPanel.A_OK).setSclass(ITheme.LOGIN_BUTTON_CLASS);
		div.appendChild(pnlButtons);
		this.appendChild(div);

	}

	public void setResetCode()
	{
		final HttpServletRequest httpRequest = (HttpServletRequest)Executions.getCurrent().getNativeRequest();
		final String accountPasswordResetCode = httpRequest.getParameter(IUserBL.PARAM_AccountPasswordResetCode);
		setResetCode(accountPasswordResetCode);
	}

	public void setResetCode(String accountPasswordResetCode)
	{
		if (Check.isEmpty(accountPasswordResetCode))
		{
			throw new WrongValueException(Msg.translate(ctx, MSG_InvalidAccountPasswordResetCodeError));
		}
		this.passwordResetCode = accountPasswordResetCode;
		//
		this.user = Services.get(IUserDAO.class).retrieveUserByPasswordResetCode(ctx, passwordResetCode);
		if (user == null)
		{
			throw new WrongValueException(Msg.translate(ctx, MSG_InvalidAccountPasswordResetCodeError));
		}
		txtUserName.setText(user.getName());
		txtLogin.setText(user.getLogin());
		txtEmail.setText(user.getEMail() != null ? user.getEMail() : "");
		txtActivationCode.setText(passwordResetCode);

		Clients.response(new AuFocus(txtNewPassword));
	}

	private void validatePassword(Component comp, Object value) throws WrongValueException
	{
		if (comp == txtNewPassword)
		{
			String password = value == null ? "" : value.toString();
			if (Check.isEmpty(password, true))
			{
				throw new WrongValueException(comp, MZul.EMPTY_NOT_ALLOWED);
			}

		}
		else if (comp == txtRetypePassword)
		{
			String password2 = value == null ? "" : value.toString();
			if (Check.isEmpty(password2, true))
			{
				throw new WrongValueException(comp, MZul.EMPTY_NOT_ALLOWED);
			}

			String password = txtNewPassword.getText();
			if (!password2.equals(password))
			{
				throw new WrongValueException(comp, Msg.translate(ctx, MSG_RetypedPasswordNoMatch));
			}
		}
	}

	@Override
	public void onEvent(Event event)
	{
		if (event.getTarget().getId().equals(ConfirmPanel.A_OK))
		{
			doPasswordReset();
		}
	}

	private void doPasswordReset()
	{
		if (!txtNewPassword.isValid())
			return;
		if (!txtRetypePassword.isValid())
			return;

		int old_user_id = user.getAD_User_ID();
		final String password = txtRetypePassword.getText();
		final IUserBL userBL = Services.get(IUserBL.class);
		user = InterfaceWrapperHelper.create(
				userBL.resetPassword(ctx, old_user_id, passwordResetCode, password),
				I_AD_User.class);

		Executions.sendRedirect("index.zul");
	}
}
