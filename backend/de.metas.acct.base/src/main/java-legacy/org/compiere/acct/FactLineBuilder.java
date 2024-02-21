package org.compiere.acct;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.PostingType;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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

@SuppressWarnings("UnusedReturnValue")
@ToString(exclude = "fact")
public final class FactLineBuilder
{
	private boolean built = false;

	private final Fact fact;
	private DocLine<?> docLine = null;
	private Integer subLineId = null;

	private MAccount account = null;

	private CurrencyId currencyId;
	@Nullable private CurrencyConversionContext currencyConversionCtx;
	@Nullable private BigDecimal amtSourceDr;
	@Nullable private BigDecimal amtSourceCr;
	@Nullable private BigDecimal amtAcctDr;
	@Nullable private BigDecimal amtAcctCr;

	private BigDecimal qty = null;
	private UomId uomId;

	private boolean alsoAddZeroLine = false;

	// Other dimensions
	private OrgId orgId;
	@Nullable private BPartnerId bpartnerId;
	@Nullable private TaxId C_Tax_ID;
	private Integer locatorId;
	private ActivityId activityId;

	private String additionalDescription = null;

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
		final MAccount account = getAccount();
		if (account == null)
		{
			throw new AdempiereException("No account for " + this);
		}

		//
		final Doc<?> doc = getDoc();
		final DocLine<?> docLine = getDocLine();
		final FactLine line = new FactLine(
				doc.get_Table_ID(), // AD_Table_ID
				doc.get_ID(), // Record_ID
				docLine == null ? 0 : docLine.get_ID()); // Line_ID

		// Set Document, Line, Sub Line
		line.setDocumentInfo(doc, docLine);
		final Integer subLine_ID = getSubLine_ID();
		if (subLine_ID != null)
		{
			line.setSubLine_ID(subLine_ID);
		}

		// Account
		line.setPostingType(getPostingType());
		line.setAccount(getAcctSchema(), account);

		//
		// Qty
		final BigDecimal qty = getQty();
		if (qty != null)
		{
			line.setQty(qty);
		}
		final UomId uomId = getUomId();
		if (uomId != null)
		{
			line.setC_UOM_ID(uomId.getRepoId());
		}

		//
		// Amounts - one needs to not zero
		final CurrencyId currencyId = getCurrencyId();
		final BigDecimal amtSourceDr = getAmtSourceDr();
		final BigDecimal amtSourceCr = getAmtSourceCr();
		line.setAmtSource(currencyId, amtSourceDr, amtSourceCr);
		if (line.isZeroAmtSource())
		{
			if (line.getQty().signum() == 0)
			{
				Fact.log.debug("Both amounts & qty = 0/Null - {}", this);
				// https://github.com/metasfresh/metasfresh/issues/4147 we might need the zero-line later
				if (!alsoAddZeroLine)
				{
					return null;
				}
			}

			if (Fact.log.isDebugEnabled())
			{
				Fact.log.debug("Both amounts = 0/Null, Qty=" + (docLine == null ? "<NULL>" : docLine.getQty()) + " - docLine=" + (docLine == null ? "<NULL>" : docLine) + " - " + toString());
			}
		}

		//
		// Currency convert
		final CurrencyConversionContext currencyConversionCtx = getCurrencyConversionCtx();
		if (currencyConversionCtx != null)
		{
			line.setCurrencyConversionCtx(currencyConversionCtx);
			line.addDescription(currencyConversionCtx.getSummary());
		}

