/**
 * This package contains model interceptors whose job it is to fire events on changes that are relevant for the material disposition service.
 * Note that the migration script which adds the {@link de.metas.material.interceptor.Main} {@link org.adempiere.ad.modelvalidator.AbstractModuleInterceptor} 
 * to the {@code AD_Model_Validator} table is contained not here but in the respective module. 
 * Therefore the model interceptors will only be added/activated if that module is part of the distribution.
 * 
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
package de.metas.material.interceptor;
