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

import org.compiere.util.Util;

import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageDataType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageLookupTransactionIDType;
import de.metas.printing.esb.base.jaxb.generated.ReplicationEventEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationModeEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationTypeEnum;

public class GetPrintPackageDataResponder extends AbstractPrintingClientResponder<PRTCPrintPackageDataType, PRTCPrintPackageDataType>
{
	public static final String TRANSACTION_ID_NOT_AVAILABLE = "N/A";

	public transient String transactionIdExpected;

	public transient byte[] responseData = null;

	@Override
	protected void validateRequest(final PRTCPrintPackageDataType xmlRequest)
	{
		assertNotNull("Expected transactionId not configured", transactionIdExpected);
		if (!Util.same(transactionIdExpected, TRANSACTION_ID_NOT_AVAILABLE))
		{
			assertEquals("Invalid transactionId", transactionIdExpected, xmlRequest.getCPrintPackageID().getTransactionID());
		}

		assertEquals("Invalid sessionId in request", sessionIdExpected, xmlRequest.getADSessionIDAttr());
	}

	@Override
	protected PRTCPrintPackageDataType createResponse(final PRTCPrintPackageDataType xmlRequest)
	{
		final PRTCPrintPackageDataType xmlResponse = new PRTCPrintPackageDataType();
		xmlResponse.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		xmlResponse.setReplicationModeAttr(ReplicationModeEnum.Table);
		xmlResponse.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		xmlResponse.setVersionAttr(JAXBConstants.PRT_AD_PRINTER_HW_FORMAT_VERSION);
		xmlResponse.setADSessionIDAttr(xmlRequest.getADSessionIDAttr());
		xmlResponse.setPrintData(responseData);

		xmlResponse.setCPrintPackageID(new PRTCPrintPackageLookupTransactionIDType());
		xmlResponse.getCPrintPackageID().setTransactionID(xmlRequest.getCPrintPackageID().getTransactionID());

		return xmlResponse;
	}

}
