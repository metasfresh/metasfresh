package de.metas.docs.sales;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Structural test for the {@code descriptionaboveline} print band ("free text above the line") in the
 * sales line-detail jasper templates.
 * <p>
 * Why structural and not a rendering test: which line-detail template a document actually renders with is
 * chosen by resource-bundle properties (e.g. {@code $R{details_product_overflow}}), so a rendering test only
 * ever reaches the handful of templates that are selected by default. The remaining templates carry the very
 * same band and can only be covered by asserting the invariants the band has to satisfy in each of them.
 * <p>
 * These invariants are not academic: the two defects this band shipped with were both structural (bands
 * narrower than their own template's content row, and bands missing the guard of the article row they sit
 * above), and both slipped past human review.
 * <p>
 * The templates live in {@code src/main/jasperreports}, i.e. they are NOT on the test classpath - they are
 * resolved from the filesystem relative to the module root, the same way {@code RunMigrationScriptsTest}
 * resolves the migration scripts. They are parsed as XML (never matched as text): the whole point is to tell
 * a band-level {@code printWhenExpression} apart from one nested inside a {@code <textField>}, and a
 * substring match cannot.
 */
public class DescriptionAboveLineBandTest
{
	private static final String TEMPLATES_DIR = "src/main/jasperreports/de/metas/docs/sales";
	private static final String TEMPLATE_FILENAME_PREFIX = "report_details";
	private static final String TEMPLATE_FILENAME_SUFFIX = ".jrxml";

	private static final String FIELD_NAME = "descriptionaboveline";
	private static final String FIELD_REF = "$F{" + FIELD_NAME + "}";

	/**
	 * The number of line-detail templates that carry the band. Deliberately a hardcoded expectation: a template
	 * that gains or loses the field has to make someone bump this number on purpose.
	 */
	private static final int EXPECTED_TEMPLATE_COUNT = 15;

	/**
	 * A band with at least this many {@code <textFieldExpression>}s is an article row (it prints many columns),
	 * as opposed to our own single-column block or the single-column description/attribute rows.
	 */
	private static final int ARTICLE_BAND_MIN_TEXT_FIELDS = 5;

	/**
	 * Slack allowed between our block's right edge and the right edge of its template's own content row.
	 * {@code order/report_details_hu_v2} legitimately ends 2pt short of its article row.
	 */
	private static final int RIGHT_EDGE_TOLERANCE = 5;

	private static final Pattern FIELD_REFERENCE_PATTERN = Pattern.compile("\\$F\\{([^}]+)}");

	@Test
	void eachTemplateCarryingTheFieldPrintsItAsOneFullWidthGuardedBandAboveItsArticleRow()
	{
		final Path templatesDir = moduleRootDir().resolve(TEMPLATES_DIR);
		final List<Path> templates = findTemplatesReferencingTheField(templatesDir);

		final List<String> violations = new ArrayList<>();

		if (templates.size() != EXPECTED_TEMPLATE_COUNT)
		{
			violations.add("Expected " + EXPECTED_TEMPLATE_COUNT + " line-detail templates to carry `" + FIELD_NAME + "`"
					+ " but found " + templates.size() + "."
					+ " If a template gained or lost the field on purpose, update EXPECTED_TEMPLATE_COUNT."
					+ " Templates found:\n" + bulletList(relativeNames(templatesDir, templates)));
		}

		for (final Path template : templates)
		{
			checkTemplate(relativeName(templatesDir, template), parseXml(template), violations);
		}

		assertThat(violations)
				.withFailMessage("The `%s` print band violates its structural invariants in %s place(s):\n\n%s\n\n"
								+ "(checked %s template(s) under %s)",
						FIELD_NAME, violations.size(), bulletList(violations), templates.size(), templatesDir)
				.isEmpty();
	}

