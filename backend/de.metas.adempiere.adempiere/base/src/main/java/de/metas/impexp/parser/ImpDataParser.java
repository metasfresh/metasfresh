package de.metas.impexp.parser;

import org.springframework.core.io.Resource;

import java.util.stream.Stream;

public interface ImpDataParser
{
	Stream<ImpDataLine> streamDataLines(Resource resource);
}
