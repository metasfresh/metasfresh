/**
 * 
 */
package de.metas.picking.terminal.form.swing;

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


import java.awt.Event;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.KeyStroke;

import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.swing.TerminalSubPanel;
import de.metas.picking.terminal.Utils;
import net.miginfocom.swing.MigLayout;

/**
 * Package Panel which contains informations about the product, partner, the left, middle panel
 * 
 * @author cg
 * 
 */
public abstract class AbstractPackageDataPanel extends TerminalSubPanel implements PropertyChangeListener
{
	public AbstractPackageDataPanel(final AbstractPackageTerminalPanel basePanel)
	{
		super(basePanel);
		this.packageTerminalPanel = basePanel;
	}
	
	private String buttonSize = Utils.getButtonSize();

	public String getButtonSize()
	{
		return buttonSize;
	}

	public void setButtonSize(final String buttonSize)
	{
		this.buttonSize = buttonSize;
	}

	private ITerminalButton bSave;
	private ITerminalButton bLogout;
	private ITerminalButton bUndo;
	private ITerminalButton bDelete;
	private ITerminalButton bPrint;
	private ITerminalButton bLock;
	private ITerminalButton bOk;
	private ITerminalButton bClose;
	
	protected static final String ACTION_Save = "Save";
	protected static final String ACTION_Logout = "Logout";
	protected static final String ACTION_Undo = "Undo";
	protected static final String ACTION_Delete = "Delete";
	protected static final String ACTION_Print = "Print";
	protected static final String ACTION_Lock = "Lock";
	protected static final String ACTION_OK = "Ok";
	protected static final String ACTION_Close = "Close"; 

	protected final AbstractPackageTerminalPanel packageTerminalPanel;

	@Override
	public void init()
	{
		initComponents();
		initLayout();
	}

	protected void initLayout()
	{
		getUI().setLayout(new MigLayout("ins 5 5 5 5", "[fill|fill|fill]", "[nogrid]unrel[||]"));

		if (buttonSize == null)
		{
			setButtonSize(Utils.getButtonSize());
		}

		add(bClose, getButtonSize());
		add(bSave, getButtonSize());
		add(bUndo, getButtonSize());
		add(bDelete, getButtonSize());
		add(bLock, getButtonSize());
		add(bPrint, getButtonSize());
		add(bLogout, getButtonSize());
		add(bOk, getButtonSize());
		setupPackingItemPanel();
	}

	protected void initComponents()
	{
		bSave = createButtonAction(ACTION_Save, KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.F4), 17f);
		bSave.setEnabled(false);
		bSave.addListener(this);
		bUndo = createButtonAction(ACTION_Undo, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, Event.ESCAPE), 17f);
		bUndo.setEnabled(false);
		bUndo.addListener(this);
		bLogout = createButtonAction(ACTION_Logout, KeyStroke.getKeyStroke(KeyEvent.VK_F10, Event.F10), 17f);
		bLogout.addListener(this);
		bDelete = createButtonAction(ACTION_Delete, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Event.DELETE), 17f);
		bDelete.setEnabled(false);
		bDelete.addListener(this);
		bPrint = createButtonAction(ACTION_Print, KeyStroke.getKeyStroke(KeyEvent.VK_PRINTSCREEN, Event.PRINT_SCREEN), 17f);
		bPrint.addListener(this);
		bLock = createButtonAction(ACTION_Lock, KeyStroke.getKeyStroke(KeyEvent.VK_F2, Event.F2), 17f);
		bLock.addListener(this);
		bOk = createButtonAction(ACTION_OK, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, Event.ENTER), 17f);
		bOk.setEnabled(false);
		bOk.addListener(this);
		bClose = createButtonAction(ACTION_Close, KeyStroke.getKeyStroke(KeyEvent.VK_F5, Event.F5), 17f);
		bClose.setEnabled(false);
		bClose.addListener(this);
	}

	public ITerminalButton getbDelete()
	{
		return bDelete;
	}

	public ITerminalButton getbLock()
	{
		return bLock;
	}

	public ITerminalButton getbClose()
	{
		return bClose;
	}

	public ITerminalButton getbOK()
	{
		return bOk;
	}

	public ITerminalButton getbPrint()
	{
		return bPrint;
	}

	public ITerminalButton getbSave()
	{
		return bSave;
	}

	abstract public void setupPackingItemPanel();

	/**
	 * set read only components
	 * 
	 * @param plus set ro state for plus button
	 * @param minus set ro state for minus button
	 * @param field set ro state for field
	 */
	public void setQtyFieldReadOnly(boolean plus, boolean minus, boolean field)
	{
		packageTerminalPanel.getProductKeysPanel().setQtyFieldReadOnly(plus, minus, field);
	}

	public void setReadOnly(final boolean ro)
	{
		packageTerminalPanel.getProductKeysPanel().setQtyFieldReadOnly(ro);
	}
	
	public void setEditable(final boolean editable)
	{
		final boolean ro = !editable;
		setReadOnly(ro);
	}

	@Override
	abstract public void propertyChange(PropertyChangeEvent evt);

	abstract public void validateModel();

	/**
	 * Called when we need to give focus to default editing component of this panel
	 */
	public void requestFocus()
	{
		// nothing at this level
	}
}
