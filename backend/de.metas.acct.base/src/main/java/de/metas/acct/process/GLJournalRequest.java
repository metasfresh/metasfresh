/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct.process;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_GL_JournalBatch;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Builder
@Value
public class GLJournalRequest
{
	@Nullable
	I_GL_JournalBatch glJournalBatch;

	@Nullable
	Instant dateFrom;

	@Nullable
	Instant dateTo;

	@Nullable
	AcctSchemaId acctSchemaId ;

	@Nullable
	AccountId accountIncomeSummaryId;

	@Nullable
	AccountId accountRetainedEarningId;

	static final ModelDynAttributeAccessor<I_C_ElementValue, BigDecimal> DYNATTR_AmtAcctDr = new ModelDynAttributeAccessor<>("AmtAcctDr", BigDecimal.class);
	static final ModelDynAttributeAccessor<I_C_ElementValue, BigDecimal> DYNATTR_AmtAcctCr = new ModelDynAttributeAccessor<>("AmtAcctCr", BigDecimal.class);

}
