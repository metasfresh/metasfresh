package org.adempiere.ad.element.api.impl;

import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.element.api.IElementLinkBL;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_Element_Link;
import org.compiere.util.DB;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ElementLinkBL implements IElementLinkBL
{
	@Override
	public void createMissingADElementLinks()
	{
		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited,
				MigrationScriptFileLoggerHolder.DDL_PREFIX + "select AD_Element_Link_Create_Missing()",
				new Object[] {});
	}

	@Override
	public void recreateADElementLinkForFieldId(@NonNull final AdFieldId adFieldId)
	{
		deleteExistingADElementLinkForFieldId(adFieldId);
		createADElementLinkForFieldId(adFieldId);
	}

	@Override
	public void createADElementLinkForFieldId(@NonNull final AdFieldId adFieldId)
	{
		// NOTE: no params because we want to log migration scripts
		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited,
				MigrationScriptFileLoggerHolder.DDL_PREFIX + "select AD_Element_Link_Create_Missing_Field(" + adFieldId.getRepoId() + ")",
				new Object[] {});
	}

	@Override
	public void deleteExistingADElementLinkForFieldId(final AdFieldId adFieldId)
	{
		// IMPORTANT: we have to call delete directly because we don't want to have the AD_Element_Link_ID is the migration scripts
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM " + I_AD_Element_Link.Table_Name + " WHERE AD_Field_ID=" + adFieldId.getRepoId(),
				ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public void recreateADElementLinkForTabId(@NonNull final AdTabId adTabId)
	{
		deleteExistingADElementLinkForTabId(adTabId);
		createADElementLinkForTabId(adTabId);
	}

	@Override
	public void createADElementLinkForTabId(@NonNull final AdTabId adTabId)
	{
		// NOTE: no params because we want to log migration scripts
		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited,
				MigrationScriptFileLoggerHolder.DDL_PREFIX + "select AD_Element_Link_Create_Missing_Tab(" + adTabId.getRepoId() + ")",
				new Object[] {});
	}

	@Override
	public void deleteExistingADElementLinkForTabId(final AdTabId adTabId)
	{
		// IMPORTANT: we have to call delete directly because we don't want to have the AD_Element_Link_ID is the migration scripts
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM " + I_AD_Element_Link.Table_Name + " WHERE AD_Tab_ID=" + adTabId.getRepoId(),
				ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public void recreateADElementLinkForWindowId(final AdWindowId adWindowId)
	{
		deleteExistingADElementLinkForWindowId(adWindowId);
		createADElementLinkForWindowId(adWindowId);
	}

	@Override
	public void createADElementLinkForWindowId(final AdWindowId adWindowId)
	{
		// NOTE: no params because we want to log migration scripts
		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited,
				MigrationScriptFileLoggerHolder.DDL_PREFIX + "select AD_Element_Link_Create_Missing_Window(" + adWindowId.getRepoId() + ")",
				new Object[] {});
	}

	@Override
	public void deleteExistingADElementLinkForWindowId(final AdWindowId adWindowId)
	{
		// IMPORTANT: we have to call delete directly because we don't want to have the AD_Element_Link_ID is the migration scripts
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM " + I_AD_Element_Link.Table_Name + " WHERE AD_Window_ID=" + adWindowId.getRepoId(),
				ITrx.TRXNAME_ThreadInherited);
	}
}
