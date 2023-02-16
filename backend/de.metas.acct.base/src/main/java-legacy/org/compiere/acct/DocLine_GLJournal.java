package org.compiere.acct;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.sectionCode.SectionCodeId;
import lombok.Getter;
import lombok.Setter;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.acct.Account;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_GL_JournalLine;

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
	@Getter
	private final int groupNo;

	@Getter
	@Setter
	private AcctSchemaId acctSchemaId;

	@Getter
	@Setter
	private BigDecimal fixedCurrencyRate;

	private Account account = null;

	@Getter
	@Setter
	private ProductId productId;

	@Getter
	@Setter
	private OrderId orderId;

	@Getter
	@Setter
	private SectionCodeId sectionCodeId;

	public DocLine_GLJournal(final I_GL_JournalLine glJournalLine, final Doc_GLJournal doc)
	{
		super(InterfaceWrapperHelper.getPO(glJournalLine), doc);
		groupNo = glJournalLine.getGL_JournalLine_Group();
		fixedCurrencyRate = glJournalLine.getCurrencyRate();
	}

	public final void setAccount(final I_C_ValidCombination acct)
	{
		account = Account.ofId(AccountId.ofRepoId(acct.getC_ValidCombination_ID()));
	}

	public final Account getAccount()
	{
		return account;
	}   // getAccount

}
