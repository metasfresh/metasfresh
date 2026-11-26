package de.metas.fresh.docs.sales;

/*
 * #%L
 * de.metas.fresh.base
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

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@code report.jrxml} (sales order and sales invoice) switches its detail band between two alternative
 * subreports via the {@code details_product_overflow} resource-bundle key: one subreport is gated on the
 * key being {@code "Y"}, the other on {@code "N"}. If either subreport's {@code printWhenExpression} is
 * ever missing, both subreports render at once whenever the key is {@code "Y"} -- every line prints
 * twice, and only the shipped {@code "N"} default hides the defect.
 *
 * <p>That is exactly how the gate was lost previously: a Jaspersoft Studio re-save silently dropped one
 * subreport's {@code printWhenExpression}, and nothing failed because the switch is a classpath
 * {@code .properties} key read through {@code ResourceBundle.getBundle(...)} -- not a system
 * configuration value -- so no cucumber scenario can flip it and no render-based test can reach the
 * regressed path without changing the shipped configuration (which must never change).
 *
 * <p>This test does not render anything. It parses the two {@code report.jrxml} files as plain XML and
 * asserts, structurally, that both subreports of that detail-band switch carry a
 * {@code printWhenExpression} gating on the specific literal ({@code "Y"} or {@code "N"}) that subreport
 * is meant to render on -- not merely that some condition mentioning the switch key is present. A
 * condition that is present but rewritten to the wrong literal (e.g. both subreports gated on the same
 * value by a re-save) would leave a weaker, presence-only check green while leaving either duplicate
 * printing (both {@code "Y"}) or, under the shipped {@code "N"} default, no product detail at all (both
 * gated {@code "N"}) -- the second of which is worse than the defect this test exists to prevent.
 */
class DetailTemplateSwitchGateTest
{
	private static final String SWITCH_KEY = "details_product_overflow";

	/** Matches {@code $R{details_product_overflow}.equals( "Y" )} (or {@code "N"}), capturing the literal. */
	private static final Pattern SWITCH_LITERAL_PATTERN = Pattern.compile(SWITCH_KEY + "\\}\\.equals\\(\\s*\"([YN])\"\\s*\\)");

	@Test
	void order_report_both_detail_subreports_carry_the_switch_gate() throws Exception
	{
		final Map<String, String> expectedLiteralBySubreportExpression = new LinkedHashMap<>();
		expectedLiteralBySubreportExpression.put("de/metas/docs/sales/order/report_details.jasper", "Y");
		expectedLiteralBySubreportExpression.put("de/metas/docs/sales/order/report_details_v2.jasper", "N");

		assertBothSubreportsGated(
				"src/main/jasperreports/de/metas/docs/sales/order/report.jrxml",
				expectedLiteralBySubreportExpression);
	}

	@Test
	void invoice_report_both_detail_subreports_carry_the_switch_gate() throws Exception
	{
		final Map<String, String> expectedLiteralBySubreportExpression = new LinkedHashMap<>();
		expectedLiteralBySubreportExpression.put("de/metas/docs/sales/invoice/report_details.jasper", "Y");
		expectedLiteralBySubreportExpression.put("de/metas/docs/sales/invoice/report_details_v2.jasper", "N");

		assertBothSubreportsGated(
				"src/main/jasperreports/de/metas/docs/sales/invoice/report.jrxml",
				expectedLiteralBySubreportExpression);
	}

	private static void assertBothSubreportsGated(
			final String jrxmlPathRelativeToModule,
			final Map<String, String> expectedLiteralBySubreportExpression) throws Exception
	{
		final File jrxmlFile = new File(jrxmlPathRelativeToModule);
		assertTrue(jrxmlFile.isFile(),
				() -> "JRXML not found at " + jrxmlFile.getAbsolutePath()
						+ " - this test assumes the module directory (de.metas.fresh.base) is the working directory,"
						+ " which is Surefire's default");

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		final Document document = factory.newDocumentBuilder().parse(jrxmlFile);

		final NodeList subreportNodes = document.getElementsByTagName("subreport");
		final Set<String> gatedSubreportsFound = new LinkedHashSet<>();

		for (int i = 0; i < subreportNodes.getLength(); i++)
		{
			final Element subreportElement = (Element)subreportNodes.item(i);
			final String subreportExpression = textOfFirstChild(subreportElement, "subreportExpression");
			if (subreportExpression == null)
			{
				continue;
			}

			for (final Map.Entry<String, String> expectedEntry : expectedLiteralBySubreportExpression.entrySet())
			{
				final String expected = expectedEntry.getKey();
				final String expectedLiteral = expectedEntry.getValue();
				if (!subreportExpression.contains(expected))
				{
					continue;
				}

				final Element reportElement = firstChildElement(subreportElement, "reportElement");
				assertNotNull(reportElement,
						() -> "<subreport> for " + expected + " in " + jrxmlPathRelativeToModule + " has no <reportElement>");

				final Element printWhenExpression = firstChildElement(reportElement, "printWhenExpression");
				assertNotNull(printWhenExpression,
						() -> "<subreport> for " + expected + " in " + jrxmlPathRelativeToModule
								+ " has no printWhenExpression on its <reportElement> -- this is the detail-switch gate;"
								+ " a missing one means this subreport always renders regardless of the switch"
								+ " (this is exactly how the original defect happened)");

				final String expressionText = printWhenExpression.getTextContent();
				assertTrue(expressionText != null && expressionText.contains(SWITCH_KEY),
						() -> "printWhenExpression for " + expected + " in " + jrxmlPathRelativeToModule
								+ " must reference " + SWITCH_KEY + ", found: " + expressionText);

				final Matcher matcher = SWITCH_LITERAL_PATTERN.matcher(expressionText);
				assertTrue(matcher.find(),
						() -> "printWhenExpression for " + expected + " in " + jrxmlPathRelativeToModule
								+ " must gate on " + SWITCH_KEY + " equalling literal \"Y\" or \"N\", found: " + expressionText);
				assertEquals(expectedLiteral, matcher.group(1),
						"<subreport> for " + expected + " in " + jrxmlPathRelativeToModule
								+ " must be gated on \"" + expectedLiteral + "\", found: " + expressionText);

				gatedSubreportsFound.add(expected);
			}
		}

		assertEquals(expectedLiteralBySubreportExpression.keySet(), gatedSubreportsFound,
				"expected to find and gate exactly these subreport elements in " + jrxmlPathRelativeToModule);
	}

	private static Element firstChildElement(final Element parent, final String tagName)
	{
		final NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); i++)
		{
			final Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && tagName.equals(child.getNodeName()))
			{
				return (Element)child;
			}
		}
		return null;
	}

	private static String textOfFirstChild(final Element parent, final String tagName)
	{
		final Element child = firstChildElement(parent, tagName);
		return child == null ? null : child.getTextContent();
	}
}
