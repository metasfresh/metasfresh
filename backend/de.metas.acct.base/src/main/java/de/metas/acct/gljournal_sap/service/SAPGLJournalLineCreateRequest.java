package de.metas.acct.gljournal_sap.service;

import de.metas.acct.Account;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.document.dimension.Dimension;
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
	@NonNull PostingSign postingSign;
	@NonNull Account account;
	@NonNull BigDecimal amount;
	@NonNull Dimension dimension;
	@Nullable TaxId taxId;
	@Nullable String description;
	boolean determineTaxBaseSAP;

	@NonNull
	public static SAPGLJournalLineCreateRequest of(
			@NonNull final SAPGLJournalLine line,
			@NonNull final Boolean negateAmounts)
	{
		return SAPGLJournalLineCreateRequest.builder()
				.postingSign(line.getPostingSign())
				.account(line.getAccount())
				.amount(line.getAmount().negateIf(negateAmounts).toBigDecimal())
				.dimension(line.getDimension())
				.description(line.getDescription())
				.taxId(line.getTaxId())
				.determineTaxBaseSAP(line.isDetermineTaxBaseSAP())
				.build();
	}
}
