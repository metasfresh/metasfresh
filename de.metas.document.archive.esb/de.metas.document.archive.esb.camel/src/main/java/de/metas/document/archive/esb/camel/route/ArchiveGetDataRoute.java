package de.metas.document.archive.esb.camel.route;

/*
 * #%L
 * de.metas.document.archive.esb.camel
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


import de.metas.document.archive.esb.api.ArchiveGetDataRequest;
import de.metas.document.archive.esb.api.ArchiveGetDataResponse;
import de.metas.document.archive.esb.base.converters.ArchiveGetDataRequestConverter;
import de.metas.document.archive.esb.base.converters.ArchiveGetDataResponseConverter;
import de.metas.document.archive.esb.base.jaxb.ADArchiveGetDataResponseType;
import de.metas.document.archive.esb.base.jaxb.JAXBConstants;
import de.metas.document.archive.esb.camel.commons.CamelConstants;
import de.metas.esb.camel.route.Xml2JsonRequestResponseRouteBuilder;

public class ArchiveGetDataRoute
		extends Xml2JsonRequestResponseRouteBuilder<ArchiveGetDataRequest, ArchiveGetDataResponse, ADArchiveGetDataResponseType>
{
	@Override
	protected void configurePrepare()
	{
		setJaxbContextPath(JAXBConstants.JAXB_ContextPath);
		setJaxbObjectFactory(JAXBConstants.JAXB_ObjectFactory);
		setExceptionEndpoint(CamelConstants.EP_ERRORS);

		setFromJsonEndpoint(RESTHttpArchiveEndpointRoute.EP_GetArchiveData);
		setToXmlEndpoint(CamelConstants.EP_ADEMPIERE_TO);

		setJsonRequestClass(ArchiveGetDataRequest.class);
		setJsonResponseClass(ArchiveGetDataResponse.class);

		setJsonRequestConverter(ArchiveGetDataRequestConverter.instance);
		setXmlResponseConverter(ArchiveGetDataResponseConverter.instance);
	}
}
