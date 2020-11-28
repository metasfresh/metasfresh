package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlDiagnosis;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraAmbulatory;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.treatment.XmlXtraStationary;

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
@Builder
public class XmlTreatment
{
	@NonNull
	XMLGregorianCalendar dateBegin;

	@NonNull
	XMLGregorianCalendar dateEnd;

	@NonNull
	String canton;

	@NonNull
	String reason;

	@Nullable
	String apid;

	@Nullable
	String acid;

	@Singular("diagnosis")
	List<XmlDiagnosis> diagnoses;

	@Nullable
	XmlXtraAmbulatory xtraAmbulatory;

	@Nullable
	XmlXtraStationary xtraStationary;
}