	private void checkTemplate(final String templateName, final Document template, final List<String> violations)
	{
		//
		// Invariant 1: the field is declared. JasperReports binds report fields by declaration, even when the
		// query is a `SELECT *`, so the `<field>` element is load-bearing and not redundant with the SQL.
		if (!hasFieldDeclaration(template))
		{
			violations.add(templateName + ": invariant 1 - no `<field name=\"" + FIELD_NAME + "\">` declaration."
					+ " JasperReports binds by field declaration even under `SELECT *`, so without it the band prints nothing.");
		}

		final Element detail = firstChildElement(template.getDocumentElement(), "detail");
		if (detail == null)
		{
			violations.add(templateName + ": has no `<detail>` section at all.");
			return;
		}

		final List<Element> bands = childElements(detail, "band");
		final List<Integer> ourBandIndexes = new ArrayList<>();
		for (int i = 0; i < bands.size(); i++)
		{
			if (bands.get(i).getTextContent().contains(FIELD_REF))
			{
				ourBandIndexes.add(i);
			}
		}

		//
		// Invariant 2: exactly one band renders the field.
		if (ourBandIndexes.size() != 1)
		{
			violations.add(templateName + ": invariant 2 - expected exactly ONE band inside `<detail>` referencing "
					+ FIELD_REF + ", but " + ourBandIndexes.size() + " do"
					+ (ourBandIndexes.isEmpty() ? "" : " (band(s) " + humanBandNumbers(ourBandIndexes) + " of " + bands.size() + ")")
					+ ". Further invariants cannot be checked for this template.");
			return;
		}

		final int ourBandIndex = ourBandIndexes.get(0);
		final Element ourBand = bands.get(ourBandIndex);

		//
		// Invariant 3: it is the first band, i.e. it renders ABOVE the article row.
		if (ourBandIndex != 0)
		{
			violations.add(templateName + ": invariant 3 - the `" + FIELD_NAME + "` band must be the FIRST band inside"
					+ " `<detail>` so that the text renders ABOVE the article row, but it is band " + (ourBandIndex + 1)
					+ " of " + bands.size() + ".");
		}

		//
		// Invariant 4: it is a standalone, single-element block. That is also what makes widening it safe.
		final List<Element> ourElements = descendantElements(ourBand, "reportElement");
		if (ourElements.size() != 1)
		{
			violations.add(templateName + ": invariant 4 - the `" + FIELD_NAME + "` band must contain exactly ONE"
					+ " `<reportElement>` (a standalone full-width block), but it contains " + ourElements.size() + ".");
		}

		//
		// Invariant 5: the band itself - not some nested textField - is guarded against null/blank text.
		final String ourGuard = bandLevelPrintWhenExpression(ourBand);
		if (ourGuard == null)
		{
			violations.add(templateName + ": invariant 5 - the `" + FIELD_NAME + "` band has no band-level"
					+ " `<printWhenExpression>` (a direct child of `<band>`); an empty text would print an empty row."
					+ " A `printWhenExpression` nested inside the `<reportElement>` or `<textField>` does not count -"
					+ " it blanks the text but still reserves the band's height.");
		}
		else
		{
			final String compactGuard = ourGuard.replaceAll("\\s+", "");
			if (!compactGuard.contains(FIELD_REF + "!=null"))
			{
				violations.add(templateName + ": invariant 5 - the band-level guard lacks a null check on " + FIELD_REF
						+ ": `" + singleLine(ourGuard) + "`");
			}
			if (!compactGuard.contains(FIELD_REF + ".trim().isEmpty()"))
			{
				violations.add(templateName + ": invariant 5 - the band-level guard lacks a `" + FIELD_REF
						+ ".trim().isEmpty()` check, so whitespace-only text would print an empty row: `"
						+ singleLine(ourGuard) + "`");
			}
		}

		//
		// Invariant 6: full width measured against THIS template's own content row.
		// Not against a fraction of pageWidth: the same 596pt page carries content rows ending at 573, 545 and 457
		// in different templates, so pageWidth is the wrong yardstick.
		if (ourElements.size() == 1)
		{
			final Element ourElement = ourElements.get(0);
			final int ourX = intAttribute(ourElement, "x");
			final int ourWidth = intAttribute(ourElement, "width");
			final int ourRightEdge = ourX + ourWidth;

			final List<Element> detailElements = descendantElements(detail, "reportElement");
			int contentRowX = Integer.MAX_VALUE;
			int contentRowRightEdge = Integer.MIN_VALUE;
			for (final Element detailElement : detailElements)
			{
				final int x = intAttribute(detailElement, "x");
				contentRowX = Math.min(contentRowX, x);
				contentRowRightEdge = Math.max(contentRowRightEdge, x + intAttribute(detailElement, "width"));
			}

			if (ourX != contentRowX)
			{
				violations.add(templateName + ": invariant 6 - the block starts at x=" + ourX
						+ " but the leftmost element in `<detail>` starts at x=" + contentRowX
						+ "; the block has to span the template's own content row.");
			}
			if (contentRowRightEdge - ourRightEdge > RIGHT_EDGE_TOLERANCE)
			{
				violations.add(templateName + ": invariant 6 - the block ends at x=" + ourRightEdge
						+ " (x=" + ourX + " width=" + ourWidth + "), which is "
						+ (contentRowRightEdge - ourRightEdge) + "pt short of the right edge of this template's own"
						+ " content row (x=" + contentRowRightEdge + "); at most " + RIGHT_EDGE_TOLERANCE
						+ "pt of slack is allowed. A narrow block wraps the customer's text far too early.");
			}
		}

		//
		// Invariant 7: our guard covers EVERY article band of this template.
		// If an article row can be suppressed by a condition our block does not share, the block renders orphaned;
		// and if a second article band prints under a different condition, a guard naming only the first silently
		// DROPS the text above rows that do print.
		final Set<String> ourGuardFields = fieldsReferencedIn(ourGuard);
		for (int i = 0; i < bands.size(); i++)
		{
			if (i == ourBandIndex)
			{
				continue;
			}

			final Element band = bands.get(i);
			final int textFieldCount = descendantElements(band, "textFieldExpression").size();
			if (textFieldCount < ARTICLE_BAND_MIN_TEXT_FIELDS)
			{
				continue; // not an article row: an article row prints many columns, our block and the description rows print one
			}

			final String articleGuard = bandLevelPrintWhenExpression(band);
			if (articleGuard == null)
			{
				// The article row always prints, so there is nothing for our block to inherit. Note that a
				// `printWhenExpression` sitting on a nested `<textField>` (e.g. IsPrintPrices) must NOT be
				// inherited - inheriting it would hide the customer's text whenever prices are switched off.
				continue;
			}

			final Set<String> notCovered = new LinkedHashSet<>(fieldsReferencedIn(articleGuard));
			notCovered.removeAll(ourGuardFields);
			if (!notCovered.isEmpty())
			{
				violations.add(templateName + ": invariant 7 - article band " + (i + 1) + " of " + bands.size()
						+ " (" + textFieldCount + " text fields) is guarded by `" + singleLine(articleGuard) + "`,"
						+ " but the `" + FIELD_NAME + "` band's guard `" + singleLine(String.valueOf(ourGuard)) + "`"
						+ " does not share " + notCovered + "."
						+ " The text would then print above a row that is suppressed, or be dropped above rows that do print.");
			}
		}
	}

