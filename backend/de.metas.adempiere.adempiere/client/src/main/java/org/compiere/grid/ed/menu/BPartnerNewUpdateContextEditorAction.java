package org.compiere.grid.ed.menu;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.Services;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.ui.AbstractContextMenuAction;
import org.compiere.grid.ed.VBPartner;
import org.compiere.grid.ed.VEditor;
import org.compiere.model.GridField;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.Lookup;
import org.compiere.util.Env;

abstract class BPartnerNewUpdateContextEditorAction extends AbstractContextMenuAction
{
	private final boolean createNew;

	public BPartnerNewUpdateContextEditorAction(boolean createNew)
	{
		super();
		this.createNew = createNew;
	}

	@Override
	public String getIcon()
	{
		return "InfoBPartner16";
	}

	@Override
	public boolean isAvailable()
	{
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		if (!Services.get(ISysConfigBL.class).getBooleanValue("UI_EnableBPartnerContextMenu", true, adClientId))
		{
			return false;
		}

		final VEditor editor = getEditor();
		final GridField gridField = editor.getField();
		if (gridField == null)
		{
			return false;
		}
		
		if (!gridField.isLookup())
		{
			return false;
		}
		
		final Lookup lookup = gridField.getLookup();
		if (lookup == null)
		{
			// No Lookup???
			log.warn("No lookup found for " + gridField + " even if is marked as Lookup");
			return false;
		}
		
		final String tableName = lookup.getTableName();
		if (!I_C_BPartner.Table_Name.equals(tableName))
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isRunnable()
	{
		final VEditor editor = getEditor();
		return editor.isReadWrite();
	}

	@Override
	public void run()
	{
		final VEditor editor = getEditor();
		final GridField gridField = editor.getField();
		final Lookup lookup = gridField.getLookup();
		final int windowNo = gridField.getWindowNo();

		final VBPartner vbp = new VBPartner(Env.getWindow(windowNo), windowNo);
		int BPartner_ID = 0;
		// if update, get current value
		if (!createNew)
		{
			final Object value = editor.getValue();
			if (value instanceof Integer)
				BPartner_ID = ((Integer)value).intValue();
			else if (value != null)
				BPartner_ID = Integer.parseInt(value.toString());
		}

		vbp.loadBPartner(BPartner_ID);
		vbp.setVisible(true);
		// get result
		int result = vbp.getC_BPartner_ID();
		if (result == 0 // 0 = not saved
				&& result == BPartner_ID) // the same
			return;
		// Maybe new BPartner - put in cache
		lookup.getDirect(IValidationContext.NULL, new Integer(result), false, true);

		// actionCombo (new Integer(result)); // data binding
		gridField.getGridTab().setValue(gridField, result);
	} // actionBPartner
}
