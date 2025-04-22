/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.bpartner.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.metas.banking.BankId;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.common.util.Check;
import de.metas.money.CurrencyId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.table.RecordChangeLog;

import javax.annotation.Nullable;

import static de.metas.common.util.CoalesceUtil.coalesce;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Data
@JsonPropertyOrder(alphabetic = true/* we want the serialized json to be less flaky in our snapshot files */)
public class BPartnerBankAccount
{
	public static final String ID = "id";
	public static final String BPARTNER_ID = "bpartnerId";
	public static final String IBAN = "iban";
	public static final String CURRENCY_ID = "currencyId";
	public static final String ACTIVE = "active";
	public static final String ACCOUNT_NAME = "accountName";
	public static final String ACCOUNT_STREET = "accountStreet";
	public static final String ACCOUNT_ZIP = "accountZip";
	public static final String ACCOUNT_CITY = "accountCity";
	public static final String ACCOUNT_COUNTRY = "accountCountry";

	@Nullable
	private BPartnerBankAccountId id;

	/**
	 * A bit redundant because it's already part of the {@link BPartnerBankAccountId}, but we use if for mapping purposes.
	 */
	@Setter(AccessLevel.NONE)
	@JsonIgnore
	private BPartnerId bpartnerId;

	@NonNull
	private String iban;

	@Nullable
	private String qrIban;

	@NonNull
	private CurrencyId currencyId;

	private boolean active;

	private final RecordChangeLog changeLog;

	@Nullable
	private OrgMappingId orgMappingId;

	@Nullable
	private BankId bankId;

	@Nullable
	private String accountName;

	@Nullable
	private String accountStreet;

	@Nullable
	private String accountZip;

	@Nullable
	private String accountCity;

	@Nullable
	private String accountCountry;


	@Builder(toBuilder = true)
	private BPartnerBankAccount(
			@Nullable final BPartnerBankAccountId id,
			@NonNull final String iban,
			@Nullable final String qrIban,
			@NonNull final CurrencyId currencyId,
			@Nullable final Boolean active,
			@Nullable final RecordChangeLog changeLog,
			@Nullable final OrgMappingId orgMappingId,
			@Nullable final BankId bankId,
			@Nullable final String accountName,
			@Nullable final String accountStreet,
			@Nullable final String accountZip,
			@Nullable final String accountCity,
			@Nullable final String accountCountry)
	{
		setId(id);
		this.iban = iban;
		this.qrIban = qrIban;
		this.currencyId = currencyId;
		this.active = coalesce(active, true);

		this.changeLog = changeLog;

		this.orgMappingId = orgMappingId;
		this.bankId = bankId;
		this.accountName = accountName;
		this.accountStreet = accountStreet;
		this.accountZip = accountZip;
		this.accountCity = accountCity;
		this.accountCountry = accountCountry;
	}

	public final void setId(@Nullable final BPartnerBankAccountId id)
	{
		this.id = id;
		this.bpartnerId = id != null ? id.getBpartnerId() : null;
	}

	@NonNull
	public BPartnerBankAccountId getIdNotNull()
	{
		return Check.assumeNotNull(id, "Assuming the id is set at this point!");
	}
}
