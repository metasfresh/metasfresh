package org.adempiere.ui.spi;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.compiere.model.GridTab;

/**
 * Implementations of this interface are responsible for providing {@link IGridTabSummaryInfo} for a given {@link GridTab}.
 * 
 * NOTE: the {@link IGridTabSummaryInfo} contains the message which is displayed on window tab's bottom.
 * 
 * @author tsa
 *
 */
public interface IGridTabSummaryInfoProvider
{
	/**
	 * Transaction info that is displayed on the window footer panel
	 * 
	 * @param gridTab
	 * @return
	 */
	IGridTabSummaryInfo getSummaryInfo(final GridTab gridTab);
}