	/**
	 * @return the {@code $F{...}} field names referenced in the given jasper expression; empty if there is none.
	 */
	private static Set<String> fieldsReferencedIn(@Nullable final String expression)
	{
		final Set<String> fieldNames = new LinkedHashSet<>();
		if (expression == null)
		{
			return fieldNames;
		}

		final Matcher matcher = FIELD_REFERENCE_PATTERN.matcher(expression);
		while (matcher.find())
		{
			fieldNames.add(matcher.group(1).trim());
		}
		return fieldNames;
	}

	/**
	 * @return the text of the {@code <printWhenExpression>} that is a DIRECT child of the given band, or {@code null}.
	 * 		Deliberately not any descendant: an expression nested inside a {@code <textField>} governs that text
	 * 		field only, never whether the band prints.
	 */
	@Nullable
	private static String bandLevelPrintWhenExpression(final Element band)
	{
		final Element printWhenExpression = firstChildElement(band, "printWhenExpression");
		return printWhenExpression == null ? null : printWhenExpression.getTextContent();
	}

	private static boolean hasFieldDeclaration(final Document template)
	{
		for (final Element field : childElements(template.getDocumentElement(), "field"))
		{
			if (FIELD_NAME.equals(field.getAttribute("name")))
			{
				return true;
			}
		}
		return false;
	}

