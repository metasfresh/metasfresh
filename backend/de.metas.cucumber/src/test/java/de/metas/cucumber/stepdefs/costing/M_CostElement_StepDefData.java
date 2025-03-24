package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableSet;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingMethod;
import de.metas.costing.impl.CostElementRepository;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;

import java.util.Set;

public class M_CostElement_StepDefData extends StepDefData<CostElement>
		implements StepDefDataGetIdAware<CostElementId, CostElement>
{
	private final CostElementRepository costElementRepository = SpringContextHolder.instance.getBean(CostElementRepository.class);

	public M_CostElement_StepDefData() {super(CostElement.class);}

	@Override
	public CostElementId extractIdFromRecord(final CostElement costElement) {return costElement.getId();}

	public final Set<CostElementId> getIdsOfCommaSeparatedString(@NonNull final String commaSeparatedString)
	{
		return StepDefDataIdentifier.ofCommaSeparatedString(commaSeparatedString)
				.stream()
				.map(this::getIdOrLoadNow)
				.collect(ImmutableSet.toImmutableSet());
	}

	private CostElementId getIdOrLoadNow(final StepDefDataIdentifier identifier)
	{
		return getOrLoadNow(identifier).getId();
	}

	private CostElement getOrLoadNow(final StepDefDataIdentifier identifier)
	{
		final CostElement costElement = getOptional(identifier).orElse(null);
		if (costElement != null)
		{
			return costElement;
		}

		CostingMethod costingMethod = toCostingMethodIfMatches(identifier);
		if (costingMethod != null)
		{
			return costElementRepository.getOrCreateMaterialCostElement(ClientId.METASFRESH, costingMethod);
		}

		throw new AdempiereException("No CostElement found for " + identifier);
	}

	private CostingMethod toCostingMethodIfMatches(final StepDefDataIdentifier identifier)
	{
		final String identifierStr = identifier.getAsString();

		for (final CostingMethod costingMethod : CostingMethod.values())
		{
			if (costingMethod.name().equals(identifierStr))
			{
				return costingMethod;
			}
			else if (costingMethod.getCode().equals(identifierStr))
			{
				return costingMethod;
			}
		}

		return null;
	}
}
