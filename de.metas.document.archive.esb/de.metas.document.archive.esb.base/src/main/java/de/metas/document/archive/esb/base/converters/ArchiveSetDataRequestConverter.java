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

import de.metas.document.archive.esb.api.ArchiveSetDataRequest;
import de.metas.document.archive.esb.base.jaxb.ADArchiveSetDataRequestType;
import de.metas.document.archive.esb.base.jaxb.JAXBConstants;
import de.metas.document.archive.esb.base.jaxb.ReplicationEventEnum;
import de.metas.document.archive.esb.base.jaxb.ReplicationModeEnum;
import de.metas.document.archive.esb.base.jaxb.ReplicationTypeEnum;

public class ArchiveSetDataRequestConverter implements Converter<ADArchiveSetDataRequestType, ArchiveSetDataRequest>
{
	public static final transient ArchiveSetDataRequestConverter instance = new ArchiveSetDataRequestConverter();

	public static final BigInteger ARCHIVE_TABLE_ID = BigInteger.valueOf(754);

	@Override
	public ADArchiveSetDataRequestType convert(final ArchiveSetDataRequest request)
	{
		final ADArchiveSetDataRequestType requestXml = new ADArchiveSetDataRequestType();

		final String name = String.valueOf(request.getAdArchiveId());

		requestXml.setName(name);
		requestXml.setADTableID(ArchiveSetDataRequestConverter.ARCHIVE_TABLE_ID);
		requestXml.setRecordID(BigInteger.valueOf(request.getAdArchiveId()));
		requestXml.setBinaryData(request.getData());
		// ADempiere Specific Data
		requestXml.setADSessionIDAttr(BigInteger.valueOf(request.getSessionId()));
		requestXml.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		requestXml.setReplicationModeAttr(ReplicationModeEnum.Table);
		requestXml.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		requestXml.setVersionAttr(JAXBConstants.EXPORT_FORMAT_VERSION);


		return requestXml;
	}

}
