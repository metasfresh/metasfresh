package de.metas.material.dispo.commons.candidate;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class CandidatesGroup implements Iterable<Candidate>
{
	public static final CandidatesGroup EMPTY = new CandidatesGroup(ImmutableList.of());

	private final ImmutableList<Candidate> list;

	private CandidatesGroup(@NonNull final ImmutableList<Candidate> list)
	{
		this.list = list;
	}

	public static CandidatesGroup ofList(@NonNull final List<Candidate> list)
	{
		return list.isEmpty() ? EMPTY : new CandidatesGroup(ImmutableList.copyOf(list));
	}

	public static CandidatesGroup of(@NonNull final Candidate... array) {return ofList(ImmutableList.copyOf(array));}

	public static Collector<Candidate, ?, CandidatesGroup> collect() {return GuavaCollectors.collectUsingListAccumulator(CandidatesGroup::ofList);}

	public void assertNotEmpty()
	{
		if (list.isEmpty())
		{
			throw new AdempiereException("candidates list shall not be empty");
		}
	}

	public boolean isEmpty() {return list.isEmpty();}

	@Override
	@NonNull
	public Iterator<Candidate> iterator() {return list.iterator();}

	@Override
	public void forEach(@NonNull final Consumer<? super Candidate> action) {list.forEach(action);}

	public ClientAndOrgId getClientAndOrgId()
	{
		assertNotEmpty();
		return list.get(0).getClientAndOrgId();
	}

	public MaterialDispoGroupId getEffectiveGroupId()
	{
		assertNotEmpty();
		return list.get(0).getEffectiveGroupId();
	}

	public CandidateBusinessCase getBusinessCase()
	{
		assertNotEmpty();
		return CollectionUtils.extractSingleElement(list, Candidate::getBusinessCase);
	}

	public Candidate getSingleCandidate()
	{
		return CollectionUtils.singleElement(list);
	}

	public Candidate getSingleSupplyCandidate()
	{
		return CollectionUtils.singleElement(list, candidate -> CandidateType.equals(candidate.getType(), CandidateType.SUPPLY));
	}

	public Candidate getSingleDemandCandidate()
	{
		return CollectionUtils.singleElement(list, candidate -> CandidateType.equals(candidate.getType(), CandidateType.DEMAND));
	}

}
