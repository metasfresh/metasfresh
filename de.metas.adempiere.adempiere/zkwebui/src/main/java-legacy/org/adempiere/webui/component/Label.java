/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.component;

import org.adempiere.webui.LayoutUtils;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class Label extends org.zkoss.zul.Label
{
    /**
	 *
	 */
	private static final long serialVersionUID = 1912498227823474678L;

	private Component decorator;

    private boolean mandatory;

    public Label()
    {
        super();
    }

    public Label(String value)
    {
        super(value != null ? value.replaceAll("[&]", "") : null);
    }

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
		setupMandatoryDecorator();
	}

	public Component getDecorator() {
		return decorator;
	}

	@Override
	public void setValue(String value) {
		super.setValue(value != null ? value.replaceAll("[&]", "") : null);
		if ((value == null || value.trim().length() == 0) && decorator != null)
			decorator.setVisible(false);
	}

	@Override
	public boolean setVisible(boolean visible) {
		if (decorator != null) {
			if (visible)
				decorator.setVisible(getValue() != null && getValue().trim().length() > 0 && mandatory);
			else
				decorator.setVisible(false);
		}
		return super.setVisible(visible);
	}

	private void setupMandatoryDecorator() {
		if (decorator == null)
			createMandatoryDecorator();
		String value = getValue();
		if (mandatory && value != null && value.trim().length() > 0) {
			decorator.setVisible(true);
		} else
			decorator.setVisible(false);
	}

	private void createMandatoryDecorator() {
		decorator = new Label("*");
		((Label)decorator).setSclass("mandatory-decorator-text");
	}

	/**
	 * alias for setValue, added to ease porting of swing form
	 * @param translate
	 */
	public void setText(String translate) {
		if (translate != null)
			translate = translate.replaceAll("[&]", "");
		this.setValue(translate);
	}

	public Component rightAlign() {
		return LayoutUtils.makeRightAlign(this);
	}
}
