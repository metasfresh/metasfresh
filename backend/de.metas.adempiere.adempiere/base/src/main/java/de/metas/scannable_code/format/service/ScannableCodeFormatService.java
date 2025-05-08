package de.metas.scannable_code.format.service;

import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import de.metas.scannable_code.format.repository.ScannableCodeFormatRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScannableCodeFormatService
{
	@NonNull private final ScannableCodeFormatRepository repository;

	public ScannableCodeFormatsCollection getAll() {return repository.getAll();}
}
