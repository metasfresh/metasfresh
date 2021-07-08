package de.metas.invoicecandidate.agg.key.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;

import de.metas.aggregation.api.AbstractAggregationKeyBuilder;
import de.metas.aggregation.api.AggregationAttribute;
import de.metas.aggregation.api.AggregationId;
import de.metas.aggregation.api.AggregationKey;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.Language;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.impl.AggregationKeyEvaluationContext;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_M_ProductGroup;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.money.CurrencyId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductPrice;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;

/**
 * Default LineAggregationKey builder for {@link I_C_Invoice_Candidate}s.
 *
 * @author tsa
 *
 */
public class ICLineAggregationKeyBuilder_OLD extends AbstractAggregationKeyBuilder<I_C_Invoice_Candidate>
{
	public static final transient ICLineAggregationKeyBuilder_OLD instance = new ICLineAggregationKeyBuilder_OLD();

	private static final List<String> columnNames = ImmutableList.<String> builder()
			.add(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_Agg_ID)
			.add(I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID)
			.add(I_C_Invoice_Candidate.COLUMNNAME_C_Charge_ID)
			.add(I_C_Invoice_Candidate.COLUMNNAME_IsManual)
			.add(I_C_Invoice_Candidate.COLUMNNAME_Description)
			.add(I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID)
			.add(I_C_Invoice_Candidate.COLUMNNAME_PriceActual)
			.add(I_C_Invoice_Candidate.COLUMNNAME_PriceActual_Override)
			.add(I_C_Invoice_Candidate.COLUMNNAME_C_UOM_ID)
			.add(I_C_Invoice_Candidate.COLUMNNAME_IsPrinted)
			.add(I_C_Invoice_Candidate.COLUMNNAME_Line)
			.build();

	private ICLineAggregationKeyBuilder_OLD()
	{
		super();
	}

	@Override
	public String buildKey(final I_C_Invoice_Candidate ic)
	{
		return buildAggregationKey(ic)
				.getAggregationKeyString();
	}

	@Override
	public AggregationKey buildAggregationKey(final I_C_Invoice_Candidate ic)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final I_C_Invoice_Candidate_Agg agg = ic.getC_Invoice_Candidate_Agg();
		Check.assumeNotNull(agg, "invoice candidate aggregation not null for {}", ic);

		final StringBuilder sb = new StringBuilder();

		if (agg.getM_ProductGroup_ID() > 0)
		{
			final I_M_ProductGroup productGroupgrcord = agg.getM_ProductGroup();
			final I_M_Product productProxyRecord = loadOutOfTrx(productGroupgrcord.getM_Product_Proxy_ID(), I_M_Product.class);

			// NOTE: the only reason why we add all these strings instead of just adding agg.getC_Invoice_Candidate_Agg_ID() is because we want an user friendly string
			sb.append(agg.getName());
			sb.append("_").append(agg.getSeqNo());
			sb.append("_").append(agg.getAD_Org_ID());
			sb.append("_").append(productGroupgrcord.getName());
			sb.append("_").append(productProxyRecord.getValue());
		}
		else
		{
			if (ic.getM_Product_ID() > 0)
			{
				final I_M_Product product = productDAO.getById(ic.getM_Product_ID());
				sb.append(product.getValue());
			}
			else if (ic.getC_Charge_ID() > 0)
			{
				sb.append("C_Charge_ID=" + ic.getC_Charge_ID());
			}
			else
			{
				Check.assume(ic.isManual(), ic + " has neither a M_Product_ID nor a C_Charge_ID and neither is manual");
				sb.append(ManualCandidateHandler.MANUAL);
			}
		}

		// 03439: if a candidate has a description, then we will only aggregate it with candidates that have the same description line
		if (!Check.isEmpty(ic.getDescription(), true))
		{
			sb.append(ic.getDescription());
		}

		final NumberFormat numberFormat = createCurrencyNumberFormat(ic);

