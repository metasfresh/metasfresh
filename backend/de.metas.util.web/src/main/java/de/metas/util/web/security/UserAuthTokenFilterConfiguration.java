package de.metas.util.web.security;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class UserAuthTokenFilterConfiguration
{
	private static final Logger logger = LogManager.getLogger(UserAuthTokenFilterConfiguration.class);

	private final CopyOnWriteArrayList<PathMatcher> excludedPaths = new CopyOnWriteArrayList<>();

	public void excludePathContaining(@NonNull final String containing)
	{
		final PathMatcher matcher = PathMatcher.builder()
				.containing(containing)
				.build();
		excludedPaths.add(matcher);
		logger.info("Excluding path: {}", matcher);
	}

	public boolean isExcludedFromSecurityChecking(@NonNull final HttpServletRequest httpRequest)
	{
		if (excludedPaths.isEmpty())
		{
			return false;
		}

		final String path = httpRequest.getServletPath();
		if (path == null)
		{
			return false;
		}

		return excludedPaths.stream().anyMatch(matcher -> matcher.isMatching(path));
	}

	@Value
	@Builder
	private static class PathMatcher
	{
		@NonNull String containing;

		public boolean isMatching(final String path) {return path.contains(containing);}
	}

}
