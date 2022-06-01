package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice.InvoiceMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody.BodyMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlCredit;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlReminder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder(toBuilder = true)
public class XmlPayload
{
	/** Expecting default = invoice */
	@NonNull
	String type;

	/** Expecting default = false */
	@NonNull
	Boolean storno;

	/** Expecting default = false */
	@NonNull
	Boolean copy;

	/** Expecting default = false */
	@Nullable
	Boolean creditAdvice;

	/** Expecting default = false */
	@Nullable
	Boolean ifStornoFollowupInvoiceProbable;

	@Nullable
	XmlCredit credit;

	@NonNull
	XmlInvoice invoice;

	@Nullable
	XmlReminder reminder;

	@NonNull
	XmlBody body;

	public XmlPayload withMod(@Nullable final PayloadMod payloadMod)
	{
		if (payloadMod == null)
		{
			return this;
		}

		final XmlPayloadBuilder builder = toBuilder();

		if (payloadMod.getStorno() != null)
		{
			builder.storno(payloadMod.getStorno());
		}
		if (payloadMod.getCopy() != null)
		{
			builder.copy(payloadMod.getCopy());
		}
		if (payloadMod.getCreditAdvice() != null)
		{
			builder.creditAdvice(payloadMod.getCreditAdvice());
		}

		return builder
				.invoice(invoice.withMod(payloadMod.getInvoiceMod()))
				.reminder(payloadMod.getReminder())
				.body(body.withMod(payloadMod.getBodyMod()))
				.build();
	}

	@Value
	@Builder
	public static class PayloadMod
	{
		@Nullable
		String type;

		@Nullable
		Boolean storno;

		@Nullable
		Boolean copy;

		@Nullable
		Boolean creditAdvice;

		@Nullable
		InvoiceMod invoiceMod;

		@Nullable
		XmlReminder reminder;

		@Nullable
		BodyMod bodyMod;
	}
}