		//
		// Optionally overwrite Acct Amount
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
			line.convert();
		}

		//
		// Set the other dimensions
		final Integer locatorId = getLocatorId();
		if (locatorId != null)
		{
			// NOTE: set locator before org because when locator is set, the org is reset.
			line.setM_Locator_ID(locatorId);
		}
		//
		final OrgId orgId = getOrgId();
		if (orgId != null)
		{
			line.setAD_Org_ID(orgId.getRepoId());
		}
		//
		final BPartnerId bpartnerId = getBpartnerId();
		if (bpartnerId != null)
		{
			line.setC_BPartner_ID(bpartnerId.getRepoId());
		}
		//
		final TaxId taxId = getC_Tax_ID();
		if (taxId != null)
		{
			line.setC_Tax_ID(taxId.getRepoId());
		}
		//
		final ActivityId activityId = getActivityId();
		if (activityId != null)
		{
			line.setC_Activity_ID(activityId.getRepoId());
		}

		if (additionalDescription != null)
		{
			line.addDescription(additionalDescription);
		}

		//
		Fact.log.debug("Built: {}", line);
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

	public FactLineBuilder setAccount(@NonNull final AccountId accountId)
	{
		final IAccountDAO accountsRepo = Services.get(IAccountDAO.class);
		return setAccount(accountsRepo.getById(Env.getCtx(), accountId));
	}

	public FactLineBuilder setAccount(final MAccount account)
	{
		assertNotBuild();
		this.account = account;
		return this;
	}

	private MAccount getAccount()
	{
		// TODO: check if we can enforce it all the time
		// Check.assumeNotNull(account, "account not null for {}", this);
		return account;
	}

	private Doc<?> getDoc()
	{
		return fact.m_doc;
	}

	public FactLineBuilder setDocLine(final DocLine<?> docLine)
	{
		assertNotBuild();
		this.docLine = docLine;
		return this;
	}

	private DocLine<?> getDocLine()
	{
		return docLine;
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

	private AcctSchema getAcctSchema()
	{
		return fact.getAcctSchema();
	}

	private PostingType getPostingType()
	{
		return fact.getPostingType();
	}

	public FactLineBuilder setQty(final BigDecimal qty)
	{
		assertNotBuild();
		this.qty = qty;
		return this;
	}

	public FactLineBuilder setQty(final Quantity qty)
	{
		assertNotBuild();
		this.qty = qty.toBigDecimal();
		this.uomId = qty.getUomId();
		return this;
	}

	private BigDecimal getQty()
	{
		return qty;
	}

	private UomId getUomId()
	{
		return uomId;
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

	public FactLineBuilder setAmtSource(@Nullable final Money amtSourceDr, @Nullable final Money amtSourceCr)
	{
		setAmtSource(
				Money.getCommonCurrencyIdOfAll(amtSourceDr, amtSourceCr),
				amtSourceDr != null ? amtSourceDr.toBigDecimal() : null,
				amtSourceCr != null ? amtSourceCr.toBigDecimal() : null);
		return this;
	}

	public FactLineBuilder setAmtAcct(@Nullable final Money amtAcctDr, @Nullable final Money amtAcctCr)
	{
		assertNotBuild();
		final CurrencyId currencyId = Money.getCommonCurrencyIdOfAll(amtAcctDr, amtAcctCr);
		Check.assumeEquals(currencyId, fact.getAcctCurrencyId(), "accounting currency");
		this.amtAcctDr = amtAcctDr != null ? amtAcctDr.toBigDecimal() : null;
		this.amtAcctCr = amtAcctCr != null ? amtAcctCr.toBigDecimal() : null;
		return this;
	}

	public FactLineBuilder setAmt(@Nullable final MoneySourceAndAcct debit, @Nullable final MoneySourceAndAcct credit)
	{
		final Money amtSourceDr = debit != null ? debit.getSource() : null;
		final Money amtSourceCr = credit != null ? credit.getSource() : null;
		final Money amtAcctDr = debit != null ? debit.getAcct() : null;
		final Money amtAcctCr = credit != null ? credit.getAcct() : null;

		setAmtSource(amtSourceDr, amtSourceCr);
		setAmtAcct(amtAcctDr, amtAcctCr);

		return this;
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

	private CurrencyId getCurrencyId()
	{
		return currencyId;
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
		return currencyConversionCtx;
	}

	@Nullable
	private BigDecimal getAmtSourceDr()
	{
		return amtSourceDr;
	}

	@Nullable
	private BigDecimal getAmtSourceCr()
	{
		return amtSourceCr;
	}

	/**
	 * Sets the AmtSourceDr (if amtSource is positive) or AmtSourceCr (if amtSource is negative).
	 */
	public FactLineBuilder setAmtSourceDrOrCr(final BigDecimal amtSource)
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

	private OrgId getOrgId()
	{
		return orgId;
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

	@Nullable
	private BPartnerId getBpartnerId()
	{
		return bpartnerId;
	}

	public FactLineBuilder setC_Tax_ID(final Integer taxId)
	{
		assertNotBuild();
		this.C_Tax_ID = taxId != null ? TaxId.ofRepoIdOrNull(taxId) : null;
		return this;
	}

	@Nullable
	private TaxId getC_Tax_ID()
	{
		return C_Tax_ID;
	}

	public FactLineBuilder locatorId(final int locatorId)
	{
		assertNotBuild();
		this.locatorId = locatorId;
		return this;
	}

	private Integer getLocatorId()
	{
		return locatorId;
	}

	public FactLineBuilder activityId(final ActivityId activityId)
	{
		assertNotBuild();
		this.activityId = activityId;
		return this;
	}

	private ActivityId getActivityId()
	{
		return activityId;
	}

	public FactLineBuilder additionalDescription(@Nullable final String additionalDescription)
	{
		assertNotBuild();
		this.additionalDescription = StringUtils.trimBlankToNull(additionalDescription);
		return this;
	}
}
