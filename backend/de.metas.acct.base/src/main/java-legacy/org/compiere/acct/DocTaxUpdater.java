package org.compiere.acct;

import de.metas.acct.accounts.AccountProvider;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.CurrencyPrecision;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.tax.api.CalculateTaxResult;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.HashMap;

class DocTaxUpdater
{
	@NonNull private final AcctDocRequiredServicesFacade services;
	@NonNull private final AccountProvider accountProvider;

	@NonNull private final PostingSign netAmtPostingSign;
	private final HashMap<TaxId, TaxAmounts> taxAmountsByTaxId = new HashMap<>();

	public DocTaxUpdater(
			@NonNull final AcctDocRequiredServicesFacade services,
			@NonNull final AccountProvider accountProvider,
			@NonNull final InvoiceDocBaseType docBaseType)
	{
		this.services = services;
		this.accountProvider = accountProvider;
		this.netAmtPostingSign = computeNetAmountPostingSign(docBaseType);
	}

	private static PostingSign computeNetAmountPostingSign(@NonNull final InvoiceDocBaseType docBaseType)
	{
		if (docBaseType.isSales())
		{
			if (docBaseType.isCreditMemo())
			{
				return PostingSign.DEBIT;
			}
			else
			{
				return PostingSign.CREDIT;
			}
		}
		else // purchase
		{
			if (docBaseType.isCreditMemo())
			{
				return PostingSign.CREDIT;
			}
			else
			{
				return PostingSign.DEBIT;
			}
		}
	}

	public void collect(@NonNull final Fact fact)
	{
		fact.forEach(this::collect);
	}

	public void collect(@Nullable final FactLine line)
	{
		if (line == null)
		{
			return;
		}

		final TaxId taxId = line.getTaxId();
		if (taxId == null)
		{
			return;
		}

		final Money taxBaseAmt = extractNetAmount(line);

		final TaxAmounts taxAmounts = taxAmountsByTaxId.computeIfAbsent(taxId, k -> newTaxAmounts(taxId, taxBaseAmt.getCurrencyId()));
		taxAmounts.addTaxBaseAmt(taxBaseAmt);
	}

	private TaxAmounts newTaxAmounts(@NonNull final TaxId taxId, @NonNull final CurrencyId currencyId)
	{
		final Tax tax = services.getTaxById(taxId);
		final CurrencyPrecision precision = services.getCurrencyStandardPrecision(currencyId);
		return new TaxAmounts(tax, currencyId, precision);
	}

	private Money extractNetAmount(final FactLine line)
	{
		return netAmtPostingSign.isDebit()
				? line.getAmtSourceDrAsMoney()
				: line.getAmtSourceCrAsMoney();
	}

	public void updateDocTaxes(final DocTaxesList taxes)
	{
		final HashMap<TaxId, TaxAmounts> allTaxAmountsToPropagate = new HashMap<>(this.taxAmountsByTaxId);

		taxes.mapEach(docTax -> {
			final TaxAmounts taxAmounts = allTaxAmountsToPropagate.remove(docTax.getTaxId());
			if (taxAmounts == null)
			{
				return null; // remove it
			}
			else
			{
				taxAmounts.updateDocTax(docTax);
				return docTax;
			}
		});

		for (final TaxAmounts taxAmounts : allTaxAmountsToPropagate.values())
		{
			taxes.add(toDocTax(taxAmounts));
		}
	}

	private DocTax toDocTax(@NonNull TaxAmounts taxAmounts)
	{
		return DocTax.builderFrom(taxAmounts.getTax())
				.accountProvider(accountProvider)
				.taxBaseAmt(taxAmounts.getTaxBaseAmt().toBigDecimal())
				.taxAmt(taxAmounts.getTaxAmt().toBigDecimal())
				.reverseChargeTaxAmt(taxAmounts.getReverseChargeAmt().toBigDecimal())
				.build();
	}

	//
	//
	//
	//
	//

	private static class TaxAmounts
	{
		@NonNull @Getter private final Tax tax;
		@NonNull private final CurrencyPrecision precision;
		@NonNull @Getter private Money taxBaseAmt;
		private boolean isTaxCalculated = false;
		private Money _taxAmt;
		private Money _reverseChargeAmt;

		public TaxAmounts(@NonNull final Tax tax, @NonNull final CurrencyId currencyId, @NonNull final CurrencyPrecision precision)
		{
			this.tax = tax;
			this.precision = precision;
			this.taxBaseAmt = this._taxAmt = this._reverseChargeAmt = Money.zero(currencyId);
		}

		public void addTaxBaseAmt(@NonNull final Money taxBaseAmtToAdd)
		{
			if (taxBaseAmtToAdd.isZero())
			{
				return;
			}

			this.taxBaseAmt = this.taxBaseAmt.add(taxBaseAmtToAdd);
			this.isTaxCalculated = false;
		}

		public Money getTaxAmt()
		{
			updateTaxAmtsIfNeeded();
			return _taxAmt;
		}

		public Money getReverseChargeAmt()
		{
			updateTaxAmtsIfNeeded();
			return _reverseChargeAmt;
		}

		private void updateTaxAmtsIfNeeded()
		{
			if (!isTaxCalculated)
			{
				final CalculateTaxResult result = tax.calculateTax(taxBaseAmt.toBigDecimal(), false, precision.toInt());
				this._taxAmt = Money.of(result.getTaxAmount(), taxBaseAmt.getCurrencyId());
				this._reverseChargeAmt = Money.of(result.getReverseChargeAmt(), taxBaseAmt.getCurrencyId());
				this.isTaxCalculated = true;
			}
		}

		public void updateDocTax(@NonNull final DocTax docTax)
		{
			if (!TaxId.equals(tax.getTaxId(), docTax.getTaxId()))
			{
				throw new AdempiereException("TaxId not matching: " + this + ", " + docTax);
			}

			docTax.setTaxBaseAmt(getTaxBaseAmt().toBigDecimal());
			docTax.setTaxAmt(getTaxAmt().toBigDecimal());
			docTax.setReverseChargeTaxAmt(getReverseChargeAmt().toBigDecimal());
		}
	}
}
