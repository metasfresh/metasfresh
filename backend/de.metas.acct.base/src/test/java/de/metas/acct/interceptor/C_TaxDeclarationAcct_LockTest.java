package de.metas.acct.interceptor;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class C_TaxDeclarationAcct_LockTest
{
	private C_TaxDeclarationAcct interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		interceptor = new C_TaxDeclarationAcct();
	}

	private I_C_TaxDeclaration createParent(final boolean processed)
	{
		final I_C_TaxDeclaration parent = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		parent.setProcessed(false);
		InterfaceWrapperHelper.save(parent);
		if (processed)
		{
			parent.setProcessed(true);
			InterfaceWrapperHelper.save(parent);
		}
		return parent;
	}

	private I_C_TaxDeclarationAcct createAcct(final I_C_TaxDeclaration parent)
	{
		final I_C_TaxDeclarationAcct acct = InterfaceWrapperHelper.newInstance(I_C_TaxDeclarationAcct.class);
		acct.setC_TaxDeclaration_ID(parent.getC_TaxDeclaration_ID());
		return acct;
	}

	@Test
	public void unlocked_new_allowed()
	{
		final I_C_TaxDeclaration parent = createParent(false);
		final I_C_TaxDeclarationAcct acct = createAcct(parent);

		Assertions.assertThatNoException().isThrownBy(() -> interceptor.rejectWhenParentLocked(acct));
	}

	@Test
	public void unlocked_change_allowed()
	{
		final I_C_TaxDeclaration parent = createParent(false);
		final I_C_TaxDeclarationAcct acct = createAcct(parent);
		InterfaceWrapperHelper.save(acct);

		acct.setDescription("changed");
		Assertions.assertThatNoException().isThrownBy(() -> interceptor.rejectWhenParentLocked(acct));
	}

	@Test
	public void locked_new_rejected()
	{
		final I_C_TaxDeclaration parent = createParent(true);
		final I_C_TaxDeclarationAcct acct = createAcct(parent);

		Assertions.assertThatThrownBy(() -> interceptor.rejectWhenParentLocked(acct))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_Locked");
	}

	@Test
	public void locked_change_rejected()
	{
		final I_C_TaxDeclaration parent = createParent(false);
		final I_C_TaxDeclarationAcct acct = createAcct(parent);
		InterfaceWrapperHelper.save(acct);

		// Now lock the parent
		parent.setProcessed(true);
		InterfaceWrapperHelper.save(parent);

		acct.setDescription("should be rejected");
		Assertions.assertThatThrownBy(() -> interceptor.rejectWhenParentLocked(acct))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_Locked");
	}

	@Test
	public void locked_delete_rejected()
	{
		final I_C_TaxDeclaration parent = createParent(false);
		final I_C_TaxDeclarationAcct acct = createAcct(parent);
		InterfaceWrapperHelper.save(acct);

		parent.setProcessed(true);
		InterfaceWrapperHelper.save(parent);

		Assertions.assertThatThrownBy(() -> interceptor.rejectWhenParentLocked(acct))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_Locked");
	}
}
