package de.metas.handlingunits.grai;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GRAITest
{
	@Nested
	class OfCanonicalString
	{
		@Test
		void valid()
		{
			final GRAI grai = GRAI.ofCanonicalString("7613204.00307.1234567890");
			assertThat(grai.toCanonicalString()).isEqualTo("7613204.00307.1234567890");
		}

		@Test
		void trimmed()
		{
			final GRAI grai = GRAI.ofCanonicalString("  7613204.00307.1234567890  ");
			assertThat(grai.toCanonicalString()).isEqualTo("7613204.00307.1234567890");
		}

		@Test
		void rejectsBlank()
		{
			assertThatThrownBy(() -> GRAI.ofCanonicalString("  "))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void rejectsNonCanonical_noDots()
		{
			assertThatThrownBy(() -> GRAI.ofCanonicalString("800307613264003095100691412000"))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void rejectsNonCanonical_twoParts()
		{
			assertThatThrownBy(() -> GRAI.ofCanonicalString("7613204.00307"))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void rejectsNonCanonical_emptyPart()
		{
			assertThatThrownBy(() -> GRAI.ofCanonicalString("7613204..1234567890"))
					.isInstanceOf(AdempiereException.class);
		}
	}

	@Nested
	class OfNullableCanonicalString
	{
		@Test
		void returnsNull_forNull()
		{
			assertThat(GRAI.ofNullableCanonicalString(null)).isNull();
		}

		@Test
		void returnsNull_forBlank()
		{
			assertThat(GRAI.ofNullableCanonicalString("  ")).isNull();
		}

		@Test
		void returnsGrai_forValid()
		{
			assertThat(GRAI.ofNullableCanonicalString("7613204.00307.1234567890"))
					.isEqualTo(GRAI.ofCanonicalString("7613204.00307.1234567890"));
		}

		@Test
		void rejectsNonCanonical()
		{
			assertThatThrownBy(() -> GRAI.ofNullableCanonicalString("not-canonical"))
					.isInstanceOf(AdempiereException.class);
		}
	}

	@Nested
	class Parse
	{
		@Test
		void canonical()
		{
			assertThat(GRAI.parse("7613204.00307.1234567890"))
					.isEqualTo(GRAI.ofCanonicalString("7613204.00307.1234567890"));
		}

		@Test
		void canonical_trimmed()
		{
			assertThat(GRAI.parse("  7613204.00307.1234567890  "))
					.isEqualTo(GRAI.ofCanonicalString("7613204.00307.1234567890"));
		}

		@Test
		void gs1_withAIPrefix()
		{
			// AI 8003 + padding(0) + 12-digit asset reference (company prefix 7613204 + asset type 00309) + check digit 5 (dropped) + serial(100691412000)
			final GRAI result = GRAI.parse("800307613204003095100691412000");
			assertThat(result).isNotNull();
			assertThat(result.toCanonicalString()).isEqualTo("7613204.00309.100691412000");
		}

		@Test
		void gs1_withoutAIPrefix()
		{
			// Same as above but without the "8003" prefix
			final GRAI result = GRAI.parse("07613204003095100691412000");
			assertThat(result).isNotNull();
			assertThat(result.toCanonicalString()).isEqualTo("7613204.00309.100691412000");
		}

		@Test
		void gs1_migrosA_firstBarcode()
		{
			// Real barcode from GRAI_A-Gebinde.pdf (MIGROS A, asset type 00309, serial 100691412000) — per the Migros-Genossenschaftsbund GRAI encoding spec (epcis/customer-docs/Migros_GRAI/GRAI Kodierung.pdf)
			final GRAI result = GRAI.parse("800307613264003095100691412000");
			assertThat(result).isNotNull();
			assertThat(result.toCanonicalString()).isEqualTo("7613264.00309.100691412000");
		}

		@Test
		void gs1_migrosA_lastBarcode()
		{
			// Real barcode from GRAI_A-Gebinde.pdf (MIGROS A, serial 100691412031)
			final GRAI result = GRAI.parse("800307613264003095100691412031");
			assertThat(result).isNotNull();
			assertThat(result.toCanonicalString()).isEqualTo("7613264.00309.100691412031");
		}

		@Test
		void gs1_migrosB_firstBarcode()
		{
			// Real barcode from GRAI_B-Gebinde.pdf (MIGROS B, asset type 00307)
			final GRAI result = GRAI.parse("800307613264003071100691412000");
			assertThat(result).isNotNull();
			assertThat(result.toCanonicalString()).isEqualTo("7613264.00307.100691412000");
		}

		@Test
		void gs1_migrosB_lastBarcode()
		{
			// Real barcode from GRAI_B-Gebinde.pdf (MIGROS B, serial 100691412031)
			final GRAI result = GRAI.parse("800307613264003071100691412031");
			assertThat(result).isNotNull();
			assertThat(result.toCanonicalString()).isEqualTo("7613264.00307.100691412031");
		}

		@Test
		void gs1_migrosA_and_migrosB_differentAssetType()
		{
			// MIGROS A and MIGROS B share company prefix but have different asset types
			final GRAI a = Objects.requireNonNull(GRAI.parse("800307613264003095100691412000"));
			final GRAI b = Objects.requireNonNull(GRAI.parse("800307613264003071100691412000"));
			assertThat(a).isNotEqualTo(b);
			assertThat(a.toCanonicalString()).isEqualTo("7613264.00309.100691412000");
			assertThat(b.toCanonicalString()).isEqualTo("7613264.00307.100691412000");
		}

		@Test
		void returnsNull_forNull()
		{
			assertThat(GRAI.parse(null)).isNull();
		}

		@Test
		void returnsNull_forBlank()
		{
			assertThat(GRAI.parse("  ")).isNull();
		}

		@Test
		void returnsNull_forGarbage()
		{
			assertThat(GRAI.parse("hello")).isNull();
		}

		@Test
		void returnsNull_forTooShortGS1()
		{
			assertThat(GRAI.parse("8003012345")).isNull();
		}

		/**
		 * me03#29827 — customer-reported bug. Scanning the raw barcode
		 * {@code 800307613264003095100691412003} originally produced
		 * {@code 7613264.003095.00691412003}, which had two problems against
		 * the GS1 EPCIS "Pure Identity" URI canonical:
		 * <ol>
		 *   <li>The leading {@code 1} of the serial was dropped (the
		 *       parser treated position 14 as a separate check digit and
		 *       skipped it).</li>
		 *   <li>The asset-type segment was 6 characters long because it
		 *       included the GS1 check digit, instead of the 5-char form
		 *       that the EPCIS URI {@code urn:epc:id:grai:7613264.00309.100691412003}
		 *       and {@link DummyGRAITemplate#MIGROS_ASSET_TYPE} both use.</li>
		 * </ol>
		 * Canonical form per the Migros GRAI encoding spec
		 * ({@code epcis/customer-docs/Migros_GRAI/GRAI Kodierung.pdf}):
		 * {@code companyPrefix(7).assetType(5).serial} with the GS1 check
		 * digit at GTIN-13 position 13 omitted from the canonical.
		 */
		@Test
		void gs1_me03_29827_canonicalMatchesEPCISPureIdentityURI()
		{
			final GRAI result = GRAI.parse("800307613264003095100691412003");
			assertThat(result).isNotNull();
			assertThat(result.toCanonicalString())
					.isEqualTo("7613264.00309.100691412003");
		}
	}

	@Nested
	class ToCanonicalString
	{
		@Test
		void roundtrip()
		{
			final String canonical = "7613204.00307.1234567890";
			assertThat(GRAI.ofCanonicalString(canonical).toCanonicalString()).isEqualTo(canonical);
		}

		@Test
		void fromGS1_returnsCanonical()
		{
			final GRAI grai = GRAI.parse("800307613204003095100691412000");
			assertThat(grai).isNotNull();
			assertThat(grai.toCanonicalString()).isEqualTo("7613204.00309.100691412000");
		}
	}

	@Nested
	class CompareTo
	{
		@Test
		void lexicographic()
		{
			final GRAI a = GRAI.ofCanonicalString("7613204.00307.0000000001");
			final GRAI b = GRAI.ofCanonicalString("7613204.00307.0000000002");

			assertThat(a).isLessThan(b);
			assertThat(b).isGreaterThan(a);
			assertThat(a).isEqualByComparingTo(a);
		}
	}

	@Nested
	class Equality
	{
		@Test
		void sameCanonical_areEqual()
		{
			assertThat(GRAI.ofCanonicalString("7613204.00307.123"))
					.isEqualTo(GRAI.ofCanonicalString("7613204.00307.123"));
		}

		@Test
		void differentCanonical_areNotEqual()
		{
			assertThat(GRAI.ofCanonicalString("7613204.00307.123"))
					.isNotEqualTo(GRAI.ofCanonicalString("7613204.00307.456"));
		}
	}
}
