package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService.ServiceModWithSelector.ServiceMod;

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
public class XmlRecordService
{
	@NonNull
	Integer recordId;

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

	/** expecting default = 1 */
	@NonNull
	BigDecimal unitFactor;

	/** expecting default = 1 */
	@NonNull
	BigDecimal externalFactor;

	@NonNull
	BigDecimal amount;

	/** expecting default = 0 */
	@NonNull
	BigDecimal vatRate;

	/** expecting default = false */
	@NonNull
	Boolean validate;

	/** expecting default = true */
	@NonNull
	Boolean obligation;

	@Nullable
	String sectionCode;

	@Nullable
	String remark;

	/** expecting default = 0 */
	@NonNull
	Long serviceAttributes;

	public XmlRecordService withModNonNull(@NonNull final ServiceMod serviceMod)
	{
		return toBuilder()
				.amount(serviceMod.getAmount())
				.build();
	}
}
