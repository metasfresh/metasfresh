package de.metas.dlm.swingui;

import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.Preference;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.ValueNamePair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.dlm.connection.DLMPermanentIniCustomizer;
import de.metas.dlm.migrator.IMigratorService;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2017 metas GmbH
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
/**
 * The job of this class is to add an additional component to the {@link Preference} user dialog.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PreferenceCustomizer
{
	/**
	 * Shall be called once, at client startup.
	 */
	public static void customizePrefernces()
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final Border insetBorder = BorderFactory.createEmptyBorder(2, 2, 2, 0);
		final FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);

		final CPanel dlmPanel = new CPanel();

		dlmPanel.setLayout(flowLayout);
		dlmPanel.setBorder(insetBorder);

		final CLabel dlmLevelLabel = new CLabel();
		dlmLevelLabel.setText(msgBL.getMsg(Env.getCtx(), DLMPermanentIniCustomizer.INI_P_DLM_DLM_LEVEL));

		final Map<Integer, ValueNamePair> values = getValues();

		final CComboBox<ValueNamePair> dlmLevelField = new CComboBox<>(ImmutableList.of(values.get(IMigratorService.DLM_Level_TEST), values.get(IMigratorService.DLM_Level_ARCHIVE)));

		dlmPanel.add(dlmLevelLabel);
		dlmPanel.add(dlmLevelField);

		Preference.addSettingsPanel(dlmPanel,

				preference -> {
					// do this on load
					final int dlmLevel = DLMPermanentIniCustomizer.INSTANCE.getDlmLevel();
					dlmLevelField.setSelectedItem(values.get(dlmLevel));
				},

				preference -> {
					// do this on store
					final ValueNamePair selectedItem = dlmLevelField.getSelectedItem();
					Ini.setProperty(DLMPermanentIniCustomizer.INI_P_DLM_DLM_LEVEL, selectedItem.getValue());
				});
	}

	public static Map<Integer, ValueNamePair> getValues()
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final ValueNamePair live = ValueNamePair.of(Integer.toString(IMigratorService.DLM_Level_TEST),
				msgBL.translate(Env.getCtx(), IMigratorService.MSG_DLM_Level_LIVE));

		final ValueNamePair archive = ValueNamePair.of(Integer.toString(IMigratorService.DLM_Level_ARCHIVE),
				msgBL.translate(Env.getCtx(), IMigratorService.MSG_DLM_Level_ARCHIVE));

		return ImmutableMap.of(IMigratorService.DLM_Level_TEST, live, IMigratorService.DLM_Level_ARCHIVE, archive);
	}
}
