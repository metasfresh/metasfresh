package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

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
public class XmlRecordServiceType implements XmlService
{
	/**
	 * expecting default = 1
	 */
	@Nullable
	XmlXtraDrg xtraDrg;

	@Nullable
	XmlXtraDrug xtraDrug;

	@NonNull
	int recordId;

	@NonNull
	String tariffType;

	@NonNull
	String code;

	@Nullable
	String refCode;

	@NonNull
	String name;

	@Nullable
	Integer session;

	@NonNull
	BigDecimal quantity;

	@NonNull
	XMLGregorianCalendar dateBegin;

	@Nullable
	XMLGregorianCalendar dateEnd;

	@NonNull
	String providerId;

	@NonNull
	String responsibleId;

	@NonNull
	BigDecimal unit;

	@Nullable
	BigDecimal unitFactor;

	@Nullable
	BigDecimal externalFactor;

	@Nullable
	BigDecimal amount;

	@Nullable
	BigDecimal vatRate;

	@Nullable
	Boolean obligation;

	@Nullable
	String sectionCode;

	@Nullable
	String remark;

	@Nullable
	Long serviceAttributes;

	@Override
	public Integer getRecordId()
	{
		return recordId;
	}

	@Override
	public XmlService withMod(@Nullable final ServiceModWithSelector.ServiceMod serviceMod)
	{
		if (serviceMod == null)
		{
			return this;
		}
		return withModNonNull(serviceMod);
	}

	@Override
	public String getName()
	{
		return name;
	}

	public XMLGregorianCalendar getDateBegin()
	{
		return dateBegin;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public BigDecimal getExternalFactor()
	{
		return externalFactor;
	}

	@Override
	public XmlService withModNonNull(final ServiceModWithSelector.ServiceMod serviceMod)
	{
		return toBuilder()
				.amount(serviceMod.getAmount())
				.build();
	}
}
