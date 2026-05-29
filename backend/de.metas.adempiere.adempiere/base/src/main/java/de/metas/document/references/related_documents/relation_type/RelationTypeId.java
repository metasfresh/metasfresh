/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.document.references.related_documents.relation_type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_AD_RelationType;

import javax.annotation.Nullable;

/**
 * Value object representing a Relation Type ID (AD_RelationType).
 * Used to type-safely pass relation type references throughout the system.
 *
 * @see I_AD_RelationType
 */
@Value
public class RelationTypeId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static RelationTypeId ofRepoId(final int repoId)
	{
		return new RelationTypeId(repoId);
	}

	@Nullable
	public static RelationTypeId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new RelationTypeId(repoId) : null;
	}

	public static int toRepoId(@Nullable final RelationTypeId relationTypeId)
	{
		return relationTypeId != null ? relationTypeId.getRepoId() : -1;
	}

	private RelationTypeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_AD_RelationType.COLUMNNAME_AD_RelationType_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
