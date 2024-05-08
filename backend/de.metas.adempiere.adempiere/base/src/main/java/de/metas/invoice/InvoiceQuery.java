/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.invoice;

import de.metas.document.DocBaseAndSubType;
import de.metas.organization.OrgId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class InvoiceQuery
{
	Integer invoiceId;
	ExternalId externalId;
	OrgId orgId;
	String documentNo;
	DocBaseAndSubType docType;

	@Builder(toBuilder = true)
	public InvoiceQuery(@Nullable final Integer invoiceId,
			@Nullable final ExternalId externalId,
			@NonNull final OrgId orgId,
			@Nullable final String documentNo,
			@Nullable final DocBaseAndSubType docType)
	{
		this.invoiceId = invoiceId;
		this.externalId = externalId;
		this.orgId = orgId;
		this.documentNo = documentNo;
		this.docType = docType;
	}
}
