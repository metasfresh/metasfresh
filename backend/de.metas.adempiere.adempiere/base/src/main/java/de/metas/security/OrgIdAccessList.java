package de.metas.security;

import com.google.common.collect.ImmutableSet;
import de.metas.organization.OrgId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Iterator;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class OrgIdAccessList
{
	public static final OrgIdAccessList ALL = new OrgIdAccessList(true, ImmutableSet.of()); // NOTE: new instance to make sure it's unique
	public static final OrgIdAccessList TABLE_ALL = new OrgIdAccessList(true, ImmutableSet.of()); // NOTE: new instance to make sure it's unique;

	@Getter
	private final boolean any;

	private final ImmutableSet<OrgId> orgIds;

	private OrgIdAccessList(final boolean any, final Set<OrgId> orgIds)
	{
		this.any = any;
		this.orgIds = ImmutableSet.copyOf(orgIds);
	}

	public static OrgIdAccessList ofSet(final Set<OrgId> orgIds)
	{
		return new OrgIdAccessList(false, orgIds);
	}

	private void assertNotAny()
	{
		if (any)
		{
			throw new AdempiereException("Assumed not an ANY org access list: " + this);
		}
	}

	public Iterator<OrgId> iterator()
	{
		assertNotAny();
		return orgIds.iterator();
	}


	public boolean contains(@NonNull final OrgId orgId)
	{
		return any || orgIds.contains(orgId);
	}

	public ImmutableSet<OrgId> toSet()
	{
		assertNotAny();
		return orgIds;
	}
}
