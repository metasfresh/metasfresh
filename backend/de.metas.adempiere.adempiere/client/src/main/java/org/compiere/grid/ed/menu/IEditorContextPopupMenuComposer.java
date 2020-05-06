package org.compiere.grid.ed.menu;

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


import org.adempiere.ui.IContextMenuAction;

/**
 * Implement this interface, together with {@link IContextMenuAction} if you want to make your action to fully take control on how is created and handled action's menu item and behaviour.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03137:_Adresse_neu_berechnen_k%C3%B6nnen_%282012081510000014%29
 */
public interface IEditorContextPopupMenuComposer
{
	/**
	 * 
	 * @param parent
	 * @return true if UI creation was successful
	 */
	boolean createUI(java.awt.Container parent);
}
