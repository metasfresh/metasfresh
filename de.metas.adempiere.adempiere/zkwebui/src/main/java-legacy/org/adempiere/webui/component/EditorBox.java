/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.apps.AEnv;
import org.zkoss.zhtml.Table;
import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;

/**
 * @author Low Heng Sin
 */
public class EditorBox extends Div {
	/**
	 *
	 */
	private static final long serialVersionUID = -3152111756471436612L;
	protected PropertyChangeSupport m_propertyChangeListeners = new PropertyChangeSupport(
			this);
	protected Textbox txt;
	protected Button btn;
	protected Td btnColumn;

	public EditorBox() {
		initComponents();
	}

	/**
	 * @param text
	 */
	public EditorBox(String text) {
		initComponents();
		setText(text);
	}

	/**
	 * @param imageSrc
	 */
	public void setButtonImage(String imageSrc) {
		btn.setImage(imageSrc);
	}

	private void initComponents() {
		Table grid = new Table();
		appendChild(grid);
		this.setWidth("100%");
		grid.setStyle("border: none; padding: 0px; margin: 0px;");
		grid.setDynamicProperty("width", "100%");
		grid.setDynamicProperty("border", "0");
		grid.setDynamicProperty("cellpadding", "0");
		grid.setDynamicProperty("cellspacing", "0");

		Tr tr = new Tr();
		grid.appendChild(tr);
		tr.setStyle("width: 100%; border: none; padding: 0px; margin: 0px; white-space:nowrap; ");

		Td td = new Td();
		tr.appendChild(td);
		td.setStyle("border: none; padding: 0px; margin: 0px;");
		txt = new Textbox();
		txt.setStyle("display: inline; width: 99%;");
		td.appendChild(txt);

		btnColumn = new Td();
		tr.appendChild(btnColumn);
		btnColumn.setStyle("border: none; padding: 0px; margin: 0px;");
		btnColumn.setSclass("editor-button");
		btn = new Button();
		btn.setTabindex(-1);
		LayoutUtils.addSclass("editor-button", btn);
		btnColumn.appendChild(btn);

		String style = AEnv.isFirefox2() ? "display: inline"
				: "display: inline-block";
		style = style
				+ ";border: none; padding: 0px; margin: 0px; background-color: transparent;";
		this.setStyle(style);
	}

	/**
	 * @return textbox component
	 */
	public Textbox getTextbox() {
		return txt;
	}

	/**
	 * @param value
	 */
	public void setText(String value) {
		txt.setText(value);
	}

	/**
	 * @return text
	 */
	public String getText() {
		return txt.getText();
	}

	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		txt.setReadonly(!enabled);
		btn.setEnabled(enabled);
		btn.setVisible(enabled);
		btnColumn.setVisible(enabled);
		if (enabled)
			btnColumn.setSclass("editor-button");
		else
			btnColumn.setSclass("");
	}

	/**
	 * @return boolean
	 */
	public boolean isEnabled() {
		return !txt.isReadonly();
	}

	/**
	 * @param evtnm
	 * @param listener
	 */
	public boolean addEventListener(String evtnm, EventListener listener) {
		if (Events.ON_CLICK.equals(evtnm)) {
			return btn.addEventListener(evtnm, listener);
		} else {
			return txt.addEventListener(evtnm, listener);
		}
	}

	/**
	 * @param l
	 */
	public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
		m_propertyChangeListeners.addPropertyChangeListener(l);
	}

	/**
	 * @param tooltiptext
	 */
	public void setToolTipText(String tooltiptext) {
		txt.setTooltiptext(tooltiptext);
	}
	
	/**
	 * @return Button
	 */
	public Button getButton() {
		return btn;
	}
}
