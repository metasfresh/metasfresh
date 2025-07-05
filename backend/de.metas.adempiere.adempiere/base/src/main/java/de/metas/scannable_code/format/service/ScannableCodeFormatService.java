package de.metas.scannable_code.format.service;

import de.metas.i18n.ExplainedOptional;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatQuery;
import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import de.metas.scannable_code.format.repository.ScannableCodeFormatRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScannableCodeFormatService
{
	@NonNull private final ScannableCodeFormatRepository repository;

	public static ScannableCodeFormatService newInstanceForUnitTesting()
	{
		SpringContextHolder.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				ScannableCodeFormatService.class,
				() -> new ScannableCodeFormatService(
						new ScannableCodeFormatRepository()
				)
		);
	}

	public ExplainedOptional<ParsedScannedCode> parse(@NonNull final ScannedCode scannedCode) {return repository.getAll().parse(scannedCode);}

	public ScannableCodeFormatsCollection getAll() {return repository.getAll();}

	public ScannableCodeFormat create(@NonNull final ScannableCodeFormatCreateRequest request)
	{
		return repository.create(request);
	}

	public int delete(@NotNull final ScannableCodeFormatQuery query)
	{
		return repository.delete(query);
	}
}
