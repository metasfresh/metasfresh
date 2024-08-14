package de.metas.cucumber.stepdefs.material.dispo;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import de.metas.cucumber.stepdefs.ItemProvider.ProviderResult;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.ddordercandidate.DD_Order_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.distributionorder.DD_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.distributionorder.DD_Order_StepDefData;
import de.metas.cucumber.stepdefs.material.dispo.MaterialDispoTableRow.DDOrderRefIdentifiers;
import de.metas.cucumber.stepdefs.material.dispo.MaterialDispoTableRow.PPOrderRefIdentifiers;
import de.metas.cucumber.stepdefs.material.dispo.MaterialDispoTableRow.Production;
import de.metas.cucumber.stepdefs.pporder.PP_OrderLine_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.ddorder.DDOrderRef;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.util.Check;
import de.metas.util.text.tabular.Table;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.assertj.core.api.SoftAssertions;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.PPOrderLineCandidateId;
import org.opentest4j.MultipleFailuresError;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
class MaterialDispoTableRowValidator
{
	@NonNull private final CandidatesToTabularStringConverter tabularConverter;
	@NonNull private final MaterialDispoDataItem_StepDefData materialDispoDataItemStepDefData;
	@NonNull private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	@NonNull private final DD_Order_Candidate_StepDefData ddOrderCandidateTable;
	@NonNull private final DD_Order_StepDefData ddOrderTable;
	@NonNull private final DD_OrderLine_StepDefData ddOrderLineTable;

	@NonNull private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	@NonNull private final PP_OrderLine_Candidate_StepDefData ppOrderLineCandidateTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;

	private SoftAssertions softly; // lazy

	public ProviderResult<MaterialDispoDataItem> findValidItem(
			@NonNull final List<MaterialDispoDataItem> items,
			@NonNull final MaterialDispoTableRow row,
			final Map<CandidateId, StepDefDataIdentifier> candidateIdsToExclude)
	{
		final ArrayList<String> resultNotFoundLogs = new ArrayList<>();
		if (items.isEmpty())
		{
			resultNotFoundLogs.add("query returned no results");
		}
		else
		{
			for (final MaterialDispoDataItem item : items)
			{
				if (candidateIdsToExclude != null && candidateIdsToExclude.containsKey(item.getCandidateId()))
				{
					resultNotFoundLogs.add("Excluded " + item.getCandidateId().getRepoId() + " because it was previously matched for identifier `" + candidateIdsToExclude.get(item.getCandidateId()) + "`");
					continue;
				}

				//
				// Exclude this item if it's already associated to a different identifier.
				final StepDefDataIdentifier otherIdentifierOfCandidate = materialDispoDataItemStepDefData.getFirstIdentifierById(item.getCandidateId(), row.getIdentifier()).orElse(null);
				if (otherIdentifierOfCandidate != null)
				{
					resultNotFoundLogs.add("Excluded " + item.getCandidateId().getRepoId() + " because it was already loaded for " + otherIdentifierOfCandidate);
					continue;
				}

				try
				{
					validate(row, item);
					return ProviderResult.resultWasFound(item);
				}
				catch (Exception | AssertionError ex)
				{
					resultNotFoundLogs.add("Candidate " + item.getCandidateId().getRepoId() + " not valid because " + extractMessage(ex));
				}
			}
		}

		return ProviderResult.resultWasNotFound(
				String.join("\n", resultNotFoundLogs)
						+ "\n\t Expected (row):"
						+ "\n" + toCandidatesTabularFromRow(row).toPrint().ident(2)
						+ "\n\t Actual items checked:"
						+ "\n" + toCandidatesTabularFromItems(items).toPrint().ident(2)
		);
	}

