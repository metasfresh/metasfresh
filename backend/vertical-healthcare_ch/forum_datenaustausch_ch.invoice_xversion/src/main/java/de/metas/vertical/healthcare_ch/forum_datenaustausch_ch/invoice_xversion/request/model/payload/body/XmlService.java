package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService.ServiceModWithSelector.ServiceMod;

import java.math.BigDecimal;
import java.time.LocalDate;

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

public interface XmlService
{
	Integer getRecordId();

	default XmlService withMod(@Nullable final ServiceMod serviceMod)
	{
		if (serviceMod == null)
		{
			return this;
		}
		return withModNonNull(serviceMod);
	}

	String getName();

	XMLGregorianCalendar getDateBegin();

	XmlService withModNonNull(ServiceMod serviceMod);

	BigDecimal getAmount();

	BigDecimal getExternalFactor();

	@Value
	public static class ServiceModWithSelector
	{
		/** recordId is the selector for the XmlService to which this mod shall be applied. */
		Integer recordId;

		ServiceMod serviceMod;

		@Value
		public static class ServiceMod
		{
			@NonNull
			BigDecimal amount;
		}

		@Builder
		public ServiceModWithSelector(
				@NonNull final Integer recordId,
				@Nullable final BigDecimal amount)
		{
			this.recordId = recordId;

			if (amount != null)
			{
				this.serviceMod = new ServiceMod(amount);
			}
			else
			{
				this.serviceMod = null;
			}
		}
	}
}
