package de.metas.invoicecandidate.api.impl.aggregationEngine;

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

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.aggregation.model.C_Aggregation_Builder;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.aggregation.model.X_C_AggregationItem;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.expectations.InvoiceLineAttributeExpectations;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Test case:
 * <ul>
 * <li>one invoice candidate
 * <li>two receipts
 * <li>we use a line aggregation key builder which is aggregating on {@link I_C_Invoice_Candidate#COLUMN_C_Invoice_Candidate_ID}.
 * </ul>
 *
 * Expectation: we expect only one invoice line to be generated (and NOT two, one for each receipt)
 *
 * @task 08489
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class TestTwoReceiptsOneInvoiceLine_LineAggregationPerIC extends AbstractTwoInOutsTests
{
	protected I_M_Attribute attribute1;
	protected static final String ATTRIBUTE_ValueString1 = "AttValue-1";
	protected static final String ATTRIBUTE_ValueString2 = "AttValue-2";

	/** {@link IInvoiceLineAttribute}s expectations for all lines which are coming from iol11 */
	protected InvoiceLineAttributeExpectations<Object> iol11_attributeExpectations;
	/** {@link IInvoiceLineAttribute}s expectations for all lines which are coming from iol21 */
	protected InvoiceLineAttributeExpectations<Object> iol21_attributeExpectations;

	private I_C_Aggregation lineAggregation_StandardFields;
	private I_C_Aggregation lineAggregation_PerInvoiceCandidate;

	@Override
	public void init()
	{
		super.init();

		//
		// Setup M_Attribute(s)
		{
			this.attribute1 = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Attribute.class, ITrx.TRXNAME_None);
			attribute1.setValue("Attribute1");
			attribute1.setName(attribute1.getValue());
			attribute1.setIsAttrDocumentRelevant(true);
			InterfaceWrapperHelper.save(attribute1);
		}

		//
		// Setup C_Aggregation(s)
		{
			//@formatter:off
			lineAggregation_StandardFields = new C_Aggregation_Builder()
				.setAD_Table_ID(I_C_Invoice_Candidate.Table_Name)
				.setIsDefault(false)
				.setAggregationUsageLevel(X_C_Aggregation.AGGREGATIONUSAGELEVEL_Line)
				.setName("Invoice Line Standard Fields") // C_Aggregation_ID=540003
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID)
					.end()
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_C_Charge_ID)
					.end()
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_PriceActual)
					.end()
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID)
					.end()
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_C_UOM_ID)
					.end()
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_IsPrinted)
					.end()
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_Line)
					.end()
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID)
					.end()
				.build();
			//@formatter:on

			//@formatter:off
			// NOTE: keep in sync with "Per invoice candidate or packing material" (C_Aggregation_ID=540002)
			lineAggregation_PerInvoiceCandidate = new C_Aggregation_Builder()
				.setAD_Table_ID(I_C_Invoice_Candidate.Table_Name)
				.setIsDefault(true)
				.setAggregationUsageLevel(X_C_Aggregation.AGGREGATIONUSAGELEVEL_Line)
				.setName("Per invoice candidate or packing material")
				// Aggregate on C_Invoice_Candidate_ID if the line is not about packaging materials
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
					.setIncludeLogic("@"+I_C_Invoice_Candidate.COLUMNNAME_IsPackagingMaterial+"@=N")
					.end()
				// Aggregate on M_Product_ID if the line is about packaging materials
				.newItem()
					.setType(X_C_AggregationItem.TYPE_Column)
					.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID)
					.setIncludeLogic("@"+I_C_Invoice_Candidate.COLUMNNAME_IsPackagingMaterial+"@=Y")
					.end()
				// Include standard invoice line fields
				.newItem()
					.setType(X_C_AggregationItem.TYPE_IncludedAggregation)
					.setIncluded_Aggregation(lineAggregation_StandardFields)
					.end()
				//
				.build();
			//@formatter:on

		}
	}

	@Override
	protected boolean config_IsSOTrx()
	{
		return false; // purchase
	}

	@Override
	protected BigDecimal config_GetPriceEntered_Override()
	{
		return null;
	}

	@Override
	protected List<I_M_InOutLine> step_createInOutLines(List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final List<I_M_InOutLine> inoutLines = super.step_createInOutLines(invoiceCandidates);

		// iol11: set Attribute1=Value1
		{
			Assert.assertSame(iol11, inoutLines.get(0));

			//@formatter:off
			iol11_attributeExpectations = InvoiceLineAttributeExpectations.newExpectation()
					.newAttributeExpectation()
						.attribute(attribute1)
						.valueString(ATTRIBUTE_ValueString1)
					.endExpectation();
			//@formatter:on
			final I_M_AttributeSetInstance asi1 = iol11_attributeExpectations.createM_AttributeSetInstance(iol11);
			iol11.setM_AttributeSetInstance(asi1);
			InterfaceWrapperHelper.save(iol11);
		}

		// iol21: set Attribute1=Value2
		{
			Assert.assertSame(iol21, inoutLines.get(1));

			//@formatter:off
			iol21_attributeExpectations = InvoiceLineAttributeExpectations.newExpectation()
					.newAttributeExpectation()
						.attribute(attribute1)
						.valueString(ATTRIBUTE_ValueString2)
					.endExpectation();
			//@formatter:on
			final I_M_AttributeSetInstance asi1 = iol21_attributeExpectations.createM_AttributeSetInstance(iol21);
			iol21.setM_AttributeSetInstance(asi1);
			InterfaceWrapperHelper.save(iol21);
		}

		return inoutLines;
	}

	@Override
	protected void step_validate_before_aggregation(java.util.@NonNull List<I_C_Invoice_Candidate> invoiceCandidates, java.util.@NonNull List<I_M_InOutLine> inOutLines)
	{
		super.step_validate_before_aggregation(invoiceCandidates, inOutLines);

		final I_C_Invoice_Candidate invoiceCandidate = CollectionUtils.singleElement(invoiceCandidates);

		// Make sure our line aggregation builder was used
		assertEquals(lineAggregation_PerInvoiceCandidate.getC_Aggregation_ID(), invoiceCandidate.getLineAggregationKeyBuilder_ID());
	}

	@Override
	protected void step_validate_after_aggregation(List<I_C_Invoice_Candidate> invoiceCandidates, List<I_M_InOutLine> inOutLines, List<IInvoiceHeader> invoices)
	{
		final IInvoiceHeader invoice = CollectionUtils.singleElement(invoices);
		final IInvoiceCandAggregate invoiceLineAggregate = CollectionUtils.singleElement(invoice.getLines());
		final IInvoiceLineRW invoiceLine = CollectionUtils.singleElement(invoiceLineAggregate.getAllLines());

		// Assert we invoiced all inout lines
		final BigDecimal qtyToInvoice_Expected = partialQty1_32.add(partialQty2_8).add(partialQty3_4);
		assertThat(invoiceLine.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(qtyToInvoice_Expected));

		// Make sure attributes were not aggregated
		assertThat(invoiceLine.getInvoiceLineAttributes(), empty());
	}
}
