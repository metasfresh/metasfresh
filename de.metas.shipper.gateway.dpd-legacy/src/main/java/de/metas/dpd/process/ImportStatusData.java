package de.metas.dpd.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Package;
import org.compiere.model.MPackage;
import org.compiere.model.Query;
import org.compiere.util.Trx;
import org.compiere.util.Util;

import de.metas.dpd.model.MDPDExceptionCode;
import de.metas.dpd.model.MDPDScanCode;
import de.metas.dpd.model.MDPDStatusData;
import de.metas.dpd.service.Iso7064;
import de.metas.impex.model.I_ImpEx_Connector;
import de.metas.impex.model.MImpExConnector;
import de.metas.impex.spi.IImportConnector;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public class ImportStatusData extends JavaProcess
{

	private static final String DATE_FMT = "yyyyMMddhhmmss";

	private static final String DELIM = ";";

	@Override
	protected String doIt() throws Exception
	{
		final Trx trx = Trx.get(get_TrxName(), false);
		final String savepointName = ImportStatusData.class.getName();

		ITrxSavepoint savepoint = trx.createTrxSavepoint(savepointName);

		final IImportConnector connectorImpl = (IImportConnector)connector.useConnector();

		InputStream inputStream = connectorImpl.connectNext(true);

		while (inputStream != null)
		{
			final boolean success = importFile(inputStream);

			if (success)
			{
				trx.releaseSavepoint(savepoint);
				commitEx();

				savepoint = trx.createTrxSavepoint(savepointName);
			}
			else
			{
				trx.rollback(savepoint);
			}

			inputStream = connectorImpl.connectNext(success);
		}
		return "@Success@";
	}

	private MImpExConnector connector;

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();

			if (para[i].getParameter() == null)
			{
				// do nothing
			}
			else if (name.equals(I_ImpEx_Connector.COLUMNNAME_ImpEx_Connector_ID))
			{

				final int connectorId = ((BigDecimal)para[i].getParameter()).intValue();
				connector = new MImpExConnector(getCtx(), connectorId, get_TrxName());
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}

	private boolean importFile(final InputStream in) throws AdempiereException, IOException, ParseException
	{
		final SimpleDateFormat dateFmt = new SimpleDateFormat(DATE_FMT);

		int lineCount = 0;
		String line = null;

		final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		while ((line = reader.readLine()) != null)
		{
			if (lineCount == 0)
			{
				// skip header line;
				lineCount++;
				continue;
			}

			final String[] fields = line.split(DELIM);

			final String parcelNo = getValIf(fields, 0);
			final String strScanCodeNo = getValIf(fields, 1);
			final String depotCode = getValIf(fields, 2);
			final String depotName = getValIf(fields, 3);
			final String strDateTime = getValIf(fields, 4);

			final String route = getValIf(fields, 5);
			final String tour = getValIf(fields, 6);
			final String pCode = getValIf(fields, 7);
			final String service = getValIf(fields, 8);
			final String consigneeCountryCode = getValIf(fields, 9);
			final String consigneeZip = getValIf(fields, 10);
			final String strAddService1 = getValIf(fields, 11);
			final String strAddService2 = getValIf(fields, 12);
			final String strAddService3 = getValIf(fields, 13);
			final String strWeight = getValIf(fields, 14);

			final String customerRef = getValIf(fields, 15);
			final String podImageRef = getValIf(fields, 16);
			final String receiverName = getValIf(fields, 17);
			final String infoText = getValIf(fields, 18);
			final String location = getValIf(fields, 19);

			final Timestamp eventDateTime;
			if (Util.isEmpty(strDateTime))
			{
				eventDateTime = null;
			}
			else
			{
				eventDateTime = new Timestamp(dateFmt.parse(strDateTime).getTime());
			}

			final MDPDScanCode scanCode = MDPDScanCode.retrieve(getCtx(), strScanCodeNo, get_TrxName());

			final MDPDStatusData statusData = MDPDStatusData.retrieveOrCreate(
					getCtx(),
					parcelNo,
					scanCode.get_ID(),
					eventDateTime,
					get_TrxName());

			statusData.setParcellNo(parcelNo);

			final MPackage pack = retrievePackage(parcelNo);
			if (pack == null)
			{
				statusData.setM_Package_ID(0);
			}
			else
			{
				statusData.setM_Package_ID(pack.get_ID());
			}

			statusData.setDPD_ScanCode_ID(scanCode.get_ID());
			statusData.setDepotCode(depotCode);
			statusData.setDepotName(depotName);
			statusData.setEventDateTime(eventDateTime);

			statusData.setRoute(route);
			statusData.setTour(tour);
			statusData.setPCode(pCode);
			statusData.setService(service);
			statusData.setConsigneeCountryCode(consigneeCountryCode);
			statusData.setConsigneeZip(consigneeZip);

			if (!Util.isEmpty(strAddService1))
			{
				final MDPDExceptionCode exCode = retrieveExCode(strAddService1);
				statusData.setAddService1_ID(exCode.get_ID());
			}

			if (!Util.isEmpty(strAddService2))
			{
				final MDPDExceptionCode exCode = retrieveExCode(strAddService2);
				statusData.setAddService2_ID(exCode.get_ID());
			}

			if (!Util.isEmpty(strAddService3))
			{
				final MDPDExceptionCode exCode = retrieveExCode(strAddService3);
				statusData.setAddService3_ID(exCode.get_ID());
			}

			if (!Util.isEmpty(strWeight))
			{
				final String replace = strWeight.replace(",", ".");
				final BigDecimal weight = new BigDecimal(replace);
				statusData.setWeight(weight);
			}

			statusData.setCustomerReference(customerRef);
			statusData.setPodImageRef(podImageRef);
			statusData.setReceiverName(receiverName);
			statusData.setInfoText(infoText);
			statusData.setLocation(location);
			statusData.saveEx();

			lineCount++;
		}

		addLog("Imported " + (lineCount - 1) + " records");
		return true;
	}

	private MDPDExceptionCode retrieveExCode(final String execptionCode)
	{
		final MDPDExceptionCode exCode = MDPDExceptionCode.retrieve(getCtx(), Integer.parseInt(execptionCode), get_TrxName());

		if (exCode == null)
		{
			throw new AdempiereException("Missing DPD exception code " + execptionCode);
		}

		return exCode;
	}

	@Cached
	private MPackage retrievePackage(final String parcelNo)
	{
		final char checkDigit = Iso7064.compute(parcelNo);

		final String whereClause = "REPLACE(" + I_M_Package.COLUMNNAME_TrackingInfo + ",' ','')=?";

		final Object[] parameters = { parcelNo + checkDigit };

		return new Query(getCtx(), I_M_Package.Table_Name, whereClause, get_TrxName()).setParameters(parameters).firstOnly();
	}

	private String getValIf(final String[] fields, final int idx)
	{
		if (fields.length > idx)
		{
			return (fields[idx]);
		}
		return "";
	}

}
