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
 * resolves the migration scripts. Two maven modules carry such templates - this one and
 * {@code metasfresh-dist/base} - so two source roots are scanned; see {@link #templateRoots()}. They are parsed as
 * XML (never matched as text): the whole point is to tell a band-level {@code printWhenExpression} apart from one
 * nested inside a {@code <textField>}, and a substring match cannot.
 */
public class DescriptionAboveLineBandTest
{
	private static final String TEMPLATES_DIR = "src/main/jasperreports/de/metas/docs/sales";

	/**
	 * The very same directory in the SIBLING maven module {@code metasfresh-dist/base}, which carries one further
	 * line-detail template ({@code alternate_inout_2/report_details_hu.jrxml}) with the same band in it. The band
	 * there would otherwise be the one nobody checks, which is exactly the situation this test exists to remove.
	 * <p>
	 * It is resolved by walking up from this module until a directory containing this path is found, so no
	 * assumption is made about how deeply either module is nested; and a checkout that does not have the sibling
	 * module simply gets that root SKIPPED rather than a failure - see {@link #templateRoots()}.
	 */
	private static final String DIST_MODULE_TEMPLATES_DIR = "metasfresh-dist/base/" + TEMPLATES_DIR;

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
	 * <p>
	 * Counted PER SOURCE ROOT, because the templates live in two maven modules: 22 in this one
	 * ({@code de.metas.fresh.base}) and 1 in {@code metasfresh-dist/base}
	 * ({@code alternate_inout_2/report_details_hu.jrxml}), 23 together.
	 */
	private static final int EXPECTED_TEMPLATE_COUNT_OWN_MODULE = 22;

	/**
	 * @see #EXPECTED_TEMPLATE_COUNT_OWN_MODULE
	 */
	private static final int EXPECTED_TEMPLATE_COUNT_DIST_MODULE = 1;

	/**
	 * An article row is a band that lays out several COLUMNS, i.e. one whose elements sit at several different
	 * {@code x} positions - as opposed to our own full-width block and the single-column
	 * description/attribute/campaign rows, which put everything at one {@code x}.
	 * <p>
	 * Measured over all 23 templates that carry the field: article rows have 3 to 12 distinct {@code x}
	 * positions, every non-article band has 0 or 1 (0 being the 3pt spacer band in
	 * {@code alternate_inout_2/report_details_hu}), so 3 separates them with the widest possible margin on
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
	 * at 545; of the other 22 templates 21 are flush with their content row (delta 0) and {@code pickingv2/report_details}
	 * is wider than it, so 2 is the smallest value that keeps the tree green. Only a block that falls SHORT of the
	 * content row is a violation of THIS bound - one that reaches beyond it cannot wrap the customer's text too
	 * early, and is bounded by the page instead (see invariant 6's upper bound).
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

	/**
	 * Matches a boolean {@code false} literal that stands on its own rather than being part of a longer name.
	 * Case-insensitive, so the same pattern catches the {@code FALSE} of {@code Boolean.FALSE}.
	 * <p>
	 * The lookarounds are what keep a legitimate name out: a {@code false} glued to an identifier character on
	 * either side (a hypothetical field accessor {@code isfalsepositive}) does NOT match, while the one in
	 * {@code false}, {@code new Boolean(false)}, {@code new Boolean (false)}, {@code Boolean.valueOf(false)} and
	 * {@code Boolean.FALSE} does - {@code .} and {@code (} are not identifier characters.
	 */
	private static final Pattern FALSE_LITERAL_PATTERN =
			Pattern.compile("(?<![A-Za-z0-9_$])false(?![A-Za-z0-9_$])", Pattern.CASE_INSENSITIVE);

	/**
	 * Matches a comparison of two NUMBER literals, e.g. {@code 0 == 1} or {@code 1 != 1} - the other everyday way of
	 * writing a constant, and one that carries no {@code false} to look for.
	 * <p>
	 * A match is only a CANDIDATE. Two literals around an operator are not necessarily the whole comparison:
	 * {@code $F{x}.intValue() - 1 == 0} contains the text {@code 1 == 0} while being an entirely data-dependent
	 * condition, and reporting that as a constant would fail a legitimate guard with an actively misleading message.
	 * {@link #isStandaloneComparison(String, int, int)} therefore has to confirm the match IS the comparison before
	 * {@link #evaluateLiteralComparison(double, String, double)} judges it.
	 */
	private static final Pattern LITERAL_COMPARISON_PATTERN =
			Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*(==|!=|<=|>=|<|>)\\s*(\\d+(?:\\.\\d+)?)");

	/**
	 * The only things that may PRECEDE a literal comparison and still leave it a comparison in its own right: the
	 * start of the expression, an opening bracket, a boolean connective or a ternary. Deliberately a WHITELIST and
	 * not a blacklist of arithmetic operators: a whitelist fails CLOSED - anything unforeseen makes invariant 5b say
	 * nothing rather than fail a correct template - which is the right way round for a check that breaks the build.
	 * <p>
	 * One qualification, found by review and worth stating rather than implying: an opening bracket is accepted
	 * WITHOUT looking at what precedes the bracket, so this pattern alone does not distinguish {@code (1 == 0)} from
	 * {@code !(1 == 0)}. The negation is what decides whether the constant is false or true, and it is handled
	 * separately - see {@link #negationsDirectlyWrapping(String, int, int)}.
	 */
	private static final Pattern LITERAL_COMPARISON_CONTEXT_BEFORE = Pattern.compile("(?:^|\\(|&&|\\|\\||\\?|:)\\s*$");

	/**
	 * @see #LITERAL_COMPARISON_CONTEXT_BEFORE - the same whitelist for what may FOLLOW.
	 */
	private static final Pattern LITERAL_COMPARISON_CONTEXT_AFTER = Pattern.compile("^\\s*(?:$|\\)|&&|\\|\\||\\?|:)");

	/**
	 * Matches a java string or character literal, including its escapes. Removed before {@code false} is looked for,
	 * so that a guard comparing against the STRING {@code "false"} is not read as a constant.
	 */
	private static final Pattern STRING_OR_CHAR_LITERAL_PATTERN =
			Pattern.compile("\"(?:\\\\.|[^\"\\\\])*\"|'(?:\\\\.|[^'\\\\])*'");

	/**
	 * Matches a java comment inside a jasper expression. These are not hypothetical: THREE of the 23 scanned
	 * templates carry {@code //hide column for now} directly above a {@code new Boolean (false)} in an element-level
	 * expression, so a comment mentioning {@code false} in a band guard is entirely plausible - and it must not be
	 * read as one.
	 */
	private static final Pattern JAVA_COMMENT_PATTERN =
			Pattern.compile("/\\*.*?\\*/|//[^\\r\\n]*", Pattern.DOTALL);

	@Test
	void eachTemplateCarryingTheFieldPrintsItAsOneFullWidthGuardedBandAboveItsArticleRow()
	{
		final List<TemplateRoot> templateRoots = templateRoots();

		final List<String> violations = new ArrayList<>();
		int checkedTemplateCount = 0;

		for (final TemplateRoot templateRoot : templateRoots)
		{
			final List<Path> templates = findTemplatesReferencingTheField(templateRoot.dir);
			checkedTemplateCount += templates.size();

			// The filename pattern classifies, it does not filter: a template that carries the field under some other
			// name is a template whose band nobody is checking, so it has to be reported - and it has to count towards
			// the expected count, or it could be added without tripping anything.
			final List<String> offPatternNames = templates.stream()
					.filter(template -> !isLineDetailTemplate(template))
					.map(templateRoot::nameOf)
					.collect(Collectors.toList());
			if (!offPatternNames.isEmpty())
			{
				violations.add("template(s) carry `" + FIELD_NAME + "` but are not named `" + TEMPLATE_FILENAME_PREFIX
						+ "*" + TEMPLATE_FILENAME_SUFFIX + "`, which is where this test expects the line-detail templates"
						+ " to live. They are checked all the same; either rename them or widen"
						+ " TEMPLATE_FILENAME_PREFIX on purpose:\n" + bulletList(offPatternNames));
			}

			if (templates.size() != templateRoot.expectedTemplateCount)
			{
				violations.add("Expected " + templateRoot.expectedTemplateCount + " line-detail template(s) under `"
						+ templateRoot.label + "` to carry `" + FIELD_NAME + "` but found " + templates.size() + "."
						+ " If a template gained or lost the field on purpose, update that root's expected count"
						+ " (EXPECTED_TEMPLATE_COUNT_OWN_MODULE / EXPECTED_TEMPLATE_COUNT_DIST_MODULE)."
						+ " Templates found:\n" + bulletList(templateRoot.namesOf(templates)));
			}

			for (final Path template : templates)
			{
				checkTemplate(templateRoot.nameOf(template), parseXml(template), violations);
			}
		}

		assertThat(violations)
				.withFailMessage("The `%s` print band violates its structural invariants in %s place(s):\n\n%s\n\n"
								+ "(checked %s template(s) under %s)",
						FIELD_NAME, violations.size(), bulletList(violations), checkedTemplateCount,
						templateRoots.stream().map(root -> String.valueOf(root.dir)).collect(Collectors.joining(", ")))
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

			//
			// Invariant 5b: the guard is not defeated by a subexpression that is false whatever the data is.
			// Invariant 5 above and invariants 7b and 7d below are all SUBSTRING containments over the guard text, so
			// a perfectly correct guard wrapped in a short-circuiting constant -
			// `new Boolean(false) && (<the correct guard>)` - satisfies every single one of them while the band never
			// prints at all. Both of these were confirmed to leave this test green before 5b existed:
			// `new Boolean(false) && (<inout_org_data_right's guard>)` and
			// `new Boolean(false) && (<individual_inout's guard>)`. The customer's text is then gone and no
			// invariant says a word about it, which is the exact failure mode this test exists to prevent.
			//
			// WHAT IT COVERS: the shapes a leftover debug toggle or a bad merge is actually written in - `false`,
			// `new Boolean(false)`, `new Boolean (false)`, `Boolean.valueOf(false)`, `Boolean.FALSE`, and a
			// comparison of two number literals such as `0 == 1` or `1 != 1` - the latter only where the two
			// literals really are the whole comparison, so that a data-dependent `$F{x}.intValue() - 1 == 0` is
			// left alone (see LITERAL_COMPARISON_CONTEXT_BEFORE).
			// WHAT IT DOES NOT COVER: a constant built out of anything else - a `$P{...}` a caller always passes
			// false, `!Boolean.TRUE`, `"a".equals("b")`, an always-empty string. Ruling those out needs an evaluator
			// for jasper expressions; this is deliberately a syntactic check over the plausible shapes, because
			// catching those is worth far more than catching none.
			//
			// It is applied to OUR band's guard ONLY, never to any other band's and never to a nested expression, and
			// that restriction is load-bearing rather than incidental. Measured over the 23 scanned templates,
			// counting only what this test actually parses - element-level `printWhenExpression`s INSIDE `<detail>`:
			// THREE templates carry a `false` literal in one (`inout_org_data_right`, `invoice_org_data_right`,
			// `order_org_data_right` - the same three cited at JAVA_COMMENT_PATTERN, hiding a column with
			// `//hide column for now` + `new Boolean (false)`), and NONE carries one in a band-level guard.
			// (Nine further templates carry `$V{LINESUM_SUM}.intValue() > 0 ? new Boolean(true) : new Boolean(false)`
			// - a real condition - but only in `columnHeader`/`pageHeader`/`pageFooter`/`lastPageFooter`, which this
			// test never looks at, so they are no evidence either way.)
			// A whole band deliberately switched off with `new Boolean (false)` is a legitimate thing in this tree
			// too - `invoice/report.jrxml`, which does not carry our field and so is not scanned, disables its
			// `<detail>` band exactly like that. So only the band-level guard of the band that prints the customer's
			// text is read, which is what bandLevelPrintWhenExpression already restricts us to.
			//
			// It is also deliberately position-blind: ANY false literal in this one guard is rejected, even one that
			// would not make the whole expression constant (`X || false`). None of the 23 legitimate guards contains
			// one, so nothing is lost - and it saves this check from having to understand operator precedence.
			final String sanitizedGuard = withoutLiteralsCommentsAndReferences(ourGuard);
			final List<String> constantFalseFindings = new ArrayList<>();
			if (FALSE_LITERAL_PATTERN.matcher(sanitizedGuard).find())
			{
				constantFalseFindings.add("a `false` literal");
			}
			final Matcher literalComparison = LITERAL_COMPARISON_PATTERN.matcher(sanitizedGuard);
			while (literalComparison.find())
			{
				if (!isStandaloneComparison(sanitizedGuard, literalComparison.start(), literalComparison.end()))
				{
					continue;
				}
				final Boolean comparisonValue = evaluateLiteralComparison(
						Double.parseDouble(literalComparison.group(1)),
						literalComparison.group(2),
						Double.parseDouble(literalComparison.group(3)));
				if (comparisonValue == null)
				{
					continue; // an operator this check does not know: report nothing rather than invent a violation
				}
				// `!(1 == 0)` is constantly TRUE and therefore harmless, while `!(1 == 1)` is constantly false just
				// as much as `1 == 0` is. So a directly wrapping negation flips the verdict rather than suppressing
				// it - see negationsDirectlyWrapping.
				final int negations = negationsDirectlyWrapping(
						sanitizedGuard, literalComparison.start(), literalComparison.end());
				final boolean effectiveValue = negations % 2 == 0 ? comparisonValue : !comparisonValue;
				if (!effectiveValue)
				{
					constantFalseFindings.add("the constantly false comparison `" + literalComparison.group() + "`"
							+ (negations == 0 ? "" : " under " + negations + " negation(s)"));
				}
			}
			if (!constantFalseFindings.isEmpty())
			{
				violations.add(templateName + ": invariant 5b - the band-level guard contains "
						+ String.join(" and ", constantFalseFindings) + ", i.e. a subexpression that is false whatever"
						+ " the data is, so the band never prints however correct the rest of the guard reads: `"
						+ singleLine(ourGuard) + "`. Invariants 5, 7b, 7c and 7d are all substring checks, so a constant"
						+ " short-circuited in front of a correct guard satisfies all of them while silently dropping"
						+ " the customer's text. Take the leftover toggle out.");
			}
		}

		//
		// Invariant 6: full width measured against THIS template's own content row, and never past its own page.
		// The LOWER bound is not a fraction of pageWidth: the same 596pt page carries content rows ending at 573, 545
		// and 457 in different templates, so pageWidth cannot say how wide "full width" is in a given template.
		// The UPPER bound is the page, for the reasons spelled out where it is checked.
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

			//
			// Invariant 6, UPPER bound: the block must not reach past its own page's printable right edge.
			// Without one the check is entirely one-sided - a `width="5000"` on `individual_inout/report_details`,
			// eight times its 596pt page, was confirmed to leave this test green - and a block that wide pushes the
			// customer's text off the paper just as surely as a narrow one wraps it too early.
			//
			// Why NOT the tempting "at most as far as the content row": that bound is WRONG here.
			// `pickingv2/report_details` legitimately ends at x=584 while the widest element of its other `<detail>`
			// bands ends at x=504, so a content-row upper bound would fail a correct template. Nor is widening the
			// yardstick to take in the `pageHeader` band a way out: folded into invariant 6's content row it makes
			// FOUR templates fail (`inout/report_details`, `inout/report_details_v2`, `order/report_details`,
			// `order/report_details_v2`), because the page header reaches further LEFT than the content row (x=34
			// against x=37) and the lower bound then rejects a correct block.
			// The page is the one yardstick no template in the tree argues with: all 23 blocks sit inside it, the
			// tightest by 11pt (`pickingv2` again, 584 of 595).
			//
			// `pageWidth - rightMargin` rather than `pageWidth - leftMargin - rightMargin` (the true printable width,
			// since an element's `x` is relative to the left margin): every template here has `leftMargin="0"`, so
			// the two coincide today, and of the two this is the LOOSER one - it can therefore never fail a template
			// that a correct printable-width bound would pass.
			final Element rootElement = template.getDocumentElement();
			final int pageWidth = intAttribute(rootElement, "pageWidth");
			if (pageWidth <= 0)
			{
				// Not merely defensive: without a pageWidth there is no upper bound at all, and this check would
				// silently stop running - which is exactly the "quietly checks nothing" outcome this test exists to
				// avoid. A jasper template without `pageWidth` does not compile, so this can only mean the attribute
				// was renamed or the root element is not the report.
				violations.add(templateName + ": invariant 6 - the root element carries no usable `pageWidth`"
						+ " (`" + rootElement.getAttribute("pageWidth") + "`), so the block's right edge cannot be"
						+ " bounded against the page and the upper half of invariant 6 would not be checked at all.");
			}
			else
			{
				final int printableRightEdge = pageWidth - intAttribute(rootElement, "rightMargin");
				if (ourRightEdge > printableRightEdge)
				{
					violations.add(templateName + ": invariant 6 - the block ends at x=" + ourRightEdge
							+ " (x=" + ourX + " width=" + ourWidth + "), which is " + (ourRightEdge - printableRightEdge)
							+ "pt PAST this template's own printable right edge (x=" + printableRightEdge
							+ ", pageWidth=" + pageWidth + " minus rightMargin="
							+ intAttribute(rootElement, "rightMargin") + "). The customer's text is then pushed off"
							+ " the paper.");
				}
			}

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
		final List<Integer> unguardedArticleBandIndexes = new ArrayList<>();
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
			else
			{
				unguardedArticleBandIndexes.add(i);
			}
		}

		//
		// 7a: every reference an article band's guard makes - $F, $P, $R or $V - has to be made by our guard too.
		// Catches a renamed or forgotten condition, in whatever namespace it lives.
		//
		// It is part of the dispatch below and not a rule of its own: it holds only while EVERY article band is
		// guarded, i.e. exactly in the 7b and 7c cases, where the article rows are the only rows there are and our
		// block has to follow their conditions. The moment ONE article band prints unconditionally, 7d applies and
		// says the very opposite - our guard must then reference nothing beyond our own field, because an
		// always-printing article row means every line HAS an article row and our text has to print above every
		// line, which makes a guarded sibling's condition irrelevant. Demanding both at once left one legitimate
		// shape - a `<detail>` with one guarded and one unguarded article band - with no satisfiable guard at all:
		// the null/blank-only guard 7d demands tripped 7a, and adding the guarded sibling's condition to satisfy 7a
		// tripped 7d. 7d is the semantically correct one there, so 7a stands down.
		if (unguardedArticleBandIndexes.isEmpty())
		{
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
			else if (!unguardedArticleBandIndexes.isEmpty())
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
							+ humanBandNumbers(unguardedArticleBandIndexes) + " of " + bands.size() + " print(s) unconditionally,"
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
	 * @return the given expression stripped of everything that may legitimately CONTAIN the text of a constant
	 * 		without being one, so that invariant 5b can look for a constant with plain patterns: string and character
	 * 		literals ({@code "false".equals(...)}), java comments (see {@link #JAVA_COMMENT_PATTERN}) and the
	 * 		{@code $F{}}/{@code $P{}}/{@code $R{}}/{@code $V{}} references (a field could be named {@code isfalse}),
	 * 		which become the placeholder {@code _REF_}.
	 * 		<p>
	 * 		String literals are removed BEFORE comments, on the grounds that a string literal in a guard is everyday
	 * 		({@code "Y".equals(...)}) whereas a {@code //} inside one would be bizarre; lexing java properly to settle
	 * 		that ordering question would be out of all proportion to what this check is for.
	 */
	private static String withoutLiteralsCommentsAndReferences(final String expression)
	{
		String sanitized = STRING_OR_CHAR_LITERAL_PATTERN.matcher(expression).replaceAll("\"\"");
		sanitized = JAVA_COMMENT_PATTERN.matcher(sanitized).replaceAll(" ");
		sanitized = EXPRESSION_REFERENCE_PATTERN.matcher(sanitized).replaceAll("_REF_");
		return sanitized;
	}

	/**
	 * @return {@code true} if the region {@code [start, end)} of {@code expression} - a
	 * 		{@link #LITERAL_COMPARISON_PATTERN} match - is a comparison in its own right rather than the tail of a
	 * 		bigger one. {@code $F{x}.intValue() - 1 == 0} matches that pattern at {@code 1 == 0} but is a real
	 * 		condition, and only what SURROUNDS the match can tell the two apart: a constant comparison is flanked by
	 * 		boolean context on both sides, an arithmetic one is not.
	 */
	private static boolean isStandaloneComparison(final String expression, final int start, final int end)
	{
		return LITERAL_COMPARISON_CONTEXT_BEFORE.matcher(expression.substring(0, start)).find()
				&& LITERAL_COMPARISON_CONTEXT_AFTER.matcher(expression.substring(end)).find();
	}

	/**
	 * @return the value of comparing the two given NUMBER LITERALS with the given java operator - which is a
	 * 		constant, since both sides are literals, and is what makes {@code 0 == 1} and {@code 1 != 1} constants
	 * 		rather than conditions. {@code null} for an operator this check does not know, so that an unrecognised
	 * 		comparison is reported as nothing at all rather than as a violation.
	 */
	@Nullable
	private static Boolean evaluateLiteralComparison(final double left, final String operator, final double right)
	{
		switch (operator)
		{
			case "==":
				return left == right;
			case "!=":
				return left != right;
			case "<":
				return left < right;
			case "<=":
				return left <= right;
			case ">":
				return left > right;
			case ">=":
				return left >= right;
			default:
				return null;
		}
	}

	/**
	 * @return how many {@code !} operators directly wrap the region {@code [start, end)} of {@code expression} - a
	 * 		{@link #LITERAL_COMPARISON_PATTERN} match - counting only the unambiguous shape {@code !(<comparison>)},
	 * 		{@code !!(<comparison>)} and so on, where the parentheses contain the comparison and NOTHING else.
	 * 		<p>
	 * 		This exists because a negation flips what the constant MEANS, and both directions matter: {@code !(1 == 0)}
	 * 		is constantly TRUE, so flagging it would fail a legitimate (if pointless) guard, while {@code !(1 == 1)} is
	 * 		constantly false exactly as {@code 1 == 0} is and must still be caught. Suppressing every negated
	 * 		comparison would fix the first at the price of opening the second.
	 * 		<p>
	 * 		Deliberately narrow, and the limit is NOT one-sided - an earlier version of this javadoc claimed a
	 * 		distant negation "can only make this check say nothing", and review disproved it by mutation. A comparison
	 * 		negated at a distance returns 0 here and is judged on its own value, so BOTH errors are reachable:
	 * 		{@code !(true && (1 == 0))} is constantly TRUE yet IS reported (a false positive - verified), while
	 * 		{@code !(true || (1 == 1))} is constantly false yet is NOT reported (a false negative - verified). Neither
	 * 		is a regression; before parity was considered at all, the first misfired exactly the same way.
	 * 		<p>
	 * 		So if invariant 5b ever fails on a guard of that nested shape, the check is at fault and not the guard.
	 * 		Untangling it needs a real expression evaluator, which is out of scope for a syntactic check (see the
	 * 		class javadoc) - and note the same page in this file already declines the 7c polarity hole for the same
	 * 		reason, rather than shipping a half-measure.
	 */
	private static int negationsDirectlyWrapping(final String expression, final int start, final int end)
	{
		int before = start - 1;
		while (before >= 0 && Character.isWhitespace(expression.charAt(before)))
		{
			before--;
		}
		if (before < 0 || expression.charAt(before) != '(')
		{
			return 0;
		}
		int after = end;
		while (after < expression.length() && Character.isWhitespace(expression.charAt(after)))
		{
			after++;
		}
		if (after >= expression.length() || expression.charAt(after) != ')')
		{
			return 0; // the parenthesis holds more than this comparison, so the `!` does not negate it alone
		}
		int negations = 0;
		int candidate = before - 1;
		while (candidate >= 0)
		{
			if (Character.isWhitespace(expression.charAt(candidate)))
			{
				candidate--;
			}
			else if (expression.charAt(candidate) == '!')
			{
				negations++;
				candidate--;
			}
			else
			{
				break;
			}
		}
		return negations;
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
	 * One directory holding line-detail templates, together with how many of them are expected to carry the field
	 * and how a template inside it is named in a violation message. There is more than one because the templates
	 * are spread over two maven modules - see {@link #DIST_MODULE_TEMPLATES_DIR}.
	 */
	private static final class TemplateRoot
	{
		private final String label;
		private final Path dir;
		private final int expectedTemplateCount;

		private TemplateRoot(final String label, final Path dir, final int expectedTemplateCount)
		{
			this.label = label;
			this.dir = dir;
			this.expectedTemplateCount = expectedTemplateCount;
		}

		/**
		 * @return the template's path relative to this root, prefixed with the root's label, so that a violation
		 * 		message says which MODULE's template it is about - two modules have a {@code report_details_hu.jrxml}.
		 */
		private String nameOf(final Path template)
		{
			return label + "/" + relativeName(dir, template);
		}

		private List<String> namesOf(final List<Path> templates)
		{
			return templates.stream().map(this::nameOf).collect(Collectors.toList());
		}
	}

	/**
	 * @return every directory that holds line-detail templates: this module's, always, plus
	 * 		{@code metasfresh-dist/base}'s when the checkout has that sibling module. The sibling being absent is
	 * 		NOT a failure - the templates are resolved from the filesystem rather than from the classpath, so the
	 * 		test has to cope with being run from a checkout that does not contain it, and then still checks
	 * 		everything it CAN see. What it must never do is quietly check nothing, which is why this module's own
	 * 		root is resolved by {@link #moduleRootDir()} and throws when it is not found.
	 */
	private static List<TemplateRoot> templateRoots()
	{
		final List<TemplateRoot> roots = new ArrayList<>();
		roots.add(new TemplateRoot("de.metas.fresh.base", moduleRootDir().resolve(TEMPLATES_DIR),
				EXPECTED_TEMPLATE_COUNT_OWN_MODULE));

		final Path distModuleParentDir = findAncestorContaining(DIST_MODULE_TEMPLATES_DIR);
		if (distModuleParentDir != null)
		{
			roots.add(new TemplateRoot("metasfresh-dist/base", distModuleParentDir.resolve(DIST_MODULE_TEMPLATES_DIR),
					EXPECTED_TEMPLATE_COUNT_DIST_MODULE));
		}

		return roots;
	}

	/**
	 * @return the first directory - starting at the working directory and walking up - that contains
	 * 		{@link #TEMPLATES_DIR}. The templates are not on the test classpath, so they have to be resolved from
	 * 		the filesystem; {@code RunMigrationScriptsTest} locates the migration scripts the same way.
	 */
	private static Path moduleRootDir()
	{
		final Path moduleRootDir = findAncestorContaining(TEMPLATES_DIR);
		if (moduleRootDir == null)
		{
			throw new IllegalStateException("Cannot locate `" + TEMPLATES_DIR + "` in "
					+ Paths.get("").toAbsolutePath().normalize()
					+ " or any of its ancestors. Run this test with the module directory as working directory.");
		}

		return moduleRootDir;
	}

	/**
	 * @return the first directory - starting at the working directory and walking up - under which
	 * 		{@code relativePath} is an existing directory; {@code null} if there is none.
	 */
	@Nullable
	private static Path findAncestorContaining(final String relativePath)
	{
		final Path workingDir = Paths.get("").toAbsolutePath().normalize();
		for (Path dir = workingDir; dir != null; dir = dir.getParent())
		{
			if (Files.isDirectory(dir.resolve(relativePath)))
			{
				return dir;
			}
		}

		return null;
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