	private static int intAttribute(final Element element, final String attributeName)
	{
		final String value = element.getAttribute(attributeName);
		return value.isEmpty() ? 0 : Integer.parseInt(value.trim());
	}

	private static List<Element> childElements(final Element parent, final String tagName)
	{
		final List<Element> children = new ArrayList<>();
		final NodeList nodes = parent.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++)
		{
			final Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE && tagName.equals(node.getNodeName()))
			{
				children.add((Element)node);
			}
		}
		return children;
	}

	@Nullable
	private static Element firstChildElement(final Element parent, final String tagName)
	{
		final List<Element> children = childElements(parent, tagName);
		return children.isEmpty() ? null : children.get(0);
	}

	private static List<Element> descendantElements(final Element ancestor, final String tagName)
	{
		final List<Element> descendants = new ArrayList<>();
		final NodeList nodes = ancestor.getElementsByTagName(tagName);
		for (int i = 0; i < nodes.getLength(); i++)
		{
			descendants.add((Element)nodes.item(i));
		}
		return descendants;
	}

	private static Document parseXml(final Path file)
	{
		try
		{
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			// the templates use a default namespace without a prefix, so non-namespace-aware parsing gives us the
			// tag names exactly as they are written in the file
			factory.setNamespaceAware(false);

			final DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(file.toFile());
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Failed parsing " + file, ex);
		}
	}

	private static List<Path> findTemplatesReferencingTheField(final Path templatesDir)
	{
		assertThat(templatesDir).as("jasper templates directory").isDirectory();

		try (final Stream<Path> files = Files.walk(templatesDir))
		{
			return files
					.filter(Files::isRegularFile)
					.filter(DescriptionAboveLineBandTest::isLineDetailTemplate)
					.filter(DescriptionAboveLineBandTest::referencesTheField)
					.sorted()
					.collect(Collectors.toList());
		}
		catch (final IOException ex)
		{
			throw new UncheckedIOException("Failed walking " + templatesDir, ex);
		}
	}

	private static boolean isLineDetailTemplate(final Path file)
	{
		final String filename = file.getFileName().toString();
		return filename.startsWith(TEMPLATE_FILENAME_PREFIX) && filename.endsWith(TEMPLATE_FILENAME_SUFFIX);
	}

	private static boolean referencesTheField(final Path file)
	{
		try
		{
			return new String(Files.readAllBytes(file), StandardCharsets.UTF_8)
					.toLowerCase()
					.contains(FIELD_NAME);
		}
		catch (final IOException ex)
		{
			throw new UncheckedIOException("Failed reading " + file, ex);
		}
	}

	/**
	 * @return the first directory - starting at the working directory and walking up - that contains
	 * 		{@link #TEMPLATES_DIR}. The templates are not on the test classpath, so they have to be resolved from
	 * 		the filesystem; {@code RunMigrationScriptsTest} locates the migration scripts the same way.
	 */
	private static Path moduleRootDir()
	{
		final Path workingDir = Paths.get("").toAbsolutePath().normalize();
		for (Path dir = workingDir; dir != null; dir = dir.getParent())
		{
			if (Files.isDirectory(dir.resolve(TEMPLATES_DIR)))
			{
				return dir;
			}
		}

		throw new IllegalStateException("Cannot locate `" + TEMPLATES_DIR + "` in " + workingDir
				+ " or any of its ancestors. Run this test with the module directory as working directory.");
	}

	private static List<String> relativeNames(final Path templatesDir, final List<Path> files)
	{
		return files.stream().map(file -> relativeName(templatesDir, file)).collect(Collectors.toList());
	}

	private static String relativeName(final Path templatesDir, final Path file)
	{
		return templatesDir.relativize(file).toString().replace('\\', '/');
	}

	private static String humanBandNumbers(final List<Integer> bandIndexes)
	{
		return bandIndexes.stream().map(index -> String.valueOf(index + 1)).collect(Collectors.joining(", "));
	}

	private static String singleLine(final String expression)
	{
		return expression.replaceAll("\\s+", " ").trim();
	}

	private static String bulletList(final List<String> lines)
	{
		return lines.stream().map(line -> " - " + line).collect(Collectors.joining("\n"));
	}
}
