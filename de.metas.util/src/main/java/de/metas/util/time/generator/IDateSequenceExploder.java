package de.metas.util.time.generator;

import java.time.LocalDateTime;
import java.util.Set;

public interface IDateSequenceExploder
{
	Set<LocalDateTime> explodeForward(LocalDateTime date);

	Set<LocalDateTime> explodeBackward(LocalDateTime date);
}
