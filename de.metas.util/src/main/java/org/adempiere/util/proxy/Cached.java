package org.adempiere.util.proxy;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adempiere.util.Services;

// import javax.interceptor.InterceptorBinding;

/**
 * Use this annotation to indicate that a method result should be cached.
 *
 * <p>
 * Notes:
 * <ul>
 * <li>Currently this only works for service implementations that are returned by {@link Services#get(Class)}.<br>
 * Technically, instead of returning the service implementation itself, {@link Services#get(Class)} returns a proxy which can either forward to the service implementation or return a cached result.</li>
 * <li>Use {@link Services#getInterceptor()} to get a decorator and then register your interceptor using {@link org.adempiere.util.proxy.IServiceInterceptor#registerInterceptor(Class, Object)}</li>
 * <li><b>Private methods will NOT be intercepted</b></li>
 * <li><b>Final methods or classes will NOT be intercepted</b></li>
 * </ul>
 *
 * TODO: move to the rough area of where the interceptor impl is.
 *
 * @see CacheIgnore
 * @author metas-dev <dev@metasfresh.com>
 */
// @InterceptorBinding
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Cached
{
	/** Never expire marker */
	int EXPIREMINUTES_Never = 0;

	/**
	 * Optional array of properties (of Object which contains annotated method!) whose values are also used when creating the hash key
	 *
	 * @return
	 */
	String[]keyProperties() default {};

	/**
	 * If true, the actual instance, whose method is cached, is not included in the caching key
	 *
	 * @return
	 */
	boolean ignoreInstance() default false;

	/**
	 * Cache name. This attribute is used to invalidate staled caches. Please include the table name(s) of the object(s) that is (are) cached.
	 *
	 * @return
	 */
	String cacheName() default "";

	/**
	 * Optional: Minutes to expire.
	 *
	 * Possible values:
	 * <ul>
	 * <li>less than ZERO - let the caching framework decide (i.e. use default value of caching framework)
	 * <li>{@link #EXPIREMINUTES_Never} (i.e. ZERO) - never expire
	 * <li>greater than ZERO - minutes to expire
	 */
	int expireMinutes() default -1;
}
