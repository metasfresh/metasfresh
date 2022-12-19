package de.metas.acct.gljournal_sap.service;

import de.metas.acct.api.AccountId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class SAPGLJournalLineCreateRequest
{
	@NonNull SAPGLJournalId glJournalId;
	@NonNull PostingSign postingSign;
	@NonNull AccountId accountId;
	@NonNull BigDecimal amount;
	@Nullable TaxId taxId;
}
