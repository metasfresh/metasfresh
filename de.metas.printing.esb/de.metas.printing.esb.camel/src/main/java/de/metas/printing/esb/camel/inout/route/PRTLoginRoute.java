package de.metas.printing.esb.camel.inout.route;

/*
 * #%L
 * de.metas.printing.esb.camel
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


import de.metas.esb.camel.route.Xml2JsonRequestResponseRouteBuilder;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.base.inout.bean.LoginRequestConverter;
import de.metas.printing.esb.base.inout.bean.LoginResponseConverter;
import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.generated.PRTLoginRequestType;
import de.metas.printing.esb.camel.commons.Constants;

public class PRTLoginRoute extends Xml2JsonRequestResponseRouteBuilder<LoginRequest, LoginResponse, PRTLoginRequestType>
{
	public static final String EP_Client_Login = "direct:login";

	@Override
	protected void configurePrepare() throws Exception
	{
		setJaxbContextPath(JAXBConstants.JAXB_ContextPath);
		setJaxbObjectFactory(JAXBConstants.JAXB_ObjectFactory);
		setExceptionEndpoint(PRTRestServiceRoute.EP_CXF_RS_ERROR);

		setFromJsonEndpoint(EP_Client_Login);
		setToXmlEndpoint(Constants.EP_JMS_TO_AD);

		setJsonRequestClass(LoginRequest.class);
		setJsonResponseClass(LoginResponse.class);

		setJsonRequestConverter(LoginRequestConverter.instance);
		setXmlResponseConverter(LoginResponseConverter.instance);
		
		setMarshalUnmarshalJSON(true);
	}

}
