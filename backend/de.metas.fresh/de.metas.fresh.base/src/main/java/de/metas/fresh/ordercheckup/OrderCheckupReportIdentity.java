package de.metas.fresh.ordercheckup;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.order.OrderId;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * What tells one {@link I_C_Order_MFGWarehouse_Report} of an order apart from another: two reports of the same
 * order carrying the same identity are the same sheet, generated twice.
 * <p>
 * Warehouse and plant are deliberately not part of it: they are taken from whichever order line first reaches a
 * report, so they follow from the identity rather than defining it.
 */
@Value
public class OrderCheckupReportIdentity
{
	/**
	 * @param responsibleUserId the user the report is addressed to, as known while the report is being built -- the
	 *         workflow's user in charge, or the plant's user.
	 */
	public static OrderCheckupReportIdentity of(
			@NonNull final OrderId orderId,
			@NonNull final OrderCheckupDocumentType documentType,
			@Nullable final UserId responsibleUserId)
	{
		return new OrderCheckupReportIdentity(orderId, documentType, persistableUserIdOrNull(UserId.toRepoId(responsibleUserId)));
	}

	public static OrderCheckupReportIdentity ofReport(@NonNull final I_C_Order_MFGWarehouse_Report report)
	{
		return new OrderCheckupReportIdentity(
				OrderId.ofRepoId(report.getC_Order_ID()),
				OrderCheckupDocumentType.ofCode(report.getDocumentType()),
				persistableUserIdOrNull(report.getAD_User_Responsible_ID()));
	}

	/**
	 * {@code AD_User_Responsible_ID} is null in the database for every user id below 1, so a report built for
	 * {@link UserId#SYSTEM} and one built for nobody are the same record. Normalizing here is what lets an
	 * identity built from a report-to-be be compared with one read back off the saved record.
	 */
	@Nullable
	private static UserId persistableUserIdOrNull(final int userRepoId)
	{
		return UserId.ofRepoIdOrNullIfSystem(userRepoId);
	}

	@NonNull OrderId orderId;
	@NonNull OrderCheckupDocumentType documentType;
	@Nullable UserId responsibleUserId;

	private OrderCheckupReportIdentity(
			@NonNull final OrderId orderId,
			@NonNull final OrderCheckupDocumentType documentType,
			@Nullable final UserId responsibleUserId)
	{
		this.orderId = orderId;
		this.documentType = documentType;
		this.responsibleUserId = responsibleUserId;
	}
}
