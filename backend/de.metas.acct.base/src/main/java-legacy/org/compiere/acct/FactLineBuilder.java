package org.compiere.acct;

import de.metas.acct.Account;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString(exclude = "fact", doNotUseGetters = true)
public final class FactLineBuilder
{
	private static final Logger log = Fact.log;
	private boolean built = false;

	private final Fact fact;
	private DocLine<?> docLine = null;
	private Integer subLineId = null;

	private Account account = null;

	private CurrencyId currencyId;
	@Nullable private CurrencyConversionContext currencyConversionCtx;
	@Nullable private BigDecimal amtSourceDr;
	@Nullable private BigDecimal amtSourceCr;
	@Nullable private BigDecimal amtAcctDr;
	@Nullable private BigDecimal amtAcctCr;

	private Quantity qty = null;

	private boolean alsoAddZeroLine = false;

	// Other dimensions
	private OrgId orgId;
	private OrgId orgTrxId;
	@Nullable private BPartnerId bpartnerId;
	@Nullable private BPartnerLocationId bPartnerLocationId;
	@Nullable private TaxId C_Tax_ID;
	private Integer locatorId;
	private ActivityId activityId;
	private ProjectId projectId;
	private int campaignId;
	private LocationId fromLocationId;
	private LocationId toLocationId;
	private CostElementId costElementId;

	private String additionalDescription = null;

	private FAOpenItemTrxInfo openItemTrxInfo;

	FactLineBuilder(@NonNull final Fact fact)
	{
		this.fact = fact;
	}

	/**
	 * Creates the {@link FactLine} and adds it to {@link Fact}.
	 *
	 * @return created {@link FactLine}
	 */
	@Nullable
	public FactLine buildAndAdd()
	{
		final FactLine fl = build();

		if (fl != null)
		{
			fact.add(fl);
		}

		return fl;
	}

	@Nullable
	private FactLine build()
	{
		markAsBuilt();

		// Data Check
		if (account == null)
		{
			throw new AdempiereException("No account for " + this);
		}

		//
		final Doc<?> doc = fact.m_doc;
		final AcctDocRequiredServicesFacade services = doc.getServices();
		final FactLine line = FactLine.builder()
				.services(services)
				.doc(doc)
				.docLine(docLine)
				.docRecordRef(doc.getRecordRef())
				.Line_ID(docLine == null ? 0 : docLine.get_ID())
				.SubLine_ID(getSubLine_ID())
				.postingType(fact.getPostingType())
				.acctSchema(fact.getAcctSchema())
				.account(services.getAccountById(this.account.getAccountId()))
				.accountConceptualName(this.account.getAccountConceptualName())
				.qty(qty)
				.M_Locator_ID(locatorId)
				.projectId(projectId)
				.activityId(activityId)
				.additionalDescription(additionalDescription)
				.build();

		//
		// Amounts - one needs to be not zero
		line.setAmtSource(getAmtSourceDr(), getAmtSourceCr());

		// Skip zero amount & qty lines
		if (!alsoAddZeroLine && line.isZeroAmtSourceAndQty())
		{
			return null;
		}

		//
		// Amounts converted to accounting currency
		if (amtAcctDr != null || amtAcctCr != null)
		{
			line.setAmtAcct(amtAcctDr, amtAcctCr);
		}
		else if (docLine != null && (docLine.getAmtAcctDr() != null || docLine.getAmtAcctCr() != null))
		{
			line.setAmtAcct(docLine.getAmtAcctDr(), docLine.getAmtAcctCr());
		}
		else
		{
			final CurrencyConversionContext currencyConversionCtx = getCurrencyConversionCtx();
			if (currencyConversionCtx != null)
			{
				line.setCurrencyConversionCtx(currencyConversionCtx);
				line.addDescription(currencyConversionCtx.getSummary());
			}

			line.convert();
		}

		//
		// Set the other dimensions
		if (orgId != null)
		{
			line.setAD_Org_ID(orgId);
		}
		if (orgTrxId != null)
		{
			line.setAD_OrgTrx_ID(orgTrxId);
		}
		//
		if (bPartnerLocationId != null)
		{
			line.setBPartnerIdAndLocation(bpartnerId, bPartnerLocationId);
		}
		else if (bpartnerId != null)
		{
			line.setBPartnerId(bpartnerId);
		}
		//
		if (C_Tax_ID != null)
		{
			line.setTaxIdAndUpdateVatCode(C_Tax_ID);
		}

		if (fromLocationId != null)
		{
			line.setC_LocFrom_ID(fromLocationId);
		}
		if (toLocationId != null)
		{
			line.setC_LocTo_ID(toLocationId);
		}

		if (costElementId != null)
		{
			line.setCostElementId(costElementId);
		}

		line.setOpenItemTrxInfo(openItemTrxInfo);

		//
		log.debug("Built: {}", line);
		return line;
	}

