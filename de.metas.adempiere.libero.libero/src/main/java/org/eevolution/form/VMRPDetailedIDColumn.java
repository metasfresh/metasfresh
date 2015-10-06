package org.eevolution.form;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.minigrid.IDColumn;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.eevolution.model.I_AD_Note;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPNoteDAO;

/**
 * Extends {@link IDColumn} and stores more informations about current {@link I_PP_MRP} row.
 * 
 * @author tsa
 *
 */
/* package */class VMRPDetailedIDColumn extends IDColumn
{
	// services
	// private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IMRPNoteDAO mrpNoteDAO = Services.get(IMRPNoteDAO.class);

	private int countAllMRPNotices = 0;
	private int countErrorMRPNotices = 0;

	private String _rowTooltipText = null;
	private boolean _rowTooltipTextLoaded = false;

	public VMRPDetailedIDColumn(int record_ID)
	{
		super(record_ID);
	}

	public int getCountAllMRPNotices()
	{
		return countAllMRPNotices;
	}

	public void setCountAllMRPNotices(final int countMRPNotices_All)
	{
		this.countAllMRPNotices = countMRPNotices_All;
		this._rowTooltipTextLoaded = false;
	}

	public int getCountErrorMRPNotices()
	{
		return countErrorMRPNotices;
	}

	public void setCountErrorMRPNotices(int countMRPNotices_Error)
	{
		this.countErrorMRPNotices = countMRPNotices_Error;
		this._rowTooltipTextLoaded = false;
	}

	public String getRowTooltipText()
	{
		if (!_rowTooltipTextLoaded)
		{
			if (countAllMRPNotices == 0)
			{
				_rowTooltipText = null;
			}
			else
			{
				_rowTooltipText = loadRowTooltipText();
			}
			_rowTooltipTextLoaded = true;
		}

		return _rowTooltipText;
	}

	private final String loadRowTooltipText()
	{
		final List<I_AD_Note> notes = mrpNoteDAO.retrieveMRPNotesForMRPRecord(Env.getCtx(), getRecord_ID());
		final StringBuilder sb = new StringBuilder();
		for (final I_AD_Note note : notes)
		{
			final String noteStr = toTooltipString(note);
			if (Check.isEmpty(noteStr, true))
			{
				continue;
			}

			if (sb.length() > 0)
			{
				sb.append("<hr>");
			}
			sb.append(noteStr);
		}

		if (sb.length() == 0)
		{
			return null;
		}

		sb.insert(0, "<html>").append("</html>");
		return sb.toString();
	}

	private String toTooltipString(I_AD_Note note)
	{
		//
		// Build line details
		final StringBuilder details = new StringBuilder();
		details.append(note.getTextMsg());

		// NOTE: not adding plant, warehouse, product because user can see them on VMRPDetailed row
		// final I_S_Resource plant = note.getPP_Plant();
		// if (plant != null && plant.getS_Resource_ID() > 0)
		// {
		// if (details.length() > 0)
		// {
		// details.append(", ");
		// }
		// details.append("@PP_Plant_ID@: ").append(plant.getName());
		// }
		//
		// final I_M_Warehouse warehouse = note.getM_Warehouse();
		// if (warehouse != null && warehouse.getM_Warehouse_ID() > 0)
		// {
		// if (details.length() > 0)
		// {
		// details.append(", ");
		// }
		// details.append("@M_Warehouse_ID@: ").append(warehouse.getName());
		// }
		//
		// final I_M_Product product = note.getM_Product();
		// if (product != null && product.getM_Product_ID() > 0)
		// {
		// if (details.length() > 0)
		// {
		// details.append(", ");
		// }
		// details.append("@M_Product_ID@: ").append(product.getName());
		// }

		//
		// Build line
		final StringBuilder line = new StringBuilder();

		// NOTE: not adding MRP error code/name because it's already contained in note's TextMsg
		// final I_AD_Message msg = note.getAD_Message();
		// final String mrpErrorCode = Util.maskHTML(msg.getValue());
		// final String mrpErrorName = Util.maskHTML(msg.getMsgText());
		// line.append("<b>").append(mrpErrorCode).append(" ").append(mrpErrorName).append("</b>");
		if (details.length() > 0)
		{
			String detailsStr = details.toString();
			// detailsStr = msgBL.parseTranslation(Env.getCtx(), details.toString());
			detailsStr = Util.maskHTML(detailsStr);
			line.append("<pre>").append(detailsStr).append("</pre>");
		}

		return line.toString();
	}
}
