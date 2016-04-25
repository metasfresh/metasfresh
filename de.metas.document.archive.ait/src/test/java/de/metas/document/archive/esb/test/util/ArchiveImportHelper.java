package de.metas.document.archive.esb.test.util;

/*
 * #%L
 * de.metas.document.archive.esb.ait
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
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.api.IImportHelper;
import org.adempiere.server.rpl.api.impl.MockedImportHelper;
import org.adempiere.util.Check;
import org.adempiere.util.collections.Converter;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Ignore;

import de.metas.document.archive.esb.base.jaxb.ADArchiveGetDataRequestType;
import de.metas.document.archive.esb.base.jaxb.ADArchiveGetDataResponseType;
import de.metas.document.archive.esb.base.jaxb.ADArchiveSetDataRequestType;
import de.metas.document.archive.esb.base.jaxb.ADArchiveSetDataResponseType;
import de.metas.document.archive.esb.base.jaxb.JAXBConstants;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.rpl.requesthandler.ArchiveGetDataHandlerConverter;
import de.metas.document.archive.rpl.requesthandler.ArchiveSetDataHandlerConverter;

/**
 * Archive Replication Import Processor (mocked implementation)
 * 
 * @author tsa
 * 
 */
@Ignore
public class ArchiveImportHelper extends MockedImportHelper implements IImportHelper
{
	public ArchiveImportHelper()
	{
		try
		{
			setJAXBContext(JAXBContext.newInstance(JAXBConstants.JAXB_ContextPath));
		}
		catch (JAXBException e)
		{
			throw new AdempiereException(e);
		}
		setJAXBObjectFactory(JAXBConstants.JAXB_ObjectFactory);

		registerConverter(ADArchiveGetDataRequestType.class, new ADArchiveGetDataRequestTypeConverter());
		registerConverter(ADArchiveSetDataRequestType.class, new ADArchiveSetDataRequestTypeConverter());
	}

	/**
	 * Convert {@link ADArchiveGetDataRequestType} to {@link ADArchiveGetDataResponseType}
	 * 
	 * @author tsa
	 * 
	 */
	public static class ADArchiveGetDataRequestTypeConverter implements Converter<Object, Object>
	{
		protected void validateRequest(final ADArchiveGetDataRequestType xmlRequest)
		{
			final BigInteger sessionId = xmlRequest.getADSessionIDAttr();
			Check.assumeNotNull(sessionId, "No session ID found for " + xmlRequest);
			Check.assume(sessionId.intValue() > 0, "Invalid session ID '" + sessionId + "' found for " + xmlRequest);

			Assert.assertNotNull("Field ADArchiveID is null: " + xmlRequest, xmlRequest.getADArchiveID());
		}

		@Override
		public ADArchiveGetDataResponseType convert(final Object xmlRequestObj)
		{
			final Properties ctx = Env.getCtx();

			final ADArchiveGetDataRequestType xmlRequest = (ADArchiveGetDataRequestType)xmlRequestObj;
			validateRequest(xmlRequest);

			final int adArchiveId = xmlRequest.getADArchiveID().intValue();

			// Lookup for our archive
			final I_AD_Archive archiveRequest = InterfaceWrapperHelper.create(ctx, adArchiveId, I_AD_Archive.class, ITrx.TRXNAME_None);
			if (archiveRequest == null)
			{
				throw new AdempiereException("No archive found for AD_Archive_ID=" + adArchiveId);
			}

			// Now call the replication request handler
			final I_AD_Archive archiveResponse = ArchiveGetDataHandlerConverter.instance.convert(archiveRequest);

			// Return response
			final ADArchiveGetDataResponseType xmlResponse = new ADArchiveGetDataResponseType();
			xmlResponse.setADArchiveID(xmlRequest.getADArchiveID());
			xmlResponse.setBinaryData(archiveResponse.getBinaryData());

			return xmlResponse;
		}

	}

	/**
	 * Convert {@link ADArchiveSetDataRequestType} to {@link ADArchiveGetDataResponseType}
	 * 
	 * @author tsa
	 * 
	 */
	public static class ADArchiveSetDataRequestTypeConverter implements Converter<Object, Object>
	{
		protected void validateRequest(final ADArchiveSetDataRequestType xmlRequest)
		{
			final BigInteger sessionId = xmlRequest.getADSessionIDAttr();
			Check.assumeNotNull(sessionId, "No session ID found for " + xmlRequest);
			Check.assume(sessionId.intValue() > 0, "Invalid session ID '" + sessionId + "' found for " + xmlRequest);
		}

		@Override
		public ADArchiveSetDataResponseType convert(final Object xmlRequestObj)
		{
			final Properties ctx = Env.getCtx();

			final ADArchiveSetDataRequestType xmlRequest = (ADArchiveSetDataRequestType)xmlRequestObj;
			validateRequest(xmlRequest);

			final int adArchiveId = xmlRequest.getRecordID() == null ? -1 : xmlRequest.getRecordID().intValue();

			final byte[] data = xmlRequest.getBinaryData();

			// Save the received "XML"
			final I_AD_Archive archiveRequest = InterfaceWrapperHelper.create(ctx, I_AD_Archive.class, ITrx.TRXNAME_None);
			archiveRequest.setIsFileSystem(false);
			archiveRequest.setAD_Table_ID(MTable.getTable_ID(org.compiere.model.I_AD_Archive.Table_Name));
			archiveRequest.setRecord_ID(adArchiveId);
			archiveRequest.setBinaryData(data);
			InterfaceWrapperHelper.save(archiveRequest);

			// Now call the replication request handler
			final I_AD_Archive archiveResponse = ArchiveSetDataHandlerConverter.instance.convert(archiveRequest);

			// Return temporary archive's ID
			final ADArchiveSetDataResponseType xmlResponse = new ADArchiveSetDataResponseType();
			xmlResponse.setADArchiveID(BigInteger.valueOf(archiveResponse.getAD_Archive_ID()));

			return xmlResponse;
		}
	}
}
