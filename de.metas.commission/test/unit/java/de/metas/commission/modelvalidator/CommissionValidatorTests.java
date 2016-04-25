package de.metas.commission.modelvalidator;

import mockit.Mocked;
import mockit.Verifications;

import org.compiere.model.ModelValidationEngine;
import org.eevolution.model.I_HR_Process;
import org.junit.Test;

public class CommissionValidatorTests
{

	@Mocked
	public ModelValidationEngine modelValidationEngine;

	/**
	 * Checks if the validator registers for the right tables upon startup.
	 */
	@Test
	public void initialize()
	{

		final CommissionValidator commissionValidator = new CommissionValidator();

		commissionValidator.initialize(modelValidationEngine, null);

		new Verifications()
		{
			{
				modelValidationEngine.addDocValidate(I_HR_Process.Table_Name, commissionValidator);
			}
		};
	}
}
