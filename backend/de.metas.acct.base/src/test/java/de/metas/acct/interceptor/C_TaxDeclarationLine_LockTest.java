package de.metas.acct.interceptor;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class C_TaxDeclarationLine_LockTest
{
	private C_TaxDeclarationLine interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		interceptor = new C_TaxDeclarationLine();
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

	private I_C_TaxDeclarationLine createLine(final I_C_TaxDeclaration parent)
	{
		final I_C_TaxDeclarationLine line = InterfaceWrapperHelper.newInstance(I_C_TaxDeclarationLine.class);
		line.setC_TaxDeclaration_ID(parent.getC_TaxDeclaration_ID());
		return line;
	}

	@Test
	public void unlocked_new_allowed()
	{
		final I_C_TaxDeclaration parent = createParent(false);
		final I_C_TaxDeclarationLine line = createLine(parent);

		Assertions.assertThatNoException().isThrownBy(() -> interceptor.rejectWhenParentLocked(line));
	}

	@Test
	public void unlocked_change_allowed()
	{
		final I_C_TaxDeclaration parent = createParent(false);
		final I_C_TaxDeclarationLine line = createLine(parent);
		InterfaceWrapperHelper.save(line);

		line.setDescription("changed");
		Assertions.assertThatNoException().isThrownBy(() -> interceptor.rejectWhenParentLocked(line));
	}

	@Test
	public void locked_new_rejected()
	{
		final I_C_TaxDeclaration parent = createParent(true);
		final I_C_TaxDeclarationLine line = createLine(parent);

		Assertions.assertThatThrownBy(() -> interceptor.rejectWhenParentLocked(line))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_Locked");
	}

	@Test
	public void locked_change_rejected()
	{
		final I_C_TaxDeclaration parent = createParent(false);
		final I_C_TaxDeclarationLine line = createLine(parent);
		InterfaceWrapperHelper.save(line);

		// Now lock the parent
		parent.setProcessed(true);
		InterfaceWrapperHelper.save(parent);

		line.setDescription("should be rejected");
		Assertions.assertThatThrownBy(() -> interceptor.rejectWhenParentLocked(line))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_Locked");
	}

	@Test
	public void locked_delete_rejected()
	{
		final I_C_TaxDeclaration parent = createParent(false);
		final I_C_TaxDeclarationLine line = createLine(parent);
		InterfaceWrapperHelper.save(line);

		parent.setProcessed(true);
		InterfaceWrapperHelper.save(parent);

		Assertions.assertThatThrownBy(() -> interceptor.rejectWhenParentLocked(line))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_Locked");
	}
}
