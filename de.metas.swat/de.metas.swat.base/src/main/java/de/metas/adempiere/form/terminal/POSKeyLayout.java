package de.metas.adempiere.form.terminal;

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


import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pos.IPOSKeyKayoutDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.I_C_POSKey;
import org.compiere.model.I_C_POSKeyLayout;
import org.compiere.model.I_M_Product;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.X_C_POSKeyLayout;
import org.compiere.print.MPrintColor;
import org.compiere.print.MPrintFont;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.logging.LogManager;

public class POSKeyLayout extends KeyLayout
{
	// services
	private final IPOSKeyKayoutDAO posKeyKayoutDAO = Services.get(IPOSKeyKayoutDAO.class); 
	
	public static final Color DEFAULT_Color = Color.lightGray;

	private final Logger log = LogManager.getLogger(getClass());

	private final I_C_POSKeyLayout keyLayout;

	public POSKeyLayout(final ITerminalContext tc, final int C_POSKeyLayout_ID)
	{
		super(tc);

		this.keyLayout = InterfaceWrapperHelper.create(tc.getCtx(), C_POSKeyLayout_ID, I_C_POSKeyLayout.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(keyLayout, "keyLayout not null");

		// Set Columns count (if available)
		// If not available leave the default.
		final int keyLayoutColumns = keyLayout.getColumns();
		if (keyLayoutColumns > 0)
		{
			setColumns(keyLayoutColumns);
		}

		//
		// Default Color
		Color defaultColor = DEFAULT_Color;
		if (keyLayout.getAD_PrintColor_ID() != 0)
		{
			final MPrintColor color = MPrintColor.get(getCtx(), keyLayout.getAD_PrintColor_ID());
			defaultColor = color.getColor();
		}
		setDefaultColor(defaultColor);

		//
		// Default font
		if (keyLayout.getAD_PrintFont_ID() > 0)
		{
			final MPrintFont printFont = MPrintFont.get(keyLayout.getAD_PrintFont_ID());
			if (printFont != null)
			{
				setDefaultFont(printFont.getFont());
			}
		}
	}

	@Override
	public String getId()
	{
		return "C_POSKeyLayout#" + keyLayout.getC_POSKeyLayout_ID();
	}

	@Override
	protected List<ITerminalKey> createKeys()
	{
		final List<I_C_POSKey> posKeys = posKeyKayoutDAO.retrievePOSKeys(keyLayout);
		final List<ITerminalKey> list = new ArrayList<ITerminalKey>();
		for (final I_C_POSKey posKey : posKeys)
		{
			final de.metas.adempiere.model.I_C_POSKey key = InterfaceWrapperHelper.create(posKey, de.metas.adempiere.model.I_C_POSKey.class);
			if (!key.isActive())
			{
				continue;
			}

			if (key.getAD_Reference_ID() <= 0)
			{
				String tableName = null;
				int idValue = -1;
				if (X_C_POSKeyLayout.POSKEYLAYOUTTYPE_Product.equals(keyLayout.getPOSKeyLayoutType()))
				{
					tableName = I_M_Product.Table_Name;
					idValue = key.getM_Product_ID();
				}

				final POSKey tk = new POSKey(getTerminalContext(), key, tableName, idValue);
				list.add(tk);
			}
			else
			{
				final MLookup lookup;
				try
				{
					String validationCode = "";
					if (key.getAD_Val_Rule_ID() > 0)
					{
						final I_AD_Val_Rule valRule = key.getAD_Val_Rule();
						validationCode = valRule.getCode();
					}

					lookup = MLookupFactory.get(getCtx(),
							Env.WINDOW_None, // WindowNo
							0, // Column_ID,
							DisplayType.Table, // DisplayType
							"ID", // ColumnName,
							key.getAD_Reference_ID(), // AD_Reference_Value_ID,
							false, // IsParent,
							validationCode // ValidationCode
							);
				}
				catch (final Exception e)
				{
					log.error(e.getLocalizedMessage(), e);
					continue;
				}

				final List<Object> values = lookup.getData(true, true, true, false);
				for (final Object v : values)
				{
					final KeyNamePair knp = (KeyNamePair)v;
					final POSKey tk = new POSKey(getTerminalContext(), key, lookup.getTableName(), knp);
					list.add(tk);
				}
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public boolean isNumeric()
	{
		return X_C_POSKeyLayout.POSKEYLAYOUTTYPE_Numberpad.equals(keyLayout.getPOSKeyLayoutType());
	}

	@Override
	public boolean isText()
	{
		return X_C_POSKeyLayout.POSKEYLAYOUTTYPE_Keyboard.equals(keyLayout.getPOSKeyLayoutType());
	}
}
