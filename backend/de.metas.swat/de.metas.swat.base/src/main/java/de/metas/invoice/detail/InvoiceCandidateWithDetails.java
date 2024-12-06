/*
 * #%L
<<<<<<<< HEAD:backend/de.metas.swat/de.metas.swat.base/src/main/java/de/metas/invoice/detail/InvoiceCandidateWithDetails.java
 * de.metas.business
========
 * de.metas.acct.base
>>>>>>>> new_dawn_uat:backend/de.metas.acct.base/src/main/java/de/metas/acct/gljournal_sap/service/SAPGLJournalCopyRequest.java
 * %%
 * Copyright (C) 2023 metas GmbH
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

<<<<<<<< HEAD:backend/de.metas.swat/de.metas.swat.base/src/main/java/de/metas/invoice/detail/InvoiceCandidateWithDetails.java
package de.metas.invoice.detail;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class InvoiceCandidateWithDetails
{
	@NonNull
	InvoiceCandidateId invoiceCandidateId;

	@Nullable InvoiceLineId invoiceLineId;

	@Singular
	ImmutableList<InvoiceDetailItem> detailItems;
========
package de.metas.acct.gljournal_sap.service;

import de.metas.acct.gljournal_sap.SAPGLJournalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class SAPGLJournalCopyRequest
{
	@NonNull SAPGLJournalId sourceJournalId;

	@NonNull Instant dateDoc;

	@NonNull Boolean negateAmounts;
>>>>>>>> new_dawn_uat:backend/de.metas.acct.base/src/main/java/de/metas/acct/gljournal_sap/service/SAPGLJournalCopyRequest.java
}
