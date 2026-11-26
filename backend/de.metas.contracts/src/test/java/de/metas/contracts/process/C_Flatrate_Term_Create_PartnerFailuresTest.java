package de.metas.contracts.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermRequest.CreateFlatrateTermRequest;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * How "Erzeuge Vertrag" ends when a partner's contract could not be created: every partner is still processed
 * (own transaction), successful contracts stay, and the action ends with ONE error naming the failed partners.
 */
class C_Flatrate_Term_Create_PartnerFailuresTest
{
	private IFlatrateBL flatrateBL;
	private I_C_Flatrate_Conditions conditions;
	private final List<String> processedPartners = new ArrayList<>();

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		flatrateBL = Mockito.mock(IFlatrateBL.class);
		Services.registerService(IFlatrateBL.class, flatrateBL);

		conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setName("c");
		saveRecord(conditions);
	}

	private I_C_BPartner partner(final String value)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue(value);
		partner.setName("name_" + value);
		saveRecord(partner);
		return partner;
	}

	/** Makes the BL fail for the given partner values and create a term for all others. */
	private void failFor(final Set<String> failingValues, final String reason)
	{
		Mockito.when(flatrateBL.createTerm(Mockito.any(CreateFlatrateTermRequest.class))).thenAnswer(invocation -> {
			final CreateFlatrateTermRequest request = invocation.getArgument(0);
			final String value = request.getBPartner().getValue();
			processedPartners.add(value);
			if (failingValues.contains(value))
			{
				throw new AdempiereException(reason + " " + value).markAsUserValidationError();
			}
			return newInstance(I_C_Flatrate_Term.class);
		});
	}

	private C_Flatrate_Term_Create process(final List<I_C_BPartner> partners)
	{
		final C_Flatrate_Term_Create process = new C_Flatrate_Term_Create()
		{
			@Override
			protected Iterable<I_C_BPartner> getBPartners()
			{
				return partners;
			}
		};
		process.setConditions(conditions);
		process.setStartDate(new java.sql.Timestamp(System.currentTimeMillis()));
		process.addProduct(null);
		return process;
	}

	@Test
	void successfulMultiPartnerRun_endsOk()
	{
		failFor(ImmutableSet.of(), "n/a");

		final String result = runDoIt(process(ImmutableList.of(partner("P1"), partner("P2"), partner("P3"))));

		assertThat(result).isEqualTo("OK");
		assertThat(processedPartners).containsExactly("P1", "P2", "P3");
	}

	@Test
	void oneFailingPartner_othersStillProcessed_oneErrorNamesTheFailedOne()
	{
		failFor(ImmutableSet.of("P2"), "overlap");
		final C_Flatrate_Term_Create process = process(ImmutableList.of(partner("P1"), partner("P2"), partner("P3")));

		assertThatThrownBy(() -> process.doIt())
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final String message = ex.getMessage();
					assertThat(message).contains("P2_name_P2: overlap P2");
					assertThat(message).doesNotContain("P1_name_P1").doesNotContain("P3_name_P3");
				});

		assertThat(processedPartners).as("the partner after the failing one is still processed").containsExactly("P1", "P2", "P3");
	}

	@Test
	void manyFailures_listIsCapped()
	{
		final List<FlatrateTermCreator.PartnerFailure> failures = new ArrayList<>();
		for (int i = 1; i <= 25; i++)
		{
			failures.add(FlatrateTermCreator.PartnerFailure.of("P" + i, "reason"));
		}

		final String message = C_Flatrate_Term_Create.buildFailuresMessage(failures).translate("en_US");

		final List<String> lines = Arrays.asList(message.split("\n"));
		assertThat(lines.stream().filter(line -> line.endsWith(": reason")).count()).as("listed partners").isEqualTo(20);
		assertThat(lines).contains("P1: reason", "P20: reason").doesNotContain("P21: reason");
		assertThat(lines.get(lines.size() - 1)).as("the 'and N more' line").contains("C_Flatrate_Term_Create_AndNMore").contains("5");
	}

	@Test
	void failureWithoutReason_noNullInMessage()
	{
		final String message = C_Flatrate_Term_Create.buildFailuresMessage(
				ImmutableList.of(FlatrateTermCreator.PartnerFailure.of("P1", null))).translate("en_US");

		assertThat(message).contains("P1").doesNotContain("null");
	}

	/**
	 * The texts of the messages go through java.text.MessageFormat: a single apostrophe would swallow the placeholders.
	 * Checks the effective (last written) text of every language of the messages used by this action and the overlap refusal.
	 */
	@Test
	void messageTexts_substituteAllPlaceholders() throws Exception
	{
		final Path dir = moduleDir().resolve("src/main/sql/postgresql/system/50-de.metas.contracts");
		final Pattern statement = Pattern.compile("(?s)(?:UPDATE AD_Message_Trl SET MsgText='((?:[^']|'')*)'.*?AD_Language='(\\w+)' AND AD_Message_ID=(\\d+))|(?:UPDATE AD_Message SET MsgText='((?:[^']|'')*)'.*?AD_Message_ID=(\\d+))|(?:INSERT INTO AD_Message \\([^)]*\\) VALUES \\(0,(\\d+) /\\*From ID Server\\*/,0,TO_TIMESTAMP\\('[^']*','[^']*'\\),100,'[^']*','Y','((?:[^']|'')*)')");
		final Map<String, String> effectiveTexts = new TreeMap<>();
		final List<Path> scripts = new ArrayList<>();
		try (java.util.stream.Stream<Path> files = Files.list(dir))
		{
			files.filter(f -> f.getFileName().toString().matches("58(26950|2841\\d|2842\\d|2843\\d)_.*\\.sql")).sorted().forEach(scripts::add);
		}
		for (final Path script : scripts)
		{
			final Matcher m = statement.matcher(new String(Files.readAllBytes(script), StandardCharsets.UTF_8));
			while (m.find())
			{
				if (m.group(1) != null)
				{
					effectiveTexts.put(m.group(3) + "/" + m.group(2), m.group(1).replace("''", "'"));
				}
				else if (m.group(7) != null)
				{
					effectiveTexts.put(m.group(6) + "/base", m.group(7).replace("''", "'"));
				}
				else
				{
					effectiveTexts.put(m.group(5) + "/base", m.group(4).replace("''", "'"));
				}
			}
		}

		assertThat(effectiveTexts.keySet()).as("fr_CH text of the failures heading is covered").contains("545912/fr_CH", "545877/fr_CH", "545913/fr_CH", "545912/base", "545913/base", "545877/base");
		effectiveTexts.forEach((key, text) -> {
			final String formatted = MessageFormat.format(text, "A", "B", "C", "D");
			assertThat(formatted).as(key + ": " + text).doesNotContain("{");
		});
	}

	/** The de.metas.contracts module directory, found from the test class location, so the test does not depend on the working directory. */
	private static Path moduleDir() throws Exception
	{
		Path dir = Paths.get(C_Flatrate_Term_Create_PartnerFailuresTest.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		while (dir != null && !Files.isDirectory(dir.resolve("src/main/sql")))
		{
			dir = dir.getParent();
		}
		assertThat(dir).as("module directory above the test classes").isNotNull();
		return dir;
	}

	private String runDoIt(final C_Flatrate_Term_Create process)
	{
		try
		{
			return process.doIt();
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
