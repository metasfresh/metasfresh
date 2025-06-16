package de.metas.scannable_code.format.service;

import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatQuery;
import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import de.metas.scannable_code.format.repository.ScannableCodeFormatRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScannableCodeFormatService
{
	@NonNull private final ScannableCodeFormatRepository repository;

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
