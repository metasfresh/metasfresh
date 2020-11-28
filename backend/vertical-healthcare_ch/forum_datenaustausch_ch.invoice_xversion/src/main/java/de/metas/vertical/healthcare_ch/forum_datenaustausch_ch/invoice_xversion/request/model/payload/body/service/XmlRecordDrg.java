package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService.ServiceModWithSelector.ServiceMod;

import javax.xml.datatype.XMLGregorianCalendar;

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
public class XmlRecordDrg implements XmlService
{
	@NonNull
	XmlRecordService recordService;

	@NonNull
	String tariffType;

	/** expecting default = 1 */
	@NonNull
	BigDecimal costFraction;

	@Override
	public String getName()
	{
		return recordService.getName();
	}

	@Override
	public XMLGregorianCalendar getDateBegin()
	{
		return recordService.getDateBegin();
	}

	@Override
	public Integer getRecordId()
	{
		return recordService.getRecordId();
	}

	@Override
	public BigDecimal getAmount()
	{
		return recordService.getAmount();
	}

	@Override
	public BigDecimal getExternalFactor()
	{
		return recordService.getExternalFactor();
	}

	@Override
	public XmlService withModNonNull(@NonNull final ServiceMod serviceMod)
	{
		return toBuilder()
				.recordService(recordService.withModNonNull(serviceMod))
				.build();
	}
}
