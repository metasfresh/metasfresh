package de.metas.payment.esr.dataimporter;

import de.metas.payment.esr.model.I_ESR_ImportLine;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The match-error messages on an ESR import line must not accumulate duplicates.
 * <p>
 * Every {@code evaluate*} entry point of {@link ESRDataLoaderUtil} is re-runnable by design -- the import
 * evaluates a line, and a later {@code @ModelChange} interceptor evaluates it again -- so an unconditional
 * append produces the same sentence twice in {@code MatchErrorMsg}, which is what the accountant reads.
 */
public class ESRDataLoaderUtilTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	private I_ESR_ImportLine newLine()
	{
		return newInstance(I_ESR_ImportLine.class);
	}

	@Nested
	@DisplayName("addMatchErrorMsg is idempotent per message")
	class Idempotent
	{
		@Test
		void sameMessageTwice_storedOnce()
		{
			final I_ESR_ImportLine line = newLine();
			final String msg = "Die importierte ESR-Referenznummer 000000000202183482010101694 wurde nicht in der Datenbank gefunden";

			ESRDataLoaderUtil.addMatchErrorMsg(line, msg);
			ESRDataLoaderUtil.addMatchErrorMsg(line, msg);

			assertThat(line.getMatchErrorMsg())
					.as("the same match error must be stored once, however often the line is evaluated")
					.isEqualTo(msg);
		}

		@Test
		void differentMessages_bothKept()
		{
			final I_ESR_ImportLine line = newLine();

			ESRDataLoaderUtil.addMatchErrorMsg(line, "first problem");
			ESRDataLoaderUtil.addMatchErrorMsg(line, "second problem");

			assertThat(line.getMatchErrorMsg())
					.as("de-duplication must not swallow a genuinely different message")
					.contains("first problem")
					.contains("second problem");
		}

		@Test
		void repeatedAcrossManyEvaluations_stillOnce()
		{
			final I_ESR_ImportLine line = newLine();
			final String msg = "Rechnung 103439 wurde im System als bereits bezahlt markiert";

			for (int i = 0; i < 5; i++)
			{
				ESRDataLoaderUtil.addMatchErrorMsg(line, msg);
			}

			assertThat(line.getMatchErrorMsg()).isEqualTo(msg);
		}

		@Test
		void blankMessage_doesNotCrashAndAddsNothingMeaningful()
		{
			final I_ESR_ImportLine line = newLine();

			ESRDataLoaderUtil.addMatchErrorMsg(line, "a real problem");
			ESRDataLoaderUtil.addMatchErrorMsg(line, "a real problem");

			assertThat(line.getMatchErrorMsg()).isEqualTo("a real problem");
		}
	}
}
