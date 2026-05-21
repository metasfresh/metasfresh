package de.metas.acct.interceptor;

import de.metas.acct.tax.TaxDeclarationRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_TaxDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class C_TaxDeclaration_LockTest
{
	private C_TaxDeclaration interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		interceptor = new C_TaxDeclaration(new TaxDeclarationRepository());
	}

	@Test
	public void unlocked_change_allowed()
	{
		final I_C_TaxDeclaration decl = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		decl.setProcessed(false);
		InterfaceWrapperHelper.save(decl);

		decl.setDescription("changed");
		Assertions.assertThatNoException().isThrownBy(() -> interceptor.rejectEditsWhenLocked(decl));
	}

	@Test
	public void locked_change_rejected_with_TaxDeclaration_Locked()
	{
		// Save with Processed=N so the repository has a saved state
		final I_C_TaxDeclaration decl = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		decl.setProcessed(false);
		InterfaceWrapperHelper.save(decl);

		// Lock: set Processed=Y and save — saved state is now Y
		decl.setProcessed(true);
		InterfaceWrapperHelper.save(decl);

		// Attempt another edit while still Processed=Y — old=Y, new=Y → must throw
		decl.setDescription("should be rejected");
		Assertions.assertThatThrownBy(() -> interceptor.rejectEditsWhenLocked(decl))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_Locked");
	}

	@Test
	public void locked_delete_rejected()
	{
		final I_C_TaxDeclaration decl = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		decl.setProcessed(false);
		InterfaceWrapperHelper.save(decl);

		decl.setProcessed(true);
		InterfaceWrapperHelper.save(decl);

		Assertions.assertThatThrownBy(() -> interceptor.deleteTaxDeclarationLinesAndAccts(decl))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TaxDeclaration_Locked");
	}

	@Test
	public void unlocked_delete_allowed()
	{
		final I_C_TaxDeclaration decl = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		decl.setProcessed(false);
		InterfaceWrapperHelper.save(decl);

		Assertions.assertThatNoException().isThrownBy(() -> interceptor.deleteTaxDeclarationLinesAndAccts(decl));
	}
}
