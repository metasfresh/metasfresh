package de.metas.document.archive.esb.base.converters;

/*
 * #%L
 * de.metas.document.archive.esb.base
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


import java.math.BigInteger;

import org.adempiere.util.collections.Converter;

import de.metas.document.archive.esb.api.ArchiveGetDataRequest;
import de.metas.document.archive.esb.base.jaxb.ADArchiveGetDataRequestType;
import de.metas.document.archive.esb.base.jaxb.JAXBConstants;
import de.metas.document.archive.esb.base.jaxb.ReplicationEventEnum;
import de.metas.document.archive.esb.base.jaxb.ReplicationModeEnum;
import de.metas.document.archive.esb.base.jaxb.ReplicationTypeEnum;

/**
 * Convert {@link ArchiveGetDataRequest}(json) to {@link ADArchiveGetDataRequestType}(xml)
 * 
 * @author tsa
 * 
 */
public class ArchiveGetDataRequestConverter implements Converter<ADArchiveGetDataRequestType, ArchiveGetDataRequest>
{
	public static final transient ArchiveGetDataRequestConverter instance = new ArchiveGetDataRequestConverter();

	@Override
	public ADArchiveGetDataRequestType convert(final ArchiveGetDataRequest request)
	{
		final ADArchiveGetDataRequestType requestXml = new ADArchiveGetDataRequestType();
		requestXml.setADArchiveID(BigInteger.valueOf(request.getAdArchiveId()));
		// ADempiere Specific Data
		requestXml.setADSessionIDAttr(BigInteger.valueOf(request.getSessionId()));
		requestXml.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		requestXml.setReplicationModeAttr(ReplicationModeEnum.Table);
		requestXml.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		requestXml.setVersionAttr(JAXBConstants.EXPORT_FORMAT_VERSION);

		return requestXml;
	}

}
