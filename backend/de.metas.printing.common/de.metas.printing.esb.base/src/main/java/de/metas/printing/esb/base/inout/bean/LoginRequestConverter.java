package de.metas.printing.esb.base.inout.bean;

/*
 * #%L
 * de.metas.printing.esb.base
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


import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.generated.PRTLoginRequestType;
import de.metas.printing.esb.base.jaxb.generated.ReplicationEventEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationModeEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationTypeEnum;
import de.metas.printing.esb.base.util.Check;
import de.metas.printing.esb.base.util.collections.Converter;

/**
 * Converts from {@link LoginRequest}(json) to {@link PRTLoginRequestType}(xml)
 *
 * @author tsa
 *
 */
public class LoginRequestConverter implements Converter<PRTLoginRequestType, LoginRequest>
{
	public static final transient LoginRequestConverter instance = new LoginRequestConverter();

	@Override
	public PRTLoginRequestType convert(final LoginRequest loginRequest)
	{
		Check.assumeNotNull(loginRequest, "loginRequest not null");
		final PRTLoginRequestType xmlLoginRequest = new PRTLoginRequestType();

		// ADempiere Specific Data
		xmlLoginRequest.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		xmlLoginRequest.setReplicationModeAttr(ReplicationModeEnum.Table);
		xmlLoginRequest.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		xmlLoginRequest.setVersionAttr(JAXBConstants.PRT_LOGINREQUEST_VERSION);
		xmlLoginRequest.setADClientValueAttr("SYSTEM"); // FIXME: workaround to let ADempiere replication know which is the #AD_Client_ID

		xmlLoginRequest.setUserName(loginRequest.getUsername());
		xmlLoginRequest.setPassword(loginRequest.getPassword());
		xmlLoginRequest.setHostKey(loginRequest.getHostKey());
		return xmlLoginRequest;
	}
}
