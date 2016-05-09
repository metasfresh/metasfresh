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


import java.math.BigInteger;
import java.util.logging.Logger;

import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.generated.ObjectFactory;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageDataType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageLookupTransactionIDType;
import de.metas.printing.esb.base.jaxb.generated.ReplicationEventEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationModeEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationTypeEnum;

/**
 * {@link PRTCPrintPackageDataType} converters.
 * 
 * NOTE: please don't add here camel related code
 * 
 * @author tsa
 * 
 */
public class PRTCPrintPackageDataTypeConverter
{
	protected final transient Logger logger = Logger.getLogger(getClass().getName());
	protected final transient ObjectFactory factory = new ObjectFactory();

	public PRTCPrintPackageDataType createPRTCPrintPackageDataTypeRequest(final int sessionId, final String transactionId)
	{
		final PRTCPrintPackageDataType printPackage = factory.createPRTCPrintPackageDataType();

		// ADempiere Specific Data
		printPackage.setADSessionIDAttr(BigInteger.valueOf(sessionId));
		printPackage.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		printPackage.setReplicationModeAttr(ReplicationModeEnum.Table);
		printPackage.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		printPackage.setVersionAttr(JAXBConstants.PRT_C_PRINT_PACKAGE_FORMAT_VERSION);

		final PRTCPrintPackageLookupTransactionIDType lookupType = factory.createPRTCPrintPackageLookupTransactionIDType();
		lookupType.setTransactionID(transactionId);
		printPackage.setCPrintPackageID(lookupType);

		return printPackage;
	}
}
