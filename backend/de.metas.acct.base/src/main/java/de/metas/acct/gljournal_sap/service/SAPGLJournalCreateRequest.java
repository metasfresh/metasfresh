/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct.gljournal_sap.service;

import com.google.common.collect.ImmutableList;
import de.metas.acct.GLCategoryId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.document.DocTypeId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.sectionCode.SectionCodeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class SAPGLJournalCreateRequest
{
	@NonNull DocTypeId docTypeId;
	@NonNull SAPGLJournalCurrencyConversionCtx conversionCtx;

	@NonNull AcctSchemaId acctSchemaId;
	@NonNull PostingType postingType;
	@NonNull Instant dateDoc;

	@NonNull Money totalAcctDR;
	@NonNull Money totalAcctCR;

	@NonNull OrgId orgId;
	@NonNull String description;
	@NonNull GLCategoryId glCategoryId;
	@Nullable SectionCodeId sectionCodeId;

	@NonNull ImmutableList<SAPGLJournalLineCreateRequest> lines;

	@NonNull
	public static SAPGLJournalCreateRequest of(
			@NonNull final SAPGLJournal journal,
			@NonNull final Instant dateDoc,
			@NonNull final Boolean negateAmounts)
	{
		return SAPGLJournalCreateRequest.builder()
				.docTypeId(journal.getDocTypeId())
				.conversionCtx(journal.getConversionCtx())
				.dateDoc(dateDoc)
				.acctSchemaId(journal.getAcctSchemaId())
				.postingType(journal.getPostingType())
				.totalAcctDR(journal.getTotalAcctDR().negateIf(negateAmounts))
				.totalAcctCR(journal.getTotalAcctCR().negateIf(negateAmounts))
				.orgId(journal.getOrgId())
				.sectionCodeId(journal.getDimension().getSectionCodeId())
				.description(journal.getDescription())
				.glCategoryId(journal.getGlCategoryId())
				//
				.lines(journal.getLines()
							   .stream()
							   .map(line -> SAPGLJournalLineCreateRequest.of(line, negateAmounts))
							   .collect(ImmutableList.toImmutableList()))
				.build();
	}
}