	private void assertNotBuild()
	{
		Check.assume(!built, "not already built");
	}

	private void markAsBuilt()
	{
		assertNotBuild();
		built = true;
	}

	@NonNull
	public FactLineBuilder setAccount(@NonNull final Account account)
	{
		assertNotBuild();
		this.account = account;
		return this;
	}

	private AcctDocRequiredServicesFacade getServices()
	{
		return fact.services;
	}

	public FactLineBuilder setDocLine(final DocLine<?> docLine)
	{
		assertNotBuild();
		this.docLine = docLine;
		return this;
	}

	public FactLineBuilder setSubLine_ID(final int subLineId)
	{
		this.subLineId = subLineId;
		return this;
	}

	private Integer getSubLine_ID()
	{
		return subLineId;
	}

	private CurrencyId getAcctCurrencyId()
	{
		return fact.getAcctSchema().getCurrencyId();
	}

	public FactLineBuilder setQty(@NonNull final Quantity qty)
	{
		assertNotBuild();
		this.qty = qty;
		return this;
	}

	public FactLineBuilder setAmtSource(final CurrencyId currencyId, @Nullable final BigDecimal amtSourceDr, @Nullable final BigDecimal amtSourceCr)
	{
		setCurrencyId(currencyId);
		setAmtSource(amtSourceDr, amtSourceCr);
		return this;
	}

	public FactLineBuilder setAmtSource(@Nullable final BigDecimal amtSourceDr, @Nullable final BigDecimal amtSourceCr)
	{
		assertNotBuild();
		this.amtSourceDr = amtSourceDr;
		this.amtSourceCr = amtSourceCr;
		return this;
	}

	public FactLineBuilder setAmtAcct(@Nullable final BigDecimal amtAcctDr, @Nullable final BigDecimal amtAcctCr)
	{
		assertNotBuild();
		this.amtAcctDr = amtAcctDr;
		this.amtAcctCr = amtAcctCr;
		return this;
	}

	public FactLineBuilder setAmtSource(@Nullable final CostAmount amtSourceDr, @Nullable final CostAmount amtSourceCr)
	{
		assertNotBuild();

		setCurrencyId(CostAmount.getCommonCurrencyIdOfAll(amtSourceDr, amtSourceCr));
		setAmtSource(
				amtSourceDr != null ? amtSourceDr.toBigDecimal() : null,
				amtSourceCr != null ? amtSourceCr.toBigDecimal() : null);

		return this;
	}

	public FactLineBuilder setAmtSource(@Nullable final Money amtSourceDr, @Nullable final Money amtSourceCr)
	{
		assertNotBuild();
		setCurrencyId(Money.getCommonCurrencyIdOfAll(amtSourceDr, amtSourceCr));
		setAmtSource(
				amtSourceDr != null ? amtSourceDr.toBigDecimal() : null,
				amtSourceCr != null ? amtSourceCr.toBigDecimal() : null);
		return this;
	}

	public FactLineBuilder setAmtSource(@NonNull final Balance balance)
	{
		assertNotBuild();
		setCurrencyId(balance.getCurrencyId());
		setAmtSource(balance.getDebit().toBigDecimal(), balance.getCredit().toBigDecimal());
		return this;
	}

