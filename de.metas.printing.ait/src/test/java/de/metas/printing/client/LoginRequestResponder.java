package de.metas.printing.client;

/*
 * #%L
 * de.metas.printing.ait
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


import org.adempiere.util.Check;
import org.junit.Assert;

import de.metas.printing.esb.base.jaxb.generated.PRTLoginRequestType;
import de.metas.printing.model.I_AD_User_Login;
import de.metas.printing.test.integration.AD2EsbConverter;

public class LoginRequestResponder extends AbstractPrintingClientResponder<PRTLoginRequestType, PRTLoginRequestType>
{
	private AD2EsbConverter _adempiere;

	/**
	 * AD_Session_ID to be set in {@link I_AD_User_Login} reponse.
	 * 
	 * <br/>
	 * <br/>
	 * NOTE: this is used only if you are not overriding {@link #createAD_User_Login_Response(PRTLoginRequestType)}.
	 */
	public Integer response_AD_Session_ID = null;

	/**
	 * If not null, the reponder will throw this exception instead of an answer.
	 * 
	 * <br/>
	 * <br/>
	 * NOTE: this is used only if you are not overriding {@link #createAD_User_Login_Response(PRTLoginRequestType)}.
	 */
	public RuntimeException response_Exception = null;

	protected void init()
	{
		// nothing at this level
	}

	public final LoginRequestResponder setAdempiere(final AD2EsbConverter adempiere)
	{
		Check.assumeNotNull(adempiere, "adempiere not null");
		Check.assumeNull(_adempiere, "adempiere not already configured");
		this._adempiere = adempiere;
		return this;
	}

	private AD2EsbConverter getAdempiere()
	{
		if (_adempiere == null)
		{
			_adempiere = new AD2EsbConverter();
		}
		return _adempiere;
	}

	@Override
	protected void validateRequest(PRTLoginRequestType xmlRequest)
	{
		// nothing
	}

	@Override
	protected PRTLoginRequestType createResponse(PRTLoginRequestType xmlRequest)
	{
		final I_AD_User_Login adUserLoginResponse = createAD_User_Login_Response(xmlRequest);

		//
		// Convert AD_User_Login response to XML response
		final AD2EsbConverter adempiere = getAdempiere();
		final PRTLoginRequestType xmlResponse = adempiere.toXml(adUserLoginResponse);
		return xmlResponse;
	}

	protected I_AD_User_Login createAD_User_Login_Response(final PRTLoginRequestType xmlRequest)
	{
		// Throw exception if we were asked to do so
		if (response_Exception != null)
		{
			throw response_Exception;
		}

		final AD2EsbConverter adempiere = getAdempiere();

		Assert.assertTrue("Request shall have AD_Session_ID NOT set!",
				xmlRequest.getADSessionID() == null);
		final I_AD_User_Login adUserLogin = adempiere.toADempiere(xmlRequest);

		if (response_AD_Session_ID != null)
		{
			adUserLogin.setAD_Session_ID(response_AD_Session_ID);
		}

		return adUserLogin;
	}
}
