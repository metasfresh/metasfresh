package de.metas.acct.interceptor;

import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_TaxDeclarationLine.class)
public class C_TaxDeclarationLine
{
	private static final AdMessageKey MSG_Locked = AdMessageKey.of("TaxDeclaration_Locked");

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE })
	public void rejectWhenParentLocked(@NonNull final I_C_TaxDeclarationLine line)
	{
		final I_C_TaxDeclaration parent = InterfaceWrapperHelper.load(
				line.getC_TaxDeclaration_ID(), I_C_TaxDeclaration.class);
		if (parent != null && parent.isProcessed())
		{
			throw new AdempiereException(MSG_Locked);
		}
	}
}