	public FactLineBuilder setAmt(@Nullable final CostAmount dr, @Nullable final CostAmount cr)
	{
		assertNotBuild();
		setAmtSource(extractAmtSource(dr), extractAmtSource(cr));
		this.amtAcctDr = extractAmtAcct(dr);
		this.amtAcctCr = extractAmtAcct(cr);

		return this;
	}

	@Nullable
	private static Money extractAmtSource(@Nullable final CostAmount costAmount)
	{
		if (costAmount == null)
		{
			return null;
		}
		else if (costAmount.toSourceMoney() != null)
		{
			return costAmount.toSourceMoney();
		}
		else
		{
			return costAmount.toMoney();
		}
	}

	@Nullable
	private BigDecimal extractAmtAcct(@Nullable final CostAmount costAmount)
	{
		if (costAmount == null)
		{
			return null;
		}

		final CurrencyId acctCurrencyId = getAcctCurrencyId();
		final Money sourceValue = costAmount.toSourceMoney();
		if (sourceValue != null && CurrencyId.equals(sourceValue.getCurrencyId(), acctCurrencyId))
		{
			return sourceValue.toBigDecimal();
		}

		final Money value = costAmount.toMoney();
		if (value != null && CurrencyId.equals(value.getCurrencyId(), acctCurrencyId))
		{
			return value.toBigDecimal();
		}

		return null;
	}

	/**
	 * Usually the {@link #buildAndAdd()} method ignores fact lines that have zero/null source amount and zero/null qty.
	 * Invoke this builder method still have the builder add them.
	 */
	public FactLineBuilder alsoAddZeroLine()
	{
		alsoAddZeroLine = true;
		return this;
	}

	public FactLineBuilder setCurrencyId(final CurrencyId currencyId)
	{
		assertNotBuild();
		this.currencyId = currencyId;
		return this;
	}

	public FactLineBuilder setCurrencyConversionCtx(@Nullable final CurrencyConversionContext currencyConversionCtx)
	{
		assertNotBuild();
		this.currencyConversionCtx = currencyConversionCtx;
		return this;
	}

	@Nullable
	private CurrencyConversionContext getCurrencyConversionCtx()
	{
		if (currencyConversionCtx != null)
		{
			return currencyConversionCtx;
		}

		return fact.getCurrencyConversionContext();
	}

	@Nullable
	private Money getAmtSourceDr()
	{
		return amtSourceDr != null ? Money.of(amtSourceDr, currencyId) : null;
	}

	@Nullable
	private Money getAmtSourceCr()
	{
		return amtSourceCr != null ? Money.of(amtSourceCr, currencyId) : null;
	}

	/**
	 * Sets the AmtSourceDr (if amtSource is positive) or AmtSourceCr (if amtSource is negative).
	 */
	public FactLineBuilder setAmtSourceDrOrCr(@NonNull final Money amtSource)
	{
		setCurrencyId(amtSource.getCurrencyId());
		setAmtSourceDrOrCr(amtSource.toBigDecimal());
		return this;
	}

	/**
	 * Sets the AmtSourceDr (if amtSource is positive) or AmtSourceCr (if amtSource is negative).
	 */
	public FactLineBuilder setAmtSourceDrOrCr(@NonNull final BigDecimal amtSource)
	{
		if (amtSource.signum() < 0)
		{
			setAmtSource(null, amtSource.abs());
		}
		else
		{
			setAmtSource(amtSource, null);
		}
		return this;
	}

	public FactLineBuilder orgId(final OrgId orgId)
	{
		assertNotBuild();
		this.orgId = orgId;
		return this;
	}

	public FactLineBuilder orgIdIfValid(final OrgId orgId)
	{
		if (orgId != null && orgId.isRegular())
		{
			orgId(orgId);
		}
		return this;
	}

	public FactLineBuilder orgTrxId(final OrgId orgTrxId)
	{
		assertNotBuild();
		this.orgTrxId = orgTrxId;
		return this;
	}

