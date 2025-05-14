package org.compiere.apps;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.element.api.AdFieldId;
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
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.Properties;

/**
 * 
 * @author tsa
 * @implSpec task http://dewiki908/mediawiki/index.php/05731_Spaltenbreite_persitieren_%28103033707449%29
 */
public class AWindowSaveStateModel
{
	private static final String ACTION_Name = "org.compiere.apps.AWindowSaveStateModel.action";

	private static final Logger logger = LogManager.getLogger(AWindowSaveStateModel.class);
	private static final CCache<UserId, Boolean> userId2enabled = new CCache<>(I_AD_Role.Table_Name, 5, 0);

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
		Services.get(ITrxManager.class).runInNewTrx(() -> save0(gridTab));
	}

	private void save0(final GridTab gridTab)
	{
		Check.assumeNotNull(gridTab, "gridTab not null");
		for (final GridField gridField : gridTab.getFields())
		{
			save(gridField);
		}
	}

	private void save(final GridField gridField)
	{
		final GridFieldVO gridFieldVO = gridField.getVO();
		final AdFieldId adFieldId = gridFieldVO.getAD_Field_ID();
		if (adFieldId == null)
		{
			return;
		}

		final I_AD_Field adField = InterfaceWrapperHelper.load(adFieldId, I_AD_Field.class);
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
