package de.metas.cache.rest;

import de.metas.cache.CCache;
import de.metas.cache.CCacheConfigDefaults;
import de.metas.cache.CCacheStatsOrderBy;
import de.metas.cache.CCacheStatsPredicate;
import de.metas.cache.CacheInterface;
import de.metas.cache.CacheMgt;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

public abstract class CacheRestControllerTemplate
{
	protected final CacheMgt cacheMgt = CacheMgt.get();

	protected static final CCacheStatsOrderBy DEFAULT_ORDER_BY = CCacheStatsOrderBy.builder()
			.descending(CCacheStatsOrderBy.Field.size)
			.build();

	protected abstract void assertAuth();

	@GetMapping("/config/defaults")
	public CCacheConfigDefaults getConfigDefaults()
	{
		assertAuth();
		return cacheMgt.getConfigDefaults();
	}

	@GetMapping("/reset")
	public final JsonCacheResetResponse cacheReset(@NonNull final HttpServletRequest httpRequest)
	{
		return cacheReset(JsonCacheResetRequest.extractFrom(httpRequest));
	}

	@PostMapping("/reset")
	public final JsonCacheResetResponse cacheReset(@RequestBody @NonNull final JsonCacheResetRequest request)
	{
		assertAuth();

		final JsonCacheResetResponse response = new JsonCacheResetResponse();

		{
			final long count = cacheMgt.reset();
			response.addLog("CacheMgt: invalidate " + count + " items");
		}
		{
			cacheResetAdditional(response, request);
		}
		{
			Services.get(IUserRolePermissionsDAO.class).resetLocalCache();
			response.addLog("user/role permissions: cache invalidated");
		}
		{
			System.gc();
			response.addLog("system: garbage collected");
		}

		return response;
	}

	protected void cacheResetAdditional(@NonNull final JsonCacheResetResponse response, @NonNull final JsonCacheResetRequest request) {}

	@GetMapping("/stats")
	public JsonGetStatsResponse getStats(
			@RequestParam(name = "cacheName", required = false) @Nullable final String cacheName,
			@RequestParam(name = "minSize", required = false) @Nullable final Integer minSize,
			@RequestParam(name = "labels", required = false) @Nullable final String labels,
			@RequestParam(name = "orderBy", required = false) @Nullable final String orderByString)
	{
		assertAuth();
		return getStats(
				JsonCacheStatsQuery.builder()
						.cacheName(cacheName)
						.minSize(minSize)
						.labels(labels)
						.orderByString(orderByString)
						.build()
		);
	}

	@PostMapping("/stats")
	public JsonGetStatsResponse getStats(@RequestBody @NonNull final JsonCacheStatsQuery jsonQuery)
	{
		assertAuth();

		final CCacheStatsPredicate filter = jsonQuery.toCCacheStatsPredicate();
		final CCacheStatsOrderBy orderBy = jsonQuery.toCCacheStatsOrderBy().orElse(DEFAULT_ORDER_BY);

		return cacheMgt.streamStats(filter)
				.sorted(orderBy)
				.map(JsonCacheStats::of)
				.collect(JsonGetStatsResponse.collect());
	}

	@GetMapping("/byId/{cacheId}")
	public JsonCache getById(
			@PathVariable("cacheId") final String cacheIdStr,
			@RequestParam(name = "limit", required = false, defaultValue = "100") final int limit)
	{
		assertAuth();

		final long cacheId = Long.parseLong(cacheIdStr);
		final CacheInterface cacheInterface = cacheMgt.getById(cacheId)
				.orElseThrow(() -> new AdempiereException("No cache found by cacheId=" + cacheId));

		if (cacheInterface instanceof final CCache<?, ?> cache)
		{
			return JsonCache.of(cache, limit);
		}
		else
		{
			throw new AdempiereException("Cache of type " + cacheInterface.getClass().getSimpleName() + " is not supported");
		}
	}

	@GetMapping("/byId/{cacheId}/reset")
	public void resetCacheById(@PathVariable("cacheId") final String cacheIdStr)
	{
		assertAuth();

		final long cacheId = Long.parseLong(cacheIdStr);
		final CacheInterface cacheInterface = cacheMgt.getById(cacheId)
				.orElseThrow(() -> new AdempiereException("No cache found by cacheId=" + cacheId));

		cacheInterface.reset();
	}

}