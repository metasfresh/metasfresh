package de.metas.adempiere.process;

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


import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.MInOut;
import org.compiere.model.MPackage;
import org.compiere.model.Query;
import org.compiere.model.X_M_Package;

import de.metas.adempiere.service.IPackageInfoService;
import de.metas.inout.model.I_M_InOut;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class PackageLabel extends SvrProcess
{

	private static final String PARAM_SERVICE_CODE_OVERRIDE = "ServiceCode";
	public static final String PARAM_ACTION = "Action";
	private static final String PARAM_M_Shipper_ID = "M_Shipper_ID";
	public static final String ACTION_PRINT = "createPrint";
	public static final String ACTION_DISCARD = "discard";
	public static final String ACTION_CHANGE_DELIVERYTYPE = "changeDelivery";

	private String action = ACTION_PRINT;

	private String serviceCodeOverride = null;

	private BigDecimal M_Shipper_ID = null;

	private MPackage pack = null;

	@Override
	protected String doIt() throws Exception
	{

		final I_M_InOut inOut = InterfaceWrapperHelper.create(retrieveInOut(), I_M_InOut.class);
		final IPackageInfoService packageInfoService = Services.get(IPackageInfoService.class);

		if (M_Shipper_ID != null && pack != null)
		{
			pack.setM_Shipper_ID(M_Shipper_ID.intValue());
			pack.saveEx();
		}

		final Properties ctx = getCtx();
		final String trxName = get_TrxName();
		if (ACTION_PRINT.equals(action))
		{
			packageInfoService.createLabel(ctx, inOut, serviceCodeOverride, pack, trxName);
			packageInfoService.printLabel(ctx, inOut, pack, M_Shipper_ID, trxName);
		}
		else if (ACTION_DISCARD.equals(action))
		{
			packageInfoService.discardLabel(ctx, inOut, pack, M_Shipper_ID, trxName);
		}
		else if (ACTION_CHANGE_DELIVERYTYPE.equals(action))
		{
			// the delivery type is already changed
			// is shipper is not DPD, cancel tracking info
			String shipperName = "";
			if (pack == null)
			{
				if (M_Shipper_ID == null)
				{
					shipperName = inOut.getM_Shipper().getName();
				}
				else
				{
					final I_M_Shipper shipper = InterfaceWrapperHelper.create(ctx, M_Shipper_ID.intValue(), I_M_Shipper.class, ITrx.TRXNAME_None);
					shipperName = shipper.getName();
				}
			}
			else
				shipperName = pack.getM_Shipper().getName();
			if (!"DPD".equals(shipperName))
			{
				packageInfoService.discardLabel(ctx, inOut, pack, M_Shipper_ID, trxName);
			}
			else if ("DPD".equals(shipperName))
			{
				packageInfoService.createLabel(ctx, inOut, serviceCodeOverride, pack, trxName);
				packageInfoService.printLabel(ctx, inOut, pack, M_Shipper_ID, trxName);
			}
			else
				throw new AdempiereException("Shipper not supported!");

		}
		return "@Success@";
	}

	private MInOut retrieveInOut()
	{

		final int tableId = getTable_ID();

		if (tableId == I_M_Package.Table_ID)
		{

			pack = new MPackage(getCtx(), getRecord_ID(),
					get_TrxName());

			return new MInOut(getCtx(), pack.getM_InOut_ID(), get_TrxName());

		}
		else if (tableId == I_M_InOut.Table_ID)
		{
			MInOut io = new MInOut(getCtx(), getRecord_ID(), get_TrxName());
			if (M_Shipper_ID != null)
			{
				String whereClause = I_M_Package.COLUMNNAME_M_InOut_ID + " = ?";
				List<X_M_Package> list = new Query(io.getCtx(), X_M_Package.Table_Name, whereClause, io.get_TrxName())
									.setParameters(getRecord_ID())
									.setClient_ID()
									.list();
				for (X_M_Package packg : list)
				{
					packg.setM_Shipper_ID(M_Shipper_ID.intValue());
					packg.saveEx();
				}
			}
			return io;
		}

		throw new AdempiereException("illegal Table_ID id for this process: "
				+ tableId + "; Expecting M_Inout or M_Package");
	}

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
			else if (name.equals(PARAM_ACTION))
			{
				action = (String)para[i].getParameter();
			}
			else if (name.equals(PARAM_SERVICE_CODE_OVERRIDE))
			{
				serviceCodeOverride = (String)para[i].getParameter();
			}
			else if (name.equals(PARAM_M_Shipper_ID))
			{
				M_Shipper_ID = (BigDecimal)para[i].getParameter();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}

}
