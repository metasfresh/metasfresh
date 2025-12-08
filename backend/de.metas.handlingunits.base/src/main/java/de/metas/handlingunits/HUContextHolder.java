package de.metas.handlingunits;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.lang.IAutoCloseable;

import javax.annotation.Nullable;

@UtilityClass
public class HUContextHolder
{
	private static final ThreadLocal<IMutableHUContext> threadLocal = new ThreadLocal<>();

	public static IAutoCloseable temporarySet(@NonNull final IMutableHUContext huContext)
	{
		final IMutableHUContext huContextPrev = threadLocal.get();
		threadLocal.set(huContext);
		return () -> threadLocal.set(huContextPrev);
	}

	@NonNull
	public static IMutableHUContext getCurrent()
	{
		return Check.assumeNotNull(getCurrentOrNull(), "Expected HUContext to be set");
	}

	@Nullable
	public static IMutableHUContext getCurrentOrNull()
	{
		return threadLocal.get();
	}
}
