package de.metas.payment.esr.model;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface I_C_BP_BankAccount extends org.compiere.model.I_C_BP_BankAccount
{
	// @formatter:off
	String COLUMNNAME_ESR_RenderedAccountNo = "ESR_RenderedAccountNo";
	@Override String getESR_RenderedAccountNo();
	@Override void setESR_RenderedAccountNo(String esrRenderedAccountNo);
	// @formatter:on


	// @formatter:off
//  not used; TODO consider dropping the column
//	String COLUMNNAME_ESR_RenderedReceiver = "ESR_RenderedReceiver";
//	@Override String getESR_RenderedReceiver();
//	@Override void setESR_RenderedReceiver(String esrRenderedReceiver);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_IsEsrAccount = "IsEsrAccount";
	@Override boolean isEsrAccount();
	@Override void setIsEsrAccount(boolean isEsrAccount);
	// @formatter:on

	// 04288: column isDefaultESR
	// @formatter:off
	String COLUMNNAME_IsDefaultESR = "IsDefaultESR";
	@Override boolean isDefaultESR();
	// @formatter:on
}
