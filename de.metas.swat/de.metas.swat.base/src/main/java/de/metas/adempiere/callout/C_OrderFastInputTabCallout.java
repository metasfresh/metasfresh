package de.metas.adempiere.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;

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


import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.model.GridTab;

/**
 * See {@link #onNew(GridTab)} javadoc.
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/09232_Auftrag_Schnellerfassung_erste_Zeile_ohne_Packvorschrift_Menge_CU_nicht_%C3%BCbernommen_%28103054677064%29
 */
public class C_OrderFastInputTabCallout extends TabCalloutAdapter
{
	/**
	 * Make sure that on a new order record, the (numeric) quick-input fields are empty, and not just 0. If they are just 0, there care problems with the quick-input callouts (task 09232).
	 */
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		OrderFastInput.clearFields(calloutRecord, false); // just clear them, don't try to save yet because mandatory fields are not yet set.
	}
}
