package de.metas.doctextline;

import de.metas.order.OrderId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DocTextLineRepositoryTest
{
	private DocTextLineRepository docTextLineRepository;
	private DocTextLineDocumentRef documentRef;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		docTextLineRepository = new DocTextLineRepository();

		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);
		documentRef = DocTextLineDocumentRef.ofOrderId(OrderId.ofRepoId(order.getC_Order_ID()));
	}

	private InsertAboveRequest.InsertAboveRequestBuilder requestBuilder()
	{
		return InsertAboveRequest.builder()
				.documentRef(documentRef)
				.textLine("some text");
	}

	@Nested
	class insertAbove
	{
		@Test
		void midpointBetweenTwoAdjacentIntegers()
		{
			final DocTextLine result = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(BigDecimal.valueOf(10))
					.referencePosition(BigDecimal.valueOf(11))
					.articleLineExistsBeforeReferencePosition(true)
					.build());

			assertThat(result.getLine()).isEqualByComparingTo("10.5");
			assertThat(result.getScope()).isEqualTo(TextLineScope.Following);
		}

		@Test
		void insertAboveTheFirstRow()
		{
			final DocTextLine result = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(BigDecimal.valueOf(10))
					.articleLineExistsBeforeReferencePosition(false)
					.build());

			assertThat(result.getLine()).isEqualByComparingTo("5");
			assertThat(result.getScope()).isEqualTo(TextLineScope.Document);
		}

		@Test
		void insertIntoAnEmptyDocument()
		{
			final DocTextLine result = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(null)
					.articleLineExistsBeforeReferencePosition(false)
					.build());

			assertThat(result.getLine()).isEqualByComparingTo("1");
			assertThat(result.getScope()).isEqualTo(TextLineScope.Document);
		}

		@Test
		void persistsTheRowRetrievableViaGetByDocument()
		{
			final DocTextLine result = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(null)
					.articleLineExistsBeforeReferencePosition(false)
					.build());

			assertThat(docTextLineRepository.getByDocument(documentRef))
					.extracting(DocTextLine::getId)
					.containsExactly(result.getId());
		}

		@Test
		void throwsWhenThePositionGapIsExhausted()
		{
			// the minimum representable gap at scale 4: (10.0000 + 10.0001) / 2 = 10.00005 -> HALF_UP -> 10.0001,
			// exactly equal to referencePosition -- nothing left to round to
			assertThatThrownBy(() -> docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(new BigDecimal("10.0000"))
					.referencePosition(new BigDecimal("10.0001"))
					.articleLineExistsBeforeReferencePosition(true)
					.build()))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void tiedPositionsThrowBeforeScopeIsDerived()
		{
			// pins the invariant articleLineExistsBefore's javadoc names: a literal tie between previousPosition
			// and referencePosition -- the one input where "does an article precede the merged-order index" and
			// "is any article position strictly less than referencePosition" could disagree -- is refused HERE,
			// before the ternary that reads articleLineExistsBeforeReferencePosition ever runs. Passing true (as
			// a caller wrongly believing an article precedes, the divergent answer) proves the derivation never
			// gets that far: reaching it would persist a 'Following' row instead of throwing.
			assertThatThrownBy(() -> docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(BigDecimal.valueOf(10))
					.referencePosition(BigDecimal.valueOf(10))
					.articleLineExistsBeforeReferencePosition(true)
					.build()))
					.isInstanceOf(AdempiereException.class);

			// the comment above claims more than "it throws" -- it claims nothing was persisted either. Pin that
			// half too: a save-before-guard reordering would still throw (the collision guard doesn't care when
			// it runs) but would leave a 'Following' row behind, and this line is what catches that.
			assertThat(docTextLineRepository.getByDocument(documentRef)).isEmpty();
		}

		@Test
		void roundsHalfUpOnATie()
		{
			// (10.0002 + 10.0007) / 2 = 10.00045 -- a genuine tie at the 5th decimal, only reachable this way
			// because the divisor is always exactly 2: dividing a scale-4 sum by 2 either terminates within
			// scale 4 (no rounding needed) or leaves exactly one further digit "5" (an exact tie). HALF_DOWN/DOWN
			// would round down to 10.0004; HALF_UP rounds away from zero to 10.0005.
			final DocTextLine result = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(new BigDecimal("10.0002"))
					.referencePosition(new BigDecimal("10.0007"))
					.articleLineExistsBeforeReferencePosition(true)
					.build());

			assertThat(result.getLine()).isEqualByComparingTo("10.0005");
		}
	}

	@Nested
	class articleLineExistsBefore
	{
		@Test
		void trueWhenAnArticlePositionPrecedesTheReference()
		{
			final boolean result = DocTextLineRepository.articleLineExistsBefore(
					Collections.singletonList(new BigDecimal("10")),
					new BigDecimal("20"));

			assertThat(result).isTrue();
		}

		@Test
		void falseWhenNoPositionsAreGiven()
		{
			final boolean result = DocTextLineRepository.articleLineExistsBefore(
					Collections.emptyList(),
					new BigDecimal("20"));

			assertThat(result).isFalse();
		}

		@Test
		void falseWhenEveryArticlePositionIsAtOrAfterTheReference()
		{
			// an article at the SAME position, and one strictly after -- neither precedes the reference
			final boolean result = DocTextLineRepository.articleLineExistsBefore(
					Arrays.asList(new BigDecimal("20"), new BigDecimal("30")),
					new BigDecimal("20"));

			assertThat(result).isFalse();
		}

		@Test
		void trueWhenPositionsAreMixedBeforeAndAtOrAfterTheReference()
		{
			// one position before, one at-or-after -- only one of them needs to precede
			final boolean result = DocTextLineRepository.articleLineExistsBefore(
					Arrays.asList(new BigDecimal("10"), new BigDecimal("20"), new BigDecimal("30")),
					new BigDecimal("20"));

			assertThat(result).isTrue();
		}

		@Test
		void falseWhenAnEqualPositionHasADifferentScale()
		{
			// the WebUI feeds scale-0 article positions (BigDecimal.valueOf(int)) against a reference that can be
			// a text row's scale-4 Line -- these two are compareTo-equal despite the different scale, and the
			// method must still treat "equal" as "not before". A refactor to .equals() (or a BigDecimal
			// set/contains form) would treat them as unequal and could flip this outcome; compareTo must not.
			final boolean result = DocTextLineRepository.articleLineExistsBefore(
					Collections.singletonList(new BigDecimal("10")),
					new BigDecimal("10.0000"));

			assertThat(result).isFalse();
		}
	}

	@Nested
	class swapPositions
	{
		@Test
		void swapsTheTwoPositions()
		{
			final DocTextLine first = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(BigDecimal.valueOf(30))
					.articleLineExistsBeforeReferencePosition(false)
					.build());
			final DocTextLine second = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(first.getLine())
					.referencePosition(BigDecimal.valueOf(30))
					.articleLineExistsBeforeReferencePosition(false)
					.build());

			docTextLineRepository.swapPositions(first.getId(), second.getId());

			final List<DocTextLine> lines = docTextLineRepository.getByDocument(documentRef);
			assertThat(lines).extracting(DocTextLine::getId).containsExactly(second.getId(), first.getId());
			assertThat(byId(lines, first.getId()).getLine()).isEqualByComparingTo(second.getLine());
			assertThat(byId(lines, second.getId()).getLine()).isEqualByComparingTo(first.getLine());
		}

		private DocTextLine byId(final List<DocTextLine> lines, final DocTextLineId id)
		{
			return lines.stream().filter(l -> l.getId().equals(id)).findFirst().orElseThrow(IllegalStateException::new);
		}
	}

	@Nested
	class deleteById
	{
		@Test
		void removesTheRow()
		{
			final DocTextLine line = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(null)
					.articleLineExistsBeforeReferencePosition(false)
					.build());

			docTextLineRepository.deleteById(line.getId());

			assertThat(docTextLineRepository.getByDocument(documentRef)).isEmpty();
		}

		@Test
		void doesNotAffectOtherRows()
		{
			final DocTextLine toDelete = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(BigDecimal.valueOf(10))
					.articleLineExistsBeforeReferencePosition(false)
					.build());
			final DocTextLine toKeep = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(toDelete.getLine())
					.referencePosition(BigDecimal.valueOf(10))
					.articleLineExistsBeforeReferencePosition(false)
					.build());

			docTextLineRepository.deleteById(toDelete.getId());

			assertThat(docTextLineRepository.getByDocument(documentRef))
					.extracting(DocTextLine::getId)
					.containsExactly(toKeep.getId());
		}
	}

	@Nested
	class getByDocument
	{
		@Test
		void returnsRowsOrderedByPosition()
		{
			final DocTextLine last = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(BigDecimal.valueOf(30))
					.articleLineExistsBeforeReferencePosition(false)
					.build());
			final DocTextLine first = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(last.getLine())
					.articleLineExistsBeforeReferencePosition(false)
					.build());

			assertThat(docTextLineRepository.getByDocument(documentRef))
					.extracting(DocTextLine::getId)
					.containsExactly(first.getId(), last.getId());
		}

		@Test
		void breaksTiesById()
		{
			// insertAbove's own collision guard only ever sees ONE pair of neighbours per call, so it cannot
			// catch two independent empty-document inserts landing on the same FIRST_POSITION_IN_EMPTY_DOCUMENT --
			// this is the resulting tie getByDocument must still order deterministically.
			final DocTextLine older = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(null)
					.articleLineExistsBeforeReferencePosition(false)
					.build());
			final DocTextLine newer = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(null)
					.articleLineExistsBeforeReferencePosition(false)
					.build());
			assertThat(older.getLine()).isEqualByComparingTo(newer.getLine());

			assertThat(docTextLineRepository.getByDocument(documentRef))
					.extracting(DocTextLine::getId)
					.containsExactly(older.getId(), newer.getId());
		}
	}
}