	@Deprecated
	public FactLineBuilder setC_BPartner_ID(final Integer bpartnerRepoId)
	{
		final BPartnerId bpartnerId = bpartnerRepoId != null ? BPartnerId.ofRepoIdOrNull(bpartnerRepoId) : null;
		return bpartnerId(bpartnerId);
	}

	public FactLineBuilder bpartnerId(@Nullable final BPartnerId bpartnerId)
	{
		assertNotBuild();
		this.bpartnerId = bpartnerId;
		return this;
	}

	public FactLineBuilder bpartnerIdIfNotNull(final BPartnerId bpartnerId)
	{
		if (bpartnerId != null)
		{
			return bpartnerId(bpartnerId);
		}
		else
		{
			return this;
		}
	}

	@NonNull
	public FactLineBuilder bPartnerAndLocationId(
			@Nullable final BPartnerId bPartnerId,
			@Nullable final BPartnerLocationId bPartnerLocationId)
	{
		assertNotBuild();
		this.bpartnerId = bPartnerId;
		this.bPartnerLocationId = bPartnerLocationId;
		return this;
	}

	public FactLineBuilder setC_Tax_ID(final Integer taxId)
	{
		assertNotBuild();
		this.C_Tax_ID = taxId != null ? TaxId.ofRepoIdOrNull(taxId) : null;
		return this;
	}

	public FactLineBuilder setC_Tax_ID(@Nullable final TaxId taxId)
	{
		assertNotBuild();
		this.C_Tax_ID = taxId;
		return this;
	}

	public FactLineBuilder locatorId(final int locatorId)
	{
		assertNotBuild();
		this.locatorId = locatorId;
		return this;
	}

	public FactLineBuilder activityId(@Nullable final ActivityId activityId)
	{
		assertNotBuild();
		this.activityId = activityId;
		return this;
	}

	public FactLineBuilder projectId(@Nullable final ProjectId projectId)
	{
		assertNotBuild();
		this.projectId = projectId;
		return this;
	}

	public FactLineBuilder campaignId(final int campaignId)
	{
		assertNotBuild();
		this.campaignId = campaignId;
		return this;
	}

	public FactLineBuilder fromLocation(final Optional<LocationId> optionalLocationId)
	{
		optionalLocationId.ifPresent(locationId -> this.fromLocationId = locationId);
		return this;
	}

	public FactLineBuilder fromLocationOfBPartner(@Nullable final BPartnerLocationId bpartnerLocationId)
	{
		return fromLocation(getServices().getLocationId(bpartnerLocationId));
	}

	public FactLineBuilder fromLocationOfLocator(final int locatorRepoId)
	{
		return fromLocation(getServices().getLocationIdByLocatorRepoId(locatorRepoId));
	}

	public FactLineBuilder toLocation(final Optional<LocationId> optionalLocationId)
	{
		optionalLocationId.ifPresent(locationId -> this.toLocationId = locationId);
		return this;
	}

	public FactLineBuilder toLocationOfBPartner(@Nullable final BPartnerLocationId bpartnerLocationId)
	{
		return toLocation(getServices().getLocationId(bpartnerLocationId));
	}

	public FactLineBuilder toLocationOfLocator(final int locatorRepoId)
	{
		return toLocation(getServices().getLocationIdByLocatorRepoId(locatorRepoId));
	}

	public FactLineBuilder costElement(@Nullable final CostElementId costElementId)
	{
		this.costElementId = costElementId;
		return this;
	}

	public FactLineBuilder costElement(@Nullable final CostElement costElement)
	{
		return costElement(costElement != null ? costElement.getId() : null);
	}

	public FactLineBuilder additionalDescription(@Nullable final String additionalDescription)
	{
		this.additionalDescription = StringUtils.trimBlankToNull(additionalDescription);
		return this;
	}

	public FactLineBuilder openItemKey(@Nullable FAOpenItemTrxInfo openItemTrxInfo)
	{
		this.openItemTrxInfo = openItemTrxInfo;
		return this;
	}
}
