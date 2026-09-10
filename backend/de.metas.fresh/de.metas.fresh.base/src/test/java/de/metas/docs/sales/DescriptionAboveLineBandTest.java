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
	 * A tripwire, NOT a specification: nothing says there have to be exactly this many templates. It protects
	 * against a template silently GAINING or LOSING the field - a merge that drops the band, a new line-detail
	 * template copied from one that has it - by making that show up as a failure instead of as untested code.
	 * The value is purely descriptive of today's tree; a template that gains or loses the field on purpose has
	 * to make someone bump this number, and think about the band while doing so.
	 */
	private static final int EXPECTED_TEMPLATE_COUNT = 15;

	/**
	 * An article row is a band that lays out several COLUMNS, i.e. one whose elements sit at several different
	 * {@code x} positions - as opposed to our own full-width block and the single-column
	 * description/attribute/campaign rows, which put everything at one {@code x}.
	 * <p>
	 * Measured over all 15 templates that carry the field: article rows have 3 to 12 distinct {@code x}
	 * positions, every non-article band has exactly 1, so 3 separates them with the widest possible margin on
	 * both sides. Distinct {@code x} over ALL element types, deliberately not over {@code <textField>}s only:
	 * {@code inout/report_details_hu}, {@code inout/report_details_hu_v2}, {@code picking/report_details_hu} and
	 * {@code picking/report_details_hu_name_over_attributes} are article rows carrying just three text fields
	 * ({@code name}, {@code uomsymbol}, {@code movementqty}), so counting text fields misclassified them as
	 * single-column rows and skipped invariant 7 on them entirely.
	 */
	private static final int ARTICLE_ROW_MIN_DISTINCT_X_POSITIONS = 3;

	/**
	 * Slack allowed between our block's right edge and the right edge of its template's own content row.
	 * The floor is set by {@code order/report_details_hu_v2}, whose block ends at 543 while its article row ends
	 * at 545; in the other 14 templates the block is flush with the content row (delta 0), so 2 is the smallest
	 * value that keeps the tree green.
	 */
	private static final int RIGHT_EDGE_TOLERANCE = 2;

	/**
	 * Matches every reference a jasper expression can make to something outside itself: a field {@code $F{}}, a
	 * parameter {@code $P{}}, a resource-bundle string {@code $R{}} and a variable {@code $V{}}. All four can
	 * gate a band, so a coverage check that knows only {@code $F{}} reads a band guarded by e.g.
	 * {@code $P{PRINTER_OPTS_IsPrintPrices}} as "fully covered". The whole reference is captured, not just the
	 * name, so {@code $F{x}} and {@code $P{x}} cannot be confused.
	 */
	private static final Pattern EXPRESSION_REFERENCE_PATTERN = Pattern.compile("\\$[FPRV]\\{[^}]+}");

	@Test
	void eachTemplateCarryingTheFieldPrintsItAsOneFullWidthGuardedBandAboveItsArticleRow()
	{
		final Path templatesDir = moduleRootDir().resolve(TEMPLATES_DIR);
		final List<Path> templates = findTemplatesReferencingTheField(templatesDir);

		final List<String> violations = new ArrayList<>();

		// The filename pattern classifies, it does not filter: a template that carries the field under some other
		// name is a template whose band nobody is checking, so it has to be reported - and it has to count towards
		// EXPECTED_TEMPLATE_COUNT, or it could be added without tripping anything.
		final List<String> offPatternNames = templates.stream()
				.filter(template -> !isLineDetailTemplate(template))
				.map(template -> relativeName(templatesDir, template))
				.collect(Collectors.toList());
		if (!offPatternNames.isEmpty())
		{
			violations.add("template(s) carry `" + FIELD_NAME + "` but are not named `" + TEMPLATE_FILENAME_PREFIX
					+ "*" + TEMPLATE_FILENAME_SUFFIX + "`, which is where this test expects the line-detail templates"
					+ " to live. They are checked all the same; either rename them or widen"
					+ " TEMPLATE_FILENAME_PREFIX on purpose:\n" + bulletList(offPatternNames));
		}

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
			final String compactGuard = compact(ourGuard);
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
		// The content row is measured over the OTHER bands only. Measuring it over the whole `<detail>` includes
		// our own element, which makes `ourX >= contentRowX` and `ourRightEdge <= contentRowRightEdge` true by
		// construction: an over-wide or over-left band would then simply move the yardstick and the check could
		// only ever catch a block that is too NARROW, never one that is misplaced.
		if (ourElements.size() == 1)
		{
			final Element ourElement = ourElements.get(0);
			final int ourX = intAttribute(ourElement, "x");
			final int ourWidth = intAttribute(ourElement, "width");
			final int ourRightEdge = ourX + ourWidth;

			int contentRowX = Integer.MAX_VALUE;
			int contentRowRightEdge = Integer.MIN_VALUE;
			for (int i = 0; i < bands.size(); i++)
			{
				if (i == ourBandIndex)
				{
					continue;
				}
				for (final Element otherElement : descendantElements(bands.get(i), "reportElement"))
				{
					final int x = intAttribute(otherElement, "x");
					contentRowX = Math.min(contentRowX, x);
					contentRowRightEdge = Math.max(contentRowRightEdge, x + intAttribute(otherElement, "width"));
				}
			}

			if (contentRowRightEdge == Integer.MIN_VALUE)
			{
				violations.add(templateName + ": invariant 6 - apart from the `" + FIELD_NAME + "` band there is no"
						+ " band with elements inside `<detail>`, so there is no content row to measure the block"
						+ " against - and no article row for the text to sit above either.");
			}
			else
			{
				if (ourX != contentRowX)
				{
					violations.add(templateName + ": invariant 6 - the block starts at x=" + ourX
							+ " but the leftmost element of the OTHER bands in `<detail>` starts at x=" + contentRowX
							+ "; the block has to span the template's own content row, and start with it.");
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
		}

		//
		// Invariant 7: our band prints for exactly the rows the article bands print for.
		// If an article row can be suppressed by a condition our block does not share, the block renders orphaned;
		// and if a second article band prints under a different condition, a guard naming only the first silently
		// DROPS the text above rows that do print.
		//
		// Comparing the mere SET of references is both polarity-blind and (before this) parameter-blind: inverting
		// the invoice guard `!$F{ishu} || "Y".equals($F{isprintwhenpackingmaterial})` into
		// `$F{ishu} && "Y".equals($F{isprintwhenpackingmaterial})` leaves the set identical while dropping the text
		// above every non-HU line, and a `$P{...}`-guarded article band used to read as "covered" because only
		// `$F{...}` was extracted. Hence four checks, dispatched by how the article bands are guarded - which is
		// what decides what our guard has to look like. Deliberately NOT a jasper expression parser; each check is
		// a syntactic one whose reasoning is spelled out.
		final Set<String> ourGuardReferences = referencesIn(ourGuard);
		final String ourCompactGuard = compact(ourGuard);

		final List<Integer> articleBandIndexes = new ArrayList<>();
		final List<Integer> guardedArticleBandIndexes = new ArrayList<>();
		for (int i = 0; i < bands.size(); i++)
		{
			if (i == ourBandIndex || !isArticleRow(bands.get(i)))
			{
				continue;
			}
			articleBandIndexes.add(i);
			if (bandLevelPrintWhenExpression(bands.get(i)) != null)
			{
				guardedArticleBandIndexes.add(i);
			}
		}

		//
		// 7a: every reference an article band's guard makes - $F, $P, $R or $V - has to be made by our guard too.
		// Catches a renamed or forgotten condition, in whatever namespace it lives.
		for (final int i : guardedArticleBandIndexes)
		{
			final String articleGuard = bandLevelPrintWhenExpression(bands.get(i));
			final Set<String> notCovered = new LinkedHashSet<>(referencesIn(articleGuard));
			notCovered.removeAll(ourGuardReferences);
			if (!notCovered.isEmpty())
			{
				violations.add(templateName + ": invariant 7a - article band " + (i + 1) + " of " + bands.size()
						+ " (" + distinctXPositions(bands.get(i)).size() + " columns) is guarded by `"
						+ singleLine(String.valueOf(articleGuard)) + "`, but the `" + FIELD_NAME + "` band's guard `"
						+ singleLine(String.valueOf(ourGuard)) + "` does not reference " + notCovered + "."
						+ " The text would then print above a row that is suppressed, or be dropped above rows that do print.");
			}
		}

		// 7b-7d all reason about what our band's own guard has to look like, so they have nothing to compare
		// against when there is none - invariant 5 already reported that, and they would only pile onto it.
		if (ourGuard != null)
		{
			if (articleBandIndexes.isEmpty())
			{
				violations.add(templateName + ": invariant 7 - `<detail>` has no article row (no band with at least "
						+ ARTICLE_ROW_MIN_DISTINCT_X_POSITIONS + " distinct element `x` positions), so there is no row for"
						+ " the text to sit above. Either the template is not a line-detail template or the discriminator"
						+ " no longer fits it.");
			}
			else if (guardedArticleBandIndexes.size() < articleBandIndexes.size())
			{
				//
				// 7d: at least one article row prints unconditionally, so every line has an article row - and our text
				// therefore has to print on every line too. Our guard may then hold nothing but our own null/blank
				// clauses. Note that a `printWhenExpression` sitting on a nested `<textField>` (e.g. IsPrintPrices) must
				// NOT be inherited either: inheriting it would hide the customer's text whenever prices are switched off.
				final Set<String> foreignReferences = new LinkedHashSet<>(ourGuardReferences);
				foreignReferences.remove(FIELD_REF);
				if (!foreignReferences.isEmpty())
				{
					violations.add(templateName + ": invariant 7d - article band "
							+ humanBandNumbers(articleBandIndexes) + " of " + bands.size() + " print(s) unconditionally,"
							+ " so every line has an article row and the text has to print above every line. But the `"
							+ FIELD_NAME + "` band's guard `" + singleLine(ourGuard) + "` also depends on "
							+ foreignReferences + ", which drops the text above the lines where that is false.");
				}
			}
			else if (guardedArticleBandIndexes.size() == 1)
			{
				//
				// 7b: exactly one article row, and it is guarded. Our block prints for exactly the lines that one row
				// prints for, so our guard has to be THAT guard plus our own null/blank clauses - which means it has to
				// contain it verbatim (whitespace aside). This is the check that catches an inverted, weakened or
				// re-parameterised copy - `!= 0` turned into `== 0`, an added `!`, a swapped `$P{...}` - and not merely
				// a renamed reference. It is deliberately rigid: a purely cosmetic rewrite of the article guard trips it
				// and has to be mirrored here, which is the point (someone looks at the band).
				final String articleGuard = bandLevelPrintWhenExpression(bands.get(guardedArticleBandIndexes.get(0)));
				if (!ourCompactGuard.contains(compact(articleGuard)))
				{
					violations.add(templateName + ": invariant 7b - the single article band "
							+ humanBandNumbers(guardedArticleBandIndexes) + " of " + bands.size() + " is guarded by `"
							+ singleLine(String.valueOf(articleGuard)) + "`, but the `" + FIELD_NAME + "` band's guard `"
							+ singleLine(ourGuard) + "` does not contain that expression verbatim, so the two do not"
							+ " provably print for the same lines - the guard may be inverted, weakened or"
							+ " differently parameterised.");
				}
			}
			else
			{
				//
				// 7c: several guarded article rows. They are ALTERNATIVES - each prints for a disjoint subset of the
				// lines (invoice: non-HU lines vs HU lines that print as packing material) - and our block has to print
				// for EITHER of them. A guard that only ANDs conditions taken from both prints above neither set
				// completely, so our guard must combine them disjunctively and hence must contain a `||`.
				// What this does NOT catch, and what would need an evaluator for jasper expressions: an inversion that
				// keeps the disjunctive shape, e.g. rewriting `!$F{ishu} || X` as `$F{ishu} || X`.
				if (!ourCompactGuard.contains("||"))
				{
					violations.add(templateName + ": invariant 7c - article bands "
							+ humanBandNumbers(guardedArticleBandIndexes) + " of " + bands.size()
							+ " are alternatives, each guarded by its own expression, so the `" + FIELD_NAME + "` band has"
							+ " to print for EITHER of them. Its guard `" + singleLine(ourGuard) + "` contains no `||`,"
							+ " i.e. it requires the conditions of the alternatives to hold at the same time - the text is"
							+ " then dropped above the article rows that do print.");
				}
			}
		}

		//
		// Invariant 8: the text element stretches with its content. Without `isStretchWithOverflow="true"` the block
		// clips to its designed height - a single 12pt line - and everything the customer typed beyond the first
		// line is silently lost. It is uniform across all templates, but the wrap scenario in
		// `freeTextAboveOrderLine.feature` can only ever exercise the one template a rendered document uses.
		final List<Element> ourTextFields = descendantElements(ourBand, "textField");
		if (ourTextFields.isEmpty())
		{
			violations.add(templateName + ": invariant 8 - the `" + FIELD_NAME + "` band has no `<textField>` at all,"
					+ " so there is nothing that could print the text.");
		}
		for (final Element ourTextField : ourTextFields)
		{
			if (!"true".equals(ourTextField.getAttribute("isStretchWithOverflow")))
			{
				violations.add(templateName + ": invariant 8 - the `" + FIELD_NAME + "` band's `<textField>` has"
						+ " isStretchWithOverflow=`" + ourTextField.getAttribute("isStretchWithOverflow") + "`,"
						+ " expected `true`. Without it the block clips to its designed height of one 12pt line and"
						+ " everything the customer typed after the first line is dropped without a trace.");
			}
		}
	}

	/**
	 * @return {@code true} if the given band lays out several columns and is therefore an article row rather than
	 * 		our own block or a single-column description/attribute row - see
	 * 		{@link #ARTICLE_ROW_MIN_DISTINCT_X_POSITIONS}.
	 */
	private static boolean isArticleRow(final Element band)
	{
		return distinctXPositions(band).size() >= ARTICLE_ROW_MIN_DISTINCT_X_POSITIONS;
	}

	private static Set<Integer> distinctXPositions(final Element band)
	{
		final Set<Integer> xPositions = new LinkedHashSet<>();
		for (final Element reportElement : descendantElements(band, "reportElement"))
		{
			xPositions.add(intAttribute(reportElement, "x"));
		}
		return xPositions;
	}

	/**
	 * @return the {@code $F{...}}, {@code $P{...}}, {@code $R{...}} and {@code $V{...}} references made by the
	 * 		given jasper expression, each as written; empty if there is none.
	 */
	private static Set<String> referencesIn(@Nullable final String expression)
	{
		final Set<String> references = new LinkedHashSet<>();
		if (expression == null)
		{
			return references;
		}

		final Matcher matcher = EXPRESSION_REFERENCE_PATTERN.matcher(expression);
		while (matcher.find())
		{
			references.add(matcher.group());
		}
		return references;
	}

	/**
	 * @return the given expression with ALL whitespace removed, so that two expressions can be compared without
	 * 		their formatting getting in the way; {@code ""} for {@code null}.
	 */
	private static String compact(@Nullable final String expression)
	{
		return expression == null ? "" : expression.replaceAll("\\s+", "");
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
					.filter(file -> file.getFileName().toString().endsWith(TEMPLATE_FILENAME_SUFFIX))
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
