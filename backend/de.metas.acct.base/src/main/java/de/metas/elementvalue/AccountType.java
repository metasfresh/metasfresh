package de.metas.elementvalue;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_ElementValue;

@AllArgsConstructor
public enum AccountType implements ReferenceListAwareEnum
{
	Asset(X_C_ElementValue.ACCOUNTTYPE_Asset),
	Liability(X_C_ElementValue.ACCOUNTTYPE_Liability),
	Revenue(X_C_ElementValue.ACCOUNTTYPE_Revenue),
	Expense(X_C_ElementValue.ACCOUNTTYPE_Expense),
	OwnerSEquity(X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity),
	Memo(X_C_ElementValue.ACCOUNTTYPE_Memo),
	;

	@Getter @NonNull String code;

	private static final ReferenceListAwareEnums.ValuesIndex<AccountType> index = ReferenceListAwareEnums.index(values());

	public static AccountType ofCode(@NonNull String code) {return index.ofCode(code);}

	public boolean isBalanceSheet() {return Asset.equals(this) || Liability.equals(this) || OwnerSEquity.equals(this);}

	// public boolean isActiva()
	// {
	// 	return X_C_ElementValue.ACCOUNTTYPE_Asset.equals(getAccountType());
	// }
	//
	// public boolean isPassiva()
	// {
	// 	String accountType = getAccountType();
	// 	return (X_C_ElementValue.ACCOUNTTYPE_Liability.equals(accountType)
	// 			|| X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity.equals(accountType));
	// }
}
