package de.metas.web.component.trl;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.EventListener;

public interface ITranslatableLabel
{
	void setLabelTrl(String labelTrl);

	void setLabel(String labelOrig);

	/**
	 * 
	 * @return Translated Label
	 */
	String getLabel();

	/**
	 * 
	 * @return original label text
	 */
	String getLabelOrig();

	Page getPage();

	boolean addEventListener(String evtnm, EventListener listener);
}
