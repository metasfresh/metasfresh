/**
 * Annotations for callout classes.
 *
 * Using the annotations, it is possible to use callouts "programatically only", without the need to register them in the database's application dictionary.<br>
 * Annotated callout classes can be registered by calling the
 * {@link org.adempiere.ad.callout.spi.IProgramaticCalloutProvider#registerAnnotatedCallout(Object) registerAnnotatedCallout(Object)} method of an {@link org.adempiere.ad.callout.spi.IProgramaticCalloutProvider}.
 * <p>
 * Also see the {@link org.adempiere.ad.modelvalidator.annotations} package, those annotations are analog to the callout annotations, but for model interceptors.<br>
 * Depending on the use case, one and the same method can be annotated by with both a callout and a model interceptor annotation.
 */
package org.adempiere.ad.callout.annotations;
