package de.metas.forex;

import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.sectionCode.SectionCodeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;

@Value
@Builder
public class ForexContractQuery
{
	@Nullable DocStatus docStatus;
	@Nullable CurrencyId currencyId;
	@Nullable CurrencyId currencyToId;
	@Nullable String displayNameSearchTerm;
	@Nullable SectionCodeId sectionCodeId;
	boolean onlyWithOpenAmount;

	@NonNull @Builder.Default QueryLimit limit = QueryLimit.ofInt(100);
}
