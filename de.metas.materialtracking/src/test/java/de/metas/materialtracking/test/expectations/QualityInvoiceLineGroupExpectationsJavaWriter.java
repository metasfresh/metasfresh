package de.metas.materialtracking.test.expectations;

/*
 * #%L
 * de.metas.materialtracking
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


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.text.IndentedStringBuilder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLine;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup;
import de.metas.pricing.IPricingResult;

/**
 * Helper class used to write Java code that creates {@link QualityInvoiceLineGroupExpectations} for given {@link IQualityInvoiceLineGroup}s.
 * 
 * See {@link #writeJava(List)}.
 * 
 * @author tsa
 *
 */
public class QualityInvoiceLineGroupExpectationsJavaWriter
{
	private final IndentedStringBuilder out;

	public QualityInvoiceLineGroupExpectationsJavaWriter()
	{
		super();
		this.out = new IndentedStringBuilder();
	}

	@Override
	public String toString()
	{
		return out.toString();
	}

	/**
	 * Creates {@link QualityInvoiceLineGroupExpectations}.
	 * 
	 * @param groups
	 */
	public void writeJava(final List<IQualityInvoiceLineGroup> groups)
	{
		out.appendLine("//@formatter:off");
		
		final String classname = QualityInvoiceLineGroupExpectations.class.getSimpleName();
		out.appendLine("return new " + classname + "()");

		out.incrementIndent();

		int groupNo = 0;
		for (final IQualityInvoiceLineGroup group : groups)
		{
			groupNo++;
			final String groupName = "Group " + groupNo + " (" + group.getQualityInvoiceLineGroupType() + ")";

			out.appendLine("//");
			out.appendLine("// " + groupName);
			out.appendLine(".newExpectation()");
			out.incrementIndent();

			appendMethodCall("expectationName", groupName);
			writeJava(groupName, group);

			out.decrementIndent();
		}
		out.appendLine(";");

		out.decrementIndent();

		out.appendLine("//@formatter:on");
	}

	/**
	 * Creates {@link QualityInvoiceLineGroupExpectation}
	 * 
	 * @param groupName
	 * @param group
	 */
	private void writeJava(final String groupName, final IQualityInvoiceLineGroup group)
	{
		appendMethodCall("type", group.getQualityInvoiceLineGroupType());

		//
		// Invoiceable Line
		final IQualityInvoiceLine invoiceableLine = group.getInvoiceableLine();
		if (invoiceableLine != null)
		{
			out.appendLine(".invoiceableLineExpectation()");
			final String expectationName = groupName + " - invoiceable line";
			writeJavaBody(expectationName, invoiceableLine);
		}

		//
		// Before Details
		{
			int detailNo = 0;
			for (IQualityInvoiceLine detail : group.getDetailsBefore())
			{
				detailNo++;

				out.appendLine(".newBeforeDetailExpectation()");

				final String expectationName = groupName + " - before detail #" + detailNo;
				writeJavaBody(expectationName, detail);
			}
		}

		//
		// After Details
		{
			int detailNo = 0;
			for (IQualityInvoiceLine detail : group.getDetailsAfter())
			{
				detailNo++;

				out.appendLine(".newAfterDetailExpectation()");

				final String expectationName = groupName + " - after detail #" + detailNo;
				writeJavaBody(expectationName, detail);
			}
		}

		out.appendLine(".endExpectation()");
	}

	/**
	 * Creates {@link QualityInvoiceLineExpectation}
	 * 
	 * @param expectationName
	 * @param invoiceableLine
	 */
	private void writeJavaBody(String expectationName, final IQualityInvoiceLine invoiceableLine)
	{
		out.incrementIndent();

		appendMethodCall("expectationName", expectationName);

		appendMethodCall("printed", invoiceableLine.isDisplayed());
		appendMethodCall("product", invoiceableLine.getM_Product());
		appendMethodCall("productName", invoiceableLine.getProductName());
		appendMethodCall("percentage", invoiceableLine.getPercentage());
		appendMethodCall("qty", invoiceableLine.getQty());
		appendMethodCall("uom", invoiceableLine.getC_UOM());
		appendMethodCall("displayed", invoiceableLine.isDisplayed());
		appendMethodCall("description", invoiceableLine.getDescription());

		final IPricingResult pricingResult = invoiceableLine.getPrice();
		final BigDecimal priceStd = pricingResult == null ? null : pricingResult.getPriceStd();
		appendMethodCall("price", priceStd);

		out.appendLine(".endExpectation()");
		out.decrementIndent();
	}

	private final void appendMethodCall(final String methodName, final String param)
	{
		final String paramJava = toJavaString(param);
		appendMethodCallWithJavaParam(methodName, paramJava);
	}

	private final void appendMethodCall(final String methodName, final boolean param)
	{
		final String paramJava = param ? "true" : "false";
		appendMethodCallWithJavaParam(methodName, paramJava);
	}

	private final void appendMethodCall(final String methodName, final BigDecimal param)
	{
		final String paramJava;
		if (param == null)
		{
			paramJava = "null";
		}
		else
		{
			paramJava = "new BigDecimal(" + toJavaString(param.toString()) + ")";
		}
		appendMethodCallWithJavaParam(methodName, paramJava);
	}

	private final void appendMethodCallWithJavaParam(final String methodName, final String paramJava)
	{
		out.appendLine("." + methodName + "(" + paramJava + ")");
	}

	private final void appendMethodCall(final String methodName, final I_M_Product product)
	{
		if (product == null)
		{
			appendMethodCallWithJavaParam(methodName, "null");
		}
		else
		{
			final String productValue = product.getValue();
			final String productJavaName = "p" + productValue;
			appendMethodCallWithJavaParam(methodName, productJavaName);
		}
	}

	private final void appendMethodCall(final String methodName, final I_C_UOM uom)
	{
		if (uom == null)
		{
			appendMethodCallWithJavaParam(methodName, "null");
		}
		else
		{
			final String uomSymbol = uom.getUOMSymbol();
			final String uomJavaName = "uom" + uomSymbol;
			appendMethodCallWithJavaParam(methodName, uomJavaName);
		}
	}

	private final void appendMethodCall(final String methodName, final Enum<?> type)
	{
		if (type == null)
		{
			appendMethodCallWithJavaParam(methodName, "null");
		}
		else
		{
			String paramJava = type.getClass().getSimpleName() + "." + type.name();
			appendMethodCallWithJavaParam(methodName, paramJava);
		}
	}

	private String toJavaString(final String str)
	{
		if (str == null)
		{
			return "null";
		}

		// TODO: escape chars
		return "\"" + str + "\"";
	}
}
