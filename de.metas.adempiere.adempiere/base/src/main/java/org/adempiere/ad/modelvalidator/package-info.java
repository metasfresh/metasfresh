/**
 * API for implementing and controlling the lifecycle of a model validator.
 * 
 * At this moment, the package contains only the API related to annotated model validators.
 * An annotated model validator can be any class which respects following format and is annotated properly.
 * e.g.
 * <code>
 * @Validator(I_C_Order.class)
 * public class C_Order
 * {
 * 	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE })
 *  public void doSomethingBeforePrepare(org.compiere.model.I_C_Order order)
 *  {
 *  ....
 *  }
 *  
 *  @ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
 *  public void doSomethingBeforeDelete(org.compiere.model.I_C_Order order)
 *  {
 *  ....
 *  }
 * }
 * </code>
 * 
 * To register this model validator, all you need to do is:
 * <code>
 * {@link org.compiere.model.ModelValidationEngine}.get().addModelValidator(new C_Order());
 * </code>
 * Internally, addModelValidator method will call check if the given object is an annotated model validator and if yes, it will use {@link org.adempiere.ad.modelvalidator.AnnotatedModelInterceptorFactory#createModelValidator(Object)} to wrap it and create an {@link org.adempiere.ad.modelvalidator.IModelInterceptor} implementation.
 * 
 * For more infos, please check annotations from org.adempiere.ad.modelvalidator.annotation package.
 * 
 * Please note that the old way of defining and using model validators is still fully supported.
 * 
 * @task http://dewiki908/mediawiki/index.php/02505:_Uncouple_Annotated_ModelValidators_from_API_implementations_%282012022410000053%29
 * 
 * @see org.adempiere.ad.modelvalidator.annotations.Validator
 * @see org.adempiere.ad.modelvalidator.annotations.ModelChange
 * @see org.adempiere.ad.modelvalidator.annotations.ModelChanges
 * @see org.adempiere.ad.modelvalidator.annotations.DocValidate
 * @see org.adempiere.ad.modelvalidator.annotations.DocValidates
 */
package org.adempiere.ad.modelvalidator;

