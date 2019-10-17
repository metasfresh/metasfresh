package de.metas.bpartner.composite;

import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
public class BPartnerContactType
{

	public static final String DEFAULT_CONTACT = "defaultContact";
	public static final String BILL_TO_DEFAULT = "billToDefault";
	public static final String SHIP_TO_DEFAULT = "shipToDefault";
	public static final String SALES = "sales";
	public static final String SALES_DEFAULT = "salesDefault";
	public static final String PURCHASE = "purchase";
	public static final String PURCHASE_DEFAULT = "purchaseDefault";
	public static final String SUBJECT_MATTER = "subjectMatter";

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> defaultContact;

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> billToDefault;

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> shipToDefault;

	@JsonInclude(Include.NON_ABSENT)
	private final Optional<Boolean> sales;

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> salesDefault;

	@JsonInclude(Include.NON_ABSENT)
	private final Optional<Boolean> purchase;

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> purchaseDefault;

	@JsonInclude(Include.NON_ABSENT)
	private final Optional<Boolean> subjectMatter;

	@Builder
	public BPartnerContactType(
			@Nullable final Boolean defaultContact,
			@Nullable final Boolean billToDefault,
			@Nullable final Boolean shipToDefault,
			@Nullable final Boolean sales,
			@Nullable final Boolean salesDefault,
			@Nullable final Boolean purchase,
			@Nullable final Boolean purchaseDefault,
			@Nullable final Boolean subjectMatter)
	{
		this.defaultContact = Optional.ofNullable(defaultContact);
		this.billToDefault = Optional.ofNullable(billToDefault);
		this.shipToDefault = Optional.ofNullable(shipToDefault);

		this.salesDefault = Optional.ofNullable(salesDefault);
		if (this.salesDefault.orElse(false) && sales == null)
		{
			this.sales = Optional.of(true);
		}
		else
		{
			this.sales = Optional.ofNullable(sales);
		}

		this.purchaseDefault = Optional.ofNullable(purchaseDefault);
		if (this.purchaseDefault.orElse(false) && purchase == null)
		{
			this.purchase = Optional.of(true);
		}
		else
		{
			this.purchase = Optional.ofNullable(purchase);
		}

		this.subjectMatter = Optional.ofNullable(subjectMatter);
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getDefaultContactNotNull()
	{
		return defaultContact.orElseThrow(() -> createException("defaultContact may not be null"));
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getBillToDefaultNotNull()
	{
		return billToDefault.orElseThrow(() -> createException("billToDefault may not be null"));
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getShipToDefaultNotNull()
	{
		return shipToDefault.orElseThrow(() -> createException("shipToDefault may not be null"));
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getSalesDefaultNotNull()
	{
		return salesDefault.orElseThrow(() -> createException("salesDefault may not be null"));
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getSalesNotNull()
	{
		return sales.orElseThrow(() -> createException("sales may not be null"));
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getPurchaseDefaultNotNull()
	{
		return purchaseDefault.orElseThrow(() -> createException("purchaseDefault may not be null"));
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getPurchaseNotNull()
	{
		return purchase.orElseThrow(() -> createException("purchase may not be null"));
	}

	/** Use this method only if the values can't be {@code null}, e.g. because this instance is coming straight from {@link BPartnerCompositeRepository}. */
	public boolean getSubjectMatterNotNull()
	{
		return subjectMatter.orElseThrow(() -> createException("subjectMatter may not be null"));
	}

	private AdempiereException createException(@NonNull final String message)
	{
		return new AdempiereException(message)
				.appendParametersToMessage()
				.setParameter("contactLocationType", this);
	}

	public void setDefaultContact(final boolean defaultContact)
	{
		this.defaultContact = Optional.of(defaultContact);
	}

	public void setBillToDefault(final boolean billToDefault)
	{
		this.billToDefault = Optional.of(billToDefault);
	}

	public void setShipToDefault(final boolean shipToDefault)
	{
		this.shipToDefault = Optional.of(shipToDefault);
	}

	public void setPurchaseDefault(final boolean purchaseDefault)
	{
		this.purchaseDefault = Optional.of(purchaseDefault);
	}

	public void setSalesDefault(final boolean salesDefault)
	{
		this.salesDefault = Optional.of(salesDefault);
	}
}
