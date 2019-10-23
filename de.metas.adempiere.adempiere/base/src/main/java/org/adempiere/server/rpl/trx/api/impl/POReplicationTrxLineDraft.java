package org.adempiere.server.rpl.trx.api.impl;

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


import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.api.impl.ImportHelper;
import org.compiere.model.PO;

/**
 * Class to bundle the results of an attempt to lookup a certain PO for a given replication EXP_Format_Line.
 *
 * @see ImportHelper
 */
public class POReplicationTrxLineDraft
{
	private final PO poDraft;
	private final I_EXP_ReplicationTrxLine trxLineDraft;
	private final boolean lookup;

	/**
	 * Creates a POReplicationTrxLineDraft with <code>trxLine=null</code> and <code>doLookup=false</code>
	 *
	 * @param po
	 */
	public POReplicationTrxLineDraft(final PO po)
	{
		poDraft = po;
		trxLineDraft = null;
		lookup = false;
	}

	public POReplicationTrxLineDraft(final PO po, final I_EXP_ReplicationTrxLine trxLine, final boolean doLookup)
	{
		poDraft = po;
		trxLineDraft = trxLine;
		lookup = doLookup;
	}

	public PO getPODraft()
	{
		return poDraft;
	}

	public I_EXP_ReplicationTrxLine getTrxLineDraftOrNull()
	{
		return trxLineDraft;
	}

	public boolean isDoLookup()
	{
		return lookup;
	}
}