	public void validate(@NonNull final MaterialDispoTableRow expected, @NonNull final MaterialDispoDataItem actual)
	{
		softly = new SoftAssertions();

		softly.assertThat(actual.getType()).as("type").isEqualTo(expected.getType());
		softly.assertThat(actual.getBusinessCase()).as("businessCase").isEqualTo(expected.getBusinessCase());
		softly.assertThat(actual.getMaterialDescriptor().getProductId()).as("productId").isEqualTo(expected.getProductId().getRepoId());
		softly.assertThat(actual.getMaterialDescriptor().getDate()).as("date").isEqualTo(expected.getTime());
		softly.assertThat(actual.getMaterialDescriptor().getQuantity().abs()).as("quantity.abs").isEqualByComparingTo(expected.getQty().abs()); // using .abs() because MaterialDispoDataItem qty is negated for demand and inventory_down
		softly.assertThat(actual.getAtp()).as("ATP").isEqualByComparingTo(expected.getAtp());
		softly.assertThat(actual.isSimulated()).isEqualTo(expected.isSimulated());

		if (expected.getAttributeSetInstanceId() != null)
		{
			validateAttributeKeys(expected.getAttributeSetInstanceId(), actual.getAttributeSetInstanceId());
		}
		if (expected.getWarehouseId() != null)
		{
			softly.assertThat(actual.getMaterialDescriptor().getWarehouseId()).as("warehouseId").isEqualTo(expected.getWarehouseId());
		}

		validateDistribution(expected.getDistribution(), actual.getBusinessCaseDetail(DistributionDetail.class).orElse(null));
		validateProduction(expected.getProduction(), actual.getBusinessCaseDetail(ProductionDetail.class).orElse(null));

		assertAll();
	}

	private void assertAll()
	{
		final SoftAssertions softly = this.softly;
		this.softly = null;
		if (softly != null)
		{
			softly.assertAll();
		}
	}

	private void validateAttributeKeys(final StepDefDataIdentifier expectedAsiIdentifier, final AttributeSetInstanceId actualAsiId)
	{
		final AttributeSetInstanceId expectedAsiId = attributeSetInstanceTable.getId(expectedAsiIdentifier);
		final AttributesKey expectedAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(expectedAsiId).orElse(AttributesKey.NONE);
		final AttributesKey actualAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(actualAsiId).orElse(AttributesKey.NONE);
		softly.assertThat(actualAttributesKeys).as("AttributeKeys").isEqualTo(expectedAttributesKey);
	}

	private void validateDistribution(@Nullable MaterialDispoTableRow.Distribution expected, @Nullable DistributionDetail actual)
	{
		if (expected == null)
		{
			return;
		}

		validate_DDOrderRef("Distribution", expected.getDdOrderRef(), actual != null ? actual.getDdOrderRef() : null);
		validate_PPOrderRef("Distribution", expected.getForwardPPOrderRef(), actual != null ? actual.getForwardPPOrderRef() : null);
	}

	@SuppressWarnings("SameParameterValue")
	private void validate_DDOrderRef(@NonNull String name, @Nullable final DDOrderRefIdentifiers expected, @Nullable final DDOrderRef actual)
	{
		if (expected == null)
		{
			return;
		}

		if (expected.getDdOrderCandidateId() != null)
		{
			final DDOrderCandidateId idExpected = ddOrderCandidateTable.getIdOfNullable(expected.getDdOrderCandidateId());
			final DDOrderCandidateId idActual = actual != null ? DDOrderCandidateId.ofRepoIdOrNull(actual.getDdOrderCandidateId()) : null;
			softly.assertThat(idActual).as(() -> name + " - DD_Order_Candidate_ID").isEqualTo(idExpected);
		}
		if (expected.getDdOrderId() != null)
		{
			final DDOrderId idExpected = ddOrderTable.getIdOfNullable(expected.getDdOrderId());
			final DDOrderId idActual = actual != null ? DDOrderId.ofRepoIdOrNull(actual.getDdOrderId()) : null;
			softly.assertThat(idActual).as(() -> name + " - DD_Order_ID").isEqualTo(idExpected);
		}
		if (expected.getDdOrderLineId() != null)
		{
			final DDOrderLineId idExpected = ddOrderLineTable.getIdOfNullable(expected.getDdOrderLineId());
			final DDOrderLineId idActual = actual != null ? DDOrderLineId.ofRepoIdOrNull(actual.getDdOrderLineId()) : null;
			softly.assertThat(idActual).as(() -> name + " - DD_OrderLine_ID").isEqualTo(idExpected);
		}
	}

