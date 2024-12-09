package org.compiere.acct;

<<<<<<< HEAD
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
=======
import de.metas.acct.Account;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.gljournal_sap.PostingSign;
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
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.MAccount;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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

<<<<<<< HEAD
@SuppressWarnings("UnusedReturnValue")
@ToString(exclude = "fact")
public final class FactLineBuilder
{
	private boolean built = false;

	private final Fact fact;
	private DocLine<?> docLine = null;
	private Integer subLineId = null;

	private MAccount account = null;
=======
@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
@ToString(exclude = "fact", doNotUseGetters = true)
public final class FactLineBuilder
{
	private static final Logger log = Fact.log;
	private final Fact fact;
	private boolean built = false;
	private DocLine<?> docLine = null;
	private int lineId = 0;
	private Integer subLineId = null;

	@Nullable private ElementValueId elementValueId;
	@Nullable private MAccount validCombination;
	@Nullable private AccountConceptualName accountConceptualName;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	private CurrencyId currencyId;
	@Nullable private CurrencyConversionContext currencyConversionCtx;
	@Nullable private BigDecimal amtSourceDr;
	@Nullable private BigDecimal amtSourceCr;
	@Nullable private BigDecimal amtAcctDr;
	@Nullable private BigDecimal amtAcctCr;

<<<<<<< HEAD
	private BigDecimal qty = null;
	private UomId uomId;
=======
	@Nullable private Optional<ProductId> productId;
	@Nullable private Quantity qty = null;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	private boolean alsoAddZeroLine = false;

	// Other dimensions
	private OrgId orgId;
<<<<<<< HEAD
	@Nullable private BPartnerId bpartnerId;
	@Nullable private TaxId C_Tax_ID;
	private Integer locatorId;
	private ActivityId activityId;

	private String additionalDescription = null;

=======
	private OrgId orgTrxId;
	@Nullable private BPartnerId bpartnerId;
	@Nullable private BPartnerLocationId bPartnerLocationId;
	@Nullable private TaxId C_Tax_ID;
	@Nullable private Integer locatorId;
	@Nullable private Optional<ActivityId> activityId;
	@Nullable private ProjectId projectId;
	private int campaignId;
	@Nullable private LocationId fromLocationId;
	@Nullable private LocationId toLocationId;
	@Nullable private CostElementId costElementId;
	@Nullable private Optional<OrderId> salesOrderId;
	@Nullable private Optional<String> userElementString1;

	private Optional<String> description = null;
	private String additionalDescription = null;

	private FAOpenItemTrxInfo openItemTrxInfo;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	FactLineBuilder(@NonNull final Fact fact)
	{
		this.fact = fact;
	}

<<<<<<< HEAD
=======
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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
=======
	@NonNull
	public FactLine buildAndAddNotNull()
	{
		final FactLine factLine = buildAndAdd();
		if (factLine == null)
		{
			throw new AdempiereException("Expected fact line to be added but it wasn't: " + this);
		}
		return factLine;
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Nullable
	private FactLine build()
	{
		markAsBuilt();

		// Data Check
<<<<<<< HEAD
		final MAccount account = getAccount();
		if (account == null)
=======
		if (elementValueId == null)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			throw new AdempiereException("No account for " + this);
		}

		//
<<<<<<< HEAD
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
=======
		final Doc<?> doc = fact.m_doc;
		final AcctDocRequiredServicesFacade services = doc.getServices();
		final FactLine line = FactLine.builder()
				.services(services)
				.doc(doc)
				.docLine(docLine)
				.docRecordRef(doc.getRecordRef())
				.Line_ID(lineId)
				.SubLine_ID(getSubLine_ID())
				.postingType(fact.getPostingType())
				.acctSchema(fact.getAcctSchema())
				.accountId(elementValueId)
				.account(validCombination)
				.accountConceptualName(accountConceptualName)
				.productId(productId)
				.qty(qty)
				.orgTrxId(orgTrxId)
				.M_Locator_ID(locatorId)
				.projectId(projectId)
				.activityId(activityId)
				.description(description)
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
=======
			final CurrencyConversionContext currencyConversionCtx = getCurrencyConversionCtx();
			if (currencyConversionCtx != null)
			{
				line.setCurrencyConversionCtx(currencyConversionCtx);
				line.addDescription(currencyConversionCtx.getSummary());
			}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			line.convert();
		}

