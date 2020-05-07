package de.metas.adempiere.engine;

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


import java.util.HashSet;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_Table_MView;
import de.metas.adempiere.service.ITableMViewBL;
import de.metas.adempiere.service.ITableMViewBL.RefreshMode;
import de.metas.adempiere.service.impl.TableMViewBL;
import de.metas.logging.LogManager;

public class MViewModelValidator implements ModelValidator
{
	private final Logger log = LogManager.getLogger(getClass());

	private int m_AD_Client_ID = -1;

	private final Set<Integer> registeredTables = new HashSet<Integer>();

	private ModelValidationEngine engine;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();
		this.engine = engine;

		Services.registerService(ITableMViewBL.class, new TableMViewBL());

		engine.addModelChange(I_AD_Table_MView.Table_Name, this);

		final ITableMViewBL mviewBL = Services.get(ITableMViewBL.class);
		for (I_AD_Table_MView mview : mviewBL.fetchAll(Env.getCtx()))
		{
			addMView(mview, false);
		}
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (I_AD_Table_MView.Table_Name.equals(po.get_TableName()))
		{
			processMetadataChanges(po, type);
		}
		else if (TYPE_AFTER_NEW == type || TYPE_AFTER_CHANGE == type || TYPE_AFTER_DELETE == type)
		{
			refreshFromSource(po, type);
		}

		return null;
	}

	private void processMetadataChanges(PO po, int type)
	{
		final I_AD_Table_MView mview = InterfaceWrapperHelper.create(po, I_AD_Table_MView.class);
		if (TYPE_AFTER_NEW == type
				|| (TYPE_AFTER_CHANGE == type && po.is_ValueChanged(I_AD_Table_MView.COLUMNNAME_IsValid) && mview.isValid()))
		{
			addMView(mview, false);
		}
	}

	private void refreshFromSource(PO sourcePO, int changeType)
	{
		final String sourceTableName = sourcePO.get_TableName();

		final ITableMViewBL mviewBL = Services.get(ITableMViewBL.class);

		for (I_AD_Table_MView mview : mviewBL.fetchAll(Env.getCtx()))
		{
			MViewMetadata mdata = mviewBL.getMetadata(mview);
			if (mdata == null)
			{
				mview.setIsValid(false);
				InterfaceWrapperHelper.save(mview);
				log.info("No metadata found for " + mview + " [SKIP]");
				continue;
			}

			if (!mdata.getSourceTables().contains(sourceTableName))
			{
				// not relevant PO for this MView
				continue;
			}

			if (!mviewBL.isSourceChanged(mdata, sourcePO, changeType))
			{
				// no relevant changes for this view
				continue;
			}

			refreshFromSource(mdata, sourcePO, new RefreshMode[] { RefreshMode.Partial });
		}

	}

	public void refreshFromSource(MViewMetadata mdata, PO sourcePO, RefreshMode[] refreshModes)
	{
		final ITableMViewBL mviewBL = Services.get(ITableMViewBL.class);
		final I_AD_Table_MView mview = mviewBL.fetchForTableName(sourcePO.getCtx(), mdata.getTargetTableName(), sourcePO.get_TrxName());

		boolean staled = true;
		for (RefreshMode refreshMode : refreshModes)
		{
			if (mviewBL.isAllowRefresh(mview, sourcePO, refreshMode))
			{
				mviewBL.refresh(mview, sourcePO, refreshMode, sourcePO.get_TrxName());
				staled = false;
				break;
			}
		}

		//
		// We should do a complete refresh => marking mview as staled
		if (staled)
		{
			mviewBL.setStaled(mview);
		}
	}

	private boolean addMView(I_AD_Table_MView mview, boolean invalidateIfNecessary)
	{
		final ITableMViewBL mviewBL = Services.get(ITableMViewBL.class);
		MViewMetadata mdata = mviewBL.getMetadata(mview);
		if (mdata == null)
		{
			// no metadata found => IGNORE
			if (invalidateIfNecessary)
			{
				mview.setIsValid(false);
				InterfaceWrapperHelper.save(mview);
			}
			return false;
		}
		for (String sourceTableName : mdata.getSourceTables())
		{
			engine.addModelChange(sourceTableName, this);
		}
		registeredTables.add(mview.getAD_Table_ID());
		return true;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}
}