	private void validateProduction(@Nullable final Production expected, @Nullable final ProductionDetail actual)
	{
		if (expected == null)
		{
			return;
		}

		validate_PPOrderRef("Production", expected.getPpOrderRef(), actual != null ? actual.getPpOrderRef() : null);
	}

	private void validate_PPOrderRef(@NonNull String name, @Nullable final PPOrderRefIdentifiers expected, @Nullable final PPOrderRef actual)
	{
		if (expected == null)
		{
			return;
		}

		if (expected.getPpOrderCandidateId() != null)
		{
			final PPOrderCandidateId idExpected = ppOrderCandidateTable.getIdOfNullable(expected.getPpOrderCandidateId());
			final PPOrderCandidateId idActual = actual != null ? PPOrderCandidateId.ofRepoIdOrNull(actual.getPpOrderCandidateId()) : null;
			softly.assertThat(idActual).as(() -> name + " - PP_Order_Candidate_ID").isEqualTo(idExpected);
		}
		if (expected.getPpOrderLineCandidateId() != null)
		{
			final PPOrderLineCandidateId idExpected = ppOrderLineCandidateTable.getIdOfNullable(expected.getPpOrderLineCandidateId());
			final PPOrderLineCandidateId idActual = actual != null ? PPOrderLineCandidateId.ofRepoIdOrNull(actual.getPpOrderLineCandidateId()) : null;
			softly.assertThat(idActual).as(() -> name + " - PP_OrderLine_Candidate_ID").isEqualTo(idExpected);
		}
		if (expected.getPpOrderId() != null)
		{
			final PPOrderId idExpected = ppOrderTable.getIdOfNullable(expected.getPpOrderId());
			final PPOrderId idActual = actual != null ? actual.getPpOrderId() : null;
			softly.assertThat(idActual).as(() -> name + " - PP_Order_ID").isEqualTo(idExpected);
		}
		if (expected.getPpOrderBOMLineId() != null)
		{
			final PPOrderBOMLineId idExpected = ppOrderBOMLineTable.getIdOfNullable(expected.getPpOrderBOMLineId());
			final PPOrderBOMLineId idActual = actual != null ? actual.getPpOrderBOMLineId() : null;
			softly.assertThat(idActual).as(() -> name + " - PP_OrderBOMLine_ID").isEqualTo(idExpected);
		}
	}

	private Table toCandidatesTabularFromRow(@NonNull final MaterialDispoTableRow tableRow)
	{
		return CandidatesToTabularStringConverter.toTable(tableRow);
	}

	public Table toCandidatesTabularFromItems(final List<MaterialDispoDataItem> items)
	{
		return tabularConverter.toTableFromItems(items);
	}

	private static String extractMessage(final Throwable throwable)
	{
		if (throwable instanceof MultipleFailuresError)
		{
			return extractMessage_MultipleFailuresError((MultipleFailuresError)throwable);
		}
		else
		{
			return extractMessage_Generic(throwable);
		}
	}

	private static String extractMessage_MultipleFailuresError(final MultipleFailuresError multipleFailuresError)
	{
		final List<Throwable> failures = multipleFailuresError.getFailures();
		if (failures.isEmpty())
		{
			return "";
		}

		return failures.stream()
				.map(MaterialDispoTableRowValidator::extractMessage)
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" | "));
	}

	private static String extractMessage_Generic(final Throwable throwable)
	{
		final String message = throwable.getMessage();
		if (message == null)
		{
			return "";
		}

		final List<String> lines = Splitter.on("\n")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(message);

		return Joiner.on(" ").skipNulls().join(lines);
	}
}
