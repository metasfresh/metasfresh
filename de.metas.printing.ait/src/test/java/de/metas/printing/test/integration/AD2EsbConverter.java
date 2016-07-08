package de.metas.printing.test.integration;

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

import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.junit.Ignore;

import de.metas.printing.esb.base.jaxb.generated.PRTADPrinterHWType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintJobInstructionsConfirmType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageDataType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageInfoType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageLookupTransactionIDType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageType;
import de.metas.printing.esb.base.jaxb.generated.PRTLoginRequestType;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_User_Login;
import de.metas.printing.model.I_C_PrintPackageData;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;

/**
 * Utility class that simulates metasfresh's replication import processor and request/response framework.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Ignore
public class AD2EsbConverter
{
	private final transient Properties ctx = Env.getCtx();
	private final transient String trxName = Services.get(ITrxManager.class).createTrxName(AD2EsbConverter.class.getName());

	public Properties getCtx()
	{
		return ctx;
	}

	public I_AD_PrinterHW toADempiere(final PRTADPrinterHWType xml)
	{
		final I_AD_PrinterHW printerHW = InterfaceWrapperHelper.create(ctx, I_AD_PrinterHW.class, trxName);
		printerHW.setName(xml.getName());
		POJOWrapper.save(printerHW);

		// TODO: convert xml.getPRTADPrinterHWMediaSize()
		// TODO: convert xml.getPRTADPrinterHWMediaTray()

		return printerHW;
	}

	public I_C_Print_Package toADempiere(final PRTCPrintPackageType xml)
	{
		final I_C_Print_Package printPackage = POJOWrapper.create(ctx, I_C_Print_Package.class, trxName);
		printPackage.setBinaryFormat(xml.getBinaryFormat());
		printPackage.setTransactionID(xml.getTransactionID());
		InterfaceWrapperHelper.save(printPackage);

		if (xml.getPRTCPrintPackageInfo() != null)
		{
			for (final PRTCPrintPackageInfoType xmlPackageInfo : xml.getPRTCPrintPackageInfo())
			{
				toADempiere(printPackage, xmlPackageInfo);
			}
		}

		return printPackage;
	}

	private I_C_Print_PackageInfo toADempiere(final I_C_Print_Package printPackage, final PRTCPrintPackageInfoType xmlPackageInfo)
	{
		// not needed now
		throw new UnsupportedOperationException("Method not supported: printPackage=" + printPackage + ", xmlPackageInfo=" + xmlPackageInfo);
	}

	public I_C_Print_Job_Instructions toADempiere(final PRTCPrintJobInstructionsConfirmType xml)
	{
		final I_C_Print_Job_Instructions printJobInstructions = InterfaceWrapperHelper.create(ctx, I_C_Print_Job_Instructions.class, trxName);
		printJobInstructions.setC_Print_Job_Instructions_ID(xml.getCPrintJobInstructionsID().intValue());
		printJobInstructions.setStatus(xml.getStatus() == null ? null : xml.getStatus().value());
		printJobInstructions.setErrorMsg(xml.getErrorMsg());

		POJOWrapper.save(printJobInstructions);

		// TODO: convert xml.getPRTADPrinterHWMediaSize()
		// TODO: convert xml.getPRTADPrinterHWMediaTray()

		return printJobInstructions;
	}

	public PRTCPrintPackageType toXml(final I_C_Print_Package printPackage)
	{
		final PRTCPrintPackageType xml = new PRTCPrintPackageType();
		xml.setTransactionID(printPackage.getTransactionID());
		xml.setBinaryFormat(printPackage.getBinaryFormat());
		// xml.setPageCount(printPackage.getPageCount());

		for (final I_C_Print_PackageInfo info : retrievePrintPackageInfos(printPackage))
		{
			final PRTCPrintPackageInfoType xmlInfo = toXml(info);
			xml.getPRTCPrintPackageInfo().add(xmlInfo);
		}

		return xml;
	}

	private PRTCPrintPackageInfoType toXml(final I_C_Print_PackageInfo info)
	{
		final PRTCPrintPackageInfoType xml = new PRTCPrintPackageInfoType();
		// xml.setName(value)
		xml.setPageFrom(BigInteger.valueOf(info.getPageFrom()));
		xml.setPageTo(BigInteger.valueOf(info.getPageTo()));
		xml.setPrintServiceName(info.getPrintServiceName());
		xml.setTrayName(info.getPrintServiceTray());

		return xml;
	}

	public I_C_Print_Package toADempiere(final PRTCPrintPackageDataType xml)
	{
		final I_C_Print_Package printPackage = POJOLookupMap.get().getFirstOnly(I_C_Print_Package.class, new IQueryFilter<I_C_Print_Package>()
		{

			@Override
			public boolean accept(final I_C_Print_Package pojo)
			{
				return Check.equals(xml.getCPrintPackageID().getTransactionID(), pojo.getTransactionID());
			}
		});
		Check.assume(printPackage != null, "No print package found");

		return printPackage;
	}

	public PRTCPrintPackageDataType toXml(final I_C_PrintPackageData printPackageData)
	{
		final PRTCPrintPackageDataType xml = new PRTCPrintPackageDataType();

		xml.setCPrintPackageID(new PRTCPrintPackageLookupTransactionIDType());
		xml.getCPrintPackageID().setTransactionID(printPackageData.getC_Print_Package().getTransactionID());

		xml.setPrintData(printPackageData.getPrintData());

		return xml;
	}

	private List<I_C_Print_PackageInfo> retrievePrintPackageInfos(final I_C_Print_Package printPackage)
	{
		return POJOLookupMap.get().getRecords(I_C_Print_PackageInfo.class, new IQueryFilter<I_C_Print_PackageInfo>()
		{

			@Override
			public boolean accept(final I_C_Print_PackageInfo pojo)
			{
				return pojo.getC_Print_Package_ID() == printPackage.getC_Print_Package_ID();
			}
		});
	}

	public I_AD_User_Login toADempiere(PRTLoginRequestType xmlRequest)
	{
		final I_AD_User_Login loginRequest = InterfaceWrapperHelper.create(ctx, I_AD_User_Login.class, trxName);
		loginRequest.setUserName(xmlRequest.getUserName());
		loginRequest.setPassword(xmlRequest.getPassword());
		loginRequest.setHostKey(xmlRequest.getHostKey());
		// InterfaceWrapperHelper.save(loginRequest); // NOT needed
		return loginRequest;
	}

	public PRTLoginRequestType toXml(final I_AD_User_Login userLogin)
	{
		final PRTLoginRequestType xmlLoginResponse = new PRTLoginRequestType();
		xmlLoginResponse.setUserName(userLogin.getUserName());
		// loginResponse.setPassword(userLogin.getPassword()); // not returning the password
		xmlLoginResponse.setADSessionID(BigInteger.valueOf(userLogin.getAD_Session_ID()));
		xmlLoginResponse.setHostKey(userLogin.getHostKey());
		return xmlLoginResponse;
	}
}
