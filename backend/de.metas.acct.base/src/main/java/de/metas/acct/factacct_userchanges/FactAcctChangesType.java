package de.metas.acct.factacct_userchanges;

import de.metas.acct.model.X_Fact_Acct_UserChange;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public enum FactAcctChangesType implements ReferenceListAwareEnum
{
	Change(X_Fact_Acct_UserChange.CHANGETYPE_Change),
	Add(X_Fact_Acct_UserChange.CHANGETYPE_Add),
	Delete(X_Fact_Acct_UserChange.CHANGETYPE_Delete),
	;

	@NonNull @Getter private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<FactAcctChangesType> index = ReferenceListAwareEnums.index(values());

	public static FactAcctChangesType ofCode(@NonNull String code) {return index.ofCode(code);}

	public boolean isAdd() {return Add.equals(this);}

	public boolean isChange() {return Change.equals(this);}

	public boolean isDelete() {return Delete.equals(this);}

	public boolean isChangeOrDelete() {return isChange() || isDelete();}

}
