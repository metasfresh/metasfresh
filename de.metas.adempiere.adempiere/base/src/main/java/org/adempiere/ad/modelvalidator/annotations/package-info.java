/**
 * Annotations for model interceptor (aka model validator) classes.
 * <p>
 * Note that a model interceptor class using any of these annotations still has to be "explicitly" registered.<br>
 * Registering a model interceptor can be done in two ways:
 * <ul>
 * <li>Adding a record to the {@value org.compiere.model.I_AD_ModelValidator#Table_Name} table.<br>
 * This is usually done with "module level" model interceptors which then register a number of "model level" interceptors.<br>
 * However, note that such module level interceptors should subclass {@link org.adempiere.ad.modelvalidator.AbstractModuleInterceptor} and override some or all of its methods,<br>
 * rather than using this package's annotations.
 * </li>
 * <li>Calling {@link org.adempiere.ad.modelvalidator.IModelValidationEngine#addModelValidator(Object, org.compiere.model.I_AD_Client)} with an instance of the model interceptor that shall be registered.<br>
 * This is usually done from the "module level" interceptor.
 * The model validation engine then uses reflection to find out which methods with which annotations the model interceptor has and makes sure that they will be called in the appropriate ways at runtime.
 * <p>
 * On a sidenote: Model interceptors are always considered to be singletons.
 * </li>
 * </ul>
 *
 * Also see the {@link org.adempiere.ad.callout.annotations} package, those annotations are analog to the model interceptor annotations, but for callouts.<br>
 * Depending on the use case, one and the same method can be annotated by with both a callout and a model interceptor annotation.
 *
 */
package org.adempiere.ad.modelvalidator.annotations;
