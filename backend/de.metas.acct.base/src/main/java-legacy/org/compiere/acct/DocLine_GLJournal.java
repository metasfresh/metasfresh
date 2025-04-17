package org.compiere.acct;

import de.metas.acct.Account;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_GL_JournalLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/*
 * #%L
 * de.metas.acct.base
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

class DocLine_GLJournal extends DocLine<Doc_GLJournal>
{
	@Getter private final int groupNo;
	@Getter @Setter private AcctSchemaId acctSchemaId;
	@Getter @Setter private BigDecimal fixedCurrencyRate;
	@Getter private Account account;
	@Nullable @Setter private ProductId productId;
	@Nullable @Setter private OrderId salesOrderId;

	DocLine_GLJournal(@NonNull final I_GL_JournalLine glJournalLine, @NonNull final Doc_GLJournal doc)
	{
		super(InterfaceWrapperHelper.getPO(glJournalLine), doc);
		groupNo = glJournalLine.getGL_JournalLine_Group();
		fixedCurrencyRate = glJournalLine.getCurrencyRate();
	}

	final void setAccount(@NonNull final I_C_ValidCombination acct)
	{
		setAccount(Account.ofId(AccountId.ofRepoId(acct.getC_ValidCombination_ID())));
	}

	final void setAccount(@NonNull final Account account)
	{
		this.account = account;
	}

	@Override
	@Nullable
	public ProductId getProductId() {return productId;}

	@Nullable
	@Override
	protected OrderId getSalesOrderId() {return salesOrderId;}
}
