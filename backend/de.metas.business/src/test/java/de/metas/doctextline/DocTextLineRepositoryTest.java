package de.metas.doctextline;

import de.metas.order.OrderId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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
					.articleLineExistsBeforeNewPosition(true)
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
					.articleLineExistsBeforeNewPosition(false)
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
					.articleLineExistsBeforeNewPosition(false)
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
					.articleLineExistsBeforeNewPosition(false)
					.build());

			assertThat(docTextLineRepository.getByDocument(documentRef))
					.extracting(DocTextLine::getId)
					.containsExactly(result.getId());
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
					.articleLineExistsBeforeNewPosition(false)
					.build());
			final DocTextLine second = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(first.getLine())
					.referencePosition(BigDecimal.valueOf(30))
					.articleLineExistsBeforeNewPosition(false)
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
					.articleLineExistsBeforeNewPosition(false)
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
					.articleLineExistsBeforeNewPosition(false)
					.build());
			final DocTextLine toKeep = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(toDelete.getLine())
					.referencePosition(BigDecimal.valueOf(10))
					.articleLineExistsBeforeNewPosition(false)
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
					.articleLineExistsBeforeNewPosition(false)
					.build());
			final DocTextLine first = docTextLineRepository.insertAbove(requestBuilder()
					.previousPosition(null)
					.referencePosition(last.getLine())
					.articleLineExistsBeforeNewPosition(false)
					.build());

			assertThat(docTextLineRepository.getByDocument(documentRef))
					.extracting(DocTextLine::getId)
					.containsExactly(first.getId(), last.getId());
		}
	}
}
