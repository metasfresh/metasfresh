package de.metas.distribution.ddorder;

import com.google.common.collect.ImmutableList;
import de.metas.dao.ValueRestriction;
import de.metas.document.engine.DocStatus;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class DDOrderQuery
{
	@Nullable DocStatus docStatus;

	@Builder.Default
	@NonNull ValueRestriction<UserId> responsibleId = ValueRestriction.any();

	@Singular
	@NonNull ImmutableList<OrderBy> orderBys;

	//
	//
	//

	public enum OrderBy
	{
		PriorityRule,
		DatePromised,
	}
}
