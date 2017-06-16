/**
 * 
 */
package org.compiere.grid.ed;

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


import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.apps.ADialogDialog;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.letters.model.I_AD_Column;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.letters.model.MADBoilerPlate.SourceDocument;
import de.metas.letters.model.MADBoilerPlateVar;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;

/**
 * @author teo_sarca
 *
 */
public class BoilerPlateMenu
{
	/** Logger */
	private static final Logger log = LogManager.getLogger(BoilerPlateMenu.class);
	
	public static boolean createFieldMenu(Object textComponent, JPopupMenu popupMenu, GridField gridField)
	{
		final List<JMenuItem> items = createMenuElements(textComponent, gridField);
		if (items == null || items.isEmpty())
		{
			return false;
		}
		
		if (popupMenu == null)
		{
			final JTextComponent text = getTextComponent(textComponent);
			if (text == null)
			{
				log.warn("No TextComponent found for " + textComponent);
				return false;
			}
			final JPopupMenu popupMenu2 = new JPopupMenu();
			text.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e))
						popupMenu2.show((Component)e.getSource(), e.getX(), e.getY());
				}
			});
			popupMenu = popupMenu2;
		}
		
		for (JMenuItem item : items)
		{
			popupMenu.add(item);
		}
		
		return true;
		
	}
	public static List<JMenuItem> createMenuElements(Object textComponent, GridField gridField)
	{
		if (!isAdvancedText(gridField))
		{
			log.debug("GridField " + gridField + " is not an advanced text field");
			return Collections.emptyList();
		}
		//
		final JTextComponent text = getTextComponent(textComponent);
		if (text == null)
		{
			log.warn("No TextComponent found for " + textComponent);
			return Collections.emptyList();
		}
		//
		final GridTab gridTab = gridField.getGridTab();
		if (gridTab == null)
		{
			log.warn("No GridTab found for "+gridField);
			return Collections.emptyList();
		}
		
		BoilerPlateMenu bpm = new BoilerPlateMenu() {
			@Override
			public BoilerPlateContext getAttributes() {
				return MADBoilerPlate.createEditorContext(SourceDocument.toSourceDocumentOrNull(gridTab));
			}

			@Override
			public void setAttributes(final BoilerPlateContext attributes)
			{
				throw new IllegalStateException("Method not supported");
			}
		};
		bpm.setResolveVariables(false);
		bpm.setResolveBoilerPlates(true);
		bpm.setHTML(false);
		
		final List<JMenuItem> result = new ArrayList<JMenuItem>();
		final JMenuItem insertSnippetMenu = bpm.createInsertSnippetMenu(text);
		if (insertSnippetMenu != null)
		{
			result.add(insertSnippetMenu);
		}
		
		final JMenuItem insertVariableMenu = bpm.createInsertVariableMenu(text);
		if (insertVariableMenu != null)
		{
			result.add(insertVariableMenu);
		}
		
		return result;
	}

	/** Context Attributes */
	private BoilerPlateContext attributes = BoilerPlateContext.EMPTY;
	/** Resolve/Parse variables */
	private boolean resolveVariables = false;
	/** Resolve/Parse text templates(boilerplate) */
	private boolean resolveBoilerPlates = false;
	/** Use HTML Text? */
	private boolean isHTML = true;

	public BoilerPlateContext getAttributes()
	{
		return attributes;
	}

	public void setAttributes(final BoilerPlateContext attributes)
	{
		this.attributes = attributes;
	}

	public boolean isResolveVariables()
	{
		return resolveVariables;
	}

	public void setResolveVariables(boolean resolveVariables)
	{
		this.resolveVariables = resolveVariables;
	}

	public boolean isResolveBoilerPlates()
	{
		return resolveBoilerPlates;
	}

	public void setResolveBoilerPlates(boolean resolveBoilerPlates)
	{
		this.resolveBoilerPlates = resolveBoilerPlates;
	}

	public boolean isHTML()
	{
		return isHTML;
	}

	public void setHTML(boolean isHTML)
	{
		this.isHTML = isHTML;
	}

	public JMenu createInsertSnippetMenu(final JTextComponent editor)
	{
		final List<MADBoilerPlate> boilerPlates = MADBoilerPlate.getAll(Env.getCtx());
		if (boilerPlates.isEmpty())
		{
			return null;
		}
		
		final JMenu menuSnippet = new JMenu(Msg.translate(Env.getCtx(), "de.metas.letter.InsertSnippet"));
		final Frame parentFrame = getParentFrame(editor);
		for (final MADBoilerPlate bp : boilerPlates)
		{
			JMenuItem mi = new JMenuItem();
			mi.setAction(new AbstractAction()
			{
				private static final long serialVersionUID = 4942007238006608191L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (isResolveBoilerPlates())
					{
						try
						{
							String text = bp.getTextSnippetParsed(getAttributes());
							if (!isHTML())
								text = MADBoilerPlate.getPlainText(text);
							insertText(editor, text);
						}
						catch (Exception ex)
						{
							new ADialogDialog(parentFrame,
									Msg.getMsg(Env.getCtx(), "Error"),
									ex.getLocalizedMessage(),
									JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						insertText(editor, bp.getTagString());
					}
				}
			});
			mi.setText(bp.getName());
			menuSnippet.add(mi);
		}
		return menuSnippet;
	}

	public JMenu createInsertVariableMenu(final JTextComponent editor)
	{
		final Collection<MADBoilerPlateVar> boilderPlateVars = MADBoilerPlateVar.getAll(Env.getCtx()).values();
		if (boilderPlateVars.isEmpty())
		{
			return null;
		}
		
		final JMenu menuVars = new JMenu(Msg.translate(Env.getCtx(), "de.metas.letter.InsertVariable"));
		final Frame parentFrame = getParentFrame(editor);
		for (final MADBoilerPlateVar var : boilderPlateVars)
		{
			JMenuItem mi = new JMenuItem();
			mi.setAction(new AbstractAction()
			{
				private static final long serialVersionUID = -5007856575142340294L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (isResolveVariables())
					{
						try
						{
							String text = var.evaluate(getAttributes());
							if (!isHTML())
								text = MADBoilerPlate.getPlainText(text);
							insertText(editor, text);
						}
						catch (Exception ex)
						{
							new ADialogDialog(parentFrame,
									Msg.getMsg(Env.getCtx(), "Error"),
									ex.getLocalizedMessage(),
									JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						insertText(editor, var.getTagString());
					}
				}
			});
			String name = var.getName();
			mi.setText(name);
			menuVars.add(mi);
		}
		//
		return menuVars;
	}

	protected static void insertText(JTextComponent editor, String text)
	{
		if (Check.isEmpty(text, false))
			return;
		//
		final Document document = editor.getDocument();
		// find out the text position where the new text should be inserted
		final int caretOffset = editor.getSelectionStart();
		// find out if the have selected text to be replaces with the new snippet
		final String selText = editor.getSelectedText();
		//
		try
		{
			if (selText != null)
			{
				document.remove(caretOffset, selText.length());
			}
			document.insertString(caretOffset, text, null);
		}
		catch (BadLocationException e1)
		{
			MetasfreshLastError.saveError(log, "BadlocationException: " + e1.getMessage() + "; Illegal offset: " + e1.offsetRequested(), e1);
		}
	}

	private static Frame getParentFrame(Component c)
	{
		Frame parent = null;
		Container e = c.getParent();
		while (e != null)
		{
			if (e instanceof Frame)
			{
				parent = (Frame)e;
				break;
			}
			e = e.getParent();
		}
		return parent;
	}
	
	private static JTextComponent getTextComponent(Object c)
	{
		if (c == null)
		{
			return null;
		}
		if (c instanceof JTextComponent)
		{
			return (JTextComponent)c;
		}
		
		if (c instanceof Container)
		{
			final Container container = (Container)c;
			for (Component cc : container.getComponents())
			{
				if (cc instanceof JTextComponent)
				{
					return (JTextComponent)cc;
				}
				else if (cc instanceof Container)
				{
					return getTextComponent(cc);
				}
			}
		}
		return null;
	}

	public static boolean isAdvancedText(GridField gridField)
	{
		if (gridField == null)
		{
			log.warn("gridField is null");
			return false;
		}
		final GridTab gridTab = gridField.getGridTab();
		if (gridTab == null)
			return false;
		
		final MColumn column = MTable.get(Env.getCtx(), gridTab.getTableName()).getColumn(gridField.getColumnName());
		if (column == null)
			return false;
		final I_AD_Column bean = InterfaceWrapperHelper.create(column, I_AD_Column.class);
		return bean.isAdvancedText();
	}
}
