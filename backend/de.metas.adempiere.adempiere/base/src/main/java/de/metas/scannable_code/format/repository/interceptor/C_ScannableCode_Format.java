package de.metas.scannable_code.format.repository.interceptor;

import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.repository.ScannableCodeFormatRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_ScannableCode_Format;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_ScannableCode_Format.class)
@Component
@RequiredArgsConstructor
public class C_ScannableCode_Format
{
	@NonNull private final ScannableCodeFormatRepository repository;

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(final I_C_ScannableCode_Format record)
	{
		repository.deleteParts(ScannableCodeFormatId.ofRepoId(record.getC_ScannableCode_Format_ID()));
	}
}
