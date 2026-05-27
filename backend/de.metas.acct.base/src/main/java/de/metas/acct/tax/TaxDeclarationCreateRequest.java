package de.metas.acct.tax;

import de.metas.acct.api.AcctSchemaId;
import de.metas.calendar.PeriodId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * Immutable request to create a new {@code C_TaxDeclaration} record via
 * {@link TaxDeclarationRepository#createTaxDeclaration(TaxDeclarationCreateRequest)}.
 * <p>
 * Carries only the persistable field values — no business logic, no guard checks.
 */
@Value
@Builder
public class TaxDeclarationCreateRequest
{
	@NonNull OrgId adOrgId;
	@NonNull AcctSchemaId acctSchemaId;
	@NonNull PeriodId cPeriodId;
	@NonNull Timestamp dateAcct;
	boolean isCorrection;
	@Nullable TaxDeclarationId originalId;
}
