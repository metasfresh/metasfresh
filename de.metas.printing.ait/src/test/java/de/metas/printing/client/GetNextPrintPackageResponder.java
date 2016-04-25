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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Ignore;

import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageInfoType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageType;
import de.metas.printing.esb.base.jaxb.generated.ReplicationEventEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationModeEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationTypeEnum;

@Ignore
public class GetNextPrintPackageResponder extends AbstractPrintingClientResponder<PRTCPrintPackageType, PRTCPrintPackageType>
{
	@Override
	protected void validateRequest(final PRTCPrintPackageType xmlRequest)
	{
		assertNotNull("Null transactionId found in request", xmlRequest.getTransactionID());
		assertTrue("Empty transactionId found in request", xmlRequest.getTransactionID().trim().length() > 0);
		assertEquals("Invalid sessionId in request", sessionIdExpected, xmlRequest.getADSessionIDAttr());
	}

	@Override
	protected PRTCPrintPackageType createResponse(final PRTCPrintPackageType xmlRequest)
	{
		return createDefaultResponse(xmlRequest);
	}

	protected PRTCPrintPackageType createDefaultResponse(final PRTCPrintPackageType xmlRequest)
	{
		final PRTCPrintPackageType xmlResponse = new PRTCPrintPackageType();
		xmlResponse.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		xmlResponse.setReplicationModeAttr(ReplicationModeEnum.Table);
		xmlResponse.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		xmlResponse.setVersionAttr(JAXBConstants.PRT_AD_PRINTER_HW_FORMAT_VERSION);
		xmlResponse.setBinaryFormat(PrintPackage.FORMAT_PDF);
		xmlResponse.setADSessionIDAttr(xmlRequest.getADSessionIDAttr());
		xmlResponse.setTransactionID(xmlRequest.getTransactionID());
		xmlResponse.setPageCount(BigInteger.valueOf(2));

		{
			final PRTCPrintPackageInfoType info = new PRTCPrintPackageInfoType();
			info.setPrintServiceName("Microsoft XPS Document Writer");
			info.setTrayName("tray01");
			info.setPageFrom(BigInteger.valueOf(1));
			info.setPageTo(BigInteger.valueOf(2));
			xmlResponse.getPRTCPrintPackageInfo().add(info);
		}
		{
			final PRTCPrintPackageInfoType info = new PRTCPrintPackageInfoType();
			info.setPrintServiceName("Microsoft XPS Document Writer");
			info.setTrayName("tray02");
			info.setPageFrom(BigInteger.valueOf(2));
			info.setPageTo(BigInteger.valueOf(2));
			xmlResponse.getPRTCPrintPackageInfo().add(info);
		}

		xmlResponse.setCPrintJobInstructionsID(BigInteger.valueOf(1234567));

		return xmlResponse;

	}
}
