package org.eevolution.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.IResourceDAO;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPContextFactory;
import org.eevolution.mrp.api.IMRPExecutorService;
import org.eevolution.mrp.api.IMRPResult;
import org.eevolution.mrp.api.IMutableMRPContext;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public abstract class AbstractMRPProcess extends JavaProcess
{
	// Services
	private final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);
	protected final IMRPExecutorService mrpExecutorService = Services.get(IMRPExecutorService.class);

	//
	// Parameters
	private int p_AD_Org_ID = -1;
	private int p_S_Resource_ID = -1;
	private int p_M_Warehouse_ID = -1;
	private boolean p_IsRequiredDRP = false;
	private int p_Planner_ID = -1;
	@SuppressWarnings("unused")
	private String p_Version = "1";
	/** Product ID - for testing purposes */
	private int p_M_Product_ID = -1;
	private boolean p_MRP_Cleanup = true;

	@Override
	protected final void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
			{
				p_AD_Org_ID = para.getParameterAsInt();
			}
			else if (name.equals("S_Resource_ID"))
			{
				p_S_Resource_ID = para.getParameterAsInt();
			}
			else if (name.equals("M_Warehouse_ID"))
			{
				p_M_Warehouse_ID = para.getParameterAsInt();
			}
			else if (name.equals("M_Product_ID"))
			{
				p_M_Product_ID = para.getParameterAsInt();
			}
			else if (name.equals("IsRequiredDRP"))
			{
				p_IsRequiredDRP = para.getParameterAsBoolean();
			}
			else if (name.equals("Version"))
			{
				p_Version = (String)para.getParameter();
			}
			else if ("MRP_Cleanup".equals(name))
			{
				p_MRP_Cleanup = para.getParameterAsBoolean();
			}
		}
	}	// prepare

	@Override
	protected final String doIt() throws Exception
	{
		final IMRPContext mrpContext = createMRPContext();
		final IMRPResult mrpResult = run(mrpContext);
		return mrpResult.getSummary(getCtx());
	}

	protected abstract IMRPResult run(final IMRPContext mrpContext);

	/**
	 * Creates {@link IMRPContext} from process parameters.
	 * 
	 * @return created MRP Context
	 */
	protected final IMRPContext createMRPContext()
	{
		final IMutableMRPContext mrpContext = mrpContextFactory.createInitialMRPContext();

		mrpContext.setCtx(getCtx());
		mrpContext.setTrxName(ITrx.TRXNAME_None);
		mrpContext.setAllowCleanup(p_MRP_Cleanup);
		mrpContext.setRequireDRP(p_IsRequiredDRP);
		mrpContext.setPlanner_User_ID(p_Planner_ID);
		mrpContext.setDate(SystemTime.asTimestamp());
		mrpContext.setAD_Client_ID(getAD_Client_ID());
		if (p_AD_Org_ID > 0)
		{
			final I_AD_Org org = orgDAO.retrieveOrg(getCtx(), p_AD_Org_ID);
			Check.assumeNotNull(org, LiberoException.class, "org not null");
			mrpContext.setAD_Org(org);
		}
		if (p_S_Resource_ID > 0)
		{
			final I_S_Resource plant = resourceDAO.retrievePlant(getCtx(), p_S_Resource_ID);
			Check.assumeNotNull(plant, LiberoException.class, "plant not null");
			mrpContext.setPlant(plant);
		}
		if (p_M_Warehouse_ID > 0)
		{
			final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(getCtx(), p_M_Warehouse_ID, I_M_Warehouse.class, ITrx.TRXNAME_None);
			Check.assumeNotNull(warehouse, LiberoException.class, "warehouse not null");
			mrpContext.setM_Warehouse(warehouse);
		}
		if (p_M_Product_ID > 0)
		{
			final I_M_Product product = InterfaceWrapperHelper.create(getCtx(), p_M_Product_ID, I_M_Product.class, ITrx.TRXNAME_None);
			Check.assumeNotNull(product, LiberoException.class, "product not null");
			mrpContext.setM_Product(product);
		}

		return mrpContext;
	}
}
