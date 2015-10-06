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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrintPackageInfo;
import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.generated.ObjectFactory;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageInfoType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageType;
import de.metas.printing.esb.base.jaxb.generated.ReplicationEventEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationModeEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationTypeEnum;

/**
 * {@link PRTCPrintPackageType} converters.
 * 
 * NOTE: please don't add here camel related code
 * 
 * @author tsa
 * 
 */
public class PRTCPrintPackageTypeConverter
{
	protected final ObjectFactory factory = new ObjectFactory();
	private static final Logger logger = Logger.getLogger(PRTCPrintPackageTypeConverter.class.getName());

	public PRTCPrintPackageType createPRTCPrintPackageTypeRequest(final int sessionId, final String transactionId)
	{
		final PRTCPrintPackageType printPackage = new PRTCPrintPackageType();

		printPackage.setTransactionID(transactionId);

		// ADempiere Specific Data
		printPackage.setADSessionIDAttr(BigInteger.valueOf(sessionId));
		printPackage.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		printPackage.setReplicationModeAttr(ReplicationModeEnum.Table);
		printPackage.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		printPackage.setVersionAttr(JAXBConstants.PRT_C_PRINT_PACKAGE_FORMAT_VERSION);

		return printPackage;
	}

	public PrintPackage createPackageTypeResponse(final PRTCPrintPackageType xmlPrintPackage)
	{
		final PRTCPrintPackageType xmlPrintPackageToUse;
		if (xmlPrintPackage == null)
		{
			xmlPrintPackageToUse = factory.createPRTCPrintPackageType();
			xmlPrintPackageToUse.getPRTCPrintPackageInfo().add(factory.createPRTCPrintPackageInfoType());
		}
		else
		{
			xmlPrintPackageToUse = xmlPrintPackage;
		}

		final PrintPackage printPckgPC = new PrintPackage();

		printPckgPC.setTransactionId(xmlPrintPackageToUse.getTransactionID());
		printPckgPC.setPageCount(toInt(xmlPrintPackageToUse.getPageCount(), 0));
		printPckgPC.setFormat(xmlPrintPackageToUse.getBinaryFormat());

		
		printPckgPC.setCopies(xmlPrintPackageToUse.getCopies() == null ? 1 : xmlPrintPackageToUse.getCopies().intValue()); // task 08958

		if (xmlPrintPackageToUse.getCPrintPackageID() != null
				&& xmlPrintPackageToUse.getCPrintJobInstructionsID() == null)
		{
			logger.log(Level.SEVERE, "Print job Instructions ID cannot be null!");

			throw new IllegalArgumentException("Print job Instructions ID cannot be null!");
		}

		// Avoid NPE, because if printPckgAD.getCPrintPackageID() is null, it means the message is meant to be empty and it's treated elsewhere
		if (xmlPrintPackageToUse.getCPrintJobInstructionsID() != null)
		{
			printPckgPC.setPrintJobInstructionsID(xmlPrintPackageToUse.getCPrintJobInstructionsID().toString());
		}

		printPckgPC.setPrintPackageInfos(mkPrintPackageInfoList(xmlPrintPackageToUse.getPRTCPrintPackageInfo()));

		return printPckgPC;
	}

	private static List<PrintPackageInfo> mkPrintPackageInfoList(final List<PRTCPrintPackageInfoType> printPckgInfosAD)
	{
		final List<PrintPackageInfo> printPckgInfosPC = new ArrayList<PrintPackageInfo>();

		for (final PRTCPrintPackageInfoType printPckgInfoAD : printPckgInfosAD)
		{
			final PrintPackageInfo printPckgInfoPC = new PrintPackageInfo();

			printPckgInfoPC.setPrintService(printPckgInfoAD.getPrintServiceName());
			printPckgInfoPC.setTray(printPckgInfoAD.getTrayName());
			printPckgInfoPC.setTrayNumber(
					printPckgInfoAD.getTrayNumber() == null
							? -1
							: printPckgInfoAD.getTrayNumber().intValue());
			printPckgInfoPC.setPageFrom(toInt(printPckgInfoAD.getPageFrom(), 0));
			printPckgInfoPC.setPageTo(toInt(printPckgInfoAD.getPageTo(), 0));

			printPckgInfoPC.setCalX(printPckgInfoAD.getCalX() == null ? 0 : printPckgInfoAD.getCalX().intValue());
			printPckgInfoPC.setCalY(printPckgInfoAD.getCalY() == null ? 0 : printPckgInfoAD.getCalY().intValue());

			printPckgInfosPC.add(printPckgInfoPC);
		}

		return printPckgInfosPC;
	}

	private static int toInt(BigInteger value, int defaultValue)
	{
		return value == null ? defaultValue : value.intValue();
	}
}
