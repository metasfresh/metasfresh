package org.compiere.apps;

import java.time.LocalDate;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Role;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/05731_Spaltenbreite_persitieren_%28103033707449%29
 */
public class AWindowSaveStateModel
{
	private static final String ACTION_Name = "org.compiere.apps.AWindowSaveStateModel.action";

	private static final transient Logger logger = LogManager.getLogger(AWindowSaveStateModel.class);
	private static final transient CCache<UserId, Boolean> userId2enabled = new CCache<>(I_AD_Role.Table_Name, 5, 0);

	public String getActionName()
	{
		return ACTION_Name;
	}

	public boolean isEnabled()
	{
		final Properties ctx = Env.getCtx();
		final UserId loggedUserId = Env.getLoggedUserId(ctx);
		return userId2enabled.getOrLoad(loggedUserId, this::retrieveEnabledNoFail);
	}

	private boolean retrieveEnabledNoFail(final UserId loggedUserId)
	{

		try
		{
			return retrieveEnabled(loggedUserId);
		}
		catch (Exception ex)
		{
			logger.error(ex.getLocalizedMessage(), ex);
			return false;
		}
	}

	private boolean retrieveEnabled(final UserId loggedUserId)
	{
		final AdWindowId windowId = AdWindowId.ofRepoIdOrNull(MTable.get(Env.getCtx(), I_AD_Field.Table_Name).getAD_Window_ID());
		if (windowId == null)
		{
			return false;
		}

		final ClientId clientId = Env.getClientId();
		final LocalDate date = Env.getLocalDate();

		//
		// Makes sure the logged in user has at least one role assigned which has read-write access to our window
		return Services.get(IUserRolePermissionsDAO.class)
				.matchUserRolesPermissionsForUser(
						clientId,
						loggedUserId,
						date,
						rolePermissions -> rolePermissions.checkWindowPermission(windowId).hasWriteAccess());
	}

	public void save(final GridTab gridTab)
	{
		Services.get(ITrxManager.class).runInNewTrx((TrxRunnable)localTrxName -> save0(gridTab, localTrxName));
	}

	private void save0(final GridTab gridTab, final String trxName)
	{
		Check.assumeNotNull(gridTab, "gridTab not null");
		for (final GridField gridField : gridTab.getFields())
		{
			save(gridField, trxName);
		}
	}

	private void save(final GridField gridField, final String trxName)
	{
		final GridFieldVO gridFieldVO = gridField.getVO();
		final int adFieldId = gridFieldVO.getAD_Field_ID();
		if (adFieldId <= 0)
		{
			return;
		}

		final I_AD_Field adField = InterfaceWrapperHelper.create(gridFieldVO.getCtx(), adFieldId, I_AD_Field.class, trxName);
		if (adField == null)
		{
			return;
		}

		adField.setIsDisplayedGrid(gridFieldVO.isDisplayedGrid());
		adField.setSeqNoGrid(gridFieldVO.getSeqNoGrid());
		adField.setColumnDisplayLength(gridFieldVO.getLayoutConstraints().getColumnDisplayLength());
		InterfaceWrapperHelper.save(adField);
	}

}
