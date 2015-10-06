package de.metas.commission.install;

/*
 * #%L
 * de.metas.commission.client
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


import java.util.Properties;

import org.compiere.model.I_AD_Field;
import org.compiere.model.MColumn;
import org.compiere.model.MField;
import org.compiere.model.MTab;
import org.compiere.model.MWindow;
import org.compiere.model.Query;

public final class FieldInstaller extends Installer {

	private MField field;

	private FieldInstaller(Properties ctx, String trxName) {
		super(ctx, trxName);
	}

	public static FieldInstaller mkInst(Properties ctx, String trxName) {
		return new FieldInstaller(ctx, trxName);
	}

	public FieldInstaller createOrUpdate(final String entityType,
			final MColumn column) {

		MField field = new Query(ctx, I_AD_Field.Table_Name,
				I_AD_Field.COLUMNNAME_AD_Column_ID + "=?", trxName)
				.setParameters(column.get_ID()).firstOnly();

		if (field == null) {
			field = new MField(ctx, 0, entityType);
			field.setAD_Column_ID(column.get_ID());
			field.set_TrxName(trxName);
		} else {
			field.setEntityType(entityType);
		}

		setSaved(false);
		this.field = field;

		return this;
	}

	public FieldInstaller setWindowAndTab(final String windowName,
			final String tabName) {

		final int windowId = MWindow.getWindow_ID(windowName);

		final int tabId = MTab.getTab_ID(windowId, tabName);

		field.setAD_Tab_ID(tabId);
		setSaved(false);

		return this;
	}

	public FieldInstaller save() {

		save(field);
		return this;
	}

	public FieldInstaller setVisible(final boolean visible) {

		field.setIsDisplayed(visible);
		setSaved(false);

		return this;
	}
}
