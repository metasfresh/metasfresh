/*
 * #%L
 * de.metas.banking.camt53
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.banking.camt53.wrapper.v08;

import de.metas.banking.camt53.jaxb.camt053_001_08.EntryTransaction10;
import de.metas.banking.camt53.jaxb.camt053_001_08.PartyIdentification135;
import de.metas.banking.camt53.jaxb.camt053_001_08.RemittanceInformation16;
import de.metas.banking.camt53.jaxb.camt053_001_08.TransactionParties6;
import de.metas.banking.camt53.wrapper.TransactionDtlsWrapper;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

import static de.metas.banking.camt53.jaxb.camt053_001_08.CreditDebitCode.CRDT;

@Value
@EqualsAndHashCode(callSuper = true)
public class TransactionDtls10Wrapper extends TransactionDtlsWrapper
{
	@NonNull
	EntryTransaction10 entryDtls;

	@Builder
	private TransactionDtls10Wrapper(@NonNull final EntryTransaction10 entryDtls)
	{
		this.entryDtls = entryDtls;
	}

	@Nullable
	@Override
	public String getAcctSvcrRef()
	{
		return entryDtls.getRefs() != null ? entryDtls.getRefs().getAcctSvcrRef() : null;
	}

	@Override
	public String getDbtrNames()
	{
		final TransactionParties6 party = entryDtls.getRltdPties();
		if (party != null && party.getDbtr() != null && party.getDbtr().getPty() != null)
		{
			final PartyIdentification135 dbtr = party.getDbtr().getPty();
			return dbtr.getNm();
		}

		return null;
	}

	@Override
	public String getCdtrNames()
	{
		final TransactionParties6 party = entryDtls.getRltdPties();
		if (party != null && party.getCdtr() != null && party.getCdtr().getPty() != null)
		{
			final PartyIdentification135 cdtr = party.getCdtr().getPty();
			return cdtr.getNm();
		}

		return null;
	}

	@Nullable
	@Override
	protected String getUnstructuredRemittanceInfo(final @NonNull String delimiter)
	{
		final RemittanceInformation16 rmtInf = entryDtls.getRmtInf();
		if(rmtInf == null)
		{
			return null;
		}
		return String.join(delimiter, rmtInf.getUstrd());
	}

	@Override
	protected @NonNull String getLineDescription(final @NonNull String delimiter)
	{
		return entryDtls.getAddtlTxInf() != null ? entryDtls.getAddtlTxInf() : "";
	}

	@Nullable
	@Override
	public String getCcy()
	{
		return entryDtls.getAmt().getCcy();
	}

	/**
	 * @return true if this is a "credit" line (i.e. we get money)
	 */
	@Override
	public boolean isCRDT()
	{
		return CRDT == entryDtls.getCdtDbtInd();
	}
}
