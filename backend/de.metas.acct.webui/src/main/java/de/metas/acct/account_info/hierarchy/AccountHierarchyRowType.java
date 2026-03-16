package de.metas.acct.account_info.hierarchy;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.ui.web.view.IViewRowType;

enum AccountHierarchyRowType implements IViewRowType
{
	L1("L1"),
	L2("L2"),
	L3("L3"),
	L4("L4"),
	L5("L5"),
	ACCOUNT("Account");

	private final String name;

	AccountHierarchyRowType(final String name)
	{
		this.name = name;
	}

	@Override
	@JsonValue
	public String getName() { return name; }

	static AccountHierarchyRowType ofLevel(final int level)
	{
		switch (level)
		{
			case 1: return L1;
			case 2: return L2;
			case 3: return L3;
			case 4: return L4;
			case 5: return L5;
			default: return ACCOUNT;
		}
	}

	boolean isSummary() { return this != ACCOUNT; }
}
