/**
 * 
 */
package de.metas.adempiere.model;

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


import org.compiere.model.I_R_MailText;

/**
 * @author tsa
 *
 */
public interface I_AD_Client extends org.compiere.model.I_AD_Client
{
	public static final String COLUMNNAME_PasswordReset_MailText_ID = "PasswordReset_MailText_ID";
	public void setPasswordReset_MailText_ID(int PasswordReset_MailText_ID);
	public int getPasswordReset_MailText_ID();
	public I_R_MailText getPasswordReset_MailText();
}
