/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Teo Sarca.                                            *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Teo Sarca (teo.sarca@gmail.com)                                   *
*                                                                     *
* Sponsors:                                                           *
* - SC ARHIPAC SERVICE SRL (http://www.arhipac.ro)                    *
***********************************************************************/
package org.compiere.grid;

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


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import de.metas.logging.LogManager;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.util.GridRowCtx;
import org.adempiere.util.Services;
import org.compiere.model.GridField;
import org.compiere.model.GridTable;
import org.compiere.model.Lookup;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

/**
 * VTableExcelAdapter enables Copy Clipboard functionality on VTables.
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - FR [ 1753943 ]
 */
public class VTableExcelAdapter
	implements ActionListener
{
	public static final String CMD_Copy = "VTable.copyPaste"; 
	public static final String CMD_CopyWithHeaders = "VTable.copyPasteH";

	private static KeyStroke KS_copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
	private static KeyStroke KS_copyWithHeader = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK,false);

	/** Logger */
	private Logger log = LogManager.getLogger(getClass());

	/** System clipboard */
	private Clipboard system;
	/** Source table */
	private VTable table;
	
	/** System locale */
	private static Locale sysLocale = new Locale(
			System.getProperty("user.language"), 
			System.getProperty("user.country")
	);
	/** System number formater */
	private static NumberFormat sysNumberFormat = NumberFormat.getNumberInstance(sysLocale);
	/** System date formater */
	private static DateFormat sysDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, sysLocale);

	/**
	 * The Excel Adapter is constructed with a
	 * VTable on which it enables Copy-Paste and acts
	 * as a Clipboard listener.
	 * 
	 * @param table
	 */
	public VTableExcelAdapter(VTable table)
	{
		this.table = table;
		table.registerKeyboardAction(this,CMD_Copy,KS_copy,JComponent.WHEN_FOCUSED);
		table.registerKeyboardAction(this,CMD_CopyWithHeaders,KS_copyWithHeader,JComponent.WHEN_FOCUSED);
		system = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	/**
	 * This method is activated on the Keystrokes we are listening to
	 * in this implementation. Here it listens for Copy and Paste ActionCommands.
	 * 
	 * @param e event 
	 */
	public void actionPerformed(ActionEvent e)
	{
		// Only GridTable model is supported
		if (!(table.getModel() instanceof GridTable))
		{
			if(LogManager.isLevelFine()) log.debug("Not supported - " + table.getModel());
			return;
		}

		boolean isCopy = CMD_Copy.equals(e.getActionCommand()); 
		boolean isCopyWithHeaders = CMD_CopyWithHeaders.equals(e.getActionCommand()); 

		if (isCopy || isCopyWithHeaders)
		{
			try
			{
				final int[] selectedRowsView = table.getSelectedRows();
				if (selectedRowsView == null || selectedRowsView.length == 0)
				{
					return;
				}

				final int columnsCountView = table.getColumnCount();
				final StringBuilder sb = new StringBuilder();
				final GridTable model = (GridTable)table.getModel();
				final GridField[] fields = model.getFields();

				// Header
				if (isCopyWithHeaders)
				{
					for (int columnIndexView = 0; columnIndexView < columnsCountView; columnIndexView++)
					{
						String value = "";
						try
						{
							final int columnIndexModel = table.convertColumnIndexToModel(columnIndexView);
							final GridField field = fields[columnIndexModel];
							
							if (!field.isDisplayed(false))
							{
								// field is never displayed => skip it because we will skip it in values too (see below) 
								continue;
							}
							
							value = field.getHeader();
						}
						catch(Exception ex)
						{
							log.warn("Copy-headers", ex);
						}
						
						value = fixString(value);
						sb.append(value).append("\t");
					}
					sb.append(Env.NL);
				}

				// Selected rows
				for (int rowIndexView : selectedRowsView)
				{
					final int rowIndexModel = table.convertRowIndexToModel(rowIndexView);
					final GridRowCtx rowCtx = new GridRowCtx(Env.getCtx(), model, model.getWindowNo(), rowIndexModel);
					
					for (int columnIndexView = 0; columnIndexView < columnsCountView; columnIndexView++)
					{
						final int columnIndexModel = table.convertColumnIndexToModel(columnIndexView);
						
						String value = null;
						try
						{
							final Object key = table.getValueAt(rowIndexView, columnIndexView);
							final GridField field = fields[columnIndexModel];
							
							if (!field.isDisplayed(false))
							{
								// field is never displayed => skip it because we skipped it in header too (see above) 
								continue;
							}
							
							if (!field.isDisplayed(rowCtx))
							{
								// field is not displayed in current row context => don't display the value
								value = null;
							}
							else if (field.isEncryptedColumn() || field.isEncryptedField())
							{
								value = "*";
							}
							else if (key instanceof Boolean)
							{
								value = Msg.getMsg(Env.getCtx(), ((Boolean)key).booleanValue() ? "Yes":"No");
							}
							else if (key instanceof BigDecimal)
							{
								try
								{
									value = sysNumberFormat.format(key != null ? key : Env.ZERO);
								}
								catch (Exception ex)
								{
								}
							}
							else if (key instanceof Date)
							{
								try
								{
									value = sysDateFormat.format(key);
								}
								catch (Exception ex)
								{
								}
							}
							else
							{
								final Lookup lookup = (field != null ? field.getLookup() : null);
								if (lookup != null && key != null)
								{
									final IValidationContext evalCtx = Services.get(IValidationRuleFactory.class).createValidationContext(field, rowIndexModel);
									value = lookup.getDisplay(evalCtx, key);
								}
								else
								{
									value = null;
								}
								if (value == null && key != null)
								{
									value = key.toString();
								}
							}
						}
						catch(Exception ex)
						{
							log.warn("Copy-rows", ex);
						}
						
						value = fixString(value);
						sb.append(value).append("\t");
					}
					sb.append(Env.NL);
				}
				
				StringSelection stsel  = new StringSelection(sb.toString());
				system = Toolkit.getDefaultToolkit().getSystemClipboard();
				system.setContents(stsel,stsel);
			}
			catch (Exception ex)
			{
				log.warn("Copy", ex);
			}
		}
	} // actionPerformed

	/**
	 * Fix Cell String
	 * @param s string
	 * @return fixed string 
	 */
	private String fixString(String s)
	{
		if (s == null || s.length() == 0)
			return "";
		String s2 = s.replaceAll("[\t\n\f\r]+", " ");
		return s2;
	}
}