		final ProductPrice priceActual = invoiceCandBL.getPriceActual(ic);
		final BigDecimal priceActualAmt = priceActual.toMoney().toBigDecimal();

		sb.append("/" + numberFormat.format(priceActualAmt));
		sb.append("/" + NumberUtils.stripTrailingDecimalZeros(priceActualAmt));

		//
		// 06718: Use UOM in aggregation
		if (ic.getC_UOM_ID() > 0)
		{
			final I_C_UOM uom = uomDAO.getById(ic.getC_UOM_ID());
			final String uomName = uom.getName(); // Unique
			sb.append("/" + uomName);
		}

		//
		// 07442
		// Also add activity and tax
		sb.append("/" + ic.getC_Activity_ID());

		// Add Tax
		final I_C_Tax taxEffective = invoiceCandBL.getTaxEffective(ic);
		sb.append("/" + taxEffective.getC_Tax_ID());

		// Add IsPrinted
		sb.append("/").append(ic.isPrinted());

		// Add LineNo
		final int lineNo = ic.getLine();
		sb.append("/").append(lineNo);

		//
		// Add InvoiceLineAttributes
		{
			final AggregationAttribute attribute = new AggregationAttribute(AggregationKeyEvaluationContext.ATTRIBUTE_CODE_AggregatePer_ProductAttributes);
			final Evaluatee ctx = null; // does not matter because it is actually not used
			final Object value = attribute.evaluate(ctx);
			sb.append("#").append(value);
		}

		//
		// Per C_Invoice_Candidate_ID
		// task 08543: *do not* aggregate different ICs into one invoice line. This includes "Gebinde/Packagingmaterial!"
		// background: the user expects the invoice candidates to be transformed into invoice lines one-by-one.
		sb.append("#").append(ic.getC_Invoice_Candidate_ID());

		//
		// Sales iols from different inOuts shall go into different invoice lines
		if (ic.isSOTrx())
		{
			final AggregationAttribute attribute = new AggregationAttribute(AggregationKeyEvaluationContext.ATTRIBUTE_CODE_AggregatePer_M_InOut_ID);
			final Evaluatee ctx = null; // does not matter because it is actually not used
			final Object value = attribute.evaluate(ctx);
			sb.append("#").append(value);
		}

		final AggregationId aggregationId = null;
		return new AggregationKey(sb.toString(), aggregationId);
	}

	/**
	 * Creates a {@link NumberFormat} instance to be used to format amounts, using Bill BPartner's locale and invoice candidate currency.
	 *
	 * @param ic
	 * @return
	 */
	private NumberFormat createCurrencyNumberFormat(final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

		final I_C_BPartner_Location billBPLocation = bpartnerDAO.getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(ic.getBill_BPartner_ID(), ic.getBill_Location_ID()));
		Check.assumeNotNull(billBPLocation, "billBPLocation not null for {}", ic);

		// We use the language of the bill location to determine the number format.
		// using the ic's context's language make no sense, because it basically amounts to the login language and can change a lot.
		final String langInfo = billBPLocation.getC_Location().getC_Country().getAD_Language();
		final Locale locale = Language.getLanguage(langInfo).getLocale();
		final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(ic.getC_Currency_ID());
		if (currencyId != null)
		{
			final CurrencyCode currencyCode = currencyDAO.getCurrencyCodeById(currencyId);
			numberFormat.setCurrency(Currency.getInstance(currencyCode.toThreeLetterCode()));
		}

		return numberFormat;
	}

	@Override
	public boolean isSame(final I_C_Invoice_Candidate model1, final I_C_Invoice_Candidate model2)
	{
		final String aggregationKey1 = buildKey(model1);
		final String aggregationKey2 = buildKey(model2);

		return Objects.equals(aggregationKey1, aggregationKey2);
	}

	@Override
	public String getTableName()
	{
		return I_C_Invoice_Candidate.Table_Name;
	}

	@Override
	public List<String> getDependsOnColumnNames()
	{
		return columnNames;
	}
}
