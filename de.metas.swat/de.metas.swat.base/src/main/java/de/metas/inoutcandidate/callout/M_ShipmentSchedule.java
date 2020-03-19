package de.metas.inoutcandidate.callout;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.Env;

@Callout(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	private static final String MSG_QTY_ORDERED_OVERRIDE_HINT = "M_ShipmentSchedule.QtyOrdered_Override_MayNotEqual_QtyDelivered_Hint";
	private static final String MSG_QTY_ORDERED_OVERRIDE = "M_ShipmentSchedule.QtyOrdered_Override_MayNotEqual_QtyDelivered";
	public static final M_ShipmentSchedule instance = new M_ShipmentSchedule();

	@CalloutMethod(columnNames = { //
			I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override,
			I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Calculated,
			I_M_ShipmentSchedule.COLUMNNAME_QtyDelivered,
			I_M_ShipmentSchedule.COLUMNNAME_IsClosed
	})
	private void updateQtyOrdered(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final ICalloutField field)
	{
		Services.get(IShipmentScheduleBL.class).updateQtyOrdered(shipmentSchedule);
	}

	@CalloutMethod(columnNames = I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override)
	public void preventQtyOrderedOverrideToCompleted(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final ICalloutField field)
	{
		if (shipmentSchedule.getQtyOrdered_Override().compareTo(shipmentSchedule.getQtyDelivered()) == 0)
		{
			shipmentSchedule.setQtyOrdered_Override(null);

			final ITranslatableString info = Services.get(IMsgBL.class)
					.getTranslatableMsgText(MSG_QTY_ORDERED_OVERRIDE_HINT);
			field.fireDataStatusEEvent(
					MSG_QTY_ORDERED_OVERRIDE,
					info.translate(Env.getAD_Language()),
					true);

		}
	}
}
