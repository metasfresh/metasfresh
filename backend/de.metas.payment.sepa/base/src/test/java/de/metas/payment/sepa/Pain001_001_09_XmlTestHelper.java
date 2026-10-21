package de.metas.payment.sepa;

import lombok.experimental.UtilityClass;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test helpers for exported pain.001.001.09.ch.03 XML: XSD validation, parsing and namespace-agnostic path lookups.
 */
@UtilityClass
public class Pain001_001_09_XmlTestHelper
{
	private static final String XSD_RESOURCE = "/pain.001.001.09.ch.03.xsd";

	/**
	 * Validates the given XML against the SIX XSD pain.001.001.09.ch.03; throws a {@link org.xml.sax.SAXParseException} with line/column if invalid.
	 */
	public static void assertValidAgainstXsd(final byte[] xml) throws Exception
	{
		final URL xsd = Pain001_001_09_XmlTestHelper.class.getResource(XSD_RESOURCE);
		assertThat(xsd).as("XSD %s on the classpath", XSD_RESOURCE).isNotNull();

		SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
				.newSchema(xsd)
				.newValidator()
				.validate(new StreamSource(new ByteArrayInputStream(xml)));
	}

	/**
	 * @return the {@code targetNamespace} of the SIX XSD pain.001.001.09.ch.03
	 */
	public static String readXsdTargetNamespace() throws Exception
	{
		try (final InputStream xsd = Pain001_001_09_XmlTestHelper.class.getResourceAsStream(XSD_RESOURCE))
		{
			assertThat(xsd).as("XSD %s on the classpath", XSD_RESOURCE).isNotNull();
			final String targetNamespace = newDocumentBuilderFactory().newDocumentBuilder().parse(xsd).getDocumentElement().getAttribute("targetNamespace");
			assertThat(targetNamespace).isNotBlank();
			return targetNamespace;
		}
	}

	public static Document parse(final byte[] xml) throws Exception
	{
		return newDocumentBuilderFactory().newDocumentBuilder().parse(new ByteArrayInputStream(xml));
	}

	private static DocumentBuilderFactory newDocumentBuilderFactory()
	{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		return factory;
	}

	/**
	 * @return text of the first node matching the given namespace-agnostic path, e.g. {@code Document/CstmrCdtTrfInitn/GrpHdr/NbOfTxs}; fails if there is no such node
	 */
	public static String text(final Document doc, final String path) throws Exception
	{
		assertThat(count(doc, path)).as("nodes at %s", path).isPositive();
		return XPathFactory.newInstance().newXPath().evaluate(toXPath(path), doc);
	}

	/**
	 * @return texts of all nodes matching the given namespace-agnostic path
	 */
	public static List<String> texts(final Document doc, final String path) throws Exception
	{
		final NodeList nodes = (NodeList)XPathFactory.newInstance().newXPath().evaluate(toXPath(path), doc, XPathConstants.NODESET);
		final List<String> result = new ArrayList<>();
		for (int i = 0; i < nodes.getLength(); i++)
		{
			result.add(nodes.item(i).getTextContent());
		}
		return result;
	}

	/**
	 * @return number of nodes matching the given namespace-agnostic path
	 */
	public static int count(final Document doc, final String path) throws Exception
	{
		final NodeList nodes = (NodeList)XPathFactory.newInstance().newXPath().evaluate(toXPath(path), doc, XPathConstants.NODESET);
		return nodes.getLength();
	}

	/**
	 * Turns {@code A/B[C/@x='y']/D} into a namespace-agnostic XPath ({@code /*[local-name()='A']/...}); predicates are converted the same way.
	 */
	private static String toXPath(final String path)
	{
		return "/" + toRelativeXPath(path);
	}

	private static String toRelativeXPath(final String path)
	{
		return Arrays.stream(path.split("/(?![^\\[]*\\])"))
				.map(Pain001_001_09_XmlTestHelper::toXPathStep)
				.collect(Collectors.joining("/"));
	}

	private static String toXPathStep(final String step)
	{
		if (step.startsWith("@"))
		{
			return step;
		}
		final int predicateStart = step.indexOf('[');
		if (predicateStart < 0)
		{
			return "*[local-name()='" + step + "']";
		}
		final String name = step.substring(0, predicateStart);
		final String predicate = step.substring(predicateStart + 1, step.length() - 1);
		final int eq = predicate.indexOf('=');
		final String predicatePath = eq < 0 ? predicate : predicate.substring(0, eq);
		final String predicateValue = eq < 0 ? "" : predicate.substring(eq);
		return "*[local-name()='" + name + "'][" + toRelativeXPath(predicatePath) + predicateValue + "]";
	}
}
