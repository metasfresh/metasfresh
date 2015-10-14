package org.adempiere.ad.process;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.compiere.model.GridTab;

/**
 * Let your process implement this interface if you want to use it as related process in toolbar and you want to control when the process is applicable
 * 
 * @author ad
 * @task http://dewiki908/mediawiki/index.php/03077:_Related_processes_-_let_them_specify_if_are_applicable_for_a_given_context_%282012080210000093%29
 */
public interface ISvrProcessPrecondition
{
	/**
	 * Determines if a process should be displayed for the current tab.
	 * 
	 * @param gridTab
	 * @return true if the process will be displayed.
	 */
	boolean isPreconditionApplicable(GridTab gridTab);
}
