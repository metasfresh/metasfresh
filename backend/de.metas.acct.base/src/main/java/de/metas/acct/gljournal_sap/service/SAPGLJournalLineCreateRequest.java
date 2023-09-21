package de.metas.acct.gljournal_sap.service;

import de.metas.acct.Account;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.gljournal_sap.SAPGLJournalLineId;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.Dimension;
import de.metas.money.CurrencyId;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class SAPGLJournalLineCreateRequest
{
	@NonNull PostingSign postingSign;
	@NonNull Account account;
	@NonNull BigDecimal amount;
	@Nullable CurrencyId expectedCurrencyId;
	@Nullable TaxId taxId;
	@Nullable String description;
	@Nullable BPartnerId bpartnerId;
	@NonNull Dimension dimension;
	boolean determineTaxBaseSAP;
	@Nullable @With FAOpenItemTrxInfo openItemTrxInfo;
	boolean isFieldsReadOnlyInUI;

	@Nullable @With SAPGLJournalLineId alreadyReservedId;
	boolean isTaxIncluded;

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
				.isTaxIncluded(line.isTaxIncluded())
				.build();
	}
}
