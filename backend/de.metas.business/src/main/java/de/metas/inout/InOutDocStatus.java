/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.inout;

import org.compiere.model.X_M_InOut;

public enum InOutDocStatus
{
	Drafted(X_M_InOut.DOCSTATUS_Drafted),
	Completed(X_M_InOut.DOCSTATUS_Completed),
	Approved(X_M_InOut.DOCSTATUS_Approved),
	NotApproved(X_M_InOut.DOCSTATUS_NotApproved),
	Voided(X_M_InOut.DOCSTATUS_Voided),
	Invalid(X_M_InOut.DOCSTATUS_Invalid),
	Reversed(X_M_InOut.DOCSTATUS_Reversed),
	Closed(X_M_InOut.DOCSTATUS_Closed),
	Unknown(X_M_InOut.DOCSTATUS_Unknown),
	InProgress(X_M_InOut.DOCSTATUS_InProgress),
	WaitingPayment(X_M_InOut.DOCSTATUS_WaitingPayment),
	WaitingConfirmation(X_M_InOut.DOCSTATUS_WaitingConfirmation);

	private final String value;

	private InOutDocStatus(String value)
	{
		this.value = value;
	}
}
