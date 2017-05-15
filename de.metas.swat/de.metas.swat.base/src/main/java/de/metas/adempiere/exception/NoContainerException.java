package de.metas.adempiere.exception;

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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.I_M_PackagingContainer;
import org.compiere.model.MWarehouse;
import org.compiere.util.Env;

import de.metas.i18n.Msg;

public class NoContainerException extends AdempiereException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 613087606261595301L;

	public static final String MSG_NO_SUFFICIENT_CONTAINERS = "Kein ausreichendes Verpackungsmaterial";

	public static final String MSG_INSUFFICIENT_FEATURES = "Maengel an vorhandenem Verpackungsmaterial:";

	/**
	 * 
	 * @param warehouseId
	 *            the id of the warehouse in which we don't have enough
	 *            packaging.
	 * @param maxVolumeExceeded
	 *            indicates if there are containers, but their maximum payload
	 *            volume is too small
	 * @param maxWeightExceeded
	 *            indicates if there are containers, but their maximum payload
	 *            weight is too low
	 */
	public NoContainerException(final int warehouseId,
			final boolean maxVolumeExceeded, final boolean maxWeightExceeded) {

		this(warehouseId, maxVolumeExceeded, maxWeightExceeded, null);
	}

	/**
	 * 
	 * @param warehouseId
	 *            the id of the warehouse in which we don't have enough
	 *            packaging.
	 * @param maxVolumeExceeded
	 *            indicates if there are containers, but their maximum payload
	 *            volume is too small
	 * @param maxWeightExceeded
	 *            indicates if there are containers, but their maximum payload
	 *            weight is too low
	 */
	public NoContainerException(final int warehouseId,
			final boolean maxVolumeExceeded, final boolean maxWeightExceeded,
			final NoContainerException originalEx) {

		super(Msg.translate(Env.getCtx(), buildMsg(warehouseId,
				maxVolumeExceeded, maxWeightExceeded)), originalEx);
	}

	public NoContainerException(final boolean maxVolumeExceeded,
			final boolean maxWeightExceeded) {
		this(0, maxVolumeExceeded, maxWeightExceeded, null);
	}

	private static String buildMsg(final int warehouseId,
			final boolean maxVolumeExceeded, final boolean maxWeightExceeded) {

		final StringBuffer sb = new StringBuffer();

		sb.append(Msg.translate(Env.getCtx(), MSG_NO_SUFFICIENT_CONTAINERS));

		if (warehouseId > 0) {
			final MWarehouse warehouse = new MWarehouse(Env.getCtx(),
					warehouseId, null);
			sb.append("\n@M_Warehouse_ID@ ");
			sb.append(warehouse.getName());
		}

		if (maxVolumeExceeded || maxWeightExceeded) {

			sb.append(Msg.translate(Env.getCtx(), MSG_INSUFFICIENT_FEATURES));
		}

		if (maxVolumeExceeded) {

			appendExceed(sb, I_M_PackagingContainer.COLUMNNAME_MaxVolume);
		}
		if (maxWeightExceeded) {

			appendExceed(sb, I_M_PackagingContainer.COLUMNNAME_MaxWeight);
		}

		return sb.toString();
	}

	private static void appendExceed(final StringBuffer sb, final String colName) {

		sb.append("\n");
		sb.append("@");
		sb.append(colName);
		sb.append("@");
	}
}