		//
		// Set the other dimensions
<<<<<<< HEAD
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
=======
		if (orgId != null)
		{
			line.setAD_Org_ID(orgId);
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
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
=======
	@NonNull
	public FactLineBuilder setAccount(@NonNull final Account account)
	{
		assertNotBuild();
		this.validCombination = getServices().getAccountById(account.getAccountId());
		this.elementValueId = validCombination.getElementValueId();
		this.accountConceptualName = account.getAccountConceptualName();
		return this;
	}

	@NonNull
	public FactLineBuilder setAccount(@NonNull final ElementValueId elementValueId)
	{
		assertNotBuild();
		this.elementValueId = elementValueId;
		this.validCombination = null;
		return this;
	}

	private AcctDocRequiredServicesFacade getServices()
	{
		return fact.services;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public FactLineBuilder setDocLine(final DocLine<?> docLine)
	{
		assertNotBuild();
		this.docLine = docLine;
<<<<<<< HEAD
		return this;
	}

	private DocLine<?> getDocLine()
	{
		return docLine;
	}

	public FactLineBuilder setSubLine_ID(final int subLineId)
	{
		this.subLineId = subLineId;
=======
		this.lineId = docLine != null ? docLine.get_ID() : 0;
		return this;
	}

	public FactLineBuilder lineId(final int lineId)
	{
		assertNotBuild();
		this.lineId = lineId;
		return this;
	}

	public FactLineBuilder lineId(@Nullable final RepoIdAware lineId)
	{
		assertNotBuild();
		this.lineId = lineId != null ? lineId.getRepoId() : 0;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return this;
	}

	private Integer getSubLine_ID()
	{
		return subLineId;
	}

<<<<<<< HEAD
	private AcctSchema getAcctSchema()
	{
		return fact.getAcctSchema();
	}

	private PostingType getPostingType()
	{
		return fact.getPostingType();
	}

	public FactLineBuilder setQty(final BigDecimal qty)
=======
	public FactLineBuilder setSubLine_ID(final int subLineId)
	{
		assertNotBuild();
		this.subLineId = subLineId;
		return this;
	}

	private CurrencyId getAcctCurrencyId()
	{
		return fact.getAcctSchema().getCurrencyId();
	}

	public FactLineBuilder setQty(@NonNull final Quantity qty)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		assertNotBuild();
		this.qty = qty;
		return this;
	}

<<<<<<< HEAD
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

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	public FactLineBuilder setAmtSource(@Nullable final Money amtSourceDr, @Nullable final Money amtSourceCr)
	{
		setAmtSource(
				Money.getCommonCurrencyIdOfAll(amtSourceDr, amtSourceCr),
				amtSourceDr != null ? amtSourceDr.toBigDecimal() : null,
				amtSourceCr != null ? amtSourceCr.toBigDecimal() : null);
=======
	public FactLineBuilder setAmtAcct(@Nullable final BigDecimal amtAcctDr, @Nullable final BigDecimal amtAcctCr)
	{
		assertNotBuild();
		this.amtAcctDr = amtAcctDr;
		this.amtAcctCr = amtAcctCr;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return this;
	}

	public FactLineBuilder setAmtAcct(@Nullable final Money amtAcctDr, @Nullable final Money amtAcctCr)
	{
		assertNotBuild();
<<<<<<< HEAD
		final CurrencyId currencyId = Money.getCommonCurrencyIdOfAll(amtAcctDr, amtAcctCr);
		Check.assumeEquals(currencyId, fact.getAcctCurrencyId(), "accounting currency");
		this.amtAcctDr = amtAcctDr != null ? amtAcctDr.toBigDecimal() : null;
		this.amtAcctCr = amtAcctCr != null ? amtAcctCr.toBigDecimal() : null;
=======

		final CurrencyId acctCurrencyId = fact.getAcctSchema().getCurrencyId();
		setAmtAcct(
				amtAcctDr != null ? amtAcctDr.assertCurrencyId(acctCurrencyId).toBigDecimal() : null,
				amtAcctCr != null ? amtAcctCr.assertCurrencyId(acctCurrencyId).toBigDecimal() : null);

		return this;
	}

	public FactLineBuilder setAmtAcct(@NonNull final PostingSign postingSign, @NonNull Money amount)
	{
		if (postingSign.isDebit())
		{
			setAmtAcct(amount, null);
		}
		else
		{
			Check.assume(postingSign.isCredit(), "PostingType shall be Debit or Credit");
			setAmtAcct((Money)null, amount);
		}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
=======
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

	public FactLineBuilder setAmtSource(@NonNull final PostingSign postingSign, @NonNull Money amount)
	{
		if (postingSign.isDebit())
		{
			setAmtSource(amount, null);
		}
		else
		{
			Check.assume(postingSign.isCredit(), "PostingType shall be Debit or Credit");
			setAmtSource((Money)null, amount);
		}

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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/**
	 * Usually the {@link #buildAndAdd()} method ignores fact lines that have zero/null source amount and zero/null qty.
	 * Invoke this builder method still have the builder add them.
	 */
	public FactLineBuilder alsoAddZeroLine()
	{
		alsoAddZeroLine = true;
		return this;
	}

<<<<<<< HEAD
=======
	public FactLineBuilder alsoAddZeroLineIf(final boolean cond)
	{
		if (cond)
		{
			alsoAddZeroLine();
		}
		return this;
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public FactLineBuilder setCurrencyId(final CurrencyId currencyId)
	{
		assertNotBuild();
		this.currencyId = currencyId;
		return this;
	}

<<<<<<< HEAD
	private CurrencyId getCurrencyId()
	{
		return currencyId;
=======
	@Nullable
	private CurrencyConversionContext getCurrencyConversionCtx()
	{
		if (currencyConversionCtx != null)
		{
			return currencyConversionCtx;
		}

		return fact.getCurrencyConversionContext();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public FactLineBuilder setCurrencyConversionCtx(@Nullable final CurrencyConversionContext currencyConversionCtx)
	{
		assertNotBuild();
		this.currencyConversionCtx = currencyConversionCtx;
		return this;
	}

	@Nullable
<<<<<<< HEAD
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
=======
	private Money getAmtSourceDr()
	{
		return amtSourceDr != null ? Money.of(amtSourceDr, currencyId) : null;
	}

	@Nullable
	private Money getAmtSourceCr()
	{
		return amtSourceCr != null ? Money.of(amtSourceCr, currencyId) : null;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * Sets the AmtSourceDr (if amtSource is positive) or AmtSourceCr (if amtSource is negative).
	 */
<<<<<<< HEAD
	public FactLineBuilder setAmtSourceDrOrCr(final BigDecimal amtSource)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	private OrgId getOrgId()
	{
		return orgId;
=======
	public FactLineBuilder orgTrxId(final OrgId orgTrxId)
	{
		assertNotBuild();
		this.orgTrxId = orgTrxId;
		return this;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	@Nullable
	private BPartnerId getBpartnerId()
	{
		return bpartnerId;
=======
	@NonNull
	public FactLineBuilder bPartnerAndLocationId(
			@Nullable final BPartnerId bPartnerId,
			@Nullable final BPartnerLocationId bPartnerLocationId)
	{
		assertNotBuild();
		this.bpartnerId = bPartnerId;
		this.bPartnerLocationId = bPartnerLocationId;
		return this;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public FactLineBuilder setC_Tax_ID(final Integer taxId)
	{
		assertNotBuild();
		this.C_Tax_ID = taxId != null ? TaxId.ofRepoIdOrNull(taxId) : null;
		return this;
	}

<<<<<<< HEAD
	@Nullable
	private TaxId getC_Tax_ID()
	{
		return C_Tax_ID;
=======
	public FactLineBuilder setC_Tax_ID(@Nullable final TaxId taxId)
	{
		assertNotBuild();
		this.C_Tax_ID = taxId;
		return this;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public FactLineBuilder locatorId(final int locatorId)
	{
		assertNotBuild();
		this.locatorId = locatorId;
		return this;
	}

<<<<<<< HEAD
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
=======
	public FactLineBuilder locatorId(@Nullable final LocatorId locatorId)
	{
		assertNotBuild();
		this.locatorId = locatorId != null ? locatorId.getRepoId() : null;
		return this;
	}

	public FactLineBuilder activityId(@Nullable final ActivityId activityId)
	{
		assertNotBuild();
		this.activityId = Optional.ofNullable(activityId);
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

	public FactLineBuilder fromLocationOfLocator(@Nullable final LocatorId locatorId)
	{
		return fromLocationOfLocator(locatorId != null ? locatorId.getRepoId() : -1);
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
		assertNotBuild();
		this.costElementId = costElementId;
		return this;
	}

	public FactLineBuilder costElement(@Nullable final CostElement costElement)
	{
		return costElement(costElement != null ? costElement.getId() : null);
	}

	public FactLineBuilder description(@Nullable final String description)
	{
		assertNotBuild();
		this.description = StringUtils.trimBlankToOptional(description);
		return this;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public FactLineBuilder additionalDescription(@Nullable final String additionalDescription)
	{
		assertNotBuild();
		this.additionalDescription = StringUtils.trimBlankToNull(additionalDescription);
		return this;
	}
<<<<<<< HEAD
=======

	public FactLineBuilder openItemKey(@Nullable FAOpenItemTrxInfo openItemTrxInfo)
	{
		assertNotBuild();
		this.openItemTrxInfo = openItemTrxInfo;
		return this;
	}

	public FactLineBuilder productId(@Nullable ProductId productId)
	{
		assertNotBuild();
		this.productId = Optional.ofNullable(productId);
		return this;
	}

	public FactLineBuilder userElementString1(@Nullable final String userElementString1)
	{
		assertNotBuild();
		this.userElementString1 = StringUtils.trimBlankToOptional(userElementString1);
		return this;
	}

	public FactLineBuilder salesOrderId(@Nullable final OrderId salesOrderId)
	{
		assertNotBuild();
		this.salesOrderId = Optional.ofNullable(salesOrderId);
		return this;
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
