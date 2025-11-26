package de.metas.distribution.config;

import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_MobileUI_UserProfile_DD_Sort;

@RequiredArgsConstructor
@Getter
public enum DistributionJobSortingField implements ReferenceListAwareEnum
{
	Priority(X_MobileUI_UserProfile_DD_Sort.FIELDNAME_Priority, DDOrderQuery.OrderByField.PriorityRule),
	DatePromised(X_MobileUI_UserProfile_DD_Sort.FIELDNAME_DatePromised, DDOrderQuery.OrderByField.DatePromised),
	SeqNo(X_MobileUI_UserProfile_DD_Sort.FIELDNAME_SeqNo, DDOrderQuery.OrderByField.SeqNo),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<DistributionJobSortingField> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;
	@NonNull private final DDOrderQuery.OrderByField ddOrderQueryOrderByField;

	public static DistributionJobSortingField ofCode(final String code) {return index.ofCode(code);}

	public static DistributionJobSortingField ofCodeOrName(@NonNull final String code) {return index.ofCodeOrName(code);}
}
