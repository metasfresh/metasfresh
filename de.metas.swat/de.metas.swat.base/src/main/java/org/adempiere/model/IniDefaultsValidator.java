/**
 *
 */
package org.adempiere.model;

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


import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.SysConfigUIDefaultsRepository;
import org.adempiere.service.ISysConfigBL;
import org.compiere.apps.AEnv;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.ValueNamePair;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author teo_sarca
 *
 */
public class IniDefaultsValidator implements ModelValidator
{
	public static final String SYSCONFIG_UILookFeel = "UILookFeel";
	public static final String SYSCONFIG_UITheme = "UITheme";

	private int m_AD_Client_ID = -1;

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
		//
		engine.addModelChange(I_AD_SysConfig.Table_Name, this);

		if (!Services.isAvailable(IClientUI.class))
		{
			Services.registerService(IClientUI.class, new SwingClientUI());
		}
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		if (!Ini.isSwingClient())
			return null;
		//
		// UI
		boolean changed = false;
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		ValueNamePair laf = getLookByName(sysConfigBL.getValue(SYSCONFIG_UILookFeel, Env.getAD_Client_ID(Env.getCtx())));
		ValueNamePair theme = getThemeByName(sysConfigBL.getValue(SYSCONFIG_UITheme, Env.getAD_Client_ID(Env.getCtx())));
		if (laf != null && theme != null)
		{
			String clazz = laf.getValue();
			String currentLaf = UIManager.getLookAndFeel().getClass().getName();
			if (!Check.isEmpty(clazz) && !currentLaf.equals(clazz))
			{
				// laf changed
				AdempierePLAF.setPLAF(laf, theme, true);
				AEnv.updateUI();
				changed = true;
			}
			else
			{
				if (UIManager.getLookAndFeel() instanceof MetalLookAndFeel)
				{
					MetalTheme currentTheme = MetalLookAndFeel.getCurrentTheme();
					String themeClass = currentTheme.getClass().getName();
					String sTheme = theme.getValue();
					if (sTheme != null && sTheme.length() > 0 && !sTheme.equals(themeClass))
					{
						ValueNamePair plaf = ValueNamePair.of(
								UIManager.getLookAndFeel().getClass().getName(),
								UIManager.getLookAndFeel().getName());
						AdempierePLAF.setPLAF(plaf, theme, true);
						AEnv.updateUI();
						changed = true;
					}
				}
			}
		}
		//
		if (changed)
			Ini.saveProperties();

		//
		// Make sure the UIDefauls from sysconfig were loaded
		SysConfigUIDefaultsRepository.ofCurrentLookAndFeelId()
				.loadAllFromSysConfigTo(UIManager.getDefaults());

		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (InterfaceWrapperHelper.isInstanceOf(po, I_AD_SysConfig.class) && (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE))
		{
			final I_AD_SysConfig cfg = InterfaceWrapperHelper.create(po, I_AD_SysConfig.class);
			final String name = cfg.getName();
			if (name.equals(SYSCONFIG_UILookFeel))
			{
				if (getLookByName(cfg.getValue()) == null)
				{
					throw new AdempiereException("@NotFound@ " + cfg.getValue());
				}
			}
			else if (name.equals(SYSCONFIG_UITheme))
			{
				if (getThemeByName(cfg.getValue()) == null)
				{
					throw new AdempiereException("@NotFound@ " + cfg.getValue());
				}
			}
		}
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}

	private static ValueNamePair getLookByName(String name)
	{
		if (Check.isEmpty(name, true))
			return null;
		for (ValueNamePair vnp : AdempierePLAF.getPLAFs())
		{
			if (vnp.getName().equals(name))
				return vnp;
		}
		return null;
	}

	private static ValueNamePair getThemeByName(String name)
	{
		if (Check.isEmpty(name, true))
			return null;
		for (ValueNamePair vnp : AdempierePLAF.getThemes())
		{
			if (vnp.getName().equals(name))
				return vnp;
		}
		return null;
	}
}
